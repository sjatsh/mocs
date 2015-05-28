package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.common.device.JsonResponseResult;
import smtcl.mocs.common.device.pages.DataPage;
import smtcl.mocs.common.device.pages.PageListBaseBean;
import smtcl.mocs.common.device.pages.PageListDataModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.StringUtils;


/**
 * 机床事件分析对应
 * @作者：JiangFeng
 * @创建时间：2013-04-26
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "StatDeviceMachineEventBean")
@ViewScoped
public class StatDeviceMachineEventBean extends PageListBaseBean implements Serializable {
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 机床名称
	 */
	private String machineName;
	/**
	 * 机床序列号集合
	 */
	private List<SelectItem> machineNameList =new ArrayList<SelectItem>();
	/**
	 * 事件名称
	 */
	private String eventName;
	/**
	 * 事件名称集合
	 */
	private List<SelectItem> eventNameList = new ArrayList<SelectItem>();
	/**
	 * 结果集
	 */
	private IDataCollection<Map<String, Object>> results;
	/**
	 * 当前节点
	 */
	private String nodeStr;
	/**
	 * 当前节点的子节点 ID 集合
	 */
	private String nodeIdList;
	/**
	 * 返回给页面的机床事件信息
	 */
	private String machineEvent;
	/**
	 * 设备接口实例
	 */
	private IDeviceService deviceService=(IDeviceService) ServiceFactory.getBean("deviceService");
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService"); // 获取注入
	
	private String dateTime=null;//当前图 显示的天
	private List<Map<String, Object>> datelist;//当前查询条件的 天的集合
	private List<Map<String, Object>> zonglist;//当前查询条件所有的数据集合
	@SuppressWarnings("unchecked")
	public List<SelectItem> getMachineNameList() {
		if (null != nodeStr && !"".equals(nodeStr)) {
			machineNameList =new ArrayList<SelectItem>();
			List<TEquipmentInfo> nlist = deviceService.getNodesAllEquName(nodeIdList);
			for (TEquipmentInfo teinfo : nlist) {
				machineNameList.add(new SelectItem(teinfo.getEquSerialNo(),teinfo.getEquName()));
			}
			if(machineNameList.size()>0 && StringUtils.isEmpty(machineName)){
				machineName=(String) machineNameList.get(0).getValue();
			}
		}
		System.out.println( machineName);
		return machineNameList;
	}
	
	public List<SelectItem> getEventNameList() {
		eventNameList.clear();
		eventNameList.add(new SelectItem("--所有--"));
		List<String> eventList = deviceService.getAllEventName();
		for (String str : eventList) {
            if(str.getBytes().length!=str.length()){//判断是否是汉字2014年12月26日11:58:28-lopman
                eventNameList.add(new SelectItem(str));
            }
		}
		//getDeviceMachineEventStatAction();
		return eventNameList;
	}
	
	public String getNodeStr() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String node=session.getAttribute("nodeid")+"";
			this.nodeStr=node;
			List<TNodes> tn=organizationService.getTNodesById(node);
			nodeIdList = organizationService.getAllTNodesId(tn.get(0));
	
		return nodeStr;
	}

	/**
	 * 机床事件重写的分页方法
	 */
	@Override
	public PageListDataModel getDefaultDataModel() {
		if (defaultDataModel == null) {
			defaultDataModel = new PageListDataModel(pageSize) {
				@Override
				public DataPage fetchPage(int startRow, int pageSize) {
					System.out.println("当前静茹分页的名称-"+machineName);
					Collection<Parameter> parameters = new HashSet<Parameter>();

					// 判断选择的下拉框是否为默认
					if (!StringUtils.isEmpty(eventName)&& eventName.equals("--所有--")) {
						eventName = null;
					}
					if (StringUtils.isEmpty(machineName)) {
						machineName = null;
					}
					// 条件的追加
					if (startTime != null) {
						parameters.add(new Parameter(" a.eventTime ","startTime", startTime, Operator.GE));
					}
					if (endTime != null) {
						parameters.add(new Parameter(" a.eventTime ","endTime", endTime, Operator.LE));
					}
					parameters.add(new Parameter(" a.equSerialNo ","equSerialNo", machineName, Operator.LIKE));

					if (!StringUtils.isEmpty(eventName)) {
						parameters.add(new Parameter(" a.eventName ","eventName", eventName, Operator.LIKE));
					}
					results = deviceService.getDeviceMachineEventStat(startRow/ pageSize + 1, pageSize, parameters);
					System.out.println(results.getTotalRows()+"=========="+startTime+"---"+endTime+"----"+eventName+"---"+machineName);
					return new DataPage(results.getTotalRows(), startRow,results.getData());
				}
			};
		}
		return defaultDataModel;
	}

	/**
	 * 绘画机床事件时间分布柱状图
	 */
	public String MachineToolEventTimeDistributionChart() {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String machine;
		// 判断选择的下拉框是否为默认
		if (!StringUtils.isEmpty(eventName) && eventName.equals("--所有--")) {
			eventName = null;
		}
		if (StringUtils.isEmpty(machineName)) {
			machineName = null;
		}
		// 条件的追加
		if (startTime != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startTime);
			//calendar.add(Calendar.DAY_OF_MONTH, -1); 
			parameters.add(new Parameter(" a.eventTime ", "startTime",calendar.getTime(), Operator.GE));
		}
		if (endTime != null) {
			parameters.add(new Parameter(" a.eventTime ", "endTime", endTime,Operator.LE));
		}
		parameters.add(new Parameter(" a.equSerialNo ", "equSerialNo",machineName, Operator.LIKE));
 
		if (!StringUtils.isEmpty(eventName)) {
			parameters.add(new Parameter(" a.eventName ", "eventName",eventName, Operator.LIKE));
		}
		
 		zonglist = deviceService.getDeviceMachineEventStatChart(parameters);
		datelist =deviceService.getDeviceMachineEventStatChartDateCount(parameters);//时间列表
		List<Map<String, Object>> rslist=new ArrayList<Map<String, Object>>();//返回数据
		
		if(null!=datelist&&datelist.size()>0){
			dateTime=datelist.get(0).get("EVENTTIME").toString();
		}
		for(Map<String, Object> map:zonglist){
			String eventtime=map.get("EVENTTIME").toString().substring(0, 10);
			if(eventtime.trim().equals(dateTime.trim())){
				rslist.add(map);
			}
		}
		JsonResponseResult tt=new JsonResponseResult();
		tt.setContent(rslist);
		machine=tt.toJsonString();
		System.out.println(machine);
		return machine;
	}
	public void next(){
		List<Map<String, Object>> rslist=new ArrayList<Map<String, Object>>();//返回数据
		boolean bool=false;
		for(Map<String, Object> map:datelist){
			String eventtime=map.get("EVENTTIME").toString();
			if(eventtime.equals(dateTime)){
				bool=true;
				continue;
			}
			if(bool){
				dateTime=eventtime;
                break;
			}
		}
		for(Map<String, Object> map:zonglist){
			String eventtime=map.get("EVENTTIME").toString().substring(0, 10);
			if(eventtime.equals(dateTime)){
				rslist.add(map);
			}
		}
		JsonResponseResult tt=new JsonResponseResult();
		tt.setContent(rslist);
        this.machineEvent= tt.toJsonString();
		
	} 
	
	public void black(){
		List<Map<String, Object>> rslist=new ArrayList<Map<String, Object>>();//返回数据
        for(int i=0;i<datelist.size();i++){
			Map<String, Object> map=datelist.get(i);
			String eventtime=map.get("EVENTTIME").toString();
			if(eventtime.equals(dateTime)){
				if(i-1>-1){
					dateTime=datelist.get(i-1).get("EVENTTIME").toString();
					break;
				}
			}
		}
		for(Map<String, Object> map:zonglist){
			String eventtime=map.get("EVENTTIME").toString().substring(0, 10);
			if(eventtime.equals(dateTime)){
				rslist.add(map);
			}
		}
		JsonResponseResult tt=new JsonResponseResult();
		tt.setContent(rslist);
        this.machineEvent= tt.toJsonString();
	}
	@Override
	public PageListDataModel getExtendDataModel() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 机床事件页面查询按钮响应
	 */
	public void getDeviceMachineEventStatAction() {
		this.machineEvent = MachineToolEventTimeDistributionChart();
		this.defaultDataModel = null;
	}
	
	/**
	 * 无参构造函数
	 */
	public StatDeviceMachineEventBean() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String equSerialNo = StringUtils.getCookie(request, "equSerialNo");
		if (null != equSerialNo) {
			machineNameList= new ArrayList<SelectItem>();
			List<TEquipmentInfo> list = deviceService.getAllEquName(equSerialNo);
			if (list != null && list.size()>0) {
				machineNameList.add(new SelectItem(equSerialNo, list.get(0).getEquName()));
				machineName=equSerialNo;
			}
		}
		if (null == startTime && null == endTime) {
            Date date = StringUtils.convertDate(StringUtils.formatDate(new Date(),2),"yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, +1);
            startTime = date;
            endTime = calendar.getTime();
		}
		getDeviceMachineEventStatAction();
	}

	/*----------------------------------------------------------------------------------------------------------------*/
	public void setEventNameList(List<SelectItem> eventNameList) {
		this.eventNameList = eventNameList;
	}

	public String getEventName() {
		return eventName;
	}

	public String getMachineEvent() {
		return machineEvent;
	}

	public void setMachineEvent(String machineEvent) {
		this.machineEvent = machineEvent;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setMachineNameList(List<SelectItem> machineNameList) {
		this.machineNameList = machineNameList;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public IDeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public IDataCollection<Map<String, Object>> getResults() {
		return results;
	}

	public void setResults(IDataCollection<Map<String, Object>> results) {
		this.results = results;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

}

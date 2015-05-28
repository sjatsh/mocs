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
 * �����¼�������Ӧ
 * @���ߣ�JiangFeng
 * @����ʱ�䣺2013-04-26
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "StatDeviceMachineEventBean")
@ViewScoped
public class StatDeviceMachineEventBean extends PageListBaseBean implements Serializable {
	/**
	 * ��ʼʱ��
	 */
	private Date startTime;
	/**
	 * ����ʱ��
	 */
	private Date endTime;
	/**
	 * ��������
	 */
	private String machineName;
	/**
	 * �������кż���
	 */
	private List<SelectItem> machineNameList =new ArrayList<SelectItem>();
	/**
	 * �¼�����
	 */
	private String eventName;
	/**
	 * �¼����Ƽ���
	 */
	private List<SelectItem> eventNameList = new ArrayList<SelectItem>();
	/**
	 * �����
	 */
	private IDataCollection<Map<String, Object>> results;
	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr;
	/**
	 * ��ǰ�ڵ���ӽڵ� ID ����
	 */
	private String nodeIdList;
	/**
	 * ���ظ�ҳ��Ļ����¼���Ϣ
	 */
	private String machineEvent;
	/**
	 * �豸�ӿ�ʵ��
	 */
	private IDeviceService deviceService=(IDeviceService) ServiceFactory.getBean("deviceService");
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService"); // ��ȡע��
	
	private String dateTime=null;//��ǰͼ ��ʾ����
	private List<Map<String, Object>> datelist;//��ǰ��ѯ������ ��ļ���
	private List<Map<String, Object>> zonglist;//��ǰ��ѯ�������е����ݼ���
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
		eventNameList.add(new SelectItem("--����--"));
		List<String> eventList = deviceService.getAllEventName();
		for (String str : eventList) {
            if(str.getBytes().length!=str.length()){//�ж��Ƿ��Ǻ���2014��12��26��11:58:28-lopman
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
	 * �����¼���д�ķ�ҳ����
	 */
	@Override
	public PageListDataModel getDefaultDataModel() {
		if (defaultDataModel == null) {
			defaultDataModel = new PageListDataModel(pageSize) {
				@Override
				public DataPage fetchPage(int startRow, int pageSize) {
					System.out.println("��ǰ�����ҳ������-"+machineName);
					Collection<Parameter> parameters = new HashSet<Parameter>();

					// �ж�ѡ����������Ƿ�ΪĬ��
					if (!StringUtils.isEmpty(eventName)&& eventName.equals("--����--")) {
						eventName = null;
					}
					if (StringUtils.isEmpty(machineName)) {
						machineName = null;
					}
					// ������׷��
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
	 * �滭�����¼�ʱ��ֲ���״ͼ
	 */
	public String MachineToolEventTimeDistributionChart() {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String machine;
		// �ж�ѡ����������Ƿ�ΪĬ��
		if (!StringUtils.isEmpty(eventName) && eventName.equals("--����--")) {
			eventName = null;
		}
		if (StringUtils.isEmpty(machineName)) {
			machineName = null;
		}
		// ������׷��
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
		datelist =deviceService.getDeviceMachineEventStatChartDateCount(parameters);//ʱ���б�
		List<Map<String, Object>> rslist=new ArrayList<Map<String, Object>>();//��������
		
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
		List<Map<String, Object>> rslist=new ArrayList<Map<String, Object>>();//��������
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
		List<Map<String, Object>> rslist=new ArrayList<Map<String, Object>>();//��������
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
	 * �����¼�ҳ���ѯ��ť��Ӧ
	 */
	public void getDeviceMachineEventStatAction() {
		this.machineEvent = MachineToolEventTimeDistributionChart();
		this.defaultDataModel = null;
	}
	
	/**
	 * �޲ι��캯��
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

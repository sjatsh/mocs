package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.model.DeviceInfoModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 用户查询多台机床之间的OEE走势比较
 * @作者：JiangFeng
 * @创建时间：2013-04-26
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "compareDevicesOEEEfficiencyBean")
@ViewScoped
public class CompareDevicesOEEEfficiencyBean implements Serializable {
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 时间单位条件
	 */
	private String dateTime="天";
	/**
	 * 时间单位条件集合
	 */
	private List<SelectItem> dateTimeList = new ArrayList<SelectItem>();
	/**
	 * 机床序列号
	 */
	private String[] serNames;
	/**
	 * 机床序列号集合
	 */
	private List<SelectItem> serNamesList = new ArrayList<SelectItem>();
	/**
	 * 节点集
	 */
	private String nodeids;
	/**
	 * 显示的折线图类型
	 */
	private String otBy = "OEE";
	/**
	 * 显示的折线图类型集合
	 */
	private List<SelectItem> otByList = new ArrayList<SelectItem>();
	/**
	 * 设备接口实例
	 */
	private IDeviceService deviceService=(IDeviceService)ServiceFactory.getBean("deviceService");;
	/**
	 * 结果集
	 */
	private List<DeviceInfoModel> result;
	/**
	 * 当前节点
	 */
	private String nodeStr; 
	/**
	 * 曲线图的加载
	 */
	private String chartData;
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");

	public List<SelectItem> getOtByList() {
		otByList.clear();
		otByList.add(new SelectItem("OEE"));
		otByList.add(new SelectItem("TEEP"));
		
		otByList.add(new SelectItem("可用率"));
		otByList.add(new SelectItem("表现性"));
		otByList.add(new SelectItem("质量"));
		
		otByList.add(new SelectItem("加工工件数"));
		otByList.add(new SelectItem("合格工件数"));
		otByList.add(new SelectItem("实际加工时间"));
		return otByList;
	}
	
	public List<SelectItem> getDateTimeList() {
		dateTimeList.clear();// 为时间单位条件添加数据
		dateTimeList.add(new SelectItem("天"));
		dateTimeList.add(new SelectItem("周"));
		dateTimeList.add(new SelectItem("月"));
		dateTimeList.add(new SelectItem("年"));
		return dateTimeList;
	}
	
	public String getNodeStr() {
		/*com.swg.authority.cache.TreeNode currentNode = (com.swg.authority.cache.TreeNode) FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			this.nodeStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
			nodeids = organizationService.getAllTNodesId(currentNode);
		}*/
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String node=session.getAttribute("nodeid")+"";
		this.nodeStr=node;
		List<TNodes> tn=organizationService.getTNodesById(node);
		nodeids = organizationService.getAllTNodesId(tn.get(0));
		return nodeStr;
	}

	@SuppressWarnings("unchecked")
	public List<SelectItem> getSerNamesList() {
		serNames=null;
		// 设备总览页面跳转所需要的数据加载
		String equSerialStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("equSerialStr");
		if (null != equSerialStr && !"".equals(equSerialStr)) {
			serNames = null;
			serNamesList.clear();
			String[] st = equSerialStr.split(",");
			String s = "";
			for (int i = 0; i < st.length; i++) {
				s += "'" + st[i] + "',";
			}
			List<TEquipmentInfo> strList = deviceService.getAllEquName(s.substring(0, s.length() - 1));
			for (TEquipmentInfo str : strList) {
				serNamesList.add(new SelectItem(str.getEquSerialNo() + "", str.getEquSerialNo() + ""));
			}
		}

		if (null != nodeStr && !"".equals(nodeStr)) {
			serNames = null;
			serNamesList.clear();
			List<TEquipmentInfo> strList = deviceService.getNodesAllEquName(nodeids);
				for (TEquipmentInfo str : strList) {
					serNamesList.add(new SelectItem(str.getEquSerialNo() + "",str.getEquSerialNo() + ""));
				}
		}
		return serNamesList;
	}
	
	/**
	 * 页面点击查询按钮响应
	 */
	public void getDevicesCompareAction() {
		if (startTime != null && endTime != null && serNames.length != 0) {
			
			result = deviceService.getDevicesOEECompare(startTime, endTime, dateTime, serNames);
			// diagramOnLoad(result);
			compareDevicesOeeChart();
		}else{
			chartData=null;
		}
	}

	/**
	 * 点击复选框与单选按钮
	 */
	public void selectNo() {
		getDevicesCompareAction();
	}

	/**
	 * 获取查询条件中的数据信息
	 * @return 集合DeviceInfoModel数据
	 */
	public Map<String, List<DeviceInfoModel>> chartData() {
		Map<String, List<DeviceInfoModel>> map = new HashMap<String, List<DeviceInfoModel>>();
		for (int i = 0; i < serNames.length; i++) {
			List<DeviceInfoModel> mList = new ArrayList<DeviceInfoModel>();
			for (DeviceInfoModel de : result) {
				if (serNames[i].equals(de.getEquSerialNo())) {
					DeviceInfoModel dInfoModel = new DeviceInfoModel();
					dInfoModel.setOeeTime(de.getOeeTime());
					dInfoModel.setDayOeevalue(de.getDayOeevalue());
					dInfoModel.setDayTeepvalue(de.getDayTeepvalue());
					
					dInfoModel.setAvailability(de.getAvailability());
					dInfoModel.setExpressivenessOf(de.getExpressivenessOf());
					dInfoModel.setQuality(de.getQuality());
					
					dInfoModel.setQualifiedPartCount(de.getQualifiedPartCount());
					dInfoModel.setWorktimeFact(de.getWorktimeFact());
					dInfoModel.setProcessPartsCount(de.getProcessPartsCount());
					
					mList.add(dInfoModel);
				}
			}
			map.put(serNames[i], mList);
		}
		return map;
	}

	/**
	 * 加载曲线图形
	 */
	public String compareDevicesOeeChart() {
		Map<String, Object> data = new HashMap<String, Object>();

		List<String> list = new ArrayList<String>();
		for (DeviceInfoModel de : result) {
			System.out.println(de.getOeeTime());
			if (!list.contains(de.getOeeTime())) {
				list.add(de.getOeeTime());
			}
		}

		// 横坐标日期数组 2013
		String[] colDate = list.toArray(new String[1]);

		double[][] devicesOEE = new double[serNames.length][colDate.length];
		double[][] devicesTEEP = new double[serNames.length][colDate.length];
		
		double[][] availability=new double[serNames.length][colDate.length];//可用率
		double[][] expressivenessOf=new double[serNames.length][colDate.length];//表现性
		double[][] quality=new double[serNames.length][colDate.length];//质量
		
		double[][] qualifiedPartCount=new double[serNames.length][colDate.length];//合格工件数
		double[][] worktimeFact=new double[serNames.length][colDate.length];//实际开机时间
		double[][] processPartsCount=new double[serNames.length][colDate.length];//加工工件数
		
		Map<String, List<DeviceInfoModel>> dMap = this.chartData();
		for (int i = 0; i < serNames.length; i++) {
			int j = 0;
			for (DeviceInfoModel dev : dMap.get(serNames[i])) {
				if (null==dev.getOeeTime()|| dev.getOeeTime().equals("")) {
					devicesOEE[i][j] = 0;
					devicesTEEP[i][j] = 0;
					
					availability[i][j] = 0;
					expressivenessOf[i][j] = 0;
					quality[i][j] = 0;
					
					qualifiedPartCount[i][j] = 0;
					worktimeFact[i][j] = 0;
					processPartsCount[i][j] = 0;
					j++;
				} else {
					devicesOEE[i][j] = Double.parseDouble(dev.getDayOeevalue());
					devicesTEEP[i][j] = Double.parseDouble(dev.getDayTeepvalue());
					
					availability[i][j] = Double.parseDouble(dev.getAvailability());
					expressivenessOf[i][j] = Double.parseDouble(dev.getExpressivenessOf());
					quality[i][j] = Double.parseDouble(dev.getQuality());
					
					qualifiedPartCount[i][j] = Double.parseDouble(dev.getQualifiedPartCount());
					worktimeFact[i][j] = Double.parseDouble(dev.getWorktimeFact());
					processPartsCount[i][j] = Double.parseDouble(dev.getProcessPartsCount());
					j++;
				}

			}
		}
		data.put("colData", colDate);
		if(otBy.trim().toUpperCase().trim().equals("OEE")){
			data.put("otValue", devicesOEE);
			data.put("title", "多台机床OEE比较");
		}else if(otBy.trim().toUpperCase().trim().equals("TEEP")){
			data.put("otValue", devicesTEEP);
			data.put("title", "多台机床TEEP比较");
		}else if(otBy.trim().equals("可用率")){
			data.put("otValue", availability);
			data.put("title", "多台机床可用率比较");
		}else if(otBy.trim().equals("表现性")){
			data.put("otValue", expressivenessOf);
			data.put("title", "多台机床表现性比较");
		}else if(otBy.trim().equals("质量")){
			data.put("otValue", quality);
			data.put("title", "多台机床质量比较");
		}else if(otBy.trim().equals("加工工件数")){
			data.put("otValue", processPartsCount);
			data.put("title", "多台机床加工工件数比较");
		}else if(otBy.trim().equals("合格工件数")){
			data.put("otValue", qualifiedPartCount);
			data.put("title", "多台机床合格工件数比较");
		}else{
			data.put("otValue", worktimeFact);
			data.put("title", "多台机床实际加工时间比较");
		}
		data.put("serNames", serNames);
		data.put("devicesSize", serNames.length);

		Gson gson = new GsonBuilder().serializeNulls().create();
		chartData = gson.toJson(data);
		System.out.println(chartData);
		return gson.toJson(data);
	}

	/**
	 * 无参构造函数
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CompareDevicesOEEEfficiencyBean() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String equSerialNo = StringUtils.getCookie(request, "str");
		IDeviceService ds = (IDeviceService) ServiceFactory.getBean("deviceService"); // 获取注入;
		if (null != equSerialNo) {
			String equ[] = equSerialNo.split("%2C");
			List li = new ArrayList();
			for (String tt : equ) {
				System.out.println(tt + "---");
				li.add(tt);
			}
			String hqlvalve = StringUtils.returnHqlIN(li);
			List<TEquipmentInfo> list = ds.getAllEquName(hqlvalve);
			if (list != null) {
				serNames = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					serNamesList.add(new SelectItem(list.get(i).getEquSerialNo(), list.get(i).getEquSerialNo()));
					serNames[i] = list.get(i).getEquSerialNo();
					System.err.println(list.get(i).getEquSerialNo());
				}
			}
		}

		if (null == startTime && null == endTime) {
			Date[] datea = StringUtils.convertDatea(1);
			startTime = datea[0];
			endTime = datea[1];
		}
		getDevicesCompareAction();
	}

	/*----------------------------------------------------------------------------------*/

	public void setSerNamesList(List<SelectItem> serNamesList) {
		this.serNamesList = serNamesList;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public void setDateTimeList(List<SelectItem> dateTimeList) {
		this.dateTimeList = dateTimeList;
	}
	
	public String getChartData() {
		return chartData;
	}

	public void setChartData(String chartData) {
		this.chartData = chartData;
	}

	public String[] getSerNames() {
		return serNames;
	}

	public void setSerNames(String[] serNames) {
		this.serNames = serNames;
	}

	public String getOtBy() {
		return otBy;
	}

	public void setOtBy(String otBy) {
		this.otBy = otBy;
	}

	public void setOtByList(List<SelectItem> otByList) {
		this.otByList = otByList;
	}

	public IDeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public List<DeviceInfoModel> getResult() {
		return result;
	}

	public void setResult(List<DeviceInfoModel> result) {
		this.result = result;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

}

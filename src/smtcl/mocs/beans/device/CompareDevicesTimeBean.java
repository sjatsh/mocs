package smtcl.mocs.beans.device;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.StringUtils;



/**
 * 时间比较
 * @创建时间 2012-04-26
 * @作者 gaozhoujun
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name="CompareDevicesTimeBean")
@ViewScoped
public class CompareDevicesTimeBean {
	/**
	 * 开始时间
	 */
	private Date start;
	/**
	 * 结束时间
	 */
	private Date end;
	 /**
	  * 当前节点
	  */
	private String nodeStr;              
	/**
	 * 设备业务逻辑
	 */
	private IDeviceService deviceService = (IDeviceService)ServiceFactory.getBean("deviceService");
	
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
	
	/**
	 * 多选框显示页面数据
	 */
	private List<SelectItem> serNamesList = new ArrayList<SelectItem>();
	
	/**
	 * 多选框过滤数据
	 */
	private String[] serNames;
	/**
	 * 设备结果集合
	 */
	private Map<String, Object> map;
	
	/**
	 * 柱状图返回数据
	 */
	private  String jsonData;
	
	/**
	 * 图表设备名称集合
	 */
	private  List<String>  allequName =new ArrayList<String>();
	
	/**
	 * 图表数据集合
	 */
	private  double[][] arraylist=new double[5][];  
	
	/**
	 * 当前节点下所有节点id
	 */
	private String nodeids;
	
	/**
	 * 判断是否当前节点
	 * */
	private String nodeValue;
	
	/**
	 * 多选框过滤数据
	 */
	private List<String> selectedDevices=new ArrayList<String>(); 
	
	/**
	  * 复选框选中事件
	  */
	 public void itemSelect(){
		if(serNames.length > 5) {
			return;
		}
		 //获取多选框的值
		 queryAction();
	 }
	 /**
	  * 图表添加数据
	  * @param list
	  */
	 public void createCartesianChartModel(List<Map<String, Object>> list){
		 if(null != allequName && allequName.size() > 0){//清空图表设备名称集合
			 allequName.clear();
		 }
		
		  double[] stop=null;
		  stop=new double[list.size()];//关机
		  double[] prepare=null;
		  prepare=new double[list.size()];//准备
		  double[] process=null;
		  process=new double[list.size()];//加工
		  double[] idle=null;
		  idle=new double[list.size()];//空闲
		  double[] breakdown=null;
		  breakdown=new double[list.size()];//故障
		
		 DecimalFormat decimal = new DecimalFormat("#.##");// 留小数点2位
		 Long total=null;//图表展现时间和
		 double numbera=0;//准备时间处理
		 double numberb=0;//加工时间处理
		 double numberc=0;//空闲待机时间处理
		 double numbere=0;//关机时间处理
		 double numberf=0;//故障时间处理
		 for(int i=0;i<list.size();i++){
			 total=(Long)list.get(i).get("prepareTime")+(Long)list.get(i).get("processTime")+(Long)list.get(i).get("idleTime")+(Long)list.get(i).get("stopTime")
					 +(Long)list.get(i).get("breakdownTime");
			 System.out.println("total=="+total);
			  numbera=Double.parseDouble(list.get(i).get("prepareTime").toString());
			  numberb=Double.parseDouble(list.get(i).get("processTime").toString());
			  numberc=Double.parseDouble(list.get(i).get("idleTime").toString());
			  numbere=Double.parseDouble(list.get(i).get("stopTime").toString());
			  numberf=Double.parseDouble(list.get(i).get("breakdownTime").toString());
			 if(null!=total){
				 prepare[i]=Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numbera/total))*100));
				 process[i]=Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numberb/total))*100));
				 idle[i]=Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numberc/total))*100));
				 stop[i]=Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numbere/total))*100));
				 breakdown[i]=100-(Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numbera/total))*100)))-(Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numberb/total))*100)))-(Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numberc/total))*100)))
						 -(Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numbere/total))*100)));
				 allequName.add(list.get(i).get("equName").toString());//封装设备名称
			 }
		 }
		 if(null != arraylist && arraylist.length > 0){
			 arraylist=new double[5][];
		 }
		 //封装2维数组
		 for(int i=0;i<arraylist.length;i++){
			 if(i == 0){
				 arraylist[i]=stop;
			 }
			 if(i == 1){
				 arraylist[i]=prepare;
			 }
			 if(i == 2){
				 arraylist[i]=process;
			 }
			 if(i == 3){
				 arraylist[i]=idle;
			 }
			 if(i == 4){
				 arraylist[i]=breakdown;
			 }
		 }
	 }
	 /**
	  * 当前节点下的设备     
	  * @param nodeids
	  */
	 @SuppressWarnings("unchecked")
     public void getnameList(String nodeids){
    
	   if(null != serNamesList && serNamesList.size() > 0){
    		serNamesList.clear();
    	}
    	if(null != serNames && serNames.length > 0){
    		serNames=null;
    	}
    	if(null != selectedDevices && selectedDevices.size() > 0){
    		selectedDevices.clear();
    	}
    	
    	if(null != nodeStr && !"".equals(nodeStr)){
			List<TEquipmentInfo> nlist=deviceService.getNodesAllEquName(nodeids);
			for(int i=0;i<nlist.size();i++){
				TEquipmentInfo teinfo=nlist.get(i);
				if(null!=teinfo){
					serNamesList.add(new SelectItem(teinfo.getEquSerialNo(),(String)teinfo.getEquSerialNo()));//把数据交给多选框显示
					if(i < 5){
						selectedDevices.add((String)teinfo.getEquSerialNo());
					}
				}
			}
			/**
			 * 单击节点，显示5个复选框
			 */
			serNames=new String[selectedDevices.size()];
			for (int i = 0; i < selectedDevices.size(); i++) {
				serNames[i] = selectedDevices.get(i);
			}
		}
    }
	/**
	  * 查询方法
	  */
	 public void queryAction(){		
		
		Collection<Parameter> parameters = new HashSet<Parameter>();
		//时间过滤
		for(String tt:serNames){
			System.out.println(tt);
		}
		//多选框的过滤
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		if(null!=serNames && serNames.length > 0){//多选框选中
			parameters.add(new Parameter("te.equSerialNo","equSerialNo", serNames, Operator.IN));
		}else{//多选框不选中
			parameters.add (new Parameter ("te.equSerialNo", "equSerialNo", null, Operator.IN));//当前节点下设备条件
		}
			
		list=deviceService.compareDevicesTime(parameters,StringUtils.formatDate(start, 2),StringUtils.formatDate(end, 2));
		if(null != list && list.size()>0){
			createCartesianChartModel(list);//调用图表添加数据方法
		 }else{
			 arraylist[0]=null;
		 }	
		 Map<String,Object> barModel=new HashMap<String,Object>();
		 if(null != arraylist[0]){
			 jsonData=this.toJSON(barModel,arraylist); //调用柱状图后台设置方法
		 }else{
			 jsonData=null;
		 }
	 }
	 
 	/**
     * 柱状图后台设置结合js
     * */
	public String toJSON(Map<String,Object> chartModel,double[][] tudate) {
		JSONObject dataToJSON = new JSONObject();
		String[] rows={"准备","加工","空闲","关机","故障"};
		try {
			dataToJSON.put("title", "多台设备时间比较");
			
			JSONArray rowKeys = new JSONArray(Arrays.asList(rows));
			dataToJSON.put("rowKeys", rowKeys);
			
			JSONArray columnKeys = new JSONArray(allequName);
			dataToJSON.put("columnKeys", columnKeys);
			for (int i = 0; i < rowKeys.length(); i++) {
				double[] datas =tudate[i];
				JSONArray jsonArray =  new JSONArray();
				for (int j = 0; j < datas.length; j++) {
					jsonArray.put(datas[j]);
				}				
				dataToJSON.put("data" + i,jsonArray);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataToJSON.toString();
	}
	 
	/**
	 * 获取下拉框 列表
	 * @return  List<SelectItem>
	 */
	public List<SelectItem> getSerNamesList() {
		 if(null != nodeids){
				getnameList(nodeids);
			}
		return serNamesList;
	}
	/**
	 * 获取当前点击节点
	 * @return String
	 */
	public String getNodeStr() {			
		
//		com.swg.authority.cache.TreeNode currentNode = (com.swg.authority.cache.TreeNode) FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
//			if (currentNode != null) {
//				this.nodeStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
//				nodeids = organizationService.getAllTNodesId(currentNode);
//				if(nodeValue != nodeStr){
//					getnameList(nodeids);
//					queryAction();
//				}
//				nodeValue=nodeStr;
//			}
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String node=session.getAttribute("nodeid")+"";
			this.nodeStr=node;
			List<TNodes> tn=organizationService.getTNodesById(node);
			nodeids = organizationService.getAllTNodesId(tn.get(0));
			if(nodeValue != nodeStr){
				getnameList(nodeids);
				queryAction();
			}
			nodeValue=nodeStr;
			return nodeStr;
		}
	/**
	 * 构造方法
	 */
	@SuppressWarnings("unchecked")
	public CompareDevicesTimeBean(){
			
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String equSerialNo = StringUtils.getCookie(request, "str");
		IDeviceService ds = (IDeviceService) ServiceFactory
				.getBean("deviceService"); // 获取注入;
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
				serNamesList.clear();
				serNames = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					serNamesList.add(new SelectItem(list.get(i).getEquSerialNo(), list.get(i).getEquSerialNo()));
					serNames[i] = list.get(i).getEquSerialNo();
				}
			}
		}
		//默认时间值 :最近一个月
		if(null==start && null==end){
			Date[] datea=StringUtils.convertDatea(1);
			start=datea[0];
			end=datea[1];
		}
			queryAction();	
	}
		
	public void setSerNamesList(List<SelectItem> serNamesList) {
		this.serNamesList = serNamesList;
	}
	
	public String[] getSerNames() {
		return serNames;
	}
	
	public void setSerNames(String[] serNames) {
		this.serNames = serNames;
	}
	
	public String getJsonData() {
		return jsonData;
	}
	
	public List<String> getAllequName() {
		return allequName;
	}
	
	public void setAllequName(List<String> allequName) {
		this.allequName = allequName;
	}
	
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public IOrganizationService getOrganizationService() {
		return organizationService;
	}
	
	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	public Map<String, Object> getMap() {
		return map;
	}
	
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}
	
	public IDeviceService getDeviceService() {
		return deviceService;
	}
	
	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
	public Date getStart() {
		return start;
	}
	
	public void setStart(Date start) {
		this.start = start;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public void setEnd(Date end) {
		this.end = end;
	}
	
	 public String getNodeids() {
			return nodeids;
	}
	 
	public void setNodeids(String nodeids) {
			this.nodeids = nodeids;
	}
	
	public List<String> getSelectedDevices() {
		return selectedDevices;
	}
	
	public void setSelectedDevices(List<String> selectedDevices) {
		this.selectedDevices = selectedDevices;
	}
			
}

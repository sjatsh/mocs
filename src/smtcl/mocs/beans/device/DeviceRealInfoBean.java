package smtcl.mocs.beans.device;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.model.DeviceInfoModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;


/**
 * 用户查看设备总览管理实时数据
 * @创建时间 2012-12-06
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name="deviceRealInfoBean")
@ViewScoped
public class DeviceRealInfoBean {
	/**
	 * 当前节点
	 */
	private String nodeStr;   
	/**
	 * 显示页面的实体模型
	 */
	private DeviceInfoModel deviceInfo; 
	/**
	 * 设备名字下拉框选中项
	 */
    private String nameselected;
    /**
	 * 设备名字下拉框列表
	 */
    private List<SelectItem> nameList;
    /**
	 * 柱状图数据
	 */
    private String pieData;
    /**
	 * 权限接口实例
	 */
    private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
    /**
	 * 设备业务逻辑
	 */
	private IDeviceService deviceService =(IDeviceService)ServiceFactory.getBean("deviceService");
	/**
	 * 程序过程控制变量
	 */
	private int init=0;
	
	/**
	 * 构造方法
	 */
	public DeviceRealInfoBean(){
		String equSerialNo="";
		//机床档案跳转使用 URL传参数 start -----
		String para = FaceContextUtil.getContext().getRequestParameterMap().get("equSerialNo"); 
		if(!StringUtils.isEmpty(para)) 
		{
			equSerialNo=para;
			//存cookie
			HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			StringUtils.saveCookie(request,response, "equSerialNo", para);
		}
		//机床档案跳转使用 URL传参数  end -------  
		else{
			//在线工厂Cookie传参 start -----
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		equSerialNo=StringUtils.getCookie(request,"equSerialNo"); 
		    //在线工厂Cookie传参 end ----- 
		}
		deviceService=(IDeviceService)ServiceFactory.getBean("deviceService"); //获取注入
		
		if(null!=equSerialNo&&!"".equals(equSerialNo)){
			System.err.println("equSerialNo  para:"+para);
			init=1;
			deviceInfo=deviceService.getDeviceInfoModelRealtime(equSerialNo);
			if(deviceInfo!=null&&null!=deviceInfo.getUpdateTime()){
				deviceInfo.setUpdateTime(deviceInfo.getUpdateTime().substring(0, 16));
				if(deviceInfo.getProgramm().contains("/"))
					deviceInfo.setProgramm(deviceInfo.getProgramm().substring(deviceInfo.getProgramm().lastIndexOf("/")+1)); //去掉路径
		    	deviceInfo.setProgramm(StringUtils.getSubString(deviceInfo.getProgramm(), "1"));//如果名称过长，取前10个字符
		    	deviceInfo.setEquSerialNo(StringUtils.getSubString(deviceInfo.getEquSerialNo(),"4"));
				searchAction(equSerialNo);
				
			}
			if(null==nameList||nameList.size()<1){
				nameList=new ArrayList<SelectItem>();
				List<TEquipmentInfo> list=deviceService.getAllEquName("'"+equSerialNo+"'");
				if(null!=list&&list.size()>0){
					TEquipmentInfo tf=(TEquipmentInfo)list.get(0);
					nameList.add(new SelectItem(equSerialNo+"",equSerialNo+""));
					nameselected=equSerialNo;
				}
			}
		}
	}
	
	/**
	 * 下拉框改变时数据加载
	 */
	public void onChange(){
		init=1;
		String equSerialNo=nameselected;
		if(null!=equSerialNo&&!"".equals(equSerialNo)){
			deviceInfo=deviceService.getDeviceInfoModelRealtime(equSerialNo);
			if(deviceInfo!=null){
				if(null!=deviceInfo.getUpdateTime()&&deviceInfo.getUpdateTime().length()>16){
					deviceInfo.setUpdateTime(deviceInfo.getUpdateTime().substring(0, 16));
				}
				if(deviceInfo.getProgramm().contains("/"))
					deviceInfo.setProgramm(deviceInfo.getProgramm().substring(deviceInfo.getProgramm().lastIndexOf("/")+1)); //去掉路径
			    deviceInfo.setProgramm(StringUtils.getSubString(deviceInfo.getProgramm(), "1"));//如果名称过长，取前20个字符
				deviceInfo.setEquSerialNo(StringUtils.getSubString(deviceInfo.getEquSerialNo(),"4"));
				searchAction(equSerialNo);
				nameselected=equSerialNo;
			}
		}
	}
	
	/**
	 * 自动刷新时数据加载
	 */
	public void refreshData(){
		init=1;
		String equSerialNo=deviceInfo.getEquSerialNo();
		if(null!=equSerialNo&&!"".equals(equSerialNo)){
			deviceInfo=deviceService.getDeviceInfoModelRealtime(equSerialNo);
			if(deviceInfo!=null){
				deviceInfo.setUpdateTime(deviceInfo.getUpdateTime().substring(0, 16));
				if(deviceInfo.getProgramm().contains("/"))
					deviceInfo.setProgramm(deviceInfo.getProgramm().substring(deviceInfo.getProgramm().lastIndexOf("/")+1)); //去掉路径
			    deviceInfo.setProgramm(StringUtils.getSubString(deviceInfo.getProgramm(), "1"));//如果名称过长，取前20个字符
				deviceInfo.setEquSerialNo(StringUtils.getSubString(deviceInfo.getEquSerialNo(),"4"));
				searchAction(equSerialNo);
				nameselected=equSerialNo;
			}
		}
	}
	
	/**
	 *获取当前点击节点 
	 * @return String
	 */
	public String getNodeStr() {
		TreeNode currentNode = (TreeNode) FaceContextUtil
				.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			String nodeids=organizationService.getAllTNodesId(currentNode); 
 	    	this.nodeStr=currentNode.getNodeName();
 	    	getnameList(nodeids);
		}
		return nodeStr;
	}
	 
	/**
	 * 获取当前节点下所有设备的名称
	*/
    public void getnameList(String nodeids){
    	if(null!=nodeStr&&!"".equals(nodeStr)){
			nameList=new ArrayList<SelectItem>();
			List<TEquipmentInfo> nlist=deviceService.getNodesAllEquName(nodeids);
			if(null!=nlist&&nlist.size()>0){
				for(TEquipmentInfo teinfo:nlist){
					nameList.add(new SelectItem(teinfo.getEquSerialNo()+"",teinfo.getEquName()+""));
				}
				if(init==0){
					if(null==nameselected){
						nameselected=nlist.get(0).getEquSerialNo();
					}
					deviceInfo=deviceService.getDeviceInfoModelRealtime(nameselected);
					if(deviceInfo!=null&&null!=deviceInfo.getEquSerialNo()){
						deviceInfo.setUpdateTime(deviceInfo.getUpdateTime().substring(0, 16));
						if(deviceInfo.getProgramm().contains("/"))
							deviceInfo.setProgramm(deviceInfo.getProgramm().substring(deviceInfo.getProgramm().lastIndexOf("/")+1)); //去掉路径
					    deviceInfo.setProgramm(StringUtils.getSubString(deviceInfo.getProgramm(), "1"));//如果名称过长，取前20个字符
						deviceInfo.setEquSerialNo(StringUtils.getSubString(deviceInfo.getEquSerialNo(),"4"));
					}
					searchAction(nameselected);
				}
				init=0;
			}
			
		}		
    }

    /**
     * 获取柱状图数据
     * @param equSerialNo
     */
    public void searchAction(String equSerialNo)
	{	
    	Map map=new HashMap();
		Collection<Parameter> parameters = new HashSet<Parameter> ();
		parameters.add (new Parameter ("tuser.equSerialNo", "equSerialNo", equSerialNo, Operator.EQ));
		List<Map<String, Object>> list=deviceService.getRealStatistics(parameters);
		if(null!=list&&list.size()>0){
			 map=list.get(0);
		}
		 
		 List<Long> listtu=this.createDonutModel(map);
		 if(null!=listtu&&listtu.size()>0){
			 pieData=this.toJSON(listtu); 
		 }else{
			 pieData=""; 
		 }
		
	}
    /**
	 * 给图表添加数据
	 * @param map
	 * @return
	 */
	 public  List<Long> createDonutModel(Map map) { 
			List<Long> list=new ArrayList<Long>();
	    	if(null != map && map.size() > 0){
	    		list.add((Long)map.get("runningTime"));//开机时间     
		        list.add((Long)map.get("stopTime"));//关机时间        
		        list.add((Long)map.get("prepareTime"));//准备时间      
		        list.add((Long)map.get("processTime"));//加工时间      
		        list.add((Long)map.get("idleTime"));//空闲时间      
		        list.add((Long)map.get("breakdownTime"));//故障时间      
		        list.add((Long)map.get("dryRunningTime"));//空运行时间      
		        list.add((Long)map.get("cuttingTime"));//切削时间      
		        list.add((Long)map.get("manualRunningTime"));//手动运行时间    
		        list.add((Long)map.get("materialTime"));//上下料时间    
		        list.add((Long)map.get("toolChangeTime"));//换刀时间     
	    	}
	        return list;
	  }  
    /**
     * 柱状图后台设置结合js
     * @exception JSONException
     * */
	public String toJSON(List<Long> tudate) {
		JSONObject dataToJSON = new JSONObject();
		String[] rows={"开机时间 "," 关 机 时 间 ","加工时间  ","准备时间  "," 空 闲 时 间  ","故障时间  ","切削时间  ","空运行时间 ","换刀时间  ","手动时间","上下料时间"};
		try {
			dataToJSON.put("day", "天");
			dataToJSON.put("hour", "小时");
			dataToJSON.put("minute", "分");
			dataToJSON.put("second", "秒");
			JSONArray rowKeys = new JSONArray(Arrays.asList(rows));
			dataToJSON.put("rowKeys", rowKeys);
			for (int i = 0; i < rowKeys.length(); i++) {
				double[] datas ={tudate.get(i)};
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

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

	public DeviceInfoModel getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfoModel deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getNameselected() {
		return nameselected;
	}

	public void setNameselected(String nameselected) {
		this.nameselected = nameselected;
	}

	public List<SelectItem> getNameList() {
		return nameList;
	}

	public void setNameList(List<SelectItem> nameList) {
		this.nameList = nameList;
	}

	public String getPieData() {
		return pieData;
	}

	public void setPieData(String pieData) {
		this.pieData = pieData;
	}
}

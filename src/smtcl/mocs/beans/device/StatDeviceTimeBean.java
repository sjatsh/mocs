package smtcl.mocs.beans.device;

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
import javax.servlet.http.HttpServletResponse;
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
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * 时间统计
 * @创建时间 2013-4-26
 * @作者 gaozhoujun,liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 * */
@ManagedBean(name="StatDeviceTimeBean")
@ViewScoped
public class StatDeviceTimeBean {
	/**
	 * 开始时间
	 */
	private Date start;
	/**
	 * 结束时间
	 */
	private Date end;
	/**
	 * 数据库查询数据
	 */
	private Map<String, Object> map;
	/**
	 * 页面响应数据
	 */
	private Map<String, Object> mapa;
	/**
	 * 当前节点
	 */
	private String nodeStr;    
	/**
	 * 设备序列号
	 */
	private String equSerialNo;  
	/**
	 * 判断加载节点首个设备序列号
	 * 
	 */
	private String equSerialNoPan;
	/**
	 * 判断是否当前节点
	 */
	private String nodeValue;
	/**
	 * 设备业务逻辑
	 */
	private IDeviceService deviceService = (IDeviceService)ServiceFactory.getBean("deviceService");
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
	/**
	 * 设备列表下拉框
	 */
	private List<SelectItem> nameList = new ArrayList<SelectItem>();
	/**
	 * 柱状图返回数据
	 */
	public String jsonData;
	/**
	 * 饼图返回数据
	 */
	private String jsonPie;

	/**
	 *获取当前点击节点 
	 * @return String
	 */
	public String getNodeStr() {
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			String node=session.getAttribute("nodeid")+"";
				//this.nodeStr=FaceContextUtil.getContext().getSessionMap().get("nodeid").toString();
				this.nodeStr=node;
				List<TNodes> tn=organizationService.getTNodesById(node);
				String nodeids = organizationService.getAllTNodesId(tn.get(0));
				getnameList(nodeids);
				nodeValue = nodeStr;
			equSerialNoPan=null;//清空
		 return nodeStr;
	}
	
	/**
	 * 给图表添加数据
	 * @param map
	 * @return List<Long>
	 */
	 public  List<Long> createDonutModel(Map map) { 
			List<Long> list=null;
			 list=new ArrayList<Long>();
	    	if(null != map && map.size() > 0){
	    		list.add((Long)map.get("runningTime"));//开机时间     2471051
		        list.add((Long)map.get("stopTime"));//关机时间        5564149
		        list.add((Long)map.get("prepareTime"));//准备时间      628843
		        list.add((Long)map.get("processTime"));//加工时间      365907
		        list.add((Long)map.get("idleTime"));//空闲时间      391137
		        list.add((Long)map.get("breakdownTime"));//故障时间      995282
		        list.add((Long)map.get("dryRunningTime"));//空运行时间      87542
		        list.add((Long)map.get("cuttingTime"));//切削时间      193265
		        list.add((Long)map.get("manualRunningTime"));//手动运行时间     316777
		        list.add((Long)map.get("materialTime"));//上下料时间     312066
		        list.add((Long)map.get("toolChangeTime"));//换刀时间     85100
	    	}
	    	
	        return list;
	  }  
	/**
	 * 查询按钮响应
	 */
	public void searchAction(){			
		if(null != map && map.size() > 0){
			map.clear();
		}
		if(null != mapa && mapa.size() > 0){
			mapa.clear();
		}
		//当前设备序列号条件
		List<Map<String, Object>> list=deviceService.findIHisroryStatistics(equSerialNo,StringUtils.formatDate(start, 2),StringUtils.formatDate(end, 2));
		if(null!=list&&list.size()>0){
			 map=list.get(0);
			 mapa=new HashMap<String, Object>();
			 if(null != map && map.size() > 0){ //数据库查询数据封装到mapa中
				 	if(null != map.get("runningTime")){
				 		mapa.put("runningTime", StringUtils.SecondIsDDmmss(map.get("runningTime")));//开机时间
				 	}else{
				 		mapa.put("runningTime", StringUtils.SecondIsDDmmss(0));//开机时间
				 	}
				 	if(null != map.get("stopTime")){
				 		mapa.put("stopTime", StringUtils.SecondIsDDmmss(map.get("stopTime")));//关机时间
				 	}else{
				 		mapa.put("stopTime", StringUtils.SecondIsDDmmss(0));//关机时间
				 	}
					if(null != map.get("prepareTime")){
						mapa.put("prepareTime", StringUtils.SecondIsDDmmss(map.get("prepareTime")));//准备时间
					}else{
						mapa.put("prepareTime", StringUtils.SecondIsDDmmss(0));//准备时间
					}
					if(null != map.get("processTime")){
						mapa.put("processTime", StringUtils.SecondIsDDmmss(map.get("processTime")));//加工时间
					}else{
						mapa.put("processTime", StringUtils.SecondIsDDmmss(0));//加工时间
						
					}
					if(null != map.get("idleTime")){
						mapa.put("idleTime", StringUtils.SecondIsDDmmss(map.get("idleTime")));//空闲待机时间
					}else{
						mapa.put("idleTime", StringUtils.SecondIsDDmmss(0));//空闲待机时间
					}
					if(null != map.get("breakdownTime")){
						mapa.put("breakdownTime", StringUtils.SecondIsDDmmss(map.get("breakdownTime")));//故障时间
					}else{
						mapa.put("breakdownTime", StringUtils.SecondIsDDmmss(0));//故障时间
					}
					if(null != map.get("dryRunningTime")){
						mapa.put("dryRunningTime", StringUtils.SecondIsDDmmss(map.get("dryRunningTime")));//空运行时间
					}else{
						mapa.put("dryRunningTime", StringUtils.SecondIsDDmmss(0));//空运行时间
					}
					if(null != map.get("cuttingTime")){
						mapa.put("cuttingTime", StringUtils.SecondIsDDmmss(map.get("cuttingTime")));//切削时间
					}else{
						mapa.put("cuttingTime", StringUtils.SecondIsDDmmss(0));//切削时间
					}
					if(null != map.get("manualRunningTime")){
						mapa.put("manualRunningTime", StringUtils.SecondIsDDmmss(map.get("manualRunningTime")));//手动运行时间
					}else{
						mapa.put("manualRunningTime", StringUtils.SecondIsDDmmss(0));//手动运行时间
					}
					if(null != map.get("materialTime")){
						mapa.put("materialTime", StringUtils.SecondIsDDmmss(map.get("materialTime")));//上下料时间
					}else{
						mapa.put("materialTime", StringUtils.SecondIsDDmmss(0));//上下料时间
					}
					if(null != map.get("otherTime")){
						mapa.put("otherTime", StringUtils.SecondIsDDmmss(map.get("otherTime")));//其它时间
					}else{
						mapa.put("otherTime", StringUtils.SecondIsDDmmss(0));//其它时间
					}
					if(null != map.get("toolChangeTime")){
						mapa.put("toolChangeTime", StringUtils.SecondIsDDmmss(map.get("toolChangeTime")));//换刀时间
					}else{
						mapa.put("toolChangeTime", StringUtils.SecondIsDDmmss(0));//换刀时间
					}
			 }
		}
		List<Long> listtu=null;
		listtu=this.createDonutModel(map);//给图表添加数据
		if(null == listtu || listtu.size() == 0){//查询不出数据，进行初始化
			listtu=new ArrayList<Long>();
			for(int i=0;i<11;i++){
				listtu.add(Long.valueOf(0));
			}
		}
		if(null != list  && list.size() > 0){
			 Map<String,Object> barModel=new HashMap<String,Object>();
			 jsonData=this.toJSON(barModel,listtu); //调用柱状图后台设置方法
		}else{
			jsonData=null;
		}
		 jsonPie=loadPieData(mapa);
	}
    
	/**
	 * 柱状图数据填充
	 * @param chartModel
	 * @param tudate
	 * @return String
	 */
	public String toJSON(Map<String,Object> chartModel,List<Long> tudate) {
		JSONObject dataToJSON = new JSONObject();
		String[] rows={"开机时间","关 机 时 间","加工时间","准备时间 ","空闲时间","故障时间","切削时间","空运行时间","换刀时间","手动时间","上下料时间"};
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
	
	/**
	 * 饼图数据填充
	 * @param deviceInfo
	 * @return String
	 */
	 public String loadPieData(Map deviceInfo){
		 if(null != deviceInfo && deviceInfo.size() > 0){
			 Map testjson = new HashMap();	
		 	 Map zmap=new HashMap();
		 	 Map mValue=new HashMap();
			 mValue.put("name", "关机时间");
			 mValue.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("stopTime").toString()));
			 Map mValue2=new HashMap();
			 mValue2.put("name", "开机时间");
			 mValue2.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("runningTime").toString()));
			 zmap.put("gj", mValue);
			 zmap.put("kj", mValue2);
			 testjson.put("data0", zmap);
				
			 Map zmap2=new HashMap();
			 Map mV2=new HashMap();
			 mV2.put("name", "关机时间");
			 mV2.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("stopTime").toString()));
			
			 Map mV3=new HashMap();
			 mV3.put("name", "故障时间");
			 mV3.put("y",StringUtils.DDMMssIsSecond(deviceInfo.get("breakdownTime").toString()));
			 
			 Map mV4=new HashMap();
			 mV4.put("name", "空闲时间");
			 mV4.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("idleTime").toString()));
			 Map mV5=new HashMap();
			 mV5.put("name", "准备时间");
			 mV5.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("prepareTime").toString()));
			 Map mV6=new HashMap();
			 mV6.put("name", "加工时间");
			 mV6.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("processTime").toString()));	
			 zmap2.put("gj", mV2);
			 zmap2.put("gz",mV3);
			 zmap2.put("kx",mV4);
			 zmap2.put("zb",mV5);
			 zmap2.put("jg",mV6);
			 testjson.put("data1", zmap2);
				
			 Map zmap3=new HashMap();
			 Map mVv2=new HashMap();
			 mVv2.put("name", "关机时间");
			 mVv2.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("stopTime").toString()));
			 Map mVv3=new HashMap();
			 mVv3.put("name", "故障时间");
			 mVv3.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("breakdownTime").toString()));
			 Map mVv4=new HashMap();
			 mVv4.put("name", "空闲时间");
			 mVv4.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("idleTime").toString()));
			 Map mVv5=new HashMap();
			 mVv5.put("name", "切削时间");
			 mVv5.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("cuttingTime").toString()));
		     Map mVv6=new HashMap();
			 mVv6.put("name", "空运行时间");
			 mVv6.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("dryRunningTime").toString()));
		     Map mVv7=new HashMap();
			 mVv7.put("name", "换刀时间");
			 mVv7.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("toolChangeTime").toString()));	
		     Map mVv8=new HashMap();
			 mVv8.put("name", "手动运行时间");
			 mVv8.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("manualRunningTime").toString()));	
		     Map mVv9=new HashMap();
			 mVv9.put("name", "上下料时间");
			 mVv9.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("materialTime").toString()));	
			 zmap3.put("gj",mVv2);
			 zmap3.put("gz",mVv3);
			 zmap3.put("kx",mVv4);
			 zmap3.put("qx",mVv5);
			 zmap3.put("ky",mVv6);
			 zmap3.put("hd",mVv7);
			 zmap3.put("sd",mVv8);
			 zmap3.put("sx",mVv9);
			 testjson.put("data2", zmap3);
			 Gson gson = new GsonBuilder().serializeNulls().create(); 
			 return gson.toJson(testjson);
		}
		 return null;
	 }
	 
	 /**
	  * 当前节点下的设备      下拉框 
	  * @param nodeids
	  * 
	  */
    public void getnameList(String nodeids){
    	System.out.println("getnameList()====equSerialNo====="+equSerialNo);
    	if(null!=nodeStr&&!"".equals(nodeStr)){
			List<TEquipmentInfo> nlist=deviceService.getNodesAllEquName(nodeids);
			for(int i=0;i<nlist.size();i++){
				TEquipmentInfo teinfo=nlist.get(i);
				if(null!=teinfo){
					nameList.add(new SelectItem(teinfo.getEquSerialNo(),(String)teinfo.getEquName()));
				}
				if (nodeValue != nodeStr && null == equSerialNoPan) {// 换节点
						equSerialNo = nlist.get(0).getEquSerialNo();
				}
			}
		}
    	searchAction();
    }
	
	/**
	 * 构造方法
	 */
	@SuppressWarnings("unchecked")
	public StatDeviceTimeBean(){
		System.out.println("StatDeviceTimeBean()====equSerialNo====="+equSerialNo);
		String equNo="";
		//机床档案跳转使用 URL传参数 start -----
		String para = FaceContextUtil.getContext().getRequestParameterMap().get("equSerialNo"); 
		if(!StringUtils.isEmpty(para)) 
		{
			equNo=para;
		    //存cookie
			HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			StringUtils.saveCookie(request,response, "equSerialNo", para);
			System.err.println("-----------------------------------"+StringUtils.getCookie(request,"equSerialNo"));
		}
		//机床档案跳转使用 URL传参数  end -------  
		else{
			//在线工厂Cookie传参 start -----
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			equNo=StringUtils.getCookie(request,"equSerialNo"); 
			//在线工厂Cookie传参 end ----- 
		}
		
		if(null!=equNo&&!"".equals(equNo)){
			List<TEquipmentInfo> tequipment=deviceService.getAllEquName("'"+equNo+"'");
			if(null != tequipment && tequipment.size() > 0){
				nameList=new ArrayList<SelectItem>();
				String equName=tequipment.get(0).getEquName();
				nameList.add(new SelectItem(equNo,equName));
			}
			equSerialNo = equNo;
			equSerialNoPan=equNo;
		}
		//默认时间值 :最近一个月
		if(null==start && null==end){
			Date[] datea=StringUtils.convertDatea(1);
			start=datea[0];
			end=datea[1];
			System.out.println("给时间赋初值  start=="+start+"end=="+end);	
		}
		searchAction();
	}
	
	
	public IOrganizationService getOrganizationService() {
		return organizationService;
	}
	
	public String getJsonData() {
		return jsonData;
	}
	
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	public List<SelectItem> getNameList() {
		return nameList;
	}
	
	public void setNameList(List<SelectItem> nameList) {
		this.nameList = nameList;
	}
	
	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}
	
	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}
			
	public String getEquSerialNo() {
		return equSerialNo;
	}
	
	public Map<String, Object> getMapa() {
		return mapa;
	}
	
	public void setMapa(Map<String, Object> mapa) {
		this.mapa = mapa;
	}
	
	public Map<String, Object> getMap() {
		return map;
	}
	
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	public IDeviceService getDeviceService() {
		return deviceService;
	}
	
	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
	public String getJsonPie() {
		return jsonPie;
	}
	
	public void setJsonPie(String jsonPie) {
		this.jsonPie = jsonPie;
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
    
}

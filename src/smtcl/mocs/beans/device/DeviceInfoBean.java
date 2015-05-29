package smtcl.mocs.beans.device;

import java.io.Serializable;
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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 用户查看设备总览管理历史
 * @创建时间 2012-12-03
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name="deviceInfoBean")
@ViewScoped
public class DeviceInfoBean implements Serializable { 
	/**
	 * 当前节点
	 */
	private String nodeStr="";    
	/**
	 * 显示页面的实体模型
	 */
	private DeviceInfoModel deviceInfo; 
	/**
	 * 下拉框显示项
	 */
    private String nameselected;
    /**
	 * 设备名字下拉框列表
	 */
    private List<SelectItem> nameList;
    /**
	 * 柱状图数据
	 */
    private String columnarChart;
    /**
	 * 饼图数据
	 */
    private String timeChart;
    /**
	 * 线图数据
	 */
    private String lineChart;
    /**
	 * 权限接口实例
	 */
    private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");//获取注入;
    /**
	 * 设备业务逻辑
	 */
    private IDeviceService deviceService =(IDeviceService)ServiceFactory.getBean("deviceService"); //获取注入;
    /**
	 *  程序过程控制变量
	 */
    private int init=0;
	
	/**
	 * 构造方法
	 */
	public DeviceInfoBean() { 
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String equSerialNo=StringUtils.getCookie(request,"equSerialNo");
		
		if(null!=equSerialNo&&!"".equals(equSerialNo)){
			init=1;
			deviceInfo=deviceService.getDeviceInfoModelHistory(equSerialNo);
			if(null!=deviceInfo){
				if(deviceInfo.getProgramm().contains("/"))
					deviceInfo.setProgramm(deviceInfo.getProgramm().substring(deviceInfo.getProgramm().lastIndexOf("/")+1)); //去掉路径
				deviceInfo.setProgramm(StringUtils.getSubString(deviceInfo.getProgramm(), "2"));//如果名称过长，取前20个字符
				lineChart=createChart(equSerialNo) ;
				columnarChart=toJSON(equSerialNo);
				timeChart=createTimeChart(deviceInfo);
			}
			if(null==nameList){
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
	 * @param vce
	 */
	public void onChange(ValueChangeEvent vce){
		init=1;
		String equSerialNo=vce.getNewValue().toString();
		if(null!=equSerialNo&&!"".equals(equSerialNo)){
			deviceInfo=deviceService.getDeviceInfoModelHistory(equSerialNo); 
			if(null!=deviceInfo){
			  if(deviceInfo.getProgramm().contains("/"))
					deviceInfo.setProgramm(deviceInfo.getProgramm().substring(deviceInfo.getProgramm().lastIndexOf("/")+1)); //去掉路径
			deviceInfo.setProgramm(StringUtils.getSubString(deviceInfo.getProgramm(), "2"));//如果名称过长，取前20个字符
			lineChart=createChart(equSerialNo) ;
			columnarChart=toJSON(equSerialNo);
			timeChart=createTimeChart(deviceInfo);
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
			this.nodeStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
			String nodeids=organizationService.getAllTNodesId(currentNode);
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
					nameselected=nlist.get(0).getEquSerialNo();
					deviceInfo=deviceService.getDeviceInfoModelHistory(nlist.get(0).getEquSerialNo());
					if(deviceInfo.getProgramm().contains("/"))
							deviceInfo.setProgramm(deviceInfo.getProgramm().substring(deviceInfo.getProgramm().lastIndexOf("/")+1)); //去掉路径
					deviceInfo.setProgramm(StringUtils.getSubString(deviceInfo.getProgramm(), "2"));//如果名称过长，取前20个字符
					lineChart=createChart(nlist.get(0).getEquSerialNo()) ;
					columnarChart=toJSON(nlist.get(0).getEquSerialNo());
					timeChart=createTimeChart(deviceInfo);
				}
				init=0;
			}
		}		
    }
    
	/**
	 * 柱状图数据加载
	 */
	public String toJSON(String equSerialNo) {
		Collection<Parameter> parameters = new HashSet<Parameter> ();	
		parameters.add (new Parameter ("tuserOne.equSerialNo", "equSerialNo", equSerialNo, Operator.EQ));
		List<Map<String, Object>> list=deviceService.selectServiceOEE(parameters);
		JSONObject dataToJSON = new JSONObject();
		String[] columns={"停机损失","效率损失","质量损失","实际生产"};
		String[] rows={"质量分析","效率分析","可用比率","理论产量"};
		if(null!=list&&list.size()>0){
			Map map=list.get(0);
			 if(null!=map){
				 //deviceService.convertData(map);//调用数据转换方法
				 double[][] citydata={
				 {100-(Double.parseDouble(map.get("availability").toString())*100),
				  100-(Double.parseDouble(map.get("availability").toString())*100),
				  100-(Double.parseDouble(map.get("availability").toString())*100),0
				 },{
				  100-(Double.parseDouble(map.get("display").toString())*(Double.parseDouble(map.get("availability").toString())*100))-(100-(Double.parseDouble(map.get("availability").toString())*100)),
				  100-(Double.parseDouble(map.get("display").toString())*(Double.parseDouble(map.get("availability").toString())*100))-(100-(Double.parseDouble(map.get("availability").toString())*100)),0,0
				 },{
					 100-(Double.parseDouble(map.get("quality").toString())*(Double.parseDouble(map.get("display").toString())*
					 (Double.parseDouble(map.get("availability").toString())*100)))-(100-(Double.parseDouble(map.get("availability").toString())*100))-
					 (100-(Double.parseDouble(map.get("display").toString())*(Double.parseDouble(map.get("availability").toString())*100))-
					 (100-(Double.parseDouble(map.get("availability").toString())*100)))
					 ,0,0,0
				  },{Double.parseDouble(map.get("quality").toString())*(Double.parseDouble(map.get("display").toString())*(Double.parseDouble(map.get("availability").toString())*100)),
					 Double.parseDouble(map.get("display").toString())*(Double.parseDouble(map.get("availability").toString())*100),
					 Double.parseDouble(map.get("availability").toString())*100,100}
				 };
				try {
					JSONArray rowKeys = new JSONArray(Arrays.asList(rows));
					dataToJSON.put("rowkeys", rowKeys);
					JSONArray columnKeys = new JSONArray(Arrays.asList(columns));
					dataToJSON.put("columnkeys", columnKeys);
					dataToJSON.put("hctitle", "城市");
					dataToJSON.put("yTitle", "月份");
					for (int i = 0; i < rowKeys.length(); i++) {
						double[] datas =citydata[i];
						JSONArray jsonArray =  new JSONArray();
						for (int j = 0; j < datas.length; j++) {
							jsonArray.put(datas[j]);
						}				
						dataToJSON.put("data" + i,jsonArray);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
			 }
		}
		return dataToJSON.toString();
	}

	/**
     * 线图数据加载
     */
    private String createChart(String equSerialNo) {
    	Collection<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter("c.equSerialNo", "equSerialNo",equSerialNo, Operator.EQ));
    	IDataCollection<Map<String, Object>> result = deviceService.getDeviceProFrequenceStat(1, 5, parameters);
    	Map rmap=new HashMap();
    	if(result.getData().size()>0){
    		double[] citydata=new double[5];
    		for(int i = 0;i < result.getData().size();i++){
    			Map map = (Map) result.getData().get(i);
    			citydata[i]=Double.parseDouble(map.get("theoryCycletime").toString());
    		}
			rmap.put("citydata", citydata);
    	}
    	Gson gson = new GsonBuilder().serializeNulls().create(); 
    	return gson.toJson(rmap);
    }  

    /**
     * 饼图数据加载
     */
    public String createTimeChart(DeviceInfoModel deviceInfo) {
		Map testjson = new HashMap();	
				Map zmap2=new HashMap();
					if(0==StringUtils.DDMMssIsSecond(deviceInfo.getStopTime())&&
					   0==StringUtils.DDMMssIsSecond(deviceInfo.getBreakDownTime())&&
					   0==StringUtils.DDMMssIsSecond(deviceInfo.getIdleTime())&&
					   0==StringUtils.DDMMssIsSecond(deviceInfo.getPrepareTime())&&
					   0==StringUtils.DDMMssIsSecond(deviceInfo.getProcessTime())){
//						System.out.println("亲，木有数据啊。赶紧的添加数据去。");
					}else{	
						Map mV2=new HashMap();
							mV2.put("name", "关机时间");
							mV2.put("y", StringUtils.DDMMssIsSecond(deviceInfo.getStopTime()));
						
						Map mV3=new HashMap();
							mV3.put("name", "故障时间");
							mV3.put("y",StringUtils.DDMMssIsSecond(deviceInfo.getBreakDownTime()));
						Map mV4=new HashMap();
							mV4.put("name", "空闲时间");
							mV4.put("y", StringUtils.DDMMssIsSecond(deviceInfo.getIdleTime()));
						Map mV5=new HashMap();
							mV5.put("name", "准备时间");
							mV5.put("y", StringUtils.DDMMssIsSecond(deviceInfo.getPrepareTime()));
						Map mV6=new HashMap();
							mV6.put("name", "加工时间");
							mV6.put("y", StringUtils.DDMMssIsSecond(deviceInfo.getProcessTime()));	
						zmap2.put("gj", mV2);
						zmap2.put("gz",mV3);
						zmap2.put("kx",mV4);
						zmap2.put("zb",mV5);
						zmap2.put("jg",mV6);
					}
	     testjson.put("data0", zmap2);
		 Gson gson = new GsonBuilder().serializeNulls().create(); 
		 return gson.toJson(testjson);
	
    }

	public DeviceInfoModel getDeviceInfo() {
		return deviceInfo;
	}

	public IOrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public IDeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
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

	public String getColumnarChart() {
		return columnarChart;
	}

	public void setColumnarChart(String columnarChart) {
		this.columnarChart = columnarChart;
	}

	public String getTimeChart() {
		return timeChart;
	}

	public void setTimeChart(String timeChart) {
		this.timeChart = timeChart;
	}

	public String getLineChart() {
		return lineChart;
	}
	public void setLineChart(String lineChart) {
		this.lineChart = lineChart;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}
    
}  
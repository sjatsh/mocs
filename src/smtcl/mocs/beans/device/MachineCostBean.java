package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.common.device.pages.PageListBaseBean;
import smtcl.mocs.common.device.pages.PageListDataModel;
import smtcl.mocs.services.device.ICostManageService;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * 成本管理的机台成本Bean
 * @作者：yyh
 * @创建时间：2013-6-4 下午13:05:16
 * @修改者：yyh
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="MachineCost")
@ViewScoped
public class MachineCostBean extends PageListBaseBean implements Serializable {

	/**
	 * 设备业务逻辑
	 */
	private	IDeviceService deviceService=(IDeviceService)ServiceFactory.getBean("deviceService");
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 转换后的开始时间
	 */
	private String startTime1;
	/**
	 * 转换后的结束时间
	 */
	private String endTime1;
	/**
	 * 产品号
	 */
	private String no;
	/**
	 * 下拉结果集
	 */
	private Map<String,Object> map = new HashMap<String,Object>();;
	/**
	 * @描述：工序成本结果数据集
	 */	
	private List<Map<String, Object>> resultsFormTime;
	/**
	 * @描述：工序成本结果数据集
	 */	
	private List<Map<String, Object>> resultsFormNo;
	/**
	 * 成本管理接口实例
	 */
	@ManagedProperty(value="#{costManageService}")
	private ICostManageService costManageService;
	
	/**
	 * 分区柱状图数据1
	 */
	public String columJsonData; 
	/**
	 * 分区柱状图数据2
	 */
	public String columTwoJsonData; 
	/**
	 *  分区柱状图数据封装
	 * @return
	 */
	public String toColumJSON() {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lst  = resultsFormTime;
		String[] rows=new String[lst.size()]; //横坐标	
		for(int i=0;i<lst.size();i++){
			Map<String, Object> map = lst.get(i);
			rows[i] = (String)map.get("equSerialNo");
		}
		String[] columns={"切削时间", "辅助时间", "准备时间"};	 //纵坐标		
		try {
			data.put("rowkeys", rows);
			data.put("columnkeys", columns);
			//纵坐标数据
			double[] datas0 = new double[lst.size()];//都是String类型的
			double[] datas1 = new double[lst.size()];
			double[] datas2 = new double[lst.size()];
			for(int i=0;i<lst.size();i++){
				Map<String, Object> map = lst.get(i);
				double energyPprepareCost = Double.parseDouble(map.get("energyPprepareCost").toString());
				double energyCuttingCost = Double.parseDouble(map.get("energyCuttingCost").toString());
				double energyDryRunningCost = Double.parseDouble(map.get("energyDryRunningCost").toString());
				datas0[i] = energyCuttingCost;
				datas1[i] = energyDryRunningCost;
				datas2[i] = energyPprepareCost;
			}
			data.put("data0",datas0);
			data.put("data1",datas1);
			data.put("data2",datas2);			
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}	
	
	/**
	 *  分区柱状图数据封装
	 * @return
	 */
	public String toColumTwoJSON() {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lst  = resultsFormNo;
		String[] rows=new String[lst.size()]; //横坐标	
		for(int i=0;i<lst.size();i++){     
			Map<String, Object> map = lst.get(i);
			rows[i] = (String)map.get("date");
		}
		String[] columns={"切削时间", "辅助时间", "准备时间"};	 //纵坐标		
		try {
			data.put("rowkeys", rows);
			data.put("columnkeys", columns);
			//纵坐标数据
			double[] datas0 = new double[lst.size()];//都是String类型的
			double[] datas1 = new double[lst.size()];
			double[] datas2 = new double[lst.size()];
			for(int i=0;i<lst.size();i++){
				Map<String, Object> map = lst.get(i);
				double energyCuttingCost = Double.parseDouble(map.get("energyCuttingCost").toString());
				double energyDryRunningCost = Double.parseDouble(map.get("energyDryRunningCost").toString());
				double energyPprepareCost = Double.parseDouble(map.get("energyPprepareCost").toString());
				datas0[i] = energyCuttingCost;
				datas1[i] = energyDryRunningCost;
				datas2[i] = energyPprepareCost;
			}
			data.put("data0",datas0);
			data.put("data1",datas1);
			data.put("data2",datas2);		
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}	
	
	/**
	 * 构造函数
	 */
	public MachineCostBean(){//构造函数只执行1次
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.startTime=StringUtils.convertDatea(1)[0];
		this.endTime=StringUtils.convertDatea(1)[1];
		if(startTime!=null && !"".equals(startTime)){
			startTime1 = sdf.format(startTime);
		}
		if(endTime!=null && !"".equals(endTime)){
			endTime1 = sdf.format(endTime);
		}
		costManageService=(ICostManageService)ServiceFactory.getBean("costManageService");
		resultsFormTime = costManageService.queryEquipmentCostListByVar(1, 50,null, startTime1, endTime1);	
		resultsFormNo = costManageService.queryEquipmentCostListByEquno(null,no, startTime1, endTime1);	
		/*
		map.put("TC500R-SYB21-03", "TC500R-SYB21-03");
		map.put("ETC3650-SYB21-02", "ETC3650-SYB21-02");
		map.put("HTC2050i-SYB21-01", "HTC2050i-SYB21-01");
		*/
		 HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
  		 String nodeid=(String)session.getAttribute("nodeid"); //替换上一行
  		 if(!StringUtils.isEmpty(nodeid))
  		 {
	        	 String nodeids=deviceService.getNodeAllId(nodeid);
	        	 List<Map<String, Object>> list=deviceService.getNodesAllEquNameStruts(nodeids);
	        	 for(Map<String, Object> mm : list){
	        		 String equSerialNo = (String)mm.get("equSerialNo"); 
	        		 map.put(equSerialNo, equSerialNo);
	        	 }
  		 }
		
	}
	
	/**
	 * 分页函数
	 */
	@Override
	public PageListDataModel getDefaultDataModel() {
	    return defaultDataModel;
	}

	/**
	 * 条件查询
	 */
	public void searchList(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(startTime!=null && !"".equals(startTime)){
			startTime1 = sdf.format(startTime);
		}
		if(endTime!=null && !"".equals(endTime)){
			endTime1 = sdf.format(endTime);
		}
		costManageService=(ICostManageService)ServiceFactory.getBean("costManageService");
		resultsFormTime = costManageService.queryEquipmentCostListByVar(1, 50,null, startTime1, endTime1);	
		resultsFormNo = costManageService.queryEquipmentCostListByEquno(null,no, startTime1, endTime1);
		no = null; // 选中的机台清空
		startTime1 =null;
		endTime1 = null;
	}
	
	@Override
	public PageListDataModel getExtendDataModel() {
		return null;
	}
	
	public String getNo() {
		return no;
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

	public void setNo(String no) {
		this.no = no;
	}

	public Map<String, Object> getMap() { //构造函数只执行1次，所有放在这里
		/*
		map.put("TC500R-SYB21-03", "TC500R-SYB21-03");
		map.put("ETC3650-SYB21-02", "ETC3650-SYB21-02");
		map.put("HTC2050i-SYB21-01", "HTC2050i-SYB21-01");
		*/
		 HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
		 String nodeid=(String)session.getAttribute("nodeid"); //替换上一行
		 if(!StringUtils.isEmpty(nodeid))
		 {
	        	 String nodeids=deviceService.getNodeAllId(nodeid);
	        	 List<Map<String, Object>> list=deviceService.getNodesAllEquNameStruts(nodeids);
	        	 for(Map<String, Object> mm : list){
	        		 String equSerialNo = (String)mm.get("equSerialNo"); 
	        		 map.put(equSerialNo, equSerialNo);
	        	 }
		 }
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public List<Map<String, Object>> getResultsFormTime() {
		return resultsFormTime;
	}

	public void setResultsFormTime(
			List<Map<String, Object>> resultsFormTime) {
		this.resultsFormTime = resultsFormTime;
	}

	public List<Map<String, Object>> getResultsFormNo() {
		return resultsFormNo;
	}

	public void setResultsFormNo(List<Map<String, Object>> resultsFormNo) {
		this.resultsFormNo = resultsFormNo;
	}

	public ICostManageService getCostManageService() {
		return costManageService;
	}

	public void setCostManageService(ICostManageService costManageService) {
		this.costManageService = costManageService;
	}
	
	public String getColumJsonData() {
		columJsonData = this.toColumJSON();
		return columJsonData;
	}

	public void setColumJsonData(String columJsonData) {
		this.columJsonData = columJsonData;
	}

	public String getColumTwoJsonData() {
		columTwoJsonData = this.toColumTwoJSON();
		return columTwoJsonData;
	}

	public void setColumTwoJsonData(String columTwoJsonData) {
		this.columTwoJsonData = columTwoJsonData;
	}

	public IDeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
	
	
}

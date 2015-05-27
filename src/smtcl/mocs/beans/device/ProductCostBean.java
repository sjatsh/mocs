package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import smtcl.mocs.services.device.ICostManageService;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * 成本管理的单件产品成本Bean
 * @作者：yyh
 * @创建时间：2013-6-4 下午13:05:16
 * @修改者：yyh
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="ProductCost")
@ViewScoped
public class ProductCostBean implements Serializable {

	/**
	 * @描述：工序成本结果数据集
	 */	
	private List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
	/**
	 * @描述：生产消耗成本结果数据集
	 */	
	private List<Map<String, Object>> costResults = new ArrayList<Map<String, Object>>();	
	/**
	 * 生产消耗成本结果列表
	 */
	private List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
	/**
	 * 产品型号 
	 */
	private String partTypeNo ; 
	/**
	 * 产品号值 
	 */
	private String partNo;
	/**
	 * 产品号的集合
	 */
	private Map<String, Object> noMap = new TreeMap<String, Object>();
	/**
	 * 成本管理接口实例
	 */
	private ICostManageService costManageService=(ICostManageService) ServiceFactory
			.getBean("costManageService");
	/**
	 * 图形的数据封装对象
	 */
	private Map<String, Object> dataMap;

	/**
	 * 饼图数据
	 */
	public String pieJsonData;
	
	/**
	 * 分区柱状图数据
	 */
	public String columJsonData; 
	
	/**
	 * 柱状和线图数据
	 */
	public String columnAndLineJsonData;
	
	/**
	 * 构造函数
	 */
	public ProductCostBean() {		
		//获取节点ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid");
		
		List<Map<String, Object>> lst = costManageService.getPartTypeList(nodeid);
		if(lst!=null&&lst.size()>0) partTypeNo=lst.get(0).get("parttypeNo")+"";   //进去默认初始
		results = costManageService.queryProcessCostList(1,100, partTypeNo);
		for (Map<String, Object> map : lst) {
			String noName  = (String)map.get("parttypeNo");
			if(noName!=null && !"".equals(noName)){
			noMap.put(noName, noName);
			}
		}
		
	}
   
	/**
	 * 查询列表
	 */
	public void searchList(){
		System.err.println(".....................");
		if(null!=results&&results.size()>0) results.clear();  //没有从列表查询到值的话，清空全部数据
		results = costManageService.queryProcessCostList(1,100, partTypeNo);
	}
	
	/**
	 *  选中数据时ajax传过来的值
	 */
	public void searNoVal(String noVal){
		partNo=noVal;
	}
	
	/**
	 *  分区柱状图数据封装
	 * @return
	 */
	public String toColumJSON() {	

		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lst =results;
		int size=0;
		if(results.size()>10) size=10;
		else size=results.size();
		String[] rows=new String[size]; //横坐标	
		for(int i=0;i<size;i++){
			Map<String, Object> map = lst.get(i);
			String partNo = (String)map.get("partNo");
			partNo = partNo.substring(partNo.length()-3, partNo.length());
			rows[i] = partNo;
		}
		String[] columns={"人工", "辅料", "能源", "折旧", "主料"};	 //纵坐标		
		
		try {
			
			data.put("rowkeys", rows);
			data.put("columnkeys", columns);
			//纵坐标数据
			double[] datas0 = new double[size];//都是String类型的
			double[] datas1 = new double[size];
			double[] datas2 = new double[size];
			double[] datas3 = new double[size];
			double[] datas4 = new double[size];
			for(int i=0;i<size;i++){
				Map<String, Object> map = lst.get(i);
				double peopleCost = Double.parseDouble(map.get("peopleCost").toString());
				double subsidiaryMaterialCost = Double.parseDouble(map.get("accMaterialCost").toString());
				double energyCost = Double.parseDouble(map.get("energyCost").toString());
				double resourceCost = Double.parseDouble(map.get("oldCost").toString());
				double mainMaterialCost = Double.parseDouble(map.get("mainMaterialCost").toString());	
				datas0[i] = peopleCost;
				datas1[i] = subsidiaryMaterialCost;
				datas2[i] = energyCost;
				datas3[i] = resourceCost;
				datas4[i] = mainMaterialCost;
			}
			data.put("data0",datas0);
			data.put("data1",datas1);
			data.put("data2",datas2);
			data.put("data3",datas3);
			data.put("data4",datas4);			

			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}

		return null;
	}
	
	
	
	/**
	 * 饼图数据封装
	 */
	public String toPieJson(){	
		if(results.size()>0){
		if(StringUtils.isEmpty(partNo)) dataMap = results.get(0);
		else 
		{
			if(results!=null)
				for(Map<String,Object> record:results)
				{
					String partNoTemp=record.get("partNo").toString();
					if(partNoTemp.equals(partNo)) 
						{ 
						    dataMap=record;
						    double costTotal = Double.parseDouble(dataMap.get("peopleCost").toString()) +
									   Double.parseDouble(dataMap.get("accMaterialCost").toString()) +
									   Double.parseDouble(dataMap.get("energyCost").toString()) +
									   Double.parseDouble(dataMap.get("oldCost").toString()) +
									   Double.parseDouble(dataMap.get("mainMaterialCost").toString());
							dataMap.put("costTotal", costTotal); //饼图的统计数值
						    break;
						}
				}
			}
		}
		JSONObject dataToJSON = new JSONObject();
		String[] names={"人工", "辅料", "能源", "折旧", "主料"};
		String peopleCost = "0";
		if(dataMap!=null&&dataMap.get("peopleCost")!=null){
			peopleCost = dataMap.get("peopleCost").toString();
		}
		String accMaterialCost = "0";
		if(dataMap!=null&&dataMap.get("accMaterialCost")!=null){
			accMaterialCost = dataMap.get("accMaterialCost").toString();
		}
		String energyCost = "0";
		if(dataMap!=null&&dataMap.get("energyCost")!=null){
			energyCost = dataMap.get("energyCost").toString();
		}
		String oldCost = "0";
		if(dataMap!=null&&dataMap.get("oldCost")!=null){
			oldCost = dataMap.get("oldCost").toString();
		}
		String mainMaterialCost = "0";
		if(dataMap!=null&&dataMap.get("peopleCost")!=null){
			mainMaterialCost = dataMap.get("mainMaterialCost").toString();
		}
		String[] values = { peopleCost,
				accMaterialCost,
				energyCost,
				oldCost,
				mainMaterialCost };
	    double costTotal = Double.parseDouble(peopleCost) +
			   Double.parseDouble(accMaterialCost) +
			   Double.parseDouble(energyCost) +
			   Double.parseDouble(oldCost) +
			   Double.parseDouble(mainMaterialCost);
	   java.text.DecimalFormat df=new   java.text.DecimalFormat("#.##"); 
	   if(dataMap!=null){
		   dataMap.put("costTotal", df.format(costTotal)); //饼图的统计数值
	   }
		try {			
			JSONArray rowKeys = new JSONArray(Arrays.asList(names));
			dataToJSON.put("rowKeys", rowKeys);
			JSONArray columnKeys = new JSONArray(Arrays.asList(values));
			dataToJSON.put("columnKeys", columnKeys);			
		} catch (JSONException e) {
			e.printStackTrace();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dataToJSON.toString();
	}

	/**
	 * 柱状和线图数据封装
	 */
	@SuppressWarnings("unchecked")
	public String tocolumnAndLineJson() {
		Map<String, Object> data = new HashMap<String, Object>();
		if(dataMap!=null){
		costResults = (List<Map<String, Object>>)dataMap.get("energycostDetailList");	
		
		lst  = costResults;
		java.text.DecimalFormat df=new   java.text.DecimalFormat("#.##");   
		for(Map<String, Object> map : lst){
			double cuttingCost = Double.parseDouble(map.get("energyCost_cutting").toString());
			double processAccessoriesCost = Double.parseDouble(map.get("energyCost_dryRunning").toString());
			double prepareAccessoriesCost = Double.parseDouble(map.get("energyCost_prepare").toString());
			double total = cuttingCost + processAccessoriesCost + prepareAccessoriesCost;
			map.put("total", df.format(total));
		}
		
		String[] rows=new String[lst.size()]; //横坐标	
		for(int i=0;i<lst.size();i++){
			Map<String, Object> map = lst.get(i);
			rows[i] = (String)map.get("equSerialNo");
		}
		String[] columns={"切削", "准备", "辅助"};	 //纵坐标		
		try {
			data.put("rowkeys", rows);
			data.put("columnkeys", columns);
			//纵坐标数据
			double[] datas0 = new double[lst.size()];//都是String类型的
			double[] datas1 = new double[lst.size()];
			double[] datas2 = new double[lst.size()];
			for(int i=0;i<lst.size();i++){
				Map<String, Object> map = lst.get(i);
				double peopleCost =  Double.parseDouble(map.get("energyCost_cutting").toString());		//切削		
				double energyCost =  Double.parseDouble(map.get("energyCost_prepare").toString());      //辅助
				double subsidiaryMaterialCost =  Double.parseDouble(map.get("energyCost_dryRunning").toString()); //准备
				datas0[i] = peopleCost;
				datas1[i] = energyCost;
				datas2[i] = subsidiaryMaterialCost;
			}			
			data.put("data0",datas0);
			data.put("data1",datas1);
			data.put("data2",datas2);			
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return null;
		
	}

	public List<Map<String, Object>> getResults() {
		return results;
	}

	public void setResults(List<Map<String, Object>> results) {
		this.results = results;
	}

	public String getPartTypeNo() {
		return partTypeNo;
	}

	public void setPartTypeNo(String partTypeNo) {
		this.partTypeNo = partTypeNo;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public ICostManageService getCostManageService() {
		return costManageService;
	}

	public void setCostManageService(ICostManageService costManageService) {
		this.costManageService = costManageService;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public List<Map<String, Object>> getCostResults() {
		return costResults;
	}

	public void setCostResults(List<Map<String, Object>> costResults) {
		this.costResults = costResults;
	}

	public Map<String, Object> getNoMap() {
		return noMap;
	}

	public void setNoMap(Map<String, Object> noMap) {
		this.noMap = noMap;
	}
	
	public String getColumJsonData() {
		columJsonData = this.toColumJSON();
		if(results.size()==0){  //如果结果集为空，子图标也为空
			columJsonData =null;
		}
		return columJsonData;
	}

	public void setColumJsonData(String columJsonData) {
		this.columJsonData = columJsonData;
	}
	
	public String getPieJsonData() {
		pieJsonData=this.toPieJson();
		if(results.size()==0){  //如果结果集为空，子图标也为空
			pieJsonData =null;
			if(dataMap!=null){
			dataMap.put("costTotal", 0);
			}
		}
		return pieJsonData;
	}

	public void setPieJsonData(String pieJsonData) {
		this.pieJsonData = pieJsonData;
	}
	
	public String getColumnAndLineJsonData() {
		columnAndLineJsonData=this.tocolumnAndLineJson();	
		if(results.size()==0){  //如果结果集为空，子图标也为空
			columnAndLineJsonData =null;
			costResults = null; //子数据表格也为空
		}
		return columnAndLineJsonData;
	}

	public void setColumnAndLineJsonData(String columnAndLineJsonData) {
		this.columnAndLineJsonData = columnAndLineJsonData;
	}
}

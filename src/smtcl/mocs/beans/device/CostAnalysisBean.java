package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.common.device.pages.PageListBaseBean;
import smtcl.mocs.common.device.pages.PageListDataModel;
import smtcl.mocs.services.device.ICostManageService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * 成本管理的成本分析Bean
 * @作者：yyh
 * @创建时间：2013-6-4 下午13:05:16
 * @修改者：yyh
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="CostAnalysis")
@ViewScoped
public class CostAnalysisBean extends PageListBaseBean implements Serializable {

	/**
	 * 产品类型名称
	 */
	private String partTypeNo;
	/**
	 * 产品名称
	 */
	private String partNo;
	/**
	 * 产品类型集合下拉列表框数据
	 */
    private Map<String,Object> typeMap = new TreeMap<String,Object>();
	/**
	 * 产品集合下拉列表框数据
	 */
    private Map<String,Object> machineMap = new TreeMap<String,Object>();
	/**
	 * @描述：物料消耗结果数据集通过类型得到
	 */	
	private List<Map<String, Object>> resultsFormType;
	/**
	 * @描述：物料消耗结果数据集通过时间取到
	 */	
	private Map<String, Object> resultsFormMachine;
	/**
	 * 成本管理接口实例
	 */
	private ICostManageService costManageService=(ICostManageService)ServiceFactory.getBean("costManageService");;
	/**
	 * 双线图数据
	 */
	public String twoLineJsonData;
	/**
	 * 极地图数据
	 */
	public String polarJsonData;
	/**
	 * 双柱状图数据
	 */
	public String twoColumnJsonData;	
	/**
	 * 构造函数
	 */
	public CostAnalysisBean(){	
		//获取节点ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid");
		
		//产品类型号集
		List<Map<String, Object>> lst1 = costManageService.getPartTypeList(nodeid);
		for (Map<String, Object> map : lst1) {
			String noName  = (String)map.get("parttypeNo");
			if(noName!=null && !"".equals(noName)){
				typeMap.put(noName, noName);
			}
		}
		//产品号集
		List<Map<String, Object>> lst = costManageService.getPartNoList("");
		for (Map<String, Object> map1 : lst) {
			String noName  = (String)map1.get("partNo");
			if(noName!=null && !"".equals(noName)){
				machineMap.put(noName, noName);
			}
		}
		
		if(lst1!=null&&lst1.size()>0) partTypeNo=lst1.get(0).get("parttypeNo")+"";   //进去默认初始		
		resultsFormType = costManageService.queryProductRealCostAnalysis(partTypeNo, partNo);
		if(resultsFormType.size()>0){
		resultsFormMachine = resultsFormType.get(0);
		}
		
	}
	
	/**
	 * 二级联动
	 *  @param 
     *  @return 
     *  @throws and @exception
	 */
	public void sub(){
		machineMap.clear(); //清空原有
		List<Map<String, Object>> lst = costManageService.getPartNoList(partTypeNo);
		for (Map<String, Object> map1 : lst) {
			String noName  = (String)map1.get("partNo");
			if(noName!=null && !"".equals(noName)){
				machineMap.put(noName, noName);
			}
		}
	}
	
	/**
	 * 条件查询
	 */
	public void searchList(){
		
	}

	/**
	 * 双线图数据数据封装
	 * @return
	 */
	public String toTwoLineJSON() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> temp=costManageService.getProductTheoryCostAnalysis(partTypeNo);	
		String[] columns=new String[resultsFormType.size()];       //时间
		double[] columnValue = new double[resultsFormType.size()]; //折线
		double[] constaValue = new double[resultsFormType.size()]; //固定的值理论成本
		for(int i=0;i<resultsFormType.size();i++){
			Map<String, Object> map = resultsFormType.get(i);
			columns[i] = map.get("partNo").toString();
			columnValue[i] = Double.parseDouble(map.get("realCost").toString());
			constaValue[i] =Double.parseDouble(temp.get("theoryCost").toString());
		}	
		try {
			data.put("columns", columns);         //时间
			data.put("columnValue", columnValue); //折线	
			data.put("constaValue", constaValue); //理论成本
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 *  极地图数据数据封装
	 * @return
	 */
	public String toPolarJSON() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> theoryTemp=costManageService.getProductTheoryCostAnalysis(partTypeNo); //
		String[] columns={"人工","折旧","能源","主料","辅料"}; 
		double[] columnValue = new double[columns.length];  //实际成本
		double[] constaValue = new double[columns.length];  //理论成本
		if(resultsFormMachine!=null&&resultsFormMachine.size()>0)
		{
			Map<String, Object> map = resultsFormMachine;
			columnValue[0] = Double.parseDouble(map.get("peopleCost").toString());
			columnValue[1] = Double.parseDouble(map.get("oldCost").toString());
			columnValue[2] = Double.parseDouble(map.get("energyCost").toString());
			columnValue[3] = Double.parseDouble(map.get("mainMaterialCost").toString());
			columnValue[4] = Double.parseDouble(map.get("accMaterialCost").toString());			
	    if(theoryTemp.get("theoryPeopleCost")!=null){
	    	constaValue[0] = Double.parseDouble(theoryTemp.get("theoryPeopleCost").toString());
	    }
	    if(theoryTemp.get("theoryDeviceCost")!=null){
	    	constaValue[1] = Double.parseDouble(theoryTemp.get("theoryDeviceCost").toString());
	    }
		if(theoryTemp.get("theoryEnergyCost")!=null){
			constaValue[2] = Double.parseDouble(theoryTemp.get("theoryEnergyCost").toString());
		}
		if(theoryTemp.get("theoryMainMaterialCost")!=null){
			constaValue[3] = Double.parseDouble(theoryTemp.get("theoryMainMaterialCost").toString());
		}
		if(theoryTemp.get("theorSubsidiaryMaterialCost")!=null){
			constaValue[4] = Double.parseDouble(theoryTemp.get("theorSubsidiaryMaterialCost").toString());
		}
		}
		try {
			data.put("columns", columns);         //横坐标名称
			data.put("columnValue", columnValue); //实际成本	
			data.put("constaValue", constaValue); //理论成本		
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 *  双柱状图数据数据封装
	 * @return
	 */
	public String toTwoColumnJSON() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> theoryTemp=costManageService.getProductTheoryCostAnalysis(partTypeNo); //
		String[] columns={"人工","折旧","能源","主料","辅料"};      //横坐标名称
		double[] columnValue = new double[columns.length];  //实际成本
		double[] constaValue = new double[columns.length];  //理论成本
		if(resultsFormMachine!=null&&resultsFormMachine.size()>0)
		{
			Map<String, Object> map = resultsFormMachine;
			columnValue[0] = Double.parseDouble(map.get("peopleCost").toString());
			columnValue[1] = Double.parseDouble(map.get("oldCost").toString());
			columnValue[2] = Double.parseDouble(map.get("energyCost").toString());
			columnValue[3] = Double.parseDouble(map.get("mainMaterialCost").toString());
			columnValue[4] = Double.parseDouble(map.get("accMaterialCost").toString());			
		if(theoryTemp.get("theoryPeopleCost")!=null){
			constaValue[0] = Double.parseDouble(theoryTemp.get("theoryPeopleCost").toString());
		}
		if(theoryTemp.get("theoryDeviceCost")!=null){
			constaValue[1] = Double.parseDouble(theoryTemp.get("theoryDeviceCost").toString());
		}
		if(theoryTemp.get("theoryEnergyCost")!=null){
			constaValue[2] = Double.parseDouble(theoryTemp.get("theoryEnergyCost").toString());
		}
		if(theoryTemp.get("theoryMainMaterialCost")!=null){
			constaValue[3] = Double.parseDouble(theoryTemp.get("theoryMainMaterialCost").toString());
		}
		if(theoryTemp.get("theorSubsidiaryMaterialCost")!=null){
			constaValue[4] = Double.parseDouble(theoryTemp.get("theorSubsidiaryMaterialCost").toString());
		}
		}
		try {
			data.put("columns", columns);         //横坐标名称
			data.put("columnValue", columnValue); //实际成本	
			data.put("constaValue", constaValue); //理论成本
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public Map<String, Object> getTypeMap() { //构造只执行1次
		costManageService=(ICostManageService)ServiceFactory.getBean("costManageService");
		resultsFormType = costManageService.queryProductRealCostAnalysis(partTypeNo, null);	
		if(resultsFormType.size()==0){
			resultsFormMachine = null;
		}else{
			if(partNo == null){
				resultsFormMachine=resultsFormType.get(0);
			}
			else{
			for(Map<String,Object> rec:resultsFormType)
			{
			    if(rec.get("partNo").equals(partNo)) 
			    	{ 
			    	  resultsFormMachine=rec;
			    	  break;
			    	}
				}
			}
		}
		partNo = null;
		return typeMap;
	}
	
	@Override
	public PageListDataModel getDefaultDataModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageListDataModel getExtendDataModel() {
		// TODO Auto-generated method stub
		return null;
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

	public void setTypeMap(Map<String, Object> typeMap) {
		this.typeMap = typeMap;
	}

	public Map<String, Object> getMachineMap() {
		return machineMap;
	}

	public void setMachineMap(Map<String, Object> machineMap) {
		this.machineMap = machineMap;
	}

	public List<Map<String, Object>> getResultsFormType() {
		return resultsFormType;
	}

	public void setResultsFormType(
			List<Map<String, Object>> resultsFormType) {
		this.resultsFormType = resultsFormType;
	}

	public Map<String, Object> getResultsFormMachine() {
		return resultsFormMachine;
	}

	public void setResultsFormMachine(
			Map<String, Object> resultsFormMachine) {
		this.resultsFormMachine = resultsFormMachine;
	}

	public ICostManageService getCostManageService() {
		return costManageService;
	}

	public void setCostManageService(ICostManageService costManageService) {
		this.costManageService = costManageService;
	}
	
	public String getTwoLineJsonData() {
		twoLineJsonData = this.toTwoLineJSON();
		return twoLineJsonData;
	}

	public void setTwoLineJsonData(String twoLineJsonData) {
		this.twoLineJsonData = twoLineJsonData;
	}
	
	public String getPolarJsonData() {
		polarJsonData = this.toPolarJSON();
		return polarJsonData;
	}

	public void setPolarJsonData(String polarJsonData) {
		this.polarJsonData = polarJsonData;
	}
	
	public String getTwoColumnJsonData() {
		twoColumnJsonData = this.toTwoColumnJSON();
		return twoColumnJsonData;
	}

	public void setTwoColumnJsonData(String twoColumnJsonData) {
		this.twoColumnJsonData = twoColumnJsonData;
	}
}

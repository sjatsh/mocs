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
 * �ɱ�����ĳɱ�����Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-6-4 ����13:05:16
 * @�޸��ߣ�yyh
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="CostAnalysis")
@ViewScoped
public class CostAnalysisBean extends PageListBaseBean implements Serializable {

	/**
	 * ��Ʒ��������
	 */
	private String partTypeNo;
	/**
	 * ��Ʒ����
	 */
	private String partNo;
	/**
	 * ��Ʒ���ͼ��������б������
	 */
    private Map<String,Object> typeMap = new TreeMap<String,Object>();
	/**
	 * ��Ʒ���������б������
	 */
    private Map<String,Object> machineMap = new TreeMap<String,Object>();
	/**
	 * @�������������Ľ�����ݼ�ͨ�����͵õ�
	 */	
	private List<Map<String, Object>> resultsFormType;
	/**
	 * @�������������Ľ�����ݼ�ͨ��ʱ��ȡ��
	 */	
	private Map<String, Object> resultsFormMachine;
	/**
	 * �ɱ�����ӿ�ʵ��
	 */
	private ICostManageService costManageService=(ICostManageService)ServiceFactory.getBean("costManageService");;
	/**
	 * ˫��ͼ����
	 */
	public String twoLineJsonData;
	/**
	 * ����ͼ����
	 */
	public String polarJsonData;
	/**
	 * ˫��״ͼ����
	 */
	public String twoColumnJsonData;	
	/**
	 * ���캯��
	 */
	public CostAnalysisBean(){	
		//��ȡ�ڵ�ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid");
		
		//��Ʒ���ͺż�
		List<Map<String, Object>> lst1 = costManageService.getPartTypeList(nodeid);
		for (Map<String, Object> map : lst1) {
			String noName  = (String)map.get("parttypeNo");
			if(noName!=null && !"".equals(noName)){
				typeMap.put(noName, noName);
			}
		}
		//��Ʒ�ż�
		List<Map<String, Object>> lst = costManageService.getPartNoList("");
		for (Map<String, Object> map1 : lst) {
			String noName  = (String)map1.get("partNo");
			if(noName!=null && !"".equals(noName)){
				machineMap.put(noName, noName);
			}
		}
		
		if(lst1!=null&&lst1.size()>0) partTypeNo=lst1.get(0).get("parttypeNo")+"";   //��ȥĬ�ϳ�ʼ		
		resultsFormType = costManageService.queryProductRealCostAnalysis(partTypeNo, partNo);
		if(resultsFormType.size()>0){
		resultsFormMachine = resultsFormType.get(0);
		}
		
	}
	
	/**
	 * ��������
	 *  @param 
     *  @return 
     *  @throws and @exception
	 */
	public void sub(){
		machineMap.clear(); //���ԭ��
		List<Map<String, Object>> lst = costManageService.getPartNoList(partTypeNo);
		for (Map<String, Object> map1 : lst) {
			String noName  = (String)map1.get("partNo");
			if(noName!=null && !"".equals(noName)){
				machineMap.put(noName, noName);
			}
		}
	}
	
	/**
	 * ������ѯ
	 */
	public void searchList(){
		
	}

	/**
	 * ˫��ͼ�������ݷ�װ
	 * @return
	 */
	public String toTwoLineJSON() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> temp=costManageService.getProductTheoryCostAnalysis(partTypeNo);	
		String[] columns=new String[resultsFormType.size()];       //ʱ��
		double[] columnValue = new double[resultsFormType.size()]; //����
		double[] constaValue = new double[resultsFormType.size()]; //�̶���ֵ���۳ɱ�
		for(int i=0;i<resultsFormType.size();i++){
			Map<String, Object> map = resultsFormType.get(i);
			columns[i] = map.get("partNo").toString();
			columnValue[i] = Double.parseDouble(map.get("realCost").toString());
			constaValue[i] =Double.parseDouble(temp.get("theoryCost").toString());
		}	
		try {
			data.put("columns", columns);         //ʱ��
			data.put("columnValue", columnValue); //����	
			data.put("constaValue", constaValue); //���۳ɱ�
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 *  ����ͼ�������ݷ�װ
	 * @return
	 */
	public String toPolarJSON() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> theoryTemp=costManageService.getProductTheoryCostAnalysis(partTypeNo); //
		String[] columns={"�˹�","�۾�","��Դ","����","����"}; 
		double[] columnValue = new double[columns.length];  //ʵ�ʳɱ�
		double[] constaValue = new double[columns.length];  //���۳ɱ�
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
			data.put("columns", columns);         //����������
			data.put("columnValue", columnValue); //ʵ�ʳɱ�	
			data.put("constaValue", constaValue); //���۳ɱ�		
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 *  ˫��״ͼ�������ݷ�װ
	 * @return
	 */
	public String toTwoColumnJSON() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> theoryTemp=costManageService.getProductTheoryCostAnalysis(partTypeNo); //
		String[] columns={"�˹�","�۾�","��Դ","����","����"};      //����������
		double[] columnValue = new double[columns.length];  //ʵ�ʳɱ�
		double[] constaValue = new double[columns.length];  //���۳ɱ�
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
			data.put("columns", columns);         //����������
			data.put("columnValue", columnValue); //ʵ�ʳɱ�	
			data.put("constaValue", constaValue); //���۳ɱ�
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public Map<String, Object> getTypeMap() { //����ִֻ��1��
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

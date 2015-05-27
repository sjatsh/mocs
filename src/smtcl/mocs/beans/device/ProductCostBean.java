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
 * �ɱ�����ĵ�����Ʒ�ɱ�Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-6-4 ����13:05:16
 * @�޸��ߣ�yyh
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="ProductCost")
@ViewScoped
public class ProductCostBean implements Serializable {

	/**
	 * @����������ɱ�������ݼ�
	 */	
	private List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
	/**
	 * @�������������ĳɱ�������ݼ�
	 */	
	private List<Map<String, Object>> costResults = new ArrayList<Map<String, Object>>();	
	/**
	 * �������ĳɱ�����б�
	 */
	private List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
	/**
	 * ��Ʒ�ͺ� 
	 */
	private String partTypeNo ; 
	/**
	 * ��Ʒ��ֵ 
	 */
	private String partNo;
	/**
	 * ��Ʒ�ŵļ���
	 */
	private Map<String, Object> noMap = new TreeMap<String, Object>();
	/**
	 * �ɱ�����ӿ�ʵ��
	 */
	private ICostManageService costManageService=(ICostManageService) ServiceFactory
			.getBean("costManageService");
	/**
	 * ͼ�ε����ݷ�װ����
	 */
	private Map<String, Object> dataMap;

	/**
	 * ��ͼ����
	 */
	public String pieJsonData;
	
	/**
	 * ������״ͼ����
	 */
	public String columJsonData; 
	
	/**
	 * ��״����ͼ����
	 */
	public String columnAndLineJsonData;
	
	/**
	 * ���캯��
	 */
	public ProductCostBean() {		
		//��ȡ�ڵ�ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid");
		
		List<Map<String, Object>> lst = costManageService.getPartTypeList(nodeid);
		if(lst!=null&&lst.size()>0) partTypeNo=lst.get(0).get("parttypeNo")+"";   //��ȥĬ�ϳ�ʼ
		results = costManageService.queryProcessCostList(1,100, partTypeNo);
		for (Map<String, Object> map : lst) {
			String noName  = (String)map.get("parttypeNo");
			if(noName!=null && !"".equals(noName)){
			noMap.put(noName, noName);
			}
		}
		
	}
   
	/**
	 * ��ѯ�б�
	 */
	public void searchList(){
		System.err.println(".....................");
		if(null!=results&&results.size()>0) results.clear();  //û�д��б��ѯ��ֵ�Ļ������ȫ������
		results = costManageService.queryProcessCostList(1,100, partTypeNo);
	}
	
	/**
	 *  ѡ������ʱajax��������ֵ
	 */
	public void searNoVal(String noVal){
		partNo=noVal;
	}
	
	/**
	 *  ������״ͼ���ݷ�װ
	 * @return
	 */
	public String toColumJSON() {	

		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lst =results;
		int size=0;
		if(results.size()>10) size=10;
		else size=results.size();
		String[] rows=new String[size]; //������	
		for(int i=0;i<size;i++){
			Map<String, Object> map = lst.get(i);
			String partNo = (String)map.get("partNo");
			partNo = partNo.substring(partNo.length()-3, partNo.length());
			rows[i] = partNo;
		}
		String[] columns={"�˹�", "����", "��Դ", "�۾�", "����"};	 //������		
		
		try {
			
			data.put("rowkeys", rows);
			data.put("columnkeys", columns);
			//����������
			double[] datas0 = new double[size];//����String���͵�
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
	 * ��ͼ���ݷ�װ
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
							dataMap.put("costTotal", costTotal); //��ͼ��ͳ����ֵ
						    break;
						}
				}
			}
		}
		JSONObject dataToJSON = new JSONObject();
		String[] names={"�˹�", "����", "��Դ", "�۾�", "����"};
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
		   dataMap.put("costTotal", df.format(costTotal)); //��ͼ��ͳ����ֵ
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
	 * ��״����ͼ���ݷ�װ
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
		
		String[] rows=new String[lst.size()]; //������	
		for(int i=0;i<lst.size();i++){
			Map<String, Object> map = lst.get(i);
			rows[i] = (String)map.get("equSerialNo");
		}
		String[] columns={"����", "׼��", "����"};	 //������		
		try {
			data.put("rowkeys", rows);
			data.put("columnkeys", columns);
			//����������
			double[] datas0 = new double[lst.size()];//����String���͵�
			double[] datas1 = new double[lst.size()];
			double[] datas2 = new double[lst.size()];
			for(int i=0;i<lst.size();i++){
				Map<String, Object> map = lst.get(i);
				double peopleCost =  Double.parseDouble(map.get("energyCost_cutting").toString());		//����		
				double energyCost =  Double.parseDouble(map.get("energyCost_prepare").toString());      //����
				double subsidiaryMaterialCost =  Double.parseDouble(map.get("energyCost_dryRunning").toString()); //׼��
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
		if(results.size()==0){  //��������Ϊ�գ���ͼ��ҲΪ��
			columJsonData =null;
		}
		return columJsonData;
	}

	public void setColumJsonData(String columJsonData) {
		this.columJsonData = columJsonData;
	}
	
	public String getPieJsonData() {
		pieJsonData=this.toPieJson();
		if(results.size()==0){  //��������Ϊ�գ���ͼ��ҲΪ��
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
		if(results.size()==0){  //��������Ϊ�գ���ͼ��ҲΪ��
			columnAndLineJsonData =null;
			costResults = null; //�����ݱ��ҲΪ��
		}
		return columnAndLineJsonData;
	}

	public void setColumnAndLineJsonData(String columnAndLineJsonData) {
		this.columnAndLineJsonData = columnAndLineJsonData;
	}
}

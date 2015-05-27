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
 * �ɱ�����Ļ�̨�ɱ�Bean
 * @���ߣ�yyh
 * @����ʱ�䣺2013-6-4 ����13:05:16
 * @�޸��ߣ�yyh
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name="MachineCost")
@ViewScoped
public class MachineCostBean extends PageListBaseBean implements Serializable {

	/**
	 * �豸ҵ���߼�
	 */
	private	IDeviceService deviceService=(IDeviceService)ServiceFactory.getBean("deviceService");
	/**
	 * ��ʼʱ��
	 */
	private Date startTime;
	/**
	 * ����ʱ��
	 */
	private Date endTime;
	/**
	 * ת����Ŀ�ʼʱ��
	 */
	private String startTime1;
	/**
	 * ת����Ľ���ʱ��
	 */
	private String endTime1;
	/**
	 * ��Ʒ��
	 */
	private String no;
	/**
	 * ���������
	 */
	private Map<String,Object> map = new HashMap<String,Object>();;
	/**
	 * @����������ɱ�������ݼ�
	 */	
	private List<Map<String, Object>> resultsFormTime;
	/**
	 * @����������ɱ�������ݼ�
	 */	
	private List<Map<String, Object>> resultsFormNo;
	/**
	 * �ɱ�����ӿ�ʵ��
	 */
	@ManagedProperty(value="#{costManageService}")
	private ICostManageService costManageService;
	
	/**
	 * ������״ͼ����1
	 */
	public String columJsonData; 
	/**
	 * ������״ͼ����2
	 */
	public String columTwoJsonData; 
	/**
	 *  ������״ͼ���ݷ�װ
	 * @return
	 */
	public String toColumJSON() {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lst  = resultsFormTime;
		String[] rows=new String[lst.size()]; //������	
		for(int i=0;i<lst.size();i++){
			Map<String, Object> map = lst.get(i);
			rows[i] = (String)map.get("equSerialNo");
		}
		String[] columns={"����ʱ��", "����ʱ��", "׼��ʱ��"};	 //������		
		try {
			data.put("rowkeys", rows);
			data.put("columnkeys", columns);
			//����������
			double[] datas0 = new double[lst.size()];//����String���͵�
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
	 *  ������״ͼ���ݷ�װ
	 * @return
	 */
	public String toColumTwoJSON() {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lst  = resultsFormNo;
		String[] rows=new String[lst.size()]; //������	
		for(int i=0;i<lst.size();i++){     
			Map<String, Object> map = lst.get(i);
			rows[i] = (String)map.get("date");
		}
		String[] columns={"����ʱ��", "����ʱ��", "׼��ʱ��"};	 //������		
		try {
			data.put("rowkeys", rows);
			data.put("columnkeys", columns);
			//����������
			double[] datas0 = new double[lst.size()];//����String���͵�
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
	 * ���캯��
	 */
	public MachineCostBean(){//���캯��ִֻ��1��
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
  		 String nodeid=(String)session.getAttribute("nodeid"); //�滻��һ��
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
	 * ��ҳ����
	 */
	@Override
	public PageListDataModel getDefaultDataModel() {
	    return defaultDataModel;
	}

	/**
	 * ������ѯ
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
		no = null; // ѡ�еĻ�̨���
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

	public Map<String, Object> getMap() { //���캯��ִֻ��1�Σ����з�������
		/*
		map.put("TC500R-SYB21-03", "TC500R-SYB21-03");
		map.put("ETC3650-SYB21-02", "ETC3650-SYB21-02");
		map.put("HTC2050i-SYB21-01", "HTC2050i-SYB21-01");
		*/
		 HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
		 String nodeid=(String)session.getAttribute("nodeid"); //�滻��һ��
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

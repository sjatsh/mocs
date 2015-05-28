package smtcl.mocs.beans.storage;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.storage.IStorageManageService;
/**
 * ���ϳ����bean
 * ����ʱ�� 2014-12-17
 * ���� FW
 * �޸���
 * �޸�ʱ��
 */
@ManagedBean(name="MaterialTransfer")
@ViewScoped
public class MaterialTransferBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String success;
	/**
	 * ���ϼ���List
	 */
	List<Map<String,Object>> materialList = new ArrayList<Map<String,Object>>();
	/**
	 * �ⷿ��Ϣ
	 */
	List<Map<String,Object>> storageInfoList = new ArrayList<Map<String,Object>>();
	/**
	 * ��λ��Ϣ
	 */
	List<Map<String,Object>> positionInfoList = new ArrayList<Map<String,Object>>();
	
	/**
	 * ���Ͽⷿ��Ϣ
	 */
	List<Map<String,Object>> materialStorageInfoList = new ArrayList<Map<String,Object>>();
	/**
	 * ���ϻ�λ��Ϣ
	 */
	List<Map<String,Object>> materialPositionInfoList = new ArrayList<Map<String,Object>>();
	
	/**
	 * ���������Ϣ
	 */
	List<Map<String,Object>> materialInStorageInfoList = new ArrayList<Map<String,Object>>();
	
	/**
	 * �������μ���
	 */
	List<Map<String,Object>> productBatchList = new ArrayList<Map<String,Object>>();
	/**
	 * �������м���
	 */
	List<Map<String,Object>> productSeqList = new ArrayList<Map<String,Object>>();
	/**
	 * ��λ����
	 */
	List<Map<String,Object>> unitList = new ArrayList<Map<String,Object>>();
	/**
	 * ���ϰ汾����
	 */
	List<Map<String,Object>> versionList = new ArrayList<Map<String,Object>>();
	/**
	 * �ڵ�
	 */
	String nodeid;
	/**
	 * ��Դ
	 */
	String source;
	/**
	 * ����ID
	 */
	String materialId;
	/**
	 * ���ϱ���
	 */
	String materialNo;
	/**
	 * ��������
	 */
	String materialName;
	
	/**
	 * �ⷿID
	 */
	String storageId;
	/**
	 * �ⷿNO
	 */
	String storageNo;
	/**
	 * ��λID
	 */
	String positionId;
	/**
	 * ��λNO
	 */
	String positionNo;
	/**
	 * ת������
	 */
	String transferNum;
	/**
	 * ���ο���
	 */
	Boolean batchCtrl;
	/**
	 * ���п���
	 */
	Boolean seqCtrl;
	/**
     * ��λ����
     */
	Boolean positionCtrl;
	/**
     * ��λ����2
     */
	Boolean tempPositionCtrl;
	/**
	 * ���ο���
	 */
	Boolean batchCtrl2;
	/**
	 * ���п���
	 */
	Boolean seqCtrl2;
	/**
     * ��λ����
     */
	Boolean positionCtrl2;
	/**
     * ��λ����2
     */
	Boolean tempPositionCtrl2;
	/**
	 * ����ID
	 */
	String batchId;
	/**
	 * ����No
	 */
	String batchNo;
	/**
	 * ����ID
	 */
	String seqId;
	/**
	 * ����No
	 */
	String seqNo;
	
	/**
     * ����No
     */
	int transNo;
	/**
	 * ����������
	 */
	Date transDate;
	/**
	 * ��������Ա
	 */
	String transUser;
	/**
	 * ��������Id
	 */
	String transTypeId;
	/**
	 * �������ͻ
	 */
	String transActivity;
	/**
	 * ������
	 */
	Double availableNum;
	/**
	 * Ŀ��ⷿID
	 */
	String transferStorageId;
	/**
	 * Ŀ��ⷿNo
	 */
	String transferStorageNo;
	/**
	 * Ŀ���λID
	 */
	String transferPositionId;
	/**
	 * Ŀ���λNo
	 */
	String transferPositionNo;
	/**
	 * ��λId
	 */
	String unitId;
	/**
	 * ��λ����
	 */
	String unitName;
	/**
	 * ��λ������
	 */
	Double unitRate;
	/**
	 * ID
	 */
	Integer id;
	/**
	 * �������ID
	 */
	String storageMaterialId;
	/**
	 * ��׼��λ����
	 */
	String baseUnitName;
	/**
	 * ��һ�ε�λ������
	 */
	Double oldUnitRate;
	/**
	 * �汾����
	 */
	Boolean versionCtrl;
	/**
	 * �汾����2
	 */
	Boolean tempVersionCtrl;
	/**
	 * �汾Id
	 */
	String versionId;
	/**
	 * �汾No
	 */
	String versionNo;
	/**
	 * �������ƿ��
	 */
	Boolean limitStorage;
	/**
	 * �������ƻ�λ
	 */
	Boolean limitPosition;
	/**
	 * �ⷿ��λ����
	 */
	String positionType;
	/**
	 * ��֯���ϻ�λ����
	 */
	String positionType2;
	/**
	 * ��֯���ϻ�λ����
	 */
	String positionType3;
	/**
	 * �ı����Ƿ�༭
	 */
	Boolean isEdit =false;
	/**
	 * �༭��λ��
	 */
	String editPositionNo;
	
	private IStorageManageService iStorageManageService=(IStorageManageService)ServiceFactory.getBean("storageManage");
	
	public MaterialTransferBean() {
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (null !=request.getParameter("transTypeId")
			&& !"".equals(request.getParameter("transTypeId"))
			&& !request.getParameter("transTypeId").equals("undefined")){
			transTypeId = request.getParameter("transTypeId").toString();
		}
		if (null !=request.getParameter("transActivity")
				&& !"".equals(request.getParameter("transActivity"))
				&& !request.getParameter("transActivity").equals("undefined")){
			transActivity = request.getParameter("transActivity").toString();
		}
		if (null !=request.getParameter("userId")
				&& !"".equals(request.getParameter("userId"))
				&& !request.getParameter("userId").equals("undefined")){
			transUser = request.getParameter("userId").toString();
		}
		if (null !=request.getParameter("date")
				&& !"".equals(request.getParameter("date"))
				&& !request.getParameter("date").equals("undefined")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			try {
				transDate = sdf.parse(request.getParameter("date").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}  
		}
		//��ȡ�ڵ�ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    nodeid = (String)session.getAttribute("nodeid");
	    //����No
	    String maxID = iStorageManageService.getMaxID();
	    transNo = Integer.parseInt(maxID)+1;
	    
	    materialInStorageInfoList.clear();
	    //���ϼ���
	    materialList = iStorageManageService.getMaterialInfoList2(nodeid);
	    
	    id=1;
	    
	    batchCtrl =false;
	    seqCtrl =false;
	    positionCtrl = false;
	    tempPositionCtrl =false;
	    
	    batchCtrl2 =true;
	    seqCtrl2 =true;
	    positionCtrl2 = false;
	    tempPositionCtrl2=false;
	}
	
	/**
	 * ��λ����
	 */
	public void getPositionInfo(){
		transferPositionId ="";
		editPositionNo ="";
		for(Map<String,Object> tt:storageInfoList){
			if(transferStorageId.equals(tt.get("id").toString())){
				transferStorageNo = tt.get("no").toString();//���No
				positionType = tt.get("positionType").toString();//��λ����
				if(positionType.equals("2")){
					isEdit = true;
					positionCtrl2 = true;
					tempPositionCtrl2 =false;
				}else if(positionType.equals("3")){
					isEdit =false;
					positionCtrl2 = true;
					tempPositionCtrl2 =false;
				}else if(positionType.equals("4")){
					if(positionType2.equals("2")){
						isEdit =true;
						positionCtrl2 = true;
						tempPositionCtrl2 =false;
					}else if(positionType2.equals("3")){
						isEdit =false;
						positionCtrl2 = true;
						tempPositionCtrl2 =false;
					}else{
						positionCtrl2=false;
						tempPositionCtrl2 =true;
						isEdit =true;
					}
					positionType = positionType2;
				}else{
					positionCtrl2=false;
					tempPositionCtrl2 =true;
					isEdit =true;
				}
			} 
			
			if(limitPosition)
			{
				positionInfoList = iStorageManageService.limitPositionList(materialId,transferStorageId);
			}else{
				positionInfoList = iStorageManageService.getPositionInfoList(transferStorageId);
			}
		}
	}
	
	/**
	 * ��λ������ı�
	 */
	public void positionChange(){
		for(Map<String,Object> tt:positionInfoList){
			if(tt.get("id").toString().equals(positionId)){
				positionNo =tt.get("no").toString();
				editPositionNo = tt.get("no").toString();
				break;
			}
		}
	}
	
	/**
	 * ������Ϣ
	 */
	public void getMaterialInfo() {
	    availableNum =0.0;
	    oldUnitRate =1.0;
	    unitRate =1.0;
	    storageMaterialId ="";
	    batchId ="";
	    batchNo ="";
	    positionId ="";
	    seqId ="";
	    seqNo ="";
	    
	    editPositionNo ="";
	    positionNo ="";
	    storageId ="";
	    versionNo ="";
	    
	    storageInfoList = iStorageManageService.storgeList(nodeid);//�ⷿ����
	    
		 for(Map<String,Object> tt:materialList){
			 if(materialId.equals(tt.get("id").toString())){
				 materialNo = tt.get("no").toString();
				 materialName = tt.get("name").toString();
				 
				 List<Map<String,Object>> list = iStorageManageService.materialCtrList(materialId);//��ȡ���Ͽ�����Ϣ
				 if(list.size()>0){
					 if(list.get(0).get("isBatchCtrl").toString().equals("0")){
						 batchCtrl =true;
						 batchCtrl2=false;
					 }else{
						 batchCtrl =false;
						 batchCtrl2 =true;
					 }
					 
					 if(list.get(0).get("isSeqCtrl").toString().equals("0")){
						 seqCtrl =true;
						 seqCtrl2 = false;
					 }else{
						 seqCtrl =false;
						 seqCtrl2 =true;
					 }
					 
					 positionType = list.get(0).get("isPositionCtrl").toString();//��λ����
					 
					 if(list.get(0).get("isVersionCtrl").toString().equals("0")){
						versionCtrl =true;
						tempVersionCtrl = false;
					 }else{
						versionCtrl =false;
						tempVersionCtrl = true;
					 }
					 
					 //���ƿ��
					 if(null!=list.get(0).get("isStockCtrl")){
						if(list.get(0).get("isStockCtrl").toString().equals("0")){
							limitStorage =true;
						}else{
							limitStorage =false;
						}
					 }else{
						limitStorage =false;
					 }
						
					 //���ƻ�λ
					 if(null!=list.get(0).get("isAxisCtrl")){
						 if(list.get(0).get("isAxisCtrl").toString().equals("0")){
							 limitPosition =true;
						 }else{
							 limitPosition =false;
						 }
					 }else{
						 limitPosition =false;
					 }
					 
					 if(null!= list.get(0).get("unitName")){
						 unitName = list.get(0).get("unitName").toString();
						 baseUnitName = list.get(0).get("unitName").toString();
					 }
					 //���Ͽⷿ����
					 List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
					 list1 = iStorageManageService.getMaterialStorageList(materialId,nodeid);
					 
					 for(Map<String,Object> map:list1){
						 if(null != map.get("availableNum") && map.get("availableNum").toString() !=""){
							 availableNum+=Double.parseDouble(map.get("availableNum").toString());
						 }
						 Boolean flag =false;
						 for(Map<String,Object> map2:materialStorageInfoList)
						 {
							 if(map.get("storageId").toString().equals(map2.get("storageId").toString())){
								 flag =true;
								 break;
							 }
						 }
						 if(!flag){
							 materialStorageInfoList.add(map);
						 }                                                                                          
					 }
					 
					 if(list1.size() ==1){
						 storageMaterialId = list1.get(0).get("id").toString();
						 for(Map<String,Object> map :materialInStorageInfoList){
							 if(storageMaterialId.equals(map.get("storageMaterialId").toString())){
								 Double tem = Double.parseDouble(map.get("realNum").toString());
								 availableNum = availableNum -tem;
							 }
						 }
					 }
					 
					 //��λ���㼯��
					 unitList =iStorageManageService.getUnitRateList(unitName, nodeid);
					//������ϰ汾����
					versionList = iStorageManageService.storageMaterialVersionList(materialId, storageId, positionId, batchId, seqId);
					//��漯��
					if(limitStorage){
						storageInfoList = iStorageManageService.limitStorgeList(materialId);//���ƿⷿ����
					}
				 }
				 
			 }
		 }
		
	}
	
	/**
	 * �������Ͽⷿ��ȡ��λ����
	 */
	public void getMaterialPositionInfo(){
		if(null ==storageId || storageId ==""){
			return;
			
		}
		versionNo ="";
		positionId ="";
		batchId ="";
		seqId ="";
		
		for(Map<String,Object> tt:materialStorageInfoList){
			if(storageId.equals(tt.get("storageId").toString())){
				storageNo = tt.get("no").toString();
				positionType3 = tt.get("positionType").toString();//��λ����
				if(positionType3.equals("2")){
					positionCtrl = true;
					tempPositionCtrl =false;
				}else if(positionType3.equals("3")){
					
					positionCtrl = true;
					tempPositionCtrl =false;
				}else if(positionType3.equals("4")){
					if(positionType2.equals("2")){
						
						positionCtrl = true;
						tempPositionCtrl =false;
					}else if(positionType2.equals("3")){
						
						positionCtrl = true;
						tempPositionCtrl =false;
					}else{
						positionCtrl=false;
						tempPositionCtrl =true;
						
					}
					positionType3 = positionType2;
				}else{
					positionCtrl=false;
					tempPositionCtrl =true;
					
				}
			}
		}
		materialPositionInfoList.clear();
		productBatchList.clear();
		productSeqList.clear();
		versionList.clear();
		if(positionCtrl){
			List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
			list1 = iStorageManageService.getMaterialStoragePositionList(materialId,storageId);
			
			availableNum =0.0;
			for(Map<String,Object> map:list1){
				 if(null != map.get("availableNum") && map.get("availableNum").toString() !="")
				 {
					 availableNum+=Double.parseDouble(map.get("availableNum").toString());
				 }
				 Boolean flag =false;
				 for(Map<String,Object> map2:materialPositionInfoList)
				 {
					 if(map.get("positionId").toString().equals(map2.get("positionId").toString())){
						 flag =true;
						 break;
					 }
				 }
				 if(!flag){
					 materialPositionInfoList.add(map);
				 }
			}
			
			if(list1.size() ==1){
				 storageMaterialId = list1.get(0).get("id").toString();
				 for(Map<String,Object> map :materialInStorageInfoList){
					 if(storageMaterialId.equals(map.get("storageMaterialId").toString())){
						 Double tem = Double.parseDouble(map.get("realNum").toString());
						 availableNum = availableNum -tem;
					 }
				 }
			 }
		}else{
			if(batchCtrl){
				productBatchList = iStorageManageService.getMaterialStorageBatchList(materialId, storageId, "");
			}
			if(seqCtrl){
				productSeqList = iStorageManageService.getMaterialStorageSeqList(materialId, storageId, "", "","");
			}
		}
		
		//�汾����
		versionList = iStorageManageService.storageMaterialVersionList(materialId, storageId, positionId, batchId, seqId);
	}
	
	/**
	 * ���ݿⷿ��ȡ���μ���
	 */
	public void getBatchInfo(){
		if(null ==positionId || positionId ==""){
			return;
			
		}
		
		batchId ="";
		seqId ="";
		versionNo ="";
		
		for(Map<String,Object> tt:materialPositionInfoList){
			if(positionId.equals(tt.get("positionId").toString())){
				positionNo = tt.get("no").toString();
			}
		}
		
		if(batchCtrl)
		{   
			productBatchList.clear();
			List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
			list1 = iStorageManageService.getMaterialStorageBatchList(materialId,storageId,positionId);
			
			availableNum =0.0;
			
			for(Map<String,Object> map:list1){
				 if(null != map.get("availableNum") && map.get("availableNum").toString() !=""){
					 availableNum+=Double.parseDouble(map.get("availableNum").toString());
				 }
				 
				 Boolean flag =false;
				 for(Map<String,Object> map2:productBatchList)
				 {
					 if(map.get("batchId").toString().equals(map2.get("batchId").toString())){
						 flag =true;
						 break;
					 }
				 }
				 if(!flag){
					 productBatchList.add(map);
				 }
			}
			
			if(list1.size() ==1){
				 storageMaterialId = list1.get(0).get("id").toString();
				 for(Map<String,Object> map :materialInStorageInfoList){
					 if(storageMaterialId.equals(map.get("storageMaterialId").toString())){
						 Double tem = Double.parseDouble(map.get("realNum").toString());
						 availableNum = availableNum -tem;
					 }
				 }
			 }
		}
		if(seqCtrl){
			productSeqList = iStorageManageService.getMaterialStorageSeqList(materialId, storageId, positionId, "","");
		}
		
		//�汾����
		versionList = iStorageManageService.storageMaterialVersionList(materialId, storageId, positionId, batchId, seqId);
	}
	
	/**
	 * ���ݿⷿ��ȡ���м���
	 */
	public void getSeqInfo(){
		if(null ==batchId || batchId ==""){
			return;
			
		}
		seqId ="";
		versionNo ="";
		
		for(Map<String,Object> tt:productBatchList){
			if(batchId.equals(tt.get("batchId").toString())){
				batchNo = tt.get("no").toString();
			}
		}
		if(seqCtrl)
		{   
			productSeqList.clear();
			List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
			list1 = iStorageManageService.getMaterialStorageSeqList(materialId, storageId, positionId, batchId,"");
			
			availableNum =0.0;
			for(Map<String,Object> map:list1){
				 if(null != map.get("availableNum") && map.get("availableNum").toString() !=""){
					 availableNum+=Double.parseDouble(map.get("availableNum").toString());
				 }
				 
				 Boolean flag =false;
				 for(Map<String,Object> map2:productSeqList)
				 {
					 if(map.get("no").toString().equals(map2.get("no").toString())){
						 flag =true;
						 break;
					 }
				 }
				 if(!flag){
					 productSeqList.add(map);
				 }
			}
			
			if(list1.size() ==1){
				 storageMaterialId = list1.get(0).get("id").toString();
				 for(Map<String,Object> map :materialInStorageInfoList){
					 if(storageMaterialId.equals(map.get("storageMaterialId").toString())){
						 Double tem = Double.parseDouble(map.get("realNum").toString());
						 availableNum = availableNum -tem;
					 }
				 }
			 }
		}
		//�汾����
		versionList = iStorageManageService.storageMaterialVersionList(materialId, storageId, positionId, batchId, seqId);
	}
	
	public void seqChange(){
		if(null ==seqId || seqId ==""){
			return;
			
		}
		for(Map<String,Object> map:productSeqList){
			if(seqId.equals(map.get("seqId").toString())){
				seqNo = map.get("no").toString();
			}
			
			List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
			list1 = iStorageManageService.getMaterialStorageSeqList(materialId, storageId, positionId, batchId,seqId);
			
			availableNum =0.0;
			for(Map<String,Object> tt:list1){
				 if(null != tt.get("availableNum") && tt.get("availableNum").toString() !=""){
					 availableNum+=Double.parseDouble(tt.get("availableNum").toString());
				 }
			}
			
			if(list1.size() ==1){
				 storageMaterialId = list1.get(0).get("id").toString();
				 for(Map<String,Object> map2 :materialInStorageInfoList){
					 if(storageMaterialId.equals(map2.get("storageMaterialId").toString())){
						 Double tem = Double.parseDouble(map2.get("realNum").toString());
						 availableNum = availableNum -tem;
					 }
				 }
			 }
		}
		
		//�汾����
	    versionList = iStorageManageService.storageMaterialVersionList(materialId, storageId, positionId, batchId, seqId);
		
	}
	
	/**
	 * ��λѡ��
	 */
	public void unitChange(){
		for(Map<String,Object> map:unitList){
			if(unitName.equals(map.get("name").toString())){
				unitRate =Double.parseDouble(map.get("rate").toString());
				transferNum =""; 
				availableNum = availableNum*oldUnitRate/unitRate;
				oldUnitRate = unitRate;
			}
		}
	}
	
	/**
	 * �汾ѡ��
	 */
	public void versionChange(){
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		list = iStorageManageService.getMaterialStorageVersionList(materialId, storageId, positionId, batchId, seqId, versionNo);
		if(list.size()==1){
			storageMaterialId = list.get(0).get("id").toString();
		}
		
	}
	/**
	 * ����һ����¼
	 * @return
	 */
	public void addInfo(){
		if(storageMaterialId ==""){
			return;
		}
		
		//��֯����
		Map<String,Object> tempMap = new HashMap<String, Object>();
		tempMap.put("id", id);
		tempMap.put("transNo", transNo);
		tempMap.put("storageMaterialId", storageMaterialId);
		tempMap.put("materialId", materialId);
		tempMap.put("storageNo", storageNo);
		
		tempMap.put("positionCtrl", positionCtrl);
		tempMap.put("positionNo", positionNo);
		
		tempMap.put("materialNo", materialNo);
		
		tempMap.put("num", transferNum);
		tempMap.put("unitName", unitName);
		tempMap.put("unitRate", unitRate);
		tempMap.put("baseUnitName", baseUnitName);
		tempMap.put("realNum", Double.parseDouble(transferNum)*unitRate);
		
		tempMap.put("isBatchCtr", batchCtrl);
		tempMap.put("batchId", batchId);
		tempMap.put("batchNo", batchNo);
		
		tempMap.put("isSeqCtr", seqCtrl);
		tempMap.put("seqId", seqId);
		tempMap.put("seqNo", seqNo);
		
		tempMap.put("transferStorageId", transferStorageId);
		tempMap.put("transferStorageNo", transferStorageNo);
		
		tempMap.put("positionCtrl2", positionCtrl2);
		tempMap.put("transferPositionId", transferPositionId);
		tempMap.put("transferPositionNo", editPositionNo);
		
		tempMap.put("versionId", versionId);
		tempMap.put("versionNo", versionNo);
		
		materialInStorageInfoList.add(tempMap);
		availableNum = availableNum -Double.parseDouble(transferNum);
		id++;
	}
	
	/**
	 * ɾ���б���Ϣ
	 * @param index
	 */
	public void delInfo(String index){
		Map<String,Object> temp = new HashMap<String, Object>();
		for(Map<String,Object> tt:materialInStorageInfoList){
			if(index.equals(tt.get("id").toString())){
				temp =tt;
				break;
			}
		}
		materialInStorageInfoList.remove(temp);
		
	}
	
	/**
	 * ���ת��
	 * @return
	 */
	public void saveDataInfo() {
		success ="";
		if(materialInStorageInfoList.size()>0){
			success = iStorageManageService.
					saveTransferMaterial(materialInStorageInfoList, source,
							transUser,transTypeId,transDate);
		}
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	
	
	public List<Map<String, Object>> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Map<String, Object>> materialList) {
		this.materialList = materialList;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
    
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	public List<Map<String, Object>> getStorageInfoList() {
		return storageInfoList;
	}

	public void setStorageInfoList(List<Map<String, Object>> storageInfoList) {
		this.storageInfoList = storageInfoList;
	}

	public List<Map<String, Object>> getPositionInfoList() {
		return positionInfoList;
	}

	public void setPositionInfoList(List<Map<String, Object>> positionInfoList) {
		this.positionInfoList = positionInfoList;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	
	
	public String getTransferNum() {
		return transferNum;
	}
	public void setTransferNum(String transferNum) {
		this.transferNum = transferNum;
	}
	public String getStorageNo() {
		return storageNo;
	}

	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}
	
	public Boolean getBatchCtrl() {
		return batchCtrl;
	}

	public void setBatchCtrl(Boolean batchCtrl) {
		this.batchCtrl = batchCtrl;
	}

	public Boolean getSeqCtrl() {
		return seqCtrl;
	}

	public void setSeqCtrl(Boolean seqCtrl) {
		this.seqCtrl = seqCtrl;
	}

	public List<Map<String, Object>> getMaterialInStorageInfoList() {
		return materialInStorageInfoList;
	}

	public void setMaterialInStorageInfoList(
			List<Map<String, Object>> materialInStorageInfoList) {
		this.materialInStorageInfoList = materialInStorageInfoList;
	}

	public List<Map<String, Object>> getProductBatchList() {
		return productBatchList;
	}

	public void setProductBatchList(List<Map<String, Object>> productBatchList) {
		this.productBatchList = productBatchList;
	}

	public List<Map<String, Object>> getProductSeqList() {
		return productSeqList;
	}

	public void setProductSeqList(List<Map<String, Object>> productSeqList) {
		this.productSeqList = productSeqList;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public Boolean getPositionCtrl() {
		return positionCtrl;
	}

	public void setPositionCtrl(Boolean positionCtrl) {
		this.positionCtrl = positionCtrl;
	}

	public int getTransNo() {
		return transNo;
	}

	public void setTransNo(int transNo) {
		this.transNo = transNo;
	}
	public Double getAvailableNum() {
		return availableNum;
	}
	public void setAvailableNum(Double availableNum) {
		this.availableNum = availableNum;
	}
	public List<Map<String, Object>> getMaterialStorageInfoList() {
		return materialStorageInfoList;
	}
	public void setMaterialStorageInfoList(
			List<Map<String, Object>> materialStorageInfoList) {
		this.materialStorageInfoList = materialStorageInfoList;
	}
	public List<Map<String, Object>> getMaterialPositionInfoList() {
		return materialPositionInfoList;
	}
	public void setMaterialPositionInfoList(
			List<Map<String, Object>> materialPositionInfoList) {
		this.materialPositionInfoList = materialPositionInfoList;
	}

	public String getTransferStorageId() {
		return transferStorageId;
	}

	public void setTransferStorageId(String transferStorageId) {
		this.transferStorageId = transferStorageId;
	}

	public String getTransferPositionId() {
		return transferPositionId;
	}

	public void setTransferPositionId(String transferPositionId) {
		this.transferPositionId = transferPositionId;
	}

	public List<Map<String, Object>> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<Map<String, Object>> unitList) {
		this.unitList = unitList;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getUnitRate() {
		return unitRate;
	}

	public void setUnitRate(Double unitRate) {
		this.unitRate = unitRate;
	}

	public Boolean getBatchCtrl2() {
		return batchCtrl2;
	}

	public void setBatchCtrl2(Boolean batchCtrl2) {
		this.batchCtrl2 = batchCtrl2;
	}

	public Boolean getSeqCtrl2() {
		return seqCtrl2;
	}

	public void setSeqCtrl2(Boolean seqCtrl2) {
		this.seqCtrl2 = seqCtrl2;
	}

	public Boolean getPositionCtrl2() {
		return positionCtrl2;
	}

	public void setPositionCtrl2(Boolean positionCtrl2) {
		this.positionCtrl2 = positionCtrl2;
	}

	public List<Map<String, Object>> getVersionList() {
		return versionList;
	}

	public void setVersionList(List<Map<String, Object>> versionList) {
		this.versionList = versionList;
	}

	public Boolean getVersionCtrl() {
		return versionCtrl;
	}

	public void setVersionCtrl(Boolean versionCtrl) {
		this.versionCtrl = versionCtrl;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public Boolean getTempVersionCtrl() {
		return tempVersionCtrl;
	}

	public void setTempVersionCtrl(Boolean tempVersionCtrl) {
		this.tempVersionCtrl = tempVersionCtrl;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public Boolean getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	public String getEditPositionNo() {
		return editPositionNo;
	}

	public void setEditPositionNo(String editPositionNo) {
		this.editPositionNo = editPositionNo;
	}

	public Boolean getTempPositionCtrl() {
		return tempPositionCtrl;
	}

	public void setTempPositionCtrl(Boolean tempPositionCtrl) {
		this.tempPositionCtrl = tempPositionCtrl;
	}

	public Boolean getTempPositionCtrl2() {
		return tempPositionCtrl2;
	}

	public void setTempPositionCtrl2(Boolean tempPositionCtrl2) {
		this.tempPositionCtrl2 = tempPositionCtrl2;
	}

	public String getPositionType3() {
		return positionType3;
	}

	public void setPositionType3(String positionType3) {
		this.positionType3 = positionType3;
	}
	
	
	
}

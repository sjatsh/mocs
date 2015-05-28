package smtcl.mocs.beans.storage;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.storage.IStorageManageService;
/**
 * ������bean
 * ����ʱ�� 2015-01-08
 * ���� FW
 * �޸���
 * �޸�ʱ��
 */
@ManagedBean(name="JobDispatchMaterialInStorage")
@ViewScoped
public class JobDispatchMaterialInStorageBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String success;
	/**
	 * ���μ���List
	 */
	List<Map<String,Object>> jobPlanList = new ArrayList<Map<String,Object>>();
	/**
	 * ���ϼ���List
	 */
	List<Map<String,Object>> materialInfoList = new ArrayList<Map<String,Object>>();
	
	/**
	 * ���������Ϣ
	 */
	List<Map<String,Object>> materialInStorageInfoList = new ArrayList<Map<String,Object>>();
	/**
	 * temp���������Ϣ
	 */
	List<Map<String,Object>> tempMaterialInStorageInfoList = new ArrayList<Map<String,Object>>();
	/**
	 * �ⷿ��Ϣ
	 */
	List<Map<String,Object>> storageInfoList = new ArrayList<Map<String,Object>>();
	/**
	 * ��λ��Ϣ
	 */
	List<Map<String,Object>> positionInfoList = new ArrayList<Map<String,Object>>();
	/**
	 * ��λ����
	 */
	List<Map<String,Object>> unitList = new ArrayList<Map<String,Object>>();
	/**
	 * �����������У���δ��ʹ�ã�
	 */
	List<Map<String,Object>> materialSeqList = new ArrayList<Map<String,Object>>();
	/**
	 * ���ϰ汾����
	 */
	List<Map<String,Object>> versionList = new ArrayList<Map<String,Object>>();
	/**
	 * �������μ���
	 */
	List<Map<String,Object>> productBatchList = new ArrayList<Map<String,Object>>();
	/**
	 * �������м���
	 */
	List<Map<String,Object>> productSeqList = new ArrayList<Map<String,Object>>();
	/**
	 * �������м���
	 */
	List<Map<String,Object>> tempProductSeqList = new ArrayList<Map<String,Object>>();
	/**
	 * ��������
	 */
	List<Map<String,Object>> setSeqList = new ArrayList<Map<String,Object>>();
	/**
	 * ��ʱ��������
	 */
	List<Map<String,Object>> tempSetSeqList = new ArrayList<Map<String,Object>>();
	/**
	 * ��ʱ��������
	 */
	List<Map<String,Object>> tempSetSeqList2 = new ArrayList<Map<String,Object>>();
	/**
	 * �ڵ�
	 */
	String nodeid;
	/**
	 * ����ID
	 */
	String jobPlanId;
	/**
	 * ѡ������ID
	 */
	String selectedMaterialId;
	/**
	 * ѡ�������
	 */
	String selectedProcessOrder;
	/**
	 * ѡ�񹤵����
	 */
	String selectedJobDispatchNo;
	/**
	 * ѡ��۸�
	 */
	String selectedPrice;
	/**
	 * ѡ����������
	 */
	String selectedMaterialName;
	/**
	 * ѡ���ID
	 */
	String selectedId;
	/**
	 * ѡ������
	 */
	String selectedNum;
	/**
	 * ѡ���������������У�
	 */
	String selectedNum2;
	/**
	 * ѡ���������������У�
	 */
	String tempSelectedNum2;
	/**
	 * ѡ��index
	 */
	String selectedIndex;
	/**
	 * ��������
	 */
	String trantionType;
	
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
	Boolean positionCtrl2;
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
	 * �汾��
	 */
	String versionNo;
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
	 * ��������ID
	 */
	String productBatchId;
	/**
	 * ��������No
	 */
	String productBatchNo;
	/**
	 * �������
	 */
	String inStorageNum;
	/**
	 * ����ģʽ
	 */
	String seqModel;
	/**
	 * ��ʼ����
	 */
	String startSeq;
	/**
	 * ��ֹ����
	 */
	String endSeq;
	/**
	 * ��ֹ�����Ƿ��������
	 */
	Boolean endSeqCtrl;
	/**
	 * ���Ͽ������
	 */
	String materialSeqNo;
	/**
	 * ��������ID
	 */
	Integer ID;
	/**
	 * ��������ID
	 */
	Integer seqId;
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
	 * ��׼��λ����
	 */
	String baseUnitName;
	/**
	 * ��һ�ε�λ������
	 */
	Double oldUnitRate;
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
	 * �ı����Ƿ�༭
	 */
	Boolean isEdit =false;
	/**
	 * �༭��λ��
	 */
	String editPositionNo;
	
	private IStorageManageService iStorageManageService=(IStorageManageService)ServiceFactory.getBean("storageManage");
	
	public JobDispatchMaterialInStorageBean() {
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
	    jobPlanList = iStorageManageService.jobPlanList(nodeid);//���μ���
	    
	    //����No
	    String maxID = iStorageManageService.getMaxID();
	    transNo = Integer.parseInt(maxID)+1;
	    
	    seqModel ="����";
	    endSeqCtrl =true;
	    
	    ID=1;
	    seqId =1;
	    
	    limitPosition =false;
	    limitStorage =false;
	    positionCtrl2= false;
	    positionCtrl =true;
	}
	
	/**
	 * ���ݿⷿ��ȡ��λ����
	 */
	public void getPositionInfo(){
		positionId ="";
		editPositionNo ="";
		for(Map<String,Object> tt:storageInfoList){
			if(storageId.equals(tt.get("id").toString())){
				storageNo = tt.get("no").toString();//���No
				positionType = tt.get("positionType").toString();//��λ����
				if(positionType.equals("2")){
					
					isEdit = true;
					positionCtrl = true;
					positionCtrl2 =false;
				}else if(positionType.equals("3")){
					isEdit =false;
					positionCtrl = true;
					positionCtrl2 =false;
				}else if(positionType.equals("4")){
					if(positionType2.equals("2")){
						isEdit =true;
						positionCtrl = true;
						positionCtrl2 =false;
					}else if(positionType2.equals("3")){
						isEdit =false;
						positionCtrl = true;
						positionCtrl2 =false;
					}else{
						positionCtrl=false;
						positionCtrl2 =true;
						isEdit =true;
					}
					positionType = positionType2;
				}else{
					positionCtrl=false;
					positionCtrl2 =true;
					isEdit =true;
				}
			} 
			
			if(limitPosition)
			{
				positionInfoList = iStorageManageService.limitPositionList(selectedMaterialId,storageId);
			}else{
				positionInfoList = iStorageManageService.getPositionInfoList(storageId);
			}
		}
	}
	
	/**
	 * ��λ�ı�
	 */
	public void positionIdChange(){
		for(Map<String,Object> tt:positionInfoList){
			if(tt.get("id").toString().equals(positionId)){
				positionNo =tt.get("no").toString();
				editPositionNo = tt.get("no").toString();
				break;
			}
		}
	}
	
	/**
	 *  ��ȡ������������
	 */
	public void getMaterialInfo(){
		materialInfoList = iStorageManageService.materialList(jobPlanId);
		for(int i=0;i<materialInfoList.size();i++){
			materialInfoList.get(i).put("inStorageNum", "0.00");//�������
			materialInfoList.get(i).put("index", i);//���
		}
		
		//���List
		productSeqList.clear();
		materialInStorageInfoList.clear();
		
	}
	
	/**
	 * ��ȡ�ⷿ��Ϣ
	 */
	public void getStorageInfo(String materialId,String processOrder,
			String jobdispatchNo,String price,String materialName,String num,String index){
		
		tempMaterialInStorageInfoList.clear();
		versionList.clear();
		versionId ="";
		versionNo ="";
		unitList.clear();
		storageInfoList = iStorageManageService.storgeList(nodeid);//�ⷿ����
		storageId ="";
	    positionId ="";
	    editPositionNo ="";
		
		selectedMaterialId = materialId;
		selectedProcessOrder = processOrder;
		selectedJobDispatchNo = jobdispatchNo;
		selectedPrice = price;
		selectedMaterialName = materialName;
		selectedNum = num;
		selectedIndex = index;
		List<Map<String,Object>> list = iStorageManageService.materialCtrList(materialId);
		
		if(list.size()>0){
			String isBatchCtrl =list.get(0).get("isBatchCtrl").toString();
			String isSeqCtrl =list.get(0).get("isSeqCtrl").toString();
			String isVersionCtrl=list.get(0).get("isVersionCtrl").toString();
			
			positionType2 =list.get(0).get("isPositionCtrl").toString();//��λ��������
			
			if(isBatchCtrl.equals("0")){
				batchCtrl =true;
			}else{
				batchCtrl =false;
			}
			
			if(isSeqCtrl.equals("0")){
				seqCtrl =true;
			}else{
				seqCtrl =false;
			}
			
			if(isVersionCtrl.equals("0")){
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
			
			//���ϰ汾����
			versionList = iStorageManageService.materialVersionList(materialId);
			for(Map<String,Object> map:versionList){
				if(null!= map.get("isDefault") && map.get("isDefault").toString().equals("1")){
					versionId = map.get("id").toString();
					versionNo = map.get("no").toString();
				}
			}
			//��λ���㼯��
			unitList =iStorageManageService.getUnitRateList(unitName, nodeid);
			unitRate =1.0;
			oldUnitRate =1.0;
			
			//�������μ���
			productBatchList = iStorageManageService.getProductBatchList(materialId);
			 //����
			materialSeqList = iStorageManageService.getProductSeqList(materialId, "",false);
			
			for(Map<String,Object> tt:materialInStorageInfoList){
				if(tt.get("selectedIndex").toString().equals(index)){
					tempMaterialInStorageInfoList.add(tt);
				}
			}
			
			//��漯��
			if(limitStorage){
				storageInfoList = iStorageManageService.limitStorgeList(materialId);//���ƿⷿ����
			}
		}
		
	}
	
	/**
	 * ����һ����¼
	 * @return
	 */
	public void addInfo(){
		for(Map<String,Object> tt:storageInfoList){
			if(tt.get("id").toString().equals(storageId)){
				storageNo =tt.get("no").toString();
				break;
			}
		}
		for(Map<String,Object> tt:positionInfoList){
			if(tt.get("id").toString().equals(positionId)){
				positionNo =tt.get("no").toString();
				break;
			}
		}
		//��֯����
		Map<String,Object> tempMap = new HashMap<String, Object>();
		tempMap.put("id", ID);
		tempMap.put("selectedIndex", selectedIndex);
		tempMap.put("selectedPrice", selectedPrice);
		tempMap.put("materialId", selectedMaterialId);
		tempMap.put("transNo", transNo);
		tempMap.put("storageId", storageId);
		tempMap.put("storageNo", storageNo);
		
		tempMap.put("positionCtrl", positionCtrl);
		tempMap.put("positionType", positionType);
		tempMap.put("positionId", positionId);
		tempMap.put("positionNo", editPositionNo);
		
		tempMap.put("num", inStorageNum);
		tempMap.put("unitName", unitName);
		tempMap.put("unitRate", unitRate);
		tempMap.put("baseUnitName", baseUnitName);
		tempMap.put("realNum", Double.parseDouble(inStorageNum)*unitRate);
		
		tempMap.put("isBatchCtrl", batchCtrl);
		tempMap.put("batch", "");
		tempMap.put("isSeqCtrl", seqCtrl);
		tempMap.put("seq", "");
		
		tempMap.put("versionId", versionId);
		tempMap.put("versionNo", versionNo);
		
		tempMaterialInStorageInfoList.add(tempMap);
		materialInStorageInfoList.add(tempMap);
		ID ++;
	}
    
    public void saveInfo() {
    	success ="";
    	//�ж������Ƿ��������
    	for(Map<String,Object> tt: materialInfoList){
    		Double rec =Double.parseDouble(tt.get("receivedNum").toString());
    		Double instorageNum =Double.parseDouble(tt.get("inStorageNum").toString());
    		if(instorageNum>rec){
    			success ="������������ȷ����ȷ��";
        		return;
    		}
    	}
    	
		success = iStorageManageService.saveJobDispatchMaterialInStorage(materialInfoList,materialInStorageInfoList, productSeqList,
				transUser,transTypeId,transDate,jobPlanId);
	}
    
    /**
	 * �������η���
	 * @return
	 */
	public void batchSelectedSet(String index,String num){
		selectedId =index;
		selectedNum2 = num;
		
		startSeq ="";
		endSeq ="";
	}
    
    /**
	 * ��λѡ��
	 */
	public void unitChange(){
		for(Map<String,Object> map:unitList){
			if(unitName.equals(map.get("name").toString())){
				unitRate =Double.parseDouble(map.get("rate").toString());
				inStorageNum =""; 
				selectedNum = Double.toString((Double.parseDouble(selectedNum)*oldUnitRate/unitRate));
				oldUnitRate = unitRate;
			}
		}
	}
	
	/**
	 * �������з���
	 * @return
	 */
	public void seqSelectedSet(String index,String num){
		selectedId =index;
		selectedNum2 = num;
		tempSelectedNum2 =num;
		startSeq ="";
		endSeq ="";
		tempProductSeqList.clear();
		
		//��������������
		tempSetSeqList.clear();
		for(Map<String,Object> tt:tempSetSeqList2){
			if(tt.get("fromId").toString().equals(selectedId)){
				tempSetSeqList.add(tt);
			}
		}
		
		for(Map<String,Object> tt:productSeqList){
			if(tt.get("id").toString().equals(selectedId)){
				tempProductSeqList.add(tt);
			}                                              
		}
		selectedNum2 = String.valueOf(Double.parseDouble(selectedNum2)-tempProductSeqList.size());
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
		tempMaterialInStorageInfoList.remove(temp);
		
		//����
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:productSeqList){
			if(index.equals(map.get("id").toString())){
				list.add(map);
			}
		}
		for(Map<String,Object> map:list){
			productSeqList.remove(map);
		}
		
	}
	
	public void setProductBatch(){
		productBatchNo =productBatchId;
	}
	
	/**
	 * ���α���
	 */
	public void saveProductBatch(){
		for(Map<String,Object> tt:materialInStorageInfoList){
			if(selectedId.equals(tt.get("id").toString())){
				tt.put("batch", productBatchNo);
			}
		}
	}
	
	/**
	 * ���������иı�
	 */
	public void seqChange(){
		if(materialSeqNo!="")
		{
			startSeq =materialSeqNo;
			materialSeqNo ="";
			setSeq();
		}
	}
	
	/**
	 * ��ֹ����ҳ�����
	 */
	public void endSeqCtrl() {
		if(seqModel.equals("����")){
			endSeqCtrl =true;
		}else
		{
			endSeqCtrl = false;
			
		}
		endSeq ="";
		startSeq ="";
	}
	
	/**
	 * ��������
	 */
	public void setSeq(){
		if(null !=startSeq && startSeq!="")
		{  
			Map<String,Object> temp = new HashMap<String, Object>();
		    setSeqList.clear();
			//�ж����������Ƿ����
		    //�ٻ������Ƿ����
		    for(Map<String,Object> tt:productSeqList){
		    	if(startSeq.equals(tt.get("seqNo").toString())){
		    		startSeq ="";
					return;
		    	}
		    }
		    for(Map<String,Object> tt:tempProductSeqList){
		    	if(startSeq.equals(tt.get("seqNo").toString())){
		    		startSeq ="";
					return;
		    	}
		    }
		    //�����ݿ����Ƿ����
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			list = iStorageManageService.getProductSeqList(selectedMaterialId, startSeq,true);
			if(list.size()>0){
				startSeq ="";
				return;
			}
			else
			{
				if(endSeqCtrl){//����
					temp.put("startSeq", startSeq);
					setSeqList.add(temp);
					endSeq = startSeq;
				}else{//��Χ
					int len = index(startSeq);//
					if(len ==0){
						temp.put("startSeq", startSeq);
						setSeqList.add(temp);
						endSeq = startSeq;
					}else{
						//������к�
						String max ="";
						String leftStr =startSeq.substring(0,startSeq.length()-len);
						String rightStr =startSeq.substring(startSeq.length()-len);
						for(int j=0;j<len;j++){
							max+="9";
						}
						if(Integer.parseInt(max)-Integer.parseInt(rightStr)>Double.parseDouble(selectedNum2)){
							for(int k=Integer.parseInt(rightStr);k<(Integer.parseInt(rightStr)+Double.parseDouble(selectedNum2));
									k++)
							{   
								String f = "%0" + rightStr.length() + "d";
						        String k1= String.format(f, k);
						        //�ٻ������Ƿ����
							    for(Map<String,Object> tt:productSeqList){
							    	if((leftStr+k1).equals(tt.get("seqNo").toString())){
							    		break;
							    	}
							    }
							    for(Map<String,Object> tt:tempProductSeqList){
							    	if(startSeq.equals(tt.get("seqNo").toString())){
							    		startSeq ="";
										return;
							    	}
							    }
							    //�����ݿ����Ƿ����
								List<Map<String,Object>> list1= new ArrayList<Map<String,Object>>();
								list1 = iStorageManageService.getProductSeqList(selectedMaterialId, leftStr+k1,true);
								if(list1.size()>0){
									//String k2= String.format(f, k-1);
									break;
								}else{
									Map<String,Object> temp2 = new HashMap<String, Object>();
									temp2.put("startSeq", leftStr+k1);
									setSeqList.add(temp2);
									endSeq = leftStr+k1;
								}
							}
						}else{
							for(int k=Integer.parseInt(rightStr);k<=Integer.parseInt(max);
									k++)
							{   
								String f = "%0" + rightStr.length() + "d";
						        String k1= String.format(f, k);
						        //�ٻ������Ƿ����
							    for(Map<String,Object> tt:productSeqList){
							    	if((leftStr+k1).equals(tt.get("seqNo").toString())){
							    		break;
							    	}
							    }
							    for(Map<String,Object> tt:tempProductSeqList){
							    	if(startSeq.equals(tt.get("seqNo").toString())){
							    		startSeq ="";
										return;
							    	}
							    }
							    //�����ݿ����Ƿ����
								List<Map<String,Object>> list1= new ArrayList<Map<String,Object>>();
								list1 = iStorageManageService.getProductSeqList(selectedMaterialId, leftStr+k1,true);
								if(list1.size()>0){
									//String k2= String.format(f, k-1);
									break;
								}else{
									Map<String,Object> temp2 = new HashMap<String, Object>();
									temp2.put("startSeq", leftStr+k1);
									setSeqList.add(temp2);
									endSeq = leftStr+k1;
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * �ж����ֳ���
	 * @param s
	 * @return
	 */
	public int index(String str)
	{    
		 int index=0;
		 for (int i = str.length(); i >0; i--)
		 {   
			if(!isNumeric(str.substring(i-1))){
				return index;
			}
			index++;
		 }
		 return index;
	}
	
	/**
	 * �ж��Ƿ�Ϊ����
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    return pattern.matcher(str).matches();     
	} 
	
	/**
	 * ��������
	 */
	public void seqAdd(){
		if(startSeq ==""){
			return;
		}
		if(setSeqList.size()>0 && setSeqList.size()<=Double.parseDouble(selectedNum2)){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", seqId);
			map.put("fromId", selectedId);
			map.put("startSeq", startSeq);
			map.put("endSeq", endSeq);
			map.put("seqNum", setSeqList.size());
			tempSetSeqList.add(map);
			tempSetSeqList2.add(map);//��¼���л���
			
			for(Map<String,Object> tt:setSeqList){
				Map<String,Object> map2 = new HashMap<String, Object>();
				map2.put("id", selectedId);
				map2.put("seqId", seqId);
				map2.put("seqNo", tt.get("startSeq").toString());
				tempProductSeqList.add(map2);
			}
			seqId++;
			selectedNum2 = String.valueOf(Double.parseDouble(selectedNum2)-setSeqList.size());
			startSeq ="";
			endSeq ="";
		}
	}
	
	public void saveProductSeq(){
		success="";
		//�ж�
		if(Double.parseDouble(tempSelectedNum2)!=tempProductSeqList.size()){
			success="������������ȷ,��ȷ��";
			return;
		}
		for(Map<String,Object> tt:tempProductSeqList){
			productSeqList.add(tt);
		}
	}
	
	/**
	 * ��֤��Ϣ
	 * @return
	 */
	public void checkData(){
		success ="";
		Double totalNum =0.0;
		if(materialInStorageInfoList.size() ==0){
			success ="���������";
			return;
		}
		//�������
		for(Map<String,Object> map:materialInStorageInfoList){
			totalNum +=Double.parseDouble(map.get("realNum").toString());
		}
		if(totalNum>Double.parseDouble(selectedNum)){
			success ="���������������������ȷ��";
			return;
		}
		//����
		if(batchCtrl){
			for(Map<String,Object> tt:materialInStorageInfoList){
				if(null == tt.get("batch") || tt.get("batch").toString() ==""){
					success ="������������Ϣ";
					return;
				}
			}
		}
		
		//����
		if(seqCtrl){
			if(totalNum !=productSeqList.size()){
				success ="������������Ϣ";
				return;
			}
		}
		success = "ͨ��";
	}
	
	
	/**
	 * �����Ϣ�༭
	 */
	public void editDataListInfo(){
		if(materialInStorageInfoList.size()>0){
			String index = materialInStorageInfoList.get(0).get("selectedIndex").toString();
			Double totalNum =0.0;
			//�������
			for(Map<String,Object> map:materialInStorageInfoList){
				totalNum +=Double.parseDouble(map.get("realNum").toString());
			}
			for(Map<String,Object> tt:materialInfoList){
				if(tt.get("index").toString().equals(index)){
					tt.put("inStorageNum", totalNum);
				}
			}
		}
		
	}
	
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getNodeid() {
		return nodeid;
	}

	public List<Map<String, Object>> getJobPlanList() {
		return jobPlanList;
	}

	public void setJobPlanList(List<Map<String, Object>> jobPlanList) {
		this.jobPlanList = jobPlanList;
	}

	public List<Map<String, Object>> getMaterialInfoList() {
		return materialInfoList;
	}

	public void setMaterialInfoList(List<Map<String, Object>> materialInfoList) {
		this.materialInfoList = materialInfoList;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getJobPlanId() {
		return jobPlanId;
	}

	public void setJobPlanId(String jobPlanId) {
		this.jobPlanId = jobPlanId;
	}

	public String getSelectedMaterialId() {
		return selectedMaterialId;
	}

	public void setSelectedMaterialId(String selectedMaterialId) {
		this.selectedMaterialId = selectedMaterialId;
	}

	public String getSelectedProcessOrder() {
		return selectedProcessOrder;
	}

	public void setSelectedProcessOrder(String selectedProcessOrder) {
		this.selectedProcessOrder = selectedProcessOrder;
	}
	
	public String getTrantionType() {
		return trantionType;
	}

	public void setTrantionType(String trantionType) {
		this.trantionType = trantionType;
	}
	public String getSelectedPrice() {
		return selectedPrice;
	}

	public void setSelectedPrice(String selectedPrice) {
		this.selectedPrice = selectedPrice;
	}

	public int getTransNo() {
		return transNo;
	}

	public void setTransNo(int transNo) {
		this.transNo = transNo;
	}

	public List<Map<String, Object>> getMaterialInStorageInfoList() {
		return materialInStorageInfoList;
	}

	public void setMaterialInStorageInfoList(
			List<Map<String, Object>> materialInStorageInfoList) {
		this.materialInStorageInfoList = materialInStorageInfoList;
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

	public List<Map<String, Object>> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<Map<String, Object>> unitList) {
		this.unitList = unitList;
	}

	public List<Map<String, Object>> getMaterialSeqList() {
		return materialSeqList;
	}

	public void setMaterialSeqList(List<Map<String, Object>> materialSeqList) {
		this.materialSeqList = materialSeqList;
	}

	public List<Map<String, Object>> getVersionList() {
		return versionList;
	}

	public void setVersionList(List<Map<String, Object>> versionList) {
		this.versionList = versionList;
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

	public List<Map<String, Object>> getTempProductSeqList() {
		return tempProductSeqList;
	}

	public void setTempProductSeqList(List<Map<String, Object>> tempProductSeqList) {
		this.tempProductSeqList = tempProductSeqList;
	}

	public List<Map<String, Object>> getSetSeqList() {
		return setSeqList;
	}

	public void setSetSeqList(List<Map<String, Object>> setSeqList) {
		this.setSeqList = setSeqList;
	}

	public List<Map<String, Object>> getTempSetSeqList() {
		return tempSetSeqList;
	}

	public void setTempSetSeqList(List<Map<String, Object>> tempSetSeqList) {
		this.tempSetSeqList = tempSetSeqList;
	}

	public String getSelectedJobDispatchNo() {
		return selectedJobDispatchNo;
	}

	public void setSelectedJobDispatchNo(String selectedJobDispatchNo) {
		this.selectedJobDispatchNo = selectedJobDispatchNo;
	}

	public String getSelectedMaterialName() {
		return selectedMaterialName;
	}

	public void setSelectedMaterialName(String selectedMaterialName) {
		this.selectedMaterialName = selectedMaterialName;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public String getSelectedNum() {
		return selectedNum;
	}

	public void setSelectedNum(String selectedNum) {
		this.selectedNum = selectedNum;
	}

	public String getTempSelectedNum2() {
		return tempSelectedNum2;
	}

	public void setTempSelectedNum2(String tempSelectedNum2) {
		this.tempSelectedNum2 = tempSelectedNum2;
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

	public Boolean getPositionCtrl() {
		return positionCtrl;
	}

	public void setPositionCtrl(Boolean positionCtrl) {
		this.positionCtrl = positionCtrl;
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

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getStorageNo() {
		return storageNo;
	}

	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionNo() {
		return positionNo;
	}

	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

	public String getProductBatchId() {
		return productBatchId;
	}

	public void setProductBatchId(String productBatchId) {
		this.productBatchId = productBatchId;
	}

	public String getProductBatchNo() {
		return productBatchNo;
	}

	public void setProductBatchNo(String productBatchNo) {
		this.productBatchNo = productBatchNo;
	}

	public String getInStorageNum() {
		return inStorageNum;
	}

	public void setInStorageNum(String inStorageNum) {
		this.inStorageNum = inStorageNum;
	}

	public String getSeqModel() {
		return seqModel;
	}

	public void setSeqModel(String seqModel) {
		this.seqModel = seqModel;
	}

	public String getStartSeq() {
		return startSeq;
	}

	public void setStartSeq(String startSeq) {
		this.startSeq = startSeq;
	}

	public String getEndSeq() {
		return endSeq;
	}

	public void setEndSeq(String endSeq) {
		this.endSeq = endSeq;
	}

	public Boolean getEndSeqCtrl() {
		return endSeqCtrl;
	}

	public void setEndSeqCtrl(Boolean endSeqCtrl) {
		this.endSeqCtrl = endSeqCtrl;
	}

	public String getMaterialSeqNo() {
		return materialSeqNo;
	}

	public void setMaterialSeqNo(String materialSeqNo) {
		this.materialSeqNo = materialSeqNo;
	}

	public Integer getSeqId() {
		return seqId;
	}

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
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

	public Double getOldUnitRate() {
		return oldUnitRate;
	}

	public void setOldUnitRate(Double oldUnitRate) {
		this.oldUnitRate = oldUnitRate;
	}

	public Boolean getTempVersionCtrl() {
		return tempVersionCtrl;
	}

	public void setTempVersionCtrl(Boolean tempVersionCtrl) {
		this.tempVersionCtrl = tempVersionCtrl;
	}

	public List<Map<String, Object>> getTempMaterialInStorageInfoList() {
		return tempMaterialInStorageInfoList;
	}

	public void setTempMaterialInStorageInfoList(
			List<Map<String, Object>> tempMaterialInStorageInfoList) {
		this.tempMaterialInStorageInfoList = tempMaterialInStorageInfoList;
	}

	public Boolean getPositionCtrl2() {
		return positionCtrl2;
	}

	public void setPositionCtrl2(Boolean positionCtrl2) {
		this.positionCtrl2 = positionCtrl2;
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
	
}

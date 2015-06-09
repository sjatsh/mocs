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
 * 库存管理bean
 * 创建时间 2015-01-08
 * 作者 FW
 * 修改者
 * 修改时间
 */
@ManagedBean(name="JobDispatchMaterialInStorage")
@ViewScoped
public class JobDispatchMaterialInStorageBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String success;
	/**
	 * 批次集合List
	 */
	List<Map<String,Object>> jobPlanList = new ArrayList<Map<String,Object>>();
	/**
	 * 物料集合List
	 */
	List<Map<String,Object>> materialInfoList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 物料入库信息
	 */
	List<Map<String,Object>> materialInStorageInfoList = new ArrayList<Map<String,Object>>();
	/**
	 * temp物料入库信息
	 */
	List<Map<String,Object>> tempMaterialInStorageInfoList = new ArrayList<Map<String,Object>>();
	/**
	 * 库房信息
	 */
	List<Map<String,Object>> storageInfoList = new ArrayList<Map<String,Object>>();
	/**
	 * 货位信息
	 */
	List<Map<String,Object>> positionInfoList = new ArrayList<Map<String,Object>>();
	/**
	 * 单位集合
	 */
	List<Map<String,Object>> unitList = new ArrayList<Map<String,Object>>();
	/**
	 * 既有物料序列（但未被使用）
	 */
	List<Map<String,Object>> materialSeqList = new ArrayList<Map<String,Object>>();
	/**
	 * 物料版本集合
	 */
	List<Map<String,Object>> versionList = new ArrayList<Map<String,Object>>();
	/**
	 * 生产批次集合
	 */
	List<Map<String,Object>> productBatchList = new ArrayList<Map<String,Object>>();
	/**
	 * 生产序列集合
	 */
	List<Map<String,Object>> productSeqList = new ArrayList<Map<String,Object>>();
	/**
	 * 生产序列集合
	 */
	List<Map<String,Object>> tempProductSeqList = new ArrayList<Map<String,Object>>();
	/**
	 * 设置序列
	 */
	List<Map<String,Object>> setSeqList = new ArrayList<Map<String,Object>>();
	/**
	 * 临时设置序列
	 */
	List<Map<String,Object>> tempSetSeqList = new ArrayList<Map<String,Object>>();
	/**
	 * 临时设置序列
	 */
	List<Map<String,Object>> tempSetSeqList2 = new ArrayList<Map<String,Object>>();
	/**
	 * 节点
	 */
	String nodeid;
	/**
	 * 批次ID
	 */
	String jobPlanId;
	/**
	 * 选择物料ID
	 */
	String selectedMaterialId;
	/**
	 * 选择工序序号
	 */
	String selectedProcessOrder;
	/**
	 * 选择工单编号
	 */
	String selectedJobDispatchNo;
	/**
	 * 选择价格
	 */
	String selectedPrice;
	/**
	 * 选择物料名称
	 */
	String selectedMaterialName;
	/**
	 * 选择的ID
	 */
	String selectedId;
	/**
	 * 选择数量
	 */
	String selectedNum;
	/**
	 * 选择数量（用于序列）
	 */
	String selectedNum2;
	/**
	 * 选择数量（用于序列）
	 */
	String tempSelectedNum2;
	/**
	 * 选择index
	 */
	String selectedIndex;
	/**
	 * 处理类型
	 */
	String trantionType;
	
	/**
     * 事务No
     */
	int transNo;
	/**
	 * 事务处理日期
	 */
	Date transDate;
	/**
	 * 事务处理人员
	 */
	String transUser;
	/**
	 * 事务类型Id
	 */
	String transTypeId;
	/**
	 * 事务类型活动
	 */
	String transActivity;
	
	/**
	 * 批次控制
	 */
	Boolean batchCtrl;
	/**
	 * 序列控制
	 */
	Boolean seqCtrl;
	/**
     * 货位控制
     */
	Boolean positionCtrl;
	/**
     * 货位控制2
     */
	Boolean positionCtrl2;
	/**
	 * 版本控制
	 */
	Boolean versionCtrl;
	/**
	 * 版本控制2
	 */
	Boolean tempVersionCtrl;
	/**
	 * 版本Id
	 */
	String versionId;
	/**
	 * 版本号
	 */
	String versionNo;
	/**
	 * 库房ID
	 */
	String storageId;
	/**
	 * 库房NO
	 */
	String storageNo;
	/**
	 * 货位ID
	 */
	String positionId;
	/**
	 * 货位NO
	 */
	String positionNo;
	/**
	 * 生产批次ID
	 */
	String productBatchId;
	/**
	 * 生产批次No
	 */
	String productBatchNo;
	/**
	 * 入库数量
	 */
	String inStorageNum;
	/**
	 * 序列模式
	 */
	String seqModel;
	/**
	 * 起始序列
	 */
	String startSeq;
	/**
	 * 终止序列
	 */
	String endSeq;
	/**
	 * 终止序列是否输入控制
	 */
	Boolean endSeqCtrl;
	/**
	 * 物料库存序列
	 */
	String materialSeqNo;
	/**
	 * 用于生成ID
	 */
	Integer ID;
	/**
	 * 用于序列ID
	 */
	Integer seqId;
	/**
	 * 单位Id
	 */
	String unitId;
	/**
	 * 单位名称
	 */
	String unitName;
	/**
	 * 单位换算率
	 */
	Double unitRate;
	/**
	 * 基准单位名称
	 */
	String baseUnitName;
	/**
	 * 上一次单位换算率
	 */
	Double oldUnitRate;
	/**
	 * 物料限制库存
	 */
	Boolean limitStorage;
	/**
	 * 物料限制货位
	 */
	Boolean limitPosition;
	/**
	 * 库房货位类型
	 */
	String positionType;
	/**
	 * 组织物料货位类型
	 */
	String positionType2;
	/**
	 * 文本框是否编辑
	 */
	Boolean isEdit =false;
	/**
	 * 编辑货位号
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
		//获取节点ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    nodeid = (String)session.getAttribute("nodeid");
	    jobPlanList = iStorageManageService.jobPlanList(nodeid);//批次集合
	    
	    //事务No
	    String maxID = iStorageManageService.getMaxID();
	    transNo = Integer.parseInt(maxID)+1;
	    
	    seqModel ="单个";
	    endSeqCtrl =true;
	    
	    ID=1;
	    seqId =1;
	    
	    limitPosition =false;
	    limitStorage =false;
	    positionCtrl2= false;
	    positionCtrl =true;
	}
	
	/**
	 * 根据库房获取货位集合
	 */
	public void getPositionInfo(){
		positionId ="";
		editPositionNo ="";
		for(Map<String,Object> tt:storageInfoList){
			if(storageId.equals(tt.get("id").toString())){
				storageNo = tt.get("no").toString();//库存No
				positionType = tt.get("positionType").toString();//货位类型
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
	 * 货位改变
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
	 *  获取批次所需物料
	 */
	public void getMaterialInfo(){
		materialInfoList = iStorageManageService.materialList(jobPlanId);
		for(int i=0;i<materialInfoList.size();i++){
			materialInfoList.get(i).put("inStorageNum", "0.00");//入库数量
			materialInfoList.get(i).put("index", i);//序号
		}
		
		//清空List
		productSeqList.clear();
		materialInStorageInfoList.clear();
		
	}
	
	/**
	 * 获取库房信息
	 */
	public void getStorageInfo(String materialId,String processOrder,
			String jobdispatchNo,String price,String materialName,String num,String index){
		
		tempMaterialInStorageInfoList.clear();
		versionList.clear();
		versionId ="";
		versionNo ="";
		unitList.clear();
		storageInfoList = iStorageManageService.storgeList(nodeid);//库房集合
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
			
			positionType2 =list.get(0).get("isPositionCtrl").toString();//货位控制类型
			
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
			
			//限制库存
			if(null!=list.get(0).get("isStockCtrl")){
				if(list.get(0).get("isStockCtrl").toString().equals("0")){
					limitStorage =true;
				}else{
					limitStorage =false;
				}
			}else{
				limitStorage =false;
			}
			
			//限制货位
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
			
			//物料版本集合
			versionList = iStorageManageService.materialVersionList(materialId);
			for(Map<String,Object> map:versionList){
				if(null!= map.get("isDefault") && map.get("isDefault").toString().equals("1")){
					versionId = map.get("id").toString();
					versionNo = map.get("no").toString();
				}
			}
			//单位换算集合
			unitList =iStorageManageService.getUnitRateList(unitName, nodeid);
			unitRate =1.0;
			oldUnitRate =1.0;
			
			//生产批次集合
			productBatchList = iStorageManageService.getProductBatchList(materialId);
			 //序列
			materialSeqList = iStorageManageService.getProductSeqList(materialId, "",false);
			
			for(Map<String,Object> tt:materialInStorageInfoList){
				if(tt.get("selectedIndex").toString().equals(index)){
					tempMaterialInStorageInfoList.add(tt);
				}
			}
			
			//库存集合
			if(limitStorage){
				storageInfoList = iStorageManageService.limitStorgeList(materialId);//限制库房集合
			}
		}
		
	}
	
	/**
	 * 增加一条记录
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
		//组织数据
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
    	//判断数据是否符合条件
    	for(Map<String,Object> tt: materialInfoList){
    		Double rec =Double.parseDouble(tt.get("receivedNum").toString());
    		Double instorageNum =Double.parseDouble(tt.get("inStorageNum").toString());
    		if(instorageNum>rec){
    			success ="物料数量不正确，请确认";
        		return;
    		}
    	}
    	
		success = iStorageManageService.saveJobDispatchMaterialInStorage(materialInfoList,materialInStorageInfoList, productSeqList,
				transUser,transTypeId,transDate,jobPlanId);
	}
    
    /**
	 * 物料批次分配
	 * @return
	 */
	public void batchSelectedSet(String index,String num){
		selectedId =index;
		selectedNum2 = num;
		
		startSeq ="";
		endSeq ="";
	}
    
    /**
	 * 单位选择
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
	 * 物料序列分配
	 * @return
	 */
	public void seqSelectedSet(String index,String num){
		selectedId =index;
		selectedNum2 = num;
		tempSelectedNum2 =num;
		startSeq ="";
		endSeq ="";
		tempProductSeqList.clear();
		
		//已设置序列设置
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
	 * 删除列表信息
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
		
		//序列
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
	 * 批次保存
	 */
	public void saveProductBatch(){
		for(Map<String,Object> tt:materialInStorageInfoList){
			if(selectedId.equals(tt.get("id").toString())){
				tt.put("batch", productBatchNo);
			}
		}
	}
	
	/**
	 * 下拉框序列改变
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
	 * 终止序列页面控制
	 */
	public void endSeqCtrl() {
		if(seqModel.equals("单个")){
			endSeqCtrl =true;
		}else
		{
			endSeqCtrl = false;
			
		}
		endSeq ="";
		startSeq ="";
	}
	
	/**
	 * 设置序列
	 */
	public void setSeq(){
		if(null !=startSeq && startSeq!="")
		{  
			Map<String,Object> temp = new HashMap<String, Object>();
		    setSeqList.clear();
			//判断输入序列是否存在
		    //①缓存中是否存在
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
		    //②数据库中是否存在
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			list = iStorageManageService.getProductSeqList(selectedMaterialId, startSeq,true);
			if(list.size()>0){
				startSeq ="";
				return;
			}
			else
			{
				if(endSeqCtrl){//单个
					temp.put("startSeq", startSeq);
					setSeqList.add(temp);
					endSeq = startSeq;
				}else{//范围
					int len = index(startSeq);//
					if(len ==0){
						temp.put("startSeq", startSeq);
						setSeqList.add(temp);
						endSeq = startSeq;
					}else{
						//最大序列号
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
						        //①缓存中是否存在
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
							    //②数据库中是否存在
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
						        //①缓存中是否存在
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
							    //②数据库中是否存在
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
	 * 判断数字长度
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
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    return pattern.matcher(str).matches();     
	} 
	
	/**
	 * 增加序列
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
			tempSetSeqList2.add(map);//记录序列缓存
			
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
		//判断
		if(Double.parseDouble(tempSelectedNum2)!=tempProductSeqList.size()){
			success="序列数量不正确,请确认";
			return;
		}
		for(Map<String,Object> tt:tempProductSeqList){
			productSeqList.add(tt);
		}
	}
	
	/**
	 * 验证信息
	 * @return
	 */
	public void checkData(){
		success ="";
		Double totalNum =0.0;
		if(materialInStorageInfoList.size() ==0){
			success ="请添加数据";
			return;
		}
		//入库数量
		for(Map<String,Object> map:materialInStorageInfoList){
			totalNum +=Double.parseDouble(map.get("realNum").toString());
		}
		if(totalNum>Double.parseDouble(selectedNum)){
			success ="入库数量大于现有量，请确认";
			return;
		}
		//批次
		if(batchCtrl){
			for(Map<String,Object> tt:materialInStorageInfoList){
				if(null == tt.get("batch") || tt.get("batch").toString() ==""){
					success ="请设置批次信息";
					return;
				}
			}
		}
		
		//序列
		if(seqCtrl){
			if(totalNum !=productSeqList.size()){
				success ="请设置序列信息";
				return;
			}
		}
		success = "通过";
	}
	
	
	/**
	 * 入库信息编辑
	 */
	public void editDataListInfo(){
		if(materialInStorageInfoList.size()>0){
			String index = materialInStorageInfoList.get(0).get("selectedIndex").toString();
			Double totalNum =0.0;
			//入库数量
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

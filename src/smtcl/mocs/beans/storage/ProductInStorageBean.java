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
 * 物料出入库bean
 * 创建时间 2014-11-25
 * 作者 FW
 * 修改者
 * 修改时间
 */
@ManagedBean(name="ProductInStorage")
@ViewScoped
public class ProductInStorageBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String success;
	/**
	 * 批次集合List
	 */
	List<Map<String,Object>> jobPlanList = new ArrayList<Map<String,Object>>();
	/**
	 * 批次信息
	 */
	List<Map<String,Object>> jobPlanInfoList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 库房信息
	 */
	List<Map<String,Object>> storageInfoList = new ArrayList<Map<String,Object>>();
	/**
	 * 货位信息
	 */
	List<Map<String,Object>> positionInfoList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 物料入库信息
	 */
	List<Map<String,Object>> materialInStorageInfoList = new ArrayList<Map<String,Object>>();
	
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
	 * 节点
	 */
	String nodeid;
	/**
	 * 批次ID
	 */
	String jobPlanId;
	/**
	 * 批次计划数量
	 */
	Integer jobPlanPlanNum;
	/**
	 * 批次完成数量
	 */
	Double jobPlanFinishNum;
	/**
	 * 批次入库数量
	 */
	Integer jobPlanInstockNum;
	/**
	 * 零件名称
	 */
	String partName;
	/**
	 * 零件编号
	 */
	String partNo;
	/**
	 * 物料ID
	 */
	String materialId;
	/**
	 * 批次可入库数量
	 */
	Double jobPlanUnInstockNum;
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
	 * 入库数量
	 */
	String inStorageNum;
	/**
	 * 批次控制
	 */
	Boolean batchCtr;
	/**
	 * 序列控制
	 */
	Boolean seqCtr;
	/**
	 * 生产批次ID
	 */
	String productBatchId;
	/**
	 * 生产批次No
	 */
	String productBatchNo;
	/**
	 * 生产序列ID
	 */
	String productSeqId;
	/**
	 * 生产序列No
	 */
	String productSeqNo;
	
	/**
	 * 选择的ID
	 */
	String selectedId;
	/**
	 * 选择的数量
	 */
	String selectedNum;
	/**
	 * temp选择的数量
	 */
	String tempSelectedNum;
	/**
     * 总入库数量
     */
	Double totalNum;
	/**
     * 货位控制
     */
	Boolean positionCtrl;
	/**
     * 货位控制2
     */
	Boolean positionCtrl2;
	/**
	 * 用于生成ID
	 */
	Integer ID;
	/**
	 * 用于序列ID
	 */
	Integer seqId;
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
	 * 版本No
	 */
	String versionNo;
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
	
	public ProductInStorageBean() {
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
				positionInfoList = iStorageManageService.limitPositionList(materialId,storageId);
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
	 * 获取批次相关信息
	 */
	public void getJobPlanInfo(){
	    ID =1;
	    seqId =1;
	    batchCtr =false;
	    seqCtr =false;
	    materialInStorageInfoList.clear();
	    productSeqList.clear();
	    tempSetSeqList.clear();
	    tempSetSeqList2.clear();
	    unitRate =1.0;
	    
	    storageId ="";
	    positionId ="";
	    editPositionNo ="";
	    
	    positionInfoList.clear();
	    storageInfoList = iStorageManageService.storgeList(nodeid);//库房集合
	    
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = iStorageManageService.getJobPlanInfoList(jobPlanId);//批次相关信息
		for(Map<String,Object> tt:list){
			if(null !=tt.get("planNum")&&tt.get("planNum").toString() !=""){
				jobPlanPlanNum = Integer.parseInt(tt.get("planNum").toString());
			}else{
				jobPlanPlanNum=0;
			}
			
			if(null !=tt.get("finishNum")&&tt.get("finishNum").toString() !=""){
				jobPlanFinishNum = Double.parseDouble(tt.get("finishNum").toString());
			}else{
				jobPlanFinishNum=0.0;
			}
			
			if(null !=tt.get("instockNum")&&tt.get("instockNum").toString() !=""){
				jobPlanInstockNum = Integer.parseInt(tt.get("instockNum").toString());
			}else{
				jobPlanInstockNum =0;
			}
			
			if(null !=tt.get("partName")){
				partName = tt.get("partName").toString();
			}
			
			if(null !=tt.get("partNo")){
				partNo = tt.get("partNo").toString();
			}
			jobPlanUnInstockNum = jobPlanFinishNum-jobPlanInstockNum;
			
			//物料控制信息
			List<Map<String,Object>> list2 = iStorageManageService.materialCtrByNoList(partNo,nodeid);
			if(list2.size()>0){
				String isBatchCtrl =list2.get(0).get("isBatchCtrl").toString();
				String isSeqCtrl =list2.get(0).get("isSeqCtrl").toString();
				positionType2 =list2.get(0).get("isPositionCtrl").toString();//货位控制类型
				String isVersionCtrl=list2.get(0).get("isVersionCtrl").toString();
				
				if(isBatchCtrl.equals("0")){
					batchCtr =true;
				}else{
					batchCtr =false;
				}
				
				if(isSeqCtrl.equals("0")){
					seqCtr =true;
				}else{
					seqCtr =false;
				}
				
				if(isVersionCtrl.equals("0")){
					versionCtrl =true;
					tempVersionCtrl = false;
				}else{
					versionCtrl =false;
					tempVersionCtrl = true;
				}
				
				//限制库存
				if(null!=list2.get(0).get("isStockCtrl")){
					if(list2.get(0).get("isStockCtrl").toString().equals("0")){
						limitStorage =true;
					}else{
						limitStorage =false;
					}
				}else{
					limitStorage =false;
				}
				
				//限制货位
				if(null!=list2.get(0).get("isAxisCtrl")){
					if(list2.get(0).get("isAxisCtrl").toString().equals("0")){
						limitPosition =true;
					}else{
						limitPosition =false;
					}
				}else{
					limitPosition =false;
				}
				
				if(null!= list2.get(0).get("unitName")){
					 unitName = list2.get(0).get("unitName").toString();
					 baseUnitName = list2.get(0).get("unitName").toString();
				}
				materialId= list2.get(0).get("id").toString();
				//生产批次集合
				productBatchList = iStorageManageService.getProductBatchList(materialId);
				//单位换算集合
				unitList =iStorageManageService.getUnitRateList(unitName, nodeid);
				 //序列
				materialSeqList = iStorageManageService.getProductSeqList(materialId, "",false);
				//物料版本集合
				versionList = iStorageManageService.materialVersionList(materialId);
				for(Map<String,Object> map:versionList){
					if(null!= map.get("isDefault") && map.get("isDefault").toString().equals("1")){
						versionId = map.get("id").toString();
						versionNo = map.get("no").toString();
					}
				}
				
				//库存集合
				if(limitStorage){
					storageInfoList = iStorageManageService.limitStorgeList(materialId);//限制库房集合
				}
				
			}
		}
		
	}
	
	/**
	 * 物料批次分配
	 * @return
	 */
	public void batchSelectedSet(String index,String num){
		selectedId =index;
		selectedNum = num;
		
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
			}
		}
	}
	
	/**
	 * 物料序列分配
	 * @return
	 */
	public void seqSelectedSet(String index,String num){
		selectedId =index;
		selectedNum = num;
		tempSelectedNum =num;
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
		selectedNum = String.valueOf(Double.parseDouble(selectedNum)-tempSetSeqList.size());
	}
	
	/**
	 * 增加一条记录
	 * @return
	 */
	public void addInfo(){
		//组织数据
		Map<String,Object> tempMap = new HashMap<String, Object>();
		tempMap.put("id", ID);
		//tempMap.put("materialId", materialId);
		tempMap.put("transNo", transNo);
		tempMap.put("partName", partName);
		tempMap.put("partNo", partNo);
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
		
		tempMap.put("isBatchCtr", batchCtr);
		tempMap.put("batch", "");
		tempMap.put("isSeqCtr", seqCtr);
		tempMap.put("seq", "");
		
		tempMap.put("versionId", versionId);
		tempMap.put("versionNo", versionNo);
		
		materialInStorageInfoList.add(tempMap);
		ID ++;
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
	 * 验证信息
	 * @return
	 */
	public void checkData(){
		success ="";
		totalNum =0.0;
		if(materialInStorageInfoList.size() ==0){
			success ="请添加数据";
			return;
		}
		//入库数量
		for(Map<String,Object> map:materialInStorageInfoList){
			totalNum +=Double.parseDouble(map.get("realNum").toString());
		}
		if(totalNum>jobPlanUnInstockNum){
			success ="入库数量大于现有量，请确认";
			return;
		}
		//批次
		if(batchCtr){
			for(Map<String,Object> tt:materialInStorageInfoList){
				if(null == tt.get("batch") || tt.get("batch").toString() ==""){
					success ="请设置批次信息";
					return;
				}
			}
		}
		
		//序列
		if(seqCtr){
			if(totalNum !=productSeqList.size()){
				success ="请设置序列信息";
				return;
			}
		}
		success = "通过";
	}
	
	
	/**
	 * 入库
	 * @return
	 */
	public void saveDataInfo() {
		success ="";
		if(materialInStorageInfoList.size()>0){
			success = iStorageManageService.
					saveProductInStorage(materialInStorageInfoList, productSeqList, materialId,
							batchCtr, seqCtr, jobPlanId, totalNum.intValue(),
							transUser,transTypeId,transDate,nodeid);
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
			list = iStorageManageService.getProductSeqList(materialId, startSeq,true);
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
						if(Integer.parseInt(max)-Integer.parseInt(rightStr)>Double.parseDouble(selectedNum)){
							for(int k=Integer.parseInt(rightStr);k<(Integer.parseInt(rightStr)+Double.parseDouble(selectedNum));
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
								list1 = iStorageManageService.getProductSeqList(materialId, leftStr+k1,true);
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
								list1 = iStorageManageService.getProductSeqList(materialId, leftStr+k1,true);
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
		if(setSeqList.size()>0 && setSeqList.size()<=Double.parseDouble(selectedNum)){
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
			selectedNum = String.valueOf(Double.parseDouble(selectedNum)-setSeqList.size());
			startSeq ="";
			endSeq ="";
		}
	}
	
	public void saveProductSeq(){
		success="";
		//判断
		if(Double.parseDouble(tempSelectedNum)!=tempProductSeqList.size()){
			success="序列数量不正确,请确认";
			return;
		}
		for(Map<String,Object> tt:tempProductSeqList){
			productSeqList.add(tt);
		}
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<Map<String, Object>> getJobPlanList() {
		return jobPlanList;
	}

	public void setJobPlanList(List<Map<String, Object>> jobPlanList) {
		this.jobPlanList = jobPlanList;
	}

	public String getNodeid() {
		return nodeid;
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

	public List<Map<String, Object>> getJobPlanInfoList() {
		return jobPlanInfoList;
	}

	public void setJobPlanInfoList(List<Map<String, Object>> jobPlanInfoList) {
		this.jobPlanInfoList = jobPlanInfoList;
	}

	public Integer getJobPlanPlanNum() {
		return jobPlanPlanNum;
	}

	public void setJobPlanPlanNum(Integer jobPlanPlanNum) {
		this.jobPlanPlanNum = jobPlanPlanNum;
	}

	public Double getJobPlanFinishNum() {
		return jobPlanFinishNum;
	}

	public void setJobPlanFinishNum(Double jobPlanFinishNum) {
		this.jobPlanFinishNum = jobPlanFinishNum;
	}

	public Integer getJobPlanInstockNum() {
		return jobPlanInstockNum;
	}

	public void setJobPlanInstockNum(Integer jobPlanInstockNum) {
		this.jobPlanInstockNum = jobPlanInstockNum;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public Double getJobPlanUnInstockNum() {
		return jobPlanUnInstockNum;
	}

	public void setJobPlanUnInstockNum(Double jobPlanUnInstockNum) {
		this.jobPlanUnInstockNum = jobPlanUnInstockNum;
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

	public String getInStorageNum() {
		return inStorageNum;
	}

	public void setInStorageNum(String inStorageNum) {
		this.inStorageNum = inStorageNum;
	}
	
	public String getStorageNo() {
		return storageNo;
	}

	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}

	public String getPositionNo() {
		return positionNo;
	}

	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

	public Boolean getBatchCtr() {
		return batchCtr;
	}

	public void setBatchCtr(Boolean batchCtr) {
		this.batchCtr = batchCtr;
	}

	public Boolean getSeqCtr() {
		return seqCtr;
	}

	public void setSeqCtr(Boolean seqCtr) {
		this.seqCtr = seqCtr;
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

	public String getProductSeqId() {
		return productSeqId;
	}

	public void setProductSeqId(String productSeqId) {
		this.productSeqId = productSeqId;
	}

	public String getProductSeqNo() {
		return productSeqNo;
	}

	public void setProductSeqNo(String productSeqNo) {
		this.productSeqNo = productSeqNo;
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

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
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

	public List<Map<String, Object>> getTempSetSeqList() {
		return tempSetSeqList;
	}

	public void setTempSetSeqList(List<Map<String, Object>> tempSetSeqList) {
		this.tempSetSeqList = tempSetSeqList;
	}

	public String getTempSelectedNum() {
		return tempSelectedNum;
	}

	public void setTempSelectedNum(String tempSelectedNum) {
		this.tempSelectedNum = tempSelectedNum;
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

	public String getMaterialSeqNo() {
		return materialSeqNo;
	}

	public void setMaterialSeqNo(String materialSeqNo) {
		this.materialSeqNo = materialSeqNo;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public Boolean getTempVersionCtrl() {
		return tempVersionCtrl;
	}

	public void setTempVersionCtrl(Boolean tempVersionCtrl) {
		this.tempVersionCtrl = tempVersionCtrl;
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

	public Boolean getPositionCtrl2() {
		return positionCtrl2;
	}

	public void setPositionCtrl2(Boolean positionCtrl2) {
		this.positionCtrl2 = positionCtrl2;
	}

	public String getEditPositionNo() {
		return editPositionNo;
	}

	public void setEditPositionNo(String editPositionNo) {
		this.editPositionNo = editPositionNo;
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
}

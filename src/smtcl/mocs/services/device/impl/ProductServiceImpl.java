package smtcl.mocs.services.device.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.JobListModel;
import smtcl.mocs.model.ProcessQualityModel;
import smtcl.mocs.model.ProductProcessModel;
import smtcl.mocs.model.TPartBasicInfoModel;
import smtcl.mocs.model.productInProgressModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TUserResource;
import smtcl.mocs.pojos.job.TEquJobDispatch;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.pojos.job.TPartBasicInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.device.IProductService;
import smtcl.mocs.utils.authority.DateUtil;


/**
 * 产品查询业务实现类
 * @作者：JiangFeng
 * @创建时间：2013-04-26
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @版本：V1.0
 */
public class ProductServiceImpl extends GenericServiceSpringImpl<TUserResource, String> implements IProductService {
	
	private IPartService partService;
	public IPartService getPartService() {
		return partService;
	}
	public void setPartService(IPartService partService) {
		this.partService = partService;
	}

	/**
	 * 查询产品类型清单
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IDataCollection<Map<String, Object>> getProductType(int pageNo, int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = null;
		if (nodeIds != null) {
			hql = " SELECT NEW MAP(a.userProdId AS userProdId,"
					+ "a.uprodName AS uprodName," 
					+ "a.uprodType AS uprodType,"
					+ "a.uprodDesc AS uprodDesc,"
					+ "a.uprodVersion AS uprodVersion,"
					+ "DATE_FORMAT(a.uprodOnlineTime ,'%Y-%m-%d %T') AS uprodOnlineTime,"
					+ "a.uprodStatus AS uprodStatus,"
					+ "a.uprodShortCode AS uprodShortCode,"
					+ "a.nodeId AS nodeId," + "a.uprodNorms AS uprodNorms"
					+ ") FROM TUserProducts a WHERE a.nodeId in(" + nodeIds + ")";
		} else {
			hql = " FROM TUserProducts a WHERE 1=1 ";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY a.userProdId ASC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}
	/**
	 * 产品基础数据查询
	 * @param partTypeId
	 * @param partClassNo
	 * @param batchNumber
	 * @param onlineStartTime
	 * @param onlineEndTime
	 * @param offlineStartTime
	 * @param offlineEndTime
	 * @param productSerial
	 * @return
	 */
	public List<TPartBasicInfoModel> getProductBasicList(String partTypeId,String partClassNo,String batchNumber,String onlineStartTime,String onlineEndTime,
			String offlineStartTime,String offlineEndTime,String productSerial){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<TPartBasicInfoModel> rl=new ArrayList<TPartBasicInfoModel>();
		String hql="select new Map("
				+ " a.id as id,"
				+ " a.no as no,"
				+ " b.name as name,"
				+ " a.batchNo as batchNo,"
				+ " a.onlineDate as onlineDate,"
				+ " a.offlineDate as offlineDate)"
				+ " FROM TPartBasicInfo a,TPartTypeInfo b"
				+ " where a.partTypeId=b.id ";
		if(null!=partTypeId&&!"".equals(partTypeId)){
			hql=hql+" and b.id='"+partTypeId+"' ";
		}else{
			if(null!=partClassNo&&!"".equals(partClassNo)){
				List<TPartTypeInfo> partType=partService.getTPartTypeInfoByTypeNo(partClassNo);
				String instr="";
				for(TPartTypeInfo tp:partType){
					instr=instr+",'"+tp.getId()+"'";
				}
				instr=instr.substring(1,instr.length());
				hql=hql+" and b.id in("+instr+") ";
			}
			
		}
		if(null!=onlineStartTime&&!"".equals(onlineStartTime)){
			hql=hql+" and  DATE_FORMAT(a.onlineDate,'%Y-%m-%d %H:%i:%s')>='"+onlineStartTime+"' ";
		}
		if(null!=onlineEndTime&&!"".equals(onlineEndTime)){
			hql=hql+" and  DATE_FORMAT(a.onlineDate,'%Y-%m-%d %H:%i:%s')<='"+onlineEndTime+"' ";
		}
		if(null!=offlineStartTime&&!"".equals(offlineStartTime)){
			hql=hql+" and  DATE_FORMAT(a.offlineDate,'%Y-%m-%d %H:%i:%s')>='"+offlineStartTime+"' ";
		}
		if(null!=offlineEndTime&&!"".equals(offlineEndTime)){
			hql=hql+" and  DATE_FORMAT(a.offlineDate,'%Y-%m-%d %H:%i:%s')<='"+offlineEndTime+"' ";
		}
		if(null!=batchNumber&&!"".equals(batchNumber)){
			hql=hql+" and a.batchNo='"+batchNumber+"'";
		}
		if(null!=productSerial&&!"".equals(productSerial)){
			hql=hql+" and a.no='"+productSerial+"'";
		}
		
		List<Map<String,Object>> rs=dao.executeQuery(hql, parameters);
		if(null!=rs&&rs.size()>0){
			for(Map<String,Object> map:rs){
				TPartBasicInfoModel tpb=new TPartBasicInfoModel();
				tpb.setNo(null==map.get("no")?"":map.get("no").toString());
				tpb.setPartName(null==map.get("name")?"":map.get("name").toString());
				tpb.setBatchNo(null==map.get("batchNo")?"":map.get("batchNo").toString());
				tpb.setOnlineDate(null==map.get("onlineDate")?"":map.get("onlineDate").toString());
				tpb.setOfflineDate(null==map.get("offlineDate")?"":map.get("offlineDate").toString());
				tpb.setId(null==map.get("id")?Long.parseLong(""):Long.parseLong(map.get("id").toString()));
				rl.add(tpb);
			}
		}
		return rl;
	}
	/**
	 * 产品过程数据查询
	 * @param productSerial
	 * @return
	 */
	public List<ProductProcessModel> getProductProcessList(String productSerial){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<ProductProcessModel> rl=new ArrayList<ProductProcessModel>();
		if(null!=productSerial&&!"".equals(productSerial)){
			String hql="select new Map("
					+ " b.id as processId,"
					+ " d.no as basicNo,"
					+ " a.id as id,"
					+ " b.no as processNo,"
					+ " b.name as processName,"
					+ " a.dispatchNo as dispatchNo,"
					+ " a.operator as operator,"
					+ " c.starttime as starttime,"
					+ " c.finishtime as finishtime,"
					+ " a.status as status)"
					+ " from TPartEventInfo a,TProcessInfo b,TUserEquWorkEvents c,TPartBasicInfo d "
					+ " where a.partId=d.id and a.processID=b.id and a.eventId=c.id"
					+ " and d.no='"+productSerial+"'";
			List<Map<String,Object>> rs=dao.executeQuery(hql, parameters);
			if(rs!=null&&rs.size()>0){
				for(Map<String,Object> map:rs){
					ProductProcessModel ppm=new ProductProcessModel();
					ppm.setId(null==map.get("id")?"":map.get("id").toString());
					ppm.setProcessNo(null==map.get("processNo")?"":map.get("processNo").toString());
					ppm.setProcessName(null==map.get("processName")?"":map.get("processName").toString());
					ppm.setDispatchNo(null==map.get("dispatchNo")?"":map.get("dispatchNo").toString());
					ppm.setOperator(null==map.get("operator")?"":map.get("operator").toString());
					ppm.setStarttime(null==map.get("starttime")?"":map.get("starttime").toString().substring(0, map.get("starttime").toString().length()-2));
					ppm.setFinishtime(null==map.get("finishtime")?"":map.get("finishtime").toString().substring(0, map.get("finishtime").toString().length()-2));
					ppm.setStatus(null==map.get("status")?"":map.get("status").toString());
					ppm.setBasicNo(null==map.get("basicNo")?"":map.get("basicNo").toString());
					ppm.setProcessId(null==map.get("processId")?"":map.get("processId").toString());
					rl.add(ppm);
				}
			}
		}
		return rl;
	}
	/**
	 * 过程质量数据查询
	 * @param selectPartType
	 * @param productSerial
	 * @param selectProcess
	 * @param onStartTime
	 * @param onEndTime
	 * @return
	 */
	public List<ProcessQualityModel> getProcessQualityList(String selectPartType,String productSerial,String selectProcess,
			String onStartTime,String onEndTime){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<ProcessQualityModel> rl=new ArrayList<ProcessQualityModel>();
		String sql="select d.name as processName,"
				+ " b.dispatchNo as dispatchNo, "
				+ " f.checkType as checkType," 
				+ " e.operator as operator, "
				+ " f.qualityName as checkParam, "
				+ " f.standardValue as standardValue,"
				+ " f.maxValue as maxValue, "
				+ " e.realValue as realValue, "
				+ " f.min_tolerance as minTolerance, "
				+ " f.max_tolerance as maxTolerance, "
				+ " f.checkTime as checkTime, "
				+ " e.result as result  "
				+ " from t_part_basic_info a inner join r_PART_EVENT_INFO b on a.id=b.partID"
				+ " inner join r_part_quality_param e on (b.processID=e.processID and a.ID=e.partID)"
				+ " inner join t_process_info d on d.id=e.processID"
				+ " inner join t_quality_param f on e.qualityID=f.ID"
				+ " where 1=1 ";
		if(null!=productSerial&&!"".equals(productSerial)){
			sql=sql+" and a.no='"+productSerial+"'";
		}
		if(null!=selectProcess&&!"".equals(selectProcess)){//如果工序不为空
			sql=sql+" and d.id='"+selectProcess+"'";
		} 
		if(null!=onStartTime&&!"".equals(onStartTime)){
			sql=sql+" and  DATE_FORMAT(a.online_date,'%Y-%m-%d %H:%i:%s')>='"+onStartTime+"'";
		}
		if(null!=onEndTime&&!"".equals(onEndTime)){
			sql=sql+" and  DATE_FORMAT(a.online_date,'%Y-%m-%d %H:%i:%s')<='"+onEndTime+"'";
		}
		List<Map<String,Object>> result=dao.executeNativeQuery(sql, parameters);
		for(Map<String,Object> mm:result){
			ProcessQualityModel pqm=new ProcessQualityModel();
			pqm.setProcessName(null==mm.get("processName")?"":mm.get("processName").toString());
			pqm.setDispatchNo(null==mm.get("dispatchNo")?"":mm.get("dispatchNo").toString());
			pqm.setCheckType(null==mm.get("checkType")?"":mm.get("checkType").toString());
			pqm.setOperator(null==mm.get("operator")?"":mm.get("operator").toString());
			pqm.setCheckParam(null==mm.get("checkParam")?"":mm.get("checkParam").toString());
			pqm.setStandardValue(null==mm.get("standardValue")?"":mm.get("standardValue").toString());
			pqm.setMaxValue(null==mm.get("maxValue")?"":mm.get("maxValue").toString());
			pqm.setRealValue(null==mm.get("realValue")?"":mm.get("realValue").toString());
			pqm.setMaxtolerance(null==mm.get("maxTolerance")?"":mm.get("maxTolerance").toString());
			pqm.setMintolerance(null==mm.get("minTolerance")?"":mm.get("minTolerance").toString());
			pqm.setCheckTime(null==mm.get("checkTime")?"":mm.get("checkTime").toString());
			pqm.setResult(null==mm.get("result")?"":mm.get("result").toString());
			rl.add(pqm);
		}
		return rl;
	}
	/**
	 * 根据零件类型查询出零件产品
	 * @param partTypeId
	 * @return
	 */
  public List<TPartBasicInfo> getTPartBasicInfoByPartTypeId(String partTypeId){
	  Collection<Parameter> parameters = new HashSet<Parameter>();
	  String hql="FROM TPartBasicInfo where partTypeId='"+partTypeId+"'";
	  return dao.executeQuery(hql, parameters);
  }
  /**
   * 根据零件产品查询工序
   * @param partBasicId
   * @return
   */
  public List<Map<String,Object>> getTProcessInfoByPartBasicId(String partBasicId){
	  Collection<Parameter> parameters = new HashSet<Parameter>();
	  String hql="select distinct new Map("
	  		+ "  c.id as id,"
	  		+ " c.name as name"
	  		+ " )"
	  		+ " FROM TPartBasicInfo a,TPartEventInfo b, TProcessInfo c"
	  		+ " where a.id=b.partId and b.processID=c.id "
	  		+ " and a.id='"+partBasicId+"'";
	return dao.executeQuery(hql, parameters);  
  }
  /**
   * 根据节点id 查询零件类型
   * @param nodeId
   * @return
   */
  public List<TPartTypeInfo> getTPartTypeInfoByNodeId(String nodeId,String procuct){
	  String hql="from TPartTypeInfo where nodeid='"+nodeId+"'";
	  if(!StringUtils.isEmpty(procuct)){
		  hql=hql+" and id='"+procuct+"'";
	  }
	  return dao.executeQuery(hql);
  }
  public List<TPartTypeInfo> getTPartTypeInfoByName(String name){
	  String hql="from TPartTypeInfo where name='"+name+"'";
	  if(!StringUtils.isEmpty(name)){
		  hql=hql+" and name='"+name+"'";
	  }
	  return dao.executeQuery(hql);
  }
  /**
   * 根据节点id 返回在制数综合查询结果
   * @param nodeId
   * @return
   */
  public TreeNode getProductInProgress(String startTime,String endTime,String procuct,String batchNo,String nodeId){
	  TreeNode root =new DefaultTreeNode(new productInProgressModel("", "", "", "", "", "", "", "", "","",""),null);
	  List<TPartTypeInfo> result=this.getTPartTypeInfoByNodeId(nodeId,procuct);
	  for(TPartTypeInfo tpi:result){
		  
		  String hql2="select "
		  		    + " dispatch.batchNo as batchNo,"
		  		    + " dispatch.taskNum as orderNo,"//订单号  需修改
		  		    + " dispatch.plan_starttime as planStarttime,"
		  		    + " dispatch.plan_endtime as planEndtime,"
		  		    + " ' ' as drawingNo,"
		  		    + " ' ' as filename,"
		  		    + " dispatch.processNum as planNum," //计划数量
		  		    + " dispatch.onlineNumber as onlineNumber,"
		  		    + " (case when (process.offlineProcess = 1 and process.onlineProcessId is null) then '1' "
				    + "		when process.offlineProcess = 1 then '2' "
				    + "		when process.onlineProcessId is null then '3' else '4' end) as flag,"
		  		    + " dispatch.finishNum as offlineNumber "//下线数量  
		  		    + " from t_jobdispatchlist_info dispatch," +
		  		    "        t_process_info process," +
		  		    "        t_processplan_info processplan," +
		  		    "        t_part_type_info part "
		  		    + " where dispatch.processID=process.ID " 
		  		    + " and process.processPlanID = processplan.id"
					+ " and processplan.parttypeID = part.id "
		  		    + " and part.id="+tpi.getId()
		  		    + " and date_format(dispatch.plan_starttime,'%Y-%m-%d')>='"+startTime+"'"
		  		    + " and date_format(dispatch.plan_starttime,'%Y-%m-%d')<='"+endTime+"'";
		  if(null!=batchNo&&!"".equals(batchNo)){
			  hql2=hql2+" and dispatch.taskNum='"+batchNo+"'";
		  }
		       hql2+=" order by dispatch.batchNo,flag asc";
		       
		  List<Map<String,Object>> result2=dao.executeNativeQuery(hql2);
		  List<Map<String,Object>> returnRes = new ArrayList<Map<String,Object>>();
		  
		  int planNum=0;
		  int onlineNum=0;
		  int pipNumber=0;
		  int offlineNumber=0;
		  for(int i=0;i<result2.size();i++){
				Map<String,Object> map1 = result2.get(i);
				if(map1.get("flag").toString().equals("4"))
					continue;
				Map<String,Object> tempMap = new HashMap<String,Object>();
				if(map1.get("flag").toString().equals("1")){
					tempMap.put("batchNo", map1.get("batchNo"));
					tempMap.put("orderNo", map1.get("orderNo"));
					tempMap.put("planStarttime", map1.get("planStarttime"));
					tempMap.put("planEndtime", map1.get("planEndtime"));
					tempMap.put("drawingNo", map1.get("drawingNo"));
					tempMap.put("filename", map1.get("filename"));
					tempMap.put("planNum", map1.get("planNum"));
					tempMap.put("onlineNumber", map1.get("onlineNumber"));
					tempMap.put("offlineNumber", map1.get("offlineNumber"));
					tempMap.put("pipNumber", Integer.parseInt(map1.get("offlineNumber").toString())-Integer.parseInt(map1.get("offlineNumber").toString()));
					returnRes.add(tempMap);
				}
				if(map1.get("flag").toString().equals("2")){
					if(i<result2.size()-1){
						Map<String,Object> map2 = result2.get(i+1);
						if(map2.get("flag").toString().equals("3")){
							tempMap.put("batchNo", map1.get("batchNo"));
							tempMap.put("orderNo", map1.get("orderNo"));
							tempMap.put("planStarttime", map1.get("planStarttime"));
							tempMap.put("planEndtime", map1.get("planEndtime"));
							tempMap.put("drawingNo", map1.get("drawingNo"));
							tempMap.put("filename", map1.get("filename"));
							tempMap.put("planNum", map1.get("planNum"));
							tempMap.put("onlineNumber", map1.get("onlineNumber"));
							tempMap.put("offlineNumber", map1.get("offlineNumber"));
							tempMap.put("pipNumber", Integer.parseInt(map2.get("offlineNumber").toString())-Integer.parseInt(map1.get("offlineNumber").toString()));
							returnRes.add(tempMap);
							i++;
						}
					}
				}
				else if(map1.get("flag").toString().equals("3")){
					tempMap.put("pipNumber", Integer.parseInt(map1.get("offlineNumber").toString())-Integer.parseInt(map1.get("offlineNumber").toString()));
					tempMap.put("batchNo", map1.get("batchNo"));
					tempMap.put("orderNo", map1.get("orderNo"));
					tempMap.put("planStarttime", map1.get("planStarttime"));
					tempMap.put("planEndtime", map1.get("planEndtime"));
					tempMap.put("drawingNo", map1.get("drawingNo"));
					tempMap.put("filename", map1.get("filename"));
					tempMap.put("planNum", map1.get("planNum"));
					tempMap.put("onlineNumber", map1.get("onlineNumber"));
					tempMap.put("offlineNumber", map1.get("offlineNumber"));
					returnRes.add(tempMap);
				}
			}
		
		  for(Map<String,Object> map:returnRes){
			  planNum += Integer.parseInt(map.get("planNum").toString());
			  onlineNum += Integer.parseInt(map.get("onlineNumber").toString());
			  pipNumber += Integer.parseInt(map.get("pipNumber").toString());
			  offlineNumber += Integer.parseInt(map.get("offlineNumber").toString());
		  }
		  TreeNode root2 =new DefaultTreeNode(new productInProgressModel(tpi.getName(), "", "", "", "", Integer.toString(planNum), Integer.toString(onlineNum), Integer.toString(pipNumber), Integer.toString(offlineNumber),"",""),root);
		  for(Map<String,Object> map:returnRes){
			  TreeNode root21 =new DefaultTreeNode(new productInProgressModel("",map.get("batchNo")+"",map.get("orderNo")+"", 
					  map.get("drawingNo")+"", map.get("filename")+"", map.get("planNum")+"", map.get("onlineNumber")+"", 
					  map.get("pipNumber")+"", map.get("offlineNumber")+"",DateUtil.formatTime(map.get("planStarttime")+"", "yyyy-MM-dd"),DateUtil.formatTime(map.get("planEndtime")+"", "yyyy-MM-dd")),root2);
		  }
	  }
	  return root;
  }
  
  /**
   * 获取工单列表的节点树,设备为节点头
   */
  @Override
  public TreeNode getJobListTree(String nodeId,String startTime,String endTime,String taskNum,String jobstatus, String partid,String equid) {
	  TreeNode root =new DefaultTreeNode(new JobListModel("","","","", "", "", "", "", "", "", "","","","","",""),null);
	  List<Map<String, Object>> list = getJobInfo(nodeId, startTime, endTime, taskNum, jobstatus,  partid, equid);
	  String equSerialNo = "";
	  TreeNode root2 = null;
	  for(Map<String, Object> map : list){
		  if(!map.get("equSerialNo").toString().equals(equSerialNo)){
			  root2 = new DefaultTreeNode(new JobListModel("","","","", "", "", "", "", map.get("equSerialNo").toString(), "", "","","","","",""),root);
		  }
		  equSerialNo = map.get("equSerialNo").toString();
		  String wisScrapNum=null;
		  if(map.get("wisScrapNum")!=null && map.get("wisScrapNum").toString()!=""){
				wisScrapNum =map.get("wisScrapNum").toString();
		  }
		  TreeNode root21 = new DefaultTreeNode(new JobListModel(
				  map.get("Id").toString(),
				  map.get("Status").toString(),
				  map.get("taskNum").toString(), 
				  map.get("processName").toString(), 
				  map.get("partNo").toString(), 
				  map.get("planNum").toString(), 
				  map.get("onlineNumber").toString(), 
				  map.get("dispatchStatus").toString(),"",
				  DateUtil.formatTime(map.get("startTime").toString(),"yyyy-MM-dd"),
				  DateUtil.formatTime(map.get("endTime").toString(),"yyyy-MM-dd"), 
				  map.get("finishNum").toString(),
				  map.get("partName").toString(),
				  map.get("No").toString(),
				  map.get("goodQuantity").toString(),wisScrapNum), root2);
	  }
	  return root;
  }
  
  private List<Map<String, Object>> getJobInfo(String nodeId,String startTime,String endTime,String taskNum,String jobstatus, String partid,String equid) {
		String hql = "select new Map("
//			+ " equ.equSerialNo as equSerialNo,"
			+ " te.equSerialNo as equSerialNo,"
			+ "	b.TProcessInfo.id as processID,"//工序id
			+ " part.no as partNo,"//零件号
			+ " b.no as No,"
			+ " b.taskNum as taskNum,"
			+ " b.id as Id,"//工单id
			+ " b.status as Status,"
			+ " b.processNum as planNum,"// 计划数量
			+ " b.onlineNumber as onlineNumber,"//上线数量
			+ " b.finishNum as finishNum,"//完成数量
			+ " t.name as dispatchStatus,"//工单状态
			+ " b.planStarttime as startTime,"//计划开始时间
			+ " b.planEndtime as endTime,"//计划结束时间
			+ " b.realEndtime as dispatchEndTime,"
			+ "	part.name as partName,"
			+ "	process.name as processName,"
			+ " b.goodQuantity as goodQuantity,b.wisScrapNum as wisScrapNum)"//工单停止时间
			+ " from TJobdispatchlistInfo b,"
			+ " TStatusInfo t,"
			+ " TEquJobDispatch te,"
			+ " TPartTypeInfo part,"
			+ " TProcessInfo process, "
			+ " TProcessplanInfo plan"
//			+ " TEquipmentInfo equ"
			+ " where b.nodeid='"+nodeId+"'"
			+ " and b.status=t.id"
			+ " and b.no = te.jobdispatchNo"
//			+ "	and te.equSerialNo = equ.equSerialNo"
			+ " and te.status <> 0"
			+ " and b.TProcessInfo.id = process.id"
			+ " and process.TProcessplanInfo.id = plan.id"
			+ " and plan.TPartTypeInfo.id = part.id";
		if(!StringUtils.isEmpty(taskNum)){
			hql += " and b.taskNum='"+taskNum+"'";
		}
		if(!StringUtils.isEmpty(jobstatus)){
			hql += " and b.status in("+jobstatus+")";
		}
		if(!StringUtils.isEmpty(partid)){
			hql += " and part.id in ("+partid+")";
		}
		if(!StringUtils.isEmpty(equid)){
			hql += " and equ.equId in ("+equid+")";
		}
		//时间查询条件
		if(!StringUtils.isEmpty(startTime)){
			hql += " and b.planStarttime >= DATE_FORMAT('"+startTime+"','%Y-%m-%d')  "; 
		}
		if(!StringUtils.isEmpty(endTime)){
			hql += " and b.planEndtime <= DATE_FORMAT('"+endTime+"','%Y-%m-%d')"; 
		}
		hql += " order by te.equSerialNo,b.taskNum,process.processOrder asc";
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> jobDispatchListInfo = dao.executeQuery(hql);
		return jobDispatchListInfo;
}
  
  @SuppressWarnings("unchecked")
private List<TEquipmentInfo> getTEquipmentInfo(String nodeId){
	  String hql = "from TEquipmentInfo t WHERE t.TNodes.TNodes.id='"+nodeId+"')";		
	  return dao.executeQuery(hql);
  }
  
  @SuppressWarnings("unchecked")
private List<TEquJobDispatch> getTEquJobDispatchByNo(String jobdispatchNo){
	  String hql = "from TEquJobDispatch t where t.jobdispatchNo='"+jobdispatchNo+"' and t.status <> 0";
	  return dao.executeQuery(hql);
  }
  
	@Override
	public TreeNode getBaseJobListTree(String nodeId,String startTime,String endTime,String taskNum,String jobstatus, String partid,String equid) {
		TreeNode root =new DefaultTreeNode(new JobListModel("","","", "", "", "", "", "", "", "","", "","","","",""),null);
		//通过参数查询工单信息
		List<Map<String, Object>> list = this.getJobdispatchInfo(nodeId,startTime,endTime,taskNum, jobstatus, partid, equid);
		String tasknum = "";
		TreeNode root2 = null;
		for(Map<String,Object> map : list){
			if(!map.get("taskNum").toString().equals(tasknum)){
				root2 = new DefaultTreeNode(new JobListModel("","",map.get("taskNum").toString(), "", "", "", "", "", "", "","","","","","",""),root);
			}
			tasknum = map.get("taskNum").toString();
			String wisScrapNum=null;
			if(map.get("wisScrapNum")!=null && map.get("wisScrapNum").toString()!=""){
				wisScrapNum =map.get("wisScrapNum").toString();
			}
			TreeNode root21 = new DefaultTreeNode(
					new JobListModel(map.get("Id").toString(),
						map.get("StatusNum").toString(),
						map.get("partName").toString(),//零件名称
						map.get("processno").toString()+"/"+map.get("name").toString(), 
						map.get("No").toString().substring(22),
						map.get("planNum").toString(), 
						map.get("onlineNumber").toString(), 
						map.get("status").toString(), 
						map.get("equSerialNo").toString(),
						DateUtil.formatTime(map.get("startTime").toString(), "yyyy-MM-dd"),
						DateUtil.formatTime(map.get("endTime").toString(),"yyyy-MM-dd"), 
						map.get("finishNum").toString(),
						map.get("partName").toString(),
						map.get("No").toString(),
						map.get("goodQuantity").toString(),wisScrapNum), root2);
		}
		
		return root;
	}
	/**
	 * 通过参数查询工单工单信息
	 * @param nodeId 
	 * @param startTime 工单开始时间
	 * @param endTime 工单结束时间
	 * @param taskNum 任务编号
	 * @param jobstatus 工单状态
	 * @param partid 零件名称对应的零件id
	 * @param equid 设备对应的id
	 * @return
	 */
	private List<Map<String, Object>> getJobdispatchInfo(String nodeId,
			String startTime, String endTime, String taskNum, String jobstatus,
			String partid, String equid) {
		String hql = "select new Map("
				   + " t.taskNum as taskNum,"//任务号
				   + " t.no as No,"// 工单号
				   + " t.id as Id,"//工单id
				   + " t.status as StatusNum,"
				   + " t.onlineNumber as onlineNumber,"// 上线数量
				   + " t.processNum as planNum,"// 计划数量
				   + " t.finishNum as finishNum,"//完成数量
				   + " ts.name as status,"// 工单状态
				   + " tp.name as name,"//工序名称
				   + " tp.no as processno,"
				   + " part.name as partName,"
				   + " part.no as partno,"
			   	   + " te.equSerialNo as equSerialNo,"//设备序列号
				   + " t.planStarttime as startTime,"// 计划开始时间
				   + " t.planEndtime as endTime,"// 计划结束时间
				   + " t.realEndtime as dispatchEndTime,"
				   + " t.goodQuantity as goodQuantity,"// 工单停止时间
				   + " t.wisScrapNum as wisScrapNum"//报废数量
				   + " ) from TJobdispatchlistInfo t,"
				   + " TProcessInfo tp,"
				   + " TStatusInfo ts,"
				   + " TEquJobDispatch te,"
				   + " TPartTypeInfo part,"
				   + " TProcessplanInfo plan"
//				   + " TEquipmentInfo equ"
				   + " where t.nodeid = '"+nodeId+"'"
				   + " and ts.id = t.status"
				   + " and t.TProcessInfo.id = tp.id"
				   + " and tp.TProcessplanInfo.id = plan.id"
				   + " and plan.TPartTypeInfo.id = part.id"
				   + " and t.no = te.jobdispatchNo"
//				   + " and te.equSerialNo = equ.equSerialNo"
				   + " and te.status <> 0";
		//时间查询条件
		if(!StringUtils.isEmpty(startTime)){
			hql += " and t.planStarttime >= DATE_FORMAT('"+startTime+"','%Y-%m-%d')  "; 
		}
		if(!StringUtils.isEmpty(endTime)){
			hql += " and t.planEndtime <= DATE_FORMAT('"+endTime+"','%Y-%m-%d')"; 
		}
		//工单状态
		if(!StringUtils.isEmpty(jobstatus)){
			hql += " and t.status in("+jobstatus+")";
		}
		//零件
		if(!StringUtils.isEmpty(partid)){
			hql += " and part.id in ("+partid+")";
		}
		//设备
		if(!StringUtils.isEmpty(equid)){
			hql += " and equ.equId in ("+equid+")";
		}
		//任务编号
		if(!StringUtils.isEmpty(taskNum)){
			String[] strVar = taskNum.split(",");
			StringBuffer sb = new StringBuffer();
			for(String str : strVar){
				sb.append("'").append(str).append("'").append(",");
			}
			String query = sb.toString().substring(0, sb.length()-1);
			hql += " and t.taskNum in ("+query+")";
		}
		hql += " order BY t.taskNum,tp.processOrder asc";
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> jobDispatchListInfo = dao.executeQuery(hql);
		return jobDispatchListInfo;
	}
	
	/**
	 * 通过id查询t_process_info中的信息
	 * @param processID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<TProcessInfo> getTProcessInfoById(String processID){
		String hql = "from TProcessInfo t where t.id="+Integer.parseInt(processID);
		return dao.executeQuery(hql);
	}
  
  /**
   * 根据零件id 获取批次号
   * @param partId
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Map<String,Object>> getbatchNo(String partId){
	  String hql="select distinct new Map("
	  		  + "  b.taskNum as batchNo)"
	  		  + " from TJobdispatchlistInfo b"
	  		  + " where b.TProcessInfo.TProcessplanInfo.TPartTypeInfo.id='"+partId+"'";
	  return dao.executeQuery(hql);
  }
  
  /**
   * 根据节点id 返回在制数综合查询结果(工序统计)
   * @param nodeId
   * @return
   */
  public List<productInProgressModel> getProcessstaticesById(int row,int pageSize,String startTime,String endTime,String procuct,String batchNo,String nodeId){
	  List<productInProgressModel> root = new ArrayList<productInProgressModel>();
	  
	  String hql2="select new Map("
	  		   + " t2.TProcessInfo.name as processName,"
	  		   + " t2.no as dispatchNo, "
		  	   + " t2.onlineNumber as onlineNumber,"
		  	   + " t2.finishNum as finishNum,"
		  	   + " (t2.onlineNumber-t2.finishNum) as pipNumber, "
		  	   + " t2.goodQuantity/t2.finishNum*100 as goodQuantity, "
		  	   + " date_format(t2.realStarttime,'%Y-%m-%d %H:%i:%s') as realStarttime,"
		  	   + " t2.TProcessInfo.onlineProcessId as onlineProcessId)"
	  		   + " from TJobdispatchlistInfo t2"
	  		   + " where  date_format(t2.planStarttime,'%Y-%m-%d')>='"+startTime+"'"
	  		   + " and  date_format(t2.planStarttime,'%Y-%m-%d')<='"+endTime+"'"
	  		   + " and t2.nodeid='"+nodeId+"'";
	  if(null!=procuct&&!"".equals(procuct)){
		  hql2=hql2+" and t2.TProcessInfo.TProcessplanInfo.TPartTypeInfo.id='"+procuct+"'";
	  }
	  if(null!=batchNo&&!"".equals(batchNo)){
		  hql2=hql2+" and t2.batchNo='"+batchNo+"'";
	  }
	  List<Map<String, Object>> list = dao.executeQuery(hql2);
	  
	  for(Map<String,Object> map:list){
		  if(null!=map.get("onlineProcessId")&&!"".equals(map.get("onlineProcessId")+"")){
			  String hql="from TJobdispatchlistInfo t  "
			  		+ " where t.TProcessInfo.id='"+map.get("onlineProcessId")+"'"
			  		+ " and t.TProcessInfo.status=0";
			  List<TJobdispatchlistInfo> tilist=dao.executeQuery(hql);
			  if(null!=tilist&&tilist.size()>0){
				  TJobdispatchlistInfo tji=tilist.get(0);
				  String pipNumber=Integer.parseInt(tji.getFinishNum()+"")-Integer.parseInt(map.get("onlineNumber")+"")+"";
				  root.add(new productInProgressModel(map.get("processName")+"",map.get("dispatchNo")+"", 
						  map.get("onlineNumber")+"", map.get("finishNum")+"",pipNumber, map.get("goodQuantity")+"", 
						  map.get("realStarttime")+"","","",""));
			  }
		  }else{
			  root.add(new productInProgressModel(map.get("processName")+"",map.get("dispatchNo")+"", 
					  map.get("onlineNumber")+"", map.get("finishNum")+"", map.get("pipNumber")+"", map.get("goodQuantity")+"", 
					  map.get("realStarttime")+"","","",""));
		  }
		 
	  }
	  return root;
  	}

  /**
   * 获取工单提示信息
   * @return
   */
  public String getJobdispatchTS(){
	  String hql="select new Map("
	  		  + " a.no as no,"
	  		  + " a.name as name,"
	  		  + " a.id as id)"
	  		  + " from TJobdispatchlistInfo a "
	  		  + " where a.status=50"
	  		  + " and (a.finishNum-a.processNum)>=0";
	  List<String> list=new ArrayList<String>();
	  List<Map<String,Object>> rs=dao.executeQuery(hql);
	  if(null!=rs&&rs.size()>0){
		  for(Map<String,Object> map:rs){
			  String no=map.get("no")+"";
			  String name=map.get("name")+"";
			  String id=map.get("id")+"";
			  list.add("no:"+no+"|name:"+name+"|id:"+id);
		  }
	  }
	  return list.toString();
  }

}

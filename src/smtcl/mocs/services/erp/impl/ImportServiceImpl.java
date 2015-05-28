package smtcl.mocs.services.erp.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.Sort;

import smtcl.mocs.dao.device.ICommonDao;
import smtcl.mocs.model.erp.JobPlanImportInfo;
import smtcl.mocs.pojos.device.TUserEquWorkEvents;
import smtcl.mocs.pojos.erp.WisQaCheck;
import smtcl.mocs.pojos.erp.WisQaCheckId;
import smtcl.mocs.pojos.erp.WisTransfer;
import smtcl.mocs.pojos.erp.WisTransferId;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProcessplanInfo;
import smtcl.mocs.pojos.job.TProductionEvents;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.erp.IImportService;
import smtcl.mocs.utils.authority.DateUtil;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 任务批次导入功能service接口实现
 * @author songkaiang
 *
 */
public class ImportServiceImpl extends GenericServiceSpringImpl<Object,String> implements IImportService {
	
	/**
	 * erp表的commonDao操作
	 */
	private ICommonDao erpCommonDao;
	
	/**
	 * a3数据表的commonDao操作
	 */
	private ICommonDao commonDao;
	private ICommonService commonService;

    private ICommonService remoteCommonService;

    public ICommonService getRemoteCommonService() {
        return remoteCommonService;
    }

    public void setRemoteCommonService(ICommonService remoteCommonService) {
        this.remoteCommonService = remoteCommonService;
    }

    public ICommonDao getErpCommonDao() {
		return erpCommonDao;
	}

	public void setErpCommonDao(ICommonDao erpCommonDao) {
		this.erpCommonDao = erpCommonDao;
	}
	
	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JobPlanImportInfo> getJobPlanImportInfo(Date startTime,
			Date endTime, int flag) {
		String sql = "select "
						+" entity.entity_name as \"jobno\","//任务号
						+" entity.entity_name as \"jobname\","
						+" entity.entity_class as \"entityClass\","//批次分类
						+" entity.entity_type as \"entityType\","//批次类型
						+" entity.quantity as \"quantity\","//投料数量
						+" item.item_code as \"partNo\","//零件编号
						+" item.item_desc as \"partName\","//零件名称
						+" entity.item_desc as \"itemDesc\","
						+" to_char(entity.start_date,'yyyy-mm-dd HH24:mi:ss') as \"startTime\","//开始时间
						+" to_char(entity.completion_date,'yyyy-mm-dd HH24:mi:ss') as \"endTime\","//结束时间
						+" to_char(entity.release_date,'yyyy-mm-dd HH24:mi:ss') as \"releaseDate\","//发放时间
						+" entity.inventory_code as \"inventoryCode\","//完工自库存
					    +" entity.flag as \"flag\","//处理标志
						+" to_char(entity.upload_date,'yyyy-mm-dd HH24:mi:ss') as \"uploadDate\" "//上传时间
					+" from" 
						+" wis_entity entity,"
						+" wis_item item "
					+" where" 
						+" entity.assembly_item_id = item.item_id"
						+" and entity.flag = "+flag
						+" and to_char(entity.start_date,'yyyy-mm-dd HH24:mi:ss') >='"+StringUtils.formatDate(startTime, 3)+"'"
						+" and to_char(entity.start_date,'yyyy-mm-dd HH24:mi:ss') < '"+StringUtils.formatDate(endTime, 3)+"'"
						+" order by entity.entity_name,entity.upload_date desc";
		List<Map<String,Object>> listMap = erpCommonDao.executeNativeQuery(sql);
		List<JobPlanImportInfo> list = new ArrayList<JobPlanImportInfo>();
		String jobno = "";
		for(Map<String,Object> map : listMap){
			//1,相同id的数据只查询时间uploadDate时间最新的
			//2,当查询未导入数据时，才会对erp中间表进行更改
			if(jobno.equals(map.get("jobno").toString()) && flag == 0){
				//将数据库中的flag数据更改为1
				this.updateWISEntity(map.get("jobno").toString(),map.get("uploadDate").toString(), 1);
				continue;
			}
			jobno = map.get("jobno").toString();
			
			JobPlanImportInfo jp = new JobPlanImportInfo();
			jp.setJobno(map.get("jobno").toString());//任务号
			jp.setJobname(map.get("jobname").toString());//任务名称
			jp.setQuantity(map.get("quantity").toString());//投料数量
			jp.setPartNo(map.get("partNo").toString());//零件编号
			jp.setPartName(map.get("partName").toString());//零件名称
			jp.setStartTime(map.get("startTime").toString());//开始时间
			jp.setEndTime(map.get("endTime").toString());//结束时间
			jp.setReleaseDate(map.get("releaseDate").toString());//发布时间
			if(flag==0){
				jp.setFlag("未导入");
			}else if(flag==1){
				jp.setFlag("已过滤");
			}else if(flag==2){
				jp.setFlag("已导入");
			}
			jp.setUploadDate(map.get("uploadDate").toString());//更新时间
			
			list.add(jp);
		}
		return list;
	}

	@Override
	public boolean updateWISEntity(String jobno,String uploaddate, int flag) {
		String sql = "update wis_entity entity"
				+ " set entity.flag = "+flag
				+ " where entity.entity_name = '"+ jobno +"'"
				+ " and to_char(entity.upload_date,'yyyy-mm-dd HH:mi:ss') = '"+DateUtil.formatTime(uploaddate, "yyyy-MM-dd hh:mm:ss")+"'";
		erpCommonDao.executeNativeUpdate(sql);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean saveInfoToPartType(JobPlanImportInfo jp, String nodeid) {
		TJobplanInfo pt = new TJobplanInfo();
		pt.setNo(jp.getJobno());
		pt.setName(jp.getJobname());
		pt.setPlanNum(Integer.parseInt(jp.getQuantity()));
		pt.setFinishNum(0);
		pt.setPlanStarttime(StringUtils.convertDate(jp.getStartTime(), "yyyy-MM-dd hh:mm:ss"));
		pt.setPlanEndtime(StringUtils.convertDate(jp.getEndTime(), "yyyy-MM-dd hh:mm:ss"));
		pt.setPriority(1);
		pt.setStatus(10);
		//1，通过零件名和零件号，查询WIS数据库中是否包含零件信息
		//2,不包含零件信息，直接返回，不做保存操作
		List<TPartTypeInfo> list = this.getPartTypeInfo(jp.getPartNo(), jp.getPartName());
		if(list.size()<=0)
			return false;
		for(TPartTypeInfo t : list){
			pt.setTPartTypeInfo(t);
			break;
		}
		pt.setChildrenTotalNum(0);
		pt.setQualifiedNum(0);
		pt.setNodeid(nodeid);
		pt.setPlanType(2);
		pt.setAllocatedNum(0);
		pt.setCreateDate(StringUtils.convertDate(jp.getReleaseDate(), "yyyy-MM-dd hh:mm:ss"));
		pt.setProcess(0.0);
		pt.setScrapNum(0);
		commonService.saveObject(pt);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TPartTypeInfo> getPartTypeInfo(String no, String name) {
		String hql = "from TPartTypeInfo t where t.no='"+no+"' and t.name='"+name+"' and t.status<>'删除' ";
		return commonDao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insertWisTransfer(long eventId) {
       /*ERP倒序处理机制 --YT 201408 start-----------------------------*/
		//加载实体		
		TUserEquWorkEvents workevent=commonService.get(TUserEquWorkEvents.class,eventId);		
		//获取对应工单信息
		Collection<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter("no", workevent.getCuttingTask(), Operator.EQ));
		List<TJobdispatchlistInfo> temp=remoteCommonService.find(TJobdispatchlistInfo.class, (List<Sort>)null, parameters);
		if(temp!=null&&temp.size()>0){
			TJobdispatchlistInfo record=temp.get(0);
			
			//查找该工单对应的后序工单清单
			TProcessInfo currentProcess=commonService.get(TProcessInfo.class,record.getTProcessInfo().getId()); //解决延时加载
			TProcessplanInfo processplan=currentProcess.getTProcessplanInfo();
			System.err.println(processplan.getId());
			Collection<Parameter> process_params = new HashSet<Parameter>();
			process_params.add(new Parameter("TProcessplanInfo", processplan, Operator.EQ));
			List<TProcessInfo> process_temp_list=commonService.find(TProcessInfo.class, (List<Sort>)null, process_params);
			Map<Long,Long> processMap=new HashMap<Long,Long>();
			if(process_temp_list!=null&&process_temp_list.size()>0)
			{
				for(TProcessInfo rec:process_temp_list)
				{
					processMap.put(rec.getOnlineProcessId(), rec.getId());
				}
			}
			
			Long processId=currentProcess.getId();
			int minNum=1;
			Collection<Parameter> jobdispatch_params = new HashSet<Parameter>();
			jobdispatch_params.add(new Parameter("batchNo", record.getBatchNo(), Operator.EQ));
			List<TJobdispatchlistInfo> jobdispatch_temp_list=remoteCommonService.find(TJobdispatchlistInfo.class, (List<Sort>)null, jobdispatch_params);
			while(processMap.get(processId)!=null)
			{
				 //后序工单的完成数相减，取最小值
				{
				   if(jobdispatch_temp_list!=null&&jobdispatch_temp_list.size()>0)
				   {
//					  System.err.println("工序："+processMap.get(processId));
					   for(TJobdispatchlistInfo jobdispatch:jobdispatch_temp_list){
						   if(jobdispatch.getTProcessInfo().getId().intValue()==processMap.get(processId).intValue())
						   {
//							   System.err.println(jobdispatch.getFinishNum());
							 if(record.getFinishNum()-jobdispatch.getFinishNum()<minNum)
							  {
								 minNum=record.getFinishNum()-jobdispatch.getFinishNum();
							  }
							  break;
						   }
					   }
				   }
				}
				processId=processMap.get(processId);
			}
		if(minNum>0)
			{
	    /*ERP倒序处理机制 --YT 201408  end-----------------------------*/
	
		String sql = "select "
						+ " jobplan.no as jobplanNo," //批次号
					//	+ " equ_type.erp_resouce_code as resouceCode,"//资源代码
						+ " ' ' as resouceCode,"//资源代码
						+ " date_format(workevents.starttime,'%Y-%m-%d %T') as startTime,"//开始时间
						+ " date_format(workevents.finishtime,'%Y-%m-%d %T') as finishTime,"//结束时间
						+ " process.process_order as processOrder"//工序顺序
					+ " from" 
						+ " t_jobdispatchlist_info job," 
						+ " t_jobplan_info         jobplan,"
						+ " t_userequworkevents    workevents,"
					//	+ " r_process_equipment    pro_equ,"
					//	+ " t_equipmenttype_info  equ_type,"
						+ " t_process_info         process"
					+ " where"
						+ " job.jobplanID = jobplan.ID"
						+ " and job.processID = process.ID"
						+ " and job.no = workevents.cuttingTask"
					//	+ " and process.ID = pro_equ.processID"
					//	+ " and equ_type.ID = pro_equ.equipmentTypeID"
						+ " and workevents.ID="+eventId;
		List<Map<String,Object>> list = remoteCommonService.executeNativeQuery(sql);
		
		//获取要存入回传表的数据
		if(list.size()>0){
			Map<String,Object> map = list.get(0);
			String jobplanNo = map.get("jobplanNo").toString(); 
			Long processOrder = Long.parseLong(map.get("processOrder").toString()); 
			String resouceCode = "";
			if(!StringUtils.isEmpty(map.get("resouceCode")+"")){
				resouceCode = map.get("resouceCode").toString();
			}
			
			
			
			Date startTime = StringUtils.convertDate(map.get("startTime").toString(), "yyyy-MM-dd hh:mm:ss");
			Date finishTime = StringUtils.convertDate(map.get("finishTime").toString(), "yyyy-MM-dd hh:mm:ss");
			Date uploadDate = new Date();
			WisTransferId wt_id = new WisTransferId(jobplanNo,processOrder,resouceCode,uploadDate); 
			WisTransfer wt = new WisTransfer();
			wt.setId(wt_id);
			wt.setStartDate(startTime);
			wt.setCompletionDate(finishTime);
			wt.setQuantity(1L);
			wt.setIntraoperationStep(1L);
			wt.setCmpFlag(0L);
			wt.setFlag(0L);
			
			String sql_erp = "select entity.organization_id as organizationId from WIS_entity entity where entity.entity_name='"+jobplanNo+"' and entity.flag=2";
			List<Map<String,Object>> erpList = erpCommonDao.executeNativeQuery(sql_erp);
			if(erpList.size()>0){
				wt.setOrganizationId(Long.parseLong(erpList.get(0).get("organizationId").toString()));
			}else{
				wt.setOrganizationId(275L);
			}
			
			try{
				erpCommonDao.save(WisTransfer.class, wt);
			}catch(Exception e){
				e.printStackTrace();
			}
		   }
	     }
		TUserEquWorkEvents tUserEquWorkEvent = commonService.get(TUserEquWorkEvents.class, eventId);
		tUserEquWorkEvent.setFlag(1L);//是否导入erp标记位，1：已导入，0：未导入
		commonService.update(tUserEquWorkEvent);
	  }	
	}
	
	/**
	 * 手动报工,向erp库中添加依序规则
	 * @param eventId
	 * @author YT
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insertWisTransfer(Integer eventId)
	{
		//加载实体		
		TProductionEvents productionevent=commonService.get(TProductionEvents.class,eventId);		
		//获取对应工单信息
		Collection<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter("no", productionevent.getJobdispatchNo(), Operator.EQ));
		List<TJobdispatchlistInfo> temp=commonService.find(TJobdispatchlistInfo.class, (List<Sort>)null, parameters);
		if(temp!=null&&temp.size()>0){
			TJobdispatchlistInfo record=temp.get(0);
			
			//查找该工单对应的后序工单清单
			TProcessInfo currentProcess=commonService.get(TProcessInfo.class,record.getTProcessInfo().getId()); //解决延时加载
			TProcessplanInfo processplan=currentProcess.getTProcessplanInfo();
			System.err.println(processplan.getId());
			Collection<Parameter> process_params = new HashSet<Parameter>();
			process_params.add(new Parameter("TProcessplanInfo", processplan, Operator.EQ));
			List<TProcessInfo> process_temp_list=commonService.find(TProcessInfo.class, (List<Sort>)null, process_params);
			Map<Long,Long> processMap=new HashMap<Long,Long>();
			if(process_temp_list!=null&&process_temp_list.size()>0)
			{
				for(TProcessInfo rec:process_temp_list)
				{
					System.err.println(rec.getOnlineProcessId()+"-----"+rec.getId()+"---"+rec.getProcessOrder());
					processMap.put(rec.getOnlineProcessId(), rec.getId());
					
				}
			}
			
			Long processId=currentProcess.getId();
			int minNum=productionevent.getProcessNum();
			Collection<Parameter> jobdispatch_params = new HashSet<Parameter>();
			jobdispatch_params.add(new Parameter("batchNo", record.getBatchNo(), Operator.EQ));
			List<TJobdispatchlistInfo> jobdispatch_temp_list=commonService.find(TJobdispatchlistInfo.class, (List<Sort>)null, jobdispatch_params);
			
			System.err.println(record.getBatchNo()+"-----"+record.getFinishNum());
			while(processMap.get(processId)!=null)
			{
				 //后序工单的完成数相减，取最小值
				{
				   if(jobdispatch_temp_list!=null&&jobdispatch_temp_list.size()>0)
				   {
					  System.err.println("工序："+processMap.get(processId));
					   for(TJobdispatchlistInfo jobdispatch:jobdispatch_temp_list){
						   System.err.println("---"+processId);
						   if(jobdispatch.getTProcessInfo().getId().intValue()==processMap.get(processId).intValue())
						   {
							   System.err.println(jobdispatch.getNo()+"------"+jobdispatch.getFinishNum());
							 if(record.getFinishNum()-jobdispatch.getFinishNum()<minNum)
							  {
								 minNum=record.getFinishNum()-jobdispatch.getFinishNum();
							  }
							  break;
						   }
					   }
				   }
				}
				processId=processMap.get(processId);
			}
			System.err.println(minNum);
		if(minNum>0)
		{
			String sql = "select "
							+ " jobplan.no as jobplanNo," //批次号
						//	+ " equ_type.erp_resouce_code as resouceCode,"//资源代码
							+ " ' ' as resouceCode,"//资源代码
							+ " date_format(productionevents.starttime,'%Y-%m-%d %T') as startTime,"//开始时间
							+ " date_format(productionevents.endtime,'%Y-%m-%d %T') as finishTime,"//结束时间
							+ " process.process_order as processOrder"//工序顺序
						    + " from  t_production_events  productionevents,"					
							+ " t_jobdispatchlist_info jobdispatch," 
							+ " t_jobplan_info         jobplan,"						
						//	+ " r_process_equipment    pro_equ,"
						//	+ " t_equipmenttype_info   equ_type,"
							+ " t_process_info         process"
						    + " where jobdispatch.no = productionevents.jobdispatchNo"
							+ " and jobdispatch.jobplanID = jobplan.ID"
							+ " and jobdispatch.processID = process.ID"
						//	+ " and process.ID = pro_equ.processID"
						//	+ " and equ_type.ID = pro_equ.equipmentTypeID"
							+ " and productionevents.ID="+eventId;
			List<Map<String,Object>> list = commonService.executeNativeQuery(sql);
			
			//获取要存入回传表的数据
			if(list.size()>0){
				Map<String,Object> map = list.get(0);
				String jobplanNo = map.get("jobplanNo").toString(); 
				Long processOrder = Long.parseLong(map.get("processOrder").toString()); 
				String resouceCode = "";
				if(!StringUtils.isEmpty(map.get("resouceCode")+"")){
					resouceCode = map.get("resouceCode").toString();
				}
				
				Date startTime = StringUtils.convertDate(map.get("startTime").toString(), "yyyy-MM-dd hh:mm:ss");
				Date finishTime = StringUtils.convertDate(map.get("finishTime").toString(), "yyyy-MM-dd hh:mm:ss");
				Date uploadDate = new Date();
				WisTransferId wt_id = new WisTransferId(jobplanNo,processOrder,resouceCode,uploadDate); 
				WisTransfer wt = new WisTransfer();
				wt.setId(wt_id);
				wt.setStartDate(startTime);
				wt.setCompletionDate(finishTime);
				wt.setQuantity(Long.valueOf(minNum));
//				wt.setIntraoperationStep(3L);
				wt.setIntraoperationStep(1L);
				wt.setCmpFlag(0L);
				wt.setFlag(0L);
				String sql_erp = "select entity.organization_id as organizationId from WIS_entity entity where entity.entity_name='"+jobplanNo+"' and entity.flag=2";
				List<Map<String,Object>> erpList = erpCommonDao.executeNativeQuery(sql_erp);
				if(erpList.size()>0){
					wt.setOrganizationId(Long.parseLong(erpList.get(0).get("organizationId").toString()));
				}else{
					wt.setOrganizationId(275L);
				}
				try{
					erpCommonDao.save(WisTransfer.class, wt);
				}catch(Exception e){
					e.printStackTrace();
				}
			   }
			
		     }
		productionevent.setFlag(1);//是否导入erp标记位，1：已导入，0：未导入
		commonService.update(productionevent);
		  }	
		
	}

	@Override
	public void chaseLeaks() {
		//自动报工 补报
		String sql = "select t.ID as eventId from t_userequworkevents t where t.flag=0";
		List<Map<String,Object>> list = commonService.executeNativeQuery(sql);
		if(list.size()>0){
			for(Map<String,Object> map:list){
				Long eventId = Long.parseLong(map.get("eventId").toString());
				this.insertWisTransfer(eventId);
			}
		}
		
	   //手动报工  补报     YT	
		sql = "select t.ID as eventId from t_production_events t where t.flag=0 and t.eventType=2 ";
		List<Map<String,Object>> productevents_list = commonService.executeNativeQuery(sql);
		if(productevents_list.size()>0){
			for(Map<String,Object> map:productevents_list){
				Integer eventId = Integer.valueOf(map.get("eventId").toString());
				this.insertWisTransfer(eventId);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 产品成套入库向Wis_Transfer表写入数据
	 * @param inStockNum
	 * @param jobplanNo
	 * @param instockNo
	 */
	public void inStockinsertWisTransfer(Integer inStockNum, String jobplanNo,String instockNo) {
		//对任务号进行验证（任务是否已经修改）
		List<Map<String,Object>> list1 = erpCommonDao.executeNativeQuery("select FLAG,ORGANIZATION_ID,INVENTORY_CODE from (select FLAG,ORGANIZATION_ID,INVENTORY_CODE"
				+ " from WIS_ENTITY"
				+ " where WIS_ENTITY.ENTITY_NAME='"+jobplanNo+"' order by WIS_ENTITY.UPLOAD_DATE desc)"
			    + " a where rownum = 1");
		if(list1.size()>0){
			if(null != list1.get(0).get("FLAG") && list1.get(0).get("FLAG").toString().equals("2")){
				
			}else{
				return;
			}
		}else{
			return;
		}
		//获取工序尾序序号
		List<Map<String,Object>> list = commonService.executeNativeQuery("select p2.process_order as processOrder"
				+ " from t_jobplan_info jp"
				+ " inner join t_jobdispatchlist_info on t_jobdispatchlist_info.jobplanID = jp.id"
				+ " inner join t_process_info p1 on p1.id = t_jobdispatchlist_info.processID"
				+ " inner join t_processplan_info on t_processplan_info.id = p1.processPlanID"
				+ " inner join t_process_info p2 on p2.processPlanID =t_processplan_info.id"
				+ " where p2.offlineProcess =1 and jp.no ='"+jobplanNo+"'");
		String processOrder ="";//工序序号
		if(list.size()>0){
			processOrder = list.get(0).get("processOrder").toString();
			Date uploadDate = new Date();
			WisTransferId wt_id = new WisTransferId(jobplanNo, Long.valueOf(processOrder), "", uploadDate);
			WisTransfer wt = new WisTransfer();
			wt.setId(wt_id);
			wt.setQuantity(Long.valueOf(inStockNum));
			wt.setIntraoperationStep(1L);
			
			//库房编号
			if(list1.size()>0 && null!= list1.get(0).get("INVENTORY_CODE") && ""!=list1.get(0).get("INVENTORY_CODE")){
				wt.setInventoryCode(list1.get(0).get("INVENTORY_CODE").toString());
			}else{
				wt.setInventoryCode(instockNo);
			}
			
			wt.setCmpFlag(1L);
			wt.setFlag(0L);
			
			//组织Id
			if(list1.size()>0 && null!= list1.get(0).get("ORGANIZATION_ID")){
				wt.setOrganizationId(Long.valueOf(list1.get(0).get("ORGANIZATION_ID").toString()));
			}
			
			try{
				erpCommonDao.save(WisTransfer.class, wt);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 获取子库存号
	 */
	public List<Map<String, Object>> getInventoryCode(String no) {
		
		return erpCommonDao.executeNativeQuery("select inventoryCode from (select inventory_code as inventoryCode"
				+ " from WIS_ENTITY "
				//+ " inner join WIS_INVENTORY on WIS_INVENTORY.INV_CODE = WIS_ENTITY.INVENTORY_CODE"
				+ " where WIS_ENTITY.ENTITY_NAME='"+no+"' order by WIS_ENTITY.UPLOAD_DATE desc)"
				+ " a where rownum = 1 ");
	}
    /**
     * 400序检验信息保存
     */
	@Override
	public void insertWisQaCheck(int num, String partNo, String partName,
			String dispatchNo, String isGood, String depName,
			String jgCheckUser, String zpCheckUser,String sjCheckUser,String createUser) {
		//获取组织ID
		//获取工序尾序序号
		List<Map<String,Object>> list = erpCommonDao.executeNativeQuery("select ORGANIZATION_ID from "
				+ " WIS_ITEM where ITEM_CODE ='"+partNo+"'");
		WisQaCheckId wisQaCheckId =new WisQaCheckId();
		WisQaCheck wisQaCheck =new WisQaCheck();
		wisQaCheckId.setItemCode(partNo);
		wisQaCheckId.setItemDesc(partName);
		wisQaCheckId.setEntityName(dispatchNo);
		wisQaCheckId.setQuantity(Long.valueOf(num));
		wisQaCheckId.setDepartment(depName);
		wisQaCheckId.setJgCheck(jgCheckUser);
		wisQaCheckId.setZpCheck(zpCheckUser);
		wisQaCheckId.setSjCheck(sjCheckUser);
		wisQaCheckId.setCreator(createUser);
		wisQaCheckId.setWriteDate((new Date()).toString());
		wisQaCheckId.setUploadDate((new Date()));
		wisQaCheckId.setFlag(1L);
		if(isGood =="是"){
		     wisQaCheckId.setOkFlag(1L);
		}else{
			 wisQaCheckId.setOkFlag(0L);
		}
		
		if(list.size()>0 && null!=list.get(0).get("ORGANIZATION_ID")){
		wisQaCheckId.setOrganizationId(Long.valueOf(list.get(0).get("ORGANIZATION_ID").toString()));
		}
		wisQaCheck.setId(wisQaCheckId);
		try{
			erpCommonDao.save(wisQaCheck);
		}catch(Exception e){ 
			e.printStackTrace();
		}
		
	}
	
	
	
}

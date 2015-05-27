package smtcl.mocs.services.report.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.apache.http.impl.cookie.DateUtils;
import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.services.report.IReportService;
import smtcl.mocs.utils.authority.DateUtil;
import smtcl.mocs.utils.device.StringUtils;

public class ReportServiceImpl extends GenericServiceSpringImpl<Object, String> implements IReportService{

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> outputData(String nodeid,String startTime,String endTime,int plan_type) {
		String hql = "select new Map(jobplan.id as jobId,"//计划Id
				+ " jobplan.planNum as planNum,"//计划数量
				+ "	jobplan.finishNum as finishNum,"//完成数量
				+ "	jobplan.TJobplanInfo.id as pid,"//父Id
				+ "	jobplan.planType as planType,"//计划类型
				+ "	part.name as partName,"//零件名称
				+ " part.id as partId,"//零件Id
				+ "	jobplan.planStarttime as startTime) "//开始时间
				+ " from TJobplanInfo jobplan,TPartTypeInfo part"
				+ " where jobplan.TPartTypeInfo.id = part.id"
				+ " and jobplan.planStarttime >= DATE_FORMAT('"+startTime+"','%Y-%m-%d %T')"
				+ " and jobplan.planStarttime < DATE_FORMAT('"+endTime+"','%Y-%m-%d %T')"
				+ " and jobplan.planType="+plan_type
				+ " and part.nodeid='"+nodeid+"'"
				+ " order by part.name asc";
		return dao.executeQuery(hql);
	}

	@Override
	public List<Map<String, Object>> monthOutputData(String nodeid,String startTime,String endTime) {
		//获取作业计划数据
		List<Map<String,Object>> jobPlan=this.outputData(nodeid, startTime, endTime,1);
		//要返回的数据集
		List<Map<String,Object>> newData = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> tempMap : jobPlan){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("planNum", tempMap.get("planNum"));//计划数量
			map.put("partName",tempMap.get("partName"));//零件名称
			//获取零件id号
			int partid = Integer.parseInt(tempMap.get("partId").toString());
			List<Map<String,Object>> tempList = this.getTJobplanInfoByPartId(partid, startTime, endTime, 2);//查询时间段内的所有批次计划
//			int finishNum=0;//完成数量
			int touliaoNum=0;//投料数量
			for(Map<String,Object> tempMap2:tempList){
//				finishNum+=Integer.parseInt(tempMap2.get("finishNum").toString());
				touliaoNum+=Integer.parseInt(tempMap2.get("planNum").toString());
			}
			map.put("startTime", DateUtil.formatTime(tempMap.get("startTime").toString(), "yyyy-MM-dd"));
			map.put("partId", tempMap.get("partId").toString());
			map.put("touliaoNum", touliaoNum);
			map.put("finishNum", tempMap.get("finishNum"));
			newData.add(map);
		}
		
		return newData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> historyTouliaoData(String nodeid,String startTime,String endTime) {
		String hql = "select new Map(part.name as partName,"//零件名称
				+ " jobplan.no as jobplanNo,"//计划编号
				+ "	jobplan.planNum as touliaoNum,"//投料数量
				+ "	jobplan.finishNum as finishNum) "//完成数量
				+ "	from TPartTypeInfo part,TJobplanInfo jobplan"
				+ " where jobplan.planType=2"
				+ " and jobplan.TPartTypeInfo.id = part.id"
				+ " and jobplan.nodeid='"+nodeid+"'"
				+ " and jobplan.planStarttime >= DATE_FORMAT('"+startTime+"','%Y-%m-%d %T')"
				+ " and jobplan.planStarttime < DATE_FORMAT('"+endTime+"','%Y-%m-%d %T')"
				+ " ORDER BY part.name asc";
		return dao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getJobInfo(int partid, String startTime, String endTime, int plan_type) {
		String hql = "select new Map(jobplan.id as jobId,"//作业计划ID
				+ " jobplan.planNum as planNum,"//计划数量
				+ "	jobplan.finishNum as finishNum,"//完成shul
				+ " jobplan.scrapNum as scrapNum,"//报废数量
				+ "	jobplan.TJobplanInfo.id as pid,"//父作业Id
				+ "	jobplan.planType as planType,"//计划类型
				+ "	part.name as partName,"//零件名称
				+ "	jobplan.planStarttime as startTime) "//开始时间
				+ " from TJobplanInfo jobplan,TPartTypeInfo part"
				+ " where jobplan.TPartTypeInfo.id = part.id"
//				+ " and jobplan.planStarttime >= DATE_FORMAT('"+startTime+"','%Y-%m-%d %T')"
//				+ " and jobplan.planStarttime < DATE_FORMAT('"+endTime+"','%Y-%m-%d %T')"
				+ " and jobplan.planType="+plan_type
				+ " and part.id="+partid
				+ " and jobplan.status <> 70 and jobplan.status <> 60"
				+ " order by jobplan.id,jobplan.TJobplanInfo.id asc";
		return dao.executeQuery(hql);
	}

	@Override
	public List<Map<String, Object>> dispatchDetailData(String nodeid,String jobplanStatus,
			String partNo, Date jobCreateDate,Date jobCreateDateEnd, Date jobStartTime,Date jobStartTimeEnd,
			String equSerialNo, String person) {
		String sql = "select distinct "
			+ " job.taskNum as taskNum,"//批次号
			+ " part.no as partNo,"//零件编号
			+ " part.name as partName,"//零件名称
			+ " job.no as jobNo,"//工单编号
//			+ " process.description as processDesc,"//工序描述
			+ " process.process_order as processNo,"//工序
			+ " process.name as processName,"
			+ " jobplan.planNum as planNum,"//工单数量
			+ " job.finishNum as finishNum,"//完成数量
			+ " job.wisScrapNum as scrapNum,"//报废数量
			+ " workevents.equ_SerialNo as equSerialNo,"//设备编号
			+ " DATE_FORMAT(job.plan_starttime,'%Y-%m-%d') as planStartTime,"//计划开始时间
			+ " DATE_FORMAT(job.plan_endtime,'%Y-%m-%d') as planEndTime,"//计划结束时间
			+ " DATE_FORMAT(job.real_starttime,'%Y-%m-%d %T') as realStartTime,"//实际开始时间
			+ " DATE_FORMAT(job.real_endtime,'%Y-%m-%d %T') as realEndTime,"//实际结束时间
			+ " job.status as jobStatus,"//工单状态
 			+ " statusinfo.name as jobst,"//工单状态
			+ " jobplan.status as jobplanStatus,"//批次状态
		    + " statusinfo2.name as jobplanst,"//批次状态
		    + " workevents.operator_no as person"
		 + " from t_jobplan_info jobplan,"
		 	+ " t_jobdispatchlist_info job,"
		 	+ " t_part_type_info part,"
		 	+ " t_process_info process,"
		 	+ " t_status_info statusinfo,"
		 	+ " t_status_info statusinfo2,"
		 	+ " t_userequworkevents workevents"
		 + " where" 
			+ " job.jobplanID = jobplan.ID" 
			+ " and job.processID = process.ID"
			+ " and job.status = statusinfo.id"
			+ " and jobplan.status = statusinfo2.id"
			+ " and workevents.partNo = part.no"
			+ " and jobplan.plan_type = 2"
			+ " and job.no = workevents.cuttingTask"
			+ " and job.nodeid='"+nodeid+"'";
		//以下是过滤查询条件
		if(!StringUtils.isEmpty(jobplanStatus)){
			sql += " and jobplan.status="+Integer.parseInt(jobplanStatus);
		}
		if(!StringUtils.isEmpty(partNo)){
			sql += " and part.id="+Integer.parseInt(partNo);
		}
		if(!StringUtils.isEmpty(equSerialNo)){
//			sql += " and equ.equ_ID="+Integer.parseInt(equSerialNo);
			sql += " and workevents.equ_SerialNo='"+equSerialNo+"'";
		}
		if(jobCreateDate!=null){
			sql += " and job.createDate >=DATE_FORMAT('"+DateUtils.formatDate(jobCreateDate, "yyyy-MM-dd")+"','%Y-%m-%d %T')";
		}
		if(jobCreateDateEnd!=null){
			sql += " and job.createDate <DATE_FORMAT('"+DateUtils.formatDate(jobCreateDateEnd, "yyyy-MM-dd")+"','%Y-%m-%d %T')";
		}
		if(jobStartTime != null){
			sql += " and job.plan_starttime >= DATE_FORMAT('"+DateUtils.formatDate(jobStartTime, "yyyy-MM-dd")+"','%Y-%m-%d %T')";
		}
		if(jobStartTimeEnd != null){
			sql += " and job.plan_starttime < DATE_FORMAT('"+DateUtils.formatDate(jobStartTimeEnd, "yyyy-MM-dd")+"','%Y-%m-%d %T')";
		}
		if(!StringUtils.isEmpty(person)){
			sql += " and workevents.operator_no='"+person+"'";
		}
		return dao.executeNativeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getPersonDetailInfo(String nodeid,String startTime,String endTime) {
		String sql = "select userpro.operator_no as operatorNo,"
				       + " member.name as personName,"
				       + " part.name as partName,"
				       + " process.name as processName,"
				       + " SUM(userpro.processNum) as finishNum"
				  + " from t_userproduction userpro,"
				       + " t_member_info    member,"
				       + " t_part_type_info part,"
				       + " t_process_info   process"
				 + " where userpro.operator_no = member.no"
				   + " and userpro.partTypeID = part.ID"
				   + " and userpro.processID = process.ID"
				   + " and part.nodeid='"+nodeid+"'";
		if(!StringUtils.isEmpty(startTime)){
			sql += " and userpro.updateDate >= DATE_FORMAT('"+startTime+"','%Y-%m-%d %T')";
		}
		if(!StringUtils.isEmpty(endTime)){
			sql += " and userpro.updateDate < DATE_FORMAT('"+endTime+"','%Y-%m-%d %T')";
		}
		sql += " group by userpro.operator_no, member.name, part.name, process.name order by userpro.operator_no asc";
		return dao.executeNativeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getEquSerialNoDetailInfo(String nodeid,String startTime,String endTime) {
		String sql = "select equpro.equ_SerialNo as equSerialNo,"
				       + " part.name as partName,"
				       + " process.name as processName,"
				       + " SUM(equpro.processNum) as finishNum"
				  + " from t_equproduction  equpro,"
				       + " t_part_type_info part,"
				       + " t_process_info   process"
				 + " where equpro.partTypeID = part.ID"
				   + " and equpro.processID = process.ID"
				   + " and part.nodeid='"+nodeid+"'";
		if(!StringUtils.isEmpty(startTime)){
			sql += " and equpro.updateDate >= DATE_FORMAT('"+startTime+"','%Y-%m-%d %T')";
		}
		if(!StringUtils.isEmpty(endTime)){
			sql += "and equpro.updateDate < DATE_FORMAT('"+endTime+"','%Y-%m-%d %T')";
		}
		sql += " group by equpro.equ_SerialNo, part.name, process.name order by equpro.equ_SerialNo asc";
		return dao.executeNativeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getTJobplanInfoByPartId(int partid,String startTime,String endTime,int plan_type) {
		String hql = "select new Map(jobplan.id as jobId,"//计划Id
				+ " jobplan.planNum as planNum,"//计划数量
				+ "	jobplan.finishNum as finishNum,"//完成数量
				+ "	jobplan.TJobplanInfo.id as pid,"//父Id
				+ "	jobplan.planType as planType,"//计划类型
				+ "	part.name as partName,"//零件名称
				+ "	jobplan.planStarttime as startTime) "//开始时间
				+ " from TJobplanInfo jobplan,TPartTypeInfo part"
				+ " where jobplan.TPartTypeInfo.id = part.id"
				+ " and jobplan.planStarttime >= DATE_FORMAT('"+startTime+"','%Y-%m-%d %T')"
				+ " and jobplan.planStarttime < DATE_FORMAT('"+endTime+"','%Y-%m-%d %T')"
				+ " and jobplan.planType="+plan_type
				+ " and jobplan.TPartTypeInfo.id="+partid
				+ " order by jobplan.id,jobplan.TJobplanInfo.id asc";
		return dao.executeQuery(hql);
	}

	@Override
	public List<Map<String, Object>> getPersonList() {
		String sql = "select "
						+ " distinct" 
						+ " workevents.operator_no as personName,"
						+ " workevents.operator_no as personValue"
				   + " from "
				   		+ " t_userequworkevents workevents" 
				   		+ " where workevents.operator_no <> '' ";
		return dao.executeNativeQuery(sql);
	}
	/**
	 * 根据时间查询生产报废报表数据
	 * @param start
	 * @return
	 */
	public List<Map<String,Object>> getProductionScrapReport(String start,String end,String scrapType){
		String sql="SELECT left(e.operateDate,10) as operateDate,e.eventNo,e.scrapType,e.responseNo,e.toName,e.toProcessOrder,"
				+ "	f.name AS zrProcessName,"
				+ " f.zrNo AS zrProcessOrder,e.partNo,e.partName,e.processNum,e.ljgs,e.hjgs,e.workTime,e.processingTime,e.operateReason"
				+ "	FROM ("
				+ "	SELECT "
				+ "	a.operate_date AS operateDate,"
				+ "	a.eventNo,"
				+ "	CASE WHEN a.eventType=1 THEN '工废'" 
				+ "	     WHEN a.eventType=4 THEN '料废'"
				+ "	END AS scrapType,"
				+ "	a.response_no AS responseNo,"
				+ "	c1.name AS toName,"
				+ "	c1.process_order AS toprocessOrder,"
				+ "	d.name AS partName,"
				+ "	d.No AS partNo,"
				+ "	a.processNum,"
				+ "	a.workTime*a.processNum AS ljgs,"
				+ "	c1.processing_time*a.processNum AS hjgs,"
				+ "	a.workTime,"
				+ "	c1.processing_time AS processingTime,"
				+ " a.operate_reason AS operateReason,"
				+ "	a.response_processNo AS zrNo,"
				+ "	b.batchNo"
				+ "	FROM t_production_events a,t_jobdispatchlist_info b,t_process_info c1,t_part_type_info d"
				+ "	WHERE  a.jobdispatchNo=b.no AND b.processID=c1.id  AND a.partTypeID=d.id";
				if(null!=start&&!"".equals(start)){
					sql+= " and left(a.operate_date,10)>='"+start+"'";
				}
				if(null!=end&&!"".equals(end)){
					sql+= " and left(a.operate_date,10)<='"+end+"'";
				}
				if(null!=scrapType&&!"".equals(scrapType)){
					sql+= " and a.eventType='"+scrapType+"'";
				}else{
					sql+= " AND (a.eventType=1||a.eventType=4)";
				}
				
			sql+= " ) e	LEFT JOIN  (SELECT c2.process_order  AS zrNo,c3.batchNo,c2.name "
				+ "				FROM t_process_info c2,t_jobdispatchlist_info c3 "
				+ "				WHERE c3.processID=c2.id) f"  
				+ "	ON e.zrNo=f.zrNo AND e.batchNo=f.batchNo";
		return dao.executeNativeQuery(sql);
	}
	
	
	/**
	 * 获取生产事件查询数据
	 * @param pageNo
	 * @param pageSize
	 * @param startTime
	 * @param endTime
	 * @param eventType
	 * @param partId
	 * @param jobdispatchNo
	 * @return
	 */
	public IDataCollection<Map<String, Object>> getProductionEventScrap(int pageNo, int pageSize, Date startTime,Date 
			endTime,String eventType,String partId,String jobdispatchNo) {
		String hql="select new Map("
				+ " a.id as id,"
				+ " a.eventNo as eventNo,"
				+ " a.jobdispatchNo as jobdispatchNo,"
				+ " a.processNum as processNum,"
				+ " a.operatorNo as operatorNo,"
				+ " DATE_FORMAT(a.operateDate,'%Y-%m-%d %T') as operateDate,"
				+ " a.operateReason as operateReason,"
				+ " b.name as partName,"
				+ " a.equSerialNo as equSerialNo,"
				+ " a.responseNo as responseNo,"
				+ " a.responseProcessNo as responseProcessNo,"
				+ " DATE_FORMAT(a.responseDate,'%Y-%m-%d') as responseDate,"
				+ "	CASE WHEN a.eventType=1 THEN '工废'" 
				+ "	     WHEN a.eventType=4 THEN '料废'"
				+ "	     WHEN a.eventType=2 THEN '手动'"
				+ "	     WHEN a.eventType=3 THEN '修改'"
				+ "	END AS eventType)"
				+ " from TProductionEvents a,TPartTypeInfo b"
				+ " where a.partTypeID=b.id ";
		if(null!=startTime&&!"".equals(startTime.toString())){
			hql+=" and a.operateDate>DATE_FORMAT('"+StringUtils.formatDate(startTime, 2) +"','%Y-%m-%d ')";
		}
		if(null!=endTime&&!"".equals(endTime.toString())){
			hql+=" and a.operateDate<DATE_FORMAT('"+StringUtils.formatDate(endTime,2)+"','%Y-%m-%d')";
		}
		if(null!=eventType&&!"".equals(eventType.toString())){
			hql+=" and a.eventType="+eventType;
		}
		if(null!=partId&&!"".equals(partId.toString())){
			hql+=" and a.partTypeID="+partId;
		}
		if(null!=jobdispatchNo&&!"".equals(jobdispatchNo.toString())){
			hql+=" and a.jobdispatchNo='"+jobdispatchNo+"'";
		}
		return dao.executeQuery(pageNo, pageSize, hql);
	}
}
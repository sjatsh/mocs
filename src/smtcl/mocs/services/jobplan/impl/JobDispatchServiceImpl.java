package smtcl.mocs.services.jobplan.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.GenericServiceSpringImpl;

import smtcl.mocs.beans.jobplan.JobdispatchAddBean;
import smtcl.mocs.beans.jobplan.JobdispatchUpdataBean;
import smtcl.mocs.pojos.job.TEquJobDispatch;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.storage.TJobdispatchMaterial;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.StringUtils;

public class JobDispatchServiceImpl extends GenericServiceSpringImpl<TJobdispatchlistInfo, String> implements IJobDispatchService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getDevicesInfo(String nodeId,String taskNum,String jobstatus, String partid,String equid,String planStime,String planEtime) {

		String hql = "select distinct new Map(equ.equId as Id, equ,equSerialNo as Name,equ.equName as equName) "
				+ " from TEquipmentInfo equ"
				+ " where equ.TNodes.TNodes.nodeId = '"+nodeId+"'";
		
		//		String hql = "SELECT DISTINCT NEW MAP(equ.equId AS Id," 
//				+ " equ.equSerialNo AS Name,"
//				+ "	equ.equName as equName) "
//				+ " FROM TEquipmentInfo equ,"
//				+ " TJobdispatchlistInfo job, "
//				+ " TEquJobDispatch equ_job,"
//				+ " TProcessInfo process,"
//				+ " TProcessplanInfo planinfo,"
//				+ " TPartTypeInfo part"
//				+ " WHERE equ.TNodes.TNodes.id='"+nodeId+"'"
//				+ " and equ.equSerialNo = equ_job.equSerialNo"
//				+ " and equ_job.jobdispatchNo = job.no"
//				+ " and equ_job.status <> 0"
//				+ " and job.TProcessInfo.id = process.id"
//				+ " and process.TProcessplanInfo.id = planinfo.id"
//				+ " and planinfo.TPartTypeInfo.id = part.id";
//		if(!StringUtils.isEmpty(taskNum)){
//			hql += " and job.taskNum in ("+taskNum+")";
//		}
//		if(!StringUtils.isEmpty(jobstatus)){
//			hql += " and job.status in("+jobstatus+")"; 
//		}
//		if(!StringUtils.isEmpty(partid)){
//			hql += " and part.id in ("+partid+")";
//		}
//		if(!StringUtils.isEmpty(equid)){
//			hql += " and equ.equId in ("+equid+")";
//		}
//		if(!StringUtils.isEmpty(planStime)){
//			hql += " AND job.planStarttime >= DATE_FORMAT('"+planStime+"','%Y-%m-%d')  "; 
//		}	
//		if(!StringUtils.isEmpty(planEtime)){
//			hql += " AND job.planEndtime <= DATE_FORMAT('"+planEtime+"','%Y-%m-%d')  "; 
//		}
		hql += " ORDER BY equ.equSerialNo ASC";		
		return dao.executeQuery(hql);		
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 查找设备对应的工单
	 */
	public List<Map<String, Object>> getJobDispatchsInfo(String nodeId,String taskNum,String jobstatus,String partid,String equid,String planStime,String planEtime) {
		List<Map<String, Object>> result=new ArrayList<Map<String, Object>>();
		String hql = "SELECT NEW MAP(jobdispatch.id AS Id,jobdispatch.no AS No," 
				+" jobdispatch.name AS Name," 
				+" jobdispatch.TProcessInfo.name AS TprocessName,"
				+" jobdispatch.taskNum as batchNo,"
				+" jobdispatch.status AS Status,"
				+" jobdispatch.oldStatus AS oldStatus,"
				+" DATE_FORMAT(jobdispatch.planStarttime,'%Y-%m-%d %T') AS StartDate,"
				+" DATE_FORMAT(jobdispatch.planEndtime,'%Y-%m-%d %T') as EndDate ,"
				+" DATE_FORMAT(jobdispatch.realStarttime,'%Y-%m-%d %T') as RealStarttime ,"
				+" DATE_FORMAT(jobdispatch.realEndtime,'%Y-%m-%d %T') as RealEndtime,"
			    +" equ.equId as ResourceId," 
				+" equ.equSerialNo as equSerialNo,"
				+" part.name as partName,"
                +" jobdispatch.processNum as planNum," 
			    +" jobdispatch.goodQuantity as goodQuantity,"
				+" jobdispatch.finishNum as finishNum,"
			    +" jobdispatch.taskNum as taskNum)"
				+" FROM TJobdispatchlistInfo jobdispatch,TEquipmentInfo equ,TEquJobDispatch tjob,"
				+ "TPartTypeInfo part,TProcessInfo process, TProcessplanInfo plan"
				+" where jobdispatch.no = tjob.jobdispatchNo and tjob.status <> 0"
				+" and equ.equSerialNo = tjob.equSerialNo"
				+" and jobdispatch.nodeid='"+nodeId+"'"
				+" and jobdispatch.TProcessInfo.id = process.id"
				+" and process.TProcessplanInfo.id = plan.id"
				+" and plan.TPartTypeInfo.id = part.id";
		if(!StringUtils.isEmpty(jobstatus)){
			hql += " AND jobdispatch.status in("+jobstatus+")"; 
		}	
		if(!StringUtils.isEmpty(taskNum)){
			hql += " AND jobdispatch.taskNum in ("+taskNum+")"; 
		}	
		if(!StringUtils.isEmpty(planStime)){
			hql += " AND jobdispatch.planStarttime >= DATE_FORMAT('"+planStime+"','%Y-%m-%d')  "; 
		}	
		if(!StringUtils.isEmpty(planEtime)){
			hql += " AND jobdispatch.planEndtime <= DATE_FORMAT('"+planEtime+"','%Y-%m-%d')  "; 
		}
		if(!StringUtils.isEmpty(partid)){
			hql += " and part.id in ("+partid+")";
		}
		if(!StringUtils.isEmpty(equid)){
			hql += " and equ.equId in ("+equid+")";
		}
		hql += " order by jobdispatch.id desc ";

		List<Map<String, Object>> rs=dao.executeQuery(hql);
		if(null!=rs&&rs.size()>0){
			for(Map<String, Object> tt:rs){
				String status = tt.get("Status").toString();
				Map<String, Object> mm=new HashMap<String, Object>();
				//创建  待派工 已派工 
				String name = tt.get("partName").toString();
				if(name.indexOf("//")>0 && name.split("//").length==3){
					name = name.split("//")[1]+"/"+tt.get("TprocessName").toString();
				} 
				if("10".equals(status)||"20".equals(status)||"30".equals(status)){
					mm.put("Id",tt.get("Id"));
					mm.put("Name",name);//
					mm.put("TprocessName", tt.get("TprocessName"));//工序名称
					mm.put("Status",status);
					mm.put("StartDate",tt.get("StartDate"));
					mm.put("EndDate",tt.get("EndDate"));
					mm.put("ResourceId",tt.get("ResourceId"));
					mm.put("planNum",tt.get("planNum"));
					mm.put("finishNum",tt.get("finishNum"));
					mm.put("Percentage",0);
					mm.put("taskNum",tt.get("taskNum"));
					mm.put("no",tt.get("No"));
					mm.put("rId",tt.get("ResourceId"));
					mm.put("partName", tt.get("partName"));//零件名称
					mm.put("equSerialNo", tt.get("equSerialNo"));//设备序列号
					mm.put("batchNo", tt.get("batchNo"));//工单编号
			    //上线  加工	
				}else if("40".equals(status)||"50".equals(status)){
					mm.put("Id",tt.get("Id"));
					mm.put("Name",name);
					mm.put("TprocessName", tt.get("TprocessName"));//工序名称
					mm.put("Status",status);
					mm.put("StartDate",tt.get("RealStarttime"));
					mm.put("EndDate",tt.get("EndDate"));
					mm.put("ResourceId",tt.get("ResourceId"));
					mm.put("planNum",tt.get("planNum"));
					mm.put("finishNum",tt.get("finishNum"));
					double rr=Double.parseDouble(tt.get("finishNum").toString())/Double.parseDouble(tt.get("planNum").toString())*100;
				    DecimalFormat df=new DecimalFormat("0.00"); 
					mm.put("Percentage",df.format(rr));
					mm.put("taskNum",tt.get("taskNum"));
					mm.put("no",tt.get("No"));
					mm.put("rId",tt.get("ResourceId"));
					mm.put("partName", tt.get("partName"));//零件名称
					mm.put("equSerialNo", tt.get("equSerialNo"));//设备序列号
					mm.put("batchNo", tt.get("batchNo"));//工单编号
				}
				//暂停/恢复
				else if("80".equals(status))
				{
					 if("30".equals(tt.get("oldStatus").toString()))
					 {
						mm.put("Id",tt.get("Id"));
						mm.put("Name",name);
						mm.put("TprocessName", tt.get("TprocessName"));//工序名称
						mm.put("Status",status);
						mm.put("StartDate",tt.get("StartDate"));
						mm.put("EndDate",tt.get("EndDate"));
						mm.put("ResourceId",tt.get("ResourceId"));
						mm.put("planNum",tt.get("planNum"));
						mm.put("finishNum",tt.get("finishNum"));
						mm.put("Percentage",0);
						mm.put("taskNum",tt.get("taskNum"));
						mm.put("no",tt.get("No"));
						mm.put("rId",tt.get("ResourceId"));
						mm.put("partName", tt.get("partName"));//零件名称
						mm.put("equSerialNo", tt.get("equSerialNo"));//设备序列号
						mm.put("batchNo", tt.get("batchNo"));//工单编号
					 }else if("40".equals(tt.get("oldStatus").toString()) || "50".equals(tt.get("oldStatus").toString())){
						 mm.put("Id",tt.get("Id"));
						 mm.put("Name",name);
						 mm.put("TprocessName", tt.get("TprocessName"));//工序名称
						 mm.put("Status",status);
					     mm.put("StartDate",tt.get("RealStarttime"));
						 mm.put("EndDate",tt.get("EndDate"));
						 mm.put("ResourceId",tt.get("ResourceId"));
						 mm.put("planNum",tt.get("planNum"));
						 mm.put("finishNum",tt.get("finishNum"));
						 double rr=Double.parseDouble(tt.get("finishNum").toString())/Double.parseDouble(tt.get("planNum").toString())*100;
						 DecimalFormat df=new DecimalFormat("0.00"); 
						 mm.put("Percentage",df.format(rr));
						 mm.put("taskNum",tt.get("taskNum"));
						 mm.put("no",tt.get("No"));
						 mm.put("rId",tt.get("ResourceId"));	
						 mm.put("partName", tt.get("partName"));//零件名称
						 mm.put("equSerialNo", tt.get("equSerialNo"));//设备序列号
						 mm.put("batchNo", tt.get("batchNo"));//工单编号
					 }
				}
				//结束  完工
				else{
					mm.put("Id",tt.get("Id"));
					mm.put("Name",name);
					mm.put("TprocessName", tt.get("TprocessName"));//工序名称
					mm.put("Status",status);
					mm.put("StartDate",tt.get("RealStarttime"));
					mm.put("EndDate",tt.get("RealEndtime"));
					mm.put("ResourceId",tt.get("ResourceId"));
					mm.put("planNum",tt.get("planNum"));
					mm.put("finishNum",tt.get("finishNum"));
					mm.put("Percentage",0);
					mm.put("no",tt.get("No"));
					mm.put("rId",tt.get("ResourceId"));
					mm.put("partName", tt.get("partName"));//零件名称
					mm.put("equSerialNo", tt.get("equSerialNo"));//设备序列号
					mm.put("batchNo", tt.get("batchNo"));//工单编号
				}
				result.add(mm);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 选择一条工单记录
	 */
	public List<Map<String, Object>> getJobDispatchsInfoOne(String jobplanId) {
		List<Map<String, Object>> result=new ArrayList<Map<String, Object>>();
		
		String hql = "SELECT NEW MAP(jobdispatch.id AS Id,jobdispatch.no AS No," 
				+" jobdispatch.TProcessInfo.name AS Name," 
				+" jobdispatch.TProcessInfo.name AS TprocessName,"
				+" jobdispatch.status AS Status,"
				+" DATE_FORMAT(jobdispatch.planStarttime,'%Y-%m-%d ') AS StartDate,"
				+" DATE_FORMAT(jobdispatch.planEndtime,'%Y-%m-%d ') as EndDate ,"
				+" DATE_FORMAT(jobdispatch.realStarttime,'%Y-%m-%d ') as RealStarttime ,"
				+" DATE_FORMAT(jobdispatch.realEndtime,'%Y-%m-%d ') as RealEndtime,"
                +" jobdispatch.processNum as planNum,"
			    +" jobdispatch.goodQuantity as goodQuantity,"
                +" jobdispatch.TEquipmenttypeInfo.equipmentType as EquType,"
				+" jobdispatch.finishNum as finishNum) "
				+" FROM TJobdispatchlistInfo jobdispatch"
				+" WHERE jobdispatch.id="+Long.valueOf(jobplanId)
		        +" order by jobdispatch.id desc ";
		
        List<Map<String, Object>> rs=dao.executeQuery(hql);
		if(null!=rs&&rs.size()>0){
			for(Map<String, Object> tt:rs){
				Map<String, Object> mm=new HashMap<String, Object>();
				//创建  待派工 已派工 
				if("30".equals(tt.get("Status").toString())){
					mm.put("Id",tt.get("Id"));
					mm.put("Name",tt.get("Name"));
					mm.put("TprocessName", tt.get("TprocessName"));//工序名称
					mm.put("Status",tt.get("Status"));
					mm.put("StartDate",tt.get("StartDate"));
					mm.put("EndDate",tt.get("EndDate"));
					mm.put("ResourceId",tt.get("ResourceId"));
					mm.put("planNum",tt.get("planNum"));
					mm.put("finishNum",tt.get("finishNum"));
					mm.put("Percentage",0);
					mm.put("no",StringUtils.getSubString(tt.get("No").toString(),"4"));
					mm.put("rId",tt.get("ResourceId"));
					mm.put("equName",tt.get("EquType"));
					mm.put("goodQuantity",tt.get("goodQuantity"));
			    //上线  加工	
				}else if("40".equals(tt.get("Status").toString())||"50".equals(tt.get("Status").toString())){
					mm.put("Id",tt.get("Id"));
					mm.put("Name",tt.get("Name"));
					mm.put("TprocessName", tt.get("TprocessName"));//工序名称
					mm.put("Status",tt.get("Status"));
					mm.put("StartDate",tt.get("RealStarttime"));
					mm.put("EndDate",tt.get("EndDate"));
					mm.put("ResourceId",tt.get("ResourceId"));
					mm.put("planNum",tt.get("planNum"));
					mm.put("finishNum",tt.get("finishNum"));
					double rr=Double.parseDouble(tt.get("finishNum").toString())/Double.parseDouble(tt.get("planNum").toString())*100;
				    DecimalFormat df=new DecimalFormat("0.00"); 
					mm.put("Percentage",df.format(rr));
					mm.put("no",StringUtils.getSubString(tt.get("No").toString(),"4"));
					mm.put("rId",tt.get("ResourceId"));
					mm.put("equName",tt.get("EquType"));
					mm.put("goodQuantity",tt.get("goodQuantity"));
				//结束  完工
				}else{
					mm.put("Id",tt.get("Id"));
					mm.put("Name",tt.get("Name"));
					mm.put("TprocessName", tt.get("TprocessName"));//工序名称
					mm.put("Status",tt.get("Status"));
					mm.put("StartDate",tt.get("RealStarttime"));
					mm.put("EndDate",tt.get("RealEndtime"));
					mm.put("ResourceId",tt.get("ResourceId"));
					mm.put("planNum",tt.get("planNum"));
					mm.put("finishNum",tt.get("finishNum"));
					mm.put("Percentage",0);
					mm.put("no",StringUtils.getSubString(tt.get("No").toString(),"4"));
					mm.put("rId",tt.get("ResourceId"));
					mm.put("equName",tt.get("EquType"));
					mm.put("goodQuantity",tt.get("goodQuantity"));
				}
				result.add(mm);
			}
		}
		return result;
	}
	
	
	@Override
	public Map<String, Object> findJobPlanDetailDefaultBYDispatchIdDefault() {
	    String hqlString="select ID as jobPlanId,no as No,NAME as jobPlanName,planNum as jobPlanNum, "+
		                 " DATE_FORMAT(plan_starttime,'%Y-%m-%d') as starttime, DATE_FORMAT(plan_endtime,'%Y-%m-%d') as endtime, "+                         
				         " priority as priority,process as process,planNo as planId,partID as partId,"+
	                     " DATE_FORMAT(finish_date,'%Y-%m-%d') as finishDate,finishNum as jobPlanfinisNum"+
                         " from t_jobplan_info  where ID in(select jobplanID from t_job_info where ID in("+
                         " select jobId from t_jobdispatchlist_info where ID in (select max(ID) from t_jobdispatchlist_info)))";
		
	    List<Map<String,Object>> tempList=dao.executeNativeQuery(hqlString);
		if(tempList!=null&&tempList.size()>0) {
			return tempList.get(0);
		}
		return null;
	}
	

	@Override
	/**
	 * 根据作业计划id，查询作业计划信息
	 */
	public Map<String, Object> getJobPlanDetail(String jobplanId) {
		 String hqlString="select ID as jobPlanId,no as No,NAME as jobPlanName,planNum as jobPlanNum, "+
                 " DATE_FORMAT(plan_starttime,'%Y-%m-%d') as starttime, DATE_FORMAT(plan_endtime,'%Y-%m-%d') as endtime, "+                         
		         " priority as priority,process as process,planNo as planId,partID as partId,"+
                 " DATE_FORMAT(finish_date,'%Y-%m-%d') as finishDate,finishNum as jobPlanfinisNum"+
                 " from t_jobplan_info  where ID in(select jobplanID from t_job_info where ID in("+
                 " select jobId from t_jobdispatchlist_info where ID="+jobplanId+"))";
		
	    List<Map<String,Object>> tempList=dao.executeNativeQuery(hqlString);
		if(tempList!=null&&tempList.size()>0) {
			return tempList.get(0);
		}
		return null;
	}

	@Override
	public void addJobDispatchInfo(TJobdispatchlistInfo jobdispatchInfo) {
		dao.save(jobdispatchInfo);

	}

	@Override
	public void updateJobDispatchInfo(TJobdispatchlistInfo jobdispatchInfo) {
		dao.update(jobdispatchInfo);

	}

	@Override
	public void deleteJobDispatchInfo(TJobdispatchlistInfo jobdispatchInfo) {
		dao.delete(jobdispatchInfo);
		String sql="delete from t_equ_jobdispatch where jobdispatchNo='"+jobdispatchInfo.getNo()+"'";
		dao.executeNativeUpdate(sql);
	}

	@Override
	public String getMaxJobDispatchId() {
		String hql="select MAX(id) from TJobdispatchlistInfo " ;
		String mID=dao.executeQuery(hql).get(0).toString();
	    return mID;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getJobPlanList(String nodeId) {
		String hql = "SELECT NEW MAP(jobplan.id as id,jobplan.no as jobNo)"
				+" FROM TJobplanInfo jobplan,TUserProdctionPlan productionplan "
				+" WHERE jobplan.TUserProdctionPlan.id=productionplan.id" +
				"  AND productionplan.TNodes.id='"+nodeId+"' ";
		return dao.executeQuery(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getJobInfoMap(String nodeId)
	{
		String hql ="select new MAP(job.no as displayfield,job.id as valuefield,job.theoryWorktime as theoryWorktime)" +
				"from TJobInfo job where status=20 and job.TJobplanInfo.nodeid='"+nodeId+"' order by job.id desc";		
		return dao.executeQuery(hql);		
	}
	
	@Override
	/**
	 * 更新工单状态
	 */
	public Boolean updateJobdispatchStatus(String dispatchId, String status,String nowDate,String flag) {
		try{
			if("0".equals(flag))  //只启动选中工单
			{
			    String sql = "update t_jobdispatchlist_info t set t.status="+Integer.valueOf(status) + ",t.real_starttime='"+nowDate+"' where t.id="+Long.valueOf(dispatchId);
			    dao.executeNativeUpdate(sql);
			}else if("1".equals(flag))
			{   
				TJobdispatchlistInfo tJobdispatchlistInfo=dao.get(TJobdispatchlistInfo.class,Long.valueOf(dispatchId));
				String sql = "update t_jobdispatchlist_info t set t.status="+Integer.valueOf(status) + ",t.real_starttime='"+nowDate+"' where batchNo='"+tJobdispatchlistInfo.getBatchNo()+"'";
				dao.executeNativeUpdate(sql);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	public Boolean updateJobInfoStatusWhenAddJobPlan(String jobId,String status) {
		try{
			String sql = "update t_job_info t set t.status="+Integer.valueOf(status) + " where t.id="+Long.valueOf(jobId);
			dao.executeNativeUpdate(sql);
		    return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getStatusByJobPlanId(long jobPlanId,int status) {
		String hql = "SELECT NEW MAP(t.status as status)"
				+" FROM TJobInfo t"
				+" WHERE t.TJobplanInfo.id="+jobPlanId+" and t.status="+status;
		return dao.executeQuery(hql);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getStatusByJobPlanIdWhenStop(long jobPlanId) {
		String hql = "SELECT NEW MAP(t.status as status)"
				+" FROM TJobInfo t"
				+" WHERE t.TJobplanInfo.id="+jobPlanId+" and t.status=40 or t.status=50 ";
		return dao.executeQuery(hql);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Boolean updateJobPlanInfoStatusByjobStatus(String jobPlanId,String nowDate,String status,String flag) {
		try{
			String sql=null;
			String hql=null;
			if(flag=="new")
			{
				 hql="select new MAP(jobPlan.realStarttime as rtime) " +
							"from TJobplanInfo jobPlan where jobPlan.id="+Integer.valueOf(jobPlanId)+" and jobPlan.realStarttime is NULL";
					List<Map<String,Object>> realStartTimeList=dao.executeQuery(hql);
					if(realStartTimeList!=null&&realStartTimeList.size()>0) {
						 sql = "update t_jobplan_info t set t.status="+Integer.valueOf(status) + " where t.id="+Integer.valueOf(jobPlanId);
						 dao.executeNativeUpdate(sql);
					}
			}
			else if(flag=="start")
			{
				 hql="select new MAP(jobPlan.realStarttime as rtime) " +
						"from TJobplanInfo jobPlan where jobPlan.id="+Integer.valueOf(jobPlanId)+" and jobPlan.realStarttime is NULL";
				List<Map<String,Object>> realStartTimeList=dao.executeQuery(hql);
				if(realStartTimeList!=null&&realStartTimeList.size()>0) {
					 sql = "update t_jobplan_info t set t.status="+Integer.valueOf(status) + ",t.real_starttime='"+nowDate+"' where t.id="+Integer.valueOf(jobPlanId);
					 dao.executeNativeUpdate(sql);
				}
            }
		    return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Boolean updateJobPlanInfoStatusByjobStatusWhenStop(String jobPlanId, String nowDate, String status) {
        try {
            String sql = "update t_jobplan_info t set t.status=" + Integer.valueOf(status) + " where t.id=" + Integer.valueOf(jobPlanId);
            if (status.equals("50")) {
                dao.executeNativeUpdate(sql);
            } else if (status.equals("60")) {
                String hql = "select new MAP(jobPlan.realStarttime as rtime) " +
                        "from TJobplanInfo jobPlan where jobPlan.id=" + Integer.valueOf(jobPlanId) + " and jobPlan.realStarttime is NULL";
                List<Map<String, Object>> realStartTimeList = dao.executeQuery(hql);
                if (realStartTimeList != null && realStartTimeList.size() > 0) {
                    sql = "update t_jobplan_info t set t.status=" + Integer.valueOf(status) + ",t.real_endtime='" + nowDate + "' where t.id=" + Integer.valueOf(jobPlanId);
                    dao.executeNativeUpdate(sql);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean updateJobDispatchWhenStop(String jobdispatchId, String nowDate, String status, String flag) {
        try {
            if ("0".equals(flag)) {
                String hql = "update t_jobdispatchlist_info t set t.status=" + Integer.valueOf(status) + ",t.real_endtime='" + nowDate + "' where t.id=" + Long.valueOf(jobdispatchId);
                dao.executeNativeUpdate(hql);
            } else if ("1".equals(flag)) {
                TJobdispatchlistInfo tJobdispatchlistInfo = dao.get(TJobdispatchlistInfo.class, Long.valueOf(jobdispatchId));
                String hql = "update t_jobdispatchlist_info t set t.status=" + Integer.valueOf(status) + ",t.real_endtime='" + nowDate + "' where batchNo='" + tJobdispatchlistInfo.getBatchNo() + "'";
                dao.executeNativeUpdate(hql);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean getDispatchStatusByEquId(String equId) {
        String hql = "SELECT NEW MAP(jobdispatch.id AS Id)"
                + " FROM TJobdispatchlistInfo jobdispatch"
                + " WHERE jobdispatch.TEquipmentInfo.equId=" + Integer.valueOf(equId) + " and (jobdispatch.status=40 or jobdispatch.status=50)";
        List<Map<String, Object>> rs = dao.executeQuery(hql);
        return !(rs != null && rs.size() > 0);

    }


    /**
     * 工单暂停
     */
    public Boolean setStatusToOldstatus(String dispatchId, String status, String flag) {
        try {
            if ("0".equals(flag)) {
                String hql1 = "update t_jobdispatchlist_info t set t.oldStatus = t.status where t.id=" + Long.valueOf(dispatchId);
                dao.executeNativeUpdate(hql1);
                String hql2 = "update t_jobdispatchlist_info t set t.status = 80 where t.id=" + Long.valueOf(dispatchId);
                dao.executeNativeUpdate(hql2);
            } else if ("1".equals(flag)) {
                TJobdispatchlistInfo tJobdispatchlistInfo = dao.get(TJobdispatchlistInfo.class, Long.valueOf(dispatchId));
                String hql1 = "update t_jobdispatchlist_info t set t.oldStatus = t.status where t.status != 80 and batchNo='" + tJobdispatchlistInfo.getBatchNo() + "'";
                dao.executeNativeUpdate(hql1);
                String hql2 = "update t_jobdispatchlist_info t set t.status = 80 where  batchNo='" + tJobdispatchlistInfo.getBatchNo() + "'";
                dao.executeNativeUpdate(hql2);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 工单恢复
     */
    public Boolean updateJobdispatchWhenRecover(String dispatchId, String status, String flag) {
        try {
            String hql1 = "";
            if ("0".equals(flag)) {
                hql1 = "update t_jobdispatchlist_info t set t.status = t.oldStatus where t.id=" + Long.valueOf(dispatchId);
            } else if ("1".equals(flag)) {
                TJobdispatchlistInfo tJobdispatchlistInfo = dao.get(TJobdispatchlistInfo.class, Long.valueOf(dispatchId));
                hql1 = "update t_jobdispatchlist_info t set t.status = t.oldStatus where t.oldStatus is not null and batchNo='" + tJobdispatchlistInfo.getBatchNo() + "'";
            }
            dao.executeNativeUpdate(hql1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 工单新建 --通过零件名称得到工艺方案ID得到工序清单
     */
    @SuppressWarnings("unchecked")
	public List<Map<String, Object>> getProcessByProcessPlanId(String nodeid, String processPlanId) {
        String hql = "SELECT NEW MAP("
                + " c.id as id,"            //工序ID ===>
                + " c.no as no,"            //工序编码
                + " c.name as cname,"       //工序名称
                + " c.theoryWorktime as theoryWorktime,"   //节拍时间
                + " c.processDuration as processDuration," //工序连续
                + " c.TProcessplanInfo.id as pid,"     //工艺方案ID
                + " c.TProcessplanInfo.name as pname,"  //工艺方案名称
                + " b.id as bid "             //工艺方案ID
                + ")"
                + " FROM TProcessplanInfo b ,TProcessInfo c "
                + " WHERE  b.id=c.TProcessplanInfo.id "
                + "AND  c.status = 0  "
                + "AND b.id = '" + processPlanId + "' ";
        return dao.executeQuery(hql);


    }

    /**
     * 工单新建 -- 通过设备类型ID得到设备类型名称
     */
    @SuppressWarnings("unchecked")
	public String getPartTypeNameById(String eduTypeId) {
        String hql = "SELECT NEW MAP("
                + " t.id as id,"
                + " t.equipmentType as name)"
                + " FROM TEquipmenttypeInfo t "
                + " WHERE  t.id = '" + eduTypeId + "' ";
        List<Map<String, Object>> lst = dao.executeQuery(hql);
        String name = "";
        if (lst.size() > 0) {
            Map<String, Object> map = lst.get(0);
            name = (String) map.get("name");
        }
        return name;
    }

    /**
     * 工单新建 -- 工单保存 --工序直接生成的工单
     */
    @SuppressWarnings("unchecked")
	public void saveDispatch(JobdispatchAddBean jobdispatchAddBean) {
        List<Map<String, Object>> jobdispatchlist = jobdispatchAddBean.getJobdispatchlist();
        for (Map<String, Object> gg : jobdispatchlist) {
            TJobdispatchlistInfo tjp = new TJobdispatchlistInfo();
            tjp.setNo((String) gg.get("dno"));
            tjp.setName((String) gg.get("dname"));
            tjp.setBatchNo((String) gg.get("batchNo"));
            tjp.setTaskNum((String) gg.get("taskNum"));
            tjp.setProcessNum(Integer.parseInt((String) gg.get("num")));
			TProcessInfo tProcessInfo=null;    //工序
            if (!StringUtils.isEmpty(gg.get("id").toString())) {
                tProcessInfo = dao.get(TProcessInfo.class, Long.valueOf(gg.get("id").toString()));
                tjp.setTheoryWorktime(tProcessInfo.getTheoryWorktime());
                tjp.setTProcessInfo(tProcessInfo);
            }

            tjp.setCreateDate(new Date());
            TEquipmenttypeInfo tEquipmenttypeInfo = null; //设备类型ID
            if (!StringUtils.isEmpty((String) gg.get("equTypeId"))) {
                tEquipmenttypeInfo = dao.get(TEquipmenttypeInfo.class, Long.valueOf((String) gg.get("equTypeId")));
            }
            tjp.setTEquipmenttypeInfo(tEquipmenttypeInfo);
            String nodeid = jobdispatchAddBean.getNodeid();
            tjp.setNodeid(nodeid);
            tjp.setFinishNum(0);    //完成数量
            tjp.setOnlineNumber(0);  //上线数量
            tjp.setPlanStarttime(jobdispatchAddBean.getStartDate()); //计划开始时间
            tjp.setPlanEndtime(jobdispatchAddBean.getEndDate());     //计划结束时间
            tjp.setBadQuantity(0);
            tjp.setGoodQuantity(0);
            if (!StringUtils.isEmpty(jobdispatchAddBean.getPlanjobId()))
                tjp.setJobplanId(Long.parseLong(jobdispatchAddBean.getPlanjobId()));//工单计划id
            //工单新建时为待派工状态
            tjp.setStatus(20);                                       //状态
            tjp.setErpScrapNum(0);
            tjp.setWisScrapNum(0);
            tjp.setDutyScrapNum(0);
            dao.save(tjp);
            
            /*********************增加工单物料信息 ADD BY FW 20141121********************/
			String hql2=" select new Map(r.requirementNum as reqNum,"
					+ " t.id as materialId)"
					+ " from"
					+ " TProcessmaterialInfo r,TMaterailTypeInfo t"
					+ " where r.TMaterailTypeInfo.id = t.id"
					+ " and r.TProcessInfo.id ="+tProcessInfo.getId()+"";
			List<Map<String,Object>> materialList=dao.executeQuery(hql2);
			for(Map<String,Object> tt:materialList){
				TJobdispatchMaterial tj =new TJobdispatchMaterial();
				tj.setJobdispatchId(Integer.parseInt(tjp.getId().toString()));
				tj.setMaterialId(Integer.parseInt(tt.get("materialId").toString()));
				tj.setReqNum(Double.parseDouble(tt.get("reqNum").toString()));
				tj.setRecNum(0.0);
				tj.setPrice(0.00);
				dao.save(TJobdispatchMaterial.class,tj);
			}
			tjp.setTEquipmenttypeInfo(tEquipmenttypeInfo);
			nodeid  = jobdispatchAddBean.getNodeid();
			tjp.setNodeid(nodeid);
			tjp.setFinishNum(0);    //完成数量
			tjp.setOnlineNumber(0);  //上线数量
			tjp.setPlanStarttime(jobdispatchAddBean.getStartDate()); //计划开始时间
			tjp.setPlanEndtime(jobdispatchAddBean.getEndDate());     //计划结束时间
			tjp.setBadQuantity(0);   
			tjp.setGoodQuantity(0);
			if(!StringUtils.isEmpty(jobdispatchAddBean.getPlanjobId()))
			tjp.setJobplanId(Long.parseLong(jobdispatchAddBean.getPlanjobId()));//工单计划id
			//工单新建时为待派工状态
			tjp.setStatus(20);                                       //状态
			tjp.setErpScrapNum(0);
			tjp.setWisScrapNum(0);
			tjp.setDutyScrapNum(0);
			dao.save(tjp);
			
			/*********************A3增加工单设备后台关联***************************************/
//			String hql=" select new Map(t.equSerialNo as EQUSERIALNO) from TEquipmentInfo t where t.equTypeId="+gg.get("equTypeId");
			String hql=" select new Map(t.equSerialNo as EQUSERIALNO) from TEquipmentInfo t,TProcessEquipment process"
					+ " where t.equId=process.equipmentId"
					+ " and process.id="+gg.get("equTypeId");
			List<Map<String,Object>> equsList=dao.executeQuery(hql);
			for(Map<String,Object> rec:equsList)
			{
				TEquJobDispatch tEquJobDispatch=new TEquJobDispatch();
				tEquJobDispatch.setEquSerialNo((String)rec.get("EQUSERIALNO"));
				tEquJobDispatch.setJobdispatchNo((String)gg.get("dno"));
				tEquJobDispatch.setStatus(1);
				dao.save(TEquJobDispatch.class,tEquJobDispatch);
			}
			/**************************************************************************/
		}
	}
	
	/**
	 * 工单新建-- 获取零件类型集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPartTypeMap(String nodeid){
		String hql = "SELECT NEW MAP(" 
				+ " c.id as id,"     //零件类型ID
				+ " c.name as name"  //零件类型名称
				+ " )"
				+ " FROM TPartTypeInfo c WHERE c.nodeid ='"+nodeid+"' "
				+ " ORDER BY c.name ASC";	
		return dao.executeQuery(hql);	
	}
	
	/**
	 * 工单新建 -- 设备类型 -- 设备类型集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getEquTypeMap(String processId){
		String hql = "SELECT NEW MAP(" 
				+" c.id as id,"              //设备类型ID
				+" c.equipmentType as name"  //设备类型名称
				+")"
				+" FROM TEquipmenttypeInfo c,TProcessEquipment pe,TProcessInfo p "
				+ "WHERE c.id = pe.equipmentTypeId "
				+ "AND pe.processId = p.id "
				+ "AND p.id = '"+processId+"' ";
		return dao.executeQuery(hql);	
	}
	
	/**
	 * 工单修改 --通过工单ID获取工单信息
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getJobDispatchById(String nodeid,String disPatchId){
		Map<String,Object> map = new HashMap<String,Object>();
		//通过工单ID得到工单
	      String hql = "SELECT NEW MAP(" 
		        +" j.name as name,"                           //工单名称
		        +" j.processNum as processNum,"               //工单数量
		        +" j.status as status,"
		        +" j.TEquipmenttypeInfo.id as id,"             //设备类型 
		        +" part.name as partName,"//零件名称
				+" DATE_FORMAT(j.planStarttime,'%Y-%m-%d %T') AS planStarttime," //开始时间
				+" DATE_FORMAT(j.planEndtime,'%Y-%m-%d %T') AS planEndtime,"  //结束时间         
				+" j.remark as remark"                        //细节说明
				+")"
				+" FROM TJobdispatchlistInfo j,"
				+ " TProcessInfo process,"
				+ " TProcessplanInfo processPlan,"
				+ " TPartTypeInfo part"
		        + " WHERE j.TProcessInfo.id = process.id"
		        + " and process.TProcessplanInfo = processPlan.id"
		        + " and processPlan.TPartTypeInfo.id = part.id"
		        + " and j.id = '"+disPatchId+"'  ";	
	    List<Map<String,Object>> lst =  dao.executeQuery(hql);
        map.put("lst", lst);
		return map;
		
	}
	
	/**
	 * 工单修改  --查询名称是否重复
	 */
	@SuppressWarnings("unchecked")
	public boolean getDispatchNameRepeat(String dispatchName){
	      String hql = "SELECT NEW MAP(" 
			        +" j.name as name,"                           //工单名称
			        +" j.processNum as processNum"               //工单数量
					+")"
					+" FROM TJobdispatchlistInfo j    "
			        +" WHERE j.name = '"+dispatchName+"'  ";	
		    List<Map<String,Object>> lst =  dao.executeQuery(hql);
		    boolean b = false;
		    if(lst.size()>0){  //已经存在
		    	b = true;
		    }
		    return b;
	}
	
	/**
	 * 工单修改  --修改
	 */
	public void updataJobdispatchlist(JobdispatchUpdataBean jobdispatchUpdataBean){
		TJobdispatchlistInfo tjp = null;
		if(!StringUtils.isEmpty(jobdispatchUpdataBean.getJobdispatchlistId())){
			tjp=dao.get(TJobdispatchlistInfo.class, Long.valueOf(jobdispatchUpdataBean.getJobdispatchlistId()));
		}
		tjp.setName(jobdispatchUpdataBean.getJobdispatchlistName());
		if(jobdispatchUpdataBean.getJobdispatchlistNum()!=null){
			tjp.setProcessNum(Integer.parseInt(jobdispatchUpdataBean.getJobdispatchlistNum()));
		}
		TEquipmenttypeInfo tEquipmenttypeInfo=null; //设备类型ID
		if(!StringUtils.isEmpty(jobdispatchUpdataBean.getEquipmentTypeId())){
			tEquipmenttypeInfo=dao.get(TEquipmenttypeInfo.class, Long.valueOf(jobdispatchUpdataBean.getEquipmentTypeId()));
		}
		tjp.setTEquipmenttypeInfo(tEquipmenttypeInfo);
		tjp.setPlanStarttime(jobdispatchUpdataBean.getJobdispatchlistStartDate());
		tjp.setPlanEndtime(jobdispatchUpdataBean.getJobdispatchlistEndDate());
//		tjp.setRemark(jobdispatchUpdataBean.getJobdispatchlistDec());
		if("true".equals(jobdispatchUpdataBean.getStartJob()) && tjp.getStatus() != 80){
			tjp.setStatus(40);
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			tjp.setRealStarttime(new Date());
		}
		dao.update(tjp);
	}
	
	/**
	 * 工单修改 -- 设备类型 -- 设备类型集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getEquTypeMapByDisPatchId(String dispacthId){
		String hql = "SELECT NEW MAP(" 
				+" c.id as id,"              //设备类型ID
				+" c.equipmentType as name"  //设备类型名称
				+")"
				+" FROM TEquipmenttypeInfo c,TProcessEquipment pe,TProcessInfo p,TJobdispatchlistInfo t "
				+ "WHERE c.id = pe.equipmentTypeId "
				+ "AND pe.processId = p.id "
				+ "AND p.id = t.TProcessInfo.id "
				+ "AND t.id = '"+dispacthId+"' ";
		return dao.executeQuery(hql);	
	}

	/**
	 * 查询工单状态
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getJobStatus() {
		String hql = "select new Map(t.id as Id, t.name as Name) from TStatusInfo t";
		return dao.executeQuery(hql);
	}

	/**
	 * 查询工单表中的批准信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getBatchNoList(String nodeId,String query) {
		String hql = "select new Map(t.taskNum as Name, t.taskNum as value) "
				+ " from TJobdispatchlistInfo t "
				+ " where t.nodeid='"+nodeId+"'";
		if(!StringUtils.isEmpty(query)){
			hql += " and t.taskNum like '%"+query+"%'";
		}
		hql += " GROUP BY t.taskNum";
		return dao.executeQuery(hql);
	}

	@Override
	public List<Map<String, Object>> getDevicesInfo(String nodeId,
			String jobState, String partName, String batchId) {
		
		List<Map<String, Object>> serailList = findTJobdispatch(nodeId, jobState, batchId);
		if(!jobState.isEmpty() && !batchId.isEmpty()){
			if(serailList == null){
				return null;
			}
		} 
		
		if(!partName.isEmpty()){
			List<Map<String, Object>> listMap = findEquipmentByPartName(nodeId, partName);
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			for(Map<String, Object> map : serailList){
				String name = map.get("Name").toString();
				for(Map<String, Object> map1 : listMap){
					if(name.equals(map1.get("Name").toString())){
						result.add(map1);
					}
				}
			}
			return result;
		}else{
			return serailList;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findEquipmentByPartName(String nodeid, String partName) {
		String partId = partName.split("@#")[0];
		String sql = "select tp.no as No from T_Process_Info tp where tp.nodeid='"+nodeid+"' and tp.processplanid=" + partId +" group by No";
		List<Map<String, Object>> listProcess= dao.executeNativeQuery(sql);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : listProcess){
			if(map.get("No") != null && !"".equals(map.get("No"))){
				String no = map.get("No").toString();
				String hql = "select new Map(te.jobdispatchNo as dispatchNo,te.equSerialNo as Name) from TEquJobDispatch te where te.jobdispatchNo like '%"+no+"%'";
				List<Map<String,Object>> equ_job = dao.executeQuery(hql);
				for(Map<String,Object> map1 : equ_job){
					if(map1.get("Name") != null && !"".equals(map1.get("Name"))){
						list.add(map1);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 通过状态和批准查询设备名称
	 * @param nodeId
	 * @param jobState
	 * @param batchId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> findTJobdispatch(String nodeId, String jobState, String batchId){
		
		String hql = "select new Map(t.no as No,t.status as Status) from TJobdispatchlistInfo t where t.nodeid='"+nodeId+"'";
		if(!jobState.isEmpty()){
			hql += "and t.status = "+Integer.parseInt(jobState);
		}
		if(!batchId.isEmpty()){
			hql += " and t.batchNo = '" + batchId +"'";
		}
		
		if(!batchId.isEmpty() || !jobState.isEmpty()){
			List<Map<String, Object>> listMap2 = dao.executeQuery(hql);//dao.executeQuery(tJob_hql);
			StringBuffer noStr = new StringBuffer();
			for(Map<String, Object> map:listMap2){
				if(map.get("No") != null && !"".equals(map.get("No"))){
					noStr.append("'").append(map.get("No")).append("'").append(",");
				}
			}
			if(listMap2.size()>0){
				noStr.deleteCharAt(noStr.length()-1);
				String noStrSql = "select t.jobdispatchNo as No, t.equ_SerialNo as Name from t_equ_jobdispatch t where t.jobdispatchNo in("+noStr+")";
				return dao.executeNativeQuery(noStrSql);
			}
		}
		
		return null;
	}

	/**
	 * 查询设备信息关联的序列号
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getEquJobDispatchList(
			String id,HttpSession session) {
		List<Map<String, Object>> job_dispatch_list = getJobPatchNoById(id);
		String no = "";
		for(Map<String, Object> map : job_dispatch_list){
			if(map.get("Name") != null && !"".equals(map.get("Name"))){
				no = map.get("no").toString();
				break;
			}
		}
		String hql = "select new Map(t.id as ID,t.jobdispatchNo as patchNo,t.equSerialNo as SerialNo,(case when t.status = 0 then '未派' when t.status = 1 then '已派' when t.status = 2 then '加工中' end) as Status, t.status as flagStatus) from TEquJobDispatch t where t.jobdispatchNo='"+no+"'";
		List<Map<String, Object>> list=dao.executeQuery(hql);
		Locale locale=SessionHelper.getCurrentLocale(session);
		if(locale.toString().equals("en") || locale.toString().equals("en_US")){
			for(Map<String, Object> map:list){
			String status =	map.get("Status").toString();
				if(status.equals("未派")){
					status="to be Dispatched";
					map.put("Status", status);
				}else if(status.equals("已派")){
					status="Dispatched";
					map.put("Status", status);
				}else if(status.equals("加工中")){
					status="Processing";
					map.put("Status", status);
				}
			}
		}else{
			return list;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getJobPatchNoByName(String name) {
		String hql = "select new Map(t.no as no,t.name as Name) from TJobdispatchlistInfo t where t.name='"+name+"'";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 通过id查询TJobdispatchlistInfo表中信息
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getJobPatchNoById(String id) {
		String hql = "select new Map(t.no as no,t.name as Name) from TJobdispatchlistInfo t where t.id="+id;
		return dao.executeQuery(hql);
	}
	
	/**
	 * 通过工单id查询设备
	 * @param dispatchNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TEquJobDispatch> getJobDispatchById(String dispatchNo){
		String hql = "from TEquJobDispatch t where t.status<>0 and t.jobdispatchNo='"+dispatchNo+"'";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 通过工单名称查询工单信息
	 */
	@SuppressWarnings("unchecked")
	public List<TJobdispatchlistInfo> getJobDispatchInfo(String id){
		String hql = "from TJobdispatchlistInfo t where t.id="+id;
		return dao.executeQuery(hql);
	}
	
	/**
	 * 更改工单状态
	 */
	public boolean updateDispatch(String jobid,int status){
		List<TJobdispatchlistInfo> list = this.getJobDispatchInfo(jobid);
		for(TJobdispatchlistInfo t : list){
			if(status==60){//当手工关闭工单时，给以结束时间
				Date date = new Date();
				t.setStatus(status);
				t.setRealEndtime(date);
			}else if(status == 80){//暂定功能，将status赋值给oldstatus
				int oldStatus = t.getStatus();
				t.setOldStatus(oldStatus);
				t.setStatus(status);
			}else if(status == 50){//当status等于50时，工单恢复将oldstatus赋值给status
				t.setStatus(t.getOldStatus());
			}else if(status==40){//手动启动工单时，加入真实开始时间
				t.setStatus(status);
				t.setRealStarttime(new Date());
			}else{
				t.setStatus(status);
			}
			dao.save(t);
		}
		return true;
	}
	
	/**
	 * 获取报表数据
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getJobDispatchReportData(String nodeid,
			String descParam, String startTime, String endTime,
			String partName, String batchNo) {
		String hql = " select new Map(part.name as partName,"
				+ " dispatch.taskNum as taskNum,"
				+ " process.name as processName,"
				+ " dispatch.processNum as planNum,"
				+ " dispatch.finishNum as finishNum,"
				+ " equ_job.equSerialNo as equSerialNo,"
				+ " (dispatch.onlineNumber-dispatch.finishNum) as workInNum,"
				+ " dispatch.processNum as processNum,"
				+ " dispatch.onlineNumber as onlineNumber,"
				+ " (case when (process.offlineProcess = 1 and process.onlineProcessId is null) then '1' when process.offlineProcess = 1 then '2' when process.onlineProcessId is null then '3' else '4' end) as flag)"
				+ " from TPartTypeInfo part,"
				+ " TJobdispatchlistInfo dispatch,"
				+ " TProcessInfo process,"
				+ " TProcessplanInfo processplan," 
				+ " TEquJobDispatch equ_job"
				+ " where dispatch.TProcessInfo.id = process.id"
				+ " and process.TProcessplanInfo.id = processplan.id"
				+ " and processplan.TPartTypeInfo.id = part.id"
				+ "	and equ_job.jobdispatchNo = dispatch.no"
				+ " and equ_job.status <> 0"
				+ " and part.nodeid='"+nodeid+"'";
//						+ " and processplan.id=15";
		if(startTime != null && !startTime.isEmpty()){
			hql += " and dispatch.planStarttime >= DATE_FORMAT('"+startTime+"','%Y-%m-%d %T')  "; 
		}	
		if(endTime != null && !endTime.isEmpty()){
			hql += " and dispatch.planEndtime <= DATE_FORMAT('"+endTime+"','%Y-%m-%d %T')  "; 
		}
		if(partName != null && !partName.isEmpty()){
			hql += " and part.id = "+Long.parseLong(partName);
		}
		if(batchNo != null && !batchNo.isEmpty()){
			hql += " and dispatch.batchNo ='"+batchNo+"'";
		}
		if(descParam != null && !descParam.isEmpty()){
			hql += " ORDER BY equ_job.equSerialNo,part.name,process.name desc";
		}else{
			hql += " ORDER BY part.name,dispatch.taskNum,process.name asc";
		}
		return dao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getequNameList(String nodeid, String query) {
		String hql = "select DISTINCT new MAP(equ.equId AS Id," 
				+ " equ.equSerialNo AS Name,equ.equName as equName)"
				+ " from TEquipmentInfo equ where 1 = 1";
		if(!StringUtils.isEmpty(query)){
			hql += " and equ.equName like '%"+query+"%'";
		}
		return dao.executeQuery(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDevicesInfo(String nodeId){
		String hql = "SELECT DISTINCT NEW MAP(equ.equId AS Id," 
				+ " equ.equSerialNo AS Name,"
				+ "	equ.equName as equName) "
				+ " FROM TEquipmentInfo equ"
				+ " WHERE equ.TNodes.TNodes.id='"+nodeId+"'";
	
		return dao.executeQuery(hql);
	}
	
}

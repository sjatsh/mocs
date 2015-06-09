package smtcl.mocs.services.jobplan.impl;

import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.job.TEquJobDispatch;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.services.jobplan.UpdataJobDispatch;

public class UpdataJobDispatchImpl extends GenericServiceSpringImpl<TEquJobDispatch, String> implements UpdataJobDispatch {

	/**
	 * 工单接口实例
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	
	@Override
    @SuppressWarnings("unchecked")
	public void updataJobDispatchStatus(String serialNo, String status, String id) {
		String hql = "from TEquJobDispatch t where t.equSerialNo='"+serialNo+"' and t.jobdispatchNo='"+getbatchNo(id)+"'";

		List<TEquJobDispatch> list = dao.executeQuery(hql);
		if("recover".equals(status)){
			for(TEquJobDispatch t : list){
				t.setStatus(1);//0:删除，1:未指派，2:已派
				dao.update(t);
			}
		}else{
			for(TEquJobDispatch t : list){
				t.setStatus(0);
				dao.update(t);
			}
		}
	}
	
	public void save(String serialNo,String jobdispatchId){
		TEquJobDispatch t = new TEquJobDispatch();
		t.setEquSerialNo(serialNo);
		t.setStatus(1);
		t.setJobdispatchNo(getbatchNo(jobdispatchId));
		dao.save(t);
	}

	private String getbatchNo(String jobdispatchId){
		List<Map<String, Object>> job_dispatch_list = jobDispatchService.getJobPatchNoById(jobdispatchId);
		for(Map<String, Object> map : job_dispatch_list){
			if(map.get("Name") != null && !"".equals(map.get("Name"))){
				return map.get("no").toString();
			}
		}
		return null;
	}

	public List<Map<String,Object>> getBatchNoByPartName2(String nodeid,String startTime, String endTime,
			String partName, String batchNo){
		String hql = " select part.name as partName,"
				+ " dispatch.taskNum as taskNum,"
				+ " dispatch.processNum as planNum,"
				+ " dispatch.finishNum as finishNum,"
				+ " dispatch.processNum as processNum,"
				+ " dispatch.onlineNumber as onlineNumber,"
				+ " (case when (process.offlineProcess = 1 and process.onlineProcessId is null) then '1' "
				+ "		when process.offlineProcess = 1 then '2' "
				+ "		when process.onlineProcessId is null then '3' else '4' end) as flag"
				+ " from t_part_type_info part,"
				+ " t_jobdispatchlist_info dispatch,"
				+ " t_process_info process,"
				+ " t_processplan_info processplan"
				+ " where dispatch.processID = process.id"
				+ " and process.processPlanID = processplan.id"
				+ " and processplan.parttypeID = part.id"
				+ " and part.nodeid='"+nodeid+"'";
				if(startTime != null && !startTime.isEmpty()){
					hql += " and dispatch.plan_starttime >= DATE_FORMAT('"+startTime+"','%Y-%m-%d %T')  "; 
				}	
				if(endTime != null && !endTime.isEmpty()){
					hql += " and dispatch.plan_endtime <= DATE_FORMAT('"+endTime+"','%Y-%m-%d %T')  "; 
				}
				if(partName != null && !partName.isEmpty()){
					hql += " and part.id = "+Long.parseLong(partName);
				}
				if(batchNo != null && !batchNo.isEmpty()){
					hql += " and dispatch.batchNo ='"+batchNo+"'";
				}
				hql += " ORDER BY part.name,dispatch.batchNo,flag asc";
		return dao.executeNativeQuery(hql);
	}
	

	@SuppressWarnings("unchecked")
	public List<TEquJobDispatch> getEquJobDispatch(String equSerialNo,String dispatchNo){
		String hql = "from TEquJobDispatch t where t.equSerialNo='"+equSerialNo+"' and t.jobdispatchNo='"+dispatchNo+"'";
		return dao.executeQuery(hql);
	}
}

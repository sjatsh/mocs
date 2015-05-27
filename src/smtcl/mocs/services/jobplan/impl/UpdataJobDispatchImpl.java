package smtcl.mocs.services.jobplan.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.job.TEquJobDispatch;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.services.jobplan.UpdataJobDispatch;

public class UpdataJobDispatchImpl extends GenericServiceSpringImpl<TEquJobDispatch, String> implements UpdataJobDispatch {

	/**
	 * 工单接口实例
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	
	/**
	 * 更新TEquJobDispatch表的状态
	 * @param serialNo
	 * @param status
	 * @param jobdispatchlistName
	 */
	@Override
	public void updataJobDispatchStatus(String serialNo, String status, String id) {
		String hql = "from TEquJobDispatch t where t.equSerialNo='"+serialNo+"' and t.jobdispatchNo='"+getbatchNo(id)+"'";
		@SuppressWarnings("unchecked")
		List<TEquJobDispatch> list = dao.executeQuery(hql);
		if("recover".equals(status)){
			for(TEquJobDispatch t : list){
				t.setStatus(1);
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
		String patchNo = "";
		for(Map<String, Object> map : job_dispatch_list){
			if(map.get("Name") != null && !"".equals(map.get("Name"))){
				return map.get("no").toString();
			}
		}
		return null;
	}

	@Override
	public List<TEquJobDispatch> getEName(String no,String equid) {
		String hql = "from TEquJobDispatch t,TEquipmentInfo equ where t.jobdispatchNo='"+no+"'"
				+" and equ.equId="+equid
				+ " and equ.equSerialNo = t.equSerialNo";
		return dao.executeQuery(hql);
	}
	@Override
	public List<String> getPartNameList(String partTypeid){
		String hql = "select new Map(t.name as name) from TProcessInfo t where t.TProcessplanInfo.id="+partTypeid;
		List<Map<String,Object>> list = dao.executeQuery(hql);
		List<String> newList = new ArrayList<String>();
		for(Map<String,Object> map : list){
			if(map.get("name") != null){
				newList.add(map.get("name").toString());
			}
				
		}
		return newList;
	}
	
	public List<TJobdispatchlistInfo> getTJobdispatchlistInfo(String batchNo,String no){
		String hql = "from TJobdispatchlistInfo t where t.no='"+no+"'"
				+ "and t.batchNo='"+batchNo+"'";
		return dao.executeQuery(hql);
	}
	/**
	 * 通过零件名称查询工单批次信息
	 * @param partName
	 * @param nodeid
	 * @return
	 */
	public List<Map<String,Object>> getBatchNoByPartName(String nodeid){
		String hql = "select DISTINCT new Map(part.name as partName,job.batchNo as batchNo)"
				+ " from TJobdispatchlistInfo job,"
				+ " TProcessInfo process,"
				+ " TProcessplanInfo processPlan,"
				+ " TPartTypeInfo part"
				+ " where part.id=processPlan.TPartTypeInfo.id"
				+ " and processPlan.id=process.TProcessplanInfo.id"
				+ " and job.TProcessInfo.id = process.id"
				+ " and part.nodeid='"+nodeid+"'";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 通过零件名称查询工单批次信息
	 * @param partName
	 * @param nodeid
	 * @return
	 */
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
//						+ " and processplan.id=15";
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
	
	/**
	 * 通过设备和工单号获取关联信息
	 * @param equSerialNo
	 * @param dispatchNo
	 * @return
	 */
	public List<TEquJobDispatch> getEquJobDispatch(String equSerialNo,String dispatchNo){
		String hql = "from TEquJobDispatch t where t.equSerialNo='"+equSerialNo+"' and t.jobdispatchNo='"+dispatchNo+"'";
		return dao.executeQuery(hql);
	}
}

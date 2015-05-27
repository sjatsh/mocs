package smtcl.mocs.services.device.impl;

import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;

import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.services.device.IProcessService;

public class ProcessServiceImpl extends GenericServiceSpringImpl<TProcessInfo, String> implements IProcessService {

	/**
	 * 通过零件名称获取工序名称信息
	 * @param partTypeName 零件信息
	 * @param nodeid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getProcessInfo(String partTypeId,
			String nodeid) {
		String hql = "select new Map(t.no as no,t.name as name) from TPartProcessCost r,TProcessInfo t where r.TPartTypeInfo.id = "
				+ partTypeId
				+ " and r.TProcessInfo.id = t.id and t.nodeid='"
				+ nodeid + "'";
		return dao.executeQuery(hql);
	}

	/**
	 * 通过任务号，获取批准
	 */
	public String getBatchNoByNo(String no){
		String hql = "from TJobdispatchlistInfo t where t.no = '"+no+"'";
		List<TJobdispatchlistInfo> list = dao.executeQuery(hql);
		for(TJobdispatchlistInfo t : list){
			return t.getBatchNo();
		}
		return "";
	}
}

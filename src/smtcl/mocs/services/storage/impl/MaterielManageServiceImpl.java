package smtcl.mocs.services.storage.impl;

import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;

import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.storage.TMaterialVersion;
import smtcl.mocs.pojos.storage.TStorageInfo;
import smtcl.mocs.services.storage.IMaterielManageService;
import smtcl.mocs.utils.device.StringUtils;

public class MaterielManageServiceImpl extends GenericServiceSpringImpl<Object, String> implements IMaterielManageService{

	@Override
	public List<Map<String, Object>> materielQuery(String nodeid,
			String storage_id, String position_id, String materiel_id,
			String materielVersion, String materielDesc_id, String status) {
		
		String sql = " select "
						+ " materailType.no as no, "
						+ " materailType.name as name,"
						+ " sum(materialStorage.available_num) as availableNum,"
						+ " sum(materialStorage.retain_num) as retainNum,"
						+ " sum(materialStorage.available_num+materialStorage.retain_num) as totalNum"
				   + " from "
						+ " t_materail_type_info materailType,"
						+ " t_material_storage materialStorage"
				   + " where materailType.ID = materialStorage.material_id ";		
		if(!StringUtils.isEmpty(nodeid)){
			sql += " and materailType.nodeID='"+nodeid+"'";
		}
		if(!StringUtils.isEmpty((storage_id))){
			sql += " and materialStorage.storage_id in ("+(storage_id)+")";
		}
		if(!StringUtils.isEmpty(position_id)){
			sql += " and materialStorage.positon_id in ("+position_id+")";
		}
		if(!StringUtils.isEmpty(materiel_id)){
			sql += " and materailType.id in ("+materiel_id+")";
		}
		if(!StringUtils.isEmpty(materielVersion)){
			sql += " and materialStorage.version_no='"+materielVersion+"'";
		}
		if(!StringUtils.isEmpty(materielDesc_id)){
			sql += " and materailType.id in ("+materielDesc_id+")";
		}
		if(!StringUtils.isEmpty(status)){
			sql += " and materailType.status="+Integer.parseInt(status);
		}
		sql += " GROUP BY materailType.NO,materailType.NAME "
				+ "order by materailType.no asc";
		List<Map<String,Object>> listMap = dao.executeNativeQuery(sql);
		for(int i=0;i<listMap.size();i++){
			Map<String,Object> map = listMap.get(i);
			map.put("rownum", i+1);
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> materielDetailQuery(String nodeid,
			String storage_id, String position_id, String materiel_id,
			String materielVersion, String materielDesc_id, String status) {
		
		String sql = "select "
						+ " materailType.no as no, "
						+ " materailType.name as name,"
						+ " sum(materialStorage.available_num) as availableNum,"
						+ " materialStorage.unitName as unitName,"
						+ " storageInfo.storage_no as storageNo,"
						+ " position.positionNo as positionNo"
					+ " from "
						+ " t_materail_type_info materailType,"
						+ " t_material_storage materialStorage,"
						+ " t_storage_info storageInfo,"
						+ " t_materiel_position_info position"
					+ " where materailType.ID = materialStorage.material_id"
						+ " and materialStorage.storage_id = storageInfo.ID"
						+ " and materialStorage.positon_id = position.ID";
	
		if(!StringUtils.isEmpty(nodeid)){
			sql += " and materailType.nodeID='"+nodeid+"'";
		}
		if(!StringUtils.isEmpty((storage_id))){
			sql += " and materialStorage.storage_id in ("+(storage_id)+")";
		}
		if(!StringUtils.isEmpty(position_id)){
			sql += " and materialStorage.positon_id in ("+position_id+")";
		}
		if(!StringUtils.isEmpty(materiel_id)){
			sql += " and materailType.id in ("+materiel_id+")";
		}
		if(!StringUtils.isEmpty(materielVersion)){
			sql += " and materialStorage.version_no='"+materielVersion+"'";
		}
		if(!StringUtils.isEmpty(materielDesc_id)){
			sql += " and materailType.id in ("+materielDesc_id+")";
		}
		if(!StringUtils.isEmpty(status)){
			sql += " and materailType.status="+Integer.parseInt(status);
		}
		sql += " GROUP BY materailType. NO,materailType. NAME,storageInfo.storage_no,position.positionNo "
				+ "order by materailType.no asc";
		List<Map<String,Object>> listMap = dao.executeNativeQuery(sql);
		for(int i=0;i<listMap.size();i++){
			Map<String,Object> map = listMap.get(i);
			map.put("rownum", i+1);
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> materielBatchQuery(String nodeid,
			String materiel_id, String storage_id, String position_id,
			String batchStart, String batchEnd, String materielDesc_id,
			String waixieid) {
		
		String sql = "select "
						+ "materialStorage.batchNo as batchNo,"
						+ "materailType.no as no,"
						+ "materailType.name as name,"
						+ "DATE_FORMAT(materialStorage.processDate,'%Y-%m-%d') as processDate,"
						+ "sum(materialStorage.available_num) as available_num,"
						+ "sum(materialStorage.available_num+materialStorage.retain_num) as totalNum,"
						+ "materialStorage.unitName as unitName"
					+ " from "
						+ "t_material_storage materialStorage,"
						+ "t_materail_type_info materailType,"
						+ " t_storage_info storageInfo,"
						+ " t_materiel_position_info position"
					+ " where materailType.id = materialStorage.material_id"
					+ " and materialStorage.storage_id = storageInfo.ID"
					+ " and materialStorage.positon_id = position.ID";
		if(waixieid.equals("0")){//不显示外协
			sql += " and materialStorage.batchNo not in ('外协')";
		}else if(waixieid.equals("1")){//只显示外协
			sql += " and materialStorage.batchNo in ('外协')";
		}else if(waixieid.equals("2")){//显示全部
			
		}
		if(!StringUtils.isEmpty(nodeid)){
			sql += " and materailType.nodeID='"+nodeid+"'";
		}
		if(!StringUtils.isEmpty(materiel_id)){
			sql += " and materailType.id in ("+materiel_id+")";
		}
		if(!StringUtils.isEmpty((storage_id))){
			sql += " and materialStorage.storage_id in ("+(storage_id)+")";
		}
		if(!StringUtils.isEmpty(position_id)){
			sql += " and materialStorage.positon_id in ("+position_id+")";
		}
		if(!StringUtils.isEmpty(batchStart)){
			sql += " and materialStorage.batchNo>='"+batchStart+"'";
		}
		if(!StringUtils.isEmpty(batchEnd)){
			sql += " and materialStorage.batchNo<='"+batchEnd+"'";
		}
		if(!StringUtils.isEmpty(materielDesc_id)){
			sql += " and materailType.id in ("+materielDesc_id+")";
		}
		
		sql += "GROUP BY materialStorage.batchNo,materailType. NO,materailType. NAME "
				+ "order by materailType.no";
		List<Map<String,Object>> listMap = dao.executeNativeQuery(sql);
		for(int i=0;i<listMap.size();i++){
			Map<String,Object> map = listMap.get(i);
			map.put("rownum", i+1);
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> materielSeqQuery(String nodeid,
			String materiel_id, String storage_id, String position_id,
			String seqStart, String seqEnd, String materielDesc_id) {
		
		String sql = "select "
				+ " materialStorage.seqNo as seqNo,"
				+ " materailType.no as no,"
				+ " materailType.name as name,"
				+ " materialStorage.unitName as unitName,"
				+ " storageInfo.storage_no as storageNo,"
				+ " position.positionNo as positionNo"
			+ " from "
				+ " t_material_storage materialStorage,"
				+ " t_materail_type_info materailType,"
				+ " t_storage_info storageInfo,"
				+ " t_materiel_position_info position"
			+ " where materailType.id = materialStorage.material_id"
				+ " and materialStorage.storage_id = storageInfo.ID"
				+ " and materialStorage.positon_id = position.ID"
				+ " and materialStorage.seqNo <> ''";
		if(!StringUtils.isEmpty(nodeid)){
			sql += " and materailType.nodeID='"+nodeid+"'";
		}
		if(!StringUtils.isEmpty(materiel_id)){
			sql += " and materailType.id in ("+materiel_id+")";
		}
		if(!StringUtils.isEmpty((storage_id))){
			sql += " and materialStorage.storage_id in ("+(storage_id)+")";
		}
		if(!StringUtils.isEmpty(position_id)){
			sql += " and materialStorage.positon_id in ("+position_id+")";
		}
		
		if(!StringUtils.isEmpty(seqStart)){
			sql += " and materialStorage.seqNo>='"+seqStart+"'";
		}
		if(!StringUtils.isEmpty(seqEnd)){
			sql += " and materialStorage.seqNo<='"+seqEnd+"'";
		}
		if(!StringUtils.isEmpty(materielDesc_id)){
			sql += " and materailType.id in ("+materielDesc_id+")";
		}
		sql+=" order by materailType.no asc";
		List<Map<String,Object>> listMap = dao.executeNativeQuery(sql);
		for(int i=0;i<listMap.size();i++){
			Map<String,Object> map = listMap.get(i);
			map.put("rownum", i+1);
		}
		return listMap;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TStorageInfo> getStorageInfo(String nodeid) {
		String hql = "from TStorageInfo storageInfo where storageInfo.nodeId='"+nodeid+"'";
		return dao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getMaterielPositionInfo(String storage_id) {
		String hql = "select new Map(position.id as id,position.positionNo as positionNo) "
				+ " from TMaterielPositionInfo position,RStoragePosition storage_position"
				+ " where position.id = storage_position.TMaterielPositionInfo.id";
		if(!StringUtils.isEmpty(storage_id)){
			hql += "	and storage_position.TStorageInfo.id = "+Integer.parseInt(storage_id);
		}
				
		return dao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TMaterailTypeInfo> getMaterailTypeInfo(String nodeid) {
		String hql = " from TMaterailTypeInfo materail where materail.nodeId='"+nodeid+"' order by materail.no asc";
		return dao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TMaterialVersion> getMaterialVersion(String materiel_id) {
		String hql = "from TMaterialVersion version ";
		if(!StringUtils.isEmpty(materiel_id)){
			hql += "where version.materialId in ("+materiel_id+")";
		}
		return dao.executeQuery(hql);
	}

	@Override
	public List<Map<String, Object>> findbatchNo() {
		String sql ="SELECT"
						+ " storage_m.batchNo AS batchNo"
					+ " FROM"
						+ " t_material_storage storage_m"
					+ " WHERE"
						+ " storage_m.batchNo <> ''"
						+ " AND storage_m.batchNo NOT IN ('外协')"
						+ " group by storage_m.batchNo";
		return dao.executeNativeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> findseqNo() {
		String sql ="SELECT"
				+ " storage_m.seqNo AS seqNo"
			+ " FROM"
				+ " t_material_storage storage_m"
			+ " WHERE"
				+ " storage_m.seqNo <> ''"
				+ " group by storage_m.seqNo";
		return dao.executeNativeQuery(sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findStrogeNo(String nodeid,String storageNo) {
		String hql = "select new Map(storageInfo.id as id,storageInfo.storageNo as storageNo) from TStorageInfo storageInfo where storageInfo.nodeId='"+nodeid+"' and storageInfo.storageNo like '%"+storageNo+"%'";
		return dao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findMaterialInfo(String nodeid,
			String materialValue,String flag ) {
		String hql = "select new Map(materail.id as id,materail.no as no,materail.name as name) "
				+ " from TMaterailTypeInfo materail where materail.nodeId='"+nodeid+"'";
		if(flag.equals("no")){
			hql += " and materail.no like '%"+materialValue+"%'";
		}else{
			hql += " and materail.name like '%"+materialValue+"%'";
		}
		hql+=" order by materail.no asc";
		return dao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPositionInfo(String nodeid,
			String query) {
		String hql = "select new Map(position.id as id,position.positionNo as positionNo) "
				+ " from TMaterielPositionInfo position where position.positionNo like '%"+query+"%'";
		return dao.executeQuery(hql);
	}

	
	
}

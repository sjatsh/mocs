package smtcl.mocs.services.storage.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import smtcl.mocs.model.TMaterialPositionModel;
import smtcl.mocs.model.TStorageInfoModel;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.storage.RStoragePosition;
import smtcl.mocs.pojos.storage.TJobdispatchMaterial;
import smtcl.mocs.pojos.storage.TMaterialStorage;
import smtcl.mocs.pojos.storage.TMaterielPositionInfo;
import smtcl.mocs.pojos.storage.TStockMaterialbath;
import smtcl.mocs.pojos.storage.TStockMaterialseq;
import smtcl.mocs.pojos.storage.TStorageInfo;
import smtcl.mocs.pojos.storage.TTransaction;
import smtcl.mocs.pojos.storage.TTransactionRelation;
import smtcl.mocs.services.storage.IStorageManageService;
import smtcl.mocs.utils.device.StringUtils;



/**
 * 
 * @version V1.0
 * @����ʱ�� 2014-08-27
 * @���� FW
 */
public class StorageManageImpl extends GenericServiceSpringImpl<Object, String>  implements IStorageManageService {
	
	@Override
	/**
	 * ��ȡ�ⷿ��Ϣ����
	 */
	public List<TStorageInfoModel> getStorageList(String str,String nodeId) {
		
//		Collection<Parameter> parameters = new HashSet<Parameter>();
//		String hql = "select new Map( "
//				+ "s.id as id, "
//				+ "s.storageNo as no, "
//				+ "s.storageName as name, "
//				+ "s.storageStatus as status, "
//				+ "s.invalidDate as invalidDate, "
//				+ "case s.isAvailable when 1 then '��' when 2 then '��' else '����' end as isAvailable, "
//				+ "case s.planType when 1 then '��С-���' when 2 then '�ڶ�����' when 3 then '���岹��' when 4 then '�̵㲹��' else '����' end as planType, "
//				+ "case s.positionType when 1 then '��' when 2 then 'Ԥָ��' when 3 then '��̬����' when 4 then '���ϲ�' else '����' end as positionType, "
//				+ "s.address as address, "
//				+ "s.storageMan as storageMan, "
//				+ "s.createDate as createDate, "
//				+ "s.preProcessTime as preProcessTime, "
//				+ "s.inProcessTime as inProcessTime, "
//				+ "s.sufProcessTime as sufProcessTime ) "
//				+ "from TStorageInfo s ";
				Collection<Parameter> parameters = new HashSet<Parameter>();
				List<TStorageInfo> rslist=new ArrayList<TStorageInfo>();
				List<TStorageInfoModel> list=new ArrayList<TStorageInfoModel>();
				String hql="from TStorageInfo where nodeId ='"+nodeId+"'";
		
				if(!StringUtils.isEmpty(str)){
					hql = hql +" and (storageNo like '%"+str+"%'"
							+ " or storageStatus like '%"+str+"%'"
							+ " or storageName like '%"+str+"%')";
				}
				rslist=dao.executeQuery(hql, parameters);
				
				
				for(TStorageInfo tt:rslist){
					TStorageInfoModel temp =new TStorageInfoModel();
					//�ɾ���
					if(tt.getIsAvailable().equals("1")){
						temp.setIsAvailable("��");
					}else{
						temp.setIsAvailable("��");
					}
					//�ƻ���ʽ
					if(tt.getPlanType().equals("1")){
						temp.setPlanType("��С-���");
					}else if(tt.getPlanType().equals("2")){
						temp.setPlanType("�ڶ�����");
					}else if(tt.getPlanType().equals("3")){
						temp.setPlanType("���岹��");
					}else{
						temp.setPlanType("�̵㲹��");
					}
					//���Ʒ�ʽ
					if(tt.getPositionType().equals("1")){
						temp.setPositionType("��");
					}else if(tt.getPositionType().equals("2")){
						temp.setPositionType("Ԥָ��");
					}else if(tt.getPositionType().equals("3")){
						temp.setPositionType("��̬����");
					}else{
						temp.setPositionType("���ϲ�");
					}
					
					temp.setId(tt.getId());
					temp.setStorageNo(tt.getStorageNo());
					temp.setStorageName(tt.getStorageName());
					temp.setStorageStatus(tt.getStorageStatus());
					temp.setInvalidDate(tt.getInvalidDate().toString());
					temp.setAddress(tt.getAddress());
					temp.setStorageMan(tt.getStorageMan());
					temp.setCreateDate(tt.getCreateDate());
					temp.setPreProcessTime(tt.getPreProcessTime());
					temp.setInProcessTime(tt.getInProcessTime());
					temp.setSufProcessTime(tt.getSufProcessTime());
					temp.setNodeId(tt.getNodeId());
					list.add(temp);
				}
			
		        return list;

	}

	@Override
	/**
	 * ���¿ⷿ��Ϣ
	 */
	public String updateStorageInfo(TStorageInfo tStorageInfo, int flag) {
		try{
			if(flag ==1){
				dao.save(tStorageInfo);
				return "�ɹ�";
			}else{
				dao.update(tStorageInfo);
				return "�ɹ�";
			}
		}catch(Exception exp){
			exp.printStackTrace();
			
			return "����ʧ��";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ��ȡ��λ��Ϣ����
	 */
	public List<TMaterialPositionModel> getMaterialPositionList(String str,String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "select new Map( "
				+ "m.id as id, "
				+ "m.positionNo as positionNo, "
				+ "m.positionName as positionName, "
				+ "m.positionStatus as positionStatus,"
				+ "m.quantitySize as quantitySize, "
				+ "m.quantityUnit as quantityUnit, "
				+ "m.capacitySize as capacitySize, "
				+ "m.capacityUnit as capacityUnit, "
				+ "m.weightSize as weightSize, "
				+ "m.weightUnit as weightUnit, "
				+ "m.dimensionSize as dimensionSize, "
				+ "m.dimensionUnit as dimensionUnit, "
				+ "m.axis as axis, "
				+ "s.id as sid, "
				+ "s.storageNo as storageNo, "
				+ "s.storageName as storageName) "
				+ "from TMaterielPositionInfo m,RStoragePosition r, TStorageInfo s "
				+ "where r.TMaterielPositionInfo.id = m.id "
				+ "and r.TStorageInfo.id = s.id "
				+ "and s.nodeId ='"+nodeId+"'";
				if(!StringUtils.isEmpty(str)){
					hql = hql +" and (m.positionNo like '%"+str+"%'"
							+ " or m.positionName like '%"+str+"%'"
							+ " or s.storageName like '%"+str+"%')";
				} 
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		List<TMaterialPositionModel> list = new ArrayList<TMaterialPositionModel>();
		for(Map<String,Object> ss :dataList){
			TMaterialPositionModel temp = new TMaterialPositionModel();
			temp.setId(Integer.parseInt(ss.get("id").toString()));
			temp.setPositionNo(ss.get("positionNo").toString());
			temp.setPositionName(ss.get("positionName").toString());
			if(ss.get("positionStatus").toString().equals("1")){
				temp.setPositionStatus("�");
			}else{
				temp.setPositionStatus("��ֹ");
			}
			
			if(null !=ss.get("quantitySize") && !StringUtils.isEmpty(ss.get("quantitySize").toString())){
				temp.setQuantitySize(Double.valueOf(ss.get("quantitySize").toString()));
			}
			
			if(null !=ss.get("quantityUnit") && !StringUtils.isEmpty(ss.get("quantityUnit").toString())){
				temp.setQuantityUnit(ss.get("quantityUnit").toString());
			}
			
			if(null !=ss.get("capacitySize") && !StringUtils.isEmpty(ss.get("capacitySize").toString())){
				temp.setCapacitySize(Double.valueOf(ss.get("capacitySize").toString()));
			}
			
			if(null !=ss.get("capacityUnit") && !StringUtils.isEmpty(ss.get("capacityUnit").toString())){
				temp.setCapacityUnit(ss.get("capacityUnit").toString());
			}
			
			if(null !=ss.get("weightSize") && !StringUtils.isEmpty(ss.get("weightSize").toString())){
				temp.setWeightSize(Double.valueOf(ss.get("weightSize").toString()));
			}
			
			if(null !=ss.get("weightUnit") && !StringUtils.isEmpty(ss.get("weightUnit").toString())){
				temp.setWeightUnit(ss.get("weightUnit").toString());
			}
			if(null !=ss.get("dimensionSize") && !StringUtils.isEmpty(ss.get("dimensionSize").toString())){
				temp.setDimensionSize(ss.get("dimensionSize").toString());
			}
			if(null !=ss.get("dimensionUnit") && !StringUtils.isEmpty(ss.get("dimensionUnit").toString())){
				temp.setDimensionUnit(ss.get("dimensionUnit").toString());
			}
			if(null !=ss.get("axis") && !StringUtils.isEmpty(ss.get("axis").toString())){
				temp.setAxis(ss.get("axis").toString());
			}
			
			temp.setSid(Integer.parseInt(ss.get("sid").toString()));
			temp.setStorageNo(ss.get("storageNo").toString());
			temp.setStorageName(ss.get("storageName").toString());
			list.add(temp);
		}
		return list;
	}

	@Override
	/**
	 * ���»�λ��Ϣ
	 */
	public String updateMaterialPositionInfo(
			TMaterielPositionInfo tMaterielPositionInfo, int flag,String storageId) {
		try{
			if(flag ==1){
				dao.save(tMaterielPositionInfo);
				
				//�м�����ݲ���
				RStoragePosition rs = new RStoragePosition();
				
				//�ⷿ��Ϣ
				TStorageInfo storageInfo = new TStorageInfo();
				storageInfo = dao.get(TStorageInfo.class,Integer.parseInt(storageId));
				
				rs.setTMaterielPositionInfo(tMaterielPositionInfo);
				rs.setTStorageInfo(storageInfo);
				dao.save(rs);
				return "�ɹ�";
			}else{
				dao.update(tMaterielPositionInfo);
				
				//�м�����ݸ���
				Collection<Parameter> parameters = new HashSet<Parameter>();
				
				//�ⷿ��Ϣ
				TStorageInfo storageInfo = new TStorageInfo();
				storageInfo = dao.get(TStorageInfo.class,Integer.parseInt(storageId));
				
				List<RStoragePosition> rslist=new ArrayList<RStoragePosition>();
				String hql="from RStoragePosition where "
						+ "TMaterielPositionInfo.id ="+tMaterielPositionInfo.getId()+"";
				rslist =dao.executeQuery(hql, parameters);
				for(RStoragePosition t:rslist){
					t.setTStorageInfo(storageInfo);
					dao.update(t);
				}
				return "�ɹ�";
			}
		}catch(Exception exp){
			exp.printStackTrace();
			return "����ʧ��";
		}
	}

	@Override
	/**
	 * �ⷿɾ������״̬��Ϊ��ֹ��
	 */
	public String delStorageInfo(TStorageInfo tStorageInfo) {
		try{
			TStorageInfo ts = new TStorageInfo();
			ts = dao.get(TStorageInfo.class,tStorageInfo.getId());
			ts.setStorageStatus("��ֹ");
			dao.update(ts);
			return "�ɹ�";
		}catch(Exception exp){
			return "����ʧ��";
		}
	}

	@Override
	public String delMaterielPositionInfo(
			TMaterielPositionInfo tMaterielPositionInfo) {
		try{
			int id = tMaterielPositionInfo.getId();
		    
		    
		    //�м������ɾ��
		    Collection<Parameter> parameters = new HashSet<Parameter>();
			RStoragePosition rs = new RStoragePosition();
			List<RStoragePosition> rsList = new ArrayList<RStoragePosition>();
			String hql="from RStoragePosition where TMaterielPositionInfo.id ='"+id+"'";
			rsList =dao.executeQuery(hql,parameters);
			for(RStoragePosition tt:rsList){
				rs = dao.get(RStoragePosition.class,tt.getId());
				dao.delete(rs);
			}
			
			dao.delete(tMaterielPositionInfo);
			
		    return "�ɹ�"; 
		}catch(Exception exp){
			exp.printStackTrace();
			return "����ʧ��";
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getList(String hql) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}
     /**
      * ��ȡ��Ա��Ϣ
      */
	@Override
	public List<Map<String, Object>> userList(String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql ="select new Map( "
				+ "m.id as id, "
				+ "m.name as name) "
				+ "from TMemberInfo m where m.nodeid ='"+nodeId+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}
   
	/**
	 * ��ȡ�����λ
	 */
	@Override
	public List<Map<String, Object>> capacityUnitList(String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql ="select new Map( "
				+ "u.id as id, "
				+ "u.unitName as name) "
				+ "from TUnitInfo u,TUnitType t "
				+ "where u.TUnitType.id =t.id "
				+ "and t.unitTypeName ='�����λ' and t.nodeId ='"+nodeId+"' and u.nodeId ='"+nodeId+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}
    
	/**
	 * ��ȡ�ⷿ��Ϣ
	 */
	@Override
	public List<Map<String, Object>> storgeList(String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql ="select new Map( "
				+ "s.id as id, "
				+ "s.storageNo as no,"
				+ "s.positionType as positionType) "
				+ "from TStorageInfo s where s.nodeId ='"+nodeId+"' and s.storageStatus ='�'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}
    
	/**
	 * ��ȡ������λ
	 */
	@Override
	public List<Map<String, Object>> quantityUnitList(String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql ="select new Map( "
				+ "u.id as id, "
				+ "u.unitName as name) "
				+ "from TUnitInfo u,TUnitType t "
				+ "where u.TUnitType.id =t.id "
				+ "and t.unitTypeName ='������λ' and t.nodeId ='"+nodeId+"' and u.nodeId ='"+nodeId+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}
	/**
	 * ��ȡ������λ
	 */
	@Override
	public List<Map<String, Object>> weightUnitList(String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql ="select new Map( "
				+ "u.id as id, "
				+ "u.unitName as name) "
				+ "from TUnitInfo u,TUnitType t "
				+ "where u.TUnitType.id =t.id "
				+ "and t.unitTypeName ='������λ' and t.nodeId ='"+nodeId+"' and u.nodeId ='"+nodeId+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}
	/**
	 * ��ȡά�ȵ�λ
	 */
	@Override
	public List<Map<String, Object>> dimensionUnitList(String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql ="select new Map( "
				+ "u.id as id, "
				+ "u.unitName as name) "
				+ "from TUnitInfo u,TUnitType t "
				+ "where u.TUnitType.id =t.id "
				+ "and t.unitTypeName ='ά�ȵ�λ' and t.nodeId ='"+nodeId+"' and u.nodeId ='"+nodeId+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql,parameters);
		return dataList;
	}

	@Override
	/**
	 * ���μ���
	 */
	public List<Map<String, Object>> jobPlanList(String nodeId) {
		String hql ="select new map("
				+ "a.id as id,"
				+ "a.no as no)"
				+ " from TJobplanInfo as a where nodeid ='"+nodeId+"' and planType =2";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@Override
	/**
	 * ���ϼ���
	 */
	public List<Map<String, Object>> materialList(String jobPlanId) {
		String sql ="select T_MATERAIL_TYPE_INFO.id as id,"
				+ " T_MATERAIL_TYPE_INFO.no as no,T_MATERAIL_TYPE_INFO.name as name,"
				+ " t_jobdispatch_material.reqNum as requirementNum,"
				+ " t_jobdispatch_material.recNum as receivedNum,"
				+ " T_PROCESS_INFO.process_order as processOrder,"
				+ " T_JOBDISPATCHLIST_INFO.no as jobdispatchNo,"
				+ " t_jobdispatch_material.id as jobdispatchMaterialId,"
				+ " t_jobdispatch_material.price as price,"
				+ " '' as materialTotalValue"
				+ " from T_JOBPLAN_INFO"
				+ " inner join T_JOBDISPATCHLIST_INFO on T_JOBDISPATCHLIST_INFO.jobplanID = T_JOBPLAN_INFO.ID"
				+ " inner join T_Process_Info on T_Process_Info.ID = T_JOBDISPATCHLIST_INFO.processID"
				+ " inner join t_jobdispatch_material on t_jobdispatch_material.jobdispatch_id = T_JOBDISPATCHLIST_INFO.ID"
				+ " inner join T_MATERAIL_TYPE_INFO on T_MATERAIL_TYPE_INFO.ID = t_jobdispatch_material.material_id"
				+ " where T_JOBPLAN_INFO.ID = "+jobPlanId+"";
//		 String hql ="select new map(b.id as id,"
//					+ " b.no as no,b.name as name,"
//					+ " e.reqNum as requirementNum,"
//					+ " e.recNum as receivedNum,"
//					+ " d.processOrder as processOrder)"
//					+ " from TJobplanInfo as a,TMaterailTypeInfo as b,TJobdispatchlistInfo as c,TProcessInfo as d"
//					+ " TJobdispatchMaterial as e"
//					+ " where a.id =c.jobplanId and c.id = e.jobdispatchId and e.materialId = b.id"
//					+ " and TJobplanInfo.ID = '"+jobPlanId+"'";
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ��ȡ��֯���Ͽ�����Ϣ
	 */
	public List<Map<String, Object>> materialCtrList(String materialId) {
		String hql ="select new map(a.isBatchCtrl as isBatchCtrl,a.isSeqCtrl as isSeqCtrl,a.isPositionCtrl as isPositionCtrl,"
				+ "a.isVersionCtrl as isVersionCtrl,b.unit as unitName,a.isStockCtrl as isStockCtrl,a.isAxisCtrl as isAxisCtrl)"
				+ " from TMaterialExtendStorage as a,TMaterailTypeInfo as b "
				+ " where a.materialId =b.id and a.materialId ="+materialId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ����NO��ȡ��֯���Ͽ�����Ϣ
	 */
	public List<Map<String, Object>> materialCtrByNoList(String materialNo,String nodeId) {
		String hql ="select new map(a.materialId as id,a.isBatchCtrl as isBatchCtrl,a.isSeqCtrl as isSeqCtrl,"
				+ " a.isPositionCtrl as isPositionCtrl,b.unit as unitName,a.isVersionCtrl as isVersionCtrl,"
				+ " a.isStockCtrl as isStockCtrl,a.isAxisCtrl as isAxisCtrl)"
				+ " from TMaterialExtendStorage as a,TMaterailTypeInfo as b "
				+ " where a.materialId =b.id"
				+ " and b.nodeId ='"+nodeId+"'"
				+ " and b.no ='"+materialNo+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> materialStorageList(String materialId,
			String isBatchCtrl, String isSeqCtrl) {
		String hql ="select a.id as id,b.storage_no as storageNo,"
				+ " if(isnull(c.positionNo),'',c.positionNo) as positionNo,a.available_num as availableNum,"
				+ " if(isnull(d.no),'',d.no) as batchNo,if(isnull(e.no),'',e.no) as seqNo,"
				+ " if(isnull(a.batch_id),'',a.batch_id) as batchId,"
				+ " if(isnull(a.seq_id),'',a.seq_id) as seqId,"
				+ " a.money_num as price,"
				+ " if(isnull(b.id),'',b.id) as storageId,"
				+ " if(isnull(c.id),'',c.id) as positonId,"
				+ " a.version_no as versionNo"
				+ " from t_material_storage as a"
				+ " left join t_storage_info as b on a.storage_id = b.id"
				+ " left join t_materiel_position_info as c on a.positon_id = c.id"
				+ " left join t_stock_materialbath as d on a.batch_id = d.id"
				+ " left join t_stock_materialseq as e on a.seq_id = e.id"
				+ " where a.available_num>0"
				+ " and a.material_id = "+materialId+"";
//		String hql ="";
//		if(isBatchCtrl.equals("0") && isSeqCtrl.equals("0")){//ͬʱ�����к����ο���
//			hql+="select new map (a.id as id,b.storageNo as storageNo,"
//				+ " c.positionNo as positionNo,a.availableNum as availableNum,"
//				+ " d.no as batchNo,e.no as seqNo,a.batchId as batchId,a.seqId as seqId,"
//				+ " a.moneyNum as price,"
//				+ " b.id as storageId,c.id as positonId,a.versionNo as versionNo)"
//				+ " from TMaterialStorage as a, TStorageInfo as b,TMaterielPositionInfo as c,"
//				+ " TStockMaterialbath as d,TStockMaterialseq as e"
//				+ " where a.TStorageInfo.id =b.id and a.positonId = c.id and"
//				+ " a.batchId = d.id and a.seqId = e.id and a.availableNum>0"
//				+ " and a.TMaterailTypeInfo.id = "+materialId+"";
//		}
//		if(isBatchCtrl.equals("0") && isSeqCtrl.equals("1")){//�������ο���
//			hql+="select new map (a.id as id,b.storageNo as storageNo,"
//				+ " c.positionNo as positionNo,a.availableNum as availableNum,"
//				+ " d.no as batchNo,'' as seqNo,a.batchId as batchId,'' as seqId,"
//				+ " a.moneyNum as price,"
//				+ " b.id as storageId,c.id as positonId,a.versionNo as versionNo)"
//				+ " from TMaterialStorage as a, TStorageInfo as b,TMaterielPositionInfo as c,"
//				+ " TStockMaterialbath as d"
//				+ " where a.TStorageInfo.id =b.id and a.positonId = c.id and"
//				+ " a.batchId = d.id and a.availableNum>0"
//				+ " and a.TMaterailTypeInfo.id = "+materialId+""
//				+ " and (a.seqId is null or a.seqId='')";
//		}
//		if(isBatchCtrl.equals("1") && isSeqCtrl.equals("0")){//�������п���
//			hql+="select new map (a.id as id,b.storageNo as storageNo,"
//				+ " c.positionNo as positionNo,a.availableNum as availableNum,"
//				+ " '' as batchNo,e.no as seqNo,'' as batchId,a.seqId as seqId,"
//				+ " a.moneyNum as price,"
//				+ " b.id as storageId,c.id as positonId,a.versionNo as versionNo)"
//				+ " from TMaterialStorage as a, TStorageInfo as b,TMaterielPositionInfo as c,"
//				+ " TStockMaterialseq as e"
//				+ " where a.TStorageInfo.id =b.id and a.positonId = c.id and"
//				+ " a.batchId = e.id and a.availableNum>0"
//				+ " and a.TMaterailTypeInfo.id = "+materialId+""
//				+ " and (a.batchId is null or a.batchId='')";
//		}
//		if(isBatchCtrl.equals("1") && isSeqCtrl.equals("1")){//���ܿ���
//			hql+="select new map (a.id as id,b.storageNo as storageNo,"
//				+ " c.positionNo as positionNo,a.availableNum as availableNum,"
//				+ " '' as batchNo,'' as seqNo,'' as batchId,'' as seqId,"
//				+ " a.moneyNum as price,"
//				+ " b.id as storageId,c.id as positonId,a.versionNo as versionNo)"
//				+ " from TMaterialStorage as a, TStorageInfo as b,TMaterielPositionInfo as c"
//				+ " where a.TStorageInfo.id =b.id and a.positonId = c.id"
//				+ " and a.TMaterailTypeInfo.id = "+materialId+" and a.availableNum>0"
//				+ " and (a.batchId is null or a.batchId='')"
//				+ " and (a.seqId is null or a.seqId='')";
//		}
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(hql);
		return dataList;	}

	@Override
	public List<Map<String, Object>> materialPositionList(String materialId,
			String storageId) {
		String sql ="select isBatchCtrl,isSeqCtrl from t_material_extend_storage where material_id ="+materialId+"";
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql);
		return dataList;
	}

	@Override
	/**
	 * ��ȡ���������ˮ��
	 */
	public String getMaxID() {
		String sql ="select transNo from t_transaction order by ID DESC LIMIT 1";
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql);
		if(dataList.size()>0){
		    return dataList.get(0).get("transNo").toString();
		}else{
			return "0";
		}
	}

	@Override
	/**
	 * ����������Ϣ
	 */
	public String saveInfo(List<Map<String,Object>> materialInfoList,
			List<Map<String,Object>> outInStorageList,
			List<Map<String,Object>> transactionList,String type,
			String userId,String transTypeId,Date transDate,String jobPlanId) {
		try{
			//��������
			TJobplanInfo tJobplanInfo = new TJobplanInfo();
			tJobplanInfo = dao.get(TJobplanInfo.class,Long.parseLong(jobPlanId));
			//������������������
			for(Map<String,Object> tt:materialInfoList){
				if(!tt.get("materialTotalValue").toString().equals("")){
					String id =tt.get("jobdispatchMaterialId").toString();
					Double num = Double.parseDouble(tt.get("receivedNum").toString());
					Double totalPrice = Double.parseDouble(tt.get("materialTotalValue").toString());
					
					TJobdispatchMaterial mm= new TJobdispatchMaterial();
					mm = dao.get(TJobdispatchMaterial.class,Integer.parseInt(id));
					Double oldNum = mm.getRecNum();
					Double oldPrice =mm.getPrice();
					mm.setPrice((oldNum*oldPrice+totalPrice)/num);
					mm.setRecNum(num);
					dao.update(mm);
				}
			}
			//��������Ϣ���޸Ŀ��������
			for(Map<String,Object> tt:outInStorageList){
				int id =Integer.parseInt(tt.get("id").toString());
				int num = Integer.parseInt(tt.get("num").toString());
				
				TMaterialStorage tMaterialStorage =new TMaterialStorage();
				tMaterialStorage = dao.get(TMaterialStorage.class,id);
				Double availableNum = tMaterialStorage.getAvailableNum();
				tMaterialStorage.setAvailableNum(availableNum-num);
				dao.update(tMaterialStorage);
			}
			
			//����������Ϣ
			for(Map<String,Object> tt:transactionList){
				TTransaction tTransaction = new TTransaction();
				
				
				String transNo = tt.get("transNo").toString();
				String materialId = tt.get("materialId").toString();
				String storageId = tt.get("storageId").toString();
				
				String positonId="0";
				if(null !=tt.get("positonId") && tt.get("positonId")!=""){
					positonId= tt.get("positonId").toString();
				}
				String processNum = tt.get("processNum").toString();
				Double price = Double.parseDouble(tt.get("price").toString());
				//���ò���ֵ
				tTransaction.setTransNo(transNo);
				
				TMaterailTypeInfo mm= new TMaterailTypeInfo();
				mm =dao.get(TMaterailTypeInfo.class,Long.parseLong(materialId));
				tTransaction.setTMaterailTypeInfo(mm);
				
				TStorageInfo ss= new TStorageInfo();
				ss =dao.get(TStorageInfo.class,Integer.parseInt(storageId));
				tTransaction.setTStorageInfo(ss);
				if(!positonId.equals("0")){
					tTransaction.setPositionId(Integer.parseInt(positonId));
				}
				if(Boolean.valueOf(tt.get("batchCtrl").toString())){
					if(null!=tt.get("batchId") && !tt.get("batchId").toString().equals("")){
						tTransaction.setBatchId(Integer.parseInt(tt.get("batchId").toString()));
					}
					
				}
				if(Boolean.valueOf(tt.get("seqCtrl").toString())){
					if(null!=tt.get("seqId") && !tt.get("seqId").toString().equals("")){
						tTransaction.setPositionId(Integer.parseInt(tt.get("seqId").toString()));
					}
				}
				
				tTransaction.setProcessNum(-Double.parseDouble(processNum));
				tTransaction.setOrderNo(tJobplanInfo.getNo());
				tTransaction.setTransDate(transDate);
				
				TTransactionRelation dd=new TTransactionRelation();
				dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
				tTransaction.setTTransactionRelation(dd);
				tTransaction.setPrice(price);
				tTransaction.setUserId(Integer.parseInt(userId));
				dao.save(tTransaction);
			}
			
			return "����ɹ�";
		}catch(RuntimeException r){
			r.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "����ʧ��";
		}
		
		
	}

	@Override
	/**
	 * ��ȡ�������ͼ���
	 */
	public List<Map<String, Object>> transactionTypeList() {
		String sql ="select id,tans_type,tans_from from t_transaction_relation";
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ����ID��ȡ������Ϣ
	 */
	public List<Map<String,Object>> getJobPlanInfoList(String jobPlanId) {
		String hql ="select new map(a.planNum as planNum,a.finishNum as finishNum,"
				+ " a.instockNum as instockNum,b.name as partName,b.no as partNo)"
				+ " from TJobplanInfo as a,TPartTypeInfo as b "
				+ " where a.TPartTypeInfo.id =b.id"
				+ " and a.id ="+jobPlanId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@Override
	/**
	 * ���ݿⷿ��ȡ��λ����
	 */
	public List<Map<String, Object>> getPositionInfoList(String storageId) {
		String hql ="select new map(a.id as id,a.positionNo as no)"
				+ " from TMaterielPositionInfo as a,RStoragePosition as b,TStorageInfo as c "
				+ " where a.id =b.TMaterielPositionInfo.id"
				+ " and c.id=b.TStorageInfo.id"
				+ " and c.id ="+storageId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ��ȡ�������μ���
	 */
	public List<Map<String, Object>> getProductBatchList(String materialId) {
		String hql ="select new map(a.id as id,a.no as no)"
				+ " from TStockMaterialbath as a"
				+ " where a.materialId ="+materialId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ��ȡ��������
	 */
	public List<Map<String, Object>> getProductSeqList(String materialId,String no,Boolean flag) {
		String hql ="select new map(a.id as id,a.no as no)"
				+ " from TStockMaterialseq as a,TMaterialStorage as b"
				+ " where a.id = b.seqId"
				+ " and a.materialId ="+materialId+"";
				if(no!=""){
					hql+= " and a.no ='"+no+"'";
				}
				if(flag){
					hql+= " and b.availableNum >0";
				}else{
					hql+= " and b.availableNum =0";
				}
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
    
	/**
	 * �깤���
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveProductInStorage(List<Map<String, Object>> dataInfo,
			List<Map<String,Object>> seqInfo,String materialId,Boolean batchCtrl,Boolean seqCtrl,String jobPlanId,int totalNum,
			String userId,String transTypeId,Date transDate,String nodeId) {
		try{ 
			//��������
			TJobplanInfo tJobplanInfo = new TJobplanInfo();
			tJobplanInfo = dao.get(TJobplanInfo.class,Long.parseLong(jobPlanId));
			//������������
			if(batchCtrl){
				TStockMaterialbath tStockMaterialbath =new TStockMaterialbath();
				for(Map<String,Object> tt:dataInfo){
					String hql ="select new map(a.id as id)"
					        + " from TStockMaterialbath as a"
					        + " where a.materialId ="+materialId+""
							+ " and a.no ='"+tt.get("batch").toString()+"'";
					List<Map<String,Object>> list = dao.executeQuery(hql);
					if(list.size()<1){
						tStockMaterialbath.setMaterialId(Integer.valueOf(materialId));
						tStockMaterialbath.setNo(tt.get("batch").toString());
						dao.save(tStockMaterialbath);
					}
				}
			}
			
			//������������
			if(seqCtrl){
				
				for(Map<String,Object> tt:seqInfo){
					TStockMaterialseq tStockMaterialseq =new TStockMaterialseq();
					String hql ="select new map(a.id as id)"
					        + " from TStockMaterialseq as a"
					        + " where a.materialId ="+materialId+""
							+ " and a.no ='"+tt.get("seqNo").toString()+"'";
					List<Map<String,Object>> list = dao.executeQuery(hql);
					if(list.size()<1){
						tStockMaterialseq.setMaterialId(Integer.valueOf(materialId));
						tStockMaterialseq.setNo(tt.get("seqNo").toString());
						dao.save(tStockMaterialseq);
					}
				}
			}
			
			//��ȡ���ϼ۸�
			TMaterailTypeInfo tMaterailTypeInfo = new TMaterailTypeInfo();
			tMaterailTypeInfo =dao.get(TMaterailTypeInfo.class,Long.parseLong(materialId));
			Double price =0.0;
			if(null !=tMaterailTypeInfo.getPrice() && tMaterailTypeInfo.getPrice().toString() !=""){
				price =Double.parseDouble(tMaterailTypeInfo.getPrice().toString());
			}
			
			//��洦��
			for(Map<String,Object> tt:dataInfo){
				String id = tt.get("id").toString();
				String storageId = tt.get("storageId").toString();//�ⷿID
				Double num = Double.parseDouble(tt.get("realNum").toString());//�������
				String versionNo = tt.get("versionNo").toString();//�汾
				int positionId = 0;//��λ
				int batchId =0;//����ID
				int seqId =0;//����ID
				//�ⷿ
				TStorageInfo tStorageInfo = new TStorageInfo();
	        	tStorageInfo =dao.get(TStorageInfo.class,Integer.parseInt(storageId));
	        	//��λ
	        	Boolean positionCtrl = Boolean.valueOf(tt.get("positionCtrl").toString());
	        	if(positionCtrl){
	        		String no = tt.get("positionNo").toString();
	        		String hql ="select new map(a.id as id)"
	        				  + " from TMaterielPositionInfo as a "
	        				  + " where a.positionNo ='"+no+"'";
	        		List<Map<String,Object>> list = dao.executeQuery(hql);
					if(list.size()>0){
						positionId = Integer.parseInt(list.get(0).get("id").toString());
					}else{
						TMaterielPositionInfo tMaterielPositionInfo = new TMaterielPositionInfo();
						tMaterielPositionInfo.setPositionNo(no);
						tMaterielPositionInfo.setPositionName(no);
						tMaterielPositionInfo.setPositionStatus("1");
						dao.save(tMaterielPositionInfo);
						
						//����λ��ϵ��
						RStoragePosition rStoragePosition =new RStoragePosition();
						rStoragePosition.setTMaterielPositionInfo(tMaterielPositionInfo);
						rStoragePosition.setTStorageInfo(tStorageInfo);
						dao.save(rStoragePosition);
						positionId = tMaterielPositionInfo.getId();
					}
	        	}
				
				//������ֵ
				if(batchCtrl){
					
					if(null !=tt.get("batch") && tt.get("batch").toString() !=""){
						String batchNo = tt.get("batch").toString();
						String hql = "select new map(a.id as id)"
						        + " from TStockMaterialbath as a"
						        + " where a.materialId ="+materialId+""
								+ " and a.no ='"+batchNo+"'";
						List<Map<String,Object>> list = dao.executeQuery(hql);
						if(list.size()>0){
							batchId = Integer.parseInt(list.get(0).get("id").toString());
						}
					}
				}
				//����
				if(seqCtrl){//�����п���
					for(Map<String,Object> map:seqInfo){
						if(map.get("id").toString().equals(id)){
							String hql ="select new map(a.id as id)"
	    					        + " from TStockMaterialseq as a"
	    					        + " where a.materialId ="+materialId+""
	    							+ " and a.no ='"+map.get("seqNo").toString()+"'";
	    					List<Map<String,Object>> list = dao.executeQuery(hql);
	    					if(list.size()>0){
	    						seqId = Integer.parseInt(list.get(0).get("id").toString());
	    					}
	    					
							//��ȡ�ⷿ�������Ƿ����
							String hql2 ="select new map(a.id as id,a.availableNum as availableNum,"
									+ " a.moneyNum as moneyNum)"
							        + " from TMaterialStorage as a"
							        + " where a.TMaterailTypeInfo.id ="+materialId+""
									+ " and a.TStorageInfo.id ="+storageId+""
									+ " and a.versionNo ='"+versionNo+"'"
								    + " and a.seqId ="+seqId+"";
							if(positionId == 0){//��λ
								hql2 +=" and (a.positonId is null or a.positonId ='')";
							}else{
								hql2 +=" and a.positonId ="+positionId+"";
							}
		                    if(batchId == 0){//����
		                    	hql2 +=" and (a.batchId is null or a.batchId ='')";
							}else{
								hql2 +=" and a.batchId ="+batchId+"";
							}
		                    List<Map<String,Object>> list2 = dao.executeQuery(hql2);
		                    if(list2.size()>0){
		                    	//�����������
		                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
		                    	TMaterialStorage temp =new TMaterialStorage();
		                    	temp = dao.get(TMaterialStorage.class,tempId);
		                    	temp.setAvailableNum(1.0);//������
		                    	temp.setMoneyNum(price);//�۸�
		                    	temp.setProcessDate(transDate);//����ʱ��
		                    	dao.update(temp);
		                    }else{
								//��Ʒ���
								TMaterialStorage temp =new TMaterialStorage();
								temp.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
		                    	temp.setTStorageInfo(tStorageInfo);//�ⷿ
		                    	if(positionId !=0){
		                    		temp.setPositonId(positionId);//��λ
		                    	}
		                    	if(batchId !=0){
		                    		temp.setBatchId(batchId);//��λ
		                    	}
		                    	
		                    	temp.setSeqId(seqId);//����ID
		                    	temp.setAvailableNum(1.0);//������
		                    	temp.setVersionNo(versionNo);//�汾
		                    	temp.setMoneyNum(price);//�۸�
		                    	temp.setProcessDate(transDate);//����ʱ��
		                    	temp.setUnitName(tt.get("baseUnitName").toString());//��λ
		                    	dao.save(temp);
		                    }
	                    	
	                    	//������
	                        TTransaction tTransaction = new TTransaction();
	                        tTransaction.setTransNo(tt.get("transNo").toString());//������
	                        tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
	                        tTransaction.setTStorageInfo(tStorageInfo);//�ⷿ
	                        if(positionId !=0){
	                        	tTransaction.setPositionId(positionId);//��λ
	                    	}
	                        tTransaction.setProcessNum(1.0);//��������
	                        tTransaction.setPrice(price);//�۸�
	                        tTransaction.setOrderNo(tJobplanInfo.getNo());//�����ţ�����Դ��
	                        tTransaction.setTransDate(transDate);//����ʱ��
	                        if(batchId !=0){
	                        	tTransaction.setBatchId(batchId);//����
	                    	}
	                        tTransaction.setSeqId(seqId);//����
	                        TTransactionRelation dd= new TTransactionRelation();
	                        dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
	        				tTransaction.setTTransactionRelation(dd);
	        				tTransaction.setUserId(Integer.parseInt(userId));//��ԱId
	        				dao.save(tTransaction);
						}
					}
					
				}else{
					//��ȡ�ⷿ�������Ƿ����
					String hql ="select new map(a.id as id,a.availableNum as availableNum,"
							+ " a.moneyNum as moneyNum)"
					        + " from TMaterialStorage as a"
					        + " where a.TMaterailTypeInfo.id ="+materialId+""
							+ " and a.TStorageInfo.id ="+storageId+""
							+ " and a.versionNo ='"+versionNo+"'"
						    + " and (a.seqId is null or a.seqId ='')";
					if(positionId == 0){//��λ
						hql +=" and (a.positonId is null or a.positonId ='')";
					}else{
						hql +=" and a.positonId ="+positionId+"";
					}
                    if(batchId == 0){//����
                    	hql +=" and (a.batchId is null or a.batchId ='')";
					}else{
						hql +=" and a.batchId ="+batchId+"";
					}
                    List<Map<String,Object>> list = dao.executeQuery(hql);
                    if(list.size()>0){
                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
                    	Double tempNum = Double.parseDouble(list.get(0).get("availableNum").toString());
                    	Double tempPrice =Double.parseDouble(list.get(0).get("moneyNum").toString());
                    	Double inSoragePrice = (tempNum*tempPrice+num*price)/(tempNum+num);//���۸�
                    	TMaterialStorage temp =new TMaterialStorage();
                    	
                    	temp = dao.get(TMaterialStorage.class,tempId);
                    	temp.setAvailableNum(num+tempNum);
                    	temp.setMoneyNum(inSoragePrice);
                    	temp.setProcessDate(transDate);//����ʱ��
                    	dao.update(temp);
                    }else{
                    	TMaterialStorage temp =new TMaterialStorage();
                    	temp.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
                    	temp.setTStorageInfo(tStorageInfo);//�ⷿ
                    	if(positionId !=0){
                    		temp.setPositonId(positionId);//��λ
                    	}
                    	if(batchId !=0){
                    		temp.setBatchId(batchId);//��λ
                    	}
                    	temp.setAvailableNum(num);
                    	temp.setMoneyNum(price);
                    	temp.setVersionNo(versionNo);//�汾
                    	temp.setProcessDate(transDate);//����ʱ��
                    	temp.setUnitName(tt.get("baseUnitName").toString());//��λ
                    	dao.save(temp);
                    }
                    
                    //������
                    TTransaction tTransaction = new TTransaction();
                    tTransaction.setTransNo(tt.get("transNo").toString());//������
                    tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
                    tTransaction.setTStorageInfo(tStorageInfo);//�ⷿ
                    if(positionId !=0){
                    	tTransaction.setPositionId(positionId);//��λ
                	}
                    tTransaction.setProcessNum(num);//��������
                    tTransaction.setPrice(price);//�۸�
                    tTransaction.setOrderNo(tJobplanInfo.getNo());//�����ţ�����Դ��
                    tTransaction.setTransDate(transDate);//����ʱ��
                    if(batchId !=0){
                    	tTransaction.setBatchId(batchId);//����
                	}
                    TTransactionRelation dd= new TTransactionRelation();
                    dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
    				tTransaction.setTTransactionRelation(dd);
    				tTransaction.setUserId(Integer.parseInt(userId));//��ԱId
    				dao.save(tTransaction);
				}
			}
			//��������������Ϣ
			
			int tempNum =0;
			if(null != tJobplanInfo.getInstockNum()){
				tempNum = tJobplanInfo.getInstockNum();
			}
			tJobplanInfo.setInstockNum(totalNum+tempNum);
			dao.update(tJobplanInfo);
			
		}catch(RuntimeException r){
			r.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "����ʧ��";
		}
		return "����ɹ�";
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ��ȡ��Դ���ͼ���
	 */
	public List<Map<String, Object>> getTransFromTypeList() {
		String hql ="select distinct new map(a.tansFrom as tansFrom)"
				+ " from TTransactionRelation as a";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ��ȡ�������ͼ���
	 */
	public List<Map<String, Object>> getTransTypeList(String transFrom) {
		String hql ="select new map(a.id as id,a.tansType as tansType,a.tansActivity as tansActivity)"
				+ " from TTransactionRelation as a"
				+ " where a.tansFrom ='"+transFrom+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ������Ϣ����
	 */
	public List<Map<String, Object>> getMaterialInfoList(String nodeId) {
		String hql ="select new map(a.id as id,a.no as no,a.name as name,b.isBatchCtrl as isBatchCtrl,b.isSeqCtrl as isSeqCtrl,"
				+ " b.isPositionCtrl as isPositionCtrl,a.unit as unitName,b.isVersionCtrl as isVersionCtrl,"
				+ " b.isStockCtrl as isStockCtrl,b.isAxisCtrl as isAxisCtrl,b.isPositionCtrl as isPositionCtrl)"
				+ " from TMaterailTypeInfo as a,TMaterialExtendStorage as b"
				+ " where a.id =b.materialId"
				+ " and a.nodeId ='"+nodeId+"'";
				
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;

	}
	
	/**
	 * �����������Ϣ����
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getMaterialInfoList2(String nodeId) {
		String hql ="select distinct new map(a.id as id,a.no as no,a.name as name)"
				+ " from TMaterailTypeInfo as a,TMaterialExtendStorage as b,TMaterialStorage as c"
				+ " where a.id =b.materialId"
				+ " and a.id = c.TMaterailTypeInfo.id"
				+ " and a.nodeId ='"+nodeId+"'";
				
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * �����������
	 */
	public String saveMaterialInStorage(List<Map<String, Object>> dataInfo,
			List<Map<String, Object>> seqInfo, String source,
			String userId, String transTypeId, Date transDate) {
		try{
			//��洦��
			for(Map<String,Object> tt:dataInfo){
				Boolean batchCtrl =Boolean.valueOf(tt.get("isBatchCtrl").toString());//���ο���
				Boolean seqCtrl =Boolean.valueOf(tt.get("isSeqCtrl").toString());//���п���
				String materialId =  tt.get("materialId").toString();//����ID
				
				String id = tt.get("id").toString();
				String storageId = tt.get("storageId").toString();//�ⷿID
				Double num = Double.parseDouble(tt.get("realNum").toString());//�������
				String versionNo =tt.get("versionNo").toString();//�汾
				int positionId = 0;//��λ
				int batchId =0;//����ID
				int seqId =0;//����ID
				
				//��ȡ���ϼ۸�
				TMaterailTypeInfo tMaterailTypeInfo = new TMaterailTypeInfo();
				tMaterailTypeInfo =dao.get(TMaterailTypeInfo.class,Long.parseLong(materialId));
				Double price =0.000;
				if(null !=tMaterailTypeInfo.getPrice() && tMaterailTypeInfo.getPrice().toString() !=""){
					price =Double.parseDouble(tMaterailTypeInfo.getPrice().toString());
				}
				
				//�ⷿ
				TStorageInfo tStorageInfo = new TStorageInfo();
	        	tStorageInfo =dao.get(TStorageInfo.class,Integer.parseInt(storageId));
	        	//��λ
	        	Boolean positionCtrl = Boolean.valueOf(tt.get("positionCtrl").toString());
	        	if(positionCtrl){
	        		String no = tt.get("positionNo").toString();
	        		String hql ="select new map(a.id as id)"
	        				  + " from TMaterielPositionInfo as a "
	        				  + " where a.positionNo ='"+no+"'";
	        		List<Map<String,Object>> list = dao.executeQuery(hql);
					if(list.size()>0){
						positionId = Integer.parseInt(list.get(0).get("id").toString());
					}else{
						TMaterielPositionInfo tMaterielPositionInfo = new TMaterielPositionInfo();
						tMaterielPositionInfo.setPositionNo(no);
						tMaterielPositionInfo.setPositionName(no);
						tMaterielPositionInfo.setPositionStatus("1");
						dao.save(tMaterielPositionInfo);
						
						//����λ��ϵ��
						RStoragePosition rStoragePosition =new RStoragePosition();
						rStoragePosition.setTMaterielPositionInfo(tMaterielPositionInfo);
						rStoragePosition.setTStorageInfo(tStorageInfo);
						dao.save(rStoragePosition);
						positionId = tMaterielPositionInfo.getId();
					}
	        	}
	        	
	        	//����
				if(batchCtrl){
					TStockMaterialbath tStockMaterialbath =new TStockMaterialbath();
					String hql ="select new map(a.id as id)"
					        + " from TStockMaterialbath as a"
					        + " where a.materialId ="+materialId+""
							+ " and a.no ='"+tt.get("batch").toString()+"'";
					List<Map<String,Object>> list = dao.executeQuery(hql);
					if(list.size()<1){
						tStockMaterialbath.setMaterialId(Integer.valueOf(materialId));
						tStockMaterialbath.setNo(tt.get("batch").toString());
						dao.save(tStockMaterialbath);
						batchId = tStockMaterialbath.getId();
					}else{
						batchId = Integer.parseInt(list.get(0).get("id").toString());
					}
				}
				
				//����
				if(seqCtrl){//�����п���
					for(Map<String,Object> map:seqInfo){
						if(map.get("id").toString().equals(id)){
							String hql ="select new map(a.id as id)"
	    					        + " from TStockMaterialseq as a"
	    					        + " where a.materialId ="+materialId+""
	    							+ " and a.no ='"+map.get("seqNo").toString()+"'";
	    					List<Map<String,Object>> list = dao.executeQuery(hql);
	    					if(list.size()>0){
	    						seqId = Integer.parseInt(list.get(0).get("id").toString());
	    					}else{
	    						TStockMaterialseq tStockMaterialseq = new TStockMaterialseq();
	    						tStockMaterialseq.setMaterialId(Integer.valueOf(materialId));
	    						tStockMaterialseq.setNo(map.get("seqNo").toString());
	    						dao.save(tStockMaterialseq);
	    						seqId = tStockMaterialseq.getId();
	    					}
	    					
							//��ȡ�ⷿ�������Ƿ����
							String hql2 ="select new map(a.id as id,a.availableNum as availableNum,"
									+ " a.moneyNum as moneyNum)"
							        + " from TMaterialStorage as a"
							        + " where a.TMaterailTypeInfo.id ="+materialId+""
									+ " and a.TStorageInfo.id ="+storageId+""
									+ " and a.versionNo ='"+versionNo+"'"
								    + " and a.seqId ="+seqId+"";
							if(positionId == 0){//��λ
								hql2 +=" and (a.positonId is null or a.positonId ='')";
							}else{
								hql2 +=" and a.positonId ="+positionId+"";
							}
		                    if(batchId == 0){//����
		                    	hql2 +=" and (a.batchId is null or a.batchId ='')";
							}else{
								hql2 +=" and a.batchId ="+batchId+"";
							}
		                    List<Map<String,Object>> list2 = dao.executeQuery(hql2);
		                    if(list2.size()>0){
		                    	//�����������
		                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
		                    	TMaterialStorage temp =new TMaterialStorage();
		                    	temp = dao.get(TMaterialStorage.class,tempId);
		                    	temp.setAvailableNum(1.0);//������
		                    	temp.setMoneyNum(price);//�۸�
		                    	temp.setProcessDate(transDate);//����ʱ��
		                    	dao.update(temp);
		                    }else{
		                    	//�����������
								TMaterialStorage temp =new TMaterialStorage();
								temp.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
		                    	temp.setTStorageInfo(tStorageInfo);//�ⷿ
		                    	if(positionId !=0){
		                    		temp.setPositonId(positionId);//��λ
		                    	}
		                    	if(batchId !=0){
		                    		temp.setBatchId(batchId);//����
		                    	}
		                    	
		                    	
		                    	temp.setSeqId(seqId);//����ID
		                    	temp.setAvailableNum(1.0);//������
		                    	temp.setMoneyNum(price);//�۸�
		                    	temp.setProcessDate(transDate);//����ʱ��
		                    	temp.setVersionNo(versionNo);//�汾
		                    	temp.setUnitName(tt.get("baseUnitName").toString());
		                    	dao.save(temp);
		                    }
	                    	//������
	                        TTransaction tTransaction = new TTransaction();
	                        tTransaction.setTransNo(tt.get("transNo").toString());//������
	                        tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
	                        tTransaction.setTStorageInfo(tStorageInfo);//�ⷿ
	                        if(positionId !=0){
	                        	tTransaction.setPositionId(positionId);//��λ
	                    	}
	                        tTransaction.setProcessNum(1.0);//��������
	                        tTransaction.setPrice(price);//�۸�
	                        tTransaction.setOrderNo(source);//�����ţ�����Դ��
	                        tTransaction.setTransDate(transDate);//����ʱ��
	                        if(batchId !=0){
	                        	tTransaction.setBatchId(batchId);//����
	                    	}
	                        tTransaction.setSeqId(seqId);//����
	                        TTransactionRelation dd= new TTransactionRelation();
	                        dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
	        				tTransaction.setTTransactionRelation(dd);
	        				tTransaction.setUserId(Integer.parseInt(userId));//��ԱId
	        				dao.save(tTransaction);
						}
					}
					
				}else{
					//��ȡ�ⷿ�������Ƿ����
					String hql ="select new map(a.id as id,a.availableNum as availableNum,"
							+ " a.moneyNum as moneyNum)"
					        + " from TMaterialStorage as a"
					        + " where a.TMaterailTypeInfo.id ="+materialId+""
							+ " and a.TStorageInfo.id ="+storageId+""
							+ " and a.versionNo ='"+versionNo+"'"
						    + " and (a.seqId is null or a.seqId ='')";
					if(positionId == 0){//��λ
						hql +=" and (a.positonId is null or a.positonId ='')";
					}else{
						hql +=" and a.positonId ="+positionId+"";
					}
                    if(batchId == 0){//����
                    	hql +=" and (a.batchId is null or a.batchId ='')";
					}else{
						hql +=" and a.batchId ="+batchId+"";
					}
                    List<Map<String,Object>> list = dao.executeQuery(hql);
                    if(list.size()>0){
                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
                    	Double tempNum = Double.parseDouble(list.get(0).get("availableNum").toString());
                    	Double tempPrice =Double.parseDouble(list.get(0).get("moneyNum").toString());
                    	Double inSoragePrice = (tempNum*tempPrice+num*price)/(tempNum+num);//���۸�
                    	TMaterialStorage temp =new TMaterialStorage();
                    	
                    	temp = dao.get(TMaterialStorage.class,tempId);
                    	temp.setAvailableNum(num+tempNum);
                    	temp.setMoneyNum(inSoragePrice);
                    	dao.update(temp);
                    }else{
                    	TMaterialStorage temp =new TMaterialStorage();
                    	temp.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
                    	temp.setTStorageInfo(tStorageInfo);//�ⷿ
                    	if(positionId !=0){
                    		temp.setPositonId(positionId);//��λ
                    	}
                    	if(batchId !=0){
                    		temp.setBatchId(batchId);//��λ
                    	}
                    	temp.setAvailableNum(num);
                    	temp.setUnitName(tt.get("unitName").toString());
                    	temp.setMoneyNum(price);
                    	temp.setProcessDate(transDate);
                    	temp.setVersionNo(versionNo);//�汾
                    	temp.setUnitName(tt.get("baseUnitName").toString());
                    	dao.save(temp);
                    }
                    
                    //������
                    TTransaction tTransaction = new TTransaction();
                    tTransaction.setTransNo(tt.get("transNo").toString());//������
                    tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
                    tTransaction.setTStorageInfo(tStorageInfo);//�ⷿ
                    if(positionId !=0){
                    	tTransaction.setPositionId(positionId);//��λ
                	}
                    tTransaction.setProcessNum(num);//��������
                    tTransaction.setPrice(price);//�۸�
                    tTransaction.setOrderNo(source);//�����ţ�����Դ��
                    tTransaction.setTransDate(transDate);//����ʱ��
                    if(batchId !=0){
                    	tTransaction.setBatchId(batchId);//����
                	}
                    TTransactionRelation dd= new TTransactionRelation();
                    dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
    				tTransaction.setTTransactionRelation(dd);
    				tTransaction.setUserId(Integer.parseInt(userId));//��ԱId
    				dao.save(tTransaction);
				}
			}
			
		}catch(RuntimeException r){
			r.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "����ʧ��";
		}
		return "����ɹ�";
	}

	@Override
	/**
	 * �����ӷ�����
	 */
	public String saveMaterialOutStorage(List<Map<String, Object>> dataInfo,
			String transNo, String materialId,
			Boolean batchCtrl, Boolean seqCtrl, String source,
			String userId, String transTypeId, Date transDate) {
		try{
			for(Map<String,Object> map:dataInfo){
				Double num =Double.valueOf(map.get("num").toString());
				if(num>0){
					int id= Integer.parseInt(map.get("id").toString());//���ID
					TMaterialStorage temp =new TMaterialStorage();
					temp = dao.get(TMaterialStorage.class,id);
					Double availableNum =temp.getAvailableNum(); //���������
					if(num>availableNum){
						return "����ʧ��,����������";
					}else{
						temp.setAvailableNum(availableNum-num);
						temp.setProcessDate(transDate);//����ʱ��
						dao.update(temp);
						
						//������
	                    TTransaction tTransaction = new TTransaction();
	                    tTransaction.setTransNo(transNo);//������
	                    
	                    TMaterailTypeInfo tMaterailTypeInfo =new TMaterailTypeInfo();
	                    tMaterailTypeInfo = dao.get(TMaterailTypeInfo.class,Long.parseLong(materialId));
	                    tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
	                    
	                    TStorageInfo tStorageInfo = new TStorageInfo();
	                    tStorageInfo = dao.get(TStorageInfo.class,Integer.parseInt(map.get("storageId").toString()));
	                    tTransaction.setTStorageInfo(tStorageInfo);//�ⷿ
	                    if(null!=map.get("positonId") && !map.get("positonId").toString().equals("")){
	                    	tTransaction.setPositionId(Integer.parseInt(map.get("positonId").toString()));//��λ
	                	}
	                    tTransaction.setProcessNum(-num);//��������
	                    tTransaction.setPrice(temp.getMoneyNum());//�۸�
	                    tTransaction.setOrderNo(source);//�����ţ�����Դ��
	                    tTransaction.setTransDate(transDate);//����ʱ��
	                    if(batchCtrl){
	                    	if(null!=map.get("batchId") && !map.get("batchId").toString().equals("")){
	                    		tTransaction.setBatchId(Integer.parseInt(map.get("batchId").toString()));//����
	                    	}
	                	}
	                    if(seqCtrl){
	                    	if(null!=map.get("seqId") && !map.get("seqId").toString().equals("")){
	                    		tTransaction.setBatchId(Integer.parseInt(map.get("seqId").toString()));//����
	                    	}
	                	}
	                    TTransactionRelation dd= new TTransactionRelation();
	                    dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
	    				tTransaction.setTTransactionRelation(dd);
	    				tTransaction.setUserId(Integer.parseInt(userId));//��ԱId
	    				dao.save(tTransaction);
						
					}
				}
			}
		}catch(RuntimeException r){
			r.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "����ʧ��";
		}
		
		return "����ɹ�";
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ���Ͽ��ⷿ����List
	 */
	public List<Map<String, Object>> getMaterialStorageList(String materialId,String nodeId) {
		String hql ="select new map(a.id as id,b.id as storageId,b.storageNo as no,a.availableNum as availableNum,"
				+ " b.positionType as positionType)"
				+ " from TMaterialStorage as a,TStorageInfo as b"
				+ " where a.TStorageInfo.id =b.id and a.TMaterailTypeInfo.id ="+materialId+""
				+ " and b.nodeId ='"+nodeId+"'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ���Ͽ���λ����List
	 */
	public List<Map<String, Object>> getMaterialStoragePositionList(
			String materialId, String storageId) {
		String hql ="select new map(a.id as id,b.id as positionId,b.positionNo as no,a.availableNum as availableNum)"
				+ " from TMaterialStorage as a,TMaterielPositionInfo as b"
				+ " where a.positonId =b.id and a.TMaterailTypeInfo.id ="+materialId+""
				+ " and a.TStorageInfo.id ="+storageId+" and b.positionStatus ='1'";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ���Ͽ�����μ���List
	 */
	public List<Map<String, Object>> getMaterialStorageBatchList(
			String materialId, String storageId, String positionId) {
		String hql ="select new map(a.id as id,b.id as batchId,b.no as no,a.availableNum as availableNum)"
				+ " from TMaterialStorage as a,TStockMaterialbath as b"
				+ " where a.batchId =b.id and a.TMaterailTypeInfo.id ="+materialId+""
				+ " and a.TStorageInfo.id ="+storageId+"";
		if(null == positionId || positionId ==""){
			//hql +=" and (a.positonId = null or a.positonId ='')";
		}else{
			hql +=" and a.positonId ="+positionId+"";
		}
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ���Ͽ�����м���List
	 */
	public List<Map<String, Object>> getMaterialStorageSeqList(
			String materialId, String storageId, String positionId,
			String batchId,String seqId) {
		String hql ="select new map(a.id as id,b.id as seqId,b.no as no,a.availableNum as availableNum)"
				+ " from TMaterialStorage as a,TStockMaterialseq as b"
				+ " where a.seqId =b.id and a.TMaterailTypeInfo.id ="+materialId+""
				+ " and a.TStorageInfo.id ="+storageId+" and a.availableNum>0";
		if(null == positionId || positionId ==""){
			//hql +=" and (a.positonId = null or a.positonId ='')";
		}else{
			hql +=" and a.positonId ="+positionId+"";
		}
		
		if(null == batchId || batchId.equals("")){
			//hql +=" and (a.batchId =null or a.batchId ='')";
		}else{
			hql +=" and a.batchId ="+batchId+"";
		}
		if(null ==seqId || seqId.equals("")){
			//hql +=" and (a.seqId =null or a.seqId ='')";
		}else{
			hql +=" and a.seqId ="+seqId+"";
		}
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getMaterialStorageVersionList(
			String materialId, String storageId, String positionId,
			String batchId, String seqId, String versionNo) {
		String hql ="select new map(a.id as id,a.availableNum as availableNum)"
				+ " from TMaterialStorage as a"
				+ " where a.TMaterailTypeInfo.id ="+materialId+""
				+ " and a.TStorageInfo.id ="+storageId+" and a.availableNum>0";
		if(null == positionId || positionId ==""){
			
		}else{
			hql +=" and a.positonId ="+positionId+"";
		}
		
		if(null == batchId || batchId.equals("")){
			
		}else{
			hql +=" and a.batchId ="+batchId+"";
		}
		if(null ==seqId || seqId.equals("")){
			
		}else{
			hql +=" and a.seqId ="+seqId+"";
		}
		
        if(null ==versionNo || versionNo.equals("")){
			
		}else{
			hql +=" and a.versionNo ="+versionNo+"";
		}
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ��λ�����ʼ���List
	 */
	public List<Map<String, Object>> getUnitRateList(String unitName,
			String nodeId) {
		
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		
		//String hql ="select new map(a.id as id)"
		//		+ " from TUnitInfo as a"
		//		+ " where a.unitName ='"+unitName+"'"
		//		+ " and a.nodeId ='"+nodeId+"'";
		//List<Map<String,Object>> tempList = dao.executeQuery(hql);
		//for(Map<String,Object> map:tempList){
		//	String id = map.get("id").toString();
		String hql2 = "select new map(a.unitName as name,b.ratio as rate)"
				+ " from TUnitInfo as a,TUnitConversion as b,TUnitType as c"
				+ " where a.id = b.TUnitInfo.id and b.refUnitId = c.id"
				+ " and a.nodeId ='"+nodeId+"' and c.unitName ='"+unitName+"'";
		dataList = dao.executeQuery(hql2);
		//}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", unitName);
		map.put("rate", "1");
		dataList.add(map);
		return dataList;
	}
    
	/**
	 * ת���ӿ�汣��
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveTransferMaterial(List<Map<String, Object>> dataInfo,
			String source, String userId, String transTypeId, Date transDate) {
		try{
			for(Map<String,Object> map:dataInfo){
				Double num =Double.valueOf(map.get("realNum").toString());//ʵ������
				if(num>0){
					String transNo = map.get("transNo").toString();//������
					String versionNo = map.get("versionNo").toString();//���ϰ汾
					
					int id= Integer.parseInt(map.get("storageMaterialId").toString());//���ID
					
					Long materialId = Long.parseLong(map.get("materialId").toString());//����ID
					TMaterailTypeInfo tMaterailTypeInfo =new TMaterailTypeInfo();
                    tMaterailTypeInfo = dao.get(TMaterailTypeInfo.class,materialId);
					
					TMaterialStorage temp =new TMaterialStorage();
					temp = dao.get(TMaterialStorage.class,id);
					Double availableNum =temp.getAvailableNum(); //���������
					
					TStorageInfo tStorageInfo = new TStorageInfo();//��Դ�ⷿ
	                tStorageInfo = dao.get(TStorageInfo.class,temp.getTStorageInfo().getId());
	                
	                TStorageInfo tStorageInfo2 = new TStorageInfo();//Ŀ��ⷿ
	                tStorageInfo2 = dao.get(TStorageInfo.class,Integer.parseInt(map.get("transferStorageId").toString()));
					if(num>availableNum){
						return "����ʧ��,����������";
					}else{
						Boolean positionCtrl =Boolean.parseBoolean(map.get("positionCtrl").toString());
						Boolean batchCtrl =Boolean.parseBoolean(map.get("isBatchCtr").toString());
	                    Boolean seqCtrl =Boolean.parseBoolean(map.get("isSeqCtr").toString());
						//����---------------------------------����-------->>>>>>>>>>>>>>>>>>>>>>>>>
						temp.setAvailableNum(availableNum-num);
						temp.setProcessDate(transDate);//����ʱ��
						dao.update(temp);
						//������(����)
	                    TTransaction tTransactionOut = new TTransaction();
	                    tTransactionOut.setTransNo(transNo);//������
	                    tTransactionOut.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
	                    
	                   
	                    tTransactionOut.setTStorageInfo(tStorageInfo);//�ⷿ
	                    if(positionCtrl){
	                    	tTransactionOut.setPositionId(temp.getPositonId());//��λ
	                	}
	                    tTransactionOut.setProcessNum(-num);//�������������⸺����
	                    tTransactionOut.setPrice(temp.getMoneyNum());//�۸�
	                    tTransactionOut.setOrderNo(source);//�����ţ�����Դ��
	                    tTransactionOut.setTransDate(transDate);//����ʱ��
	                    
	                    
	                    if(batchCtrl){
	                    	tTransactionOut.setBatchId(Integer.parseInt(map.get("batchId").toString()));//����
	                	}
	                    if(seqCtrl){
	                    	tTransactionOut.setSeqId(Integer.parseInt(map.get("seqId").toString()));//����
	                	}
	                    TTransactionRelation dd= new TTransactionRelation();
	                    dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
	                    tTransactionOut.setTTransactionRelation(dd);
	                    tTransactionOut.setUserId(Integer.parseInt(userId));//��ԱId
	    				dao.save(tTransactionOut);
	    				//-----------------------------------����-----------------<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	    				
						//���---------------------------���-------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	    				int positionId = 0;//��λ
	    				//��λ
	    	        	Boolean positionCtrl2 = Boolean.valueOf(map.get("positionCtrl2").toString());
	    	        	if(positionCtrl2){
	    	        		String no = map.get("transferPositionNo").toString();
	    	        		String hql ="select new map(a.id as id)"
	    	        				  + " from TMaterielPositionInfo as a "
	    	        				  + " where a.positionNo ='"+no+"'";
	    	        		List<Map<String,Object>> list = dao.executeQuery(hql);
	    					if(list.size()>0){
	    						positionId = Integer.parseInt(list.get(0).get("id").toString());
	    					}else{
	    						TMaterielPositionInfo tMaterielPositionInfo = new TMaterielPositionInfo();
	    						tMaterielPositionInfo.setPositionNo(no);
	    						tMaterielPositionInfo.setPositionName(no);
	    						tMaterielPositionInfo.setPositionStatus("1");
	    						dao.save(tMaterielPositionInfo);
	    						
	    						//����λ��ϵ��
	    						RStoragePosition rStoragePosition =new RStoragePosition();
	    						rStoragePosition.setTMaterielPositionInfo(tMaterielPositionInfo);
	    						rStoragePosition.setTStorageInfo(tStorageInfo);
	    						dao.save(rStoragePosition);
	    						positionId = tMaterielPositionInfo.getId();
	    					}
	    	        	}
	    				//��ȡ�ⷿ�������Ƿ����
						String hql ="select new map(a.id as id,a.availableNum as availableNum,"
								+ " a.moneyNum as moneyNum)"
						        + " from TMaterialStorage as a"
						        + " where a.TMaterailTypeInfo.id ="+materialId+""
						        + " and a.versionNo ='"+versionNo+"'"
								+ " and a.TStorageInfo.id ="+map.get("transferStorageId").toString()+"";
						if(!positionCtrl2){//��λ
							hql +=" and (a.positonId is null or a.positonId ='')";
						}else{
							hql +=" and a.positonId ="+positionId+"";
						}
	                    if(!batchCtrl){//����
	                    	hql +=" and (a.batchId is null or a.batchId ='')";
						}else{
							hql +=" and a.batchId ="+map.get("batchId").toString()+"";
						}
	                    if(!seqCtrl){//����
	                    	hql +=" and (a.seqId is null or a.seqId ='')";
						}else{
							hql +=" and a.seqId ="+map.get("seqId").toString()+"";
						}
	                    List<Map<String,Object>> list = dao.executeQuery(hql);
						if(seqCtrl){//�����п���
							//�������
							if(list.size()>0){
								int tempId =Integer.parseInt(list.get(0).get("id").toString());
		                    	TMaterialStorage tt =new TMaterialStorage();
		                    	
		                    	tt = dao.get(TMaterialStorage.class,tempId);
		                    	tt.setAvailableNum(1.0);
		                    	tt.setMoneyNum(temp.getMoneyNum());
		                    	temp.setProcessDate(transDate);//����ʱ��
		                    	dao.update(tt);
							}else{
								TMaterialStorage tMaterialStorage =new TMaterialStorage();
								tMaterialStorage.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
								tMaterialStorage.setTStorageInfo(tStorageInfo2);//Ŀ��ⷿ
		                    	if(positionCtrl2){
		                    		tMaterialStorage.setPositonId(positionId);//Ŀ���λ
		                    	}
		                    	if(batchCtrl){
		                    		tMaterialStorage.setBatchId(Integer.parseInt(map.get("batchId").toString()));//��λ
		                    	}
		                    	
		                    	
		                    	tMaterialStorage.setSeqId(Integer.parseInt(map.get("seqId").toString()));//����ID
		                    	tMaterialStorage.setAvailableNum(num);//������
		                    	tMaterialStorage.setUnitName(map.get("baseUnitName").toString());
		                    	tMaterialStorage.setMoneyNum(temp.getMoneyNum());//�۸�
		                    	tMaterialStorage.setProcessDate(transDate);//����ʱ��
		                    	tMaterialStorage.setVersionNo(versionNo);//���ϰ汾
		                    	dao.save(tMaterialStorage);
							}
	                    	//������
	                        TTransaction tTransaction = new TTransaction();
	                        tTransaction.setTransNo(transNo);//������
	                        tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
	                        tTransaction.setTStorageInfo(tStorageInfo2);//�ⷿ
	                        if(positionCtrl2){
	                        	tTransaction.setPositionId(positionId);//��λ
	                    	}
	                        tTransaction.setProcessNum(num);//��������
	                        tTransaction.setPrice(temp.getMoneyNum());//�۸�
	                        tTransaction.setOrderNo(source);//�����ţ�����Դ��
	                        tTransaction.setTransDate(transDate);//����ʱ��
	                        if(batchCtrl){
	                        	tTransaction.setBatchId(Integer.parseInt(map.get("batchId").toString()));//����
	                    	}
	                        tTransaction.setSeqId(Integer.parseInt(map.get("seqId").toString()));//����
	                        TTransactionRelation tTransactionRelation= new TTransactionRelation();//��������
	                        tTransactionRelation = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
	        				tTransaction.setTTransactionRelation(tTransactionRelation);
	        				tTransaction.setUserId(Integer.parseInt(userId));//��ԱId
	        				dao.save(tTransaction);
	        				
						  }else{
		                    if(list.size()>0){
		                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
		                    	Double tempNum = Double.parseDouble(list.get(0).get("availableNum").toString());
		                    	Double tempPrice =Double.parseDouble(list.get(0).get("moneyNum").toString());
		                    	Double inSoragePrice = (tempNum*tempPrice+num*temp.getMoneyNum())/(tempNum+num);//���۸�
		                    	TMaterialStorage tt =new TMaterialStorage();
		                    	
		                    	tt = dao.get(TMaterialStorage.class,tempId);
		                    	tt.setAvailableNum(num+tempNum);
		                    	tt.setMoneyNum(inSoragePrice);
		                    	dao.update(tt);
		                    }else{
		                    	TMaterialStorage tt =new TMaterialStorage();
		                    	tt.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
		                    	tt.setTStorageInfo(tStorageInfo);//�ⷿ
		                    	if(positionCtrl2){
		                    		tt.setPositonId(positionId);//��λ
		                    	}
		                    	if(batchCtrl){
		                    		temp.setBatchId(Integer.parseInt(map.get("batchId").toString()));//����
		                    	}
		                    	tt.setAvailableNum(num);
		                    	tt.setUnitName(map.get("baseUnitName").toString());
		                    	tt.setMoneyNum(temp.getMoneyNum());
		                    	tt.setProcessDate(transDate);
		                    	tt.setVersionNo(versionNo);//���ϰ汾
		                    	dao.save(tt);
		                    }
		                    
		                    //������
		                    TTransaction tTransaction = new TTransaction();
		                    tTransaction.setTransNo(transNo);//������
		                    tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
		                    tTransaction.setTStorageInfo(tStorageInfo);//�ⷿ
		                    if(positionCtrl2){
		                    	tTransaction.setPositionId(positionId);//��λ
		                	}
		                    tTransaction.setProcessNum(num);//��������
		                    tTransaction.setPrice(temp.getMoneyNum());//�۸�
		                    tTransaction.setOrderNo(source);//�����ţ�����Դ��
		                    tTransaction.setTransDate(transDate);//����ʱ��
		                    if(batchCtrl){
		                    	tTransaction.setBatchId(Integer.parseInt(map.get("batchId").toString()));//����
		                	}
		                    TTransactionRelation tTransactionRelation= new TTransactionRelation();
		                    tTransactionRelation = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
		    				tTransaction.setTTransactionRelation(tTransactionRelation);
		    				tTransaction.setUserId(Integer.parseInt(userId));//��ԱId
		    				dao.save(tTransaction);
						}
					  }
				}
			}
		}catch(RuntimeException r){
			r.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "����ʧ��";
		}
		
		return "����ɹ�";
	}
    
	/**
	 * ��ȡ���ϰ汾��Ϣ
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> materialVersionList(String materialId) {
		String hql ="select new map(a.id as id,a.materialId as materialId,"
				+ " a.versionNo as no,a.isDefault as isDefault)"
				+ " from TMaterialVersion as a"
				+ " where a.materialId ="+materialId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
	
	
	/**
	 * ��ȡ������ϰ汾��Ϣ
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> storageMaterialVersionList(String materialId,String storageId,
			String positionId,String batchId,String seqId) {
		String hql ="select distinct new map(a.versionNo as no)"
				+ " from TMaterialStorage as a"
				+ " where a.TMaterailTypeInfo.id ="+materialId+"";
		if(null !=storageId && !storageId.equals("")){
			hql +=" and a.TStorageInfo.id ="+storageId+"";
		}
		if(null !=positionId && !positionId.equals("")){
			hql +=" and a.positonId ="+positionId+"";
		}
		if(null !=batchId && !batchId.equals("")){
			hql +=" and a.batchId ="+batchId+"";
		}
		if(null !=seqId && !seqId.equals("")){
			hql +=" and a.seqId ="+seqId+"";
		}
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	/**
	 * ����������Ϣ����
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveJobDispatchMaterialInStorage(
			List<Map<String, Object>> materialInfoList,
			List<Map<String, Object>> outInStorageList,
			List<Map<String, Object>> productSeqList, String userId,
			String transTypeId, Date transDate, String jobPlanId) {
		try{
			//��������
			TJobplanInfo tJobplanInfo = new TJobplanInfo();
			tJobplanInfo = dao.get(TJobplanInfo.class,Long.parseLong(jobPlanId));
			//������������������
			for(Map<String,Object> tt:materialInfoList){
				if(Double.parseDouble(tt.get("inStorageNum").toString())>0){
					String id =tt.get("jobdispatchMaterialId").toString();
					
					Double num = Double.parseDouble(tt.get("receivedNum").toString());
					Double instorageNum =Double.parseDouble(tt.get("inStorageNum").toString());
					
					TJobdispatchMaterial mm= new TJobdispatchMaterial();
					mm = dao.get(TJobdispatchMaterial.class,Integer.parseInt(id));
					mm.setRecNum(num-instorageNum);
					dao.save(mm);
				}
			}
			//��⴦��
			for(Map<String,Object> tt:outInStorageList){
				//������Ϣ
				Boolean positionCtrl =Boolean.parseBoolean(tt.get("positionCtrl").toString());
				Boolean batchCtrl =Boolean.parseBoolean(tt.get("isBatchCtrl").toString());
                Boolean seqCtrl =Boolean.parseBoolean(tt.get("isSeqCtrl").toString());
                
				String id = tt.get("id").toString(); 
				
				String materialId = tt.get("materialId").toString(); //����ID
				TMaterailTypeInfo tMaterailTypeInfo = new TMaterailTypeInfo();
				tMaterailTypeInfo = dao.get(TMaterailTypeInfo.class,Long.parseLong(materialId));
				
				String versionNo = tt.get("versionNo").toString(); //���ϰ汾
				String storageId = tt.get("storageId").toString();//�ⷿID
				Double num = Double.parseDouble(tt.get("realNum").toString());//�������
				Double price = Double.parseDouble(tt.get("selectedPrice").toString());//�۸�
				String unitName = tt.get("baseUnitName").toString();//��λ
				
				//�ⷿ
				TStorageInfo tStorageInfo = new TStorageInfo();
	        	tStorageInfo =dao.get(TStorageInfo.class,Integer.parseInt(storageId));
	        	
	        	int positionId = 0;//��λ
	        	int batchId =0;//����ID
	        	int seqId=0;//����ID
	        	//��λ
	        	if(positionCtrl){
	        		String no = tt.get("positionNo").toString();
	        		String hql ="select new map(a.id as id)"
	        				  + " from TMaterielPositionInfo as a "
	        				  + " where a.positionNo ='"+no+"'";
	        		List<Map<String,Object>> list = dao.executeQuery(hql);
					if(list.size()>0){
						positionId = Integer.parseInt(list.get(0).get("id").toString());
					}else{
						TMaterielPositionInfo tMaterielPositionInfo = new TMaterielPositionInfo();
						tMaterielPositionInfo.setPositionNo(no);
						tMaterielPositionInfo.setPositionName(no);
						tMaterielPositionInfo.setPositionStatus("1");
						dao.save(tMaterielPositionInfo);
						
						//����λ��ϵ��
						RStoragePosition rStoragePosition =new RStoragePosition();
						rStoragePosition.setTMaterielPositionInfo(tMaterielPositionInfo);
						rStoragePosition.setTStorageInfo(tStorageInfo);
						dao.save(rStoragePosition);
						positionId = tMaterielPositionInfo.getId();
					}
	        	}
				//������ֵ
				if(batchCtrl){
					if(null !=tt.get("batch") && tt.get("batch").toString() !=""){
						TStockMaterialbath tStockMaterialbath =new TStockMaterialbath();
						String batchNo = tt.get("batch").toString();
						String hql = "select new map(a.id as id)"
						        + " from TStockMaterialbath as a"
						        + " where a.materialId ="+materialId+""
								+ " and a.no ='"+batchNo+"'";
						List<Map<String,Object>> list = dao.executeQuery(hql);
						if(list.size()<1){
							tStockMaterialbath.setMaterialId(Integer.valueOf(materialId));
							tStockMaterialbath.setNo(tt.get("batch").toString());
							dao.save(tStockMaterialbath);
							batchId = tStockMaterialbath.getId();
						}else{
							batchId = Integer.parseInt(list.get(0).get("id").toString());
						}
					}
				}
				//����
				if(seqCtrl){//�����п���
					for(Map<String,Object> map:productSeqList){
						if(map.get("id").toString().equals(id)){
							TStockMaterialseq tStockMaterialseq =new TStockMaterialseq();
							String hql ="select new map(a.id as id)"
	    					        + " from TStockMaterialseq as a"
	    					        + " where a.materialId ="+materialId+""
	    							+ " and a.no ='"+map.get("seqNo").toString()+"'";
	    					List<Map<String,Object>> list = dao.executeQuery(hql);
	    					if(list.size()<1){
	    						tStockMaterialseq.setMaterialId(Integer.valueOf(materialId));
	    						tStockMaterialseq.setNo(map.get("seqNo").toString());
	    						dao.save(tStockMaterialseq);
	    						seqId = tStockMaterialseq.getId();
	    					}else{
	    						seqId = Integer.parseInt(list.get(0).get("id").toString());
	    					}
	    					
							//��ȡ�ⷿ�������Ƿ����
							String hql2 ="select new map(a.id as id,a.availableNum as availableNum,"
									+ " a.moneyNum as moneyNum)"
							        + " from TMaterialStorage as a"
							        + " where a.TMaterailTypeInfo.id ="+materialId+""
									+ " and a.TStorageInfo.id ="+storageId+""
									+ " and a.versionNo ='"+versionNo+"'"
								    + " and a.seqId ="+seqId+"";
							if(positionCtrl){//��λ
								hql2 +=" and (a.positonId is null or a.positonId ='')";
							}else{
								hql2 +=" and a.positonId ="+positionId+"";
							}
		                    if(batchCtrl){//����
		                    	hql2 +=" and (a.batchId is null or a.batchId ='')";
							}else{
								hql2 +=" and a.batchId ="+batchId+"";
							}
		                    List<Map<String,Object>> list2 = dao.executeQuery(hql2);
		                    if(list2.size()>0){
		                    	//�����������
		                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
		                    	TMaterialStorage temp =new TMaterialStorage();
		                    	temp = dao.get(TMaterialStorage.class,tempId);
		                    	temp.setAvailableNum(1.0);//������
		                    	temp.setMoneyNum(price);//�۸�
		                    	temp.setProcessDate(transDate);//����ʱ��
		                    	dao.update(temp);
		                    }else{
								//���
								TMaterialStorage temp =new TMaterialStorage();
								
								temp.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
		                    	temp.setTStorageInfo(tStorageInfo);//�ⷿ
		                    	if(positionCtrl){
		                    		temp.setPositonId(positionId);//��λ
		                    	}
		                    	if(batchCtrl){
		                    		temp.setBatchId(batchId);//����
		                    	}
		                    	
		                    	temp.setSeqId(seqId);//����ID
		                    	temp.setAvailableNum(1.0);//������
		                    	temp.setUnitName(unitName);//��λ
		                    	temp.setMoneyNum(price);//�۸�
		                    	temp.setVersionNo(versionNo);//�汾
		                    	temp.setProcessDate(transDate);//����ʱ��
		                    	dao.save(temp);
		                    }
	                    	
	                    	//������
	                        TTransaction tTransaction = new TTransaction();
	                        tTransaction.setTransNo(tt.get("transNo").toString());//������
	                        tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
	                        tTransaction.setTStorageInfo(tStorageInfo);//�ⷿ
	                        if(positionCtrl){
	                        	tTransaction.setPositionId(positionId);//��λ
	                    	}
	                        tTransaction.setProcessNum(1.0);//��������
	                        
	                        tTransaction.setPrice(price);//�۸�
	                        tTransaction.setOrderNo(tJobplanInfo.getNo());//�����ţ�����Դ��
	                        tTransaction.setTransDate(transDate);//����ʱ��
	                        if(batchCtrl){
	                        	tTransaction.setBatchId(batchId);//����
	                    	}
	                        tTransaction.setSeqId(seqId);//����
	                        TTransactionRelation dd= new TTransactionRelation();
	                        dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
	        				tTransaction.setTTransactionRelation(dd);
	        				tTransaction.setUserId(Integer.parseInt(userId));//��ԱId
	        				dao.save(tTransaction);
						}
					}
					
				}else{
					//��ȡ�ⷿ�������Ƿ����
					String hql ="select new map(a.id as id,a.availableNum as availableNum,"
							+ " a.moneyNum as moneyNum)"
					        + " from TMaterialStorage as a"
					        + " where a.TMaterailTypeInfo.id ="+materialId+""
							+ " and a.TStorageInfo.id ="+storageId+""
							+ " and a.versionNo ='"+versionNo+"'"
						    + " and (a.seqId is null or a.seqId ='')";
					if(positionCtrl){//��λ
						hql +=" and (a.positonId is null or a.positonId ='')";
					}else{
						hql +=" and a.positonId ="+positionId+"";
					}
                    if(batchCtrl){//����
                    	hql +=" and (a.batchId is null or a.batchId ='')";
					}else{
						hql +=" and a.batchId ="+batchId+"";
					}
                    List<Map<String,Object>> list = dao.executeQuery(hql);
                    if(list.size()>0){
                    	int tempId =Integer.parseInt(list.get(0).get("id").toString());
                    	Double tempNum = Double.parseDouble(list.get(0).get("availableNum").toString());
                    	Double tempPrice =Double.parseDouble(list.get(0).get("moneyNum").toString());
                    	Double inSoragePrice = (tempNum*tempPrice+num*price)/(tempNum+num);//���۸�
                    	TMaterialStorage temp =new TMaterialStorage();
                    	
                    	temp = dao.get(TMaterialStorage.class,tempId);
                    	temp.setAvailableNum(num+tempNum);
                    	temp.setMoneyNum(inSoragePrice);
                    	temp.setProcessDate(transDate);//����ʱ��
                    	dao.update(temp);
                    }else{
                    	TMaterialStorage temp =new TMaterialStorage();
                    	temp.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
                    	temp.setTStorageInfo(tStorageInfo);//�ⷿ
                    	if(positionCtrl){
                    		temp.setPositonId(positionId);//��λ
                    	}
                    	if(batchCtrl){
                    		temp.setBatchId(batchId);//��λ
                    	}
                    	temp.setAvailableNum(num);
                    	temp.setUnitName(unitName);//��λ
                    	temp.setMoneyNum(price);
                    	temp.setVersionNo(versionNo);//�汾
                    	temp.setProcessDate(transDate);//����ʱ��
                    	dao.save(temp);
                    }
                    
                    //������
                    TTransaction tTransaction = new TTransaction();
                    tTransaction.setTransNo(tt.get("transNo").toString());//������
                    tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//����Id
                    tTransaction.setTStorageInfo(tStorageInfo);//�ⷿ
                    if(positionCtrl){
                    	tTransaction.setPositionId(positionId);//��λ
                	}
                    tTransaction.setProcessNum(num);//��������
                    tTransaction.setPrice(price);//�۸�
                    tTransaction.setOrderNo(tJobplanInfo.getNo());//�����ţ�����Դ��
                    tTransaction.setTransDate(transDate);//����ʱ��
                    if(batchId !=0){
                    	tTransaction.setBatchId(batchId);//����
                	}
                    TTransactionRelation dd= new TTransactionRelation();
                    dd = dao.get(TTransactionRelation.class,Integer.parseInt(transTypeId));
    				tTransaction.setTTransactionRelation(dd);
    				tTransaction.setUserId(Integer.parseInt(userId));//��ԱId
    				dao.save(tTransaction);
				}
			}
			return "����ɹ�";
		}catch(RuntimeException r){
			r.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "����ʧ��";
		}
	}
    
	
	/**
	 * ��ȡ���ƿⷿ��Ϣ
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> limitStorgeList(String materialId) {
		
		//TODO ȡ�ñ�����������֯��������д�Ŀⷿ
		String hql ="select new map(a.id as id,a.materialId as materialId,"
				+ " a.versionNo as no,a.isDefault as isDefault)"
				+ " from TMaterialVersion as a"
				+ " where a.materialId ="+materialId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
    
	
	/**
	 * ��ȡ���ƻ�λ��Ϣ
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> limitPositionList(String materialId,
			String storageId) {
		//TODO ȡ�ñ�����������֯��������д�Ļ�λ
		String hql ="select new map(a.id as id,a.materialId as materialId,"
				+ " a.versionNo as no,a.isDefault as isDefault)"
				+ " from TMaterialVersion as a"
				+ " where a.materialId ="+materialId+"";
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}
	
	
	
	
	
	
	
	
	
	
}

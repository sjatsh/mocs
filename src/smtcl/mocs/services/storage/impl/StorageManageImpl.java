package smtcl.mocs.services.storage.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;

import smtcl.mocs.model.TMaterialPositionModel;
import smtcl.mocs.model.TStorageInfoModel;
import smtcl.mocs.pojos.storage.RStoragePosition;
import smtcl.mocs.pojos.storage.TMaterielPositionInfo;
import smtcl.mocs.pojos.storage.TStorageInfo;
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
				+ " u.id as id, "
				+ " u.unitName as name) "
				+ " from TUnitInfo u,TUnitType t "
				+ " where u.TUnitType.id = t.id "
				+ " and t.unitTypeName ='�����λ' and t.nodeId ='"+nodeId+"' and u.nodeId ='"+nodeId+"'";
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
				+ "s.storageName as name) "
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
	
	
	
	
	
	
	
}

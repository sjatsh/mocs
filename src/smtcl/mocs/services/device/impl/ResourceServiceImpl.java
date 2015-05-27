package smtcl.mocs.services.device.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.CuttertypeModel;
import smtcl.mocs.model.TFixtureModel;
import smtcl.mocs.pojos.device.TFixtureClassInfo;
import smtcl.mocs.pojos.device.TUserFixture;
import smtcl.mocs.pojos.device.TUserResource;
import smtcl.mocs.pojos.job.TCutterClassInfo;
import smtcl.mocs.pojos.job.TCuttertypeInfo;
import smtcl.mocs.pojos.job.TFixtureTypeInfo;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TMaterialClass;
import smtcl.mocs.pojos.job.TPartClassInfo;
import smtcl.mocs.pojos.job.TProgramInfo;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.device.IResourceService;
import smtcl.mocs.utils.device.StringUtils;


/**
 * 
 * ��ѯ��Դ���ʵ����
 * @���ߣ�LiuChuanzhen
 * @����ʱ�䣺2012-11-13 ����13:00:25
 * @�޸��ߣ�JiangFeng
 * @�޸����ڣ�
 * @�޸�˵����
 * @�汾��V1.0
 */
public class ResourceServiceImpl extends GenericServiceSpringImpl<TUserResource, String> implements IResourceService {
	private ICommonService commonService;
	
	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * ��ȡ����Ʒ������
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<Map<String, Object>> getConsumeStorage(int pageNo, int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = "SELECT new Map(a.userResourceId as userResourceId,"
				+ " a.TNodes.nodeName as nodeName,"
				+ " a.uresoType as uresoType,  "
				+ " a.uresoNo as uresoNo,"
				+ " a.uresoNorms as uresoNorms,"
				+ " a.uresoProvider as uresoProvider,"
				+ " a.uresoSerialNo as uresoSerialNo,"
				+ " a.uresoUnit as uresoUnit,"
				+ " a.uresoStock as uresoStock,"
				+ " DATE_FORMAT(a.uresoUpdatetime ,'%Y-%m-%d %T') as uresoUpdatetime) "
				+ " FROM TUserResource a  " + " where 1=1 ";
		if (nodeIds != null) {
			hql += " AND a.TNodes.nodeId in(" + nodeIds + ")";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY  DATE_FORMAT(a.uresoUpdatetime ,'%Y-%m-%d %T')  DESC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * ��ȡ����Ʒʹ����Ϣ
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<Map<String, Object>> getConsumeUse(int pageNo, int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = "SELECT new Map(a.id as id,"
				+ " a.TNodes.nodeName as nodeName,"
				+ " DATE_FORMAT(a.beginTime ,'%Y-%m-%d') as beginTime,  " 
				+ " DATE_FORMAT(a.endTime ,'%Y-%m-%d') as endTime,"
				+ " a.usedAmount as usedAmount," 
				+ " a.uresoType as uresoType,"
				+ " a.uresoNo as uresoNo," 
				+ " a.uresoNorms as uresoNorms,"
				+ " a.uresoProvider as uresoProvider,"
				+ " a.uresoSerialNo as uresoSerialNo,"
				+ "a.uresoUnit as uresoUnit) " 
				+ " FROM TNodeCostResource a "
				+ " where 1=1 ";
		if (nodeIds != null) {
			hql += " AND a.TNodes.nodeId in(" + nodeIds + ")";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * ��ȡ��������Ʒ����
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllConType() {
		String hql = " SELECT DISTINCT a.uresoType as uresoType FROM TNodeCostResource a WHERE a.uresoType IS NOT NULL";
		return dao.executeQuery(hql);
	}
	
	/**
	 * ��ѯ�о��嵥
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<Map<String, Object>> getClampList(int pageNo, int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = "SELECT new Map(a.userFixtureId as userFixtureId,"
				+ " a.ufixNo as ufixNo,  " 
				+ " a.ufixType as ufixType,"
				+ " a.ufixNorms as ufixNorms," 
				+ " a.ufixName as ufixName,"
				+ " a.ufixProvider as ufixProvider,"
				+ " a.ufixMaterialNo as ufixMaterialNo,"
				+ " a.ufixMaking as ufixMaking,"
				+ " a.ufixStorePlace as ufixStorePlace,"
				+ " a.ufixUsingMachine as ufixUsingMachine) "
				+ " FROM TUserFixture a ,TNodes c "
				+ " WHERE a.TNodes.nodeId= c.nodeId ";
		if (nodeIds != null) {
			hql += " AND c.nodeId in(" + nodeIds + ")";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY a.userFixtureId DESC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * �о�ʹ����ʷ
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<Map<String, Object>> getClampUse(int pageNo, int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = "SELECT NEW MAP(a.id as id,"
				+ "DATE_FORMAT(a.startUsageTime ,'%Y-%m-%d %T') as startUsageTime,"
				+ "DATE_FORMAT(a.endUsageTime ,'%Y-%m-%d %T') as endUsageTime,"
				+ "a.usageMemo as usageMemo," 
				+ "a.ufixNo as ufixNo,"
				+ "a.ufixType as ufixType," 
				+ "a.ufixName as ufixName,"
				+ "a.ufixProvider as ufixProvider,"
				+ "a.ufixMaterialNo as ufixMaterialNo,"
				+ "a.ufixMaking as ufixMaking,"
				+ "a.ufixStorePlace as ufixStorePlace,"
				+ "a.ufixUsingMachine as ufixUsingMachine) "
				+ " FROM TUserFixtureUsage a, TNodes c "
				+ " WHERE a.TNodes.nodeId= c.nodeId ";
		if (nodeIds != null) {
			hql += " AND c.nodeId in(" + nodeIds + ")";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY DATE_FORMAT(a.startUsageTime ,'%Y-%m-%d %T')  DESC ";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}
	
	/* ��ȡ��ǰ�ڵ��������豸��--�о�
	@SuppressWarnings("unchecked")
	public List<TUserFixture> clafindByMachineName(String nodeIds) {
		String hql = " SELECT DISTINCT a.ufixUsingMachine as ucutUsingMachine FROM TUserFixture a WHERE a.ufixUsingMachine IS NOT NULL AND  a.TNodes.nodeId in("
				+ nodeIds + ")";
		return dao.executeQuery(hql);
	}

	// ��ȡ��ǰ�ڵ��������豸��--����
	@SuppressWarnings("unchecked")
	public List<TUserCutter> cutfindByMachineName(String nodeIds) {
		String hql = " SELECT DISTINCT a.ucutUsingMachine as ucutUsingMachine FROM TUserCutter a WHERE a.ucutUsingMachine IS NOT NULL AND  a.TNodes.nodeId in("
				+ nodeIds + ")";
		return dao.executeQuery(hql);
	}*/

	/**
	 * �����嵥
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<Map<String, Object>> getCutleryList(int pageNo,
			int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = "SELECT new Map(a.userCutterId as userCutterId,"
				+ " a.TNodes.nodeName as nodeName," 
				+ " a.ucutNo as ucutNo,  "
				+ " a.ucutType as ucutType," 
				+ " a.ucutNorms as ucutNorms,"
				+ " a.ucutStatus as ucutStatus,"
				+ " a.ucutProvider as ucutProvider,"
				+ " a.ucutMaterialNo as ucutMaterialNo,"
				+ " a.ucutMakings as ucutMakings, "
				+ " a.ucutStorePlace as ucutStorePlace,"
				+ " a.ucutUsingMachine as ucutUsingMachine,"
				+ " a.ucutRemainLifetime as ucutRemainLifetime, "
				+ "a.ucutDesignLifetime as ucutDesignLifetime) "
				+ " FROM TUserCutter a, TNodes c "
				+ " WHERE a.TNodes.nodeId= c.nodeId  ";
		if (nodeIds != null) {
			hql += " AND c.nodeId in(" + nodeIds + ")";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY a.userCutterId DESC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * ����ʹ����ʷ
	 * @param pageNo
	 * @param pageSize
	 * @param parameters
	 * @param nodeIds
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public IDataCollection<Map<String, Object>> getCutleryUse(int pageNo, int pageSize, Collection<Parameter> parameters, String nodeIds) {
		String hql = "SELECT NEW Map(a.id as id,"
				+ " a.TNodes.nodeName as nodeName,"
				+ " DATE_FORMAT(a.startUsageTime, '%Y-%m-%d %T') as startUsageTime,"
				+ " DATE_FORMAT(a.endUsageTime, '%Y-%m-%d %T') as endUsageTime,"
				+ " a.cuttingTime as cuttingTime," 
				+ " a.ucutNo as ucutNo,"
				+ " a.ucutType as ucutType," 
				+ " a.ucutNorms as ucutNorms,"
				+ " a.ucutStatus as ucutStatus,"
				+ " a.ucutProvider as ucutProvider,"
				+ " a.ucutMaterialNo as ucutMaterialNo,"
				+ " a.ucutMakings as ucutMakings,"
				+ " a.ucutStorePlace as ucutStorePlace,"
				+ " a.ucutUsingMachine as ucutUsingMachine,"
				+ " a.ucutRemainLifetime as ucutRemainLifetime,"
				+ "a.ucutDesignLifetime as ucutDesignLifetime) "
				+ " FROM TUserCutterUsage a , TNodes c "
				+ "  WHERE a.TNodes.nodeId= c.nodeId  ";
		if (nodeIds != null) {
			hql += " AND c.nodeId in(" + nodeIds + ")";
		}
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY DATE_FORMAT(a.startUsageTime, '%Y-%m-%d %T') DESC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * ��ѯ���е�������
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllUCutType() {
		String hql = " SELECT DISTINCT a.ucutType as ucutType FROM TUserCutter a";
		return dao.executeQuery(hql);
	}
	
	/**
	 **********************************************��Դ����*********************************************
	 */
	/**
	 * ����������ѯ���Ӧ�����Ͻڵ���Ϣ
	 * @param search
	 * @return
	 */
	public TreeNode getMaterailTreeNodeOnAll(String search,String nodeid){
		TreeNode root =new DefaultTreeNode("��",null);
		TreeNode root2 =new DefaultTreeNode("��",null);
		root2.setExpanded(true);
		Collection<Parameter> parameters = new HashSet<Parameter>();
		if(null==search||"".equals(search)){
			String hql="from TMaterialClass where MClassid='-999' and MStatus=0 ";
			List<TMaterialClass> tmclist=dao.executeQuery(hql, parameters);
			TMaterialClass tmc;
			if(null!=tmclist&&tmclist.size()>0){
				 tmc=(TMaterialClass)tmclist.get(0);
			}else{
				tmc =new TMaterialClass();
			}
			root2=newNodeChildren(tmc, root,nodeid);
			
		}else{
			List<TMaterialClass> tmclist=this.getTMaterialClassByName(search);
			if(null!=tmclist&&tmclist.size()>0){
				TMaterialClass tmcOther=tmclist.get(0);
				root2=newNodeChildren(tmcOther, root,nodeid);
			}
		}
		return root;
	}
	
	/**
	 * �ݹ鷵���������ڵ�
	 * @param tmc
	 * @param parent
	 * @return
	 */  
	public TreeNode newNodeChildren(TMaterialClass tmc,TreeNode parent,String nodeid) {
		TreeNode newNode = new DefaultTreeNode(tmc, parent);
		newNode.setExpanded(true);
		for (TMaterialClass tt:tmc.getTMaterialClasses()) {
			if(tt.getMStatus()==0&&tt.getNodeId().equals(nodeid)){
				newNodeChildren(tt, newNode,nodeid);
			}
		}
		return newNode;
	}
	/**
	 * �������ֲ�ѯ�������
	 * @param search
	 * @return
	 */
	public List<TMaterialClass> getTMaterialClassByName(String search){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TMaterialClass where MStatus='0' and MClassname='"+search+"' ";
		List<TMaterialClass> tmclist=dao.executeQuery(hql, parameters);
		return tmclist;
	}
	/**
	 * ����id��ѯ�������
	 * @param search
	 * @return
	 */
	public List<TMaterialClass> getTMaterialClassById(String id){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TMaterialClass where MStatus=0 and MClassid="+id;
		List<TMaterialClass> tmclist=dao.executeQuery(hql, parameters);
		return tmclist;
	}
	
	/**
	 * �����������
	 * @param tmc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String saveTMaterialClass(TMaterialClass tmc){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TMaterialClass "
				 + " where MStatus=0 "
				 + " and (MClassname='"+tmc.getMClassname()+"'or MClassno='"+tmc.getMClassno()+"' or MClasstype='"+tmc.getMClasstype()+"')  "
				 + " and nodeId='"+tmc.getNodeId()+"'";
		List<TMaterialClass> tmclist=dao.executeQuery(hql, parameters);
		if(null!=tmclist&&tmclist.size()>0){
			return "�Ѵ���";
		}else{
			tmc.setMStatus(0);
			try {
				commonService.save(tmc);
				return "����ɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "����ʧ��";
			}
		}
	}
	/**
	 * ��������
	 * @param tmc
	 * @return
	 */
	public String updateTMaterialClass(TMaterialClass tmc){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TMaterialClass where MStatus=0 and (MClassname='"+tmc.getMClassname()+"' or MClassno='"+tmc.getMClassno()+"'" +
				" ) and MClasstype='"+tmc.getMClasstype()+"' and MClassid<>'"+tmc.getMClassid()+"'";
		List<TMaterialClass> tmclist=dao.executeQuery(hql, parameters);
		if(null!=tmclist&&tmclist.size()>0){
			return "�Ѵ���";
		}else{
			try {
				commonService.update(tmc);
				return "���³ɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "����ʧ��";
			}
			
		}
	}
	/**
	 * ��ȡ���������������
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getSelectTMaterialClassForAll(String nodeid){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select new Map(MClassname as MClassname) from TMaterialClass where MStatus=0 and nodeId='"+nodeid+"'";
		List<Map<String,String>> tmclist=dao.executeQuery(hql, parameters);
		return tmclist;
	}
	/**
	 * ���ݸ��ڵ��ѯ������ϸ��Ϣ
	 * @param pid
	 * @return
	 */
	public List<TMaterailTypeInfo> getTMaterailTypeInfoByPid(String pid,String nodeid,String search){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TMaterailTypeInfo "
				+ " where status=0"  
				+ " and nodeId='"+nodeid+"'";
		if(null!=pid&&!pid.equals("")){
			hql=hql+"and PId="+pid;
		}
		if(null!=search&&!search.equals("")){
			hql=hql+"and (name like '%"+search+"%' or no like '%"+search+"%' or typeno like '%"+search+"%')";
		}
		List<TMaterailTypeInfo> tmclist=dao.executeQuery(hql, parameters);
		return tmclist;
	}
	
	/**
	 * ���������ϸ
	 * @param tmti
	 * @return
	 */
	public String saveTMaterailTypeInfo(TMaterailTypeInfo tmti){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TMaterailTypeInfo where status=0 and no='"+tmti.getNo()+"' and id<>"+tmti.getId()+" and nodeId='"+tmti.getNodeId()+"'";
		List<TMaterailTypeInfo> tmclist=dao.executeQuery(hql, parameters);
		if(null!=tmclist&&tmclist.size()>0){
			return "�Ѵ���";
		}else{
			try {
				TMaterailTypeInfo t=new TMaterailTypeInfo();
				t.setNo(tmti.getNo());
				t.setName(tmti.getName());
				t.setNorm(tmti.getNorm());
				t.setUnit(tmti.getUnit());
				t.setPrice(tmti.getPrice());
				t.setTypeno(tmti.getTypeno());
				t.setMemo(tmti.getMemo());
				t.setPId(tmti.getPId());
				t.setStatus(tmti.getStatus());
				t.setDensity(tmti.getDensity());
				t.setNodeId(tmti.getNodeId());
				commonService.save(t);
				return "����ɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "����ʧ��";
			}
		}
		
	}
	
	/**
	 * ���������ϸ
	 * @param tmti
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String updateTMaterailTypeInfo(TMaterailTypeInfo tmti){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TMaterailTypeInfo where status=0 and no='"+tmti.getNo()+"' and id<>"+tmti.getId();
		List<TMaterailTypeInfo> tmclist=dao.executeQuery(hql, parameters);
		if(null!=tmclist&&tmclist.size()>0){
			return "�Ѵ���";
		}else{
			try {
				commonService.update(tmti);
				return "���³ɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "����ʧ��";
			}
		}
	}
	
	public void deleteTMaterailTypeInfo(TMaterailTypeInfo tmti){
		commonService.update(tmti);
	}
	/**
	 * �������id��ȡTfixtureConfig�����datatable����
	 * @param classId
	 * @return
	 */
	public List<Map<String,Object>> getTfixtureConfigByClassId(String classId,String nodeid,String query){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "SELECT new Map(a.id as id,"
				+ " a.fixturesNo as ufixNo,  " 
				+ " a.fixturesName as ufixName,"
				+ " a.fixturesDescription as ufixMemo,"
				+ " b.name as type) "
				+ " FROM TFixtureTypeInfo a,TFixtureClassInfo b"
				+ " WHERE a.fixtureclassId=b.id "
				+ " and a.nodeId='"+nodeid+"'";
		if(null!=classId&&!"".equals(classId)){
			hql=hql+ " and  a.fixtureclassId='"+classId+"'";
		}
		if(null!=query&&!query.equals("")){
			hql=hql+ " and  (a.fixturesNo like '%"+query+"%' or a.fixturesName like '%"+query+"%')";
		}
		return dao.executeQuery(hql, parameters);
	}
	/**
	 * �о����ڵ�ģ����ѯ
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TFixtureClassInfo> getTFixtureClassInfoByQuery(String query,String nodeid){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TFixtureClassInfo where (nodeid is null or nodeid='"+nodeid+"')";
		if(null!=query&&!"".equals(query)){
			hql=hql+" and  name like '%"+query+"%'";
		}
		hql=hql+" order by id";
		List<TFixtureClassInfo> rs=dao.executeQuery(hql, parameters);
		return rs;
	}
	
	/**
	 * �о����͸���
	 * @return
	 */
	public String updateTUserFixture(TFixtureModel tfm){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TFixtureTypeInfo where (fixturesName='"+tfm.getUfixName()+"'"
				 + " or fixturesNo='"+tfm.getUfixNo()+"') and id<>"+tfm.getId();
		List<TFixtureTypeInfo> list=dao.executeQuery(hql, parameters);
		if(null==list&&list.size()>0){
			return "�Ѵ���";
		}else{
			try {
				String hql2="from TFixtureTypeInfo where id='"+tfm.getId()+"'";
				List<TFixtureTypeInfo> tlist=dao.executeQuery(hql2, parameters);
				TFixtureTypeInfo tuf=tlist.get(0);
				tuf.setFixturesName(tfm.getUfixName());
				tuf.setFixturesNo(tfm.getUfixNo());
				TFixtureClassInfo tfci=this.getTFixtureClassInfoByName(tfm.getType()).get(0);
				tuf.setFixtureclassId(tfci.getId());
				tuf.setFixturesDescription(tfm.getUfixMemo());
				return "���³ɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "����ʧ��";
			}
		}
	}
	/**
	 * ���ݼо����id���ؼо����
	 * @param id
	 * @return
	 */
	public List<TFixtureClassInfo> getTFixtureClassInfoByName(String name){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TFixtureClassInfo where name='"+name+"'";
		return dao.executeQuery(hql, parameters);
	}
	/**
	 * �о�����
	 * @param tuf
	 * @return
	 */
	public String addTUserFixture(TFixtureTypeInfo tuf){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TFixtureTypeInfo where (fixturesName='"+tuf.getFixturesName()+"'"
				 + " or fixturesNo='"+tuf.getFixturesNo()+"') and nodeId='"+tuf.getNodeId()+"'";
		List<TFixtureTypeInfo> list=dao.executeQuery(hql, parameters);
		if(null!=list&&list.size()>0){
			return "�Ѵ���";
		}else{
			try {
				commonService.save(tuf);
				return "���ӳɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "���ӳɹ�";
			}
		}
	}
	/**
	 * �о�ɾ��
	 * @param id
	 * @return
	 */
	public String deleteTUserFixture(String id){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		try {
			String hql="from TFixtureTypeInfo where id='"+id+"'";
			List<TFixtureTypeInfo> rs=dao.executeQuery(hql, parameters);
			if(null!=rs&&rs.size()>0){
				TFixtureTypeInfo tt=rs.get(0);
				commonService.delete(tt);
				return "ɾ���ɹ�";
			}else{
				return "ɾ��ʧ��";
			}
		} catch (Exception e) {
			return "ɾ��ʧ��";
		}
	}
	
	/**
	 * ���ݳ����Ų�ѯ������Ϣ
	 * @param progNo
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfoByProgNo(String progNo) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TProgramInfo where progNo = " + progNo;
		List<TProgramInfo> tprlist = dao.executeQuery(hql, parameters);
		return tprlist;
	}

	/**
	 * ���ݳ������Ʋ�ѯ������Ϣ
	 * @param progName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TProgramInfo> getTprogramInfoByProgName(String progName) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TProgramInfo where progName ='" + progName + "'";
		List<TProgramInfo> tprlist = dao.executeQuery(hql, parameters);
		return tprlist;
	}

	/**
	 * ���ݴ����˲�ѯ������Ϣ
	 * @param creator
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TProgramInfo> getTprogramInfoByCreator(String creator) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TProgramInfo where creator ='" + creator + "'";
		List<TProgramInfo> tprlist = dao.executeQuery(hql, parameters);
		return tprlist;
	}

	/**
	 * ����������ѯTProgramInfo�����������Ϊ�գ����ѯȫ������
	 * @param searchStr
	 * @return
	 */
	public List<TProgramInfo> getTprogramInfo(String searchStr,String nodeid) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = " from TProgramInfo where nodeid='"+nodeid+"' ";	
		if(searchStr != null && !"".equals(searchStr)){
			hql = hql + " and (progNo like '%" + searchStr + "%'" + "or progName like '%"  + searchStr + "%'" + "or creator like '%" + searchStr + "%')";
		}
		List<TProgramInfo>  rs=dao.executeQuery(hql, parameters);
        return rs;
	}
	
	/**
	 * ���ӳ�����Ϣ
	 * @param tpr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String saveTprogramInfo(TProgramInfo tpr) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TProgramInfo where (progNo = '" + tpr.getProgNo() + "' or progName='"+tpr.getProgName()+"') and nodeid='"+tpr.getNodeid()+"'";
		List<TProgramInfo> tprlist = dao.executeQuery(hql, parameters);
		try {
			if(tprlist != null && tprlist.size() < 1){
				commonService.save(tpr);
				return "���ӳɹ�";
			}else {
				return "�Ѵ���";
			}
		} catch (Exception e1) {

			e1.printStackTrace();		
		}
		return "����ʧ��";
	}
	
	/**
	 * ���³�����Ϣ
	 * @param tpr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String updateTprogramInfo(TProgramInfo tpr){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TProgramInfo where (progNo='" + tpr.getProgNo() + "' or progName='"+tpr.getProgName()
				+"') and nodeid='"+tpr.getNodeid()+"' and id<>'"+tpr.getId()+"'";
		List<TProgramInfo> tprlist = dao.executeQuery(hql, parameters);
		try {
			if(null == tprlist || tprlist.size() < 1){
				List<TProgramInfo> rslist = dao.executeQuery("from TProgramInfo where id='"+tpr.getId()+"'");
				TProgramInfo tt=rslist.get(0);
				tt.setProgNo(tpr.getProgNo());
				tt.setProgName(tpr.getProgName());
				Date date = new Date();
				tt.setUpdateTime(date);
				tt.setUpdator(tpr.getUpdator());
				tt.setContent(tpr.getContent());
				commonService.update(tt);
				return "���³ɹ�";
			}else{	
				return "�Ѵ���";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "����ʧ�ܣ�";
		}
	}
	
	/**
	 * ɾ������
	 * @param tpr
	 * @return
	 */
	public void deleteTprogramInfo(TProgramInfo tpr){
		String hql = "delete from TProgramInfo where id = '" + tpr.getId() + "'";
		try {
			if(null != tpr.getProgNo() && ""!= tpr.getProgNo()){
				commonService.executeUpdate(hql);
			}
			
			} catch (Exception e) {
			
			e.printStackTrace();
		}		
	}
	
	/**
	 * ��ȡ���������
	 * @param search
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCutterClassTree(String search,String nodeid){
		Collection<Parameter> parameters = new HashSet<Parameter>();                
		String hql = "select new Map( id as id ,name as name,status as status) "
				+ "from TCutterClassInfo where (nodeid is null or nodeid='"+nodeid+"')";
		if(null != search && !"".equals(search)){
			hql = hql + " where ( name like '%" + search + "%')";
		}
		System.out.println(hql);
		List<Map<String, Object>> list = dao.executeQuery(hql, parameters);		
		return list;
		
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @param pid
	 * @return
	 */
	public List<Map<String, Object>> getCutterById(Integer pid,String nodeid,String search){
		String hql = "select new Map( a.id as id ,a.name as name,a.no as no,a.norm as norm,a.remark as remark,b.name as cutter) "
				+ " from TCuttertypeInfo a , TCutterClassInfo b "
				+ " where a.pid = b.id "
				
			    + " and a.nodeId='"+nodeid+"'";	
			if(null!=pid&&!"".equals(pid)){
				hql=hql+" and a.pid ='" + pid+"'";
			}
			if(null!=search&&!"".equals(search)){
				hql=hql+" and (a.id like '%" + search +"%' or a.name like '%" +search+"%')";
			}
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String,Object>> ts = dao.executeQuery(hql, parameters);		
		return ts;
	}
	
	/**
	 * ���ӵ�����Ϣ
	 * @param cutt
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addCutterManage(CuttertypeModel cutt){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TCuttertypeInfo where (name='"+cutt.getName()+"' or no='"+cutt.getNo() + "') and nodeId='"+cutt.getNodeid()+"'";
		List list=dao.executeQuery(hql, parameters);
		if(null!=list&&list.size()>0){
			return "�Ѵ���";
		}else{
			String hqlc="from TCutterClassInfo where name='"+cutt.getCutterclass().trim()+"'";
			List<TCutterClassInfo> clist=dao.executeQuery(hqlc, parameters);
			if(null!=clist&&clist.size()>0){
				TCutterClassInfo tcc=clist.get(0);
				TCuttertypeInfo add=new TCuttertypeInfo();
				add.setName(cutt.getName());
				add.setPid(tcc.getId());
				add.setNo(cutt.getNo());
				add.setNorm(cutt.getNorm());
				add.setRemark(cutt.getRemark());
				add.setNodeId(cutt.getNodeid());
				try {
					commonService.save(add);
					return "���ӳɹ�";
				} catch (Exception e) {
					e.printStackTrace();
					return "����ʧ��";
				}
			}else {
				return "����ʧ��";
			}
		}

	}
	
	/**
	 * ɾ��������Ϣ
	 * @param cutt
	 * @return
	 */
	public String deleteCutterManage(CuttertypeModel cutt){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TCuttertypeInfo where id = '" + cutt.getId() +  "'";
		List lsit = dao.executeQuery(hql, parameters);		
		try {
			commonService.delete(lsit.get(0));			
			return "ɾ���ɹ�";
		} catch (Exception e) {			
			e.printStackTrace();
			return "ɾ��ʧ��";
		}
	}
	
	/**
	 * �޸ĵ�����Ϣ
	 * @param cutt
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String updateCutterManage(CuttertypeModel cutt){		
		Collection<Parameter> parameters = new HashSet<Parameter>();		
		String hql = "from TCuttertypeInfo where (name='" + cutt.getName() + "' or no='" + cutt.getNo() + "' or norm= '" + cutt.getNorm() + "' or remark='" + cutt.getRemark() + "') and id<>'" +cutt.getId()+"'";
		List list=dao.executeQuery(hql, parameters);
		if(null!=list&&list.size()>0){
			return "�Ѵ���";
		}else{
			String hql2="from TCutterClassInfo where name='"+cutt.getCutterclass()+"'";
			List<TCutterClassInfo> clist=dao.executeQuery(hql2, parameters);
			String hqlu="from TCuttertypeInfo where id='"+cutt.getId()+"'";
			TCuttertypeInfo up =(TCuttertypeInfo) dao.executeQuery(hqlu, parameters).get(0);
			up.setName(cutt.getName());
			up.setNo(cutt.getNo());
			up.setNorm(cutt.getNorm());
			up.setRemark(cutt.getRemark());
			//up.setPid(Integer.parseInt(clist.get(0).getId().toString()));			
			try {
				commonService.update(up);
				return "���³ɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "����ʧ��";
			}
		}
			
	}


}
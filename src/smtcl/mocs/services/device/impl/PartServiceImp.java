package smtcl.mocs.services.device.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import smtcl.mocs.model.CuttertypeModel;
import smtcl.mocs.model.MaterialsModel;
import smtcl.mocs.model.PartModel;
import smtcl.mocs.model.TProcessEquipmentModel;
import smtcl.mocs.model.TProcessFixturetypeModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUserEquNcprogs;
import smtcl.mocs.pojos.job.RProcessQuality;
import smtcl.mocs.pojos.job.TCuttertypeInfo;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;
import smtcl.mocs.pojos.job.TFixtureTypeInfo;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TPartClassInfo;
import smtcl.mocs.pojos.job.TPartProcessCost;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessCuttertypeInfo;
import smtcl.mocs.pojos.job.TProcessEquipment;
import smtcl.mocs.pojos.job.TProcessFixturetype;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProcessmaterialInfo;
import smtcl.mocs.pojos.job.TProcessplanInfo;
import smtcl.mocs.pojos.job.TQualityParam;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.utils.device.StringUtils;
import smtcl.mocs.dao.device.ICommonDao;

/**
 * ���Service
 * @����ʱ�� 2013-07-09
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0  
 */
public class PartServiceImp extends GenericServiceSpringImpl<TNodes, String> implements IPartService{
	/**
	 * ����service
	 */
	
	
	private  ICommonDao commonDao;
	
	
	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	/**
	 * ����������ѯTPartTypeInfo�����������Ϊ�գ����ѯȫ������
	 * @param query
	 * @return List<TPartTypeInfo>
	 * @throws Exception 
	 */
	public List<TPartTypeInfo> getTPartTypeInfo(String query,String status,String nodeid){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<TPartTypeInfo> rslist=new ArrayList<TPartTypeInfo>();
		String hql="from TPartTypeInfo where nodeid='"+nodeid+"' ";
		if(null!=status&&!"".equals(status)){
			hql=hql+" and status='"+status+"' ";
		}
		if(null!=query&&!"".equals(query)){
			hql=hql+" and (id like '%"+query+"%'" +
					" or no like '%"+query+"%'" +
					" or name like '%"+query+"%')";
		}
		rslist=commonDao.executeQuery(hql, parameters);
		
		return rslist;
	}
	/**
	 * 
	 * @param nodeid
	 * @return
	 */
	public List<Map<String,Object>> getTPartTypeInfo(String nodeid,String partNo,Date startTime,Date endTime){
		List<Map<String,Object>> rslist=new ArrayList<Map<String,Object>>();
		String hql="select new Map("
				+ " a.id as Id,"
				+ " a.no as Name,"
				+ " a.name as No)"
				+ " from TPartTypeInfo a where  a.status<>'ɾ��'"
				+ " and a.nodeid='"+nodeid+"' ";
		if(null!=partNo&&!"".equals(partNo)){
			hql+=" and a.no='"+partNo+"'";
		}
		if(null!=startTime&&!"".equals(startTime.toString())){
			hql+=" and a.updateDate>='"+StringUtils.formatDate(startTime, 3)+"'";
		}
		if(null!=endTime&&!"".equals(endTime.toString())){
			hql+=" and a.updateDate<='"+StringUtils.formatDate(endTime, 3)+"'";
		}
		hql+="order by a.no";
		rslist=commonDao.executeQuery(hql);
		return rslist;
	}
	
	
	/**
	 * ����TPartTypeInfo
	 * @param tPartTypeInfo
	 */
	@SuppressWarnings("unchecked")
	public String saveTPartTypeInfo(TPartTypeInfo tPartTypeInfo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		Date date=new Date();
		String hql="from TPartTypeInfo where (no='"+tPartTypeInfo.getNo()+"' or name='"+tPartTypeInfo.getName()+"') and id<>"+tPartTypeInfo.getId();
		List<TPartTypeInfo> rs=commonDao.executeQuery(hql, parameters);
		if(rs!=null&&rs.size()>0){
			return "�Ѵ���";
		}else{
			String hql2="from TPartTypeInfo where id="+tPartTypeInfo.getId();
			List<TPartTypeInfo> rss=(List<TPartTypeInfo>)commonDao.executeQuery(hql2, parameters);
			TPartTypeInfo tt=rss.get(0);
			tt.setNo(tPartTypeInfo.getNo());
			tt.setName(tPartTypeInfo.getName());
			tt.setTypeNo(tPartTypeInfo.getTypeNo());
			tt.setDrawingno(tPartTypeInfo.getDrawingno());
			tt.setFilename(tPartTypeInfo.getFilename());
			tt.setVersion(tPartTypeInfo.getVersion());
			tt.setSource(tPartTypeInfo.getSource());
			tt.setDescription(tPartTypeInfo.getDescription());
			tt.setCreateDate(tPartTypeInfo.getCreateDate());
			tt.setUpdateDate(date);
			tt.setOriginalStatus(tPartTypeInfo.getOriginalStatus());
			tt.setPath(tPartTypeInfo.getPath());
			try {
				commonDao.update(tt);
				return "���³ɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "����ʧ��";
			}
		}
		
	}
	
	/**
	 * ɾ��TPartTypeInfo
	 * @param tPartTypeInfo
	 */
	public void deleteTPartTypeInfo(TPartTypeInfo tPartTypeInfo){
		TPartTypeInfo tt=(TPartTypeInfo)this.getTPartTypeInfoByNo(tPartTypeInfo.getNo()).get(0);
		if(null!=tPartTypeInfo.getStatus()&&!"ɾ��".equals(tPartTypeInfo.getStatus())){
			tt.setOriginalStatus(tPartTypeInfo.getStatus());
		}
		tt.setStatus("ɾ��");
		try {
			commonDao.update(tt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ��� TPartTypeInfo
	 * @param tPartTypeInfo
	 */
	public String addTPartTypeInfo(TPartTypeInfo tPartTypeInfo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		Date date=new Date();
		String hql="from TPartTypeInfo where (no='"+tPartTypeInfo.getNo()+"' or name='"+tPartTypeInfo.getName()+"') "
				+ " and nodeid='"+tPartTypeInfo.getNodeid()+"'";
		List<TPartTypeInfo> rs=commonDao.executeQuery(hql, parameters);
		if(null!=rs&&rs.size()>0){
			return "�Ѵ���";
		}else{
			tPartTypeInfo.setCreateDate(date);
			tPartTypeInfo.setUpdateDate(date);
			tPartTypeInfo.setStatus("�½�");
			try {
				commonDao.save(tPartTypeInfo);
				return "��ӳɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "���ʧ��";
			}
		}
		
		
		
	}
	/**
	 * ���������Ų�ѯ�����Ϣ
	 * @param tPartTypeInfo
	 */
	public List<TPartTypeInfo> getTPartTypeInfoByNo(String no){
	   Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TPartTypeInfo where no='"+no+"'";
		return commonDao.executeQuery(hql, parameters);
		
	}
	/**
	 * �����������Ų�ѯ������
	 * @param typeNo
	 * @return
	 */
	public List<TPartTypeInfo> getTPartTypeInfoByTypeNo(String typeNo){
		   Collection<Parameter> parameters = new HashSet<Parameter>();
			String hql="from TPartTypeInfo ";
			if(null!=typeNo&&!"".equals(typeNo)){
				hql=hql+" where typeNo='"+typeNo+"'";
			}
			return commonDao.executeQuery(hql, parameters);
			
		}
	/**
	 * ͣ�ã����÷���
	 * @param tPartTypeInfo
	 * @param status
	 */
	public void stopOrStartTPartTypeInfo(TPartTypeInfo tPartTypeInfo,String status){
		try {
			/*if(status.equals("ͣ��")){
				if(tPartTypeInfo.getStatus().equals("�½�")||tPartTypeInfo.getStatus().equals("Ͷ��")){
					tPartTypeInfo.setOriginalStatus(tPartTypeInfo.getStatus());
					tPartTypeInfo.setStatus(status);
				}
				
			}else{
				if(tPartTypeInfo.getStatus().equals("ͣ��")){
					tPartTypeInfo.setStatus(tPartTypeInfo.getOriginalStatus());
				}
				
			}*/
			tPartTypeInfo.setStatus(tPartTypeInfo.getOriginalStatus());
			commonDao.update(tPartTypeInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	/**
	 * ��TPartClassInfo���л�ȡ���е�typeNo
	 * @return
	 */
	public List<String> getTypeNo(String nodeid){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select distinct no  from TPartClassInfo where status=0 and nodeid='"+nodeid+"'";
		return commonDao.executeQuery(hql, parameters);
	}
	
	
	
	/**
	 * ����������ѯTPartClassInfo�����������Ϊ�գ����ѯȫ������
	 * @param query
	 * @return List<TPartTypeInfo>
	 * @throws Exception 
	 */
	public List<TPartClassInfo> getTPartClassInfo(String query,String nodeid){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<TPartClassInfo> rslist=new ArrayList<TPartClassInfo>();
		String hql="from TPartClassInfo where status=0 and nodeid='"+nodeid+"' ";
		if(null!=query&&!"".equals(query)){
			hql=hql+" and (id like '%"+query+"%'" +
					" or no like '%"+query+"%'" +
					" or name like '%"+query+"%') ";
				
		}
		rslist=commonDao.executeQuery(hql, parameters);
		return rslist;
	}
	
	/**
	 * ����TPartClassInfo
	 * @param tPartClassInfo
	 */
	@SuppressWarnings("unchecked")
	public String saveTPartClassInfo(TPartClassInfo tPartClassInfo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TPartClassInfo where (no='"+tPartClassInfo.getNo()+"' or name='"+tPartClassInfo.getName()+"') "
				+ " and id<>"+tPartClassInfo.getId()+""
				+ " and status=0";
		List<TPartClassInfo> rs = commonDao.executeQuery(hql, parameters);
		if(null!=rs&&rs.size()>0){
			return "�Ѵ���";
		}else{
			String hql2="from TPartClassInfo where id="+tPartClassInfo.getId();
			List<TPartClassInfo> rss = commonDao.executeQuery(hql2, parameters);
			TPartClassInfo tt=rss.get(0);
			tt.setName(tPartClassInfo.getName());
			tt.setDescription(tPartClassInfo.getDescription());
			tt.setNo(tPartClassInfo.getNo());
			tt.setProperty(tPartClassInfo.getProperty());
			try {
				commonDao.update(tt);
				return "���³ɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "����ʧ��";
			}
		}
		
	}
	
	/**
	 * ɾ��TPartClassInfo
	 * @param tPartClassInfo
	 */
	public void deleteTPartClassInfo(TPartClassInfo tPartClassInfo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		TPartClassInfo tt=(TPartClassInfo)commonDao.executeQuery("from TPartClassInfo where id="+tPartClassInfo.getId(), parameters).get(0);
		tt.setStatus(1);
		try {
			commonDao.update(tt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ��� TPartClassInfo
	 * @param tPartClassInfo
	 */
	public String addTPartClassInfo(TPartClassInfo tPartClassInfo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TPartClassInfo where (no='"+tPartClassInfo.getNo()+"' or name='"+tPartClassInfo.getName()+"') "
				+ " and nodeid='"+tPartClassInfo.getNodeid()+"' and status=0";
		List<TPartClassInfo> rs = commonDao.executeQuery(hql, parameters);
		if(null!=rs&&rs.size()>0){
			return "�Ѵ���";
		}else{
			try {
				tPartClassInfo.setStatus(0);
				commonDao.save(tPartClassInfo);
				return "��ӳɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "���ʧ��";
			}
		}
		
		
	}
	
	/**
	 * ��ȡ��������б���
	 * @return
	 */
	public List<Map<String,Object>> getPartTree(String query,String nodeid){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select new Map(" +
				  " name as name," +
				  " no as no," +
				  " drawingno as drawingno) " +
				  " from TPartTypeInfo where status not in('ͣ��','ɾ��') "
				 +" and nodeid='"+nodeid+"'";
		  if(null!=query&&!"".equals(query)){
			  hql=hql+"  and (id like '%"+query+"%'" +
						" or no like '%"+query+"%'" +
						" or name like '%"+query+"%')";
		  }
		  List<Map<String,Object>> rs=commonDao.executeQuery(hql, parameters);
		return rs;
	}
	
	/**
	 * ���������Ż�ȡ���շ���
	 * @param no
	 * @return
	 */
	public List<TProcessplanInfo> getTProcessplanInfo(String no,String parm){
		String hql="from TProcessplanInfo a where a.TPartTypeInfo.no='"+no+"' and status=0";
		if(null!=parm&&!"".equals(parm)){
			hql=hql+" and name like '%"+parm+"%'";
		}
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<TProcessplanInfo> rs=commonDao.executeQuery(hql, parameters);
		return rs;
	}
	
	public List<Map<String,Object>> getTProcessplanInfo(String no){
//		String hql="select new Map("
//				+ " a.id as Id,"
//				+ " '' as No,"
//				+ " a.name as Name)"
//				+ " from TProcessplanInfo a where a.TPartTypeInfo.no='"+no+"' and status=0 order by a.id";
//		List<Map<String,Object>> rs=commonDao.executeQuery(hql);
//		return rs;
		String hql="select new Map("
				+ " a.name as processPlanName,"
				+ " b.processOrder as processOrder,"
				+ " b.name as processName,"
				+ " b.id as processId)"
				+ " from TProcessplanInfo a,TProcessInfo b"
				+ " where a.id=b.TProcessplanInfo.id"
				+ " and a.TPartTypeInfo.no='"+no+"' "
				+ " and a.status=0 "
				+ " and a.defaultSelected=1 "
				+ " order by b.processOrder";
		return dao.executeQuery(hql);
	}
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public List<TProcessInfo> getTProcessInfo(String processPlanID){
		String hql="from TProcessInfo a where a.TProcessplanInfo.id='"+processPlanID+"' and status=0 order by processOrder";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<TProcessInfo> rs=commonDao.executeQuery(hql, parameters);
		return rs;
	}
	public List<Map<String,Object>> getTProcessInfoByGYFA(String processPlanID){
		String hql="select new Map("
				+ " a.id as Id,"
				+ " a.no as No,"
				+ " a.name as Name,"
				+ " a.processOrder as processOrder)"
				+ " from TProcessInfo a where a.TProcessplanInfo.id='"+processPlanID+"' and status=0 order by processOrder";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String,Object>> rs=commonDao.executeQuery(hql, parameters);
		return rs;
	}
	/**
	 * ����ǰ���Ų��ҹ���
	 * @param onlineProcessID
	 * @return
	 */
	public List<TProcessInfo> getTProcessInfoByOnlineProcessID(String onlineProcessID){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TProcessInfo where id='"+onlineProcessID+"'";
		return commonDao.executeQuery(hql, parameters);
	}
	/**
	 * ���¹���
	 * @param tProcessInfo
	 */
	public void saveTProcessInfo(TProcessInfo tProcessInfo){
		try {
			commonDao.update(tProcessInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��������
	 * @param tProcessInfo
	 */
	public void addTProcessInfo(TProcessInfo tProcessInfo){
		try {
			
			commonDao.save(tProcessInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ɾ������
	 * @param tPartClassInfo
	 */
	public void deleteTProcessInfo(TProcessInfo tProcessInfo){
		tProcessInfo.setStatus(1);
		try {
			commonDao.update(tProcessInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ��ȡ�������еĳ����б�
	 * @return
	 */
	public List<Map<String,Object>> getSelectTUserEquNcprogs(String nodeid){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select new Map(id as id,progName as progName) "
				+ " from TProgramInfo "
				+ " order by id ";
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * ��ȡ�������еĳ����б�
	 * @return
	 */
	public List<TUserEquNcprogs> getSelectTUserEquNcprogsById(String id){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TUserEquNcprogs where id='"+id+"'";
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * ��ȡ�������й��շ����б�
	 * @return
	 */
	public List<Map<String,Object>> getSelectTProcessplanInfo(){
		String hql="select new Map(id as id,name as name) from TProcessplanInfo group by id ";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String,Object>> rs=commonDao.executeQuery(hql, parameters);
		return rs;
	}
	
	/**
	 * ��ȡ�������мо���Ϣ�б�
	 * @return
	 */
	public List<TFixtureTypeInfo> getSelectTFixturesInfo(String fixtureclassId,String nodeid){
		String hql="from TFixtureTypeInfo "
				 +" where fixtureclassId='"+fixtureclassId+"'"
				 +" and nodeId='"+nodeid+"'"
				+ " order by id";
		
		hql=hql+" ";
		Collection<Parameter> parameters = new HashSet<Parameter>();      
		return commonDao.executeQuery(hql,parameters);
	}
	/**
	 * ���ݼо߱�Ų�ѯ�о���Ϣ
	 * @param no
	 * @return
	 */
	public List<TFixtureTypeInfo> getTFixtureTypeInfoById(String Id){
		String hql="from TFixtureTypeInfo where id='"+Id+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * ��ȡ�������й���վ��Ϣ
	 * @return
	 */
	public List<Map<String,Object>> getSelectTWorkstationInfo(){
		String hql="select new Map(id as id)from TWorkstationInfo group by id ";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String,Object>> rs=commonDao.executeQuery(hql, parameters);
		return rs;
	}
	
	/**
	 * ��ȡ�������л��������б�
	 * @return
	 */
	public List<Map<String,Object>> getSelectTEquipmenttypeInfo(){
		String hql="select new Map(id as id,equipmentType as equipmentType)from TEquipmenttypeInfo group by id ";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String,Object>> rs=commonDao.executeQuery(hql, parameters);
		return rs;
	}
	/**
	 * ���سɱ�����
	 * @param pprs
	 * @return
	 */
	public List<Map<String,Object>> getCBData(List<TProcessInfo> pprs){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String ids="";
		for(TProcessInfo pp:pprs){
			ids=ids+",'"+pp.getId()+"'";
		}
		if(null!=pprs&&pprs.size()>0){
			ids=ids.substring(1,ids.length());
		}
		String hql="select new Map(" +
				  " a.TProcessInfo.no as no," +
				  " a.TProcessInfo.name as name," +
				  " a.mainMaterialCost as mainMaterialCost," +
				  " a.subsidiaryMaterialCost as subsidiaryMaterialCost," +
				  " a.peopleCost as peopleCost," +
				  " a.energyCost as energyCost," +
				  " a.deviceCost as deviceCost," +
				  " a.resourceCost as resourceCost," +
				  " a.mainMaterialCost+a.subsidiaryMaterialCost+a.peopleCost+a.energyCost+a.deviceCost+a.resourceCost as total)" +
				  " from TPartProcessCost a" +
				  " where a.TProcessInfo.id in("+ids+")";
		return commonDao.executeQuery(hql, parameters);
	}
	
	public List<Map<String,Object>> getCBData(String processId){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select new Map(" +
				  " a.mainMaterialCost as mainMaterialCost," +
				  " a.subsidiaryMaterialCost as subsidiaryMaterialCost," +
				  " a.peopleCost as peopleCost," +
				  " a.energyCost as energyCost," +
				  " a.deviceCost as deviceCost," +
				  " a.resourceCost as resourceCost," +
				  " a.mainMaterialCost+a.subsidiaryMaterialCost+a.peopleCost+a.energyCost+a.deviceCost+a.resourceCost as total)" +
				  " from TPartProcessCost a" +
				  " where a.TProcessInfo.id ='"+processId+"'";
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * ������������
	 * @param pprs
	 * @return
	 */
	public List<Map<String,Object>> getWLData(List<TProcessInfo> pprs){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String ids="";
		for(TProcessInfo pp:pprs){
			ids=ids+",'"+pp.getId()+"'";
		}
		if(null!=pprs&&pprs.size()>0){
			ids=ids.substring(1,ids.length());
		}
		String hql="select new Map(" +
				 " a.TProcessInfo.no as no," +
				 " a.TProcessInfo.name as name," +
				 " a.TMaterailTypeInfo.no as wlno," +
				 " a.TMaterailTypeInfo.name as wlname," +
				 " a.TMaterailTypeInfo.norm as wlnorm," +
				 " a.requirementNum as requirementNum," +
				 " a.requirementType as requirementType)" +
				 " from TProcessmaterialInfo a" +
				 " where a.TProcessInfo.id in("+ids+")";
		
		
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * ���ؼо�����
	 * @param processPlanID
	 * @return
	 */
	public List<Map<String,Object>> getJJData(String processPlanID){
		String hql="select new Map(" +
				  " a.no as no," +
				  " a.name as name," +
				  " b.fixturesNo as fixturesNo," +
				  " b.fixturesName as fixturesName," +
				  " a.installTime as installTime," +
				  " a.uninstallTime as uninstallTime)" +
				  " from TProcessInfo a,TFixtureTypeInfo b ,TProcessFixturetype c"
				+ " where a.id=c.processId and b.id=c.fixturetypeId"
				+ " and a.TProcessplanInfo.id='"+processPlanID+"' and a.status=0";
		
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	/**
	 * ���ص�����������
	 * @param processPlanID
	 * @return
	 */
	public List<Map<String,Object>>  getDJData(String processPlanID){
		String hql="select new Map(" +
				  " a.no as no," +
				  " a.name as name,"
				+ " c.requirementNum as num," +
				  " b.no as djno," +
				  " b.name as djname)" +
				  " from TProcessInfo a ,TCuttertypeInfo b ,TProcessCuttertypeInfo c where " +
				  " c.processId=a.id " +
				  " and c.cuttertypeId=b.id "+
				  " and a.TProcessplanInfo.id='"+processPlanID+"' and a.status=0 ";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * ���ػ�̨����
	 * @param processPlanID
	 * @return
	 */
	public List<Map<String,Object>> getJTData(String processPlanID){
//		String hql="select new Map(" +
//				  " a.no as no," +
//				  " a.name as name," +
//				  " d.equipmentType as equipmentType," +
//				  " b.progName as programmfile," +
//				  " a.processingTime as processingTime," +
//				  " a.theoryWorktime as theoryWorktime," +
//				  " a.capacity as capacity)" +
//				  " from TProcessInfo a ,TProgramInfo b ,TProcessEquipment c,TEquipmenttypeInfo d where "
//				+ " a.programId=b.id and a.id=c.processId and d.id=c.equipmentTypeId" +
//				  " and a.TProcessplanInfo.id='"+processPlanID+"' and a.status=0";
		String sql= " SELECT  d.no,d.name,d.equipmentType,d.processing_time ,d.theorywork_time,d.capacity,e.prog_name as programmfile"
					+" FROM ("
					+"	SELECT  a.no ,a.name ,b.equipmentType ,a.processing_time , a.theorywork_time,a.capacity ,a.programID" 
					+"	FROM t_process_info a,r_process_equipment c,t_equipmenttype_info b "
					+"	WHERE  a.ID = c.processID AND b.ID = c.equipmentTypeID  AND a.status = 0"
					+"	AND a.processPlanID = "+processPlanID+" "
					+"	 ) d "
					+" LEFT JOIN t_program_info e ON d.programID=e.id";
//		Collection<Parameter> parameters = new HashSet<Parameter>();
//		return commonDao.executeQuery(hql, parameters);
		return commonDao.executeNativeQuery(sql);
	}
	
	
	/**
	 * ���ݹ��շ���id��ȡ���շ�������
	 * @param id
	 * @return
	 */
	public List<TProcessplanInfo> getTProcessplanInfoById(String id){
		String hql="from TProcessplanInfo where id='"+id+"' and status=0";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	
	/**
	 * ��ȡ�豸����
	 * @return
	 */
	public List<TEquipmenttypeInfo> getTEquipmenttypeInfo(){
		String hql="from TEquipmenttypeInfo";
		
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * ��ȡ�豸����
	 * @return
	 */
	public List<TEquipmenttypeInfo> getTEquipmenttypeInfoById(String id){
		String hql="from TEquipmenttypeInfo where id='"+id+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	/**
	 * ���ݹ���id��ѯ ������Ϣ
	 * @param no
	 * @return
	 */
	public List<TProcessmaterialInfo> getTProcessmaterialInfo(String id){
		String hql="from TProcessmaterialInfo a where a.TProcessInfo.id='"+id+"'";
		return commonDao.executeQuery(hql);
	}
	/**
	 * ��ȡ������������������
	 * @return
	 */
	public List<Map<String,Object>> getSelectTMaterailTypeInfo(String nodeid){
		String hql="select new Map(a.no as no)" +
				  " from TMaterailTypeInfo a"
				+ " where a.status=0 "
				+ " and a.nodeId='"+nodeid+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	/**
	 * �����������ͱ�Ż�ȡ��������
	 * @param no
	 * @return
	 */
	public List<TMaterailTypeInfo> getSelectTMaterailTypeInfoByNo(String no){
		String hql= " from TMaterailTypeInfo a where a.no='"+no+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * �������ϱ�Ż�ȡ����
	 * @return
	 */
	public List<TMaterailTypeInfo> getTMaterailTypeInfo(String no){
		String hql="from TMaterailTypeInfo a where a.no='"+no+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * ��ȡ��������������
	 * @return
	 */
	public List<Map<String,Object>> getSelectTCuttertypeInfo(String nodeid){
		String hql="select new Map(a.no as no)" +
				  " from TCuttertypeInfo a"
				+ " where a.nodeId='"+nodeid+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	/**
	 * ���ݵ��߱�Ż�ȡ������Ϣ
	 * @param no
	 * @return
	 */
	public List<TCuttertypeInfo> getTCuttertypeInfo(String no){
		String hql="from TCuttertypeInfo a where a.no='"+no+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * ���������򵼱���
	 * @param part �򵼸���ģ��
	 * @param materialList ��������
	 * @param cuttertypeList ��������
	 * @param partTypeno ������
	 * @param tqplist �ʼ�����
	 * @param teti �豸����
	 * @param tftmlist �о�����
	 */
	public String savePartProcessGuide(PartModel part,List<MaterialsModel> materialList,List<CuttertypeModel> cuttertypeList,String partTypeNo,
			List<TQualityParam> tqplist,List<TProcessEquipmentModel> teti,List<TProcessFixturetypeModel> tftmlist){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String buzhou="";
		try {
			List<TPartTypeInfo> tPartTypeInfoList=this.getTPartTypeInfoByNo(partTypeNo);
			/**
			 **********************************������Ϣ����׼��************************************** 
			 */
			String hql="from TProcessInfo where no='"+part.getNo()+"' and status=0 "
					+ " and nodeid='"+part.getNodeid()+"'";
			List<TProcessInfo> rs=commonDao.executeQuery(hql, parameters);
			if(null!=rs&&rs.size()>0){
				return "�Ѵ���";
			}
			TProcessInfo tProcessInfo=new TProcessInfo();//Ҫ����Ĺ������
			tProcessInfo.setStatus(0);
			buzhou="���������ô���!";
			tProcessInfo.setNo(part.getNo());//���ù�����
			buzhou="�����������ô���!";
			tProcessInfo.setName(part.getName());//���ù�������
			buzhou="�����������ô���!";
			tProcessInfo.setProcessType(Integer.parseInt(part.getProcessType()));//���ù�������
			buzhou="����ڵ�id���ô���!";
			tProcessInfo.setNodeid(part.getNodeid());
			buzhou="ǰ�������ô���!";
			if(null!=part.getOnlineProcessID()&&!"".equals(part.getOnlineProcessID())){//����ǰ����
				tProcessInfo.setOnlineProcessId(Long.parseLong(part.getOnlineProcessID()));
			}
			buzhou="����������ô���!";
			if(null!=part.getProcessOrder()&&!"".equals(part.getProcessOrder())){
				tProcessInfo.setProcessOrder(Integer.parseInt(part.getProcessOrder()));//���ù������
			}
			buzhou="�Ƿ����߹������ô���!";
			if(null!=part.getOfflineProcess()&&!"".equals(part.getOfflineProcess())){
				tProcessInfo.setOfflineProcess(Integer.parseInt(part.getOfflineProcess()));
			}
			buzhou="�����Ƿ��������ô���!";
			if(null!=part.getProcessDuration()&&!"".equals(part.getProcessDuration())){
				tProcessInfo.setProcessDuration(Integer.parseInt(part.getProcessDuration()));
			}
			buzhou="ָ���ļ����ô���!";
			if(null!=part.getFile()&&!"".equals(part.getFile())){
				tProcessInfo.setFile(part.getFile());
			}
			buzhou="�������ô���!";
			if(null!=part.getDescription()&&!"".equals(part.getDescription())){
				tProcessInfo.setDescription(part.getDescription());
			}
			buzhou="װ��ʱ�����ô���!";
			tProcessInfo.setTProcessplanInfo(part.gettProcessplanInfo()); //���ù��շ���
			if(null!=part.getInstallTime()&&!"".equals(part.getInstallTime())){
				tProcessInfo.setInstallTime(Integer.parseInt(part.getInstallTime()));
			}
			buzhou="ж��ʱ�����ô���!";
			if(null!=part.getUninstallTime()&&!"".equals(part.getUninstallTime())){
				tProcessInfo.setUninstallTime(Integer.parseInt(part.getUninstallTime()));
			}
			buzhou="�Ƿ�ؼ����ô���!";
			if(null!=part.getIscheck()&&!"".equals(part.getIscheck())){
				tProcessInfo.setNeedcheck(Integer.parseInt(part.getIscheck()));//�Ƿ�ؼ�
			}
			buzhou="�����������ô���!";
			if(null!=part.getChecktype()&&!"".equals(part.getChecktype())){
				tProcessInfo.setCheckType(Integer.parseInt(part.getChecktype()));//��������
			}
			/**
			 **********************************��̨����׼��************************************** 
			 */
			buzhou="�������ô���!";
			if(null!=part.getProgramID()&&!"��ѡ��".equals(part.getProgramID())){
				tProcessInfo.setProgramId(Long.parseLong(part.getProgramID()));;//����
			}
			buzhou="�ӹ�ʱ�����ô���!";
			if(null!=part.getProcessingTime()&&!"".equals(part.getProcessingTime())){
				tProcessInfo.setProcessingTime(Double.parseDouble(part.getProcessingTime()));
			}
			buzhou="����ʱ�����ô���!";
			if(null!=part.getTheoryWorktime()&&!"".equals(part.getTheoryWorktime())){
				tProcessInfo.setTheoryWorktime(Integer.parseInt(part.getTheoryWorktime()));//����ʱ��
			}
			buzhou="Ԥ�Ʋ���	���ô���!";
			if(null!=part.getCapacity()&&!"".equals(part.getCapacity())){
				tProcessInfo.setCapacity(Double.parseDouble(part.getCapacity()));
			}
		
			buzhou="���湤�򱨴�";
			commonDao.save(tProcessInfo);
			System.out.println(tProcessInfo.getId());
			/**
			 **********************************�豸����׼��************************************** 
			 */
			buzhou="�����豸����";
			for(TProcessEquipmentModel tp:teti){
				TProcessEquipment tpe=new TProcessEquipment();
				tpe.setProcessId(tProcessInfo.getId());
				tpe.setEquipmentTypeId(Long.parseLong(tp.getEquipmentTypeId().toString()));
				if(null!=tp.getEquipmentId()){
					tpe.setEquipmentId(Long.parseLong(tp.getEquipmentId().toString()));
				}
				commonDao.save(tpe);//��ӹ�������豸
			}
			/**
			 **********************************������Ϣ����׼��************************************** 
			 */
			buzhou="�������ϱ���";
			for(MaterialsModel mm:materialList){
				 String mphql=" from TProcessmaterialInfo where TProcessInfo.id="+tProcessInfo.getId()+" "
				 		    + " and TMaterailTypeInfo.no='"+mm.getWlNo()+"'";
				 List list=commonDao.executeQuery(mphql);
				 if(null!=list&&list.size()>0){
					 return "�����ظ�";
				 }else{
					 TProcessmaterialInfo tpinfo=new TProcessmaterialInfo(); 
					 List<TMaterailTypeInfo>  tmaterail=this.getSelectTMaterailTypeInfoByNo(mm.getWlNo());
					 tpinfo.setTMaterailTypeInfo(tmaterail.get(0));//������������
					 tpinfo.setRequirementType(mm.getWlType());
					 tpinfo.setRequirementNum(Integer.parseInt(mm.getWlNumber()));
					 tpinfo.setTProcessInfo(tProcessInfo);//���ù���
					 commonDao.save(tpinfo);//����������Ϣ
				 }
				 
			 }
			
			/**
			 **********************************��Դ��Ϣ����׼��************************************** 
			 */
			buzhou="����о߱���";
			for(TProcessFixturetypeModel tpfm:tftmlist){
				TProcessFixturetype tpf=new TProcessFixturetype();
				tpf.setFixturetypeId(Long.parseLong(tpfm.getFixturetypeId().toString()));
				tpf.setProcessId(tProcessInfo.getId());
				commonDao.save(tpf);
			}
			 buzhou="���浶�߱���";
			for(CuttertypeModel mm:cuttertypeList){
				 TProcessCuttertypeInfo tc=new TProcessCuttertypeInfo(); 
				 tc.setProcessId(tProcessInfo.getId());//���ù���id
				 tc.setCuttertypeId(Long.parseLong(mm.getDid()));//���õ���id
				 tc.setRequirementNum(Integer.parseInt(mm.getRequirementNum()));//��������
				 tc.setStatus(0);
				 commonDao.save(tc);//���浶����Ϣ
			 }
			/**
			 **********************************�ɱ���Ϣ����׼��************************************** 
			 */
			 TPartProcessCost tPartProcessCost=new TPartProcessCost();
			 tPartProcessCost.setTProcessInfo(tProcessInfo);//���ù���
			 buzhou="�ɱ����ô���!";
			 if(null!=part.getMainMaterialCost()&&!"".equals(part.getMainMaterialCost())){
				 tPartProcessCost.setMainMaterialCost(Double.parseDouble(part.getMainMaterialCost()));
			 }
			 if(null!=part.getPeopleCost()&&!"".equals(part.getPeopleCost())){
				 tPartProcessCost.setPeopleCost(Double.parseDouble(part.getPeopleCost()));
			 }
			 if(null!=part.getSubsidiaryMaterialCost()&&!"".equals(part.getSubsidiaryMaterialCost())){
				 tPartProcessCost.setSubsidiaryMaterialCost(Double.parseDouble(part.getSubsidiaryMaterialCost()));
			 }
			 if(null!=part.getEnergyCost()&&!"".equals(part.getEnergyCost())){
				 tPartProcessCost.setEnergyCost(Double.parseDouble(part.getEnergyCost()));
			 }
			 if(null!=part.getDeviceCost()&&!"".equals(part.getDeviceCost())){
				 tPartProcessCost.setDeviceCost(Double.parseDouble(part.getDeviceCost()));
			 }
			 if(null!=part.getResourceCost()&&!"".equals(part.getResourceCost())){
				 tPartProcessCost.setResourceCost(Double.parseDouble(part.getResourceCost()));
			 }
			 tPartProcessCost.setTPartTypeInfo(tPartTypeInfoList.get(0));
			 buzhou="����ɱ���Ϣ����";
			 commonDao.save(tPartProcessCost);//����ɱ���Ϣ
			 /**
				 **********************************�ʼ�����׼��************************************** 
			 */
			 buzhou="�ʼ����ô���!";
			 for(TQualityParam tqp:tqplist){
				 TQualityParam tt=new TQualityParam();
				if(null!=tqp.getQualityNo())
					tt.setQualityNo(tqp.getQualityNo());
				if(null!=tqp.getQualityName())
					tt.setQualityName(tqp.getQualityName());
				if(null!=tqp.getStandardValue())
					tt.setStandardValue(tqp.getStandardValue());
				if(null!=tqp.getUnit())
					tt.setUnit(tqp.getUnit());
				if(null!=tqp.getMaxValue())
					tt.setMaxValue(tqp.getMaxValue());
				if(null!=tqp.getMinValue())
					tt.setMinValue(tqp.getMinValue());
				if(null!=tqp.getMinTolerance())
					tt.setMinTolerance(tqp.getMinTolerance());
				if(null!=tqp.getMaxTolerance())
					tt.setMaxTolerance(tqp.getMaxTolerance());
				if(null!=tqp.getIsKey())
					tt.setIsKey(tqp.getIsKey());
				if(null!=tqp.getDescription())
					tt.setDescription(tqp.getDescription());
				if(null!=tqp.getCheckTime())
					tt.setCheckTime(tqp.getCheckTime());
				if(null!=tqp.getCheckType())
					tt.setCheckType(tqp.getCheckType());
				buzhou="�����ʼ챨��";
				 commonDao.save(tt);
				 RProcessQuality rpq=new RProcessQuality();
				 rpq.setProcessId(Long.parseLong(tProcessInfo.getId().toString()));
				 rpq.setQualityId(tt.getId());
				 buzhou="���湤���ʼ챨��";
				 commonDao.save(rpq);
			 }
			 return "����ɹ�";
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return buzhou;
		}
	}
	/**
	 * �����򵼸���(���°�)
	 */
	public String savePartProcessGuideTest(PartModel part,List<MaterialsModel> materialList,List<CuttertypeModel> cuttertypeList,String partTypeNo,
			List<TQualityParam> tqplist,List<TProcessEquipmentModel> teti,List<TProcessFixturetypeModel> tftmlist){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String buzhou="";
		try {
			List<TPartTypeInfo> tPartTypeInfoList=this.getTPartTypeInfoByNo(partTypeNo);
			/**
			 **********************************������Ϣ����׼��************************************** 
			 */
			String hql="from TProcessInfo where (no='"+part.getNo()+"') and id<>'"+part.getId()+"' "
					+ " and status=0 and nodeid='"+part.getNodeid()+"'";
			List<TProcessInfo> rs=commonDao.executeQuery(hql, parameters);
			if(null!=rs&&rs.size()>0){
				return "�Ѵ���";
			}
			String hqltp="from TProcessInfo where id='"+part.getId()+"'";
			List<TProcessInfo> rstp=commonDao.executeQuery(hqltp, parameters);
			TProcessInfo tProcessInfo=rstp.get(0);//Ҫ����Ĺ������
			tProcessInfo.setStatus(0);
			buzhou="���������ô���!";
			tProcessInfo.setNo(part.getNo());
			buzhou="�����������ô���!";
			tProcessInfo.setName(part.getName());
			buzhou="�����������ô���!";
			tProcessInfo.setProcessType(Integer.parseInt(part.getProcessType()));//���ù�������
			buzhou="ǰ�������ô���!";
			if(null!=part.getOnlineProcessID()&&!"".equals(part.getOnlineProcessID())){
				tProcessInfo.setOnlineProcessId(Long.parseLong(part.getOnlineProcessID()));
			}
			buzhou="����������ô���!";
			if(null!=part.getProcessOrder()&&!"".equals(part.getProcessOrder())){
				tProcessInfo.setProcessOrder(Integer.parseInt(part.getProcessOrder()));//���ù������
			}
			buzhou="�Ƿ����߹������ô���!";
			if(null!=part.getOfflineProcess()&&!"".equals(part.getOfflineProcess())){
				tProcessInfo.setOfflineProcess(Integer.parseInt(part.getOfflineProcess()));
			}
			buzhou="�����Ƿ��������ô���!";	
			if(null!=part.getProcessDuration()&&!"".equals(part.getProcessDuration())){
				tProcessInfo.setProcessDuration(Integer.parseInt(part.getProcessDuration()));
			}
			buzhou="ָ���ļ����ô���!";
			if(null!=part.getFile()&&!"".equals(part.getFile())){
				tProcessInfo.setFile(part.getFile());
			}
			buzhou="�������ô���!";
			if(null!=part.getDescription()&&!"".equals(part.getDescription())){
				tProcessInfo.setDescription(part.getDescription());
			}
			buzhou="���շ������ô���!";
			tProcessInfo.setTProcessplanInfo(part.gettProcessplanInfo()); //���ù��շ���
			buzhou="װ��ʱ�����ô���!";
			if(null!=part.getInstallTime()&&!"".equals(part.getInstallTime())){
				tProcessInfo.setInstallTime(Integer.parseInt(part.getInstallTime()));
			}
			buzhou="ж��ʱ�����ô���!";
			if(null!=part.getUninstallTime()&&!"".equals(part.getUninstallTime())){
				tProcessInfo.setUninstallTime(Integer.parseInt(part.getUninstallTime()));
			}
			buzhou="�Ƿ�ؼ����ô���!";
			if(null!=part.getIscheck()&&!"".equals(part.getIscheck())){
				tProcessInfo.setNeedcheck(Integer.parseInt(part.getIscheck()));//�Ƿ�ؼ�
			}
			buzhou="�����������ô���!";
			if(null!=part.getChecktype()&&!"".equals(part.getChecktype())){
				tProcessInfo.setCheckType(Integer.parseInt(part.getChecktype()));//��������
			}
			
			/**
			 **********************************��̨����׼��************************************** 
			 */
			buzhou="û�л�ȡ������";
			tProcessInfo.setProgramId(Long.parseLong(part.getProgramID()));
			
			if(null!=part.getProcessingTime()&&!"".equals(part.getProcessingTime())){
				tProcessInfo.setProcessingTime(Double.parseDouble(part.getProcessingTime()));
			}
			tProcessInfo.setTheoryWorktime(Integer.parseInt(part.getTheoryWorktime()));
			if(null!=part.getCapacity()&&!"".equals(part.getCapacity())){
				tProcessInfo.setCapacity(Double.parseDouble(part.getCapacity()));
			}
			
			buzhou="���湤�򱨴�";
			commonDao.update(tProcessInfo);
			System.out.println(tProcessInfo.getId());
			
			/**
			 **********************************�豸����׼��************************************** 
			 */
			buzhou="�����豸����";
			String delsb="delete from TProcessEquipment where processId='"+tProcessInfo.getId()+"'";
			commonDao.executeUpdate(delsb, parameters); //ɾ���豸
			for(TProcessEquipmentModel tp:teti){
				TProcessEquipment tpe=new TProcessEquipment();
				tpe.setProcessId(tProcessInfo.getId());
				tpe.setEquipmentTypeId(Long.parseLong(tp.getEquipmentTypeId().toString()));
				if(null!=tp.getEquipmentId()&&!"".equals(tp.getEquipmentId())){
					tpe.setEquipmentId(Long.parseLong(tp.getEquipmentId().toString()));
				}
				commonDao.save(tpe);//��ӹ�������豸
			}
			/**
			**********************************�о���Ϣ����׼��2************************************** 
		    */
			buzhou="���¼о߱���";
			String deljj="delete from TProcessFixturetype where processId='"+tProcessInfo.getId()+"'";
			commonDao.executeUpdate(deljj, parameters);//ɾ���о�
			for(TProcessFixturetypeModel tpfm:tftmlist){
				TProcessFixturetype tpf=new TProcessFixturetype();
				tpf.setFixturetypeId(Long.parseLong(tpfm.getFixturetypeId().toString()));
				tpf.setProcessId(tProcessInfo.getId());
				commonDao.save(tpf);//��Ӽо�
			}
			
			
			
			/**
			 **********************************������Ϣ����׼��************************************** 
			 */
			buzhou="�������ϱ���";
			String delwl="delete from TProcessmaterialInfo where TProcessInfo.id='"+tProcessInfo.getId()+"'";
			commonDao.executeUpdate(delwl, parameters);//ɾ������
			 for(MaterialsModel mm:materialList){
				 TProcessmaterialInfo tpinfo=new TProcessmaterialInfo();
				 List<TMaterailTypeInfo>  tmaterail=this.getSelectTMaterailTypeInfoByNo(mm.getWlNo());
				 tpinfo.setTMaterailTypeInfo(tmaterail.get(0));//������������
				 tpinfo.setRequirementType(mm.getWlType());
				 tpinfo.setRequirementNum(Integer.parseInt(mm.getWlNumber()));
				 tpinfo.setTProcessInfo(tProcessInfo);//���ù���
				 commonDao.save(tpinfo);//����������Ϣ
			 }
			/**
			 **********************************��Դ��Ϣ����׼��************************************** 
			 */
			 buzhou="���浶�߱���";
			 String deldj="delete from r_process_cuttertype_info  where processID='"+tProcessInfo.getId()+"'";
			// dao.executeUpdate(deldj);
			 commonDao.executeNativeUpdate(deldj);
			 for(CuttertypeModel mm:cuttertypeList){
				 TProcessCuttertypeInfo tpci=new TProcessCuttertypeInfo(); 
				 tpci.setProcessId(tProcessInfo.getId());//���ù���id
				 tpci.setCuttertypeId(Long.parseLong(mm.getDid()));//���õ���id
				 tpci.setRequirementNum(Integer.parseInt(mm.getRequirementNum()));//��������
				 tpci.setStatus(0);
				 System.out.println("-tProcessInfo.getId()-"+tProcessInfo.getId()+"-mm.getDid()-"+mm.getDid()+"-mm.getRequirementNum()-"+mm.getRequirementNum());
				 //commonDao.save(tc);//���浶����Ϣ
				 commonDao.save(TProcessCuttertypeInfo.class, tpci);
			 }
			/**
			 **********************************�ɱ���Ϣ����׼��************************************** 
			 */
			String delCost="delete from TPartProcessCost where TProcessInfo.id='"+tProcessInfo.getId()+"'";
			 commonDao.executeUpdate(delCost, parameters);
			 
			 TPartProcessCost tpc=new TPartProcessCost();
			 tpc.setTProcessInfo(tProcessInfo);//���ù���
			 
			 if(null!=part.getMainMaterialCost()&&!"".equals(part.getMainMaterialCost())){
				 tpc.setMainMaterialCost(Double.parseDouble(part.getMainMaterialCost()));
			 }
			 if(null!=part.getPeopleCost()&&!"".equals(part.getPeopleCost())){
				 tpc.setPeopleCost(Double.parseDouble(part.getPeopleCost()));
			 }
			 if(null!=part.getSubsidiaryMaterialCost()&&!"".equals(part.getSubsidiaryMaterialCost())){
				 tpc.setSubsidiaryMaterialCost(Double.parseDouble(part.getSubsidiaryMaterialCost()));
			 }
			 if(null!=part.getEnergyCost()&&!"".equals(part.getEnergyCost())){
				 tpc.setEnergyCost(Double.parseDouble(part.getEnergyCost()));
			 }
			 if(null!=part.getDeviceCost()&&!"".equals(part.getDeviceCost())){
				 tpc.setDeviceCost(Double.parseDouble(part.getDeviceCost()));
			 }
			 if(null!=part.getResourceCost()&&!"".equals(part.getResourceCost())){
				 tpc.setResourceCost(Double.parseDouble(part.getResourceCost()));
			 }
			 tpc.setTPartTypeInfo(tPartTypeInfoList.get(0));
			 buzhou="���³ɱ�����";
			 commonDao.save(tpc);//����ɱ���Ϣ
			 /**
				 **********************************�ʼ�����׼��************************************** 
			 */
			 String eqzj="from RProcessQuality where processId='"+tProcessInfo.getId()+"'";
			 List<RProcessQuality> zjlist=commonDao.executeQuery(eqzj, parameters);
			 for(RProcessQuality rpqq:zjlist){
				 String delzj="delete from TQualityParam where id='"+rpqq.getQualityId()+"'";
				 commonDao.executeUpdate(delzj, parameters);//ɾ���ʼ������
				 commonDao.delete(rpqq);//ɾ���ʼ칤�������
			 }
			 for(TQualityParam tqp:tqplist){
				 TQualityParam tt=new TQualityParam();
				if(null!=tqp.getQualityNo())
					tt.setQualityNo(tqp.getQualityNo());
				if(null!=tqp.getQualityName())
					tt.setQualityName(tqp.getQualityName());
				if(null!=tqp.getStandardValue())
					tt.setStandardValue(tqp.getStandardValue());
				if(null!=tqp.getUnit())
					tt.setUnit(tqp.getUnit());
				if(null!=tqp.getMaxValue())
					tt.setMaxValue(tqp.getMaxValue());
				if(null!=tqp.getMinValue())
					tt.setMinValue(tqp.getMinValue());
				if(null!=tqp.getMinTolerance())
					tt.setMinTolerance(tqp.getMinTolerance());
				if(null!=tqp.getMaxTolerance())
					tt.setMaxTolerance(tqp.getMaxTolerance());
				if(null!=tqp.getIsKey())
					tt.setIsKey(tqp.getIsKey());
				if(null!=tqp.getDescription())
					tt.setDescription(tqp.getDescription());
				if(null!=tqp.getCheckTime())
					tt.setCheckTime(tqp.getCheckTime());
				if(null!=tqp.getCheckType())
					tt.setCheckType(tqp.getCheckType());
				 buzhou="�����ʼ챨��";
				 commonDao.save(tt);//����ʼ�����
				 RProcessQuality rpq=new RProcessQuality();
				 rpq.setProcessId(Long.parseLong(tProcessInfo.getId().toString()));
				 rpq.setQualityId(tt.getId());
				 buzhou="�����ʼ칤�򱨴�";
				 commonDao.save(rpq);//�ʼ칤�������
			 }
			 return "����ɹ�";
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return buzhou;
		}
	}

	/**
	 * ���������򵼸���
	 * @param part
	 * @param materialList
	 * @param cuttertypeList
	 */
	public String savePartProcessGuide(PartModel part,List<MaterialsModel> materialList,List<CuttertypeModel> cuttertypeList,
			String partTypeNo,TProcessInfo tt,TPartProcessCost tpc,List<MaterialsModel> deleteWL
			,List<CuttertypeModel> deleteDJ){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String buzhou="";
		try {
			List<TPartTypeInfo> tPartTypeInfoList=this.getTPartTypeInfoByNo(partTypeNo);
			/**
			 **********************************������Ϣ���ݴ���************************************** 
			 */
			String hql="from TProcessInfo where (no='"+part.getNo()+"' or name='"+part.getName()+"') and id<>'"+part.getId()+"' and status=0";
			List<TProcessInfo> rs=commonDao.executeQuery(hql, parameters);
			if(null!=rs&&rs.size()>0){
				return "�����Ѵ���";
			}
			String hqltp="from TProcessInfo where id='"+part.getId()+"'";
			List<TProcessInfo> rstp=commonDao.executeQuery(hqltp, parameters);
			TProcessInfo tProcessInfo=rstp.get(0);//Ҫ����Ĺ������
			tProcessInfo.setStatus(0);
			tProcessInfo.setNo(part.getNo());
			tProcessInfo.setName(part.getName());
			if(null!=part.getOnlineProcessID()&&!"".equals(part.getOnlineProcessID())){
				//tProcessInfo.setOnlineProcessId(Integer.parseInt(part.getOnlineProcessID()));
			}
			if(null!=part.getOfflineProcess()&&!"".equals(part.getOfflineProcess())){
				tProcessInfo.setOfflineProcess(Integer.parseInt(part.getOfflineProcess()));
			}
			if(null!=part.getProcessDuration()&&!"".equals(part.getProcessDuration())){
				tProcessInfo.setProcessDuration(Integer.parseInt(part.getProcessDuration()));
			}
			if(null!=part.getFile()&&!"".equals(part.getFile())){
				tProcessInfo.setFile(part.getFile());
			}
			if(null!=part.getDescription()&&!"".equals(part.getDescription())){
				tProcessInfo.setDescription(part.getDescription());
			}
			buzhou="û�л�ȡ�����շ���";
			tProcessInfo.setTProcessplanInfo(part.gettProcessplanInfo()); //���ù��շ���
			
			/**
			**********************************�о���Ϣ����׼��************************************** 
		    */
			if(null!=part.getInstallTime()&&!"".equals(part.getInstallTime())){
				tProcessInfo.setInstallTime(Integer.parseInt(part.getInstallTime()));
			}
			if(null!=part.getUninstallTime()&&!"".equals(part.getUninstallTime())){
				tProcessInfo.setUninstallTime(Integer.parseInt(part.getUninstallTime()));
			}
			buzhou="û�л�ȡ���о���Ϣ";
			//tProcessInfo.setFixtureId(Long.parseLong(part.getJjId()));
			
			/**
			 **********************************��̨���ݴ���************************************** 
			 */
			buzhou="û�л�ȡ���豸����";
			List<TEquipmenttypeInfo> equtype=this.getTEquipmenttypeInfoById(part.getEquipmentTypeID());
			//tProcessInfo.setTEquipmenttypeInfo(equtype.get(0)); //�����豸����
			buzhou="û�л�ȡ������";
			//tProcessInfo.setProgramId(Integer.parseInt(part.getProgramID()));
			tProcessInfo.setProcessingTime(Double.parseDouble(part.getProcessingTime()));
			tProcessInfo.setTheoryWorktime(Integer.parseInt(part.getTheoryWorktime()));
			tProcessInfo.setCapacity(Double.parseDouble(part.getCapacity()));
			buzhou="���湤�򱨴�";
			commonDao.update(tProcessInfo);//���湤�����
			
			/**
			 **********************************������Ϣ���ݴ���************************************** 
			 */
			 for(MaterialsModel mm:materialList){
				
				 if(null!=mm.getDid()&&!"".equals(mm.getDid())){
					 List<TMaterailTypeInfo>  tmaterail=this.getSelectTMaterailTypeInfoByNo(mm.getWlNo());
					 buzhou="��ȡ��������ʧ��";
					 TProcessmaterialInfo tProcessmaterialInfo=(TProcessmaterialInfo)commonDao.executeQuery("from TProcessmaterialInfo where id='"+mm.getDid()+"'", parameters).get(0);
					 tProcessmaterialInfo.setTMaterailTypeInfo(tmaterail.get(0));//������������
					 tProcessmaterialInfo.setRequirementType(mm.getWlType());
					 tProcessmaterialInfo.setRequirementNum(Integer.parseInt(mm.getWlNumber()));
					 tProcessmaterialInfo.setTProcessInfo(tProcessInfo);//���ù���
					 tProcessmaterialInfo.setId(Long.parseLong(mm.getDid()));
					 buzhou="������������ʧ��";
					 commonDao.update(tProcessmaterialInfo);//����������Ϣ  
				 }else{
					 TProcessmaterialInfo tProcessmaterialInfo=new TProcessmaterialInfo();
					 List<TMaterailTypeInfo>  tmaterail=this.getSelectTMaterailTypeInfoByNo(mm.getWlNo());
					 tProcessmaterialInfo.setTMaterailTypeInfo(tmaterail.get(0));//������������
					 tProcessmaterialInfo.setRequirementType(mm.getWlType());
					 tProcessmaterialInfo.setRequirementNum(Integer.parseInt(mm.getWlNumber()));
					 tProcessmaterialInfo.setTProcessInfo(tProcessInfo);//���ù���
					 buzhou="������������ʧ��";
					 commonDao.save(tProcessmaterialInfo);//����������Ϣ
				 }
				
			 }
			 for(MaterialsModel mm:deleteWL){
				 List<TMaterailTypeInfo>  tmaterail=this.getSelectTMaterailTypeInfoByNo(mm.getWlNo());
				 TProcessmaterialInfo tProcessmaterialInfo=(TProcessmaterialInfo)commonDao.executeQuery("from TProcessmaterialInfo where id='"+mm.getDid()+"'", parameters).get(0);
				 tProcessmaterialInfo.setStatus(1);
				 buzhou="ɾ����������ʧ��";
				 commonDao.update(tProcessmaterialInfo); //ɾ������
			 }
			
			/**
			 **********************************��Դ��Ϣ���ݴ���************************************** 
			 */
			 for(CuttertypeModel mm:cuttertypeList){
				
				 if(null!=mm.getDid()&&!"".equals(mm.getDid())&&!"-1".equals(mm.getDid())){
					 buzhou="��ȡ���߱���";
					 TProcessCuttertypeInfo tc=(TProcessCuttertypeInfo)commonDao.executeQuery("from TProcessCuttertypeInfo where id="+mm.getDid(), parameters).get(0); 
					 //tc.setProcessno(tProcessInfo.getNo()); //���ù�����
					// tc.setCuttertypeNo(mm.getNo()); //���õ������ͱ��
					 tc.setRequirementNum(Integer.parseInt(mm.getRequirementNum()));//��������
					 tc.setStatus(0);
					 buzhou="���µ��߱���";
					 commonDao.update(tc);
				 }else{
					 TProcessCuttertypeInfo tc=new TProcessCuttertypeInfo(); 
					 //tc.setProcessno(tProcessInfo.getNo()); //���ù�����
					// tc.setCuttertypeNo(mm.getNo()); //���õ������ͱ��
					 tc.setRequirementNum(Integer.parseInt(mm.getRequirementNum()));//��������
					 buzhou="�½����߱���";
					 commonDao.save(tc);//���浶����Ϣ 
				 }
				
			 }
			 for(CuttertypeModel mm:deleteDJ){
				 TProcessCuttertypeInfo tc=(TProcessCuttertypeInfo)commonDao.executeQuery("from TProcessCuttertypeInfo where id="+mm.getDid(), parameters).get(0); 
				 //tc.setProcessno(tProcessInfo.getNo()); //���ù�����
				 //tc.setCuttertypeNo(mm.getName()); //���õ������ͱ��
				 tc.setRequirementNum(Integer.parseInt(mm.getRequirementNum()));//��������
				 //tc.setId(Integer.parseInt(mm.getDid()));
				 tc.setStatus(1);
				 buzhou="ɾ�����߱���";
				 commonDao.update(tc);
			 }
			 
			/**
			 **********************************�ɱ���Ϣ���ݴ���************************************** 
			 */
			 tpc.setTProcessInfo(tProcessInfo);//���ù���
			 
			 if(null!=part.getMainMaterialCost()&&!"".equals(part.getMainMaterialCost())){
				 tpc.setMainMaterialCost(Double.parseDouble(part.getMainMaterialCost()));
			 }
			 if(null!=part.getPeopleCost()&&!"".equals(part.getPeopleCost())){
				 tpc.setPeopleCost(Double.parseDouble(part.getPeopleCost()));
			 }
			 if(null!=part.getSubsidiaryMaterialCost()&&!"".equals(part.getSubsidiaryMaterialCost())){
				 tpc.setSubsidiaryMaterialCost(Double.parseDouble(part.getSubsidiaryMaterialCost()));
			 }
			 if(null!=part.getEnergyCost()&&!"".equals(part.getEnergyCost())){
				 tpc.setEnergyCost(Double.parseDouble(part.getEnergyCost()));
			 }
			 if(null!=part.getDeviceCost()&&!"".equals(part.getDeviceCost())){
				 tpc.setDeviceCost(Double.parseDouble(part.getDeviceCost()));
			 }
			 if(null!=part.getResourceCost()&&!"".equals(part.getResourceCost())){
				 tpc.setResourceCost(Double.parseDouble(part.getResourceCost()));
			 }
			 tpc.setTPartTypeInfo(tPartTypeInfoList.get(0));
			 buzhou="���³ɱ�����";
			 commonDao.update(tpc);//����ɱ���Ϣ
			 
			 return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return buzhou;
		}
	}
	
	/**
	 * ���湤�շ���
	 * @param plan
	 * @return
	 */
	public String saveTProcessplanInfo(TProcessplanInfo plan){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<TProcessplanInfo> rs=commonDao.executeQuery("from TProcessplanInfo where name='"+plan.getName()+"' and status=0", parameters);
		if(null!=rs&&rs.size()>0){
			return "0";
		}else{
			try {
				commonDao.save(plan);
				return "1";
			} catch (Exception e) {
				e.printStackTrace();
				return "2";
			}
		}
		
	}
	/**
	 * ���¹��շ���
	 * @param plan
	 * @return
	 */
	public String updateTProcessplanInfo(TProcessplanInfo plan){
		try {
			commonDao.update(plan);
			return "���³ɹ�";
		} catch (Exception e) {
			e.printStackTrace();
			return "����ʧ��";
		}
		
	}
	/**
	 * ���շ�����ΪĬ��
	 * @param plan
	 * @return
	 */
	public String updateDeDefaultTProcessplanInfo(TProcessplanInfo plan){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TProcessplanInfo where TPartTypeInfo.id='"+plan.getTPartTypeInfo().getId()+"' " +
				  " and defaultSelected=1 and id<>'"+plan.getId()+"'";
		List<TProcessplanInfo> rs=commonDao.executeQuery(hql, parameters);
		TProcessplanInfo p=new TProcessplanInfo();
		
		String hql2="from TProcessplanInfo where id='"+plan.getId()+"' ";
		List<TProcessplanInfo> rs2=commonDao.executeQuery(hql2, parameters);
		TProcessplanInfo pp=rs2.get(0);
		pp.setDefaultSelected(1);
		
		if(null!=rs&&rs.size()>0){
			p=rs.get(0);
			p.setDefaultSelected(0);
			try {
				commonDao.update(p);
				commonDao.update(pp);
				return "���óɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "����ʧ��";
			}
		}else{
			try {
				commonDao.update(pp);
				return "���óɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "����ʧ��";
			}
		}
		
		
		
	}
	
	/**
	 * ���ݹ���id���ع������
	 * @param Id
	 * @return
	 */
	public TProcessInfo getTProcessInfoById(String Id){
		String hql="from TProcessInfo a where a.id='"+Id+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return (TProcessInfo)commonDao.executeQuery(hql, parameters).get(0);
	}

	/**
	 * ���ݹ���id��ѯ������Ϣ
	 * @param no
	 * @return
	 */
	public List<Map<String,Object>> getTProcessCuttertypeInfo(String processId,String nodeid){
		String hql="select new Map(" +
				  " b.name as name," +
				  " b.no as no," +
				  " a.requirementNum as requirementNum," +
				  " a.id as id,"
				  + "b.id as bid)" +
				  " from TProcessCuttertypeInfo a,TCuttertypeInfo b"
				+ " where a.cuttertypeId=b.id "
				+ " and b.nodeId='"+nodeid+"'"
				+ " and a.processId='"+processId+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * ���ݹ���id��ѯ�о���Ϣ
	 * @param id
	 * @return
	 */
	public List<TPartProcessCost> getTPartProcessCost(String id){
		String hql="from TPartProcessCost a" +
				   " where a.TProcessInfo.id ='"+id+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * ��ѯ�豸�ڵ�
	 * @param search
	 * @return
	 */
	public TreeNode getTreeNodeEquInfo(String nodeid){
		TreeNode root =new DefaultTreeNode("��",null);
		TreeNode root2 =new DefaultTreeNode("��",null);
		root2.setExpanded(true);
		Collection<Parameter> parameters = new HashSet<Parameter>();
			String hql="from TEquipmenttypeInfo where id='-999'";
			List<TEquipmenttypeInfo> tmclist=commonDao.executeQuery(hql, parameters);
			TEquipmenttypeInfo tmc;
			if(null!=tmclist&&tmclist.size()>0){
				 tmc=(TEquipmenttypeInfo)tmclist.get(0);
			}else{
				tmc =new TEquipmenttypeInfo();
			}
			root=newNodeChildren(tmc, root,nodeid);
			
		
		return root;
	}
	
	/**
	 * �ݹ鷵���������ڵ�
	 * @param tmc
	 * @param parent
	 * @return
	 */
	public TreeNode newNodeChildren(TEquipmenttypeInfo tmc,TreeNode parent,String nodeid) {
		TreeNode newNode = new DefaultTreeNode(tmc, parent);
		newNode.setExpanded(true);
		for (TEquipmenttypeInfo tt:tmc.getTEquipmenttypeInfos()) {
			if(nodeid.equals(tt.getNodeid())){
				if(null!=tt.getIsdel()&&!tt.getIsdel().equals("1")){
					newNodeChildren(tt, newNode,nodeid);
				}
			}
		}
		return newNode;
	}
	/**
	 * �����豸����id �����豸
	 * @param list
	 * @return
	 */
	public List<TEquipmentInfo> getTEquipmentInfo(List list){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String tt=StringUtils.returnHqlIN(list);
		String hql="from TEquipmentInfo where equTypeId in("+tt+")";
		return commonDao.executeQuery(hql, parameters);
	}
	/**
	 * �����豸����id���ҵ����豸
	 * @param id
	 * @return
	 */
	public List<TEquipmentInfo> getTEquipmentInfoByEquSerialNo(String equSerialNo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TEquipmentInfo where equSerialNo='"+equSerialNo+"'";
		return commonDao.executeQuery(hql, parameters);
	}
	/**
	 * ���ݹ���id���ҹ����豸��
	 * @return
	 */
	public List<TProcessEquipmentModel> getTProcessEquipmentByProcessId(String processId){
		List<TProcessEquipmentModel> tpemlist=new ArrayList<TProcessEquipmentModel>();
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql="select c.equipmentTypeId as equipmentTypeId,"
				+ " c.equipmentTypeName as equipmentTypeName, "
				+ " c.processID as processID,"
				+ " c.equipmentID as equipmentID,"
				+ " d.equ_name as equipmentName "
				+ " from "
				+ " (select b.id as equipmentTypeId, "
				+ " b.equipmentType as equipmentTypeName, "
				+ " a.processID as processId,"
				+ " a.equipmentID as equipmentId "
				+ " from r_process_equipment a ,t_equipmenttype_info b "
				+ " where a.equipmentTypeID=b.id) c left join T_EquipmentAddInfo d"
				+ " on c.equipmentId=d.equ_Id "
				+ " where c.processId='"+processId+"'";
		List<Map<String,Object>> rs=commonDao.executeNativeQuery(sql, parameters);
		if(null!=rs&&rs.size()>0){
			int i=0;
			for(Map<String,Object> mm:rs){
				TProcessEquipmentModel tpem=new TProcessEquipmentModel();
				tpem.setId(i+"");
				tpem.setEquipmentId((null==mm.get("equipmentID")?"":mm.get("equipmentID").toString()));
				tpem.setEquipmentName((null==mm.get("equipmentName")?"":mm.get("equipmentName").toString()));
				tpem.setEquipmentTypeId(mm.get("equipmentTypeId").toString());
				tpem.setEquipmentTypeName(mm.get("equipmentTypeName").toString());
				tpemlist.add(tpem);
			}
		}
		return tpemlist;
	}
  /**
   * ���ݹ���id��ȡ�о�����
   * @return
   */
  public List<TProcessFixturetypeModel> getTProcessFixturetypeModelByProcessId(String processid){
	  Collection<Parameter> parameters = new HashSet<Parameter>();
	  List<TProcessFixturetypeModel> tpfmlist=new ArrayList<TProcessFixturetypeModel>();
	  String hql="select new Map(a.fixturetypeId as fixturetypeId,"
	  		+ " b.fixturesNo as fixtureNO,"
	  		+ " b.fixturesName as fixtureName)"
	  		+ " from TProcessFixturetype a,TFixtureTypeInfo b "
	  		+ " where a.fixturetypeId=b.id and a.processId='"+processid+"'";
	  List<Map<String,Object>> rs=commonDao.executeQuery(hql, parameters);
	  if(null!=rs&&rs.size()>0){
		  int i=0;
		  for(Map<String,Object> tpf:rs){
			  TProcessFixturetypeModel tpfm=new TProcessFixturetypeModel();
			  tpfm.setId(Long.parseLong(i+""));
			  tpfm.setFixturetypeId(Long.parseLong(tpf.get("fixturetypeId").toString()));
			  tpfm.setFixtureNO(tpf.get("fixtureNO").toString());
			  tpfm.setFixtureName(tpf.get("fixtureName").toString());
			  tpfmlist.add(tpfm);
			  i++;
		  }
	  }
	  return tpfmlist;
  }
  /**
   * ���ݹ����ȡ�ʼ�����
   * @param processid
   * @return
   */
  public List<TQualityParam> getTQualityParamByProcessId(String processid){
	  Collection<Parameter> parameters = new HashSet<Parameter>();
	  List<TQualityParam> tqplist=new ArrayList<TQualityParam>();
	  String hql="from RProcessQuality where processId='"+processid+"'";
	  List<RProcessQuality> rs=commonDao.executeQuery(hql, parameters);
	  if(null!=rs&&rs.size()>0){
		  List list=new ArrayList();
		  for(RProcessQuality rpq:rs){
			  list.add(rpq.getQualityId());
		  }
		  String param=StringUtils.returnHqlIN(list);
		  String hql2="from TQualityParam where id in("+param+")";
		  tqplist=commonDao.executeQuery(hql2, parameters);
	  }
	  return tqplist;
  }
  /**
   * ���ݹ��շ�����ȡ�ʼ�����
   * @param ProcessPlanId
   * @return
   */
  public List<Map<String,Object>> getTQualityParamByProcessPlanId(String ProcessPlanId){
	  Collection<Parameter> parameters = new HashSet<Parameter>();
	  String sql=" SELECT b.no as no,"
	  		    + " d.qualityNo as qualityNo,"
	  		    + " d.qualityName as qualityName,"
	  		    + " d.standardValue as standardValue,"
	  		    + " d.unit as unit,"
	  		    + " d.max_Value as maxValue,"
	  		    + " d.minValue as minValue,"
	  		    + " d.min_tolerance as mintolerance,"
	  		    + " d.max_tolerance as maxtolerance,"
	  		    + " d.isKey as isKey,"
	  		    + " d.description as description,"
	  		    + " d.checkTime as checkTime,"
	  		    + " d.checkType as checkType "
	  		   + " FROM t_processplan_info a,t_process_info b,r_process_quality c,t_quality_param d "
	  		   + " WHERE a.ID=b.processPlanID AND b.ID=c.processID AND c.qualityID=d.ID "
	  		   + "  AND a.id='"+ProcessPlanId+"'";
	  return commonDao.executeNativeQuery(sql, parameters);
  }
  
  /**
   * ���Ӳ�ѯ������ģ����ѯ�������
   */
  public List<TPartTypeInfo> getAllPartType(String nodeid, String partName){
	  String hql = "from TPartTypeInfo t where t.nodeid = '"+nodeid+"' and t.name like '%"+partName+"%' and t.status<>'ɾ��' ORDER BY t.name ASC";
	  return dao.executeQuery(hql);
  }
  
  /**
   * ����������Ʋ�ѯ�����Ϣ
   * @return
   */
  public List<TPartTypeInfo> getPartTypeInfo(String partName){
	  String hql = "from TPartTypeInfo t where t.name = '"+partName+"'";
	  return dao.executeQuery(hql);
  }
  
  /**
   * ���ݹ���id ����������Ϣ
   * @param processId
   * @return
   */
  public List<Map<String,Object>> getProcessWL(String processId){
	  String hql="select new Map("
	  		  + " a.TMaterailTypeInfo.no as No,"
	  		  + " a.TMaterailTypeInfo.name as Name,"
	  		  + " a.requirementNum as RequirementNum,"
	  		  + " a.TMaterailTypeInfo.price as Price)"
	  		  + " from TProcessmaterialInfo a"
	  		  + " where a.TProcessInfo.id='"+processId+"' order by a.requirementType,a.TMaterailTypeInfo.no";
	  return dao.executeQuery(hql);
  }
  /**
   * ��ȡ�����̨
   * @param processId
   * @return
   */
  public List<Map<String,Object>> getProcessJTData(String processId){
		String hql="select new Map(" +
				  " d.erpResouceCode as typecode," +
				  " a.processingTime as processingTime,"
				 +" a.theoryWorktime as theoryWorktime)" +
				  " from TProcessInfo a  ,TProcessEquipment c,TEquipmenttypeInfo d where "
				+ "  a.id=c.processId and d.id=c.equipmentTypeId" +
				  " and a.id='"+processId+"' and a.status=0";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}

	@Override
	public List<Map<String, Object>> getJopPlanByPartId(String partId) {
		String hql="select new Map("
				+ " no as no,"
				+ " name as name,"
				+ " id as id)"
				+ " from TJobplanInfo "
				+ " where TPartTypeInfo.id="+partId+""
				+ " and planType=2";
		return commonDao.executeQuery(hql);
	}
  
 }

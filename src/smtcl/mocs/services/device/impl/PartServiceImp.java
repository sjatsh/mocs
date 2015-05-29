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
 * 零件Service
 * @创建时间 2013-07-09
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0  
 */
public class PartServiceImp extends GenericServiceSpringImpl<TNodes, String> implements IPartService{
	/**
	 * 数据service
	 */
	
	
	private  ICommonDao commonDao;
	
	
	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	/**
	 * 根据条件查询TPartTypeInfo对象如果条件为空，则查询全部对象
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
				+ " from TPartTypeInfo a where  a.status<>'删除'"
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
	 * 更新TPartTypeInfo
	 * @param tPartTypeInfo
	 */
	@SuppressWarnings("unchecked")
	public String saveTPartTypeInfo(TPartTypeInfo tPartTypeInfo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		Date date=new Date();
		String hql="from TPartTypeInfo where (no='"+tPartTypeInfo.getNo()+"' or name='"+tPartTypeInfo.getName()+"') and id<>"+tPartTypeInfo.getId();
		List<TPartTypeInfo> rs=commonDao.executeQuery(hql, parameters);
		if(rs!=null&&rs.size()>0){
			return "已存在";
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
				return "更新成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "更新失败";
			}
		}
		
	}
	
	/**
	 * 删除TPartTypeInfo
	 * @param tPartTypeInfo
	 */
	public void deleteTPartTypeInfo(TPartTypeInfo tPartTypeInfo){
		TPartTypeInfo tt=(TPartTypeInfo)this.getTPartTypeInfoByNo(tPartTypeInfo.getNo()).get(0);
		if(null!=tPartTypeInfo.getStatus()&&!"删除".equals(tPartTypeInfo.getStatus())){
			tt.setOriginalStatus(tPartTypeInfo.getStatus());
		}
		tt.setStatus("删除");
		try {
			commonDao.update(tt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 添加 TPartTypeInfo
	 * @param tPartTypeInfo
	 */
	public String addTPartTypeInfo(TPartTypeInfo tPartTypeInfo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		Date date=new Date();
		String hql="from TPartTypeInfo where (no='"+tPartTypeInfo.getNo()+"' or name='"+tPartTypeInfo.getName()+"') "
				+ " and nodeid='"+tPartTypeInfo.getNodeid()+"'";
		List<TPartTypeInfo> rs=commonDao.executeQuery(hql, parameters);
		if(null!=rs&&rs.size()>0){
			return "已存在";
		}else{
			tPartTypeInfo.setCreateDate(date);
			tPartTypeInfo.setUpdateDate(date);
			tPartTypeInfo.setStatus("新建");
			try {
				commonDao.save(tPartTypeInfo);
				return "添加成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "添加失败";
			}
		}
		
		
		
	}
	/**
	 * 根据零件编号查询零件信息
	 * @param tPartTypeInfo
	 */
	public List<TPartTypeInfo> getTPartTypeInfoByNo(String no){
	   Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TPartTypeInfo where no='"+no+"'";
		return commonDao.executeQuery(hql, parameters);
		
	}
	/**
	 * 根据零件类别编号查询零件类别
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
	 * 停用，启用方法
	 * @param tPartTypeInfo
	 * @param status
	 */
	public void stopOrStartTPartTypeInfo(TPartTypeInfo tPartTypeInfo,String status){
		try {
			/*if(status.equals("停用")){
				if(tPartTypeInfo.getStatus().equals("新建")||tPartTypeInfo.getStatus().equals("投产")){
					tPartTypeInfo.setOriginalStatus(tPartTypeInfo.getStatus());
					tPartTypeInfo.setStatus(status);
				}
				
			}else{
				if(tPartTypeInfo.getStatus().equals("停用")){
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
	 * 从TPartClassInfo表中获取所有的typeNo
	 * @return
	 */
	public List<String> getTypeNo(String nodeid){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select distinct no  from TPartClassInfo where status=0 and nodeid='"+nodeid+"'";
		return commonDao.executeQuery(hql, parameters);
	}
	
	
	
	/**
	 * 根据条件查询TPartClassInfo对象如果条件为空，则查询全部对象
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
	 * 更新TPartClassInfo
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
			return "已存在";
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
				return "更新成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "更新失败";
			}
		}
		
	}
	
	/**
	 * 删除TPartClassInfo
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
	 * 添加 TPartClassInfo
	 * @param tPartClassInfo
	 */
	public String addTPartClassInfo(TPartClassInfo tPartClassInfo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TPartClassInfo where (no='"+tPartClassInfo.getNo()+"' or name='"+tPartClassInfo.getName()+"') "
				+ " and nodeid='"+tPartClassInfo.getNodeid()+"' and status=0";
		List<TPartClassInfo> rs = commonDao.executeQuery(hql, parameters);
		if(null!=rs&&rs.size()>0){
			return "已存在";
		}else{
			try {
				tPartClassInfo.setStatus(0);
				commonDao.save(tPartClassInfo);
				return "添加成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "添加失败";
			}
		}
		
		
	}
	
	/**
	 * 获取零件类型列表树
	 * @return
	 */
	public List<Map<String,Object>> getPartTree(String query,String nodeid){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select new Map(" +
				  " name as name," +
				  " no as no," +
				  " drawingno as drawingno) " +
				  " from TPartTypeInfo where status not in('停用','删除') "
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
	 * 根据零件编号获取工艺方案
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
	 * 获取工序
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
	 * 根据前序编号查找工序
	 * @param onlineProcessID
	 * @return
	 */
	public List<TProcessInfo> getTProcessInfoByOnlineProcessID(String onlineProcessID){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TProcessInfo where id='"+onlineProcessID+"'";
		return commonDao.executeQuery(hql, parameters);
	}
	/**
	 * 更新工序
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
	 * 新增工序
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
	 * 删除工序
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
	 * 获取下拉框中的程序列表
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
	 * 获取下拉框中的程序列表
	 * @return
	 */
	public List<TUserEquNcprogs> getSelectTUserEquNcprogsById(String id){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TUserEquNcprogs where id='"+id+"'";
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * 获取下拉框中工艺方案列表
	 * @return
	 */
	public List<Map<String,Object>> getSelectTProcessplanInfo(){
		String hql="select new Map(id as id,name as name) from TProcessplanInfo group by id ";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String,Object>> rs=commonDao.executeQuery(hql, parameters);
		return rs;
	}
	
	/**
	 * 获取下拉框中夹具信息列表
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
	 * 根据夹具编号查询夹具信息
	 * @param no
	 * @return
	 */
	public List<TFixtureTypeInfo> getTFixtureTypeInfoById(String Id){
		String hql="from TFixtureTypeInfo where id='"+Id+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * 获取下拉框中工作站信息
	 * @return
	 */
	public List<Map<String,Object>> getSelectTWorkstationInfo(){
		String hql="select new Map(id as id)from TWorkstationInfo group by id ";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String,Object>> rs=commonDao.executeQuery(hql, parameters);
		return rs;
	}
	
	/**
	 * 获取下拉框中机床类型列表
	 * @return
	 */
	public List<Map<String,Object>> getSelectTEquipmenttypeInfo(){
		String hql="select new Map(id as id,equipmentType as equipmentType)from TEquipmenttypeInfo group by id ";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String,Object>> rs=commonDao.executeQuery(hql, parameters);
		return rs;
	}
	/**
	 * 返回成本数据
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
	 * 返回物料数据
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
	 * 返回夹具数据
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
	 * 返回刀具类型数据
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
	 * 返回机台数据
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
	 * 根据工艺方案id获取工艺方案对象
	 * @param id
	 * @return
	 */
	public List<TProcessplanInfo> getTProcessplanInfoById(String id){
		String hql="from TProcessplanInfo where id='"+id+"' and status=0";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	
	/**
	 * 获取设备类型
	 * @return
	 */
	public List<TEquipmenttypeInfo> getTEquipmenttypeInfo(){
		String hql="from TEquipmenttypeInfo";
		
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * 获取设备类型
	 * @return
	 */
	public List<TEquipmenttypeInfo> getTEquipmenttypeInfoById(String id){
		String hql="from TEquipmenttypeInfo where id='"+id+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	/**
	 * 根据工序id查询 物料信息
	 * @param no
	 * @return
	 */
	public List<TProcessmaterialInfo> getTProcessmaterialInfo(String id){
		String hql="from TProcessmaterialInfo a where a.TProcessInfo.id='"+id+"'";
		return commonDao.executeQuery(hql);
	}
	/**
	 * 获取物料类型下拉框数据
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
	 * 根据物料类型编号获取物料类型
	 * @param no
	 * @return
	 */
	public List<TMaterailTypeInfo> getSelectTMaterailTypeInfoByNo(String no){
		String hql= " from TMaterailTypeInfo a where a.no='"+no+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * 根据物料编号获取物料
	 * @return
	 */
	public List<TMaterailTypeInfo> getTMaterailTypeInfo(String no){
		String hql="from TMaterailTypeInfo a where a.no='"+no+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * 获取刀具下拉框数据
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
	 * 根据刀具编号获取刀具信息
	 * @param no
	 * @return
	 */
	public List<TCuttertypeInfo> getTCuttertypeInfo(String no){
		String hql="from TCuttertypeInfo a where a.no='"+no+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return commonDao.executeQuery(hql, parameters);
	}
	
	/**
	 * 工序配置向导保存
	 * @param part 向导辅助模型
	 * @param materialList 物料数据
	 * @param cuttertypeList 刀具数据
	 * @param partTypeno 零件编号
	 * @param tqplist 质检数据
	 * @param teti 设备数据
	 * @param tftmlist 夹具数据
	 */
	public String savePartProcessGuide(PartModel part,List<MaterialsModel> materialList,List<CuttertypeModel> cuttertypeList,String partTypeNo,
			List<TQualityParam> tqplist,List<TProcessEquipmentModel> teti,List<TProcessFixturetypeModel> tftmlist){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String buzhou="";
		try {
			List<TPartTypeInfo> tPartTypeInfoList=this.getTPartTypeInfoByNo(partTypeNo);
			/**
			 **********************************基本信息数据准备************************************** 
			 */
			String hql="from TProcessInfo where no='"+part.getNo()+"' and status=0 "
					+ " and nodeid='"+part.getNodeid()+"'";
			List<TProcessInfo> rs=commonDao.executeQuery(hql, parameters);
			if(null!=rs&&rs.size()>0){
				return "已存在";
			}
			TProcessInfo tProcessInfo=new TProcessInfo();//要保存的工序对象
			tProcessInfo.setStatus(0);
			buzhou="工序编号设置错误!";
			tProcessInfo.setNo(part.getNo());//设置工序编号
			buzhou="工序名称设置错误!";
			tProcessInfo.setName(part.getName());//设置工序名称
			buzhou="工序类型设置错误!";
			tProcessInfo.setProcessType(Integer.parseInt(part.getProcessType()));//设置工序类型
			buzhou="工序节点id设置错误!";
			tProcessInfo.setNodeid(part.getNodeid());
			buzhou="前序工序设置错误!";
			if(null!=part.getOnlineProcessID()&&!"".equals(part.getOnlineProcessID())){//设置前序工序
				tProcessInfo.setOnlineProcessId(Long.parseLong(part.getOnlineProcessID()));
			}
			buzhou="工序序号设置错误!";
			if(null!=part.getProcessOrder()&&!"".equals(part.getProcessOrder())){
				tProcessInfo.setProcessOrder(Integer.parseInt(part.getProcessOrder()));//设置工序序号
			}
			buzhou="是否下线工序设置错误!";
			if(null!=part.getOfflineProcess()&&!"".equals(part.getOfflineProcess())){
				tProcessInfo.setOfflineProcess(Integer.parseInt(part.getOfflineProcess()));
			}
			buzhou="工序是否连续设置错误!";
			if(null!=part.getProcessDuration()&&!"".equals(part.getProcessDuration())){
				tProcessInfo.setProcessDuration(Integer.parseInt(part.getProcessDuration()));
			}
			buzhou="指导文件设置错误!";
			if(null!=part.getFile()&&!"".equals(part.getFile())){
				tProcessInfo.setFile(part.getFile());
			}
			buzhou="描述设置错误!";
			if(null!=part.getDescription()&&!"".equals(part.getDescription())){
				tProcessInfo.setDescription(part.getDescription());
			}
			buzhou="装夹时间设置错误!";
			tProcessInfo.setTProcessplanInfo(part.gettProcessplanInfo()); //设置工艺方案
			if(null!=part.getInstallTime()&&!"".equals(part.getInstallTime())){
				tProcessInfo.setInstallTime(Integer.parseInt(part.getInstallTime()));
			}
			buzhou="卸载时间设置错误!";
			if(null!=part.getUninstallTime()&&!"".equals(part.getUninstallTime())){
				tProcessInfo.setUninstallTime(Integer.parseInt(part.getUninstallTime()));
			}
			buzhou="是否必检设置错误!";
			if(null!=part.getIscheck()&&!"".equals(part.getIscheck())){
				tProcessInfo.setNeedcheck(Integer.parseInt(part.getIscheck()));//是否必检
			}
			buzhou="检验类型设置错误!";
			if(null!=part.getChecktype()&&!"".equals(part.getChecktype())){
				tProcessInfo.setCheckType(Integer.parseInt(part.getChecktype()));//检验类型
			}
			/**
			 **********************************机台数据准备************************************** 
			 */
			buzhou="程序设置错误!";
			if(null!=part.getProgramID()&&!"请选择".equals(part.getProgramID())){
				tProcessInfo.setProgramId(Long.parseLong(part.getProgramID()));;//程序
			}
			buzhou="加工时间设置错误!";
			if(null!=part.getProcessingTime()&&!"".equals(part.getProcessingTime())){
				tProcessInfo.setProcessingTime(Double.parseDouble(part.getProcessingTime()));
			}
			buzhou="节拍时间设置错误!";
			if(null!=part.getTheoryWorktime()&&!"".equals(part.getTheoryWorktime())){
				tProcessInfo.setTheoryWorktime(Integer.parseInt(part.getTheoryWorktime()));//节拍时间
			}
			buzhou="预计产能	设置错误!";
			if(null!=part.getCapacity()&&!"".equals(part.getCapacity())){
				tProcessInfo.setCapacity(Double.parseDouble(part.getCapacity()));
			}
		
			buzhou="保存工序报错";
			commonDao.save(tProcessInfo);
			System.out.println(tProcessInfo.getId());
			/**
			 **********************************设备数据准备************************************** 
			 */
			buzhou="保存设备报错";
			for(TProcessEquipmentModel tp:teti){
				TProcessEquipment tpe=new TProcessEquipment();
				tpe.setProcessId(tProcessInfo.getId());
				tpe.setEquipmentTypeId(Long.parseLong(tp.getEquipmentTypeId().toString()));
				if(null!=tp.getEquipmentId()){
					tpe.setEquipmentId(Long.parseLong(tp.getEquipmentId().toString()));
				}
				commonDao.save(tpe);//添加工序关联设备
			}
			/**
			 **********************************物料信息数据准备************************************** 
			 */
			buzhou="保存物料报错";
			for(MaterialsModel mm:materialList){
				 String mphql=" from TProcessmaterialInfo where TProcessInfo.id="+tProcessInfo.getId()+" "
				 		    + " and TMaterailTypeInfo.no='"+mm.getWlNo()+"'";
				 List list=commonDao.executeQuery(mphql);
				 if(null!=list&&list.size()>0){
					 return "物料重复";
				 }else{
					 TProcessmaterialInfo tpinfo=new TProcessmaterialInfo(); 
					 List<TMaterailTypeInfo>  tmaterail=this.getSelectTMaterailTypeInfoByNo(mm.getWlNo());
					 tpinfo.setTMaterailTypeInfo(tmaterail.get(0));//设置物料类型
					 tpinfo.setRequirementType(mm.getWlType());
					 tpinfo.setRequirementNum(Integer.parseInt(mm.getWlNumber()));
					 tpinfo.setTProcessInfo(tProcessInfo);//设置工序
					 commonDao.save(tpinfo);//保存物料信息
				 }
				 
			 }
			
			/**
			 **********************************资源信息数据准备************************************** 
			 */
			buzhou="保存夹具报错";
			for(TProcessFixturetypeModel tpfm:tftmlist){
				TProcessFixturetype tpf=new TProcessFixturetype();
				tpf.setFixturetypeId(Long.parseLong(tpfm.getFixturetypeId().toString()));
				tpf.setProcessId(tProcessInfo.getId());
				commonDao.save(tpf);
			}
			 buzhou="保存刀具报错";
			for(CuttertypeModel mm:cuttertypeList){
				 TProcessCuttertypeInfo tc=new TProcessCuttertypeInfo(); 
				 tc.setProcessId(tProcessInfo.getId());//设置工序id
				 tc.setCuttertypeId(Long.parseLong(mm.getDid()));//设置刀具id
				 tc.setRequirementNum(Integer.parseInt(mm.getRequirementNum()));//设置数量
				 tc.setStatus(0);
				 commonDao.save(tc);//保存刀具信息
			 }
			/**
			 **********************************成本信息数据准备************************************** 
			 */
			 TPartProcessCost tPartProcessCost=new TPartProcessCost();
			 tPartProcessCost.setTProcessInfo(tProcessInfo);//设置工序
			 buzhou="成本设置错误!";
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
			 buzhou="保存成本信息报错";
			 commonDao.save(tPartProcessCost);//保存成本信息
			 /**
				 **********************************质检数据准备************************************** 
			 */
			 buzhou="质检设置错误!";
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
				buzhou="保存质检报错";
				 commonDao.save(tt);
				 RProcessQuality rpq=new RProcessQuality();
				 rpq.setProcessId(Long.parseLong(tProcessInfo.getId().toString()));
				 rpq.setQualityId(tt.getId());
				 buzhou="保存工序质检报错";
				 commonDao.save(rpq);
			 }
			 return "保存成功";
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return buzhou;
		}
	}
	/**
	 * 工序向导更新(最新版)
	 */
	public String savePartProcessGuideTest(PartModel part,List<MaterialsModel> materialList,List<CuttertypeModel> cuttertypeList,String partTypeNo,
			List<TQualityParam> tqplist,List<TProcessEquipmentModel> teti,List<TProcessFixturetypeModel> tftmlist){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String buzhou="";
		try {
			List<TPartTypeInfo> tPartTypeInfoList=this.getTPartTypeInfoByNo(partTypeNo);
			/**
			 **********************************基本信息数据准备************************************** 
			 */
			String hql="from TProcessInfo where (no='"+part.getNo()+"') and id<>'"+part.getId()+"' "
					+ " and status=0 and nodeid='"+part.getNodeid()+"'";
			List<TProcessInfo> rs=commonDao.executeQuery(hql, parameters);
			if(null!=rs&&rs.size()>0){
				return "已存在";
			}
			String hqltp="from TProcessInfo where id='"+part.getId()+"'";
			List<TProcessInfo> rstp=commonDao.executeQuery(hqltp, parameters);
			TProcessInfo tProcessInfo=rstp.get(0);//要保存的工序对象
			tProcessInfo.setStatus(0);
			buzhou="工序编号设置错误!";
			tProcessInfo.setNo(part.getNo());
			buzhou="工序名称设置错误!";
			tProcessInfo.setName(part.getName());
			buzhou="工序类型设置错误!";
			tProcessInfo.setProcessType(Integer.parseInt(part.getProcessType()));//设置工序类型
			buzhou="前序工序设置错误!";
			if(null!=part.getOnlineProcessID()&&!"".equals(part.getOnlineProcessID())){
				tProcessInfo.setOnlineProcessId(Long.parseLong(part.getOnlineProcessID()));
			}
			buzhou="工序序号设置错误!";
			if(null!=part.getProcessOrder()&&!"".equals(part.getProcessOrder())){
				tProcessInfo.setProcessOrder(Integer.parseInt(part.getProcessOrder()));//设置工序序号
			}
			buzhou="是否下线工序设置错误!";
			if(null!=part.getOfflineProcess()&&!"".equals(part.getOfflineProcess())){
				tProcessInfo.setOfflineProcess(Integer.parseInt(part.getOfflineProcess()));
			}
			buzhou="工序是否连续设置错误!";	
			if(null!=part.getProcessDuration()&&!"".equals(part.getProcessDuration())){
				tProcessInfo.setProcessDuration(Integer.parseInt(part.getProcessDuration()));
			}
			buzhou="指导文件设置错误!";
			if(null!=part.getFile()&&!"".equals(part.getFile())){
				tProcessInfo.setFile(part.getFile());
			}
			buzhou="描述设置错误!";
			if(null!=part.getDescription()&&!"".equals(part.getDescription())){
				tProcessInfo.setDescription(part.getDescription());
			}
			buzhou="工艺方案设置错误!";
			tProcessInfo.setTProcessplanInfo(part.gettProcessplanInfo()); //设置工艺方案
			buzhou="装夹时间设置错误!";
			if(null!=part.getInstallTime()&&!"".equals(part.getInstallTime())){
				tProcessInfo.setInstallTime(Integer.parseInt(part.getInstallTime()));
			}
			buzhou="卸载时间设置错误!";
			if(null!=part.getUninstallTime()&&!"".equals(part.getUninstallTime())){
				tProcessInfo.setUninstallTime(Integer.parseInt(part.getUninstallTime()));
			}
			buzhou="是否必检设置错误!";
			if(null!=part.getIscheck()&&!"".equals(part.getIscheck())){
				tProcessInfo.setNeedcheck(Integer.parseInt(part.getIscheck()));//是否必检
			}
			buzhou="检验类型设置错误!";
			if(null!=part.getChecktype()&&!"".equals(part.getChecktype())){
				tProcessInfo.setCheckType(Integer.parseInt(part.getChecktype()));//检验类型
			}
			
			/**
			 **********************************机台数据准备************************************** 
			 */
			buzhou="没有获取到程序";
			tProcessInfo.setProgramId(Long.parseLong(part.getProgramID()));
			
			if(null!=part.getProcessingTime()&&!"".equals(part.getProcessingTime())){
				tProcessInfo.setProcessingTime(Double.parseDouble(part.getProcessingTime()));
			}
			tProcessInfo.setTheoryWorktime(Integer.parseInt(part.getTheoryWorktime()));
			if(null!=part.getCapacity()&&!"".equals(part.getCapacity())){
				tProcessInfo.setCapacity(Double.parseDouble(part.getCapacity()));
			}
			
			buzhou="保存工序报错";
			commonDao.update(tProcessInfo);
			System.out.println(tProcessInfo.getId());
			
			/**
			 **********************************设备数据准备************************************** 
			 */
			buzhou="更新设备报错";
			String delsb="delete from TProcessEquipment where processId='"+tProcessInfo.getId()+"'";
			commonDao.executeUpdate(delsb, parameters); //删除设备
			for(TProcessEquipmentModel tp:teti){
				TProcessEquipment tpe=new TProcessEquipment();
				tpe.setProcessId(tProcessInfo.getId());
				tpe.setEquipmentTypeId(Long.parseLong(tp.getEquipmentTypeId().toString()));
				if(null!=tp.getEquipmentId()&&!"".equals(tp.getEquipmentId())){
					tpe.setEquipmentId(Long.parseLong(tp.getEquipmentId().toString()));
				}
				commonDao.save(tpe);//添加工序关联设备
			}
			/**
			**********************************夹具信息数据准备2************************************** 
		    */
			buzhou="更新夹具报错";
			String deljj="delete from TProcessFixturetype where processId='"+tProcessInfo.getId()+"'";
			commonDao.executeUpdate(deljj, parameters);//删除夹具
			for(TProcessFixturetypeModel tpfm:tftmlist){
				TProcessFixturetype tpf=new TProcessFixturetype();
				tpf.setFixturetypeId(Long.parseLong(tpfm.getFixturetypeId().toString()));
				tpf.setProcessId(tProcessInfo.getId());
				commonDao.save(tpf);//添加夹具
			}
			
			
			
			/**
			 **********************************物料信息数据准备************************************** 
			 */
			buzhou="更新物料报错";
			String delwl="delete from TProcessmaterialInfo where TProcessInfo.id='"+tProcessInfo.getId()+"'";
			commonDao.executeUpdate(delwl, parameters);//删除物料
			 for(MaterialsModel mm:materialList){
				 TProcessmaterialInfo tpinfo=new TProcessmaterialInfo();
				 List<TMaterailTypeInfo>  tmaterail=this.getSelectTMaterailTypeInfoByNo(mm.getWlNo());
				 tpinfo.setTMaterailTypeInfo(tmaterail.get(0));//设置物料类型
				 tpinfo.setRequirementType(mm.getWlType());
				 tpinfo.setRequirementNum(Integer.parseInt(mm.getWlNumber()));
				 tpinfo.setTProcessInfo(tProcessInfo);//设置工序
				 commonDao.save(tpinfo);//保存物料信息
			 }
			/**
			 **********************************资源信息数据准备************************************** 
			 */
			 buzhou="保存刀具报错";
			 String deldj="delete from r_process_cuttertype_info  where processID='"+tProcessInfo.getId()+"'";
			// dao.executeUpdate(deldj);
			 commonDao.executeNativeUpdate(deldj);
			 for(CuttertypeModel mm:cuttertypeList){
				 TProcessCuttertypeInfo tpci=new TProcessCuttertypeInfo(); 
				 tpci.setProcessId(tProcessInfo.getId());//设置工序id
				 tpci.setCuttertypeId(Long.parseLong(mm.getDid()));//设置刀具id
				 tpci.setRequirementNum(Integer.parseInt(mm.getRequirementNum()));//设置数量
				 tpci.setStatus(0);
				 System.out.println("-tProcessInfo.getId()-"+tProcessInfo.getId()+"-mm.getDid()-"+mm.getDid()+"-mm.getRequirementNum()-"+mm.getRequirementNum());
				 //commonDao.save(tc);//保存刀具信息
				 commonDao.save(TProcessCuttertypeInfo.class, tpci);
			 }
			/**
			 **********************************成本信息数据准备************************************** 
			 */
			String delCost="delete from TPartProcessCost where TProcessInfo.id='"+tProcessInfo.getId()+"'";
			 commonDao.executeUpdate(delCost, parameters);
			 
			 TPartProcessCost tpc=new TPartProcessCost();
			 tpc.setTProcessInfo(tProcessInfo);//设置工序
			 
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
			 buzhou="更新成本报错";
			 commonDao.save(tpc);//保存成本信息
			 /**
				 **********************************质检数据准备************************************** 
			 */
			 String eqzj="from RProcessQuality where processId='"+tProcessInfo.getId()+"'";
			 List<RProcessQuality> zjlist=commonDao.executeQuery(eqzj, parameters);
			 for(RProcessQuality rpqq:zjlist){
				 String delzj="delete from TQualityParam where id='"+rpqq.getQualityId()+"'";
				 commonDao.executeUpdate(delzj, parameters);//删除质检表数据
				 commonDao.delete(rpqq);//删除质检工序表数据
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
				 buzhou="更新质检报错";
				 commonDao.save(tt);//添加质检数据
				 RProcessQuality rpq=new RProcessQuality();
				 rpq.setProcessId(Long.parseLong(tProcessInfo.getId().toString()));
				 rpq.setQualityId(tt.getId());
				 buzhou="更新质检工序报错";
				 commonDao.save(rpq);//质检工序表数据
			 }
			 return "保存成功";
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return buzhou;
		}
	}

	/**
	 * 工序配置向导更新
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
			 **********************************基本信息数据处理************************************** 
			 */
			String hql="from TProcessInfo where (no='"+part.getNo()+"' or name='"+part.getName()+"') and id<>'"+part.getId()+"' and status=0";
			List<TProcessInfo> rs=commonDao.executeQuery(hql, parameters);
			if(null!=rs&&rs.size()>0){
				return "工序已存在";
			}
			String hqltp="from TProcessInfo where id='"+part.getId()+"'";
			List<TProcessInfo> rstp=commonDao.executeQuery(hqltp, parameters);
			TProcessInfo tProcessInfo=rstp.get(0);//要保存的工序对象
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
			buzhou="没有获取到工艺方案";
			tProcessInfo.setTProcessplanInfo(part.gettProcessplanInfo()); //设置工艺方案
			
			/**
			**********************************夹具信息数据准备************************************** 
		    */
			if(null!=part.getInstallTime()&&!"".equals(part.getInstallTime())){
				tProcessInfo.setInstallTime(Integer.parseInt(part.getInstallTime()));
			}
			if(null!=part.getUninstallTime()&&!"".equals(part.getUninstallTime())){
				tProcessInfo.setUninstallTime(Integer.parseInt(part.getUninstallTime()));
			}
			buzhou="没有获取到夹具信息";
			//tProcessInfo.setFixtureId(Long.parseLong(part.getJjId()));
			
			/**
			 **********************************机台数据处理************************************** 
			 */
			buzhou="没有获取到设备类型";
			List<TEquipmenttypeInfo> equtype=this.getTEquipmenttypeInfoById(part.getEquipmentTypeID());
			//tProcessInfo.setTEquipmenttypeInfo(equtype.get(0)); //设置设备类型
			buzhou="没有获取到程序";
			//tProcessInfo.setProgramId(Integer.parseInt(part.getProgramID()));
			tProcessInfo.setProcessingTime(Double.parseDouble(part.getProcessingTime()));
			tProcessInfo.setTheoryWorktime(Integer.parseInt(part.getTheoryWorktime()));
			tProcessInfo.setCapacity(Double.parseDouble(part.getCapacity()));
			buzhou="保存工序报错";
			commonDao.update(tProcessInfo);//保存工序对象
			
			/**
			 **********************************物料信息数据处理************************************** 
			 */
			 for(MaterialsModel mm:materialList){
				
				 if(null!=mm.getDid()&&!"".equals(mm.getDid())){
					 List<TMaterailTypeInfo>  tmaterail=this.getSelectTMaterailTypeInfoByNo(mm.getWlNo());
					 buzhou="获取物料类型失败";
					 TProcessmaterialInfo tProcessmaterialInfo=(TProcessmaterialInfo)commonDao.executeQuery("from TProcessmaterialInfo where id='"+mm.getDid()+"'", parameters).get(0);
					 tProcessmaterialInfo.setTMaterailTypeInfo(tmaterail.get(0));//设置物料类型
					 tProcessmaterialInfo.setRequirementType(mm.getWlType());
					 tProcessmaterialInfo.setRequirementNum(Integer.parseInt(mm.getWlNumber()));
					 tProcessmaterialInfo.setTProcessInfo(tProcessInfo);//设置工序
					 tProcessmaterialInfo.setId(Long.parseLong(mm.getDid()));
					 buzhou="更新物料类型失败";
					 commonDao.update(tProcessmaterialInfo);//跟新物料信息  
				 }else{
					 TProcessmaterialInfo tProcessmaterialInfo=new TProcessmaterialInfo();
					 List<TMaterailTypeInfo>  tmaterail=this.getSelectTMaterailTypeInfoByNo(mm.getWlNo());
					 tProcessmaterialInfo.setTMaterailTypeInfo(tmaterail.get(0));//设置物料类型
					 tProcessmaterialInfo.setRequirementType(mm.getWlType());
					 tProcessmaterialInfo.setRequirementNum(Integer.parseInt(mm.getWlNumber()));
					 tProcessmaterialInfo.setTProcessInfo(tProcessInfo);//设置工序
					 buzhou="新增物料类型失败";
					 commonDao.save(tProcessmaterialInfo);//保存物料信息
				 }
				
			 }
			 for(MaterialsModel mm:deleteWL){
				 List<TMaterailTypeInfo>  tmaterail=this.getSelectTMaterailTypeInfoByNo(mm.getWlNo());
				 TProcessmaterialInfo tProcessmaterialInfo=(TProcessmaterialInfo)commonDao.executeQuery("from TProcessmaterialInfo where id='"+mm.getDid()+"'", parameters).get(0);
				 tProcessmaterialInfo.setStatus(1);
				 buzhou="删除物料类型失败";
				 commonDao.update(tProcessmaterialInfo); //删除物料
			 }
			
			/**
			 **********************************资源信息数据处理************************************** 
			 */
			 for(CuttertypeModel mm:cuttertypeList){
				
				 if(null!=mm.getDid()&&!"".equals(mm.getDid())&&!"-1".equals(mm.getDid())){
					 buzhou="获取刀具报错";
					 TProcessCuttertypeInfo tc=(TProcessCuttertypeInfo)commonDao.executeQuery("from TProcessCuttertypeInfo where id="+mm.getDid(), parameters).get(0); 
					 //tc.setProcessno(tProcessInfo.getNo()); //设置工序编号
					// tc.setCuttertypeNo(mm.getNo()); //设置刀具类型编号
					 tc.setRequirementNum(Integer.parseInt(mm.getRequirementNum()));//设置数量
					 tc.setStatus(0);
					 buzhou="更新刀具报错";
					 commonDao.update(tc);
				 }else{
					 TProcessCuttertypeInfo tc=new TProcessCuttertypeInfo(); 
					 //tc.setProcessno(tProcessInfo.getNo()); //设置工序编号
					// tc.setCuttertypeNo(mm.getNo()); //设置刀具类型编号
					 tc.setRequirementNum(Integer.parseInt(mm.getRequirementNum()));//设置数量
					 buzhou="新建刀具报错";
					 commonDao.save(tc);//保存刀具信息 
				 }
				
			 }
			 for(CuttertypeModel mm:deleteDJ){
				 TProcessCuttertypeInfo tc=(TProcessCuttertypeInfo)commonDao.executeQuery("from TProcessCuttertypeInfo where id="+mm.getDid(), parameters).get(0); 
				 //tc.setProcessno(tProcessInfo.getNo()); //设置工序编号
				 //tc.setCuttertypeNo(mm.getName()); //设置刀具类型编号
				 tc.setRequirementNum(Integer.parseInt(mm.getRequirementNum()));//设置数量
				 //tc.setId(Integer.parseInt(mm.getDid()));
				 tc.setStatus(1);
				 buzhou="删除刀具报错";
				 commonDao.update(tc);
			 }
			 
			/**
			 **********************************成本信息数据处理************************************** 
			 */
			 tpc.setTProcessInfo(tProcessInfo);//设置工序
			 
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
			 buzhou="更新成本报错";
			 commonDao.update(tpc);//保存成本信息
			 
			 return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return buzhou;
		}
	}
	
	/**
	 * 保存工艺方案
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
	 * 更新工艺方案
	 * @param plan
	 * @return
	 */
	public String updateTProcessplanInfo(TProcessplanInfo plan){
		try {
			commonDao.update(plan);
			return "更新成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "更新失败";
		}
		
	}
	/**
	 * 工艺方案设为默认
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
				return "设置成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "设置失败";
			}
		}else{
			try {
				commonDao.update(pp);
				return "设置成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "设置失败";
			}
		}
		
		
		
	}
	
	/**
	 * 根据工序id返回工序对象
	 * @param Id
	 * @return
	 */
	public TProcessInfo getTProcessInfoById(String Id){
		String hql="from TProcessInfo a where a.id='"+Id+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return (TProcessInfo)commonDao.executeQuery(hql, parameters).get(0);
	}

	/**
	 * 根据工序id查询刀具信息
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
	 * 根据工序id查询夹具信息
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
	 * 查询设备节点
	 * @param search
	 * @return
	 */
	public TreeNode getTreeNodeEquInfo(String nodeid){
		TreeNode root =new DefaultTreeNode("根",null);
		TreeNode root2 =new DefaultTreeNode("根",null);
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
	 * 递归返回物料树节点
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
	 * 根据设备类型id 查找设备
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
	 * 根据设备类型id查找单个设备
	 * @param id
	 * @return
	 */
	public List<TEquipmentInfo> getTEquipmentInfoByEquSerialNo(String equSerialNo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TEquipmentInfo where equSerialNo='"+equSerialNo+"'";
		return commonDao.executeQuery(hql, parameters);
	}
	/**
	 * 根据工序id查找工序设备表
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
   * 根据工序id获取夹具数据
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
   * 根据工序获取质检数据
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
   * 根据工艺方案获取质检数据
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
   * 更加查询条件，模糊查询零件名称
   */
  public List<TPartTypeInfo> getAllPartType(String nodeid, String partName){
	  String hql = "from TPartTypeInfo t where t.nodeid = '"+nodeid+"' and t.name like '%"+partName+"%' and t.status<>'删除' ORDER BY t.name ASC";
	  return dao.executeQuery(hql);
  }
  
  /**
   * 更加零件名称查询零件信息
   * @return
   */
  public List<TPartTypeInfo> getPartTypeInfo(String partName){
	  String hql = "from TPartTypeInfo t where t.name = '"+partName+"'";
	  return dao.executeQuery(hql);
  }
  
  /**
   * 根据工序id 返回物料信息
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
   * 获取工序机台
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

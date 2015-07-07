package smtcl.mocs.services.device.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;


import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TMemberInfo;
import smtcl.mocs.pojos.job.TPositionInfo;
import smtcl.mocs.pojos.job.TTeamInfo;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.device.IMemberService;
import smtcl.mocs.utils.device.StringUtils;


@SuppressWarnings("unchecked")
public class MemberServiceImpl extends GenericServiceSpringImpl<TNodes, String> implements IMemberService{
	
	private ICommonService commonService;
	 
	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	
	/**
	 * 获取人员
	 * @param pageNo
	 * @param pageSize
	 * @param no
	 * @return
	 */
	public List<TMemberInfo> queryMemberList(int pageNo, int pageSize, String area) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = " from TMemberInfo t where status=0 " ;
		if(area=="0")
			;
		else if(area ==""||area==null)
			hql+= "and nodeid=null or nodeid=''";
		else{
			if(area.split(",").length > 1)
				hql+= "and nodeid in("+area+")";
			else
				hql+= "and nodeid in('"+area+"')";
		}
		List<TMemberInfo> dataList = dao.executeQuery(hql, parameters);
		
		return dataList;
	}   	
	
	public List<Map<String,Object>> queryMemberList(String area,String name) {		
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select m.id as id,m.idcard as idcard, m.name as name,m.no as no,m.age as age,m.sex as sex,m.birthday as birthday,m.marriage as marriage,m.education as education," +
				"m.phone_number as phoneNumber,m.address as address,m.email as email,m.positionid as positionid,m.nodeid as nodeid,m.teamid as teamid,"+
				"n.nodeName as nodeName,p.position_name as positionName,t.team_name as teamName,m.salary as salary "+
				" from t_member_info m " +
				" left join T_Nodes n on m.nodeid=n.nodeID" +
				" left join t_position_info p on m.positionid=p.position_id "+
				" left join t_team_info t on m.teamid=t.teamid"+
				" where 1=1 and m.status=0 ";
		if(area !=""&&area!=null)
		{
			if(area.contains("'"))
				sql+= "and m.nodeid in("+area+")";
			else
				sql+= "and m.nodeid in('"+area+"')";
		}
		else if(name !=""&&name!=null&&!name.equals("输入名称/工号"))
			sql+= " and (m.name like '%"+name+"%' or m.no like '%"+name+"%')";
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}   
	//根据menberId获取用户
	public List<TUser> getUserByMenberId(String MenberId) {	
		String hql="from TUser where memberId="+MenberId;
		return dao.executeQuery(hql);
	}
	
	public List<TMemberInfo> queryList(String search) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = " from TMemberInfo t " ;
		if(search!=""&&search!=null)
			hql+= "where name='"+search+"' or no='"+search+"'";
			
		List<TMemberInfo> dataList = dao.executeQuery(hql, parameters);
		 
		return dataList;
	}   		
	 
	
//	public void deleteMemberRecord(int id){
//		Collection<Parameter> parameters = new HashSet<Parameter>();
//		String sql = "delete from t_member_info where id="+id;
//		
//		dao.executeNativeUpdate(sql, parameters);
//	}
	
	
	
	public List<TTeamInfo> getTeamRecordById(int id){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TTeamInfo where teamid="+id;
		
		List<TTeamInfo> dataList = dao.executeQuery(hql, parameters);

		return dataList;
	}
	
	public List<TMemberInfo> getRecordById(int id){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "from TMemberInfo where id="+id;
		
		List<TMemberInfo> dataList = dao.executeQuery(hql, parameters);

		return dataList;
	}
	
	public void setStatus(String table, String id, long value){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "update "+table+" set status=1 where "+id+"="+value;
		
		dao.executeNativeUpdate(sql, parameters);
	}
	
	public void deleteRecord(TMemberInfo tm) {
		commonService.delete(tm);
	}   		
	
	public void deleteRecord(String table){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "delete from "+table+" where status=1";
		
		dao.executeNativeUpdate(sql, parameters);
	}
	
	public void updateRecord(TMemberInfo tm,String userId) {
		commonService.update(tm);
		if(null!=userId&&!"".equals(userId)){
			String hql="from TUser where userId='"+userId+"' and memberId="+tm.getId();
			List<TUser> rsUser=dao.executeQuery(hql);
			if(null!=rsUser&&rsUser.size()>0){
				
			}else{
				TUser user=dao.get(TUser.class, userId);
				user.setMemberId(tm.getId().toString());
				dao.update(TUser.class, user);
			}
		}
	}  
	
	public void updateRecord(String nodeid,int id){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "";
		
		if(nodeid==null||nodeid=="")
			sql = "update T_Member_info set nodeid=NULL,positionid=NULL where id="+id;
		else
			sql = "update T_Member_info set nodeid='"+nodeid+"',positionid=NULL where id="+id;
		dao.executeNativeUpdate(sql, parameters);
	}
	
	public void updateTeamRecord(String nodeid,int id){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "";
		
		if(nodeid==null||nodeid=="")
			sql = "update T_Member_info set nodeid=NULL,positionid=NULL where id="+id;
		else
			sql = "update T_Member_info set nodeid='"+nodeid+"',teamid=NULL where id="+id;
		dao.executeNativeUpdate(sql, parameters);
	}
	
	public List<Map<String, Object>> getAreaList() {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "SELECT distinct  nodeid, nodeName from t_nodes where nodeClass!='root' and nodeClass!='13' ";	
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	public List<Map<String, Object>> getTeamList(String nodeid) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select new Map(t.teamid as teamid,t.teamName as teamName) from TTeamInfo t ";
		if(!StringUtils.isEmpty(nodeid)){
			//hql+=" where t.nodeid='"+nodeid+"'";
			if(nodeid.split(",").length > 1)
				hql+= "where t.nodeid in("+nodeid+")";
			else
				hql+= "where t.nodeid in('"+nodeid+"')";
		}
		return dao.executeQuery(hql, parameters);
	}
	
	public List<Map<String, Object>> getPositionList(String nodeid) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select new Map(t.positionId as positionId,t.positionName as positionName) from TPositionInfo t where t.status=0 ";
		if(!StringUtils.isEmpty(nodeid)) {
//			hql+=" and t.nodeid='"+nodeid+"'";			
			if(nodeid.split(",").length > 1)
				hql+= "and t.nodeid in("+nodeid+")";
			else
				hql+= "and t.nodeid in('"+nodeid+"')";
		}
		return dao.executeQuery(hql, parameters);
	}
	
	
	@Override
	public void insertRecord(TMemberInfo tm,String userId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		commonService.save(tm);
		String hql="from TUser where userId='"+userId+"'";
		List<TUser> rsuser=dao.executeQuery(hql);
		if(null!=rsuser&&rsuser.size()>0){
			TUser tu=rsuser.get(0);
			tu.setMemberId(tm.getId().toString());
			commonService.update(TUser.class, tu);
		}
	}
	/**
	 * 人员维护
	 */
//	public List<TMemberInfo> getMemberList(String nodeid) {
//		Collection<Parameter> parameters = new HashSet<Parameter>();
//		String hql = " from TMemberInfo t " ;
//		if(area!=""&&area!=null)
//			hql+= "where nodeid='"+area+"'";
//			
//		List<TMemberInfo> dataList = dao.executeQuery(hql, parameters);
//		
//		return dataList;
//	}   	
	
	/**
	 * 部门职位维护
	 */
	public List<TPositionInfo> queryPositionList(String nodeid, String type, String name)
	{
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = " from TPositionInfo t  where status=0 " ;
		if(!StringUtils.isEmpty(nodeid)) 
			hql+=" and nodeId='"+nodeid+"'";
		if(!StringUtils.isEmpty(type)) 
			hql+=" and category='"+type+"'";
		if(!StringUtils.isEmpty(name)) 
			hql+=" and positionName='"+name+"'";
		List<TPositionInfo> dataList = dao.executeQuery(hql, parameters);
		
		return dataList;
	}
	
	/**
	 * 班组维护
	 */
	public List<Map<String,Object>> queryTeamList(String nodeid, String teamName)
	{
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = " select t.teamid as teamid,nodes.nodeID as nodeid,nodes.nodeName as nodeName,t.team_name as teamName,t.category as category,t2.name as name,t2.id as id " +
				"from t_member_info t2 " +
				" left join T_Nodes nodes on t2.nodeid=nodes.nodeID" +
				" left join t_team_info t on t2.teamid=t.teamid where t2.status=0 ";
		if(!StringUtils.isEmpty(nodeid)) 
			sql+=" and t2.nodeid='"+nodeid+"'";
		if(!StringUtils.isEmpty(teamName)) 
			sql+=" and t.team_name='"+teamName+"'";
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}
	
	public List<Map<String,Object>> queryTeamList(String nodeid)
	{
		Collection<Parameter> parameters = new HashSet<Parameter>();
//		String sql = " select t.teamid as id,t.team_name as teamName,t.teamcode as teamCode,t.workstation as workstation" +
//				" from t_team_info t ";
//	
//		if(!StringUtils.isEmpty(nodeid)) 
//			sql+=" where t.nodeid='"+nodeid+"'";
//		
//		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		String sql = " select new MAP(t.teamid as id,t.nodeid as nodeid,t.teamName as teamName,t.teamcode as teamCode,t.workstation as workstation,n.nodeName as nodeName) " +
				" from TTeamInfo t, TNodes n where n.nodeId=t.nodeid";
	
		if(!StringUtils.isEmpty(nodeid)) 
			sql+=" and t.nodeid='"+nodeid+"'";
		
		List<Map<String,Object>> dataList = dao.executeQuery(sql, parameters);
		
		return dataList;
	}
	
	/**
	 * 部门人员维护
	 */
	public List<Map<String,Object>> queryPositionList1(String nodeid, String type, String name)
	{
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = " select t.position_id as positionid,nodes.nodeName as nodeName,t.position_name as positionName,t.category as category,t2.name as name,t2.id as id " +
				"from t_member_info t2 " +
				" left join T_Nodes nodes on t2.nodeid=nodes.nodeID" +
				" left join t_position_info t on t2.positionid=t.position_id where t2.status=0 and t.status=0 ";
		if(!StringUtils.isEmpty(nodeid)) 
			sql+=" and t2.nodeid='"+nodeid+"'";
		if(!StringUtils.isEmpty(type)) 
			sql+=" and t.category='"+type+"'";
		if(!StringUtils.isEmpty(name)) 
			sql+=" and t.position_name='"+name+"'";
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}
	
	/**
	 * 部门插入记录
	 * @param tm
	 */
	public void insertPositionRecord(TPositionInfo tm)
	{
		commonService.save(tm);
	}
		
//	/**
//	 * 部门删除记录
//	 */
//	public void deletePositionRecord(TPositionInfo tm)
//	{
//		commonService.delete(tm);
//	}
	
	
	public void updatePositionRecord(TPositionInfo tm)
	{
		commonService.update(tm);
	}
	
//	public List<Map<String,Object>> getPartTree(String query)
//	{
//		List<Map<String,Object>> rs=null ;
//		return rs;
//	}

	@Override
	public List<Map<String, Object>> getTeamTypesByNodeId(String nodeId) {
		// TODO Auto-generated method stub
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql="select teamid,team_name from t_team_info ";
		if(!StringUtils.isEmpty(nodeId)) 
			sql+=" where nodeId='"+nodeId+"'";
				
		return dao.executeNativeQuery(sql, parameters);
	}
	
	
	@Override
	public List<Map<String, Object>> getPositionTypesByNodeId(String nodeId) {
		// TODO Auto-generated method stub
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select distinct new Map(position.positionId as positionId,position.category as positionType) from TPositionInfo position where status=0 ";
		if(!StringUtils.isEmpty(nodeId)) 
			hql+=" and nodeId='"+nodeId+"'";
		
		return dao.executeQuery(hql, parameters);
	}

	@Override
	public List<Map<String, Object>> getPositionNamesByNodeId(String nodeId,
			String positonType) {
		// TODO Auto-generated method stub
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select distinct new Map(position.positionId as positionId,position.positionName as positionName,position.category as positionType) from TPositionInfo position where status=0 ";
		if(!StringUtils.isEmpty(nodeId)) hql+=" and nodeId='"+nodeId+"'";
		if(!StringUtils.isEmpty(positonType)) hql+=" and category='"+positonType+"'";
		return dao.executeQuery(hql, parameters);
	}

	public List<Map<String, Object>> checkMember(String name,String nodeid){
		
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TMemberInfo where no='"+name+"' and status=0";
		//String hql="from TMemberInfo where no='"+name+"' and nodeid='"+nodeid+"'";
		return dao.executeQuery(hql, parameters);
	}
	
	//public List<Map<String, Object>> checkDelePosition(String nodeid){
//		
	//}
	
//	public List<Map<String, Object>> checkPosition(String code){
//		
//		Collection<Parameter> parameters = new HashSet<Parameter>();
//		String hql="from TPositionInfo where teamcode='"+code+"'";
//		return dao.executeQuery(hql, parameters);
//	}
	
	public List<Map<String, Object>> checkTeam(String code){
		
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TTeamInfo where teamcode='"+code+"'";
		return dao.executeQuery(hql, parameters);
	}
	
	/**
	 * 班组插入记录
	 * @param tm
	 */
	public void insertTeamRecord(TTeamInfo tm){
		commonService.save(tm);
	}
	
	/**
	 * 班组删除记录
	 */
	public void deleteTeamRecord(int teamid){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql="delete from t_team_info where teamid="+teamid;

		dao.executeNativeUpdate(sql, parameters);
	}
	
	/**
	 * 班组更新记录
	 */
	public void updateTeamRecord(TTeamInfo tm){
		commonService.update(tm);
	}
	
}

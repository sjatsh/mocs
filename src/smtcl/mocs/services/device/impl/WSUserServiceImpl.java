package smtcl.mocs.services.device.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.misc.AlgorithmUtil;
import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;

import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TEquipmentMemberInfo;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TEquJobDispatch;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.pojos.job.TMemberInfo;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.device.IWSUserService;
import smtcl.mocs.utils.device.Constants;


/**
 * WebService 用户登录调用接口
 * 
 * @创建者:JiangFeng
 * @修改者:
 * @备注:
 * @创建时间：2013-04-26
 * @修改日期：
 * @修改说明：
 * @版本：V1.0
 */
public class WSUserServiceImpl extends GenericServiceSpringImpl<TUser, String>
		implements IWSUserService {
	
	/**
	 * 注入通用service
	 */
	private ICommonService commonService;

    /**
     * remote 远程数据源服务接口
     */
    private ICommonService remoteCommonService;

    public ICommonService getCommonService() {
        return commonService;
    }

    public void setCommonService(ICommonService commonService) {
        this.commonService = commonService;
    }

    public ICommonService getRemoteCommonService() {
        return remoteCommonService;
    }

    public void setRemoteCommonService(ICommonService remoteCommonService) {
        this.remoteCommonService = remoteCommonService;
    }

	/**
	 * 用户登录
	 */
	@Override
	public Map<String, Object> userLogin(String username, String userpwd) {
		Map<String, Object> loginRes = new HashMap<String, Object>();
		Parameter p_user_name = new Parameter("loginName", username,Operator.EQ);
		TUser user = this.get(p_user_name);
		if (null != user) {
			String encode;
			try {
				encode = AlgorithmUtil.md5(userpwd).toUpperCase();
				if (user.getPassword().equals(encode)) {
					String sessionId = "session" + user.getUserId();
					loginRes.put("sessionId", sessionId);
					loginRes.put("name", user.getNickName());
					loginRes.put("userId", user.getUserId());
					loginRes.put("orgNode", 0);
					loginRes.put("msg",Constants.USER_LOGIN_MSG[2]);
					loginRes.put("success", true);
				} else {
					loginRes.put("success", false);
					loginRes.put("msg", Constants.USER_LOGIN_MSG[1]);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			loginRes.put("success", false);
			loginRes.put("msg", Constants.USER_LOGIN_MSG[0]);
		}
		return loginRes;
	}

	@Override
	public Map<String, Object> getUserInfo(String userId, String userName) {
		Map<String, Object> result = null;
		
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "select new Map( "
				+ "u.email as email, "
				+ "u.email as phoneNumber ) "
				+ "from TUser as u "
				+ "where u.userId='" + userId + "' or u.nickName='" + userName+"'";
		List<Map<String, Object>> rs = dao.executeQuery(hql, parameters);
		if(rs.size()!=0){
			result = rs.get(0);
			result.put("msg", "success");
		} else{
			result = new HashMap<String, Object>();
			result.put("msg", "failure");
		}
		return result;
	}

	@Override
	public Map<String, Object> getUserList() {

		Map<String, Object> result = null;
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "select new Map( "
				+ "u.userId as userId, "
				+ "u.nickName as userName, "
				+ "u.email as email, "
				+ "u.email as phoneNumber ) "
				+ "from TUser as u";
		List<Map<String, Object>> rs = dao.executeQuery(hql, parameters);
		if(rs.size()!=0){
			result = new HashMap<String, Object>();
			result.put("content", rs);
			result.put("msg", "success");
		} else{
			result = new HashMap<String, Object>();
			result.put("msg", "failure");
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProduceTask(String equSerialNo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select new Map("
				 + " a.no as no,"                       //工单编号
				 + " a.TProcessInfo.name as processName," //工序名称
				 + " a.TProcessInfo.no as processNo," //工序编号
				 + " a.TProcessInfo.TProcessplanInfo.TPartTypeInfo.name as partName,"//产品名称
				 + " a.TProcessInfo.TProcessplanInfo.TPartTypeInfo.no as materialName,"//产品编号
				 + " b.equSerialNo as equSerialNo,"//设备序列号
				 + " a.processNum as planNum," //计划数量
				 + " a.finishNum as finishNum,"//完成数量
				 + " DATE_FORMAT(a.realStarttime,'%Y-%m-%d %T') as startTime,"//实际开始时间
				 + " a.onlineNumber as onlineNumber,"//上线数量
				 + " c.progName as progName) "//程序名
				 + " from TJobdispatchlistInfo a,TEquJobDispatch b,TProgramInfo c"
				 + " where a.no=b.jobdispatchNo "
				 + " and a.TProcessInfo.programId=c.id "
				 + " and b.status<>0 "
				 + " and (a.status=40 or a.status=50) "
				 + " and b.equSerialNo='"+equSerialNo+"' "
		 		 + " order by a.realStarttime asc ";
		List<Map<String,Object>>  rs=dao.executeQuery(hql, parameters);
		return rs;
	}
	
	/**
	 * 更新生产任务
	 * @param equSerialNo
	 * @param jobDispatchNo 工单编号
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public boolean updateProduceTask(String equSerialNo,String jobDispatchNo){
		String hql="from TJobdispatchlistInfo t where no='"+jobDispatchNo+"'";
        List<TJobdispatchlistInfo> tjlist = remoteCommonService.executeQuery(hql);
		if(null!=tjlist&&tjlist.size()>0){
			TJobdispatchlistInfo tj=tjlist.get(0);
            if (tj.getFinishNum() <= 0) {
//				tj.setOnlineNumber((null==tj.getOnlineNumber()?0:tj.getOnlineNumber())+1);
                tj.setStatus(50); //状态从上线(40) 到 加工(50)
                Date da=new Date();
                tj.setRealStarttime(da);
            }
//            else {
//				tj.setOnlineNumber((null==tj.getOnlineNumber()?0:tj.getOnlineNumber())+1);
//            }

            remoteCommonService.update(TJobdispatchlistInfo.class,tj);

			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 切换工单
	 * @param equSerialNo 机床序列号
	 * @param jobDispatchNo 工单编号
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public Map<String,Object> changeProduceTask(String equSerialNo,String jobDispatchNo){
        Map<String, Object> res = new HashMap<String, Object>();

        String sql = "update  t_equ_jobdispatch set status=1 where status!=0 and equ_SerialNo='" + equSerialNo + "'";
        remoteCommonService.executeNativeUpdate(sql);

        String hql = "from TEquJobDispatch where equSerialNo='" + equSerialNo + "' and jobdispatchNo='" + jobDispatchNo + "'";
        List<TEquJobDispatch> rs = remoteCommonService.executeQuery(hql);
        if (null != rs && rs.size() > 0) {
            TEquJobDispatch ejd = rs.get(0);
            ejd.setStatus(2);
            remoteCommonService.update(TEquJobDispatch.class, ejd);
        }

        hql = " from TJobdispatchlistInfo where no='" + jobDispatchNo + "'";
        List<TJobdispatchlistInfo> jobdispatch = remoteCommonService.executeQuery(hql);
        if (null != jobdispatch && jobdispatch.size() > 0) {
            TJobdispatchlistInfo rec = jobdispatch.get(0);
            res.put("planNum", rec.getProcessNum());
            res.put("finishNum", rec.getFinishNum());
            res.put("jobDispatchNo", rec.getNo());
        }
        return res;

	}
	
	public Boolean switchMode(String equSerialNo,String type)
	{
		try
		{
			String hql="update TEquipmentInfo t set t.status="+type+" where t.equSerialNo='"+equSerialNo+"'";
			dao.executeUpdate(hql);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

    @SuppressWarnings("unchecked")
	public Boolean jobFinished(String equSerialNo,String jobDispatchNo){
        String hql = " from TJobdispatchlistInfo t where no='" + jobDispatchNo + "'";
        List<TJobdispatchlistInfo> tjlist = remoteCommonService.executeQuery(hql);
        if (null != tjlist && tjlist.size() > 0) {
            TJobdispatchlistInfo tj = tjlist.get(0);
            if (tj.getStatus() == 60 || tj.getStatus() == 70)
                return true;
        }
        return false;
    }
	
	@Override
	public Map<String,Object> memberLogin(String memID, String memPass,
			String equSerialNo) {
		// TODO Auto-generated method stub
		//暂时不验证密码，后续完善
		//添加验证密码:密码先固定123456
		Map<String, Object> loginRes = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(memID))
		{
           if(Constants.LOGIN_PWD.equals(memPass))	
	        {
				String hql=" from TMemberInfo t where t.no='"+memID+"'";
				List<TMemberInfo> member=dao.executeQuery(hql);
				//人员存在
				if(member!=null&&member.size()>0) 
				    {
				       TMemberInfo memberinfo=member.get(0);
					   hql=" from TEquipmentInfo t where t.equSerialNo='"+equSerialNo+"'";
						List<TEquipmentInfo> list=dao.executeQuery(hql);
						if(list!=null&&list.size()>0)
						{
							TEquipmentInfo tEquipmentInfo=list.get(0);
							hql=" from TEquipmentMemberInfo t where t.equId="+tEquipmentInfo.getEquId();
							List<TEquipmentMemberInfo> list1=dao.executeQuery(hql);
							if(list1!=null&&list1.size()>0)
							{
								TEquipmentMemberInfo tEquipmentMemberInfo=list1.get(0);
								tEquipmentMemberInfo.setOperatorNo(memID);
								dao.update(TEquipmentMemberInfo.class,tEquipmentMemberInfo);
								loginRes.put("msg",Constants.USER_LOGIN_MSG[2]);
								loginRes.put("success", true);
								loginRes.put("memName", memberinfo.getName());
							}
						 }
				    }
				else {
					loginRes.put("msg",Constants.USER_LOGIN_MSG[0]); //人员不存在
					loginRes.put("success", false);
				  }
	           }
			else 
			{
				loginRes.put("msg",Constants.USER_LOGIN_MSG[1]);
				loginRes.put("success", false); //密码不正确
				
			}
		}
		
		return loginRes;
	}

	@Override
    @SuppressWarnings("unchecked")
	public int JobOnlineCheck(String equSerialNo, String jobDispatchNo) {
		String hql = "select new Map(jobdis.id as Id,jobdis.onlineNumber as onlineNumber,jobdis.processNum as planNum) "
				+ " from TJobdispatchlistInfo jobdis,TEquJobDispatch equ_job"
				+ " where equ_job.jobdispatchNo=jobdis.no"
				+ " and equ_job.jobdispatchNo='"+jobDispatchNo+"'"
				+ " and equ_job.equSerialNo='"+equSerialNo+"'";
		List<Map<String,Object>> list = remoteCommonService.executeQuery(hql);
		if(list != null && list.size()>0){
			Map<String,Object> map = list.get(0);
			int onlineNumber=0;
			int planNum=0;
			if(map.get("onlineNumber")!=null && !map.get("onlineNumber").toString().equals("")){
				onlineNumber = Integer.parseInt(map.get("onlineNumber").toString());
			}
			if(map.get("planNum")!=null && !map.get("planNum").toString().equals("")){
				planNum = Integer.parseInt(map.get("planNum").toString());
			}
			
			Long id = Long.parseLong(map.get("Id").toString());
			TJobdispatchlistInfo jobdis = dao.get(TJobdispatchlistInfo.class, id);
			jobdis.setOnlineNumber(jobdis.getOnlineNumber()==null?0:jobdis.getOnlineNumber()+1);
            remoteCommonService.save(TJobdispatchlistInfo.class,jobdis);
			
			if(onlineNumber>planNum){
				return 2;
			}
		}
		return 1;
	}
}

package smtcl.mocs.services.device.impl;

import java.util.ArrayList;
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
 * WebService �û���¼���ýӿ�
 * 
 * @������:JiangFeng
 * @�޸���:
 * @��ע:
 * @����ʱ�䣺2013-04-26
 * @�޸����ڣ�
 * @�޸�˵����
 * @�汾��V1.0
 */
public class WSUserServiceImpl extends GenericServiceSpringImpl<TUser, String>
		implements IWSUserService {
	
	/**
	 * ע��ͨ��service
	 */
	private ICommonService commonService;
	 
	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * �û���¼
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
				 + " a.no as no,"                       //�������
				 + " a.TProcessInfo.name as processName," //��������
				 + " a.TProcessInfo.no as processNo," //������
				 + " a.TProcessInfo.TProcessplanInfo.TPartTypeInfo.name as partName,"//��Ʒ����
				 + " a.TProcessInfo.TProcessplanInfo.TPartTypeInfo.no as materialName,"//��Ʒ���
				 + " b.equSerialNo as equSerialNo,"//�豸���к�
				 + " a.processNum as planNum," //�ƻ�����
				 + " a.finishNum as finishNum,"//�������
				 + " DATE_FORMAT(a.realStarttime,'%Y-%m-%d %T') as startTime,"//ʵ�ʿ�ʼʱ��
				 + " a.onlineNumber as onlineNumber,"//��������
				 + " c.progName as progName) "//������
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
	 * ������������
	 * @param equSerialNo
	 * @param jobDispatchNo �������
	 * @return
	 */
	public boolean updateProduceTask(String equSerialNo,String jobDispatchNo){
		String hql="from TJobdispatchlistInfo t where no='"+jobDispatchNo+"'";
		List<TJobdispatchlistInfo> tjlist=dao.executeQuery(hql);
		if(null!=tjlist&&tjlist.size()>0){
			TJobdispatchlistInfo tj=tjlist.get(0);
			if(tj.getFinishNum()>0){
				tj.setOnlineNumber((null==tj.getOnlineNumber()?0:tj.getOnlineNumber())+1);
			}else{
				tj.setOnlineNumber((null==tj.getOnlineNumber()?0:tj.getOnlineNumber())+1);
				tj.setStatus(50); //״̬������(40) �� �ӹ�(50)
				Date da=new Date();
				tj.setRealStarttime(da);
			}
			
			try {
				dao.update(TJobdispatchlistInfo.class,tj);
				/*
				String sql="update  t_equ_jobdispatch set status=1 where status!=0 and equ_SerialNo='"+equSerialNo+"'";
				dao.executeNativeUpdate(sql);         
				
				hql="from TEquJobDispatch where equSerialNo='"+equSerialNo+"' and jobdispatchNo='"+jobDispatchNo+"'";
				List<TEquJobDispatch> rs=dao.executeQuery(hql);
				TEquJobDispatch ejd=new TEquJobDispatch();
				if(null!=rs&&rs.size()>0){
					ejd=rs.get(0);
					//ejd.setJobdispatchNo(tj.getNo());
					ejd.setStatus(2);					
					dao.update(TEquJobDispatch.class, ejd);
				}
		*/
				
//				}else{
//					ejd.setEquSerialNo(equSerialNo);
//					ejd.setJobdispatchNo(tj.getNo());
//					dao.save(TEquJobDispatch.class, ejd);
//				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * �л�����
	 * @param equSerialNo
	 * @param jobDispatchNo �������
	 * @return
	 */
	public Map<String,Object> changeProduceTask(String equSerialNo,String jobDispatchNo){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			
			String sql="update  t_equ_jobdispatch set status=1 where status!=0 and equ_SerialNo='"+equSerialNo+"'";
			dao.executeNativeUpdate(sql);         
			
			String hql="from TEquJobDispatch where equSerialNo='"+equSerialNo+"' and jobdispatchNo='"+jobDispatchNo+"'";
			List<TEquJobDispatch> rs=dao.executeQuery(hql);
			TEquJobDispatch ejd=new TEquJobDispatch();
			if(null!=rs&&rs.size()>0){
				ejd=rs.get(0);
				//ejd.setJobdispatchNo(tj.getNo());
				ejd.setStatus(2);					
				dao.update(TEquJobDispatch.class, ejd);
			}
			
			hql=" from TJobdispatchlistInfo where no='"+jobDispatchNo+"'";
			List<TJobdispatchlistInfo> jobdispatch=dao.executeQuery(hql);
			if(null!=jobdispatch&&jobdispatch.size()>0){
				TJobdispatchlistInfo rec=jobdispatch.get(0);
				res.put("planNum", rec.getProcessNum());
				res.put("finishNum",rec.getFinishNum());
				res.put("jobDispatchNo",rec.getNo());
			  }
			return res;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
	
	public Boolean jobFinished(String equSerialNo,String jobDispatchNo){
		try{
		String hql=" from TJobdispatchlistInfo t where no='"+jobDispatchNo+"'";
		List<TJobdispatchlistInfo> tjlist=dao.executeQuery(hql);
		if(null!=tjlist&&tjlist.size()>0){
			TJobdispatchlistInfo tj=tjlist.get(0);
			if(tj.getStatus().intValue()==60 || tj.getStatus().intValue()==70)
			return true;
		  }	
		}catch(Exception e){
			
		}
		return false;
	}
	
	@Override
	public Map<String,Object> memberLogin(String memID, String memPass,
			String equSerialNo) {
		// TODO Auto-generated method stub
		//��ʱ����֤���룬��������
		//�����֤����:�����ȹ̶�123456
		Map<String, Object> loginRes = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(memID))
		{
           if(Constants.LOGIN_PWD.equals(memPass))	
	        {
				String hql=" from TMemberInfo t where t.no='"+memID+"'";
				List<TMemberInfo> member=dao.executeQuery(hql);
				//��Ա����
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
					loginRes.put("msg",Constants.USER_LOGIN_MSG[0]); //��Ա������
					loginRes.put("success", false);
				  }
	           }
			else 
			{
				loginRes.put("msg",Constants.USER_LOGIN_MSG[1]);
				loginRes.put("success", false); //���벻��ȷ
				
			}
		}
		
		return loginRes;
	}
}

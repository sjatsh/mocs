
package smtcl.mocs.services.jobplan.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ibm.faces.util.StringUtil;

import smtcl.mocs.beans.jobplan.JobPlanAddBean;
import smtcl.mocs.beans.jobplan.JobPlanUpdataBean;
import smtcl.mocs.dao.device.ICommonDao;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TJobplanTaskInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;

/**
 * ��ҵ�ƻ�����SERVICE�ӿ�ʵ����
 * @���� Jf
 * @����ʱ�� 2013-05-06
 * @�޸��� songkaiang
 * @�޸�����
 * @�޸�˵��
 * @version V1.0
 */
public class JobPlanServiceImpl extends GenericServiceSpringImpl<TJobplanInfo, String> implements IJobPlanService {

	@Override
	public List<Map<String, Object>> findAllJobPlanAndPartInfo(String nodeId) {
		String hql="SELECT d.id AS Id,  d.typeNo AS Cls,d.name AS Name " +
				"from t_part_type_info d where d.status !='ɾ��' " +
				"and d.nodeid='"+nodeId+"'" +
				"order by CONVERT(d.name USING gbk) asc ";
		return dao.executeNativeQuery(hql);
	}
	
	public Map<String, Object> getAllJobPlanAndPartInfo(String nodeId,String partName,String planStatus,String startTime,String endTime,String isexpand,String locale){
		String sql="SELECT"
				+ " a.id as partId,"
				+ " a.name AS partName,"
				+ " b.ID AS jobPlanId,"
				+ " b.name AS jobPlanName,"
				+ " b.p_id AS pId,"
				+ " b.plan_type AS planType"
				+ " FROM t_part_type_info a LEFT JOIN t_jobplan_info b"
				+ " ON a.id=b.partId ";
		if(startTime!=null && !startTime.isEmpty()){
			sql += " and b.plan_starttime >=DATE_FORMAT('"+startTime+"','%Y-%m-%d %T') ";
		}
		if(endTime != null && !endTime.isEmpty()){
			sql += " and b.plan_endtime <= DATE_FORMAT('"+endTime+"','%Y-%m-%d %T') ";
		}
		
		sql += " WHERE a.status<>'ɾ��' "
				+ " AND a.nodeid = '"+nodeId+"'";
		//��ӹ��˲�ѯ����
		if(partName!=null && !partName.isEmpty()){
			sql += " and a.id in("+partName+")";
		}
		if(planStatus!=null && !planStatus.isEmpty()){
			sql += " and b.status in ("+planStatus+")";
		}
		
		sql += " ORDER BY a.name,b.plan_type,b.name";
		List<Map<String, Object>> ds=dao.executeNativeQuery(sql);
		List<String> prlist = new ArrayList<String>();
		List<Map<String, Object>> rs=new ArrayList<Map<String, Object>>();
		boolean iszk=Boolean.parseBoolean(isexpand);
		//iszk=true;
		for(Map<String, Object> map:ds){
			if(null!=map.get("partId")&&!prlist.contains(map.get("partId").toString())){
				
				List<Map<String, Object>> san=new ArrayList<Map<String, Object>>();
				Map<String, Object> pc=new HashMap<String, Object>();
				pc.put("Id",map.get("partId")+"0000002");
                if(locale.equals("en") || locale.equals("en_US")){
                    pc.put("Name","Batch Plan");
                }else{
                    pc.put("Name","���μƻ�");
                }

				pc.put("expanded",iszk);
				pc.put("children",new ArrayList<Map<String, Object>>());
				pc.put("leaf",true);
				san.add(pc);//��ӵڶ������μƻ�
				
				List<Map<String, Object>> er=new ArrayList<Map<String, Object>>(); //�ڶ���
				Map<String, Object> zy=new HashMap<String, Object>();
				zy.put("Id",map.get("partId")+"0000001");
                if(locale.equals("en") || locale.equals("en_US")){
                    zy.put("Name", "Production Plan");
                }else {
                    zy.put("Name", "��ҵ�ƻ�");
                }
				zy.put("expanded",iszk);
				zy.put("children",san);
				zy.put("leaf",false);
				er.add(zy);//��ӵڶ��� ��ҵ�ƻ�
				
				Map<String, Object> map1=new HashMap<String, Object>();
				map1.put("Id",map.get("partId")+"");
				map1.put("Name",map.get("partName")+"");
				map1.put("expanded",iszk);
				map1.put("children",er);
				rs.add(map1);//��ӵ�һ�� ���
				
				prlist.add(map.get("partId").toString());
				
			}
		}
		Map<String, Object> rsmap=new HashMap<String, Object>();
		rsmap.put("Id", "0");
		rsmap.put("children", rs);
		return rsmap;
		
	}
	/**	
//	@Override
	public Map<String, Object> getAllJobPlanAndPartInfo(String nodeId,String partid,String planStatus,String startTime,String endTime){
		String sql="SELECT"
				+ " a.id as partId,"
				+ " a.name AS partName,"
				+ " b.ID AS jobPlanId,"
				+ " b.name AS jobPlanName,"
				+ " b.p_id AS pId,"
				+ " b.plan_type AS planType"
				+ " FROM t_part_type_info a LEFT JOIN t_jobplan_info b"
				+ " ON a.id=b.partId "
				+ " WHERE a.status<>'ɾ��' "
				+ " AND a.nodeid = '"+nodeId+"'";
		//��ӹ��˲�ѯ����
		if(partid!=null && !partid.isEmpty()){
//			sql += " and a.name='"+partName+"'";
			sql += " and a.id in ("+partid+")";
		}
		if(planStatus!=null && !planStatus.isEmpty()){
			sql += " and b.status in ("+planStatus+")";
		}
		if(startTime!=null && !startTime.isEmpty()){
			sql += " and b.plan_starttime >=DATE_FORMAT('"+startTime+"','%Y-%m-%d %T') ";
		}
		if(endTime != null && !endTime.isEmpty()){
			sql += " and b.plan_endtime <= DATE_FORMAT('"+endTime+"','%Y-%m-%d %T') ";
		}
		sql += " ORDER BY a.name,b.plan_type,b.name";
		List<Map<String, Object>> ds=dao.executeNativeQuery(sql);
		List prlist=new ArrayList();
		List<Map<String, Object>> rs=new ArrayList<Map<String, Object>>();
		
//		  �㷨1 ��ʽ����
//		 * ���
//		 * 	 ���μƻ�
//		 * 		���μƻ�1
//		 * 		���μƻ�2
//		 * 	��ҵ�ƻ�
//		 * 		��ҵ�ƻ�1
//		 * 		��ҵ�ƻ�2
		 
		for(Map<String, Object> map:ds){
			if(null!=map.get("partId")&&!prlist.contains(map.get("partId").toString())){
				if(null==map.get("planType")){//�ж��Ƿ�����ҵ�ƻ�
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("Id",map.get("partId")+"");
					map1.put("Name",map.get("partName")+"");
					map1.put("expanded",true);
					map1.put("children",new ArrayList());
					map1.put("leaf",true);
					rs.add(map1);//��ӵ�һ�� ���
					prlist.add(map.get("partId").toString());
					continue;
				}else if(Integer.parseInt(map.get("planType").toString())==1){//�ж��Ƿ�����ҵ����
					List<Map<String, Object>> san=new ArrayList();
					Map<String, Object> sanzy=new HashMap<String, Object>();
					sanzy.put("Id",map.get("jobPlanId")+"");
					sanzy.put("Name",map.get("jobPlanName")+"");
					sanzy.put("expanded",false);
					sanzy.put("leaf",true);
					san.add(sanzy);//��ӵ����� ��ҵ�ƻ�
					
					List<Map<String, Object>> er=new ArrayList(); //�ڶ���
					
					Map<String, Object> zy=new HashMap<String, Object>();
					zy.put("Id",map.get("jobPlanId")+"01");
					zy.put("Name","��ҵ�ƻ�");
					zy.put("expanded",false);
					zy.put("children",san);
					zy.put("leaf",false);
					er.add(zy);//��ӵڶ��� ��ҵ�ƻ�
					
					Map<String, Object> pc=new HashMap<String, Object>();
					pc.put("Id",map.get("jobPlanId")+"02");
					pc.put("Name","���μƻ�");
					pc.put("expanded",false);
					pc.put("children",new ArrayList());
					pc.put("leaf",true);
					er.add(pc);//��ӵڶ������μƻ�
					
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("Id",map.get("partId")+"");
					map1.put("Name",map.get("partName")+"");
					map1.put("expanded",true);
					map1.put("children",er);
					rs.add(map1);//��ӵ�һ�� ���
				}
				else{
					List<Map<String, Object>> san=new ArrayList();
					Map<String, Object> sanzy=new HashMap<String, Object>();
					sanzy.put("Id",map.get("jobPlanId")+"");
					sanzy.put("Name",map.get("jobPlanName")+"");
					sanzy.put("expanded",false);
					sanzy.put("leaf",true);
					san.add(sanzy);//��ӵ����� ���μƻ�
					
					List<Map<String, Object>> er=new ArrayList(); //�ڶ���
					
					
					
					Map<String, Object> zy=new HashMap<String, Object>();
					zy.put("Id",map.get("jobPlanId")+"01");
					zy.put("Name","��ҵ�ƻ�");
					zy.put("expanded",false);
					zy.put("children",new ArrayList());
					zy.put("leaf",true);
					er.add(zy);//��ӵڶ��� ��ҵ�ƻ�
					
					Map<String, Object> pc=new HashMap<String, Object>();
					pc.put("Id",map.get("jobPlanId")+"02");
					pc.put("Name","���μƻ�");
					pc.put("expanded",false);
					pc.put("children",san);
					pc.put("leaf",false);
					er.add(pc);//��ӵڶ������μƻ�
					
					
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("Id",map.get("partId")+"");
					map1.put("Name",map.get("partName")+"");
					map1.put("expanded",true);
					map1.put("children",er);
					rs.add(map1);//��ӵ�һ�� ���
				}
				prlist.add(map.get("partId").toString());
				
			}else{
				for(Map<String,Object> mm:rs){//���±�����һ��  ���
					if(mm.get("Id").toString().equals(map.get("partId").toString())){//�жϵ�ǰ����Ƿ�����������ͬ
						List<Map<String, Object>> two=(List<Map<String, Object>>)mm.get("children"); //��ȡ�ڶ��������  
						for(Map<String, Object> twoMap:two){//������������
							String twoMapName=twoMap.get("Name")+"";
							if(Integer.parseInt(map.get("planType").toString())==1&&twoMapName.equals("��ҵ�ƻ�")){//�ж϶�����������ҵ�ƻ�  ���ҵ�ǰѭ���ļƻ�������ҵ�ƻ�
								List<Map<String, Object>> three=(List<Map<String, Object>>)twoMap.get("children"); //��ȡ��ҵ�ƻ��ĵ���������
								Map<String, Object> threeMap=new HashMap<String, Object>();//�������ĵ����� ��ӽ�����
								threeMap.put("Id",map.get("jobPlanId")+"");
								threeMap.put("Name",map.get("jobPlanName")+"");
								threeMap.put("leaf",true);
								three.add(threeMap);
								twoMap.put("leaf", false);
								break;
							}else if(twoMapName.equals("���μƻ�")){
								List<Map<String, Object>> three=(List<Map<String, Object>>)twoMap.get("children"); //��ȡ���μƻ��ĵ���������
								Map<String, Object> threeMap=new HashMap<String, Object>();//�������ĵ����� ��ӽ�����
								threeMap.put("Id",map.get("jobPlanId")+"");
								threeMap.put("Name",map.get("jobPlanName")+"");
								threeMap.put("leaf",true);
								three.add(threeMap);
								twoMap.put("leaf", false);
								break;
							}
						}
					}
				}
			}
		}
		
//		 * �㷨2 ��ʽ����
//		 * ���
//		 * 	 ���μƻ�1
//		 * 		��ҵ�ƻ�1
//		 * 		��ҵ�ƻ�2
//		 *  ���μƻ�2
//		 *  	��ҵ�ƻ�1
//		 *  	��ҵ�ƻ�2
		
		for(Map<String, Object> map:ds){
			if(null!=map.get("partId")&&!prlist.contains(map.get("partId").toString())){
				if(null==map.get("planType")){//�ж��Ƿ�����ҵ�ƻ�
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("Id",map.get("partId")+"");
					map1.put("Name",map.get("partName")+"");
					map1.put("expanded",true);
					map1.put("children",new ArrayList());
					map1.put("leaf",true);
					rs.add(map1);//��ӵ�һ�� ���
					prlist.add(map.get("partId").toString());
					continue;
				}else if(Integer.parseInt(map.get("planType").toString())==1){//�ж��Ƿ�����ҵ����
					List<Map<String, Object>> nextlist=new ArrayList();
					Map<String, Object> map2=new HashMap<String, Object>();
					map2.put("Id",map.get("jobPlanId")+"");
					map2.put("Name",map.get("jobPlanName")+"");
					map2.put("expanded",false);
					map2.put("children",new ArrayList());
					map2.put("leaf",true);
					nextlist.add(map2);//��ӵڶ��� ��ҵ�ƻ�
					
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("Id",map.get("partId")+"");
					map1.put("Name",map.get("partName")+"");
					map1.put("expanded",true);
					map1.put("children",nextlist);
					rs.add(map1);//��ӵ�һ�� ���
				}
				else{
					List<Map<String, Object>> nextlist2=new ArrayList();
					Map<String, Object> map3=new HashMap<String, Object>();
					map3.put("Id",map.get("jobPlanId")+"");
					map3.put("Name",map.get("jobPlanName")+"");
					map3.put("leaf",true);
					nextlist2.add(map3);//��ӵ����� ���μƻ�
					
					List<Map<String, Object>> nextlist=new ArrayList();
					Map<String, Object> map2=new HashMap<String, Object>();
					map2.put("Id",null);
					map2.put("Name",null);
					map2.put("expanded",false);
					map2.put("children",nextlist2);
					nextlist.add(map2);//��ӵڶ��� ��ҵ�ƻ�
					
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("Id",map.get("partId")+"");
					map1.put("Name",map.get("partName")+"");
					map1.put("expanded",true);
					map1.put("children",nextlist);
					rs.add(map1); //��ӵ�һ�� ���
				}
				prlist.add(map.get("partId").toString());
				
			}else{
				for(Map<String,Object> mm:rs){
					if(mm.get("Id").toString().equals(map.get("partId").toString())){
						if(Integer.parseInt(map.get("planType").toString())==1){//�ж��ǽ�������ӽ��ڶ���
							List<Map<String, Object>> two=(List<Map<String, Object>>)mm.get("children"); //��ȡ�ڶ��������  
							Map<String, Object> yytwoMap=two.get(0);
							if(yytwoMap.get("Name")==null&&yytwoMap.get("Id")==null){//�ж�����ǵڶ���û�����ĵ����� 
								yytwoMap.put("Id",map.get("jobPlanId")+"");
								yytwoMap.put("Name",map.get("jobPlanName")+"");
								yytwoMap.put("expanded",false);
								yytwoMap.put("children",new ArrayList());
								yytwoMap.put("leaf",true);
								break;
							}else{
								Map<String, Object> twoMap=new HashMap<String, Object>();//�������ĵڶ��� ��ӽ�����
								twoMap.put("Id",map.get("jobPlanId")+"");
								twoMap.put("Name",map.get("jobPlanName")+"");
								twoMap.put("expanded",false);
								twoMap.put("children",new ArrayList());
								twoMap.put("leaf",true);
								two.add(twoMap);
								break;
							}
						}
						else{
							List<Map<String, Object>> two=(List<Map<String, Object>>)mm.get("children"); //��ȡ�ڶ��������  
							for(Map<String, Object> yytwoMap:two){//�����ڶ��������
								if(yytwoMap.get("Id").toString().equals(map.get("pId").toString())){//����ڶ����id �뵱ǰpid������� ˵������ �����������
									if(null==yytwoMap.get("children")||"".equals(yytwoMap.get("children")+"")||
											((List<Map<String, Object>>)yytwoMap.get("children")).size()<1){
										List<Map<String, Object>> three=new ArrayList<Map<String,Object>>();
										Map<String, Object> threeMap=new HashMap<String, Object>();//�������ĵ����� ��ӽ�����
										threeMap.put("Id",map.get("jobPlanId")+"");
										threeMap.put("Name",map.get("jobPlanName")+"");
										threeMap.put("leaf",true);
										three.add(threeMap);
										yytwoMap.put("children", three);
										yytwoMap.put("leaf",false);
										break;
									}else{
										List<Map<String, Object>> three=(List<Map<String, Object>>)yytwoMap.get("children");
										Map<String, Object> threeMap=new HashMap<String, Object>();//�������ĵ����� ��ӽ�����
										threeMap.put("Id",map.get("jobPlanId")+"");
										threeMap.put("Name",map.get("jobPlanName")+"");
										threeMap.put("leaf",true);
										three.add(threeMap);
										yytwoMap.put("children", three);
										yytwoMap.put("leaf",false);
										break;
									}
									
								}
							}
							
						}
					}
				}
			}
		}
		
		Map<String, Object> rsmap=new HashMap<String, Object>();
		rsmap.put("Id", "0");
		rsmap.put("children", rs);
		return rsmap;
		
	}*/
	//��ѯ������ҵ�ƻ�
	@Override
    @SuppressWarnings("unchecked")
	public List<Map<String, Object>> findAllJobPlan(String nodeId,String partid,String planStatus,String startTime,String endTime) {
		List<Map<String, Object>> result=new ArrayList<Map<String, Object>>();
		String hql = "SELECT NEW MAP(a.id AS Id,a.no AS No," 
				+"a.name AS Name," 
				+"a.status AS Status,"
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS StartDate,"//�ƻ���ʼʱ��
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as EndDate ,"//�ƻ�����ʱ��
				+" DATE_FORMAT(a.realStarttime,'%Y-%m-%d %T') as RealStarttime ,"//ʵ�ʿ�ʼʱ��
				+" DATE_FORMAT(a.realEndtime,'%Y-%m-%d %T') as RealEndtime,"//ʵ�ʽ���ʱ��
				+" tsi.name as statusName, "
				+" a.id as ResourceId, "
				+" a.planNo as planId," 
				+" a.qualifiedNum as qualifiedNum,"
				+" a.planNum as planNum,a.finishNum as finishNum,a.finishDate as finishDate,a.priority as Priority,a.planType as planType,"
				+" part.name as partName,part.id as partId) "
				+" FROM TJobplanInfo a, "
				+" TStatusInfo tsi,"
				+" TPartTypeInfo part"
				+" WHERE 1=1 "
				+" and a.TPartTypeInfo.id = part.id"
				+ " and tsi.id = a.status";
				if(!StringUtils.isEmpty(nodeId))
				{
					hql=hql+" AND a.nodeid='"+nodeId+"' ";
				}
				if(!StringUtils.isEmpty(partid)){
//					hql+=" and part.name='"+partName+"'";
					hql+=" and part.id in ("+partid+")";
				}
				if(!StringUtils.isEmpty(planStatus)){
					hql+=" and a.status in ("+planStatus+")";
				}
				if(!StringUtils.isEmpty(startTime)){
					hql+=" and a.planStarttime>=DATE_FORMAT('"+startTime+"','%Y-%m-%d %T')";
				}
				if(!StringUtils.isEmpty(endTime)){
					hql+=" and a.planEndtime<=DATE_FORMAT('"+endTime+"','%Y-%m-%d %T')";
				}
				hql=hql+" order by a.id desc";		
						
	    List<Map<String, Object>> rs = dao.executeQuery(hql);
		if(null!=rs&&rs.size()>0){
			for(Map<String, Object> tt:rs){
				Map<String, Object> mm=new HashMap<String, Object>();
				//����  ���ɹ� ���ɹ� 
				if("10".equals(tt.get("Status").toString())||"20".equals(tt.get("Status").toString())||"30".equals(tt.get("Status").toString())){
					mm.put("Id",tt.get("Id"));
					mm.put("Name",tt.get("Name"));
					mm.put("Status",tt.get("Status"));
					mm.put("StartDate",tt.get("StartDate"));
					mm.put("EndDate",tt.get("EndDate"));
					if(null!=tt.get("planType")&&tt.get("planType").toString().equals("1")){
						mm.put("ResourceId",tt.get("partId")+"0000001");
					}else{
						mm.put("ResourceId",tt.get("partId")+"0000002");
					}
					mm.put("planNum",tt.get("planNum"));
					mm.put("finishNum",tt.get("finishNum"));
					mm.put("Percentage",0);
					mm.put("No",tt.get("No"));
					mm.put("planId",tt.get("planId"));
					mm.put("Priority",tt.get("Priority"));
					mm.put("partName", tt.get("partName"));
					mm.put("planType", tt.get("planType"));
					mm.put("statusName", tt.get("statusName"));
					mm.put("partId", tt.get("partId"));
				//����  �ӹ�	
				}else if("40".equals(tt.get("Status").toString())||"50".equals(tt.get("Status").toString())){
					mm.put("Id",tt.get("Id"));
					mm.put("Name",tt.get("Name"));
					mm.put("Status",tt.get("Status"));
					mm.put("StartDate",tt.get("RealStarttime"));
					mm.put("EndDate",tt.get("EndDate"));
					//System.out.println(tt.get("planType"));
					if(null!=tt.get("planType")&&tt.get("planType").toString().equals("1")){
						mm.put("ResourceId",tt.get("partId")+"0000001");
					}else{
						mm.put("ResourceId",tt.get("partId")+"0000002");
					}
					mm.put("planNum",tt.get("planNum"));
					mm.put("finishNum",tt.get("finishNum"));
					double rr=Double.parseDouble(tt.get("finishNum").toString())/Double.parseDouble(tt.get("planNum").toString())*100;
					DecimalFormat df=new DecimalFormat("0.00"); 
					mm.put("Percentage",df.format(rr));
					mm.put("No",tt.get("No"));
					mm.put("planId",tt.get("planId"));
					mm.put("Priority",tt.get("Priority"));
					mm.put("partName", tt.get("partName"));
					mm.put("planType", tt.get("planType"));
					mm.put("statusName", tt.get("statusName"));
					mm.put("partId", tt.get("partId"));
				//����  �깤
				}else{
					mm.put("Id",tt.get("Id"));
					mm.put("Name",tt.get("Name"));
					mm.put("Status",tt.get("Status"));
					mm.put("StartDate",tt.get("RealStarttime"));
					mm.put("EndDate",tt.get("RealEndtime"));
					if(null!=tt.get("planType")&&tt.get("planType").toString().equals("1")){
						mm.put("ResourceId",tt.get("partId")+"0000001");
					}else{
						mm.put("ResourceId",tt.get("partId")+"0000002");
					}
					mm.put("planNum",tt.get("planNum"));
					mm.put("finishNum",tt.get("finishNum"));
					mm.put("Percentage",0);
					mm.put("No",tt.get("No"));
					mm.put("planId",tt.get("planId"));
					mm.put("Priority",tt.get("Priority"));
					mm.put("partName", tt.get("partName"));
					mm.put("planType", tt.get("planType"));
					mm.put("statusName", tt.get("statusName"));
					mm.put("partId", tt.get("partId"));
				}
				result.add(mm);
			}
		}
		return result;
    }

	/**
	 * ���ݲ�Ʒid����ѯ��ҵ�ƻ���Ϣ
	 */
    @SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findJobPlanDetail(String jobNo) {
		String hql="select new MAP(jobPlan.id as jobPlanId,jobPlan.no as No," +
				"jobPlan.name as jobPlanName," +
				"jobPlan.planNum as jobPlanNum," +
				"DATE_FORMAT(jobPlan.planStarttime,'%Y-%m-%d') as starttime," +
				"DATE_FORMAT(jobPlan.planEndtime,'%Y-%m-%d') as endtime," +
				"jobPlan.priority as priority," +
				"jobPlan.process as process," +
				"jobPlan.planNo as planId," +
				"jobPlan.status as status,"+
				"jobPlan.planType as planType,"+
				"jobPlan.qualifiedNum as qualifiedNum,"+
				"jobPlan.TPartTypeInfo.id as partId," +	
				"DATE_FORMAT(jobPlan.finishDate,'%Y-%m-%d') as finishDate,jobPlan.finishNum as jobPlanfinisNum,jobPlan.TPartTypeInfo.no as partName)" +
				" from TJobplanInfo jobPlan where jobPlan.name='"+jobNo+"'";
		List<Map<String,Object>> tempList=dao.executeQuery(hql);
		if(tempList!=null&&tempList.size()>0) {
			return tempList.get(0);
		}
		return null;
	}
	
	/**
	 * ��ҵ�ƻ�����Ĭ��ȡһ����¼
	 */
    @SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findJobPlanDetailDefault() {
		String hql="select new MAP(jobPlan.id as jobPlanId,jobPlan.no as No," +
				"jobPlan.name as jobPlanName," +
				"jobPlan.planNum as jobPlanNum," +
				"DATE_FORMAT(jobPlan.planStarttime,'%Y-%m-%d') as starttime," +
				"DATE_FORMAT(jobPlan.planEndtime,'%Y-%m-%d') as endtime," +
				"jobPlan.priority as priority," +
				"jobPlan.process as process," +
				"jobPlan.planNo as planId," +
				"jobPlan.TPartTypeInfo.id as partId," +	
				"DATE_FORMAT(jobPlan.finishDate,'%Y-%m-%d') as finishDate,jobPlan.finishNum as jobPlanfinisNum) " +
				" from TJobplanInfo jobPlan order by id desc";
		List<Map<String,Object>> tempList=dao.executeQuery(hql);
		if(tempList!=null&&tempList.size()>0) {
			return tempList.get(0);
		}
		return null;
	}
	

	@Override
	public void addJobPlanInfo(TJobplanInfo jobPlan) {
		dao.save(jobPlan);
	}

	@Override
	public void updateJobPlanInfo(TJobplanInfo jobPlan) {
		dao.update(jobPlan);
	}

	@Override
	public void deleteJobPlanInfoById(TJobplanInfo jobPlan) {
		dao.delete(jobPlan);
	}

	@Override
	/**
	 * ��ȡ��ҵ�ƻ����е�ǰ���ID
	 */
	public String getMaxJobPlanInfoId()
	{
		String hql="select MAX(id) from TJobplanInfo" ;
        return dao.executeQuery(hql).get(0).toString();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ����id����ѯ��ҵ�ƻ���Ϣ
	 */
	public TJobplanInfo geTJobplanInfoById(Long jobPlanId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = " FROM TJobplanInfo WHERE id="+jobPlanId;
		List<TJobplanInfo> tempList = dao.executeQuery(hql, parameters);
		if(tempList.size() > 0){
			return tempList.get(0);
		}
		return null;
}
	
	@SuppressWarnings("unchecked")
	public TJobplanInfo geTJobplanInfoById1(Long jobPlanId) {
		//��֤�Ƿ��й�������������һ����������������ҵ�ƻ������μƻ�������ɾ��
		String sql=" select jobplan.id from t_jobplan_info jobplan,t_jobdispatchlist_info dispatch " +
				" where jobplan.ID=dispatch.jobplanID " +
				" and (jobplan.ID="+jobPlanId + " or jobplan.p_id="+jobPlanId +")" ;
		List<Map<String,Object>> temp= dao.executeNativeQuery(sql);	
		if(temp!=null&&temp.size()>0) return null;
		
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = " FROM TJobplanInfo WHERE id="+jobPlanId;
		List<TJobplanInfo> tempList = dao.executeQuery(hql, parameters);
		if(tempList.size() > 0){
			return tempList.get(0);
		}
		return null;
}
		
	private ICommonDao commonDao;
	private ICommonService commonService;
	
	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * ��ȡ��ת���༭ҳ��ļƻ������������Ϣ
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPlanAndPartList(String jobplabid){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //��ҵ�ƻ�
				+" a.name AS name," 
				+" a.no AS no," 
				+" a.status AS status,"
				+ "s.name as statusName,"
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "
				+" DATE_FORMAT(a.finishDate,'%Y-%m-%d %T') as finishDate, "
				+" a.planNum as planNum,"
				+" a.priority as priority,"
				+" a.finishNum as finishNum,"
				+" a.childrenTotalNum as childrenTotalNum,"        
				+" a.planNo as planNo,"      //�����ƻ����
				+" a.planType as planType,"      
				+" a.TJobplanInfo.id as pid,"  
				+" b.id as bid,"             //���
				+" b.no as bno,"
				+" b.name as bname,"
//				+" b.type as type,"        //���������ݿ�
				+" b.drawingno as drawingno,"
				+" b.version as version,"
				+" b.source as source"
				+")"
				+" FROM TJobplanInfo a , TPartTypeInfo b,TStatusInfo s "
				+" WHERE a.TPartTypeInfo.id=b.id   "
				+" and s.id=a.status";
		if(jobplabid!=null && !"".equals(jobplabid)){
			hql += " AND a.id = '"+jobplabid+"'  "; 
		}		
		return dao.executeQuery(hql);	
	}
	
	/**
	 * ��ҵ�ƻ�ҳ��������ƻ������б�
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPlanAndForList(String nodeid){
		String hql = "SELECT NEW MAP(" 
		+" c.id as cid,"            //�����ƻ�
		+" c.uplanNo as uplanNo,"  
		+" c.uplanType as uplanType,"
		+" c.uplanNum as uplanNum,"
		+" c.uplanStatus as uplanStatus,"
		+" c.uplanName AS uplanName" //�����ƻ�����
		+")"
		+" FROM TUserProdctionPlan c ";
		if(nodeid!=null && !"".equals(nodeid)){
		hql +=	" WHERE c.TNodes.nodeId='"+nodeid+"' ";  
		}
		return dao.executeQuery(hql);	
	}
	
	/**
	 * ��ҵ�ƻ�ҳ�����������б�
	 */
	public List<Map<String,Object>> getPartAndForList(String nodeid){
		        String sql="select "
		        		+ " b.id as bid,"
		        		+ " b.no as bno,"
		        		+ " b.name as bname,"
		        		+ " b.drawingno as drawingno,"
		        		+ " b.version as version,"
		        		+ " b.source as source "
		        		+ " from t_part_type_info b where nodeid='"+nodeid+"'"
		        		+ " order by b.name";
		        return dao.executeNativeQuery(sql);
			}

	@SuppressWarnings("unchecked")
	public List<String> getTJobplanInfoNo(String no){
		List<String> lst1  = new ArrayList<String>();
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "select new Map(tt.no as no)"
				+ " from TJobplanInfo tt ";
				if(no!=null&&!"".equals(no.trim())){
					no = no.trim();
		        hql += " where tt.no like '%"+no+"%'  ";
				}
		 List<Map<String, Object>> lst = dao.executeQuery(hql, parameters);
		 HashSet<String> s = new HashSet<String>();
		 for(Map<String, Object> map : lst){
			 String mtclass = (String)map.get("no");
			 s.add(mtclass);
		 }
		 for(Object obj :s){
			 if(obj!=null&&!"".equals(obj)){
			 lst1.add((String)obj);
			 }
		 }
		return lst1;
	}
	
	/**
	 * ��ȡ���ȼ�
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPriority(){
		String hql = "SELECT distinct NEW MAP(a.priority as displayfield)"
				+" FROM TJobplanInfo a ";
		return dao.executeQuery(hql);	
	}
	/**
	 * ��ҵ�ƻ�����
	 */
	public String addJobPlan(JobPlanAddBean jobPlanAddBean){
	  try {
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			String nodeid = (String)session.getAttribute("nodeid");		
			
		    TJobplanInfo jpi = new TJobplanInfo(); //��������һ�������в���

		    String proPlanNo = jobPlanAddBean.getProPlanNo();//�����ƻ����
			
			String partTypeId=jobPlanAddBean.getPartTypeId();
			TPartTypeInfo tPartTypeInfo=null;
			if(!StringUtils.isEmpty(partTypeId)){
				tPartTypeInfo=commonService.get(TPartTypeInfo.class, Long.valueOf(partTypeId));
			}
			System.out.println("jobPlanAddBean.getNo().length():"+jobPlanAddBean.getNo().length());
			jpi.setNo(jobPlanAddBean.getNo());
			//�������ҵ�ƻ�,Ĭ��״̬Ϊ40
			if("1".equals(jobPlanAddBean.getPlanType()))
				{ 
				   jpi.setStatus(40);
				   jpi.setRealStarttime(jobPlanAddBean.getPlanStarttime()); //�ƻ���ʼʱ�����ʵ�ʿ�ʼʱ��
				}
			else 
				jpi.setStatus(10);  //���μƻ�������Ĭ����10
//			jpi.setTUserProdctionPlan(tUserProdctionPlan);     //���������ƻ����ơ�
			jpi.setPlanNo(proPlanNo);     //�����ƻ����
			jpi.setName(jobPlanAddBean.getName());
			jpi.setPlanStarttime(jobPlanAddBean.getPlanStarttime());
			jpi.setPlanEndtime(jobPlanAddBean.getPlanEndtime());
			jpi.setFinishDate(jobPlanAddBean.getSubmitTime());
			if(!StringUtils.isEmpty(jobPlanAddBean.getPlanNum())){
			   jpi.setPlanNum(Integer.parseInt(jobPlanAddBean.getPlanNum()));
			  }
			jpi.setFinishNum(0);
			jpi.setQualifiedNum(0);
			jpi.setTPartTypeInfo(tPartTypeInfo);     //�������
			jpi.setProcess((double) 0);
			jpi.setChildrenTotalNum(0);
			jpi.setAllocatedNum(Integer.parseInt(jobPlanAddBean.getAllocatedNum()));
			if(!StringUtils.isEmpty(jobPlanAddBean.getPriority())){
			jpi.setPriority(Integer.parseInt(jobPlanAddBean.getPriority()));
			}
			jpi.setNodeid(nodeid);   //�ڵ�ID
			
			jpi.setPlanType(Integer.parseInt(jobPlanAddBean.getPlanType()));//�ƻ�����
			jpi.setCreateDate(new Date());//����ʱ��
			if(null!=jobPlanAddBean.getpJobPlanId()&&!"".equals(jobPlanAddBean.getpJobPlanId())){//����ҵ�ƻ�
				List<TJobplanInfo> pjob=this.getTJobplanInfoById(jobPlanAddBean.getpJobPlanId());
				if(null!=pjob&&pjob.size()>0){
					jpi.setTJobplanInfo(pjob.get(0));
				}
			}
			jpi.setScrapNum(0);
			commonService.saveObject(jpi);
			if(jpi.getPlanType()==2){
				TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);//��ȡ�û�
				List<Map<String,Object>> task=jobPlanAddBean.getRealaddTask();//��ȡ��������
				for(Map<String,Object> map:task){
					TJobplanTaskInfo taskinfo=new TJobplanTaskInfo();
					taskinfo.setJobPlanId(jpi.getId());
					taskinfo.setJobPlanNo(jpi.getNo());
					taskinfo.setTaskNO(map.get("addTaskNo").toString());
					taskinfo.setTaskNum(Integer.parseInt(map.get("addTaskNum").toString()));
					taskinfo.setOperator(user.getNickName());
					taskinfo.setOperatorTime(new Date());
					taskinfo.setReportNum(Integer.parseInt(map.get("reportNum").toString()));
					commonService.saveObject(taskinfo); //��ӷ�������
				}
			}
			Locale locale=SessionHelper.getCurrentLocale(session);
			if(locale.toString().equals("en") || locale.toString().equals("en_US")){
				  return "Created successfully";
			}else{
			      return "�����ɹ�";
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			Locale locale=SessionHelper.getCurrentLocale(session);
			if(locale.toString().equals("en") || locale.toString().equals("en_US")){
				return "Failed to add";
			}else{
				return "���ʧ��";
			}
			
		}		
	}

	public List<Map<String,Object>> getTJobplanTaskInfo(String jopPlanId){
		String sql="select "
				+ " a.id as id,"
				+ " a.id as realId,"
				+ " a.jobPlanId as jobPlanId,"
				+ " a.taskNO as addTaskNo,"
				+ " a.taskNum as addTaskNum,"
				+ " a.reportNum as reportNum "
				+ " from t_jobplan_task_info a "
				+ " where a.jobPlanId='"+jopPlanId+"'";
		List<Map<String,Object>> rs=dao.executeNativeQuery(sql);
		for(Map<String,Object> mm:rs){
			String sql2="select status from T_JOBDISPATCHLIST_INFO where jobplanID='"+mm.get("jobPlanId")+"' and (status=40 or status=50)";
			List<Map<String, Object>> list=dao.executeNativeQuery(sql2);
			if(null!=list&&list.size()>0){
				mm.put("edit", null);
			}else{
				mm.put("edit","test");
			}
			if(Integer.parseInt(mm.get("reportNum").toString())>0){
				mm.put("xs",null);
			}else{
				mm.put("xs", "test");
			}
		}
		return rs;
	}

	@SuppressWarnings("unchecked")
	private List<TJobplanTaskInfo> getTJobplanTaskInfoObject(String jopPlanId){
		String hql=" from TJobplanTaskInfo a"
				+ " where a.jobPlanId='"+jopPlanId+"'";
		return  dao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPlanByName(String name){ 
		
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //��ҵ�ƻ�
				+" a.no AS no" 
				+" )"
				+" FROM TJobplanInfo a "
				+" WHERE a.no = '"+name+"'  ";
		return dao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPartTypeByIdFor(String partTypeId){
		
		String hql = "SELECT NEW MAP(" 
				+" b.id as bid,"
				+" b.no as bno,"
				+" b.name as bname,"
//				+" b.type as type,"        //���������ݿ�
				+" b.drawingno as drawingno,"
				+" b.version as version,"
				+" b.source as source"
				+" )"
				+" FROM TPartTypeInfo b "
				+" WHERE b.id = '"+partTypeId+"'  ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * ��ҵ�ƻ������ID��Ϊ��ҵ�ƻ�ƴװ
	 */
	@SuppressWarnings("unchecked")
	public String getMaxJobPlanId(){
		String hql = "SELECT MAX(b.id) "
				+" FROM TJobplanInfo b ";
		 List<Long> lst = dao.executeQuery(hql);
		 if(lst.size()>0&&lst.get(0)!=null)
		 {
			 String maxId  = lst.get(0).toString();
			 maxId = String.valueOf(Integer.parseInt(maxId) +1);
			 return maxId;
		 }else 
			 return "1";
	}
	
	
	/**
	 * ��ҵ�ƻ��޸�
	 */
	public String updataJobPlan(JobPlanUpdataBean jobPlanAddBean){
		try {
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			 TJobplanInfo jpi = null;
				if(!StringUtils.isEmpty(jobPlanAddBean.getId())){
					jpi=commonService.get(TJobplanInfo.class, Long.valueOf(jobPlanAddBean.getId()));
				}
				String proPlanNo = jobPlanAddBean.getProPlanNo(); //�����ƻ����
				
				String partTypeId=jobPlanAddBean.getPartTypeId();
				TPartTypeInfo tPartTypeInfo=null;
				if(!StringUtils.isEmpty(partTypeId)){
					tPartTypeInfo=commonService.get(TPartTypeInfo.class, Long.valueOf(partTypeId));
				}

				jpi.setNo(jobPlanAddBean.getNo());
//				jpi.setStatus(10);  //����10״̬�²����޸�
				jpi.setPlanNo(proPlanNo);   //�����ƻ����
				jpi.setName(jobPlanAddBean.getName());
				jpi.setPlanStarttime(jobPlanAddBean.getPlanStarttime());
				jpi.setPlanEndtime(jobPlanAddBean.getPlanEndtime());
				jpi.setFinishDate(jobPlanAddBean.getSubmitTime());
				if(!StringUtils.isEmpty(jobPlanAddBean.getPlanNum())){
					jpi.setPlanNum(Integer.parseInt(jobPlanAddBean.getPlanNum()));
				}
				
				jpi.setTPartTypeInfo(tPartTypeInfo);     //�������
				
				if(!StringUtils.isEmpty(jobPlanAddBean.getPriority())){
					jpi.setPriority(Integer.parseInt(jobPlanAddBean.getPriority()));
				}
				
				jpi.setPlanType(Integer.parseInt(jobPlanAddBean.getPlanType()));//�ƻ�����
				if(null!=jobPlanAddBean.getpJobPlanId()&&!"".equals(jobPlanAddBean.getpJobPlanId())){//����ҵ�ƻ�
					List<TJobplanInfo> pjob=this.getTJobplanInfoById(jobPlanAddBean.getpJobPlanId());
					if(null!=pjob&&pjob.size()>0){
						jpi.setTJobplanInfo(pjob.get(0));
					}else{
						jpi.setTJobplanInfo(null);
					}
				}else{
					jpi.setTJobplanInfo(null);
				}
				commonService.updateObject(jpi);
				if(jpi.getPlanType()==2){
					List<TJobplanTaskInfo> ttilist=getTJobplanTaskInfoObject(jpi.getId().toString());//������ҵid ��ȡ��ҵ�����������
					TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);//��ȡ�û�
					List<String> delList=new ArrayList<String>();
					for(Map<String,Object> mm:jobPlanAddBean.getRealaddTask()){
						if(!jobPlanAddBean.getAddTask().contains(mm)){
							if(null!=mm.get("realId")){
								delList.add(mm.get("realId").toString());//�ж���Ҫɾ��������
							}
						}
					}
					for(Map<String,Object> mm:jobPlanAddBean.getRealaddTask()){
						if(null==mm.get("realId")){//realId Ϊ��˵���÷�������������
							TJobplanTaskInfo taskinfo=new TJobplanTaskInfo();
							taskinfo.setJobPlanId(jpi.getId());
							taskinfo.setJobPlanNo(jpi.getNo());
							taskinfo.setTaskNO(mm.get("addTaskNo").toString());
							taskinfo.setTaskNum(Integer.parseInt(mm.get("addTaskNum").toString()));
							taskinfo.setOperator(user.getNickName());
							taskinfo.setOperatorTime(new Date());
							taskinfo.setReportNum(Integer.parseInt(mm.get("reportNum").toString()));
							commonService.saveObject(taskinfo); //��ӷ�������
							continue;
						}
						
						for(TJobplanTaskInfo tti:ttilist){
							if(mm.get("realId").toString().equals(tti.getId().toString())){ //��Ϊ��  �Ļ� ƥ�����
								if(delList.contains(tti.getId().toString())){
									commonService.delete(tti);
									continue;
								}
								if(mm.get("addTaskNo").toString().equals(tti.getTaskNO())
										&&mm.get("addTaskNum").toString().equals(tti.getTaskNum().toString())){//���û���޸� �򲻲������ݽ�����һ�β���
									break;
								}else{
									tti.setTaskNO(mm.get("addTaskNo").toString());
									tti.setTaskNum(Integer.parseInt(mm.get("addTaskNum").toString()));
									commonService.update(tti);
									break;
								}
							}
						}
						
					}
				}
		    Locale locale=SessionHelper.getCurrentLocale(session);
		    if(locale.toString().equals("en") || locale.toString().equals("en_US")){
				  return "Update successfully";
			}else{
			      return "�޸ĳɹ�!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			Locale locale=SessionHelper.getCurrentLocale(session);
		    if(locale.toString().equals("en") || locale.toString().equals("en_US")){
				  return "failed to Update";
			}else{
				return "�޸�ʧ��";
			}
			
		}
	   
		
	}
	
	/**
	 * ͨ����ҵ�ƻ��õ������嵥
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProcessByJobPlanId(String jobplabid){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //��ҵ�ƻ�
				+" c.id as cid,"            //����
				+" c.no as no,"  
				+" c.name as name,"
				+" c.theoryWorktime as theoryWorktime,"
				+" c.processDuration as processDuration,"
				+" c.TEquipmenttypeInfo.equipmentType as equipmentType,"
				+" c.TProcessplanInfo.id as pid,"     //����ID
				+" c.TProcessplanInfo.name as pname,"  //��������
				+" b.id as bid "             //��ҵ
				+")"
				+" FROM TJobplanInfo a , TJobInfo b ,TProcessInfo c"
				+" WHERE a.id=b.TJobplanInfo.id AND b.TProcessInfo.id=c.id  ";
		if(jobplabid!=null && !"".equals(jobplabid)){
			hql += " AND a.id = '"+jobplabid+"'  "; 
		}		
		return dao.executeQuery(hql);	
	}
	
	/**
	 * ����ҳ��--ͨ�����ID�õ���ҵ�ƻ�ID����
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobPlanMapByPart(String nodeid,String partId){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //��ҵ�ƻ�
				+" a.no AS no,"            //��ҵ�ƻ����===>
				+" a.name AS name," 
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //yao
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //yao
				+" a.planNum as planNum,"                                          //yao
				+" a.priority as priority,"
				+" a.TPartTypeInfo.id as partid,"    //���ID
				+" a.TPartTypeInfo.name as partname"
				+")"
				+" FROM TJobplanInfo a  WHERE  a.nodeid = '"+nodeid+"'  ";
		if(partId!=null && !"".equals(partId)){
			hql += " AND a.TPartTypeInfo.id = '"+partId+"'  "; 
		}	
		List<Map<String,Object>> lst = dao.executeQuery(hql);		
		return lst;		
	}
	/**
	 * ����ҳ��--ͨ�����ID�õ����շ���ID����
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProcessPlanMapByPart(String nodeid,String partId){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //��ҵ�ƻ�
				+" a.no AS no,"            //��ҵ�ƻ����===>
				+" a.name AS name," 
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //yao
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //yao
				+" a.planNum as planNum,"                                          //yao
				+" a.priority as priority,"
				+" a.TPartTypeInfo.id as partid,"    //���ID
				+" a.TPartTypeInfo.name as partname,"
				+" b.id as pid,"     //���շ���ID
				+" b.name as pname"  //���շ�������
				+")"
				+" FROM TJobplanInfo a ,  TProcessplanInfo b"
				+" WHERE a.TPartTypeInfo.id=b.TPartTypeInfo.id  AND a.nodeid = '"+nodeid+"'   ";
		if(partId!=null && !"".equals(partId)){
			hql += " AND a.TPartTypeInfo.id = '"+partId+"'  "; 
		}	
		List<Map<String,Object>> lst = dao.executeQuery(hql);		
		return lst;	
	}
	
	
	/**
	 * ͨ����ҵ�ƻ�ID�����շ���ID�����ID�õ������嵥
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProcessPlanById(String nodeid,String jobplanid,String processPlanId,String partId){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //��ҵ�ƻ�
				+" a.no AS no,"            //��ҵ�ƻ����===>
				+" a.name AS name," 
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //yao
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //yao
				+" a.planNum as planNum,"                                          //yao
				+" a.priority as priority,"
				+" a.TPartTypeInfo.id as partid,"    //���ID
				+" a.TPartTypeInfo.name as partname,"
				+" b.id as bid "             //���շ���ID
				+")"
				+" FROM TJobplanInfo a ,  TProcessplanInfo b"
				+" WHERE a.TPartTypeInfo.id=b.TPartTypeInfo.id";
		if(nodeid!=null && !"".equals(nodeid)){
			hql += " AND a.nodeid = '"+nodeid+"'  "; 
		}
		if(jobplanid!=null && !"".equals(jobplanid)){
			hql += " AND a.id = '"+jobplanid+"'  "; 
		}	
		if(partId!=null && !"".equals(partId)){
			hql += " AND a.TPartTypeInfo.id = '"+partId+"'  "; 
		}	
		List<Map<String,Object>> lst = dao.executeQuery(hql);
		for(Map<String,Object> map :lst){
			String no = map.get("cno").toString();
			String cid = map.get("cid").toString();
			String nocid = no +"@#" + cid;
			map.put("nocid", nocid);
			map.put("bool", true);  //Ϊҳ�渴ѡ��Ϊѡ��
		}		
		return lst;
		
	}
	
	/**
	 * ͨ����ҵ�ƻ�ID�����շ���ID�����ID�õ������嵥,--��Ҫ������ҵ�ƻ�������1�Զ�,�������շ�����ťʱ
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProcessPlanById1(String jobplanid,String processPlanId,String partId){
		String hql = "SELECT NEW MAP(" 
				+" c.id as cid,"            //���� ===>
				+" c.no as cno,"  
				+" c.name as cname,"
				+" c.theoryWorktime as theoryWorktime,"   //����ʱ��
				+" c.processDuration as processDuration," //��������
				+" c.TEquipmenttypeInfo.equipmentType as equipmentType," //�豸����
				+" c.TProcessplanInfo.id as pid,"     //���շ���ID
				+" c.TProcessplanInfo.name as pname"  //���շ�������
				+")"
				+" FROM TProcessInfo c"
				+" WHERE c.status = 0  ";
		if(processPlanId!=null && !"".equals(processPlanId)){
			hql += " AND c.TProcessplanInfo.id = '"+processPlanId+"'  "; 
		}	
		List<Map<String,Object>> lst = dao.executeQuery(hql);
		for(Map<String,Object> map :lst){
			String no = map.get("cno").toString();
			String cid = map.get("cid").toString();
			String nocid = no +"@#" + cid;
			map.put("nocid", nocid);
			map.put("bool", true);  //Ϊҳ�渴ѡ��Ϊѡ��
		}		
		return lst;
		
	}
	
	/**
	 * ͨ����ҵ�ƻ��õ���ҵ�б� --��ҵ�ƻ�����ҳ��
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobByJobPlanId(String jobplanId){
		String hql = "SELECT NEW MAP(" 
				+" a.no AS jobNo,"                       //��ҵID����ʵ����ҵno
				+" a.TProcessInfo.id AS processId,"     //����ID
				+" a.name AS joName"                          //��ҵ����
				+")"
				+" FROM TJobInfo a "
				+" WHERE a.TJobplanInfo.id = '"+jobplanId+"'  ";
		return dao.executeQuery(hql);	
	}
	

	/**
	 * ��ҵ�ƻ�����-- ͨ����ҵ�ƻ�ID�õ����ID
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPartIdByJobPlanId(String jobplanId){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"                       //��ҵID����ʵ����ҵno
				+" a.TPartTypeInfo.id AS partId"         //���ID
				+")"
				+" FROM TJobplanInfo a "
				+" WHERE a.id = '"+jobplanId+"'  ";
		return dao.executeQuery(hql);	
	}	
	

	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPlanListByIdAndTime(String jobplabid,Date startTime,Date endTime){
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		String startTime1 = null;
		if(startTime != null && !startTime.toString().equals("")){
		startTime1 =  sdf.format(startTime);
		}
		String endTime1 = null;
		if(endTime!=null && !endTime.toString().equals("")){
		endTime1 =  sdf.format(endTime);
		}
		
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //��ҵ�ƻ�
				+" a.name AS name," 
				+" a.status AS status,"
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "
				+" a.planNum as planNum,"
				+" a.priority as priority,"
				+" a.finishNum as finishNum,"
				+" a.childrenTotalNum as childrenTotalNum,"
				+" a.qualifiedNum as qualifiedNum,"  //�ϸ���				
				+" b.id as bid,"             //���
				+" b.no as no,"
				+" b.name as bname,"
				+" b.drawingno as drawingno,"
				+" b.version as version,"
				+" b.source as source"
				+")"
				+" FROM TJobplanInfo a , TPartTypeInfo b"
				+" WHERE a.TPartTypeInfo.id=b.id ";
		if(jobplabid!=null && !"".equals(jobplabid) && !jobplabid.equals("undefined")){
			hql += " AND a.id = '"+jobplabid+"'  "; 
		}	
		if(startTime!=null){
			hql += " AND a.planStarttime >= DATE_FORMAT('"+startTime1+"','%Y-%m-%d %T')  "; 
		}	
		if(endTime!=null){
			hql += " AND a.planStarttime <= DATE_FORMAT('"+endTime1+"','%Y-%m-%d %T')  "; 
		}	
		hql += " order by a.id asc ";
		return dao.executeQuery(hql);	
	}

	/**
	 * �޸Ĺ���ʱ�õ���ҵ����
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobIdMap(){
		//�õ���ҵ����
		String hql = "SELECT NEW MAP(" 
		    	+" j.id as jobid,"                        //��ҵID
		    	+" j.planNum as jobplanNum,"        //��ҵ�ļƻ�����
		    	+" j.TJobplanInfo.id as jobplanid,"         //��ҵ�ƻ�ID
			    +" j.name as jobname"         //��ҵ����   
				+")"
				+" FROM TJobInfo j ";
		return dao.executeQuery(hql);
	} 
	
	/**
	 * ��ȡ�������ʱ����Ϣ
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getJobdispatchlistInfoForAdd(String jobplanId){//ͨ����ҵID
		Map<String,Object> lst = new HashMap<String,Object>();
		//�õ���ҵ����
		String hql0 = "SELECT NEW MAP(" 
		    	+" j.id as jobid,"                        //��ҵID
		    	+" j.planNum as jobplanNum,"        //��ҵ�ļƻ�����
		    	+" j.TJobplanInfo.id as jobplanid,"         //��ҵ�ƻ�ID
			    +" j.name as jobname"         //��ҵ����   
				+")"
				+" FROM TJobInfo j where j.status = 20  ";
		List<Map<String,Object>> lst0 =  dao.executeQuery(hql0);
		lst.put("lst0", lst0);					
		
		if(jobplanId!=null && !"".equals(jobplanId)){
			//ͨ����ҵID�õ���ҵ�ƻ���ID�����۹�ʱ 
			String hql7 = "SELECT NEW MAP(" 
			    	+" j.id as jobid,"                        //��ҵID
			    	+" j.planNum as jobplanNum,"        //��ҵ�ļƻ�����
			    	+" j.TJobplanInfo.id as jobplanid,"             //��ҵ�ƻ�ID ===
			    	+" j.theoryWorktime as theoryWorktime,"         //���۹�ʱ    ====
				    +" j.name as jobname"         //��ҵ����   
					+")"
					+" FROM TJobInfo j  "
					+" WHERE j.id = '"+jobplanId+"'  ";	
			List<Map<String,Object>> lst7 =  dao.executeQuery(hql7);
			lst.put("lst7", lst7);
			
			//ͨ����ҵID�õ���ҵ�ļƻ�����
			String hql6 = "SELECT NEW MAP(" 
			    	+" j.id as jobid,"                        //��ҵID
			    	+" j.planNum as jobplanNum,"        //��ҵ�ƻ�����
				    +" j.name as jobname"         //��ҵ����   
					+")"
					+" FROM TJobInfo j   "
			        +" WHERE j.id = '"+jobplanId+"'  ";	
			List<Map<String,Object>> lst6 =  dao.executeQuery(hql6);
			lst.put("lst6", lst6);	
			
			//ͨ����ҵID�õ��豸��Ϣ
		String hql5 = "SELECT NEW MAP(" 
				    	+" d.id as cid,"                         //�豸����ID
				    	+" t.equipmentId as equipmentId,"        //�豸ID
				    	+" d.equipmentType as equipmentType"     //�豸��������    
						+")"
						+" FROM TJobInfo j ,TProcessInfo c ,TProcessEquipment t, TEquipmenttypeInfo d"
						+" WHERE j.TProcessInfo.id = c.id   "
						+" AND c.id = t.processId  "
						+" AND t.equipmentTypeId = d.id   ";	
				   if(!"".equals(jobplanId)){
					   hql5 += " AND  j.id =  '"+jobplanId+"'   "; 
				   }
		List<Map<String,Object>> lst5 =  dao.executeQuery(hql5);
		
		//ͨ����ҵID�õ�����
		String hql1 = "SELECT NEW MAP(" 
			    	+" t.id as tid,"                         //����ID
				    +" t.TMaterailTypeInfo.no as tno,"       //���ϱ��
			    	+" t.TMaterailTypeInfo.name as tname,"   //��������
			    	+" t.requirementNum as requirementNum"   //����������      
					+")"
					+" FROM TJobInfo j ,TProcessInfo c,TProcessplanInfo b , TProcessmaterialInfo t  "
					+" WHERE j.TProcessInfo.id = c.id AND c.TProcessplanInfo.id = b.id  AND b.materialId = t.id  ";	
			   if(!"".equals(jobplanId)){
				   hql1 += " AND  j.id =  '"+jobplanId+"'   "; 
			   }
		 List<Map<String,Object>> lst1 =  dao.executeQuery(hql1);
		 
		 //ͨ����ҵID����ҵ����
		 String hql2 = "SELECT NEW MAP(" 
					+" DATE_FORMAT(j.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //��ҵ--�ƻ���ʼʱ��
					+" DATE_FORMAT(j.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //��ҵ--�ƻ�����ʱ��
					+" j.planNum as jplanNum"                                          //��ҵ--�ƻ�����
					+")"
					+" FROM TJobInfo j   "
			        +" WHERE j.id = '"+jobplanId+"'  ";	
			List<Map<String,Object>> lst2 =  dao.executeQuery(hql2);
			 //ͨ����ҵID�ӹ�������
		 String hql3 = "SELECT NEW MAP(" 
				        +" c.TProcessplanInfo.name as pname,"  //���շ�������
				        +" c.theoryWorktime as theoryWorktime,"  //���ս���ʱ��
						+" c.file as file"             //����ָ���ļ�
						+")"
						+" FROM TJobInfo j ,TProcessInfo c   "
				        +" WHERE j.TProcessInfo.id = c.id  AND  j.id = '"+jobplanId+"'  ";	
			List<Map<String,Object>> lst3 =  dao.executeQuery(hql3);
			//ͨ����ҵID���������
		 String hql4 = "SELECT NEW MAP(" 
				        +" a.TPartTypeInfo.id as partid,"     //���ID
				        +" a.TPartTypeInfo.name as partname"  //�������
						+")"
						+" FROM TJobInfo j ,TJobplanInfo a   "
						+" WHERE j.TJobplanInfo.id = a.id  "
				        +" AND j.id = '"+jobplanId+"'  ";	
			List<Map<String,Object>> lst4 =  dao.executeQuery(hql4);
			
			lst.put("lst1", lst1);
			lst.put("lst2", lst2);
			lst.put("lst3", lst3);
			lst.put("lst4", lst4);
			lst.put("lst5", lst5);
			lst.put("lst6", lst6);
			lst.put("lst7", lst7);
		 }else{
				lst.put("lst1", null);
				lst.put("lst2", null);
				lst.put("lst3", null);
				lst.put("lst4", null);
				lst.put("lst5", null);
				lst.put("lst6", null);
				lst.put("lst7", null);
		 }			
		
		return lst;
	}
	
	/**
	 * ��ȡ�������ʱ����Ϣ--�豸����
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getEquimentAndType(String typeId){
		String hql = "SELECT NEW MAP(" 
                +" b.id as id,"             //�豸����ID
				+" b.equipmentType as equipmentType "             //�豸��������
				+")"
				+" FROM TEquipmenttypeInfo b "
				+" WHERE 1=1  ";
		   if(typeId!=null && !"".equals(typeId)){
			   hql += " AND  b.id =  '"+typeId+"'   "; 
		   }
		return dao.executeQuery(hql);	
	}
	
	/**
	 * ��ȡ�������ʱ����Ϣ--�豸����
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getEquimentByType(String nodeid,String typeId){
		String hql = "SELECT NEW MAP(" 
				+" a.equId AS equId,"                //�豸ID
				+" a.equName as equName,"            //�豸����
                +" b.id as id,"             //�豸����ID
				+" b.equipmentType as equipmentType "             //�豸��������
				+")"
				+" FROM TEquipmentInfo a , TEquipmenttypeInfo b "
				+" WHERE a.equTypeId = b.id  AND a.TNodes.TNodes.nodeId ='"+nodeid+"' ";
		   if(typeId!=null && !"".equals(typeId)){
			   hql += " AND  b.id =  '"+typeId+"'   "; 
		   }
		return dao.executeQuery(hql);	
	}
	
	
	/**
	 * ͨ�����Ʋ�ѯ���ж��Ƿ����ύ����
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobdispatchlistByName(String name){ 
		
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //����
				+" a.name AS name" 
				+" )"
				+" FROM TJobdispatchlistInfo a "
				+" WHERE a.name = '"+name+"'  ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * ͨ��ID��ת�������޸�ҳ��
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobdispatchlistById(String id){
		String hql = "SELECT NEW MAP(" 
				+" a.name AS name,"                                                //��������
				+" a.TJobInfo.id as id,"                                           //��ҵID
				+" a.no as no,"                                                    //�������
                +" a.processNum as processNum,"                                    //�����ƻ���
                +" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //�����ƻ���ʼʱ��
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //�����ƻ�����ʱ��
				+" a.remark as remark "                                            //������ϸ��Ϣ
				+")"
				+" FROM TJobdispatchlistInfo a  "
				+" WHERE a.id = '"+id+"'   ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * ����ͳ�ƣ�ͨ������ź�ʱ��
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobdispatchlistByIdAndTime(String nodeid,String taskNum,Date startTime,Date endTime){
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		String startTime1 = null;
		if(startTime!=null){
			startTime1 =  sdf.format(startTime);
		}
		String endTime1 = null;
		if(endTime!=null){
			endTime1 =  sdf.format(endTime);
		}
		String hql = "SELECT NEW MAP(" 
				+" a.id AS dispatchid,"                                             //����ID
				+" a.no AS dispatchno,"                                             //�������
				+" a.name AS name,"                                                 //��������
				+" a.TEquipmenttypeInfo.equipmentType AS equName,"                  //�豸����
				+" a.status AS status,"                                             //����״̬				
                +" a.processNum as processNum,"                                    //�����ƻ���
                +" a.finishNum as finishNum,"                                      //�����깤��
                +" DATE_FORMAT(a.planStarttime,'%Y-%m-%d') AS planStarttime,"   //�����ƻ���ʼʱ��
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d') as planEndtime, "      //�����ƻ�����ʱ��
				+" DATE_FORMAT(a.realStarttime,'%Y-%m-%d') AS realStarttime,"   //����ʵ�ʿ�ʼʱ��
				+" DATE_FORMAT(a.realEndtime,'%Y-%m-%d') as realEndtime, "      //����ʵ�ʽ���ʱ��
				                                                           			//Ч��Ԥ��
				+" a.remark as remark,"                                            //������ϸ��Ϣ
				+" a.taskNum as taskNum"										   //���������
				+")"
				+" FROM TJobdispatchlistInfo a where 1=1";
		if(taskNum!=null && !"".equals(taskNum)){
			hql += " AND taskNum = '"+taskNum+"'  "; 
		}	
		if(startTime!=null){
			hql += " AND a.planStarttime >= DATE_FORMAT('"+startTime1+"','%Y-%m-%d %T')  "; 
		}	
		if(endTime!=null){
			hql += " AND a.planEndtime <= DATE_FORMAT('"+endTime1+"','%Y-%m-%d %T')  "; 
		}	
		hql += " order by a.id asc ";
	    return dao.executeQuery(hql);			
	}
	
	//����ͳ�� ��������ŵõ�
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getjobdispatchByTaskNum(){
		String hql = "select distinct new Map(taskNum as taskNum) from TJobdispatchlistInfo";
		return dao.executeQuery(hql);
	}

	/**
	 * �������й���--�ѷ��乤���嵥
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getAlreadyDispatchList(String nodeid){
		/*
		String hql = "SELECT NEW MAP(" 
				+" a.no AS no,"                                                    //������
				+" b.TProcessInfo.name AS name,"                                   //��������
				+" b.id as jobid,"                                                 //��ҵID
				+" b.no as jobno,"                                                 //��ҵ���
				+" a.TEquipmentInfo.equId as equId,"                               //�豸ID
				+" a.TEquipmentInfo.equName as equName,"                           //�豸����
                +" a.processNum as processNum,"                                    //����--�ƻ���
                +" DATE_FORMAT(a.planStarttime,'%Y-%m-%d') AS planStarttime,"   //�����ƻ���ʼʱ��
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d') as planEndtime, "      //�����ƻ����ʱ��
				+" a.finishNum as finishNum "                                      //����--�������
				+")"
				+" FROM TJobdispatchlistInfo a, TJobInfo b  "
				+" WHERE  a.TJobInfo.id  =  b.id "
//				+" AND  (a.status = 30  OR  a.status = 40 OR  a.status = 50  ) " //�������Ѿ�����ģ�ȥ���˾������еĹ���
                +" AND  b.TJobplanInfo.nodeid = :nodeid ";  //����˽ڵ�
		*/
		String hql = "SELECT NEW MAP(" 
				+" a.no AS no,"                                                    //������
				+" p.name AS name,"                                               //��������
		//		+" b.id as jobid,"                                                 //��ҵID
		//		+" b.no as jobno,"                                                 //��ҵ���
		//		+" a.TEquipmentInfo.equId as equId,"                               //�豸ID
		//		+" a.TEquipmentInfo.equName as equName,"                           //�豸����
				+" c.equipmentType as equName,"                    //�豸��������
                +" a.processNum as processNum,"                                    //����--�ƻ���
                +" DATE_FORMAT(a.planStarttime,'%Y-%m-%d') AS planStarttime,"   //�����ƻ���ʼʱ��
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d') as planEndtime, "      //�����ƻ����ʱ��
				+" a.finishNum as finishNum "                                      //����--�������
				+")"
				+" FROM TJobdispatchlistInfo a,TEquipmenttypeInfo c,TProcessInfo p  "
				+" WHERE  c.id  =  a.TEquipmenttypeInfo.id "
				+ "AND a.TProcessInfo.id = p.id "
//				+" AND  (a.status = 30  OR  a.status = 40 OR  a.status = 50  ) " //�������Ѿ�����ģ�ȥ���˾������еĹ���
                +" AND  a.nodeid = :nodeid ";  //����˽ڵ�
          Collection<Parameter> parameters = new HashSet<Parameter>();
          parameters.add(new Parameter("nodeid",nodeid,Operator.EQ));
		return dao.executeQuery(hql,parameters);
	}
	
	/**
	 * �������й���--��ҵ�ƻ�����
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobPlanDetail(String jobplanId){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS jobplanid,"                                             //��ҵ�ƻ�ID
				+" a.process AS process,"                                          //����
				+" a.planNo AS userProdPlanId,"        							   //�����ƻ����
				+" a.TPartTypeInfo.id AS parttypeid,"                              //�������ID
				+" a.name AS name,"                                                //��ҵ�ƻ�����
                +" a.planNum as planNum,"                                          //��ҵ�ƻ��ƻ���
                +" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //��ҵ�ƻ���ʼʱ��
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //��ҵ�ƻ�����ʱ��
				+" a.priority as priority,"                                        //���ȼ�
				+" a.finishNum as finishNum "                                      //��ҵ�ƻ��������
				+")"
				+" FROM TJobplanInfo a  "
				+" WHERE a.id = '"+jobplanId+"'   ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * ��������--ʼĩʱ��Ĺ�����ʼʱ��Ϊ��Ӧ�ƻ��Ŀ�ʼʱ��
	 */
	@SuppressWarnings("unchecked")
	public Date getMaxEndTimeFormDispatchList(String jobid){
		String hql = " SELECT DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') as planStarttime FROM  TJobInfo a WHERE a.id ='"+jobid+"'  ";
		List<String> lst  = dao.executeQuery(hql);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		if (lst.size() > 0) {
			String time = lst.get(0);
			try {
				if(time!=null){
				d = sdf.parse(time);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return d;
	}
	
	/**
	 * ��ȡ������������
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSubordinatePlanInfoMap(String nodeid)
	{
		//Map<String, Object> map = new HashMap<String, Object>();
		String hql ="select new MAP(productionplan.uplanName as displayfield,productionplan.id as valuefield)" +
				"from TUserProdctionPlan productionplan where productionplan.TNodes.nodeId='"+nodeid+"'";

        return dao.executeQuery(hql);
	}

	/**
	 * ������ҵ�ƻ����״̬
	 */
	@Override
	public Boolean updateJobPlanInfoStatus(String jobPlanId,String status) {
        if ("40".equals(status)) {
            TJobplanInfo jobplan = dao.get(TJobplanInfo.class,Long.parseLong(jobPlanId));
            jobplan.setStatus(Integer.parseInt(status));
            jobplan.setRealStarttime(new Date());
            dao.update(TJobplanInfo.class,jobplan);
        }
        if ("60".equals(status)) {
            TJobplanInfo jobplan = dao.get(TJobplanInfo.class,Long.parseLong(jobPlanId));
            jobplan.setStatus(Integer.parseInt(status));
            jobplan.setRealEndtime(new Date());
            dao.update(TJobplanInfo.class,jobplan);
        }
		return false;
	}
	
	
/********************************A3******************************************/
	
	/**
	 * ��ҵ�ƻ����� -- ��ȡ������ͼ���
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPartTypeMap(){
		String hql = "SELECT NEW MAP(" 
				+" c.id as id,"     //�������ID
				+" c.name as name"  //�����������
				+")"
				+" FROM TPartTypeInfo c ";	
		return dao.executeQuery(hql);	
	}
	
	/**
	 * ��ҵ�ƻ����� -- ��ȡ��ҵ�ƻ�����
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobPlanMap(){
		String hql = "SELECT NEW MAP(" 
				+" c.id as id,"         //��ҵ�ƻ�ID
				+" c.planNo as planNo," //��ҵ�ƻ����
				+" c.name as name"      //��ҵ�ƻ�����
				+")"
				+" FROM TJobplanInfo c WHERE (c.status = 20  OR  c.status = 30 OR  c.status = 40 OR  c.status = 50)";	//��������ɣ�������Ҫ
		return dao.executeQuery(hql);	
	}
	
	/**
	 * ��ҵ�ƻ����� -- ��ȡ���շ�������
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProcessplanMap(String partid){
		String hql = "SELECT NEW MAP(" 
				+" c.id as id,"     //����ID
				+" c.name as name"  //��������
				+")"
				+" FROM TProcessplanInfo c "
				+ " where c.defaultSelected=1";
		if(!StringUtil.isEmpty(partid)){
			hql += " and  c.TPartTypeInfo.id = '"+partid+"' ";
		}
		return dao.executeQuery(hql);	
	}
	
	/**
	 * ��ҵ�ƻ����� -- ͨ����ҵ�ƻ�Id��ȡ������ͼ���
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPartTypeMapByJobPlanid(String jobplanid){
		String hql = "SELECT NEW MAP(" 
				+" c.TPartTypeInfo.id as id,"         //���ID
				+" c.TPartTypeInfo.name as name"      //�������
				+")"
				+" FROM TJobplanInfo c ";
		if(!StringUtil.isEmpty(jobplanid)){
			hql += " WHERE  c.id = '"+jobplanid+"' ";
		}
		return dao.executeQuery(hql);	
	}
	
    /**
     * ��ҵ�ƻ����� --Ͷ�������б�
     * @param processplanId ���շ���ID
     */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getBatchList(String processplanId,String jobplabid){
		String hql = "SELECT NEW MAP(" 
				+" a.no as no,"     //�������--���׹�λ�������
				+" a.processNum as processNum,"  //��������
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d') as planStarttime"  //��ʼʱ��
				+")"
				+" FROM TJobdispatchlistInfo a,TProcessInfo b, TJobInfo c   "
		        +" WHERE a.TJobInfo.id = c.id "
		        +" AND c.TProcessInfo.id = b.id  "
		        +" AND b.TProcessplanInfo.id = '"+processplanId+"' "
		        +" AND c.TJobplanInfo.id = '"+jobplabid+"' "
		        +" AND b.onlineProcessId is null  ";
		return dao.executeQuery(hql);
	}

	/**
	 * ��ҵ�ƻ����� --ͨ�����շ���ID�õ������嵥
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProcessByProcessPlanId(String nodeid,String processPlanId){
		String hql = "SELECT NEW MAP(" 
				+" c.id as id,"            //����ID ===>
				+" c.no as no,"            //�������
				+" c.name as cname,"       //��������
				+" c.theoryWorktime as theoryWorktime,"   //����ʱ��
				+" c.processDuration as processDuration," //��������
				+" c.TProcessplanInfo.id as pid,"     //���շ���ID
				+" c.TProcessplanInfo.name as pname,"  //���շ�������
				+" b.id as bid "             //���շ���ID
				+")"
				+" FROM TProcessplanInfo b ,TProcessInfo c "
				+" WHERE  b.id=c.TProcessplanInfo.id "
				+ "AND  c.status = 0  "
				+ "AND b.id = '"+processPlanId+"' ";
		List<Map<String,Object>> lst = dao.executeQuery(hql);
		for(Map<String,Object> m : lst){
			String id = m.get("id").toString(); //����ID 
			String sql = "SELECT NEW MAP("
					+" d.equId as equId,"                 //�豸ID
					+" d.equName as equName,"             //�豸����
					+" d.equSerialNo as equSerialNo"      //�豸���к�
					+")"
					+" FROM  TProcessEquipment t , TEquipmentInfo d "
					+" WHERE  t.equipmentId = d.equId "
					+" AND  t.processId= '"+id+"' ";
			List<Map<String,Object>> prolst = dao.executeQuery(sql);
			Map<String,Object> pro1 = prolst.get(0);
			m.put("equId", pro1.get("equId"));
			m.put("equSerialNo", pro1.get("equSerialNo"));
			m.put("equName", pro1.get("equName"));
		}
		
		return lst;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getSerNoByProcessId(String processNo){//����ID
		String hql = "SELECT NEW MAP(" 
				+" d.equId as equId,"             //�豸ID
				+" d.equName as equName,"             //�豸����
				+" d.equSerialNo as equSerialNo"             //�豸���к�
				+")"
				+" FROM TProcessInfo c, TProcessEquipment t , TEquipmentInfo d "
				+ "WHERE c.id = t.processId "
				+ "AND t.equipmentId = d.equId  "
				+ "AND c.id = '"+processNo+"'  " ;
		return dao.executeQuery(hql);
	}
	
	@SuppressWarnings("unchecked")
	public String getEquNameByEquId(String equId){
		String hql = "SELECT NEW MAP(" 
				+" d.equId as equId,"             //�豸ID
				+" d.equName as equName,"             //�豸����
				+" d.equSerialNo as equSerialNo"             //�豸���к�
				+")"
				+" FROM  TEquipmentInfo d "
				+ "WHERE d.equId = '"+equId+"'  " ;
		List<Map<String,Object>> lst = dao.executeQuery(hql);
		String equName =null;
		if(lst.size()>0){
			Map<String,Object> map = lst.get(0);
			equName = (String)map.get("equName");
		}
		return equName;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobPlanByJobIdAndPartId(String nodeid,String jobplanid,String partId){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //��ҵ�ƻ�
				+" a.no AS no,"            //��ҵ�ƻ����===>
				+" a.name AS name," 
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //yao
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //yao
				+" a.planNum as planNum,"                                          //yao
				+" a.priority as priority,"
				+" a.TPartTypeInfo.id as partid,"    //���ID
				+" a.TPartTypeInfo.name as partname"
				+")"
				+" FROM TJobplanInfo a "	
				+ "WHERE  1 = 1  ";
		if(nodeid!=null && !"".equals(nodeid)){
			hql += " AND a.nodeid = '"+nodeid+"'  "; 
		}
		if(jobplanid!=null && !"".equals(jobplanid)){
			hql += " AND a.id = '"+jobplanid+"'  "; 
		}	
		if(partId!=null && !"".equals(partId)){
			hql += " AND a.TPartTypeInfo.id = '"+partId+"'  "; 
		}
        return dao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	public List<TJobplanInfo> getTJobplanInfoByBatchNo(String batchNo,String partTypeId){
		String hql="from TJobplanInfo where 1=1 "
				+ " and TPartTypeInfo.id='"+partTypeId+"'"
				+ "and planType<>2 ";
		return dao.executeQuery(hql);
	}

    /**
     * ͨ��id��ѯ��ҵ�ƻ���Ϣ
     * @param id ��ҵ�ƻ�ID
     * @return ������ҵ�ƻ���Ϣ
     */
	@SuppressWarnings("unchecked")
	private List<TJobplanInfo> getTJobplanInfoById(String id){
		String hql="from TJobplanInfo where id='"+id+"'";
		return dao.executeQuery(hql);
	}
	

	@SuppressWarnings("unchecked")
	public List<TJobplanInfo> getJobPlan(String nodeId,String partTypeId){
		String hql = "FROM TJobplanInfo a "
				+ " where a.planType = 2 and a.nodeid='"+nodeId
				+ "' and a.TPartTypeInfo.id=" + partTypeId
				+ " and a.status = 40";
		return dao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobdispatchList(String nodeid,String query,String jobplanId){
		String hql="select new Map("
				+ " a.id as id,"
				+ " a.no as no)"
				+ " from TJobdispatchlistInfo a where a.nodeid='"+nodeid+"'";
		if(null!=query&&!"".equals(query)){
			hql+=" and a.no like '%"+query+"%'";
		}
		if(null!=jobplanId&&!"".equals(jobplanId)){
			hql+=" and a.jobplanId ='"+jobplanId+"'";
		}
		return dao.executeQuery(hql);
	}

	@SuppressWarnings("unchecked")
	public Map<String,Object> getDataByjobdispatchNo(String jobdispatchNo){
		Map<String,Object> rsmap=new HashMap<String, Object>();
		String erplisthql="select new Map("
					   + " b.no as taskNO"
					   + " )"
					   + " from TJobdispatchlistInfo a,TJobplanInfo b"
					   + " where a.jobplanId=b.id  "
					   + " and a.no='"+jobdispatchNo+"'";
		List<Map<String,Object>> erplist=dao.executeQuery(erplisthql);
		if(null!=erplist&&erplist.size()>0){
			Map<String,Object> erpMap=erplist.get(0);
			rsmap.put("taskNO", erpMap.get("taskNO"));//���erp������
		}
		
		String gxhql="select new Map("
				  + " a.TProcessInfo.TProcessplanInfo.TPartTypeInfo.no as itemCode,"
				  + " a.TProcessInfo.TProcessplanInfo.TPartTypeInfo.id as partId,"
				  + " a.TProcessInfo.TProcessplanInfo.TPartTypeInfo.name as itemDesc,"
				  + " a.TProcessInfo.processOrder as processOrder,"
				  + " a.TProcessInfo.onlineProcessId as onlineProcessId)"
				  + " from TJobdispatchlistInfo a"
				  + " where  a.no='"+jobdispatchNo+"'";
		List<Map<String,Object>> gxrs=dao.executeQuery(gxhql);
		if(null!=gxrs&&gxrs.size()>0){
			Map<String,Object> gx=gxrs.get(0);
			rsmap.put("partId", gx.get("partId"));//�������id
			rsmap.put("itemCode", gx.get("itemCode"));//������ϱ���
			rsmap.put("itemDesc", gx.get("itemDesc"));//�����������
			rsmap.put("toOperationNum", gx.get("processOrder"));//��� ������
			rsmap.put("onlineProcessId",gx.get("onlineProcessId"));
		}else{
			rsmap.put("itemCode", "");//������ϱ���
			rsmap.put("itemDesc", "");//�����������
			rsmap.put("toOperationNum", "");//��� ������
		}
		
		String sqlgylist="SELECT b.process_order as processOrder,b.theorywork_time as processingTime,b.onlineProcessID,b.offlineProcess,"
						+ " b.processPlanID as processPlanID,b.id as itemCode,b.name as processName" 
						+" FROM t_jobdispatchlist_info a,t_process_info b WHERE " 
						+"	a.processID=b.ID "
						+"	AND a.batchno =( "
						+"	SELECT  batchno FROM t_jobdispatchlist_info WHERE NO='"+jobdispatchNo+"')"
						+" order by b.process_order ";
		List<Map<String,Object>> zrgxrs=dao.executeNativeQuery(sqlgylist);				
		if(null!=zrgxrs&&zrgxrs.size()>0){
			rsmap.put("zrOperationNum", zrgxrs);//������ι����б�
			Map<String,Object> mp=zrgxrs.get(0);
			rsmap.put("processPlanID", mp.get("processPlanID"));
		}
		return rsmap;
	}



}

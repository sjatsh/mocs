
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
 * 作业计划管理SERVICE接口实现类
 * @作者 Jf
 * @创建时间 2013-05-06
 * @修改者 songkaiang
 * @修改日期
 * @修改说明
 * @version V1.0
 */
public class JobPlanServiceImpl extends GenericServiceSpringImpl<TJobplanInfo, String> implements IJobPlanService {

	@Override
	public List<Map<String, Object>> findAllJobPlanAndPartInfo(String nodeId) {
		String hql="SELECT d.id AS Id,  d.typeNo AS Cls,d.name AS Name " +
				"from t_part_type_info d where d.status !='删除' " +
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
		
		sql += " WHERE a.status<>'删除' "
				+ " AND a.nodeid = '"+nodeId+"'";
		//添加过滤查询条件
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
                    pc.put("Name","批次计划");
                }

				pc.put("expanded",iszk);
				pc.put("children",new ArrayList<Map<String, Object>>());
				pc.put("leaf",true);
				san.add(pc);//添加第二层批次计划
				
				List<Map<String, Object>> er=new ArrayList<Map<String, Object>>(); //第二层
				Map<String, Object> zy=new HashMap<String, Object>();
				zy.put("Id",map.get("partId")+"0000001");
                if(locale.equals("en") || locale.equals("en_US")){
                    zy.put("Name", "Production Plan");
                }else {
                    zy.put("Name", "作业计划");
                }
				zy.put("expanded",iszk);
				zy.put("children",san);
				zy.put("leaf",false);
				er.add(zy);//添加第二层 作业计划
				
				Map<String, Object> map1=new HashMap<String, Object>();
				map1.put("Id",map.get("partId")+"");
				map1.put("Name",map.get("partName")+"");
				map1.put("expanded",iszk);
				map1.put("children",er);
				rs.add(map1);//添加第一层 零件
				
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
				+ " WHERE a.status<>'删除' "
				+ " AND a.nodeid = '"+nodeId+"'";
		//添加过滤查询条件
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
		
//		  算法1 格式如下
//		 * 零件
//		 * 	 批次计划
//		 * 		批次计划1
//		 * 		批次计划2
//		 * 	作业计划
//		 * 		作业计划1
//		 * 		作业计划2
		 
		for(Map<String, Object> map:ds){
			if(null!=map.get("partId")&&!prlist.contains(map.get("partId").toString())){
				if(null==map.get("planType")){//判断是否有作业计划
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("Id",map.get("partId")+"");
					map1.put("Name",map.get("partName")+"");
					map1.put("expanded",true);
					map1.put("children",new ArrayList());
					map1.put("leaf",true);
					rs.add(map1);//添加第一层 零件
					prlist.add(map.get("partId").toString());
					continue;
				}else if(Integer.parseInt(map.get("planType").toString())==1){//判断是否是作业类型
					List<Map<String, Object>> san=new ArrayList();
					Map<String, Object> sanzy=new HashMap<String, Object>();
					sanzy.put("Id",map.get("jobPlanId")+"");
					sanzy.put("Name",map.get("jobPlanName")+"");
					sanzy.put("expanded",false);
					sanzy.put("leaf",true);
					san.add(sanzy);//添加第三层 作业计划
					
					List<Map<String, Object>> er=new ArrayList(); //第二层
					
					Map<String, Object> zy=new HashMap<String, Object>();
					zy.put("Id",map.get("jobPlanId")+"01");
					zy.put("Name","作业计划");
					zy.put("expanded",false);
					zy.put("children",san);
					zy.put("leaf",false);
					er.add(zy);//添加第二层 作业计划
					
					Map<String, Object> pc=new HashMap<String, Object>();
					pc.put("Id",map.get("jobPlanId")+"02");
					pc.put("Name","批次计划");
					pc.put("expanded",false);
					pc.put("children",new ArrayList());
					pc.put("leaf",true);
					er.add(pc);//添加第二层批次计划
					
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("Id",map.get("partId")+"");
					map1.put("Name",map.get("partName")+"");
					map1.put("expanded",true);
					map1.put("children",er);
					rs.add(map1);//添加第一层 零件
				}
				else{
					List<Map<String, Object>> san=new ArrayList();
					Map<String, Object> sanzy=new HashMap<String, Object>();
					sanzy.put("Id",map.get("jobPlanId")+"");
					sanzy.put("Name",map.get("jobPlanName")+"");
					sanzy.put("expanded",false);
					sanzy.put("leaf",true);
					san.add(sanzy);//添加第三层 批次计划
					
					List<Map<String, Object>> er=new ArrayList(); //第二层
					
					
					
					Map<String, Object> zy=new HashMap<String, Object>();
					zy.put("Id",map.get("jobPlanId")+"01");
					zy.put("Name","作业计划");
					zy.put("expanded",false);
					zy.put("children",new ArrayList());
					zy.put("leaf",true);
					er.add(zy);//添加第二层 作业计划
					
					Map<String, Object> pc=new HashMap<String, Object>();
					pc.put("Id",map.get("jobPlanId")+"02");
					pc.put("Name","批次计划");
					pc.put("expanded",false);
					pc.put("children",san);
					pc.put("leaf",false);
					er.add(pc);//添加第二层批次计划
					
					
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("Id",map.get("partId")+"");
					map1.put("Name",map.get("partName")+"");
					map1.put("expanded",true);
					map1.put("children",er);
					rs.add(map1);//添加第一层 零件
				}
				prlist.add(map.get("partId").toString());
				
			}else{
				for(Map<String,Object> mm:rs){//重新遍历第一层  零件
					if(mm.get("Id").toString().equals(map.get("partId").toString())){//判断当前零件是否与遍历零件相同
						List<Map<String, Object>> two=(List<Map<String, Object>>)mm.get("children"); //获取第二层的数据  
						for(Map<String, Object> twoMap:two){//遍历二层数据
							String twoMapName=twoMap.get("Name")+"";
							if(Integer.parseInt(map.get("planType").toString())==1&&twoMapName.equals("作业计划")){//判断二层数据是作业计划  并且当前循环的计划属于作业计划
								List<Map<String, Object>> three=(List<Map<String, Object>>)twoMap.get("children"); //获取作业计划的第三层数据
								Map<String, Object> threeMap=new HashMap<String, Object>();//将新增的第三层 添加进数据
								threeMap.put("Id",map.get("jobPlanId")+"");
								threeMap.put("Name",map.get("jobPlanName")+"");
								threeMap.put("leaf",true);
								three.add(threeMap);
								twoMap.put("leaf", false);
								break;
							}else if(twoMapName.equals("批次计划")){
								List<Map<String, Object>> three=(List<Map<String, Object>>)twoMap.get("children"); //获取批次计划的第三层数据
								Map<String, Object> threeMap=new HashMap<String, Object>();//将新增的第三层 添加进数据
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
		
//		 * 算法2 格式如下
//		 * 零件
//		 * 	 批次计划1
//		 * 		作业计划1
//		 * 		作业计划2
//		 *  批次计划2
//		 *  	作业计划1
//		 *  	作业计划2
		
		for(Map<String, Object> map:ds){
			if(null!=map.get("partId")&&!prlist.contains(map.get("partId").toString())){
				if(null==map.get("planType")){//判断是否有作业计划
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("Id",map.get("partId")+"");
					map1.put("Name",map.get("partName")+"");
					map1.put("expanded",true);
					map1.put("children",new ArrayList());
					map1.put("leaf",true);
					rs.add(map1);//添加第一层 零件
					prlist.add(map.get("partId").toString());
					continue;
				}else if(Integer.parseInt(map.get("planType").toString())==1){//判断是否是作业类型
					List<Map<String, Object>> nextlist=new ArrayList();
					Map<String, Object> map2=new HashMap<String, Object>();
					map2.put("Id",map.get("jobPlanId")+"");
					map2.put("Name",map.get("jobPlanName")+"");
					map2.put("expanded",false);
					map2.put("children",new ArrayList());
					map2.put("leaf",true);
					nextlist.add(map2);//添加第二层 作业计划
					
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("Id",map.get("partId")+"");
					map1.put("Name",map.get("partName")+"");
					map1.put("expanded",true);
					map1.put("children",nextlist);
					rs.add(map1);//添加第一层 零件
				}
				else{
					List<Map<String, Object>> nextlist2=new ArrayList();
					Map<String, Object> map3=new HashMap<String, Object>();
					map3.put("Id",map.get("jobPlanId")+"");
					map3.put("Name",map.get("jobPlanName")+"");
					map3.put("leaf",true);
					nextlist2.add(map3);//添加第三重 批次计划
					
					List<Map<String, Object>> nextlist=new ArrayList();
					Map<String, Object> map2=new HashMap<String, Object>();
					map2.put("Id",null);
					map2.put("Name",null);
					map2.put("expanded",false);
					map2.put("children",nextlist2);
					nextlist.add(map2);//添加第二层 作业计划
					
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.put("Id",map.get("partId")+"");
					map1.put("Name",map.get("partName")+"");
					map1.put("expanded",true);
					map1.put("children",nextlist);
					rs.add(map1); //添加第一层 零件
				}
				prlist.add(map.get("partId").toString());
				
			}else{
				for(Map<String,Object> mm:rs){
					if(mm.get("Id").toString().equals(map.get("partId").toString())){
						if(Integer.parseInt(map.get("planType").toString())==1){//判断是将数据添加进第二层
							List<Map<String, Object>> two=(List<Map<String, Object>>)mm.get("children"); //获取第二层的数据  
							Map<String, Object> yytwoMap=two.get(0);
							if(yytwoMap.get("Name")==null&&yytwoMap.get("Id")==null){//判断如果是第二层没有填充的的数据 
								yytwoMap.put("Id",map.get("jobPlanId")+"");
								yytwoMap.put("Name",map.get("jobPlanName")+"");
								yytwoMap.put("expanded",false);
								yytwoMap.put("children",new ArrayList());
								yytwoMap.put("leaf",true);
								break;
							}else{
								Map<String, Object> twoMap=new HashMap<String, Object>();//将新增的第二层 添加进数据
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
							List<Map<String, Object>> two=(List<Map<String, Object>>)mm.get("children"); //获取第二层的数据  
							for(Map<String, Object> yytwoMap:two){//遍历第二层的数据
								if(yytwoMap.get("Id").toString().equals(map.get("pId").toString())){//如果第二层的id 与当前pid数据相等 说明这是 第三层的数据
									if(null==yytwoMap.get("children")||"".equals(yytwoMap.get("children")+"")||
											((List<Map<String, Object>>)yytwoMap.get("children")).size()<1){
										List<Map<String, Object>> three=new ArrayList<Map<String,Object>>();
										Map<String, Object> threeMap=new HashMap<String, Object>();//将新增的第三层 添加进数据
										threeMap.put("Id",map.get("jobPlanId")+"");
										threeMap.put("Name",map.get("jobPlanName")+"");
										threeMap.put("leaf",true);
										three.add(threeMap);
										yytwoMap.put("children", three);
										yytwoMap.put("leaf",false);
										break;
									}else{
										List<Map<String, Object>> three=(List<Map<String, Object>>)yytwoMap.get("children");
										Map<String, Object> threeMap=new HashMap<String, Object>();//将新增的第三层 添加进数据
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
	//查询所有作业计划
	@Override
    @SuppressWarnings("unchecked")
	public List<Map<String, Object>> findAllJobPlan(String nodeId,String partid,String planStatus,String startTime,String endTime) {
		List<Map<String, Object>> result=new ArrayList<Map<String, Object>>();
		String hql = "SELECT NEW MAP(a.id AS Id,a.no AS No," 
				+"a.name AS Name," 
				+"a.status AS Status,"
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS StartDate,"//计划开始时间
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as EndDate ,"//计划结束时间
				+" DATE_FORMAT(a.realStarttime,'%Y-%m-%d %T') as RealStarttime ,"//实际开始时间
				+" DATE_FORMAT(a.realEndtime,'%Y-%m-%d %T') as RealEndtime,"//实际结束时间
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
				//创建  待派工 已派工 
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
				//上线  加工	
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
				//结束  完工
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
	 * 根据产品id，查询作业计划信息
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
	 * 作业计划详情默认取一条记录
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
	 * 获取作业计划表中当前最大ID
	 */
	public String getMaxJobPlanInfoId()
	{
		String hql="select MAX(id) from TJobplanInfo" ;
        return dao.executeQuery(hql).get(0).toString();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 根据id，查询作业计划信息
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
		//验证是否有工单关联，工单一旦启动，关联的作业计划和批次计划不允许删除
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
	 * 获取跳转到编辑页面的计划表和零件表的信息
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPlanAndPartList(String jobplabid){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //作业计划
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
				+" a.planNo as planNo,"      //生产计划编号
				+" a.planType as planType,"      
				+" a.TJobplanInfo.id as pid,"  
				+" b.id as bid,"             //零件
				+" b.no as bno,"
				+" b.name as bname,"
//				+" b.type as type,"        //被改了数据库
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
	 * 作业计划页面的生产计划下拉列表
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPlanAndForList(String nodeid){
		String hql = "SELECT NEW MAP(" 
		+" c.id as cid,"            //生产计划
		+" c.uplanNo as uplanNo,"  
		+" c.uplanType as uplanType,"
		+" c.uplanNum as uplanNum,"
		+" c.uplanStatus as uplanStatus,"
		+" c.uplanName AS uplanName" //生产计划名称
		+")"
		+" FROM TUserProdctionPlan c ";
		if(nodeid!=null && !"".equals(nodeid)){
		hql +=	" WHERE c.TNodes.nodeId='"+nodeid+"' ";  
		}
		return dao.executeQuery(hql);	
	}
	
	/**
	 * 作业计划页面的零件下拉列表
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
	 * 获取优先级
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPriority(){
		String hql = "SELECT distinct NEW MAP(a.priority as displayfield)"
				+" FROM TJobplanInfo a ";
		return dao.executeQuery(hql);	
	}
	/**
	 * 作业计划保存
	 */
	public String addJobPlan(JobPlanAddBean jobPlanAddBean){
	  try {
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			String nodeid = (String)session.getAttribute("nodeid");		
			
		    TJobplanInfo jpi = new TJobplanInfo(); //必须是在一个对象中操作

		    String proPlanNo = jobPlanAddBean.getProPlanNo();//生产计划编号
			
			String partTypeId=jobPlanAddBean.getPartTypeId();
			TPartTypeInfo tPartTypeInfo=null;
			if(!StringUtils.isEmpty(partTypeId)){
				tPartTypeInfo=commonService.get(TPartTypeInfo.class, Long.valueOf(partTypeId));
			}
			System.out.println("jobPlanAddBean.getNo().length():"+jobPlanAddBean.getNo().length());
			jpi.setNo(jobPlanAddBean.getNo());
			//如果是作业计划,默认状态为40
			if("1".equals(jobPlanAddBean.getPlanType()))
				{ 
				   jpi.setStatus(40);
				   jpi.setRealStarttime(jobPlanAddBean.getPlanStarttime()); //计划开始时间就是实际开始时间
				}
			else 
				jpi.setStatus(10);  //批次计划创建是默认是10
//			jpi.setTUserProdctionPlan(tUserProdctionPlan);     //从属生产计划名称、
			jpi.setPlanNo(proPlanNo);     //生产计划编号
			jpi.setName(jobPlanAddBean.getName());
			jpi.setPlanStarttime(jobPlanAddBean.getPlanStarttime());
			jpi.setPlanEndtime(jobPlanAddBean.getPlanEndtime());
			jpi.setFinishDate(jobPlanAddBean.getSubmitTime());
			if(!StringUtils.isEmpty(jobPlanAddBean.getPlanNum())){
			   jpi.setPlanNum(Integer.parseInt(jobPlanAddBean.getPlanNum()));
			  }
			jpi.setFinishNum(0);
			jpi.setQualifiedNum(0);
			jpi.setTPartTypeInfo(tPartTypeInfo);     //零件名称
			jpi.setProcess((double) 0);
			jpi.setChildrenTotalNum(0);
			jpi.setAllocatedNum(Integer.parseInt(jobPlanAddBean.getAllocatedNum()));
			if(!StringUtils.isEmpty(jobPlanAddBean.getPriority())){
			jpi.setPriority(Integer.parseInt(jobPlanAddBean.getPriority()));
			}
			jpi.setNodeid(nodeid);   //节点ID
			
			jpi.setPlanType(Integer.parseInt(jobPlanAddBean.getPlanType()));//计划类型
			jpi.setCreateDate(new Date());//创建时间
			if(null!=jobPlanAddBean.getpJobPlanId()&&!"".equals(jobPlanAddBean.getpJobPlanId())){//父作业计划
				List<TJobplanInfo> pjob=this.getTJobplanInfoById(jobPlanAddBean.getpJobPlanId());
				if(null!=pjob&&pjob.size()>0){
					jpi.setTJobplanInfo(pjob.get(0));
				}
			}
			jpi.setScrapNum(0);
			commonService.saveObject(jpi);
			if(jpi.getPlanType()==2){
				TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);//获取用户
				List<Map<String,Object>> task=jobPlanAddBean.getRealaddTask();//获取分配数据
				for(Map<String,Object> map:task){
					TJobplanTaskInfo taskinfo=new TJobplanTaskInfo();
					taskinfo.setJobPlanId(jpi.getId());
					taskinfo.setJobPlanNo(jpi.getNo());
					taskinfo.setTaskNO(map.get("addTaskNo").toString());
					taskinfo.setTaskNum(Integer.parseInt(map.get("addTaskNum").toString()));
					taskinfo.setOperator(user.getNickName());
					taskinfo.setOperatorTime(new Date());
					taskinfo.setReportNum(Integer.parseInt(map.get("reportNum").toString()));
					commonService.saveObject(taskinfo); //添加分配任务
				}
			}
			Locale locale=SessionHelper.getCurrentLocale(session);
			if(locale.toString().equals("en") || locale.toString().equals("en_US")){
				  return "Created successfully";
			}else{
			      return "创建成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			Locale locale=SessionHelper.getCurrentLocale(session);
			if(locale.toString().equals("en") || locale.toString().equals("en_US")){
				return "Failed to add";
			}else{
				return "添加失败";
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
				+" a.id AS id,"            //作业计划
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
//				+" b.type as type,"        //被改了数据库
				+" b.drawingno as drawingno,"
				+" b.version as version,"
				+" b.source as source"
				+" )"
				+" FROM TPartTypeInfo b "
				+" WHERE b.id = '"+partTypeId+"'  ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 作业计划的最大ID，为作业计划拼装
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
	 * 作业计划修改
	 */
	public String updataJobPlan(JobPlanUpdataBean jobPlanAddBean){
		try {
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			 TJobplanInfo jpi = null;
				if(!StringUtils.isEmpty(jobPlanAddBean.getId())){
					jpi=commonService.get(TJobplanInfo.class, Long.valueOf(jobPlanAddBean.getId()));
				}
				String proPlanNo = jobPlanAddBean.getProPlanNo(); //生产计划编号
				
				String partTypeId=jobPlanAddBean.getPartTypeId();
				TPartTypeInfo tPartTypeInfo=null;
				if(!StringUtils.isEmpty(partTypeId)){
					tPartTypeInfo=commonService.get(TPartTypeInfo.class, Long.valueOf(partTypeId));
				}

				jpi.setNo(jobPlanAddBean.getNo());
//				jpi.setStatus(10);  //创建10状态下才能修改
				jpi.setPlanNo(proPlanNo);   //生产计划编号
				jpi.setName(jobPlanAddBean.getName());
				jpi.setPlanStarttime(jobPlanAddBean.getPlanStarttime());
				jpi.setPlanEndtime(jobPlanAddBean.getPlanEndtime());
				jpi.setFinishDate(jobPlanAddBean.getSubmitTime());
				if(!StringUtils.isEmpty(jobPlanAddBean.getPlanNum())){
					jpi.setPlanNum(Integer.parseInt(jobPlanAddBean.getPlanNum()));
				}
				
				jpi.setTPartTypeInfo(tPartTypeInfo);     //零件名称
				
				if(!StringUtils.isEmpty(jobPlanAddBean.getPriority())){
					jpi.setPriority(Integer.parseInt(jobPlanAddBean.getPriority()));
				}
				
				jpi.setPlanType(Integer.parseInt(jobPlanAddBean.getPlanType()));//计划类型
				if(null!=jobPlanAddBean.getpJobPlanId()&&!"".equals(jobPlanAddBean.getpJobPlanId())){//父作业计划
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
					List<TJobplanTaskInfo> ttilist=getTJobplanTaskInfoObject(jpi.getId().toString());//根据作业id 获取作业分配任务对象
					TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);//获取用户
					List<String> delList=new ArrayList<String>();
					for(Map<String,Object> mm:jobPlanAddBean.getRealaddTask()){
						if(!jobPlanAddBean.getAddTask().contains(mm)){
							if(null!=mm.get("realId")){
								delList.add(mm.get("realId").toString());//判断需要删除的数据
							}
						}
					}
					for(Map<String,Object> mm:jobPlanAddBean.getRealaddTask()){
						if(null==mm.get("realId")){//realId 为空说明该分配任务是新增
							TJobplanTaskInfo taskinfo=new TJobplanTaskInfo();
							taskinfo.setJobPlanId(jpi.getId());
							taskinfo.setJobPlanNo(jpi.getNo());
							taskinfo.setTaskNO(mm.get("addTaskNo").toString());
							taskinfo.setTaskNum(Integer.parseInt(mm.get("addTaskNum").toString()));
							taskinfo.setOperator(user.getNickName());
							taskinfo.setOperatorTime(new Date());
							taskinfo.setReportNum(Integer.parseInt(mm.get("reportNum").toString()));
							commonService.saveObject(taskinfo); //添加分配任务
							continue;
						}
						
						for(TJobplanTaskInfo tti:ttilist){
							if(mm.get("realId").toString().equals(tti.getId().toString())){ //不为空  的话 匹配对象
								if(delList.contains(tti.getId().toString())){
									commonService.delete(tti);
									continue;
								}
								if(mm.get("addTaskNo").toString().equals(tti.getTaskNO())
										&&mm.get("addTaskNum").toString().equals(tti.getTaskNum().toString())){//如果没有修改 则不操作数据进入下一次操作
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
			      return "修改成功!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			Locale locale=SessionHelper.getCurrentLocale(session);
		    if(locale.toString().equals("en") || locale.toString().equals("en_US")){
				  return "failed to Update";
			}else{
				return "修改失败";
			}
			
		}
	   
		
	}
	
	/**
	 * 通过作业计划得到工序清单
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProcessByJobPlanId(String jobplabid){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //作业计划
				+" c.id as cid,"            //工序
				+" c.no as no,"  
				+" c.name as name,"
				+" c.theoryWorktime as theoryWorktime,"
				+" c.processDuration as processDuration,"
				+" c.TEquipmenttypeInfo.equipmentType as equipmentType,"
				+" c.TProcessplanInfo.id as pid,"     //工艺ID
				+" c.TProcessplanInfo.name as pname,"  //工艺名称
				+" b.id as bid "             //作业
				+")"
				+" FROM TJobplanInfo a , TJobInfo b ,TProcessInfo c"
				+" WHERE a.id=b.TJobplanInfo.id AND b.TProcessInfo.id=c.id  ";
		if(jobplabid!=null && !"".equals(jobplabid)){
			hql += " AND a.id = '"+jobplabid+"'  "; 
		}		
		return dao.executeQuery(hql);	
	}
	
	/**
	 * 控制页面--通过零件ID得到作业计划ID下拉
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobPlanMapByPart(String nodeid,String partId){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //作业计划
				+" a.no AS no,"            //作业计划编号===>
				+" a.name AS name," 
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //yao
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //yao
				+" a.planNum as planNum,"                                          //yao
				+" a.priority as priority,"
				+" a.TPartTypeInfo.id as partid,"    //零件ID
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
	 * 控制页面--通过零件ID得到工艺方案ID下拉
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProcessPlanMapByPart(String nodeid,String partId){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //作业计划
				+" a.no AS no,"            //作业计划编号===>
				+" a.name AS name," 
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //yao
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //yao
				+" a.planNum as planNum,"                                          //yao
				+" a.priority as priority,"
				+" a.TPartTypeInfo.id as partid,"    //零件ID
				+" a.TPartTypeInfo.name as partname,"
				+" b.id as pid,"     //工艺方案ID
				+" b.name as pname"  //工艺方案名称
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
	 * 通过作业计划ID，工艺方案ID，零件ID得到工序清单
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProcessPlanById(String nodeid,String jobplanid,String processPlanId,String partId){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //作业计划
				+" a.no AS no,"            //作业计划编号===>
				+" a.name AS name," 
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //yao
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //yao
				+" a.planNum as planNum,"                                          //yao
				+" a.priority as priority,"
				+" a.TPartTypeInfo.id as partid,"    //零件ID
				+" a.TPartTypeInfo.name as partname,"
				+" b.id as bid "             //工艺方案ID
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
			map.put("bool", true);  //为页面复选框为选定
		}		
		return lst;
		
	}
	
	/**
	 * 通过作业计划ID，工艺方案ID，零件ID得到工序清单,--不要关联作业计划表，会是1对多,触发工艺方案按钮时
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProcessPlanById1(String jobplanid,String processPlanId,String partId){
		String hql = "SELECT NEW MAP(" 
				+" c.id as cid,"            //工序 ===>
				+" c.no as cno,"  
				+" c.name as cname,"
				+" c.theoryWorktime as theoryWorktime,"   //节拍时间
				+" c.processDuration as processDuration," //工序连续
				+" c.TEquipmenttypeInfo.equipmentType as equipmentType," //设备类型
				+" c.TProcessplanInfo.id as pid,"     //工艺方案ID
				+" c.TProcessplanInfo.name as pname"  //工艺方案名称
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
			map.put("bool", true);  //为页面复选框为选定
		}		
		return lst;
		
	}
	
	/**
	 * 通过作业计划得到作业列表 --作业计划控制页面
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobByJobPlanId(String jobplanId){
		String hql = "SELECT NEW MAP(" 
				+" a.no AS jobNo,"                       //作业ID，其实是作业no
				+" a.TProcessInfo.id AS processId,"     //工序ID
				+" a.name AS joName"                          //作业名称
				+")"
				+" FROM TJobInfo a "
				+" WHERE a.TJobplanInfo.id = '"+jobplanId+"'  ";
		return dao.executeQuery(hql);	
	}
	

	/**
	 * 作业计划控制-- 通过作业计划ID得到零件ID
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPartIdByJobPlanId(String jobplanId){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"                       //作业ID，其实是作业no
				+" a.TPartTypeInfo.id AS partId"         //零件ID
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
				+" a.id AS id,"            //作业计划
				+" a.name AS name," 
				+" a.status AS status,"
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "
				+" a.planNum as planNum,"
				+" a.priority as priority,"
				+" a.finishNum as finishNum,"
				+" a.childrenTotalNum as childrenTotalNum,"
				+" a.qualifiedNum as qualifiedNum,"  //合格数				
				+" b.id as bid,"             //零件
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
	 * 修改工单时得到作业集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobIdMap(){
		//得到作业集合
		String hql = "SELECT NEW MAP(" 
		    	+" j.id as jobid,"                        //作业ID
		    	+" j.planNum as jobplanNum,"        //作业的计划数量
		    	+" j.TJobplanInfo.id as jobplanid,"         //作业计划ID
			    +" j.name as jobname"         //作业名称   
				+")"
				+" FROM TJobInfo j ";
		return dao.executeQuery(hql);
	} 
	
	/**
	 * 获取工单添加时的信息
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getJobdispatchlistInfoForAdd(String jobplanId){//通过作业ID
		Map<String,Object> lst = new HashMap<String,Object>();
		//得到作业集合
		String hql0 = "SELECT NEW MAP(" 
		    	+" j.id as jobid,"                        //作业ID
		    	+" j.planNum as jobplanNum,"        //作业的计划数量
		    	+" j.TJobplanInfo.id as jobplanid,"         //作业计划ID
			    +" j.name as jobname"         //作业名称   
				+")"
				+" FROM TJobInfo j where j.status = 20  ";
		List<Map<String,Object>> lst0 =  dao.executeQuery(hql0);
		lst.put("lst0", lst0);					
		
		if(jobplanId!=null && !"".equals(jobplanId)){
			//通过作业ID得到作业计划的ID和理论工时 
			String hql7 = "SELECT NEW MAP(" 
			    	+" j.id as jobid,"                        //作业ID
			    	+" j.planNum as jobplanNum,"        //作业的计划数量
			    	+" j.TJobplanInfo.id as jobplanid,"             //作业计划ID ===
			    	+" j.theoryWorktime as theoryWorktime,"         //理论工时    ====
				    +" j.name as jobname"         //作业名称   
					+")"
					+" FROM TJobInfo j  "
					+" WHERE j.id = '"+jobplanId+"'  ";	
			List<Map<String,Object>> lst7 =  dao.executeQuery(hql7);
			lst.put("lst7", lst7);
			
			//通过作业ID得到作业的计划数量
			String hql6 = "SELECT NEW MAP(" 
			    	+" j.id as jobid,"                        //作业ID
			    	+" j.planNum as jobplanNum,"        //作业计划数量
				    +" j.name as jobname"         //作业名称   
					+")"
					+" FROM TJobInfo j   "
			        +" WHERE j.id = '"+jobplanId+"'  ";	
			List<Map<String,Object>> lst6 =  dao.executeQuery(hql6);
			lst.put("lst6", lst6);	
			
			//通过作业ID得到设备信息
		String hql5 = "SELECT NEW MAP(" 
				    	+" d.id as cid,"                         //设备类型ID
				    	+" t.equipmentId as equipmentId,"        //设备ID
				    	+" d.equipmentType as equipmentType"     //设备类型名称    
						+")"
						+" FROM TJobInfo j ,TProcessInfo c ,TProcessEquipment t, TEquipmenttypeInfo d"
						+" WHERE j.TProcessInfo.id = c.id   "
						+" AND c.id = t.processId  "
						+" AND t.equipmentTypeId = d.id   ";	
				   if(!"".equals(jobplanId)){
					   hql5 += " AND  j.id =  '"+jobplanId+"'   "; 
				   }
		List<Map<String,Object>> lst5 =  dao.executeQuery(hql5);
		
		//通过作业ID得到物料
		String hql1 = "SELECT NEW MAP(" 
			    	+" t.id as tid,"                         //物料ID
				    +" t.TMaterailTypeInfo.no as tno,"       //物料编号
			    	+" t.TMaterailTypeInfo.name as tname,"   //物料名称
			    	+" t.requirementNum as requirementNum"   //物料需求数      
					+")"
					+" FROM TJobInfo j ,TProcessInfo c,TProcessplanInfo b , TProcessmaterialInfo t  "
					+" WHERE j.TProcessInfo.id = c.id AND c.TProcessplanInfo.id = b.id  AND b.materialId = t.id  ";	
			   if(!"".equals(jobplanId)){
				   hql1 += " AND  j.id =  '"+jobplanId+"'   "; 
			   }
		 List<Map<String,Object>> lst1 =  dao.executeQuery(hql1);
		 
		 //通过作业ID子作业详情
		 String hql2 = "SELECT NEW MAP(" 
					+" DATE_FORMAT(j.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //作业--计划开始时间
					+" DATE_FORMAT(j.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //作业--计划结束时间
					+" j.planNum as jplanNum"                                          //作业--计划数量
					+")"
					+" FROM TJobInfo j   "
			        +" WHERE j.id = '"+jobplanId+"'  ";	
			List<Map<String,Object>> lst2 =  dao.executeQuery(hql2);
			 //通过作业ID子工艺详情
		 String hql3 = "SELECT NEW MAP(" 
				        +" c.TProcessplanInfo.name as pname,"  //工艺方案名称
				        +" c.theoryWorktime as theoryWorktime,"  //工艺节拍时间
						+" c.file as file"             //工艺指导文件
						+")"
						+" FROM TJobInfo j ,TProcessInfo c   "
				        +" WHERE j.TProcessInfo.id = c.id  AND  j.id = '"+jobplanId+"'  ";	
			List<Map<String,Object>> lst3 =  dao.executeQuery(hql3);
			//通过作业ID子零件详情
		 String hql4 = "SELECT NEW MAP(" 
				        +" a.TPartTypeInfo.id as partid,"     //零件ID
				        +" a.TPartTypeInfo.name as partname"  //零件名称
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
	 * 获取工单添加时的信息--设备类型
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getEquimentAndType(String typeId){
		String hql = "SELECT NEW MAP(" 
                +" b.id as id,"             //设备类型ID
				+" b.equipmentType as equipmentType "             //设备类型名称
				+")"
				+" FROM TEquipmenttypeInfo b "
				+" WHERE 1=1  ";
		   if(typeId!=null && !"".equals(typeId)){
			   hql += " AND  b.id =  '"+typeId+"'   "; 
		   }
		return dao.executeQuery(hql);	
	}
	
	/**
	 * 获取工单添加时的信息--设备名称
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getEquimentByType(String nodeid,String typeId){
		String hql = "SELECT NEW MAP(" 
				+" a.equId AS equId,"                //设备ID
				+" a.equName as equName,"            //设备名称
                +" b.id as id,"             //设备类型ID
				+" b.equipmentType as equipmentType "             //设备类型名称
				+")"
				+" FROM TEquipmentInfo a , TEquipmenttypeInfo b "
				+" WHERE a.equTypeId = b.id  AND a.TNodes.TNodes.nodeId ='"+nodeid+"' ";
		   if(typeId!=null && !"".equals(typeId)){
			   hql += " AND  b.id =  '"+typeId+"'   "; 
		   }
		return dao.executeQuery(hql);	
	}
	
	
	/**
	 * 通过名称查询，判断是否多次提交工单
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobdispatchlistByName(String name){ 
		
		String hql = "SELECT NEW MAP(" 
				+" a.id AS id,"            //工单
				+" a.name AS name" 
				+" )"
				+" FROM TJobdispatchlistInfo a "
				+" WHERE a.name = '"+name+"'  ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 通过ID跳转到工单修改页面
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobdispatchlistById(String id){
		String hql = "SELECT NEW MAP(" 
				+" a.name AS name,"                                                //工单名称
				+" a.TJobInfo.id as id,"                                           //作业ID
				+" a.no as no,"                                                    //工单编号
                +" a.processNum as processNum,"                                    //工单计划数
                +" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //工单计划开始时间
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //工单计划结束时间
				+" a.remark as remark "                                            //工单详细信息
				+")"
				+" FROM TJobdispatchlistInfo a  "
				+" WHERE a.id = '"+id+"'   ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 工单统计，通过任务号和时间
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
				+" a.id AS dispatchid,"                                             //工单ID
				+" a.no AS dispatchno,"                                             //工单编号
				+" a.name AS name,"                                                 //工单名称
				+" a.TEquipmenttypeInfo.equipmentType AS equName,"                  //设备名称
				+" a.status AS status,"                                             //工单状态				
                +" a.processNum as processNum,"                                    //工单计划数
                +" a.finishNum as finishNum,"                                      //工单完工数
                +" DATE_FORMAT(a.planStarttime,'%Y-%m-%d') AS planStarttime,"   //工单计划开始时间
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d') as planEndtime, "      //工单计划结束时间
				+" DATE_FORMAT(a.realStarttime,'%Y-%m-%d') AS realStarttime,"   //工单实际开始时间
				+" DATE_FORMAT(a.realEndtime,'%Y-%m-%d') as realEndtime, "      //工单实际结束时间
				                                                           			//效期预警
				+" a.remark as remark,"                                            //工单详细信息
				+" a.taskNum as taskNum"										   //工单任务号
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
	
	//工单统计 根据任务号得到
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getjobdispatchByTaskNum(){
		String hql = "select distinct new Map(taskNum as taskNum) from TJobdispatchlistInfo";
		return dao.executeQuery(hql);
	}

	/**
	 * 工单队列管理--已分配工单清单
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getAlreadyDispatchList(String nodeid){
		/*
		String hql = "SELECT NEW MAP(" 
				+" a.no AS no,"                                                    //工单号
				+" b.TProcessInfo.name AS name,"                                   //工序名称
				+" b.id as jobid,"                                                 //作业ID
				+" b.no as jobno,"                                                 //作业编号
				+" a.TEquipmentInfo.equId as equId,"                               //设备ID
				+" a.TEquipmentInfo.equName as equName,"                           //设备名称
                +" a.processNum as processNum,"                                    //工单--计划数
                +" DATE_FORMAT(a.planStarttime,'%Y-%m-%d') AS planStarttime,"   //工单计划开始时间
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d') as planEndtime, "      //工单计划完成时间
				+" a.finishNum as finishNum "                                      //工单--已完成量
				+")"
				+" FROM TJobdispatchlistInfo a, TJobInfo b  "
				+" WHERE  a.TJobInfo.id  =  b.id "
//				+" AND  (a.status = 30  OR  a.status = 40 OR  a.status = 50  ) " //这里是已经分配的，去除了就是所有的工单
                +" AND  b.TJobplanInfo.nodeid = :nodeid ";  //添加了节点
		*/
		String hql = "SELECT NEW MAP(" 
				+" a.no AS no,"                                                    //工单号
				+" p.name AS name,"                                               //工序名称
		//		+" b.id as jobid,"                                                 //作业ID
		//		+" b.no as jobno,"                                                 //作业编号
		//		+" a.TEquipmentInfo.equId as equId,"                               //设备ID
		//		+" a.TEquipmentInfo.equName as equName,"                           //设备名称
				+" c.equipmentType as equName,"                    //设备类型名称
                +" a.processNum as processNum,"                                    //工单--计划数
                +" DATE_FORMAT(a.planStarttime,'%Y-%m-%d') AS planStarttime,"   //工单计划开始时间
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d') as planEndtime, "      //工单计划完成时间
				+" a.finishNum as finishNum "                                      //工单--已完成量
				+")"
				+" FROM TJobdispatchlistInfo a,TEquipmenttypeInfo c,TProcessInfo p  "
				+" WHERE  c.id  =  a.TEquipmenttypeInfo.id "
				+ "AND a.TProcessInfo.id = p.id "
//				+" AND  (a.status = 30  OR  a.status = 40 OR  a.status = 50  ) " //这里是已经分配的，去除了就是所有的工单
                +" AND  a.nodeid = :nodeid ";  //添加了节点
          Collection<Parameter> parameters = new HashSet<Parameter>();
          parameters.add(new Parameter("nodeid",nodeid,Operator.EQ));
		return dao.executeQuery(hql,parameters);
	}
	
	/**
	 * 工单队列管理--作业计划详情
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobPlanDetail(String jobplanId){
		String hql = "SELECT NEW MAP(" 
				+" a.id AS jobplanid,"                                             //作业计划ID
				+" a.process AS process,"                                          //进度
				+" a.planNo AS userProdPlanId,"        							   //生产计划编号
				+" a.TPartTypeInfo.id AS parttypeid,"                              //零件类型ID
				+" a.name AS name,"                                                //作业计划名称
                +" a.planNum as planNum,"                                          //作业计划计划数
                +" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //作业计划开始时间
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //作业计划结束时间
				+" a.priority as priority,"                                        //优先级
				+" a.finishNum as finishNum "                                      //作业计划已完成量
				+")"
				+" FROM TJobplanInfo a  "
				+" WHERE a.id = '"+jobplanId+"'   ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 工单创建--始末时间的工单开始时间为对应计划的开始时间
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
	 * 获取从属生产名称
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
	 * 更改作业计划表的状态
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
	 * 作业计划控制 -- 获取零件类型集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPartTypeMap(){
		String hql = "SELECT NEW MAP(" 
				+" c.id as id,"     //零件类型ID
				+" c.name as name"  //零件类型名称
				+")"
				+" FROM TPartTypeInfo c ";	
		return dao.executeQuery(hql);	
	}
	
	/**
	 * 作业计划控制 -- 获取作业计划集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getJobPlanMap(){
		String hql = "SELECT NEW MAP(" 
				+" c.id as id,"         //作业计划ID
				+" c.planNo as planNo," //作业计划编号
				+" c.name as name"      //作业计划名称
				+")"
				+" FROM TJobplanInfo c WHERE (c.status = 20  OR  c.status = 30 OR  c.status = 40 OR  c.status = 50)";	//创建，完成，结束不要
		return dao.executeQuery(hql);	
	}
	
	/**
	 * 作业计划控制 -- 获取工艺方案集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProcessplanMap(String partid){
		String hql = "SELECT NEW MAP(" 
				+" c.id as id,"     //工艺ID
				+" c.name as name"  //工艺名称
				+")"
				+" FROM TProcessplanInfo c "
				+ " where c.defaultSelected=1";
		if(!StringUtil.isEmpty(partid)){
			hql += " and  c.TPartTypeInfo.id = '"+partid+"' ";
		}
		return dao.executeQuery(hql);	
	}
	
	/**
	 * 作业计划控制 -- 通过作业计划Id获取零件类型集合
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPartTypeMapByJobPlanid(String jobplanid){
		String hql = "SELECT NEW MAP(" 
				+" c.TPartTypeInfo.id as id,"         //零件ID
				+" c.TPartTypeInfo.name as name"      //零件名称
				+")"
				+" FROM TJobplanInfo c ";
		if(!StringUtil.isEmpty(jobplanid)){
			hql += " WHERE  c.id = '"+jobplanid+"' ";
		}
		return dao.executeQuery(hql);	
	}
	
    /**
     * 作业计划控制 --投料批次列表
     * @param processplanId 工艺方案ID
     */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getBatchList(String processplanId,String jobplabid){
		String hql = "SELECT NEW MAP(" 
				+" a.no as no,"     //工单编号--》首工位工单编号
				+" a.processNum as processNum,"  //工单数量
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d') as planStarttime"  //开始时间
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
	 * 作业计划控制 --通过工艺方案ID得到工序清单
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getProcessByProcessPlanId(String nodeid,String processPlanId){
		String hql = "SELECT NEW MAP(" 
				+" c.id as id,"            //工序ID ===>
				+" c.no as no,"            //工序编码
				+" c.name as cname,"       //工序名称
				+" c.theoryWorktime as theoryWorktime,"   //节拍时间
				+" c.processDuration as processDuration," //工序连续
				+" c.TProcessplanInfo.id as pid,"     //工艺方案ID
				+" c.TProcessplanInfo.name as pname,"  //工艺方案名称
				+" b.id as bid "             //工艺方案ID
				+")"
				+" FROM TProcessplanInfo b ,TProcessInfo c "
				+" WHERE  b.id=c.TProcessplanInfo.id "
				+ "AND  c.status = 0  "
				+ "AND b.id = '"+processPlanId+"' ";
		List<Map<String,Object>> lst = dao.executeQuery(hql);
		for(Map<String,Object> m : lst){
			String id = m.get("id").toString(); //工序ID 
			String sql = "SELECT NEW MAP("
					+" d.equId as equId,"                 //设备ID
					+" d.equName as equName,"             //设备名称
					+" d.equSerialNo as equSerialNo"      //设备序列号
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
	public List<Map<String,Object>> getSerNoByProcessId(String processNo){//工序ID
		String hql = "SELECT NEW MAP(" 
				+" d.equId as equId,"             //设备ID
				+" d.equName as equName,"             //设备名称
				+" d.equSerialNo as equSerialNo"             //设备序列号
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
				+" d.equId as equId,"             //设备ID
				+" d.equName as equName,"             //设备名称
				+" d.equSerialNo as equSerialNo"             //设备序列号
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
				+" a.id AS id,"            //作业计划
				+" a.no AS no,"            //作业计划编号===>
				+" a.name AS name," 
				+" DATE_FORMAT(a.planStarttime,'%Y-%m-%d %T') AS planStarttime,"   //yao
				+" DATE_FORMAT(a.planEndtime,'%Y-%m-%d %T') as planEndtime, "      //yao
				+" a.planNum as planNum,"                                          //yao
				+" a.priority as priority,"
				+" a.TPartTypeInfo.id as partid,"    //零件ID
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
     * 通过id查询作业计划信息
     * @param id 作业计划ID
     * @return 返回作业计划信息
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
			rsmap.put("taskNO", erpMap.get("taskNO"));//添加erp的数据
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
			rsmap.put("partId", gx.get("partId"));//添加物料id
			rsmap.put("itemCode", gx.get("itemCode"));//添加物料编码
			rsmap.put("itemDesc", gx.get("itemDesc"));//添加物料描述
			rsmap.put("toOperationNum", gx.get("processOrder"));//添加 至工序
			rsmap.put("onlineProcessId",gx.get("onlineProcessId"));
		}else{
			rsmap.put("itemCode", "");//添加物料编码
			rsmap.put("itemDesc", "");//添加物料描述
			rsmap.put("toOperationNum", "");//添加 至工序
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
			rsmap.put("zrOperationNum", zrgxrs);//添加责任工序列表
			Map<String,Object> mp=zrgxrs.get(0);
			rsmap.put("processPlanID", mp.get("processPlanID"));
		}
		return rsmap;
	}



}

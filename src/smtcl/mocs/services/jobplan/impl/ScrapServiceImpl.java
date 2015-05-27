package smtcl.mocs.services.jobplan.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;
import org.springframework.transaction.annotation.Transactional;

import smtcl.mocs.dao.device.ICommonDao;
import smtcl.mocs.model.TProductionScrapInfoModel;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProductionEvents;
import smtcl.mocs.services.jobplan.IScrapSerice;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;


public class ScrapServiceImpl extends GenericServiceSpringImpl<TNodes, String> implements IScrapSerice {
	private ICommonDao commonDao;
	public ICommonDao getCommonDao() {
		return commonDao;
	}
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	/**
	 *  保存生产报废
	 * @param tps 保存实体类
	 * @param jobdispatchNo 当前选中的工单号
	 * @param isCurrentProcess 是否至工序已加工·
	 * @param processScrapNum 工废
	 * @param materialScrapNum 料废
	 * @param onlineProcessId 前道工序id
	 * @param selectedPartId 零件类型id
	 * @param djgs 当件工时
	 * @param selectedpl 工序集合
	 * @return
	 */
	public String saveTProductionScrapInfo(TProductionScrapInfoModel tps,String jobdispatchNo,List isCurrentProcess,String processScrapNum,
			String materialScrapNum,String onlineProcessId,String processPlanID,String selectedPartId,String djgs,List selectedpl){
		String wy=StringUtils.getUniqueString();//唯一标识
		processScrapNum=null==processScrapNum||"".equals(processScrapNum)?"0":processScrapNum;
		materialScrapNum=null==materialScrapNum||"".equals(materialScrapNum)?"0":materialScrapNum;
		int SumScrapNum=Integer.parseInt(processScrapNum)+Integer.parseInt(materialScrapNum);//总报废数
		try {
			String tjihql="from TJobdispatchlistInfo where no='"+jobdispatchNo+"'";
			List<TJobdispatchlistInfo> tjilist=commonDao.executeQuery(tjihql);
			TJobdispatchlistInfo tji=new TJobdispatchlistInfo();
			if(null!=tjilist&&tjilist.size()>0){
				tji=tjilist.get(0);//查找工单	
			}
			//查找批次计划
			String jobplansql="from TJobplanInfo where id="+tji.getJobplanId();
			TJobplanInfo tjpi=(TJobplanInfo)commonDao.executeQuery(jobplansql).get(0);
			//查找前道工序
			String onlinehql=" from TJobdispatchlistInfo a"
					  + " where a.TProcessInfo.id='"+onlineProcessId+"'"
					  + " and a.batchNo='"+tji.getBatchNo()+"'"; 
			List<TJobdispatchlistInfo> onlinelist=commonDao.executeQuery(onlinehql);//查找 前道工序
			if(isCurrentProcess.size()>0){//判断是否勾选至工序已加工
				if((tji.getFinishNum()-Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum))<0){
					return "最多可以报废数:"+tji.getFinishNum();
				}else{
					//计算工时
					String totleTime=djgs;
					
					//保存工废	
					if(null!=processScrapNum&&!"".equals(processScrapNum)&&!"0".equals(processScrapNum)){
						//--------------------------------------wis 保存报废---------------------------------------
						String wistpesql="from TProductionEvents where eventNo='Bz_"+tps.getTzdCode()+"_1'";
						List wistpelist=commonDao.executeQuery(wistpesql);
						if(null!=wistpelist&&wistpelist.size()>0){
							return "wis库中已存在该报废信息,请重新输入报废单号。";
						}else{
							this.saveTProductionEvents(tps, jobdispatchNo, processScrapNum, djgs, selectedPartId, 1);
						}
						//--------------------------------------wis 保存报废---------------------------------------
						//添加工单报废
						tji.setErpScrapNum(tji.getErpScrapNum()+Integer.parseInt(processScrapNum));
						tji.setWisScrapNum(tji.getWisScrapNum()+Integer.parseInt(processScrapNum));
						tji.setFinishNum(tji.getFinishNum()-Integer.parseInt(processScrapNum));
						//添加批次报废
						tjpi.setScrapNum((null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(processScrapNum));
						//添加责任报废
						/*
						String hql="from TJobdispatchlistInfo a where a.TProcessInfo.processOrder="+tps.getZrOperationNum()+""
								+ " and a.TProcessInfo.TProcessplanInfo.id="+processPlanID
								+ " and a.batchNo='"+tji.getBatchNo()+"'";
						List<TJobdispatchlistInfo> rstjlist=commonDao.executeQuery(hql);
						if(null!=rstjlist&&rstjlist.size()>0){
							TJobdispatchlistInfo tjob=rstjlist.get(0);
							tjob.setDutyScrapNum(tjob.getDutyScrapNum()+Integer.parseInt(processScrapNum));
							commonDao.update(TJobdispatchlistInfo.class, tjob);
						}*/

					}				
					
					if(null!=materialScrapNum&&!"".equals(materialScrapNum)&&!"0".equals(materialScrapNum)){//保存料废
						//--------------------------------------wis 保存报废---------------------------------------
						String wistpesql="from TProductionEvents where eventNo='Bz_"+tps.getTzdCode()+"_2'";
						List wistpelist=commonDao.executeQuery(wistpesql);
						if(null!=wistpelist&&wistpelist.size()>0){
							return "wis库中已存在该报废信息,请重新输入报废单号。";
						}else{
							this.saveTProductionEvents(tps, jobdispatchNo, materialScrapNum, djgs, selectedPartId,4);
						}
						//--------------------------------------wis 保存报废---------------------------------------
						//添加工单报废
						tji.setErpScrapNum(tji.getErpScrapNum()+Integer.parseInt(materialScrapNum));
						tji.setWisScrapNum(tji.getWisScrapNum()+Integer.parseInt(materialScrapNum));
						tji.setFinishNum(tji.getFinishNum()-Integer.parseInt(materialScrapNum));
						//添加批次报废
						tjpi.setScrapNum((null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(materialScrapNum));
					}
					//判断工单是否完成
					if(tji.getProcessNum().intValue()-tji.getFinishNum().intValue()-tji.getWisScrapNum().intValue()<=0){
						tji.setStatus(70);
						tji.setRealEndtime(new Date());

					}
					//判断是否存在前序工单
					if(null!=onlinelist&&onlinelist.size()>0){
						TJobdispatchlistInfo mm=(TJobdispatchlistInfo)onlinelist.get(0);
						//判断工单是否完成
						//前序工单的完成数=本序工单的完成数+本序工单的发现报废数，且前序工单状态已为被关闭（状态=结束）时，本序工单被认为已无需工人操作，机床上不再可看到工单，状态变为完成
						if((mm.getFinishNum().intValue()-tji.getFinishNum().intValue()-tji.getWisScrapNum().intValue())<=0&&
								(mm.getStatus()==60||mm.getStatus()==70)){
							tji.setStatus(70);
							tji.setRealEndtime(new Date());

						}
					}
					//保存工单
					commonDao.update(TJobdispatchlistInfo.class,tji);
					//保存批次
					commonDao.update(TJobplanInfo.class,tjpi);
					//判断该批次是否全部完成
					if(null!=onlinelist&&onlinelist.size()>0){
						String processsql="from TProcessInfo where id="+tji.getTProcessInfo().getId();
						TProcessInfo tp=(TProcessInfo)commonDao.executeQuery(processsql).get(0);
						//判断是否尾序
						if(tp.getOfflineProcess()==1){
							//当批次的总发现报废数和尾序的完成数之和等于批次计划数量时，调度员已有理由关闭该批次（仅在界面中提示，暂不对该状态命名和控制变化）在关闭批次时，整组工单都关闭（状态=结束）
							if(tjpi.getPlanNum().intValue()-tjpi.getScrapNum().intValue()-tji.getFinishNum().intValue()<=0){
								String jobdispatchsql="from TJobdispatchlistInfo where jobplanId='"+tjpi.getId()+"'";
								List<TJobdispatchlistInfo> tjobdispathclist=commonDao.executeQuery(jobdispatchsql);
								for(TJobdispatchlistInfo tj:tjobdispathclist){
									if(tj.getStatus()!=60||tj.getStatus()!=70)
									{
										tj.setStatus(70);
										tj.setRealEndtime(new Date());
										commonDao.update(TJobdispatchlistInfo.class, tj);
									}
								}
							}
						}
					}
					
				}
			}else{
				//如果存在前道工序
				if(null!=onlinelist&&onlinelist.size()>0){
					TJobdispatchlistInfo mm=(TJobdispatchlistInfo)onlinelist.get(0);
					String onlinecount=mm.getFinishNum()+"";
					int count=Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum);
					//前工序完成数-当前工序完成数-当前工序报废数   算出可报废数量
					if((Integer.parseInt(onlinecount)-tji.getFinishNum()-tji.getWisScrapNum()-count)<0){
						return "最多可以报废数:"+(Integer.parseInt(onlinecount)-tji.getFinishNum()-tji.getWisScrapNum());
					}
				}
				if((tji.getProcessNum()-tji.getFinishNum()-Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum))<0){
					int count=tji.getProcessNum()-tji.getFinishNum();
					return "最多可以报废数:"+count+"";
				}else{
					//计算工时
					String totleTime=djgs;
					
					//保存工废	处理
					if(null!=processScrapNum&&!"".equals(processScrapNum)&&!"0".equals(processScrapNum)){
						//--------------------------------------wis 保存报废---------------------------------------
						String wistpesql="from TProductionEvents where eventNo='Bz_"+tps.getTzdCode()+"_1'";
						List wistpelist=commonDao.executeQuery(wistpesql);
						if(null!=wistpelist&&wistpelist.size()>0){
							return "wis库中已存在该报废信息,请重新输入报废单号。";
						}else{
							this.saveTProductionEvents(tps, jobdispatchNo, processScrapNum, djgs, selectedPartId, 1);
						}
						//--------------------------------------wis 保存报废---------------------------------------
						
						//添加工单报废
						tji.setErpScrapNum(tji.getErpScrapNum()+Integer.parseInt(processScrapNum));
						tji.setWisScrapNum(tji.getWisScrapNum()+Integer.parseInt(processScrapNum));
						//添加批次报废
						tjpi.setScrapNum((null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(processScrapNum));
						//添加责任报废
						/*String hql="from TJobdispatchlistInfo a where a.TProcessInfo.processOrder="+tps.getZrOperationNum()+""
								+ " and a.TProcessInfo.TProcessplanInfo.id="+processPlanID
						 	    + " and a.batchNo='"+tji.getBatchNo()+"'"; 
						List<TJobdispatchlistInfo> rstjlist=commonDao.executeQuery(hql);
						if(null!=rstjlist&&rstjlist.size()>0){
							TJobdispatchlistInfo tjobtest=rstjlist.get(0);
							tjobtest.setDutyScrapNum((null==tjobtest.getDutyScrapNum()?0:tjobtest.getDutyScrapNum())+Integer.parseInt(processScrapNum));
							commonDao.update(TJobdispatchlistInfo.class, tjobtest);
						}*/
					}
					//保存料废处理
					if(null!=materialScrapNum&&!"".equals(materialScrapNum)&&!"0".equals(materialScrapNum)){
						//--------------------------------------wis 保存报废---------------------------------------
						String wistpesql="from TProductionEvents where eventNo='Bz_"+tps.getTzdCode()+"_2'";
						List wistpelist=commonDao.executeQuery(wistpesql);
						if(null!=wistpelist&&wistpelist.size()>0){
							return "wis库中已存在该报废信息,请重新输入报废单号。";
						}else{
							this.saveTProductionEvents(tps, jobdispatchNo, materialScrapNum, djgs, selectedPartId,4);
						}
						//--------------------------------------wis 保存报废---------------------------------------
						//添加工单报废
						tji.setErpScrapNum(tji.getErpScrapNum()+Integer.parseInt(materialScrapNum));
						tji.setWisScrapNum(tji.getWisScrapNum()+Integer.parseInt(materialScrapNum));
						//添加批次报废
						tjpi.setScrapNum((null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(materialScrapNum));
						
					}
					
					//判断工单是否完成
					if(tji.getProcessNum().intValue()-tji.getFinishNum().intValue()-tji.getWisScrapNum().intValue()<=0){
						tji.setStatus(70);
						tji.setRealEndtime(new Date());

					}
					//判断是否存在前序工单
					if(null!=onlinelist&&onlinelist.size()>0){
						TJobdispatchlistInfo mm=(TJobdispatchlistInfo)onlinelist.get(0);
						//判断工单是否完成
						//前序工单的完成数=本序工单的完成数+本序工单的发现报废数，且前序工单状态已为被关闭（状态=结束）时，本序工单被认为已无需工人操作，机床上不再可看到工单，状态变为完成
						if((mm.getFinishNum().intValue()-tji.getFinishNum().intValue()-tji.getWisScrapNum().intValue())<=0&&
								(mm.getStatus()==60||mm.getStatus()==70)){
							tji.setStatus(70);
							tji.setRealEndtime(new Date());

						}
					}
					//保存工单
					commonDao.update(TJobdispatchlistInfo.class,tji);
					//保存批次
					commonDao.update(TJobplanInfo.class,tjpi);
					//判断该批次是否全部完成
					if(null!=onlinelist&&onlinelist.size()>0){
						String processsql="from TProcessInfo where id="+tji.getTProcessInfo().getId();
						TProcessInfo tp=(TProcessInfo)commonDao.executeQuery(processsql).get(0);
						//判断是否尾序
						if(tp.getOfflineProcess()==1){
							//当批次的总发现报废数和尾序的完成数之和等于批次计划数量时，调度员已有理由关闭该批次（仅在界面中提示，暂不对该状态命名和控制变化）在关闭批次时，整组工单都关闭（状态=结束）
							if(tjpi.getPlanNum().intValue()-tjpi.getScrapNum().intValue()-tji.getFinishNum().intValue()<=0){
								String jobdispatchsql="from TJobdispatchlistInfo where jobplanId='"+tjpi.getId()+"'";
								List<TJobdispatchlistInfo> tjobdispathclist=commonDao.executeQuery(jobdispatchsql);
								for(TJobdispatchlistInfo tj:tjobdispathclist){
									if(tj.getStatus()!=60||tj.getStatus()!=70)
									{
										tj.setStatus(70);
										tj.setRealEndtime(new Date());
										commonDao.update(TJobdispatchlistInfo.class, tj);
									}

								}
							}
						}
					}
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return "保存成功";
	}
	
	public void saveTProductionEvents(TProductionScrapInfoModel tps,String jobdispatchNo,String processScrapNum,String djgs,
			String selectedPartId,int scriptType){
		TProductionEvents wisTpe=new TProductionEvents();
		
		wisTpe.setJobdispatchNo(jobdispatchNo);
		wisTpe.setProcessNum(Integer.parseInt(processScrapNum));
		TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		String sql="SELECT b.no FROM t_user a,t_member_info b"
					+"	WHERE a.memberid=b.id"
					+"	and a.UserID="+user.getUserId();
		List<Map<String,Object>> userlist=commonDao.executeNativeQuery(sql);
		if(null!=userlist&&userlist.size()>0){
			Map<String,Object> userMap=userlist.get(0);
			wisTpe.setOperatorNo(userMap.get("no").toString());//工人号
		}
		wisTpe.setWorkTime(Integer.parseInt(djgs));
		wisTpe.setOperateDate(new Date());
		wisTpe.setOperateReason(tps.getReson());
		wisTpe.setPartTypeID(Integer.parseInt(selectedPartId));
		if(scriptType==1){
			wisTpe.setEventNo("BF_"+tps.getTzdCode()+"_1");
			String sql2="SELECT b.no FROM t_user a,t_member_info b"
					+"	WHERE a.memberid=b.id"
					+"	and a.UserID="+tps.getResponser();
			List<Map<String,Object>> userlist2=commonDao.executeNativeQuery(sql);
			if(null!=userlist2&&userlist2.size()>0){
				Map<String,Object> userMap=userlist2.get(0);
				//负责人工号
				wisTpe.setResponseNo(userMap.get("no").toString());
			}
			wisTpe.setEventType(1);//设置为工废
		}else if(scriptType==4){
			wisTpe.setEventNo("BF_"+tps.getTzdCode()+"_2");
			//责任厂家
			wisTpe.setResponseNo(tps.getVendor());
			wisTpe.setEventType(4);//设置为料废
		}
		wisTpe.setResponseProcessNo(tps.getZrOperationNum());
		commonDao.save(TProductionEvents.class, wisTpe);
	}
	/**
	 * 获取工单提示信息
	 * @param jobdispatchNo
	 * @param onlineProcessId
	 * @param processScrapNum
	 * @param materialScrapNum
	 * @param isCurrentProcess
	 * @return
	 */
	
	public String getJobdispatchTSXX(String jobdispatchNo,String onlineProcessId,String processScrapNum,String materialScrapNum,
			List isCurrentProcess){
		processScrapNum=null==processScrapNum||"".equals(processScrapNum)?"0":processScrapNum;
		materialScrapNum=null==materialScrapNum||"".equals(materialScrapNum)?"0":materialScrapNum;
		try {
			String tjihql="from TJobdispatchlistInfo where no='"+jobdispatchNo+"'";
			List<TJobdispatchlistInfo> tjilist=commonDao.executeQuery(tjihql);
			TJobdispatchlistInfo tji=new TJobdispatchlistInfo();
			if(null!=tjilist&&tjilist.size()>0){
				tji=tjilist.get(0);//查找工单	
			}
			//查找批次计划
			String jobplansql="from TJobplanInfo where id="+tji.getJobplanId();
			TJobplanInfo tjpi=(TJobplanInfo)commonDao.executeQuery(jobplansql).get(0);
			//查找前道工序
			String onlinehql=" from TJobdispatchlistInfo a"
					  + " where a.TProcessInfo.id='"+onlineProcessId+"'"
					  + " and a.batchNo='"+tji.getBatchNo()+"'"; 
			List<TJobdispatchlistInfo> onlinelist=commonDao.executeQuery(onlinehql);//查找 前道工序
			String resourceCode="test";//资源代码  算法暂未实现
			if(isCurrentProcess.size()>0){//判断是否勾选至工序已加工
				if((tji.getFinishNum()-Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum))<0){
					return "最多可以报废数:"+tji.getFinishNum();
				}else{
					//判断该批次是否全部完成
					if(null!=onlinelist&&onlinelist.size()>0){
						String processsql="from TProcessInfo where id="+tji.getTProcessInfo().getId();
						TProcessInfo tp=(TProcessInfo)commonDao.executeQuery(processsql).get(0);
						//判断是否尾序
						if(tp.getOfflineProcess()==1){
							//当批次的总发现报废数和尾序的完成数之和等于批次计划数量时，调度员已有理由关闭该批次（仅在界面中提示，暂不对该状态命名和控制变化）在关闭批次时，整组工单都关闭（状态=结束）
							int pc=(null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(materialScrapNum)+Integer.parseInt(processScrapNum);
							if(tjpi.getPlanNum()-pc-tji.getFinishNum()<=0){
								return "关闭工单";
							}
						}
					}
				}
			}else{
				//如果存在前道工序
				if(null!=onlinelist&&onlinelist.size()>0){
					TJobdispatchlistInfo mm=(TJobdispatchlistInfo)onlinelist.get(0);
					String onlinecount=mm.getFinishNum()+"";
					int count=Integer.parseInt(processScrapNum)+Integer.parseInt(materialScrapNum);
					//a=前工序完成数-当前工序完成数-当前工序报废数  算出可报废数量
					if((Integer.parseInt(onlinecount)-tji.getFinishNum()-tji.getWisScrapNum()-count)<0){
						return "最多可以报废数:"+(Integer.parseInt(onlinecount)-tji.getFinishNum()-tji.getWisScrapNum());
					}
				}
				if((tji.getProcessNum()-tji.getFinishNum()-Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum))<0){
					int count=tji.getProcessNum()-tji.getFinishNum();
					return "最多可以报废数:"+count+"";
				}else{
					//判断该批次是否全部完成
					if(null!=onlinelist&&onlinelist.size()>0){
						String processsql="from TProcessInfo where id="+tji.getTProcessInfo().getId();
						TProcessInfo tp=(TProcessInfo)commonDao.executeQuery(processsql).get(0);
						//判断是否尾序
						if(tp.getOfflineProcess()==1){
							//当批次的总发现报废数和尾序的完成数之和等于批次计划数量时，调度员已有理由关闭该批次（仅在界面中提示，暂不对该状态命名和控制变化）在关闭批次时，整组工单都关闭（状态=结束）
							int pc=(null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(materialScrapNum)+Integer.parseInt(processScrapNum);
							if(tjpi.getPlanNum()-pc-tji.getFinishNum()<=0){
								return "关闭工单";
							}
						}
					}
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return "正常保存";
	}
	
	
	/**
	 * 根据id节点id获取零件列表
	 * @param nodeid
	 * @return
	 */
	public List<Map<String,Object>> getTPartTypeInfoByNodeid(String nodeid,String partId){
		   Collection<Parameter> parameters = new HashSet<Parameter>();
			String hql="select new Map("
					+ " name as name,"
					+ " id as id,"
					+ " no as no)"
					+ " from TPartTypeInfo where nodeid='"+nodeid+"'";
			if(null!=partId&&!"".equals(partId)){
				hql+= " and  id='"+partId+"'";
			}
			return commonDao.executeQuery(hql, parameters);
			
		}
	
	/**
	 * 根据零件编号获取 批次计划
	 * @param partId
	 * @return
	 */
	public List<Map<String,Object>> getJopPlanByPartId(String partId){
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

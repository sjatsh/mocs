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
	 *  ������������
	 * @param tps ����ʵ����
	 * @param jobdispatchNo ��ǰѡ�еĹ�����
	 * @param isCurrentProcess �Ƿ��������Ѽӹ���
	 * @param processScrapNum ����
	 * @param materialScrapNum �Ϸ�
	 * @param onlineProcessId ǰ������id
	 * @param selectedPartId �������id
	 * @param djgs ������ʱ
	 * @param selectedpl ���򼯺�
	 * @return
	 */
	public String saveTProductionScrapInfo(TProductionScrapInfoModel tps,String jobdispatchNo,List isCurrentProcess,String processScrapNum,
			String materialScrapNum,String onlineProcessId,String processPlanID,String selectedPartId,String djgs,List selectedpl){
		String wy=StringUtils.getUniqueString();//Ψһ��ʶ
		processScrapNum=null==processScrapNum||"".equals(processScrapNum)?"0":processScrapNum;
		materialScrapNum=null==materialScrapNum||"".equals(materialScrapNum)?"0":materialScrapNum;
		int SumScrapNum=Integer.parseInt(processScrapNum)+Integer.parseInt(materialScrapNum);//�ܱ�����
		try {
			String tjihql="from TJobdispatchlistInfo where no='"+jobdispatchNo+"'";
			List<TJobdispatchlistInfo> tjilist=commonDao.executeQuery(tjihql);
			TJobdispatchlistInfo tji=new TJobdispatchlistInfo();
			if(null!=tjilist&&tjilist.size()>0){
				tji=tjilist.get(0);//���ҹ���	
			}
			//�������μƻ�
			String jobplansql="from TJobplanInfo where id="+tji.getJobplanId();
			TJobplanInfo tjpi=(TJobplanInfo)commonDao.executeQuery(jobplansql).get(0);
			//����ǰ������
			String onlinehql=" from TJobdispatchlistInfo a"
					  + " where a.TProcessInfo.id='"+onlineProcessId+"'"
					  + " and a.batchNo='"+tji.getBatchNo()+"'"; 
			List<TJobdispatchlistInfo> onlinelist=commonDao.executeQuery(onlinehql);//���� ǰ������
			if(isCurrentProcess.size()>0){//�ж��Ƿ�ѡ�������Ѽӹ�
				if((tji.getFinishNum()-Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum))<0){
					return "�����Ա�����:"+tji.getFinishNum();
				}else{
					//���㹤ʱ
					String totleTime=djgs;
					
					//���湤��	
					if(null!=processScrapNum&&!"".equals(processScrapNum)&&!"0".equals(processScrapNum)){
						//--------------------------------------wis ���汨��---------------------------------------
						String wistpesql="from TProductionEvents where eventNo='Bz_"+tps.getTzdCode()+"_1'";
						List wistpelist=commonDao.executeQuery(wistpesql);
						if(null!=wistpelist&&wistpelist.size()>0){
							return "wis�����Ѵ��ڸñ�����Ϣ,���������뱨�ϵ��š�";
						}else{
							this.saveTProductionEvents(tps, jobdispatchNo, processScrapNum, djgs, selectedPartId, 1);
						}
						//--------------------------------------wis ���汨��---------------------------------------
						//��ӹ�������
						tji.setErpScrapNum(tji.getErpScrapNum()+Integer.parseInt(processScrapNum));
						tji.setWisScrapNum(tji.getWisScrapNum()+Integer.parseInt(processScrapNum));
						tji.setFinishNum(tji.getFinishNum()-Integer.parseInt(processScrapNum));
						//������α���
						tjpi.setScrapNum((null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(processScrapNum));
						//������α���
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
					
					if(null!=materialScrapNum&&!"".equals(materialScrapNum)&&!"0".equals(materialScrapNum)){//�����Ϸ�
						//--------------------------------------wis ���汨��---------------------------------------
						String wistpesql="from TProductionEvents where eventNo='Bz_"+tps.getTzdCode()+"_2'";
						List wistpelist=commonDao.executeQuery(wistpesql);
						if(null!=wistpelist&&wistpelist.size()>0){
							return "wis�����Ѵ��ڸñ�����Ϣ,���������뱨�ϵ��š�";
						}else{
							this.saveTProductionEvents(tps, jobdispatchNo, materialScrapNum, djgs, selectedPartId,4);
						}
						//--------------------------------------wis ���汨��---------------------------------------
						//��ӹ�������
						tji.setErpScrapNum(tji.getErpScrapNum()+Integer.parseInt(materialScrapNum));
						tji.setWisScrapNum(tji.getWisScrapNum()+Integer.parseInt(materialScrapNum));
						tji.setFinishNum(tji.getFinishNum()-Integer.parseInt(materialScrapNum));
						//������α���
						tjpi.setScrapNum((null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(materialScrapNum));
					}
					//�жϹ����Ƿ����
					if(tji.getProcessNum().intValue()-tji.getFinishNum().intValue()-tji.getWisScrapNum().intValue()<=0){
						tji.setStatus(70);
						tji.setRealEndtime(new Date());

					}
					//�ж��Ƿ����ǰ�򹤵�
					if(null!=onlinelist&&onlinelist.size()>0){
						TJobdispatchlistInfo mm=(TJobdispatchlistInfo)onlinelist.get(0);
						//�жϹ����Ƿ����
						//ǰ�򹤵��������=���򹤵��������+���򹤵��ķ��ֱ���������ǰ�򹤵�״̬��Ϊ���رգ�״̬=������ʱ�����򹤵�����Ϊ�����蹤�˲����������ϲ��ٿɿ���������״̬��Ϊ���
						if((mm.getFinishNum().intValue()-tji.getFinishNum().intValue()-tji.getWisScrapNum().intValue())<=0&&
								(mm.getStatus()==60||mm.getStatus()==70)){
							tji.setStatus(70);
							tji.setRealEndtime(new Date());

						}
					}
					//���湤��
					commonDao.update(TJobdispatchlistInfo.class,tji);
					//��������
					commonDao.update(TJobplanInfo.class,tjpi);
					//�жϸ������Ƿ�ȫ�����
					if(null!=onlinelist&&onlinelist.size()>0){
						String processsql="from TProcessInfo where id="+tji.getTProcessInfo().getId();
						TProcessInfo tp=(TProcessInfo)commonDao.executeQuery(processsql).get(0);
						//�ж��Ƿ�β��
						if(tp.getOfflineProcess()==1){
							//�����ε��ܷ��ֱ�������β��������֮�͵������μƻ�����ʱ������Ա�������ɹرո����Σ����ڽ�������ʾ���ݲ��Ը�״̬�����Ϳ��Ʊ仯���ڹر�����ʱ�����鹤�����رգ�״̬=������
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
				//�������ǰ������
				if(null!=onlinelist&&onlinelist.size()>0){
					TJobdispatchlistInfo mm=(TJobdispatchlistInfo)onlinelist.get(0);
					String onlinecount=mm.getFinishNum()+"";
					int count=Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum);
					//ǰ���������-��ǰ���������-��ǰ���򱨷���   ����ɱ�������
					if((Integer.parseInt(onlinecount)-tji.getFinishNum()-tji.getWisScrapNum()-count)<0){
						return "�����Ա�����:"+(Integer.parseInt(onlinecount)-tji.getFinishNum()-tji.getWisScrapNum());
					}
				}
				if((tji.getProcessNum()-tji.getFinishNum()-Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum))<0){
					int count=tji.getProcessNum()-tji.getFinishNum();
					return "�����Ա�����:"+count+"";
				}else{
					//���㹤ʱ
					String totleTime=djgs;
					
					//���湤��	����
					if(null!=processScrapNum&&!"".equals(processScrapNum)&&!"0".equals(processScrapNum)){
						//--------------------------------------wis ���汨��---------------------------------------
						String wistpesql="from TProductionEvents where eventNo='Bz_"+tps.getTzdCode()+"_1'";
						List wistpelist=commonDao.executeQuery(wistpesql);
						if(null!=wistpelist&&wistpelist.size()>0){
							return "wis�����Ѵ��ڸñ�����Ϣ,���������뱨�ϵ��š�";
						}else{
							this.saveTProductionEvents(tps, jobdispatchNo, processScrapNum, djgs, selectedPartId, 1);
						}
						//--------------------------------------wis ���汨��---------------------------------------
						
						//��ӹ�������
						tji.setErpScrapNum(tji.getErpScrapNum()+Integer.parseInt(processScrapNum));
						tji.setWisScrapNum(tji.getWisScrapNum()+Integer.parseInt(processScrapNum));
						//������α���
						tjpi.setScrapNum((null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(processScrapNum));
						//������α���
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
					//�����Ϸϴ���
					if(null!=materialScrapNum&&!"".equals(materialScrapNum)&&!"0".equals(materialScrapNum)){
						//--------------------------------------wis ���汨��---------------------------------------
						String wistpesql="from TProductionEvents where eventNo='Bz_"+tps.getTzdCode()+"_2'";
						List wistpelist=commonDao.executeQuery(wistpesql);
						if(null!=wistpelist&&wistpelist.size()>0){
							return "wis�����Ѵ��ڸñ�����Ϣ,���������뱨�ϵ��š�";
						}else{
							this.saveTProductionEvents(tps, jobdispatchNo, materialScrapNum, djgs, selectedPartId,4);
						}
						//--------------------------------------wis ���汨��---------------------------------------
						//��ӹ�������
						tji.setErpScrapNum(tji.getErpScrapNum()+Integer.parseInt(materialScrapNum));
						tji.setWisScrapNum(tji.getWisScrapNum()+Integer.parseInt(materialScrapNum));
						//������α���
						tjpi.setScrapNum((null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(materialScrapNum));
						
					}
					
					//�жϹ����Ƿ����
					if(tji.getProcessNum().intValue()-tji.getFinishNum().intValue()-tji.getWisScrapNum().intValue()<=0){
						tji.setStatus(70);
						tji.setRealEndtime(new Date());

					}
					//�ж��Ƿ����ǰ�򹤵�
					if(null!=onlinelist&&onlinelist.size()>0){
						TJobdispatchlistInfo mm=(TJobdispatchlistInfo)onlinelist.get(0);
						//�жϹ����Ƿ����
						//ǰ�򹤵��������=���򹤵��������+���򹤵��ķ��ֱ���������ǰ�򹤵�״̬��Ϊ���رգ�״̬=������ʱ�����򹤵�����Ϊ�����蹤�˲����������ϲ��ٿɿ���������״̬��Ϊ���
						if((mm.getFinishNum().intValue()-tji.getFinishNum().intValue()-tji.getWisScrapNum().intValue())<=0&&
								(mm.getStatus()==60||mm.getStatus()==70)){
							tji.setStatus(70);
							tji.setRealEndtime(new Date());

						}
					}
					//���湤��
					commonDao.update(TJobdispatchlistInfo.class,tji);
					//��������
					commonDao.update(TJobplanInfo.class,tjpi);
					//�жϸ������Ƿ�ȫ�����
					if(null!=onlinelist&&onlinelist.size()>0){
						String processsql="from TProcessInfo where id="+tji.getTProcessInfo().getId();
						TProcessInfo tp=(TProcessInfo)commonDao.executeQuery(processsql).get(0);
						//�ж��Ƿ�β��
						if(tp.getOfflineProcess()==1){
							//�����ε��ܷ��ֱ�������β��������֮�͵������μƻ�����ʱ������Ա�������ɹرո����Σ����ڽ�������ʾ���ݲ��Ը�״̬�����Ϳ��Ʊ仯���ڹر�����ʱ�����鹤�����رգ�״̬=������
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
		
		return "����ɹ�";
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
			wisTpe.setOperatorNo(userMap.get("no").toString());//���˺�
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
				//�����˹���
				wisTpe.setResponseNo(userMap.get("no").toString());
			}
			wisTpe.setEventType(1);//����Ϊ����
		}else if(scriptType==4){
			wisTpe.setEventNo("BF_"+tps.getTzdCode()+"_2");
			//���γ���
			wisTpe.setResponseNo(tps.getVendor());
			wisTpe.setEventType(4);//����Ϊ�Ϸ�
		}
		wisTpe.setResponseProcessNo(tps.getZrOperationNum());
		commonDao.save(TProductionEvents.class, wisTpe);
	}
	/**
	 * ��ȡ������ʾ��Ϣ
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
				tji=tjilist.get(0);//���ҹ���	
			}
			//�������μƻ�
			String jobplansql="from TJobplanInfo where id="+tji.getJobplanId();
			TJobplanInfo tjpi=(TJobplanInfo)commonDao.executeQuery(jobplansql).get(0);
			//����ǰ������
			String onlinehql=" from TJobdispatchlistInfo a"
					  + " where a.TProcessInfo.id='"+onlineProcessId+"'"
					  + " and a.batchNo='"+tji.getBatchNo()+"'"; 
			List<TJobdispatchlistInfo> onlinelist=commonDao.executeQuery(onlinehql);//���� ǰ������
			String resourceCode="test";//��Դ����  �㷨��δʵ��
			if(isCurrentProcess.size()>0){//�ж��Ƿ�ѡ�������Ѽӹ�
				if((tji.getFinishNum()-Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum))<0){
					return "�����Ա�����:"+tji.getFinishNum();
				}else{
					//�жϸ������Ƿ�ȫ�����
					if(null!=onlinelist&&onlinelist.size()>0){
						String processsql="from TProcessInfo where id="+tji.getTProcessInfo().getId();
						TProcessInfo tp=(TProcessInfo)commonDao.executeQuery(processsql).get(0);
						//�ж��Ƿ�β��
						if(tp.getOfflineProcess()==1){
							//�����ε��ܷ��ֱ�������β��������֮�͵������μƻ�����ʱ������Ա�������ɹرո����Σ����ڽ�������ʾ���ݲ��Ը�״̬�����Ϳ��Ʊ仯���ڹر�����ʱ�����鹤�����رգ�״̬=������
							int pc=(null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(materialScrapNum)+Integer.parseInt(processScrapNum);
							if(tjpi.getPlanNum()-pc-tji.getFinishNum()<=0){
								return "�رչ���";
							}
						}
					}
				}
			}else{
				//�������ǰ������
				if(null!=onlinelist&&onlinelist.size()>0){
					TJobdispatchlistInfo mm=(TJobdispatchlistInfo)onlinelist.get(0);
					String onlinecount=mm.getFinishNum()+"";
					int count=Integer.parseInt(processScrapNum)+Integer.parseInt(materialScrapNum);
					//a=ǰ���������-��ǰ���������-��ǰ���򱨷���  ����ɱ�������
					if((Integer.parseInt(onlinecount)-tji.getFinishNum()-tji.getWisScrapNum()-count)<0){
						return "�����Ա�����:"+(Integer.parseInt(onlinecount)-tji.getFinishNum()-tji.getWisScrapNum());
					}
				}
				if((tji.getProcessNum()-tji.getFinishNum()-Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum))<0){
					int count=tji.getProcessNum()-tji.getFinishNum();
					return "�����Ա�����:"+count+"";
				}else{
					//�жϸ������Ƿ�ȫ�����
					if(null!=onlinelist&&onlinelist.size()>0){
						String processsql="from TProcessInfo where id="+tji.getTProcessInfo().getId();
						TProcessInfo tp=(TProcessInfo)commonDao.executeQuery(processsql).get(0);
						//�ж��Ƿ�β��
						if(tp.getOfflineProcess()==1){
							//�����ε��ܷ��ֱ�������β��������֮�͵������μƻ�����ʱ������Ա�������ɹرո����Σ����ڽ�������ʾ���ݲ��Ը�״̬�����Ϳ��Ʊ仯���ڹر�����ʱ�����鹤�����رգ�״̬=������
							int pc=(null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(materialScrapNum)+Integer.parseInt(processScrapNum);
							if(tjpi.getPlanNum()-pc-tji.getFinishNum()<=0){
								return "�رչ���";
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
		
		return "��������";
	}
	
	
	/**
	 * ����id�ڵ�id��ȡ����б�
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
	 * ���������Ż�ȡ ���μƻ�
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

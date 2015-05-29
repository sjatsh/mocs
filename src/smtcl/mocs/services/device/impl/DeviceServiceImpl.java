package smtcl.mocs.services.device.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.persistence.Sort;
import org.dreamwork.util.IDataCollection;
import org.dreamwork.util.ListDataCollection;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import smtcl.mocs.model.DeviceInfoModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodeProductionProfiles;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUserEquCurStatus;
import smtcl.mocs.pojos.device.TUserEquEvents;
import smtcl.mocs.pojos.device.TUserEquOeedaily;
import smtcl.mocs.pojos.device.TUserEquOeestore;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.DateUtils;
import smtcl.mocs.utils.device.StringUtils;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TPartBasicInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProductionEvents;
import smtcl.mocs.pojos.storage.TMaterialStorage;
import smtcl.mocs.pojos.storage.TMaterialVersion;
import smtcl.mocs.pojos.storage.TStorageInfo;
import smtcl.mocs.pojos.storage.TTransaction;
import smtcl.mocs.pojos.storage.TTransactionRelation;

/**
 * �û��鿴�豸����ʵ�ֲ�
 * @version V1.0
 * @����ʱ�� 2012-12-03
 * @���� liguoqiang
 * @�޸���
 * @�޸�����
 * @�޸�˵��
 */
public class DeviceServiceImpl extends GenericServiceSpringImpl<TNodes, String>
		implements IDeviceService {
	
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
	 * ��ȡ�豸��������ʷ����
	 * @param serialon �豸���к�
	 * @return  DeviceInfoModel
	 */
	@Override
	public DeviceInfoModel getDeviceInfoModelHistory(String serialon) {
		DeviceInfoModel dim = new DeviceInfoModel();
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "select new map(" + "a.equSerialNo as equSerialNo,"
				+ " a.equId as equId," + " a.equName as equName," +
					"a.path as path,"
				+ " a.equType as equType,"
				+ " a.manufacturetype as manufactureType,"
				+ " b.runningTime as runningTime,"
				+ " b.stopTime as stopTime, "
				+ " b.processTime as processTime,"
				+ " b.prepareTime as prepareTime," + " b.idleTime as idleTime,"
				+ " b.breakdownTime as breakdownTime,"
				+ " b.dryRunningTime as dryRunningTime," +
				"   b.toolChangeTime as toolChangeTime,"
				+ " b.cuttingTime as cuttingTime,"
				+ " b.manualRunningTime as manualRunningTime,"
				+ " b.materialTime as materialTime,"
				+ " b.otherTime as otherTime,"
				+ " b.totalProcessPartsCount as totalProcessPartsCount,"
				+ " b.anualProcessPartsCount as anualProcessPartsCount,"
				+ " b.monthProcessPartsCount as monthProcessPartsCount,"
				+ " b.dayProcessPartsCount as dayProcessPartsCount " + ")"
				+ " from TEquipmentInfo as a , TUserEquStatusStore as b "
				+ " where a.equSerialNo=b.equSerialNo and a.equSerialNo='"
				+ serialon + "'";

		String hql2 = "select new map(programm as programm,operator as operator) from TUserEquCurStatus where equSerialNo='"
				+ serialon + "'  order by updateTime desc ";
		try {
			IDataCollection<Map<String, Object>> test = dao.executeQuery(1, 1, hql, parameters);
			if (test.getData().size() > 0) {
				Map<String, Object> record = test.getData().get(0);
				dim.setEquSerialNo(StringUtils.isEmpty2(record.get("equSerialNo")+""));
				dim.setEquName(StringUtils.isEmpty2(record.get("equName")+""));
				dim.setEquType(StringUtils.isEmpty2(record.get("equType")+""));
				dim.setManufactureType(StringUtils.isEmpty2(record.get("manufactureType")+""));
				dim.setRunningTime(StringUtils.SecondIsDDmmss(record.get("runningTime")));
				dim.setStopTime(StringUtils.SecondIsDDmmss(record.get("stopTime")));
				dim.setProcessTime(StringUtils.SecondIsDDmmss(record.get("processTime")));
				dim.setPrepareTime(StringUtils.SecondIsDDmmss(record.get("prepareTime")));
				dim.setIdleTime(StringUtils.SecondIsDDmmss(record.get("idleTime")));
				dim.setBreakDownTime(StringUtils.SecondIsDDmmss(record.get("breakdownTime")));
				dim.setDryRunningTime(StringUtils.SecondIsDDmmss(record.get("dryRunningTime")));
				dim.setToolChangeTime(StringUtils.SecondIsDDmmss(record.get("toolChangeTime")));
				dim.setCuttingTime(StringUtils.SecondIsDDmmss(record.get("cuttingTime")));
				dim.setManualRunningTime(StringUtils.SecondIsDDmmss(record.get("manualRunningTime")));
				dim.setMaterialTime(StringUtils.SecondIsDDmmss(record.get("materialTime")));
				dim.setOtherTime(StringUtils.SecondIsDDmmss(record.get("otherTime")));
				dim.setTotalProcessPartsCount(record.get("totalProcessPartsCount") + "");
				dim.setAnualProcessPartsCount(record.get("anualProcessPartsCount") + "");
				dim.setMonthProcessPartsCount(record.get("monthProcessPartsCount") + "");
				dim.setDayProcessPartsCount(record.get("dayProcessPartsCount") + "");

				IDataCollection<Map<String, Object>> test2 = dao.executeQuery( 1, 1, hql2, parameters);
				dim.setProgramm(StringUtils.isEmpty2(test2.getData().get(0).get("programm")+""));
				dim.setOperator(StringUtils.isEmpty2(test2.getData().get(0).get("operator")+""));
				dim.setPath(StringUtils.isEmpty2(record.get("path")+""));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dim;
	}

	/**
	 * ��ȡ�ڵ����豸����Ϣ
	 * @param nodeid �豸���к�
	 * @return List<DeviceInfoModel>
	 */
	@Override
	public List<DeviceInfoModel> getDevicesOverview(Long nodeid) {
		nodeid = (long) 6;
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<DeviceInfoModel> rlist = new ArrayList<DeviceInfoModel>();
		String hql = "select new Map( "
				+ "a.nodeId as nodeId, "
				+ "b.equName as equName, "
				+ "b.equType as equType, "
				+ "b.equSerialNo as equSerialNo, "
				+ "c.status as status ) "
				+ "from TNodes as a,TEquipmentInfo as b,TUserEquCurStatus as c "
				+ "where  a.nodeId=b.TNodes.nodeId  and b.equSerialNo=c.equSerialNo "
				+ "and a.nodeId=" + nodeid;
		try {
			List<Map<String, Object>> rs = dao.executeQuery(hql, parameters);
			for (Map<String, Object> record : rs) {
				DeviceInfoModel dim = new DeviceInfoModel();
				dim.setNodeId(record.get("nodeId") + "");
				dim.setEquName(record.get("equName") + "");
				dim.setEquType(record.get("equType") + "");
				dim.setEquSerialNo(record.get("equSerialNo") + "");
				dim.setStatus(record.get("status") + "");
				System.out.println(dim.getEquName());
				rlist.add(dim);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rlist;
	}

	/**
	 * ��ȡ�豸������ʵʱ����
	 * @param serialon �豸���к�
	 * @return DeviceInfoModel
	 */
	@Override
	public DeviceInfoModel getDeviceInfoModelRealtime(String serialon) {
		DeviceInfoModel dim = new DeviceInfoModel();
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "select new Map(" 
				+ "a.symgEquId as symgEquId,"
				+ "b.updateTime as updateTime," 
				+ "b.status as status,"
				+ "b.programm as programm," 
				+ "b.toolNo as toolNo,"
				+ "b.part as part," 
				+ "b.partTimeCount as partTimeCount,"
				+ "b.partCount as partCount," 
				+ "b.feedSpeed as feedSpeed,"
				+ "b.feedrate as feedrate," 
				+ "b.axisspeed as axisspeed,"
				+ "b.axisspeedRate as axisspeedRate," 
				+ "b.xfeed as xfeed,"
				+ "b.yfeed as yfeed," 
				+ "b.zfeed as zfeed,"
				+ "b.cuttingpower as cuttingpower, " 
				+ "b.equSerialNo as equSerialNo, "
				+ "b.operator as operator) "
				+ "from TEquipmentInfo a,TUserEquCurStatus b "
				+ "where a.equSerialNo=b.equSerialNo and a.equSerialNo='"
				+ serialon + "'";
		try {
			IDataCollection<Map<String, Object>> test = dao.executeQuery(1, 1,hql, parameters);
			if(null!=test.getData()&&test.getData().size()>0){
				Map<String, Object> record = test.getData().get(0);
				dim.setSymgEquId(StringUtils.isEmpty2(record.get("symgEquId")+""));
				dim.setUpdateTime(StringUtils.isEmpty2(record.get("updateTime")+""));
				dim.setStatus(StringUtils.isEmpty2(record.get("status") + ""));
				dim.setProgramm(StringUtils.isEmpty2(record.get("programm") + ""));
				dim.setToolNo(StringUtils.isEmpty2(record.get("toolNo") + ""));
				dim.setPart(StringUtils.isEmpty2(record.get("part") + ""));
				dim.setPartTimeCount(StringUtils.isEmpty2(record.get("partTimeCount") + ""));
				dim.setPartCount(StringUtils.isEmpty2(record.get("partCount") + ""));
				dim.setFeedSpeed(StringUtils.isEmpty2(record.get("feedSpeed") + ""));
				dim.setFeedrate(StringUtils.isEmpty2(record.get("feedrate") + ""));
				dim.setAxisspeed(StringUtils.isEmpty2(record.get("axisspeed") + ""));
				dim.setAxisspeedRate(StringUtils.isEmpty2(record.get("axisspeedRate") + ""));
				dim.setXfeed(StringUtils.isEmpty2(record.get("xfeed") + ""));
				dim.setYfeed(StringUtils.isEmpty2(record.get("yfeed") + ""));
				dim.setZfeed(StringUtils.isEmpty2(record.get("zfeed") + ""));
				dim.setCuttingpower(StringUtils.isEmpty2(record.get("cuttingpower") + ""));
				dim.setEquSerialNo(StringUtils.isEmpty2(record.get("equSerialNo") + ""));
				dim.setOperator(StringUtils.isEmpty2(record.get("operator") + ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dim;
	}

    /**
     * ʱ��ͳ�Ʋ�ѯ
     * @param equSerialNo
     * @param startTime
     * @param endTime
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findIHisroryStatistics(String equSerialNo,String startTime,String endTime) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "select new Map(tuser.equSerialNo as equSerialNo,"
				+ " sum(tuser.runningTime) as runningTime,"
				+ " sum(tuser.stopTime) as stopTime,"
				+ " sum(tuser.processTime) as processTime,"
				+ " sum(tuser.prepareTime) as prepareTime,"
				+ " sum(tuser.idleTime) as idleTime,"
				+ " sum(tuser.breakdownTime) as breakdownTime,"
				+ " sum(tuser.cuttingTime) as cuttingTime, "
				+ " sum(tuser.dryRunningTime) as dryRunningTime,"
				+ " sum(tuser.toolChangeTime) as toolChangeTime,"
				+ " sum(tuser.manualRunningTime) as manualRunningTime,"
				+ " sum(tuser.materialTime) as materialTime,"
				+ " sum(tuser.otherTime) as otherTime,"
				+ " sum(tuser.dayProcessPartsCount) as dayProcessPartsCount)"
				+ " from TNodes nodes ,TEquipmentInfo te ,TUserEquStatusDaily tuser"
				+ " where nodes.nodeId=te.TNodes.nodeId "
				+ " and te.equSerialNo=tuser.equSerialNo "
				+ " and te.equSerialNo='"+equSerialNo+"' "
			    + " and tuser.updatedate>='"+startTime+"' "
				+ " and tuser.updatedate<='"+endTime+"' ";
					
		hql += " GROUP BY tuser.equSerialNo";

		return dao.executeQuery(hql, parameters);
	}
	
	/**
	 * ��ȡʵʱ״̬ͼ������
	 * @param parameters ��ѯ��������
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getRealStatistics( 
			Collection<Parameter> parameters) {

		String hql = "select new Map(tuser.equSerialNo as equSerialNo,"
				+ " tuser.runningTime as runningTime,"
				+ " tuser.stopTime as stopTime,"
				+ " tuser.processTime as processTime,"
				+ " tuser.prepareTime as prepareTime,"
				+ " tuser.idleTime as idleTime,"
				+ " tuser.breakdownTime as breakdownTime,"
				+ " tuser.cuttingTime as cuttingTime, "
				+ " tuser.dryRunningTime as dryRunningTime,"
				+ " tuser.toolChangeTime as toolChangeTime,"
				+ " tuser.manualRunningTime as manualRunningTime,"
				+ " tuser.materialTime as materialTime,"
				+ " tuser.otherTime as otherTime,"
				+ " tuser.dayProcessPartsCount as dayProcessPartsCount)"
				+ " from TNodes nodes ,TEquipmentInfo te ,TUserEquStatusStore tuser"
				+ " where nodes.nodeId=te.TNodes.nodeId "
				+ " and te.equSerialNo=tuser.equSerialNo ";
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		return dao.executeQuery(hql, parameters);
	}

	/**
	 * ʱ��Ƚ�   ͼ��Ĳ�ѯ
	 * @param parameters ��ѯ��������
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> compareDevicesTime(Collection<Parameter> parameters,String startTime,String endTime) {
		//DATE_FORMAT(c.starttime,'%Y-%m-%d %T')
		String hql = "select new Map("
				+ "te.equName as equName,"
				+ " te.equSerialNo as equSerialNo,"
				+ " sum(tuser.runningTime) as runningTime,"
				+ " sum(tuser.stopTime) as stopTime,"
				+ " sum(tuser.processTime) as processTime,"
				+ " sum(tuser.prepareTime) as prepareTime,"
				+ " sum(tuser.idleTime) as idleTime,"
				+ " sum(tuser.breakdownTime) as breakdownTime,"
				+ " sum(tuser.cuttingTime) as cuttingTime, "
				+ " sum(tuser.dryRunningTime) as dryRunningTime,"
				+ " sum(tuser.toolChangeTime) as toolChangeTime,"
				+ " sum(tuser.manualRunningTime) as manualRunningTime,"
				+ " sum(tuser.materialTime) as materialTime,"
				+ " sum(tuser.otherTime) as otherTime)"
				+ " from TNodes nodes ,TEquipmentInfo te ,TUserEquStatusDaily tuser"
				+ " where nodes.nodeId=te.TNodes.nodeId "
				+ " and te.equSerialNo=tuser.equSerialNo ";
		if (null!=startTime) {			            
		       hql=hql+" and DATE_FORMAT(tuser.updatedate,'%Y-%m-%d')>='"+startTime+"' " ;
		}
		if (null!=endTime) {			            
		       hql=hql+" and DATE_FORMAT(tuser.updatedate,'%Y-%m-%d')<='"+endTime+"' ";
	     }
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " GROUP BY te.equName,te.equSerialNo ";

		return dao.executeQuery(hql, parameters);
	}

	/**
	 * ��̨�豸ʱ���OEE��ѯ 
	 * @param parameters ��ѯ��������
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> selectServiceOEE(
			Collection<Parameter> parameters) {

		String hql = "select new Map("
				+ " tuserOne.equSerialNo as equSerialNo,"
				+ " sum(tuserOne.worktimeFact) as worktimeFact,"
				+ " sum(tuserOne.worktimePlan) as worktimePlan,"
				+ " sum(tuserOne.acturalOutputTheorytime) as acturalOutputTheorytime,"
				+ " sum(tuserOne.processPartsCount) as processPartsCount,"
				+ " sum(tuserOne.qualifiedPartCount) as qualifiedPartCount,"
				+ " sum(tuserOne.dayOeevalue) as dayOeevalue,"
				+ " sum(tuserOne.dayTeepvalue) as dayTeepvalue)"
				+ " from TNodes nodes ,TEquipmentInfo te ,TUserEquOeedaily tuserOne"
				+ " where nodes.nodeId=te.TNodes.nodeId"
				+ " and te.equSerialNo=tuserOne.equSerialNo";
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " GROUP BY tuserOne.equSerialNo";
		return dao.executeQuery(hql, parameters);
	}
	public List<Map<String, Object>> selectServiceOEE(String startTime,String endTime,String equSerialNo) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql = "select new Map("
				+ " tuserOne.equSerialNo as equSerialNo,"
				+ " sum(tuserOne.worktimeFact) as worktimeFact,"
				+ " sum(tuserOne.worktimePlan) as worktimePlan,"
				+ " sum(tuserOne.acturalOutputTheorytime) as acturalOutputTheorytime,"
				+ " sum(tuserOne.processPartsCount) as processPartsCount,"
				+ " sum(tuserOne.qualifiedPartCount) as qualifiedPartCount,"
				+ " sum(tuserOne.dayOeevalue) as dayOeevalue,"
				+ " sum(tuserOne.dayTeepvalue) as dayTeepvalue)"
				+ " from TNodes nodes ,TEquipmentInfo te ,TUserEquOeedaily tuserOne"
				+ " where nodes.nodeId=te.TNodes.nodeId"
				+ " and te.equSerialNo=tuserOne.equSerialNo"
				+ " and tuserOne.caclDate>='"+startTime+"'"
				+ " and tuserOne.caclDate<='"+endTime+"' "
				+ " and te.equSerialNo='"+equSerialNo+"'";
		hql += " GROUP BY tuserOne.equSerialNo";
		return dao.executeQuery(hql, parameters);
	}
	/**
	 * ���ݽڵ�id��ȡ�豸��Ϣ
	 * @param nodeid �ڵ�id
	 * @return List<TEquipmentInfo>
	 */
	@Override
	public List<TEquipmentInfo> getNodesAllEquName(String nodeid) {
		List<String> nodeIdList = new ArrayList<String>();// ��ǰ�ڵ�����ӽڵ��id
		List<TEquipmentInfo> rs = new ArrayList<TEquipmentInfo>();
		try {
			Collection<Parameter> parameters = new HashSet<Parameter>();

			String hql2 = "from TEquipmentInfo " + "where TNodes.nodeId in("
					+ nodeid + ")";
			rs = dao.executeQuery(hql2, parameters);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * ���ݽڵ�id ��ѯ�ýڵ��������豸������ �����ӽڵ�
	 * @param nodeid �ڵ�id
	 * @return List<TEquipmentInfo>
	 */
	public List<TEquipmentInfo> getNodesAllTEquipmentInfo(String nodeid) {
		List<String> nodeIdList = new ArrayList<String>();// ��ǰ�ڵ�����ӽڵ��id
		List<TEquipmentInfo> rs = new ArrayList<TEquipmentInfo>();
		try {
			Collection<Parameter> parameters = new HashSet<Parameter>();

			nodeIdList=getNodesAllId(nodeid);// �ݹ��ȡǰ�ڵ�id���ӽڵ��id
			String nodeidstr = StringUtils.returnHqlIN(nodeIdList);

			String hql2 = "from TEquipmentInfo " + "where TNodes.nodeId in("
					+ nodeidstr + ")";
			rs = dao.executeQuery(hql2, parameters);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * �����豸���кŲ�ѯ�����豸��Ϣ
	 * @param equSerialNos �豸���к�
	 * @return List<TEquipmentInfo>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TEquipmentInfo> getAllEquName(String equSerialNos) {
		List<TEquipmentInfo> rs = new ArrayList<TEquipmentInfo>();
		try {
			String hql2 = "from TEquipmentInfo " + "where equSerialNo in("
					+ equSerialNos + ")";
			rs = dao.executeQuery(hql2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * ��ȡǰ�ڵ�id���ӽڵ��id
	 * @param nodeId �ڵ�id
	 * @return List<String>
	 */
	public List<String> getNodesAllId(String nodeId) {
		String hql=" select nodeId from TNodes t where TNodes.nodeId='"+nodeId+"'";
		return dao.executeQuery(hql);
	}

	/**
	 * ��ȡĳ��ʱ�����ĳ��������oee��teep
	 * @param pageNo ҳ��
	 * @param pageSize ����
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @param name ����
	 * @param dateType ʱ������
	 * @return IDataCollection<DeviceInfoModel> 
	 */
    @SuppressWarnings("unchecked")
	public IDataCollection<DeviceInfoModel> getOEETEEP(int pageNo,
			int pageSize, Date start, Date end, String name, String dateType) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		DecimalFormat f = new DecimalFormat("0.00");
		List<DeviceInfoModel> rdim = new ArrayList<DeviceInfoModel>();// ���ص��豸list
		ListDataCollection<DeviceInfoModel> result = new ListDataCollection<DeviceInfoModel>();

		try {
			if ("��".equals(dateType) || dateType.equals("Day")) {
				String hql = "from TUserEquOeedaily "
						+ "where caclDate>=:start" 
						+ " and caclDate<=:end"
						+ " and equSerialNo='" + name + "' order by caclDate";
				parameters.add(new Parameter("start", start, Operator.EQ));
				parameters.add(new Parameter("end", end, Operator.EQ));
				IDataCollection<TUserEquOeedaily> rs = dao.executeQuery(pageNo,pageSize, hql, parameters);
				int id = 1;
				for (TUserEquOeedaily tueo : rs.getData()) {
					DeviceInfoModel dim = new DeviceInfoModel();
               
					Long worktimeFact = tueo.getWorktimeFact(); // ʵ�ʿ���ʱ��
					Long worktimePlan = tueo.getWorktimePlan(); // �ƻ�����ʱ��
					Long acturalOutputTheorytime = tueo
							.getActuralOutputTheorytime(); // ʵ�ʲ������ۺ�ʱ
					double processPartsCount = tueo.getProcessPartsCount(); // �ӹ�������
					double qualifiedPartCount = tueo.getQualifiedPartCount(); // �ϸ񹤼���
					double availability = worktimeFact
							/ Double.parseDouble(worktimePlan.toString());// ������
					// ʵ�ʿ���ʱ��/�ƻ�����ʱ��
					double expressivenessOf = acturalOutputTheorytime
							/ Double.parseDouble(worktimeFact.toString());// ������
																			// ʵ�ʲ������ۺ�ʱ/ʵ�ʿ���ʱ��
					double quality = qualifiedPartCount / processPartsCount; // ����

					if (worktimePlan != 0) {
						dim.setAvailability(f.format(availability));
					} else {
						dim.setAvailability("0.00");
					}

					if (worktimeFact != 0) {
						dim.setExpressivenessOf(f.format(expressivenessOf));
					} else {
						dim.setExpressivenessOf("0.00");
					}

					if (processPartsCount != 0) {
						dim.setQuality(f.format(quality));
					} else {
						dim.setQuality("0.00");
					}
					dim.setId(id);
					dim.setDayOeevalue(tueo.getDayOeevalue() + "");
					dim.setDayTeepvalue(tueo.getDayTeepvalue() + "");
					dim.setOeeTime(tueo.getCaclDate().toString().substring(0,10) + "");
					rdim.add(dim);
					id = id + 1;
				}
				if (rdim.size() > 0) {
					result.setData(rdim);
					result.setTotalRows(rs.getTotalRows());
					return result;
				}
			} else {
				String hql = "from TUserEquOeestore  where equSerialNo='"
						+ name + "' ";
				String s = StringUtils.formatDate(start, 2);
				String e = StringUtils.formatDate(end, 2);
				String[] st = s.split("-");
				String[] en = e.split("-");

				if ("��".equals(dateType) || dateType.equals("Week")) {
					hql=hql+" and concat(year,case when length(weekofYear)<2 then concat('0',weekofYear) "+
	                        "   when  length(weekofYear)>1 then concat('',weekofYear) end) "+
							"<='"+StringUtils.getYearWeek(end)+"' "+
							" and "+
							"concat(year,case when length(weekofYear)<2 then concat('0',weekofYear) "+
	                        "   when  length(weekofYear)>1 then concat('',weekofYear) end) "+
							">='"+StringUtils.getYearWeek(start)+"' "+
							" and month=0 and weekofYear!=0 "+
							" order by year,weekofYear";
				} else if ("��".equals(dateType) || dateType.equals("Month")) {
					hql=hql+" and concat(year,case when length(month)<2 then concat('0',month) "+
	                        "   when  length(month)>1 then concat('',month) end) "+
							"<='"+(en[0] + en[1])+"' "+
							" and "+
							"concat(year,case when length(month)<2 then concat('0',month) "+
	                        "   when  length(month)>1 then concat('',month) end) "+
							">='"+(st[0] + st[1])+"' "+
							" and weekofYear=0 and month!=0 "+
							" order by year,month";
				} else if ("��".equals(dateType) || dateType.equals("Year")) {
					hql = hql + " and year>=:syear " + " and year<=:eyear "
							+ " and month=0 " + " and weekofYear=0 and year!=0"
							+ "  order by year";
					parameters.add(new Parameter("syear",
							Long.parseLong(st[0]), Operator.EQ));
					parameters.add(new Parameter("eyear",
							Long.parseLong(en[0]), Operator.EQ));
				}

				IDataCollection<TUserEquOeestore> rs = dao.executeQuery(pageNo,
						pageSize, hql, parameters);
				int id = 1;
				System.out.println("rs�ĳ���Ϊ��" + rs.getData().size());
				for (TUserEquOeestore tueo : rs.getData()) {
					DeviceInfoModel dim = new DeviceInfoModel();

					Long totalworkTimeFact = tueo.getTotalworkTimeFact(); // ʵ�ʿ���ʱ��
					Long totalWorkTimePlan = tueo.getTotalWorkTimePlan(); // �ƻ�����ʱ��
					Long totalActOutputTheoryTime = tueo
							.getTotalActOutputTheoryTime(); // ʵ�ʲ������ۺ�ʱ
					double totalProcesspartscount = tueo
							.getTotalProcesspartscount(); // �ӹ�������
					double totalQualifiedPartCount = tueo
							.getTotalQualifiedPartCount(); // �ϸ񹤼���

					double availability = totalworkTimeFact
							/ Double.parseDouble(totalWorkTimePlan.toString());// ������
					// ʵ�ʿ���ʱ��/�ƻ�����ʱ��
					double expressivenessOf = totalActOutputTheoryTime
							/ Double.parseDouble(totalworkTimeFact.toString());// ������
																				// ʵ�ʲ������ۺ�ʱ/ʵ�ʿ���ʱ��
					double quality = totalQualifiedPartCount
							/ totalProcesspartscount; // ����

					if (totalWorkTimePlan != 0) {
						dim.setAvailability(f.format(availability));
					} else {
						dim.setAvailability("0.00");
					}

					if (totalworkTimeFact != 0) {
						dim.setExpressivenessOf(f.format(expressivenessOf));
					} else {
						dim.setExpressivenessOf("0.00");
					}

					if (totalProcesspartscount != 0) {
						dim.setQuality(f.format(quality));
					} else {
						dim.setQuality("0.00");
					}
					dim.setId(id);
					dim.setDayOeevalue(tueo.getTotalOeevalue() + "");
					dim.setDayTeepvalue(tueo.getTotalTeepvalue() + "");
					if ("��".equals(dateType) || dateType.equals("Week")) {
						dim.setOeeTime(tueo.getYear() + "-"
								+ tueo.getWeekofYear());
					} else if ("��".equals(dateType) || dateType.equals("Month")) {
						dim.setOeeTime(tueo.getYear() + "-" + tueo.getMonth());
					} else if ("��".equals(dateType) || dateType.equals("Year")) {
						dim.setOeeTime(tueo.getYear() + "");
					}
					rdim.add(dim);
					id = id + 1;
				}
				if (rdim.size() > 0) {
					result.setData(rdim);
					result.setTotalRows(rs.getTotalRows());
					return result;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ��ʷ������ҳ��ѯ�����
	 * @param pageNo ҳ��
	 * @param pageSize ����
	 * @param parameters ��ѯ��������
	 * @return IDataCollection<Map<String, Object>>
	 */
	@Override
	public IDataCollection<Map<String, Object>> getDeviceProFrequenceStat(
			int pageNo, int pageSize, Collection<Parameter> parameters) {
		String hql = "SELECT new map( a.nodeId AS nodeId, "
				+ "	b.equName AS equName, " 
				+ " c.id AS id, "
				+ " c.cuttingeventId AS cuttingeventId, "
				+ " c.equSerialNo AS equSerialNo, "
				+ " DATE_FORMAT(c.starttime,'%Y-%m-%d %T')  AS starttime, "
				+ " DATE_FORMAT(c.finishtime,'%Y-%m-%d %T')  AS finishtime, "
				+ " c.cuttingTask AS cuttingTask, "
				+ " c.partNo AS partName, " 
				+ " c.workTime AS workTime, "
				+ " c.theoryCycletime AS theoryCycletime, "
				+ " c.toolchangeTime AS toolchangeTime, "
				+ " c.cuttingTime AS cuttingTime, "
				+ " c.theoryWorktime AS theoryWorktime) "
				+ " FROM TNodes a, TEquipmentInfo b, TUserEquWorkEvents c "
				+ " where a.nodeId = b.TNodes.nodeId "
				+ " and b.equSerialNo = c.equSerialNo ";
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql +=  " ORDER BY c.cuttingeventId DESC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * �����豸���Ʋ�ѯ������
	 * @param nameSelected �豸���к�
	 * @return List<String>
	 */
	public List<String> getPartNameByEquName(String nameSelected) {
		String hql = "SELECT DISTINCT c.partNo AS partName "
				+ " FROM TNodes a, TEquipmentInfo b, TUserEquWorkEvents c "
				+ " where a.nodeId = b.TNodes.nodeId "
				+ " and b.equSerialNo = c.equSerialNo "
				+ " and b.equSerialNo = '" + nameSelected + "'"
				+ " and c.partNo is not null ";
		
		hql += " ORDER BY c.partNo ASC";
		System.err.println("hql:" + hql);
		return dao.executeQuery(hql);
	}

	/**
	 * �ӹ��¼�������Ӧ�Ĳ�ѯ 
	 * @param pageNo ҳ��
	 * @param pageSize ����
	 * @param parameters ��ѯ��������
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IDataCollection<Map<String, Object>> getDeviceProcessEventStat(
			int pageNo, int pageSize, Collection<Parameter> parameters,
			String nodeIds) {
		String hql = " SELECT NEW MAP( c.nodeId AS nodeId,"
				+ " b.equName AS equName,"
				+ " a.equSerialNo AS equSerialNo,"
				+ " a.cuttingeventId AS cuttingeventId,"
				+ " DATE_FORMAT(a.starttime,'%Y-%m-%d %T') AS starttime,"
				+ " DATE_FORMAT(a.finishtime,'%Y-%m-%d %T') AS finishtime,"
				+ " a.cuttingTask AS cuttingTask,"
				+ " a.partNo AS partName,"
				+ " a.theoryWorktime AS theoryWorktime,"
				+ " a.cuttingTime AS cuttingTime,"
				+ " a.toolchangeTime AS toolchangeTime,"
				+ " a.workTime AS workTime )"
				+ " FROM TUserEquWorkEvents a , TEquipmentInfo b, TNodes c "
				+ " WHERE c.nodeId = b.TNodes.nodeId AND a.equSerialNo=b.equSerialNo ";

		if (nodeIds != null) {
			hql += " AND c.nodeId IN (" + nodeIds + ") ";
		}
		for (Parameter p : parameters) {
			System.out.println(p.name+"---"+p.value);
			hql += " AND " + p;
		}
		hql += " ORDER BY DATE_FORMAT(a.starttime,'%Y-%m-%d %T') DESC";
		return dao.executeQuery(pageNo, pageSize, hql, parameters);
	}

	/**
	 * ��ȡ���������������
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllPartName() {
		String hql = " SELECT DISTINCT  a.partNo as partName FROM TUserEquWorkEvents AS a WHERE a.partNo IS NOT NULL ORDER BY a.partNo";
		return dao.executeQuery(hql);
	}

	/**
	 * ��ȡ���������¼�����
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllEventName() {
		String hql = " SELECT DISTINCT a.eventName AS eventName FROM TUserEquEvents a ";
		return dao.executeQuery(hql);
	}

	/**
	 * ��ȡ��ѯ������,�������ݵ�����,��ȥ���ظ�
	 * @param parameters ��ѯ��������
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> getDateSum(Collection<Parameter> parameters) {
		String hql = "SELECT DISTINCT DATE_FORMAT(a.eventTime ,'%Y-%m-%d') AS eventTime FROM TUserEquEvents a WHERE 1=1 AND DATE_FORMAT(a.eventTime,'%T')^='00:00:00' ";
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY DATE_FORMAT(a.eventTime ,'%Y-%m-%d')  ASC ";
		return dao.executeQuery(hql, parameters);
	}

	/**
	 * �����¼�������Ӧ�Ĳ�ѯ
	 * @param pageNo ҳ��
	 * @param pageSize ����
	 * @param parameters ��ѯ��������
	 * @return IDataCollection<Map<String, Object>>
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public IDataCollection<Map<String, Object>> getDeviceMachineEventStat(Locale locale,int pageNo, int pageSize, Collection<Parameter> parameters,Map<String,String> eventMap) {
		String hql = "SELECT NEW MAP(  c.nodeId AS nodeId,"
				+ " b.equName AS equName,"
				+ " a.equSerialNo AS equSerialNo,"
				+ " a.eventId AS eventId,"
				+ " DATE_FORMAT(a.eventTime ,'%Y-%m-%d %T') AS eventTime,"
				+ " a.eventName AS eventName,"
				+ " a.programmname AS programmname,"
				//+ " nvl(a.eventMemo,' ') AS eventMemo)"
				+ " a.eventMemo AS eventMemo)"
				
				+ " FROM TUserEquEvents a , TEquipmentInfo b, TNodes c "
				+ " WHERE c.nodeId = b.TNodes.nodeId AND a.equSerialNo=b.equSerialNo ";
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY a.eventId  ASC ";
        IDataCollection<Map<String, Object>> list = dao.executeQuery(pageNo, pageSize, hql, parameters);
        if(locale.toString().equals("en") || locale.toString().equals("en_US")){
            List<Map<String,Object>> listMap = list.getData();
            for(Map<String, Object> map : listMap){
                String key = map.get("eventName").toString();
                String value = eventMap.get(key);
                map.put("eventName", value);

            }

            for(Map<String, Object> map : listMap){
                if("NULL".equals((String)map.get("eventMemo"))){
                    map.put("eventMemo","");
                }
            }
            return list;
        }
		return list;
	}

	/**
	 * ��ȡ���ݿ�Ļ����¼���Ϣ
	 * @param parameters ��ѯ��������
	 * @return List<Map<String, Object>>
	 */
	@Override
	public List<Map<String, Object>> getDeviceMachineEventStatChart(
            Locale locale,Collection<Parameter> parameters,Map<String,String> eventMap) {
		String hql = "SELECT NEW MAP( "
				+ " a.equSerialNo AS EQUSERIALNO,"
				+ " DATE_FORMAT(a.eventTime ,'%Y-%m-%d %T') AS EVENTTIME,"
				+ " a.eventName AS EVENTNAME)"
				+ " FROM TUserEquEvents a , TEquipmentInfo b, TNodes c "
				+ " WHERE c.nodeId = b.TNodes.nodeId AND a.equSerialNo=b.equSerialNo ";
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY a.eventTime  DESC ";
        List<Map<String, Object>> list = dao.executeQuery(hql, parameters);
        if(locale.toString().equals("en") || locale.toString().equals("en_US")){
            for(Map<String, Object> map : list){
                String key = map.get("EVENTNAME").toString();
                String value = eventMap.get(key);
                map.put("EVENTNAME", value);
            }
            return list;
        }
        return list;
	}
	
	public List<Map<String, Object>> getDeviceMachineEventStatChartDateCount(
			Collection<Parameter> parameters) {
		String hql = "SELECT distinct NEW MAP( "
				+ " DATE_FORMAT(a.eventTime ,'%Y-%m-%d') AS EVENTTIME)"
				+ " FROM TUserEquEvents a , TEquipmentInfo b, TNodes c "
				+ " WHERE c.nodeId = b.TNodes.nodeId AND a.equSerialNo=b.equSerialNo ";
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		hql += " ORDER BY a.eventTime ";
		return dao.executeQuery(hql, parameters);
	}
	
	/**
	 * �û���ѯ��̨����֮��� OEE ���ƱȽϽ�� 
	 * @param startTime ��ʼʱ��	
	 * @param endTime	����ʱ��
	 * @param dateTimeType ʱ������
	 * @param serNames ����豸���к�
	 * @return List<DeviceInfoModel>
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<DeviceInfoModel> getDevicesOEECompare(Date startTime,
			Date endTime, String dateTimeType, String[] serNames) {
		List<DeviceInfoModel> result = new ArrayList<DeviceInfoModel>();
		Collection<Parameter> parameters = new HashSet<Parameter>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// ���ݴ���Ĳ������岻ͬ��HQL
		if (dateTimeType.equals("��") || dateTimeType.equals("Day")) {
			String hql =" FROM TUserEquOeedaily WHERE DATE_FORMAT(caclDate,'%Y-%m-%d %T')>='"+StringUtils.formatDate(startTime, 2)+"' "
					+ " AND DATE_FORMAT(caclDate,'%Y-%m-%d %T')<='"+StringUtils.formatDate(endTime, 2)+"' AND equSerialNo IN (";
			for (int i = 0; i < serNames.length; i++) {
				// �жϵ�ǰ����ֵ�Ƿ�Ϊ���һλ
				if (i == (serNames.length - 1)) {
					hql += " '" + serNames[i] + "' ) ";
				} else {
					hql += " '" + serNames[i] + "' ,";
				}
			}
			//parameters.add(new Parameter("startTime", startTime, Operator.EQ));
			//parameters.add(new Parameter("endTime", endTime, Operator.EQ));

			hql += " ORDER BY caclDate ";
			List<TUserEquOeedaily> deList = dao.executeQuery(hql, parameters);
			for (TUserEquOeedaily t : deList) {
				DeviceInfoModel dm = new DeviceInfoModel();
				dm.setEquSerialNo(t.getEquSerialNo());
				dm.setOeeTime(format.format(t.getCaclDate()));
				dm.setDayOeevalue(t.getDayOeevalue().toString());
				dm.setDayTeepvalue(t.getDayTeepvalue().toString());
				result.add(dm);
			}
			if (result.size() > 0) {
				return result;
			}
		} else {
			String hql = " FROM TUserEquOeestore WHERE equSerialNo IN (";
			for (int i = 0; i < serNames.length; i++) {
				// �жϵ�ǰ����ֵ�Ƿ�Ϊ���һλ
				if (i == (serNames.length - 1)) {
					hql += " '" + serNames[i] + "' ) ";
				} else {
					hql += " '" + serNames[i] + "' ,";
				}
			}

			String start = StringUtils.formatDate(startTime, 2);
			String end = StringUtils.formatDate(endTime, 2);
			String[] starts = start.split("-");
			String[] ends = end.split("-");
			
			if (dateTimeType.equals("��") || dateTimeType.equals("Week")) {
				System.out.println( StringUtils.getYearWeek(startTime) +"-------------"+StringUtils.getYearWeek(endTime));
				hql=hql+" and concat(year,case when length(weekofYear)<2 then concat('0',weekofYear) "+
                        "   when  length(weekofYear)>1 then concat('',weekofYear) end) "+
						"<='"+StringUtils.getYearWeek(endTime)+"' "+
						" and "+
						"concat(year,case when length(weekofYear)<2 then concat('0',weekofYear) "+
                        "   when  length(weekofYear)>1 then concat('',weekofYear) end) "+
						">='"+StringUtils.getYearWeek(startTime)+"' "+
						" and month=0 and weekofYear!=0 "+
						" order by year,weekofYear";

			} else if (dateTimeType.equals("��") || dateTimeType.equals("Month")) {
				
				
				hql=hql+" and concat(year,case when length(month)<2 then concat('0',month) "+
                        "   when  length(month)>1 then concat('',month) end) "+
						"<='"+(ends[0] + ends[1])+"' "+
						" and "+
						"concat(year,case when length(month)<2 then concat('0',month) "+
                        "   when  length(month)>1 then concat('',month) end) "+
						">='"+(starts[0] + starts[1])+"' "+
						" and weekofYear=0 and month!=0 "+
						" order by year,month";
			} else if (dateTimeType.equals("��") || dateTimeType.equals("Year")) {
				
				
				hql = hql + " and year>=:syear " + " and year<=:eyear "
						+ " and month=0 " + " and weekofYear=0 and year!=0 "
						+ "  order by year";
				parameters.add(new Parameter("syear",
						Long.parseLong(starts[0]), Operator.EQ));
				parameters.add(new Parameter("eyear",
						Long.parseLong(ends[0]), Operator.EQ));
			} else {
				return result;
			}

			List<TUserEquOeestore> tueList = dao.executeQuery(hql, parameters);
			for (TUserEquOeestore te : tueList) {
				DeviceInfoModel dm = new DeviceInfoModel();
				dm.setEquSerialNo(te.getEquSerialNo());
				dm.setDayOeevalue(te.getTotalOeevalue().toString());
				dm.setDayTeepvalue(te.getTotalTeepvalue().toString());
				if ("��".equals(dateTimeType) || dateTimeType.equals("Week")) {
					dm.setOeeTime(te.getYear() + "-" + te.getWeekofYear());
				} else if ("��".equals(dateTimeType) || dateTimeType.equals("Month")) {
					dm.setOeeTime(te.getYear() + "-" + te.getMonth());
				} else if ("��".equals(dateTimeType) || dateTimeType.equals("Year")) {
					dm.setOeeTime(te.getYear() + "");
				}
				result.add(dm);
			}
			if (result.size() > 0) {
				return result;
			}
		}
		return result;
	}

	/**
	 * ��̨�豸ʱ��η���  ���Ի���
	 * @param map ��Ҫת��������
	 */
	public void convertData(Map<String, Object> map,Locale locale) {

		DecimalFormat decimal = new DecimalFormat("#.##");// ��С����2λ
		/*
		 * ��ӿ�����
		 */
		Long worktimeFact = (Long) map.get("worktimeFact");// ʵ�ʿ���ʱ��
		Long worktimePlan = (Long) map.get("worktimePlan");// �ƻ�����ʱ��
		double number = 0.00;
		if (null != worktimePlan && 0 != worktimePlan && null != worktimeFact
				&& 0 != worktimeFact) {// �ж�
			number = (double) (Double.parseDouble(worktimeFact.toString()) / Double
					.parseDouble(worktimePlan.toString()));// ������
			map.put("availability", Double.parseDouble(decimal.format(number)));
		} else if (null != worktimeFact
				&& (null == worktimePlan || 0 == worktimePlan)) {// �ж�
			number = (double) (Double.parseDouble(worktimeFact.toString()) / 8);// ������
			map.put("availability", Double.parseDouble(decimal.format(number)));
		} else {
			map.put("availability", 0.00);
		}

		/*
		 * ��ӱ�����
		 */
		Long acturalOutputTheorytime = (Long) map
				.get("acturalOutputTheorytime");// ʵ�ʲ������ۺ�ʱ
		if (null != worktimeFact && 0 != worktimeFact
				&& null != acturalOutputTheorytime
				&& 0 != acturalOutputTheorytime) {
			double numbera = (double) (Double
					.parseDouble(acturalOutputTheorytime.toString()) / Double
					.parseDouble(worktimeFact.toString()));// ������
			map.put("display", Double.parseDouble(decimal.format(numbera)));
		} else {
			map.put("display", 0.00);
		}
		/*
		 * �������ָ��
		 */
		Double qualifiedPartCount = (Double) map.get("qualifiedPartCount");// �ϸ񹤼���
		Double processPartsCount = (Double) map.get("processPartsCount");// ʵ�ʲ���
		if (null != processPartsCount && 0.0 != processPartsCount
				&& null != qualifiedPartCount && 0.0 != qualifiedPartCount) {
			double numberb = (double) (Double.parseDouble(qualifiedPartCount
					.toString()) / Double.parseDouble(processPartsCount
					.toString()));// ����ָ��
			map.put("quality", Double.parseDouble(decimal.format(numberb)));
		} else {
			map.put("quality", 0.00);
		}
		/**
		 * ���oee
		 * 
		 * ***/
		Double availability = (Double) map.get("availability");//������
		Double display = (Double) map.get("display");//������
		Double quality = (Double) map.get("quality");//����ָ��
		if(null != availability && 0.0 != availability  && null != display && 0.0 != display  && null != quality 
		       && 0.0 != quality){
			double numberoee=availability*display*quality;
			map.put("dayOeevaluea", Double.parseDouble(decimal.format(numberoee)));
		}else{
			map.put("dayOeevaluea", 0.00);
		}
		/*
		 * 
		 * �豸������
		 */
		Double totalTime = (Double) map.get("totalTime"); // ��ʹ��ʱ��
		if (null != worktimeFact && 0 != worktimeFact && null != totalTime
				&& 0.0 != totalTime) {
			double numberd = (double) (Double.parseDouble(worktimeFact
					.toString()) / Double.parseDouble(totalTime.toString()));
			map.put("ratio", Double.parseDouble(decimal.format(numberd)));
		} else {
			map.put("ratio", 0.00);
		}
		/**
		 * ���teep
		 * 
		 * **/
		Double dayOeevaluea = (Double) map.get("dayOeevaluea"); // oee
		if(null != dayOeevaluea && 0.0 != dayOeevaluea && null != worktimePlan && 0.0 != worktimePlan 
				&& null != totalTime && 0.0 != totalTime){
			double numberteep=dayOeevaluea*worktimePlan/totalTime;
			map.put("dayTeepvaluea", Double.parseDouble(decimal.format(numberteep)));
		}else{
			map.put("dayTeepvaluea", 0.00);
		}
		
		/*
		 * �޸�key��ʵ������ʱ�䣬�ƻ�����ʱ�䣬ʵ�ʲ������ۺ�ʱ
		 */
		if (null != worktimeFact && 0 != worktimeFact) {
			map.put("worktimeFacta", StringUtils.SecondIsDDmmss(worktimeFact,locale));
		} else {
			map.put("worktimeFacta", StringUtils.SecondIsDDmmss(0,locale));
		}

		if (null != worktimePlan && 0 != worktimePlan) {
			map.put("worktimePlana", StringUtils.SecondIsDDmmss(worktimePlan,locale));
		} else {
			map.put("worktimePlana", StringUtils.SecondIsDDmmss(0,locale));
		}

		if (null != acturalOutputTheorytime && 0 != acturalOutputTheorytime) {
			map.put("acturalOutputTheorytimea",
					StringUtils.SecondIsDDmmss(acturalOutputTheorytime,locale));
		} else {
			map.put("acturalOutputTheorytimea", StringUtils.SecondIsDDmmss(0,locale));
		}

	}
	
	/**
	 * ���ݽڵ�ID��ȡ�ڵ�����
	 * @param nodeid �ڵ�id
	 * @return List
	 */
	@Override
	public List<Map<String,Object>> getNodeName(String nodeid) {
		String hql = "SELECT new Map(a.nodeName as nodeName ,a.path as path)" + " FROM TNodes a "
				+ "where a.nodeId = " + "'" + nodeid + "'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return dao.executeQuery(hql, parameters);
	}

	/**
	 * flagΪ2ʱ��   �豸�µ��豸
	 * @param userId �û�id
	 * @return List<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getEquipmentAllEquName(String userId) {
		String hql = "SELECT new map( te.equSerialNo as equSerialNo,"
				+ " te.equName as equName)"
				+ " FROM TMydevicesInfo t,TEquipmentInfo te"
				+ " where t.userid = " + "'" + userId + "'" + " and "
				+ " t.serialno = te.equSerialNo";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String, Object>> list = dao.executeQuery(hql, parameters);
		return list;

	}

	/**
	 * ��ȡ��ǰ�ڵ����ӽڵ�
	 * @param nodeId  �ڵ�id
	 * @return String
	 */
	@Override
	public String getNodeAllId(String nodeId) {
		List<String> nodeIdList = new ArrayList<String>();// ��ǰ�ڵ�����ӽڵ��id
		nodeIdList=getNodesAllId(nodeId);// �ݹ��ȡǰ�ڵ�id���ӽڵ��id
		String nodeid = StringUtils.returnHqlIN(nodeIdList);
		return nodeid;
	}

	/**
	 * ��ȡ��ǰ�ڵ��������豸������״̬�������ӽڵ㣩
	 * @param nodeid �ڵ�id
	 * @param pageNo ҳ��
	 * @param size ����
	 * @param status ״̬
	 * @return IDataCollection<Map<String, Object>>
	 */
	@Override
	public IDataCollection<Map<String, Object>> getNodesAllEquNameStruts(
			String nodeid, int pageNo, int size, String status) {
		List<String> nodeIdList = new ArrayList<String>();// ��ǰ�ڵ�����ӽڵ��id
		try {
			Collection<Parameter> parameters = new HashSet<Parameter>();
			nodeIdList=getNodesAllId(nodeid);// �ݹ��ȡǰ�ڵ�id���ӽڵ��id
			String nodeidstr = StringUtils.returnHqlIN(nodeIdList);
			String hql2 = "select new Map(" + " a.equId as equId, "
					+ " a.equName as equName,"
					+ " a.equSerialNo as equSerialNo,"
					+ " '' as grandfathername," + " '' as parentname,"
					+ " b.status as status) "
					+ " from TEquipmentInfo a,TUserEquCurStatus b "
					+ " where a.equSerialNo=b.equSerialNo "
					+ " and a.TNodes.nodeId in(" + nodeidstr + ") ";

			if (null != status && !"".equals(status)) {
				hql2 = hql2 + " and b.status='" + status + "'";
			}
			IDataCollection<Map<String, Object>> rs = dao.executeQuery(pageNo,size, hql2, parameters);
			if (rs.getData().size() > 0)
				return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���ݽڵ��ѯ���豸����״̬
	 * @param nodeid �ڵ�id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getNodesAllEquNameStruts(String nodeid) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String, Object>> rs=new ArrayList<Map<String,Object>>();
		try {
			 String sql="select a.equ_SerialNo as equSerialNo," +
		 				  " a.equ_name as equName," +
		 				  " a.path as path," +
		 				  " a.x_AXIS as xAxis," +
		 				  " a.y_Axis as yAxis," +
		 				  " a.IPAddress as IPAddress," +
		 				  " b.updateTime as updateTime," +
		 				  " b.status as status" + 
			 		    " from T_EquipmentAddInfo a left join T_UserEquCurStatus b on a.equ_SerialNo=b.equ_SerialNo" +
			 		    " where a.nodeID in(" + nodeid + ") "
			 		  + " and (a.x_AXIS is not null or a.y_Axis is not null)";
			 rs=dao.executeNativeQuery(sql, parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return rs;
		}
		return rs;
	}
	
	/**
	 * ���ݽڵ�id ��ѯ�ӽڵ��������豸��Ϣ
	 * @param nodeid �ڵ�id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getNodesAllEquNameInfo(String nodeid) {
		List<String> nodeIdList = new ArrayList<String>();// ��ǰ�ڵ�����ӽڵ��id
		List<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
		try {
			Collection<Parameter> parameters = new HashSet<Parameter>();
			nodeIdList=getNodesAllId(nodeid);// �ݹ��ȡǰ�ڵ�id���ӽڵ��id
			String nodeidstr = StringUtils.returnHqlIN(nodeIdList);
			String hql2="select new Map(count(b.status) as count,b.status as status) "
					+ " from TEquipmentInfo a,TUserEquCurStatus b "
					+ " where a.equSerialNo=b.equSerialNo "
					+ " and a.TNodes.nodeId in(" + nodeidstr + ") " +
					  " group by b.status";
			rs = dao.executeQuery(hql2, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * ��ʷ������ѯȫ�������
	 * @param parameters��ѯ��������
	 * @return List
	 */
	@Override
	public List getAllDeviceProFrequenceStat(Collection<Parameter> parameters) {
		String hql = "SELECT c.workTime AS workTime "
				+ " FROM TNodes a, TEquipmentInfo b, TUserEquWorkEvents c "
				+ " where a.nodeId = b.TNodes.nodeId "
				+ " and b.equSerialNo = c.equSerialNo ";
		for (Parameter p : parameters) {
			hql += " AND " + p;
		}
		return dao.executeQuery(hql, parameters);
	}

	/**
	 * ��ʱ���UserEquCurStatus�����ݳ�����Сʱû�в�������Ϊͣ����
	 */
	@Override
	public void UpdateUserEquCurStatus() {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="FROM TUserEquCurStatus where status!='�ػ�' and status!='�ѻ�'";
		try {
			List<TUserEquCurStatus> rs =dao.executeQuery(hql, parameters);
			for(TUserEquCurStatus tt:rs){
				Date da=new Date();
				if(da.getTime()-tt.getUpdateTime().getTime()>Constants.CONTROL_STOP_TIME){
					tt.setUpdateTime(da);
					tt.setStatus("�ػ�");
					TUserEquEvents tu=new TUserEquEvents();
					tu.setEquSerialNo(tt.getEquSerialNo());
					tu.setEventTime(da);
					tu.setEventName("ͣ��");
					commonService.update(tt);
					commonService.save(tu);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ�����ƻ������
	 * @param nodeid �ڵ�id
	 * @param date ʱ�� ��ʽyyyy-MM
	 * @return List<Map<String, Object>>
	 */
	
	public List<Map<String, Object>> getppcr(String nodeid,String date){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String, Object>> rs=new ArrayList<Map<String,Object>>();
		String hql="select new Map((sum(finishNum)/sum(planNum)*100) as wcl) from TJobplanInfo" +
				  " where status=50";
		String ahql="select new Map(sum(a.finishNum)/sum(a.processNum)*100 as wcl) from TJobdispatchlistInfo a"
				+ " where a.TProcessInfo.offlineProcess=1 and a.status=50"
				+ " and a.nodeid='"+nodeid+"'";
		try {
			 rs=dao.executeQuery(ahql);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> mm=new HashMap<String, Object>();
			mm.put("wcl", 0);
			rs.add(mm);
			return rs;
		}
		return rs;
	}
	
	/**
	 * ��ȡ���ռӹ���
	 * @param nodeid
	 * @param date ��ʽyyyy-MM-dd
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getNumberOfDayProcessing(String nodeid,String date){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select new Map(a.dailyOutput as dailyOutput)" +
				  " from TNodeProductionProfiles a " +
				  " where " +
				  " a.TNodes.nodeId ='"+nodeid+"'" +
				  " and DATE_FORMAT(a.updateTime,'%Y-%m-%d')='"+date+"'";
		List<Map<String, Object>> rs=new ArrayList<Map<String,Object>>();
		try {
			 rs=dao.executeQuery(hql, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * ��ȡ�豸Ч��
	 * @param nodeid
	 * @param date
	 * @return
	 */
	public List<Map<String,Object>> getEquEfficiency(String nodeid,String date){
		Collection<Parameter> parameters = new HashSet<Parameter>(); 
		String hql="select new Map(a.equSerialNo as equSerialNo,a.dayOeevalue as dayOeevalue)" +
				  " from TUserEquOeedaily a,TEquipmentInfo b" +
				  " where a.equSerialNo=b.equSerialNo" +
				  " and b.TNodes.nodeId in("+nodeid+")" +
				  " and DATE_FORMAT(a.caclDate,'%Y-%m-%d')='"+date+"'";
		List<Map<String, Object>> rs=new ArrayList<Map<String,Object>>();
		try {
			 rs=dao.executeQuery(hql, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 *����״̬
	 * @param equSerialNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getMachineToolStatus(String equSerialNo){
		Date date=new Date();
		Collection<Parameter> parameters = new HashSet<Parameter>(); 
		String sql="SELECT a. equ_serialno AS equSerialNo,"+
			       " a.status as status,"+ 
			       " a.updateTime as updateTime,"+ 
			       " ROUND(a.xfeed,3) AS xfeed,"+
			       " ROUND(a.yfeed,3) AS yfeed,"+
			       " ROUND(a.zfeed,3) AS zfeed,"+
			       " ROUND(a.afeed,3) AS afeed,"+
			       " ROUND(a.bfeed,3) AS bfeed,"+
			       " ROUND(a.cfeed,3) AS cfeed,"+
			       " ROUND(a.ufeed,3) AS ufeed,"+
			       " ROUND(a.vfeed,3) AS vfeed,"+
			       " ROUND(a.wfeed,3) AS wfeed,"+
			       " ROUND(a.feedSpeed) AS feedSpeed,"+
			       " ROUND( a.axisspeed) AS axisspeed,"+
			       " IFNULL(b.cuttingTime,0)  AS cuttingTime,"+
			       " IFNULL(b.dryRunningTime+b.toolChangeTime,0) AS assistedTime,"+
			       " IFNULL(b.prepareTime,0) AS prepareTime,"+
			       " IFNULL(b.idleTime,0) AS idleTime,"+
			       " IFNULL(b.stopTime,86400) AS stopTime"+
			" FROM t_userequcurstatus a LEFT JOIN ("+
			" SELECT * FROM t_userequstatusdaily WHERE equ_serialno='"+equSerialNo+"'"+
			" AND LEFT(updatedate,10)='"+StringUtils.formatDate(date, 2)+"') b"+
			" ON a.equ_serialno=b.equ_serialno"+
			" WHERE a.equ_serialno='"+equSerialNo+"'";
			
		List<Map<String, Object>> rs=new ArrayList<Map<String,Object>>();
		System.err.println(rs.size());
		try {
			 rs=dao.executeNativeQuery(sql, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * �ӹ�����
	 * @param equSerialNo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getMachiningTask(String equSerialNo){
		String equhql=" from TEquJobDispatch where equSerialNo='"+equSerialNo+"' and status=2";
		List equlist=dao.executeQuery(equhql);//�ж��Ƿ����豸 �������й���
		
		String hqlone="select DISTINCT new Map(" +
				" a.no as jobDispatchListNo," +
				" a.processNum as processNum," +
				" a.finishNum as finishNum," +
				" a.theoryWorktime*(a.processNum-a.finishNum)/60 as estimateLastTime," +
				" d.no as no," +
				" d.name as name,"
			+   " d.path as path,"
			+ "   a.TProcessInfo.name as processName)" +
		  " from TJobdispatchlistInfo a,TPartTypeInfo d ,TEquJobDispatch b" +
		  " where b.jobdispatchNo=a.no and a.TProcessInfo.TProcessplanInfo.TPartTypeInfo.id=d.id " +
		  " and b.equSerialNo='"+equSerialNo+"'";
		if(null!=equlist&&equlist.size()>0){
			hqlone+=" and b.status=2";
		}  
		  
		hqlone+= " order by a.id desc";
		List<Map<String,Object>> rsone=dao.executeQuery(hqlone);
		List list=new ArrayList();
		for(Map<String,Object> map:rsone){
			list.add(map.get("jobDispatchListNo").toString());
		}
		if(list.size()>0){
			String sql="SELECT  cuttingtask as cuttingtask  FROM T_UserEquWorkEvents "
					+ " WHERE cuttingTask IN("+StringUtils.returnHqlIN(list)+") "
					+ " ORDER BY id DESC limit 2 ";
			List<Map<String, Object>> ls=dao.executeNativeQuery(sql);
			if(null!=ls&&ls.size()>0){
				Map<String, Object> mm=ls.get(0);
				List<Map<String, Object>> rs=new ArrayList<Map<String, Object>>();
				for(Map<String,Object> map:rsone){
					if(map.get("jobDispatchListNo").toString().equals(mm.get("cuttingtask").toString())){
						rs.add(map);
					}
				}
				return rs;
			}else{
				return rsone;
			}
		}else{
			return rsone;
		}
		
		
	}
	
	
	
	/**
	 * �豸Ч��
	 * @param equSerialNo
	 * @return Map<String,Object>
	 */
	public Map<String,Object> getEquipmentEfficiency(String equSerialNo){
		Date date=new Date();
		Collection<Parameter> parameters = new HashSet<Parameter>();
		DecimalFormat f = new DecimalFormat("0");
		String hql="from TUserEquOeedaily where equSerialNo='"+equSerialNo+"'" +
				  " and DATE_FORMAT(caclDate,'%Y-%m-%d')='"+StringUtils.formatDate(date, 2)+"'";
		Map<String,Object> map=new HashMap<String, Object>();
		List<Map<String,Object>> rs=dao.executeQuery(hql, parameters);
		if(StringUtils.listIsNull(rs)){
			TUserEquOeedaily  tueo=(TUserEquOeedaily)rs.get(0);
			Long worktimeFact = tueo.getWorktimeFact(); // ʵ�ʿ���ʱ��
			Long worktimePlan = tueo.getWorktimePlan(); // �ƻ�����ʱ��
			Long acturalOutputTheorytime = tueo.getActuralOutputTheorytime(); // ʵ�ʲ������ۺ�ʱ
			double processPartsCount = tueo.getProcessPartsCount(); // �ӹ�������
			double qualifiedPartCount = tueo.getQualifiedPartCount(); // �ϸ񹤼���
			double availability = worktimeFact/ Double.parseDouble(worktimePlan.toString());// ������
			double expressivenessOf = acturalOutputTheorytime/ Double.parseDouble(worktimeFact.toString());// ������													
			double quality = qualifiedPartCount / processPartsCount; // ����
			if (worktimePlan != 0) {
				map.put("availability", f.format(availability*100));
			} else {
				map.put("availability", "0.00");
			}
			if (worktimeFact != 0) {
				map.put("expressivenessOf", f.format(expressivenessOf*100));
			} else {
				map.put("expressivenessOf", "0.00");
			}
			if (processPartsCount != 0) {
				map.put("quality", f.format(quality*100));
			} else {
				map.put("quality","0.00");
			}
			map.put("oee", f.format(tueo.getDayOeevalue()*100));
		}else{
			map.put("availability", "0.00");
			map.put("expressivenessOf", "0.00");
			map.put("quality","0.00");
			map.put("oee", "0.00");
		}
		return map;
	}
	
	/**
	 * ��ȡ�ƶ��豸�ĵ��ռӹ���
	 * @param nodeid
	 * @param date ��ʽyyyy-MM-dd
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getappointdayEquNumber(String[] equSerialNo,String date){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List sl=new ArrayList();
		for(String tt:equSerialNo){
			sl.add(tt);
		}
		String equStr=StringUtils.returnHqlIN(sl);
		String hql="select count(*) from TUserEquWorkEvents " +
				   " where equSerialNo in("+equStr+") and DATE_FORMAT(finishtime,'%Y-%m-%d')='"+date+"'";
		List rs=new ArrayList();
		try {
			 rs=dao.executeQuery(hql, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * �����豸���кŻ�ȡ�豸��ά�����ַ
	 * @param equSerialNo
	 * @return
	 */
	public List getipAddress(String equSerialNo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select ipAddress as ipAddress from TEquipmentInfo where equSerialNo='"+equSerialNo+"'";
		return dao.executeQuery(hql, parameters);
	}
	/**
	 *�����豸���кŻ�ȡ�豸ʵʱ��Ϣ
	 * @param equSerialNo
	 * @return
	 */
	public List<TUserEquCurStatus> getTUserEquCurStatusByEqusrialno(String equSerialNo){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TUserEquCurStatus where equSerialNo='"+equSerialNo+"'";
		return dao.executeQuery(hql, parameters);
	}
	/**
	 * ͨ����ǰ�ڵ��ѯ���ڵ�
	 */
	public String getParentIdByNodeid(String nodeid){
		String parentid="";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TNodes t where t.nodeId='"+nodeid+"'";
		List<TNodes> rs=dao.executeQuery(hql, parameters);
		if(rs.size()>0){
			TNodes tn = rs.get(0);
			parentid = tn.getTNodes().getNodeId();
		}
		return parentid;
	}
	/**
	 * ��ѯ�ڵ��µ��豸
	 * @param pId
	 * @return
	 */
	public List getTEquipmentInfoByPId(String pId){
		String hql="select equSerialNo as equSerialNo from TEquipmentInfo where TNodes.TNodes.nodeId='"+pId+"' ";
		return dao.executeQuery(hql);
	}
	/**
	 * ����id��ȡ�豸�嵥��Ϣ
	 * @param nodeId
	 * @return
	 */
	public List<Map<String,Object>> getEquInventoryInfo(String nodeId){
		String sql="SELECT * FROM ( "
						+ " SELECT k.equName,k.equSerialNo ,k.status,l.name AS operatorName,k.updateTime,k.equStatus"
						+ " FROM("
						+ "		SELECT a.equ_name AS equName,a.equ_serialno AS equSerialNo,b.status  AS status,b.updateTime as updateTime,"
						+ "		j.operator_no AS operatorNo,CASE a.status=1 WHEN 1 THEN '�Զ�ģʽ' WHEN 0 THEN '�ֶ�ģʽ' END AS equStatus"
						+ "		FROM t_equipmentaddinfo a,t_userequcurstatus b,r_equipment_member_info j"
						+ "		WHERE a.equ_serialno=b.equ_serialno AND a.equ_ID=j.equ_ID "
						+ "		AND (a.nodeID='"+nodeId+"' "
						+ "			OR a.nodeID IN (SELECT nodeid FROM t_nodes WHERE p_nodeid='"+nodeId+"')) "
						+ "     order by a.equ_serialno) k "
						+ "	LEFT JOIN t_member_info l"
						+ "	ON k.operatorNo=l.no "
						+ "	) h"
				+ " LEFT JOIN ("
					+ "	SELECT c.equ_SerialNo AS equSerialNo,e.name AS processName,g.name AS partName"
					+ "	FROM t_equ_jobdispatch c,t_jobdispatchlist_info d,t_process_info e,t_processplan_info f,t_part_type_info g"
					+ "	WHERE c.jobdispatchNo=d.no AND d.processID=e.ID  AND e.processPlanID=f.ID AND f.parttypeID=g.ID AND c.status=2) i"
				+ " ON h.equSerialNo=i.equSerialNo";
		List<Map<String,Object>> equInventory=dao.executeNativeQuery(sql);
		for(Map<String,Object> map:equInventory){
			String sta=null==map.get("status")?null:map.get("status").toString();
			
			Date da=new Date();
			 if(null==map.get("updateTime")||"".equals(map.get("updateTime"))){
				 
			 }else if(da.getTime()-((Date)map.get("updateTime")).getTime()>Constants.CONTROL_TUOJI_TIME){
				sta="�ѻ�";
				map.put("status", "�ѻ�");
			 }
			if(sta.equals("����")||sta.equals("�ӹ�")){
				map.put("color", "yx"); 
			 }else if(sta.equals("�ѻ�")||sta.equals("�ػ�")||sta.equals("ͣ��")){
				 map.put("color", "tj");
			 }else if(sta.equals("׼��")){
				 map.put("color", "zb");
			 } else if(sta.equals("����")||sta.equals("�ж�")||sta.equals("��ͣ")){
				 map.put("color", "gz");
			 }else if(sta.equals("����")){
				 map.put("color", "kx");
			 }else{
				 map.put("color", "kx");
			 }
		}
		
		
		 for(int i=0;i<19-equInventory.size();i++){
			 Map<String,Object> map=new HashMap<String, Object>();
			 map.put("equName","��");
			 map.put("equSerialNo", null);
			 map.put("status", null);
			 map.put("processName", null);
			 map.put("partName", null);
			 map.put("operatorName", null);
			 equInventory.add(map);
		 }
		 System.out.println(equInventory.size());
		return equInventory;
	}
	
	@Override
	/**
	 * �ֶ�������Ϣ����
	 */
	public String saveInfo(int num,String userId,String equId,String dispatchId,Date startTime,Date finishTime,String partNo,String loginUserNo,String isGood,
			 String depName,String jgCheckUser,String zpCheckUser,String sjCheckUser) {
		try{
			Collection<Parameter> parameters = new HashSet<Parameter>();
			String sql = "";
			String tempEventNo ="";
			int partId =0;
			String partName ="";
			List<Map<String,Object>> jobDispatchInfo = dao.executeNativeQuery("select no,processNum,finishNum,jobplanID,batchNo,nodeid,processID,wisScrapNum from T_JOBDISPATCHLIST_INFO where ID ="+dispatchId+"", parameters);
			List<Map<String,Object>> memberInfo = dao.executeNativeQuery("select no,name from t_member_info where ID ="+userId+"", parameters);
			List<Map<String,Object>> partInfo = dao.executeNativeQuery("select ID,name from t_part_type_info where no ='"+partNo+"'", parameters);
			List<Map<String,Object>> equInfo = dao.executeNativeQuery("select equ_SerialNo from t_equipmentaddinfo where equ_ID ="+equId+"", parameters);
			
			String userNo = memberInfo.get(0).get("no").toString();
			String userName = memberInfo.get(0).get("name").toString();
			String equ_SerialNo = equInfo.get(0).get("equ_SerialNo").toString();
			if(partInfo.size()>0){
			   partId = Integer.parseInt(partInfo.get(0).get("ID").toString());
			   partName = partInfo.get(0).get("name").toString();
			}
			
			String jobdispatchNo = jobDispatchInfo.get(0).get("no").toString();
			String processId = jobDispatchInfo.get(0).get("processID").toString();
			String noteId = jobDispatchInfo.get(0).get("nodeid").toString();
			int finishNum = Integer.parseInt(jobDispatchInfo.get(0).get("finishNum").toString());
			int planNum = Integer.parseInt(jobDispatchInfo.get(0).get("processNum").toString());
			int wisScrapNum = Integer.parseInt(jobDispatchInfo.get(0).get("wisScrapNum").toString());
			Long jobplanId = null;
			if(jobDispatchInfo.get(0).get("jobplanID") != null ){
				jobplanId =Long.parseLong(jobDispatchInfo.get(0).get("jobplanID").toString());
			}
			
			List<Map<String,Object>> processInfo = dao.executeNativeQuery("select offlineProcess,no,process_order from t_process_info where ID ="+processId+"", parameters);
			
			String processOrder= processInfo.get(0).get("process_order").toString();
			int offlineProcess =Integer.parseInt(processInfo.get(0).get("offlineProcess").toString());
			if(finishNum +num+wisScrapNum >planNum){
				return "���������������ޣ�";
			}else{
				//���¹���״̬
				if(finishNum ==0){
					sql = "update t_jobdispatchlist_info set status =50 where ID ="+dispatchId+";";
					dao.executeNativeUpdate(sql, parameters);
				}
				if(finishNum+num+wisScrapNum ==planNum){
					sql = "update t_jobdispatchlist_info set status =70,real_endtime ='"+StringUtils.formatDate(new Date(), 3)+"' where ID ="+dispatchId+";";
					dao.executeNativeUpdate(sql, parameters);
				}
				//�ֶ���������-20140730 FW  start_2  ---------------------------------------------//
				//���¹����������
			    sql ="update t_jobdispatchlist_info set finishNum="+(finishNum+num)+" where ID ="+dispatchId+";";
				dao.executeNativeUpdate(sql, parameters);
//				
//				//���WorkEvents
//				for(int i =0;i<num;i++){
//					sql ="insert into T_UserEquWorkEvents (equ_serialNo,starttime,finishtime,cuttingTask,operator_no,partNo,theoryWorktime,cuttingTime,toolchangeTime,workTime,theoryCycletime,flag) VALUES ((select equ_SerialNo from T_EquipmentAddInfo where equ_ID="+equId+"),"
//							+ "'"+StringUtils.formatDate(startTime, 3)+"','"+StringUtils.formatDate(finishTime, 3)+"',(select no from T_JOBDISPATCHLIST_INFO where ID="+dispatchId+"),(select no from T_Member_INFO where ID="+userId+"),"
//									+ "'"+partNo+"',0,0,0,0,0,0);";
//					dao.executeNativeUpdate(sql, parameters);
//				}
				//��t_production_events�����ݱ���
				TProductionEvents tproductionEvents =new TProductionEvents();
				tproductionEvents.setJobdispatchNo(jobdispatchNo);
				tproductionEvents.setProcessNum(num);
				tproductionEvents.setOperatorNo(loginUserNo);
				tproductionEvents.setOperateDate(new Date());
				tproductionEvents.setOperateReason("�ֶ�����");
				tproductionEvents.setPartTypeID(partId);
				tproductionEvents.setEquSerialNo(equ_SerialNo);
				tproductionEvents.setResponseNo(userNo);
				tproductionEvents.setResponseProcessNo(processOrder);
				tproductionEvents.setResponseDate(new Date());
				tproductionEvents.setStarttime(startTime);
				tproductionEvents.setEndtime(finishTime);
				tproductionEvents.setEventType(2);//1�Ǳ���,2�Ǳ���,3�޸�
				tproductionEvents.setFlag(0);
				
				dao.save(TProductionEvents.class, tproductionEvents);
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				String eventNo = "SD"+df.format(new Date())+tproductionEvents.getId();
				tproductionEvents.setEventNo(eventNo);
				tempEventNo =eventNo;
				dao.update(TProductionEvents.class, tproductionEvents);
				
				// ��t_equproduction�����ݱ���
				List<Map<String,Object>> equProductionInfo = dao.executeNativeQuery("select ID,processNum from t_equproduction where equ_SerialNo='"+equ_SerialNo+"' "
						+ "and partTypeID ="+partId+" and processID ="+processId+""
								+ " and DATE_FORMAT(updateDate,'%Y-%m-%d')='"+StringUtils.formatDate(finishTime, 2)+"'", parameters);
				if(equProductionInfo.size()>0){
					int count =  Integer.parseInt(equProductionInfo.get(0).get("processNum").toString());
					String id =equProductionInfo.get(0).get("ID").toString();
					dao.executeNativeUpdate("update t_equproduction set processNum ="+(count+num)+",updateDate ='"+StringUtils.formatDate(finishTime, 2)+"' where id ="+id+"", parameters);
				}else{
					dao.executeNativeUpdate("insert into t_equproduction (equ_SerialNo,partTypeID,processID,processNum,updateDate) values ('"+equ_SerialNo+"',"+partId+","+processId+","+num+",'"+StringUtils.formatDate(finishTime, 2)+"')", parameters);
				}
				
				// �� t_userproduction�����ݱ���
				List<Map<String,Object>> userProductionInfo = dao.executeNativeQuery("select ID,processNum from t_userproduction where operator_no='"+userNo+"' "
						+ "and partTypeID ="+partId+" and processID ="+processId+""
								+ " and DATE_FORMAT(updateDate,'%Y-%m-%d')='"+StringUtils.formatDate(finishTime, 2)+"'", parameters);
				if(userProductionInfo.size()>0){
					int count =  Integer.parseInt(userProductionInfo.get(0).get("processNum").toString());
					String id =userProductionInfo.get(0).get("ID").toString();
					dao.executeNativeUpdate("update t_userproduction set processNum ="+(count+num)+",updateDate ='"+StringUtils.formatDate(finishTime, 2)+"' where id ="+id+"", parameters);
				}else{
					dao.executeNativeUpdate("insert into t_userproduction (operator_no,partTypeID,processID,processNum,updateDate) values ('"+userNo+"',"+partId+","+processId+","+num+",'"+StringUtils.formatDate(finishTime, 2)+"')", parameters);
				}
				//�ֶ���������-20140730  FW  end_2  ---------------------------------------------//
				
				//��ӱ������ݴ���-20140725 YT  start_1  ---------------------------------------------//
				//���ǰ���������+������=�ƻ���,�����
				TJobdispatchlistInfo record=commonService.get(TJobdispatchlistInfo.class,Long.parseLong(dispatchId));
				TProcessInfo tProcessInfo=record.getTProcessInfo();
				if(record.getStatus()!=60||record.getStatus()!=70)
				{
					//�����ϵ������Ӧ�Ĺ���
					if(tProcessInfo.getOnlineProcessId()!=null)
					{
						Collection<Parameter> f_parameters = new HashSet<Parameter>();
						f_parameters.add(new Parameter("batchNo", record.getBatchNo(), Operator.EQ)); //������batchNo������jobplanId������ͨ��һЩ
						f_parameters.add(new Parameter("TProcessInfo", commonService.get(TProcessInfo.class,tProcessInfo.getOnlineProcessId()), Operator.EQ)); // �ϵ�����		
						List<TJobdispatchlistInfo> f_temp=commonService.find(TJobdispatchlistInfo.class, (List<Sort>)null, f_parameters);
						//����ǰ�򹤵�����£�����ǰ�򹤵��������=���򹤵��������+���򹤵��ķ��ֱ����� Ҳ��ɸù���
						if(f_temp!=null&&f_temp.size()>0)
						{
							  TJobdispatchlistInfo f_jobdispatch=f_temp.get(0);
							  //ǰ�򹤵��Ѿ����
							  if((f_jobdispatch.getFinishNum().intValue()<=record.getFinishNum().intValue()+record.getWisScrapNum().intValue())&&(f_jobdispatch.getStatus()==60||f_jobdispatch.getStatus()==70)){
								  record.setStatus(70);
								  record.setRealEndtime(new Date());
								}
						}
					}
					commonService.save(record);
				}
				//��ӱ������ݴ���-20140725  YT  end_1  ---------------------------------------------//
				
				//���߹�λ����
				if(offlineProcess==1)  //�����߹�λ
				{
					//�����������
					String hql="select new Map(parttype.no as partTypeNo,parttype.name as partTypeName,parttype.id as partTypeId) from TPartProcessCost partprocesscost,TPartTypeInfo parttype,TProcessInfo process" +
							" where partprocesscost.TProcessInfo.id=process.id and partprocesscost.TPartTypeInfo.id=parttype.id "+
							" and process.id="+processId;
					
					Collection<Parameter> parameters1 = new HashSet<Parameter>();				
					List<Map<String,Object>> parttypetemp=dao.executeQuery(hql,parameters1);
					
					if(parttypetemp!=null&&parttypetemp.size()>0)
						{  
						     Map<String,Object> parttypeinfo=parttypetemp.get(0);
						     TPartBasicInfo addPartBasicInfo=new TPartBasicInfo();
						     addPartBasicInfo.setName(parttypeinfo.get("partTypeName").toString());
						     addPartBasicInfo.setPartTypeId(Long.parseLong(parttypeinfo.get("partTypeId").toString()));
						     addPartBasicInfo.setOfflineDate(finishTime);
						  
						     commonService.save(addPartBasicInfo);
						     
						     addPartBasicInfo.setNo(parttypeinfo.get("partTypeNo")+""+addPartBasicInfo.getId());
						     
						   //���½ڵ������ſ� 
						       Date partFinishDate= finishTime;
						       String partFinishiDateStr=StringUtils.formatDate(partFinishDate, 2);//����ʱ��
						       TNodes tnodes=commonService.get(TNodes.class,noteId);
						       //ÿ�����һ�����ݣ��ж��ǲ��뻹�Ǹ���
						        Collection<Parameter> nodeproductionprofile_param = new HashSet<Parameter>();
						        nodeproductionprofile_param.add(new Parameter("TNodes",tnodes, Operator.EQ));
						        nodeproductionprofile_param.add(new Parameter("uprodId",addPartBasicInfo.getPartTypeId().longValue(),Operator.EQ));//B21 ���޲�Ʒ���ͣ���Ʒ����ID�����������IDƥ��
						        List<TNodeProductionProfiles> nodeproductionprofile_rs=commonService.find(TNodeProductionProfiles.class, Arrays.asList (new Sort ("updateTime", Sort.Direction.DESC)), nodeproductionprofile_param);
						        if(nodeproductionprofile_rs!=null&&nodeproductionprofile_rs.size()>0)
						        {
						        	TNodeProductionProfiles tnodeproductionprofiles=nodeproductionprofile_rs.get(0);
						        	
						        	Date updateTime=tnodeproductionprofiles.getUpdateTime();
						        	if(partFinishiDateStr.equals(StringUtils.formatDate(updateTime,2)))
						        		{   //���²�����Ϣ
						        		    tnodeproductionprofiles.setDailyOutput(tnodeproductionprofiles.getDailyOutput()+num);
						        		    tnodeproductionprofiles.setWeeklyOutput(tnodeproductionprofiles.getWeeklyOutput()+num);
						        		    tnodeproductionprofiles.setMonthlyOutput(tnodeproductionprofiles.getMonthlyOutput()+num);
						        		    tnodeproductionprofiles.setAnnualOutput(tnodeproductionprofiles.getAnnualOutput()+num);
						        		    tnodeproductionprofiles.setTotalOutput(tnodeproductionprofiles.getTotalOutput()+num);
						        		    commonService.update(tnodeproductionprofiles);
						        		
						        		}
						        	else {
						        		   //����ʷ��Ϣ�������µ���Ϣ
						        		   int previous_year=updateTime.getYear();
						        		   int previous_month=updateTime.getMonth();
						        		   int year=partFinishDate.getYear();
						        		   int month=partFinishDate.getMonth();
						        		   TNodeProductionProfiles addNodeProductionProfiles=new TNodeProductionProfiles();
						        		   addNodeProductionProfiles.setDailyOutput(new Double(num)); //�ղ���
						        		   addNodeProductionProfiles.setTotalOutput(tnodeproductionprofiles.getTotalOutput()+num); //�ܲ���
						        		   addNodeProductionProfiles.setTNodes(tnodeproductionprofiles.getTNodes()); //�ڵ�
						        		   addNodeProductionProfiles.setUpdateTime(partFinishDate); //����ʱ��
						        		   addNodeProductionProfiles.setUprodId(tnodeproductionprofiles.getUprodId()); //��Ʒ���ID
						        		   
						        		   if(previous_year==year) //ͬ��
						        			   { 
						        			       addNodeProductionProfiles.setAnnualOutput(tnodeproductionprofiles.getAnnualOutput()+num);
						        			       if(previous_month==month) //ͬ�·�
						        			           {
						        			    	      addNodeProductionProfiles.setMonthlyOutput(tnodeproductionprofiles.getMonthlyOutput()+num);
						        			    	      //ͬ��
						        			    	      if(StringUtils.getYearWeek(updateTime).equals(StringUtils.getYearWeek(partFinishDate)))
						        			    	          addNodeProductionProfiles.setWeeklyOutput(tnodeproductionprofiles.getWeeklyOutput()+num);
						        			    	      else addNodeProductionProfiles.setWeeklyOutput(new Double(num));
						        			           }
						        			       else { 
						        			    	   //���·�
						        			    	   addNodeProductionProfiles.setMonthlyOutput(new Double(num));
						        			    	 //���·� ͬ��
						        			    	   if(StringUtils.getYearWeek(updateTime).equals(StringUtils.getYearWeek(partFinishDate)))
						        			    	       {
						        			    	          addNodeProductionProfiles.setWeeklyOutput(tnodeproductionprofiles.getWeeklyOutput()+num);
						        			    	       }
						        			    	   else 
						        			    	       {
						        			    		      addNodeProductionProfiles.setWeeklyOutput(new Double(num));
						        			    	       }
						        			            }
						        			        }
						        		   else 
						        			   {   //����
						        			       addNodeProductionProfiles.setAnnualOutput(new Double(num));
						        			       addNodeProductionProfiles.setMonthlyOutput(new Double(num));
						        			       addNodeProductionProfiles.setWeeklyOutput(new Double(num)); //?����ͬ�ܣ���ô��
						        			   }
						        		   commonService.save(addNodeProductionProfiles);
						        	}
						        }else{
						        	 //û����ʷ��Ϣ�������µ���Ϣ
						        	   TNodeProductionProfiles addNodeProductionProfiles=new TNodeProductionProfiles();				        		
					        		   addNodeProductionProfiles.setTNodes(tnodes); //�ڵ�
					        		   addNodeProductionProfiles.setUpdateTime(partFinishDate); //����ʱ��
					        		   addNodeProductionProfiles.setUprodId(addPartBasicInfo.getPartTypeId().longValue()); //��Ʒ���ID
					        		   addNodeProductionProfiles.setDailyOutput(new Double(num)); //�ղ���
					        		   addNodeProductionProfiles.setTotalOutput(new Double(num)); //�ܲ���
					        		   addNodeProductionProfiles.setAnnualOutput(new Double(num));
			        			       addNodeProductionProfiles.setMonthlyOutput(new Double(num));
			        			       addNodeProductionProfiles.setWeeklyOutput(new Double(num)); 
			        			       commonService.save(addNodeProductionProfiles);
						        }
						}
					

					
					
					//�������μƻ�
					
					if(jobplanId !=null && !StringUtils.isEmpty(jobplanId+""))
					{
						   TJobplanInfo JobplanInfo=commonService.get(TJobplanInfo.class,jobplanId);
						   JobplanInfo.setFinishNum(JobplanInfo.getFinishNum()+num);
						   int qualifiedNum=0;
						   if(JobplanInfo.getQualifiedNum()!=null) qualifiedNum=JobplanInfo.getQualifiedNum();
						   JobplanInfo.setQualifiedNum(qualifiedNum+num);
						   //��ӱ������ݴ���-20140725  start_2  ---------------------------------------------//
						   //
						   if(JobplanInfo.getFinishNum().intValue()+JobplanInfo.getScrapNum().intValue()>=JobplanInfo.getPlanNum().intValue()) 
							 {
							   JobplanInfo.setStatus(70); //�����
							   JobplanInfo.setFinishDate(new Date());
							   JobplanInfo.setRealEndtime(new Date());
							   commonService.update(JobplanInfo);
							   //�����ε��ܷ��ֱ�������β��������֮�͵������μƻ�����ʱ���رո����Σ�ͬʱ���鹤�����ر�
							    String jobdispatchsql="from TJobdispatchlistInfo where jobplanId="+jobplanId;
								List<TJobdispatchlistInfo> tjobdispathclist=commonService.executeQuery(jobdispatchsql);
								if(tjobdispathclist!=null&&tjobdispathclist.size()>0)
								for(TJobdispatchlistInfo tj:tjobdispathclist){
									if(tj.getStatus()!=70||tj.getStatus()!=60) //��û�йرյĹ���ȫ���ر�
									{
										tj.setStatus(70);
										tj.setRealEndtime(new Date());
										commonService.update(TJobdispatchlistInfo.class, tj);
									}
								}
							   
							 }
						   //��ӱ������ݴ���-20140725  end_2  ---------------------------------------------// 	
						   
						   //������ҵ�ƻ�
						   //���´���(1����ֻ��һ���ƻ�)
						   TJobplanInfo p_JobplanInfo=JobplanInfo.getTJobplanInfo();
						   if(p_JobplanInfo!=null){//�����ҵ�ƻ�Ϊnull�����ڴ���
							   //��ȡ��ǰʱ�����ڵ��·�
							   Calendar c = Calendar.getInstance();
							   int currentmonth=c.get(Calendar.MONTH);
							   //ȡ����ҵ�ƻ���ʵ�ʿ�ʼʱ��
							   Calendar pcal=Calendar.getInstance();
							   pcal.setTime(p_JobplanInfo.getRealStarttime()); 
							   int pmonth=pcal.get(Calendar.MONTH);
							   if(pmonth==currentmonth)//��ǰ�·ݵ��ڸ��ƻ����·�
							   {
								   p_JobplanInfo.setFinishNum(p_JobplanInfo.getFinishNum()+num);
								   int qualifiedNum1=0;
								   if(p_JobplanInfo.getQualifiedNum()!=null) qualifiedNum1=p_JobplanInfo.getQualifiedNum();
								   p_JobplanInfo.setQualifiedNum(qualifiedNum1+num);
								   if(p_JobplanInfo.getFinishNum().intValue()==p_JobplanInfo.getPlanNum().intValue()) 
									  {
									       p_JobplanInfo.setStatus(70); //�����
									       p_JobplanInfo.setRealEndtime(new Date()); //�����
									  }
								   commonService.update(p_JobplanInfo);	
							   }else  //�����¸��·ݵ���ҵ�ƻ�
							   {
								   SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM");
								  // c.add(Calendar.MONTH,1); //ȡ��һ����
								   String nextMonth=dayFormat.format(c.getTime());  //����String�͵�ʱ��
								   
								   hql=" from TJobplanInfo t where t.planType=1 " +
								   		" and t.TPartTypeInfo.id="+p_JobplanInfo.getTPartTypeInfo().getId()+
								   		" and DATE_FORMAT(t.realStarttime,'%Y-%m') = '"+nextMonth+"'";
								  List<TJobplanInfo> list= commonService.executeQuery(hql);
								  //�����ҵ��¸��µ���ҵ�ƻ�
								  if(list!=null&&list.size()>0)
								  {
									  TJobplanInfo next_p_jobplanInfo=list.get(0);
									  next_p_jobplanInfo.setFinishNum(next_p_jobplanInfo.getFinishNum()+num);
									   int qualifiedNum1=0;
									   if(next_p_jobplanInfo.getQualifiedNum()!=null) qualifiedNum1=next_p_jobplanInfo.getQualifiedNum();
									   next_p_jobplanInfo.setQualifiedNum(qualifiedNum1+num);
									   if(next_p_jobplanInfo.getFinishNum().intValue()==next_p_jobplanInfo.getPlanNum().intValue()) 
										  {
										   next_p_jobplanInfo.setStatus(70); //�����
										   next_p_jobplanInfo.setRealEndtime(new Date()); //�����
										  }
									   commonService.update(next_p_jobplanInfo);	
								  }else //û���ҵ���һ���µģ�����Ȼ�ӵ���ǰ�·�
								  {
									   p_JobplanInfo.setFinishNum(p_JobplanInfo.getFinishNum()+num);
									   int qualifiedNum1=0;
									   if(p_JobplanInfo.getQualifiedNum()!=null) qualifiedNum1=p_JobplanInfo.getQualifiedNum();
									   p_JobplanInfo.setQualifiedNum(qualifiedNum1+num);
									   if(p_JobplanInfo.getFinishNum().intValue()==p_JobplanInfo.getPlanNum().intValue()) 
										  {
										       p_JobplanInfo.setStatus(70); //�����
										       p_JobplanInfo.setRealEndtime(new Date()); //�����
										  }
									   commonService.update(p_JobplanInfo);	
								  }
								   
							   }
						   }else  //���û�ж�Ӧ��p_id,�Զ����ҵ��µ���ҵ�ƻ�
						   {
							  //��ȡ��ǰʱ�����ڵ��·�
							   Calendar c = Calendar.getInstance();
							   SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM");
							   //ȡ������ҵ�ƻ�
							   String currentMonth=dayFormat.format(c.getTime());  //����String�͵�ʱ��
							   
							   hql=" from TJobplanInfo t where t.planType=1 " +
							   		" and t.TPartTypeInfo.id="+JobplanInfo.getTPartTypeInfo().getId()+
							   		" and DATE_FORMAT(t.realStarttime,'%Y-%m') = '"+currentMonth+"'";
							  List<TJobplanInfo> list= commonService.executeQuery(hql);
							   
							  if(list!=null&&list.size()>0)
							  {
								   TJobplanInfo p0_JobplanInfo=list.get(0); //ȡ���¼ƻ�
								   p0_JobplanInfo.setFinishNum(p0_JobplanInfo.getFinishNum()+num);
								   int qualifiedNum1=0;
								   if(p0_JobplanInfo.getQualifiedNum()!=null) qualifiedNum1=p0_JobplanInfo.getQualifiedNum();
								   p0_JobplanInfo.setQualifiedNum(qualifiedNum1+num);
								   if(p0_JobplanInfo.getFinishNum().intValue()==p0_JobplanInfo.getPlanNum().intValue()) 
									  {
									      p0_JobplanInfo.setStatus(70); //�����
									      p0_JobplanInfo.setRealEndtime(new Date()); //�����
									  }
								   commonService.update(p0_JobplanInfo);	
							  } //���û�в�����һ����
							  else{
							       Calendar pcal=Calendar.getInstance();
							       pcal.add(Calendar.MONTH,-1); //ȡ��һ����
								   String lastMonth=dayFormat.format(pcal.getTime());  //����String�͵�ʱ��
								   
								   hql=" from TJobplanInfo t where t.planType=1 " +
								   		" and t.TPartTypeInfo.id="+JobplanInfo.getTPartTypeInfo().getId()+
								   		" and DATE_FORMAT(t.realStarttime,'%Y-%m') = '"+lastMonth+"'";
								  List<TJobplanInfo> list0= commonService.executeQuery(hql);
								  //�����ҵ��ϸ��µ���ҵ�ƻ�
								  if(list0!=null&&list0.size()>0)
								  {
									  TJobplanInfo last_p_jobplanInfo=list0.get(0);
									  last_p_jobplanInfo.setFinishNum(last_p_jobplanInfo.getFinishNum()+num);
									   int qualifiedNum1=0;
									   if(last_p_jobplanInfo.getQualifiedNum()!=null) qualifiedNum1=last_p_jobplanInfo.getQualifiedNum();
									   last_p_jobplanInfo.setQualifiedNum(qualifiedNum1+num);
									   if(last_p_jobplanInfo.getFinishNum().intValue()==last_p_jobplanInfo.getPlanNum().intValue()) 
										  {
										   last_p_jobplanInfo.setStatus(70); //�����
										   last_p_jobplanInfo.setRealEndtime(new Date()); //�����
										  }
									   commonService.update(last_p_jobplanInfo);	
								  }else //û���ҵ���һ���µģ�ʲô������
								  {
									 
								  }
								   
							   }
						   
						   }
					}
				}
				try{
					//����ERP�м��				
					/*IImportService importService = (IImportService)ServiceFactory.getBean("importService");
					if(importService!=null)
					{
						importService.insertWisTransfer(tproductionEvents.getId());
						if(processOrder.equals("400")){
						importService.insertWisQaCheck(num, partNo, partName, jobdispatchNo, isGood, depName, jgCheckUser, zpCheckUser,sjCheckUser, userName);
						}
					}*/
				}catch(Exception e){
					
				}
				
				return "��ӳɹ�"+tempEventNo;

			}
			//����ERP�м��
		}
		catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "���ʧ��";
		}
	}
	

	@Override
	public String checkNum(String dispatchId,int num) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		try{
			List<Map<String,Object>> jobDispatchInfo = dao.executeNativeQuery("select processNum,finishNum,wisScrapNum from T_JOBDISPATCHLIST_INFO where ID ="+dispatchId+"", parameters);
			int finishNum = Integer.parseInt(jobDispatchInfo.get(0).get("finishNum").toString());
			int planNum = Integer.parseInt(jobDispatchInfo.get(0).get("processNum").toString());
			int wisScrapNum = Integer.parseInt(jobDispatchInfo.get(0).get("wisScrapNum").toString());
			if(finishNum+num+wisScrapNum>planNum){
				return "���������������ޣ�";
			}else{
				return "����Ҫ��";
			}
		}catch(Exception e){
			return "�����ִ���";
		}
	}


	@Override
	public List<Map<String, Object>> queryEquipmentList(String equId,String equId2,String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select e.equ_ID as equId,e.equ_name as name, e.equ_SerialNo as no "+
				" from t_equipmentaddinfo e " +
				" left join T_Nodes n on e.nodeid=n.nodeID" +
				" left join t_equipmenttype_info et on e.equ_type=et.id "+
				" where 1=1 and n.p_NodeID ='"+nodeId+"'";
		if(!StringUtils.isEmpty(equId)){
			sql = sql +" and e.equ_ID in ( "+equId+")";
		}
		if(!StringUtils.isEmpty(equId2)){
			sql = sql +" and e.equ_ID in ( "+equId2+")";
		}
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}
	
    @Override
	public List<Map<String, Object>> queryJobDispatchList(String equipmentId,String dispatchId) {
    	Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select j.id as jobdispatchid,j.no as no,j.processNum as plannum, j.finishNum as finishNum,j.wisScrapNum as wisScrapNum,"+
				" e.equ_SerialNo as serialno, p.name as processname,p.process_order as processorder,"
//				+ "jp.no as jobplanno,"
				+ "pt.name as partname,pt.no as partNo " +
				" from T_JobDispatchList_INFO j " +
				" inner join t_equ_jobdispatch e on j.no=e.jobdispatchNo " +
				" inner join T_Process_Info p on j.processID=p.id "+
//				" inner join T_JOBPLAN_INFO jp on j.jobplanID=jp.id "+
				" inner join T_PROCESSPLAN_INFO pp on p.processPlanID=pp.id "+
				" inner join T_PART_TYPE_INFO pt on pp.parttypeID=pt.id "+
				" inner join t_status_info s on s.id = j.status "+
				" where 1=1 and s.id in (40,50) and e.status !=0 and "+
				" e.equ_SerialNo = (select equ_SerialNo from t_equipmentaddinfo where equ_ID ="+equipmentId+") "
						+ " and pp.defaultSelected =1 ";
		if(!StringUtils.isEmpty(dispatchId)){
			sql = sql +" and j.id in ( "+dispatchId+")";
		}
		sql =sql +" order by j.plan_starttime desc";
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}
    

	@Override
	public List<Map<String, Object>> getUserList(String userId,String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select id,name from T_Member_INFO where status =0 and nodeid ='"+nodeId+"' ";
		if(!StringUtils.isEmpty(userId)){
			sql += " and ID ="+userId+"";
		}
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}
	
	@Override
	/**
	 * ��ȡ���������
	 */
	public List<Map<String, Object>> getPartTypeMap(String partId,String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select id,name from t_part_type_info where nodeid ='"+nodeId+"' ";
		if(!StringUtils.isEmpty(partId)){
			sql += " and ID ="+partId+"";
		}
		sql +=" order by name asc";
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	@Override
	/**
	 * ��ȡ������ż���
	 */
	public List<Map<String, Object>> getDispatchNoMap() {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select id,no from t_jobdispatchlist_info ";
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	@Override
	/**
	 * ��ȡ�豸���кż���
	 */
	public List<Map<String, Object>> getEquTypeMap() {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select equ_ID as id,equ_SerialNo as "
				+ "no from t_equipmentaddinfo ";
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	@Override
	/**
	 * ��ȡ�豸���Ƽ���
	 */
	public List<Map<String, Object>> getEquTypeNameMap() {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select equ_ID as id,equ_name as "
				+ "name from t_equipmentaddinfo ";
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	
	/**
	 * ��ȡ���� 
	 * @param equSerialNo
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> getRCLData(String equSerialNo,int type) {
		 Date date=new Date();
		 String sql="SELECT sum(a.processnum) AS jgNum,a.equ_serialNo as SerialNo,d.name AS partName,c.name AS processName,a.updateDate "
			 		+ " FROM t_equproduction a,t_process_info c, t_part_type_info d"
			 		+ " WHERE a.processId=c.id"
			 		+ " AND a.partTypeId=d.id"
			 		+ " and a.equ_serialNo='"+equSerialNo+"'";
		 //������ղ���
		 if(type==1){
			  sql+= " and DATE_FORMAT(a.updateDate,'%Y-%m-%d')='"+StringUtils.formatDate(date, 2)+"'";  //������Ϊ��������ʱ�ر�
			  sql+= " GROUP BY a.equ_serialNo,a.partTypeId";
		 }else{
			  sql+= " and DATE_FORMAT(a.updateDate,'%Y-%m')='"+StringUtils.formatDate(DateUtils.getCurrentMonthStartTime(), 6)+"'";  //������Ϊ��������ʱ�ر�
			  sql+= " GROUP BY a.equ_serialNo,a.partTypeId";
		 }
			 		
		return dao.executeNativeQuery(sql);
	}
	@Override
	/**
	 * ��ȡmember��Ϣ
	 */
	public List<Map<String, Object>> getMemberInfo(String userId,String nodeId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select id,name,no from t_member_info inner join t_user"
				+ " on t_user.memberID =t_member_info.id "
				+ " where  t_user.UserID ='"+userId+"' and t_member_info.nodeid ='"+nodeId+"' ";
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	@Override
	/**
	 * ��ȡ�豸���кţ�������
	 */
	public List<Map<String, Object>> getEquSerialNoMap(String query) {
		String hql = "select DISTINCT new MAP(equ.equId AS id," 
				+ " equ.equSerialNo AS no)"
				+ " from TEquipmentInfo equ where 1 = 1";
		if(!StringUtils.isEmpty(query)){
			hql += " and equ.equSerialNo like '%"+query+"%'";
		}
		List<Map<String,Object>> dataList= dao.executeQuery(hql);
		return dataList;
	}

	@Override
	/**
	 * ��ȡ�豸���ƣ�������
	 */
	public List<Map<String, Object>> getEquTypeNameMap(String query) {
		String hql = "select DISTINCT new MAP(equ.equId AS id," 
				+ " equ.equName as name)"
				+ " from TEquipmentInfo equ where 1 = 1";
		if(!StringUtils.isEmpty(query)){
			hql += " and equ.equName like '%"+query+"%'";
		}
		List<Map<String,Object>> dataList = dao.executeQuery(hql);
		return dataList;
	}

	@Override
	/**
	 * ��ȡ����������Ϣ
	 */
	public List<Map<String, Object>> getJobDispatch(String dispatchId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
//		String sql = "select no,processNum,finishNum,wisScrapNum,t_status_info.name as statusName,jobplanID,processID"
//				+ " from t_jobdispatchlist_info inner join t_status_info on t_status_info.ID =t_jobdispatchlist_info.status"
//				+ " where t_jobdispatchlist_info.id ="+dispatchId+"";
		String sql = "select no,processNum,finishNum,wisScrapNum,t_status_info.name as statusName,jobplanID,processID,taskNum"
				+ " from t_jobdispatchlist_info inner join t_status_info on t_status_info.ID =t_jobdispatchlist_info.status"
				+ " where t_jobdispatchlist_info.id ="+dispatchId+"";
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	@Override
	/**
	 * ��ȡ���κż���
	 */
	public List<Map<String, Object>> getJobPlanNoList(String partId,String jobplanId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql=null;
		if(!StringUtils.isEmpty(jobplanId)){
			sql = "select id,no,partID,planNum"
					+ " from t_jobplan_info"
					+ " where plan_type =2 ";
			      if(!StringUtils.isEmpty(partId)){
			    	  sql+= " and partID ="+partId+"";
			      }
			      if(!StringUtils.isEmpty(jobplanId)){
			    	  sql+= " and ID ="+jobplanId+"";
			      }
		}else {
			sql = "select DISTINCT joblist.taskNum as no,joblist.processNum as planNum"
					  + " from t_jobdispatchlist_info joblist,"
								 + " t_process_info     process,"
					       + " t_part_type_info   part,"
					       + " t_processplan_info plan"
					 + " where process.processPlanID = plan.ID"
					   + " and plan.parttypeID = part.ID"
						 + " and process.ID = joblist.processID"
					   + " and part.ID = "+partId+"";
//				   		+ " GROUP BY joblist.taskNum";
		}
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	@Override
	/**
	 * ��ȡ����No����
	 */
	public List<Map<String, Object>> getJobdispatchNoList(String jobplanNo) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select id,no,jobplanID"
				+ " from t_jobdispatchlist_info"
				+ " where taskNum ='"+jobplanNo+"'";
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	@Override
	public List<Map<String, Object>> getJobplanList(String jobplanId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select ID,planNum,finishNum,partID,no"
				+ " from t_jobplan_info"
				+ " where ID ="+jobplanId+"";
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	@Override
	/**
	 * ��ȡ�ۺ���Ϣ(���������޸�)
	 */
	public List<Map<String, Object>> getDataList(String equ_SerialNo,
			String partTypeID, String processID,Date dateTime) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select ID,"
				+ " processNum"
				+ " from t_equproduction"
				+ " where equ_SerialNo ='"+equ_SerialNo+"'"
				+ " and partTypeID ="+partTypeID+" and processID ="+processID+""
			    + " and DATE_FORMAT(updateDate,'%Y-%m-%d')='"+StringUtils.formatDate(dateTime, 2)+"' ";
		
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	@Override
	/**
	 * ��ȡ������Ϣ
	 */
	public List<Map<String, Object>> getProcessInfo(String processId) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
//		String sql = "select ID,no,process_order"
//				+ " from t_process_info";
//		if(!StringUtils.isEmpty(processId)){
//			sql += " where ID ="+processId+"";
//		}
		String sql = "select process.ID as ID,process.no as no,process.process_order as process_order,part.ID as partId"
				 + " from t_process_info process,t_part_type_info part,t_processplan_info plan" 
				 + " where process.processPlanID = plan.ID"
				 + " and plan.parttypeID = part.ID";
				if(!StringUtils.isEmpty(processId)){
					sql += " and process.ID ="+processId+"";
				}
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	@Override
	/**
	 * ��ȡ�豸������Ϣ
	 */
	public List<Map<String, Object>> getEquProductionInfo(String equ_SerialNo,
			String partTypeID, String processID, String updateDate) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select ID,processNum"
				+ " from t_equproduction"
				+ " where equ_SerialNo ='"+equ_SerialNo+"' and partTypeID ="+partTypeID+""
				+ " and processID ="+processID+""
				+ " and DATE_FORMAT(updateDate,'%Y-%m-%d')='"+updateDate+"'";
		 
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	@Override
	/**
	 * �޸ı������ݴ���
	 */
	public String saveChangeProcessNum(String changeNum,
			String userId, String jobdispatchId, String equ_SerialNo,
			String partTypeID, String processID, Date updateDate,
			String loginUserNo,String eventNo) {
		try{
			Collection<Parameter> parameters = new HashSet<Parameter>();
			String sql = "";
			List<Map<String,Object>> jobDispatchInfo = dao.executeNativeQuery("select no,processNum,finishNum,jobplanID,batchNo,nodeid,processID,wisScrapNum from T_JOBDISPATCHLIST_INFO where ID ="+jobdispatchId+"", parameters);
			List<Map<String,Object>> memberInfo = dao.executeNativeQuery("select no from t_member_info where ID ="+userId+"", parameters);

			String userNo = memberInfo.get(0).get("no").toString();
			
			String jobdispatchNo = jobDispatchInfo.get(0).get("no").toString();
			String noteId = jobDispatchInfo.get(0).get("nodeid").toString();
			int finishNum = Integer.parseInt(jobDispatchInfo.get(0).get("finishNum").toString());
			int planNum = Integer.parseInt(jobDispatchInfo.get(0).get("processNum").toString());
			int wisScrapNum = Integer.parseInt(jobDispatchInfo.get(0).get("wisScrapNum").toString());
			int num  = Integer.parseInt(changeNum);
			Long jobplanId = null;
			if(jobDispatchInfo.get(0).get("jobplanID") != null ){
				jobplanId =Long.parseLong(jobDispatchInfo.get(0).get("jobplanID").toString());
			}
			
			List<Map<String,Object>> processInfo = dao.executeNativeQuery("select offlineProcess,no,process_order from t_process_info where ID ="+processID+"", parameters);
			
			String processOrder= processInfo.get(0).get("process_order").toString();
			int offlineProcess =Integer.parseInt(processInfo.get(0).get("offlineProcess").toString());
			if(finishNum +num+wisScrapNum >planNum){
				return "���������������ޣ�";
			}else{
				//���¹���״̬
				//if(finishNum ==0){
				//	sql = "update t_jobdispatchlist_info set status =50 where ID ="+jobdispatchId+";";
				//	dao.executeNativeUpdate(sql, parameters);
				//}
				//if(finishNum+num+wisScrapNum ==planNum){
				//	sql = "update t_jobdispatchlist_info set status =70,real_endtime ='"+StringUtils.formatDate(new Date(), 3)+"' where ID ="+dispatchId+";";
				//	dao.executeNativeUpdate(sql, parameters);
				//}
				//���¹����������
			    sql ="update t_jobdispatchlist_info set finishNum="+(finishNum+num)+" where ID ="+jobdispatchId+";";
				dao.executeNativeUpdate(sql, parameters);

				//��t_production_events�����ݱ���
				TProductionEvents tproductionEvents =new TProductionEvents();
				tproductionEvents.setJobdispatchNo(jobdispatchNo);
				tproductionEvents.setEventNo("XG"+eventNo);
				tproductionEvents.setProcessNum(num);
				tproductionEvents.setOperatorNo(loginUserNo);
				tproductionEvents.setOperateDate(new Date());
				tproductionEvents.setOperateReason("���������޸�");
				tproductionEvents.setPartTypeID(Integer.parseInt(partTypeID));
				tproductionEvents.setEquSerialNo(equ_SerialNo);
				tproductionEvents.setResponseNo(userNo);
				tproductionEvents.setResponseProcessNo(processOrder);
				tproductionEvents.setResponseDate(new Date());
				tproductionEvents.setEventType(3);//1�Ǳ���,2�Ǳ���,3�޸�
				
				dao.save(TProductionEvents.class, tproductionEvents);
				//SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				//String eventNo = "SD"+df.format(new Date())+tproductionEvents.getId();
				//tproductionEvents.setEventNo(eventNo);
				//dao.update(TProductionEvents.class, tproductionEvents);
				
				// ��t_equproduction�����ݱ���
				List<Map<String,Object>> equProductionInfo = dao.executeNativeQuery("select ID,processNum from t_equproduction where equ_SerialNo='"+equ_SerialNo+"' "
						+ "and partTypeID ="+partTypeID+" and processID ="+processID+""
								+ " and DATE_FORMAT(updateDate,'%Y-%m-%d')='"+StringUtils.formatDate(updateDate, 2)+"'", parameters);
				if(equProductionInfo.size()>0){
					int count =  Integer.parseInt(equProductionInfo.get(0).get("processNum").toString());
					String id =equProductionInfo.get(0).get("ID").toString();
					dao.executeNativeUpdate("update t_equproduction set processNum ="+(count+num)+" where id ="+id+"", parameters);
				}else{
					dao.executeNativeUpdate("insert into t_equproduction (equ_SerialNo,partTypeID,processID,processNum,updateDate) values ('"+equ_SerialNo+"',"+partTypeID+","+processID+","+num+",'"+StringUtils.formatDate(updateDate, 2)+"')", parameters);
				}
				
				// �� t_userproduction�����ݱ���
				List<Map<String,Object>> userProductionInfo = dao.executeNativeQuery("select ID,processNum from t_userproduction where operator_no='"+userNo+"' "
						+ "and partTypeID ="+partTypeID+" and processID ="+processID+""
								+ " and DATE_FORMAT(updateDate,'%Y-%m-%d')='"+StringUtils.formatDate(updateDate, 2)+"'", parameters);
				if(userProductionInfo.size()>0){
					int count =  Integer.parseInt(userProductionInfo.get(0).get("processNum").toString());
					String id =userProductionInfo.get(0).get("ID").toString();
					dao.executeNativeUpdate("update t_userproduction set processNum ="+(count+num)+" where id ="+id+"", parameters);
				}else{
					dao.executeNativeUpdate("insert into t_userproduction (operator_no,partTypeID,processID,processNum,updateDate) values ('"+userNo+"',"+partTypeID+","+processID+","+num+",'"+StringUtils.formatDate(updateDate, 2)+"')", parameters);
				}
				
				//���ǰ���������+������=�ƻ���,�����
				TJobdispatchlistInfo record=commonService.get(TJobdispatchlistInfo.class,Long.parseLong(jobdispatchId));
				TProcessInfo tProcessInfo=record.getTProcessInfo();
				if(record.getStatus()!=60||record.getStatus()!=70)
				{
					//�����ϵ������Ӧ�Ĺ���
					if(tProcessInfo.getOnlineProcessId()!=null)
					{
						Collection<Parameter> f_parameters = new HashSet<Parameter>();
						f_parameters.add(new Parameter("batchNo", record.getBatchNo(), Operator.EQ)); //������batchNo������jobplanId������ͨ��һЩ
						f_parameters.add(new Parameter("TProcessInfo", commonService.get(TProcessInfo.class,tProcessInfo.getOnlineProcessId()), Operator.EQ)); // �ϵ�����		
						List<TJobdispatchlistInfo> f_temp=commonService.find(TJobdispatchlistInfo.class, (List<Sort>)null, f_parameters);
						//����ǰ�򹤵�����£�����ǰ�򹤵��������=���򹤵��������+���򹤵��ķ��ֱ����� Ҳ��ɸù���
						if(f_temp!=null&&f_temp.size()>0)
						{
							  TJobdispatchlistInfo f_jobdispatch=f_temp.get(0);
							  //ǰ�򹤵��Ѿ����
							  if((f_jobdispatch.getFinishNum().intValue()<=record.getFinishNum().intValue()+record.getWisScrapNum().intValue())&&(f_jobdispatch.getStatus()==60||f_jobdispatch.getStatus()==70)){
								  record.setStatus(70);
								  record.setRealEndtime(new Date());
								}
						}
					}
					commonService.save(record);
				}
				
				//���߹�λ����
				if(offlineProcess==1)  //�����߹�λ
				{
					//�����������
					String hql="select new Map(parttype.no as partTypeNo,parttype.name as partTypeName,parttype.id as partTypeId) from TPartProcessCost partprocesscost,TPartTypeInfo parttype,TProcessInfo process" +
							" where partprocesscost.TProcessInfo.id=process.id and partprocesscost.TPartTypeInfo.id=parttype.id "+
							" and process.id="+processID;
					
					Collection<Parameter> parameters1 = new HashSet<Parameter>();				
					List<Map<String,Object>> parttypetemp=dao.executeQuery(hql,parameters1);
					
					if(parttypetemp!=null&&parttypetemp.size()>0)
						{  
						     Map<String,Object> parttypeinfo=parttypetemp.get(0);
						     TPartBasicInfo addPartBasicInfo=new TPartBasicInfo();
						     addPartBasicInfo.setName(parttypeinfo.get("partTypeName").toString());
						     addPartBasicInfo.setPartTypeId(Long.parseLong(parttypeinfo.get("partTypeId").toString()));
						     //addPartBasicInfo.setOfflineDate(finishTime);
						  
						     //commonService.save(addPartBasicInfo);
						     
						     addPartBasicInfo.setNo(parttypeinfo.get("partTypeNo")+""+addPartBasicInfo.getId());
						     
						   //���½ڵ������ſ� 
						       Date partFinishDate=updateDate;
						       String partFinishiDateStr=StringUtils.formatDate(partFinishDate, 2);//����ʱ��
						       TNodes tnodes=commonService.get(TNodes.class,noteId);
						       //ÿ�����һ�����ݣ��ж��ǲ��뻹�Ǹ���
						        Collection<Parameter> nodeproductionprofile_param = new HashSet<Parameter>();
						        nodeproductionprofile_param.add(new Parameter("TNodes",tnodes, Operator.EQ));
						        nodeproductionprofile_param.add(new Parameter("uprodId",addPartBasicInfo.getPartTypeId().longValue(),Operator.EQ));//B21 ���޲�Ʒ���ͣ���Ʒ����ID�����������IDƥ��
						        List<TNodeProductionProfiles> nodeproductionprofile_rs=commonService.find(TNodeProductionProfiles.class, Arrays.asList (new Sort ("updateTime", Sort.Direction.DESC)), nodeproductionprofile_param);
						        if(nodeproductionprofile_rs!=null&&nodeproductionprofile_rs.size()>0)
						        {
						        	TNodeProductionProfiles tnodeproductionprofiles=nodeproductionprofile_rs.get(0);
						        	
						        	Date updateTime=tnodeproductionprofiles.getUpdateTime();
						        	if(partFinishiDateStr.equals(StringUtils.formatDate(updateTime,2)))
						        		{   //���²�����Ϣ
						        		    tnodeproductionprofiles.setDailyOutput(tnodeproductionprofiles.getDailyOutput()+num);
						        		    tnodeproductionprofiles.setWeeklyOutput(tnodeproductionprofiles.getWeeklyOutput()+num);
						        		    tnodeproductionprofiles.setMonthlyOutput(tnodeproductionprofiles.getMonthlyOutput()+num);
						        		    tnodeproductionprofiles.setAnnualOutput(tnodeproductionprofiles.getAnnualOutput()+num);
						        		    tnodeproductionprofiles.setTotalOutput(tnodeproductionprofiles.getTotalOutput()+num);
						        		    commonService.update(tnodeproductionprofiles);
						        		
						        		}
						        	else {
						        		   //����ʷ��Ϣ�������µ���Ϣ
						        		   int previous_year=updateTime.getYear();
						        		   int previous_month=updateTime.getMonth();
						        		   int year=partFinishDate.getYear();
						        		   int month=partFinishDate.getMonth();
						        		   TNodeProductionProfiles addNodeProductionProfiles=new TNodeProductionProfiles();
						        		   addNodeProductionProfiles.setDailyOutput(new Double(num)); //�ղ���
						        		   addNodeProductionProfiles.setTotalOutput(tnodeproductionprofiles.getTotalOutput()+num); //�ܲ���
						        		   addNodeProductionProfiles.setTNodes(tnodeproductionprofiles.getTNodes()); //�ڵ�
						        		   addNodeProductionProfiles.setUpdateTime(partFinishDate); //����ʱ��
						        		   addNodeProductionProfiles.setUprodId(tnodeproductionprofiles.getUprodId()); //��Ʒ���ID
						        		   
						        		   if(previous_year==year) //ͬ��
						        			   { 
						        			       addNodeProductionProfiles.setAnnualOutput(tnodeproductionprofiles.getAnnualOutput()+num);
						        			       if(previous_month==month) //ͬ�·�
						        			           {
						        			    	      addNodeProductionProfiles.setMonthlyOutput(tnodeproductionprofiles.getMonthlyOutput()+num);
						        			    	      //ͬ��
						        			    	      if(StringUtils.getYearWeek(updateTime).equals(StringUtils.getYearWeek(partFinishDate)))
						        			    	          addNodeProductionProfiles.setWeeklyOutput(tnodeproductionprofiles.getWeeklyOutput()+num);
						        			    	      else addNodeProductionProfiles.setWeeklyOutput(new Double(num));
						        			           }
						        			       else { 
						        			    	   //���·�
						        			    	   addNodeProductionProfiles.setMonthlyOutput(new Double(num));
						        			    	 //���·� ͬ��
						        			    	   if(StringUtils.getYearWeek(updateTime).equals(StringUtils.getYearWeek(partFinishDate)))
						        			    	       {
						        			    	          addNodeProductionProfiles.setWeeklyOutput(tnodeproductionprofiles.getWeeklyOutput()+num);
						        			    	       }
						        			    	   else 
						        			    	       {
						        			    		      addNodeProductionProfiles.setWeeklyOutput(new Double(num));
						        			    	       }
						        			            }
						        			        }
						        		   else 
						        			   {   //����
						        			       addNodeProductionProfiles.setAnnualOutput(new Double(num));
						        			       addNodeProductionProfiles.setMonthlyOutput(new Double(num));
						        			       addNodeProductionProfiles.setWeeklyOutput(new Double(num)); //?����ͬ�ܣ���ô��
						        			   }
						        		   commonService.save(addNodeProductionProfiles);
						        	}
						        }else{
						        	 //û����ʷ��Ϣ�������µ���Ϣ
						        	   TNodeProductionProfiles addNodeProductionProfiles=new TNodeProductionProfiles();				        		
					        		   addNodeProductionProfiles.setTNodes(tnodes); //�ڵ�
					        		   addNodeProductionProfiles.setUpdateTime(partFinishDate); //����ʱ��
					        		   addNodeProductionProfiles.setUprodId(addPartBasicInfo.getPartTypeId().longValue()); //��Ʒ���ID
					        		   addNodeProductionProfiles.setDailyOutput(new Double(num)); //�ղ���
					        		   addNodeProductionProfiles.setTotalOutput(new Double(num)); //�ܲ���
					        		   addNodeProductionProfiles.setAnnualOutput(new Double(num));
			        			       addNodeProductionProfiles.setMonthlyOutput(new Double(num));
			        			       addNodeProductionProfiles.setWeeklyOutput(new Double(num)); 
			        			       commonService.save(addNodeProductionProfiles);
						        }
						}
					

					
					
					//�������μƻ�
					
					if(jobplanId !=null && !StringUtils.isEmpty(jobplanId+""))
					{
						   TJobplanInfo JobplanInfo=commonService.get(TJobplanInfo.class,jobplanId);
						   JobplanInfo.setFinishNum(JobplanInfo.getFinishNum()+num);
						   int qualifiedNum=0;
						   if(JobplanInfo.getQualifiedNum()!=null) qualifiedNum=JobplanInfo.getQualifiedNum();
						   JobplanInfo.setQualifiedNum(qualifiedNum+num);
						   //��ӱ������ݴ���-20140725  start_2  ---------------------------------------------//
						   //
						   if(JobplanInfo.getFinishNum().intValue()+JobplanInfo.getScrapNum().intValue()>=JobplanInfo.getPlanNum().intValue()) 
							 {
							   JobplanInfo.setStatus(70); //�����
							   JobplanInfo.setFinishDate(new Date());
							   JobplanInfo.setRealEndtime(new Date());
							   commonService.update(JobplanInfo);
							   //�����ε��ܷ��ֱ�������β��������֮�͵������μƻ�����ʱ���رո����Σ�ͬʱ���鹤�����ر�
							    String jobdispatchsql="from TJobdispatchlistInfo where jobplanId="+jobplanId;
								List<TJobdispatchlistInfo> tjobdispathclist=commonService.executeQuery(jobdispatchsql);
								if(tjobdispathclist!=null&&tjobdispathclist.size()>0)
								for(TJobdispatchlistInfo tj:tjobdispathclist){
									if(tj.getStatus()!=70||tj.getStatus()!=60) //��û�йرյĹ���ȫ���ر�
									{
										tj.setStatus(70);
										tj.setRealEndtime(new Date());
										commonService.update(TJobdispatchlistInfo.class, tj);
									}
								}
							   
							 }
						   //��ӱ������ݴ���-20140725  end_2  ---------------------------------------------// 	
						   
						   //������ҵ�ƻ�
						   //���´���(1����ֻ��һ���ƻ�)
						   TJobplanInfo p_JobplanInfo=JobplanInfo.getTJobplanInfo();
						   if(p_JobplanInfo!=null){//�����ҵ�ƻ�Ϊnull�����ڴ���
							   //��ȡ��ǰʱ�����ڵ��·�
							   Calendar c = Calendar.getInstance();
							   int currentmonth=c.get(Calendar.MONTH);
							   //ȡ����ҵ�ƻ���ʵ�ʿ�ʼʱ��
							   Calendar pcal=Calendar.getInstance();
							   pcal.setTime(p_JobplanInfo.getRealStarttime()); 
							   int pmonth=pcal.get(Calendar.MONTH);
							   if(pmonth==currentmonth)//��ǰ�·ݵ��ڸ��ƻ����·�
							   {
								   p_JobplanInfo.setFinishNum(p_JobplanInfo.getFinishNum()+num);
								   int qualifiedNum1=0;
								   if(p_JobplanInfo.getQualifiedNum()!=null) qualifiedNum1=p_JobplanInfo.getQualifiedNum();
								   p_JobplanInfo.setQualifiedNum(qualifiedNum1+num);
								   if(p_JobplanInfo.getFinishNum().intValue()==p_JobplanInfo.getPlanNum().intValue()) 
									  {
									       p_JobplanInfo.setStatus(70); //�����
									       p_JobplanInfo.setRealEndtime(new Date()); //�����
									  }
								   commonService.update(p_JobplanInfo);	
							   }else  //�����¸��·ݵ���ҵ�ƻ�
							   {
								   SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM");
								  // c.add(Calendar.MONTH,1); //ȡ��һ����
								   String nextMonth=dayFormat.format(c.getTime());  //����String�͵�ʱ��
								   
								   hql=" from TJobplanInfo t where t.planType=1 " +
								   		" and t.TPartTypeInfo.id="+p_JobplanInfo.getTPartTypeInfo().getId()+
								   		" and DATE_FORMAT(t.realStarttime,'%Y-%m') = '"+nextMonth+"'";
								  List<TJobplanInfo> list= commonService.executeQuery(hql);
								  //�����ҵ��¸��µ���ҵ�ƻ�
								  if(list!=null&&list.size()>0)
								  {
									  TJobplanInfo next_p_jobplanInfo=list.get(0);
									  next_p_jobplanInfo.setFinishNum(next_p_jobplanInfo.getFinishNum()+num);
									   int qualifiedNum1=0;
									   if(next_p_jobplanInfo.getQualifiedNum()!=null) qualifiedNum1=next_p_jobplanInfo.getQualifiedNum();
									   next_p_jobplanInfo.setQualifiedNum(qualifiedNum1+num);
									   if(next_p_jobplanInfo.getFinishNum().intValue()==next_p_jobplanInfo.getPlanNum().intValue()) 
										  {
										   next_p_jobplanInfo.setStatus(70); //�����
										   next_p_jobplanInfo.setRealEndtime(new Date()); //�����
										  }
									   commonService.update(next_p_jobplanInfo);	
								  }else //û���ҵ���һ���µģ�����Ȼ�ӵ���ǰ�·�
								  {
									   p_JobplanInfo.setFinishNum(p_JobplanInfo.getFinishNum()+num);
									   int qualifiedNum1=0;
									   if(p_JobplanInfo.getQualifiedNum()!=null) qualifiedNum1=p_JobplanInfo.getQualifiedNum();
									   p_JobplanInfo.setQualifiedNum(qualifiedNum1+num);
									   if(p_JobplanInfo.getFinishNum().intValue()==p_JobplanInfo.getPlanNum().intValue()) 
										  {
										       p_JobplanInfo.setStatus(70); //�����
										       p_JobplanInfo.setRealEndtime(new Date()); //�����
										  }
									   commonService.update(p_JobplanInfo);	
								  }
								   
							   }
						   }else  //���û�ж�Ӧ��p_id,�Զ����ҵ��µ���ҵ�ƻ�
						   {
							  //��ȡ��ǰʱ�����ڵ��·�
							   Calendar c = Calendar.getInstance();
							   SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM");
							   //ȡ������ҵ�ƻ�
							   String currentMonth=dayFormat.format(c.getTime());  //����String�͵�ʱ��
							   
							   hql=" from TJobplanInfo t where t.planType=1 " +
							   		" and t.TPartTypeInfo.id="+JobplanInfo.getTPartTypeInfo().getId()+
							   		" and DATE_FORMAT(t.realStarttime,'%Y-%m') = '"+currentMonth+"'";
							  List<TJobplanInfo> list= commonService.executeQuery(hql);
							   
							  if(list!=null&&list.size()>0)
							  {
								   TJobplanInfo p0_JobplanInfo=list.get(0); //ȡ���¼ƻ�
								   p0_JobplanInfo.setFinishNum(p0_JobplanInfo.getFinishNum()+num);
								   int qualifiedNum1=0;
								   if(p0_JobplanInfo.getQualifiedNum()!=null) qualifiedNum1=p0_JobplanInfo.getQualifiedNum();
								   p0_JobplanInfo.setQualifiedNum(qualifiedNum1+num);
								   if(p0_JobplanInfo.getFinishNum().intValue()==p0_JobplanInfo.getPlanNum().intValue()) 
									  {
									      p0_JobplanInfo.setStatus(70); //�����
									      p0_JobplanInfo.setRealEndtime(new Date()); //�����
									  }
								   commonService.update(p0_JobplanInfo);	
							  } //���û�в�����һ����
							  else{
							       Calendar pcal=Calendar.getInstance();
							       pcal.add(Calendar.MONTH,-1); //ȡ��һ����
								   String lastMonth=dayFormat.format(pcal.getTime());  //����String�͵�ʱ��
								   
								   hql=" from TJobplanInfo t where t.planType=1 " +
								   		" and t.TPartTypeInfo.id="+JobplanInfo.getTPartTypeInfo().getId()+
								   		" and DATE_FORMAT(t.realStarttime,'%Y-%m') = '"+lastMonth+"'";
								  List<TJobplanInfo> list0= commonService.executeQuery(hql);
								  //�����ҵ��ϸ��µ���ҵ�ƻ�
								  if(list0!=null&&list0.size()>0)
								  {
									  TJobplanInfo last_p_jobplanInfo=list0.get(0);
									  last_p_jobplanInfo.setFinishNum(last_p_jobplanInfo.getFinishNum()+num);
									   int qualifiedNum1=0;
									   if(last_p_jobplanInfo.getQualifiedNum()!=null) qualifiedNum1=last_p_jobplanInfo.getQualifiedNum();
									   last_p_jobplanInfo.setQualifiedNum(qualifiedNum1+num);
									   if(last_p_jobplanInfo.getFinishNum().intValue()==last_p_jobplanInfo.getPlanNum().intValue()) 
										  {
										   last_p_jobplanInfo.setStatus(70); //�����
										   last_p_jobplanInfo.setRealEndtime(new Date()); //�����
										  }
									   commonService.update(last_p_jobplanInfo);	
								  }else //û���ҵ���һ���µģ�ʲô������
								  {
									 
								  }
								   
							   }
						   
						   }
					}
				}
				try{
					//����ERP�м��				
					/*IImportService importService = (IImportService)ServiceFactory.getBean("importService");
					if(importService!=null)
					{
						importService.insertWisTransfer(tproductionEvents.getId());
					}*/
				}catch(Exception e){
					
				}
				
				return "��ӳɹ�";

			}
			//����ERP�м��
		}
		catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "���ʧ��";
		}
	}

	@Override
	public List<Map<String, Object>> getListInfo(String sql) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
		
		return dataList;
	}

	@Override
	/**
	 * �����Ʒ�������
	 */
	public String saveInStockDataInfo(String num,String inventoryId,String materialPositionId,String partId,String instockNo,List<Map<String,Object>> dataList){
		    //String planNo;
		   // Integer stockNUm;
			try{ 
				Collection<Parameter> parameters = new HashSet<Parameter>();
				for(Map<String,Object> dd:dataList){
					if(null !=dd.get("num") && null !=num){
						String no = dd.get("jobplanNo").toString();
						String storageNo = dd.get("storageNo").toString(); //������κ�
						int bomNum =0;
						if(!no.equals("������") && !dd.get("instockNum").toString().equals("������")){//���������������
						    bomNum =Integer.parseInt(dd.get("num").toString());
							int instockNum =Integer.parseInt(dd.get("instockNum").toString());
							String sql="update t_jobplan_info set instockNum ="+(instockNum+(bomNum*Integer.parseInt(num)))+" where no ='"+no+"'";
							dao.executeNativeUpdate(sql,parameters);
						}
						String partTypeId ="";
						String hql ="";
						if(null !=dd.get("type") && dd.get("type").toString().equals("����")){
							partTypeId = partId;
							hql="select new Map(materialType.id as id) from TPartTypeInfo parttype,TMaterailTypeInfo materialType" +
									" where materialType.no=parttype.no"+
									" and parttype.id="+partTypeId;
						}else{
							partTypeId = dd.get("materailId").toString();
							hql="select new Map(materialType.id as id) from TMaterailTypeInfo materialType" +
									" where materialType.id="+partTypeId+"";
						}
						//if(null !=dd.get("type") && dd.get("type").toString().equals("����")){
							//����ҵ����
						Collection<Parameter> parameters1 = new HashSet<Parameter>();				
						List<Map<String,Object>> parttypetemp=dao.executeQuery(hql,parameters1);
						if(parttypetemp.size()>0){//��Ʒ�����������д���
							String materialId = parttypetemp.get(0).get("id").toString();
							TMaterialStorage tMaterialStorage =new TMaterialStorage();
							List<Map<String,Object>> temp=dao.executeNativeQuery("select id from t_material_storage"
									+ " where batchNo ='"+storageNo+"' and storage_id ="+inventoryId+" and material_id ="+materialId+"",parameters1);
							//��ȡ������Ϣ
							TMaterailTypeInfo tMaterailTypeInfo = commonService.get(TMaterailTypeInfo.class,Long.parseLong(materialId));
							//��ȡ�ⷿ��Ϣ
							TStorageInfo tStorageInfo = commonService.get(TStorageInfo.class,Integer.parseInt(inventoryId));
						    //��ȡ�汾��Ϣ
							TMaterialVersion tMaterialVersion = new TMaterialVersion();
							if(null !=tMaterailTypeInfo.getVersionId()){
								tMaterialVersion =commonService.get(TMaterialVersion.class,tMaterailTypeInfo.getVersionId());
							}
							//�����Ϣ
							if(temp.size()>0){
								tMaterialStorage = commonService.get(TMaterialStorage.class,Integer.parseInt(temp.get(0).get("id").toString()));
								Double i = tMaterialStorage.getAvailableNum();
								tMaterialStorage.setAvailableNum(i+Double.parseDouble(num));
								commonService.update(tMaterialStorage);
							}else{
								tMaterialStorage.setTMaterailTypeInfo(tMaterailTypeInfo);//����
								tMaterialStorage.setTStorageInfo(tStorageInfo);//�ⷿ
								tMaterialStorage.setPositonId(Integer.parseInt(materialPositionId));//��λID
								tMaterialStorage.setBatchNo(storageNo);//�������
								tMaterialStorage.setAvailableNum(Double.parseDouble(num));//������
								tMaterialStorage.setUnitName(tMaterailTypeInfo.getUnit());//��λ
								tMaterialStorage.setVersionNo(tMaterialVersion.getVersionNo());//�汾
								tMaterialStorage.setRetainNum(0.00);
								tMaterialStorage.setProcessDate(new Date());//��������
								commonService.save(tMaterialStorage);
								
							}
							//���������
							TTransaction tTransaction = new TTransaction();
							tTransaction.setTransNo(instockNo);//��ⵥ��
							tTransaction.setTMaterailTypeInfo(tMaterailTypeInfo);//����
							tTransaction.setTStorageInfo(tStorageInfo);//�ⷿ
							tTransaction.setPositionId(Integer.parseInt(materialPositionId));//��λ
							tTransaction.setVersionNo(tMaterialVersion.getVersionNo());
							tTransaction.setProcessNum(Double.parseDouble(num));//����
							tTransaction.setUnitName(tMaterailTypeInfo.getUnit());
							tTransaction.setBatchNo(storageNo);
							//�����������ϵ TODO
							//�����ϵ
							List<TTransactionRelation> list = new ArrayList<TTransactionRelation>();
							String strHql ="from TTransactionRelation where tansType ='��Ʒ���'";
							list = commonService.executeQuery(strHql);
							for(TTransactionRelation tt:list){
								tTransaction.setTTransactionRelation(tt);//�����ϵ
							}
							
							//����
							String strSql ="select t_jobdispatchlist_info.no as no from t_jobdispatchlist_info "
									+ " inner join t_process_info on t_process_info.id =t_jobdispatchlist_info.processID"
									+ " where t_jobdispatchlist_info.taskNum ='"+no+"' and t_process_info.offlineProcess =1";
							List<Map<String,Object>> list2 = commonService.executeNativeQuery(strSql);
							for(Map<String,Object> ss:list2){
								tTransaction.setJobdispatchNo(ss.get("no").toString());//������
							}
							
							tTransaction.setTransDate(new Date());//������ʱ��
							commonService.save(tTransaction);
							
							//�м�⣨ֻ�������ϣ�
							if(null !=dd.get("type") && dd.get("type").toString().equals("����")){
								try{
									//����ERP�м��				
									/*IImportService importService = (IImportService)ServiceFactory.getBean("importService");
									if(importService!=null)
									{
										importService.inStockinsertWisTransfer(bomNum*Integer.parseInt(num), no, instockNo);
									}*/
								}catch(Exception e){
									
								}
							}
						}
					}
				}
				
				return "��ӳɹ�";
			}
	        catch(Exception e){
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "���ʧ��";
			}
			
	}
	
	
	
	
	
	
	
	
}

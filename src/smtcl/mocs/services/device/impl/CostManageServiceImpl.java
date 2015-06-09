package smtcl.mocs.services.device.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.Sort;

import smtcl.mocs.common.device.LogHelper;
import smtcl.mocs.dao.device.ICommonDao;
import smtcl.mocs.pojos.device.TEquproduction;
import smtcl.mocs.pojos.device.TNodeProductionProfiles;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUserEquWorkEvents;
import smtcl.mocs.pojos.device.TUserproduction;
import smtcl.mocs.pojos.job.TJobInfo;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TPartBasicInfo;
import smtcl.mocs.pojos.job.TPartEventInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.device.ICostManageService;
import smtcl.mocs.utils.device.StringUtils;

@SuppressWarnings("unchecked")
public class CostManageServiceImpl extends GenericServiceSpringImpl<TNodes, String> implements ICostManageService {
	
	private ICommonService commonService;
	protected ICommonDao commonDao;
	
	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	/**
	 * ��ȡ�ɱ�
	 * @param pageNo
	 * @param pageSize
	 * @param no
	 * @return
	 */
	public List<Map<String, Object>> queryProcessCostList(int pageNo, int pageSize, String  partTypeNo) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select results.*,"+
					 	"cost.equ_serialNo,"+
					 	"if(cost.equ_price/period_depreciation is null,0,cost.equ_price/(period_depreciation*365*24*3600)) as depreciationCost,"+
					 	"if(cost.process_accessories_cost is null,0,cost.process_accessories_cost) as proacceCost,"+
					 	"if(cost.prepare_accessories_cost is null,0, cost.prepare_accessories_cost) as preacceCost,"+
					 	"if(cost.prepare_cost is null,0,cost.prepare_cost) as prepareCost,"+
					 	"if(cost.cutting_cost is null,0,cost.cutting_cost) as cuttingCost,"+
					 	"if(cost.dryRunning_cost is null,0,cost.dryRunning_cost) as dryRunningCost,"+
					    "member.salary/(22*8*3600) as salary"+
				       " from " +
				        "(select equ_serialNo,partID,partNo,parttypeNo,eventID,processID,starttime,finishtime,cuttingTime,workTime,installtime,processname,materialPrice,reqNum,reqType " +
				          " from " +
				           "(select a.* " +
				             " from " +
				              "( select distinct workevent.equ_serialNo as equ_serialNo," +
				                  "part.`name` as partName," +
				                  "part.id as partID,"+
				           	      "part.`no` as partNo," +
				                  "partevent.eventID as eventID," +
				                  "parttype.`no` as parttypeNo," +
				                  "workevent.starttime," +
				                  "workevent.finishtime," +
				                  "workevent.cuttingTime," +
				                  "workevent.workTime," +
				                  "process.install_time as installtime," +
				                  "process.ID as processID," +
				                  "process.`name` as processname," +
				                  "material.price as materialPrice," +
				                  "processmaterial.requirementNum as reqNum," +
				                  "processmaterial.requirementType as reqType" +
				                  " from t_part_basic_info part INNER JOIN t_part_type_info parttype on part.parttype_id=parttype.ID" +
				                  " inner join r_PART_EVENT_INFO partevent on  part.id=partevent.partID" +
				                  " inner join t_userequworkevents workevent on workevent.id=partevent.eventID" +
				                  " inner join t_jobdispatchlist_info jobdispatch on workevent.cuttingTask=jobdispatch.`no`" +
				                  " inner join t_process_info process on jobdispatch.processID=process.ID" +
				                  " left outer join r_ProcessMaterial_info processmaterial on processmaterial.processID=process.ID" +
				                  " left outer join t_materail_type_info material on material.ID=processmaterial.materialTypeID" +				              			                  
				                  " order by equ_serialNo desc,starttime ASC" +
				                  ") a " +
				                  ") c) results " +
				                  " left outer JOIN t_equipmentaddinfo equipment on results.equ_serialNo=equipment.equ_serialNo" +
				                  " left outer join t_equipment_cost_info cost on cost.equ_serialNo=equipment.equ_SerialNo" +
				                  "	inner join r_Equipment_Member_info equMem on equMem.equ_ID=equipment.equ_id" +
				                  " inner join t_member_info member on member.ID=equMem.member_ID";	        
			
			if(!StringUtils.isEmpty(partTypeNo)) sql+=" where parttypeNo='"+partTypeNo	+"'";	
			
			List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
			List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> rs = new ArrayList<Map<String,Object>>();
			Set<String> set = new HashSet<String>();
			
			int seq=1;
			for(int i=0;i<dataList.size();i++)
			{
				Map<String,Object> record=dataList.get(i);				
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("partNo",record.get("partNo"));  //��Ʒ����
				map.put("equSerialNo", record.get("equ_serialNo"));
				map.put("processID", record.get("processID")); //����	
				map.put("eventID",record.get("eventID"));//�ӹ�ʱ��ID
				
				set.add(record.get("partNo")+"");
				//int rank=(Integer)record.get("rank");
				
				int workTime=0;
				if(!StringUtils.isEmpty(record.get("workTime")+"")) workTime=(Integer)record.get("workTime");//�ӹ�ʱ��     t��
				int cuttingTime=0;
				if(!StringUtils.isEmpty(record.get("cuttingTime")+"")) cuttingTime=(Integer)record.get("cuttingTime");//����ʱ�� t��
				int dryRunning=workTime-cuttingTime; //������ʱ��   t��
				double salarypersec=0;
				if(!StringUtils.isEmpty(record.get("salary")+""))   
					salarypersec=Double.valueOf(record.get("salary").toString()); //  k��
				
				double proacceCost=0;
				if(!StringUtils.isEmpty(record.get("proacceCost")+""))		
					proacceCost=Double.valueOf(record.get("proacceCost").toString());  // �ӹ�ʱ�丨������  k�Ӹ�
				
				double preacceCost=0;
				if(!StringUtils.isEmpty(record.get("preacceCost")+""))	
					preacceCost=Double.valueOf(record.get("preacceCost").toString());   // ׼��ʱ�丨������  k׼��
				
				double depreciationCost=0;
				if(!StringUtils.isEmpty(record.get("depreciationCost")+""))
					depreciationCost=Double.valueOf(record.get("depreciationCost").toString()); //�����۾���   k��
				
				double prepareCost=0;
				if(!StringUtils.isEmpty(record.get("prepareCost")+""))
					prepareCost=Double.valueOf(record.get("prepareCost").toString()); //K׼
				
				double cuttingCost=0;
				if(!StringUtils.isEmpty(record.get("cuttingCost")+""))
					cuttingCost=Double.valueOf(record.get("cuttingCost").toString()); //K��
				
				double dryRunningCost=0;
				if(!StringUtils.isEmpty(record.get("dryRunningCost")+""))
					dryRunningCost=Double.valueOf(record.get("dryRunningCost").toString());//K��
				
				//����
				double materialPrice=0;
				if(!StringUtils.isEmpty(record.get("materialPrice")+""))
				     materialPrice=Double.valueOf(record.get("materialPrice").toString()); //�����ϵ���
				
				double reqNum =0;
				if(!StringUtils.isEmpty(record.get("reqNum")+""))		
					reqNum=Double.valueOf(record.get("reqNum").toString());//��������
				
				String reqType=(String)record.get("reqType"); //�������
				
				long preparttime=0; //׼��ʱ��   t׼

				if(seq!=1&&i>0)
				{
				
				Map<String,Object> record_last=dataList.get(i-1);
			
				if(record.get("processID").toString().equals(record_last.get("processID").toString()))  //׼��ʱ�䣺�ӹ��¼����е�ѡ��һ���Ŀ�ʼʱ�������һ���Ľ���ʱ������������ֵ�빤����е�װ��ʱ�����Ƚϣ�����������
				{	
					seq++;
					long between=StringUtils.dateTimeBetween(record_last.get("finishtime").toString(),record.get("starttime").toString());
				    int installtime=Integer.parseInt((record.get("installtime") == null || record.get("installtime").toString().equals("")) ? "0" : record.get("installtime").toString()); //װ��ʱ��
				    
				    if(between<installtime&&between>0) preparttime=between;
				    else preparttime=installtime;
				}
				else {	seq=1;}}
				else{
					seq++;
					String installtime=record.get("installtime")+"";
					if(!StringUtils.isEmpty(installtime))
					     preparttime=(Integer)record.get("installtime"); //װ��ʱ��
					else preparttime=0;
					
				}
				
				//ÿ��������˹�
				double peopleCost=(preparttime+workTime)*salarypersec;
				map.put("peopleCost",peopleCost);
				//ÿ�����������
				double mainMaterialCost=0;
				if(!"ǰ������".equals(reqType))	mainMaterialCost=materialPrice*reqNum;
				map.put("mainMaterialCost",mainMaterialCost);
				//����
				double accMaterialCost=preparttime*preacceCost+workTime*proacceCost;
				map.put("accMaterialCost",accMaterialCost);
				//�۾�
				double oldCost=(preparttime+workTime)*depreciationCost;
				map.put("oldCost",oldCost);
				//��Դ(��̨)
				double energyCost=preparttime*prepareCost+cuttingTime*cuttingCost+dryRunning*dryRunningCost;
				map.put("energyCost",energyCost);
				map.put("energyCost_prepare", StringUtils.doubleConvertFormat(preparttime*prepareCost));
				map.put("energyCost_cutting", StringUtils.doubleConvertFormat(cuttingTime*cuttingCost));
				map.put("energyCost_dryRunning", StringUtils.doubleConvertFormat(dryRunning*dryRunningCost));
				tempList.add(map);
			}
		    
			//set ת list ����
			List<String> partNolist=new ArrayList<String>(set);		
			Collections.sort(partNolist, new Comparator<String>()  
				      {public int compare(String o1, String o2) { return o1.compareTo(o2);}});
			
			for(String partrec:partNolist){
				Map<String,Object> partcost=new HashMap<String,Object>();
				String partNo =partrec;
				partcost.put("partNo", partNo);
				double total_peopleCost=0;
				double total_mainMaterialCost=0;
				double total_accMaterialCost=0;
				double total_oldCost=0;
				double total_energyCost=0;
				List<Map<String,Object>> energycostDetailList= new ArrayList<Map<String,Object>>();
				for(Map<String,Object> rec:tempList)
				  {
					  if(null!=rec.get("partNo")&&rec.get("partNo").equals(partNo))
					  {
						   Map<String,Object> energycost=new HashMap<String,Object>();
						   energycost.put("equSerialNo", rec.get("equSerialNo"));						 
						   energycost.put("energyCost_cutting", rec.get("energyCost_cutting"));
						   energycost.put("energyCost_dryRunning", rec.get("energyCost_dryRunning"));
						   energycost.put("energyCost_prepare", rec.get("energyCost_prepare"));
						   energycostDetailList.add(energycost);
						   total_peopleCost+=Double.valueOf(rec.get("peopleCost").toString());
						   total_mainMaterialCost+=Double.valueOf(rec.get("mainMaterialCost").toString());
						   total_accMaterialCost+=Double.valueOf(rec.get("accMaterialCost").toString());
						   total_oldCost+=Double.valueOf(rec.get("oldCost").toString());
						   total_energyCost+=Double.valueOf(rec.get("energyCost").toString());
					  }
//					  System.err.println("partNo:"+rec.get("partNo")+"--processID:"+rec.get("processID")+"--eventID:"+rec.get("eventID")+"--peopleCost:"+rec.get("peopleCost")+"--mainMaterialCost:"
//				                       +rec.get("mainMaterialCost")+"--accMaterialCost:"+rec.get("accMaterialCost")+"--oldCost:"+rec.get("oldCost")+"--energyCost:"+rec.get("energyCost"));
//	
			       }
				partcost.put("peopleCost", StringUtils.doubleConvertFormat(total_peopleCost));
				partcost.put("mainMaterialCost", StringUtils.doubleConvertFormat(total_mainMaterialCost));
				partcost.put("accMaterialCost", StringUtils.doubleConvertFormat(total_accMaterialCost));
				partcost.put("oldCost", StringUtils.doubleConvertFormat(total_oldCost));
				partcost.put("energyCost",StringUtils.doubleConvertFormat(total_energyCost));
				partcost.put("energycostDetailList",energycostDetailList);
				rs.add(partcost);
	     }
					
			Collections.reverse(rs);//rs ����	
			/*for(Map<String,Object> rec:rs)
				 System.err.println("----------partNo:"+rec.get("partNo")+"--peopleCost:"+rec.get("peopleCost")+"--mainMaterialCost:"
	                       +rec.get("mainMaterialCost")+"--accMaterialCost:"+rec.get("accMaterialCost")+"--oldCost:"+rec.get("oldCost")+"--energyCost:"+rec.get("energyCost"));
	                       */
			return rs;
	}   	   	
	
	/**
	 * ͨ��ʱ�� ��̨�ɱ�
	 * @param pageNo
	 * @param pageSize
	 * @param no
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> queryEquipmentCostListByVar(int pageNo, int pageSize, String partTypeNo,String startTime,String endTime) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select results.*,"+
					 	"cost.equ_serialNo,"+					 	
					 	"if(cost.process_accessories_cost is null,0,cost.process_accessories_cost) as proacceCost,"+
					 	"if(cost.prepare_accessories_cost is null,0, cost.prepare_accessories_cost) as preacceCost,"+
					 	"if(cost.prepare_cost is null,0,cost.prepare_cost) as prepareCost,"+
					 	"if(cost.cutting_cost is null,0,cost.cutting_cost) as cuttingCost,"+
					 	"if(cost.dryRunning_cost is null,0,cost.dryRunning_cost) as dryRunningCost"+
					   " from " +
				        "(select equ_serialNo,partNo,parttypeNo,eventID,processID,starttime,finishtime,cuttingTime,workTime,installtime,processname " +
				          " from " +
				           "(select a.* " +
				             " from " +
				              "( select distinct workevent.equ_serialNo as equ_serialNo," +
				                  "part.`name` as partName," +
				           	      "part.`no` as partNo," +
				                  "partevent.eventID as eventID," +
				                  "parttype.`no` as parttypeNo," +
				                  "workevent.starttime," +
				                  "workevent.finishtime," +
				                  "workevent.cuttingTime," +
				                  "workevent.workTime," +
				                  "process.install_time as installtime," +
				                  "process.ID as processID," +
				                  "process.`name` as processname " +				               
				                  " from t_part_basic_info part INNER JOIN t_part_type_info parttype on part.parttype_id=parttype.ID" +
				                  " inner join r_PART_EVENT_INFO partevent on  part.id=partevent.partID" +
				                  " inner join t_userequworkevents workevent on workevent.id=partevent.eventID" +
				                  " inner join t_jobdispatchlist_info jobdispatch on workevent.cuttingTask=jobdispatch.`no`" +
				                  " inner join t_process_info process on jobdispatch.processID=process.ID where 1=1 " ;
					                if(!StringUtils.isEmpty(startTime)) sql+=" and workevent.startTime>='"+startTime+"'";	
					      			if(!StringUtils.isEmpty(endTime)) sql+=" and workevent.finishtime<='"+endTime+"'";
//				      			   sql+= " order by equ_serialNo desc,starttime ASC" +
        sql += " order by  starttime ASC" +
                ") a " +
                ") c) results " +
                " left outer JOIN t_equipmentaddinfo equipment on results.equ_serialNo=equipment.equ_serialNo" +
                " left outer join t_equipment_cost_info cost on cost.equ_serialNo=equipment.equ_SerialNo";

        if (!StringUtils.isEmpty(partTypeNo)) sql += " and  parttypeNo='" + partTypeNo + "'";

        List<Map<String, Object>> dataList = dao.executeNativeQuery(sql, parameters);
        List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
        Set<String> set = new HashSet<String>();

        int seq = 1;
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> record = dataList.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("partNo", record.get("partNo"));  //��Ʒ����
            map.put("equSerialNo", record.get("equ_serialNo"));
            map.put("processID", record.get("processID")); //����	
            map.put("eventID", record.get("eventID"));//�ӹ�ʱ��ID

            set.add(record.get("equ_serialNo").toString());

            int workTime = 0;
            if (!StringUtils.isEmpty(record.get("workTime") + ""))
                workTime = (Integer) record.get("workTime");//�ӹ�ʱ��     t��
            int cuttingTime = 0;
            if (!StringUtils.isEmpty(record.get("cuttingTime") + ""))
                cuttingTime = (Integer) record.get("cuttingTime");//����ʱ�� t��
            int dryRunning = workTime - cuttingTime; //������ʱ��   t��	

            double prepareCost = 0;
            if (!StringUtils.isEmpty(record.get("prepareCost") + ""))
                prepareCost = Double.valueOf(record.get("prepareCost").toString()); //K׼
            double cuttingCost = 0;
            if (!StringUtils.isEmpty(record.get("cuttingCost") + ""))
                cuttingCost = Double.valueOf(record.get("cuttingCost").toString()); //K��
            double dryRunningCost = Double.valueOf(record.get("dryRunningCost").toString());//K��	

            long preparttime = 0; //׼��ʱ��   t׼

            if (seq != 1 && i > 0) {

                Map<String, Object> record_last = dataList.get(i - 1);

                if (record.get("processID").toString().equals(record_last.get("processID").toString()))  //׼��ʱ�䣺�ӹ��¼����е�ѡ��һ���Ŀ�ʼʱ�������һ���Ľ���ʱ������������ֵ�빤����е�װ��ʱ�����Ƚϣ�����������
                {
                    seq++;
                    long between = StringUtils.dateTimeBetween(record_last.get("finishtime").toString(), record.get("starttime").toString());
                    int installtime = (Integer) record.get("installtime"); //װ��ʱ��
                    if (between < installtime && between > 0) preparttime = between;
                    else preparttime = installtime;
                } else {
                    seq = 1;
                }
            } else {
                seq++;
                String installtime = record.get("installtime") + "";
                if (!StringUtils.isEmpty(installtime))
                    preparttime = (Integer) record.get("installtime"); //װ��ʱ��
                else preparttime = 0;

            }

            //��Դ(��̨)
            double energyCost = preparttime * prepareCost + cuttingTime * cuttingCost + dryRunning * dryRunningCost;
            map.put("energyCost", energyCost);
            map.put("energyCost_prepare", StringUtils.doubleConvertFormat(preparttime * prepareCost));
            map.put("energyCost_cutting", StringUtils.doubleConvertFormat(cuttingTime * cuttingCost));
            map.put("energyCost_dryRunning", StringUtils.doubleConvertFormat(dryRunning * dryRunningCost));
            tempList.add(map);
        }

        for (Iterator<String> iterator = set.iterator(); iterator.hasNext(); ) {
            Map<String, Object> partcost = new HashMap<String, Object>();
            String equSerialNo = (String) iterator.next();
            partcost.put("equSerialNo", equSerialNo);
            double total_energyCost_prepare = 0;
            double total_energyCost_cutting = 0;
            double total_energyCost_dryRunning = 0;

            for (Map<String, Object> rec : tempList) {
                if (rec.get("equSerialNo").equals(equSerialNo)) {
                    total_energyCost_prepare += Double.valueOf(rec.get("energyCost_prepare").toString());
                    total_energyCost_cutting += Double.valueOf(rec.get("energyCost_cutting").toString());
                    total_energyCost_dryRunning += Double.valueOf(rec.get("energyCost_dryRunning").toString());
                }
            }
            partcost.put("energyPprepareCost", StringUtils.doubleConvertFormat(total_energyCost_prepare));
            partcost.put("energyCuttingCost", StringUtils.doubleConvertFormat(total_energyCost_cutting));
            partcost.put("energyDryRunningCost", StringUtils.doubleConvertFormat(total_energyCost_dryRunning));
            rs.add(partcost);
        }

        for (Map<String, Object> rec : rs)
            System.err.println("----------equSerialNo:" + rec.get("equSerialNo") + "--energyPprepareCost:" + rec.get("energyPprepareCost") + "--energyCuttingCost:"
                    + rec.get("energyCuttingCost") + "--energyDryRunningCost:" + rec.get("energyDryRunningCost"));

        return rs;
    }

    /**
     * ͨ����Ʒ��Ų�ѯ	 ��̨�ɱ�
     *
     * @param partTypeNo
     * @param equNo
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> queryEquipmentCostListByEquno(String partTypeNo, String equNo, String startTime, String endTime) {
        Collection<Parameter> parameters = new HashSet<Parameter>();
        String sql = "select results.*," +
                "cost.equ_serialNo," +
                "if(cost.process_accessories_cost is null,0,cost.process_accessories_cost) as proacceCost," +
                "if(cost.prepare_accessories_cost is null,0, cost.prepare_accessories_cost) as preacceCost," +
                "if(cost.prepare_cost is null,0,cost.prepare_cost) as prepareCost," +
                "if(cost.cutting_cost is null,0,cost.cutting_cost) as cuttingCost," +
                "if(cost.dryRunning_cost is null,0,cost.dryRunning_cost) as dryRunningCost" +
                " from " +
                "(select equ_serialNo,partNo,parttypeNo,eventID,processID,starttime,finishtime,cuttingTime,workTime,installtime,processname " +
                " from " +
                "(select a.* " +
                " from " +
                "( select distinct workevent.equ_serialNo as equ_serialNo," +
                "part.`name` as partName," +
                "part.`no` as partNo," +
                "partevent.eventID as eventID," +
                "parttype.`no` as parttypeNo," +
                "workevent.starttime," +
                "workevent.finishtime," +
                "workevent.cuttingTime," +
                "workevent.workTime," +
                "process.install_time as installtime," +
                "process.ID as processID," +
                "process.`name` as processname " +
                " from t_part_basic_info part INNER JOIN t_part_type_info parttype on part.parttype_id=parttype.ID" +
                " inner join r_PART_EVENT_INFO partevent on  part.id=partevent.partID" +
                " inner join t_userequworkevents workevent on workevent.id=partevent.eventID" +
                " inner join t_jobdispatchlist_info jobdispatch on workevent.cuttingTask=jobdispatch.`no`" +
                " inner join t_process_info process on jobdispatch.processID=process.ID where 1=1 ";
        if (!StringUtils.isEmpty(startTime)) sql += " and workevent.startTime>='" + startTime + "'";
        if (!StringUtils.isEmpty(endTime)) sql += " and workevent.finishtime<='" + endTime + "'";
//					      		  sql+= " order by equ_serialNo desc,starttime ASC" +
					      		  sql+= " order by starttime ASC" +
				                  ") a " +
				                  ") c) results " +
				                  " left outer JOIN t_equipmentaddinfo equipment on results.equ_serialNo=equipment.equ_serialNo" +
				                  " left outer join t_equipment_cost_info cost on cost.equ_serialNo=equipment.equ_SerialNo" ;				                        
			if(!StringUtils.isEmpty(partTypeNo)) sql+=" and  parttypeNo='"+partTypeNo+"'";	
			if(!StringUtils.isEmpty(equNo)) sql+=" and results.equ_serialNo='"+equNo+"'";
			
			List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
			List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> rs = new ArrayList<Map<String,Object>>();
			Set<String> set = new HashSet<String>();
			
			int seq=1;
			for(int i=0;i<dataList.size();i++)
			{
				Map<String,Object> record=dataList.get(i);				
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("partNo",record.get("partNo"));  //��Ʒ����
				map.put("equSerialNo", record.get("equ_serialNo"));				
				map.put("starttime", record.get("starttime"));	
				
				set.add(record.get("starttime").toString().substring(0,10));
				
				int workTime=0;
				if(!StringUtils.isEmpty(record.get("workTime")+"")) workTime=(Integer)record.get("workTime");//�ӹ�ʱ��     t��
				int cuttingTime=0;
				if(!StringUtils.isEmpty(record.get("cuttingTime")+"")) cuttingTime=(Integer)record.get("cuttingTime");//����ʱ�� t��
				int dryRunning=workTime-cuttingTime; //������ʱ��   t��	
	
				double prepareCost=0;
				if(!StringUtils.isEmpty(record.get("prepareCost")+"")) prepareCost=Double.valueOf(record.get("prepareCost").toString()); //K׼
				double cuttingCost=0;
				if(!StringUtils.isEmpty(record.get("cuttingCost")+"")) cuttingCost=Double.valueOf(record.get("cuttingCost").toString()); //K��
				double dryRunningCost=Double.valueOf(record.get("dryRunningCost").toString());//K��	
				
				long preparttime=0; //׼��ʱ��   t׼

				if(seq!=1&&i>0)
				{
				
				Map<String,Object> record_last=dataList.get(i-1);
			
				if(record.get("processID").toString().equals(record_last.get("processID").toString()))  //׼��ʱ�䣺�ӹ��¼����е�ѡ��һ���Ŀ�ʼʱ�������һ���Ľ���ʱ������������ֵ�빤����е�װ��ʱ�����Ƚϣ�����������
				{	
					seq++;
					long between=StringUtils.dateTimeBetween(record_last.get("finishtime").toString(),record.get("starttime").toString());
				    int installtime=(Integer)record.get("installtime"); //װ��ʱ��
				    if(between<installtime&&between>0) preparttime=between;
				    else preparttime=installtime;
				}
				else {	seq=1;}}
				else{
					seq++;
					String installtime=record.get("installtime")+"";
					if(!StringUtils.isEmpty(installtime))
					     preparttime=(Integer)record.get("installtime"); //װ��ʱ��
					else preparttime=0;
					
				}
				
				//��Դ(��̨)
				double energyCost=preparttime*prepareCost+cuttingTime*cuttingCost+dryRunning*dryRunningCost;
				map.put("energyCost",energyCost);
				map.put("energyCost_prepare", StringUtils.doubleConvertFormat(preparttime*prepareCost));
				map.put("energyCost_cutting", StringUtils.doubleConvertFormat(cuttingTime*cuttingCost));
				map.put("energyCost_dryRunning", StringUtils.doubleConvertFormat(dryRunning*dryRunningCost));
				
				tempList.add(map);
			}
			
			//set ת list ����
			List<String> datelist=new ArrayList<String>(set);		
			Collections.sort(datelist, new Comparator<String>()  
				      {public int compare(String o1, String o2) { return o1.compareTo(o2);}});

			for(String time:datelist){
				Map<String,Object> partcost=new HashMap<String,Object>();
				partcost.put("date", time);
				double total_energyCost_prepare=0;
				double total_energyCost_cutting=0;
				double total_energyCost_dryRunning=0;
				
				for(Map<String,Object> rec:tempList)
				  {
					if(rec.get("starttime").toString().substring(0,10).equals(time))
					  {
						   total_energyCost_prepare+=Double.valueOf(rec.get("energyCost_prepare").toString());
						   total_energyCost_cutting+=Double.valueOf(rec.get("energyCost_cutting").toString());
						   total_energyCost_dryRunning+=Double.valueOf(rec.get("energyCost_dryRunning").toString());
					  }	
			       }
				partcost.put("energyPprepareCost", StringUtils.doubleConvertFormat(total_energyCost_prepare));
				partcost.put("energyCuttingCost", StringUtils.doubleConvertFormat(total_energyCost_cutting));
				partcost.put("energyDryRunningCost", StringUtils.doubleConvertFormat(total_energyCost_dryRunning));
				rs.add(partcost);
	}
			
			for(Map<String,Object> rec:rs)
				 System.err.println("----------date:"+rec.get("date")+"--energyPprepareCost:"+rec.get("energyPprepareCost")+"--energyCuttingCost:"
	                       +rec.get("energyCuttingCost")+"--energyDryRunningCost:"+rec.get("energyDryRunningCost"));
			
			return rs;	
		
		
	}
	
	/**
	 * ͨ�����ͻ������Ƶõ���������ɱ�����
	 * @param partTypeNo
	 * @return
	 */
	public List<Map<String, Object>> queryProductRealCostAnalysis(String  partTypeNo,String partNo) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String sql = "select results.*,"+
					 	"cost.equ_serialNo,"+
					 	"if(cost.equ_price/period_depreciation is null,0,cost.equ_price/(period_depreciation*365*24*3600)) as depreciationCost,"+
					 	"if(cost.process_accessories_cost is null,0,cost.process_accessories_cost) as proacceCost,"+
					 	"if(cost.prepare_accessories_cost is null,0, cost.prepare_accessories_cost) as preacceCost,"+
					 	"if(cost.prepare_cost is null,0,cost.prepare_cost) as prepareCost,"+
					 	"if(cost.cutting_cost is null,0,cost.cutting_cost) as cuttingCost,"+
					 	"if(cost.dryRunning_cost is null,0,cost.dryRunning_cost) as dryRunningCost,"+
					    "member.salary/(22*8*3600) as salary"+
				       " from " +
				        "(select equ_serialNo,partNo,parttypeNo,eventID,processID,starttime,finishtime,cuttingTime,workTime,installtime,processname,materialPrice,reqNum,reqType " +
				          " from " +
				           "(select a.* " +
				             " from " +
				              "( select distinct workevent.equ_serialNo as equ_serialNo," +
				                  "part.`name` as partName," +
				           	      "part.`no` as partNo," +
				                  "partevent.eventID as eventID," +
				                  "parttype.`no` as parttypeNo," +
				                  "workevent.starttime," +
				                  "workevent.finishtime," +
				                  "workevent.cuttingTime," +
				                  "workevent.workTime," +
				                  "process.install_time as installtime," +
				                  "process.ID as processID," +
				                  "process.`name` as processname," +
				                  "material.price as materialPrice," +
				                  "processmaterial.requirementNum as reqNum," +
				                  "processmaterial.requirementType as reqType" +
				                  " from t_part_basic_info part INNER JOIN t_part_type_info parttype on part.parttype_id=parttype.ID" +
				                  " inner join r_PART_EVENT_INFO partevent on  part.id=partevent.partID" +
				                  " inner join t_userequworkevents workevent on workevent.id=partevent.eventID" +
				                  " inner join t_jobdispatchlist_info jobdispatch on workevent.cuttingTask=jobdispatch.`no`" +
				                  " inner join t_process_info process on jobdispatch.processID=process.ID" +
				                  " left outer join r_ProcessMaterial_info processmaterial on processmaterial.processID=process.ID" +
				                  " left outer join t_materail_type_info material on material.ID=processmaterial.materialTypeID" +				              			                  
				                  " order by equ_serialNo desc,starttime ASC" +
				                  ") a " +
				                  ") c) results " +
				                  " left outer JOIN t_equipmentaddinfo equipment on results.equ_serialNo=equipment.equ_serialNo" +
				                  " left outer join t_equipment_cost_info cost on cost.equ_serialNo=equipment.equ_SerialNo" +
				                  "	inner join r_Equipment_Member_info equMem on equMem.equ_ID=equipment.equ_id" +
				                  " inner join t_member_info member on member.ID=equMem.member_ID where 1=1";	        
			
			if(!StringUtils.isEmpty(partTypeNo)) sql+=" and parttypeNo='"+partTypeNo	+"'";
			if(!StringUtils.isEmpty(partNo)) sql+=" and partNo='"+partNo	+"'";
			List<Map<String,Object>> dataList = dao.executeNativeQuery(sql, parameters);
			List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> rs = new ArrayList<Map<String,Object>>();
			Set<String> set = new HashSet<String>();
			
			int seq=1;
			for(int i=0;i<dataList.size();i++)
			{
				Map<String,Object> record=dataList.get(i);				
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("partNo",record.get("partNo"));  //��Ʒ����
				map.put("equSerialNo", record.get("equ_serialNo"));
				map.put("processID", record.get("processID")); //����	
				map.put("eventID",record.get("eventID"));//�ӹ�ʱ��ID
				
				set.add(record.get("partNo").toString());
				//int rank=(Integer)record.get("rank");
				
				int workTime=0;
				if(!StringUtils.isEmpty(record.get("workTime")+"")) workTime=(Integer)record.get("workTime");//�ӹ�ʱ��     t��
				int cuttingTime=0;
				if(!StringUtils.isEmpty(record.get("cuttingTime")+"")) cuttingTime=(Integer)record.get("cuttingTime");//����ʱ�� t��
				int dryRunning=workTime-cuttingTime; //������ʱ��   t��	
	
				double salarypersec=0;
				if(!StringUtils.isEmpty(record.get("salary")+""))   
					salarypersec=Double.valueOf(record.get("salary").toString()); //  k��
				
				double proacceCost=0;
				if(!StringUtils.isEmpty(record.get("proacceCost")+""))		
					proacceCost=Double.valueOf(record.get("proacceCost").toString());  // �ӹ�ʱ�丨������  k�Ӹ�
				
				double preacceCost=0;
				if(!StringUtils.isEmpty(record.get("preacceCost")+""))	
					preacceCost=Double.valueOf(record.get("preacceCost").toString());   // ׼��ʱ�丨������  k׼��
				
				double depreciationCost=0;
				if(!StringUtils.isEmpty(record.get("depreciationCost")+""))
					depreciationCost=Double.valueOf(record.get("depreciationCost").toString()); //�����۾���   k��
				
				double prepareCost=0;
				if(!StringUtils.isEmpty(record.get("prepareCost")+""))
					prepareCost=Double.valueOf(record.get("prepareCost").toString()); //K׼
				
				double cuttingCost=0;
				if(!StringUtils.isEmpty(record.get("cuttingCost")+""))
					cuttingCost=Double.valueOf(record.get("cuttingCost").toString()); //K��
				
				double dryRunningCost=0;
				if(!StringUtils.isEmpty(record.get("dryRunningCost")+""))
					dryRunningCost=Double.valueOf(record.get("dryRunningCost").toString());//K��
				
				//����
				double materialPrice=0;
				if(!StringUtils.isEmpty(record.get("materialPrice")+""))
				     materialPrice=Double.valueOf(record.get("materialPrice").toString()); //�����ϵ���
				
				double reqNum =0;
				if(!StringUtils.isEmpty(record.get("reqNum")+""))		
					reqNum=Double.valueOf(record.get("reqNum").toString());//��������
				
				String reqType=(String)record.get("reqType"); //�������
				
				long preparttime=0; //׼��ʱ��   t׼

				if(seq!=1&&i>0)
				{
				
				Map<String,Object> record_last=dataList.get(i-1);
			
				if(record.get("processID").toString().equals(record_last.get("processID").toString()))  //׼��ʱ�䣺�ӹ��¼����е�ѡ��һ���Ŀ�ʼʱ�������һ���Ľ���ʱ������������ֵ�빤����е�װ��ʱ�����Ƚϣ�����������
				{	
					seq++;
					long between=StringUtils.dateTimeBetween(record_last.get("finishtime").toString(),record.get("starttime").toString());
				    int installtime=(Integer)record.get("installtime"); //װ��ʱ��
				    if(between<installtime&&between>0) preparttime=between;
				    else preparttime=installtime;
				}
				else {	seq=1;}}
				else{
					seq++;
					String installtime=record.get("installtime")+"";
					if(!StringUtils.isEmpty(installtime))
					     preparttime=(Integer)record.get("installtime"); //װ��ʱ��
					else preparttime=0;
					
				}
				
				//ÿ��������˹�
				double peopleCost=(preparttime+workTime)*salarypersec;
				map.put("peopleCost",peopleCost);
				//ÿ�����������
				double mainMaterialCost=0;
				if(!"ǰ������".equals(reqType))	mainMaterialCost=materialPrice*reqNum;
				map.put("mainMaterialCost",mainMaterialCost);
				//����
				double accMaterialCost=preparttime*preacceCost+workTime*proacceCost;
				map.put("accMaterialCost",accMaterialCost);
				//�۾�
				double oldCost=(preparttime+workTime)*depreciationCost;
				map.put("oldCost",oldCost);
				//��Դ(��̨)
				double energyCost=preparttime*prepareCost+cuttingTime*cuttingCost+dryRunning*dryRunningCost;
				map.put("energyCost",energyCost);
				map.put("energyCost_prepare", StringUtils.doubleConvertFormat(preparttime*prepareCost));
				map.put("energyCost_cutting", StringUtils.doubleConvertFormat(cuttingTime*cuttingCost));
				map.put("energyCost_dryRunning", StringUtils.doubleConvertFormat(dryRunning*dryRunningCost));
				tempList.add(map);
			}

			//set ת list ����
			List<String> partNolist=new ArrayList<String>(set);		
			Collections.sort(partNolist, new Comparator<String>()  
				      {public int compare(String o1, String o2) { return o1.compareTo(o2);}});
			
			for(String partrec:partNolist){
				Map<String,Object> partcost=new HashMap<String,Object>();
				String partNo1 =partrec;			
				partcost.put("partNo", partNo1);
				double total_peopleCost=0;
				double total_mainMaterialCost=0;
				double total_accMaterialCost=0;
				double total_oldCost=0;
				double total_energyCost=0;
				for(Map<String,Object> rec:tempList)
				  {
					  if(rec.get("partNo").equals(partNo1))
					  {
						   total_peopleCost+=Double.valueOf(rec.get("peopleCost").toString());
						   total_mainMaterialCost+=Double.valueOf(rec.get("mainMaterialCost").toString());
						   total_accMaterialCost+=Double.valueOf(rec.get("accMaterialCost").toString());
						   total_oldCost+=Double.valueOf(rec.get("oldCost").toString());
						   total_energyCost+=Double.valueOf(rec.get("energyCost").toString());
					  }	
			       }
				partcost.put("realCost", StringUtils.doubleConvertFormat(total_peopleCost+total_mainMaterialCost+total_accMaterialCost+total_oldCost+total_energyCost));				
				partcost.put("peopleCost", StringUtils.doubleConvertFormat(total_peopleCost));
				partcost.put("mainMaterialCost", StringUtils.doubleConvertFormat(total_mainMaterialCost));
				partcost.put("accMaterialCost", StringUtils.doubleConvertFormat(total_accMaterialCost));
				partcost.put("oldCost", StringUtils.doubleConvertFormat(total_oldCost));
				partcost.put("energyCost",StringUtils.doubleConvertFormat(total_energyCost));
				rs.add(partcost);
	}
			
			for(Map<String,Object> rec:rs)
				 System.err.println("----------partNo:"+rec.get("partNo")+"--realCost:"+rec.get("realCost")+"--peopleCost:"+rec.get("peopleCost")+"--mainMaterialCost:"
	                       +rec.get("mainMaterialCost")+"--accMaterialCost:"+rec.get("accMaterialCost")+"--oldCost:"+rec.get("oldCost")+"--energyCost:"+rec.get("energyCost"));
			
			return rs;
	}
	
	/**
	 * ����ֵ
	 */
	@Override
	public Map<String, Object> getProductTheoryCostAnalysis(String partTypeNo) {
		// TODO Auto-generated method stub
		Collection<Parameter> parameters = new HashSet<Parameter>();		
		String hql="select new Map(parttype.no as partTypeNo,sum(cost.mainMaterialCost+cost.subsidiaryMaterialCost+cost.peopleCost+cost.energyCost+cost.deviceCost) as theoryCost," +
				" sum(cost.mainMaterialCost) as theoryMainMaterialCost,sum(cost.subsidiaryMaterialCost) as theorSubsidiaryMaterialCost,sum(cost.peopleCost) as theoryPeopleCost,sum(cost.energyCost) as theoryEnergyCost,sum(cost.deviceCost) as theoryDeviceCost) " +
				"   from TPartTypeInfo parttype,TProcessplanInfo processplan,TPartProcessCost cost,TProcessInfo process where parttype.id=cost.TPartTypeInfo.id and processplan.TPartTypeInfo.id=parttype.id and process.id=cost.TProcessInfo.id and processplan.defaultSelected=1 and process.status!=1 and parttype.no='"+partTypeNo+"'";
		List<Map<String,Object>> temp=dao.executeQuery(hql, parameters);
		if(temp!=null&&temp.size()>0) return temp.get(0);
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getPartTypeList(String nodeid) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select new Map(t.no as parttypeNo) from TPartTypeInfo t WHERE t.nodeid='"+nodeid+"' and t.status<> 'ɾ��' order by t.no asc";
		return dao.executeQuery(hql, parameters);
	}

	/**
	 * ��Ʒ���б�
	 */	
	@Override
	public List<Map<String, Object>> getPartNoList(String partTypeNo) {
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="select new Map(part.no as partNo) from TPartBasicInfo part ,TPartTypeInfo t WHERE part.partTypeId = t.id  ";
		if(partTypeNo!=null && !"".equals(partTypeNo)){
			hql += " AND  t.no = '"+partTypeNo+"'  "  ;
          }
		hql += "order by part.id desc";
		return dao.executeQuery(hql, parameters);
	}

	/**
	 * �ӹ��¼�����
	 */
	@Override
	public Boolean processEquWorkEvent(Long eventId) {
		// TODO Auto-generated method stub
		if(null==eventId) return false;
		//����ʵ��		
		TUserEquWorkEvents tUserEquWorkEvent=commonService.get(TUserEquWorkEvents.class,eventId);		
		if(tUserEquWorkEvent==null||StringUtils.isEmpty(tUserEquWorkEvent.getCuttingTask())) return false;
		//��ȡ��Ӧ������Ϣ
		Collection<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter("no", tUserEquWorkEvent.getCuttingTask(), Operator.EQ));
		List<TJobdispatchlistInfo> temp=commonService.find(TJobdispatchlistInfo.class, (List<Sort>)null, parameters);
		if(temp!=null&&temp.size()>0){
			TJobdispatchlistInfo record=temp.get(0);
			
			LogHelper.log("--���¹�������,����ǰ�������",record.getFinishNum()+"");
			
			record.setFinishNum(record.getFinishNum()+1); //���������� 1	
			
			TProcessInfo tProcessInfo=record.getTProcessInfo();
			int offlineProcess=tProcessInfo.getOfflineProcess();
			
			//�������������ڼӹ�������״̬Ϊ���
			//��ӱ������ݴ���-20140725  start_1  ---------------------------------------------//
			//�����ǰ���������+������=�ƻ���,�����
			if(record.getFinishNum().intValue()+record.getWisScrapNum().intValue()>=record.getProcessNum().intValue())
			{
			   record.setStatus(new Integer(70));
			   record.setRealEndtime(new Date());
			}
			//�����ϵ������Ӧ�Ĺ���
			else if(tProcessInfo.getOnlineProcessId()!=null)
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
			//��ӱ������ݴ���-20140725  end_1  ---------------------------------------------//
			
			//����豸����Ա��������-20140807  start  ---------------------------------------------//
			//����豸����
			//�����������
			Long partTypeId=0L;
			Collection<Parameter> parttype_parameters = new HashSet<Parameter>();
			parttype_parameters.add(new Parameter("no", tUserEquWorkEvent.getPartNo(), Operator.EQ)); //
			List<TPartTypeInfo> parttype_temp=commonService.find(TPartTypeInfo.class, (List<Sort>)null, parttype_parameters);
			if(parttype_temp!=null&&parttype_temp.size()>0) 
				partTypeId=parttype_temp.get(0).getId();
			
			Collection<Parameter> equproduction_parameters = new HashSet<Parameter>();
			equproduction_parameters.add(new Parameter("equSerialNo", tUserEquWorkEvent.getEquSerialNo(), Operator.EQ)); //�豸
			equproduction_parameters.add(new Parameter("processId", tProcessInfo.getId(), Operator.EQ)); // ����ID
			equproduction_parameters.add(new Parameter("partTypeId", partTypeId, Operator.EQ)); // �������ID
			equproduction_parameters.add(new Parameter("updateDate", new Date(),Operator.EQ)); // ʱ��
			List<TEquproduction> equproduction_temp=commonService.find(TEquproduction.class, (List<Sort>)null, equproduction_parameters);
			if(equproduction_temp!=null&&equproduction_temp.size()>0) //���Բ�ѯ���ü�¼
			{
				TEquproduction tEquproduction=equproduction_temp.get(0);
				tEquproduction.setProcessNum(tEquproduction.getProcessNum()+1);
				commonService.update(tEquproduction);
				
			}else {  //�����µ�����
				if(partTypeId.longValue()!=0)  //��ֹ�����쳣ֵ
				{
					TEquproduction tEquproduction=new TEquproduction();
					tEquproduction.setEquSerialNo(tUserEquWorkEvent.getEquSerialNo());
					tEquproduction.setPartTypeId(partTypeId);
					tEquproduction.setProcessId(tProcessInfo.getId());
					tEquproduction.setProcessNum(1);
					tEquproduction.setUpdateDate(new Date());
					commonService.save(tEquproduction);
				}
			}
			
		    //�����Ա����
			Collection<Parameter> userproduction_parameters = new HashSet<Parameter>();
			userproduction_parameters.add(new Parameter("operatorNo", tUserEquWorkEvent.getOperatorNo(), Operator.EQ)); //��Ա����
			userproduction_parameters.add(new Parameter("processId", tProcessInfo.getId(), Operator.EQ)); // ����ID
			userproduction_parameters.add(new Parameter("partTypeId", partTypeId, Operator.EQ)); // �������ID
			userproduction_parameters.add(new Parameter("updateDate", new Date(),Operator.EQ)); // ʱ��
			List<TUserproduction> userproduction_temp=commonService.find(TUserproduction.class, (List<Sort>)null, userproduction_parameters);
			if(userproduction_temp!=null&&userproduction_temp.size()>0) //���Բ�ѯ���ü�¼
			{
				TUserproduction tUserproduction=userproduction_temp.get(0);
				tUserproduction.setProcessNum(tUserproduction.getProcessNum()+1);
				commonService.update(tUserproduction);
				
			}else {  //�����µ�����
				if(partTypeId.longValue()!=0&&!StringUtils.isEmpty(tUserEquWorkEvent.getOperatorNo()))  //��ֹ�����쳣ֵ
				{
					TUserproduction tUserproduction=new TUserproduction();
					tUserproduction.setOperatorNo(tUserEquWorkEvent.getOperatorNo());
					tUserproduction.setPartTypeId(partTypeId);
					tUserproduction.setProcessId(tProcessInfo.getId());
					tUserproduction.setProcessNum(1);
					tUserproduction.setUpdateDate(new Date());
					commonService.save(tUserproduction);
				}
			}
			//����豸����Ա��������-20140807  end  ---------------------------------------------//
			
			
			Long jobplanId=record.getJobplanId();
			
			LogHelper.log("--���¹�������,���º������:",record.getFinishNum()+"");
			
			//�ж��Ƿ������߹�λ			
			if(offlineProcess==1)  //�����߹�λ
			{
				LogHelper.log("--���߹�λ����:",tProcessInfo.getName());
				//�����������
				String hql="select new Map(parttype.no as partTypeNo,parttype.name as partTypeName,parttype.id as partTypeId) from TPartProcessCost partprocesscost,TPartTypeInfo parttype,TProcessInfo process" +
						" where partprocesscost.TProcessInfo.id=process.id and partprocesscost.TPartTypeInfo.id=parttype.id "+
						" and process.id="+tProcessInfo.getId();
				
				System.err.println(tProcessInfo.getId());
				Collection<Parameter> parameters1 = new HashSet<Parameter>();				
				List<Map<String,Object>> parttypetemp=commonDao.executeQuery(hql,parameters1);
				
				if(parttypetemp!=null&&parttypetemp.size()>0)
					{  
					     Map<String,Object> parttypeinfo=parttypetemp.get(0);
					     TPartBasicInfo addPartBasicInfo=new TPartBasicInfo();
					     addPartBasicInfo.setName(parttypeinfo.get("partTypeName").toString());
					     addPartBasicInfo.setPartTypeId(Long.parseLong(parttypeinfo.get("partTypeId").toString()));
					     addPartBasicInfo.setOfflineDate(tUserEquWorkEvent.getFinishtime());
					     
					     LogHelper.log("-----�ɼ���Ʒ��Ϣ:",addPartBasicInfo.getName());
					     commonService.save(addPartBasicInfo);
					     
					     addPartBasicInfo.setNo(parttypeinfo.get("partTypeNo")+""+addPartBasicInfo.getId());
					     
					     //�������иò�Ʒ�����ι���
					     String hql1="select distinct new Map(jobdispatch.no as jobdispatchNo) from TJobdispatchlistInfo jobdispatch " +
									" where  jobdispatch.batchNo='"+record.getBatchNo()+"'";  //���κ�ȥ����					             
					     
					     List<Map<String,Object>> partworkevent=dao.executeQuery(hql1,parameters1);
					     if(partworkevent!=null&&partworkevent.size()>0)
					     {
					    	 LogHelper.log("-----�ɼ��ò�Ʒ���еļӹ��¼�:",addPartBasicInfo.getName());
					    	
					    	 /*���㵱ǰ���߹�λ��λ��*/
                        String hql2 = " select new Map(t.id as eventId) from TUserEquWorkEvents t where t.cuttingTask='" + record.getNo() + "' order by t.starttime asc";
                        List<Map<String, Object>> rowidListTemp = dao.executeQuery(hql2, parameters1);
                        int rowid = 0;
                        if (rowidListTemp != null && rowidListTemp.size() > 0)
                            for (int index = 0; index < rowidListTemp.size(); index++) {
                                Map<String, Object> idtemp = rowidListTemp.get(index);
                                if (Long.valueOf(idtemp.get("eventId").toString()).longValue() == tUserEquWorkEvent.getId().longValue()) {
                                    rowid = index;
                                    break;
                                }
                            }

                        boolean tag = true;
				    		/*�������й�λ��ֵ*/
                        for (Map<String, Object> rec : partworkevent) {
                            String hql3 = " select new Map(t.id as eventId,t.starttime as starttime) from TUserEquWorkEvents t where t.cuttingTask='" + rec.get("jobdispatchNo") + "' order by t.starttime asc";
                            List<Map<String, Object>> rowidListTemp1 = dao.executeQuery(hql3, parameters1);
                            if (rowidListTemp1 != null && rowidListTemp1.size() > rowid) {
                                Map<String, Object> recordevent = rowidListTemp1.get(rowid);
                                LogHelper.log("-------�ӹ��¼�ID:", recordevent.get("eventId").toString());
                                if (tag) {
                                    addPartBasicInfo.setOnlineDate((Date) recordevent.get("starttime")); //��Ӳ�Ʒ����ʱ��
                                    tag = false;
                                }
                                TPartEventInfo addtPartEventInfo = new TPartEventInfo();
                                addtPartEventInfo.setEventId(Long.valueOf(recordevent.get("eventId").toString()));
                                addtPartEventInfo.setPartId(addPartBasicInfo.getId());
                                commonService.save(addtPartEventInfo);
                            }
                        }
                    }
                    commonService.update(addPartBasicInfo);
                    //���½ڵ������ſ� 
                    Date partFinishDate = tUserEquWorkEvent.getFinishtime();
                    partFinishDate = StringUtils.convertDate(StringUtils.formatDate(partFinishDate, 2), "yyyy-MM-dd");
                    String partFinishiDateStr = StringUtils.formatDate(partFinishDate, 2);//����ʱ��
                    TNodes tnodes = commonService.get(TNodes.class, record.getNodeid());
                    //ÿ�����һ�����ݣ��ж��ǲ��뻹�Ǹ���
                    Collection<Parameter> nodeproductionprofile_param = new HashSet<Parameter>();
                    nodeproductionprofile_param.add(new Parameter("TNodes", tnodes, Operator.EQ));
                    nodeproductionprofile_param.add(new Parameter("uprodId", addPartBasicInfo.getPartTypeId(), Operator.EQ));//B21 ���޲�Ʒ���ͣ���Ʒ����ID�����������IDƥ��
                    List<TNodeProductionProfiles> nodeproductionprofile_rs = commonService.find(TNodeProductionProfiles.class, Arrays.asList(new Sort("updateTime", Sort.Direction.DESC)), nodeproductionprofile_param);
                    if (nodeproductionprofile_rs != null && nodeproductionprofile_rs.size() > 0) {
                        TNodeProductionProfiles tnodeproductionprofiles = nodeproductionprofile_rs.get(0);

                        Date updateTime = tnodeproductionprofiles.getUpdateTime();
                        if (partFinishiDateStr.equals(StringUtils.formatDate(updateTime, 2))) {   //���²�����Ϣ
                            tnodeproductionprofiles.setDailyOutput(tnodeproductionprofiles.getDailyOutput() + 1);
                            tnodeproductionprofiles.setWeeklyOutput(tnodeproductionprofiles.getWeeklyOutput() + 1);
                            tnodeproductionprofiles.setMonthlyOutput(tnodeproductionprofiles.getMonthlyOutput() + 1);
                            tnodeproductionprofiles.setAnnualOutput(tnodeproductionprofiles.getAnnualOutput() + 1);
                            tnodeproductionprofiles.setTotalOutput(tnodeproductionprofiles.getTotalOutput() + 1);
                            commonService.update(TNodeProductionProfiles.class, tnodeproductionprofiles);
                        } else {
                            Calendar calendar_update = Calendar.getInstance();
                            calendar_update.setTime(updateTime);

                            Calendar calendar_finish = Calendar.getInstance();
                            calendar_finish.setTime(partFinishDate);
                            //����ʷ��Ϣ�������µ���Ϣ
                            int previous_year = calendar_update.get(Calendar.YEAR);
                            int previous_month = calendar_update.get(Calendar.MONTH);
                            int year = calendar_finish.get(Calendar.YEAR);
                            int month = calendar_finish.get(Calendar.MONTH);
                            TNodeProductionProfiles addNodeProductionProfiles = new TNodeProductionProfiles();
                            addNodeProductionProfiles.setDailyOutput((double) 1); //�ղ���
                            addNodeProductionProfiles.setTotalOutput(tnodeproductionprofiles.getTotalOutput() + 1); //�ܲ���
                            addNodeProductionProfiles.setTNodes(tnodeproductionprofiles.getTNodes()); //�ڵ�
                            addNodeProductionProfiles.setUpdateTime(partFinishDate); //����ʱ��
                            addNodeProductionProfiles.setUprodId(tnodeproductionprofiles.getUprodId()); //��Ʒ���ID

                            if (previous_year == year) //ͬ��
                            {
                                addNodeProductionProfiles.setAnnualOutput(tnodeproductionprofiles.getAnnualOutput() + 1);
                                if (previous_month == month) //ͬ�·�
                                {
                                    addNodeProductionProfiles.setMonthlyOutput(tnodeproductionprofiles.getMonthlyOutput() + 1);
                                    //ͬ��
                                    if (StringUtils.getYearWeek(updateTime).equals(StringUtils.getYearWeek(partFinishDate)))
                                        addNodeProductionProfiles.setWeeklyOutput(tnodeproductionprofiles.getWeeklyOutput() + 1);
                                    else
                                        addNodeProductionProfiles.setWeeklyOutput((double) 1);
                                } else {
                                    //���·�
                                    addNodeProductionProfiles.setMonthlyOutput((double) 1);
                                    //���·� ͬ��
                                    if (StringUtils.getYearWeek(updateTime).equals(StringUtils.getYearWeek(partFinishDate))) {
                                        addNodeProductionProfiles.setWeeklyOutput(tnodeproductionprofiles.getWeeklyOutput() + 1);
                                    } else {
                                        addNodeProductionProfiles.setWeeklyOutput((double) 1);
                                    }
                                }
                            } else {   //����
                                addNodeProductionProfiles.setAnnualOutput((double) 1);
                                addNodeProductionProfiles.setMonthlyOutput((double) 1);
                                addNodeProductionProfiles.setWeeklyOutput((double) 1); //?����ͬ�ܣ���ô��
                            }
                            commonService.save(TNodeProductionProfiles.class, addNodeProductionProfiles);
                        }
                    } else {
                        //û����ʷ��Ϣ�������µ���Ϣ
                        TNodeProductionProfiles addNodeProductionProfiles = new TNodeProductionProfiles();
                        addNodeProductionProfiles.setTNodes(tnodes); //�ڵ�
                        addNodeProductionProfiles.setUpdateTime(partFinishDate); //����ʱ��
                        addNodeProductionProfiles.setUprodId(addPartBasicInfo.getPartTypeId()); //��Ʒ���ID
                        addNodeProductionProfiles.setDailyOutput((double) 1); //�ղ���
                        addNodeProductionProfiles.setTotalOutput((double) 1); //�ܲ���
                        addNodeProductionProfiles.setAnnualOutput((double) 1);
                        addNodeProductionProfiles.setMonthlyOutput((double) 1);
                        addNodeProductionProfiles.setWeeklyOutput((double) 1);
                        commonService.save(TNodeProductionProfiles.class, addNodeProductionProfiles);
                    }
                }

                //�������μƻ�
                if (!StringUtils.isEmpty(jobplanId + "")) {
                    TJobplanInfo JobplanInfo = commonService.get(TJobplanInfo.class, jobplanId);
                    JobplanInfo.setFinishNum(JobplanInfo.getFinishNum() + 1);
                    int qualifiedNum = 0;
                    if (JobplanInfo.getQualifiedNum() != null)
                        qualifiedNum = JobplanInfo.getQualifiedNum();
                    JobplanInfo.setQualifiedNum(qualifiedNum + 1);
                    commonService.update(TJobplanInfo.class, JobplanInfo);
                    //��ӱ������ݴ���-20140725  start_2  ---------------------------------------------//
                    if (JobplanInfo.getFinishNum() + JobplanInfo.getScrapNum() >= JobplanInfo.getPlanNum()) {
                        JobplanInfo.setStatus(70); //�����
                        JobplanInfo.setFinishDate(new Date());
                        JobplanInfo.setRealEndtime(new Date());
                        commonService.update(TJobplanInfo.class, JobplanInfo);
                        //�����ε��ܷ��ֱ�������β��������֮�͵������μƻ�����ʱ���رո����Σ�ͬʱ���鹤�����ر�
                        String jobdispatchsql = "from TJobdispatchlistInfo where jobplanId=" + jobplanId;
                        List<TJobdispatchlistInfo> tjobdispathclist = commonService.executeQuery(jobdispatchsql);
                        if (tjobdispathclist != null && tjobdispathclist.size() > 0) {
                            for (TJobdispatchlistInfo tj : tjobdispathclist) {
                                if (tj.getStatus() != 70 || tj.getStatus() != 60) //��û�йرյĹ���ȫ���ر�
                                {
                                    tj.setStatus(70);
                                    tj.setRealEndtime(new Date());
                                    commonService.update(TJobdispatchlistInfo.class, tj);
                                }
                            }
                        }
                    }
                    //��ӱ������ݴ���-20140725  end_2  ---------------------------------------------// 	

                    //������ҵ�ƻ�
                    //���´���(1����ֻ��һ���ƻ�)
                    TJobplanInfo p_JobplanInfo = JobplanInfo.getTJobplanInfo();
                    if (p_JobplanInfo != null) {//�����ҵ�ƻ�Ϊnull�����ڴ���
                        //��ȡ��ǰʱ�����ڵ��·�
                        Calendar c = Calendar.getInstance();
                        int currentmonth = c.get(Calendar.MONTH);
                        //ȡ����ҵ�ƻ���ʵ�ʿ�ʼʱ��
                        Calendar pcal = Calendar.getInstance();
                        pcal.setTime(p_JobplanInfo.getRealStarttime());
                        int pmonth = pcal.get(Calendar.MONTH);
                        if (pmonth == currentmonth)//��ǰ�·ݵ��ڸ��ƻ����·�
                        {
                            p_JobplanInfo.setFinishNum(p_JobplanInfo.getFinishNum() + 1);
                            int qualifiedNum1 = 0;
                            if (p_JobplanInfo.getQualifiedNum() != null)
                                qualifiedNum1 = p_JobplanInfo.getQualifiedNum();
                            p_JobplanInfo.setQualifiedNum(qualifiedNum1 + 1);
                            if (p_JobplanInfo.getFinishNum().intValue() == p_JobplanInfo.getPlanNum().intValue()) {
                                p_JobplanInfo.setStatus(70); //�����
                                p_JobplanInfo.setRealEndtime(new Date()); //�����
                            }
                            commonService.update(TJobplanInfo.class, p_JobplanInfo);
                        } else  //�����¸��·ݵ���ҵ�ƻ�
                        {
                            SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM");
                            // c.add(Calendar.MONTH,1); //ȡ��һ����
                            String nextMonth = dayFormat.format(c.getTime());  //����String�͵�ʱ��

                            hql = " from TJobplanInfo t where t.planType=1 " +
                                    " and t.TPartTypeInfo.id=" + p_JobplanInfo.getTPartTypeInfo().getId() +
                                    " and DATE_FORMAT(t.realStarttime,'%Y-%m') = '" + nextMonth + "'";
                            List<TJobplanInfo> list = commonService.executeQuery(hql);
                            //�����ҵ��¸��µ���ҵ�ƻ�
                            if (list != null && list.size() > 0) {
                                TJobplanInfo next_p_jobplanInfo = list.get(0);
                                next_p_jobplanInfo.setFinishNum(next_p_jobplanInfo.getFinishNum() + 1);
                                int qualifiedNum1 = 0;
                                if (next_p_jobplanInfo.getQualifiedNum() != null)
                                    qualifiedNum1 = next_p_jobplanInfo.getQualifiedNum();
                                next_p_jobplanInfo.setQualifiedNum(qualifiedNum1 + 1);
                                if (next_p_jobplanInfo.getFinishNum().intValue() == next_p_jobplanInfo.getPlanNum().intValue()) {
                                    next_p_jobplanInfo.setStatus(70); //�����
                                    next_p_jobplanInfo.setRealEndtime(new Date()); //�����
                                }
                                commonService.update(TJobplanInfo.class, next_p_jobplanInfo);
                            } else //û���ҵ���һ���µģ�����Ȼ�ӵ���ǰ�·�
                            {
                                p_JobplanInfo.setFinishNum(p_JobplanInfo.getFinishNum() + 1);
                                int qualifiedNum1 = 0;
                                if (p_JobplanInfo.getQualifiedNum() != null)
                                    qualifiedNum1 = p_JobplanInfo.getQualifiedNum();
                                p_JobplanInfo.setQualifiedNum(qualifiedNum1 + 1);
                                if (p_JobplanInfo.getFinishNum().intValue() == p_JobplanInfo.getPlanNum().intValue()) {
                                    p_JobplanInfo.setStatus(70); //�����
                                    p_JobplanInfo.setRealEndtime(new Date()); //�����
                                }
                                commonService.update(TJobplanInfo.class, p_JobplanInfo);
                            }

                        }
                    } else  //���û�ж�Ӧ��p_id,�Զ����ҵ��µ���ҵ�ƻ�
                    {
                        //��ȡ��ǰʱ�����ڵ��·�
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM");
                        //ȡ������ҵ�ƻ�
                        String currentMonth = dayFormat.format(c.getTime());  //����String�͵�ʱ��

                        hql = " from TJobplanInfo t where t.planType=1 " +
                                " and t.TPartTypeInfo.id=" + JobplanInfo.getTPartTypeInfo().getId() +
                                " and DATE_FORMAT(t.realStarttime,'%Y-%m') = '" + currentMonth + "'";
                        List<TJobplanInfo> list = commonService.executeQuery(hql);

                        if (list != null && list.size() > 0) {
                            TJobplanInfo p0_JobplanInfo = list.get(0); //ȡ���¼ƻ�
                            p0_JobplanInfo.setFinishNum(p0_JobplanInfo.getFinishNum() + 1);
                            int qualifiedNum1 = 0;
                            if (p0_JobplanInfo.getQualifiedNum() != null)
                                qualifiedNum1 = p0_JobplanInfo.getQualifiedNum();
                            p0_JobplanInfo.setQualifiedNum(qualifiedNum1 + 1);
                            if (p0_JobplanInfo.getFinishNum().intValue() == p0_JobplanInfo.getPlanNum().intValue()) {
                                p0_JobplanInfo.setStatus(70); //�����
                                p0_JobplanInfo.setRealEndtime(new Date()); //�����
                            }
                            commonService.update(p0_JobplanInfo);
                        } //���û�в�����һ����
                        else {
                            Calendar pcal = Calendar.getInstance();
                            pcal.add(Calendar.MONTH, -1); //ȡ��һ����
                            String lastMonth = dayFormat.format(pcal.getTime());  //����String�͵�ʱ��

                            hql = " from TJobplanInfo t where t.planType=1 " +
                                    " and t.TPartTypeInfo.id=" + JobplanInfo.getTPartTypeInfo().getId() +
                                    " and DATE_FORMAT(t.realStarttime,'%Y-%m') = '" + lastMonth + "'";
                            List<TJobplanInfo> list0 = commonService.executeQuery(hql);
                            //�����ҵ��ϸ��µ���ҵ�ƻ�
                            if (list0 != null && list0.size() > 0) {
                                TJobplanInfo last_p_jobplanInfo = list0.get(0);
                                last_p_jobplanInfo.setFinishNum(last_p_jobplanInfo.getFinishNum() + 1);
                                int qualifiedNum1 = 0;
                                if (last_p_jobplanInfo.getQualifiedNum() != null)
                                    qualifiedNum1 = last_p_jobplanInfo.getQualifiedNum();
                                last_p_jobplanInfo.setQualifiedNum(qualifiedNum1 + 1);
                                if (last_p_jobplanInfo.getFinishNum().intValue() == last_p_jobplanInfo.getPlanNum().intValue()) {
                                    last_p_jobplanInfo.setStatus(70); //�����
                                    last_p_jobplanInfo.setRealEndtime(new Date()); //�����
                                }
                                commonService.update(last_p_jobplanInfo);
                            }

                        }

                    }
                }
            }
        }
        LogHelper.log("--�ӹ��¼�����:", eventId + "");
        return true;
    }

    public Boolean processEquWorkEvent(Long eventId, String partNo) {
        if (null == eventId) return false;
        //����ʵ��		
        TUserEquWorkEvents tUserEquWorkEvent = commonService.get(TUserEquWorkEvents.class, eventId);
        if (tUserEquWorkEvent == null || StringUtils.isEmpty(tUserEquWorkEvent.getCuttingTask())) return false;
        //��ȡ��Ӧ������Ϣ
        Collection<Parameter> parameters = new HashSet<Parameter>();
        parameters.add(new Parameter("no", tUserEquWorkEvent.getCuttingTask(), Operator.EQ));
        List<TJobdispatchlistInfo> temp = commonService.find(TJobdispatchlistInfo.class, (List<Sort>) null, parameters);
        if (temp != null && temp.size() > 0) {
            TJobdispatchlistInfo record = temp.get(0);

            LogHelper.log("--���¹�������,����ǰ�������", record.getFinishNum() + "");

            record.setFinishNum(record.getFinishNum() + 1); //���������� 1			
            commonService.save(record);

            LogHelper.log("--���¹�������,���º������:", record.getFinishNum() + "");

//			Long jobplanId=record.getJobplanId();

            Long jobId = record.getTJobInfo().getId();

            //������ҵ
            TJobInfo tJobInfo = commonService.get(TJobInfo.class, jobId);
            if (tJobInfo != null) {
                tJobInfo.setFinishNum(tJobInfo.getFinishNum() + 1);
                LogHelper.log("-----������ҵ:", tJobInfo.getId().toString());
                commonService.update(tJobInfo);

                TPartEventInfo tpi = new TPartEventInfo();
                String partIdHql = "from TPartBasicInfo where no='" + partNo + "'";
                Collection<Parameter> parameters2 = new HashSet<Parameter>();
                TPartBasicInfo partBasicInfo = (TPartBasicInfo) dao.executeQuery(partIdHql, parameters2).get(0);//��ȡ���
                String partBasicInfoHql = "select new Map(" +
                        " p.id as processId,"
                        + " p.TProcessplanInfo.id as processplanId)" +
                        " from TJobInfo j,TProcessInfo p" +
                        " where j.TProcessInfo.id=p.id " +
                        " and j.id=" + record.getTJobInfo().getId();
                Map<String, Object> process = (Map<String, Object>) dao.executeQuery(partBasicInfoHql, parameters2).get(0);//��ȡ����id

                tpi.setProcessID(Long.parseLong(process.get("processId") == null ? "" : process.get("processId").toString()));
                tpi.setPartId(partBasicInfo.getId());
                tpi.setEventId(eventId);
                tpi.setDispatchNo(record.getNo());
                tpi.setStatus(301);//301�ɹ�  302ʧ��
                commonService.save(tpi);//����  TPartEventInfo

                String ProcessInfoHql = "from TProcessInfo where TProcessplanInfo.id='" + process.get("processplanId") + "' "
                        + " order by processOrder";
                List<TProcessInfo> processInfoList = dao.executeQuery(ProcessInfoHql, parameters2); //��ѯ���շ�����Ӧ�ù���
                int tt = 0;
                for (int i = 0; i < processInfoList.size(); i++) {//������һ������
                    TProcessInfo tp = processInfoList.get(i);
                    if (tp.getId().toString().equals(process.get("processId").toString())) {
                        tt = i + 1;
                        break;
                    }
                }
                TProcessInfo tpp = processInfoList.get(tt);
                partBasicInfo.setCurrentProcessID(tpp.getId());
                partBasicInfo.setProcessDate(null);
                commonService.update(partBasicInfo);
            }
        }
        return true;
    }

    @Override
    public List<Map<String, Object>> queryLastPartCost(String equSerialNo) {
        Collection<Parameter> parameters = new HashSet<Parameter>();

        String sql1 = " select max(t.partID) as maxId from r_part_event_info t,t_userequworkevents t1 where t.eventID=t1.ID and t1.equ_serialNo='" + equSerialNo + "'";
        List<Map<String, Object>> maxrecord = dao.executeNativeQuery(sql1, parameters);
        String maxId = "";
        if (maxrecord != null && maxrecord.size() > 0)
            maxId = maxrecord.get(0).get("maxId") + "";

        String sql = "select results.*," +
                "cost.equ_serialNo," +
                "if(cost.equ_price/period_depreciation is null,0,cost.equ_price/(period_depreciation*365*24*3600)) as depreciationCost," +
                "if(cost.process_accessories_cost is null,0,cost.process_accessories_cost) as proacceCost," +
                "if(cost.prepare_accessories_cost is null,0, cost.prepare_accessories_cost) as preacceCost," +
                "if(cost.prepare_cost is null,0,cost.prepare_cost) as prepareCost," +
                "if(cost.cutting_cost is null,0,cost.cutting_cost) as cuttingCost," +
                "if(cost.dryRunning_cost is null,0,cost.dryRunning_cost) as dryRunningCost," +
                "member.salary/(22*8*3600) as salary" +
                " from " +
                "(select equ_serialNo,partNo,parttypeNo,eventID,processID,starttime,finishtime,cuttingTime,workTime,installtime,processname,materialPrice,reqNum,reqType " +
                " from " +
                "(select a.* " +
                " from " +
                "( select distinct workevent.equ_serialNo as equ_serialNo," +
                "part.`name` as partName," +
                "part.`no` as partNo," +
                "partevent.eventID as eventID," +
                "parttype.`no` as parttypeNo," +
                "workevent.starttime," +
                "workevent.finishtime," +
                "workevent.cuttingTime," +
                "workevent.workTime," +
                "process.install_time as installtime," +
                "process.ID as processID," +
                "process.`name` as processname," +
                "material.price as materialPrice," +
                "processmaterial.requirementNum as reqNum," +
                "processmaterial.requirementType as reqType" +
                " from t_part_basic_info part INNER JOIN t_part_type_info parttype on part.parttype_id=parttype.ID" +
                " inner join r_PART_EVENT_INFO partevent on  part.id=partevent.partID" +
                " inner join t_userequworkevents workevent on workevent.id=partevent.eventID" +
                " inner join t_jobdispatchlist_info jobdispatch on workevent.cuttingTask=jobdispatch.`no`" +
                " inner join t_process_info process on jobdispatch.processID=process.ID" +
                " left outer join r_ProcessMaterial_info processmaterial on processmaterial.processID=process.ID" +
                " left outer join t_materail_type_info material on material.ID=processmaterial.materialTypeID" +
                " where part.id=" + maxId +
                " order by equ_serialNo desc,starttime ASC" +
                ") a " +
                ") c) results " +
                " left outer JOIN t_equipmentaddinfo equipment on results.equ_serialNo=equipment.equ_serialNo" +
                " left outer join t_equipment_cost_info cost on cost.equ_serialNo=equipment.equ_SerialNo" +
                "	inner join r_Equipment_Member_info equMem on equMem.equ_ID=equipment.equ_id" +
                " inner join t_member_info member on member.ID=equMem.member_ID";
        List<Map<String, Object>> dataList = dao.executeNativeQuery(sql, parameters);
        List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
        Set<String> set = new HashSet<String>();

        int seq = 1;
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> record = dataList.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("partNo", record.get("partNo"));  //��Ʒ����
            map.put("equSerialNo", record.get("equ_serialNo"));
            map.put("processID", record.get("processID")); //����	
            map.put("eventID", record.get("eventID"));//�ӹ�ʱ��ID

            set.add(record.get("partNo").toString());
            //int rank=(Integer)record.get("rank");

            int workTime = (Integer) record.get("workTime");//�ӹ�ʱ��     t��
            int cuttingTime = (Integer) record.get("cuttingTime");//����ʱ�� t��
            int dryRunning = workTime - cuttingTime; //������ʱ��   t��
            double salarypersec = 0;
            if (!StringUtils.isEmpty(record.get("salary") + ""))
                salarypersec = Double.valueOf(record.get("salary").toString()); //  k��

            double proacceCost = 0;
            if (!StringUtils.isEmpty(record.get("proacceCost") + ""))
                proacceCost = Double.valueOf(record.get("proacceCost").toString());  // �ӹ�ʱ�丨������  k�Ӹ�

            double preacceCost = 0;
            if (!StringUtils.isEmpty(record.get("preacceCost") + ""))
                preacceCost = Double.valueOf(record.get("preacceCost").toString());   // ׼��ʱ�丨������  k׼��

            double depreciationCost = 0;
            if (!StringUtils.isEmpty(record.get("depreciationCost") + ""))
                depreciationCost = Double.valueOf(record.get("depreciationCost").toString()); //�����۾���   k��

            double prepareCost = 0;
            if (!StringUtils.isEmpty(record.get("prepareCost") + ""))
                prepareCost = Double.valueOf(record.get("prepareCost").toString()); //K׼

            double cuttingCost = 0;
            if (!StringUtils.isEmpty(record.get("cuttingCost") + ""))
                cuttingCost = Double.valueOf(record.get("cuttingCost").toString()); //K��

            double dryRunningCost = 0;
            if (!StringUtils.isEmpty(record.get("dryRunningCost") + ""))
                dryRunningCost = Double.valueOf(record.get("dryRunningCost").toString());//K��

            //����
            double materialPrice = 0;
            if (!StringUtils.isEmpty(record.get("materialPrice") + ""))
                materialPrice = Double.valueOf(record.get("materialPrice").toString()); //�����ϵ���

            double reqNum = 0;
            if (!StringUtils.isEmpty(record.get("reqNum") + ""))
                reqNum = Double.valueOf(record.get("reqNum").toString());//��������

            String reqType = (String) record.get("reqType"); //�������

            long preparttime = 0; //׼��ʱ��   t׼

            if (seq != 1 && i > 0) {

                Map<String, Object> record_last = dataList.get(i - 1);

                if (record.get("processID").toString().equals(record_last.get("processID").toString()))  //׼��ʱ�䣺�ӹ��¼����е�ѡ��һ���Ŀ�ʼʱ�������һ���Ľ���ʱ������������ֵ�빤����е�װ��ʱ�����Ƚϣ�����������
                {
                    seq++;
                    long between = StringUtils.dateTimeBetween(record_last.get("finishtime").toString(), record.get("starttime").toString());
                    int installtime = (Integer) record.get("installtime"); //װ��ʱ��
                    if (between < installtime && between > 0) preparttime = between;
                    else preparttime = installtime;
                } else {
                    seq = 1;
                }
            } else {
                seq++;
                String installtime = record.get("installtime") + "";
                if (!StringUtils.isEmpty(installtime))
                    preparttime = (Integer) record.get("installtime"); //װ��ʱ��
                else preparttime = 0;

            }

            //ÿ��������˹�
            double peopleCost = (preparttime + workTime) * salarypersec;
            map.put("peopleCost", peopleCost);
            //ÿ�����������
            double mainMaterialCost = 0;
            if (!"ǰ������".equals(reqType)) mainMaterialCost = materialPrice * reqNum;
            map.put("mainMaterialCost", mainMaterialCost);
            //����
            double accMaterialCost = preparttime * preacceCost + workTime * proacceCost;
            map.put("accMaterialCost", accMaterialCost);
            //�۾�
            double oldCost = (preparttime + workTime) * depreciationCost;
            map.put("oldCost", oldCost);
            //��Դ(��̨)
            double energyCost = preparttime * prepareCost + cuttingTime * cuttingCost + dryRunning * dryRunningCost;
            map.put("energyCost", energyCost);
            map.put("energyCost_prepare", StringUtils.doubleConvertFormat(preparttime * prepareCost));
            map.put("energyCost_cutting", StringUtils.doubleConvertFormat(cuttingTime * cuttingCost));
            map.put("energyCost_dryRunning", StringUtils.doubleConvertFormat(dryRunning * dryRunningCost));
            tempList.add(map);
        }

        for (Iterator<String> iterator = set.iterator(); iterator.hasNext(); ) {
            Map<String, Object> partcost = new HashMap<String, Object>();
            String partNo = (String) iterator.next();
            partcost.put("partNo", partNo);
            double total_peopleCost = 0;
            double total_mainMaterialCost = 0;
            double total_accMaterialCost = 0;
            double total_oldCost = 0;
            double total_energyCost = 0;

            double total_energyCost_cutting = 0;
            double total_energyCost_dryRunning = 0;
            double total_energyCost_prepare = 0;

            List<Map<String, Object>> energycostDetailList = new ArrayList<Map<String, Object>>();

            Map<String, Object> energycost = new HashMap<String, Object>();

            for (Map<String, Object> rec : tempList) {
                if (rec.get("partNo").equals(partNo)) {
                    //��Դ�ɱ�����
                    total_energyCost_cutting += Double.valueOf(rec.get("energyCost_cutting").toString());
                    total_energyCost_dryRunning += Double.valueOf(rec.get("energyCost_dryRunning").toString());
                    total_energyCost_prepare += Double.valueOf(rec.get("energyCost_prepare").toString());
                    //����ɱ�
                    total_peopleCost += Double.valueOf(rec.get("peopleCost").toString());
                    total_mainMaterialCost += Double.valueOf(rec.get("mainMaterialCost").toString());
                    total_accMaterialCost += Double.valueOf(rec.get("accMaterialCost").toString());
                    total_oldCost += Double.valueOf(rec.get("oldCost").toString());
                    total_energyCost += Double.valueOf(rec.get("energyCost").toString());
                }

            }
            partcost.put("peopleCost", StringUtils.doubleConvertFormat(total_peopleCost));
            partcost.put("mainMaterialCost", StringUtils.doubleConvertFormat(total_mainMaterialCost));
            partcost.put("accMaterialCost", StringUtils.doubleConvertFormat(total_accMaterialCost));
            partcost.put("oldCost", StringUtils.doubleConvertFormat(total_oldCost));
            partcost.put("energyCost", StringUtils.doubleConvertFormat(total_energyCost));

            energycost.put("energyCost_cutting", StringUtils.doubleConvertFormat(total_energyCost_cutting));
            energycost.put("energyCost_dryRunning", StringUtils.doubleConvertFormat(total_energyCost_dryRunning));
            energycost.put("energyCost_prepare", StringUtils.doubleConvertFormat(total_energyCost_prepare));
            energycostDetailList.add(energycost);

            partcost.put("energycostDetailList", energycostDetailList);
            rs.add(partcost);
        }

        for (Map<String, Object> rec : rs)
            System.err.println("----------partNo:" + rec.get("partNo") + "--peopleCost:" + rec.get("peopleCost") + "--mainMaterialCost:"
                    + rec.get("mainMaterialCost") + "--accMaterialCost:" + rec.get("accMaterialCost") + "--oldCost:" + rec.get("oldCost") + "--energyCost:" + rec.get("energyCost"));

        if (rs == null || rs.size() < 1) {
            Map<String, Object> partcost = new HashMap<String, Object>();
            partcost.put("partNo", "");
            partcost.put("peopleCost", 0);
            partcost.put("mainMaterialCost", 0);
            partcost.put("accMaterialCost", 0);
            partcost.put("oldCost", 0);
            partcost.put("energyCost", 0);

            List<Map<String, Object>> energycostDetailList = new ArrayList<Map<String, Object>>();
            Map<String, Object> energycost = new HashMap<String, Object>();
            energycost.put("energyCost_cutting", 0);
            energycost.put("energyCost_dryRunning", 0);
            energycost.put("energyCost_prepare", 0);
            energycostDetailList.add(energycost);
            partcost.put("energycostDetailList", energycostDetailList);

            rs.add(partcost);
        }
        return rs;
    }

    @Override
    public String getRCR(String nodeId) {
        Collection<Parameter> parameters = new HashSet<Parameter>();
        List<String> nodeIdList = new ArrayList<String>();// ��ǰ�ڵ�����ӽڵ��id
        nodeIdList = getNodesAllId(nodeId);// �ݹ��ȡǰ�ڵ�id���ӽڵ��id
        String nodeidstr = StringUtils.returnHqlIN(nodeIdList);

        String sql = "select results.*," +
                "cost.equ_serialNo," +
                "if(cost.equ_price/period_depreciation is null,0,cost.equ_price/(period_depreciation*365*24*3600)) as depreciationCost," +
                "if(cost.process_accessories_cost is null,0,cost.process_accessories_cost) as proacceCost," +
                "if(cost.prepare_accessories_cost is null,0, cost.prepare_accessories_cost) as preacceCost," +
                "if(cost.prepare_cost is null,0,cost.prepare_cost) as prepareCost," +
                "if(cost.cutting_cost is null,0,cost.cutting_cost) as cuttingCost," +
                "if(cost.dryRunning_cost is null,0,cost.dryRunning_cost) as dryRunningCost," +
                "member.salary/(22*8*3600) as salary" +
                " from " +
                "(select equ_serialNo,partID,partNo,parttypeNo,eventID,processID,starttime,finishtime,cuttingTime,workTime,installtime,processname,materialPrice,reqNum,reqType " +
                " from " +
                "(select a.* " +
                " from " +
                "( select distinct workevent.equ_serialNo as equ_serialNo," +
                "part.`name` as partName," +
                "part.id as partID," +
                "part.`no` as partNo," +
                "partevent.eventID as eventID," +
                "parttype.`no` as parttypeNo," +
                "workevent.starttime," +
                "workevent.finishtime," +
                "workevent.cuttingTime," +
                "workevent.workTime," +
                "process.install_time as installtime," +
                "process.ID as processID," +
                "process.`name` as processname," +
                "material.price as materialPrice," +
                "processmaterial.requirementNum as reqNum," +
                "processmaterial.requirementType as reqType" +
                " from t_part_basic_info part INNER JOIN t_part_type_info parttype on part.parttype_id=parttype.ID" +
                " inner join r_PART_EVENT_INFO partevent on  part.id=partevent.partID" +
                " inner join t_userequworkevents workevent on workevent.id=partevent.eventID" +
                " inner join t_jobdispatchlist_info jobdispatch on workevent.cuttingTask=jobdispatch.`no`" +
                " inner join t_process_info process on jobdispatch.processID=process.ID" +
                " left outer join r_ProcessMaterial_info processmaterial on processmaterial.processID=process.ID" +
                " left outer join t_materail_type_info material on material.ID=processmaterial.materialTypeID" +
                " order by equ_serialNo desc,starttime ASC" +
                ") a " +
                ") c) results " +
                " inner JOIN t_equipmentaddinfo equipment on results.equ_serialNo=equipment.equ_serialNo" +
                " left outer join t_equipment_cost_info cost on cost.equ_serialNo=equipment.equ_SerialNo" +
                "	inner join r_Equipment_Member_info equMem on equMem.equ_ID=equipment.equ_id" +
                " inner join t_member_info member on member.ID=equMem.member_ID " +
                " where equipment.nodeID in(" + nodeidstr + ") ";

        List<Map<String, Object>> dataList = dao.executeNativeQuery(sql, parameters);
        List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
        Set<String> set = new HashSet<String>();

        int seq = 1;
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> record = dataList.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("partNo", record.get("partNo"));  //��Ʒ����
            map.put("equSerialNo", record.get("equ_serialNo"));
            map.put("processID", record.get("processID")); //����	
            map.put("eventID", record.get("eventID"));//�ӹ�ʱ��ID
            map.put("partTypeNo", record.get("parttypeNo"));// ��Ʒ�ͺ�

            set.add(record.get("partNo").toString());

            int workTime = (Integer) record.get("workTime");//�ӹ�ʱ��     t��
            int cuttingTime = (Integer) record.get("cuttingTime");//����ʱ�� t��
            int dryRunning = workTime - cuttingTime; //������ʱ��   t��
            double salarypersec = 0;
            if (!StringUtils.isEmpty(record.get("salary") + ""))
                salarypersec = Double.valueOf(record.get("salary").toString()); //  k��

            double proacceCost = 0;
            if (!StringUtils.isEmpty(record.get("proacceCost") + ""))
                proacceCost = Double.valueOf(record.get("proacceCost").toString());  // �ӹ�ʱ�丨������  k�Ӹ�

            double preacceCost = 0;
            if (!StringUtils.isEmpty(record.get("preacceCost") + ""))
                preacceCost = Double.valueOf(record.get("preacceCost").toString());   // ׼��ʱ�丨������  k׼��

            double depreciationCost = 0;
            if (!StringUtils.isEmpty(record.get("depreciationCost") + ""))
                depreciationCost = Double.valueOf(record.get("depreciationCost").toString()); //�����۾���   k��

            double prepareCost = 0;
            if (!StringUtils.isEmpty(record.get("prepareCost") + ""))
                prepareCost = Double.valueOf(record.get("prepareCost").toString()); //K׼

            double cuttingCost = 0;
            if (!StringUtils.isEmpty(record.get("cuttingCost") + ""))
                cuttingCost = Double.valueOf(record.get("cuttingCost").toString()); //K��

            double dryRunningCost = 0;
            if (!StringUtils.isEmpty(record.get("dryRunningCost") + ""))
                dryRunningCost = Double.valueOf(record.get("dryRunningCost").toString());//K��

            //����
            double materialPrice = 0;
            if (!StringUtils.isEmpty(record.get("materialPrice") + ""))
                materialPrice = Double.valueOf(record.get("materialPrice").toString()); //�����ϵ���

            double reqNum = 0;
            if (!StringUtils.isEmpty(record.get("reqNum") + ""))
                reqNum = Double.valueOf(record.get("reqNum").toString());//��������

            String reqType = (String) record.get("reqType"); //�������

            long preparttime = 0; //׼��ʱ��   t׼

            if (seq != 1 && i > 0) {

                Map<String, Object> record_last = dataList.get(i - 1);

                if (record.get("processID").toString().equals(record_last.get("processID").toString()))  //׼��ʱ�䣺�ӹ��¼����е�ѡ��һ���Ŀ�ʼʱ�������һ���Ľ���ʱ������������ֵ�빤����е�װ��ʱ�����Ƚϣ�����������
                {
                    seq++;
                    long between = StringUtils.dateTimeBetween(record_last.get("finishtime").toString(), record.get("starttime").toString());
                    int installtime = (Integer) record.get("installtime"); //װ��ʱ��
                    if (between < installtime && between > 0) preparttime = between;
                    else preparttime = installtime;
                } else {
                    seq = 1;
                }
            } else {
                seq++;
                String installtime = record.get("installtime") + "";
                if (!StringUtils.isEmpty(installtime))
                    preparttime = (Integer) record.get("installtime"); //װ��ʱ��
                else preparttime = 0;

            }

            //ÿ��������˹�
            double peopleCost = (preparttime + workTime) * salarypersec;
            map.put("peopleCost", peopleCost);
            //ÿ�����������
            double mainMaterialCost = 0;
            if (!"ǰ������".equals(reqType)) mainMaterialCost = materialPrice * reqNum;
            map.put("mainMaterialCost", mainMaterialCost);
            //����
            double accMaterialCost = preparttime * preacceCost + workTime * proacceCost;
            map.put("accMaterialCost", accMaterialCost);
            //�۾�
            double oldCost = (preparttime + workTime) * depreciationCost;
            map.put("oldCost", oldCost);
            //��Դ(��̨)
            double energyCost = preparttime * prepareCost + cuttingTime * cuttingCost + dryRunning * dryRunningCost;
            map.put("energyCost", energyCost);
            map.put("energyCost_prepare", StringUtils.doubleConvertFormat(preparttime * prepareCost));
            map.put("energyCost_cutting", StringUtils.doubleConvertFormat(cuttingTime * cuttingCost));
            map.put("energyCost_dryRunning", StringUtils.doubleConvertFormat(dryRunning * dryRunningCost));
            tempList.add(map);
        }

        //set ת list ����
        List<String> partNolist = new ArrayList<String>(set);
        Collections.sort(partNolist, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        //�������۳ɱ�
        String theortyhql = "select new Map(parttype.no as partTypeNo,sum(cost.mainMaterialCost+cost.subsidiaryMaterialCost+cost.peopleCost+cost.energyCost+cost.deviceCost) as theorytotal) " +
                "   from TPartTypeInfo parttype,TProcessplanInfo processplan,TPartProcessCost cost,TProcessInfo process where parttype.id=cost.TPartTypeInfo.id and processplan.TPartTypeInfo.id=parttype.id and process.id=cost.TProcessInfo.id and processplan.defaultSelected=1 and process.status!=1 group by parttype.no ";
        List<Map<String, Object>> theortytempList = dao.executeQuery(theortyhql, parameters);

        double realTotal = 0;
        double thoeryTotal = 0;
        for (String partrec : partNolist) {
            Map<String, Object> partcost = new HashMap<String, Object>();
            String partNo = partrec;
            partcost.put("partNo", partNo);
            double total_peopleCost = 0;
            double total_mainMaterialCost = 0;
            double total_accMaterialCost = 0;
            double total_oldCost = 0;
            double total_energyCost = 0;

            String partTypeNo = "";

            for (Map<String, Object> rec : tempList) {
                if (rec.get("partNo").equals(partNo)) {
                    partTypeNo = rec.get("partTypeNo").toString();
                    total_peopleCost += Double.valueOf(rec.get("peopleCost").toString());
                    total_mainMaterialCost += Double.valueOf(rec.get("mainMaterialCost").toString());
                    total_accMaterialCost += Double.valueOf(rec.get("accMaterialCost").toString());
                    total_oldCost += Double.valueOf(rec.get("oldCost").toString());
                    total_energyCost += Double.valueOf(rec.get("energyCost").toString());
                }

            }

            for (Map<String, Object> thoeryrec : theortytempList) {
                if (partTypeNo.equals(thoeryrec.get("partTypeNo").toString())) {
                    thoeryTotal += Double.valueOf(thoeryrec.get("theorytotal").toString());
                    break;
                }
            }

            realTotal += total_peopleCost + total_mainMaterialCost + total_accMaterialCost + total_oldCost + total_energyCost;
        }
        if (thoeryTotal == 0) return "0";
        System.err.println(StringUtils.doubleConvertFormat(realTotal / thoeryTotal));
        return StringUtils.doubleConvertFormat(realTotal / thoeryTotal);
    }

    @Override
    public String getEquRCR(String equSerialNos) {
        Collection<Parameter> parameters = new HashSet<Parameter>();
        String[] tempstr = equSerialNos.split(",");
        List<String> equSrianNos = Arrays.asList(tempstr);
        String equStr = StringUtils.returnHqlIN(equSrianNos);
        String sql = "select results.*," +
                "cost.equ_serialNo," +
                "if(cost.equ_price/period_depreciation is null,0,cost.equ_price/(period_depreciation*365*24*3600)) as depreciationCost," +
                "if(cost.process_accessories_cost is null,0,cost.process_accessories_cost) as proacceCost," +
                "if(cost.prepare_accessories_cost is null,0, cost.prepare_accessories_cost) as preacceCost," +
                "if(cost.prepare_cost is null,0,cost.prepare_cost) as prepareCost," +
                "if(cost.cutting_cost is null,0,cost.cutting_cost) as cuttingCost," +
                "if(cost.dryRunning_cost is null,0,cost.dryRunning_cost) as dryRunningCost," +
                "member.salary/(22*8*3600) as salary" +
                " from " +
                "(select equ_serialNo,partID,partNo,parttypeNo,eventID,processID,starttime,finishtime,cuttingTime,workTime,installtime,processname,materialPrice,reqNum,reqType " +
                " from " +
                "(select a.* " +
                " from " +
                "( select distinct workevent.equ_serialNo as equ_serialNo," +
                "part.`name` as partName," +
                "part.id as partID," +
                "part.`no` as partNo," +
                "partevent.eventID as eventID," +
                "parttype.`no` as parttypeNo," +
                "workevent.starttime," +
                "workevent.finishtime," +
                "workevent.cuttingTime," +
                "workevent.workTime," +
                "process.install_time as installtime," +
                "process.ID as processID," +
                "process.`name` as processname," +
                "material.price as materialPrice," +
                "processmaterial.requirementNum as reqNum," +
                "processmaterial.requirementType as reqType" +
                " from t_part_basic_info part INNER JOIN t_part_type_info parttype on part.parttype_id=parttype.ID" +
                " inner join r_PART_EVENT_INFO partevent on  part.id=partevent.partID" +
                " inner join t_userequworkevents workevent on workevent.id=partevent.eventID" +
                " inner join t_jobdispatchlist_info jobdispatch on workevent.cuttingTask=jobdispatch.`no`" +
                " inner join t_process_info process on jobdispatch.processID=process.ID" +
                " left outer join r_ProcessMaterial_info processmaterial on processmaterial.processID=process.ID" +
                " left outer join t_materail_type_info material on material.ID=processmaterial.materialTypeID" +
                " order by equ_serialNo desc,starttime ASC" +
                ") a " +
                ") c) results " +
                " inner JOIN t_equipmentaddinfo equipment on results.equ_serialNo=equipment.equ_serialNo" +
                " left outer join t_equipment_cost_info cost on cost.equ_serialNo=equipment.equ_SerialNo" +
                "	inner join r_Equipment_Member_info equMem on equMem.equ_ID=equipment.equ_id" +
                " inner join t_member_info member on member.ID=equMem.member_ID " +
                " where equipment.equ_SerialNo in(" + equStr + ")";

        List<Map<String, Object>> dataList = dao.executeNativeQuery(sql, parameters);

        double energyCost = 0;

        int seq = 1;
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> record = dataList.get(i);

            int workTime = (Integer) record.get("workTime");//�ӹ�ʱ��     t��
            int cuttingTime = (Integer) record.get("cuttingTime");//����ʱ�� t��
            int dryRunning = workTime - cuttingTime; //������ʱ��   t��				

            double prepareCost = 0;
            if (!StringUtils.isEmpty(record.get("prepareCost") + ""))
                prepareCost = Double.valueOf(record.get("prepareCost").toString()); //K׼

            double cuttingCost = 0;
            if (!StringUtils.isEmpty(record.get("cuttingCost") + ""))
                cuttingCost = Double.valueOf(record.get("cuttingCost").toString()); //K��

            double dryRunningCost = 0;
            if (!StringUtils.isEmpty(record.get("dryRunningCost") + ""))
                dryRunningCost = Double.valueOf(record.get("dryRunningCost").toString());//K��			


            long preparttime = 0; //׼��ʱ��   t׼

            if (seq != 1 && i > 0) {

                Map<String, Object> record_last = dataList.get(i - 1);

                if (record.get("processID").toString().equals(record_last.get("processID").toString()))  //׼��ʱ�䣺�ӹ��¼����е�ѡ��һ���Ŀ�ʼʱ�������һ���Ľ���ʱ������������ֵ�빤����е�װ��ʱ�����Ƚϣ�����������
                {
                    seq++;
                    long between = StringUtils.dateTimeBetween(record_last.get("finishtime").toString(), record.get("starttime").toString());
                    int installtime = (Integer) record.get("installtime"); //װ��ʱ��
                    if (between < installtime && between > 0) preparttime = between;
                    else preparttime = installtime;
                } else {
                    seq = 1;
                }
            } else {
                seq++;
                String installtime = record.get("installtime") + "";
                if (!StringUtils.isEmpty(installtime))
                    preparttime = (Integer) record.get("installtime"); //װ��ʱ��
                else preparttime = 0;

            }
            //��Դ(��̨)
            energyCost += preparttime * prepareCost + cuttingTime * cuttingCost + dryRunning * dryRunningCost;
        }
        return StringUtils.doubleConvertFormat(energyCost);
    }


    /**
     * ��ȡǰ�ڵ�id���ӽڵ��id
     *
     * @param nodeId �ڵ�id
     * @return List<String>
     */
    public List<String> getNodesAllId(String nodeId) {
        String hql = " select nodeId from TNodes t where t.TNodes.nodeId='" + nodeId + "'";
        return dao.executeQuery(hql);
    }

}

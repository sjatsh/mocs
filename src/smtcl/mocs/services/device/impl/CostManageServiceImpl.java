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
	 * 获取成本
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
				map.put("partNo",record.get("partNo"));  //产品个体
				map.put("equSerialNo", record.get("equ_serialNo"));
				map.put("processID", record.get("processID")); //工序	
				map.put("eventID",record.get("eventID"));//加工时间ID
				
				set.add(record.get("partNo")+"");
				//int rank=(Integer)record.get("rank");
				
				int workTime=0;
				if(!StringUtils.isEmpty(record.get("workTime")+"")) workTime=(Integer)record.get("workTime");//加工时间     t加
				int cuttingTime=0;
				if(!StringUtils.isEmpty(record.get("cuttingTime")+"")) cuttingTime=(Integer)record.get("cuttingTime");//切削时间 t切
				int dryRunning=workTime-cuttingTime; //空运行时间   t空
				double salarypersec=0;
				if(!StringUtils.isEmpty(record.get("salary")+""))   
					salarypersec=Double.valueOf(record.get("salary").toString()); //  k人
				
				double proacceCost=0;
				if(!StringUtils.isEmpty(record.get("proacceCost")+""))		
					proacceCost=Double.valueOf(record.get("proacceCost").toString());  // 加工时间辅料因数  k加辅
				
				double preacceCost=0;
				if(!StringUtils.isEmpty(record.get("preacceCost")+""))	
					preacceCost=Double.valueOf(record.get("preacceCost").toString());   // 准备时间辅料因数  k准辅
				
				double depreciationCost=0;
				if(!StringUtils.isEmpty(record.get("depreciationCost")+""))
					depreciationCost=Double.valueOf(record.get("depreciationCost").toString()); //机床折旧率   k折
				
				double prepareCost=0;
				if(!StringUtils.isEmpty(record.get("prepareCost")+""))
					prepareCost=Double.valueOf(record.get("prepareCost").toString()); //K准
				
				double cuttingCost=0;
				if(!StringUtils.isEmpty(record.get("cuttingCost")+""))
					cuttingCost=Double.valueOf(record.get("cuttingCost").toString()); //K切
				
				double dryRunningCost=0;
				if(!StringUtils.isEmpty(record.get("dryRunningCost")+""))
					dryRunningCost=Double.valueOf(record.get("dryRunningCost").toString());//K空
				
				//主料
				double materialPrice=0;
				if(!StringUtils.isEmpty(record.get("materialPrice")+""))
				     materialPrice=Double.valueOf(record.get("materialPrice").toString()); //主材料单价
				
				double reqNum =0;
				if(!StringUtils.isEmpty(record.get("reqNum")+""))		
					reqNum=Double.valueOf(record.get("reqNum").toString());//需求数量
				
				String reqType=(String)record.get("reqType"); //需求类别
				
				long preparttime=0; //准备时间   t准

				if(seq!=1&&i>0)
				{
				
				Map<String,Object> record_last=dataList.get(i-1);
			
				if(record.get("processID").toString().equals(record_last.get("processID").toString()))  //准备时间：加工事件表中的选中一条的开始时间和他上一条的结束时间做减法，差值与工序表中的装夹时间作比较，超出则作废
				{	
					seq++;
					long between=StringUtils.dateTimeBetween(record_last.get("finishtime").toString(),record.get("starttime").toString());
				    int installtime=Integer.parseInt((record.get("installtime") == null || record.get("installtime").toString().equals("")) ? "0" : record.get("installtime").toString()); //装夹时间
				    
				    if(between<installtime&&between>0) preparttime=between;
				    else preparttime=installtime;
				}
				else {	seq=1;}}
				else{
					seq++;
					String installtime=record.get("installtime")+"";
					if(!StringUtils.isEmpty(installtime))
					     preparttime=(Integer)record.get("installtime"); //装夹时间
					else preparttime=0;
					
				}
				
				//每个工序的人工
				double peopleCost=(preparttime+workTime)*salarypersec;
				map.put("peopleCost",peopleCost);
				//每个工序的主料
				double mainMaterialCost=0;
				if(!"前序物料".equals(reqType))	mainMaterialCost=materialPrice*reqNum;
				map.put("mainMaterialCost",mainMaterialCost);
				//辅料
				double accMaterialCost=preparttime*preacceCost+workTime*proacceCost;
				map.put("accMaterialCost",accMaterialCost);
				//折旧
				double oldCost=(preparttime+workTime)*depreciationCost;
				map.put("oldCost",oldCost);
				//能源(机台)
				double energyCost=preparttime*prepareCost+cuttingTime*cuttingCost+dryRunning*dryRunningCost;
				map.put("energyCost",energyCost);
				map.put("energyCost_prepare", StringUtils.doubleConvertFormat(preparttime*prepareCost));
				map.put("energyCost_cutting", StringUtils.doubleConvertFormat(cuttingTime*cuttingCost));
				map.put("energyCost_dryRunning", StringUtils.doubleConvertFormat(dryRunning*dryRunningCost));
				tempList.add(map);
			}
		    
			//set 转 list 排序
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
					
			Collections.reverse(rs);//rs 倒排	
			/*for(Map<String,Object> rec:rs)
				 System.err.println("----------partNo:"+rec.get("partNo")+"--peopleCost:"+rec.get("peopleCost")+"--mainMaterialCost:"
	                       +rec.get("mainMaterialCost")+"--accMaterialCost:"+rec.get("accMaterialCost")+"--oldCost:"+rec.get("oldCost")+"--energyCost:"+rec.get("energyCost"));
	                       */
			return rs;
	}   	   	
	
	/**
	 * 通过时间 机台成本
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
            map.put("partNo", record.get("partNo"));  //产品个体
            map.put("equSerialNo", record.get("equ_serialNo"));
            map.put("processID", record.get("processID")); //工序	
            map.put("eventID", record.get("eventID"));//加工时间ID

            set.add(record.get("equ_serialNo").toString());

            int workTime = 0;
            if (!StringUtils.isEmpty(record.get("workTime") + ""))
                workTime = (Integer) record.get("workTime");//加工时间     t加
            int cuttingTime = 0;
            if (!StringUtils.isEmpty(record.get("cuttingTime") + ""))
                cuttingTime = (Integer) record.get("cuttingTime");//切削时间 t切
            int dryRunning = workTime - cuttingTime; //空运行时间   t空	

            double prepareCost = 0;
            if (!StringUtils.isEmpty(record.get("prepareCost") + ""))
                prepareCost = Double.valueOf(record.get("prepareCost").toString()); //K准
            double cuttingCost = 0;
            if (!StringUtils.isEmpty(record.get("cuttingCost") + ""))
                cuttingCost = Double.valueOf(record.get("cuttingCost").toString()); //K切
            double dryRunningCost = Double.valueOf(record.get("dryRunningCost").toString());//K空	

            long preparttime = 0; //准备时间   t准

            if (seq != 1 && i > 0) {

                Map<String, Object> record_last = dataList.get(i - 1);

                if (record.get("processID").toString().equals(record_last.get("processID").toString()))  //准备时间：加工事件表中的选中一条的开始时间和他上一条的结束时间做减法，差值与工序表中的装夹时间作比较，超出则作废
                {
                    seq++;
                    long between = StringUtils.dateTimeBetween(record_last.get("finishtime").toString(), record.get("starttime").toString());
                    int installtime = (Integer) record.get("installtime"); //装夹时间
                    if (between < installtime && between > 0) preparttime = between;
                    else preparttime = installtime;
                } else {
                    seq = 1;
                }
            } else {
                seq++;
                String installtime = record.get("installtime") + "";
                if (!StringUtils.isEmpty(installtime))
                    preparttime = (Integer) record.get("installtime"); //装夹时间
                else preparttime = 0;

            }

            //能源(机台)
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
     * 通过产品编号查询	 机台成本
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
				map.put("partNo",record.get("partNo"));  //产品个体
				map.put("equSerialNo", record.get("equ_serialNo"));				
				map.put("starttime", record.get("starttime"));	
				
				set.add(record.get("starttime").toString().substring(0,10));
				
				int workTime=0;
				if(!StringUtils.isEmpty(record.get("workTime")+"")) workTime=(Integer)record.get("workTime");//加工时间     t加
				int cuttingTime=0;
				if(!StringUtils.isEmpty(record.get("cuttingTime")+"")) cuttingTime=(Integer)record.get("cuttingTime");//切削时间 t切
				int dryRunning=workTime-cuttingTime; //空运行时间   t空	
	
				double prepareCost=0;
				if(!StringUtils.isEmpty(record.get("prepareCost")+"")) prepareCost=Double.valueOf(record.get("prepareCost").toString()); //K准
				double cuttingCost=0;
				if(!StringUtils.isEmpty(record.get("cuttingCost")+"")) cuttingCost=Double.valueOf(record.get("cuttingCost").toString()); //K切
				double dryRunningCost=Double.valueOf(record.get("dryRunningCost").toString());//K空	
				
				long preparttime=0; //准备时间   t准

				if(seq!=1&&i>0)
				{
				
				Map<String,Object> record_last=dataList.get(i-1);
			
				if(record.get("processID").toString().equals(record_last.get("processID").toString()))  //准备时间：加工事件表中的选中一条的开始时间和他上一条的结束时间做减法，差值与工序表中的装夹时间作比较，超出则作废
				{	
					seq++;
					long between=StringUtils.dateTimeBetween(record_last.get("finishtime").toString(),record.get("starttime").toString());
				    int installtime=(Integer)record.get("installtime"); //装夹时间
				    if(between<installtime&&between>0) preparttime=between;
				    else preparttime=installtime;
				}
				else {	seq=1;}}
				else{
					seq++;
					String installtime=record.get("installtime")+"";
					if(!StringUtils.isEmpty(installtime))
					     preparttime=(Integer)record.get("installtime"); //装夹时间
					else preparttime=0;
					
				}
				
				//能源(机台)
				double energyCost=preparttime*prepareCost+cuttingTime*cuttingCost+dryRunning*dryRunningCost;
				map.put("energyCost",energyCost);
				map.put("energyCost_prepare", StringUtils.doubleConvertFormat(preparttime*prepareCost));
				map.put("energyCost_cutting", StringUtils.doubleConvertFormat(cuttingTime*cuttingCost));
				map.put("energyCost_dryRunning", StringUtils.doubleConvertFormat(dryRunning*dryRunningCost));
				
				tempList.add(map);
			}
			
			//set 转 list 排序
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
	 * 通过类型或者名称得到结果集。成本分析
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
				map.put("partNo",record.get("partNo"));  //产品个体
				map.put("equSerialNo", record.get("equ_serialNo"));
				map.put("processID", record.get("processID")); //工序	
				map.put("eventID",record.get("eventID"));//加工时间ID
				
				set.add(record.get("partNo").toString());
				//int rank=(Integer)record.get("rank");
				
				int workTime=0;
				if(!StringUtils.isEmpty(record.get("workTime")+"")) workTime=(Integer)record.get("workTime");//加工时间     t加
				int cuttingTime=0;
				if(!StringUtils.isEmpty(record.get("cuttingTime")+"")) cuttingTime=(Integer)record.get("cuttingTime");//切削时间 t切
				int dryRunning=workTime-cuttingTime; //空运行时间   t空	
	
				double salarypersec=0;
				if(!StringUtils.isEmpty(record.get("salary")+""))   
					salarypersec=Double.valueOf(record.get("salary").toString()); //  k人
				
				double proacceCost=0;
				if(!StringUtils.isEmpty(record.get("proacceCost")+""))		
					proacceCost=Double.valueOf(record.get("proacceCost").toString());  // 加工时间辅料因数  k加辅
				
				double preacceCost=0;
				if(!StringUtils.isEmpty(record.get("preacceCost")+""))	
					preacceCost=Double.valueOf(record.get("preacceCost").toString());   // 准备时间辅料因数  k准辅
				
				double depreciationCost=0;
				if(!StringUtils.isEmpty(record.get("depreciationCost")+""))
					depreciationCost=Double.valueOf(record.get("depreciationCost").toString()); //机床折旧率   k折
				
				double prepareCost=0;
				if(!StringUtils.isEmpty(record.get("prepareCost")+""))
					prepareCost=Double.valueOf(record.get("prepareCost").toString()); //K准
				
				double cuttingCost=0;
				if(!StringUtils.isEmpty(record.get("cuttingCost")+""))
					cuttingCost=Double.valueOf(record.get("cuttingCost").toString()); //K切
				
				double dryRunningCost=0;
				if(!StringUtils.isEmpty(record.get("dryRunningCost")+""))
					dryRunningCost=Double.valueOf(record.get("dryRunningCost").toString());//K空
				
				//主料
				double materialPrice=0;
				if(!StringUtils.isEmpty(record.get("materialPrice")+""))
				     materialPrice=Double.valueOf(record.get("materialPrice").toString()); //主材料单价
				
				double reqNum =0;
				if(!StringUtils.isEmpty(record.get("reqNum")+""))		
					reqNum=Double.valueOf(record.get("reqNum").toString());//需求数量
				
				String reqType=(String)record.get("reqType"); //需求类别
				
				long preparttime=0; //准备时间   t准

				if(seq!=1&&i>0)
				{
				
				Map<String,Object> record_last=dataList.get(i-1);
			
				if(record.get("processID").toString().equals(record_last.get("processID").toString()))  //准备时间：加工事件表中的选中一条的开始时间和他上一条的结束时间做减法，差值与工序表中的装夹时间作比较，超出则作废
				{	
					seq++;
					long between=StringUtils.dateTimeBetween(record_last.get("finishtime").toString(),record.get("starttime").toString());
				    int installtime=(Integer)record.get("installtime"); //装夹时间
				    if(between<installtime&&between>0) preparttime=between;
				    else preparttime=installtime;
				}
				else {	seq=1;}}
				else{
					seq++;
					String installtime=record.get("installtime")+"";
					if(!StringUtils.isEmpty(installtime))
					     preparttime=(Integer)record.get("installtime"); //装夹时间
					else preparttime=0;
					
				}
				
				//每个工序的人工
				double peopleCost=(preparttime+workTime)*salarypersec;
				map.put("peopleCost",peopleCost);
				//每个工序的主料
				double mainMaterialCost=0;
				if(!"前序物料".equals(reqType))	mainMaterialCost=materialPrice*reqNum;
				map.put("mainMaterialCost",mainMaterialCost);
				//辅料
				double accMaterialCost=preparttime*preacceCost+workTime*proacceCost;
				map.put("accMaterialCost",accMaterialCost);
				//折旧
				double oldCost=(preparttime+workTime)*depreciationCost;
				map.put("oldCost",oldCost);
				//能源(机台)
				double energyCost=preparttime*prepareCost+cuttingTime*cuttingCost+dryRunning*dryRunningCost;
				map.put("energyCost",energyCost);
				map.put("energyCost_prepare", StringUtils.doubleConvertFormat(preparttime*prepareCost));
				map.put("energyCost_cutting", StringUtils.doubleConvertFormat(cuttingTime*cuttingCost));
				map.put("energyCost_dryRunning", StringUtils.doubleConvertFormat(dryRunning*dryRunningCost));
				tempList.add(map);
			}

			//set 转 list 排序
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
	 * 理论值
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
		String hql="select new Map(t.no as parttypeNo) from TPartTypeInfo t WHERE t.nodeid='"+nodeid+"' and t.status<> '删除' order by t.no asc";
		return dao.executeQuery(hql, parameters);
	}

	/**
	 * 产品号列表
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
	 * 加工事件调度
	 */
	@Override
	public Boolean processEquWorkEvent(Long eventId) {
		// TODO Auto-generated method stub
		if(null==eventId) return false;
		//加载实体		
		TUserEquWorkEvents tUserEquWorkEvent=commonService.get(TUserEquWorkEvents.class,eventId);		
		if(tUserEquWorkEvent==null||StringUtils.isEmpty(tUserEquWorkEvent.getCuttingTask())) return false;
		//获取对应工单信息
		Collection<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter("no", tUserEquWorkEvent.getCuttingTask(), Operator.EQ));
		List<TJobdispatchlistInfo> temp=commonService.find(TJobdispatchlistInfo.class, (List<Sort>)null, parameters);
		if(temp!=null&&temp.size()>0){
			TJobdispatchlistInfo record=temp.get(0);
			
			LogHelper.log("--更新工单数量,更新前完成数：",record.getFinishNum()+"");
			
			record.setFinishNum(record.getFinishNum()+1); //工单数量加 1	
			
			TProcessInfo tProcessInfo=record.getTProcessInfo();
			int offlineProcess=tProcessInfo.getOfflineProcess();
			
			//如果完成数量等于加工数量，状态为完成
			//添加报废数据处理-20140725  start_1  ---------------------------------------------//
			//如果当前工单完成数+报废数=计划数,则完成
			if(record.getFinishNum().intValue()+record.getWisScrapNum().intValue()>=record.getProcessNum().intValue())
			{
			   record.setStatus(new Integer(70));
			   record.setRealEndtime(new Date());
			}
			//查找上道工序对应的工单
			else if(tProcessInfo.getOnlineProcessId()!=null)
			{
				Collection<Parameter> f_parameters = new HashSet<Parameter>();
				f_parameters.add(new Parameter("batchNo", record.getBatchNo(), Operator.EQ)); //建议用batchNo，不用jobplanId，更加通用一些
				f_parameters.add(new Parameter("TProcessInfo", commonService.get(TProcessInfo.class,tProcessInfo.getOnlineProcessId()), Operator.EQ)); // 上道工序		
				List<TJobdispatchlistInfo> f_temp=commonService.find(TJobdispatchlistInfo.class, (List<Sort>)null, f_parameters);
				//存在前序工单情况下，满足前序工单的完成数=本序工单的完成数+本序工单的发现报废数 也完成该工单
				if(f_temp!=null&&f_temp.size()>0)
				{
					  TJobdispatchlistInfo f_jobdispatch=f_temp.get(0);
					  //前序工单已经完成
					  if((f_jobdispatch.getFinishNum().intValue()<=record.getFinishNum().intValue()+record.getWisScrapNum().intValue())&&(f_jobdispatch.getStatus()==60||f_jobdispatch.getStatus()==70)){
						  record.setStatus(70);
						  record.setRealEndtime(new Date());
						}
				}
				}
			commonService.save(record);
			//添加报废数据处理-20140725  end_1  ---------------------------------------------//
			
			//添加设备、人员产量处理-20140807  start  ---------------------------------------------//
			//添加设备产量
			//查找零件类型
			Long partTypeId=0L;
			Collection<Parameter> parttype_parameters = new HashSet<Parameter>();
			parttype_parameters.add(new Parameter("no", tUserEquWorkEvent.getPartNo(), Operator.EQ)); //
			List<TPartTypeInfo> parttype_temp=commonService.find(TPartTypeInfo.class, (List<Sort>)null, parttype_parameters);
			if(parttype_temp!=null&&parttype_temp.size()>0) 
				partTypeId=parttype_temp.get(0).getId();
			
			Collection<Parameter> equproduction_parameters = new HashSet<Parameter>();
			equproduction_parameters.add(new Parameter("equSerialNo", tUserEquWorkEvent.getEquSerialNo(), Operator.EQ)); //设备
			equproduction_parameters.add(new Parameter("processId", tProcessInfo.getId(), Operator.EQ)); // 工序ID
			equproduction_parameters.add(new Parameter("partTypeId", partTypeId, Operator.EQ)); // 零件类型ID
			equproduction_parameters.add(new Parameter("updateDate", new Date(),Operator.EQ)); // 时间
			List<TEquproduction> equproduction_temp=commonService.find(TEquproduction.class, (List<Sort>)null, equproduction_parameters);
			if(equproduction_temp!=null&&equproduction_temp.size()>0) //可以查询到该记录
			{
				TEquproduction tEquproduction=equproduction_temp.get(0);
				tEquproduction.setProcessNum(tEquproduction.getProcessNum()+1);
				commonService.update(tEquproduction);
				
			}else {  //插入新的数据
				if(partTypeId.longValue()!=0)  //防止插入异常值
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
			
		    //添加人员产量
			Collection<Parameter> userproduction_parameters = new HashSet<Parameter>();
			userproduction_parameters.add(new Parameter("operatorNo", tUserEquWorkEvent.getOperatorNo(), Operator.EQ)); //人员工号
			userproduction_parameters.add(new Parameter("processId", tProcessInfo.getId(), Operator.EQ)); // 工序ID
			userproduction_parameters.add(new Parameter("partTypeId", partTypeId, Operator.EQ)); // 零件类型ID
			userproduction_parameters.add(new Parameter("updateDate", new Date(),Operator.EQ)); // 时间
			List<TUserproduction> userproduction_temp=commonService.find(TUserproduction.class, (List<Sort>)null, userproduction_parameters);
			if(userproduction_temp!=null&&userproduction_temp.size()>0) //可以查询到该记录
			{
				TUserproduction tUserproduction=userproduction_temp.get(0);
				tUserproduction.setProcessNum(tUserproduction.getProcessNum()+1);
				commonService.update(tUserproduction);
				
			}else {  //插入新的数据
				if(partTypeId.longValue()!=0&&!StringUtils.isEmpty(tUserEquWorkEvent.getOperatorNo()))  //防止插入异常值
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
			//添加设备、人员产量处理-20140807  end  ---------------------------------------------//
			
			
			Long jobplanId=record.getJobplanId();
			
			LogHelper.log("--更新工单数量,更新后完成数:",record.getFinishNum()+"");
			
			//判断是否是下线工位			
			if(offlineProcess==1)  //是下线工位
			{
				LogHelper.log("--下线工位触发:",tProcessInfo.getName());
				//查找零件类型
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
					     
					     LogHelper.log("-----采集产品信息:",addPartBasicInfo.getName());
					     commonService.save(addPartBasicInfo);
					     
					     addPartBasicInfo.setNo(parttypeinfo.get("partTypeNo")+""+addPartBasicInfo.getId());
					     
					     //查找所有该产品的批次工单
					     String hql1="select distinct new Map(jobdispatch.no as jobdispatchNo) from TJobdispatchlistInfo jobdispatch " +
									" where  jobdispatch.batchNo='"+record.getBatchNo()+"'";  //批次号去查找					             
					     
					     List<Map<String,Object>> partworkevent=dao.executeQuery(hql1,parameters1);
					     if(partworkevent!=null&&partworkevent.size()>0)
					     {
					    	 LogHelper.log("-----采集该产品所有的加工事件:",addPartBasicInfo.getName());
					    	
					    	 /*计算当前下线工位的位置*/
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
				    		/*查找所有工位的值*/
                        for (Map<String, Object> rec : partworkevent) {
                            String hql3 = " select new Map(t.id as eventId,t.starttime as starttime) from TUserEquWorkEvents t where t.cuttingTask='" + rec.get("jobdispatchNo") + "' order by t.starttime asc";
                            List<Map<String, Object>> rowidListTemp1 = dao.executeQuery(hql3, parameters1);
                            if (rowidListTemp1 != null && rowidListTemp1.size() > rowid) {
                                Map<String, Object> recordevent = rowidListTemp1.get(rowid);
                                LogHelper.log("-------加工事件ID:", recordevent.get("eventId").toString());
                                if (tag) {
                                    addPartBasicInfo.setOnlineDate((Date) recordevent.get("starttime")); //添加产品上线时间
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
                    //更新节点生产概况 
                    Date partFinishDate = tUserEquWorkEvent.getFinishtime();
                    partFinishDate = StringUtils.convertDate(StringUtils.formatDate(partFinishDate, 2), "yyyy-MM-dd");
                    String partFinishiDateStr = StringUtils.formatDate(partFinishDate, 2);//下线时间
                    TNodes tnodes = commonService.get(TNodes.class, record.getNodeid());
                    //每天插入一条数据，判断是插入还是更新
                    Collection<Parameter> nodeproductionprofile_param = new HashSet<Parameter>();
                    nodeproductionprofile_param.add(new Parameter("TNodes", tnodes, Operator.EQ));
                    nodeproductionprofile_param.add(new Parameter("uprodId", addPartBasicInfo.getPartTypeId(), Operator.EQ));//B21 暂无产品类型，产品类型ID将与零件类型ID匹配
                    List<TNodeProductionProfiles> nodeproductionprofile_rs = commonService.find(TNodeProductionProfiles.class, Arrays.asList(new Sort("updateTime", Sort.Direction.DESC)), nodeproductionprofile_param);
                    if (nodeproductionprofile_rs != null && nodeproductionprofile_rs.size() > 0) {
                        TNodeProductionProfiles tnodeproductionprofiles = nodeproductionprofile_rs.get(0);

                        Date updateTime = tnodeproductionprofiles.getUpdateTime();
                        if (partFinishiDateStr.equals(StringUtils.formatDate(updateTime, 2))) {   //更新产量信息
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
                            //有历史信息，插入新的信息
                            int previous_year = calendar_update.get(Calendar.YEAR);
                            int previous_month = calendar_update.get(Calendar.MONTH);
                            int year = calendar_finish.get(Calendar.YEAR);
                            int month = calendar_finish.get(Calendar.MONTH);
                            TNodeProductionProfiles addNodeProductionProfiles = new TNodeProductionProfiles();
                            addNodeProductionProfiles.setDailyOutput((double) 1); //日产量
                            addNodeProductionProfiles.setTotalOutput(tnodeproductionprofiles.getTotalOutput() + 1); //总产量
                            addNodeProductionProfiles.setTNodes(tnodeproductionprofiles.getTNodes()); //节点
                            addNodeProductionProfiles.setUpdateTime(partFinishDate); //更新时间
                            addNodeProductionProfiles.setUprodId(tnodeproductionprofiles.getUprodId()); //产品零件ID

                            if (previous_year == year) //同年
                            {
                                addNodeProductionProfiles.setAnnualOutput(tnodeproductionprofiles.getAnnualOutput() + 1);
                                if (previous_month == month) //同月份
                                {
                                    addNodeProductionProfiles.setMonthlyOutput(tnodeproductionprofiles.getMonthlyOutput() + 1);
                                    //同周
                                    if (StringUtils.getYearWeek(updateTime).equals(StringUtils.getYearWeek(partFinishDate)))
                                        addNodeProductionProfiles.setWeeklyOutput(tnodeproductionprofiles.getWeeklyOutput() + 1);
                                    else
                                        addNodeProductionProfiles.setWeeklyOutput((double) 1);
                                } else {
                                    //跨月份
                                    addNodeProductionProfiles.setMonthlyOutput((double) 1);
                                    //跨月份 同周
                                    if (StringUtils.getYearWeek(updateTime).equals(StringUtils.getYearWeek(partFinishDate))) {
                                        addNodeProductionProfiles.setWeeklyOutput(tnodeproductionprofiles.getWeeklyOutput() + 1);
                                    } else {
                                        addNodeProductionProfiles.setWeeklyOutput((double) 1);
                                    }
                                }
                            } else {   //跨年
                                addNodeProductionProfiles.setAnnualOutput((double) 1);
                                addNodeProductionProfiles.setMonthlyOutput((double) 1);
                                addNodeProductionProfiles.setWeeklyOutput((double) 1); //?跨年同周，怎么办
                            }
                            commonService.save(TNodeProductionProfiles.class, addNodeProductionProfiles);
                        }
                    } else {
                        //没有历史信息，插入新的信息
                        TNodeProductionProfiles addNodeProductionProfiles = new TNodeProductionProfiles();
                        addNodeProductionProfiles.setTNodes(tnodes); //节点
                        addNodeProductionProfiles.setUpdateTime(partFinishDate); //更新时间
                        addNodeProductionProfiles.setUprodId(addPartBasicInfo.getPartTypeId()); //产品零件ID
                        addNodeProductionProfiles.setDailyOutput((double) 1); //日产量
                        addNodeProductionProfiles.setTotalOutput((double) 1); //总产量
                        addNodeProductionProfiles.setAnnualOutput((double) 1);
                        addNodeProductionProfiles.setMonthlyOutput((double) 1);
                        addNodeProductionProfiles.setWeeklyOutput((double) 1);
                        commonService.save(TNodeProductionProfiles.class, addNodeProductionProfiles);
                    }
                }

                //更新批次计划
                if (!StringUtils.isEmpty(jobplanId + "")) {
                    TJobplanInfo JobplanInfo = commonService.get(TJobplanInfo.class, jobplanId);
                    JobplanInfo.setFinishNum(JobplanInfo.getFinishNum() + 1);
                    int qualifiedNum = 0;
                    if (JobplanInfo.getQualifiedNum() != null)
                        qualifiedNum = JobplanInfo.getQualifiedNum();
                    JobplanInfo.setQualifiedNum(qualifiedNum + 1);
                    commonService.update(TJobplanInfo.class, JobplanInfo);
                    //添加报废数据处理-20140725  start_2  ---------------------------------------------//
                    if (JobplanInfo.getFinishNum() + JobplanInfo.getScrapNum() >= JobplanInfo.getPlanNum()) {
                        JobplanInfo.setStatus(70); //已完成
                        JobplanInfo.setFinishDate(new Date());
                        JobplanInfo.setRealEndtime(new Date());
                        commonService.update(TJobplanInfo.class, JobplanInfo);
                        //当批次的总发现报废数和尾序的完成数之和等于批次计划数量时，关闭该批次，同时整组工单都关闭
                        String jobdispatchsql = "from TJobdispatchlistInfo where jobplanId=" + jobplanId;
                        List<TJobdispatchlistInfo> tjobdispathclist = commonService.executeQuery(jobdispatchsql);
                        if (tjobdispathclist != null && tjobdispathclist.size() > 0) {
                            for (TJobdispatchlistInfo tj : tjobdispathclist) {
                                if (tj.getStatus() != 70 || tj.getStatus() != 60) //将没有关闭的工单全部关闭
                                {
                                    tj.setStatus(70);
                                    tj.setRealEndtime(new Date());
                                    commonService.update(TJobdispatchlistInfo.class, tj);
                                }
                            }
                        }
                    }
                    //添加报废数据处理-20140725  end_2  ---------------------------------------------// 	

                    //更新作业计划
                    //跨月处理(1个月只有一个计划)
                    TJobplanInfo p_JobplanInfo = JobplanInfo.getTJobplanInfo();
                    if (p_JobplanInfo != null) {//如果作业计划为null，不在处理
                        //获取当前时间所在的月份
                        Calendar c = Calendar.getInstance();
                        int currentmonth = c.get(Calendar.MONTH);
                        //取父作业计划的实际开始时间
                        Calendar pcal = Calendar.getInstance();
                        pcal.setTime(p_JobplanInfo.getRealStarttime());
                        int pmonth = pcal.get(Calendar.MONTH);
                        if (pmonth == currentmonth)//当前月份等于父计划的月份
                        {
                            p_JobplanInfo.setFinishNum(p_JobplanInfo.getFinishNum() + 1);
                            int qualifiedNum1 = 0;
                            if (p_JobplanInfo.getQualifiedNum() != null)
                                qualifiedNum1 = p_JobplanInfo.getQualifiedNum();
                            p_JobplanInfo.setQualifiedNum(qualifiedNum1 + 1);
                            if (p_JobplanInfo.getFinishNum().intValue() == p_JobplanInfo.getPlanNum().intValue()) {
                                p_JobplanInfo.setStatus(70); //已完成
                                p_JobplanInfo.setRealEndtime(new Date()); //已完成
                            }
                            commonService.update(TJobplanInfo.class, p_JobplanInfo);
                        } else  //查找下个月份的作业计划
                        {
                            SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM");
                            // c.add(Calendar.MONTH,1); //取下一个月
                            String nextMonth = dayFormat.format(c.getTime());  //返回String型的时间

                            hql = " from TJobplanInfo t where t.planType=1 " +
                                    " and t.TPartTypeInfo.id=" + p_JobplanInfo.getTPartTypeInfo().getId() +
                                    " and DATE_FORMAT(t.realStarttime,'%Y-%m') = '" + nextMonth + "'";
                            List<TJobplanInfo> list = commonService.executeQuery(hql);
                            //可以找到下个月的作业计划
                            if (list != null && list.size() > 0) {
                                TJobplanInfo next_p_jobplanInfo = list.get(0);
                                next_p_jobplanInfo.setFinishNum(next_p_jobplanInfo.getFinishNum() + 1);
                                int qualifiedNum1 = 0;
                                if (next_p_jobplanInfo.getQualifiedNum() != null)
                                    qualifiedNum1 = next_p_jobplanInfo.getQualifiedNum();
                                next_p_jobplanInfo.setQualifiedNum(qualifiedNum1 + 1);
                                if (next_p_jobplanInfo.getFinishNum().intValue() == next_p_jobplanInfo.getPlanNum().intValue()) {
                                    next_p_jobplanInfo.setStatus(70); //已完成
                                    next_p_jobplanInfo.setRealEndtime(new Date()); //已完成
                                }
                                commonService.update(TJobplanInfo.class, next_p_jobplanInfo);
                            } else //没有找到下一个月的，就仍然加到当前月份
                            {
                                p_JobplanInfo.setFinishNum(p_JobplanInfo.getFinishNum() + 1);
                                int qualifiedNum1 = 0;
                                if (p_JobplanInfo.getQualifiedNum() != null)
                                    qualifiedNum1 = p_JobplanInfo.getQualifiedNum();
                                p_JobplanInfo.setQualifiedNum(qualifiedNum1 + 1);
                                if (p_JobplanInfo.getFinishNum().intValue() == p_JobplanInfo.getPlanNum().intValue()) {
                                    p_JobplanInfo.setStatus(70); //已完成
                                    p_JobplanInfo.setRealEndtime(new Date()); //已完成
                                }
                                commonService.update(TJobplanInfo.class, p_JobplanInfo);
                            }

                        }
                    } else  //如果没有对应的p_id,自动查找当月的作业计划
                    {
                        //获取当前时间所在的月份
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM");
                        //取当月作业计划
                        String currentMonth = dayFormat.format(c.getTime());  //返回String型的时间

                        hql = " from TJobplanInfo t where t.planType=1 " +
                                " and t.TPartTypeInfo.id=" + JobplanInfo.getTPartTypeInfo().getId() +
                                " and DATE_FORMAT(t.realStarttime,'%Y-%m') = '" + currentMonth + "'";
                        List<TJobplanInfo> list = commonService.executeQuery(hql);

                        if (list != null && list.size() > 0) {
                            TJobplanInfo p0_JobplanInfo = list.get(0); //取当月计划
                            p0_JobplanInfo.setFinishNum(p0_JobplanInfo.getFinishNum() + 1);
                            int qualifiedNum1 = 0;
                            if (p0_JobplanInfo.getQualifiedNum() != null)
                                qualifiedNum1 = p0_JobplanInfo.getQualifiedNum();
                            p0_JobplanInfo.setQualifiedNum(qualifiedNum1 + 1);
                            if (p0_JobplanInfo.getFinishNum().intValue() == p0_JobplanInfo.getPlanNum().intValue()) {
                                p0_JobplanInfo.setStatus(70); //已完成
                                p0_JobplanInfo.setRealEndtime(new Date()); //已完成
                            }
                            commonService.update(p0_JobplanInfo);
                        } //如果没有查找上一个月
                        else {
                            Calendar pcal = Calendar.getInstance();
                            pcal.add(Calendar.MONTH, -1); //取上一个月
                            String lastMonth = dayFormat.format(pcal.getTime());  //返回String型的时间

                            hql = " from TJobplanInfo t where t.planType=1 " +
                                    " and t.TPartTypeInfo.id=" + JobplanInfo.getTPartTypeInfo().getId() +
                                    " and DATE_FORMAT(t.realStarttime,'%Y-%m') = '" + lastMonth + "'";
                            List<TJobplanInfo> list0 = commonService.executeQuery(hql);
                            //可以找到上个月的作业计划
                            if (list0 != null && list0.size() > 0) {
                                TJobplanInfo last_p_jobplanInfo = list0.get(0);
                                last_p_jobplanInfo.setFinishNum(last_p_jobplanInfo.getFinishNum() + 1);
                                int qualifiedNum1 = 0;
                                if (last_p_jobplanInfo.getQualifiedNum() != null)
                                    qualifiedNum1 = last_p_jobplanInfo.getQualifiedNum();
                                last_p_jobplanInfo.setQualifiedNum(qualifiedNum1 + 1);
                                if (last_p_jobplanInfo.getFinishNum().intValue() == last_p_jobplanInfo.getPlanNum().intValue()) {
                                    last_p_jobplanInfo.setStatus(70); //已完成
                                    last_p_jobplanInfo.setRealEndtime(new Date()); //已完成
                                }
                                commonService.update(last_p_jobplanInfo);
                            }

                        }

                    }
                }
            }
        }
        LogHelper.log("--加工事件结束:", eventId + "");
        return true;
    }

    public Boolean processEquWorkEvent(Long eventId, String partNo) {
        if (null == eventId) return false;
        //加载实体		
        TUserEquWorkEvents tUserEquWorkEvent = commonService.get(TUserEquWorkEvents.class, eventId);
        if (tUserEquWorkEvent == null || StringUtils.isEmpty(tUserEquWorkEvent.getCuttingTask())) return false;
        //获取对应工单信息
        Collection<Parameter> parameters = new HashSet<Parameter>();
        parameters.add(new Parameter("no", tUserEquWorkEvent.getCuttingTask(), Operator.EQ));
        List<TJobdispatchlistInfo> temp = commonService.find(TJobdispatchlistInfo.class, (List<Sort>) null, parameters);
        if (temp != null && temp.size() > 0) {
            TJobdispatchlistInfo record = temp.get(0);

            LogHelper.log("--更新工单数量,更新前完成数：", record.getFinishNum() + "");

            record.setFinishNum(record.getFinishNum() + 1); //工单数量加 1			
            commonService.save(record);

            LogHelper.log("--更新工单数量,更新后完成数:", record.getFinishNum() + "");

//			Long jobplanId=record.getJobplanId();

            Long jobId = record.getTJobInfo().getId();

            //更新作业
            TJobInfo tJobInfo = commonService.get(TJobInfo.class, jobId);
            if (tJobInfo != null) {
                tJobInfo.setFinishNum(tJobInfo.getFinishNum() + 1);
                LogHelper.log("-----更新作业:", tJobInfo.getId().toString());
                commonService.update(tJobInfo);

                TPartEventInfo tpi = new TPartEventInfo();
                String partIdHql = "from TPartBasicInfo where no='" + partNo + "'";
                Collection<Parameter> parameters2 = new HashSet<Parameter>();
                TPartBasicInfo partBasicInfo = (TPartBasicInfo) dao.executeQuery(partIdHql, parameters2).get(0);//获取零件
                String partBasicInfoHql = "select new Map(" +
                        " p.id as processId,"
                        + " p.TProcessplanInfo.id as processplanId)" +
                        " from TJobInfo j,TProcessInfo p" +
                        " where j.TProcessInfo.id=p.id " +
                        " and j.id=" + record.getTJobInfo().getId();
                Map<String, Object> process = (Map<String, Object>) dao.executeQuery(partBasicInfoHql, parameters2).get(0);//获取工序id

                tpi.setProcessID(Long.parseLong(process.get("processId") == null ? "" : process.get("processId").toString()));
                tpi.setPartId(partBasicInfo.getId());
                tpi.setEventId(eventId);
                tpi.setDispatchNo(record.getNo());
                tpi.setStatus(301);//301成功  302失败
                commonService.save(tpi);//保存  TPartEventInfo

                String ProcessInfoHql = "from TProcessInfo where TProcessplanInfo.id='" + process.get("processplanId") + "' "
                        + " order by processOrder";
                List<TProcessInfo> processInfoList = dao.executeQuery(ProcessInfoHql, parameters2); //查询工艺方案对应得工序
                int tt = 0;
                for (int i = 0; i < processInfoList.size(); i++) {//查找下一个工序
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
            map.put("partNo", record.get("partNo"));  //产品个体
            map.put("equSerialNo", record.get("equ_serialNo"));
            map.put("processID", record.get("processID")); //工序	
            map.put("eventID", record.get("eventID"));//加工时间ID

            set.add(record.get("partNo").toString());
            //int rank=(Integer)record.get("rank");

            int workTime = (Integer) record.get("workTime");//加工时间     t加
            int cuttingTime = (Integer) record.get("cuttingTime");//切削时间 t切
            int dryRunning = workTime - cuttingTime; //空运行时间   t空
            double salarypersec = 0;
            if (!StringUtils.isEmpty(record.get("salary") + ""))
                salarypersec = Double.valueOf(record.get("salary").toString()); //  k人

            double proacceCost = 0;
            if (!StringUtils.isEmpty(record.get("proacceCost") + ""))
                proacceCost = Double.valueOf(record.get("proacceCost").toString());  // 加工时间辅料因数  k加辅

            double preacceCost = 0;
            if (!StringUtils.isEmpty(record.get("preacceCost") + ""))
                preacceCost = Double.valueOf(record.get("preacceCost").toString());   // 准备时间辅料因数  k准辅

            double depreciationCost = 0;
            if (!StringUtils.isEmpty(record.get("depreciationCost") + ""))
                depreciationCost = Double.valueOf(record.get("depreciationCost").toString()); //机床折旧率   k折

            double prepareCost = 0;
            if (!StringUtils.isEmpty(record.get("prepareCost") + ""))
                prepareCost = Double.valueOf(record.get("prepareCost").toString()); //K准

            double cuttingCost = 0;
            if (!StringUtils.isEmpty(record.get("cuttingCost") + ""))
                cuttingCost = Double.valueOf(record.get("cuttingCost").toString()); //K切

            double dryRunningCost = 0;
            if (!StringUtils.isEmpty(record.get("dryRunningCost") + ""))
                dryRunningCost = Double.valueOf(record.get("dryRunningCost").toString());//K空

            //主料
            double materialPrice = 0;
            if (!StringUtils.isEmpty(record.get("materialPrice") + ""))
                materialPrice = Double.valueOf(record.get("materialPrice").toString()); //主材料单价

            double reqNum = 0;
            if (!StringUtils.isEmpty(record.get("reqNum") + ""))
                reqNum = Double.valueOf(record.get("reqNum").toString());//需求数量

            String reqType = (String) record.get("reqType"); //需求类别

            long preparttime = 0; //准备时间   t准

            if (seq != 1 && i > 0) {

                Map<String, Object> record_last = dataList.get(i - 1);

                if (record.get("processID").toString().equals(record_last.get("processID").toString()))  //准备时间：加工事件表中的选中一条的开始时间和他上一条的结束时间做减法，差值与工序表中的装夹时间作比较，超出则作废
                {
                    seq++;
                    long between = StringUtils.dateTimeBetween(record_last.get("finishtime").toString(), record.get("starttime").toString());
                    int installtime = (Integer) record.get("installtime"); //装夹时间
                    if (between < installtime && between > 0) preparttime = between;
                    else preparttime = installtime;
                } else {
                    seq = 1;
                }
            } else {
                seq++;
                String installtime = record.get("installtime") + "";
                if (!StringUtils.isEmpty(installtime))
                    preparttime = (Integer) record.get("installtime"); //装夹时间
                else preparttime = 0;

            }

            //每个工序的人工
            double peopleCost = (preparttime + workTime) * salarypersec;
            map.put("peopleCost", peopleCost);
            //每个工序的主料
            double mainMaterialCost = 0;
            if (!"前序物料".equals(reqType)) mainMaterialCost = materialPrice * reqNum;
            map.put("mainMaterialCost", mainMaterialCost);
            //辅料
            double accMaterialCost = preparttime * preacceCost + workTime * proacceCost;
            map.put("accMaterialCost", accMaterialCost);
            //折旧
            double oldCost = (preparttime + workTime) * depreciationCost;
            map.put("oldCost", oldCost);
            //能源(机台)
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
                    //能源成本汇总
                    total_energyCost_cutting += Double.valueOf(rec.get("energyCost_cutting").toString());
                    total_energyCost_dryRunning += Double.valueOf(rec.get("energyCost_dryRunning").toString());
                    total_energyCost_prepare += Double.valueOf(rec.get("energyCost_prepare").toString());
                    //分项成本
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
        List<String> nodeIdList = new ArrayList<String>();// 当前节点包括子节点的id
        nodeIdList = getNodesAllId(nodeId);// 递归获取前节点id和子节点的id
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
            map.put("partNo", record.get("partNo"));  //产品个体
            map.put("equSerialNo", record.get("equ_serialNo"));
            map.put("processID", record.get("processID")); //工序	
            map.put("eventID", record.get("eventID"));//加工时间ID
            map.put("partTypeNo", record.get("parttypeNo"));// 产品型号

            set.add(record.get("partNo").toString());

            int workTime = (Integer) record.get("workTime");//加工时间     t加
            int cuttingTime = (Integer) record.get("cuttingTime");//切削时间 t切
            int dryRunning = workTime - cuttingTime; //空运行时间   t空
            double salarypersec = 0;
            if (!StringUtils.isEmpty(record.get("salary") + ""))
                salarypersec = Double.valueOf(record.get("salary").toString()); //  k人

            double proacceCost = 0;
            if (!StringUtils.isEmpty(record.get("proacceCost") + ""))
                proacceCost = Double.valueOf(record.get("proacceCost").toString());  // 加工时间辅料因数  k加辅

            double preacceCost = 0;
            if (!StringUtils.isEmpty(record.get("preacceCost") + ""))
                preacceCost = Double.valueOf(record.get("preacceCost").toString());   // 准备时间辅料因数  k准辅

            double depreciationCost = 0;
            if (!StringUtils.isEmpty(record.get("depreciationCost") + ""))
                depreciationCost = Double.valueOf(record.get("depreciationCost").toString()); //机床折旧率   k折

            double prepareCost = 0;
            if (!StringUtils.isEmpty(record.get("prepareCost") + ""))
                prepareCost = Double.valueOf(record.get("prepareCost").toString()); //K准

            double cuttingCost = 0;
            if (!StringUtils.isEmpty(record.get("cuttingCost") + ""))
                cuttingCost = Double.valueOf(record.get("cuttingCost").toString()); //K切

            double dryRunningCost = 0;
            if (!StringUtils.isEmpty(record.get("dryRunningCost") + ""))
                dryRunningCost = Double.valueOf(record.get("dryRunningCost").toString());//K空

            //主料
            double materialPrice = 0;
            if (!StringUtils.isEmpty(record.get("materialPrice") + ""))
                materialPrice = Double.valueOf(record.get("materialPrice").toString()); //主材料单价

            double reqNum = 0;
            if (!StringUtils.isEmpty(record.get("reqNum") + ""))
                reqNum = Double.valueOf(record.get("reqNum").toString());//需求数量

            String reqType = (String) record.get("reqType"); //需求类别

            long preparttime = 0; //准备时间   t准

            if (seq != 1 && i > 0) {

                Map<String, Object> record_last = dataList.get(i - 1);

                if (record.get("processID").toString().equals(record_last.get("processID").toString()))  //准备时间：加工事件表中的选中一条的开始时间和他上一条的结束时间做减法，差值与工序表中的装夹时间作比较，超出则作废
                {
                    seq++;
                    long between = StringUtils.dateTimeBetween(record_last.get("finishtime").toString(), record.get("starttime").toString());
                    int installtime = (Integer) record.get("installtime"); //装夹时间
                    if (between < installtime && between > 0) preparttime = between;
                    else preparttime = installtime;
                } else {
                    seq = 1;
                }
            } else {
                seq++;
                String installtime = record.get("installtime") + "";
                if (!StringUtils.isEmpty(installtime))
                    preparttime = (Integer) record.get("installtime"); //装夹时间
                else preparttime = 0;

            }

            //每个工序的人工
            double peopleCost = (preparttime + workTime) * salarypersec;
            map.put("peopleCost", peopleCost);
            //每个工序的主料
            double mainMaterialCost = 0;
            if (!"前序物料".equals(reqType)) mainMaterialCost = materialPrice * reqNum;
            map.put("mainMaterialCost", mainMaterialCost);
            //辅料
            double accMaterialCost = preparttime * preacceCost + workTime * proacceCost;
            map.put("accMaterialCost", accMaterialCost);
            //折旧
            double oldCost = (preparttime + workTime) * depreciationCost;
            map.put("oldCost", oldCost);
            //能源(机台)
            double energyCost = preparttime * prepareCost + cuttingTime * cuttingCost + dryRunning * dryRunningCost;
            map.put("energyCost", energyCost);
            map.put("energyCost_prepare", StringUtils.doubleConvertFormat(preparttime * prepareCost));
            map.put("energyCost_cutting", StringUtils.doubleConvertFormat(cuttingTime * cuttingCost));
            map.put("energyCost_dryRunning", StringUtils.doubleConvertFormat(dryRunning * dryRunningCost));
            tempList.add(map);
        }

        //set 转 list 排序
        List<String> partNolist = new ArrayList<String>(set);
        Collections.sort(partNolist, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        //计算理论成本
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

            int workTime = (Integer) record.get("workTime");//加工时间     t加
            int cuttingTime = (Integer) record.get("cuttingTime");//切削时间 t切
            int dryRunning = workTime - cuttingTime; //空运行时间   t空				

            double prepareCost = 0;
            if (!StringUtils.isEmpty(record.get("prepareCost") + ""))
                prepareCost = Double.valueOf(record.get("prepareCost").toString()); //K准

            double cuttingCost = 0;
            if (!StringUtils.isEmpty(record.get("cuttingCost") + ""))
                cuttingCost = Double.valueOf(record.get("cuttingCost").toString()); //K切

            double dryRunningCost = 0;
            if (!StringUtils.isEmpty(record.get("dryRunningCost") + ""))
                dryRunningCost = Double.valueOf(record.get("dryRunningCost").toString());//K空			


            long preparttime = 0; //准备时间   t准

            if (seq != 1 && i > 0) {

                Map<String, Object> record_last = dataList.get(i - 1);

                if (record.get("processID").toString().equals(record_last.get("processID").toString()))  //准备时间：加工事件表中的选中一条的开始时间和他上一条的结束时间做减法，差值与工序表中的装夹时间作比较，超出则作废
                {
                    seq++;
                    long between = StringUtils.dateTimeBetween(record_last.get("finishtime").toString(), record.get("starttime").toString());
                    int installtime = (Integer) record.get("installtime"); //装夹时间
                    if (between < installtime && between > 0) preparttime = between;
                    else preparttime = installtime;
                } else {
                    seq = 1;
                }
            } else {
                seq++;
                String installtime = record.get("installtime") + "";
                if (!StringUtils.isEmpty(installtime))
                    preparttime = (Integer) record.get("installtime"); //装夹时间
                else preparttime = 0;

            }
            //能源(机台)
            energyCost += preparttime * prepareCost + cuttingTime * cuttingCost + dryRunning * dryRunningCost;
        }
        return StringUtils.doubleConvertFormat(energyCost);
    }


    /**
     * 获取前节点id和子节点的id
     *
     * @param nodeId 节点id
     * @return List<String>
     */
    public List<String> getNodesAllId(String nodeId) {
        String hql = " select nodeId from TNodes t where t.TNodes.nodeId='" + nodeId + "'";
        return dao.executeQuery(hql);
    }

}

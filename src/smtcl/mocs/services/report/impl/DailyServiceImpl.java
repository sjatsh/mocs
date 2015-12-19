package smtcl.mocs.services.report.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;

import smtcl.mocs.services.report.IDailyService;

public class DailyServiceImpl extends GenericServiceSpringImpl<Object, String>
		implements IDailyService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * smtcl.mocs.services.cjs.imp.IDailyService#getProductionDailyReport(java
	 * .lang.String)
	 */
	
	
	@Override
	public List<Map<String, Object>> getProductionDailyReport(String time) {
		
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		/*List<Map<String, Object>> list1=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list2=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list3=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list4=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list5=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list6=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list7=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list8=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list9=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> list10=new ArrayList<Map<String,Object>>();*/
		//String sqltest="SELECT tp.name as orderNo from t_part_type_info tp";
		//月投料计划
		String sql1 = "SELECT tp.name as modelNo1,SUM(tj.planNum) as monthFeedPlan "
				+ "from t_part_type_info tp,t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.plan_starttime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.plan_starttime<='"+time+"'"
				+ " GROUP BY modelNo1";
		//list1=dao.executeNativeQuery(sql1);
		//投料产量 今日之前，今日，累计
		String sql2="SELECT tp.name as modelNo1,SUM(tj.planNum) as yieldBefore "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_starttime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.real_starttime<'"+time+"'"
				+ "GROUP BY modelNo1";
		//list2=dao.executeNativeQuery(sql2);
		//投料产量 今日
		String sql3="SELECT tp.name as modelNo1,SUM(tj.planNum) as yieldToday "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_starttime='"+time+"'"
				+ "GROUP BY modelNo1";
		//list3=dao.executeNativeQuery(sql3);
		//投料产量 累计
		String sql4="SELECT tp.name as modelNo1,SUM(tj.planNum) as yieldSum "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_starttime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.real_starttime<='"+time+"'"
				+ "GROUP BY modelNo1";
		//list4=dao.executeNativeQuery(sql4);
		//月提交计划
		String sql5 = "SELECT tp.name as modelNo1,SUM(tj.planNum) as monthSubmitPlan "
				+ "from t_part_type_info tp,t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.plan_endtime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.plan_endtime<='"+time+"'"
				+ " GROUP BY modelNo1 ";
		//list5=dao.executeNativeQuery(sql5);
		//提交产量 今日之前
		String sql6="SELECT tp.name as modelNo1,SUM(tj.finishNum) as submitBefore "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_endtime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.real_endtime<'"+time+"'"
				+ "GROUP BY modelNo1";
		//list6=dao.executeNativeQuery(sql6);
		//提交产量 今日
		String sql7="SELECT tp.name as modelNo1,SUM(tj.finishNum) as submitToday "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_endtime='"+time+"'"
				+ "GROUP BY modelNo1";
		//list7=dao.executeNativeQuery(sql7);
		//提交产量 累计
		String sql8="SELECT tp.name as modelNo1,SUM(tj.finishNum) as submitSum "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_endtime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.real_endtime<='"+time+"'"
				+ "GROUP BY modelNo1";
		//list8=dao.executeNativeQuery(sql8);
		//%按本月累计投料数/投料计划数计算的百分比
		String sql9="SELECT a.modelNo1 as modelNo1, "
				+ "cast(a.yieldSum/b.monthFeedPlan/10 as decimal(10,2)) as feedPercent "
				+ "from ("+sql4+")a, "
				+ "("+sql1+")b "
				+ "where a.modelNo1=b.modelNo1";
		//list9=dao.executeNativeQuery(sql9);
		String sql10="SELECT a.modelNo1 as modelNo1, "
				+ "cast(a.submitSum/b.monthSubmitPlan/10 as decimal(10,2)) as sumbitPercent "
				+ "from ("+sql8+")a, "
				+ "("+sql5+")b "
				+ "where a.modelNo1=b.modelNo1";
		//list10=dao.executeNativeQuery(sql10);
		//合~~~~~~~
				
		String sqlA="SELECT aa.modelNo1 as modelNo1,"
				+ "aa.monthFeedPlan as monthFeedPlan,"
				+ "(case when bb.yieldBefore is null then 0 else bb.yieldBefore end)yieldBefore,"
				+ "(case when c.yieldToday is null then 0 else c.yieldToday end)yieldToday,"
				+ "(case when d.yieldSum is null then 0 else d.yieldSum end)yieldSum,"
				+ "(case when e.monthSubmitPlan is null then 0 else e.monthSubmitPlan end)monthSubmitPlan,"
				+ "(case when f.submitBefore is null then 0 else f.submitBefore end)submitBefore,"
				+ "(case when g.submitToday is null then 0 else g.submitToday end)submitToday,"
				+ "(case when h.submitSum is null then 0 else h.submitSum end)submitSum,"
				+ "(case when i.feedPercent is null then 0 else i.feedPercent end)feedPercent,"
				+ "(case when j.sumbitPercent is null then 0 else j.sumbitPercent end)sumbitPercent "
				+ "from "
				+ "("+sql1+") as aa "
				+ "left join ("+sql2+") as bb on aa.modelNo1=bb.modelNo1 "
				+ "left join ("+sql3+") as c on aa.modelNo1=c.modelNo1 "
				+ "left join ("+sql4+") as d on aa.modelNo1=d.modelNo1 "
				+ "left join ("+sql5+") as e on aa.modelNo1=e.modelNo1 "
				+ "left join ("+sql6+") as f on aa.modelNo1=f.modelNo1 "
				+ "left join ("+sql7+") as g on aa.modelNo1=g.modelNo1 "
				+ "left join ("+sql8+") as h on aa.modelNo1=h.modelNo1 "
				+ "left join ("+sql9+") as i on aa.modelNo1=i.modelNo1 "
				+ "left join ("+sql10+") as j on aa.modelNo1=j.modelNo1";

		list=dao.executeNativeQuery(sqlA);
		
		
		//序号列添加；
		int i=1;
		String model;
		String part;
		for (Map<String, Object> map : list) {
					map.put("orderNo",i );
					i++;
				}
		//切割字段"-"
		for (Map<String, Object> map2 : list) {
			if(map2.get("modelNo1").toString()!=null){
				String temp[]=map2.get("modelNo1").toString().split("-");
				model=temp[0];
				if(temp.length>1){
					part=temp[1];
					map2.put("partNo", part);
				}
				else{
					map2.put("partNo", "");
				}
				map2.put("modelNo", model);
			}
			
		}
		
		return list;
	}

	@Override
	public List<Map<String, Object>> getAmountData(String time) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		String sql1 = "SELECT tp.name as modelNo1,SUM(tj.planNum) as monthFeedPlan "
				+ "from t_part_type_info tp,t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.plan_starttime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.plan_starttime<='"+time+"'"
				+ " GROUP BY modelNo1";
		//投料产量 今日之前，今日，累计
		String sql2="SELECT tp.name as modelNo1,SUM(tj.planNum) as yieldBefore "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_starttime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.real_starttime<'"+time+"'"
				+ "GROUP BY modelNo1";
		//投料产量 今日
		String sql3="SELECT tp.name as modelNo1,SUM(tj.planNum) as yieldToday "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_starttime='"+time+"'"
				+ "GROUP BY modelNo1";
		//投料产量 累计
		String sql4="SELECT tp.name as modelNo1,SUM(tj.planNum) as yieldSum "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_starttime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.real_starttime<='"+time+"'"
				+ "GROUP BY modelNo1";
		//list4=dao.executeNativeQuery(sql4);
		//月提交计划
		String sql5 = "SELECT tp.name as modelNo1,SUM(tj.planNum) as monthSubmitPlan "
				+ "from t_part_type_info tp,t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.plan_endtime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.plan_endtime<='"+time+"'"
				+ " GROUP BY modelNo1 ";
		//list5=dao.executeNativeQuery(sql5);
		//提交产量 今日之前
		String sql6="SELECT tp.name as modelNo1,SUM(tj.finishNum) as submitBefore "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_endtime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.real_endtime<'"+time+"'"
				+ "GROUP BY modelNo1";
		//提交产量 今日
		String sql7="SELECT tp.name as modelNo1,SUM(tj.finishNum) as submitToday "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_endtime='"+time+"'"
				+ "GROUP BY modelNo1";
		//提交产量 累计
		String sql8="SELECT tp.name as modelNo1,SUM(tj.finishNum) as submitSum "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_endtime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.real_endtime<='"+time+"'"
				+ "GROUP BY modelNo1";
		//%按本月累计投料数/投料计划数计算的百分比
		String sql9="SELECT a.modelNo1 as modelNo1, "
				+ "cast(a.yieldSum/b.monthFeedPlan/10 as decimal(10,2)) as feedPercent "
				+ "from ("+sql4+")a, "
				+ "("+sql1+")b "
				+ "where a.modelNo1=b.modelNo1";
		String sql10="SELECT a.modelNo1 as modelNo1, "
				+ "cast(a.submitSum/b.monthSubmitPlan/10 as decimal(10,2)) as sumbitPercent "
				+ "from ("+sql8+")a, "
				+ "("+sql5+")b "
				+ "where a.modelNo1=b.modelNo1";
		String sqlA="SELECT aa.modelNo1 as modelNo1,"
				+ "aa.monthFeedPlan as monthFeedPlan,"
				+ "(case when bb.yieldBefore is null then 0 else bb.yieldBefore end)yieldBefore,"
				+ "(case when c.yieldToday is null then 0 else c.yieldToday end)yieldToday,"
				+ "(case when d.yieldSum is null then 0 else d.yieldSum end)yieldSum,"
				+ "(case when e.monthSubmitPlan is null then 0 else e.monthSubmitPlan end)monthSubmitPlan,"
				+ "(case when f.submitBefore is null then 0 else f.submitBefore end)submitBefore,"
				+ "(case when g.submitToday is null then 0 else g.submitToday end)submitToday,"
				+ "(case when h.submitSum is null then 0 else h.submitSum end)submitSum,"
				+ "(case when i.feedPercent is null then 0 else i.feedPercent end)feedPercent,"
				+ "(case when j.sumbitPercent is null then 0 else j.sumbitPercent end)sumbitPercent "
				+ "from "
				+ "("+sql1+") as aa "
				+ "left join ("+sql2+") as bb on aa.modelNo1=bb.modelNo1 "
				+ "left join ("+sql3+") as c on aa.modelNo1=c.modelNo1 "
				+ "left join ("+sql4+") as d on aa.modelNo1=d.modelNo1 "
				+ "left join ("+sql5+") as e on aa.modelNo1=e.modelNo1 "
				+ "left join ("+sql6+") as f on aa.modelNo1=f.modelNo1 "
				+ "left join ("+sql7+") as g on aa.modelNo1=g.modelNo1 "
				+ "left join ("+sql8+") as h on aa.modelNo1=h.modelNo1 "
				+ "left join ("+sql9+") as i on aa.modelNo1=i.modelNo1 "
				+ "left join ("+sql10+") as j on aa.modelNo1=j.modelNo1";
	//按大类合计  在此处将t_part_class_info 改为物料表		
		
		String amountsql2="SELECT mc.m_classname as partClassName,"
				+ "sum(ym.monthFeedPlan) as sumMonthFeedPlan, "
				+ "sum(ym.yieldBefore) as sumYieldBefore,"
				+ "sum(ym.yieldToday) as sumYieldToday,"
				+ "sum(ym.yieldSum) as sumYieldSum,"
				+ "sum(ym.monthSubmitPlan) as sumMonthSubmitPlan,"
				+ "sum(ym.submitBefore) as sumSubmitBefore,"
				+ "sum(ym.submitToday) as sumSubmitToday,"
				+ "sum(ym.submitSum) as sumSubmitSum,"
				+ "sum(ym.feedPercent) as sumFeedPercent,"
				+ "sum(ym.sumbitPercent) as sumSumbitPercent "
				+ "from ("+sqlA+") as ym,"
				+ "t_part_type_info as pt,"
				+ "t_materail_type_info as mt,"
				+ "t_material_class as mc "
				+ "where ym.modelNo1=pt.name and "
				+ "pt.no=mt.no and "
				+ "mt.p_id=mc.m_classid "
				+ "group by partClassName";
	/*	String amountsql="SELECT pc.name as partClassName,"
				+ "sum(ym.monthFeedPlan) as sumMonthFeedPlan, "
				+ "sum(ym.yieldBefore) as sumYieldBefore,"
				+ "sum(ym.yieldToday) as sumYieldToday,"
				+ "sum(ym.yieldSum) as sumYieldSum,"
				+ "sum(ym.monthSubmitPlan) as sumMonthSubmitPlan,"
				+ "sum(ym.submitBefore) as sumSubmitBefore,"
				+ "sum(ym.submitToday) as sumSubmitToday,"
				+ "sum(ym.submitSum) as sumSubmitSum,"
				+ "sum(ym.feedPercent) as sumFeedPercent,"
				+ "sum(ym.sumbitPercent) as sumSumbitPercent "
				+ "from ("+sqlA+") as ym,"
				+ "t_part_class_info as pc,"
				+ "t_part_type_info as pt "
				+ "where ym.modelNo1=pt.name and "
				+ "pt.typeNo=pc.no "
				+ "group by partClassName";*/
		list=dao.executeNativeQuery(amountsql2);
		return list;
	}

	@Override
	public List<Map<String, Object>> getTotalData(String time) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		String sql1 = "SELECT tp.name as modelNo1,SUM(tj.planNum) as monthFeedPlan "
				+ "from t_part_type_info tp,t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.plan_starttime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.plan_starttime<='"+time+"'"
				+ " GROUP BY modelNo1";
		//投料产量 今日之前，今日，累计
		String sql2="SELECT tp.name as modelNo1,SUM(tj.planNum) as yieldBefore "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_starttime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.real_starttime<'"+time+"'"
				+ "GROUP BY modelNo1";
		//投料产量 今日
		String sql3="SELECT tp.name as modelNo1,SUM(tj.planNum) as yieldToday "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_starttime='"+time+"'"
				+ "GROUP BY modelNo1";
		//投料产量 累计
		String sql4="SELECT tp.name as modelNo1,SUM(tj.planNum) as yieldSum "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_starttime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.real_starttime<='"+time+"'"
				+ "GROUP BY modelNo1";
		//list4=dao.executeNativeQuery(sql4);
		//月提交计划
		String sql5 = "SELECT tp.name as modelNo1,SUM(tj.planNum) as monthSubmitPlan "
				+ "from t_part_type_info tp,t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.plan_endtime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.plan_endtime<='"+time+"'"
				+ " GROUP BY modelNo1 ";
		//list5=dao.executeNativeQuery(sql5);
		//提交产量 今日之前
		String sql6="SELECT tp.name as modelNo1,SUM(tj.finishNum) as submitBefore "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_endtime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.real_endtime<'"+time+"'"
				+ "GROUP BY modelNo1";
		//提交产量 今日
		String sql7="SELECT tp.name as modelNo1,SUM(tj.finishNum) as submitToday "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_endtime='"+time+"'"
				+ "GROUP BY modelNo1";
		//提交产量 累计
		String sql8="SELECT tp.name as modelNo1,SUM(tj.finishNum) as submitSum "
				+ "from t_part_type_info tp, "
				+ "t_jobplan_info tj "
				+ "WHERE tj.partID=tp.ID AND "
				+ "tj.real_endtime>= "
				+ "(SELECT CONCAT(date_format('"+time+"','%Y-%m-'),'01')) AND "
				+ "tj.real_endtime<='"+time+"'"
				+ "GROUP BY modelNo1";
		//%按本月累计投料数/投料计划数计算的百分比
		String sql9="SELECT a.modelNo1 as modelNo1, "
				+ "cast(a.yieldSum/b.monthFeedPlan/10 as decimal(10,2)) as feedPercent "
				+ "from ("+sql4+")a, "
				+ "("+sql1+")b "
				+ "where a.modelNo1=b.modelNo1";
		String sql10="SELECT a.modelNo1 as modelNo1, "
				+ "cast(a.submitSum/b.monthSubmitPlan/10 as decimal(10,2)) as sumbitPercent "
				+ "from ("+sql8+")a, "
				+ "("+sql5+")b "
				+ "where a.modelNo1=b.modelNo1";
		String sqlA="SELECT aa.modelNo1 as modelNo1,"
				+ "aa.monthFeedPlan as monthFeedPlan,"
				+ "(case when bb.yieldBefore is null then 0 else bb.yieldBefore end)yieldBefore,"
				+ "(case when c.yieldToday is null then 0 else c.yieldToday end)yieldToday,"
				+ "(case when d.yieldSum is null then 0 else d.yieldSum end)yieldSum,"
				+ "(case when e.monthSubmitPlan is null then 0 else e.monthSubmitPlan end)monthSubmitPlan,"
				+ "(case when f.submitBefore is null then 0 else f.submitBefore end)submitBefore,"
				+ "(case when g.submitToday is null then 0 else g.submitToday end)submitToday,"
				+ "(case when h.submitSum is null then 0 else h.submitSum end)submitSum,"
				+ "(case when i.feedPercent is null then 0 else i.feedPercent end)feedPercent,"
				+ "(case when j.sumbitPercent is null then 0 else j.sumbitPercent end)submitPercent "
				+ "from "
				+ "("+sql1+") as aa "
				+ "left join ("+sql2+") as bb on aa.modelNo1=bb.modelNo1 "
				+ "left join ("+sql3+") as c on aa.modelNo1=c.modelNo1 "
				+ "left join ("+sql4+") as d on aa.modelNo1=d.modelNo1 "
				+ "left join ("+sql5+") as e on aa.modelNo1=e.modelNo1 "
				+ "left join ("+sql6+") as f on aa.modelNo1=f.modelNo1 "
				+ "left join ("+sql7+") as g on aa.modelNo1=g.modelNo1 "
				+ "left join ("+sql8+") as h on aa.modelNo1=h.modelNo1 "
				+ "left join ("+sql9+") as i on aa.modelNo1=i.modelNo1 "
				+ "left join ("+sql10+") as j on aa.modelNo1=j.modelNo1";
		
		String totalsql="SELECT "
				+ "sum(ym.monthFeedPlan) as totalMonthFeedPlan,"
				+ "sum(ym.yieldBefore) as totalYieldBefore,"
				+ "sum(ym.yieldToday) as totalYieldToday,"
				+ "sum(ym.yieldSum) as totalYieldSum,"
				+ "sum(ym.monthSubmitPlan) as totalMonthSubmitPlan,"
				+ "sum(ym.submitBefore) as totalSubmitBefore,"
				+ "sum(ym.submitToday) as totalSubmitToday,"
				+ "sum(ym.submitSum) as totalSubmitSum "
				//+ "sum(ym.feedPercent) as totalFeedPercent,"
				//+ "sum(ym.submitPercent) as totalSubmitPercent "
				+ "from ("+sqlA+")ym";
		
		//%     %
		String totalsql2="SELECT t1.totalMonthFeedPlan as totalMonthFeedPlan,"
				+ "t1.totalYieldBefore as totalYieldBefore,"
				+ "t1.totalYieldToday as totalYieldToday,"
				+ "t1.totalYieldSum as totalYieldSum,"
				+ "t1.totalMonthSubmitPlan as totalMonthSubmitPlan,"
				+ "t1.totalSubmitBefore as totalSubmitBefore,"
				+ "t1.totalSubmitToday as totalSubmitToday,"
				+ "t1.totalSubmitSum as totalSubmitSum,"
				+ "cast(t1.totalYieldSum/t1.totalMonthFeedPlan/10 as decimal(10,2)) as totalFeedPercent,"
				+ "(case when t1.totalSubmitSum/t1.totalMonthSubmitPlan/10 is null "
				+ "then 0.00 else cast(t1.totalSubmitSum/t1.totalMonthSubmitPlan/10 as decimal(10,2)) end)totalSubmitPercent "
				//+ "cast(t1.totalSubmitSum/t1.totalMonthSubmitPlan/10 as decimal(10,2)) as totalSubmitPercent "
				+ "from ("+totalsql+") as t1";
			list=dao.executeNativeQuery(totalsql2);
		
			for (Map<String, Object> map : list) {
				if(map.get("totalMonthFeedPlan")!=null){
					map.put("total", "合计");
				}
				else{
					return null;
				}
			}
		
		return list;
	}
}

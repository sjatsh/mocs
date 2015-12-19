package smtcl.mocs.services.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IMonthPlanService {
	
	//总表头信息
	List<Map<String, Object>> getTotalTableHeadData(String number, String search,Date searchTime);
	//所有大类类名以及表头信息
	List<Map<String, Object>> getPartClassAndHeadData( String number, String search,Date searchTime);
	//每个大类的表体信息
	List<Map<String, Object>> getTypeData(String number, String search,Date searchTime, int partClass);
	
	//产能周计算公式
	public void productivityWeek(Date searchTime);
	
	//获取五个时间节点
	public Date[] getProductivityWeek();
	
	//获取四个产能周
	public String[] getDelivery();
	
	//投料 	     提交	  过热		产能周       对应总和查询
	double totalFeeding(Date search, int partClass, String type);
	double totalSubmit(Date search, int partClass, String type);
	double totalOvearHeat(Date search, int partClass, String type);
	double totalProductivityWeek(String number, String search, Date start, Date end, int partClass, String partNo);
	
	//投料 	     提交	  过热		产能周       对应种类数查询
	int totalType(Date search, int partClass);
	
	//获取所有零件大类名
	List<Map<String, Object>> getAllPartClass(String search, Date searchTime);
	
	 
	
}

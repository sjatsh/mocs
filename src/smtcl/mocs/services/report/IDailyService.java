package smtcl.mocs.services.report;


import java.util.List;
import java.util.Map;

public interface IDailyService {

	//根据日期查成产日报表信息
	public  List<Map<String, Object>> getProductionDailyReport(
			String time);
	public  List<Map<String, Object>> getAmountData(
			String time);
	List<Map<String, Object>> getTotalData(String time);

}
package smtcl.mocs.services.report;


import java.util.List;
import java.util.Map;

public interface IDailyService {

	//�������ڲ�ɲ��ձ�����Ϣ
	public  List<Map<String, Object>> getProductionDailyReport(
			String time);
	public  List<Map<String, Object>> getAmountData(
			String time);
	List<Map<String, Object>> getTotalData(String time);

}
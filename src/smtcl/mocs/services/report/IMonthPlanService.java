package smtcl.mocs.services.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IMonthPlanService {
	
	//�ܱ�ͷ��Ϣ
	List<Map<String, Object>> getTotalTableHeadData(String number, String search,Date searchTime);
	//���д��������Լ���ͷ��Ϣ
	List<Map<String, Object>> getPartClassAndHeadData( String number, String search,Date searchTime);
	//ÿ������ı�����Ϣ
	List<Map<String, Object>> getTypeData(String number, String search,Date searchTime, int partClass);
	
	//�����ܼ��㹫ʽ
	public void productivityWeek(Date searchTime);
	
	//��ȡ���ʱ��ڵ�
	public Date[] getProductivityWeek();
	
	//��ȡ�ĸ�������
	public String[] getDelivery();
	
	//Ͷ�� 	     �ύ	  ����		������       ��Ӧ�ܺͲ�ѯ
	double totalFeeding(Date search, int partClass, String type);
	double totalSubmit(Date search, int partClass, String type);
	double totalOvearHeat(Date search, int partClass, String type);
	double totalProductivityWeek(String number, String search, Date start, Date end, int partClass, String partNo);
	
	//Ͷ�� 	     �ύ	  ����		������       ��Ӧ��������ѯ
	int totalType(Date search, int partClass);
	
	//��ȡ�������������
	List<Map<String, Object>> getAllPartClass(String search, Date searchTime);
	
	 
	
}

package smtcl.mocs.services.device;

import java.util.List;
import java.util.Map;

/**
 * ���������
 * @author songkaiang
 *
 */
public interface IProcessService {

	/**
	 * ͨ��������ƻ�ȡ����������Ϣ
	 * @param partTypeName �����Ϣ
	 * @param nodeid
	 * @return
	 */
	public List<Map<String, Object>> getProcessInfo(String partTypeName, String nodeid);
	
	/**
	 * ͨ������ţ���ȡ��׼
	 */
	public String getBatchNoByNo(String no);
}

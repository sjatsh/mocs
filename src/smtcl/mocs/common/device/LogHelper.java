package smtcl.mocs.common.device;

import org.apache.log4j.Logger;

/**
 * ��־������
 * @���ߣ�yutao
 * @����ʱ�䣺2013-04-26
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
public class LogHelper {
	
	private static Logger logger = Logger.getLogger("log4j.logger.Businesslog");

	public static void log(String paramString1, String paramString2)
	  {
		  logger.info("method:"+paramString1+" params:"+paramString2);
	  }	
}

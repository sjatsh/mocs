package smtcl.mocs.common.device;

import org.apache.log4j.Logger;

/**
 * 日志工具类
 * @作者：yutao
 * @创建时间：2013-04-26
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
public class LogHelper {
	
	private static Logger logger = Logger.getLogger("log4j.logger.Businesslog");

	public static void log(String paramString1, String paramString2)
	  {
		  logger.info("method:"+paramString1+" params:"+paramString2);
	  }	
}

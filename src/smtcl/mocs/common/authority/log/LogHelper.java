package smtcl.mocs.common.authority.log;

import org.apache.log4j.Logger;

import smtcl.mocs.pojos.authority.Log;

/**
 * 日志工具
 * @author gaokun
 * @create Sep 4, 2012 4:48:53 PM
 */
public class LogHelper {
	/**
	 * LogHelper 日志
	 */
	private static Logger logger = Logger.getLogger(ILogFinal.CATEGORY_NAME);
	
	/**
	 * 日志记录用在登录
	 * @param loginName
	 * @param ip
	 * @param pageId
	 * @param message
	 * @param status
	 */
	public static void log(String loginName, String ip, String pageId, String message, int status){
		Log log = new Log();
		log.setUserName(loginName);
		log.setIp(ip);
		log.setPageId(pageId);
		log.setMessage(message);
		log.setStatus(status);
		log(log);
	}
	
	/**
	 * 日志记录操作;
	 * @param loginName
	 * @param pageId
	 * @param message
	 * @param status
	 */
	public static void log(String loginName, String pageId, String message, int status){
		log(loginName, null, pageId, message, status);
	}
	
	
	/**
	 * 日志记录方法;
	 * @param o
	 */
	public static void log(Log log){
		logger.fatal(log);
	}
	

}

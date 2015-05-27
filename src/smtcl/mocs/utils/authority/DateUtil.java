/**
 * com.swg.authority.util DateUtil.java
 */
package smtcl.mocs.utils.authority;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author gaokun
 * @create Sep 27, 2012 2:14:59 PM
 */
public class DateUtil {
	
	/**
	 * 时分00:00:00;
	 * @param date
	 * @return
	 */
	public static Date truncate( Date date ){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 格式化日期时间
	 * @return
	 */
	public static String formatTime(String date, String styel){
		DateFormat format = new SimpleDateFormat(styel);
		try {
			Date dDate = format.parse(date);
			return format.format(dDate);
		} catch (ParseException e) {
			return date;
		}  
	}
	
	/**
	 * 根据参数获取本月前几月或后几月的某一天
	 */
	public static Date getData(int value,int date){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+value);
		cal.set(Calendar.DAY_OF_MONTH, date);
		return cal.getTime();
	}
	
	/**
	 * 根据参数获取月份和天书
	 * return 返回yyyy-MM-dd 日期时间
	 */
	public static String getData(Date d,int value,int date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+value);
		cal.set(Calendar.DAY_OF_MONTH, date);
		return new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());  
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(DateUtil.formatTime("2014-03-07 00:00:00.0", "yyyy-MM-dd"));
	}

}

package smtcl.mocs.utils.device;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
		public static void main(String[] args) {
		//	System.out.println("当前小时开始："+getCurrentHourStartTime().toString());
		//	System.out.println("当前小时结束："+getCurrentHourEndTime().toString());
			System.out.println("当前天开始："+getCurrentDayStartTime().toString());
			System.out.println("当前天时结束："+getCurrentDayEndTime().toString());
			System.out.println("当前周开始："+getCurrentWeekDayStartTime().toString());
			System.out.println("当前周结束："+getCurrentWeekDayEndTime().toString());
			System.out.println("当前月开始："+getCurrentMonthStartTime().toString());
			System.out.println("当前月结束："+getCurrentMonthEndTime().toString());
			System.out.println("当前季度开始："+getCurrentQuarterStartTime().toString());
			System.out.println("当前季度结束："+getCurrentQuarterEndTime().toString());
			System.out.println("当前半年/后半年开始："+getHalfYearStartTime().toString());
			System.out.println("当前半年/后半年结束："+getHalfYearEndTime().toString());
			System.out.println("当前年开始："+getCurrentYearStartTime().toString());
			System.out.println("当前年结束："+getCurrentYearEndTime().toString());
			Date time[]=getDoubleDate(2);
			System.out.println(time[0].toString());
			System.out.println(time[1].toString());
		}
		/**
		 * 
		 * @param type 1  本年      2  半年    3 本季度   4   本月    5 本周
		 * @return
		 */
		public static Date[] getDoubleDate(int type){
			Date time[] = new Date[2];
			switch (type) {
			case 1:  
				time[0]=getCurrentYearStartTime();
				time[1]=getCurrentYearEndTime();
				break;				
			case 2:
				break;
			case 3:
				time[0]=getCurrentQuarterStartTime();
				time[1]=getCurrentQuarterEndTime();
				break;
			case 4:				
				time[0]=getCurrentMonthStartTime();
				time[1]=getCurrentMonthEndTime();
				break;
			case 5:
				time[0]=getCurrentWeekDayStartTime();
				time[1]=getCurrentWeekDayEndTime();
				break;
			}
			return time;
		}
		/**
		 * 获取 当前年、半年、季度、月、日、小时 开始结束时间
		 */
		private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		private final static SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");;
		private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;


		/**
		 * 获得本周的第一天，周一
		 * 
		 * @return
		 */
		public static Date getCurrentWeekDayStartTime() {
			Calendar c = Calendar.getInstance();
			try {
				int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
				c.add(Calendar.DATE, -weekday);
				c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return c.getTime();
		}

		/**
		 * 获得本周的最后一天，周日
		 * 
		 * @return
		 */
		public static Date getCurrentWeekDayEndTime() {
			Calendar c = Calendar.getInstance();
			try {
				int weekday = c.get(Calendar.DAY_OF_WEEK);
				c.add(Calendar.DATE, 8 - weekday);
				c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return c.getTime();
		}

		/**
		 * 获得本天的开始时间，即2012-01-01 00:00:00
		 * 
		 * @return
		 */
		public static Date getCurrentDayStartTime() {
			Date now = new Date();
			try {
				now = shortSdf.parse(shortSdf.format(now));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return now;
		}

		/**
		 * 获得本天的结束时间，即2012-01-01 23:59:59
		 * 
		 * @return
		 */
		public static Date getCurrentDayEndTime() {
			Date now = new Date();
			try {
				now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return now;
		}

		/**
		 * 获得本小时的开始时间，即2012-01-01 23:59:59
		 * 
		 * @return
		 */
		public static Date getCurrentHourStartTime() {
			Date now = new Date();
			try {
				now = longHourSdf.parse(longHourSdf.format(now));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return now;
		}

		/**
		 * 获得本小时的结束时间，即2012-01-01 23:59:59
		 * 
		 * @return
		 */
		public static Date getCurrentHourEndTime() {
			Date now = new Date();
			try {
				now = longSdf.parse(longHourSdf.format(now) + ":59:59");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return now;
		}

		/**
		 * 获得本月的开始时间，即2012-01-01 00:00:00
		 * 
		 * @return
		 */
		public static Date getCurrentMonthStartTime() {
			Calendar c = Calendar.getInstance();
			Date now = null;
			try {
				c.set(Calendar.DATE, 1);
				now = shortSdf.parse(shortSdf.format(c.getTime()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return now;
		}

		/**
		 * 当前月的结束时间，即2012-01-31 23:59:59
		 * 
		 * @return
		 */
		public static Date getCurrentMonthEndTime() {
			Calendar c = Calendar.getInstance();
			Date now = null;
			try {
				c.set(Calendar.DATE, 1);
				c.add(Calendar.MONTH, 1);
				c.add(Calendar.DATE, -1);
				now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return now;
		}

		/**
		 * 当前年的开始时间，即2012-01-01 00:00:00
		 * 
		 * @return
		 */
		public static Date getCurrentYearStartTime() {
			Calendar c = Calendar.getInstance();
			Date now = null;
			try {
				c.set(Calendar.MONTH, 0);
				c.set(Calendar.DATE, 1);
				now = shortSdf.parse(shortSdf.format(c.getTime()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return now;
		}

		/**
		 * 当前年的结束时间，即2012-12-31 23:59:59
		 * 
		 * @return
		 */
		public static Date getCurrentYearEndTime() {
			Calendar c = Calendar.getInstance();
			Date now = null;
			try {
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DATE, 31);
				now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return now;
		}

		/**
		 * 当前季度的开始时间，即2012-01-1 00:00:00
		 * 
		 * @return
		 */
		public static Date getCurrentQuarterStartTime() {
			Calendar c = Calendar.getInstance();
			int currentMonth = c.get(Calendar.MONTH) + 1;
			Date now = null;
			try {
				if (currentMonth >= 1 && currentMonth <= 3)
					c.set(Calendar.MONTH, 0);
				else if (currentMonth >= 4 && currentMonth <= 6)
					c.set(Calendar.MONTH, 3);
				else if (currentMonth >= 7 && currentMonth <= 9)
					c.set(Calendar.MONTH, 4);
				else if (currentMonth >= 10 && currentMonth <= 12)
					c.set(Calendar.MONTH, 9);
				c.set(Calendar.DATE, 1);
				now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return now;
		}

		/**
		 * 当前季度的结束时间，即2012-03-31 23:59:59
		 * 
		 * @return
		 */
		public static Date getCurrentQuarterEndTime() {
			Calendar c = Calendar.getInstance();
			int currentMonth = c.get(Calendar.MONTH) + 1;
			Date now = null;
			try {
				if (currentMonth >= 1 && currentMonth <= 3) {
					c.set(Calendar.MONTH, 2);
					c.set(Calendar.DATE, 31);
				} else if (currentMonth >= 4 && currentMonth <= 6) {
					c.set(Calendar.MONTH, 5);
					c.set(Calendar.DATE, 30);
				} else if (currentMonth >= 7 && currentMonth <= 9) {
					c.set(Calendar.MONTH, 8);
					c.set(Calendar.DATE, 30);
				} else if (currentMonth >= 10 && currentMonth <= 12) {
					c.set(Calendar.MONTH, 11);
					c.set(Calendar.DATE, 31);
				}
				now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return now;
		}

		/**
		 * 获取前/后半年的开始时间
		 * 
		 * @return
		 */
		public static Date getHalfYearStartTime() {
			Calendar c = Calendar.getInstance();
			int currentMonth = c.get(Calendar.MONTH) + 1;
			Date now = null;
			try {
				if (currentMonth >= 1 && currentMonth <= 6) {
					c.set(Calendar.MONTH, 0);
				} else if (currentMonth >= 7 && currentMonth <= 12) {
					c.set(Calendar.MONTH, 6);
				}
				c.set(Calendar.DATE, 1);
				now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return now;

		}

		/**
		 * 获取前/后半年的结束时间
		 * 
		 * @return
		 */
		public static Date getHalfYearEndTime() {
			Calendar c = Calendar.getInstance();
			int currentMonth = c.get(Calendar.MONTH) + 1;
			Date now = null;
			try {
				if (currentMonth >= 1 && currentMonth <= 6) {
					c.set(Calendar.MONTH, 5);
					c.set(Calendar.DATE, 30);
				} else if (currentMonth >= 7 && currentMonth <= 12) {
					c.set(Calendar.MONTH, 11);
					c.set(Calendar.DATE, 31);
				}
				now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return now;
		}

}

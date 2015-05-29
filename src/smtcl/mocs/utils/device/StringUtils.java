 package smtcl.mocs.utils.device;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.richfaces.model.UploadedFile;

/**
 * 常用字符串工具集
 * @作者：YuTao
 * @创建时间：2012-11-5 下午1:31:52
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

	/**
	 * 判断输入参数是否为空
	 * @param text
	 * @return boolean
	 */
	public static boolean isEmpty(String text) {
		return null == text || text.trim().length() == 0||"null".equals(text)||"".equals(text);
	}
	
	/**
	 * 判断输入参数是否为空(涵盖null字符串)
	 * @param text
	 * @return boolean
	 */
	public static String isEmpty2(String obj){
		if(null==obj||"null".equals(obj))
			return "";
		else
		    return obj.toString();
	}
	
	/**
	 * 如果是空值，返回null
	 * @param text
	 * @return boolean
	 */
	public static String isEmpty3(String obj){
		if(isEmpty(obj)) 
			return null;
		else   
			return obj;
	}
	
	/**
	 * 时间字符串转换 
	 * @param date
	 * @param type 将时间格式的样式
	 * 			   1:yyyy-MM-dd HH:mm,
	 * 			   2:yyyy-MM-dd,
	 * 			   3:yyyy-MM-dd HH:mm:ss,
	 * 			   4:MMdd,
	 * 			   5:HH:mm:ss
	 * @return  String     
	 */
	public static String formatDate(Date date, int type) {
		String result = "";
		SimpleDateFormat minuteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat mouthFormat = new SimpleDateFormat("MMdd");
		SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat mouth = new SimpleDateFormat("yyyy-MM");

		if (date == null) {
			return "";
		}

		try {
			if (type == 1)
				result = minuteFormat.format(date);
			else if (type == 2)
				result = dayFormat.format(date);
			else if (type == 3)
				result = secondFormat.format(date);
			else if (type == 4)
				result = mouthFormat.format(date);
			else if(type==5)
				result = hms.format(date);
			else if(type==6)
				result = mouth.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return date.toString();
		}
		return result;
	}
	
	/**
	 * java日期转化成sql日期
	 * @param d
	 * @return java.sql.Date     
	 */
	public static java.sql.Date utilDateToSQLDate(Date d) {
		if (d == null)
			return null;
		else
			return new java.sql.Date(d.getTime());
	}
	
	/**
	 * 输入一个n秒的参数  返回一个字符串
	 * @param object 根据需要可重写该方法 改变参数类型的不同
	 * @return String 格式为 xx天xx小时xx分钟xx秒
	 */
	public static String SecondIsDDmmss(Object object) {
		long second=Long.parseLong(object.toString());
		int day=0;
		int hour=0;
		int minute=0;
		
		double number=0;
		day=(int)second/86400;
		number=(second%86400);
		hour=(int)number/3600;
		number=number%3600;
		minute=(int)number/60;
		second=(int)number%60;
		return  day+"天 "+hour+"小时 "+minute+"分钟 "+second+"秒";
	}

    /**
     * 输入一个n秒的参数  返回一个字符串
     * @param object 根据需要可重写该方法 改变参数类型的不同
     * @return String 格式为 xx天xx小时xx分钟xx秒
     */
    public static String SecondIsDDmmss(Object object,Locale locale) {
        long second=Long.parseLong(object.toString());
        int day, hour, minute;

        double number;
        day=(int)second/86400;
        number=(second%86400);
        hour=(int)number/3600;
        number=number%3600;
        minute=(int)number/60;
        second=(int)number%60;
        if(locale.toString().equals("en") || locale.toString().equals("en_US")){
            return day+"d "+hour+"h "+minute+"m "+second+"s";
        }else {
            return day + "天 " + hour + "小时 " + minute + "分钟 " + second + "秒";
        }
    }

	/**
	 * 根据传入的字符串时间，返回它的秒数
	 * @param str   格式为 xx天xx小时xx分钟xx秒
	 * @return int 单位为秒
	 */
	public static int DDMMssIsSecond(String str){
		if(null==str||"".equals(str)){
			return 0;
		}
		
		int day=str.indexOf("天");
		int hour=str.indexOf("小时");
		int minute=str.indexOf("分钟");
		int second=str.indexOf("秒");
		int totle=0;
		
		if(0!=Integer.parseInt(str.substring(0,day).trim()))
			totle=totle+Integer.parseInt(str.substring(0,day).trim())*86400;
		
		if(0!=Integer.parseInt(str.substring(day+1,hour).trim()))
			totle=totle+Integer.parseInt(str.substring(day+1,hour).trim())*3600;
		
		if(0!=Integer.parseInt(str.substring(hour+2,minute).trim()))
			totle=totle+Integer.parseInt(str.substring(hour+2,minute).trim())*60;
		
		if(0!=Integer.parseInt(str.substring(minute+2,second).trim()))
			totle=totle+Integer.parseInt(str.substring(minute+2,second).trim());

		return totle;
	}

	/**
	 * 转换时间函数 
	 * @param planTimeValue "0":当日  "1":本周  "2":本月  "3":本年
	 * @return Date[]  时间段内的开始时间和结束时间
	 */
	public static Date[] convertDate(int planTimeValue) {
		Date timeSearchJava[] = new Date[2];
		Date timeSearch[] = new Date[2];
		String[] exp = new String[2];
		Date now = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(now);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = sdf.format(now);
		switch (planTimeValue) {
		case -1:
			timeSearch[0] = null;
			timeSearch[1] = null;
			break;
		case 0:
			ca.add(Calendar.HOUR_OF_DAY,-(ca.get(Calendar.HOUR_OF_DAY)));
			timeSearchJava[0] = ca.getTime();
			exp[0] = sdf.format(timeSearchJava[0]);
			try {
				timeSearch[0] = sdf.parse(exp[0]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				timeSearch[1] = sdf.parse(nowTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 1:
			ca.add(Calendar.DAY_OF_MONTH,-(ca.get(Calendar.DAY_OF_WEEK)-1));
			timeSearchJava[0] = ca.getTime();
			exp[0] = sdf.format(timeSearchJava[0]);
			try {
				timeSearch[0] = sdf.parse(exp[0]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				timeSearch[1] = sdf.parse(nowTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			ca.add(Calendar.DAY_OF_MONTH, -(ca.get(Calendar.DAY_OF_MONTH)-1));
			timeSearchJava[0] = ca.getTime();
			exp[0] = sdf.format(timeSearchJava[0]);
			try {
				timeSearch[0] = sdf.parse(exp[0]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				timeSearch[1] = sdf.parse(nowTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			ca.add(Calendar.DAY_OF_MONTH, -(ca.get(Calendar.DAY_OF_YEAR)-1));
			timeSearchJava[0] = ca.getTime();
			exp[0] = sdf.format(timeSearchJava[0]);
			try {
				timeSearch[0] = sdf.parse(exp[0]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				timeSearch[1] = sdf.parse(nowTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		return timeSearch;
	}
	
	/**
	 * 最近一个月的时间
	 * @param planTimeValue
	 * @return Date[]
	 */
	public static Date[] convertDatea(int planTimeValue) {
		Date timeSearchJava[] = new Date[2];
		Date timeSearch[] = new Date[2];
		String[] exp = new String[2];
		Date now = new Date();
		Calendar ca = Calendar.getInstance();
		Calendar sa = Calendar.getInstance();
		ca.setTime(now);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = sdf.format(now);
		switch (planTimeValue) {
		case 1:
			sa.set(Calendar.DATE, 1);
			sa.roll(Calendar.DATE, -1);
			ca.add(Calendar.DAY_OF_MONTH, -(sa.get(Calendar.DATE)));
			timeSearchJava[0] = ca.getTime();
			exp[0] = sdf.format(timeSearchJava[0]);
			try {
				timeSearch[0] = sdf.parse(exp[0]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				timeSearch[1] = sdf.parse(nowTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		
		return timeSearch;
	}
	
	
	/**
	 * SQL时间类型换成UTIL时间
	 * @param d
	 * @return Date     
	 */
	public static Date SQLDateToUtilDate(java.sql.Date d) {
		if (d == null)
			return null;
		else
			return new Date(d.getTime());
	}
	
	/**
	 * 判断输入时间是否为空
	 * @param time
	 * @return boolean
	 */
	public static boolean hasNoTime(Date time) {
		return null == time ;
	}
	
	/**
	 * 将List转换成String,逗号分隔
	 * @param list 
	 * @return String
	 */
	public static String returnHqlIN(List list){
		if(list!=null&&list.size()>0)
		{
		StringBuffer str=new StringBuffer();
		String returnstr;
		str.append("");
		for(Object object:list){
			str.append("'"+object.toString()+"',");
		}
		returnstr=str.substring(0, str.length()-1).toString();
		return returnstr;
		}
		return "NULL";
	}

	
	/**
	 * 获取日期是属于当年第多少周
	 * @param date
	 * @return String
	 */
	public static String getYearWeek(Date date){
		String year="";
		String week="";
		String month="";
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		String st=formatDate(date, 2);
		String[] s=st.split("-");
		year=s[0];
		month=s[1];
		week=calendar.get(Calendar.WEEK_OF_YEAR)+"";
		if(month.equals("12")&&week.equals("1")){
			year=(Integer.parseInt(year)+1)+"";
		}
		if(week.length()<2)
			week="0"+week;
		return year+""+week;
	}
	
	/**
	 * 将String转换成Date
	 * @param DStr  要转换的String 
	 * @param format  要转换成Date的类型  如 yyyy-MM-dd
	 * @return Date
	 */
	public static Date convertDate(String DStr, String format) {
		java.util.Date date = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			date = simpleDateFormat.parse(DStr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
			return date;
		}
	
   /**
    * 获取Cookie值
    * @param request
    * @param name
    * @return String
    */
	public static String getCookie(HttpServletRequest request,String name)
	{
		String values = ""; 
		Cookie[] cookies = request.getCookies();    
		if(cookies!=null)    
		{    
		    for (int i = 0; i < cookies.length; i++)     
		    {    
		       Cookie c = cookies[i];         
		       if(c.getName().equalsIgnoreCase(name))    
		    	   values = c.getValue();  
		    }     
	   }  
		return values;
	}
	
	/**
	 * 删除Cookie值
	 * @param request
	 * @param response
	 * @param name
	 */
	public static void delCookie(HttpServletRequest request,HttpServletResponse response,String name)
	{
		 Cookie[] cookies = request.getCookies();  
	      try  
	      {  
	           for(int i=0;i<cookies.length;i++)    
	           {  
	        	   Cookie c = cookies[i];
	        	   if(c.getName().equalsIgnoreCase(name)){
	        		Cookie cookie = new Cookie(c.getName(), null);  
	   	            cookie.setMaxAge(0);  
	   	            response.addCookie(cookie);  
	   	            System.out.println("删除cookie");
	        	   }
	           
	           }  
	      }catch(Exception ex)  
	      {  
	    	  ex.printStackTrace();
	           System.out.println("清空Cookies发生异常！");  
	      }   
	}
	
	/**
	 * 保存Cookie值
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void saveCookie(HttpServletRequest request,HttpServletResponse response,String name,String value)
	{
        //开始保存Cookie
        Cookie cookie = new Cookie(name,value);
    	//cookie的有效期  30天
        cookie.setMaxAge(30*24*60*60*1000);
        cookie.setPath("/");
        response.addCookie(cookie);
	}
	
	/**
	 * 机床状态映射
	 * @param status
	 * @return String
	 */
	public static String rstatus(String status){
		String rStr="";
		if(null!=status){
			int number=Integer.parseInt(status);
			switch (number) {
			case 0:rStr="空闲";break;
			case 1:rStr="复位";break;
			case 2:rStr="运行";break;
			case 3:rStr="空闲";break;
			case 4:rStr="中断";break;
			case 5:rStr="保持";break;
			case 6:rStr="急停";break;
			case 7:rStr="UNKNOW1";break;
			case 8:rStr="UNKNOW2";break;
			case 9:rStr="故障";break;
			case 20:rStr="准备";break;
			case 21:rStr="开机";break;
			case 22:rStr="关机";break;
			default:rStr=status+"";break;
			}
		}
		
		return rStr;
	}
	
	/**
	 * 截取字符长度
	 * @param str
	 * @param type
	 * @return String
	 */
	public static String getSubString(String str,String type){		
		String overstr="...";
		System.err.println(str);
		if(isEmpty(str)) return "";
		else if("1".equals(type)&&str.length()>10) return str.substring(0,10)+overstr;//截取10个字符  
		else if("2".equals(type)&&str.length()>20) return str.substring(0,20)+overstr;//截取20个字符  
		else if("3".equals(type)&&str.length()>30) return str.substring(0,30)+overstr;//截取30个字符  
		else if("4".equals(type)&&str.length()>15) return str.substring(0,15)+overstr;//截取15个字符  
		else if("5".equals(type)&&str.length()>5) return str.substring(0,5)+overstr;//截取5个字符  
		else if("6".equals(type)&&str.length()>7) return str.substring(0,7)+overstr;//截取7个字符  
		else return str;		
	}	
	
	/**
	 * 验证手机号码
	 * @param mobile
	 * @return boolean
	 */	
	public static boolean checkYiDongMobile(String mobile) {
		if (mobile.equals("")) {
			return false;
		}
		String regex = "^0{0,1}(13[4-9]|15[7-9]|15[0-2]|18[7-8])[0-9]{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	
	/**
	 * 判断是否为数字
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumeric(String str)
	{
	Pattern pattern = Pattern.compile("[0-9]*");
	Matcher isNum = pattern.matcher(str);
	if( !isNum.matches() )
	{
	return false;
	}
	return true;
	} 
	
	/**
	 * 获取URL属性文件(yt注释掉，变更从context取)
	 * @return Map<String,String>
	 */
	/*
	public static Map<String, String> getUrl() {
		Map<String, String> proMap = new HashMap<String, String>();
		
		//获取项目根目录
		String appPath = Constants.class.getResource("Constants.class").toString();	 
		int index = appPath.indexOf("WEB-INF")-1;
	    String temp=appPath.substring(0,index);
	    temp=temp.substring( temp.lastIndexOf("/")+1,index);
	
		//获取配置文件的绝对路径
		String projectBaseURL =temp;
		String nowpath=System.getProperty("user.dir");
		String tempdir=nowpath.replace("bin", "webapps");  //把bin 文件夹变到 webapps文件里面 
		tempdir+="/"+projectBaseURL;
		
		Properties p;
		try {
			//读取配置文件里的url
		//	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

			//InputStream inputStream = classLoader.getResourceAsStream("FilterUrl.properties");

			InputStream inputStream = new BufferedInputStream(new FileInputStream(tempdir + "//WEB-INF//FilterUrl.properties"));
			
			p = new Properties();
			p.load(inputStream);
			String authorityURL = p.getProperty("authorityURL");
			String onlineFactoryURL =  p.getProperty("onlineFactoryURL");
			String i5portalURL =  p.getProperty("i5portalURL");		
			
			proMap.put("authorityURL",authorityURL);
			proMap.put("onlineFactoryURL", onlineFactoryURL);
			proMap.put("i5portalURL", i5portalURL);
			//yyh 
			String deployAddress =  p.getProperty("deployAddress");
			String serverSYURL =  p.getProperty("serverSYURL");
			String serverSHURL =  p.getProperty("serverSHURL");
			String ssocontrol= p.getProperty("ssocontrol");
			
			proMap.put("deployAddress", deployAddress);
			proMap.put("serverSYURL", serverSYURL);
			proMap.put("serverSHURL", serverSHURL);
			proMap.put("ssocontrol", ssocontrol);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proMap;
	}*/
	
	/**
	 * 获取URL属性文件
	 * @return Map<String,String>
	 */
	public static Map<String, String> getUrl(ServletContext context) {
		Map<String, String> proMap = new HashMap<String, String>();
	
		String configPath = "/WEB-INF/FilterUrl.properties";
		String base = context.getRealPath ("/");
		
		Properties p;
		try {
			//读取配置文件里的url
			InputStream inputStream = new BufferedInputStream(new FileInputStream(base + configPath));
			p = new Properties();
			p.load(inputStream);
			String authorityURL = p.getProperty("authURL");
			String onlineFactoryURL =  p.getProperty("mocsURL");
			
			proMap.put("authURL",authorityURL);
			proMap.put("mocsURL", onlineFactoryURL);
			//yyh 
			String deployAddress =  p.getProperty("defaultNode");
			proMap.put("defaultNode", deployAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proMap;
	}

	/**
	 * 获取属性文件传入的属性  
	 * ps  如果传入参数为null 则默认读取所有属性
	 * @param context 上下文
	 * @param Url  属性文件路劲
	 * @param parm 需要获取的属性名字
	 * @return
	 */
	public static Map<String, String> getUrlForAll(ServletContext context,String Url,String ...parm) {
		Map<String, String> proMap = new HashMap<String, String>();
		String configPath = "/WEB-INF/"+Url;
		String base = context.getRealPath ("/");
		Properties p;
		InputStream inputStream;
		try {
			//读取配置文件里的url
			inputStream = new BufferedInputStream(new FileInputStream(base + configPath));
			p = new Properties();
			p.load(inputStream);
			if(null!=parm&&parm.length>0){
				for(int i=0;i<parm.length;i++){
					proMap.put( parm[i],p.getProperty(parm[i]));
				}
			}else{
				Iterator itr =p.entrySet().iterator();//遍历
				while (itr.hasNext()) {
				Entry e = (Entry)itr.next();
				//System.err.println(e.getKey()+":"+e.getValue());//获取对应的值
				proMap.put(e.getKey()+"",e.getValue()+"");
				}
			}
			
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proMap;
		
		//获取属性文件全部属性
//		 HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();		
//   	 Map<String, String> map=StringUtils.getUrlForAll(request.getServletContext(), "ErpParm.properties", null);
//   	 Collection<String> c = map.values();
//        Iterator it = c.iterator();
//        for (; it.hasNext();) {
//            System.out.println(it.next());
//        }
		//获取属性文件部分属性
//        Map<String, String> map2=StringUtils.getUrlForAll(request.getServletContext(), "ErpParm.properties","test1","test2","test3");
//        Collection<String> d = map2.values();
//        Iterator itd = d.iterator();
//        for (; itd.hasNext();) {
//            System.out.println(itd.next());
//        }
	}
	/**
	 * 获取属性文件单条属性
	 * @param context 上下文
	 * @param Url  属性文件路劲
	 * @param parm 需要获取的属性名字
	 * @return
	 */
	public static String getUrlForOne(ServletContext context,String Url,String parm) {
		String proMap = "";
		String configPath = "/WEB-INF/"+Url;
		String base = context.getRealPath ("/");
		Properties p;
		InputStream inputStream;
		try {
			//读取配置文件里的url
			inputStream = new BufferedInputStream(new FileInputStream(base + configPath));
			p = new Properties();
			p.load(inputStream);
			proMap=p.getProperty(parm);
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proMap;
	}
	
	
	/**
	 * 手机合法性验证
	 * @param phone
	 * @return boolean
	 */
	public static boolean validateMoblie(String phone) {
		   int l = phone.length();
		   boolean rs=false;
		   switch (l) {
		   case 7:
		    if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4}$", phone)) {
		     rs= true;
		    }
		    break;
		   case 11:
		    if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$", phone)) {
		     rs= true;
		    }
		    break;
		   default:
		    rs=false;
		    break;
		   }
		   return rs;
		}
	
	/**
	 * 正则表达式处理
	 * @param text
	 * @param expression
	 * @return boolean
	 */
	private static boolean matchingText(String expression, String text){
		   Pattern p = Pattern.compile(expression); // 正则表达式
		   Matcher m = p.matcher(text); // 操作的字符串
		   boolean b = m.matches();
		   return b;
	}
	
	public static boolean listIsNull(List list){
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	public static long dateTimeBetween(String starttime,String endtime)
	{
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long between = 0;
        try {
            java.util.Date begin = dfs.parse(starttime);
            java.util.Date end = dfs.parse(endtime);
            between = (end.getTime() - begin.getTime())/1000;// 得到两者的秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return between;
	}

    public static String doubleConvertFormat(double param )
    {
    	 DecimalFormat df=new DecimalFormat("0.00");
    	 return df.format(param);
    }
    
    /**
     * 文件上传验证
     * @param file
     * @return
     */
    public static String getFileName(UploadedFile file){
		 String fileName=null;
		 Date now =new Date();
		 Random ran=new Random();
		 String[] letter={"a","b","c","d","e","f","g","h","r","j","k","l","m","n","o","p","q","r","s","t","u","v","w","s","y","z"};
		 String letterName="a";
		 for(int i=0;i<10;i++){
			 letterName+=letter[ran.nextInt(letter.length-1)];
		 }
		 String[] arrayFileName=file.getName().replace(".", "@").split("@");
		 if(null != arrayFileName && arrayFileName.length > 0){
			 fileName=now.getTime()+letterName+"."+arrayFileName[arrayFileName.length-1];
		 }
		 return fileName;
	 }
    
	/**
	 * 计算两段时间的时间差
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static String getBetweenDayNumber(String dateA, String dateB) {
	      long dayNumber = 0;
	      long DAY = 24L * 60L * 60L * 1000L;
	      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	      try {
	       java.util.Date d1 = df.parse(dateA);
	       java.util.Date d2 = df.parse(dateB);
	       dayNumber = (d2.getTime() - d1.getTime()) / DAY;     
	      } catch (Exception e) {
	       e.printStackTrace();
	      }
	      return String.valueOf(0 - dayNumber);         
	     }
	
   /*
    * 判断当前的连接地址是否有效 ，返回码
    */
	public static int isValid(String url) {
		Boolean connect = false;
		int returnCode= -1; // -1 标识connect失败
		try {
			URL u = new URL(url);
			try {
				HttpURLConnection uConnection = (HttpURLConnection) u.openConnection();
				try {
					uConnection.setConnectTimeout(5000);
					uConnection.connect();
					System.out.println(uConnection.getResponseCode());
					returnCode=uConnection.getResponseCode();
					connect = true;
				} catch (Exception e) {
					connect = false;
					// e.printStackTrace();
					// System.out.println("connect failed");
				}
			} catch (IOException e) {
				// System.out.println("build failed");
				// e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// System.out.println("build url failed");
			// e.printStackTrace();
		}
		return returnCode;
	}
	 
	//产生唯一标识
    public static synchronized String getUniqueString()
    {
        if(generateCount > 99999)
            generateCount = 0;
        String uniqueNumber = Long.toString(System.currentTimeMillis()) + Integer.toString(generateCount);
        generateCount++;
        return uniqueNumber;
    }
    private static final int MAX_GENERATE_COUNT = 99999;
    private static int generateCount = 0;
}


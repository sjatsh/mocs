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
 * �����ַ������߼�
 * @���ߣ�YuTao
 * @����ʱ�䣺2012-11-5 ����1:31:52
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

	/**
	 * �ж���������Ƿ�Ϊ��
	 * @param text
	 * @return boolean
	 */
	public static boolean isEmpty(String text) {
		return null == text || text.trim().length() == 0||"null".equals(text)||"".equals(text);
	}
	
	/**
	 * �ж���������Ƿ�Ϊ��(����null�ַ���)
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
	 * ����ǿ�ֵ������null
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
	 * ʱ���ַ���ת�� 
	 * @param date
	 * @param type ��ʱ���ʽ����ʽ
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
	 * java����ת����sql����
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
	 * ����һ��n��Ĳ���  ����һ���ַ���
	 * @param object ������Ҫ����д�÷��� �ı�������͵Ĳ�ͬ
	 * @return String ��ʽΪ xx��xxСʱxx����xx��
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
		return  day+"�� "+hour+"Сʱ "+minute+"���� "+second+"��";
	}

    /**
     * ����һ��n��Ĳ���  ����һ���ַ���
     * @param object ������Ҫ����д�÷��� �ı�������͵Ĳ�ͬ
     * @return String ��ʽΪ xx��xxСʱxx����xx��
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
            return day + "�� " + hour + "Сʱ " + minute + "���� " + second + "��";
        }
    }

	/**
	 * ���ݴ�����ַ���ʱ�䣬������������
	 * @param str   ��ʽΪ xx��xxСʱxx����xx��
	 * @return int ��λΪ��
	 */
	public static int DDMMssIsSecond(String str){
		if(null==str||"".equals(str)){
			return 0;
		}
		
		int day=str.indexOf("��");
		int hour=str.indexOf("Сʱ");
		int minute=str.indexOf("����");
		int second=str.indexOf("��");
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
	 * ת��ʱ�亯�� 
	 * @param planTimeValue "0":����  "1":����  "2":����  "3":����
	 * @return Date[]  ʱ����ڵĿ�ʼʱ��ͽ���ʱ��
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
	 * ���һ���µ�ʱ��
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
	 * SQLʱ�����ͻ���UTILʱ��
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
	 * �ж�����ʱ���Ƿ�Ϊ��
	 * @param time
	 * @return boolean
	 */
	public static boolean hasNoTime(Date time) {
		return null == time ;
	}
	
	/**
	 * ��Listת����String,���ŷָ�
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
	 * ��ȡ���������ڵ���ڶ�����
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
	 * ��Stringת����Date
	 * @param DStr  Ҫת����String 
	 * @param format  Ҫת����Date������  �� yyyy-MM-dd
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
    * ��ȡCookieֵ
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
	 * ɾ��Cookieֵ
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
	   	            System.out.println("ɾ��cookie");
	        	   }
	           
	           }  
	      }catch(Exception ex)  
	      {  
	    	  ex.printStackTrace();
	           System.out.println("���Cookies�����쳣��");  
	      }   
	}
	
	/**
	 * ����Cookieֵ
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void saveCookie(HttpServletRequest request,HttpServletResponse response,String name,String value)
	{
        //��ʼ����Cookie
        Cookie cookie = new Cookie(name,value);
    	//cookie����Ч��  30��
        cookie.setMaxAge(30*24*60*60*1000);
        cookie.setPath("/");
        response.addCookie(cookie);
	}
	
	/**
	 * ����״̬ӳ��
	 * @param status
	 * @return String
	 */
	public static String rstatus(String status){
		String rStr="";
		if(null!=status){
			int number=Integer.parseInt(status);
			switch (number) {
			case 0:rStr="����";break;
			case 1:rStr="��λ";break;
			case 2:rStr="����";break;
			case 3:rStr="����";break;
			case 4:rStr="�ж�";break;
			case 5:rStr="����";break;
			case 6:rStr="��ͣ";break;
			case 7:rStr="UNKNOW1";break;
			case 8:rStr="UNKNOW2";break;
			case 9:rStr="����";break;
			case 20:rStr="׼��";break;
			case 21:rStr="����";break;
			case 22:rStr="�ػ�";break;
			default:rStr=status+"";break;
			}
		}
		
		return rStr;
	}
	
	/**
	 * ��ȡ�ַ�����
	 * @param str
	 * @param type
	 * @return String
	 */
	public static String getSubString(String str,String type){		
		String overstr="...";
		System.err.println(str);
		if(isEmpty(str)) return "";
		else if("1".equals(type)&&str.length()>10) return str.substring(0,10)+overstr;//��ȡ10���ַ�  
		else if("2".equals(type)&&str.length()>20) return str.substring(0,20)+overstr;//��ȡ20���ַ�  
		else if("3".equals(type)&&str.length()>30) return str.substring(0,30)+overstr;//��ȡ30���ַ�  
		else if("4".equals(type)&&str.length()>15) return str.substring(0,15)+overstr;//��ȡ15���ַ�  
		else if("5".equals(type)&&str.length()>5) return str.substring(0,5)+overstr;//��ȡ5���ַ�  
		else if("6".equals(type)&&str.length()>7) return str.substring(0,7)+overstr;//��ȡ7���ַ�  
		else return str;		
	}	
	
	/**
	 * ��֤�ֻ�����
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
	 * �ж��Ƿ�Ϊ����
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
	 * ��ȡURL�����ļ�(ytע�͵��������contextȡ)
	 * @return Map<String,String>
	 */
	/*
	public static Map<String, String> getUrl() {
		Map<String, String> proMap = new HashMap<String, String>();
		
		//��ȡ��Ŀ��Ŀ¼
		String appPath = Constants.class.getResource("Constants.class").toString();	 
		int index = appPath.indexOf("WEB-INF")-1;
	    String temp=appPath.substring(0,index);
	    temp=temp.substring( temp.lastIndexOf("/")+1,index);
	
		//��ȡ�����ļ��ľ���·��
		String projectBaseURL =temp;
		String nowpath=System.getProperty("user.dir");
		String tempdir=nowpath.replace("bin", "webapps");  //��bin �ļ��б䵽 webapps�ļ����� 
		tempdir+="/"+projectBaseURL;
		
		Properties p;
		try {
			//��ȡ�����ļ����url
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
	 * ��ȡURL�����ļ�
	 * @return Map<String,String>
	 */
	public static Map<String, String> getUrl(ServletContext context) {
		Map<String, String> proMap = new HashMap<String, String>();
	
		String configPath = "/WEB-INF/FilterUrl.properties";
		String base = context.getRealPath ("/");
		
		Properties p;
		try {
			//��ȡ�����ļ����url
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
	 * ��ȡ�����ļ����������  
	 * ps  ����������Ϊnull ��Ĭ�϶�ȡ��������
	 * @param context ������
	 * @param Url  �����ļ�·��
	 * @param parm ��Ҫ��ȡ����������
	 * @return
	 */
	public static Map<String, String> getUrlForAll(ServletContext context,String Url,String ...parm) {
		Map<String, String> proMap = new HashMap<String, String>();
		String configPath = "/WEB-INF/"+Url;
		String base = context.getRealPath ("/");
		Properties p;
		InputStream inputStream;
		try {
			//��ȡ�����ļ����url
			inputStream = new BufferedInputStream(new FileInputStream(base + configPath));
			p = new Properties();
			p.load(inputStream);
			if(null!=parm&&parm.length>0){
				for(int i=0;i<parm.length;i++){
					proMap.put( parm[i],p.getProperty(parm[i]));
				}
			}else{
				Iterator itr =p.entrySet().iterator();//����
				while (itr.hasNext()) {
				Entry e = (Entry)itr.next();
				//System.err.println(e.getKey()+":"+e.getValue());//��ȡ��Ӧ��ֵ
				proMap.put(e.getKey()+"",e.getValue()+"");
				}
			}
			
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proMap;
		
		//��ȡ�����ļ�ȫ������
//		 HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();		
//   	 Map<String, String> map=StringUtils.getUrlForAll(request.getServletContext(), "ErpParm.properties", null);
//   	 Collection<String> c = map.values();
//        Iterator it = c.iterator();
//        for (; it.hasNext();) {
//            System.out.println(it.next());
//        }
		//��ȡ�����ļ���������
//        Map<String, String> map2=StringUtils.getUrlForAll(request.getServletContext(), "ErpParm.properties","test1","test2","test3");
//        Collection<String> d = map2.values();
//        Iterator itd = d.iterator();
//        for (; itd.hasNext();) {
//            System.out.println(itd.next());
//        }
	}
	/**
	 * ��ȡ�����ļ���������
	 * @param context ������
	 * @param Url  �����ļ�·��
	 * @param parm ��Ҫ��ȡ����������
	 * @return
	 */
	public static String getUrlForOne(ServletContext context,String Url,String parm) {
		String proMap = "";
		String configPath = "/WEB-INF/"+Url;
		String base = context.getRealPath ("/");
		Properties p;
		InputStream inputStream;
		try {
			//��ȡ�����ļ����url
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
	 * �ֻ��Ϸ�����֤
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
	 * ������ʽ����
	 * @param text
	 * @param expression
	 * @return boolean
	 */
	private static boolean matchingText(String expression, String text){
		   Pattern p = Pattern.compile(expression); // ������ʽ
		   Matcher m = p.matcher(text); // �������ַ���
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
            between = (end.getTime() - begin.getTime())/1000;// �õ����ߵ�����
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
     * �ļ��ϴ���֤
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
	 * ��������ʱ���ʱ���
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
    * �жϵ�ǰ�����ӵ�ַ�Ƿ���Ч ��������
    */
	public static int isValid(String url) {
		Boolean connect = false;
		int returnCode= -1; // -1 ��ʶconnectʧ��
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
	 
	//����Ψһ��ʶ
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


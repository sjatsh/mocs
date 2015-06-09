package smtcl.mocs.web.webservice.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import smtcl.mocs.pojos.device.TUserEquCurStatus;
import smtcl.mocs.utils.device.StringUtils;

public class MachineToolStatusModel {
	private ConcurrentHashMap<String, TUserEquCurStatus> temp;
	public void init(){
		if(null == temp){
			temp=new ConcurrentHashMap<String, TUserEquCurStatus>(16);
		}else{
			temp.clear();
		}
	}
	public void destory(){
		temp=null;
	}
	public boolean addElementFromHttprequest(HttpServletRequest request){
		boolean flag=true;
		
		return flag;
	}
	//24 pa
	public boolean addElement(String equSerialNo, String updateTime,
			String status, String programm, String toolNo, String part,
			String partTimeCount, String feedSpeed, String feedrate,
			String axisspeed, String axisspeedRate, String xfeed, String yfeed,
			String zfeed, String cuttingpower, String operator,
			String spindleLoad, String partCount,String afeed,String bfeed,String cfeed,String ufeed,String vfeed,String wfeed){
		boolean flag=true;
		TUserEquCurStatus tu=new TUserEquCurStatus();
		tu.setEquSerialNo(equSerialNo);
		try {
			tu.setUpdateTime(parse(updateTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		tu.setStatus(StringUtils.rstatus(status));
		tu.setProgramm(programm);
		tu.setToolNo(toolNo);
		tu.setPart(part);
		tu.setPartTimeCount(partTimeCount);
		tu.setFeedSpeed(feedSpeed);
		tu.setFeedrate(feedrate);
		tu.setAxisspeed(axisspeed);
		tu.setAxisspeedRate(axisspeedRate);
		tu.setXfeed(StringUtils.isEmpty3(xfeed));
		tu.setYfeed(StringUtils.isEmpty3(yfeed));
		tu.setZfeed(StringUtils.isEmpty3(zfeed));
		tu.setCuttingpower(cuttingpower);
		tu.setOperator(operator);
		tu.setSpindleLoad(spindleLoad);
		tu.setPartCount(partCount);
		tu.setAfeed(StringUtils.isEmpty3(afeed));
		tu.setBfeed(StringUtils.isEmpty3(bfeed));
		tu.setCfeed(StringUtils.isEmpty3(cfeed));
		tu.setUfeed(StringUtils.isEmpty3(ufeed));
		tu.setVfeed(StringUtils.isEmpty3(vfeed));
		tu.setWfeed(StringUtils.isEmpty3(wfeed));
		if(temp.containsKey(equSerialNo)){
			temp.replace(equSerialNo, tu);
		}else{
			temp.put(equSerialNo, tu);
		}
		return flag;
	}
	//18 pa
	public boolean addElement(String equSerialNo, String updateTime,
			String status, String programm, String toolNo, String part,
			String partTimeCount, String feedSpeed, String feedrate,
			String axisspeed, String axisspeedRate, String xfeed, String yfeed,
			String zfeed, String cuttingpower, String operator,
			String spindleLoad, String partCount){
		TUserEquCurStatus tu=new TUserEquCurStatus();
		boolean flag=true;
		tu.setEquSerialNo(equSerialNo);
		try {
			tu.setUpdateTime(parse(updateTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		tu.setStatus(StringUtils.rstatus(status));
		tu.setProgramm(programm);
		tu.setToolNo(toolNo);
		tu.setPart(part);
		tu.setPartTimeCount(partTimeCount);
		tu.setFeedSpeed(feedSpeed);
		tu.setFeedrate(feedrate);
		tu.setAxisspeed(axisspeed);
		tu.setAxisspeedRate(axisspeedRate);
		tu.setXfeed(StringUtils.isEmpty3(xfeed));
		tu.setYfeed(StringUtils.isEmpty3(yfeed));
		tu.setZfeed(StringUtils.isEmpty3(zfeed));
		tu.setCuttingpower(cuttingpower);
		tu.setOperator(operator);
		tu.setSpindleLoad(spindleLoad);
		tu.setPartCount(partCount);
		if(temp.containsKey(equSerialNo)){
			temp.replace(equSerialNo, tu);
		}else{
			temp.put(equSerialNo, tu);
		}
		return flag;
	}
	public void removeElement(String machineNum){
		if(temp.containsKey(machineNum)){
			temp.remove(machineNum);
		}
	}
	public void removeAllElement(){
		temp.clear();
	}
	public void displayAllElement(){
		while(temp.keys().hasMoreElements()){
			String tempSer=temp.keys().nextElement();
			try {
				System.out.println(tempSer+" : "+formatDate(temp.get(temp.keys().nextElement()).getUpdateTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public boolean haveElement(String machineNum){
		return temp.containsKey(machineNum);
	}
	private static final String date_format = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>(); 
 
    public static DateFormat getDateFormat()   
    {  
        DateFormat df = threadLocal.get();  
        if(df==null){  
            df = new SimpleDateFormat(date_format);  
            threadLocal.set(df);  
        }  
        return df;  
    }
    public static String formatDate(Date date) throws ParseException {
        return getDateFormat().format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        return getDateFormat().parse(strDate);
    }
}

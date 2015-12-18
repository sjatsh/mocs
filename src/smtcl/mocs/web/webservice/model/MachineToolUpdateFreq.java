package smtcl.mocs.web.webservice.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;


public class MachineToolUpdateFreq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4078170311817504940L;
	private ConcurrentHashMap<String, ConcurrentLinkedDeque<MachineRequestInfo>> temp;
	private AtomicLong requestNumPreMinutes;
	public AtomicLong getRequestNumPreMinutes() {
		return requestNumPreMinutes;
	}
	public ConcurrentHashMap<String, ConcurrentLinkedDeque<MachineRequestInfo>> getTemp() {
		return temp;
	}
	private void init(){
		if(null == temp){
			temp= new ConcurrentHashMap<String, ConcurrentLinkedDeque<MachineRequestInfo>>();
		}else{
			temp.clear();
		}
		if(null == requestNumPreMinutes){
			requestNumPreMinutes =new AtomicLong(0L);
		}else{
			requestNumPreMinutes.set(0L);
		}
	}
	private void destory(){
		temp=null;
	}
	
	public boolean addElementFrom(String no){
		boolean flag=true;
		Date flagRequestCome=new Date();
		if(temp.containsKey(no)){
			ConcurrentLinkedDeque<MachineRequestInfo> tempLinkedDeque=temp.get(no);
			MachineRequestInfo tempRequest=new MachineRequestInfo(flagRequestCome, no);
			tempLinkedDeque.offer(tempRequest);
			temp.replace(no, tempLinkedDeque);
		}else{
			ConcurrentLinkedDeque<MachineRequestInfo> initDequeValue= new ConcurrentLinkedDeque<MachineRequestInfo>();
			MachineRequestInfo tempRequest=new MachineRequestInfo(flagRequestCome, no);
			initDequeValue.offerFirst(tempRequest);
			temp.put(no, initDequeValue);
		}
		requestNumPreMinutes.set(requestNumPreMinutes.incrementAndGet());
		return flag;
	}
	
	public void removeElement(String machineNum){
		if(temp.containsKey(machineNum)){
			temp.remove(machineNum);
		}
	}
	public void removeAllElement(){
		if(!temp.isEmpty()){
			System.out.println("Before Run Me ~"+temp.size());
			temp.clear();
		}
		System.out.println("After Run Me ~"+temp.size());
	}
	public void makeToZero(){
		try {
			System.out.println("¶¨Ê±Çå³ý requestNumPreMinutes:  "+requestNumPreMinutes.get()+" : "+formatDate(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		requestNumPreMinutes.set(0L);
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

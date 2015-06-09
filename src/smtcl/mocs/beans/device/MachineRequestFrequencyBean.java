package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dreamwork.persistence.ServiceFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import smtcl.mocs.web.webservice.model.MachineRequestInfo;
import smtcl.mocs.web.webservice.model.MachineToolUpdateFreq;

@ManagedBean(name="MachineRequestFrequencyBean")
@ViewScoped
public class MachineRequestFrequencyBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -715562064085792572L;
	private MachineToolUpdateFreq machineToolUpdateFreq=(MachineToolUpdateFreq)ServiceFactory.getBean("machineToolUpdateFreq");
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getValue() {
		return value;
	}
	public String getJsonValue() {
		ConcurrentHashMap<String, ConcurrentLinkedDeque<MachineRequestInfo>> tempModel= machineToolUpdateFreq.getTemp();
		JSONArray jsonArr = new JSONArray();  
        JSONObject item = null;
        Iterator<Entry<String, ConcurrentLinkedDeque<MachineRequestInfo>>> tempIterator= tempModel.entrySet().iterator();
        while(tempIterator.hasNext()){
        	item = new JSONObject();  
        	Entry<String, ConcurrentLinkedDeque<MachineRequestInfo>> tempEntry=tempIterator.next();
        	item.put(tempEntry.getKey(), tempEntry.getValue().size());
        	jsonArr.add(item);
        }
        JSONObject allItem=new JSONObject();  
        allItem.put("finalValue", jsonArr.toString());
        jsonValue=allItem.toString();
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private String lable;
	private String value;
	private String jsonValue;
	private String requestmintes;
	private String requestMachineFreqMintes;
	public void clearMachineToolUpdateFreq(ActionEvent actionEvent){
		machineToolUpdateFreq.removeAllElement();
	}
	public String getRequestmintes() {
		Long requestmintesValue=machineToolUpdateFreq.getRequestNumPreMinutes().longValue();
		requestmintes=requestmintesValue.toString();
		return requestmintes;
	}
	public void setRequestmintes(String requestmintes) {
		this.requestmintes = requestmintes;
	}
	public String getRequestMachineFreqMintes() {
		ConcurrentHashMap<String, ConcurrentLinkedDeque<MachineRequestInfo>> tempModel= machineToolUpdateFreq.getTemp();
		JSONArray jsonArr = new JSONArray();  
        JSONObject item = null;
        DateTime currentTime = new DateTime();
        DateTime oneMinutesAgo = new DateTime(currentTime.getMillis()- 1000*60 );
        
        Iterator<Entry<String, ConcurrentLinkedDeque<MachineRequestInfo>>> tempIterator= tempModel.entrySet().iterator();
        while(tempIterator.hasNext()){
        	item = new JSONObject();  
        	Entry<String, ConcurrentLinkedDeque<MachineRequestInfo>> tempEntry=tempIterator.next();
        	ConcurrentLinkedDeque<MachineRequestInfo> tempQueueInEachMachine=new ConcurrentLinkedDeque<MachineRequestInfo>();
        	tempQueueInEachMachine.addAll(tempEntry.getValue());
        	
        	Iterator<MachineRequestInfo> temp= tempQueueInEachMachine.iterator();
        	while(temp.hasNext()){
        		DateTime tempObjectFromHead = new DateTime(temp.next().getRequestTime());
        		boolean inPeriod=(tempObjectFromHead.isAfter(oneMinutesAgo.getMillis()) && tempObjectFromHead.isBefore(currentTime.getMillis())) ? true : false;
        		if(!inPeriod){
        			tempQueueInEachMachine.pollFirst();
        		}
        	}
        	item.put(tempEntry.getKey(), tempQueueInEachMachine.size());
        	jsonArr.add(item);
        }
        JSONObject allItem=new JSONObject();  
        allItem.put("finalValueMintes", jsonArr.toString());
        requestMachineFreqMintes=allItem.toString();
		return requestMachineFreqMintes;
	}
	public void setRequestMachineFreqMintes(String requestMachineFreqMintes) {
		this.requestMachineFreqMintes = requestMachineFreqMintes;
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

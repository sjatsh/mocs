/**
 * com.swg.authority.util JsonResponseResult.java
 */
package smtcl.mocs.utils.authority;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * @author gaokun
 * @create Oct 12, 2012 11:11:05 AM
 */
public class JsonResponseResult {
	
	static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); 
	
	@Expose
	private boolean		succ = true;
	
	@Expose
	private Object		content;
	
	@Expose
	private String		msg;
	
	@Expose
	private Map attach = new HashMap();


	public void attach(String key, Object value){
		attach.put(key, value);
	}
	/**
	 * @return the succ
	 */
	public boolean isSucc() {
		return succ;
	}

	/**
	 * @param succ the succ to set
	 */
	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	/**
	 * @return the content
	 */
	public Object getContent() {
		return content;
	}
	
	/**
	 * @param content the content to set
	 */
	public void setContent(Object content) {
		this.content = content;
	}
	
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String toJsonString(){
		return "(" + gson.toJson(this) + ")";
	}
}

package smtcl.mocs.utils.device;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * 
 * Json处理
 * @作者：YuTao  
 * @创建时间：2012-12-10 
 * @修改者： 
 * @修改日期： 
 * @修改说明： 
 * @version V1.0
 */

public class JsonResponseResult {
	
	static Gson gson = new GsonBuilder().serializeNulls().create(); 
	
	@Expose
	private boolean		success = true;
	
	@Expose
	private Object		content;
	
	@Expose
	private String		msg;
	
	
	@Expose
	private int       count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the success
	 */
	public boolean isSucc() {
		return success;
	}

	/**
	 * @param succ the success to set
	 */
	public void setSucc(boolean success) {
		this.success = success;
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

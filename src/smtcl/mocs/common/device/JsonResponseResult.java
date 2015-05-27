package smtcl.mocs.common.device;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * 
 * @����˵���� Json����
 * @���ߣ�YuTao  
 * @����ʱ�䣺2012-12-10 
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵���� 
 * @�汾��V1.0
 */

public class JsonResponseResult{
	
	static Gson gson = new GsonBuilder().serializeNulls().create(); 
	
	@Expose
	private String		msg;
	
	@Expose
	private Object		content;
	
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
	
	public String toJsonString(){
		return "(" + gson.toJson(this) + ")";
	}
}

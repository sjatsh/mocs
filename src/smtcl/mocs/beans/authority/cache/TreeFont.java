/**
 * com.swg.authority.cache TreeFont.java
 */
package smtcl.mocs.beans.authority.cache;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * @author gaokun
 * @create Nov 5, 2012 5:38:10 PM
 */
public class TreeFont implements Serializable {
	
	public static TreeFont DISABLED_FONT = new TreeFont();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose
	private String color;
	
	
	public TreeFont(){
		this("#cac8bb");
	}
	
	public TreeFont(String color){
		this.color = color;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

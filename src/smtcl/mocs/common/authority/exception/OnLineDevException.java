/**
 * com.swg.authority.exception AuthorityException.java
 */
package smtcl.mocs.common.authority.exception;

/**
 * @author gaokun
 * @create Oct 13, 2012 2:55:57 PM
 */
public class OnLineDevException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OnLineDevException(String msg){
		super(msg);
	}
	
	public static void main(String[] args) {
		OnLineDevException ae = new OnLineDevException("ok");
		System.out.println(ae.getClass() == OnLineDevException.class);
	}
}

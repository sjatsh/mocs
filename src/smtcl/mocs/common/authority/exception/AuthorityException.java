/**
 * com.swg.authority.exception AuthorityException.java
 */
package smtcl.mocs.common.authority.exception;

/**
 * @author gaokun
 * @create Oct 13, 2012 2:55:57 PM
 */
public class AuthorityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthorityException(String msg){
		super(msg);
	}
	
	public static void main(String[] args) {
		AuthorityException ae = new AuthorityException("ok");
		System.out.println(ae.getClass() == AuthorityException.class);
	}
}

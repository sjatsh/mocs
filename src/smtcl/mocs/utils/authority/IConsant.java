/**
 * com.swg.alcom.util IConsant.java
 */
package smtcl.mocs.utils.authority;

/**
 * @author gaokun
 * @create Jul 12, 2011 3:55:05 PM
 * 常量;
 */
public interface IConsant {
	// session related keys
	String SESSION_LOGINUSER_KEY = "swg.authority.session.user";

    // login related keys
    String LOGIN_FORM_DATA = "swg.authority.login.form.data";
    String LOGIN_LOCATION = "swg.authority.login.location";
    String LOGIN_METHOD_KEY = "swg.authority.login.method";
	
	String LOGINUSER_MAP_KEY = "swg.authority.user.map";

    // sso related keys
    String SSO_TOKEN_KEY = "swg.authority.sso.token";
    String SSO_TARGET_KEY = "swg.authority.sso.target";
    String SSO_PROCESSOR_KEY = "swg.authority.sso.processor";
    String SSO_CALLBACK_KEY = "swg.authority.sso.callback";
    String SSO_SUPPORTED_KEY = "swg.authority.sso.supported";
	
	String APP_TITLE = "swg.authority.app.title";
	
	String USER_BIND_NODE = "swg.authority.user.bind.node";
	
	String FLAT_NODE = "swg.authority.flat.node";
	
	String RETURN_HOME = "swg.authority.return.home";
	
	String INDEX_HOME = "swg.authority.index.home";
    /**
     * 查询按钮id
     */
    String BTN_VIEW = "button.view";
    
    String AUTH_APP = "passport";
	
	/**
	 * 有效;
	 */
	int VALID   = 1;
	
	/**
	 * 无效;
	 */
	int INVALID = 0;
	
	String APP_PREFIX	= "A$";
	
	String MOD_PREFIX	= "M$";
	
	String PAGE_PREFIX	= "_P$_";
	
	String BTN_PREFIX	= "_B$_"; 

	String MARK_HQL_AUTH = "@auth@";
	
	String MARK_HQL_AUTH_UP = "@authup@";
	
	String MARK_HQL_EX_ADMIN = "@exadmin@";
	
	String NODE_TYPE_DEV = "13";
}

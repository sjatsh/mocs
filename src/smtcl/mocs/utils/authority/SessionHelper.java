/**
 * com.swg.authority.util SessionHelper.java
 */
package smtcl.mocs.utils.authority;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.i18n.IResourceAdapter;
import org.dreamwork.i18n.IResourceManager;
import org.dreamwork.jasmine2.configure.JasmineConfig;
import org.dreamwork.jasmine2.engine.HttpContext;
import org.dreamwork.jasmine2.web.IWebControl;

import smtcl.mocs.beans.authority.secure.LoginUser;


/**
 * @author gaokun
 * @create Nov 1, 2012 1:58:27 PM
 */
public class SessionHelper {
	
	private static Map<String, String[]> MODS = new HashMap<String, String[]>(); 
	
	static{
		MODS.put("userInfoList.jasmine", new String[]{"用户管理", "userInfoList.jasmine"});
    	MODS.put("userInfoDetail.jasmine", new String[]{"用户管理", "userInfoList.jasmine"});
    	MODS.put("roleList.jasmine", new String[]{"功能角色管理", "roleList.jasmine"});
    	MODS.put("roleDetail.jasmine", new String[]{"功能角色管理", "roleList.jasmine"});
    	MODS.put("orgList.jasmine", new String[]{"组织架构管理", "orgList.jasmine"});
    	MODS.put("org_group_list.jasmine", new String[]{"数据角色管理", "org_group_list.jasmine"});
    	MODS.put("org_group_detail.jasmine", new String[]{"数据角色管理", "org_group_list.jasmine"});
    	MODS.put("orgEdit.jasmine", new String[]{"组织架构调整", "orgEdit.jasmine"});
	}
	
	public static LoginUser loginUser (HttpSession session){
		return (LoginUser) session.getAttribute(IConsant.SESSION_LOGINUSER_KEY);
	}

    public static Locale getCurrentLocale (HttpSession session) {
        Locale locale = (Locale) session.getAttribute (IWebControl.LOCALE_KEY);
        if (locale == null) {
            ServletContext application = session.getServletContext ();
            locale = (Locale) application.getAttribute (JasmineConfig.DEFAULT_LOCALE);
        }
        return locale;
    }

    /**
     * 多语言
     */
    public static String getTextByKey(String resourceName, HttpSession session, String key) {
        Locale locale = getCurrentLocale(session);
        if (locale!=null) {
            IResourceManager manager = (IResourceManager) session.getServletContext().getAttribute(JasmineConfig.JASMINE_I18N_HOLDER);
            IResourceAdapter adapter = manager.getResourceAdapter(resourceName);
            return adapter.getString(locale, key);
        }
        return key;
    }
    
    /**
     * 多语言
     * 非JSP页面使用;由于SESSION问题;
     */
    public static String getText(String key) {
    	HttpContext hc = HttpContext.current();
    	HttpSession session = hc.getSession();
    	return getTextByKey(null, session, key);
    }
    
    public static boolean userBindNode(HttpSession session) {
    	Boolean result = (Boolean)session.getServletContext().getAttribute(IConsant.USER_BIND_NODE);
        if(result == null)
        	result = new Boolean(true);
        return result;
    }
    
    public static boolean flatNode(HttpSession session) {
    	Boolean result = (Boolean)session.getServletContext().getAttribute(IConsant.FLAT_NODE);
        if(result == null)
        	result = new Boolean(true);
        return result;
    } 
    
    public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
    
    public static String modName(HttpServletRequest request){
    	String result = "";
    	String path = request.getServletPath ();
    	for (String key : MODS.keySet()) {
			if(path.indexOf(key) > -1 ){
				result = MODS.get(key)[0];
				break;
			}
		}
    	return result;
    }
    
    public static String modKey(HttpServletRequest request){
    	String result = "swg-999-key";
    	String path = request.getServletPath ();
    	for (String key : MODS.keySet()) {
			if(path.indexOf(key) > -1 ){
				result = MODS.get(key)[1];
				break;
			}
		}
    	return result;
    }


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

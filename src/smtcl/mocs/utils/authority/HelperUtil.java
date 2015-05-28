package smtcl.mocs.utils.authority;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dreamwork.config.ApplicationConfigParser;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.StringUtil;

import smtcl.mocs.services.authority.ITreeService;

import smtcl.mocs.beans.authority.cache.NodeData;
import smtcl.mocs.pojos.authority.Module;
import smtcl.mocs.pojos.authority.Page;

/**
 * @author gaokun
 * @create Jul 12, 2011 2:50:52 PM 工具?
 */
public class HelperUtil {

	/**
	 * HelperUtil 日志?
	 */
	private final static Logger logger = Logger.getLogger(HelperUtil.class);
	
	private static ReentrantLock lock = new ReentrantLock();
	
	private static ReentrantLock org_lock = new ReentrantLock();
	
	private static final Pattern TOKEN_PATTERN = Pattern.compile ("[\\s,|;]");
	
//	private final static BSFManager bsf = new BSFManager();
//	private static BSFEngine bsfe = null;
	
	private static Configuration con = null;
	static{
//		try {
//			bsfe = bsf.loadScriptingEngine("javascript");
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("swg.authority.BSFEngine error!"+e.getMessage());
//		}
		
		try {
			String dir = HelperUtil.class.getResource("/").getPath();
			con = new XMLConfiguration(new File(dir, "../authority.xml"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("swg.authority.authority parse error!"+e.getMessage());
		}
	}
	
//	public static Double eval( String exp ){
//		Double result = null;
//		try{
//			result = Double.valueOf(HelperUtil.bsfe.eval("javascript", 0, 0, exp).toString());
//		}catch(Exception e){
//			e.printStackTrace();
//			logger.error("swg.authority.pase formula error ["+exp+"]!"+e.getMessage());
//		}
//		return result;
//	}
	
	/**
	 * 权限系统名称;
	 * @return
	 */
	public static String title(){
		String title = "SWG权限系统";
		if(con != null)
			title = con.getString("title", "SWG权限系统");
		return title;
	}
	
	
	/**
	 * 超级用户配置;
	 * 
	 * @return
	 */
	public static Set<String> queryAdmin() {
//		List<String> list = admin.getList("loginName");
//
//		Set<String> result = new HashSet<String>();
//		for (String login : list) {
//			if (StringUtils.isNotBlank(login)) {
//				result.add(StringUtils.trim(login));
//			}
//		}
//		logger.info("akcome super admin : " + StringUtils.join(result, ","));
//		return result;
		return null;
	}


	/**
	 * 1.扁平化组织结? 2.初始化组织树; 3.初始化公司树;
	 */
	public static void flatOrg(String[] objs) {
		if(objs == null) return;
		org_lock.lock();
		try {
			ITreeService treeService = (ITreeService) ServiceFactory
					.getBean("treeService");
			if(objs.length > 0){
				treeService.flatOrgTemp(objs[0], objs[1], objs[2]);
			}else{
				List<NodeData> list_node = treeService.queryOrgList();
				// 扁平化组织将数据插入临时?
				treeService.flatOrgTemp(list_node);
			}
			//插入真实?
			treeService.updateFlatOrg();
			 logger.info(" flat org success..................!");
		} catch (Exception e) {
			logger.error(" flat org error!" + e.getMessage());
		}finally{
			org_lock.unlock();
		}
	}
	
	/**
	 * 关闭数据库连?
	 * 
	 * @param obj
	 */
	public static void closeSql(Object obj) {
		if (obj != null) {
			try {
				if (obj instanceof Connection)
					((Connection) obj).close();
				else if (obj instanceof PreparedStatement )
					((PreparedStatement) obj).close();
				else if (obj instanceof CallableStatement)
					((CallableStatement) obj).close();
				else
					throw new RuntimeException("unknow database object["
							+ obj.getClass() + "] ! can't close!");
			} catch (Exception e) {
				obj = null;
			}
		}
	}
	
	public static List<String> querySSOTarget(ServletContext context)throws Exception{
		ApplicationConfigParser parser = null;
		String configPath = "/WEB-INF/sso-config.xml";
		String base = context.getRealPath ("/");
        if (StringUtils.isNotEmpty (base)) {
            File file = new File (base, configPath);
            if (file.exists () && file.canRead ()) {
                if (logger.isDebugEnabled ())
                    logger.debug ("sso config file found: " + file.getCanonicalPath ());
                parser = new ApplicationConfigParser (file);
            }
        }

        InputStream in = context.getResourceAsStream (configPath);
        if (in != null)
            parser = new ApplicationConfigParser (in);

        if(parser == null){
        	logger.warn ("Can't find sso config");
        	throw new Exception("Can't find sso config");
        }
        parser.parse();
        String line = (String) parser.getValue ("sso-target");
        List<String> targets = new ArrayList<String> ();
        if (!StringUtil.isEmpty (line)) {
            String[] a = TOKEN_PATTERN.split (line);
            for (String s : a) {
                if (s != null && s.trim ().length () > 0) targets.add (s);
            }
        }
        return targets;
	}
	
	public static boolean isAbsoluteURL(String path){
    	boolean result = false;
    	if(StringUtils.isNotBlank(path)){
    		path = path.trim().toLowerCase();
    		if(path.startsWith("http://")||
            		path.startsWith("https://")){
            	result = true;
            }
    	}
    	return result;
    }
	
	public static void close(InputStream in){
		try{
			if(in != null)
				in.close();
		}catch(Exception e){
			
		}
	}
	
	public static void close(Reader reader){
		try{
			if(reader != null)
				reader.close();
		}catch(Exception e){
			
		}
	}
	
	public static List<Page> orderPage(Collection<Page> list){
		List<Page> result = Collections.emptyList();
		if(list == null)
			return result;
		
		result = new ArrayList<Page>(list);
		Collections.sort(result, new Comparator<Page>() {

			@Override
			public int compare(Page o1, Page o2) {
				if(o1 == null)
					return 1;
				if(o2 == null){
					return -1;
				}
				Integer seq1 = o1.getSeq();
				Integer seq2 = o2.getSeq();

				if(seq1 == null)
					return 1;

				if(seq2 == null)
					return -1;

				int i = (int)Math.signum((double)(seq1 - seq2));
				if(i == 0){
					String n1 = o1.getPageId();
					String n2 = o2.getPageId();
					if(n1 == null)
						return 1;

					if(n2 == null)
						return -1;
					return -n1.compareTo(n2);
				}else
					return i;
			}
		});
		return result;
	}
	
	public static List<Module> orderModule(Collection<Module> list){
		List<Module> result = Collections.emptyList();
		if(list == null)
			return result;
		
		result = new ArrayList<Module>(list);
		Collections.sort(result, new Comparator<Module>() {

			@Override
			public int compare(Module o1, Module o2) {
				if(o1 == null)
					return 1;
				if(o2 == null){
					return -1;
				}
				Integer seq1 = o1.getSeq();
				Integer seq2 = o2.getSeq();

				if(seq1 == null)
					return 1;

				if(seq2 == null)
					return -1;

				int i = (int)Math.signum((double)(seq1 - seq2));
				if(i == 0){
					String n1 = o1.getLabel();
					String n2 = o2.getLabel();
					if(n1 == null)
						return 1;

					if(n2 == null)
						return -1;
					return n1.compareTo(n2);
				}else
					return i;
			}
		});
		return result;
	}
	

	public static void main(String[] args) {
	}
}

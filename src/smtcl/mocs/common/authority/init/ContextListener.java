package smtcl.mocs.common.authority.init;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dreamwork.config.ApplicationConfigParser;
import org.dreamwork.util.StringUtil;
import org.xml.sax.SAXException;

import smtcl.mocs.beans.authority.cache.FlatManager;

import smtcl.mocs.common.authority.sso.SsoScanner;
import smtcl.mocs.utils.authority.IConsant;

/**
 * Application Lifecycle Listener implementation class ContextListener
 * @author gaokun
 */
public class ContextListener implements ServletContextListener {
    private SsoScanner scanner;
    private ApplicationConfigParser parser;
    private static final Logger logger = Logger.getLogger (ContextListener.class);

    private static final Pattern TOKEN_PATTERN = Pattern.compile ("[\\s,|;]");

    /**
     * Default constructor. 
     */
    public ContextListener() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
    	System.out.println("listener .......init............");
    	
    	//
//    	sce.getServletContext().setAttribute(IConsant.APP_TITLE, HelperUtil.title());

        ServletContext context = sce.getServletContext ();
        
        String returnHome = context.getInitParameter ("return.home");
        returnHome = StringUtils.trimToNull(returnHome);
        String rh = context.getContextPath() + "/login?action=logout";
        if(StringUtils.isNotBlank(returnHome)){
        	rh = returnHome;
        }
        context.setAttribute(IConsant.RETURN_HOME, rh);
        
        String indexHome = context.getInitParameter ("index.home");
        indexHome = StringUtils.trimToNull(indexHome);
        String ih = "#";
        if(StringUtils.isNotBlank(indexHome)){
        	ih = indexHome;
        }
        context.setAttribute(IConsant.INDEX_HOME, ih);
        
        String userBindNode = context.getInitParameter ("user.bind.node");
        userBindNode = StringUtils.trimToNull(userBindNode);
        Boolean f = true;
        if("false".equals(userBindNode))
			f = false;
        sce.getServletContext().setAttribute(IConsant.USER_BIND_NODE, f);
        logger.info("com.swg.authority.parse success! web.xml=>user.bind.node:" + f);
        
        String flatNode = context.getInitParameter ("flat.node");
        flatNode = StringUtils.trimToNull(flatNode);
        Boolean n = true;
        if("false".equals(flatNode))
			n = false;
        sce.getServletContext().setAttribute(IConsant.FLAT_NODE, n);
        logger.info("com.swg.authority.parse success! web.xml=>flat.node:" + n);
        
        FlatManager.setPro(n);
        FlatManager.getInstance().start();
        FlatManager.getInstance().add();
    
        
        String ssoConfigFile = context.getInitParameter ("sso.config.location");
        boolean ssoSupported = StringUtils.isNotEmpty (ssoConfigFile);
        // startup sso scanner
        if (ssoSupported) try {
            context.setAttribute (IConsant.SSO_SUPPORTED_KEY, ssoSupported);
            prepareSSO (context, ssoConfigFile);
            long interval = 60000;
            try {
                interval = Long.parseLong ((String) parser.getValue ("interval"));
            } catch (Exception ignore) {
            }
            scanner = new SsoScanner (context, interval);
            scanner.start ();
        } catch (Exception ex) {
            throw new RuntimeException (ex);
        }
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
    	//
    	sce.getServletContext().removeAttribute(IConsant.APP_TITLE);
        if (scanner != null)
            scanner.setRunning (false);
        
        FlatManager.getInstance().close();
    }

    /**
     * 
     * @param context Servlet Context
     * @param configPath 
     * @throws java.io.IOException IO Exception
     * @throws org.xml.sax.SAXException XML Parse Exception
     */
    private void prepareSSO (ServletContext context, String configPath) throws IOException, SAXException {
        if (logger.isDebugEnabled ())
            logger.debug ("trying to parse sso config");
        parser = findConfig (context, configPath);
        if (parser == null)
            throw new RuntimeException ("Can't find sso config file with path: " + configPath);

        parser.parse ();
        String callback = (String) parser.getValue ("callback-url");
        if (StringUtils.isEmpty (callback))
            throw new IllegalArgumentException ("Can't find sso callback");
        callback = URLEncoder.encode (callback, "utf-8");
        String ssoProcessor = (String) parser.getValue ("sso-processor");
        if (StringUtils.isEmpty (ssoProcessor))
            throw new RuntimeException ("Can't find sso processor");

        String line = (String) parser.getValue ("sso-target");
        List<String> targets = new ArrayList<String> ();
        if (!StringUtil.isEmpty (line)) {
            String[] a = TOKEN_PATTERN.split (line);
            for (String s : a) {
                if (s != null && s.trim ().length () > 0) targets.add (s);
            }
        }

        context.setAttribute (IConsant.SSO_CALLBACK_KEY, callback);
        context.setAttribute (IConsant.SSO_PROCESSOR_KEY, ssoProcessor);
        context.setAttribute (IConsant.SSO_TARGET_KEY, targets);
    }

    private ApplicationConfigParser findConfig (ServletContext context, String configPath) throws IOException, SAXException {
        if (StringUtils.isEmpty (configPath)) return null;
        configPath = configPath.trim ();
        String target = System.getProperty (configPath);
        if (StringUtils.isNotEmpty (target)) {
            File file = new File (target);
            if (file.exists () && file.canRead ()) {
                if (logger.isDebugEnabled ())
                    logger.debug ("sso config file found : " + file.getCanonicalPath ());
                return new ApplicationConfigParser (file);
            }
        }

        String base = context.getRealPath ("/");
        if (StringUtils.isNotEmpty (base)) {
            File file = new File (base, configPath);
            if (file.exists () && file.canRead ()) {
                if (logger.isDebugEnabled ())
                    logger.debug ("sso config file found: " + file.getCanonicalPath ());
                return new ApplicationConfigParser (file);
            }
        }

        InputStream in = context.getResourceAsStream (configPath);
        if (in != null)
            return new ApplicationConfigParser (in);

        logger.warn ("Can't find sso config");
        return null;
    }
}
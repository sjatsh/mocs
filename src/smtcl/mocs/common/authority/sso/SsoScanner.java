package smtcl.mocs.common.authority.sso;

import smtcl.mocs.utils.authority.IConsant;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-9-29
 * Time: ÏÂÎç2:36
 */
public class SsoScanner extends Thread {
    public static final Object locker = new Object ();
    private static final Logger logger = Logger.getLogger (SsoScanner.class);

    private ServletContext application;
    private boolean running = true;
    private long interval;

    public SsoScanner (ServletContext context, long interval) {
        application = context;
        this.interval = interval;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public void run () {
        while (isRunning ()) {
            Map<String, SsoToken> map = (Map<String, SsoToken>) application.getAttribute (IConsant.SSO_TOKEN_KEY);
            if (map == null || map.size () == 0) {
                if (logger.isDebugEnabled ())
                    logger.debug ("there's no token in cache, wait for cache is not empty...");
                synchronized (locker) {
                    try {
                        locker.wait ();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace ();
                    }
                }
                if (logger.isDebugEnabled ())
                    logger.debug ("wake up");
                continue;
            }

            synchronized (locker) {
                Map<String, SsoToken> tokens = new HashMap<String, SsoToken> (map.size ());
                tokens.putAll (map);
                for (String fingerPrint : tokens.keySet ()) {
                    SsoToken token = tokens.get (fingerPrint);
                    if (token.getTimestamp () < System.currentTimeMillis ()) {
                        map.remove (fingerPrint);
                        if (logger.isDebugEnabled ()) {
                            logger.debug ("fingerPrint [" + fingerPrint + "] has expired, remove it!");
                        }
                    }
                }
                tokens.clear ();
            }

            try {
                sleep (interval);
            } catch (InterruptedException ex) {
                // ignore
            }
        }
    }

    public boolean isRunning () {
        synchronized (locker) {
            return running;
        }
    }

    public void setRunning (boolean running) {
        synchronized (locker) {
            this.running = running;
        }
    }
}
package smtcl.mocs.common.authority.i18n;

import org.dreamwork.i18n.IResourceManager;
import org.dreamwork.jasmine2.i18n.IWebResourceManagerFactory;

import javax.servlet.ServletContext;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-1
 * Time: 下午8:00
 */
public class DatabaseWebResourceManagerFactory implements IWebResourceManagerFactory {
    private static DatabaseResourceManager manager;
    private static final Object locker = new Object ();

    public static final String TEMP_RESOURCE_MANAGER_KEY = "temp.resource.manager.key";

    @Override
    public IResourceManager getResourceManager (ServletContext context, Locale defaultLocale, String resourceName) {
        synchronized (locker) {
            if (manager == null) {
                manager = new DatabaseResourceManager (defaultLocale);
            } else {
                manager.setDefaultLocale (defaultLocale);
            }
            return manager;
        }
    }

    public static DatabaseResourceManager getResourceManager () {
        synchronized (locker) {
            return manager == null ? manager = new DatabaseResourceManager () : manager;
        }
    }
}

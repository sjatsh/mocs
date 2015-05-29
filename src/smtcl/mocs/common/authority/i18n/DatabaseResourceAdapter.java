package smtcl.mocs.common.authority.i18n;

import org.dreamwork.i18n.AbstractResourceAdapter;
import org.dreamwork.i18n.IResourceBundle;
import org.dreamwork.i18n.LocaleWarp;
import org.dreamwork.i18n.MissingResourceException;

import java.util.Collection;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-1
 * Time: 下午8:02
 */
public class DatabaseResourceAdapter extends AbstractResourceAdapter {
    private Locale defaultLocale;
    private SortedSet<LocaleWarp> locales = new TreeSet<LocaleWarp> ();

    public DatabaseResourceAdapter () {
    }

    /**
     * 装载指定名称和区域设置的资源绑定文件
     *
     * @param locale 区域设置
     * @return 资源绑定信息
     */
    @Override
    protected IResourceBundle loadResourceByLocale (Locale locale) {
        return softCache.get (locale);
    }

    @Override
    public String getString (Locale locale, String name, String defaultValue) {
        return super.getString (locale, name, defaultValue);
    }

    public DatabaseResourceBundle getResourceBundle (Locale locale) {
        return (DatabaseResourceBundle) softCache.get (locale);
    }

    public void addResourceBundle (Locale locale, IResourceBundle bundle) {
        softCache.put (locale, bundle);
        locales.add (new LocaleWarp (locale));
        if (locale.equals (defaultLocale))
            defaultResourceBundle = bundle;
    }

    public void setDefaultLocale (Locale locale) {
        this.defaultLocale = locale;
        if (!softCache.containsKey (locale))
            throw new MissingResourceException ("Default locale [" + defaultLocale.getDisplayName () + "] missing.");

        defaultResourceBundle = softCache.get (locale);
    }

    @Override
    public Collection<LocaleWarp> getSupportedLocales () {
        return locales;
    }
}
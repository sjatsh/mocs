package smtcl.mocs.common.authority.i18n;

import org.dreamwork.i18n.AbstractResourceManager;
import org.dreamwork.i18n.IResourceAdapter;
import org.dreamwork.i18n.LocaleWarp;

import java.util.Collection;
import java.util.Locale;
import java.util.SortedSet;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-1
 * Time: 下午7:59
 */
public class DatabaseResourceManager extends AbstractResourceManager {
    private Locale defaultLocale;
    private DatabaseResourceAdapter adapter;

    public DatabaseResourceManager () {

    }

    public DatabaseResourceManager (Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /**
     * 根据给定的资源名称，返回特定IResourceAdapter 实现
     *
     * @param baseName 资源名称
     * @return 资源适配
     */
    @Override
    protected IResourceAdapter createResourceAdapter (String baseName) {
        return adapter;
    }

    /**
     * 获取资源绑定器的工厂方法.
     * <p>根据给定的基名和默认的区域设置，以某种方式返回资源绑定器.
     * code>defaultLocale</code>无法载入，实现类应该抛出异常
     *
     * @param baseName 基础语言
     * @return IResourceBundle 实例
     */
    @Override
    public IResourceAdapter getResourceAdapter (String baseName) {
        return adapter;
    }

    public void setAdapter (DatabaseResourceAdapter adapter) {
        this.adapter = adapter;
        locales.addAll (adapter.getSupportedLocales ());
        if (defaultLocale != null)
            adapter.setDefaultLocale (defaultLocale);
    }

    public void setDefaultLocale (Locale locale) {
        this.defaultLocale = locale;
        if (adapter != null) adapter.setDefaultLocale (defaultLocale);
    }

    public Locale getDefaultLocale () {
        return defaultLocale;
    }

    /**
     * 获取当前资源管理器所有支持的区域设置
     *
     * @return 支持的区域设
     */
    @Override
    public Collection<LocaleWarp> getSupportedLocales () {
        if (adapter == null) {
            adapter = new DatabaseResourceAdapter ();
            locales = (SortedSet<LocaleWarp>) adapter.getSupportedLocales ();
        }
        return locales;
    }
}

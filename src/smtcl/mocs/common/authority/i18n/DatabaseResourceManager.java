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
 * Time: ����7:59
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
     * ���ݸ�������Դ���ƣ������ض�IResourceAdapter ʵ��
     *
     * @param baseName ��Դ����
     * @return ��Դ����
     */
    @Override
    protected IResourceAdapter createResourceAdapter (String baseName) {
        return adapter;
    }

    /**
     * ��ȡ��Դ�����Ĺ�������.
     * <p>���ݸ����Ļ�����Ĭ�ϵ��������ã���ĳ�ַ�ʽ������Դ����.
     * code>defaultLocale</code>�޷����룬ʵ����Ӧ���׳��쳣
     *
     * @param baseName ��������
     * @return IResourceBundle ʵ��
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
     * ��ȡ��ǰ��Դ����������֧�ֵ���������
     *
     * @return ֧�ֵ�������
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

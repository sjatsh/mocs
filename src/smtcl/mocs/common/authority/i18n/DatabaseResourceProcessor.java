package smtcl.mocs.common.authority.i18n;

import org.dreamwork.i18n.LocaleUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-1
 * Time: 下午8:43
 */
public class DatabaseResourceProcessor implements BeanFactoryPostProcessor {
    private SessionFactory sessionFactory;
    private String tableName;

    public SessionFactory getSessionFactory () {
        return sessionFactory;
    }

    public void setSessionFactory (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String getTableName () {
        return tableName;
    }

    public void setTableName (String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void postProcessBeanFactory (ConfigurableListableBeanFactory factory) throws BeansException {
        Session session = sessionFactory.openSession ();
        try {
            String sql = "SELECT LABEL_KEY as NAME, LABEL_CONTENT as CONTENT, LANGUAGE as LANGUAGE FROM " + tableName + " ORDER BY LANGUAGE";
            Query query = session.createSQLQuery (sql);
            query.setResultTransformer (Transformers.ALIAS_TO_ENTITY_MAP);
            List list = query.list ();

            Map<String, Locale> groups = new HashMap<String, Locale> ();

            DatabaseResourceAdapter adapter = new DatabaseResourceAdapter ();
            for (Object o : list) {
                Map map = (Map) o;
                String name = (String) map.get ("NAME");
                String content = (String) map.get ("CONTENT");
                String language = (String) map.get ("LANGUAGE");
                Locale locale;
                if (!groups.containsKey (language)) {
                    locale = LocaleUtil.parseLocale (language);
                    groups.put (language, locale);
                } else {
                    locale = groups.get (language);
                }

                DatabaseResourceBundle bundle = adapter.getResourceBundle (locale);
                if (bundle == null) {
                    bundle = new DatabaseResourceBundle ();
                    adapter.addResourceBundle (locale, bundle);
                }
                bundle.setString (name, content);
            }
            DatabaseResourceManager manager = DatabaseWebResourceManagerFactory.getResourceManager ();
            manager.setAdapter (adapter);
        } finally {
            session.close ();
        }
    }
}
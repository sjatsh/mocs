package smtcl.mocs.services.authority.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dreamwork.i18n.IResourceAdapter;
import org.dreamwork.i18n.IResourceManager;
import org.dreamwork.i18n.LocaleUtil;
import org.dreamwork.jasmine2.configure.JasmineConfig;
import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.Sort;
import org.dreamwork.util.StringUtil;

import smtcl.mocs.common.authority.i18n.DatabaseWebResourceManagerFactory;
import smtcl.mocs.dao.authority.ICommonDao;
import smtcl.mocs.pojos.authority.Application;
import smtcl.mocs.pojos.authority.Module;
import smtcl.mocs.pojos.authority.Page;
import smtcl.mocs.services.authority.IMenuService;
import smtcl.mocs.utils.authority.AuthorityUtil;
import smtcl.mocs.utils.authority.HelperUtil;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-5
 * Time: 下午4:54

 -- 获取有权限的菜单
 SELECT x.APP_ID as id, x.URL as url, y.LABEL_CONTENT as label
   FROM T_APPLICATION x, T_LABEL y
  WHERE x.LABEL_KEY = y.LABEL_KEY
    AND y.LANGUAGE = :language
    AND EXISTS (
        SELECT d.APP_ID
 		   FROM R_ROLE_PAGE a,
 		        R_USER_ROLE b,
 		        T_PAGE c,
 		        T_MODULE d
 		  WHERE b.USERID = :userId
 		    AND a.ROLEID = b.ROLEID
 		    AND a.PAGE_ID = c.PAGE_ID
 		    and c.MODULE_ID = d.MODULE_ID
 		    and d.APP_ID = x.APP_ID)

 */
public class MenuServiceImpl extends GenericServiceSpringImpl<Object, String> implements IMenuService {
    private ICommonDao commonDao;

    public ICommonDao getCommonDao () {
        return commonDao;
    }

    @SuppressWarnings ("unchecked")
    public void setCommonDao (ICommonDao commonDao) {
        this.commonDao = commonDao;
        super.setDao (commonDao);
    }

    /**
     * 获取当前用户拥有权限的应用系统.
     *
     * @param userId   当前用户ID
     * @param language 当前语言版本.  可为<code>null</code>，此时，使用系统的默认语言版本
     * @return 所有有权限的应用系统，在当前的区域设置.
     */
    @Override
    public List<Application> getApplications (String userId, String language) {
        return getSpecApplications(userId, null, language);
    }
    
    /**
     * 获取当前用户拥有权限的指定系统的应用系统.
     * 返回的字典中，
     *     键值为 id 的是应用系统在数据库中的主键
     *     键值为 label 的是应用系统当前语系中的文本
     *     键值为 url 的是应用系统的默认页面URL
     * @param userId 当前用户ID
     * @param appId 系统ID;
     * @param language 当前语言版本.  可为<code>null</code>，此时，使用系统的默认语言版本
     * @return 所有有权限的应用系统，在当前的区域设置.
     */
    @Override
    public List<Application> getSpecApplications (String userId, String appId, String language) {
        Locale locale;
        if (StringUtil.isEmpty (language))
            locale = JasmineConfig.i18n.defaultLocale;
        else
            locale = LocaleUtil.parseLocale (language);

        Collection<Parameter> params = new HashSet<Parameter> ();
        params.add (new Parameter ("language", locale.toString (), Operator.EQ));

        String sql =
            "SELECT x.APP_ID as ID, x.URL as URL, y.LABEL_CONTENT as LABEL\n" +
            "  FROM T_APPLICATION x, T_LABEL y\n" +
            " WHERE x.LABEL_KEY = y.LABEL_KEY\n" +
            "   AND y.LANGUAGE = :language";
        if(StringUtils.isNotBlank(appId)){
        	sql += "	AND x.APP_ID ='" + appId + "'";
        }
        if (!AuthorityUtil.isAdmin (userId)) {
            sql +=
            "   AND EXISTS (\n" +
            "       SELECT d.APP_ID \n" +
            "       FROM R_ROLE_PAGE a, \n" +
            "            R_USER_ROLE_ORGGROUP b,\n" +
            "            T_PAGE c,\n" +
            "            T_MODULE d\n" +
            "      WHERE b.USERID = :userId\n" +
            "        AND a.ROLEID = b.ROLEID\n" +
            "        AND a.PAGE_ID = c.PAGE_ID\n" +
            "        AND c.MODULE_ID = d.MODULE_ID\n" +
            "        AND d.APP_ID = x.APP_ID)\n";
            params.add (new Parameter ("userId", userId, Operator.EQ));
        }
        sql += " ORDER BY x.SEQ ASC";

        List list = dao.executeNativeQuery (sql, params);
        List<Application> result = new ArrayList<Application> (list.size ());
        for (Object o : list) {
            Map map = (Map) o;
            Application application = new Application ();
            application.setAppId ((String) map.get ("ID"));
            application.setLabel ((String) map.get ("LABEL"));
            application.setUrl ((String) map.get ("URL"));
            result.add (application);
        }
        return result;
    }

    /**
     * 获取当前用户在指定应用系统内有权限的模块.
     *
     * @param userId        当前用户
     * @param applicationId 指定的应用系统
     * @param language      指定的语言版本.  可为<code>null</code>，此时，使用系统的默认语言版本
     * @return 模块列表
     */
    @Override
    public List<Module> getMenu (String userId, String applicationId, String language) {
        Locale locale;
        if (StringUtil.isEmpty (language)) locale = JasmineConfig.i18n.defaultLocale;
        else locale = LocaleUtil.parseLocale (language);

        if (AuthorityUtil.isAdmin (userId))
            return getAdminMenu (applicationId, locale);

        return getUserMenu (userId, applicationId, locale);
    }

    @Override
    public String getNodeLabel (String nodeId) {
        return AuthorityUtil.getNodeTxt (String.valueOf(nodeId));
    }

    private List<Module> getAdminMenu (String applicationId, Locale locale) {
        Parameter appId = new Parameter ("app.appId", "appId", applicationId, Operator.EQ);
        List<Module> modules = commonDao.find (
                Module.class,
                Arrays.asList (new Sort ("SEQ", Sort.Direction.ASC)),
                appId
        );
        IResourceManager manager = DatabaseWebResourceManagerFactory.getResourceManager ();
        IResourceAdapter adapter = manager.getResourceAdapter ("");
        for (Module module : modules) {
            String label = adapter.getString (locale, module.getModuleName ());
            module.setLabel (label);
            for (Page page : module.getPages ()) {
                label = adapter.getString (locale, page.getPageName ());
                page.setLabel (label);
            }
            module.setPages(HelperUtil.orderPage(module.getPages()));
        }

        return modules;
    }

    private List<Module> getUserMenu (String userId, String applicationId, Locale locale) {
        Parameter p_user_id = new Parameter ("userId", userId, Operator.EQ);
        Parameter p_app_id = new Parameter ("appId", applicationId, Operator.EQ);
        List<Map<String, Object>> temp = commonDao.listByQueryName ("getMenuByUserId", p_user_id, p_app_id);
        Map<String, Module> group = new HashMap<String, Module> ();

        IResourceManager manager = DatabaseWebResourceManagerFactory.getResourceManager ();
        IResourceAdapter adapter = manager.getResourceAdapter ("");

        for (Map<String, Object> map : temp) {
            Module module = (Module) map.get ("module");
            String moduleId = module.getModuleId ();
            if (!group.containsKey (moduleId)) {
                group.put (moduleId, module);
                String label = adapter.getString (locale, module.getModuleName ());
                module.setLabel (label);
                module.setPages (new ArrayList<Page> ());
            } else {
                module = group.get (moduleId);
            }

            Page page = new Page ();
            page.setPageId ((String) map.get ("id"));
            page.setUrl ((String) map.get ("url"));
            String key = (String) map.get ("label_key");
            page.setLabel (adapter.getString (locale, key));
            page.setSeq((Integer)map.get("seq"));

            module.getPages ().add (page);
        }
        
        //排序;
        for (Module m : group.values()) {
            m.setPages(HelperUtil.orderPage(m.getPages()));
        }

        List<Module> modules = new ArrayList<Module> ();
        modules.addAll (group.values ());
        return modules;
    }
}
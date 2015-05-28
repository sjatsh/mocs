package smtcl.mocs.common.authority.i18n;

import org.dreamwork.i18n.IResourceBundle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-11-1
 * Time: 下午8:13
 */
public class DatabaseResourceBundle implements IResourceBundle {
    private Map<String, String> map = new HashMap<String, String> ();
    /**
     * 获取适配器中，指定名称的区域资源。若为找到，则返�?code>defaultValue</code>
     *
     * @param name         资源名称
     * @param defaultValue 默认�?
     * @return 资源
     */
    @Override
    public String getString (String name, String defaultValue) {
        if (map.containsKey (name)) return map.get (name);
        return defaultValue;
    }

    @Override
    public boolean isResourcePresent (String name) {
        return map.containsKey (name);
    }

    public void setString (String name, String value) {
        map.put (name, value);
    }
}

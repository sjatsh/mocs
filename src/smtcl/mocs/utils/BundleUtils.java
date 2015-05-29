package smtcl.mocs.utils;

import org.dreamwork.i18n.MissingResourceException;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by ËÎ¿­°º
 * CreateTime:2015/3/13
 * Description:
 */
public class BundleUtils {

    private static final String NO_RESOURCE_FOUND = "no_resouce_found";

    private static final String MESSAGE_BUNDLE = "smtcl.mocs.common.messages";

    public static String getStringFromBundle(String key,Locale locale,String fileName) {
        ResourceBundle bundle = getBundle(locale,fileName);
        return getStringSafely(bundle, key);
    }

    private static ResourceBundle getBundle(Locale locale,String fileName) {
        ClassLoader ldr = Thread.currentThread().getContextClassLoader();
        if (ldr==null) ldr = ClassLoader.getSystemClassLoader();
        String path = MESSAGE_BUNDLE+"."+fileName;
        return ResourceBundle.getBundle(path, locale, ldr);
    }

    private static String getStringSafely(ResourceBundle bundle, String key) {
        String resource;
        try {
            resource = bundle.getString(key);
        } catch (MissingResourceException mrex) {
            resource = NO_RESOURCE_FOUND + key;
        }
        return resource;
    }
}

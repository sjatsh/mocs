package smtcl.mocs.utils.authority;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-9-29
 * Time: ÏÂÎç4:06
 */
public class RequestWrapper {
    private Map<String, Object> cache = new HashMap<String, Object> ();

    public RequestWrapper (HttpServletRequest request) {
        parse (request);
    }

    public String[] getParameterValues (String name) {
        Object o = cache.get (name);
        if (o == null) return new String[0];

        return (String[]) o;
    }

    public Set<String> keySet () {
        return cache.keySet ();
    }

    @SuppressWarnings ("unchecked")
    private void parse (HttpServletRequest request) {
        Enumeration<String> en = request.getParameterNames ();
        for (; en.hasMoreElements (); ) {
            String name = en.nextElement ();
            String[] values = request.getParameterValues (name);
            /*if (values.length == 1) cache.put (name, values [0]);
            else if (values.length > 0) */
            cache.put (name, values);
        }
    }
}
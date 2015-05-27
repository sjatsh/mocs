package smtcl.mocs.common.authority.sso;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-10-9
 * Time: обнГ4:51
 */
public class JsonSerializer implements ISsoSerializer {
    private static final Gson g = new GsonBuilder ().setDateFormat ("yyyy-MM-dd").create ();
    /* (non-Javadoc)
	 * @see com.swg.authority.sso.ISsoSerializer#serialize(java.lang.Object)
	 */
    public String serialize (Object target) {
        return g.toJson (target);
    }
}

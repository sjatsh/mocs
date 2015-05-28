package smtcl.mocs.common.authority.sso;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import smtcl.mocs.pojos.authority.User;
import smtcl.mocs.utils.authority.IConsant;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-10-9
 * Time: 下午5:05
 */
public class JsonUserReceiver implements ISsoReceiver {
    private static final Gson g = new GsonBuilder ().setDateFormat ("yyyy-MM-dd").create ();

    
    public void receive (HttpSession session, String target) {
        User user = g.fromJson (target, User.class);
        session.setAttribute (IConsant.SESSION_LOGINUSER_KEY, user);
    }
}
package smtcl.mocs.common.authority.sso;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-10-9
 * Time: обнГ4:42
 */
public interface ISsoReceiver {
    void receive (HttpSession session, String target);
}
package smtcl.mocs.common.authority.sso.taglib;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-10-9
 * Time: обнГ6:10
 */
public class NameValuePair implements Serializable {
    private String name;
    private String value;

    public NameValuePair (String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getValue () {
        return value;
    }

    public void setValue (String value) {
        this.value = value;
    }
}
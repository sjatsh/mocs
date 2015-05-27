package smtcl.mocs.common.authority.sso.taglib;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-10-8
 * Time: обнГ4:47
 */
public class SsoVariableTEI extends TagExtraInfo {
    @Override
    public VariableInfo[] getVariableInfo (TagData data) {
        String name = data.getAttributeString ("var");
        String type = data.getAttributeString ("type");

        return new VariableInfo[] {
            new VariableInfo (name, type, true, VariableInfo.NESTED)
        };
    }
}
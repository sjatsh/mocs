/**
 * com.swg.authority.component.web Out.java
 */
package smtcl.mocs.beans.authority.component;



import java.io.IOException;
import java.io.PrintWriter;

import org.dreamwork.jasmine2.web.controls.WebControl;

/**
 * @author gaokun
 * @create Oct 11, 2012 5:06:06 PM
 */
public class Out extends WebControl {
	
	@Override
	protected void beginTag (PrintWriter writer) throws IOException {
		try{
			onDataBind(null);
			Object value = attrs.get("value");
			writer.write(value == null ? "" : value.toString());
		}catch(Exception e){
			
		}
    }
}

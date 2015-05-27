package smtcl.mocs.beans.converter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class DateTableConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		int length=value.length();
		String tt=value;
		if(length>10){
			tt=tt.substring(0,10);
		}
		return tt;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		int length=value.toString().length();
		String tt=value.toString();
		if(length>10){
			tt=tt.substring(0,10)+"...";
		}
		return tt;
	}

}

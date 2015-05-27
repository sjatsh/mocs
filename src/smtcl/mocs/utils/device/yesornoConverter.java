package smtcl.mocs.utils.device;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter("YesOrNo")
public class yesornoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent vomponent, String newValue) {
		if(newValue.equals("1")){
			return "ÊÇ";
		}else if(newValue.equals("0")){
			return "·ñ";
		}else{
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent vomponent, Object object) {
		if(object.toString().equals("1")){
			return "ÊÇ";
		}else if(object.toString().equals("0")){
			return "·ñ";
		}else{
			return null;
		}
		//return object.toString();
	}

}

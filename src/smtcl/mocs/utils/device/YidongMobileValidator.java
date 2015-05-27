package smtcl.mocs.utils.device;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;



public class YidongMobileValidator implements Validator {
	
	@Override
	public void validate(FacesContext facesContext, UIComponent component,
			Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		if (value == null || value.equals("")) {
			return;
		}
		String mobile = (String) value;
		if (StringUtils.checkYiDongMobile(mobile) == false) {
			FacesMessage message = new javax.faces.application.FacesMessage(
					FacesMessage.SEVERITY_ERROR, "", "手机格式有误");
			throw new ValidatorException(message);
		}
	}
}

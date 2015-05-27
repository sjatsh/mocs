package smtcl.mocs.beans.validator;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("a3mocsNumber")
public class NumberValidator implements Validator{
	
	public void validate(FacesContext context, UIComponent ui, Object value)
			throws ValidatorException {
		 Pattern pattern = Pattern.compile("[0-9]*"); 
		    String tt=value.toString();
		    System.out.println(pattern.matcher(tt).matches());
	    if (!pattern.matcher(tt).matches()){ 
	    	  
	       FacesMessage message = new FacesMessage(); 
	       message.setDetail("Ö»ÔÊÐíÌî³äÊý×Ö£¡"); 
	       message.setSummary(""); 
	       message.setSeverity(FacesMessage.SEVERITY_ERROR); 
	       //FacesContext.getCurrentInstance().addMessage(null, message);
	      throw new ValidatorException(message); 
	    } 
		
	}

}

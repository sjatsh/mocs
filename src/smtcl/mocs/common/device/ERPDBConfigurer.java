package smtcl.mocs.common.device;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import smtcl.mocs.utils.device.DESUtil;


public class ERPDBConfigurer extends PropertyPlaceholderConfigurer {
	
	 @Override  
	 protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)  
	            throws BeansException {  
	        String userName = props.getProperty("erp.connection.username"); 
	        LogHelper.log("erp.connection.username", userName);
	        if (userName != null ) {  
	        	LogHelper.log("erp.connection.username", DESUtil.getDesString(userName));
	            props.setProperty("erp.connection.username", DESUtil.getDesString(userName));  
	        }  
	        String password = props.getProperty("erp.connection.password");  
	        if (password != null ) {  
	           	LogHelper.log("erp.connection.password", DESUtil.getDesString(password));
	            props.setProperty("erp.connection.password", DESUtil.getDesString(password));  
	        }  
	        super.processProperties(beanFactory, props);  
	    }  

}

package smtcl.mocs.common.device;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import smtcl.mocs.utils.device.DESUtil;


public class DBConfigurer extends PropertyPlaceholderConfigurer {
	
	 @Override  
	 protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)  
	            throws BeansException {  
	        String userName = props.getProperty("connection.username"); 
	        LogHelper.log("connection.username111", userName);
	        if (userName != null ) {  
	        	LogHelper.log("connection.username", DESUtil.getDesString(userName));
	            props.setProperty("connection.username", DESUtil.getDesString(userName));  
	        }  
	        String password = props.getProperty("connection.password");  
	        if (password != null ) {  
	           	LogHelper.log("connection.password", DESUtil.getDesString(password));
	            props.setProperty("connection.password", DESUtil.getDesString(password));  
	        }  
	        super.processProperties(beanFactory, props);  
	    }  

}

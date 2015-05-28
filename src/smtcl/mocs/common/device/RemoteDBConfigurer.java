package smtcl.mocs.common.device;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import smtcl.mocs.utils.device.DESUtil;


public class RemoteDBConfigurer extends PropertyPlaceholderConfigurer {
	
	 @Override  
	 protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)  
	            throws BeansException {  
	        String userName = props.getProperty("remote.connection.username"); 
	        LogHelper.log("remote.connection.username", userName);
	        if (userName != null ) {  
	        	LogHelper.log("remote.connection.username", DESUtil.getDesString(userName));
	            props.setProperty("remote.connection.username", DESUtil.getDesString(userName));  
	        }  
	        String password = props.getProperty("remote.connection.password");  
	        if (password != null ) {  
	           	LogHelper.log("remote.connection.password", DESUtil.getDesString(password));
	            props.setProperty("remote.connection.password", DESUtil.getDesString(password));  
	        }  
	        super.processProperties(beanFactory, props);  
	    }  

}

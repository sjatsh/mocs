package smtcl.mocs.common.device;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import sense.Living1.LivingKey;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.DESUtil;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 数据验证
 * @author YT
 *
 */
public class DBConfigurer extends PropertyPlaceholderConfigurer {
	
	 @Override  
	 protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)  
	            throws BeansException { 
		    String key=LivingKey.getKey();		    
		    if(!StringUtils.isEmpty(key))
		    	  Constants.KEY_STRING=key;
		    else if(!Constants.IS_DEV_MODE) 
		    	{ 
		    	    System.out.println("密码狗工具验证失败!"); 
		    	    LogHelper.log("验证错误", "密码狗工具验证失败!");
		    	    System.exit(0);
		    	}
	        String userName = props.getProperty("connection.username"); 
	        if (userName != null ) {  
	            props.setProperty("connection.username", DESUtil.getDesString(userName));  
	        }  
	        String password = props.getProperty("connection.password");  
	        if (password != null ) {  
	            props.setProperty("connection.password", DESUtil.getDesString(password));  
	        } 
	        String url = props.getProperty("connection.url");  
	        if (url != null ) {  
	            props.setProperty("connection.url", DESUtil.getDesString(url));  
	        }  
	        String drive = props.getProperty("connection.driver");  
	        if (drive != null ) {  
	            props.setProperty("connection.driver", DESUtil.getDesString(drive));  
	        }  
	        String dialect = props.getProperty("connection.dialect");  
	        if (dialect != null ) {  
	            props.setProperty("connection.dialect", DESUtil.getDesString(dialect));  
	        }  
	        super.processProperties(beanFactory, props);  
	    }  

}

package smtcl.mocs.dao.device.impl;

public class CustomerContextHolder {
	public final static String dataSource0 = "dataSource0";
    public final static String dataSource1 = "dataSource1";
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
    
    public static void setCustomerType(String customerType) {  
        contextHolder.set(customerType);  
    }  
      
    public static String getCustomerType() {  
        return contextHolder.get();  
    }  
      
    public static void clearCustomerType() {  
        contextHolder.remove();  
    } 
}

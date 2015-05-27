package smtcl.mocs.model;
import java.util.List;  
import javax.faces.model.ListDataModel;  
import org.primefaces.model.SelectableDataModel;  

import smtcl.mocs.pojos.job.TMemberInfo;


public class MemberDataModel extends ListDataModel<TMemberInfo> implements SelectableDataModel<TMemberInfo> {    
	  
    public MemberDataModel() {  
    }  
  
    public MemberDataModel(List<TMemberInfo> data) {  
        super(data);  
    }  
      
    @Override  
    public TMemberInfo getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<TMemberInfo> cars = (List<TMemberInfo>) getWrappedData();  
          
        for(TMemberInfo car : cars) {  
            if(car.getId().toString().equals(rowKey))  
                return car;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(TMemberInfo car) {  
        return car.getId();  
    }  
}  
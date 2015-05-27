package smtcl.mocs.model;
import java.util.List;  
import javax.faces.model.ListDataModel;  
import org.primefaces.model.SelectableDataModel;  

import smtcl.mocs.pojos.job.TPositionInfo;

public class PositionDataModel extends ListDataModel<TPositionInfo> implements SelectableDataModel<TPositionInfo> {    
	  
    public PositionDataModel() {  
    }  
  
    public PositionDataModel(List<TPositionInfo> data) {  
        super(data);  
    }  
      
    @Override  
    public TPositionInfo getRowData(String rowKey) {  
        List<TPositionInfo> cars = (List<TPositionInfo>) getWrappedData();  
          
        for(TPositionInfo car : cars) { 
        	
            if(car.getPositionId().toString().equals(rowKey))  
                return car;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(TPositionInfo car) {  
        return car.getPositionId();  
    }  
}  
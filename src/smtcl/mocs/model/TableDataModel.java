package smtcl.mocs.model;

import java.util.List;
import java.util.Map;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class TableDataModel extends ListDataModel<Map<String,Object>> implements SelectableDataModel<Map<String,Object>> {    
	  
    public TableDataModel() {  
    }  
  
    public TableDataModel(List<Map<String,Object>> data) {  
        super(data);  
    }  
      
    @Override  
    public Map<String,Object> getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<Map<String,Object>> cars = (List<Map<String,Object>>) getWrappedData();  
          
        for(Map<String,Object> car : cars) {  
        	System.out.println(car.get("id").toString()+"=="+rowKey+" "+car.get("id").toString().equals(rowKey));
            if(car.get("id").toString().equals(rowKey))  
                return car;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Map<String,Object> car) {  
        return car.get("id");
    }  
}  
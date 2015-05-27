package smtcl.mocs.model;

import java.util.List;

import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class TProcessFixturetypeDataModel extends ListDataModel<TProcessFixturetypeModel> implements SelectableDataModel<TProcessFixturetypeModel>{
	 	public TProcessFixturetypeDataModel() {  
	    }  
	  
	    public TProcessFixturetypeDataModel(List<TProcessFixturetypeModel> data) {  
	        super(data);  
	    }  
	      
	    @Override  
	    public TProcessFixturetypeModel getRowData(String rowKey) {  
	        List<TProcessFixturetypeModel> cars = (List<TProcessFixturetypeModel>) getWrappedData();  
	        for(TProcessFixturetypeModel car : cars) {  
	            if(car.getId().toString().equals(rowKey))  
	                return car;  
	        }  
	        return null;  
	    }  
	    @Override  
	    public Object getRowKey(TProcessFixturetypeModel car) {  
	        return car.getId();  
	    }  
}

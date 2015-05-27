package smtcl.mocs.model;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import smtcl.mocs.pojos.job.TQualityParam;

public class TQualityParamDataModel extends ListDataModel<TQualityParam> implements SelectableDataModel<TQualityParam>{
	 	public TQualityParamDataModel() {  
	    }  
	  
	    public TQualityParamDataModel(List<TQualityParam> data) {  
	        super(data);  
	    }  
	      
	    @Override  
	    public TQualityParam getRowData(String rowKey) {  
	        List<TQualityParam> cars = (List<TQualityParam>) getWrappedData();  
	        for(TQualityParam car : cars) {  
	            if(car.getId().toString().equals(rowKey))  
	                return car;  
	        }  
	        return null;  
	    }  
	    @Override  
	    public Object getRowKey(TQualityParam car) {  
	        return car.getId();  
	    }  
}

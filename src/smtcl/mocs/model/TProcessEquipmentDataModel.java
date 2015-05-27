package smtcl.mocs.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import smtcl.mocs.pojos.job.TProcessEquipment;
import smtcl.mocs.pojos.job.TQualityParam;

public class TProcessEquipmentDataModel extends ListDataModel<TProcessEquipmentModel> implements SelectableDataModel<TProcessEquipmentModel>{
	 	public TProcessEquipmentDataModel() {  				  
	    }  
	  
	    public TProcessEquipmentDataModel(List<TProcessEquipmentModel> data) {  
	        super(data);  
	    }  
	      
	    @Override  
	    public TProcessEquipmentModel getRowData(String rowKey) {  
	        List<TProcessEquipmentModel> cars = (List<TProcessEquipmentModel>) getWrappedData();  
	        for(TProcessEquipmentModel car : cars) {  
	            if((car.getId()+"").equals(rowKey))  
	                return car;  
	        }  
	        return null;  
	    }  
	    @Override  
	    public Object getRowKey(TProcessEquipmentModel car) {  
	        return car.getId();  
	    }  
}

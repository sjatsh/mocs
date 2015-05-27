package smtcl.mocs.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class ProductProcessDataModel  extends ListDataModel<ProductProcessModel> implements SelectableDataModel<ProductProcessModel>{
	public ProductProcessDataModel() {  				  
    }  
  
    public ProductProcessDataModel(List<ProductProcessModel> data) {  
        super(data);  
    }  
    
    @Override  
    public ProductProcessModel getRowData(String rowKey) {  
        List<ProductProcessModel> cars = (List<ProductProcessModel>) getWrappedData();  
        for(ProductProcessModel car : cars) {  
            if(car.getId().equals(rowKey))  
                return car;  
        }  
        return null;  
    }  
    @Override  
    public Object getRowKey(ProductProcessModel car) {  
        return car.getId();  
    }  
}

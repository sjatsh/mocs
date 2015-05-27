package smtcl.mocs.model;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class TPartBasicInfoDataModel extends ListDataModel<TPartBasicInfoModel> implements SelectableDataModel<TPartBasicInfoModel>{
	public TPartBasicInfoDataModel() {  				  
    }  
  
    public TPartBasicInfoDataModel(List<TPartBasicInfoModel> data) {  
        super(data);  
    }  
      
    @Override  
    public TPartBasicInfoModel getRowData(String rowKey) {  
        List<TPartBasicInfoModel> cars = (List<TPartBasicInfoModel>) getWrappedData();  
        for(TPartBasicInfoModel car : cars) {  
            if((car.getId()+"").equals(rowKey))  
                return car;  
        }  
        return null;  
    }  
    @Override  
    public Object getRowKey(TPartBasicInfoModel car) {  
        return car.getId();  
    }  
}

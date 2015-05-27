package smtcl.mocs.model;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;


public class ProcessQualityDataModel extends ListDataModel<ProcessQualityModel> implements SelectableDataModel<ProcessQualityModel>{
	public ProcessQualityDataModel() {  
		
    }  
  
    public ProcessQualityDataModel(List<ProcessQualityModel> data) {  
        super(data);  
    }  
    
	@Override
	public ProcessQualityModel getRowData(String Id) {
		List<ProcessQualityModel> parts = (List<ProcessQualityModel>)getWrappedData();  
        for(ProcessQualityModel material : parts) {  
            if(material.getId().toString().equals(Id))  
                return material;  
        }  
		return null;
	}

	@Override
	public Object getRowKey(ProcessQualityModel material) {
		return material.getId();
	}
}
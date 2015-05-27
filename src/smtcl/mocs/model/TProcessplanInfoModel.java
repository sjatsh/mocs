package smtcl.mocs.model;

import java.util.List;

import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import smtcl.mocs.pojos.job.TProcessplanInfo;

public class TProcessplanInfoModel extends ListDataModel<TProcessplanInfo> implements SelectableDataModel<TProcessplanInfo> {
	
	public TProcessplanInfoModel() {  
		
    }  
  
    public TProcessplanInfoModel(List<TProcessplanInfo> data) {  
        super(data);  
    }  
    
	@Override
	public TProcessplanInfo getRowData(String Id) {
		List<TProcessplanInfo> parts = (List<TProcessplanInfo>)getWrappedData();  
        for(TProcessplanInfo part : parts) {  
        	System.out.println(part.getId());
            if(part.getId().toString().equals(Id))  
                return part;  
        }  
        
		return null;
	}

	@Override
	public Object getRowKey(TProcessplanInfo part) {
		return part.getId();
	}
}

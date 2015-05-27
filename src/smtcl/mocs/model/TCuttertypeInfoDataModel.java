package smtcl.mocs.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;


public class TCuttertypeInfoDataModel extends ListDataModel<CuttertypeModel> implements SelectableDataModel<CuttertypeModel>{
	
	public TCuttertypeInfoDataModel() {  
		
    }  
  
    public TCuttertypeInfoDataModel(List<CuttertypeModel> data) {  
        super(data);  
    }  
    
	@Override
	public CuttertypeModel getRowData(String Id) {
		List<CuttertypeModel> parts = (List<CuttertypeModel>)getWrappedData();  
        for(CuttertypeModel part : parts) {  
        	System.out.println(part.getId());
            if(part.getId().equals(Id))  
                return part;  
        }  
        
		return null;
	}
	
	@Override
	public Object getRowKey(CuttertypeModel part) {	
		return part.getId();
	}
}

package smtcl.mocs.beans.jobplan;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import smtcl.mocs.pojos.job.TProcessInfo;



public class TProcessInfoDataModel extends ListDataModel<TProcessInfo> implements SelectableDataModel<TProcessInfo> {

	public TProcessInfoDataModel() {  
		
    }  
  
    public TProcessInfoDataModel(List<TProcessInfo> data) {  
        super(data);  
    }  
    
	@SuppressWarnings("unchecked")
	@Override
	public TProcessInfo getRowData(String Id) {
		List<TProcessInfo> process = (List<TProcessInfo>)getWrappedData();  
        for(TProcessInfo proces : process) {  
        	System.out.println(proces.getId());
            if(proces.getId().toString().equals(Id))  
                return proces;  
        }  
        
		return null;
	}

	@Override
	public Object getRowKey(TProcessInfo part) {
		return part.getId();
	}
	

}

package smtcl.mocs.beans.equipment;

import java.util.List;
import java.util.Map;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class TEquipmentCostInfoDataModel extends ListDataModel<Map<String, Object>> implements SelectableDataModel<Map<String, Object>> {
	
public TEquipmentCostInfoDataModel() {  
		
    }  
  
    public TEquipmentCostInfoDataModel(List<Map<String, Object>> data) {  
        super(data);  
    }  
    
	@Override
	public Map<String, Object> getRowData(String Id) {
		List<Map<String, Object>> process = (List<Map<String, Object>>)getWrappedData();  
        for(Map<String, Object> proces : process) {  
            if(proces.get("id").toString().equals(Id))  
                return proces;  
        }  
        
		return null;
	}

	@Override
	public Object getRowKey(Map<String, Object> part) {
		return part.get("id");
	}

}

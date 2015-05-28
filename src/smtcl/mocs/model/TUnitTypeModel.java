package smtcl.mocs.model;

import java.util.List;
import java.util.Map;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;



public class TUnitTypeModel extends ListDataModel<Map<String, Object>> implements SelectableDataModel<Map<String, Object>>{
	
	
     public TUnitTypeModel(){
		
	}
	
	public TUnitTypeModel (List<Map<String, Object>> data){
		    super(data);
	}

	@Override
	public Map<String, Object> getRowData(String Id) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> unitlist = (List<Map<String, Object>>) getWrappedData();
		for (Map<String, Object> tun : unitlist) {
			if(tun.get("Id").toString().equals(Id)){
				return tun;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Map<String, Object> tun) {
		// TODO Auto-generated method stub
		return tun.get("Id");
	}
	

	
}

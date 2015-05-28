package smtcl.mocs.model;

import java.util.List;
import java.util.Map;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class TUnitInfoModel extends ListDataModel<Map<String, Object>> implements SelectableDataModel<Map<String, Object>>{

	
	 public TUnitInfoModel(){
			
	 }
		
     public TUnitInfoModel (List<Map<String, Object>> data){
			    super(data);
	 }
	
	
	@Override
	public Map<String, Object> getRowData(String Id) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> unitInfolist = (List<Map<String, Object>>) getWrappedData();
		for (Map<String, Object> tunInfo : unitInfolist) {
			if(tunInfo.get("Id").toString().equals(Id)){
				return tunInfo;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Map<String, Object> tunInfo) {
		// TODO Auto-generated method stub
		return tunInfo.get("Id");
	}

}

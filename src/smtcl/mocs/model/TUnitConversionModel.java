package smtcl.mocs.model;

import java.util.List;
import java.util.Map;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class TUnitConversionModel extends ListDataModel<Map<String, Object>> implements SelectableDataModel<Map<String, Object>>{

	
	  public TUnitConversionModel(){
			
		}
		
	 public TUnitConversionModel (List<Map<String, Object>> data){
			    super(data);
	   }

    @Override
	public Map<String, Object> getRowData(String Id) {
		// TODO Auto-generated method stub
    	List<Map<String, Object>> UConversionlist = (List<Map<String, Object>>) getWrappedData();
		for (Map<String, Object> tucon : UConversionlist) {
			if(tucon.get("Id").toString().equals(Id)){
				return tucon;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(Map<String, Object> tucon) {
		// TODO Auto-generated method stub
		return tucon.get("Id");
	}

}

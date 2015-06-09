package smtcl.mocs.beans.jobplan;

import java.util.List;
import java.util.Map;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class TJobDispatchDataModel extends ListDataModel<Map<String,Object>> implements SelectableDataModel<Map<String,Object>> {

	public TJobDispatchDataModel() {

	}

	public TJobDispatchDataModel(List<Map<String,Object>> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getRowData(String Id) {
		List<Map<String,Object>> process = (List<Map<String,Object>>) getWrappedData();
		for (Map<String,Object> proces : process) {
			System.out.println(proces.get("id"));
			if (proces.get("id").toString().equals(Id))
				return proces;
		}

		return null;
	}

	@Override
	public Object getRowKey(Map<String,Object> part) {
		return part.get("id");
	}

	
}

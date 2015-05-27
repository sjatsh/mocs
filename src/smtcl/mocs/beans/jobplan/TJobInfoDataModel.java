package smtcl.mocs.beans.jobplan;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import smtcl.mocs.pojos.job.TJobInfo;

public class TJobInfoDataModel extends ListDataModel<TJobInfo> implements SelectableDataModel<TJobInfo> {

	public TJobInfoDataModel() {

	}

	public TJobInfoDataModel(List<TJobInfo> data) {
		super(data);
	}

	@Override
	public TJobInfo getRowData(String Id) {
		List<TJobInfo> process = (List<TJobInfo>) getWrappedData();
		for (TJobInfo proces : process) {
			System.out.println(proces.getId());
			if (proces.getId().toString().equals(Id))
				return proces;
		}

		return null;
	}

	@Override
	public Object getRowKey(TJobInfo part) {
		return part.getId();
	}

}

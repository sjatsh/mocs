package smtcl.mocs.model.erp;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import smtcl.mocs.pojos.job.TPartTypeInfo;


/**
 * dataTable数据辅助类，用于监听，鼠标当前选中的列
 * @author songkaiang
 *
 */
public class JobPlanDataModel extends ListDataModel<JobPlanImportInfo> implements SelectableDataModel<JobPlanImportInfo>{
	
	public JobPlanDataModel() {  
		
    }  
  
    public JobPlanDataModel(List<JobPlanImportInfo> data) {  
        super(data);  
    }  

	@SuppressWarnings("unchecked")
	@Override
	public JobPlanImportInfo getRowData(String Id) {
		List<JobPlanImportInfo> parts = (List<JobPlanImportInfo>)getWrappedData();  
        for(JobPlanImportInfo part : parts) {  
            if(part.getJobno().toString().equals(Id))  
                return part;  
        }  
        
		return null;
	}
	@Override
	public Object getRowKey(JobPlanImportInfo part) {
		return part.getJobno();
	}
}

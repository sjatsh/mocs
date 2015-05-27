package smtcl.mocs.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import smtcl.mocs.pojos.job.TProcessInfo;

/**
 * 零件工序配置接收行模型
 * @创建时间 2013-07-12
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
public class PartProcessDataModel extends ListDataModel<TProcessInfo> implements SelectableDataModel<TProcessInfo>{
	public PartProcessDataModel() {  
		
    }  
  
    public PartProcessDataModel(List<TProcessInfo> data) {  
        super(data);  
    }  
    
	@Override
	public TProcessInfo getRowData(String Id) {
		List<TProcessInfo> parts = (List<TProcessInfo>)getWrappedData();  
        for(TProcessInfo part : parts) {  
        	System.out.println(part.getId());
            if(part.getId().toString().equals(Id))  
                return part;  
        }  
        
		return null;
	}

	@Override
	public Object getRowKey(TProcessInfo part) {
		return part.getId();
	}
}

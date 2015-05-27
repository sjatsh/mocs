package smtcl.mocs.model;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import smtcl.mocs.pojos.job.TPartTypeInfo;

/**
 * 零件类型接受选中数据辅助类
 * @创建时间 2013-07-11
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
public class PartTypeDataModel extends ListDataModel<TPartTypeInfo> implements SelectableDataModel<TPartTypeInfo>{
	public PartTypeDataModel() {  
		
    }  
  
    public PartTypeDataModel(List<TPartTypeInfo> data) {  
        super(data);  
    }  
    
	@Override
	public TPartTypeInfo getRowData(String Id) {
		List<TPartTypeInfo> parts = (List<TPartTypeInfo>)getWrappedData();  
        for(TPartTypeInfo part : parts) {  
        	System.out.println(part.getId());
            if(part.getId().toString().equals(Id))  
                return part;  
        }  
        
		return null;
	}

	@Override
	public Object getRowKey(TPartTypeInfo part) {
		return part.getId();
	}
}

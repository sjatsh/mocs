package smtcl.mocs.model;

import java.util.List;  
import javax.faces.model.ListDataModel;   
import org.primefaces.model.SelectableDataModel; 
import smtcl.mocs.pojos.job.TPartClassInfo;

/**
 * 零件类型配置辅助类，用于监听，鼠标当前选中的列
 * @创建时间 2013-07-09
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
public class PartClassDataModel extends ListDataModel<TPartClassInfo> implements SelectableDataModel<TPartClassInfo>{
	
	public PartClassDataModel() {  
		
    }  
  
    public PartClassDataModel(List<TPartClassInfo> data) {  
        super(data);  
    }  
    
	@Override
	public TPartClassInfo getRowData(String Id) {
		List<TPartClassInfo> parts = (List<TPartClassInfo>)getWrappedData();  
        for(TPartClassInfo part : parts) {  
        	System.out.println(part.getId());
            if(part.getId().toString().equals(Id))  
                return part;  
        }  
        
		return null;
	}

	@Override
	public Object getRowKey(TPartClassInfo part) {
		return part.getId();
	}

}

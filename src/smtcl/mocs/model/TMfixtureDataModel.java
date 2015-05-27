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
public class TMfixtureDataModel extends ListDataModel<TFixtureModel> implements SelectableDataModel<TFixtureModel>{
	
	public TMfixtureDataModel() {  
		
    }  
  
    public TMfixtureDataModel(List<TFixtureModel> data) {  
        super(data);  
    }  
    
	@Override
	public TFixtureModel getRowData(String Id) {
		List<TFixtureModel> parts = (List<TFixtureModel>)getWrappedData();  
        for(TFixtureModel part : parts) {  
        	System.out.println(part.getId());
            if(part.getId().equals(Id))  
                return part;  
        }  
        
		return null;
	}

	@Override
	public Object getRowKey(TFixtureModel part) {
		return part.getId();
	}

}

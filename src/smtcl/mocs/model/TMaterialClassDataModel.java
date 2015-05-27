package smtcl.mocs.model;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;


/**
 * 零件工序配置接收行模型
 * @创建时间 2013-07-12
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
public class TMaterialClassDataModel extends ListDataModel<TMaterialClassModel> implements SelectableDataModel<TMaterialClassModel>{
	public TMaterialClassDataModel() {  
		
    }  
  
    public TMaterialClassDataModel(List<TMaterialClassModel> data) {  
        super(data);  
    }  
    
	@Override
	public TMaterialClassModel getRowData(String Id) {
		List<TMaterialClassModel> parts = (List<TMaterialClassModel>)getWrappedData(); 
		System.out.println(Id);
        for(TMaterialClassModel material : parts) {  
            if(material.getId().equals(Id))  
                return material;  
        }  
		return null;
	}

	@Override
	public Object getRowKey(TMaterialClassModel material) {
		return material.getId();
	}
}

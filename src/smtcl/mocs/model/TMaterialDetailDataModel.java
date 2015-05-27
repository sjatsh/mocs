package smtcl.mocs.model;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;


/**
 * 零件工序配置接收行模型
 * @创建时间 2013-07-12
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
public class TMaterialDetailDataModel extends ListDataModel<TMaterailTypeInfo> implements SelectableDataModel<TMaterailTypeInfo>{
	public TMaterialDetailDataModel() {  
		
    }  
  
    public TMaterialDetailDataModel(List<TMaterailTypeInfo> data) {  
        super(data);  
    }  
    
	@Override
	public TMaterailTypeInfo getRowData(String Id) {
		List<TMaterailTypeInfo> parts = (List<TMaterailTypeInfo>)getWrappedData();  
        for(TMaterailTypeInfo material : parts) {  
            if(material.getId().toString().equals(Id))  
                return material;  
        }  
        
		return null;
	}

	@Override
	public Object getRowKey(TMaterailTypeInfo material) {
		return material.getId();
	}
}

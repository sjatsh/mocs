package smtcl.mocs.model;

import java.util.List;  
import java.util.Map;

import javax.faces.model.ListDataModel;   

import org.primefaces.model.SelectableDataModel; 

import smtcl.mocs.pojos.job.TPartClassInfo;

/**
 * erp��������ģ��
 * @����ʱ�� 2014-07-11
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
public class MaterialDataModel extends ListDataModel<Map<String,Object>> implements SelectableDataModel<Map<String,Object>>{
	
	public MaterialDataModel() {  
		
    }  
  
    public MaterialDataModel(List<Map<String,Object>> data) {  
        super(data);  
    }  
    
	@Override
	public Map<String,Object> getRowData(String Id) {
		List<Map<String,Object>> parts = (List<Map<String,Object>>)getWrappedData();  
        for(Map<String,Object> part : parts) {  
            if(part.get("itemCode").toString().equals(Id))  
                return part;  
        }  
        
		return null;
	}

	@Override
	public Object getRowKey(Map<String,Object> part) {
		return part.get("itemCode");
	}

}

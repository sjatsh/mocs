package smtcl.mocs.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

/**
 * ��λ����ѡ�����ݸ�����
 * @����ʱ�� 2014-09-02
 * @���� FW
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
public class MaterialPositionDataModel extends ListDataModel<TMaterialPositionModel> implements SelectableDataModel<TMaterialPositionModel>{
	public MaterialPositionDataModel() {  
		
    }  
  
    public MaterialPositionDataModel(List<TMaterialPositionModel> data) {  
        super(data);  
    }  
    
	@Override
	public TMaterialPositionModel getRowData(String Id) {
		List<TMaterialPositionModel> storages = (List<TMaterialPositionModel>)getWrappedData();  
        for(TMaterialPositionModel part : storages) {  
        	System.out.println(part.getId());
            if(part.getId().toString().equals(Id))  
                return part;  
        }  
        
		return null;
	}

	@Override
	public Object getRowKey(TMaterialPositionModel storage) {
		return storage.getId();
	}
}

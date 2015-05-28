package smtcl.mocs.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

/**
 * �ⷿ����ѡ�����ݸ�����
 * @����ʱ�� 2014-09-02
 * @���� FW
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
public class StorageInfoDataModel extends ListDataModel<TStorageInfoModel> implements SelectableDataModel<TStorageInfoModel>{
	public StorageInfoDataModel() {  
		
    }  
  
    public StorageInfoDataModel(List<TStorageInfoModel> data) {  
        super(data);  
    }  
    
	@Override
	public TStorageInfoModel getRowData(String Id) {
		List<TStorageInfoModel> storages = (List<TStorageInfoModel>)getWrappedData();  
        for(TStorageInfoModel part : storages) {  
        	System.out.println(part.getId());
            if(part.getId().toString().equals(Id))  
                return part;  
        }  
        
		return null;
	}

	@Override
	public Object getRowKey(TStorageInfoModel storage) {
		return storage.getId();
	}
}

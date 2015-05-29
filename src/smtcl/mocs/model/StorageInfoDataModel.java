package smtcl.mocs.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

/**
 * 库房接受选中数据辅助类
 * @创建时间 2014-09-02
 * @作者 FW
 * @修改者： 
 * @修改日期： 
 * @修改说明
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

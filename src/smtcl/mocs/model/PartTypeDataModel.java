package smtcl.mocs.model;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;
import smtcl.mocs.pojos.job.TPartTypeInfo;

/**
 * ������ͽ���ѡ�����ݸ�����
 * @����ʱ�� 2013-07-11
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
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

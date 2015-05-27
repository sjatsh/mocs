package smtcl.mocs.model;

import java.util.List;  
import javax.faces.model.ListDataModel;   
import org.primefaces.model.SelectableDataModel; 
import smtcl.mocs.pojos.job.TPartClassInfo;

/**
 * ����������ø����࣬���ڼ�������굱ǰѡ�е���
 * @����ʱ�� 2013-07-09
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
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

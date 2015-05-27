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

package smtcl.mocs.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProcessmaterialInfo;

/**
 * ���Ϲ������ý�����ģ��
 * @����ʱ�� 2013-07-12
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
public class TProcessmaterialInfoDataModel extends ListDataModel<MaterialsModel> implements SelectableDataModel<MaterialsModel>{
	public TProcessmaterialInfoDataModel() {  
		
    }  
  
    public TProcessmaterialInfoDataModel(List<MaterialsModel> data) {  
        super(data);  
    }  
    
	@Override
	public MaterialsModel getRowData(String Id) {
		List<MaterialsModel> parts = (List<MaterialsModel>)getWrappedData();  
        for(MaterialsModel part : parts) {  
        	System.out.println(part.getId());
            if(part.getId().equals(Id))  
                return part;  
        }  
        
		return null;
	}

	@Override
	public Object getRowKey(MaterialsModel part) {
		return part.getId();
	}
}

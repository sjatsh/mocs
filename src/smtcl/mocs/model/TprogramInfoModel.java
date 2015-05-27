package smtcl.mocs.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import smtcl.mocs.pojos.job.TProgramInfo;

/**
 * �������ø�����
 * @����ʱ�� 2013-08-09
 * @���� wangkaili
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
public class TprogramInfoModel extends ListDataModel<TProgramInfo> implements SelectableDataModel<TProgramInfo>{

	public TprogramInfoModel(){
		
	}
	
	public TprogramInfoModel(List<TProgramInfo> data){
		super(data);
	}

	@Override
	public TProgramInfo getRowData(String progNo) {
		List<TProgramInfo> tprolist = (List<TProgramInfo>)getWrappedData();
		for (TProgramInfo tpro : tprolist) {
			if(tpro.getProgNo().toString().equals(progNo)){
				return tpro;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(TProgramInfo tpro) {	
		return tpro.getProgNo();
	}

}

package smtcl.mocs.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import smtcl.mocs.pojos.job.TProgramInfo;

/**
 * 程序配置辅助类
 * @创建时间 2013-08-09
 * @作者 wangkaili
 * @修改者： 
 * @修改日期： 
 * @修改说明
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

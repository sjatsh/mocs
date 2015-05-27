package smtcl.mocs.common.device.pages;

import java.util.List;
/**
 * 
 * 结合richfaces实现分页,页面数据封装
 * @作者：YuTao  
 * @创建时间：2012-11-22 下午5:21:40
 * @修改者： 
 * @修改日期： 
 * @修改说明： 
 * @version V1.0
 */

public class DataPage {	

	private int datasetSize;

	private int startRow;

	private List data;

	/**
	 * 
	 * @param datasetSize	
	 * @param startRow
	 * @param data
	 */
	public DataPage(int datasetSize, int startRow, List data) {
		this.datasetSize = datasetSize;
		this.startRow = startRow;
		this.data = data;

	}
	
	public int getDatasetSize() {
		return datasetSize;
	}

	public int getStartRow() {
		return startRow;
	}

	
	public List getData() {
		return data;
	}

}
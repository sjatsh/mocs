package smtcl.mocs.common.device.pages;

import java.util.List;
/**
 * 
 * ���richfacesʵ�ַ�ҳ,ҳ�����ݷ�װ
 * @���ߣ�YuTao  
 * @����ʱ�䣺2012-11-22 ����5:21:40
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵���� 
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
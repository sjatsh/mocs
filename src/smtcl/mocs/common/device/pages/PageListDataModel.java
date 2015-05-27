package smtcl.mocs.common.device.pages;

import java.io.Serializable;

import javax.faces.model.DataModel;


/**
 * 
 * ҳ������ģ��
 * @���ߣ�YuTao
 * @����ʱ�䣺2012-11-22 ����5:25:19
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */

public abstract class PageListDataModel extends DataModel implements
		Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private int pageSize;
	private int rowIndex;
	private DataPage page;

	/**
	 * ����һ��datamodel��ʾ���ݣ�ÿһҳָ����������
	 */
	public PageListDataModel(int pageSize) {
		super();
		this.pageSize = pageSize;
		this.rowIndex = -1;
		this.page = null;
	}

	/**
	 * ������ͨ��һ���ص�fetchData������ȡ����������ȷָ��һ���б�
	 */
	public void setWrappedData(Object o) {
		if (o instanceof DataPage) {
			this.page = (DataPage) o;
		} else {
			throw new UnsupportedOperationException(" setWrappedData ");
		}
	}

	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * ָ���ġ���ǰ�С���DataSet��.��ע�⣬UIData������ظ������������ͨ��getRowData��ȡʹ�ñ��еĶ���
	 * 
	 */
	public void setRowIndex(int index) {
		rowIndex = index;
	}

	/**
	 * ���ص������ݼ���С����ֻ�ǵ������ڵ�ǰҳ�棡 �� ��
	 */
	public int getRowCount() {
		return getPage().getDatasetSize();
	}

	/**
	 * ����DataPage����;���Ŀǰ��û��fetchPageһ������ע�⣬�Ⲣ����֤datapage���ذ�����ǰrowIndex��;
	 * ��getRowData ��
	 */
	private DataPage getPage() {
		if (page != null) {
			return page;
		}
		int rowIndex = getRowIndex();
		int startRow = rowIndex;
		if (rowIndex == -1) {
			startRow = 0;
		}
		page = fetchPage(startRow, pageSize);
		return page;
	}

	public void setPage(DataPage page) {
		this.page = page;
	}

	/**
	 * ������Ӧ�Ķ��󵽵�ǰrowIndex �����ĿǰDataPage���󻺴治������ָ����ͨ��fetchPage�ҵ��ʵ���ҳ�档
	 */
	public Object getRowData() {
		if (rowIndex < 0) {
			throw new IllegalArgumentException(
					" Invalid rowIndex for PagedListDataModel; not within page ");
		}

		if (page == null) {
			page = fetchPage(rowIndex, pageSize);
		}
		int datasetSize = page.getDatasetSize();
		int startRow = page.getStartRow();
		int nRows = page.getData().size();
		int endRow = startRow + nRows;
		if (rowIndex >= datasetSize) {
			throw new IllegalArgumentException(" Invalid rowIndex ");
		}
		if (rowIndex < startRow) {
			page = fetchPage(rowIndex, pageSize);
			startRow = page.getStartRow();
		} else if (rowIndex >= endRow) {
			page = fetchPage(rowIndex, pageSize);
			startRow = page.getStartRow();
		}
		return page.getData().get(rowIndex - startRow);
	}

	public Object getWrappedData() {
		return page.getData();
	}

	/**
	 * ���rowIndex��ֵ��DataSet�У������棻��ע�⣬��������ƥ��һ�ж����ǵ�ǰDataPage���棻
	 * ����ǵĻ���getRowData��������ҳ��Ҳ�����fetchData������
	 */
	public boolean isRowAvailable() {
		DataPage page = getPage();
		if (page == null) {
			return false;
		}
		int rowIndex = getRowIndex();
		if (rowIndex < 0) {
			return false;
		} else if (rowIndex >= page.getDatasetSize()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * �ܹ�bean����ʵ�ִ˷���
	 */
	public abstract DataPage fetchPage(int startRow, int pageSize);

	/**
	 * ����ɾ���Ȳ�����������ı��б���ҷ����б�ҳ�ģ�����ô˷���������ˢ���б�
	 */
	public void refresh() {
		if (this.page != null) {
			this.page = null;
			getPage();
		}
	}
}

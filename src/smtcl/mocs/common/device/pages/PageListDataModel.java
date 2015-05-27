package smtcl.mocs.common.device.pages;

import java.io.Serializable;

import javax.faces.model.DataModel;


/**
 * 
 * 页面数据模型
 * @作者：YuTao
 * @创建时间：2012-11-22 下午5:25:19
 * @修改者：
 * @修改日期：
 * @修改说明：
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
	 * 创建一个datamodel显示数据，每一页指定的行数。
	 */
	public PageListDataModel(int pageSize) {
		super();
		this.pageSize = pageSize;
		this.rowIndex = -1;
		this.page = null;
	}

	/**
	 * 数据是通过一个回调fetchData方法获取，而不是明确指定一个列表
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
	 * 指定的“当前行“在DataSet中.请注意，UIData组件会重复调用这个方法通过getRowData获取使用表中的对象！
	 * 
	 */
	public void setRowIndex(int index) {
		rowIndex = index;
	}

	/**
	 * 返回的总数据集大小（不只是的行数在当前页面！ ） 。
	 */
	public int getRowCount() {
		return getPage().getDatasetSize();
	}

	/**
	 * 返回DataPage对象;如果目前还没有fetchPage一个。请注意，这并不保证datapage返回包含当前rowIndex行;
	 * 见getRowData 。
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
	 * 返回相应的对象到当前rowIndex 。如果目前DataPage对象缓存不包括该指数则通过fetchPage找到适当的页面。
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
	 * 如果rowIndex的值在DataSet中，返回真；请注意，它可能是匹配一行而不是当前DataPage缓存；
	 * 如果是的话当getRowData调用请求页面也会调用fetchData方法。
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
	 * 受管bean必须实现此方法
	 */
	public abstract DataPage fetchPage(int startRow, int pageSize);

	/**
	 * 进行删除等操作后会立即改变列表项并且返回列表页的，请调用此方法，用于刷新列表。
	 */
	public void refresh() {
		if (this.page != null) {
			this.page = null;
			getPage();
		}
	}
}

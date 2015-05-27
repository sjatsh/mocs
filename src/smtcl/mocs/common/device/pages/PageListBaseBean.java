package smtcl.mocs.common.device.pages;


import smtcl.mocs.utils.device.Constants;
 /**
  * 
  * 后台bean继承的页面封装类
  * @作者：YuTao  
  * @创建时间：2012-11-22 下午5:22:57
  * @修改者： 
  * @修改日期： 
  * @修改说明： 
  * @version V1.0
  */
public abstract class PageListBaseBean {

	/**
	 * 当前页码，跟dataSroller的page属性绑定
	 */
	protected int scrollerPage = 1;

	/**
	 * 当前页面大小
	 */
	protected int pageSize =Constants.PAGE_SIZE;
	protected int pageSize2=13;
	protected PageListDataModel defaultDataModel;
	
	protected PageListDataModel extendDataModel;

	public int getScrollerPage() {
		return scrollerPage;
	}

	public void setScrollerPage(int scrollerPage) {
		this.scrollerPage = scrollerPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public abstract PageListDataModel getDefaultDataModel();
	
	public abstract PageListDataModel getExtendDataModel();
}



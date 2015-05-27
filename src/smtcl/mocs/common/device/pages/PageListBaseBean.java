package smtcl.mocs.common.device.pages;


import smtcl.mocs.utils.device.Constants;
 /**
  * 
  * ��̨bean�̳е�ҳ���װ��
  * @���ߣ�YuTao  
  * @����ʱ�䣺2012-11-22 ����5:22:57
  * @�޸��ߣ� 
  * @�޸����ڣ� 
  * @�޸�˵���� 
  * @version V1.0
  */
public abstract class PageListBaseBean {

	/**
	 * ��ǰҳ�룬��dataSroller��page���԰�
	 */
	protected int scrollerPage = 1;

	/**
	 * ��ǰҳ���С
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



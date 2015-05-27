package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.common.device.pages.DataPage;
import smtcl.mocs.common.device.pages.PageListBaseBean;
import smtcl.mocs.common.device.pages.PageListDataModel;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.device.IProductService;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;


/**
 * ��Ʒ�����嵥
 * @���ߣ�JiangFeng
 * @����ʱ�䣺2012-11-19 ����15:55:16
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "ProductTypeManagementBean")
@ViewScoped
public class ProductTypeManagementBean extends PageListBaseBean implements Serializable {
	/**
	 * ��Ʒ�ͺ�
	 */
	private String proType;
	/**
	 * ��ʼʱ��
	 */
	private Date startTime;
	/**
	 * ����ʱ��
	 */
	private Date endTime;
	/**
	 * ��Ʒ�ӿ�ʵ��
	 */
	@ManagedProperty(value = "#{productService}")
	private IProductService productService;
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService"); // ��ȡע��
	/**
	 * ������ݼ�
	 */
	private IDataCollection<Map<String, Object>> results;
	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr; 
	/**
	 * ��ǰ�ڵ���ӽڵ� ID ����
	 */
	private String nodeIdList;
	
	public String getNodeStr() {
		TreeNode currentNode = (TreeNode) FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			this.nodeStr = currentNode.getNodeId(); // �������ڵ��ֵ������ǰ�ڵ�nodeStr
			nodeIdList = organizationService.getAllTNodesId(currentNode);
			defaultDataModel = null;
			this.getDefaultDataModel();
		}
		findProListByProTypeAction();
		return nodeStr;
	}
	
	/**
	 * ҳ������ѯ��ť��Ӧ
	 */
	public void findProListByProTypeAction() {
		this.defaultDataModel = null;
	}
	
	/**
	 * ��Ʒ��Ϣ��д�ķ�ҳ����
	 */
	@Override
	public PageListDataModel getDefaultDataModel() {
		if (nodeIdList != null) {
			if (defaultDataModel == null) {
				defaultDataModel = new PageListDataModel(pageSize) {
					@Override
					public DataPage fetchPage(int startRow, int pageSize) {
						Collection<Parameter> parameters = new HashSet<Parameter>();
						if (startTime != null) {
							parameters.add(new Parameter("a.uprodOnlineTime", "starttime", startTime, Operator.GE));
						}
						if (endTime != null) {
							parameters.add(new Parameter("a.uprodOnlineTime", "endTime", endTime, Operator.LE));
						}
						if (!StringUtils.isEmpty(proType)) {
							parameters.add(new Parameter("a.uprodType", "uprodType", "%" + proType.toUpperCase() + '%', Operator.LIKE));
						}
						results = productService.getProductType(startRow / pageSize + 1, pageSize, parameters, nodeIdList);
						return new DataPage(results.getTotalRows(), startRow, results.getData());
					}
				};
			}
			return defaultDataModel;
		}
		return null;
	}

	@Override
	public PageListDataModel getExtendDataModel() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * �޲ι��캯��
	 */
	public ProductTypeManagementBean() {
		if (null == startTime && null == endTime) {
			Date[] datea = StringUtils.convertDatea(1);
			startTime = datea[0];
			endTime = datea[1];
		}
	}

/*-----------------------------------------------------------------------------*/

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public IDataCollection<Map<String, Object>> getResults() {
		return results;
	}

	public void setResults(IDataCollection<Map<String, Object>> results) {
		this.results = results;
	}

	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}
	
}

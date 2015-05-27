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
 * 产品类型清单
 * @作者：JiangFeng
 * @创建时间：2012-11-19 下午15:55:16
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "ProductTypeManagementBean")
@ViewScoped
public class ProductTypeManagementBean extends PageListBaseBean implements Serializable {
	/**
	 * 产品型号
	 */
	private String proType;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 产品接口实例
	 */
	@ManagedProperty(value = "#{productService}")
	private IProductService productService;
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService"); // 获取注入
	/**
	 * 结果数据集
	 */
	private IDataCollection<Map<String, Object>> results;
	/**
	 * 当前节点
	 */
	private String nodeStr; 
	/**
	 * 当前节点的子节点 ID 集合
	 */
	private String nodeIdList;
	
	public String getNodeStr() {
		TreeNode currentNode = (TreeNode) FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			this.nodeStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
			nodeIdList = organizationService.getAllTNodesId(currentNode);
			defaultDataModel = null;
			this.getDefaultDataModel();
		}
		findProListByProTypeAction();
		return nodeStr;
	}
	
	/**
	 * 页面点击查询按钮响应
	 */
	public void findProListByProTypeAction() {
		this.defaultDataModel = null;
	}
	
	/**
	 * 产品信息重写的分页方法
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
	 * 无参构造函数
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

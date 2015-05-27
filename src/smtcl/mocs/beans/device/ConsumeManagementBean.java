package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.common.device.pages.DataPage;
import smtcl.mocs.common.device.pages.PageListBaseBean;
import smtcl.mocs.common.device.pages.PageListDataModel;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.device.IResourceService;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;


/**
 * 用户查询消耗品库存、消耗品使用情况
 * @作者：LiuChuanzhen
 * @创建时间：2012-11-13 下午4:25:38
 * @修改者： yutao JiangFeng
 * @修改日期： 2012-12-1
 * @修改说明：
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "ConsumeManagementBean")
@ViewScoped
public class ConsumeManagementBean extends PageListBaseBean implements Serializable {
	/**
	 * 开始时间
	 */
	private Date starttime;
	/**
	 * 结束时间
	 */
	private Date endtime;
	/**
	 * 当前用户选择的消耗品类型
	 */
	private String uresoType;
	/**
	 * 消耗品类型集合
	 */
	private List<SelectItem> selectUresoType = new ArrayList<SelectItem>();
	/**
	 *  资源接口实例
	 */
	@ManagedProperty(value = "#{resourceService}")
	private IResourceService resourceService;
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");
	/**
	 * 结果数据集
	 */
	private IDataCollection<Map<String, Object>> result;
	/**
	 * 结果数据集
	 */
	private IDataCollection<Map<String, Object>> consumeUserList;
	/**
	 * 当前节点
	 */
	private String nodeStr; 
	/**
	 * 当前节点的子节点 ID信息
	 */
	private String nodeIdList;
	
	public List<SelectItem> getSelectUresoType() {
		selectUresoType.clear();
		selectUresoType.add(new SelectItem("--所有--"));
		List<String> urType = resourceService.getAllConType();
		for(String str : urType){
			selectUresoType.add(new SelectItem(str));
		}
		return selectUresoType;
	}
	
	public String getNodeStr() {
		TreeNode currentNode = (TreeNode) FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			this.nodeStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
			nodeIdList = organizationService.getAllTNodesId(currentNode);
			defaultDataModel = null;
			this.getDefaultDataModel();
		}
		return nodeStr;
	}
	
	/**
	 * 夹具库存情况重写的分页方法
	 */
	@Override
	public PageListDataModel getDefaultDataModel() {
		if (nodeIdList != null) {
			if (defaultDataModel == null) {
				defaultDataModel = new PageListDataModel(pageSize) {
					public DataPage fetchPage(int startRow, int pageSize) {
						String searchType = uresoType;
						if ("--所有--".equals(uresoType)) {
							searchType = null;
						}
						Collection<Parameter> parameters = new HashSet<Parameter>();
						if (!StringUtils.isEmpty(searchType)
								&& searchType != null) {
							parameters.add(new Parameter("a.uresoType", "uresoType", searchType, Operator.EQ));
						}
						result = resourceService.getConsumeStorage(startRow / pageSize + 1, pageSize, parameters, nodeIdList);
						return new DataPage(result.getTotalRows(), startRow, result.getData());
					}
				};
			}
			return defaultDataModel;
		}
		return null;
	}

	/**
	 * 夹具使用信息重写的分页方法
	 */
	@Override
	public PageListDataModel getExtendDataModel() {
		if (nodeIdList != null) {
			if (extendDataModel == null) {
				extendDataModel = new PageListDataModel(pageSize) {
					public DataPage fetchPage(int startRow, int pageSize) {
						Collection<Parameter> parameters = new HashSet<Parameter>();
						String searchType = uresoType;
						if ("--所有--".equals(uresoType)) {
							searchType = null;
						}
						if (!StringUtils.isEmpty(searchType) && searchType != null) {
							parameters.add(new Parameter("a.uresoType", "uresoType", searchType, Operator.EQ));
						}
						if (null != starttime) {
							parameters.add(new Parameter("a.beginTime", "beginTime", starttime, Operator.GE));
						}
						if (null != endtime) {
							parameters.add(new Parameter("a.endTime", "endTime", endtime, Operator.LE));
						}
						consumeUserList = resourceService.getConsumeUse( startRow / pageSize + 1, pageSize, parameters, nodeIdList);
						return new DataPage(consumeUserList.getTotalRows(), startRow, consumeUserList.getData());
					}
				};
			}
			return extendDataModel;
		}
		return null;
	}

	/**
	 * 消耗品库存页面查询按钮响应
	 */
	public void getConsumeStorageAction() {
		this.defaultDataModel = null;
	}

	/**
	 * 消耗品使用信息页面查询按钮响应
	 */
	public void getConsumeUseAction() {
		this.extendDataModel = null;
	}

	/**
	 * 无参构造函数，
	 * @描述TUserResourceBean.java 
	 */
	public ConsumeManagementBean() {
		if (null == starttime && null == endtime) {
			Date[] datea = StringUtils.convertDatea(1);
			starttime = datea[0];
			endtime = datea[1];
		}
	}
	
	/*----------------------------------------------------------------------------------*/
	
	public void setSelectUresoType(List<SelectItem> selectUresoType) {
		this.selectUresoType = selectUresoType;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public IDataCollection<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(IDataCollection<Map<String, Object>> result) {
		this.result = result;
	}

	public IDataCollection<Map<String, Object>> getConsumeUserList() {
		return consumeUserList;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

	public void setConsumeUserList( IDataCollection<Map<String, Object>> consumeUserList) {
		this.consumeUserList = consumeUserList;
	}

	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public String getUresoType() {
		return uresoType;
	}

	public void setUresoType(String uresoType) {
		this.uresoType = uresoType;
	}

}
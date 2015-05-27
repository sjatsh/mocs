package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.device.IResourceService;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;


/**
 * 用户查询当前的工装夹具清单，夹具使用历史 
 * @作者：LiuChuanzhen
 * @创建时间：2012-11-19 下午15:55:16
 * @修改者：JiangFeng
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "ClampManagementBean")
@ViewScoped
public class ClampManagementBean extends PageListBaseBean implements Serializable {
	/**
	 * 当前节点
	 */
	private String nodeStr; 
	/**
	 * 当前节点的子节点 ID信息
	 */
	private String nodeIdList; 
	/**
	 * 夹具编号
	 */
	private String ufixNo;
	/**
	 * 夹具名称
	 */
	private String ufixName;
	/**
	 * 当前点击的夹具名称
	 */
	private String selectUfixName;
	/**
	 * 当前点击的夹具编号
	 */
	private String selectUfixNo;
	/**
	 * 当前选择的机床名称
	 */
	private String machineName;
	/**
	 * 机床名称集合
	 */
	private List<SelectItem> machineNameList = new ArrayList<SelectItem>();
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory .getBean("organizationService");
	/**
	 * 资源接口实例
	 */
	@ManagedProperty(value = "#{resourceService}")
	private IResourceService resourceService;
	/**
	 * 设备接口实例
	 */
	@ManagedProperty(value = "#{deviceService}")
	private IDeviceService deviceService;
	/**
	 * 夹具信息结果数据集
	 */
	private IDataCollection<Map<String, Object>> results;
	/**
	 * 夹具历史信息结果数据集
	 */
	private IDataCollection<Map<String, Object>> rs;

	@SuppressWarnings("unchecked")
	public List<SelectItem> getMachineNameList() {
		machineNameList.clear();
		machineNameList.add(new SelectItem("--所有--"));
		if (null != nodeStr && !"".equals(nodeStr)) {
			List<TEquipmentInfo> nlist = deviceService.getNodesAllEquName(nodeIdList);
			for (TEquipmentInfo teinfo : nlist) {
				machineNameList.add(new SelectItem(teinfo.getEquSerialNo(),teinfo.getEquName()));
			}
		}
		return machineNameList;
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
	 *夹具重写的分页方法
	 */
	@Override
	public PageListDataModel getDefaultDataModel() {
		if (nodeIdList != null) {
			if (defaultDataModel == null) {
				defaultDataModel = new PageListDataModel(pageSize) {
					public DataPage fetchPage(int startRow, int pageSize) {
						Collection<Parameter> parameters = new HashSet<Parameter>();
						// 判断选择的下拉框是否为默认
						if (!StringUtils.isEmpty(machineName) && machineName.equals("--所有--")) {
							machineName = null;
						}
						if (!StringUtils.isEmpty(ufixName)) {
							parameters.add(new Parameter("a.ufixName", "ufixName", "%" + ufixName.trim() + "%", Operator.LIKE));
						}
						if (!StringUtils.isEmpty(machineName)) {
							parameters.add(new Parameter(" a.ufixUsingMachine", "machineName", machineName.trim() + "%", Operator.LIKE));
						}
						results = resourceService.getClampList(startRow/ pageSize + 1, pageSize, parameters,nodeIdList);
						
						return new DataPage(results.getTotalRows(), startRow,results.getData());	
					}
				};
			}
		}
			return defaultDataModel;
	}

	/**
	 * 具体夹具对应的历史信息重写的分页方法
	 */
	@Override
	public PageListDataModel getExtendDataModel() {
		if (nodeIdList != null) {
			if (extendDataModel == null) {
				extendDataModel = new PageListDataModel(pageSize) {
					public DataPage fetchPage(int startRow, int pageSize) {
						Collection<Parameter> parameters = new HashSet<Parameter>();
						if (!StringUtils.isEmpty(ufixNo)) {
							parameters.add(new Parameter("a.ufixNo", "ufixNo","%" + ufixNo.toUpperCase() + '%',Operator.LIKE));
						}
						rs = resourceService.getClampUse(startRow / pageSize+ 1, pageSize, parameters, nodeIdList);

						return new DataPage(rs.getTotalRows(), startRow,rs.getData());
					}
				};
			}
		}
		return extendDataModel;
	}
	
	/**
	 * 夹具信息页面点击查看夹具历史信息响应
	 * @param ufNo 点击的夹具编号
	 * @param uName 点击的夹具名称
	 */
	public void detailClick(String ufNo,String uName) {
		this.selectUfixName=uName;
		this.ufixNo = ufNo;
		extendDataModel=null;
	}

	/**
	 * 夹具历史信息页面查询按钮响应
	 */
	public void fixByNoAction() {
		this.extendDataModel = null;
	}

	/**
	 * 夹具信息页面点击查询按钮响应
	 */
	public void fixsearchAction() {
		this.defaultDataModel = null;
	}

	/**
	 * 无参构造函数
	 * @类名：TestBean.java
	 */
	public ClampManagementBean() {
		
	}

	/*-----------------------------------------------------------------------------*/
	public String getUfixNo() {
		return ufixNo;
	}

	public void setUfixNo(String ufixNo) {
		this.ufixNo = ufixNo;
	}

	public String getUfixName() {
		return ufixName;
	}

	public void setUfixName(String ufixName) {
		this.ufixName = ufixName;
	}

	public IDataCollection<Map<String, Object>> getRs() {
		return rs;
	}

	public IDataCollection<Map<String, Object>> getResults() {
		return results;
	}
	
	public String getSelectUfixName() {
		return selectUfixName;
	}

	public void setSelectUfixName(String selectUfixName) {
		this.selectUfixName = selectUfixName;
	}

	public String getSelectUfixNo() {
		return selectUfixNo;
	}

	public void setSelectUfixNo(String selectUfixNo) {
		this.selectUfixNo = selectUfixNo;
	}

	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public IDeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public void setResults(IDataCollection<Map<String, Object>> results) {
		this.results = results;
	}

	public void setRs(IDataCollection<Map<String, Object>> rs) {
		this.rs = rs;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public void setMachineNameList(List<SelectItem> machineNameList) {
		this.machineNameList = machineNameList;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}
}

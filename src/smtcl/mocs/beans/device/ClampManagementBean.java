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
 * �û���ѯ��ǰ�Ĺ�װ�о��嵥���о�ʹ����ʷ 
 * @���ߣ�LiuChuanzhen
 * @����ʱ�䣺2012-11-19 ����15:55:16
 * @�޸��ߣ�JiangFeng
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "ClampManagementBean")
@ViewScoped
public class ClampManagementBean extends PageListBaseBean implements Serializable {
	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr; 
	/**
	 * ��ǰ�ڵ���ӽڵ� ID��Ϣ
	 */
	private String nodeIdList; 
	/**
	 * �о߱��
	 */
	private String ufixNo;
	/**
	 * �о�����
	 */
	private String ufixName;
	/**
	 * ��ǰ����ļо�����
	 */
	private String selectUfixName;
	/**
	 * ��ǰ����ļо߱��
	 */
	private String selectUfixNo;
	/**
	 * ��ǰѡ��Ļ�������
	 */
	private String machineName;
	/**
	 * �������Ƽ���
	 */
	private List<SelectItem> machineNameList = new ArrayList<SelectItem>();
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory .getBean("organizationService");
	/**
	 * ��Դ�ӿ�ʵ��
	 */
	@ManagedProperty(value = "#{resourceService}")
	private IResourceService resourceService;
	/**
	 * �豸�ӿ�ʵ��
	 */
	@ManagedProperty(value = "#{deviceService}")
	private IDeviceService deviceService;
	/**
	 * �о���Ϣ������ݼ�
	 */
	private IDataCollection<Map<String, Object>> results;
	/**
	 * �о���ʷ��Ϣ������ݼ�
	 */
	private IDataCollection<Map<String, Object>> rs;

	@SuppressWarnings("unchecked")
	public List<SelectItem> getMachineNameList() {
		machineNameList.clear();
		machineNameList.add(new SelectItem("--����--"));
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
			this.nodeStr = currentNode.getNodeId(); // �������ڵ��ֵ������ǰ�ڵ�nodeStr
			nodeIdList = organizationService.getAllTNodesId(currentNode);
			defaultDataModel = null;
			this.getDefaultDataModel();
		}
		return nodeStr;
	}
	
	/**
	 *�о���д�ķ�ҳ����
	 */
	@Override
	public PageListDataModel getDefaultDataModel() {
		if (nodeIdList != null) {
			if (defaultDataModel == null) {
				defaultDataModel = new PageListDataModel(pageSize) {
					public DataPage fetchPage(int startRow, int pageSize) {
						Collection<Parameter> parameters = new HashSet<Parameter>();
						// �ж�ѡ����������Ƿ�ΪĬ��
						if (!StringUtils.isEmpty(machineName) && machineName.equals("--����--")) {
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
	 * ����о߶�Ӧ����ʷ��Ϣ��д�ķ�ҳ����
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
	 * �о���Ϣҳ�����鿴�о���ʷ��Ϣ��Ӧ
	 * @param ufNo ����ļо߱��
	 * @param uName ����ļо�����
	 */
	public void detailClick(String ufNo,String uName) {
		this.selectUfixName=uName;
		this.ufixNo = ufNo;
		extendDataModel=null;
	}

	/**
	 * �о���ʷ��Ϣҳ���ѯ��ť��Ӧ
	 */
	public void fixByNoAction() {
		this.extendDataModel = null;
	}

	/**
	 * �о���Ϣҳ������ѯ��ť��Ӧ
	 */
	public void fixsearchAction() {
		this.defaultDataModel = null;
	}

	/**
	 * �޲ι��캯��
	 * @������TestBean.java
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

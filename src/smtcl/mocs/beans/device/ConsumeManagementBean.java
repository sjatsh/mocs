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
 * �û���ѯ����Ʒ��桢����Ʒʹ�����
 * @���ߣ�LiuChuanzhen
 * @����ʱ�䣺2012-11-13 ����4:25:38
 * @�޸��ߣ� yutao JiangFeng
 * @�޸����ڣ� 2012-12-1
 * @�޸�˵����
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "ConsumeManagementBean")
@ViewScoped
public class ConsumeManagementBean extends PageListBaseBean implements Serializable {
	/**
	 * ��ʼʱ��
	 */
	private Date starttime;
	/**
	 * ����ʱ��
	 */
	private Date endtime;
	/**
	 * ��ǰ�û�ѡ�������Ʒ����
	 */
	private String uresoType;
	/**
	 * ����Ʒ���ͼ���
	 */
	private List<SelectItem> selectUresoType = new ArrayList<SelectItem>();
	/**
	 *  ��Դ�ӿ�ʵ��
	 */
	@ManagedProperty(value = "#{resourceService}")
	private IResourceService resourceService;
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");
	/**
	 * ������ݼ�
	 */
	private IDataCollection<Map<String, Object>> result;
	/**
	 * ������ݼ�
	 */
	private IDataCollection<Map<String, Object>> consumeUserList;
	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr; 
	/**
	 * ��ǰ�ڵ���ӽڵ� ID��Ϣ
	 */
	private String nodeIdList;
	
	public List<SelectItem> getSelectUresoType() {
		selectUresoType.clear();
		selectUresoType.add(new SelectItem("--����--"));
		List<String> urType = resourceService.getAllConType();
		for(String str : urType){
			selectUresoType.add(new SelectItem(str));
		}
		return selectUresoType;
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
	 * �о߿�������д�ķ�ҳ����
	 */
	@Override
	public PageListDataModel getDefaultDataModel() {
		if (nodeIdList != null) {
			if (defaultDataModel == null) {
				defaultDataModel = new PageListDataModel(pageSize) {
					public DataPage fetchPage(int startRow, int pageSize) {
						String searchType = uresoType;
						if ("--����--".equals(uresoType)) {
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
	 * �о�ʹ����Ϣ��д�ķ�ҳ����
	 */
	@Override
	public PageListDataModel getExtendDataModel() {
		if (nodeIdList != null) {
			if (extendDataModel == null) {
				extendDataModel = new PageListDataModel(pageSize) {
					public DataPage fetchPage(int startRow, int pageSize) {
						Collection<Parameter> parameters = new HashSet<Parameter>();
						String searchType = uresoType;
						if ("--����--".equals(uresoType)) {
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
	 * ����Ʒ���ҳ���ѯ��ť��Ӧ
	 */
	public void getConsumeStorageAction() {
		this.defaultDataModel = null;
	}

	/**
	 * ����Ʒʹ����Ϣҳ���ѯ��ť��Ӧ
	 */
	public void getConsumeUseAction() {
		this.extendDataModel = null;
	}

	/**
	 * �޲ι��캯����
	 * @����TUserResourceBean.java 
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
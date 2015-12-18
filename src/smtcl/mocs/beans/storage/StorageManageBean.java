package smtcl.mocs.beans.storage;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;

import smtcl.mocs.model.StorageInfoDataModel;
import smtcl.mocs.model.TStorageInfoModel;
import smtcl.mocs.pojos.storage.TStorageInfo;
import smtcl.mocs.services.storage.IStorageManageService;

/**
 * �ⷿ����bean ����ʱ�� 2014-08-26 ���� FW �޸��� �޸�ʱ��
 */
@ManagedBean(name = "StorageManage")
@ViewScoped
public class StorageManageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * �ⷿ����List
	 */
	List<TStorageInfo> storageNoList;
	List<TStorageInfoModel> storageList;
	/**
	 * ����Ա����List
	 */
	List<Map<String, Object>> memberList = new CopyOnWriteArrayList<Map<String, Object>>();

	/**
	 * ����ѡ��model
	 */
	private StorageInfoDataModel data;
	/**
	 * ѡ�е���
	 */
	private TStorageInfoModel[] selectedStorage;
	String success;
	/**
	 * �ⷿID
	 */
	private String id;
	/**
	 * �ⷿ���
	 */
	private String no;
	/**
	 * �ⷿ˵��
	 */
	private String name;
	/**
	 * �ⷿ״̬
	 */
	private String status;
	/**
	 * ʧЧ����
	 */
	private Date invalidDate;
	/**
	 * �ƻ���ʽ
	 */
	private String planType;
	/**
	 * �ɾ���
	 */
	private String isAvailable;
	/**
	 * ��λ����
	 */
	private String positionType;
	/**
	 * �ⷿλ��
	 */
	private String address;
	/**
	 * ������Ա
	 */
	private String storageMan;
	/**
	 * ����ʱ��
	 */
	private Date createDate;
	/**
	 * Ԥ�ӹ���ǰ��
	 */
	private String preProcessTime;
	/**
	 * �ӹ�����ǰ��
	 */
	private String inProcessTime;
	/**
	 * ��ӹ���ǰ��
	 */
	private String sufProcessTime;
	/**
	 * �ڵ�
	 */
	String nodeid;
	/**
	 * ��ѯ����
	 */
	String query;
	/**
	 * �ж��Ƿ�ѡ��
	 */
	private String selected;

	private IStorageManageService iStorageManageService = (IStorageManageService) ServiceFactory
			.getBean("storageManage");

	public StorageManageBean() {
		// ��ȡ�ڵ�ID
		// HttpSession session =
		// (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		// nodeid = (String)session.getAttribute("nodeid");
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		nodeid = session.getAttribute("nodeid2") + "";
		// ��ȡ�ⷿ��Ϣ
		storageList = iStorageManageService.getStorageList("", nodeid);
		data = new StorageInfoDataModel(storageList);
		// ��ȡ����Ա��Ϣ
		memberList = iStorageManageService.userList(nodeid);
	}

	/**
	 * ˢ�±���Ա��Ϣ
	 */
	public void updateMember() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		nodeid = session.getAttribute("nodeid2") + "";
		memberList.clear();
		// ��ȡ����Ա��Ϣ
		memberList = iStorageManageService.userList(nodeid);
	}

	/**
	 * ��ѯ����
	 */
	public void queryData() {
		try {
			if ("������/����/״̬".equals(query))
				query = null;
			storageList = iStorageManageService.getStorageList(query, nodeid);
			data = new StorageInfoDataModel(storageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��λ��Ϣ����
	 */
	public void saveStorageInfo() {
		// �ж���Ϣ�Ƿ����
		List<Map<String, Object>> list = iStorageManageService
				.getList("select new Map( " + "s.storageNo as no) "
						+ "from TStorageInfo s where s.storageNo ='" + no + "'");
		if (list.size() > 0) {
			success = "�ⷿ����Ѵ��ڣ���ȷ��";
			return;
		}
		// ����ֵ
		TStorageInfo info = new TStorageInfo();
		info.setStorageNo(no);
		info.setStorageName(name);
		info.setStorageStatus(status);
		info.setInvalidDate(invalidDate);
		info.setIsAvailable(isAvailable);
		info.setPlanType(planType);
		info.setPositionType(positionType);
		info.setAddress(address);
		info.setStorageMan(storageMan);
		info.setCreateDate(new Date());
		info.setNodeId(nodeid);
		success = iStorageManageService.updateStorageInfo(info, 1);
		if (success.equals("�ɹ�")) {
			queryData();
		}

	}

	/**
	 * ���·���
	 * 
	 * @param event
	 */
	public void onEdit(RowEditEvent event) {
		TStorageInfoModel info = (TStorageInfoModel) event.getObject();
		if ("2222".equals("��ѡ��")) {
			FacesMessage msg = new FacesMessage("�ⷿ����", "����ʧ��,��ѡ��һ�����ԣ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {

			// ����ֵ
			TStorageInfo storageInfo = new TStorageInfo();
			storageInfo.setId(info.getId());
			storageInfo.setStorageNo(info.getStorageNo());
			storageInfo.setStorageName(info.getStorageName());
			storageInfo.setStorageStatus(info.getStorageStatus());

			String time1 = info.getInvalidDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			try {
				storageInfo.setInvalidDate(sdf.parse(time1));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			storageInfo.setIsAvailable(info.getIsAvailable());
			storageInfo.setPlanType(info.getPlanType());
			storageInfo.setPositionType(info.getPositionType());
			storageInfo.setAddress(info.getAddress());
			storageInfo.setStorageMan(info.getStorageMan());
			storageInfo.setCreateDate(info.getCreateDate());

			storageInfo.setPreProcessTime(info.getInProcessTime());
			storageInfo.setInProcessTime(info.getInProcessTime());
			storageInfo.setSufProcessTime(info.getSufProcessTime());
			storageInfo.setNodeId(info.getNodeId());
			String tt = iStorageManageService.updateStorageInfo(storageInfo, 2);
			if (tt.equals("�ɹ�")) {
				FacesMessage msg = new FacesMessage("�ⷿ����", "���³ɹ�");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				FacesMessage msg = new FacesMessage("�ⷿ����", "����ʧ��");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		queryData();
	}

	/**
	 * ȡ��
	 * 
	 * @param event
	 */
	public void onCancel(RowEditEvent event) {

	}

	public void onSelected() {
		for (TStorageInfoModel tt : selectedStorage) {
			selected = tt.getId().toString();
		}
	}

	/**
	 * ɾ���·���
	 */
	public void onDelete() {
		for (TStorageInfoModel tt : selectedStorage) {
			// ����ֵ
			TStorageInfo storageInfo = new TStorageInfo();
			storageInfo.setId(tt.getId());
			iStorageManageService.delStorageInfo(storageInfo);
		}
		queryData();
	}

	public List<TStorageInfo> getStorageNoList() {
		return storageNoList;
	}

	public void setStorageNoList(List<TStorageInfo> storageNoList) {
		this.storageNoList = storageNoList;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStorageMan() {
		return storageMan;
	}

	public void setStorageMan(String storageMan) {
		this.storageMan = storageMan;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPreProcessTime() {
		return preProcessTime;
	}

	public void setPreProcessTime(String preProcessTime) {
		this.preProcessTime = preProcessTime;
	}

	public String getInProcessTime() {
		return inProcessTime;
	}

	public void setInProcessTime(String inProcessTime) {
		this.inProcessTime = inProcessTime;
	}

	public String getSufProcessTime() {
		return sufProcessTime;
	}

	public void setSufProcessTime(String sufProcessTime) {
		this.sufProcessTime = sufProcessTime;
	}

	public List<Map<String, Object>> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Map<String, Object>> memberList) {
		this.memberList = memberList;
	}

	public String getQuery() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		nodeid = session.getAttribute("nodeid2") + "";
		queryData();
		this.updateMember();
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public TStorageInfoModel[] getSelectedStorage() {
		return selectedStorage;
	}

	public void setSelectedStorage(TStorageInfoModel[] selectedStorage) {
		this.selectedStorage = selectedStorage;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public StorageInfoDataModel getData() {
		return data;
	}

	public void setData(StorageInfoDataModel data) {
		this.data = data;
	}

	public List<TStorageInfoModel> getStorageList() {
		return storageList;
	}

	public void setStorageList(List<TStorageInfoModel> storageList) {
		this.storageList = storageList;
	}

}

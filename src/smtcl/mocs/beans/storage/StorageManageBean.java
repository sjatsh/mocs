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
 * 库房管理bean 创建时间 2014-08-26 作者 FW 修改者 修改时间
 */
@ManagedBean(name = "StorageManage")
@ViewScoped
public class StorageManageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 库房集合List
	 */
	List<TStorageInfo> storageNoList;
	List<TStorageInfoModel> storageList;
	/**
	 * 保管员集合List
	 */
	List<Map<String, Object>> memberList = new CopyOnWriteArrayList<Map<String, Object>>();

	/**
	 * 多行选择model
	 */
	private StorageInfoDataModel data;
	/**
	 * 选中的行
	 */
	private TStorageInfoModel[] selectedStorage;
	String success;
	/**
	 * 库房ID
	 */
	private String id;
	/**
	 * 库房编号
	 */
	private String no;
	/**
	 * 库房说明
	 */
	private String name;
	/**
	 * 库房状态
	 */
	private String status;
	/**
	 * 失效日期
	 */
	private Date invalidDate;
	/**
	 * 计划方式
	 */
	private String planType;
	/**
	 * 可净得
	 */
	private String isAvailable;
	/**
	 * 库位控制
	 */
	private String positionType;
	/**
	 * 库房位置
	 */
	private String address;
	/**
	 * 保管人员
	 */
	private String storageMan;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 预加工提前期
	 */
	private String preProcessTime;
	/**
	 * 加工中提前期
	 */
	private String inProcessTime;
	/**
	 * 后加工提前期
	 */
	private String sufProcessTime;
	/**
	 * 节点
	 */
	String nodeid;
	/**
	 * 查询条件
	 */
	String query;
	/**
	 * 判断是否选中
	 */
	private String selected;

	private IStorageManageService iStorageManageService = (IStorageManageService) ServiceFactory
			.getBean("storageManage");

	public StorageManageBean() {
		// 获取节点ID
		// HttpSession session =
		// (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		// nodeid = (String)session.getAttribute("nodeid");
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		nodeid = session.getAttribute("nodeid2") + "";
		// 获取库房信息
		storageList = iStorageManageService.getStorageList("", nodeid);
		data = new StorageInfoDataModel(storageList);
		// 获取保管员信息
		memberList = iStorageManageService.userList(nodeid);
	}

	/**
	 * 刷新保管员信息
	 */
	public void updateMember() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		nodeid = session.getAttribute("nodeid2") + "";
		memberList.clear();
		// 获取保管员信息
		memberList = iStorageManageService.userList(nodeid);
	}

	/**
	 * 查询方法
	 */
	public void queryData() {
		try {
			if ("输入编号/名称/状态".equals(query))
				query = null;
			storageList = iStorageManageService.getStorageList(query, nodeid);
			data = new StorageInfoDataModel(storageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 库位信息保存
	 */
	public void saveStorageInfo() {
		// 判断信息是否存在
		List<Map<String, Object>> list = iStorageManageService
				.getList("select new Map( " + "s.storageNo as no) "
						+ "from TStorageInfo s where s.storageNo ='" + no + "'");
		if (list.size() > 0) {
			success = "库房编号已存在，请确认";
			return;
		}
		// 设置值
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
		if (success.equals("成功")) {
			queryData();
		}

	}

	/**
	 * 更新方法
	 * 
	 * @param event
	 */
	public void onEdit(RowEditEvent event) {
		TStorageInfoModel info = (TStorageInfoModel) event.getObject();
		if ("2222".equals("请选择")) {
			FacesMessage msg = new FacesMessage("库房更新", "更新失败,请选择一个属性！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {

			// 设置值
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
			if (tt.equals("成功")) {
				FacesMessage msg = new FacesMessage("库房更新", "更新成功");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				FacesMessage msg = new FacesMessage("库房更新", "更新失败");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		queryData();
	}

	/**
	 * 取消
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
	 * 删除新方法
	 */
	public void onDelete() {
		for (TStorageInfoModel tt : selectedStorage) {
			// 设置值
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

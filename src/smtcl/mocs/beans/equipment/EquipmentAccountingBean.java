package smtcl.mocs.beans.equipment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.mina.util.CopyOnWriteMap;
import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.exception.FileUploadException;
import org.richfaces.model.UploadedFile;

import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TUserEquCurStatus;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;
import smtcl.mocs.services.jobplan.IEquipmentService;
import smtcl.mocs.utils.device.ExcelUtils;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 
 * 设备台帐维护Bean
 * 
 * @作者：yyh
 * @创建时间：2013-08-06 下午13:05:16
 * @修改者：yyh
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name = "EquipmentAccounting")
@ViewScoped
public class EquipmentAccountingBean implements Serializable {

	/**
	 * 作业计划接口实例
	 */
	private IEquipmentService equipmentService = (IEquipmentService) ServiceFactory
			.getBean("equipmentService");
	/**
	 * 车间名称
	 */
	private String room;
	/**
	 * 列表数
	 */
	private String num;
	/**
	 * 设备dataTable数据
	 */
	private List<Map<String, Object>> equlist = new CopyOnWriteArrayList<Map<String, Object>>();
	/**
	 * 设备dataTable数据 外层封装
	 */
	private TEquipmentInfoDataModel mediumEquipmentInfoModel = new TEquipmentInfoDataModel();
	/**
	 * 设备选中的行
	 */
	private Map<String, Object>[] selectedEquipment;
	/**
	 * 设备类型ID
	 */
	private String equipmentTypeId;
	/**
	 * 设备ID
	 */
	private String equipmentId;
	/**
	 * 左侧树对象
	 */
	private TreeNode root;
	/**
	 * 左侧树对象
	 */
	private TreeNode tree;
	/**
	 * 当前选择树节点
	 */
	private TreeNode treeSelectedEqu;
	/**
	 * 添加的对象
	 */
	private TEquipmentInfo equObj = new TEquipmentInfo();
	/**
	 * 添加是的父设备类别下拉
	 */
	private Map<String, Object> equTypeMap = new CopyOnWriteMap<String, Object>();
	/**
	 * 上传文件
	 */
	private UploadedFile file;
	/**
	 * 封装上传文件 图片路径
	 */
	private List<String> listdocStorePath = new CopyOnWriteArrayList<String>();

	private String equSerialNo;

	/**
	 * 递归
	 */
	public void getTreeNode(TEquipmenttypeInfo te, TreeNode node, String nodeid) {
		TreeNode root = new DefaultTreeNode(te, node);
		for (TEquipmenttypeInfo tei : te.getTEquipmenttypeInfos()) {
			if (null != tei.getNodeid()
					&& tei.getNodeid().toString().equals(nodeid)
					&& !tei.getIsdel().equals("1")) {
				getTreeNode(tei, root, nodeid);
			}
		}
	}

	/**
	 * 构造
	 */
	public EquipmentAccountingBean() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		String nodeid = (String) session.getAttribute("nodeid2");
		// 左侧树
		root = new DefaultTreeNode(new TEquipmenttypeInfo(), null);
		TEquipmenttypeInfo lstx = equipmentService.getgetEquTypeIdById("-999");
		getTreeNode(lstx, root, nodeid);

		// 右侧列表
		equlist.clear();
		equlist = equipmentService.getMachineList(equipmentId);
		int num1 = equlist.size();
		num = String.valueOf(num1);
		mediumEquipmentInfoModel = new TEquipmentInfoDataModel(equlist);

		// 添加时的下拉
		List<Map<String, Object>> lst2 = equipmentService.getParentIdMap();
		for (Map<String, Object> map : lst2) {
			if (map.get("id") != null && !"".equals(map.get("id"))) {
				equTypeMap.put((String) map.get("equipmentType"), map.get("id")
						.toString());
			}
		}
	}

	/**
	 * 点击树查询
	 */
	public void onNodeSelect(NodeSelectEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Selected", event.getTreeNode().toString());
		FacesContext.getCurrentInstance().addMessage(null, message);

		// System.out.println("event.getTreeNode().toString()-------------->"+event.getTreeNode().toString());
		TEquipmenttypeInfo te = (TEquipmenttypeInfo) event.getTreeNode()
				.getData();
		String typeName = te.getEquipmentType();

		String typeId = "";
		if (typeName != null && typeName.equals("设备类型")) {
			typeId = "0";
		} else {
			typeId = equipmentService.getEquTypeIdByName(typeName);
		}
		equlist.clear();
		equlist = equipmentService.getEquByTypeInAcc(typeId);
		int num1 = equlist.size();
		num = String.valueOf(num1);
		mediumEquipmentInfoModel = new TEquipmentInfoDataModel(equlist);
	}

	/**
	 * 设备添加
	 */
	public void addEqu() {
		List<Map<String, Object>> lst = equipmentService.getEquRepeat(equObj
				.getEquSerialNo());
		if (lst.size() == 0) {
			// 获取节点ID
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			String nodeid = (String) session.getAttribute("nodeid2");
			equipmentService.addEqu(nodeid, this);

			// 左侧树
			root = new DefaultTreeNode(new TEquipmenttypeInfo(), null);
			TEquipmenttypeInfo lstx = equipmentService
					.getgetEquTypeIdById("-999");
			getTreeNode(lstx, root, nodeid);
		} else {
			System.out.println("已经存在，不要重复添加----------->");
		}
	}

	/**
	 * 更新方法
	 * 
	 * @param event
	 */
	public void onEdit(RowEditEvent event) {
		System.out.println("更新----------->");
		// Map<String, Object> map=(Map<String, Object>) event.getObject();
		equipmentService.updateEqu(this);
//		for (Map<String, Object> part : selectedEquipment) {
//			String id = part.get("peopleId").toString();
//			String name = equipmentService.getMenberNameById(id);
//			part.put("peopleName", name);
//		}
	}

	/**
	 * 取消
	 * 
	 * @param event
	 */
	public void onCancel(RowEditEvent event) {

	}

	/**
	 * 上传图片的方法
	 */
	public void updateEquImage() {
		equipmentService.updateEquImage(equId, listdocStorePath);
	}

	private String equId;

	/**
	 * 获取当前选中的零件id
	 * 
	 * @param id
	 */
	public void setPart(String no, String equSerialNo) {
		equId = no;
		this.equSerialNo = equSerialNo;
	}

	/**
	 * 删除
	 */
	public void onDelete() {
		System.out.println("selectedEquipment.length----------->"
				+ selectedEquipment.length);
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		String nodeid = (String) session.getAttribute("nodeid2");
		equipmentService.delEqu(nodeid, this);
		try {
			equlist.clear();
			equlist = equipmentService.getMachineList(equipmentId);
			int num1 = equlist.size();
			num = String.valueOf(num1);
			mediumEquipmentInfoModel = new TEquipmentInfoDataModel(equlist);

			// 左侧树
			root = new DefaultTreeNode(new TEquipmenttypeInfo(), null);
			TEquipmenttypeInfo lstx = equipmentService
					.getgetEquTypeIdById("-999");
			getTreeNode(lstx, root, nodeid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String selected;

	/**
	 * 判断是否选中
	 */
	public void onSelected() {
		System.out.println("选中----------->");
		for (Map<String, Object> tt : selectedEquipment) {
			selected = tt.get("equId").toString();
		}
	}

	private String imgUrl1;

	/**
	 * 通过设备id得到图片预览
	 */
	public void getImgUrlAddress(String eduId) {
		TEquipmentInfo te = equipmentService.getTEquipmentInfoById(eduId);
		if (te != null) {
			imgUrl1 = te.getPath();
		}
	}

	/**
	 * excel下载,要有UploadFile这个文件，否则报错
	 */
	public void downloadExcel() {
		List<Map<String, Object>> equlist2 = equipmentService
				.getMachineList(equipmentId);
		String[] firstRowValue = new String[] { "设备ID", "设备序列号", "设备类型名称 ",
				"设备名称 ", "型号/牌号", "出厂编号", "厂家", "进/验日期", "x位置", "y位置", "IP地址",
				"备注", "操作人" };
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 不能用/杠，找不到路径
		Date d = new Date();
		String d1 = sdf.format(d);
		new ExcelUtils().outputEquExcel(d1 + ".xls", equlist2, firstRowValue);// 不支持中文名称

	}

	/**
	 * 文件上传监听
	 * 
	 * @param event
	 * @throws Exception
	 * @return
	 */
	public void listener(FileUploadEvent event) throws Exception {
		file = null;
		file = event.getUploadedFile();
		String uri = file.getName();
		// StringUtils.getFileName(file);
		// uri=equSerialNo+uri.substring(uri.length()-4, uri.length());
		/*
		 * TUserEquCurStatus
		 * tt=equipmentService.getTUserEquCurStatus(equSerialNo); String
		 * sta=tt.getStatus(); if(sta.equals("运行")||sta.equals("加工")){ sta="yx";
		 * }else if(sta.equals("脱机")||sta.equals("关机")){ sta="tj"; }else
		 * if(sta.equals("准备")){ sta="zb"; } else
		 * if(sta.equals("故障")||sta.equals("中断")||sta.equals("急停")){ sta="gz";
		 * }else if(sta.equals("空闲")){ sta="kx"; }else{ sta="tj"; } uri=sta+uri;
		 */

		if (null != file) {
			savaFile(file, uri);
			listdocStorePath.add("/images/" + uri); // ti替换file.getName()
		}

	}

	/**
	 * 保存文件上传到服务器
	 * 
	 * @param file
	 * @param uri
	 * @return
	 * @throws and
	 *             @exception
	 */
	public void savaFile(UploadedFile file, String uri) throws Exception {

		// String path =
		// FacesContext.getCurrentInstance().getExternalContext().getRealPath("/images");
		// String realPath= path.replace("machinearchive", "");
		String realPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/static/images/device/");
		System.out.println("realPath=====" + realPath);
		File imageFile = new File(realPath, uri); // ti替换file.getName()
		try {
			FileOutputStream fops = new FileOutputStream(imageFile);
			// 将上传的文件信息保存到相应的文件目录里
			fops.write(file.getData());
			fops.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ================================set,get方法================================
	 * =============
	 **/
	public TreeNode getTree() {

		/**
		 * 刷新左侧树
		 */
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		String nodeid = (String) session.getAttribute("nodeid2");
		root = new DefaultTreeNode(new TEquipmenttypeInfo(), null);
		TEquipmenttypeInfo lstx = equipmentService.getgetEquTypeIdById("-999");
		getTreeNode(lstx, root, nodeid);
		return root;
	}

	public void setTree(TreeNode root) {

		 this.root=root;
	}

	public IEquipmentService getEquipmentService() {
		return equipmentService;
	}

	public void setEquipmentService(IEquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public TEquipmentInfoDataModel getMediumEquipmentInfoModel() {
		return mediumEquipmentInfoModel;
	}

	public void setMediumEquipmentInfoModel(
			TEquipmentInfoDataModel mediumEquipmentInfoModel) {
		this.mediumEquipmentInfoModel = mediumEquipmentInfoModel;
	}

	public String getEquipmentTypeId() {
		return equipmentTypeId;
	}

	public void setEquipmentTypeId(String equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}

	public TreeNode getRoot() {

		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public List<Map<String, Object>> getEqulist() {
		return equlist;
	}

	public void setEqulist(List<Map<String, Object>> equlist) {
		this.equlist = equlist;
	}

	public Map<String, Object>[] getSelectedEquipment() {
		return selectedEquipment;
	}

	public void setSelectedEquipment(Map<String, Object>[] selectedEquipment) {
		this.selectedEquipment = selectedEquipment;
	}

	public TreeNode getTreeSelectedEqu() {
		return treeSelectedEqu;
	}

	public void setTreeSelectedEqu(TreeNode treeSelectedEqu) {
		this.treeSelectedEqu = treeSelectedEqu;
	}

	public TEquipmentInfo getEquObj() {
		return equObj;
	}

	public void setEquObj(TEquipmentInfo equObj) {
		this.equObj = equObj;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public Map<String, Object> getEquTypeMap() {
		return equTypeMap;
	}

	public void setEquTypeMap(Map<String, Object> equTypeMap) {
		this.equTypeMap = equTypeMap;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<String> getListdocStorePath() {
		return listdocStorePath;
	}

	public void setListdocStorePath(List<String> listdocStorePath) {
		this.listdocStorePath = listdocStorePath;
	}

	public String getEquSerialNo() {
		return equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	public String getEquId() {
		return equId;
	}

	public void setEquId(String equId) {
		this.equId = equId;
	}

	public String getImgUrl1() {
		return imgUrl1;
	}

	public void setImgUrl1(String imgUrl1) {
		this.imgUrl1 = imgUrl1;
	}

}

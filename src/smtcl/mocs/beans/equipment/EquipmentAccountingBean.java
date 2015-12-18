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
 * �豸̨��ά��Bean
 * 
 * @���ߣ�yyh
 * @����ʱ�䣺2013-08-06 ����13:05:16
 * @�޸��ߣ�yyh
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@ManagedBean(name = "EquipmentAccounting")
@ViewScoped
public class EquipmentAccountingBean implements Serializable {

	/**
	 * ��ҵ�ƻ��ӿ�ʵ��
	 */
	private IEquipmentService equipmentService = (IEquipmentService) ServiceFactory
			.getBean("equipmentService");
	/**
	 * ��������
	 */
	private String room;
	/**
	 * �б���
	 */
	private String num;
	/**
	 * �豸dataTable����
	 */
	private List<Map<String, Object>> equlist = new CopyOnWriteArrayList<Map<String, Object>>();
	/**
	 * �豸dataTable���� ����װ
	 */
	private TEquipmentInfoDataModel mediumEquipmentInfoModel = new TEquipmentInfoDataModel();
	/**
	 * �豸ѡ�е���
	 */
	private Map<String, Object>[] selectedEquipment;
	/**
	 * �豸����ID
	 */
	private String equipmentTypeId;
	/**
	 * �豸ID
	 */
	private String equipmentId;
	/**
	 * ���������
	 */
	private TreeNode root;
	/**
	 * ���������
	 */
	private TreeNode tree;
	/**
	 * ��ǰѡ�����ڵ�
	 */
	private TreeNode treeSelectedEqu;
	/**
	 * ��ӵĶ���
	 */
	private TEquipmentInfo equObj = new TEquipmentInfo();
	/**
	 * ����ǵĸ��豸�������
	 */
	private Map<String, Object> equTypeMap = new CopyOnWriteMap<String, Object>();
	/**
	 * �ϴ��ļ�
	 */
	private UploadedFile file;
	/**
	 * ��װ�ϴ��ļ� ͼƬ·��
	 */
	private List<String> listdocStorePath = new CopyOnWriteArrayList<String>();

	private String equSerialNo;

	/**
	 * �ݹ�
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
	 * ����
	 */
	public EquipmentAccountingBean() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		String nodeid = (String) session.getAttribute("nodeid2");
		// �����
		root = new DefaultTreeNode(new TEquipmenttypeInfo(), null);
		TEquipmenttypeInfo lstx = equipmentService.getgetEquTypeIdById("-999");
		getTreeNode(lstx, root, nodeid);

		// �Ҳ��б�
		equlist.clear();
		equlist = equipmentService.getMachineList(equipmentId);
		int num1 = equlist.size();
		num = String.valueOf(num1);
		mediumEquipmentInfoModel = new TEquipmentInfoDataModel(equlist);

		// ���ʱ������
		List<Map<String, Object>> lst2 = equipmentService.getParentIdMap();
		for (Map<String, Object> map : lst2) {
			if (map.get("id") != null && !"".equals(map.get("id"))) {
				equTypeMap.put((String) map.get("equipmentType"), map.get("id")
						.toString());
			}
		}
	}

	/**
	 * �������ѯ
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
		if (typeName != null && typeName.equals("�豸����")) {
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
	 * �豸���
	 */
	public void addEqu() {
		List<Map<String, Object>> lst = equipmentService.getEquRepeat(equObj
				.getEquSerialNo());
		if (lst.size() == 0) {
			// ��ȡ�ڵ�ID
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			String nodeid = (String) session.getAttribute("nodeid2");
			equipmentService.addEqu(nodeid, this);

			// �����
			root = new DefaultTreeNode(new TEquipmenttypeInfo(), null);
			TEquipmenttypeInfo lstx = equipmentService
					.getgetEquTypeIdById("-999");
			getTreeNode(lstx, root, nodeid);
		} else {
			System.out.println("�Ѿ����ڣ���Ҫ�ظ����----------->");
		}
	}

	/**
	 * ���·���
	 * 
	 * @param event
	 */
	public void onEdit(RowEditEvent event) {
		System.out.println("����----------->");
		// Map<String, Object> map=(Map<String, Object>) event.getObject();
		equipmentService.updateEqu(this);
//		for (Map<String, Object> part : selectedEquipment) {
//			String id = part.get("peopleId").toString();
//			String name = equipmentService.getMenberNameById(id);
//			part.put("peopleName", name);
//		}
	}

	/**
	 * ȡ��
	 * 
	 * @param event
	 */
	public void onCancel(RowEditEvent event) {

	}

	/**
	 * �ϴ�ͼƬ�ķ���
	 */
	public void updateEquImage() {
		equipmentService.updateEquImage(equId, listdocStorePath);
	}

	private String equId;

	/**
	 * ��ȡ��ǰѡ�е����id
	 * 
	 * @param id
	 */
	public void setPart(String no, String equSerialNo) {
		equId = no;
		this.equSerialNo = equSerialNo;
	}

	/**
	 * ɾ��
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

			// �����
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
	 * �ж��Ƿ�ѡ��
	 */
	public void onSelected() {
		System.out.println("ѡ��----------->");
		for (Map<String, Object> tt : selectedEquipment) {
			selected = tt.get("equId").toString();
		}
	}

	private String imgUrl1;

	/**
	 * ͨ���豸id�õ�ͼƬԤ��
	 */
	public void getImgUrlAddress(String eduId) {
		TEquipmentInfo te = equipmentService.getTEquipmentInfoById(eduId);
		if (te != null) {
			imgUrl1 = te.getPath();
		}
	}

	/**
	 * excel����,Ҫ��UploadFile����ļ������򱨴�
	 */
	public void downloadExcel() {
		List<Map<String, Object>> equlist2 = equipmentService
				.getMachineList(equipmentId);
		String[] firstRowValue = new String[] { "�豸ID", "�豸���к�", "�豸�������� ",
				"�豸���� ", "�ͺ�/�ƺ�", "�������", "����", "��/������", "xλ��", "yλ��", "IP��ַ",
				"��ע", "������" };
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ������/�ܣ��Ҳ���·��
		Date d = new Date();
		String d1 = sdf.format(d);
		new ExcelUtils().outputEquExcel(d1 + ".xls", equlist2, firstRowValue);// ��֧����������

	}

	/**
	 * �ļ��ϴ�����
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
		 * sta=tt.getStatus(); if(sta.equals("����")||sta.equals("�ӹ�")){ sta="yx";
		 * }else if(sta.equals("�ѻ�")||sta.equals("�ػ�")){ sta="tj"; }else
		 * if(sta.equals("׼��")){ sta="zb"; } else
		 * if(sta.equals("����")||sta.equals("�ж�")||sta.equals("��ͣ")){ sta="gz";
		 * }else if(sta.equals("����")){ sta="kx"; }else{ sta="tj"; } uri=sta+uri;
		 */

		if (null != file) {
			savaFile(file, uri);
			listdocStorePath.add("/images/" + uri); // ti�滻file.getName()
		}

	}

	/**
	 * �����ļ��ϴ���������
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
		File imageFile = new File(realPath, uri); // ti�滻file.getName()
		try {
			FileOutputStream fops = new FileOutputStream(imageFile);
			// ���ϴ����ļ���Ϣ���浽��Ӧ���ļ�Ŀ¼��
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
	 * ================================set,get����================================
	 * =============
	 **/
	public TreeNode getTree() {

		/**
		 * ˢ�������
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

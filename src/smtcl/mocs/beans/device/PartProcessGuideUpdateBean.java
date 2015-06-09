package smtcl.mocs.beans.device;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.exception.FileUploadException;
import org.richfaces.model.UploadedFile;

import smtcl.mocs.model.CuttertypeModel;
import smtcl.mocs.model.MaterialsModel;
import smtcl.mocs.model.PartModel;
import smtcl.mocs.model.TCuttertypeInfoDataModel;
import smtcl.mocs.model.TProcessEquipmentDataModel;
import smtcl.mocs.model.TProcessEquipmentModel;
import smtcl.mocs.model.TProcessFixturetypeDataModel;
import smtcl.mocs.model.TProcessFixturetypeModel;
import smtcl.mocs.model.TProcessmaterialInfoDataModel;
import smtcl.mocs.model.TQualityParamDataModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TFixtureClassInfo;
import smtcl.mocs.pojos.device.TUserFixture;
import smtcl.mocs.pojos.job.TCuttertypeInfo;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;
import smtcl.mocs.pojos.job.TFixtureTypeInfo;
//import smtcl.mocs.pojos.job.TFixturesInfo;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TPartProcessCost;
import smtcl.mocs.pojos.job.TProcessEquipment;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProcessmaterialInfo;
import smtcl.mocs.pojos.job.TProcessplanInfo;
import smtcl.mocs.pojos.job.TQualityParam;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.device.IResourceService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.StringUtils;

/**
 * ��������򵼸���
 * 
 * @����ʱ�� 2013-07-23
 * @���� liguoqiang
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name = "partProcessGuideUpdateBean")
@ViewScoped
public class PartProcessGuideUpdateBean {
	/**
	 * �������ģ��
	 */
	private PartModel part = new PartModel();
	/**
	 * ����ӿ�ʵ��
	 */
	private IPartService partService = (IPartService) ServiceFactory
			.getBean("partService");
	/**
	 * ��Դ�ӿ�ʵ��
	 */
	private IResourceService resourceService = (IResourceService) ServiceFactory
			.getBean("resourceService");
	/**
	 * ������
	 */
	private String selectProcess = "";
	/**
	 * ���շ���id
	 */
	private String selectProcessPlan;
	/**
	 * �ϴ��ļ�
	 */
	private UploadedFile file;
	/**
	 * ��װ�ϴ��ļ� ͼƬ·��
	 */
	private List<String> listdocStorePath = new ArrayList<String>();
	/**
	 * �豸�����������б�
	 */
	private List<TEquipmenttypeInfo> equList;
	/**
	 * �豸�б�
	 */
	private List<TEquipmentInfo> equinfolist;
	/**
	 * �����������б�
	 */
	private List<Map<String, Object>> ncprogsList;

	/**
	 * ����dataTable����
	 */
	private TProcessmaterialInfoDataModel mediumPartModel;

	/**
	 * ����dataTableѡ�е���
	 */
	private MaterialsModel[] selectedRowPart;

	/**
	 * ��������
	 */
	private MaterialsModel addWL = new MaterialsModel();
	/**
	 * ������ʱ���ݴ洢
	 */
	private List<MaterialsModel> materialList = new ArrayList<MaterialsModel>();
	/**
	 * ��������������
	 */
	private List<Map<String, Object>> selectWL = new ArrayList<Map<String, Object>>();
	/**
	 * ����dataTable����
	 */
	private TCuttertypeInfoDataModel mediumDJModel;
	/**
	 * ����dataTableѡ�е���
	 */
	private CuttertypeModel[] selectedRowDJ;
	/**
	 * ��������
	 */
	private CuttertypeModel addDJ = new CuttertypeModel();
	/**
	 * ������ʱ���ݴ洢
	 */
	private List<CuttertypeModel> cuttertypeList = new ArrayList<CuttertypeModel>();
	/**
	 * ��������������
	 */
	private List<Map<String, Object>> selectDJ = new ArrayList<Map<String, Object>>();

	/**
	 * ������ϸ�б�
	 */
	private Map<String, Object> processDetail;

	/**
	 * �о��������б�
	 */
	private List<TFixtureTypeInfo> jjlist;
	/**
	 * �о����������
	 */
	private List<TFixtureClassInfo> jjlblist;
	/**
	 * �о����ѡ����
	 */
	private String jjflb;
	/**
	 * ���ݼ��ض���
	 */
	private String loadData = "1";

	/**
	 * ��ʱ�ʼ���󼯺�
	 */
	private List<TQualityParam> tqplist = new ArrayList<TQualityParam>();
	/**
	 * �����ʼ����
	 */
	private TQualityParam tqp = new TQualityParam();
	/**
	 * �ʼ�dataTable����
	 */
	private TQualityParamDataModel tqpdModel;
	/**
	 * �ʼ�dataTableѡ�е���
	 */
	private TQualityParam[] selectedRowtqp;

	/**
	 * ��ʱ�豸���󼯺�
	 */
	private List<TProcessEquipmentModel> teti = new ArrayList<TProcessEquipmentModel>();
	/**
	 * �����豸����
	 */
	private TProcessEquipmentModel addtpEqu = new TProcessEquipmentModel();
	/**
	 * �豸datable����
	 */
	private TProcessEquipmentDataModel tpeqModel;
	/**
	 * �豸datableѡ����
	 */
	private TProcessEquipmentModel[] selectedRowEqu;
	/**
	 * �豸�ڵ����Ϣ
	 */
	private TreeNode root;
	/**
	 * �豸��ǰѡ�нڵ���Ϣ
	 */
	private TreeNode selectedNode;

	/**
	 * ��ʱ�о߶��󼯺�
	 */
	private List<TProcessFixturetypeModel> tftmlist = new ArrayList<TProcessFixturetypeModel>();
	/**
	 * �����о߹���
	 */
	private TProcessFixturetypeModel addtftm = new TProcessFixturetypeModel();
	/**
	 * �о�datable
	 */
	private TProcessFixturetypeDataModel tpftdm;
	/**
	 * ��ǰѡ�еļо�
	 */
	private TProcessFixturetypeModel[] selectRowJJ;

	private TProcessInfo tt;

	private String tNo = "0";

	List<TProcessInfo> pprs;
	private String sycpid;
	private String nodeid;
	private String updateSuccess;

	public String getSelectProcess() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Map map = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		String sp = (String) session.getAttribute("selectProcess");// ������
		String spp = (String) session.getAttribute("selectProcessPlan");// ���շ���id
		String pid = (String) session.getAttribute("processId");// ����id
		nodeid = session.getAttribute("nodeid2") + "";
		System.out.println(sp);
		System.out.println(spp);
		System.out.println(pid + "--" + sycpid);
		System.out.println(loadData);
		if (loadData.equals("1") || !sycpid.equals(pid)) {
			loadData = "0";
			sycpid = pid;
			if (Constants.LOADCOUNT <= 1) {
				/*------�����������start--------*/
				part = new PartModel();// ������ģ��

				teti = new ArrayList<TProcessEquipmentModel>();// ����豸�����б�
				// tpeqModel =new TProcessEquipmentDataModel(teti);

				materialList = new ArrayList<MaterialsModel>();// ��������б�
				// mediumPartModel =new
				// TProcessmaterialInfoDataModel(materialList); //��ʼ������DataTable

				tftmlist = new ArrayList<TProcessFixturetypeModel>();// ��ռо��б�
				tpftdm = new TProcessFixturetypeDataModel(tftmlist); // ��ʼ���о�DataTable

				cuttertypeList = new ArrayList<CuttertypeModel>();// ��յ�������
				// mediumDJModel =new TCuttertypeInfoDataModel(cuttertypeList);
				// //��ʼ������DataTable

				tqplist = new ArrayList<TQualityParam>();// ����ʼ�
				tqpdModel = new TQualityParamDataModel(tqplist);
				/*------�����������end--------*/
				++Constants.LOADCOUNT;
			}
			List<TProcessplanInfo> tProcessplanInfoList = partService
					.getTProcessplanInfoById(spp); // ���շ���
			part.settProcessplanInfo(tProcessplanInfoList.get(0));
			if (null != sp) {
				selectProcess = sp;
			}
			pprs = partService.getTProcessInfo(spp);// ����ǰ�ù����б�
			for (int i = 0; i < pprs.size(); i++) {
				TProcessInfo tpi = pprs.get(i);
				if (tpi.getId().toString().equals(pid)) {
					pprs.remove(i);
					break;
				}
			}
			selectProcessPlan = tProcessplanInfoList.get(0).getName();// ��ȡ���շ�������
			System.out.println(selectProcess);
			tt = partService.getTProcessInfoById(pid);// ��ȡ��ǰ����
			/** -------------������Ϣ�������----------------- **/
			part.setId(tt.getId().toString());
			part.setNo(tt.getNo());
			part.setName(tt.getName());
			part.setProcessOrder(tt.getProcessOrder().toString());
			part.setOnlineProcessID(null == tt.getOnlineProcessId() ? "" : tt
					.getOnlineProcessId().toString());
			part.setOfflineProcess(null == tt.getOfflineProcess() ? "" : tt
					.getOfflineProcess().toString());
			part.setProcessDuration(null == tt.getProcessDuration() ? "" : tt
					.getProcessDuration().toString());
			part.setProcessType(null == tt.getProcessType() ? "" : tt
					.getProcessType().toString());
			part.setFile(tt.getFile());

			part.setDescription(tt.getDescription());
			part.settProcessplanInfo(tt.getTProcessplanInfo()); // ���ù��շ���
			part.setIscheck((null == tt.getNeedcheck() ? "" : tt.getNeedcheck()
					.toString())); // �Ƿ�ؼ�
			part.setChecktype((null == tt.getCheckType() ? "" : tt
					.getCheckType().toString())); // ��������

			/** -------------��̨�������----------------- **/

			root = partService.getTreeNodeEquInfo(nodeid);// �����豸����
			teti = partService.getTProcessEquipmentByProcessId(tt.getId()
					.toString());
			tpeqModel = new TProcessEquipmentDataModel(teti); // ��ʼ���豸datable

			part.setProgramID(null == tt.getProgramId() ? "" : tt
					.getProgramId().toString()); // ���ó���
			part.setProcessingTime(null == tt.getProcessingTime() ? "" : tt
					.getProcessingTime().toString());
			part.setTheoryWorktime(null == tt.getTheoryWorktime() ? "" : tt
					.getTheoryWorktime().toString());
			part.setCapacity(null == tt.getCapacity() ? "" : tt.getCapacity()
					.toString());

			/** -------------�����������----------------- **/
			List<TProcessmaterialInfo> material = partService
					.getTProcessmaterialInfo(tt.getId().toString());
			materialList = new ArrayList<MaterialsModel>();
			for (int i = 0; i < material.size(); i++) {
				MaterialsModel mt = new MaterialsModel();
				TProcessmaterialInfo tm = material.get(i);
				mt.setId((i + 1) + "");
				mt.setDid(tm.getId() + "");
				mt.setWlName(tm.getTMaterailTypeInfo().getName());
				mt.setWlNo(tm.getTMaterailTypeInfo().getNo());
				mt.setWlnorm(tm.getTMaterailTypeInfo().getNorm());
				mt.setWlNumber(tm.getRequirementNum() + "");
				mt.setWlType(tm.getRequirementType());
				materialList.add(mt);
			}
			mediumPartModel = new TProcessmaterialInfoDataModel(materialList); // ��ʼ������DataTable

			/** -------------��Դ�������----------------- **/
			jjlblist = resourceService
					.getTFixtureClassInfoByQuery(null, nodeid);// �о�������������ݼ���
			tftmlist = partService.getTProcessFixturetypeModelByProcessId(tt
					.getId().toString());
			tpftdm = new TProcessFixturetypeDataModel(tftmlist);// ���ؼо�����

			List<Map<String, Object>> tclist = partService
					.getTProcessCuttertypeInfo(tt.getId().toString(), nodeid); // �������ݲ�ѯ

			for (int i = 0; i < tclist.size(); i++) {
				CuttertypeModel ct = new CuttertypeModel();
				Map<String, Object> tc = tclist.get(i);
				ct.setId((i + 1) + "");
				ct.setDid(tc.get("bid") + "");
				ct.setName(null == tc.get("name") ? "" : tc.get("name")
						.toString());
				ct.setNo(null == tc.get("no") ? "" : tc.get("no").toString());
				ct.setRequirementNum(null == tc.get("requirementNum") ? "" : tc
						.get("requirementNum").toString());
				cuttertypeList.add(ct);
			}
			mediumDJModel = new TCuttertypeInfoDataModel(cuttertypeList); // ��ʼ������DataTable
			/** -------------�ɱ��������----------------- **/
			List<TPartProcessCost> pclist = partService.getTPartProcessCost(tt
					.getId().toString());
			if (null != pclist && pclist.size() > 0) {
				TPartProcessCost pc = pclist.get(0);
				part.setMainMaterialCost(null == pc.getMainMaterialCost() ? ""
						: pc.getMainMaterialCost().toString());
				part.setPeopleCost(null == pc.getPeopleCost() ? "" : pc
						.getPeopleCost().toString());
				part.setSubsidiaryMaterialCost(null == pc
						.getSubsidiaryMaterialCost() ? "" : pc
						.getSubsidiaryMaterialCost().toString());
				part.setEnergyCost(null == pc.getEnergyCost() ? "" : pc
						.getEnergyCost().toString());
				part.setDeviceCost(null == pc.getDeviceCost() ? "" : pc
						.getDeviceCost().toString());
				part.setResourceCost(null == pc.getResourceCost() ? "" : pc
						.getResourceCost().toString());
			}
			/** -------------�ʼ��������----------------- **/
			tqplist = partService.getTQualityParamByProcessId(tt.getId()
					.toString());
			tqpdModel = new TQualityParamDataModel(tqplist);

			equList = partService.getTEquipmenttypeInfo();// ��ȡ���е��豸����
			ncprogsList = partService.getSelectTUserEquNcprogs(nodeid);// ��ȡ���г����б�

			selectWL = new ArrayList<Map<String, Object>>();
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("no", "��ѡ��");
			selectWL.add(mm);
			List<Map<String, Object>> slist = partService
					.getSelectTMaterailTypeInfo(nodeid);// ��������������
			for (Map<String, Object> add : slist) {
				selectWL.add(add);
			}

			selectDJ = new ArrayList<Map<String, Object>>();
			Map<String, Object> ss = new HashMap<String, Object>();
			ss.put("no", "��ѡ��");
			selectDJ.add(ss);
			List<Map<String, Object>> sdjlist = partService
					.getSelectTCuttertypeInfo(nodeid);// ��������������
			for (Map<String, Object> adc : sdjlist) {
				selectDJ.add(adc);
			}

			part.setInstallTime(null == tt.getInstallTime() ? "" : tt
					.getInstallTime().toString());
			part.setUninstallTime(null == tt.getUninstallTime() ? "" : tt
					.getUninstallTime().toString());
		}

		return selectProcess;
	}

	public String getLoadData() {

		return loadData;
	}

	public void setLoadData(String loadData) {
		this.loadData = loadData;
	}

	/**
	 * ���췽��
	 */
	public PartProcessGuideUpdateBean() {

	}

	/**
	 * ������ϸ
	 */
	public void partProcessDetil() {

	}

	/**
	 * ��һ��
	 */
	public void Next() {
		System.out.println(part.getId());
		System.out.println(part.getTheoryWorktime());
	}

	/**
	 * ��һ��
	 */
	public void Back() {
		System.out.println(part.getId());
	}

	/**
	 * �ļ��ϴ�
	 * 
	 * @param event
	 */
	public void Upload(FileUploadEvent event) {
		file = null;
		file = event.getUploadedFile();

		String uri = StringUtils.getFileName(file);
		part.setFile(uri);
		if (null != file) {
			savaFile(file, uri);
			listdocStorePath.add("/images/part/" + uri);
		}
	}

	public void savaFile(UploadedFile file, String uri) {
		String realPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/static/images/part/");
		System.out.println("realPath=====" + realPath + "" + uri);
		File imageFile = new File(realPath, uri);
		try {
			FileOutputStream fops = new FileOutputStream(imageFile);
			fops.write(file.getData());
			fops.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ����豸
	 */
	public void onaddEqu() {
		if (teti.size() > 0) {
			int maxid = 0;
			for (TProcessEquipmentModel mm : teti) {
				if (Integer.parseInt(mm.getId()) > maxid) {
					maxid = Integer.parseInt(mm.getId());
				}
			}
			TProcessEquipmentModel tt = new TProcessEquipmentModel();
			tt.setId((maxid + 1) + ""); // ѭ��id
			tt.setEquipmentTypeId(addtpEqu.getEquipmentTypeId()); // ����id
			tt.setEquipmentTypeName(addtpEqu.getEquipmentTypeName()); // ��������
			if (null != addtpEqu.getEquipmentName()
					&& !"".equals(addtpEqu.getEquipmentName())) {
				tt.setEquipmentName(addtpEqu.getEquipmentName());// �豸���к�
				TEquipmentInfo ti = partService.getTEquipmentInfoByEquSerialNo(
						addtpEqu.getEquipmentName()).get(0);
				tt.setEquipmentId(ti.getEquId().toString());// �豸id
			}

			teti.add(tt);
			tpeqModel = new TProcessEquipmentDataModel(teti); // ��ʼ���豸DataTable
		} else {
			TProcessEquipmentModel tt = new TProcessEquipmentModel();
			tt.setId("1");
			tt.setEquipmentTypeId(addtpEqu.getEquipmentTypeId()); // ����id
			tt.setEquipmentTypeName(addtpEqu.getEquipmentTypeName()); // ��������
			if (null != addtpEqu.getEquipmentName()
					&& !"".equals(addtpEqu.getEquipmentName())) {
				tt.setEquipmentName(addtpEqu.getEquipmentName());// �豸���к�
				TEquipmentInfo ti = partService.getTEquipmentInfoByEquSerialNo(
						addtpEqu.getEquipmentName()).get(0);
				tt.setEquipmentId(ti.getEquId().toString());// �豸id
			}
			teti.add(tt);
			addtpEqu = new TProcessEquipmentModel();
			tpeqModel = new TProcessEquipmentDataModel(teti); // ��ʼ���豸DataTable
		}
		addtpEqu = new TProcessEquipmentModel();
		equinfolist = new ArrayList<TEquipmentInfo>();
	}

	/**
	 * ɾ���豸
	 */
	public void ondelEqu() {
		for (TProcessEquipmentModel mm : selectedRowEqu) {
			for (int i = 0; i < teti.size(); i++) {
				TProcessEquipmentModel ml = teti.get(i);
				if (mm.getId().equals(ml.getId())) {
					teti.remove(ml);
					break;
				}
			}
		}
		tqpdModel = new TQualityParamDataModel(tqplist); // ��ʼ������DataTable
	}

	/**
	 * �豸������ı��¼�
	 */
	public void selectSBDChange() {
		addtpEqu.getEquipmentTypeId();
	}

	/**
	 * ���ڵ����¼�
	 */
	public void onNodeSelect(NodeSelectEvent event) {
		TEquipmenttypeInfo mc = (TEquipmenttypeInfo) event.getTreeNode()
				.getData();
		part.setEquipmentTypeID(mc.getEquipmentType());
		List sblist = new ArrayList();
		newNodeChildren(mc, sblist);
		equinfolist = partService.getTEquipmentInfo(sblist);
		addtpEqu.setEquipmentTypeId(mc.getId().toString());
		addtpEqu.setEquipmentTypeName(mc.getEquipmentType());
	}

	/**
	 * �ݹ������ȡ�豸id
	 * 
	 * @param tmc
	 * @param sblist
	 * @return
	 */
	public List newNodeChildren(TEquipmenttypeInfo tmc, List sblist) {
		sblist.add(tmc.getId());
		for (TEquipmenttypeInfo tt : tmc.getTEquipmenttypeInfos()) {
			newNodeChildren(tt, sblist);
		}
		return sblist;
	}

	/**
	 * ����
	 */
	public void onEdit() {
		for (MaterialsModel mm : selectedRowPart) {
			for (MaterialsModel ml : materialList) {
				if (mm.getId().equals(ml.getId())) {
					ml.setWlName(mm.getWlName());
					ml.setWlNo(mm.getWlNo());
					ml.setWlnorm(mm.getWlnorm());
					ml.setWlNumber(mm.getWlNumber());
					ml.setWlType(mm.getWlType());
				}
			}
		}

	}

	/**
	 * ɾ��������ʱ����
	 */
	public void onDelete() {
		for (MaterialsModel mm : selectedRowPart) {
			for (int i = 0; i < materialList.size(); i++) {
				MaterialsModel ml = materialList.get(i);
				if (mm.getId().equals(ml.getId())) {
					materialList.remove(ml);
					break;
				}
			}
		}
		mediumPartModel = new TProcessmaterialInfoDataModel(materialList); // ��ʼ������DataTable
	}

	/**
	 * ��������������ı��¼�
	 */
	public void selectWLChange() {
		String no = addWL.getWlNo();
		if (null != no && !no.equals("��ѡ��")) {
			List<TMaterailTypeInfo> srs = partService.getTMaterailTypeInfo(no);
			TMaterailTypeInfo w = srs.get(0);
			addWL.setWlName(w.getName());
			addWL.setWlnorm(w.getNorm());
		} else {
			addWL.setWlName("");
			addWL.setWlnorm("");
		}

	}

	/**
	 * ���������ʱ����
	 */
	public void AddWlPojo() {
		if (materialList.size() > 0) {
			int maxid = 0;
			for (MaterialsModel mm : materialList) {
				if (Integer.parseInt(mm.getId()) > maxid) {
					maxid = Integer.parseInt(mm.getId());
				}
			}
			MaterialsModel tt = new MaterialsModel();
			tt.setId((maxid + 1) + "");
			tt.setWlName(addWL.getWlName());
			tt.setWlNo(addWL.getWlNo());
			tt.setWlnorm(addWL.getWlnorm());
			tt.setWlNumber(addWL.getWlNumber());
			tt.setWlType(addWL.getWlType());
			materialList.add(tt);
			mediumPartModel = new TProcessmaterialInfoDataModel(materialList); // ��ʼ������DataTable
		} else {
			MaterialsModel tt = new MaterialsModel();
			tt.setId("1");
			tt.setWlName(addWL.getWlName());
			tt.setWlNo(addWL.getWlNo());
			tt.setWlnorm(addWL.getWlnorm());
			tt.setWlNumber(addWL.getWlNumber());
			tt.setWlType(addWL.getWlType());
			materialList.add(tt);
			mediumPartModel = new TProcessmaterialInfoDataModel(materialList); // ��ʼ������DataTable
		}
		addWL = new MaterialsModel();

	}

	/**
	 * ����ɾ��
	 */
	public void onZYDelete() {
		for (CuttertypeModel mm : selectedRowDJ) {
			for (int i = 0; i < cuttertypeList.size(); i++) {
				CuttertypeModel ml = cuttertypeList.get(i);
				if (mm.getId().equals(ml.getId())) {
					cuttertypeList.remove(ml);
					break;
				}
			}
		}
		mediumDJModel = new TCuttertypeInfoDataModel(cuttertypeList); // ��ʼ������DataTable
	}

	/**
	 * ���߱༭
	 */
	public void onDJEdit() {
		for (CuttertypeModel mm : selectedRowDJ) {
			for (CuttertypeModel ml : cuttertypeList) {
				if (mm.getId().equals(ml.getId())) {
					ml.setName(mm.getName());
					ml.setNo(mm.getNo());
					ml.setRequirementNum(mm.getRequirementNum());
				}
			}
		}
	}

	/**
	 * ����������ı��¼�
	 */
	public void selectDJChange() {
		String no = addDJ.getNo();
		if (null != no && !no.equals("��ѡ��")) {
			List<TCuttertypeInfo> srs = partService.getTCuttertypeInfo(no);
			TCuttertypeInfo w = srs.get(0);
			addDJ.setName(w.getName());
			addDJ.setDid(w.getId().toString());
		} else {
			addDJ.setName("");
		}
	}

	/**
	 * ��ӵ�����ʱ����
	 */
	public void AddDjPojo() {
		if (cuttertypeList.size() > 0) {
			int maxid = 0;
			for (CuttertypeModel mm : cuttertypeList) {
				if (Integer.parseInt(mm.getId()) > maxid) {
					maxid = Integer.parseInt(mm.getId());
				}
			}
			CuttertypeModel tt = new CuttertypeModel();
			tt.setId((maxid + 1) + "");
			tt.setName(addDJ.getName());
			tt.setNo(addDJ.getNo());
			tt.setRequirementNum(addDJ.getRequirementNum());
			tt.setDid(addDJ.getDid());
			cuttertypeList.add(tt);
			mediumDJModel = new TCuttertypeInfoDataModel(cuttertypeList); // ��ʼ������DataTable
		} else {
			CuttertypeModel tt = new CuttertypeModel();
			tt.setId("1");
			tt.setName(addDJ.getName());
			tt.setNo(addDJ.getNo());
			tt.setRequirementNum(addDJ.getRequirementNum());
			tt.setDid(addDJ.getDid());
			cuttertypeList.add(tt);
			mediumDJModel = new TCuttertypeInfoDataModel(cuttertypeList); // ��ʼ������DataTable
		}
		addDJ = new CuttertypeModel();
	}

	/**
	 * �о�������ı��¼�
	 */
	public void selectJJChange() {
		String id = addtftm.getFixturetypeId().toString();
		if (null != id && !id.equals("��ѡ��")) {
			List<TFixtureTypeInfo> srs = partService
					.getTFixtureTypeInfoById(id);
			TFixtureTypeInfo tf = srs.get(0);
			addtftm.setFixtureNO(tf.getFixturesNo());
			addtftm.setFixtureName(tf.getFixturesName());
			addtftm.setFixturetypeId(tf.getId());
		}
	}

	/**
	 * �о����������ı��¼�
	 */
	public void selectJJLBChange() {
		jjlist = partService.getSelectTFixturesInfo(jjflb, nodeid);// �о�����������
		addtftm = new TProcessFixturetypeModel();
	}

	/**
	 * ��Ӽо���ʱ����
	 */
	public void addJJ() {
		if (tftmlist.size() > 0) {
			int maxid = 0;
			for (TProcessFixturetypeModel mm : tftmlist) {
				if (Integer.parseInt(mm.getId().toString()) > maxid) {
					maxid = Integer.parseInt(mm.getId().toString());
				}
			}
			TProcessFixturetypeModel tt = new TProcessFixturetypeModel();
			tt.setId(Long.parseLong((maxid + 1) + ""));
			tt.setFixtureName(addtftm.getFixtureName());
			tt.setFixtureNO(addtftm.getFixtureNO());
			tt.setFixturetypeId(addtftm.getFixturetypeId());
			tftmlist.add(tt);
			tpftdm = new TProcessFixturetypeDataModel(tftmlist); // ��ʼ������DataTable
		} else {
			TProcessFixturetypeModel tt = new TProcessFixturetypeModel();
			tt.setId(Long.parseLong("1"));
			tt.setFixtureName(addtftm.getFixtureName());
			tt.setFixtureNO(addtftm.getFixtureNO());
			tt.setFixturetypeId(addtftm.getFixturetypeId());
			tftmlist.add(tt);
			tpftdm = new TProcessFixturetypeDataModel(tftmlist); // ��ʼ���о�DataTable
		}
		addtftm = new TProcessFixturetypeModel();
		jjlist = new ArrayList<TFixtureTypeInfo>();
	}

	/**
	 * ɾ���о���ʱ����
	 */
	public void delJJ() {
		for (TProcessFixturetypeModel mm : selectRowJJ) {
			for (int i = 0; i < tftmlist.size(); i++) {
				TProcessFixturetypeModel ml = tftmlist.get(i);
				if (mm.getId().equals(ml.getId())) {
					tftmlist.remove(ml);
					break;
				}
			}
		}
		tpftdm = new TProcessFixturetypeDataModel(tftmlist); // ��ʼ���о�DataTable
	}

	/**
	 * �����ʼ�
	 */
	public void addzj() {
		if (tqplist.size() > 0) {
			int maxid = 0;
			for (TQualityParam mm : tqplist) {
				if (Integer.parseInt(mm.getId().toString()) > maxid) {
					maxid = Integer.parseInt(mm.getId().toString());
				}
			}
			TQualityParam tt = new TQualityParam();
			tt.setId(Long.parseLong((maxid + 1) + ""));
			if (null != tqp.getQualityNo())
				tt.setQualityNo(tqp.getQualityNo());

			if (null != tqp.getQualityName())
				tt.setQualityName(tqp.getQualityName());

			if (null != tqp.getStandardValue())
				tt.setStandardValue(tqp.getStandardValue());

			if (null != tqp.getUnit())
				tt.setUnit(tqp.getUnit());

			if (null != tqp.getMaxValue())
				tt.setMaxValue(tqp.getMaxValue());

			if (null != tqp.getMinValue())
				tt.setMinValue(tqp.getMinValue());

			if (null != tqp.getMinTolerance())
				tt.setMinTolerance(tqp.getMinTolerance());

			if (null != tqp.getMaxTolerance())
				tt.setMaxTolerance(tqp.getMaxTolerance());

			if (null != tqp.getIsKey())
				tt.setIsKey(tqp.getIsKey());

			if (null != tqp.getDescription())
				tt.setDescription(tqp.getDescription());

			if (null != tqp.getCheckTime())
				tt.setCheckTime(tqp.getCheckTime());

			if (null != tqp.getCheckType())
				tt.setCheckType(tqp.getCheckType());

			tqplist.add(tt);
			tqpdModel = new TQualityParamDataModel(tqplist); // ��ʼ������DataTable
		} else {
			TQualityParam tt = new TQualityParam();
			tt.setId(Long.parseLong("1"));
			if (null != tqp.getQualityNo())
				tt.setQualityNo(tqp.getQualityNo());

			if (null != tqp.getQualityName())
				tt.setQualityName(tqp.getQualityName());

			if (null != tqp.getStandardValue())
				tt.setStandardValue(tqp.getStandardValue());

			if (null != tqp.getUnit())
				tt.setUnit(tqp.getUnit());

			if (null != tqp.getMaxValue())
				tt.setMaxValue(tqp.getMaxValue());

			if (null != tqp.getMinValue())
				tt.setMinValue(tqp.getMinValue());

			if (null != tqp.getMinTolerance())
				tt.setMinTolerance(tqp.getMinTolerance());

			if (null != tqp.getMaxTolerance())
				tt.setMaxTolerance(tqp.getMaxTolerance());

			if (null != tqp.getIsKey())
				tt.setIsKey(tqp.getIsKey());

			if (null != tqp.getDescription())
				tt.setDescription(tqp.getDescription());

			if (null != tqp.getCheckTime())
				tt.setCheckTime(tqp.getCheckTime());

			if (null != tqp.getCheckType())
				tt.setCheckType(tqp.getCheckType());
			tqplist.add(tt);
			tqpdModel = new TQualityParamDataModel(tqplist); // ��ʼ������DataTable
		}
		tqp = new TQualityParam();
	}

	/**
	 * ɾ���ʼ�
	 */
	public void deletezj() {
		for (TQualityParam mm : selectedRowtqp) {
			for (int i = 0; i < tqplist.size(); i++) {
				TQualityParam ml = tqplist.get(i);
				if (mm.getId().equals(ml.getId())) {
					tqplist.remove(ml);
					break;
				}
			}
		}
		tqpdModel = new TQualityParamDataModel(tqplist); // ��ʼ������DataTable
	}

	public void ProcessOrderOnblur() {
		if (null != part.getProcessOrder()
				&& !"".equals(part.getProcessOrder())) {
			if (null == part.getOnlineProcessID()
					|| "".equals(part.getOnlineProcessID())) {
				if (null != pprs && pprs.size() > 0) {
					int max = 10000;
					for (TProcessInfo tpi : pprs) {
						if (tpi.getProcessOrder() < max) {
							max = tpi.getProcessOrder();
						}
					}
					if (max > Integer.parseInt(part.getProcessOrder())) {
						FacesMessage msg = new FacesMessage("��������һ��",
								"�������ƫС���޸Ĺ������!");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					} else {
						FacesMessage msg = new FacesMessage("��������һ��",
								"�Ѵ����׵�����������ѡ���׵�����!");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
				}
			} else {
				TProcessInfo tpi = partService
						.getTProcessInfoByOnlineProcessID(
								part.getOnlineProcessID()).get(0);
				if (tpi.getProcessOrder() > Integer.parseInt(part
						.getProcessOrder())) {
					FacesMessage msg = new FacesMessage("��������һ��",
							"������ű�ǰ���������Ŵ�,�������������");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		} else {
			FacesMessage msg = new FacesMessage("��������һ��", "�����빤����ţ�");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void processTypeChange() {
		if (part.getProcessType().equals("1")
				|| part.getProcessType().equals("2")) {
			part.setOfflineProcess("0");
		} else {
			part.setOfflineProcess("1");
		}
	}

	/**
	 * ��������
	 */
	public void Save() {
		if ("1".equals(part.getProcessType())) {
			updateSuccess = "����ʧ��";
			if (null == part.getNo() || "".equals(part.getNo())) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,�����빤����!");
				FacesContext.getCurrentInstance().addMessage(null, msg);

			} else if (null == part.getName() || "".equals(part.getName())) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,�����빤������!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == teti || teti.size() < 1) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,�봴���豸!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			/*
			 * else if(null==materialList||materialList.size()<1){ FacesMessage
			 * msg = new FacesMessage("�����򵼱���","����ʧ��,�봴������!");
			 * FacesContext.getCurrentInstance().addMessage(null, msg); }
			 */
			else {
				String mess = partService.savePartProcessGuideTest(part,
						materialList, cuttertypeList, selectProcess, tqplist,
						teti, tftmlist);
				if (mess.equals("����ɹ�")) {
					FacesMessage msg = new FacesMessage("�����򵼱���", "�ļ�����ɹ�");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					updateSuccess = "���³ɹ�";
				} else if (mess.equals("�Ѵ���")) {
					FacesMessage msg = new FacesMessage("�����򵼱���",
							"����ʧ��,�����Ѿ�����!");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,"
							+ mess);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		} else if ("2".equals(part.getProcessType())) {
			updateSuccess = "����ʧ��";
			if (null == part.getNo() || "".equals(part.getNo())) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,�����빤����!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == part.getName() || "".equals(part.getName())) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,�����빤������!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == part.getIscheck()
					|| "".equals(part.getIscheck())) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,��ѡ���Ƿ�ؼ�!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == part.getProgramID()
					|| "��ѡ��".equals(part.getProgramID())) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,��ѡ��ӹ�����!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == part.getTheoryWorktime()
					|| "".equals(part.getTheoryWorktime())) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,���������ʱ��!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == teti || teti.size() < 1) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,�봴���豸!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			/*
			 * else if(null==materialList||materialList.size()<1){ FacesMessage
			 * msg = new FacesMessage("�����򵼱���","����ʧ��,�봴������!");
			 * FacesContext.getCurrentInstance().addMessage(null, msg); } else
			 * if(null==tftmlist||tftmlist.size()<1){ FacesMessage msg = new
			 * FacesMessage("�����򵼱���","����ʧ��,�봴���о�!");
			 * FacesContext.getCurrentInstance().addMessage(null, msg); }else
			 * if(null==cuttertypeList||cuttertypeList.size()<1){ FacesMessage
			 * msg = new FacesMessage("�����򵼱���","����ʧ��,�봴������!");
			 * FacesContext.getCurrentInstance().addMessage(null, msg); } else
			 * if(null==tqplist||tqplist.size()<1){ FacesMessage msg = new
			 * FacesMessage("�����򵼱���","����ʧ��,�봴���ʼ�!");
			 * FacesContext.getCurrentInstance().addMessage(null, msg); }
			 */
			else {
				String mess = partService.savePartProcessGuideTest(part,
						materialList, cuttertypeList, selectProcess, tqplist,
						teti, tftmlist);
				if (mess.equals("����ɹ�")) {
					FacesMessage msg = new FacesMessage("�����򵼱���", "�ļ�����ɹ�");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					updateSuccess = "���³ɹ�";
				} else if (mess.equals("�Ѵ���")) {
					FacesMessage msg = new FacesMessage("�����򵼱���",
							"����ʧ��,�����Ѿ�����!");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,"
							+ mess);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		} else if ("3".equals(part.getProcessType())) {
			updateSuccess = "����ʧ��";
			if (null == part.getNo() || "".equals(part.getNo())) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,�����빤����!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == part.getName() || "".equals(part.getName())) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,�����빤������!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == part.getIscheck()
					|| "".equals(part.getIscheck())) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,��ѡ���Ƿ�ؼ�!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == teti || teti.size() < 1) {
				FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,�봴���豸!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			/*
			 * else if(null==tqplist||tqplist.size()<1){ FacesMessage msg = new
			 * FacesMessage("�����򵼱���","����ʧ��,�봴���ʼ�!");
			 * FacesContext.getCurrentInstance().addMessage(null, msg); }
			 */
			else {
				String mess = partService.savePartProcessGuideTest(part,
						materialList, cuttertypeList, selectProcess, tqplist,
						teti, tftmlist);
				if (mess.equals("����ɹ�")) {
					FacesMessage msg = new FacesMessage("�����򵼱���", "�ļ�����ɹ�");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					updateSuccess = "���³ɹ�";
				} else if (mess.equals("�Ѵ���")) {
					FacesMessage msg = new FacesMessage("�����򵼱���",
							"����ʧ��,�����Ѿ�����!");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,"
							+ mess);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		} else {
			FacesMessage msg = new FacesMessage("�����򵼱���", "����ʧ��,�����빤������!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			updateSuccess = "����ʧ��";
		}

	}

	public PartModel getPart() {
		return part;
	}

	public void setPart(PartModel part) {
		this.part = part;
	}

	public void setSelectProcess(String selectProcess) {
		this.selectProcess = selectProcess;
	}

	public String getSelectProcessPlan() {
		return selectProcessPlan;
	}

	public void setSelectProcessPlan(String selectProcessPlan) {
		this.selectProcessPlan = selectProcessPlan;
	}

	public IPartService getPartService() {
		return partService;
	}

	public void setPartService(IPartService partService) {
		this.partService = partService;
	}

	public List<TEquipmenttypeInfo> getEquList() {
		return equList;
	}

	public void setEquList(List<TEquipmenttypeInfo> equList) {
		this.equList = equList;
	}

	public List<Map<String, Object>> getNcprogsList() {
		return ncprogsList;
	}

	public void setNcprogsList(List<Map<String, Object>> ncprogsList) {
		this.ncprogsList = ncprogsList;
	}

	public TProcessmaterialInfoDataModel getMediumPartModel() {
		return mediumPartModel;
	}

	public void setMediumPartModel(TProcessmaterialInfoDataModel mediumPartModel) {
		this.mediumPartModel = mediumPartModel;
	}

	public MaterialsModel[] getSelectedRowPart() {
		return selectedRowPart;
	}

	public void setSelectedRowPart(MaterialsModel[] selectedRowPart) {
		this.selectedRowPart = selectedRowPart;
	}

	public MaterialsModel getAddWL() {
		return addWL;
	}

	public void setAddWL(MaterialsModel addWL) {
		this.addWL = addWL;
	}

	public List<MaterialsModel> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<MaterialsModel> materialList) {
		this.materialList = materialList;
	}

	public List<Map<String, Object>> getSelectWL() {
		return selectWL;
	}

	public void setSelectWL(List<Map<String, Object>> selectWL) {
		this.selectWL = selectWL;
	}

	public TCuttertypeInfoDataModel getMediumDJModel() {
		return mediumDJModel;
	}

	public void setMediumDJModel(TCuttertypeInfoDataModel mediumDJModel) {
		this.mediumDJModel = mediumDJModel;
	}

	public CuttertypeModel[] getSelectedRowDJ() {
		return selectedRowDJ;
	}

	public void setSelectedRowDJ(CuttertypeModel[] selectedRowDJ) {
		this.selectedRowDJ = selectedRowDJ;
	}

	public CuttertypeModel getAddDJ() {
		return addDJ;
	}

	public void setAddDJ(CuttertypeModel addDJ) {
		this.addDJ = addDJ;
	}

	public List<CuttertypeModel> getCuttertypeList() {
		return cuttertypeList;
	}

	public void setCuttertypeList(List<CuttertypeModel> cuttertypeList) {
		this.cuttertypeList = cuttertypeList;
	}

	public List<Map<String, Object>> getSelectDJ() {
		return selectDJ;
	}

	public void setSelectDJ(List<Map<String, Object>> selectDJ) {
		this.selectDJ = selectDJ;
	}

	public Map<String, Object> getProcessDetail() {
		return processDetail;
	}

	public void setProcessDetail(Map<String, Object> processDetail) {
		this.processDetail = processDetail;
	}

	public List<TFixtureTypeInfo> getJjlist() {
		return jjlist;
	}

	public void setJjlist(List<TFixtureTypeInfo> jjlist) {
		this.jjlist = jjlist;
	}

	public List<TQualityParam> getTqplist() {
		return tqplist;
	}

	public void setTqplist(List<TQualityParam> tqplist) {
		this.tqplist = tqplist;
	}

	public TQualityParam getTqp() {
		return tqp;
	}

	public void setTqp(TQualityParam tqp) {
		this.tqp = tqp;
	}

	public TQualityParamDataModel getTqpdModel() {
		return tqpdModel;
	}

	public void setTqpdModel(TQualityParamDataModel tqpdModel) {
		this.tqpdModel = tqpdModel;
	}

	public TQualityParam[] getSelectedRowtqp() {
		return selectedRowtqp;
	}

	public void setSelectedRowtqp(TQualityParam[] selectedRowtqp) {
		this.selectedRowtqp = selectedRowtqp;
	}

	public TProcessEquipmentDataModel getTpeqModel() {
		return tpeqModel;
	}

	public void setTpeqModel(TProcessEquipmentDataModel tpeqModel) {
		this.tpeqModel = tpeqModel;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public List<TEquipmentInfo> getEquinfolist() {
		return equinfolist;
	}

	public void setEquinfolist(List<TEquipmentInfo> equinfolist) {
		this.equinfolist = equinfolist;
	}

	public TProcessEquipmentModel getAddtpEqu() {
		return addtpEqu;
	}

	public void setAddtpEqu(TProcessEquipmentModel addtpEqu) {
		this.addtpEqu = addtpEqu;
	}

	public TProcessEquipmentModel[] getSelectedRowEqu() {
		return selectedRowEqu;
	}

	public void setSelectedRowEqu(TProcessEquipmentModel[] selectedRowEqu) {
		this.selectedRowEqu = selectedRowEqu;
	}

	public List<TProcessEquipmentModel> getTeti() {
		return teti;
	}

	public void setTeti(List<TProcessEquipmentModel> teti) {
		this.teti = teti;
	}

	public List<TProcessFixturetypeModel> getTftmlist() {
		return tftmlist;
	}

	public void setTftmlist(List<TProcessFixturetypeModel> tftmlist) {
		this.tftmlist = tftmlist;
	}

	public TProcessFixturetypeModel getAddtftm() {
		return addtftm;
	}

	public void setAddtftm(TProcessFixturetypeModel addtftm) {
		this.addtftm = addtftm;
	}

	public TProcessFixturetypeDataModel getTpftdm() {
		return tpftdm;
	}

	public void setTpftdm(TProcessFixturetypeDataModel tpftdm) {
		this.tpftdm = tpftdm;
	}

	public TProcessFixturetypeModel[] getSelectRowJJ() {
		return selectRowJJ;
	}

	public void setSelectRowJJ(TProcessFixturetypeModel[] selectRowJJ) {
		this.selectRowJJ = selectRowJJ;
	}

	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public List<TFixtureClassInfo> getJjlblist() {
		return jjlblist;
	}

	public void setJjlblist(List<TFixtureClassInfo> jjlblist) {
		this.jjlblist = jjlblist;
	}

	public String getJjflb() {
		return jjflb;
	}

	public void setJjflb(String jjflb) {
		this.jjflb = jjflb;
	}

	public List<TProcessInfo> getPprs() {
		return pprs;
	}

	public void setPprs(List<TProcessInfo> pprs) {
		this.pprs = pprs;
	}

	public String getUpdateSuccess() {
		return updateSuccess;
	}

	public void setUpdateSuccess(String updateSuccess) {
		this.updateSuccess = updateSuccess;
	}

}

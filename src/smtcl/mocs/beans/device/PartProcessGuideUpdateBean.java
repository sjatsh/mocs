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
 * 零件工序向导更新
 * 
 * @创建时间 2013-07-23
 * @作者 liguoqiang
 * @修改者：
 * @修改日期：
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name = "partProcessGuideUpdateBean")
@ViewScoped
public class PartProcessGuideUpdateBean {
	/**
	 * 零件帮助模型
	 */
	private PartModel part = new PartModel();
	/**
	 * 零件接口实例
	 */
	private IPartService partService = (IPartService) ServiceFactory
			.getBean("partService");
	/**
	 * 资源接口实例
	 */
	private IResourceService resourceService = (IResourceService) ServiceFactory
			.getBean("resourceService");
	/**
	 * 零件编号
	 */
	private String selectProcess = "";
	/**
	 * 工艺方案id
	 */
	private String selectProcessPlan;
	/**
	 * 上传文件
	 */
	private UploadedFile file;
	/**
	 * 封装上传文件 图片路径
	 */
	private List<String> listdocStorePath = new ArrayList<String>();
	/**
	 * 设备类型下拉框列表
	 */
	private List<TEquipmenttypeInfo> equList;
	/**
	 * 设备列表
	 */
	private List<TEquipmentInfo> equinfolist;
	/**
	 * 程序下拉框列表
	 */
	private List<Map<String, Object>> ncprogsList;

	/**
	 * 物料dataTable数据
	 */
	private TProcessmaterialInfoDataModel mediumPartModel;

	/**
	 * 物料dataTable选中的行
	 */
	private MaterialsModel[] selectedRowPart;

	/**
	 * 新增物料
	 */
	private MaterialsModel addWL = new MaterialsModel();
	/**
	 * 物料临时数据存储
	 */
	private List<MaterialsModel> materialList = new ArrayList<MaterialsModel>();
	/**
	 * 物料下拉框数据
	 */
	private List<Map<String, Object>> selectWL = new ArrayList<Map<String, Object>>();
	/**
	 * 刀具dataTable数据
	 */
	private TCuttertypeInfoDataModel mediumDJModel;
	/**
	 * 刀具dataTable选中的行
	 */
	private CuttertypeModel[] selectedRowDJ;
	/**
	 * 新增刀具
	 */
	private CuttertypeModel addDJ = new CuttertypeModel();
	/**
	 * 刀具临时数据存储
	 */
	private List<CuttertypeModel> cuttertypeList = new ArrayList<CuttertypeModel>();
	/**
	 * 刀具下拉框数据
	 */
	private List<Map<String, Object>> selectDJ = new ArrayList<Map<String, Object>>();

	/**
	 * 程序详细列表
	 */
	private Map<String, Object> processDetail;

	/**
	 * 夹具下拉框列表
	 */
	private List<TFixtureTypeInfo> jjlist;
	/**
	 * 夹具类别下拉框
	 */
	private List<TFixtureClassInfo> jjlblist;
	/**
	 * 夹具类别选中项
	 */
	private String jjflb;
	/**
	 * 数据加载对象
	 */
	private String loadData = "1";

	/**
	 * 临时质检对象集合
	 */
	private List<TQualityParam> tqplist = new ArrayList<TQualityParam>();
	/**
	 * 新增质检对象
	 */
	private TQualityParam tqp = new TQualityParam();
	/**
	 * 质检dataTable数据
	 */
	private TQualityParamDataModel tqpdModel;
	/**
	 * 质检dataTable选中的行
	 */
	private TQualityParam[] selectedRowtqp;

	/**
	 * 临时设备对象集合
	 */
	private List<TProcessEquipmentModel> teti = new ArrayList<TProcessEquipmentModel>();
	/**
	 * 新增设备对象
	 */
	private TProcessEquipmentModel addtpEqu = new TProcessEquipmentModel();
	/**
	 * 设备datable数据
	 */
	private TProcessEquipmentDataModel tpeqModel;
	/**
	 * 设备datable选中行
	 */
	private TProcessEquipmentModel[] selectedRowEqu;
	/**
	 * 设备节点的信息
	 */
	private TreeNode root;
	/**
	 * 设备当前选中节点信息
	 */
	private TreeNode selectedNode;

	/**
	 * 临时夹具对象集合
	 */
	private List<TProcessFixturetypeModel> tftmlist = new ArrayList<TProcessFixturetypeModel>();
	/**
	 * 新增夹具关联
	 */
	private TProcessFixturetypeModel addtftm = new TProcessFixturetypeModel();
	/**
	 * 夹具datable
	 */
	private TProcessFixturetypeDataModel tpftdm;
	/**
	 * 当前选中的夹具
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
		String sp = (String) session.getAttribute("selectProcess");// 零件编号
		String spp = (String) session.getAttribute("selectProcessPlan");// 工艺方案id
		String pid = (String) session.getAttribute("processId");// 工序id
		nodeid = session.getAttribute("nodeid2") + "";
		System.out.println(sp);
		System.out.println(spp);
		System.out.println(pid + "--" + sycpid);
		System.out.println(loadData);
		if (loadData.equals("1") || !sycpid.equals(pid)) {
			loadData = "0";
			sycpid = pid;
			if (Constants.LOADCOUNT <= 1) {
				/*------新增清空数据start--------*/
				part = new PartModel();// 清空零件模型

				teti = new ArrayList<TProcessEquipmentModel>();// 清空设备类型列表
				// tpeqModel =new TProcessEquipmentDataModel(teti);

				materialList = new ArrayList<MaterialsModel>();// 清空物料列表
				// mediumPartModel =new
				// TProcessmaterialInfoDataModel(materialList); //初始化物料DataTable

				tftmlist = new ArrayList<TProcessFixturetypeModel>();// 清空夹具列表
				tpftdm = new TProcessFixturetypeDataModel(tftmlist); // 初始化夹具DataTable

				cuttertypeList = new ArrayList<CuttertypeModel>();// 清空刀具数据
				// mediumDJModel =new TCuttertypeInfoDataModel(cuttertypeList);
				// //初始化刀具DataTable

				tqplist = new ArrayList<TQualityParam>();// 清空质检
				tqpdModel = new TQualityParamDataModel(tqplist);
				/*------新增清空数据end--------*/
				++Constants.LOADCOUNT;
			}
			List<TProcessplanInfo> tProcessplanInfoList = partService
					.getTProcessplanInfoById(spp); // 工艺方案
			part.settProcessplanInfo(tProcessplanInfoList.get(0));
			if (null != sp) {
				selectProcess = sp;
			}
			pprs = partService.getTProcessInfo(spp);// 加载前置工序列表
			for (int i = 0; i < pprs.size(); i++) {
				TProcessInfo tpi = pprs.get(i);
				if (tpi.getId().toString().equals(pid)) {
					pprs.remove(i);
					break;
				}
			}
			selectProcessPlan = tProcessplanInfoList.get(0).getName();// 获取工艺方案名称
			System.out.println(selectProcess);
			tt = partService.getTProcessInfoById(pid);// 获取当前工序
			/** -------------基本信息数据填充----------------- **/
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
			part.settProcessplanInfo(tt.getTProcessplanInfo()); // 设置工艺方案
			part.setIscheck((null == tt.getNeedcheck() ? "" : tt.getNeedcheck()
					.toString())); // 是否必检
			part.setChecktype((null == tt.getCheckType() ? "" : tt
					.getCheckType().toString())); // 检验类型

			/** -------------机台数据填充----------------- **/

			root = partService.getTreeNodeEquInfo(nodeid);// 设置设备类型
			teti = partService.getTProcessEquipmentByProcessId(tt.getId()
					.toString());
			tpeqModel = new TProcessEquipmentDataModel(teti); // 初始化设备datable

			part.setProgramID(null == tt.getProgramId() ? "" : tt
					.getProgramId().toString()); // 设置程序
			part.setProcessingTime(null == tt.getProcessingTime() ? "" : tt
					.getProcessingTime().toString());
			part.setTheoryWorktime(null == tt.getTheoryWorktime() ? "" : tt
					.getTheoryWorktime().toString());
			part.setCapacity(null == tt.getCapacity() ? "" : tt.getCapacity()
					.toString());

			/** -------------物料数据填充----------------- **/
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
			mediumPartModel = new TProcessmaterialInfoDataModel(materialList); // 初始化物料DataTable

			/** -------------资源数据填充----------------- **/
			jjlblist = resourceService
					.getTFixtureClassInfoByQuery(null, nodeid);// 夹具类别下拉框数据加载
			tftmlist = partService.getTProcessFixturetypeModelByProcessId(tt
					.getId().toString());
			tpftdm = new TProcessFixturetypeDataModel(tftmlist);// 加载夹具数据

			List<Map<String, Object>> tclist = partService
					.getTProcessCuttertypeInfo(tt.getId().toString(), nodeid); // 刀具数据查询

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
			mediumDJModel = new TCuttertypeInfoDataModel(cuttertypeList); // 初始化刀具DataTable
			/** -------------成本数据填充----------------- **/
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
			/** -------------质检数据填充----------------- **/
			tqplist = partService.getTQualityParamByProcessId(tt.getId()
					.toString());
			tqpdModel = new TQualityParamDataModel(tqplist);

			equList = partService.getTEquipmenttypeInfo();// 获取所有的设备类型
			ncprogsList = partService.getSelectTUserEquNcprogs(nodeid);// 获取所有程序列表

			selectWL = new ArrayList<Map<String, Object>>();
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("no", "请选择");
			selectWL.add(mm);
			List<Map<String, Object>> slist = partService
					.getSelectTMaterailTypeInfo(nodeid);// 物料下拉框数据
			for (Map<String, Object> add : slist) {
				selectWL.add(add);
			}

			selectDJ = new ArrayList<Map<String, Object>>();
			Map<String, Object> ss = new HashMap<String, Object>();
			ss.put("no", "请选择");
			selectDJ.add(ss);
			List<Map<String, Object>> sdjlist = partService
					.getSelectTCuttertypeInfo(nodeid);// 刀具下拉框数据
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
	 * 构造方法
	 */
	public PartProcessGuideUpdateBean() {

	}

	/**
	 * 程序详细
	 */
	public void partProcessDetil() {

	}

	/**
	 * 下一步
	 */
	public void Next() {
		System.out.println(part.getId());
		System.out.println(part.getTheoryWorktime());
	}

	/**
	 * 上一步
	 */
	public void Back() {
		System.out.println(part.getId());
	}

	/**
	 * 文件上传
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
	 * 添加设备
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
			tt.setId((maxid + 1) + ""); // 循环id
			tt.setEquipmentTypeId(addtpEqu.getEquipmentTypeId()); // 类型id
			tt.setEquipmentTypeName(addtpEqu.getEquipmentTypeName()); // 类型名字
			if (null != addtpEqu.getEquipmentName()
					&& !"".equals(addtpEqu.getEquipmentName())) {
				tt.setEquipmentName(addtpEqu.getEquipmentName());// 设备序列号
				TEquipmentInfo ti = partService.getTEquipmentInfoByEquSerialNo(
						addtpEqu.getEquipmentName()).get(0);
				tt.setEquipmentId(ti.getEquId().toString());// 设备id
			}

			teti.add(tt);
			tpeqModel = new TProcessEquipmentDataModel(teti); // 初始化设备DataTable
		} else {
			TProcessEquipmentModel tt = new TProcessEquipmentModel();
			tt.setId("1");
			tt.setEquipmentTypeId(addtpEqu.getEquipmentTypeId()); // 类型id
			tt.setEquipmentTypeName(addtpEqu.getEquipmentTypeName()); // 类型名字
			if (null != addtpEqu.getEquipmentName()
					&& !"".equals(addtpEqu.getEquipmentName())) {
				tt.setEquipmentName(addtpEqu.getEquipmentName());// 设备序列号
				TEquipmentInfo ti = partService.getTEquipmentInfoByEquSerialNo(
						addtpEqu.getEquipmentName()).get(0);
				tt.setEquipmentId(ti.getEquId().toString());// 设备id
			}
			teti.add(tt);
			addtpEqu = new TProcessEquipmentModel();
			tpeqModel = new TProcessEquipmentDataModel(teti); // 初始化设备DataTable
		}
		addtpEqu = new TProcessEquipmentModel();
		equinfolist = new ArrayList<TEquipmentInfo>();
	}

	/**
	 * 删除设备
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
		tqpdModel = new TQualityParamDataModel(tqplist); // 初始化刀具DataTable
	}

	/**
	 * 设备下拉框改变事件
	 */
	public void selectSBDChange() {
		addtpEqu.getEquipmentTypeId();
	}

	/**
	 * 树节点点击事件
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
	 * 递归遍历获取设备id
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
	 * 更新
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
	 * 删除物料临时数据
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
		mediumPartModel = new TProcessmaterialInfoDataModel(materialList); // 初始化物料DataTable
	}

	/**
	 * 新增物料下拉框改变事件
	 */
	public void selectWLChange() {
		String no = addWL.getWlNo();
		if (null != no && !no.equals("请选择")) {
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
	 * 添加物料临时数据
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
			mediumPartModel = new TProcessmaterialInfoDataModel(materialList); // 初始化物料DataTable
		} else {
			MaterialsModel tt = new MaterialsModel();
			tt.setId("1");
			tt.setWlName(addWL.getWlName());
			tt.setWlNo(addWL.getWlNo());
			tt.setWlnorm(addWL.getWlnorm());
			tt.setWlNumber(addWL.getWlNumber());
			tt.setWlType(addWL.getWlType());
			materialList.add(tt);
			mediumPartModel = new TProcessmaterialInfoDataModel(materialList); // 初始化物料DataTable
		}
		addWL = new MaterialsModel();

	}

	/**
	 * 刀具删除
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
		mediumDJModel = new TCuttertypeInfoDataModel(cuttertypeList); // 初始化刀具DataTable
	}

	/**
	 * 刀具编辑
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
	 * 刀具下拉框改变事件
	 */
	public void selectDJChange() {
		String no = addDJ.getNo();
		if (null != no && !no.equals("请选择")) {
			List<TCuttertypeInfo> srs = partService.getTCuttertypeInfo(no);
			TCuttertypeInfo w = srs.get(0);
			addDJ.setName(w.getName());
			addDJ.setDid(w.getId().toString());
		} else {
			addDJ.setName("");
		}
	}

	/**
	 * 添加刀具临时数据
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
			mediumDJModel = new TCuttertypeInfoDataModel(cuttertypeList); // 初始化刀具DataTable
		} else {
			CuttertypeModel tt = new CuttertypeModel();
			tt.setId("1");
			tt.setName(addDJ.getName());
			tt.setNo(addDJ.getNo());
			tt.setRequirementNum(addDJ.getRequirementNum());
			tt.setDid(addDJ.getDid());
			cuttertypeList.add(tt);
			mediumDJModel = new TCuttertypeInfoDataModel(cuttertypeList); // 初始化刀具DataTable
		}
		addDJ = new CuttertypeModel();
	}

	/**
	 * 夹具下拉框改变事件
	 */
	public void selectJJChange() {
		String id = addtftm.getFixturetypeId().toString();
		if (null != id && !id.equals("请选择")) {
			List<TFixtureTypeInfo> srs = partService
					.getTFixtureTypeInfoById(id);
			TFixtureTypeInfo tf = srs.get(0);
			addtftm.setFixtureNO(tf.getFixturesNo());
			addtftm.setFixtureName(tf.getFixturesName());
			addtftm.setFixturetypeId(tf.getId());
		}
	}

	/**
	 * 夹具类别下拉框改变事件
	 */
	public void selectJJLBChange() {
		jjlist = partService.getSelectTFixturesInfo(jjflb, nodeid);// 夹具下拉框数据
		addtftm = new TProcessFixturetypeModel();
	}

	/**
	 * 添加夹具临时数据
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
			tpftdm = new TProcessFixturetypeDataModel(tftmlist); // 初始化刀具DataTable
		} else {
			TProcessFixturetypeModel tt = new TProcessFixturetypeModel();
			tt.setId(Long.parseLong("1"));
			tt.setFixtureName(addtftm.getFixtureName());
			tt.setFixtureNO(addtftm.getFixtureNO());
			tt.setFixturetypeId(addtftm.getFixturetypeId());
			tftmlist.add(tt);
			tpftdm = new TProcessFixturetypeDataModel(tftmlist); // 初始化夹具DataTable
		}
		addtftm = new TProcessFixturetypeModel();
		jjlist = new ArrayList<TFixtureTypeInfo>();
	}

	/**
	 * 删除夹具临时数据
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
		tpftdm = new TProcessFixturetypeDataModel(tftmlist); // 初始化夹具DataTable
	}

	/**
	 * 新增质检
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
			tqpdModel = new TQualityParamDataModel(tqplist); // 初始化刀具DataTable
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
			tqpdModel = new TQualityParamDataModel(tqplist); // 初始化刀具DataTable
		}
		tqp = new TQualityParam();
	}

	/**
	 * 删除质检
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
		tqpdModel = new TQualityParamDataModel(tqplist); // 初始化刀具DataTable
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
						FacesMessage msg = new FacesMessage("工序向导下一步",
								"工序序号偏小请修改工序序号!");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					} else {
						FacesMessage msg = new FacesMessage("工序向导下一步",
								"已存在首道工序，请慎重选择首道工序!");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
				}
			} else {
				TProcessInfo tpi = partService
						.getTProcessInfoByOnlineProcessID(
								part.getOnlineProcessID()).get(0);
				if (tpi.getProcessOrder() > Integer.parseInt(part
						.getProcessOrder())) {
					FacesMessage msg = new FacesMessage("工序向导下一步",
							"工序序号比前道工序的序号大,请重新输入序号");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		} else {
			FacesMessage msg = new FacesMessage("工序向导下一步", "请输入工序序号！");
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
	 * 保存数据
	 */
	public void Save() {
		if ("1".equals(part.getProcessType())) {
			updateSuccess = "更新失败";
			if (null == part.getNo() || "".equals(part.getNo())) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请输入工序编号!");
				FacesContext.getCurrentInstance().addMessage(null, msg);

			} else if (null == part.getName() || "".equals(part.getName())) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请输入工序名称!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == teti || teti.size() < 1) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请创建设备!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			/*
			 * else if(null==materialList||materialList.size()<1){ FacesMessage
			 * msg = new FacesMessage("工序向导保存","保存失败,请创建物料!");
			 * FacesContext.getCurrentInstance().addMessage(null, msg); }
			 */
			else {
				String mess = partService.savePartProcessGuideTest(part,
						materialList, cuttertypeList, selectProcess, tqplist,
						teti, tftmlist);
				if (mess.equals("保存成功")) {
					FacesMessage msg = new FacesMessage("工序向导保存", "文件保存成功");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					updateSuccess = "更新成功";
				} else if (mess.equals("已存在")) {
					FacesMessage msg = new FacesMessage("工序向导保存",
							"保存失败,工序已经存在!");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,"
							+ mess);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		} else if ("2".equals(part.getProcessType())) {
			updateSuccess = "更新失败";
			if (null == part.getNo() || "".equals(part.getNo())) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请输入工序编号!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == part.getName() || "".equals(part.getName())) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请输入工序名称!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == part.getIscheck()
					|| "".equals(part.getIscheck())) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请选择是否必检!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == part.getProgramID()
					|| "请选择".equals(part.getProgramID())) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请选择加工程序!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == part.getTheoryWorktime()
					|| "".equals(part.getTheoryWorktime())) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请输入节拍时间!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == teti || teti.size() < 1) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请创建设备!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			/*
			 * else if(null==materialList||materialList.size()<1){ FacesMessage
			 * msg = new FacesMessage("工序向导保存","保存失败,请创建物料!");
			 * FacesContext.getCurrentInstance().addMessage(null, msg); } else
			 * if(null==tftmlist||tftmlist.size()<1){ FacesMessage msg = new
			 * FacesMessage("工序向导保存","保存失败,请创建夹具!");
			 * FacesContext.getCurrentInstance().addMessage(null, msg); }else
			 * if(null==cuttertypeList||cuttertypeList.size()<1){ FacesMessage
			 * msg = new FacesMessage("工序向导保存","保存失败,请创建刀具!");
			 * FacesContext.getCurrentInstance().addMessage(null, msg); } else
			 * if(null==tqplist||tqplist.size()<1){ FacesMessage msg = new
			 * FacesMessage("工序向导保存","保存失败,请创建质检!");
			 * FacesContext.getCurrentInstance().addMessage(null, msg); }
			 */
			else {
				String mess = partService.savePartProcessGuideTest(part,
						materialList, cuttertypeList, selectProcess, tqplist,
						teti, tftmlist);
				if (mess.equals("保存成功")) {
					FacesMessage msg = new FacesMessage("工序向导保存", "文件保存成功");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					updateSuccess = "更新成功";
				} else if (mess.equals("已存在")) {
					FacesMessage msg = new FacesMessage("工序向导保存",
							"保存失败,工序已经存在!");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,"
							+ mess);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		} else if ("3".equals(part.getProcessType())) {
			updateSuccess = "更新失败";
			if (null == part.getNo() || "".equals(part.getNo())) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请输入工序编号!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == part.getName() || "".equals(part.getName())) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请输入工序名称!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == part.getIscheck()
					|| "".equals(part.getIscheck())) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请选择是否必检!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (null == teti || teti.size() < 1) {
				FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请创建设备!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			/*
			 * else if(null==tqplist||tqplist.size()<1){ FacesMessage msg = new
			 * FacesMessage("工序向导保存","保存失败,请创建质检!");
			 * FacesContext.getCurrentInstance().addMessage(null, msg); }
			 */
			else {
				String mess = partService.savePartProcessGuideTest(part,
						materialList, cuttertypeList, selectProcess, tqplist,
						teti, tftmlist);
				if (mess.equals("保存成功")) {
					FacesMessage msg = new FacesMessage("工序向导保存", "文件保存成功");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					updateSuccess = "更新成功";
				} else if (mess.equals("已存在")) {
					FacesMessage msg = new FacesMessage("工序向导保存",
							"保存失败,工序已经存在!");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,"
							+ mess);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		} else {
			FacesMessage msg = new FacesMessage("工序向导保存", "保存失败,请输入工序类型!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			updateSuccess = "更新失败";
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

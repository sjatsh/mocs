package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;
import org.primefaces.model.chart.CartesianChartModel;

import smtcl.mocs.common.device.pages.DataPage;
import smtcl.mocs.common.device.pages.PageListBaseBean;
import smtcl.mocs.common.device.pages.PageListDataModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 节拍分析
 * @作者：ZhaoHongshi
 * @创建时间：2012-12-10 上午9:25:00
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@SuppressWarnings( { "unchecked", "rawtypes","serial" })
@ManagedBean(name = "StatDeviceProFrequencyBean")
@ViewScoped
public class StatDeviceProFrequencyBean extends PageListBaseBean implements Serializable {
	/**
	 * 设备接口实例
	 */
	private IDeviceService deviceService=(IDeviceService) ServiceFactory.getBean("deviceService");
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");// 获取注入;
	/**
	 * 节点ID
	 */
	private String nodeID;
	/**
	 * 结果数据集
	 */
	private IDataCollection<Map<String, Object>> result;
	/**
	 * 设备下所有工件信息
	 */
	private List resultAll;
	/**
	 * 当前节点名称
	 */
	private String thisNodeName = "请选择节点";
	/**
	 * 选择工件下拉框
	 */
	public List toolNameList;
	/**
	 * 设备名称下拉框
	 */
	private List<SelectItem> nameList = new ArrayList<SelectItem>();
	/**
	 * 线状图
	 */
	private String lineChartData;
	/**
	 * 柱状图
	 */
	private String barChartData;

	/**
	 * 工件名称
	 */
	public String toolName;
	
	/**
	 * 开始时间
	 */
	public Date startTime;
	
	/**
	 * 结束时间
	 */
	public Date endTime;
	
	/**
	 * 机床名称下拉框选择
	 */
	private String nameSelected;
	
	/**
	 * 当前节点
	 */
	private String nodeStr; 
	
	/**
	 * 当前节点所有子节点id集合
	 */
	private String nodeids; 
	
	/**
	 * 设备序列号
	 */
	private String equSerialNo = null;
	
	/**
	 * 是否被选择
	 */
	private boolean nocheck;

	public String getLineChartData() {
		lineChartData = toJSON();
		return lineChartData;
	}
	
	public String getBarChartData() {
		barChartData = toJSON2();
		return barChartData;
	}
	
	/**
	 * 查询按钮
	 */
	public void getDeviceProFrequenceStatAction() {
		this.defaultDataModel = null;
	}
	
	public String getNodeID() {
//		com.swg.authority.cache.TreeNode currentNode = (com.swg.authority.cache.TreeNode) FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
//		if (currentNode != null) {
//			this.nodeStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
//			nodeID = nodeStr;
//			nodeids = organizationService.getAllTNodesId(currentNode);
//			getNameList();
//		}
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String node=session.getAttribute("nodeid")+"";
		this.nodeStr=node;
		nodeID = nodeStr;
		List<TNodes> tn=organizationService.getTNodesById(node);
		nodeids = organizationService.getAllTNodesId(tn.get(0));
		getNameList();
		return nodeID;
	}

	public void equipmentSelect(ValueChangeEvent event) {
		Object a=new Object();
		nameSelected = event.getNewValue().toString();
		if (nameSelected != null) {
			if (null == toolNameList)
				toolNameList = new ArrayList();
			else
				toolNameList.clear();
			Collection<Parameter> parameters = new HashSet<Parameter>();
			parameters.add(new Parameter("b.equSerialNo", "equSerialNo",nameSelected, Operator.EQ));
			List<String> list = deviceService.getPartNameByEquName(nameSelected);
			if (null != equSerialNo) {
				//toolNameList.add(new SelectItem(equSerialNo));
			}
			toolNameList.add(new SelectItem(null, "--全部--"));
			for (String str : list) {
				toolNameList.add(new SelectItem(str));
			}
		}
	}

	public PageListDataModel getDefaultDataModel() {
		if (defaultDataModel == null) {
			defaultDataModel = new PageListDataModel(pageSize2) {
				public DataPage fetchPage(int startRow, int pageSize) {
					Collection<Parameter> parameters = new HashSet<Parameter>();
					if (null != startTime) {
						parameters.add(new Parameter("c.starttime","starttime", startTime, Operator.GE));
					}
					if (null != endTime) {
						parameters.add(new Parameter("c.finishtime","finishtime", endTime, Operator.LE));
					}
					if (!StringUtils.isEmpty(toolName)) {
						parameters.add(new Parameter("c.partNo", "partNo",toolName.toString(), Operator.EQ));
					}
					parameters.add(new Parameter("c.equSerialNo","equSerialNo", nameSelected, Operator.EQ));
					result = deviceService.getDeviceProFrequenceStat(startRow/ pageSize + 1, pageSize2, parameters);
					resultAll = deviceService.getAllDeviceProFrequenceStat(parameters);
					return new DataPage(result.getTotalRows(), startRow,result.getData());
				}
			};
		}
		return defaultDataModel;
	}

	public List<SelectItem> getNameList() {
//		String node="8a8abc973f1d7fbb013f1db8b6480001";
	 if (null != nodeStr && !"".equals(nodeStr)) {
		List<TNodes> tn=organizationService.getTNodesById(nodeStr);
		nodeids = organizationService.getAllTNodesId(tn.get(0));
		if (null != nodeStr && !"".equals(nodeStr)) {
			nameList = new ArrayList<SelectItem>();
			List<TEquipmentInfo> nlist = deviceService.getNodesAllEquName(nodeids);
			nameList.add(new SelectItem(null, "--请选择--"));
			for (TEquipmentInfo obj : nlist) {
				nameList.add(new SelectItem(obj.getEquSerialNo(), obj.getEquName() + ""));
			}
		}
	 }
		return nameList;
	}

	private String toJSON() {
		if (result != null) {
			Map<String, Object> data = new HashMap<String, Object>();
			int size = result.getData().size();
			long[] workTime = new long[size];
			String[] cuttingeventId = new String[size];
			for (int i = 0; i < result.getData().size(); i++) {
				Map map = (Map) result.getData().get(i);
				workTime[i] = (Long) map.get("workTime");
				cuttingeventId[i] = (String) map.get("cuttingeventId");
			}
			data.put("cuttingeventId", cuttingeventId);
			data.put("workTime", workTime);
			data.put("workTimeName", "加工用时");
			data.put("title", "加工循环用时");
			data.put("xName", "加工事件ID");
			data.put("yName", "加工用时");
			data.put("size", size);
			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
		}
		return null;
	}

	private String toJSON2() {
		if (resultAll != null) {
			Map<String, Object> dataAll = new HashMap<String, Object>();
			int sizeAll = resultAll.size();
			if (resultAll.size() != 0) {
				long maxWorkTime = (Long) resultAll.get(0);
				long minWorkTime = (Long) resultAll.get(0);
				long[] workTimeAll = new long[sizeAll];
				for (int i = 0; i < resultAll.size(); i++) {
					workTimeAll[i] = (Long) resultAll.get(i);
					if ((Long) resultAll.get(i) > maxWorkTime) {
						maxWorkTime = (Long) resultAll.get(i);
					}
					if ((Long) resultAll.get(i) < minWorkTime) {
						minWorkTime = (Long) resultAll.get(i);
					}
				}
				int n = 5;
				Long num = (maxWorkTime - minWorkTime) / n;
				Long point0 = minWorkTime;
				Long point1 = minWorkTime + num;
				Long point2 = minWorkTime + num * 2;
				Long point3 = minWorkTime + num * 3;
				Long point4 = minWorkTime + num * 4;
				int[] a = new int[5];
				String[] areas = new String[5];
				areas[0] = Long.toString(minWorkTime) + "--" + point1.toString();
				areas[1] = point1.toString() + "--" + point2.toString();
				areas[2] = point2.toString() + "--" + point3.toString();
				areas[3] = point3.toString() + "--" + point4.toString();
				areas[4] = point4.toString() + "--" + Long.toString(maxWorkTime);
				
				for (int j = 0; j < resultAll.size(); j++) {
					if (workTimeAll[j] >= point0 && workTimeAll[j] < point1) {
						a[0]++;
					} else if (workTimeAll[j] >= point1 && workTimeAll[j] < point2) {
						a[1]++;
					} else if (workTimeAll[j] >= point2 && workTimeAll[j] < point3) {
						a[2]++;
					} else if (workTimeAll[j] >= point3 && workTimeAll[j] < point4) {
						a[3]++;
					} else
						a[4]++;
				}
				dataAll.put("title", "循环完成分布时间");
				dataAll.put("xName", "加工用时区间");
				dataAll.put("yName", "事件个数");
				dataAll.put("a", a);
				dataAll.put("areas", areas);
				dataAll.put("name", "事件次数");
				Gson gson = new GsonBuilder().serializeNulls().create();
				return gson.toJson(dataAll);
			}
		}
		return null;
	}
	
	@Override
	public PageListDataModel getExtendDataModel() {
		return null;
	}
	
	/**
	 * 构造方法
	 */
	public StatDeviceProFrequencyBean() {
		if (null == startTime && null == endTime) {
			Date[] datea = StringUtils.convertDatea(1);
			startTime = datea[0];
			endTime = datea[1];
		}
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String equSerialNo = StringUtils.getCookie(request, "equSerialNo");
		if(null != equSerialNo && !"".equals(equSerialNo)){
			nameSelected = equSerialNo;
		}
		getNameList();
	}
	
/*--------------------------------------------------------------------*/

	public void setNameList(List<SelectItem> nameList) {
		this.nameList = nameList;
	}

	public String getNameSelected() {
		return nameSelected;
	}

	public void setNameSelected(String nameSelected) {
		this.nameSelected = nameSelected;
	}

	public void setCategoryModel(CartesianChartModel categoryModel) {
	}

	public IDeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public IDataCollection<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(IDataCollection<Map<String, Object>> result) {
		this.result = result;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List getToolNameList() {
		return toolNameList;
	}

	public void setToolNameList(List toolNameList) {
		this.toolNameList = toolNameList;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getNodeStr() {
		return nodeStr;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

	public IOrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public String getThisNodeName() {
		return thisNodeName;
	}

	public void setThisNodeName(String thisNodeName) {
		this.thisNodeName = thisNodeName;
	}

	public String getNodeids() {
		return nodeids;
	}

	public void setNodeids(String nodeids) {
		this.nodeids = nodeids;
	}

	public String getEquSerialNo() {
		return equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public void setLineChartData(String lineChartData) {
		this.lineChartData = lineChartData;
	}

	public List getResultAll() {
		return resultAll;
	}

	public void setResultAll(List resultAll) {
		this.resultAll = resultAll;
	}

	public void setBarChartData(String barChartData) {
		this.barChartData = barChartData;
	}

}

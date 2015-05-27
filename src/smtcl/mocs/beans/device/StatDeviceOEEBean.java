package smtcl.mocs.beans.device;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.StringUtils;


/**
 * 单台设备时间段OEE分析
 * @创建时间 2012-04-26
 * @作者 gaozhoujun
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name = "StatDeviceOEEBean")
@ViewScoped
public class StatDeviceOEEBean {
	/**
	 * 开始时间
	 */
	private Date start;
	/**
	 * 结束时间
	 */
	private Date end;
	/**
	 * 数据库数据
	 */
	private Map<String, Object> map;
	/**
	 * 当前节点
	 */
	private String nodeStr;
	/**
	 * 设备序列号
	 */
	private String equSerialNo;
	/**
	 * 判断加载节点首个设备序列号
	 * 
	 */
	private String equSerialNoPan;
	/**
	 * 设备列表下拉框
	 */
	private List<SelectItem> nameList = new ArrayList<SelectItem>();
	/**
	 * 柱状图返回数据
	 */
	public String jsonData;
	/**
	 * 判断是否当前节点
	 */
	private String nodeValue;
	/**
	 * 总时间  
	 */
	private Long totalTime;
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");
	/**
	 *设备业务逻辑
	 */
	private IDeviceService deviceService = (IDeviceService) ServiceFactory.getBean("deviceService");

	/**
	 * 获取当前点击节点
	 * @return
	 */
	public String getNodeStr() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String node=session.getAttribute("nodeid")+"";
		if (node != null) {
			if (null != nameList && nameList.size() > 0) {
				nameList.clear();
			}
			this.nodeStr = node; // 将单机节点的值赋给当前节点nodeStr
			List<TNodes> tn=organizationService.getTNodesById(node);
			String nodeids = organizationService.getAllTNodesId(tn.get(0));
			getnameList(nodeids);
			nodeValue = nodeStr;
		}
		equSerialNoPan=null;//清空
		return nodeStr;
	}
	
	/**
	 * 当前节点下的设备 下拉框
	 * 
	 * @param nodeids
	 */
	@SuppressWarnings("unchecked")
	public void getnameList(String nodeids) {
		if (null != nodeStr && !"".equals(nodeStr)) {
			List<TEquipmentInfo> nlist = deviceService.getNodesAllEquName(nodeids);
			for (int i = 0; i < nlist.size(); i++) {
				TEquipmentInfo teinfo = nlist.get(i);
				if (null != teinfo) {
					nameList.add(new SelectItem(teinfo.getEquSerialNo(),(String) teinfo.getEquName()));
				}
				if (nodeValue != nodeStr && null == equSerialNoPan) {// 换节点
					equSerialNo = nlist.get(0).getEquSerialNo();
				}
			}
		}
		selectBeanOEE();
	}

	
	/**
	 * 查询OEE方法
	 */
	public void selectBeanOEE() {
		System.out.println("selectBeanOEE()====");
		System.out.println("selectBeanOEE()=====时间之差===="+(end.getTime()-start.getTime())/1000);
		totalTime=(end.getTime()-start.getTime())/1000;
		if (null != map && map.size() > 0) {
			map.clear();
		}
		Collection<Parameter> parameters = new HashSet<Parameter>();
		// 开始时间条件
		if (null != start) {
			parameters.add(new Parameter("tuserOne.caclDate", "start", start,Operator.GE));
		}
		// 结束时间条件
		if (null != end) {
			parameters.add(new Parameter("tuserOne.caclDate", "end", end,Operator.LE));
		}
		parameters.add(new Parameter("tuserOne.equSerialNo", "equSerialNo",equSerialNo, Operator.EQ));
		List<Map<String, Object>> list = null;
		list = deviceService.selectServiceOEE(StringUtils.formatDate(start, 2),StringUtils.formatDate(end, 2),equSerialNo);
		double[][] tudate = { { 0.0, 0.0, 0.0, 0.0 }, { 0.0, 0.0, 0.0, 0.0 },{ 0.0, 0.0, 0.0, 0.0 }, { 0.0, 0.0, 0.0, 0.0 } };
		if (null != list && list.size() > 0) {
			map = list.get(0);
			if (null != map && map.size() > 0) {
				map.put("totalTimea", StringUtils.SecondIsDDmmss(totalTime)); // 页面显示总使用时间
				map.put("totalTime",Double.parseDouble(totalTime.toString()));
				deviceService.convertData(map);// 调用数据转换方法
				tudate = createHistogram(map);// 调用柱状图数据加载方法
			}
		}
		if (null != list && list.size() > 0) {
			Map<String, Object> barModel = new HashMap<String, Object>();
			jsonData = this.toJSON(barModel, tudate); // 调用柱状图后台设置方法
		} else {
			jsonData = null;
		}
	}

	/**
	 * 柱状图数据加载
	 * @return double[][]
	 */
	private double[][] createHistogram(Map<String, Object> map) {
		double  display=Double.parseDouble(map.get("display").toString());
		double availability=Double.parseDouble(map.get("availability").toString());
		double quality=Double.parseDouble(map.get("quality").toString());
		int availabilityint=(int)(100 - (availability * 100));
		int playBilityint=(int)(100 - (display * (availability* 100))-availabilityint);
		int quaAvaPlayint=(int)(100 - (quality * (display * (availability * 100)))- availabilityint - playBilityint);
		// 实际生产 效率损失 质量损失 停机损失
		double[][] data = {
							  {100 - playBilityint - quaAvaPlayint - availabilityint,	100 - playBilityint - availabilityint,	100 - availabilityint,100},
							  {playBilityint,playBilityint,0,0 },
							  {quaAvaPlayint,0,0,0 },
							  {availabilityint,availabilityint,availabilityint,0} 
					       };
		return data;
	}
	
	/**
	 * 柱状图后台设置结合js
	 * @return String
	 */
	public String toJSON(Map<String, Object> chartModel, double[][] tudate) {
		JSONObject dataToJSON = new JSONObject();
		String[] columns = { "质量分析", "效率分析", "可用比率", "理论产量" };
		String[] rows = { "实际生产", "效率损失", "质量损失", "停机损失" };
		try {
			JSONArray columnKeys = new JSONArray(Arrays.asList(columns));
			dataToJSON.put("columnKeys", columnKeys);

			JSONArray rowKeys = new JSONArray(Arrays.asList(rows));
			dataToJSON.put("rowKeys", rowKeys);
			dataToJSON.put("title", "设备全局效率分析");

			for (int i = 0; i < tudate.length; i++) {
				for (int y = 0; y < tudate[i].length; y++) {
					System.out.print("tudate[][]==" + tudate[i][y] + "  ");
				}
				System.out.print("\n");
			}

			for (int i = 0; i < rowKeys.length(); i++) {
				double[] datas = tudate[i];
				JSONArray jsonArray = new JSONArray();
				for (int j = 0; j < datas.length; j++) {
					jsonArray.put(datas[j]);
				}
				dataToJSON.put("data" + i, jsonArray);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataToJSON.toString();
	}

	/**
	 * 构造方法
	 */
	@SuppressWarnings("unchecked")
	public StatDeviceOEEBean() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String equNo = StringUtils.getCookie(request, "equSerialNo");
		if (null != equNo && !"".equals(equNo)) {
			List<TEquipmentInfo> tequipment = deviceService.getAllEquName("'" + equNo+ "'");
			if (null != tequipment && tequipment.size() > 0) {
				String equName = tequipment.get(0).getEquName();
				nameList.add(new SelectItem(equNo, equName));
			}
			equSerialNo = equNo;
			equSerialNoPan=equNo;
		}
		// 默认时间值 :最近一个月
		if (null == start && null == end) {
			Date[] datea = StringUtils.convertDatea(1);
			start = datea[0];
			end = datea[1];
			System.out.println("给时间赋初值  start==" + start + "end==" + end);
		}
		selectBeanOEE();
	}

	
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public List<SelectItem> getNameList() {
		return nameList;
	}

	public void setNameList(List<SelectItem> nameList) {
		this.nameList = nameList;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public IDeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}
	
	public String getEquSerialNo() {
		return equSerialNo;
	}
}

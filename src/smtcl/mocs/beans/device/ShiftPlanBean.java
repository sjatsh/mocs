package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.common.device.pages.DataPage;
import smtcl.mocs.common.device.pages.PageListBaseBean;
import smtcl.mocs.common.device.pages.PageListDataModel;
import smtcl.mocs.services.device.IProductionService;
import smtcl.mocs.utils.device.FaceContextUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 查看组织节点排班计划
 * @作者：ZhaoHongshi
 * @创建时间：2012-12-03 下午4:25:00
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */

@SuppressWarnings("serial")
@ManagedBean(name = "ShiftPlanBean")
@ViewScoped
public class ShiftPlanBean extends PageListBaseBean implements Serializable {
	/**
	 * 生产状态接口实例
	 */
	@ManagedProperty(value = "#{productionService}")
	private IProductionService productionService;
	/**
	 * 节点ID
	 */
	private String nodeID;
	/**
	 * 结果数据集
	 */
	private IDataCollection<Map<String, Object>> result;
	/**
	 * 选择开始时间
	 */
	public Date startTime;
	/**
	 * 选择结束时间
	 */
	public Date endTime;
	/**
	 *生成图表
	 */
	private String chartData;
	/**
	 * 当前节点名称
	 */
	private String thisNodeName = "请选择节点";
	/**
	 * 当前节点
	 */
	private String nodeStr; 

	public String getNodeID() {
		TreeNode currentNode = (TreeNode) FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			this.nodeStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
		}
		getShiftPlanAction();
		return nodeID;
	}

	public PageListDataModel getDefaultDataModel() {
		if (defaultDataModel == null) {
			defaultDataModel = new PageListDataModel(pageSize) {
				public DataPage fetchPage(int startRow, int pageSize) {
					Collection<Parameter> parameters = new HashSet<Parameter>();
					if (null != startTime) {
						parameters.add(new Parameter("a.startworkTime", "startworkTime", startTime, Operator.GE));
					}
					if (null != endTime) {
						parameters.add(new Parameter("a.endworkTime", "endworkTime", endTime, Operator.LE));
					}
					if (null != nodeStr && !"".equals(nodeStr)) {
						nodeID = nodeStr;
					}
					parameters.add(new Parameter("a.TNodes.nodeId", "nodeId", nodeID, Operator.EQ));
					result = productionService.getShiftPlan(startRow / pageSize + 1, pageSize, parameters);
					return new DataPage(result.getTotalRows(), startRow, result.getData());
				}
			};
		}
		return defaultDataModel;
	}

	@SuppressWarnings("rawtypes")
	private String toJSON() {
		if (result != null) {
			Map<String, Object> data = new HashMap<String, Object>();
			System.out.println(result.getTotalRows() + "====");
			int size = result.getData().size();
			
			System.out.println("size" + size);
			long[] planQuantity = new long[size];
			long[] actualQuantity = new long[size];
			String[] userShiftName = new String[size];

			for (int i = 0; i < result.getData().size(); i++) {
				Map map = (Map) result.getData().get(i);
				planQuantity[i] = (Long) map.get("planQuantity");
				actualQuantity[i] = (Long) map.get("actualQuantity");
				userShiftName[i] = (String) map.get("userShiftName");
			}
			data.put("planQuantity", planQuantity);
			data.put("actualQuantity", actualQuantity);
			data.put("userShiftName", userShiftName);
			data.put("planQuantityName", "计划产量");
			data.put("actualQuantityName", "完成产量");
			data.put("title", "计划完成情况");
			data.put("xName", "班次名");
			data.put("yName", "产量");
			data.put("size", size);

			Gson gson = new GsonBuilder().serializeNulls().create();
			System.out.println(gson.toJson(data));
			return gson.toJson(data);
		}
		return null;
	}
	
	public String getChartData() {
		chartData = toJSON();
		return chartData;
	}
	
	@Override
	public PageListDataModel getExtendDataModel() {
		return null;
	}
	
	/**
	 * 查询按钮
	 */
	public void getShiftPlanAction() {
		this.defaultDataModel = null;
	}

	public ShiftPlanBean() {
		
	}
	
/*---------------------------------------------------------------------------------------*/
	public IProductionService getProductionService() {
		return productionService;
	}

	public String getNodeStr() {
		return nodeStr;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

	public void setProductionService(IProductionService productionService) {
		this.productionService = productionService;
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

	public String getThisNodeName() {
		return thisNodeName;
	}

	public void setThisNodeName(String thisNodeName) {
		this.thisNodeName = thisNodeName;
	}

	public void setChartData(String chartData) {
		this.chartData = chartData;
	}

}

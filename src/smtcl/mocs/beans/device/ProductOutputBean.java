package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.Collection;
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
 * 查看生产概况 
 * @作者：ZhaoHongshi
 * @创建时间：2012-11-13 上午9:39:00
 * @修改者：JiangFeng
 * @修改日期：
 * @修改说明：整合页面美工
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "ProductOutputBean")
@ViewScoped
public class ProductOutputBean extends PageListBaseBean implements Serializable {
	/**
	 *  生产状态接口实例
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
	 * 当前节点
	 */
	private String nodeStr; 
	/**
	 * 生成图表
	 */
	private String chartData;

	public String getNodeID() {
		// 构造方法获取公共信息
		TreeNode currentNode = (TreeNode) 
		FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			this.nodeStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
		}
		searchAction();
		nodeID = nodeStr;
		return nodeID;
	}

	/**
	 * 生产查询重写的分页方法
	 */
	@Override
	public PageListDataModel getDefaultDataModel() {
		if (defaultDataModel == null) {
			defaultDataModel = new PageListDataModel(pageSize) {
				public DataPage fetchPage(int startRow, int pageSize) {
					Collection<Parameter> parameters = new HashSet<Parameter>();
					if (null != nodeStr && !"".equals(nodeStr)) {
						nodeID = nodeStr;
					}
					parameters.add(new Parameter("a.TNodes.nodeId", "nodeId", nodeID, Operator.EQ));
					result = productionService.getProductOutput(startRow/ pageSize + 1, pageSize, parameters);
					chartData = toJSON();
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
			int size = result.getData().size();
			double[] dailyOutput = new double[size];
			double[] weeklyOutput = new double[size];
			double[] monthlyOutput = new double[size];
			double[] annualOutput = new double[size];
			String[] uprodName = new String[size];


			for (int i = 0; i < result.getData().size(); i++) {
				Map map = (Map) result.getData().get(i);
				dailyOutput[i] = (Double) map.get("dailyOutput");
				weeklyOutput[i] = (Double) map.get("weeklyOutput");
				monthlyOutput[i] = (Double) map.get("monthlyOutput");
				annualOutput[i] = (Double) map.get("annualOutput");
				uprodName[i] = (String) map.get("uprodName");
			}

			data.put("uprodName", uprodName);
			data.put("dailyOutput", dailyOutput);
			data.put("weeklyOutput", weeklyOutput);
			data.put("monthlyOutput", monthlyOutput);
			data.put("annualOutput", annualOutput);
			// data.put("title", "生产概况");
			data.put("size", size);

			Gson gson = new GsonBuilder().serializeNulls().create();
			return gson.toJson(data);
		}
		return null;
	}

	@Override
	public PageListDataModel getExtendDataModel() {
		return null;
	}
	
	/**
	 * 构造函数
	 * @类名：ProductOutputBean.java
	 */
	public ProductOutputBean() {
	}

	/***********************************************************************************/
	public IProductionService getProductionService() {
		return productionService;
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

	public String getNodeStr() {
		return nodeStr;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

	public void searchAction() {
		this.defaultDataModel = null;
	}

	public String getChartData() {
		return chartData;
	}

	public void setChartData(String chartData) {
		this.chartData = chartData;
	}

}

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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.common.device.pages.DataPage;
import smtcl.mocs.common.device.pages.PageListBaseBean;
import smtcl.mocs.common.device.pages.PageListDataModel;
import smtcl.mocs.services.device.IProductionService;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * 查看生产计划
 * @作者：ZhaoHongshi
 * @创建时间：2012-11-15 上午10:25:00
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "ProductPlanBean")
@ViewScoped
public class ProductPlanBean extends PageListBaseBean implements Serializable {
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
	 * 选取计划编号下拉框
	 */
	@SuppressWarnings("rawtypes")
	public List uplanNoList;
	/**
	 * 查看计划时间范围下拉框
	 */
	public List<SelectItem> planTimeList;
	/** 
	 * 查看计划计划类型下拉框
	 */
	public List<SelectItem> planTypeList;
	/**
	 * 生成图表
	 */
	private String chartData="ceshi";
	/**
	 * 当前节点名称
	 */
	private String thisNodeName = "请选择节点";
	/**
	 * 当前节点
	 */
	private String nodeStr; 
	/**
	 * 下拉框返回值
	 */
	public String uplanNo;
	public String planTime = "-1";
	public String planType = "-1";


	public String getNodeID() {
		TreeNode currentNode = (TreeNode) FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			this.nodeStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
		}
		getProductionPlanAction();
		return nodeID;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getUplanNoList() {
		if (null == uplanNoList)
			uplanNoList = new ArrayList();
		else
			uplanNoList.clear();
		Collection<Parameter> parameters = new HashSet<Parameter>();
		if (null != nodeStr && !"".equals(nodeStr)) {
			nodeID = nodeStr;
		}
		parameters.add(new Parameter("a.TNodes.nodeId", "nodeId", nodeID, Operator.EQ));
		uplanNoList.add(new SelectItem(null, "--全部--"));
		List plannolist = productionService.getAllPlanNo(parameters);
		
			for (int i = 0; i < plannolist.size(); i++) {
				uplanNoList.add(new SelectItem(plannolist.get(i)));
		}
		return uplanNoList;
	}

	public List<SelectItem> getPlanTimeList() {
		List<SelectItem> planTimeList = new ArrayList<SelectItem>();
		planTimeList.add(new SelectItem("-1", "--全部--"));
		planTimeList.add(new SelectItem("0", "当日"));
		planTimeList.add(new SelectItem("1", "本周"));
		planTimeList.add(new SelectItem("2", "本月"));
		planTimeList.add(new SelectItem("3", "本年度"));
		return planTimeList;
	}

	/**
	 * 生产计划重写的方法
	 */
	@Override
	public PageListDataModel getDefaultDataModel() {
		if (defaultDataModel == null) {
			defaultDataModel = new PageListDataModel(pageSize) {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public DataPage fetchPage(int startRow, int pageSize) {
					String searchId = uplanNo;
					String searchType = planType;
					
					if ("1".equals(planType))
						searchType = "月度计划";
					else if ("0".equals(planType))
						searchType = "年度计划";
					else if ("2".equals(planType))
						searchType = "周度计划";
					else if ("3".equals(planType))
						searchType = "订单计划";
					else
						searchType = null;

					Collection<Parameter> parameters = new HashSet<Parameter>();
					if (null != nodeStr && !"".equals(nodeStr)) {
						nodeID = nodeStr;
					}
					parameters.add(new Parameter("a.TNodes.nodeId", "nodeId", nodeID, Operator.EQ));
					if (!StringUtils.isEmpty(searchId)) {
						parameters.add(new Parameter("a.uplanNo", "uplanNo", searchId, Operator.EQ));
					}
					if (!StringUtils.isEmpty(searchType)) {
						parameters.add(new Parameter("a.uplanType", "uplanType", searchType, Operator.EQ));
					}
					if(planTime !=null){
						int planTimeValue = Integer.parseInt(planTime);
						Date[] para = StringUtils.convertDate(planTimeValue);
						if (!StringUtils.hasNoTime(para[0])) {
							parameters.add(new Parameter("a.uplanOnlineDate", "uplanOnlineDate", para[0], Operator.GE));
						}
						if (!StringUtils.hasNoTime(para[1])) {
							parameters.add(new Parameter("a.uplanFinishDate", "uplanFinishDate", para[1], Operator.LE));
						}
					}
					result = productionService.getProductPlan(startRow / pageSize + 1, pageSize, parameters);
					IDataCollection<Map<String, Object>> temp = result;
					for(int j=0;j<temp.getData().size();j++){
						Map map = (Map) temp.getData().get(j);
						if(map.get("uprodType") != null){
						map.put("uprodType", StringUtils.getSubString(map.get("uprodType").toString(),"1"));
						}
						if(map.get("uplanName") != null){
						map.put("uplanName", StringUtils.getSubString(map.get("uplanName").toString(),"1"));
						}
					}
					result = temp;
					return new DataPage(result.getTotalRows(), startRow, result.getData());
				}
			};
		}
		return defaultDataModel;
	}

	public List<SelectItem> getPlanTypeList() {
		if (null == planTypeList)
			planTypeList = new ArrayList<SelectItem>();
		else
			planTypeList.clear();
		planTypeList.add(new SelectItem("-1", "--全部--"));
		planTypeList.add(new SelectItem("0", "年度计划"));
		planTypeList.add(new SelectItem("1", "月度计划"));
		planTypeList.add(new SelectItem("2", "周度计划"));
		planTypeList.add(new SelectItem("3", "订单计划"));
		return planTypeList;
	}

	public String getChartData() {
		chartData=toJSON();
		return chartData;
	}

	@Override
	public PageListDataModel getExtendDataModel() {
		return null;
	}
	
	/**
	 * 转换格式
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String toJSON(){
		if(result!=null) {
		Map<String,Object> data = new HashMap<String,Object>();
		int size = result.getData().size();
		double[] uplanNum = new double[size];
		double[] uplanActualQuantity = new double[size];
		double[] uplanAnqualifiedQuantity = new double[size];
		long[] uplanGoodQuantity = new long[size];
		String[] uplanNo = new String[size];
		double maxY = 0;
		
		for (int i = 0; i < result.getData().size(); i++) {
				Map map = (Map) result.getData().get(i);
				uplanNum[i] = (Double)map.get("uplanNum");
				uplanActualQuantity[i] = (Double) map.get("uplanActualQuantity");
				uplanGoodQuantity[i] = (Long) map.get("uplanGoodQuantity");
				uplanNo[i] = (String) map.get("uplanNo");
				uplanAnqualifiedQuantity[i] = uplanActualQuantity[i]-uplanGoodQuantity[i];
				if(uplanNum[i] > maxY) {
					maxY = uplanNum[i];
				}
			}
			int max = (int)(maxY * 1.2); 
			data.put("maxY", max);
			data.put("uplanNum", uplanNum);
			data.put("uplanAnqualifiedQuantity", uplanAnqualifiedQuantity);
			data.put("uplanGoodQuantity", uplanGoodQuantity);
			data.put("uplanNo", uplanNo);
			data.put("title", "计划完成情况");
			data.put("size", size);
			data.put("xName", "计划编号/批次编号");
			data.put("yName", "产量");
			data.put("uplanNumName", "计划产量");
			data.put("uplanGoodQuantityName", "合格产量");
			data.put("uplanAnqualifiedQuantityName", "不合格产量");
			
			Gson gson = new GsonBuilder().serializeNulls().create(); 
		    return gson.toJson(data);
		}
		return null;
	}
	
	
/*------------------------------------------------------------------------------------*/
	public void setPlanTypeList(List<SelectItem> planTypeList) {
		this.planTypeList = planTypeList;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public void setPlanTimeList(List<SelectItem> planTimeList) {
		this.planTimeList = planTimeList;
	}

	public String getPlanTime() {
		return planTime;
	}

	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public IProductionService getProductionService() {
		return productionService;
	}

	public void setProductionService(IProductionService productionService) {
		this.productionService = productionService;
	}

	public IDataCollection<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(IDataCollection<Map<String, Object>> result) {
		this.result = result;
	}

	public void getProductionPlanAction() {
		this.defaultDataModel = null;
	}

	public String getNodeStr() {
		return nodeStr;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

	public String getUplanNo() {
		return uplanNo;
	}

	public void setUplanNo(String uplanNo) {
		this.uplanNo = uplanNo;
	}

	@SuppressWarnings("rawtypes")
	public void setUplanNoList(List uplanNoList) {
		this.uplanNoList = uplanNoList;
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

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
 * �鿴��֯�ڵ��Ű�ƻ�
 * @���ߣ�ZhaoHongshi
 * @����ʱ�䣺2012-12-03 ����4:25:00
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */

@SuppressWarnings("serial")
@ManagedBean(name = "ShiftPlanBean")
@ViewScoped
public class ShiftPlanBean extends PageListBaseBean implements Serializable {
	/**
	 * ����״̬�ӿ�ʵ��
	 */
	@ManagedProperty(value = "#{productionService}")
	private IProductionService productionService;
	/**
	 * �ڵ�ID
	 */
	private String nodeID;
	/**
	 * ������ݼ�
	 */
	private IDataCollection<Map<String, Object>> result;
	/**
	 * ѡ��ʼʱ��
	 */
	public Date startTime;
	/**
	 * ѡ�����ʱ��
	 */
	public Date endTime;
	/**
	 *����ͼ��
	 */
	private String chartData;
	/**
	 * ��ǰ�ڵ�����
	 */
	private String thisNodeName = "��ѡ��ڵ�";
	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr; 

	public String getNodeID() {
		TreeNode currentNode = (TreeNode) FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			this.nodeStr = currentNode.getNodeId(); // �������ڵ��ֵ������ǰ�ڵ�nodeStr
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
			data.put("planQuantityName", "�ƻ�����");
			data.put("actualQuantityName", "��ɲ���");
			data.put("title", "�ƻ�������");
			data.put("xName", "�����");
			data.put("yName", "����");
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
	 * ��ѯ��ť
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

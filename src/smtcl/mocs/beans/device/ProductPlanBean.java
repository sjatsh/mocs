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
 * �鿴�����ƻ�
 * @���ߣ�ZhaoHongshi
 * @����ʱ�䣺2012-11-15 ����10:25:00
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "ProductPlanBean")
@ViewScoped
public class ProductPlanBean extends PageListBaseBean implements Serializable {
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
	 * ѡȡ�ƻ����������
	 */
	@SuppressWarnings("rawtypes")
	public List uplanNoList;
	/**
	 * �鿴�ƻ�ʱ�䷶Χ������
	 */
	public List<SelectItem> planTimeList;
	/** 
	 * �鿴�ƻ��ƻ�����������
	 */
	public List<SelectItem> planTypeList;
	/**
	 * ����ͼ��
	 */
	private String chartData="ceshi";
	/**
	 * ��ǰ�ڵ�����
	 */
	private String thisNodeName = "��ѡ��ڵ�";
	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr; 
	/**
	 * �����򷵻�ֵ
	 */
	public String uplanNo;
	public String planTime = "-1";
	public String planType = "-1";


	public String getNodeID() {
		TreeNode currentNode = (TreeNode) FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			this.nodeStr = currentNode.getNodeId(); // �������ڵ��ֵ������ǰ�ڵ�nodeStr
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
		uplanNoList.add(new SelectItem(null, "--ȫ��--"));
		List plannolist = productionService.getAllPlanNo(parameters);
		
			for (int i = 0; i < plannolist.size(); i++) {
				uplanNoList.add(new SelectItem(plannolist.get(i)));
		}
		return uplanNoList;
	}

	public List<SelectItem> getPlanTimeList() {
		List<SelectItem> planTimeList = new ArrayList<SelectItem>();
		planTimeList.add(new SelectItem("-1", "--ȫ��--"));
		planTimeList.add(new SelectItem("0", "����"));
		planTimeList.add(new SelectItem("1", "����"));
		planTimeList.add(new SelectItem("2", "����"));
		planTimeList.add(new SelectItem("3", "�����"));
		return planTimeList;
	}

	/**
	 * �����ƻ���д�ķ���
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
						searchType = "�¶ȼƻ�";
					else if ("0".equals(planType))
						searchType = "��ȼƻ�";
					else if ("2".equals(planType))
						searchType = "�ܶȼƻ�";
					else if ("3".equals(planType))
						searchType = "�����ƻ�";
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
		planTypeList.add(new SelectItem("-1", "--ȫ��--"));
		planTypeList.add(new SelectItem("0", "��ȼƻ�"));
		planTypeList.add(new SelectItem("1", "�¶ȼƻ�"));
		planTypeList.add(new SelectItem("2", "�ܶȼƻ�"));
		planTypeList.add(new SelectItem("3", "�����ƻ�"));
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
	 * ת����ʽ
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
			data.put("title", "�ƻ�������");
			data.put("size", size);
			data.put("xName", "�ƻ����/���α��");
			data.put("yName", "����");
			data.put("uplanNumName", "�ƻ�����");
			data.put("uplanGoodQuantityName", "�ϸ����");
			data.put("uplanAnqualifiedQuantityName", "���ϸ����");
			
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

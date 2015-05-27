package smtcl.mocs.services.device;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.CuttertypeModel;
import smtcl.mocs.model.MaterialsModel;
import smtcl.mocs.model.PartModel;
import smtcl.mocs.model.TProcessEquipmentModel;
import smtcl.mocs.model.TProcessFixturetypeModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUserEquNcprogs;
import smtcl.mocs.pojos.device.TUserFixture;
import smtcl.mocs.pojos.job.TCuttertypeInfo;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;
import smtcl.mocs.pojos.job.TFixtureTypeInfo;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TPartClassInfo;
import smtcl.mocs.pojos.job.TPartProcessCost;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessEquipment;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProcessmaterialInfo;
import smtcl.mocs.pojos.job.TProcessplanInfo;
import smtcl.mocs.pojos.job.TQualityParam;

/**
 * 零件Service接口
 * @创建时间 2013-07-09
 * @作者 liguoqiang  
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
public interface IPartService extends IGenericService<TNodes, String>{
	/**
	 * 根据条件查询TPartTypeInfo对象如果条件为空，则查询全部对象
	 * @param query
	 * @return List<TPartTypeInfo>
	 * @throws Exception 
	 */
	public List<TPartTypeInfo> getTPartTypeInfo(String query,String status,String nodeid);
	public List<Map<String,Object>> getTPartTypeInfo(String nodeid,String partNo,Date startTime,Date endTime);
	/**
	 * 更新TPartTypeInfo
	 * @param tPartTypeInfo
	 */
	public String saveTPartTypeInfo(TPartTypeInfo tPartTypeInfo);
	
	/**
	 * 删除TPartTypeInfo
	 * @param tPartTypeInfo
	 */
	public void deleteTPartTypeInfo(TPartTypeInfo tPartTypeInfo);
	
	/**
	 * 添加 TPartTypeInfo
	 * @param tPartTypeInfo
	 */
	public String addTPartTypeInfo(TPartTypeInfo tPartTypeInfo);
	
	/**
	 * 停用，启用方法
	 * @param tPartTypeInfo
	 * @param status
	 */
	public void stopOrStartTPartTypeInfo(TPartTypeInfo tPartTypeInfo,String status);
	
	/**
	 * 从TPartClassInfo表中获取所有的typeNo
	 * @return
	 */
	public List<String> getTypeNo(String nodeid);
	
	/**
	 * 根据条件查询TPartClassInfo对象如果条件为空，则查询全部对象
	 * @param query
	 * @return List<TPartTypeInfo>
	 * @throws Exception 
	 */
	public List<TPartClassInfo> getTPartClassInfo(String query,String nodeid);
	
	/**
	 * 更新TPartClassInfo
	 * @param tPartTypeInfo
	 */
	public String saveTPartClassInfo(TPartClassInfo tPartClassInfo);
	
	/**
	 * 删除TPartClassInfo
	 * @param tPartTypeInfo
	 */
	public void deleteTPartClassInfo(TPartClassInfo tPartClassInfo);
	
	/**
	 * 添加 TPartClassInfo
	 * @param tPartTypeInfo
	 */
	public String addTPartClassInfo(TPartClassInfo tPartClassInfo);
	
	/**
	 * 根据零件编号查询零件信息
	 * @param tPartTypeInfo
	 */
	public List<TPartTypeInfo> getTPartTypeInfoByNo(String no);
	/**
	 * 根据零件类别编号查询零件类别
	 * @param typeNo
	 * @return
	 */
	public List<TPartTypeInfo> getTPartTypeInfoByTypeNo(String typeNo);
	/**
	 * 获取零件类型列表树
	 * @return
	 */
	public List<Map<String,Object>> getPartTree(String query,String nodeid);
	
	/**
	 * 获取工艺方案
	 * @param no
	 * @return
	 */
	public List<TProcessplanInfo> getTProcessplanInfo(String no,String parm);
	public List<Map<String,Object>> getTProcessplanInfo(String no);
	/**
	 * 获取工序
	 * @return
	 */
	public List<TProcessInfo> getTProcessInfo(String processPlanID);
	public List<Map<String,Object>> getTProcessInfoByGYFA(String processPlanID);
	/**
	 * 根据前序编号查找工序
	 * @param onlineProcessID
	 * @return
	 */
	public List<TProcessInfo> getTProcessInfoByOnlineProcessID(String onlineProcessID);
	/**
	 * 更新工序
	 * @param tProcessInfo
	 */
	public void saveTProcessInfo(TProcessInfo tProcessInfo);
	
	/**
	 * 新增工序
	 * @param tProcessInfo
	 */
	public void addTProcessInfo(TProcessInfo tProcessInfo);
	
	/**
	 * 删除工序
	 * @param tPartClassInfo
	 */
	public void deleteTProcessInfo(TProcessInfo tProcessInfo);
	
	/**
	 * 获取下拉框中的程序列表
	 * @return
	 */
	public List<Map<String,Object>> getSelectTUserEquNcprogs(String nodeid);
	
	/**
	 * 获取下拉框中工艺方案列表
	 * @return
	 */
	public List<Map<String,Object>> getSelectTProcessplanInfo();
	
	/**
	 * 获取下拉框中夹具信息列表
	 * @return
	 */
	public List<TFixtureTypeInfo> getSelectTFixturesInfo(String fixtureclassId,String nodeid);
	
	/**
	 *  获取下拉框中工作站信息
	 * @return
	 */
	public List<Map<String,Object>> getSelectTWorkstationInfo();
	
	/**
	 * 获取下拉框中机床类型列表
	 * @return
	 */
	public List<Map<String,Object>> getSelectTEquipmenttypeInfo();
	
	/**
	 * 返回成本数据
	 * @param pprs
	 * @return
	 */
	public List<Map<String,Object>> getCBData(List<TProcessInfo> pprs);
	public List<Map<String,Object>> getCBData(String processId);
	/**
	 * 返回物料数据
	 * @param pprs
	 * @return
	 */
	public List<Map<String,Object>> getWLData(List<TProcessInfo> pprs);
	
	/**
	 * 返回夹具数据
	 * @param processPlanID
	 * @return
	 */
	public List<Map<String,Object>> getJJData(String processPlanID);
	
	/**
	 * 返回刀具类型数据
	 * @param processPlanID
	 * @return
	 */
	public List<Map<String,Object>>  getDJData(String processPlanID);
	
	/**
	 * 返回机台数据
	 * @param processPlanID
	 * @return
	 */
	public List<Map<String,Object>> getJTData(String processPlanID);
	/**
	 * 根据工序id获取工序对象
	 * @param id
	 * @return
	 */
	public List<TProcessplanInfo> getTProcessplanInfoById(String id);
	
	/**
	 * 获取设备类型
	 * @return
	 */
	public List<TEquipmenttypeInfo> getTEquipmenttypeInfo();
	
	/**
	 * 根据工序id查询 物料信息
	 * @param no
	 * @return
	 */
	public List<TProcessmaterialInfo> getTProcessmaterialInfo(String id);
	
	/**
	 * 获取物料类型下拉框数据
	 * @return
	 */
	public List<Map<String,Object>> getSelectTMaterailTypeInfo(String nodeid);
	
	/**
	 * 根据物料编号获取物料
	 * @return
	 */
	public List<TMaterailTypeInfo> getTMaterailTypeInfo(String no);
	
	/**
	 * 获取刀具下拉框数据
	 * @return
	 */
	public List<Map<String,Object>> getSelectTCuttertypeInfo(String nodeid);
	
	/**
	 * 根据刀具编号获取刀具信息
	 * @param no
	 * @return
	 */
	public List<TCuttertypeInfo> getTCuttertypeInfo(String no);
	
	/**
	 * 获取设备类型
	 * @return
	 */
	public List<TEquipmenttypeInfo> getTEquipmenttypeInfoById(String id);
	
	/**
	 * 获取下拉框中的程序列表
	 * @return
	 */
	public List<TUserEquNcprogs> getSelectTUserEquNcprogsById(String id);
	
	/**
	 * 根据物料类型编号获取物料类型
	 * @param no
	 * @return
	 */
	public List<TMaterailTypeInfo> getSelectTMaterailTypeInfoByNo(String no);
	

	/**
	 * 工序配置向导保存
	 * @param part 向导辅助模型
	 * @param materialList 物料数据
	 * @param cuttertypeList 刀具数据
	 * @param partTypeno 零件编号
	 * @param tqplist 质检数据
	 * @param teti 设备数据
	 * @param tftmlist 夹具数据
	 */
	public String savePartProcessGuide(PartModel part,List<MaterialsModel> materialList,List<CuttertypeModel> cuttertypeList,String partTypeNo,
			List<TQualityParam> tqplist,List<TProcessEquipmentModel> teti,List<TProcessFixturetypeModel> tftmlist);
	
	
	public String savePartProcessGuideTest(PartModel part,List<MaterialsModel> materialList,List<CuttertypeModel> cuttertypeList,String partTypeNo,
			List<TQualityParam> tqplist,List<TProcessEquipmentModel> teti,List<TProcessFixturetypeModel> tftmlist);
	/**
	 * 工序配置向导更新
	 * @param part
	 * @param materialList
	 * @param cuttertypeList
	 */
	public String savePartProcessGuide(PartModel part,List<MaterialsModel> materialList,List<CuttertypeModel> cuttertypeList,
			String partTypeNo,TProcessInfo tProcessInfo,TPartProcessCost tPartProcessCost,List<MaterialsModel> deleteWL
			,List<CuttertypeModel> deleteDJ);
	/**
	 * 保存工艺方案
	 * @param plan
	 * @return
	 */
	public String saveTProcessplanInfo(TProcessplanInfo plan);
	
	/**
	 * 根据工序id返回工序对象
	 * @param Id
	 * @return
	 */
	public TProcessInfo getTProcessInfoById(String Id);
	
	/**
	 * 根据工序id查询刀具信息
	 * @param no
	 * @return
	 */
	public List<Map<String,Object>> getTProcessCuttertypeInfo(String processId,String nodeid);
	
	/**
	 * 根据工序id查询夹具信息
	 * @param id
	 * @return
	 */
	public List<TPartProcessCost> getTPartProcessCost(String id);
	
	/**
	 * 根据夹具编号查询夹具信息
	 * @param no
	 * @return
	 */
	public List<TFixtureTypeInfo> getTFixtureTypeInfoById(String Id);
	/**
	 * 更新工艺方案
	 * @param plan
	 * @return
	 */
	public String updateTProcessplanInfo(TProcessplanInfo plan);
	/**
	 * 工艺方案设为默认
	 * @param plan
	 * @return
	 */
	public String updateDeDefaultTProcessplanInfo(TProcessplanInfo plan);
	/**
	 * 查询设备节点
	 * @param search
	 * @return
	 */
	public TreeNode getTreeNodeEquInfo(String nodeid);
	/**
	 * 根据设备类型id 查找设备
	 * @param list
	 * @return
	 */
	public List<TEquipmentInfo> getTEquipmentInfo(List list);
	/**
	 * 根据设备类型id查找单个设备
	 * @param id
	 * @return 
	 */
	public List<TEquipmentInfo> getTEquipmentInfoByEquSerialNo(String id);
	/**
	 * 根据工序id查找工序设备表
	 * @return
	 */
	public List<TProcessEquipmentModel> getTProcessEquipmentByProcessId(String processId);
	 /**
	   * 根据工序id获取夹具数据
	   * @return
	   */
	  public List<TProcessFixturetypeModel> getTProcessFixturetypeModelByProcessId(String processid);
	  /**
	   * 根据工序获取质检数据
	   * @param processid
	   * @return
	   */
	  public List<TQualityParam> getTQualityParamByProcessId(String processid);
	  /**
	   * 根据工艺方案获取质检数据
	   * @param ProcessPlanId
	   * @return
	   */
	  public List<Map<String,Object>> getTQualityParamByProcessPlanId(String ProcessPlanId);
	  
	  /**
	   * 根据查询条件，模糊查询零件名称
	   */
	  public List<TPartTypeInfo> getAllPartType(String nodeid, String partName);
	  
	  /**
	   * 根据零件名称查询零件信息
	   * @return
	   */
	  public List<TPartTypeInfo> getPartTypeInfo(String partName);
	  /**
	   * 根据工序id 返回物料信息
	   * @param processId
	   * @return
	   */
	  public List<Map<String,Object>> getProcessWL(String processId);
	  /**
	   * 获取工序机台
	   * @param processId
	   * @return
	   */
	  public List<Map<String,Object>> getProcessJTData(String processId);
	  /**
		 * 根据零件编号获取 批次计划
		 * @param partId
		 * @return
		 */
		public List<Map<String,Object>> getJopPlanByPartId(String partId);
}

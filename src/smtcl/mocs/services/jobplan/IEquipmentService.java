package smtcl.mocs.services.jobplan;

import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;

import smtcl.mocs.beans.equipment.EquipmentAccountingBean;
import smtcl.mocs.beans.equipment.EquipmentCostBean;
import smtcl.mocs.beans.equipment.EquipmentTypeBean;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TUserEquCurStatus;
import smtcl.mocs.pojos.job.TEquipmentCostInfo;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;

/**
 * 设备管理SERVICE接口
 * @作者：yyh
 * @创建时间：2013-08-06
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
public interface IEquipmentService extends IGenericService<TEquipmentCostInfo, String> {
	
	/**
	 * 设备类型维护--设备类型信息列表
	 */
	public List<Map<String, Object>> getEquipmentInfoAList(String equipmentType);
	
	/**
	 * 设备类型维护--左侧树的设备类别
	 */
	public List<Map<String, Object>> getEquType();
	
	/**
	 * 设备类型维护--左侧树的根据设备类别查找到设备
	 */
	public List<Map<String, Object>> getEquByTypeId(String equipmentType);
	
	/**
	 * 设备类型维护--通过类型ID得到类型
	 */
	public TEquipmenttypeInfo getgetEquTypeIdById(String typeId);
	
	/**
	 * 设备类型维护--通过类型名称得到类型ID
	 */
	public String getEquTypeIdByName(String name);
	
	/**
	 * 设备类型维护--左侧树点击得到右侧列表
	 */
	public List<Map<String, Object>> getEquTypeByClick(String pEqutypeId);
	
	/**
	 * 设备类型维护--右侧列表字段副设备类型名称
	 */
	public List<Map<String, Object>> getParentNameById(String Id);
	
	/**
	 * 设备类型维护--添加时的下拉
	 */
	public List<Map<String, Object>> getParentIdMap();
	
	/**
	 * 设备类型维护--保存
	 */
	public void addEquType(EquipmentTypeBean equipmentTypeBean);
	
	/**
	 * 设备类型维护--查询单个通过设备ID，查看是否重复
	 */
	public List<Map<String, Object>> getEquTypeRepeat(String typecode);
	
	/**
	 * 设备类型维护--删除成本
	 */
	public void delEquType(EquipmentTypeBean equipmentTypeBean);
	
	/**
	 * 设备类型维护--删除是判断是否已经使用，即在机床关联了
	 */
	public List<Map<String, Object>> getEquTypeInEquSize(String equTypeId);
	
	/**
	 * 设备类型维护--修改成本
	 */
	public void updateEquType(EquipmentTypeBean equipmentTypeBean);		
	
	/**
	 * 设备台帐维护--设备信息列表
	 */
	public List<Map<String, Object>> getMachineList(String equipmentType);
	
	/**
	 * 设备台帐维护--左侧树的设备类别
	 */
	public List<Map<String, Object>> getEquTypeInAcc();
	
	/**
	 * 设备台帐维护--左侧树的根据设备类别查找子设备类别
	 */
	public List<Map<String, Object>> getEquByTypeIdInAcc(String equipmentType);
	
	/**
	 * 设备台帐维护--左侧树的根据设备类别和子设备类别查找设备
	 */
	public List<Map<String, Object>> getEquByTypeInAcc(String typeId);
	
	/**
	 * 设备台帐维护--保存
	 */
	public void addEqu(String nodeid,EquipmentAccountingBean equipmentAccountingBean);
	
	/**
	 * 设备台帐维护--查询单个通过设备equSerialNo，查看是否重复
	 */
	public List<Map<String, Object>> getEquRepeat(String equSerialNo);
	
	/**
	 * 设备台帐维护--删除
	 */
	public void delEqu(String nodeid,EquipmentAccountingBean equipmentAccountingBean);
	
	/**
	 * 设备台帐维护--通过设备ID得到设备
	 */
	public  TEquipmentInfo getTEquipmentInfoById(String equId);
	
	/**
	 * 设备台帐维护--通过操作人ID找到操作人名称
	 */
	public String getMenberNameById(String id);
	
	/**
	 * 设备台帐维护--修改
	 */
	public void updateEqu(EquipmentAccountingBean equipmentAccountingBean);	
	
	/**
	 * 设备台帐维护--修改图片
	 */
	public void updateEquImage(String equId,List<String> listdocStorePath);
	
	/**
	 * 设备成本维护--列表
	 */
	public List<Map<String, Object>> getEquipmentCostList(String roomId,String equTypeId,String equId,String code);
	
	/**
	 * 设备成本维护--得到车间集合
	 */
	public List<Map<String, Object>> getRoomMap();
	
	/**
	 * 设备成本维护--得到设备类型集合
	 */
	public List<Map<String, Object>> getEquTypeMap();
	
	/**
	 * 设备成本维护--得到设备集合
	 */
	public List<Map<String, Object>> getEquMap();
	
	/**
	 * 设备成本维护--得到设备编码集合
	 */
	public List<Map<String, Object>> getEquCodeMap();
	
	/**
	 * 设备成本维护--查询单个
	 */
	public List<Map<String, Object>> getEquCost();
	
	/**
	 * 设备成本维护--保存
	 */
	public void addEquCost(EquipmentCostBean equipmentCostBean);	
	
	/**
	 * 设备成本维护--查询单个通过编号，查看是否重复
	 */
	public List<Map<String, Object>> getEquCostRepeat(String equSerialNo);
	
	/**
	 * 设备成本维护--删除成本
	 */
	public void delEquCost(EquipmentCostBean equipmentCostBean);
	
	/**
	 * 设备成本维护--修改成本
	 */
	public void updateEquCost(EquipmentCostBean equipmentCostBean);
	
	public TUserEquCurStatus getTUserEquCurStatus(String equSerialNo);
	/**
	 * 获取设备位置数据
	 * @return
	 */
	public String getEquData(String nodeIds,int type);
	/**
	 * 保存机床位置数据
	 * @param equData
	 * @return
	 */
	public String saveEquData(String equData);

}

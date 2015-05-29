package smtcl.mocs.services.storage;

import java.util.List;
import java.util.Map;

import smtcl.mocs.beans.storage.SetUnitBean;
import smtcl.mocs.beans.storage.SetUnitConversionBean;
import smtcl.mocs.beans.storage.SetUnitInfoBean;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.storage.TUnitType;

/**
 *计量单位设置接口 
 * 
 * @author dingxuan
 *
 */

public interface ISetUnitServcie {

	/***
	 * 计量单位分类的基本信息
	 * 
	 * @return   List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getUnitTypeInfo(String nodeid);
	
	/***
	 * 
	 * 新增计量单位分类的基本信息
	 */
	
	public String addTUnitType(SetUnitBean sunitbean);
	
	/**
	 * 修改计量单位分类的基本信息
	 */
	
	public String updateTUnitType(Map<String, Object> tuntype);
	
	/**
	 * 	删除单位分类
	 */
	public void deleteTUnitType(Map tuntype);
	
	
	/***
	 * 计量单位设置的基本信息
	 * 
	 * @return  List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getUnitInfo(String nodeid);
	
	/**
	 * 计量单位设置的单位分类名称的下拉 列表
	 * 
	 * @return  List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getunitTypeName(String nodeid);

	/**
	 * 通过单位分类获得单位名称
	 */
	public List<Map<String, Object>> getunitName(String unittypename,String nodeid);
	
	
	
	
	/**
	 * 新增单位设置的基本信息
	 * 
	 */
	public String addTUnitInfo(SetUnitInfoBean sunitbean);
	
	/**
	 * 修改单位设置的基本信息
	 */
	
	public String updateTUnitInfo(Map<String, Object> tunInfo);
	
	/**
	 * 	删除单位设置单位信息
	 */
	public void deleteTUnitInfo(Map tunInfo);
	
	
	/**
	 * 计量单位换算标准的基本信息
	 * 
	 * @return  List<Map<String, Object>>
	 */
	public  List<Map<String, Object>> getunitConversionInfo(String nodeid);
	
	/**
	 * 计量单位的下拉列表
	 * 
	 * 
	 * @return  List<Map<String, Object>>
	 */
	public  List<Map<String, Object>> getConversionUnit(String unittypename,String nodeid);
	
	/**
	 * 新增换算单位标准的基本信息
	 * 
	 */
	public String addTUnitConversion(SetUnitConversionBean suncbean);
	
	/**
	 * 修改换算单位标准的基本信息
	 */
	
	public String updateTUnitConversion(Map<String, Object> tuncon);
	
	
	/**
	 * 	删除换算单位标准的信息
	 */
	public void deleteTUnitConversion(Map tuncon);
	
	
	/**
	 * 获取换算单位分类内的信息
	 * 
	 */
    public  List<Map<String, Object>> getunitConclassifyInfo(String nodeid);
	
   
    
	/**
	 * 查询物料编号的信息
	 */
    public List<Map<String,Object>> getMaterNo(String nodeid,String no);
    
    /**
     * 通过物料编号查询物料说明的信息
     * 
     */
    public List<Map<String,Object>> getMaterName(String nodeid,String no);
    
    /**
	 * 新增单位换算分类内的基本信息
	 * 
	 */
	public String addTunitConclassify(SetUnitConversionBean suncbean);
	
	/**
	 * 修改单位换算分类内的基本信息
	 */
	
	public String updateTunitConclassify(Map<String, Object> tuncon);
	
	/**
	 * 	删除单位换算分类内的信息
	 */
	public void deleteTunitConclassify(Map tuncon);
	
	 /**
	  * 获取换算单位分类间的信息
	  * 
	  */
	 public  List<Map<String, Object>> getunitConsortInfo(String nodeid);
	    
	 
	 /**
	  * 新增分类间的信息
	  */
	 public String addUnitConsortInfo(SetUnitConversionBean suncbean);
	 
	 /**
	   * 修改换算单位分类间的信息
	   */
		
    public String updateUnitConsortInfo(Map<String, Object> tuncon);
    
    
    /**
	 * 	删除单位换算分类间的信息
	 */
	public void deleteUnitConsortInfo(Map tuncon);
}

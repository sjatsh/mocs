package smtcl.mocs.services.storage;

import java.util.List;
import java.util.Map;

import smtcl.mocs.beans.storage.SetUnitBean;
import smtcl.mocs.beans.storage.SetUnitConversionBean;
import smtcl.mocs.beans.storage.SetUnitInfoBean;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.storage.TUnitType;

/**
 *������λ���ýӿ� 
 * 
 * @author dingxuan
 *
 */

public interface ISetUnitServcie {

	/***
	 * ������λ����Ļ�����Ϣ
	 * 
	 * @return   List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getUnitTypeInfo(String nodeid);
	
	/***
	 * 
	 * ����������λ����Ļ�����Ϣ
	 */
	
	public String addTUnitType(SetUnitBean sunitbean);
	
	/**
	 * �޸ļ�����λ����Ļ�����Ϣ
	 */
	
	public String updateTUnitType(Map<String, Object> tuntype);
	
	/**
	 * 	ɾ����λ����
	 */
	public void deleteTUnitType(Map tuntype);
	
	
	/***
	 * ������λ���õĻ�����Ϣ
	 * 
	 * @return  List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getUnitInfo(String nodeid);
	
	/**
	 * ������λ���õĵ�λ�������Ƶ����� �б�
	 * 
	 * @return  List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getunitTypeName(String nodeid);

	/**
	 * ͨ����λ�����õ�λ����
	 */
	public List<Map<String, Object>> getunitName(String unittypename,String nodeid);
	
	
	
	
	/**
	 * ������λ���õĻ�����Ϣ
	 * 
	 */
	public String addTUnitInfo(SetUnitInfoBean sunitbean);
	
	/**
	 * �޸ĵ�λ���õĻ�����Ϣ
	 */
	
	public String updateTUnitInfo(Map<String, Object> tunInfo);
	
	/**
	 * 	ɾ����λ���õ�λ��Ϣ
	 */
	public void deleteTUnitInfo(Map tunInfo);
	
	
	/**
	 * ������λ�����׼�Ļ�����Ϣ
	 * 
	 * @return  List<Map<String, Object>>
	 */
	public  List<Map<String, Object>> getunitConversionInfo(String nodeid);
	
	/**
	 * ������λ�������б�
	 * 
	 * 
	 * @return  List<Map<String, Object>>
	 */
	public  List<Map<String, Object>> getConversionUnit(String unittypename,String nodeid);
	
	/**
	 * �������㵥λ��׼�Ļ�����Ϣ
	 * 
	 */
	public String addTUnitConversion(SetUnitConversionBean suncbean);
	
	/**
	 * �޸Ļ��㵥λ��׼�Ļ�����Ϣ
	 */
	
	public String updateTUnitConversion(Map<String, Object> tuncon);
	
	
	/**
	 * 	ɾ�����㵥λ��׼����Ϣ
	 */
	public void deleteTUnitConversion(Map tuncon);
	
	
	/**
	 * ��ȡ���㵥λ�����ڵ���Ϣ
	 * 
	 */
    public  List<Map<String, Object>> getunitConclassifyInfo(String nodeid);
	
   
    
	/**
	 * ��ѯ���ϱ�ŵ���Ϣ
	 */
    public List<Map<String,Object>> getMaterNo(String nodeid,String no);
    
    /**
     * ͨ�����ϱ�Ų�ѯ����˵������Ϣ
     * 
     */
    public List<Map<String,Object>> getMaterName(String nodeid,String no);
    
    /**
	 * ������λ��������ڵĻ�����Ϣ
	 * 
	 */
	public String addTunitConclassify(SetUnitConversionBean suncbean);
	
	/**
	 * �޸ĵ�λ��������ڵĻ�����Ϣ
	 */
	
	public String updateTunitConclassify(Map<String, Object> tuncon);
	
	/**
	 * 	ɾ����λ��������ڵ���Ϣ
	 */
	public void deleteTunitConclassify(Map tuncon);
	
	 /**
	  * ��ȡ���㵥λ��������Ϣ
	  * 
	  */
	 public  List<Map<String, Object>> getunitConsortInfo(String nodeid);
	    
	 
	 /**
	  * ������������Ϣ
	  */
	 public String addUnitConsortInfo(SetUnitConversionBean suncbean);
	 
	 /**
	   * �޸Ļ��㵥λ��������Ϣ
	   */
		
    public String updateUnitConsortInfo(Map<String, Object> tuncon);
    
    
    /**
	 * 	ɾ����λ�����������Ϣ
	 */
	public void deleteUnitConsortInfo(Map tuncon);
}

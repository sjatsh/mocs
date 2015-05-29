package smtcl.mocs.services.storage.impl;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Parameter;

import smtcl.mocs.utils.device.StringUtils;
import smtcl.mocs.beans.storage.SetUnitBean;
import smtcl.mocs.beans.storage.SetUnitConversionBean;
import smtcl.mocs.beans.storage.SetUnitInfoBean;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProgramInfo;
import smtcl.mocs.pojos.storage.RStoragePosition;
import smtcl.mocs.pojos.storage.TUnitConversion;
import smtcl.mocs.pojos.storage.TUnitInfo;
import smtcl.mocs.pojos.storage.TUnitType;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.storage.ISetUnitServcie;

public class SetUnitServiceImpl extends GenericServiceSpringImpl<Object, String> implements
		ISetUnitServcie {
     private ICommonService commonService;
	
	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	/**
	 * ��ȡ������λ����Ļ�����Ϣ
	 */
	@Override
	public List<Map<String, Object>> getUnitTypeInfo(String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_typeName as \"unittypename\"," //��λ��������
				+ "  unittype.ID  as \"Id\", "             //��λ����Id
				+ "  unittype.nodeId as \"nodeId\" , "     //�ڵ�Id
				+ "  unittype.unit_typeDesc as \"unittypedesc\", "//��λ����˵��
				+ "  unittype.unit_name as \"unit\","     //��׼��λ����
				+ "  unittype.unit_short as \"unitshort\","// ��׼��λ��д
				+ "  unittype.invalid_date as \"invadate\" "//ʧЧ����
				+ "  from t_unit_type unittype "
				+ "  where unittype.nodeId='"+nodeid+"' ";
		return dao.executeNativeQuery(sql);
	}
    
	/**
	 * ����������λ����Ļ�����Ϣ
	 */
	@Override
	public String addTUnitType(SetUnitBean sunitbean) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_typeName as \"unittypename\","//��λ��������
				+ "  unittype.unit_name as \"unit\" "//��׼��λ����
				+ "  from t_unit_type unittype "
				+ "  where(unittype.unit_typeName = '" + sunitbean.getUnittypename() + "' "
				+ "  or unittype.unit_name='"+sunitbean.getUnit()+"' ) and unittype.nodeId='"+sunitbean.getNodeid()+"' ";
		List<Map<String,Object>> typeList = dao.executeNativeQuery(sql);
		if(null!=typeList&&typeList.size()>0){
			return "�Ѵ���";
		}else{
			try {
				TUnitType untype=new TUnitType();
				untype.setUnitTypeName(sunitbean.getUnittypename());//���õ�λ��������
				untype.setUnitTypeDesc(sunitbean.getUnittypedesc());//���õ�λ��������
				untype.setUnitName(sunitbean.getUnit());//���ñ�׼��λ����
				untype.setUnitShort(sunitbean.getUnitshort());//���ñ�׼��λ����
				untype.setInvalidDate(sunitbean.getInvadate());//���ñ�׼��λʧЧ����
				Date dt=new Date();
			    untype.setCreateDate(dt);//��׼��λ��������
			    untype.setNodeId(sunitbean.getNodeid());//��õ�ǰ�Ľڵ�Id
				String sql1=" select unitInfo.unit_name as \"unitname\" "
						  + " from t_unit_info unitInfo "
						  + " where unitInfo.unit_name='"+sunitbean.getUnit()+"' "
						  + " and unitInfo.nodeId='"+sunitbean.getNodeid()+"' ";
				
				List<Map<String,Object>> unInfo=dao.executeNativeQuery(sql1);
				if(null!=unInfo&&unInfo.size()>0){
					return "��λ�����Ѵ���";
				}else{
			    TUnitInfo unitInfo=new TUnitInfo();
				unitInfo.setUnitName(sunitbean.getUnit());//��λ����
				unitInfo.setUnitShort(sunitbean.getUnitshort());//��λ������д
				unitInfo.setUnitClass("1");//��λ���(1Ϊ��׼��2Ϊ�Ǳ�׼)
				unitInfo.setUnitStatus("1");//��λ״̬(1Ϊ���ã�2Ϊδ����)
				unitInfo.setNodeId(sunitbean.getNodeid());
				unitInfo.setCreateDate(dt);//��λ���ñ��еĵ�λ����
				unitInfo.setTUnitType(untype);
				commonService.save(unitInfo);
				}
				commonService.save(untype);
				return "��ӳɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "���ʧ��";
			}
			
		}
	}
	
	/**
	 * �޸ļ�����λ�ķ�����Ϣ
	 */
	
	@Override
	public String updateTUnitType(Map<String, Object> tuntype) {
		// TODO Auto-generated method stub
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql1 = "from TUnitType where unitTypeName='" + tuntype.get("unittypename") + "'  and  id<>'"+tuntype.get("Id")+"'";
		List<TUnitType> tprlist = dao.executeQuery(hql1, parameters);
			try {
				if(null == tprlist || tprlist.size() < 1){
				String hql="from TUnitType where id="+tuntype.get("Id");//��ȡѡ���е�Id
				List<TUnitType> rslist2 =dao.executeQuery(hql);
				TUnitType type=rslist2.get(0);
				type.setUnitTypeName(tuntype.get("unittypename").toString());//��λ��������
				type.setUnitTypeDesc(tuntype.get("unittypedesc").toString());//��λ��������
				type.setUnitName(tuntype.get("unit").toString());//��׼��λ����
				type.setUnitShort(tuntype.get("unitshort").toString());//��׼��λ��д
				Date date=null;
				date=StringUtils.convertDate(tuntype.get("invadate").toString(), "yyyy-MM-dd HH:mm:ss");//��׼��λʧЧ����
				type.setInvalidDate(date);
				Date dt=new Date();
			    type.setCreateDate(dt);//��׼��λ��������
				return "�޸ĳɹ�";
				}
				else
				{
					return "�Ѵ���";
				}	
			} catch (Exception e) {
				e.printStackTrace();
				return "�޸�ʧ��";
			}
			
		}
	
	
	/**
	 * ɾ��������λ��Ϣ
	 */
	@Override
	public void deleteTUnitType(Map tuntype) {
		// TODO Auto-generated method stub
		String sql = " select unittype.ID  as \"Id\" from t_unit_type unittype  where unittype.ID = '" + tuntype.get("Id") + "'";
		TUnitType type=new TUnitType();
		TUnitInfo tun=new TUnitInfo();
		TUnitConversion conver=new TUnitConversion();
		 try {
			if(null != tuntype.get("unittypename") && ""!= tuntype.get("unittypename")){
				String sql1="select unitInfo.ID as \"Id\" from t_unit_info unitInfo where unitInfo.unit_typeID='"+tuntype.get("Id")+"'";
				 List<Map<String,Object>> rslist1=dao.executeNativeQuery(sql1);
				  String sql2="select unitconversion.ID as \"Id\" from t_unit_conversion unitconversion where unitconversion.con_unitID in("
							+ "select unitInfo.ID as \"Id\" from t_unit_info unitInfo where unitInfo.unit_typeID='"+tuntype.get("Id")+"')";
					List<Map<String,Object>> rslist2=dao.executeNativeQuery(sql2);
					 String id1=null;
					 String id2=null;
					 String id3=null;
				   for(Map<String,Object> tt:rslist2){
					        id1= tt.get("Id").toString();
							conver = dao.get(TUnitConversion.class,new Integer(id1));
							dao.delete(conver);
						}
						for(Map<String,Object> tt:rslist1){
							id2=tt.get("Id").toString();
							tun = dao.get(TUnitInfo.class,new Integer(id2));
							dao.delete(tun);
						}
						List<Map<String,Object>>rslist = dao.executeNativeQuery(sql);
						for(Map<String,Object> tt:rslist){
							id3=tt.get("Id").toString();
							type = dao.get(TUnitType.class,new Integer(id3));
							dao.delete(type);
						}
						
					}
				
				} catch (Exception e) {
			
			      e.printStackTrace();
		        }		
	}
	
	/**
	 * ��ȡ��λ���õĻ�����Ϣ
	 */

	@Override
	public List<Map<String, Object>> getUnitInfo(String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_typeName as\"unittypename\","//��λ��������
				+ "  unitInfo.ID as \"Id\","  //Id
				+ "  unitInfo.unit_name as \"unitname\"," //��λ����
				+ "  unitInfo.unit_short as\"unitshort\","//��λ��д
				+ "  unitInfo.unit_desc as \"unitdesc\", "//��λ����
		        + "  (case  unitInfo.unit_status when  '1' then '����' when '2' then 'δ����' else '0' end ) as \"unitstatus\" "//��λ״̬��1Ϊ���ã�2Ϊδ����
				+ "  from t_unit_info unitInfo ,t_unit_type unittype "
				+ "  where unitInfo.unit_typeID=unittype.ID "
				+ "  and unitInfo.nodeId='"+nodeid+"' ";
		
		return dao.executeNativeQuery(sql);
	}
    /**
     * ��ȡ��λ�������Ƶ����� �б�
     */
	@Override
	public List<Map<String, Object>> getunitTypeName(String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_typeName as \"unittypename\","//��λ��������
				 + " unittype.ID  as\"Id\" "//��λ����ID
				 + " from t_unit_type unittype "
				 + " where unittype.nodeId='"+nodeid+"'";
		return dao.executeNativeQuery(sql);
	}
	
	/**
	 * ͨ����λ�����õ�λ����
	 */
	@Override
	public List<Map<String, Object>> getunitName(String unittypename,String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_name as \"unit\" "//��λ����
				 + " from t_unit_type unittype "
				 + " where unittype.unit_typeName='"+unittypename+"' "//��λ��������
				 + " and unittype.nodeId='"+nodeid+"' ";
		return dao.executeNativeQuery(sql);
	}

	
	
	
    /**
     * ������λ���õ���Ϣ
     */

	@Override
	public String addTUnitInfo(SetUnitInfoBean sunitbean) {
		// TODO Auto-generated method stub
		String sql=" select unitInfo.unit_name as \"unitname\", "//��λ����
				+ "  unitInfo.unit_short as \"unitshort\" "//��λ��д
				+ "  from t_unit_info unitInfo "
				+ "  where (unitInfo.unit_name = '" + sunitbean.getUnitName() + "' "
				+ "  or unitInfo.unit_short='"+sunitbean.getUnitShort()+"' )  and unitInfo.nodeId='"+sunitbean.getNodeid()+"'";
		List<Map<String,Object>> tunlist = dao.executeNativeQuery(sql);
		if(null!=tunlist&&tunlist.size()>0){
			return "�Ѵ���";
		}else{
			try {
				TUnitInfo tun=new TUnitInfo();
				TUnitType tutype=null;
				String id=null;
				String unittypename=sunitbean.getUnittypename();//��ȡ��λ��������
				List<Map<String,Object>> unitIdList=dao.executeNativeQuery("select unittype.ID as \"Id\"  from t_unit_type unittype  where unittype.unit_typeName='"+unittypename+"' ");
				for(Map<String,Object> map:unitIdList){
					id=map.get("Id").toString();
				}
				if(!StringUtils.isEmpty(id)){
					tutype=dao.get(TUnitType.class, new Integer(id));
					
				}//ͨ����λ�������ƻ���������Id
				tun.setTUnitType(tutype);
				tun.setUnitName(sunitbean.getUnitName());//��λ����
				tun.setUnitShort(sunitbean.getUnitShort());//��λ��д
				tun.setUnitDesc(sunitbean.getUnitDesc());//��λ����
				Date dt=new Date();
			    tun.setCreateDate(dt);//��λ��������
				tun.setUnitClass("2");//��λ��Ϊ��׼�ͷǱ�׼
				tun.setUnitStatus("1");//��λ״̬��1Ϊ���ã�2Ϊδ����
				tun.setNodeId(sunitbean.getNodeid());
				commonService.save(tun);
				return "��ӳɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "���ʧ��";
			}
				
		}
		
	}
    
	/**
	 * �޸ĵ�λ������Ϣ
	 */
	
	@Override
	public String updateTUnitInfo(Map<String, Object> tunInfo) {
		// TODO Auto-generated method stub
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql1 = "from TUnitInfo where unitName='" + tunInfo.get("unitname") + "'  and  id<>'"+tunInfo.get("Id")+"'";
		List<TUnitInfo> tprlist = dao.executeQuery(hql1, parameters);
			try {
				if(null == tprlist || tprlist.size() < 1){
			    String hql="from TUnitInfo  where id="+tunInfo.get("Id");//��ȡѡ���е�Id
				List<TUnitInfo> rslist2 =dao.executeQuery(hql);
				TUnitInfo  tuninfo=rslist2.get(0);
				tuninfo.setUnitName(tunInfo.get("unitname").toString());//��λ����
				tuninfo.setUnitShort(tunInfo.get("unitshort").toString());//��λ��д
				tuninfo.setUnitDesc(tunInfo.get("unitdesc").toString());//��λ����
				Date dt=new Date();
			    tuninfo.setCreateDate(dt);//��������
			    String str=tunInfo.get("unitstatus").toString();//��λ״̬
			    if(str.equals("����")){
			    	tuninfo.setUnitStatus("1");
			    }
			    if(str.equals("δ����")){
			    	tuninfo.setUnitStatus("2");
			       }
			    return "�޸ĳɹ�";
				}
				else
				{
					return "�Ѵ���";
				}	
			} catch (Exception e) {
				e.printStackTrace();
				return "�޸�ʧ��";
			}
			
		}
	
	
	/**
	 * ɾ����λ���õ���Ϣ
	 */
	
	@Override
	public void deleteTUnitInfo(Map tunInfo) {
		// TODO Auto-generated method stub
		TUnitInfo tun=new TUnitInfo();
		TUnitConversion conver=new TUnitConversion();
		String sql1="select unitInfo.ID as \"Id\" from t_unit_info unitInfo where unitInfo.ID='"+tunInfo.get("Id")+"'";
		try {
			if(null != tunInfo.get("unitname") && ""!= tunInfo.get("unitname")){
				
				 
				  String sql2="select unitconversion.ID as \"Id\" from t_unit_conversion unitconversion where unitconversion.con_unitID in("
							+ "select unitInfo.ID as \"Id\" from t_unit_info unitInfo where unitInfo.ID='"+tunInfo.get("Id")+"')";
					List<Map<String,Object>> rslist2=dao.executeNativeQuery(sql2);
					 String id1=null;
					 String id2=null;
					 
				   for(Map<String,Object> tt:rslist2){
					        id1= tt.get("Id").toString();
							conver = dao.get(TUnitConversion.class,new Integer(id1));
							dao.delete(conver);
						}
				   List<Map<String,Object>> rslist1=dao.executeNativeQuery(sql1);
						for(Map<String,Object> tt:rslist1){
							id2=tt.get("Id").toString();
							tun = dao.get(TUnitInfo.class,new Integer(id2));
							dao.delete(tun);
						}
						
						
					}
				
				} catch (Exception e) {
			
			      e.printStackTrace();
		        }		
	}

	

	
	/**
	 * ��ȡ������λ�����׼�Ļ�����Ϣ
	 */
	@Override
	public List<Map<String, Object>> getunitConversionInfo(String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_typeName as\"unittypename\","//��λ��������
				+ "  unittype.unit_name as \"unit\","//��׼��λ����
				/*+ "  unitInfo.unit_name as \"unitname\","*///��λ����
				+ "  unitconversion.ID as \"Id\" ,"//������Id
				+ "  unitconversion.ratio as \"ratio\" ,"//��λ������
				+ "  unitconversion.con_unitID as \"conunitId\" "
				+ "  from t_unit_info unitInfo ,t_unit_type unittype ,"
				+ "  t_unit_conversion   unitconversion "
				+ "  where unitInfo.unit_typeID=unittype.ID "
				+ "  and unitconversion.con_unitID=unitInfo.ID "
				+ "  and unitconversion.conversion_type='1'"
				+ "  and unitconversion.nodeId='"+nodeid+"' ";
				
		return dao.executeNativeQuery(sql);
	}

	/**
	 * ��ȡ������λ�������б�
	 */
	@Override
	public List<Map<String, Object>> getConversionUnit(String unittypename,String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unitInfo.unit_name as \"unitname\", "//��λ����
				+ "  unitInfo.ID  as \"Id\" "//��λ���ñ��Id
				+ "  from t_unit_info unitInfo ,t_unit_type unittype "
				+ "  where unitInfo.unit_typeID=unittype.ID "
				+ "  and unitInfo.unit_class='2' "
				+ "  and unittype.unit_typeName='"+unittypename+"'"
				+ "  and unitInfo.nodeId='"+nodeid+"' ";
		return dao.executeNativeQuery(sql);
	}
    /**
     * ��ӻ��㵥λ��׼��Ϣ
     */
	@Override
	public String addTUnitConversion(SetUnitConversionBean suncbean) {
		// TODO Auto-generated method stub
		Integer id2=0;
	    List<Map<String,Object>> unitIdList1=dao.executeNativeQuery("select unittype.ID as \"Id\"  from t_unit_type unittype  where unittype.unit_name='"+suncbean.getUnit()+"' ");
		for(Map<String,Object> map:unitIdList1){
			id2=(Integer) map.get("Id");
		}//�õ��ο���λ��ID
		String id1=null;
		String unitName=suncbean.getUnitname();
		List<Map<String,Object>> unitIdList=dao.executeNativeQuery("select unitInfo.ID as \"Id\"  from t_unit_info unitInfo   where unitInfo.unit_name='"+unitName+"' ");
		for(Map<String,Object> map:unitIdList){
			id1= map.get("Id").toString();
		}//�õ��뵥λ���ñ��������ID
		String sql=" select unitconversion.con_unitID as \"unitInfoId\","//�뵥λ���ñ���������Id
				+ "  unitconversion.ref_unitID as\"unittypeId\" "//���յ�λId���뵥λ����������Id
				+ "  from t_unit_conversion   unitconversion  "
				+ "  where (unitconversion.con_unitID ='"+id1+"' "
				+ "  or unitconversion.ref_unitID='"+id2+"') "
				+ "  and unitconversion.conversion_type='1'"
				+ "  and unitconversion.nodeId='"+suncbean.getNodeid()+"'";
				
		List<Map<String,Object>> tunlist = dao.executeNativeQuery(sql);
		if(null!=tunlist&&tunlist.size()>0){
			return "�Ѵ���";
		}else{
			try {
				TUnitConversion tuncover=new TUnitConversion();
				TUnitInfo tunInfo=null;
				if(!StringUtils.isEmpty(id1)){
					tunInfo=dao.get(TUnitInfo.class, new Integer(id1));
				}
				tuncover.setTUnitInfo(tunInfo);
				tuncover.setRefUnitId(id2);//���յ�λId
				tuncover.setRatio(suncbean.getRatio());//��λ������
				String contype=suncbean.getConversionType();//��λ��������ͣ�1Ϊ��׼�ͣ�2Ϊ�������ͣ�3Ϊ�������
				if("".equals(contype)||contype.equals("conbz")){
					tuncover.setConversionType("1");
				}
				tuncover.setNodeId(suncbean.getNodeid());//�ڵ�Id
				commonService.save(tuncover);
				return "��ӳɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "���ʧ��";
			}
				
		}
	}

	/**
	 * �޸Ļ��㵥λ��׼��Ϣ
	 */
	
	@Override
	public String updateTUnitConversion(Map<String, Object> tuncon) {
		
		String unitname=tuncon.get("unitname").toString();//��λ����
		String sql=" select unittype.unit_name as \"unitname\" "//��λ����
				+ "  from t_unit_type unittype   "
				+ "  where unittype.unit_name='"+unitname+"'  ";
		List<Map<String,Object>> tunlist = dao.executeNativeQuery(sql);
		if(null!=tunlist&&tunlist.size()>0){
			return "���׼��λ��ͬ";
		}else{
		  try {
		        TUnitConversion unit = dao.get(TUnitConversion.class,Integer.parseInt(tuncon.get("Id").toString()));
		        Double ratio= Double.parseDouble(tuncon.get("ratio").toString());//��û�����
		        unit.setRatio(ratio);
		        TUnitInfo tunInfo=null;
		        String id1=null;
				List<Map<String,Object>> unitIdList=dao.executeNativeQuery("select unitInfo.ID as \"Id\"  from t_unit_info unitInfo   where unitInfo.unit_name='"+unitname+"' ");
				for(Map<String,Object> map:unitIdList){
					id1= map.get("Id").toString();
				}//�õ��뵥λ���ñ��������ID
		        if(!StringUtils.isEmpty(id1)){
			        tunInfo=dao.get(TUnitInfo.class, new Integer(id1));
		         }
		        unit.setTUnitInfo(tunInfo);
		        dao.update(TUnitConversion.class,unit);
		        return "�޸ĳɹ�";
		         }
		       catch (Exception e) {
			    e.printStackTrace();
			     return "�޸�ʧ��";
		      }
	   }
	}
	
	/**
	 * ɾ����λ��׼��Ϣ
	 */
	@Override
	public void deleteTUnitConversion(Map tuncon) {
		// TODO Auto-generated method stub
		TUnitConversion conver=new TUnitConversion();
		String sql2="select unitconversion.ID as \"Id\" from t_unit_conversion unitconversion where unitconversion.ID='"+tuncon.get("Id")+"' ";
		try {
			if(null != tuncon.get("unittypename") && ""!= tuncon.get("unittypename")){
				List<Map<String,Object>> rslist2=dao.executeNativeQuery(sql2);
					 String id1=null;
				for(Map<String,Object> tt:rslist2){
					        id1= tt.get("Id").toString();
							conver = dao.get(TUnitConversion.class,new Integer(id1));
							dao.delete(conver);
						}
				 
						
					}
				
				} catch (Exception e) {
			
			      e.printStackTrace();
		        }		
	}
	
		
	/**
	 * ��ȡ���㵥λ�����ڵ���Ϣ
	 */
	@Override
	public List<Map<String, Object>> getunitConclassifyInfo(String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_typeName as \"unittypename\" ,"//��λ��������
				 + " unittype.unit_name as \"unit\" ,"//��׼��λ����
				 + " unitconversion.con_unitID as \"conunitId\", "
				/* + " unitInfo.unit_name as \"unitname\" ,"*///��λ����
				 + " unitconversion.ID as \"Id\" , "//��λ�����Id
				 + " unitconversion.ratio as \"ratio\", "//��λ������
				 + " matetype.no as \"no\"  "//���ϱ��
				 + " from t_unit_info unitInfo ,t_unit_type unittype , "
				 + " t_unit_conversion   unitconversion , "
				 + " t_materail_type_info  matetype  "
				 + " where unitInfo.unit_typeID=unittype.ID "
				 + " and unitconversion.con_unitID=unitInfo.ID "
				 + " and matetype.ID=unitconversion.materail_id "
				 + " and unitconversion.conversion_type='2'"
				 + " and unitconversion.nodeId='"+nodeid+"' ";
		   return dao.executeNativeQuery(sql);
	}

	
	
	/**
	 * ��ѯ���ϱ�ŵ���Ϣ
	 */
	@Override
	public List<Map<String, Object>> getMaterNo(String nodeid,String no) {
		// TODO Auto-generated method stub
		String hql=" select new Map( "
				+ "  matetype.no as no   "//���ϱ��
				+ "  ) "
				+ "  from  TMaterailTypeInfo matetype  "
				+ "  where matetype.nodeId='"+nodeid+"' ";
		if(null!=no&&!"".equals(no)){
			hql+=" and matetype.no like '%"+no+"%'";
		}     
		return dao.executeQuery(hql);
	}

    /**
     * ͨ�����ϱ�Ų�ѯ��������
     */
	@Override
	public List<Map<String, Object>> getMaterName(String nodeid,String no) {
		// TODO Auto-generated method stub
		
		String hql=" select new Map( "
				+ "  matetype.name as matename  "//��������
			    + "  ) "
				+ "  from  TMaterailTypeInfo matetype  "
				+ "  where matetype.nodeId='"+nodeid+"' "
			    + "  and matetype.no='"+no+"'";
		return dao.executeQuery(hql);
	}

	/**
	 * ������������Ϣ
	 */
	@Override
	public String addTunitConclassify(SetUnitConversionBean suncbean) {
		// TODO Auto-generated method stub
		
		Integer id2=0;
	    List<Map<String,Object>> unitIdList1=dao.executeNativeQuery("select unittype.ID as \"Id\"  from t_unit_type unittype  where unittype.unit_name='"+suncbean.getUnit1()+"' ");
		for(Map<String,Object> map:unitIdList1){
			id2=(Integer) map.get("Id");
		}//�õ��ο���λID
		String id1=null;
		String unitName=suncbean.getUnitname1();
		List<Map<String,Object>> unitIdList=dao.executeNativeQuery("select unitInfo.ID as \"Id\"  from t_unit_info unitInfo   where unitInfo.unit_name='"+unitName+"' ");
		for(Map<String,Object> map:unitIdList){
			id1= map.get("Id").toString();
		}//�õ��뵥λ���ñ��������ID
		Integer id3=0;
		String no=suncbean.getNo();
		List<Map<String,Object>> unitIdList2=dao.executeNativeQuery("select mateInfo.ID as \"Id\"  from t_materail_type_info mateInfo   where mateInfo.no='"+no+"' ");
		for(Map<String,Object> map:unitIdList2){
			id3=(Integer) map.get("Id");
		}//�õ�����ID
		String sql=" select unitconversion.con_unitID as \"unitInfoId\"," //�뵥λ���ñ���������Id
				+ "  unitconversion.ref_unitID as\"unittypeId\" , "//���յ�λId���뵥λ����������Id
				+ "  unitconversion.materail_id as \"mateid\" "
				+ "  from t_unit_conversion   unitconversion , "
				+ "  t_unit_info unitInfo "
				+ "  where (unitconversion.con_unitID ='"+id1+"' "
				+ "  or unitconversion.ref_unitID='"+id2+"') "
				+ "  and unitconversion.materail_id ='"+id3+"'"
				+ "  and unitconversion.conversion_type='2'"
				+ "  and unitconversion.nodeId='"+suncbean.getNodeid()+"'";
				
		List<Map<String,Object>> tunlist = dao.executeNativeQuery(sql);
		if(null!=tunlist&&tunlist.size()>0){
			return "�Ѵ���";
		}else{
			try {
				TUnitConversion tuncover=new TUnitConversion();
				TUnitInfo tunInfo=null;
				
				if(!StringUtils.isEmpty(id1)){
					tunInfo=dao.get(TUnitInfo.class, new Integer(id1));
				}
				tuncover.setTUnitInfo(tunInfo);
				tuncover.setRefUnitId(id2);//���յ�λId
				tuncover.setMaterailId(id3);//����Id
				tuncover.setRatio(suncbean.getRatio1());//��λ������
				String contype=suncbean.getConversionType();//��λ��������ͣ�1Ϊ��׼�ͣ�2Ϊ�������ͣ�3Ϊ�������
				if(contype.equals("confn")){
					tuncover.setConversionType("2");
				}
				tuncover.setNodeId(suncbean.getNodeid());//�ڵ�Id
				commonService.save(tuncover);
				return "��ӳɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "���ʧ��";
			}
				
		}
	}

	/**
	 * �޸Ļ��㵥λ�����ڵ���Ϣ
	 */
	
	@Override
	public String updateTunitConclassify(Map<String, Object> tuncon) {
		// TODO Auto-generated method stub
      String unitname=tuncon.get("unitname").toString();//��ȡ��λ����
	  String sql=" select unittype.unit_name as \"unitname\" "//��λ����
				+ "  from t_unit_type unittype   "
				+ "  where unittype.unit_name='"+unitname+"'  ";
		List<Map<String,Object>> tunlist = dao.executeNativeQuery(sql);
		if(null!=tunlist&&tunlist.size()>0){
			return "���׼��λ��ͬ";
		}else{
		 try {
			  TUnitConversion conve = dao.get(TUnitConversion.class,Integer.parseInt(tuncon.get("Id").toString()));
			   Double ratio= Double.parseDouble(tuncon.get("ratio").toString());//��û�����
			   conve.setRatio(ratio);
			    TUnitInfo tunInfo=null;
		        String id1=null;
				List<Map<String,Object>> unitIdList=dao.executeNativeQuery("select unitInfo.ID as \"Id\"  from t_unit_info unitInfo   where unitInfo.unit_name='"+unitname+"' ");
				for(Map<String,Object> map:unitIdList){
					id1= map.get("Id").toString();
				}//�õ��뵥λ���ñ��������ID
		        if(!StringUtils.isEmpty(id1)){
			        tunInfo=dao.get(TUnitInfo.class, new Integer(id1));
		         }
		        conve.setTUnitInfo(tunInfo);
			     dao.update(TUnitConversion.class,conve);
			    return "�޸ĳɹ�";
			 }
			catch (Exception e) {
				e.printStackTrace();
				return "�޸�ʧ��";
			}
		}
	}

	/**
	 * ɾ����λ��������ڵ���Ϣ
	 */
	
	@Override
	public void deleteTunitConclassify(Map tuncon) {
		// TODO Auto-generated method stub
		TUnitConversion conver=new TUnitConversion();
		String sql2="select unitconversion.ID as \"Id\" from t_unit_conversion unitconversion where unitconversion.ID='"+tuncon.get("Id")+"' ";
		try {
			if(null != tuncon.get("no") && ""!= tuncon.get("no")){
				List<Map<String,Object>> rslist2=dao.executeNativeQuery(sql2);
					 String id1=null;
				for(Map<String,Object> tt:rslist2){
					        id1= tt.get("Id").toString();
							conver = dao.get(TUnitConversion.class,new Integer(id1));
							dao.delete(conver);
						}
				 
						
					}
				
				} catch (Exception e) {
			
			      e.printStackTrace();
		        }		
	}

	
	
	
	
	/**
	 * ��ȡ���㵥λ��������Ϣ
	 */
	@Override
	public List<Map<String, Object>> getunitConsortInfo(String nodeid) {
		// TODO Auto-generated method stub
		String sql="  select DISTINCT unittypename2, unit2, "//���൥λ����2����׼��λ2
				+ " unittype.unit_name as \"unit1\", "//��׼��λ1
				+ " unittype.unit_typeName as \"unittypename1\", "//��������1
				+ " unitconversion.ratio as \"ratio\" , "//��λ������
				+ " matetype.no as \"no\" , "
				+ " unitconversion.ID as \"Id\"  "//���ϱ��
				+ " from ( "
				+ " select unitInfo.ID as Id , "
				+ " unittype.unit_typeName as \"unittypename2\", "//���൥λ����2
				+ " unittype.unit_name as \"unit2\" "//��׼��λ2
				+ " from t_unit_type unittype ,t_unit_info unitInfo "
				+ " where unittype.ID=unitInfo.unit_typeID"
				+ " ) t1,t_unit_type unittype ,"
				+ " t_unit_conversion unitconversion, t_materail_type_info matetype "
			    + " where unitconversion.ref_unitID=unittype.ID "
				+ " and unitconversion.con_unitID=t1.Id "
				+ " and matetype.ID=unitconversion.materail_id "
				+ " and unitconversion.conversion_type='3'"
				+ " and unitconversion.nodeId='"+nodeid+"' "; 
          return dao.executeNativeQuery(sql);
	}

	/**
	 * �����������Ϣ
	 */
	
	@Override
	public String addUnitConsortInfo(SetUnitConversionBean suncbean) {
		// TODO Auto-generated method stub
		Integer id2=0;
		String unit2=suncbean.getUnit2();
	    List<Map<String,Object>> unitIdList1=dao.executeNativeQuery("select unittype.ID as \"Id\"  from t_unit_type unittype  where unittype.unit_name='"+unit2+"' ");
		for(Map<String,Object> map:unitIdList1){
			id2=(Integer) map.get("Id");
		}//�õ��ο���λID
		String id1=null;
		String unit3=suncbean.getUnit3();
		List<Map<String,Object>> unitIdList2=dao.executeNativeQuery("select unittype.ID as \"Id\"  from t_unit_type unittype  where unittype.unit_name='"+unit3+"' ");
		for(Map<String,Object> map:unitIdList2){
			id1= map.get("Id").toString();
		}//�õ��뵥λ������������ID
		String id4=null;
		List<Map<String,Object>> unitIdList=dao.executeNativeQuery("select unitInfo.ID as \"Id\"  from t_unit_info unitInfo   where unitInfo.unit_typeID='"+id1+"' ");
		for(Map<String,Object> map:unitIdList){
			id4= map.get("Id").toString();
		}//�õ��뵥λ���ñ������ID
		Integer id3=0;
		String no1=suncbean.getNo1();
		List<Map<String,Object>> unitIdList3=dao.executeNativeQuery("select mateInfo.ID as \"Id\"  from t_materail_type_info mateInfo   where mateInfo.no='"+no1+"' ");
		for(Map<String,Object> map:unitIdList3){
			id3=(Integer) map.get("Id");
		}//�õ�����ID
		String sql=" select unitconversion.con_unitID as \"unitInfoId\","//�뵥λ���ñ���������Id
				+ "  unitconversion.ref_unitID as\"unittypeId\" , "//���յ�λId���뵥λ����������Id
				+ "  unitconversion.materail_id as \"mateid\" "
				+ "  from t_unit_conversion   unitconversion , "
				+ "  t_unit_info unitInfo "
				+ "  where (unitconversion.con_unitID ='"+id4+"' "
				+ "  or unitconversion.ref_unitID='"+id2+"') "
				+ "  and unitconversion.materail_id ='"+id3+"'"
				+ "  and unitconversion.conversion_type='3'"
				+ "  and unitconversion.nodeId='"+suncbean.getNodeid()+"'";
				
				
		List<Map<String,Object>> tunlist = dao.executeNativeQuery(sql);
		if(null!=tunlist&&tunlist.size()>0){
			return "�Ѵ���";
		}else{
			try {
				TUnitConversion tuncover=new TUnitConversion();
				TUnitInfo tunInfo=null;
				
				if(!StringUtils.isEmpty(id4)){
					tunInfo=dao.get(TUnitInfo.class, new Integer(id4));
				}
				tuncover.setTUnitInfo(tunInfo);//��λ���ñ�������ID
				tuncover.setRefUnitId(id2);//���յ�λId
				tuncover.setMaterailId(id3);//����Id
				tuncover.setRatio(suncbean.getRatio2());//��λ������
				String contype=suncbean.getConversionType();//��λ��������ͣ�1Ϊ��׼�ͣ�2Ϊ�������ͣ�3Ϊ�������
				if(contype.equals("confj")){
					tuncover.setConversionType("3");
				}
				tuncover.setNodeId(suncbean.getNodeid());//�ڵ�Id
				commonService.save(tuncover);
				return "��ӳɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "���ʧ��";
			}
				
		}
	}

	/**
	 * �޸ĵ�λ�����������Ϣ
	 */
	@Override
	public String updateUnitConsortInfo(Map<String, Object> tuncon) {
		// TODO Auto-generated method stub
		try {
			  TUnitConversion conve = dao.get(TUnitConversion.class,Integer.parseInt(tuncon.get("Id").toString()));
			   Double ratio= Double.parseDouble(tuncon.get("ratio").toString());//��û�����
			   conve.setRatio(ratio);
			   dao.update(TUnitConversion.class,conve);
			    return "�޸ĳɹ�";
			 }
			catch (Exception e) {
				e.printStackTrace();
				return "�޸�ʧ��";
			}
	}

    /**
     * ɾ����λ�����������Ϣ
     * 
     */
	@Override
	public void deleteUnitConsortInfo(Map tuncon) {
		// TODO Auto-generated method stub
		TUnitConversion conver=new TUnitConversion();
		String sql2="select unitconversion.ID as \"Id\" from t_unit_conversion unitconversion where unitconversion.ID='"+tuncon.get("Id")+"' ";
		try {
			if(null != tuncon.get("no") && ""!= tuncon.get("no")){
				List<Map<String,Object>> rslist2=dao.executeNativeQuery(sql2);
					 String id1=null;
				for(Map<String,Object> tt:rslist2){
					        id1= tt.get("Id").toString();
							conver = dao.get(TUnitConversion.class,new Integer(id1));
							dao.delete(conver);
						}
				 
				   }
				
				} catch (Exception e) {
			     e.printStackTrace();
		     }		
	}

}

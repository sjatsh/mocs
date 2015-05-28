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
	 * 获取计量单位分类的基本信息
	 */
	@Override
	public List<Map<String, Object>> getUnitTypeInfo(String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_typeName as \"unittypename\"," //单位分类名称
				+ "  unittype.ID  as \"Id\", "             //单位分类Id
				+ "  unittype.nodeId as \"nodeId\" , "     //节点Id
				+ "  unittype.unit_typeDesc as \"unittypedesc\", "//单位分类说明
				+ "  unittype.unit_name as \"unit\","     //标准单位名称
				+ "  unittype.unit_short as \"unitshort\","// 标准单位缩写
				+ "  unittype.invalid_date as \"invadate\" "//失效日期
				+ "  from t_unit_type unittype "
				+ "  where unittype.nodeId='"+nodeid+"' ";
		return dao.executeNativeQuery(sql);
	}
    
	/**
	 * 新增计量单位分类的基本信息
	 */
	@Override
	public String addTUnitType(SetUnitBean sunitbean) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_typeName as \"unittypename\","//单位分类名称
				+ "  unittype.unit_name as \"unit\" "//标准单位名称
				+ "  from t_unit_type unittype "
				+ "  where(unittype.unit_typeName = '" + sunitbean.getUnittypename() + "' "
				+ "  or unittype.unit_name='"+sunitbean.getUnit()+"' ) and unittype.nodeId='"+sunitbean.getNodeid()+"' ";
		List<Map<String,Object>> typeList = dao.executeNativeQuery(sql);
		if(null!=typeList&&typeList.size()>0){
			return "已存在";
		}else{
			try {
				TUnitType untype=new TUnitType();
				untype.setUnitTypeName(sunitbean.getUnittypename());//设置单位分类名称
				untype.setUnitTypeDesc(sunitbean.getUnittypedesc());//设置单位分类描述
				untype.setUnitName(sunitbean.getUnit());//设置标准单位名称
				untype.setUnitShort(sunitbean.getUnitshort());//设置标准单位描述
				untype.setInvalidDate(sunitbean.getInvadate());//设置标准单位失效日期
				Date dt=new Date();
			    untype.setCreateDate(dt);//标准单位创建日期
			    untype.setNodeId(sunitbean.getNodeid());//获得当前的节点Id
				String sql1=" select unitInfo.unit_name as \"unitname\" "
						  + " from t_unit_info unitInfo "
						  + " where unitInfo.unit_name='"+sunitbean.getUnit()+"' "
						  + " and unitInfo.nodeId='"+sunitbean.getNodeid()+"' ";
				
				List<Map<String,Object>> unInfo=dao.executeNativeQuery(sql1);
				if(null!=unInfo&&unInfo.size()>0){
					return "单位名称已存在";
				}else{
			    TUnitInfo unitInfo=new TUnitInfo();
				unitInfo.setUnitName(sunitbean.getUnit());//单位名称
				unitInfo.setUnitShort(sunitbean.getUnitshort());//单位名称缩写
				unitInfo.setUnitClass("1");//单位类别(1为标准，2为非标准)
				unitInfo.setUnitStatus("1");//单位状态(1为启用，2为未启用)
				unitInfo.setNodeId(sunitbean.getNodeid());
				unitInfo.setCreateDate(dt);//单位设置表中的单位名称
				unitInfo.setTUnitType(untype);
				commonService.save(unitInfo);
				}
				commonService.save(untype);
				return "添加成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "添加失败";
			}
			
		}
	}
	
	/**
	 * 修改计量单位的分类信息
	 */
	
	@Override
	public String updateTUnitType(Map<String, Object> tuntype) {
		// TODO Auto-generated method stub
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql1 = "from TUnitType where unitTypeName='" + tuntype.get("unittypename") + "'  and  id<>'"+tuntype.get("Id")+"'";
		List<TUnitType> tprlist = dao.executeQuery(hql1, parameters);
			try {
				if(null == tprlist || tprlist.size() < 1){
				String hql="from TUnitType where id="+tuntype.get("Id");//获取选中行的Id
				List<TUnitType> rslist2 =dao.executeQuery(hql);
				TUnitType type=rslist2.get(0);
				type.setUnitTypeName(tuntype.get("unittypename").toString());//单位分类名称
				type.setUnitTypeDesc(tuntype.get("unittypedesc").toString());//单位分类描述
				type.setUnitName(tuntype.get("unit").toString());//标准单位名称
				type.setUnitShort(tuntype.get("unitshort").toString());//标准单位缩写
				Date date=null;
				date=StringUtils.convertDate(tuntype.get("invadate").toString(), "yyyy-MM-dd HH:mm:ss");//标准单位失效日期
				type.setInvalidDate(date);
				Date dt=new Date();
			    type.setCreateDate(dt);//标准单位创建日期
				return "修改成功";
				}
				else
				{
					return "已存在";
				}	
			} catch (Exception e) {
				e.printStackTrace();
				return "修改失败";
			}
			
		}
	
	
	/**
	 * 删除计量单位信息
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
	 * 获取单位设置的基本信息
	 */

	@Override
	public List<Map<String, Object>> getUnitInfo(String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_typeName as\"unittypename\","//单位分类名称
				+ "  unitInfo.ID as \"Id\","  //Id
				+ "  unitInfo.unit_name as \"unitname\"," //单位名称
				+ "  unitInfo.unit_short as\"unitshort\","//单位缩写
				+ "  unitInfo.unit_desc as \"unitdesc\", "//单位描述
		        + "  (case  unitInfo.unit_status when  '1' then '启用' when '2' then '未启用' else '0' end ) as \"unitstatus\" "//单位状态，1为启用，2为未启用
				+ "  from t_unit_info unitInfo ,t_unit_type unittype "
				+ "  where unitInfo.unit_typeID=unittype.ID "
				+ "  and unitInfo.nodeId='"+nodeid+"' ";
		
		return dao.executeNativeQuery(sql);
	}
    /**
     * 获取单位分类名称的下拉 列表
     */
	@Override
	public List<Map<String, Object>> getunitTypeName(String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_typeName as \"unittypename\","//单位分类名称
				 + " unittype.ID  as\"Id\" "//单位分类ID
				 + " from t_unit_type unittype "
				 + " where unittype.nodeId='"+nodeid+"'";
		return dao.executeNativeQuery(sql);
	}
	
	/**
	 * 通过单位分类获得单位名称
	 */
	@Override
	public List<Map<String, Object>> getunitName(String unittypename,String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_name as \"unit\" "//单位名称
				 + " from t_unit_type unittype "
				 + " where unittype.unit_typeName='"+unittypename+"' "//单位分类名称
				 + " and unittype.nodeId='"+nodeid+"' ";
		return dao.executeNativeQuery(sql);
	}

	
	
	
    /**
     * 新增单位设置的信息
     */

	@Override
	public String addTUnitInfo(SetUnitInfoBean sunitbean) {
		// TODO Auto-generated method stub
		String sql=" select unitInfo.unit_name as \"unitname\", "//单位名称
				+ "  unitInfo.unit_short as \"unitshort\" "//单位缩写
				+ "  from t_unit_info unitInfo "
				+ "  where (unitInfo.unit_name = '" + sunitbean.getUnitName() + "' "
				+ "  or unitInfo.unit_short='"+sunitbean.getUnitShort()+"' )  and unitInfo.nodeId='"+sunitbean.getNodeid()+"'";
		List<Map<String,Object>> tunlist = dao.executeNativeQuery(sql);
		if(null!=tunlist&&tunlist.size()>0){
			return "已存在";
		}else{
			try {
				TUnitInfo tun=new TUnitInfo();
				TUnitType tutype=null;
				String id=null;
				String unittypename=sunitbean.getUnittypename();//获取单位分类名称
				List<Map<String,Object>> unitIdList=dao.executeNativeQuery("select unittype.ID as \"Id\"  from t_unit_type unittype  where unittype.unit_typeName='"+unittypename+"' ");
				for(Map<String,Object> map:unitIdList){
					id=map.get("Id").toString();
				}
				if(!StringUtils.isEmpty(id)){
					tutype=dao.get(TUnitType.class, new Integer(id));
					
				}//通过单位分类名称获得相关联的Id
				tun.setTUnitType(tutype);
				tun.setUnitName(sunitbean.getUnitName());//单位名称
				tun.setUnitShort(sunitbean.getUnitShort());//单位缩写
				tun.setUnitDesc(sunitbean.getUnitDesc());//单位描述
				Date dt=new Date();
			    tun.setCreateDate(dt);//单位创建日期
				tun.setUnitClass("2");//单位分为标准和非标准
				tun.setUnitStatus("1");//单位状态，1为启用，2为未启用
				tun.setNodeId(sunitbean.getNodeid());
				commonService.save(tun);
				return "添加成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "添加失败";
			}
				
		}
		
	}
    
	/**
	 * 修改单位设置信息
	 */
	
	@Override
	public String updateTUnitInfo(Map<String, Object> tunInfo) {
		// TODO Auto-generated method stub
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql1 = "from TUnitInfo where unitName='" + tunInfo.get("unitname") + "'  and  id<>'"+tunInfo.get("Id")+"'";
		List<TUnitInfo> tprlist = dao.executeQuery(hql1, parameters);
			try {
				if(null == tprlist || tprlist.size() < 1){
			    String hql="from TUnitInfo  where id="+tunInfo.get("Id");//获取选中行的Id
				List<TUnitInfo> rslist2 =dao.executeQuery(hql);
				TUnitInfo  tuninfo=rslist2.get(0);
				tuninfo.setUnitName(tunInfo.get("unitname").toString());//单位名称
				tuninfo.setUnitShort(tunInfo.get("unitshort").toString());//单位缩写
				tuninfo.setUnitDesc(tunInfo.get("unitdesc").toString());//单位描述
				Date dt=new Date();
			    tuninfo.setCreateDate(dt);//创建日期
			    String str=tunInfo.get("unitstatus").toString();//单位状态
			    if(str.equals("启用")){
			    	tuninfo.setUnitStatus("1");
			    }
			    if(str.equals("未启用")){
			    	tuninfo.setUnitStatus("2");
			       }
			    return "修改成功";
				}
				else
				{
					return "已存在";
				}	
			} catch (Exception e) {
				e.printStackTrace();
				return "修改失败";
			}
			
		}
	
	
	/**
	 * 删除单位设置的信息
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
	 * 获取计量单位换算标准的基本信息
	 */
	@Override
	public List<Map<String, Object>> getunitConversionInfo(String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_typeName as\"unittypename\","//单位分类名称
				+ "  unittype.unit_name as \"unit\","//标准单位名称
				/*+ "  unitInfo.unit_name as \"unitname\","*///单位名称
				+ "  unitconversion.ID as \"Id\" ,"//换算表的Id
				+ "  unitconversion.ratio as \"ratio\" ,"//单位换算率
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
	 * 获取计量单位的下拉列表
	 */
	@Override
	public List<Map<String, Object>> getConversionUnit(String unittypename,String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unitInfo.unit_name as \"unitname\", "//单位名称
				+ "  unitInfo.ID  as \"Id\" "//单位设置表的Id
				+ "  from t_unit_info unitInfo ,t_unit_type unittype "
				+ "  where unitInfo.unit_typeID=unittype.ID "
				+ "  and unitInfo.unit_class='2' "
				+ "  and unittype.unit_typeName='"+unittypename+"'"
				+ "  and unitInfo.nodeId='"+nodeid+"' ";
		return dao.executeNativeQuery(sql);
	}
    /**
     * 添加换算单位标准信息
     */
	@Override
	public String addTUnitConversion(SetUnitConversionBean suncbean) {
		// TODO Auto-generated method stub
		Integer id2=0;
	    List<Map<String,Object>> unitIdList1=dao.executeNativeQuery("select unittype.ID as \"Id\"  from t_unit_type unittype  where unittype.unit_name='"+suncbean.getUnit()+"' ");
		for(Map<String,Object> map:unitIdList1){
			id2=(Integer) map.get("Id");
		}//得到参考单位的ID
		String id1=null;
		String unitName=suncbean.getUnitname();
		List<Map<String,Object>> unitIdList=dao.executeNativeQuery("select unitInfo.ID as \"Id\"  from t_unit_info unitInfo   where unitInfo.unit_name='"+unitName+"' ");
		for(Map<String,Object> map:unitIdList){
			id1= map.get("Id").toString();
		}//得到与单位设置表相关联的ID
		String sql=" select unitconversion.con_unitID as \"unitInfoId\","//与单位设置表的相关联的Id
				+ "  unitconversion.ref_unitID as\"unittypeId\" "//参照单位Id，与单位分类表相关联Id
				+ "  from t_unit_conversion   unitconversion  "
				+ "  where (unitconversion.con_unitID ='"+id1+"' "
				+ "  or unitconversion.ref_unitID='"+id2+"') "
				+ "  and unitconversion.conversion_type='1'"
				+ "  and unitconversion.nodeId='"+suncbean.getNodeid()+"'";
				
		List<Map<String,Object>> tunlist = dao.executeNativeQuery(sql);
		if(null!=tunlist&&tunlist.size()>0){
			return "已存在";
		}else{
			try {
				TUnitConversion tuncover=new TUnitConversion();
				TUnitInfo tunInfo=null;
				if(!StringUtils.isEmpty(id1)){
					tunInfo=dao.get(TUnitInfo.class, new Integer(id1));
				}
				tuncover.setTUnitInfo(tunInfo);
				tuncover.setRefUnitId(id2);//参照单位Id
				tuncover.setRatio(suncbean.getRatio());//单位换算率
				String contype=suncbean.getConversionType();//单位换算的类型，1为标准型，2为分类内型，3为分类间型
				if("".equals(contype)||contype.equals("conbz")){
					tuncover.setConversionType("1");
				}
				tuncover.setNodeId(suncbean.getNodeid());//节点Id
				commonService.save(tuncover);
				return "添加成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "添加失败";
			}
				
		}
	}

	/**
	 * 修改换算单位标准信息
	 */
	
	@Override
	public String updateTUnitConversion(Map<String, Object> tuncon) {
		
		String unitname=tuncon.get("unitname").toString();//单位名称
		String sql=" select unittype.unit_name as \"unitname\" "//单位名称
				+ "  from t_unit_type unittype   "
				+ "  where unittype.unit_name='"+unitname+"'  ";
		List<Map<String,Object>> tunlist = dao.executeNativeQuery(sql);
		if(null!=tunlist&&tunlist.size()>0){
			return "与基准单位相同";
		}else{
		  try {
		        TUnitConversion unit = dao.get(TUnitConversion.class,Integer.parseInt(tuncon.get("Id").toString()));
		        Double ratio= Double.parseDouble(tuncon.get("ratio").toString());//获得换算率
		        unit.setRatio(ratio);
		        TUnitInfo tunInfo=null;
		        String id1=null;
				List<Map<String,Object>> unitIdList=dao.executeNativeQuery("select unitInfo.ID as \"Id\"  from t_unit_info unitInfo   where unitInfo.unit_name='"+unitname+"' ");
				for(Map<String,Object> map:unitIdList){
					id1= map.get("Id").toString();
				}//得到与单位设置表相关联的ID
		        if(!StringUtils.isEmpty(id1)){
			        tunInfo=dao.get(TUnitInfo.class, new Integer(id1));
		         }
		        unit.setTUnitInfo(tunInfo);
		        dao.update(TUnitConversion.class,unit);
		        return "修改成功";
		         }
		       catch (Exception e) {
			    e.printStackTrace();
			     return "修改失败";
		      }
	   }
	}
	
	/**
	 * 删除单位标准信息
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
	 * 获取换算单位分类内的信息
	 */
	@Override
	public List<Map<String, Object>> getunitConclassifyInfo(String nodeid) {
		// TODO Auto-generated method stub
		String sql=" select unittype.unit_typeName as \"unittypename\" ,"//单位分类名称
				 + " unittype.unit_name as \"unit\" ,"//标准单位名称
				 + " unitconversion.con_unitID as \"conunitId\", "
				/* + " unitInfo.unit_name as \"unitname\" ,"*///单位名称
				 + " unitconversion.ID as \"Id\" , "//单位换算表Id
				 + " unitconversion.ratio as \"ratio\", "//单位换算率
				 + " matetype.no as \"no\"  "//物料编号
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
	 * 查询物料编号的信息
	 */
	@Override
	public List<Map<String, Object>> getMaterNo(String nodeid,String no) {
		// TODO Auto-generated method stub
		String hql=" select new Map( "
				+ "  matetype.no as no   "//物料编号
				+ "  ) "
				+ "  from  TMaterailTypeInfo matetype  "
				+ "  where matetype.nodeId='"+nodeid+"' ";
		if(null!=no&&!"".equals(no)){
			hql+=" and matetype.no like '%"+no+"%'";
		}     
		return dao.executeQuery(hql);
	}

    /**
     * 通过物料编号查询物料名称
     */
	@Override
	public List<Map<String, Object>> getMaterName(String nodeid,String no) {
		// TODO Auto-generated method stub
		
		String hql=" select new Map( "
				+ "  matetype.name as matename  "//物料名称
			    + "  ) "
				+ "  from  TMaterailTypeInfo matetype  "
				+ "  where matetype.nodeId='"+nodeid+"' "
			    + "  and matetype.no='"+no+"'";
		return dao.executeQuery(hql);
	}

	/**
	 * 新增分类内信息
	 */
	@Override
	public String addTunitConclassify(SetUnitConversionBean suncbean) {
		// TODO Auto-generated method stub
		
		Integer id2=0;
	    List<Map<String,Object>> unitIdList1=dao.executeNativeQuery("select unittype.ID as \"Id\"  from t_unit_type unittype  where unittype.unit_name='"+suncbean.getUnit1()+"' ");
		for(Map<String,Object> map:unitIdList1){
			id2=(Integer) map.get("Id");
		}//得到参考单位ID
		String id1=null;
		String unitName=suncbean.getUnitname1();
		List<Map<String,Object>> unitIdList=dao.executeNativeQuery("select unitInfo.ID as \"Id\"  from t_unit_info unitInfo   where unitInfo.unit_name='"+unitName+"' ");
		for(Map<String,Object> map:unitIdList){
			id1= map.get("Id").toString();
		}//得到与单位设置表相关联的ID
		Integer id3=0;
		String no=suncbean.getNo();
		List<Map<String,Object>> unitIdList2=dao.executeNativeQuery("select mateInfo.ID as \"Id\"  from t_materail_type_info mateInfo   where mateInfo.no='"+no+"' ");
		for(Map<String,Object> map:unitIdList2){
			id3=(Integer) map.get("Id");
		}//得到物料ID
		String sql=" select unitconversion.con_unitID as \"unitInfoId\"," //与单位设置表的相关联的Id
				+ "  unitconversion.ref_unitID as\"unittypeId\" , "//参照单位Id，与单位分类表相关联Id
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
			return "已存在";
		}else{
			try {
				TUnitConversion tuncover=new TUnitConversion();
				TUnitInfo tunInfo=null;
				
				if(!StringUtils.isEmpty(id1)){
					tunInfo=dao.get(TUnitInfo.class, new Integer(id1));
				}
				tuncover.setTUnitInfo(tunInfo);
				tuncover.setRefUnitId(id2);//参照单位Id
				tuncover.setMaterailId(id3);//物料Id
				tuncover.setRatio(suncbean.getRatio1());//单位换算率
				String contype=suncbean.getConversionType();//单位换算的类型，1为标准型，2为分类内型，3为分类间型
				if(contype.equals("confn")){
					tuncover.setConversionType("2");
				}
				tuncover.setNodeId(suncbean.getNodeid());//节点Id
				commonService.save(tuncover);
				return "添加成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "添加失败";
			}
				
		}
	}

	/**
	 * 修改换算单位分类内的信息
	 */
	
	@Override
	public String updateTunitConclassify(Map<String, Object> tuncon) {
		// TODO Auto-generated method stub
      String unitname=tuncon.get("unitname").toString();//获取单位名称
	  String sql=" select unittype.unit_name as \"unitname\" "//单位名称
				+ "  from t_unit_type unittype   "
				+ "  where unittype.unit_name='"+unitname+"'  ";
		List<Map<String,Object>> tunlist = dao.executeNativeQuery(sql);
		if(null!=tunlist&&tunlist.size()>0){
			return "与基准单位相同";
		}else{
		 try {
			  TUnitConversion conve = dao.get(TUnitConversion.class,Integer.parseInt(tuncon.get("Id").toString()));
			   Double ratio= Double.parseDouble(tuncon.get("ratio").toString());//获得换算率
			   conve.setRatio(ratio);
			    TUnitInfo tunInfo=null;
		        String id1=null;
				List<Map<String,Object>> unitIdList=dao.executeNativeQuery("select unitInfo.ID as \"Id\"  from t_unit_info unitInfo   where unitInfo.unit_name='"+unitname+"' ");
				for(Map<String,Object> map:unitIdList){
					id1= map.get("Id").toString();
				}//得到与单位设置表相关联的ID
		        if(!StringUtils.isEmpty(id1)){
			        tunInfo=dao.get(TUnitInfo.class, new Integer(id1));
		         }
		        conve.setTUnitInfo(tunInfo);
			     dao.update(TUnitConversion.class,conve);
			    return "修改成功";
			 }
			catch (Exception e) {
				e.printStackTrace();
				return "修改失败";
			}
		}
	}

	/**
	 * 删除单位换算分类内的信息
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
	 * 获取换算单位分类间的信息
	 */
	@Override
	public List<Map<String, Object>> getunitConsortInfo(String nodeid) {
		// TODO Auto-generated method stub
		String sql="  select DISTINCT unittypename2, unit2, "//分类单位名称2，标准单位2
				+ " unittype.unit_name as \"unit1\", "//标准单位1
				+ " unittype.unit_typeName as \"unittypename1\", "//分类名称1
				+ " unitconversion.ratio as \"ratio\" , "//单位换算率
				+ " matetype.no as \"no\" , "
				+ " unitconversion.ID as \"Id\"  "//物料编号
				+ " from ( "
				+ " select unitInfo.ID as Id , "
				+ " unittype.unit_typeName as \"unittypename2\", "//分类单位名称2
				+ " unittype.unit_name as \"unit2\" "//标准单位2
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
	 * 新增分类间信息
	 */
	
	@Override
	public String addUnitConsortInfo(SetUnitConversionBean suncbean) {
		// TODO Auto-generated method stub
		Integer id2=0;
		String unit2=suncbean.getUnit2();
	    List<Map<String,Object>> unitIdList1=dao.executeNativeQuery("select unittype.ID as \"Id\"  from t_unit_type unittype  where unittype.unit_name='"+unit2+"' ");
		for(Map<String,Object> map:unitIdList1){
			id2=(Integer) map.get("Id");
		}//得到参考单位ID
		String id1=null;
		String unit3=suncbean.getUnit3();
		List<Map<String,Object>> unitIdList2=dao.executeNativeQuery("select unittype.ID as \"Id\"  from t_unit_type unittype  where unittype.unit_name='"+unit3+"' ");
		for(Map<String,Object> map:unitIdList2){
			id1= map.get("Id").toString();
		}//得到与单位分类表相关联的ID
		String id4=null;
		List<Map<String,Object>> unitIdList=dao.executeNativeQuery("select unitInfo.ID as \"Id\"  from t_unit_info unitInfo   where unitInfo.unit_typeID='"+id1+"' ");
		for(Map<String,Object> map:unitIdList){
			id4= map.get("Id").toString();
		}//得到与单位设置表相关联ID
		Integer id3=0;
		String no1=suncbean.getNo1();
		List<Map<String,Object>> unitIdList3=dao.executeNativeQuery("select mateInfo.ID as \"Id\"  from t_materail_type_info mateInfo   where mateInfo.no='"+no1+"' ");
		for(Map<String,Object> map:unitIdList3){
			id3=(Integer) map.get("Id");
		}//得到物料ID
		String sql=" select unitconversion.con_unitID as \"unitInfoId\","//与单位设置表的相关联的Id
				+ "  unitconversion.ref_unitID as\"unittypeId\" , "//参照单位Id，与单位分类表相关联Id
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
			return "已存在";
		}else{
			try {
				TUnitConversion tuncover=new TUnitConversion();
				TUnitInfo tunInfo=null;
				
				if(!StringUtils.isEmpty(id4)){
					tunInfo=dao.get(TUnitInfo.class, new Integer(id4));
				}
				tuncover.setTUnitInfo(tunInfo);//单位设置表的相关联ID
				tuncover.setRefUnitId(id2);//参照单位Id
				tuncover.setMaterailId(id3);//物料Id
				tuncover.setRatio(suncbean.getRatio2());//单位换算率
				String contype=suncbean.getConversionType();//单位换算的类型，1为标准型，2为分类内型，3为分类间型
				if(contype.equals("confj")){
					tuncover.setConversionType("3");
				}
				tuncover.setNodeId(suncbean.getNodeid());//节点Id
				commonService.save(tuncover);
				return "添加成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "添加失败";
			}
				
		}
	}

	/**
	 * 修改单位换算分类间的信息
	 */
	@Override
	public String updateUnitConsortInfo(Map<String, Object> tuncon) {
		// TODO Auto-generated method stub
		try {
			  TUnitConversion conve = dao.get(TUnitConversion.class,Integer.parseInt(tuncon.get("Id").toString()));
			   Double ratio= Double.parseDouble(tuncon.get("ratio").toString());//获得换算率
			   conve.setRatio(ratio);
			   dao.update(TUnitConversion.class,conve);
			    return "修改成功";
			 }
			catch (Exception e) {
				e.printStackTrace();
				return "修改失败";
			}
	}

    /**
     * 删除单位换算分类间的信息
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

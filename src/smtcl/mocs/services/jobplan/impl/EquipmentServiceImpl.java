package smtcl.mocs.services.jobplan.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.GenericServiceSpringImpl;

import smtcl.mocs.beans.equipment.EquipmentAccountingBean;
import smtcl.mocs.beans.equipment.EquipmentCostBean;
import smtcl.mocs.beans.equipment.EquipmentTypeBean;
import smtcl.mocs.common.device.JsonResponseResult;
import smtcl.mocs.dao.device.ICommonDao;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TEquipmentMemberInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUserEquCurStatus;
import smtcl.mocs.pojos.job.TEquipmentCostInfo;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.jobplan.IEquipmentService;
import smtcl.mocs.utils.device.StringUtils;

import com.ibm.faces.util.StringUtil;

/**
 * 设备管理SERVICE接口实现类
 * @作者：yyh
 * @创建时间：2013-08-06
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
public class EquipmentServiceImpl extends GenericServiceSpringImpl<TEquipmentCostInfo, String> implements IEquipmentService {

	private ICommonDao commonDao;
	
	private ICommonService commonService;
	
	public ICommonDao getCommonDao() {
		return commonDao;
	}
	
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}
	
	public ICommonService getCommonService() {
		return commonService;
	}
	
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}	
	
	/**
	 * 设备类型维护--设备类型信息列表
	 */
	public List<Map<String, Object>> getEquipmentInfoAList(String equipmentType){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " ty.id as id,"      //设备类型ID
				+ " ty.equipmentType as equipmentType,"     //设备类型名称 
				+ " ty.typecode as typecode,"    //分类编码
				+ " ty.norm as norm,"        //制造商型号
				+ " ty.cnc as cnc,"                   //数控系统
				+ " ty.description as description)"                 //设备类型描述
		        + " FROM TEquipmenttypeInfo ty  "
		        + " WHERE ty.level = 1 AND ty.isdel = '0'  "
		        + " and ty.nodeid='"+nodeid+"'";
		if(equipmentType!=null && !"".equals(equipmentType)){
		        hql += " AND ty.id = '"+ equipmentType +"'   ";
		}      
		return dao.executeQuery(hql);
	}
	
	/**
	 * 设备类型维护--左侧树的设备类别
	 */
	public List<Map<String, Object>> getEquType(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //设备类别ID
				+ " t.equipmentType as equipmentType,"    // 设备类别名称
				+ " t.description as description)"         //设备类别描述
		        + " FROM TEquipmenttypeInfo t WHERE t.level = 1 AND t.isdel = '0' "
		        + " and t.nodeid='"+nodeid+"'";
		
		return dao.executeQuery(hql);
	}
	
	/**
	 * 设备类型维护--左侧树的设备类别--和下级
	 */
	public List<Map<String, Object>> getEquByTypeId(String pEqutypeId){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //设备类别ID
				+ " t.equipmentType as equipmentType,"    // 设备类别名称
				+ " t.description as description)"         //设备类别描述
		        + " FROM TEquipmenttypeInfo t  "
		        + " WHERE  t.TEquipmenttypeInfo.id = '"+ pEqutypeId +"' AND t.isdel = '0' "
		        + " AND t.nodeid = '"+ nodeid +"'   ";
		return dao.executeQuery(hql);
	}
	/**
	 * 设备类型维护--通过类型ID得到类型
	 */
	public TEquipmenttypeInfo getgetEquTypeIdById(String typeId){
		TEquipmenttypeInfo ti =null;
		  if(!StringUtil.isEmpty(typeId)){
			  ti=commonService.get(TEquipmenttypeInfo.class, Long.valueOf(typeId));
		  }
		  return ti;
	}
	
	
	/**
	 * 设备类型维护--通过类型名称得到类型ID
	 */
	public String getEquTypeIdByName(String name){
		String hql =  "select new Map("
				+ " t.id as id,"                           //设备类别ID
				+ " t.equipmentType as equipmentType,"    // 设备类别名称
				+ " t.description as description)"         //设备类别描述
		        + " FROM TEquipmenttypeInfo t  "
		        + " WHERE  t.equipmentType = '"+ name +"' ";
		String typeId ="";
		List<Map<String, Object>> lst = dao.executeQuery(hql);
		if(lst.size()>0){
			Map<String, Object> map = lst.get(0);
			typeId  = map.get("id").toString();
		}	
		return typeId;	
	}
	
	/**
	 * 设备类型维护--左侧树点击得到右侧列表
	 */
	public List<Map<String, Object>> getEquTypeByClick(String pEqutypeId){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " ty.id as id,"      //设备类型ID
				+ " ty.equipmentType as equipmentType,"     //设备类型名称 
				+ " ty.typecode as typecode,"    //分类编码
				+ " ty.norm as norm,"        //制造商型号
				+ " ty.cnc as cnc,"                   //数控系统
				+ " ty.description as description,"
				+ " ty.erpResouceCode as erpResouceCode,"
				+ " ty.erpResouceDesc as erpResouceDesc)"                 //设备类型描述
		        + " FROM TEquipmenttypeInfo ty  "
		        + " WHERE ty.TEquipmenttypeInfo.id = '"+pEqutypeId+"' AND ty.isdel = '0' "
		        + " and ty.nodeid = '"+ nodeid +"'   ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 设备类型维护--右侧列表字段副设备类型名称
	 */
	public List<Map<String, Object>> getParentNameById(String Id){
		String hql =  "select new Map("
				+ " ty.id as id,"      //设备类型ID
				+ " ty.equipmentType as equipmentType,"     //设备类型名称 
				+ " ty.description as description)"                 //设备类型描述
		        + " FROM TEquipmenttypeInfo ty  "
		        + " WHERE ty.id = '"+Id+"'  ";        
		return dao.executeQuery(hql);
	}
	
	/**
	 * 设备类型维护--添加时的下拉
	 */
	public List<Map<String, Object>> getParentIdMap(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //设备类别ID
				+ " t.equipmentType as equipmentType,"    // 设备类别名称
				+ " t.description as description)"         //设备类别描述
		        + " FROM TEquipmenttypeInfo t  "
		        + " where t.isdel=0 "
		        + " and t.nodeid = '"+ nodeid +"'   or t.id='-999'";
		return dao.executeQuery(hql);	
	}
	
	/**
	 * 设备类型维护--保存
	 */

	public void addEquType(EquipmentTypeBean equipmentTypeBean){
		equipmentTypeBean.getEquTypeObj().setIsdel("0");
		Integer parentId = Integer.parseInt((equipmentTypeBean.getEquTypeObj().getTEquipmenttypeInfo().getId().toString()));
		TEquipmenttypeInfo ec  = null;
		if(!StringUtils.isEmpty(parentId.toString())){
			ec=commonService.get(TEquipmenttypeInfo.class, Long.valueOf(parentId.toString()));
		}
		Integer level = 0;
		if(parentId  == 0){
			level = 0 + 1;
		}else if(ec  != null){
			level = ec.getLevel() + 1;
		}
		equipmentTypeBean.getEquTypeObj().setLevel(level);
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		equipmentTypeBean.getEquTypeObj().setNodeid(nodeid);
		commonService.saveObject(equipmentTypeBean.getEquTypeObj());	
	}	

	/**
	 * 设备类型维护--查询单个通过设备ID，查看是否重复
	 */
	public List<Map<String, Object>> getEquTypeRepeat(String typecode){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //设备ID
				+ " t.equipmentType as equipmentType,"               // 设备编码即序列号
				+ " t.typecode as typecode)"                       //设备类别描述
		        + " FROM  TEquipmenttypeInfo t   "
		        + " WHERE t.typecode  = '"+typecode.trim()+"' "
		        + " and isdel=0"
		        + " and t.nodeid = '"+ nodeid +"'   ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 设备类型维护--删除
	 */
	public void delEquType(EquipmentTypeBean equipmentTypeBean){
		for(Map<String, Object> part:equipmentTypeBean.getSelectedEquipment()){
			Long id = new Long(part.get("id").toString());
			TEquipmenttypeInfo ec  = null;
			if(!StringUtils.isEmpty(part.get("id").toString())){
				ec=commonService.get(TEquipmenttypeInfo.class, Long.valueOf(part.get("id").toString()));
			}
//			ec.setId(id);
//			commonService.delete(ec);	 //改为逻辑删除
			ec.setIsdel("1"); //1为删除
			commonService.updateObject(ec);
		}
	}
	
	/**
	 * 设备类型维护--删除是判断是否已经使用，即在机床关联了
	 */
	public List<Map<String, Object>> getEquTypeInEquSize(String equTypeId){
		String hql =  "select new Map("
				+ " t.equId as equId)"     //设备ID
		        + " FROM TEquipmentInfo t , TEquipmenttypeInfo ty  "
		        + " WHERE t.equTypeId = ty.id  AND  ty.TEquipmenttypeInfo.id = '"+equTypeId+"'  ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 设备类型维护--修改
	 */
	public void updateEquType(EquipmentTypeBean equipmentTypeBean){
		for(Map<String, Object> part:equipmentTypeBean.getSelectedEquipment()){			
			TEquipmenttypeInfo ec  = null;
			if(!StringUtils.isEmpty(part.get("id").toString())){
				ec=commonService.get(TEquipmenttypeInfo.class, Long.valueOf(part.get("id").toString()));
			}
			ec.setEquipmentType((String)part.get("equipmentType"));
			ec.setTypecode((String)part.get("typecode"));
			ec.setNorm((String)part.get("norm"));
			ec.setCnc((String)part.get("cnc"));
			ec.setDescription((String)part.get("description"));			
			commonService.updateObject(ec);	
		}
	}
	
	/**
	 * 设备台帐维护--设备信息列表
	 */
	public List<Map<String, Object>> getMachineList(String equid){
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				
				+ " t.equId as equId,"     //设备ID
				+ " t.TNodes.nodeName as nodeName,"     //节点名称
				+ " ty.equipmentType as equipmentType,"            //设备类型名称 
				+ " t.equName as equName,"              //设备名称 
				+ " t.norm as norm,"  // 型号/牌号
				+ " t.outfacNo as outfacNo,"         //出厂编号
				+ " t.manufacturer as manufacturer,"        // 厂家
				+ " DATE_FORMAT(t.checktime,'%Y-%m-%d %T') as checktime,"    // 进/验日期
				+ " t.xAxis as xAxis,"             // x位置
				+ " t.yAxis as yAxis,"             // y位置
				+ " t.equSerialNo as equSerialNo,"             // 设备序列号
				+ " t.ipAddress as ipAddress,"             // IP地址
				+ " t.path as path,"             // 图片路径
				+ " t.equDesc as equDesc)"         //备注
		        + " FROM TEquipmentInfo t , TEquipmenttypeInfo ty  "
		        + " WHERE t.equTypeId = ty.id  ";
		if(equid!=null && !"".equals(equid)){
		        hql += " AND t.equId = '"+ equid +"'   ";
		} 
		if(!StringUtils.isEmpty(nodeid)){
		    hql += " AND ty.nodeid = '"+ nodeid +"'   ";
	    }
		List<Map<String, Object>> lst = dao.executeQuery(hql);	
		//查找下拉集合
		String sql =  "select new Map("
					+ " t.id as peopleId,"         //人员ID
					+ " t.name as peopleName"     //人员名称
				    + ")"         
				    + " FROM TMemberInfo t  ";
					if(nodeid!=null && !"".equals(nodeid)){
						sql += " where t.nodeid = '"+ nodeid +"'  ";
				     } 
		List<Map<String, Object>> maplst = dao.executeQuery(sql);	
		Map<String, Object> allmap = new HashMap<String, Object>();
		for(Map<String, Object> map : maplst){	
			if(map.get("peopleId")!=null && !map.get("peopleId").equals("")){
				allmap.put(map.get("peopleName").toString(), map.get("peopleId").toString());
			}
		}
		//查找操作人ID和名称
		for(Map<String, Object> map : lst){	
			map.put("allmap", allmap);
			//查找操作人ID和名称
			if(map.get("equId")!=null){
			String str = " select new Map( "
					+ " te.id as peopleId ,"
					+ " te.name as peopleName"
					+ " ) "
					+ " from TEquipmentMemberInfo t,TMemberInfo te  "
					+ " where t.memberId = te.id and t.equId='"+map.get("equId").toString()+"' ";
			List<Map<String, Object>> strlst = dao.executeQuery(str);
			   if(strlst.size()>0){
			    Map<String, Object> p = strlst.get(0);
				map.put("peopleId", p.get("peopleId").toString());
				map.put("peopleName", p.get("peopleName").toString());
			   }else{
				map.put("peopleId", "");
				map.put("peopleName", "");
			   }
			}else{
				map.put("peopleId", "");
				map.put("peopleName", "");
			}
		}
		return lst;
	}
	
	/**
	 * 设备台帐维护--左侧树的设备类别
	 */
	public List<Map<String, Object>> getEquTypeInAcc(){
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //设备类别ID
				+ " t.equipmentType as equipmentType,"    // 设备类别名称
				+ " t.description as description)"         //设备类别描述
		        + " FROM TEquipmenttypeInfo t WHERE t.level = 1 AND t.isdel = '0' ";
		if(!StringUtils.isEmpty(nodeid)){
		    hql += " AND t.nodeid = '"+ nodeid +"'   ";
	    }
		return dao.executeQuery(hql);
	}
	
	/**
	 * 设备台帐维护--左侧树的根据设备类别查找子设备类别
	 */
	public List<Map<String, Object>> getEquByTypeIdInAcc(String pEqutypeId){
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //设备类别ID
				+ " t.equipmentType as equipmentType,"    // 设备类别名称
				+ " t.description as description)"         //设备类别描述
		        + " FROM TEquipmenttypeInfo t  "
		        + " WHERE  t.TEquipmenttypeInfo.id = '"+ pEqutypeId +"' AND t.isdel = '0' ";
		if(!StringUtils.isEmpty(nodeid)){
		    hql += " AND t.nodeid = '"+ nodeid +"'   ";
	    }
		return dao.executeQuery(hql);
	}
	
	/**
	 * 设备台帐维护--左侧树的根据设备类别和子设备类别查找设备
	 */
	public List<Map<String, Object>> getEquByTypeInAcc(String typeId){
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("

				+ " t.equId as equId,"     //设备ID
				+ " t.TNodes.nodeName as nodeName,"     //节点名称
				+ " ty.equipmentType as equipmentType,"            //设备类型名称 
				+ " t.equName as equName,"              //设备名称 
				+ " t.norm as norm,"  // 型号/牌号
				+ " t.outfacNo as outfacNo,"         //出厂编号
				+ " t.manufacturer as manufacturer,"        // 厂家
				+ " DATE_FORMAT(t.checktime,'%Y-%m-%d %T') as checktime,"    // 进/验日期
				+ " t.xAxis as xAxis,"             // x位置
				+ " t.yAxis as yAxis,"             // y位置
				+ " t.equSerialNo as equSerialNo,"             // 设备序列号
				+ " t.ipAddress as ipAddress,"             // IP地址
				+ " t.path as path,"             // 图片路径
				+ " t.equDesc as equDesc)"         //备注
		        + " FROM TEquipmenttypeInfo ty , TEquipmentInfo t  "
		        + " WHERE ty.id = t.equTypeId   ";
		if(typeId != "0"){
			hql += "  AND  t.equTypeId = '"+ typeId +"'  ";
		}
		if(!StringUtils.isEmpty(nodeid)){
		    hql += " AND ty.nodeid = '"+ nodeid +"'   ";
	    }
		//查找下拉集合
		List<Map<String, Object>> lst = dao.executeQuery(hql);		
		String sql =  "select new Map("
					+ " t.id as peopleId,"         //人员ID
					+ " t.name as peopleName"     //人员名称
				    + ")"         
				    + " FROM TMemberInfo t  ";
		List<Map<String, Object>> maplst = dao.executeQuery(sql);	
		Map<String, Object> allmap = new HashMap<String, Object>();
		for(Map<String, Object> map : maplst){	
			if(map.get("peopleId")!=null && !map.get("peopleId").equals("")){
				allmap.put(map.get("peopleName").toString(), map.get("peopleId").toString());
			}
		}
		//查找操作人ID和名称
		for(Map<String, Object> map : lst){	
			map.put("allmap", allmap);
			//查找操作人ID和名称
			if(map.get("equId")!=null){
			String str = " select new Map( "
					+ " te.id as peopleId ,"
					+ " te.name as peopleName"
					+ " ) "
					+ " from TEquipmentMemberInfo t,TMemberInfo te  "
					+ " where t.memberId = te.id and t.equId='"+map.get("equId").toString()+"' ";
			List<Map<String, Object>> strlst = dao.executeQuery(str);
			   if(strlst.size()>0){ 
			    Map<String, Object> p = strlst.get(0);
				map.put("peopleId", p.get("peopleId").toString());
				map.put("peopleName", p.get("peopleName").toString());
			   }else{
				map.put("peopleId", "");
				map.put("peopleName", "");
			   }
			}else{
				map.put("peopleId", "");
				map.put("peopleName", "");
			}
		}
		return lst;
	}	
	
	/**
	 * 设备台帐维护--保存
	 */
	public void addEqu(String nodeid,EquipmentAccountingBean equipmentAccountingBean){
//		nodeid="8a8abc973f1dc2dc013f1dc3d7dc0001";
		TNodes tNodes  = null;
		if(!StringUtils.isEmpty(nodeid)){
			tNodes=commonService.get(TNodes.class, nodeid);
		}
		equipmentAccountingBean.getEquObj().setTNodes(tNodes);
		commonService.saveObject(equipmentAccountingBean.getEquObj());	
	}	
	
	/**
	 * 设备台帐维护--查询单个通过设备equSerialNo，查看是否重复
	 */
	public List<Map<String, Object>> getEquRepeat(String equSerialNo){
		String hql =  "select new Map("
				+ " t.equId as equId,"                           //设备ID
				+ " t.equName as equName,"                      // 设备名称
				+ " t.equDesc as equDesc)"                       //设备描述
		        + " FROM  TEquipmentInfo t   "
		        + " WHERE t.equSerialNo  = '"+equSerialNo+"'   ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 设备台帐维护--删除
	 */
	public void delEqu(String nodeid,EquipmentAccountingBean equipmentAccountingBean){
//		nodeid="8a8abc973f1dc2dc013f1dc3d7dc0001";
		for(Map<String, Object> part:equipmentAccountingBean.getSelectedEquipment()){
			TEquipmentInfo ec  = new TEquipmentInfo();
			Long id = new Long(part.get("equId").toString());
			ec.setEquId(id);
			ec.setEquSerialNo("");
			TNodes tNodes  = null;
			if(!StringUtils.isEmpty(nodeid)){
				tNodes=commonService.get(TNodes.class, nodeid);
			}
			ec.setTNodes(tNodes);
			commonService.delete(ec);	
		}
	}
	
	/**
	 * 设备台帐维护--通过设备ID得到设备
	 */
	public  TEquipmentInfo getTEquipmentInfoById(String equId){
		TEquipmentInfo ec  = null;
		if(!StringUtils.isEmpty(equId)){
			ec=commonService.get(TEquipmentInfo.class, Long.valueOf(equId));
		}
		return ec;
	}
	
	/**
	 * 设备台帐维护--通过操作人ID找到操作人名称
	 */
	public String getMenberNameById(String id){
		String hql ="select te.name as name from TMemberInfo te where te.id = '"+id+"'  ";
		List<String> telst  = dao.executeQuery(hql);
		String name="";
		if(telst.size()>0){
			name = telst.get(0);
		}
		return name;
	}
	
	/**
	 * 设备台帐维护--修改
	 */
	public void updateEqu(EquipmentAccountingBean equipmentAccountingBean){
		for(Map<String, Object> part:equipmentAccountingBean.getSelectedEquipment()){			
			TEquipmentInfo ec  = null;
			if(!StringUtils.isEmpty(part.get("equId").toString())){
				ec=commonService.get(TEquipmentInfo.class, Long.valueOf(part.get("equId").toString()));
			}
//			ec.setEquName((String)part.get("equName"));
			ec.setManufacturetype((String)part.get("manufacturetype"));
			ec.setOutfacNo((String)part.get("outfacNo"));
			ec.setManufacturer((String)part.get("manufacturer"));

			/*  //不同的标签取值的类型是不一样的。这个是P标签是Date类型
			Date checktime = (Date)part.get("checktime");
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
			String checktime1 = sdf.format(checktime);
			try {
				if(checktime1!=null && !"".equals(checktime1)){					
				checktime1 = "20" + checktime1.substring(2, checktime1.length());
				checktime = sdf.parse(checktime1);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			// js插件，String类型
			String checktime1 = (String)part.get("checktime");
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
			Date checktime = new Date();
			try {
				checktime = sdf.parse(checktime1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			ec.setChecktime(checktime);
			if(part.get("xAxis")!=null && !"".equals(part.get("xAxis"))){
			ec.setxAxis(Double.parseDouble(part.get("xAxis").toString()));
			}
			if(part.get("yAxis")!=null && !"".equals(part.get("yAxis"))){
			ec.setyAxis(Double.parseDouble(part.get("yAxis").toString()));
			}
			if(part.get("equSerialNo")!=null && !"".equals(part.get("equSerialNo"))){
			ec.setEquSerialNo(part.get("equSerialNo").toString());
			}
			if(part.get("ipAddress")!=null && !"".equals(part.get("ipAddress"))){
			ec.setIpAddress(part.get("ipAddress").toString());
			}
			if(part.get("path")!=null && !"".equals(part.get("path"))){
			ec.setPath(part.get("path").toString());
			}
			ec.setEquDesc((String)part.get("equDesc"));					
			commonService.updateObject(ec);	
			
		    Long equId = new Long(0);
		    Long peopleId = new Long(0);
			if(part.get("equId")!=null){
				equId = new Long(part.get("equId").toString());
			}
			if(part.get("peopleId")!=null){
				peopleId = new Long(part.get("peopleId").toString());	
			}
			String hql ="from TEquipmentMemberInfo tem where tem.equId = '"+equId+"'  ";
			List<TEquipmentMemberInfo> temlst  = dao.executeQuery(hql);
			if(temlst.size()==0){  // 为0数据库没有关联，是添加
				TEquipmentMemberInfo tem = new TEquipmentMemberInfo();//操作人员
				if(part.get("equId")!=null){
				tem.setEquId(equId);
				}
				if(part.get("peopleId")!=null){
				tem.setMemberId(peopleId);	
				}
				tem.setStatus(new Long(1));
				commonService.save(tem);
			}else{ //不是为0，就是修改
				TEquipmentMemberInfo tem = temlst.get(0);
				if(part.get("equId")!=null){
				tem.setEquId(equId);
				}
				if(part.get("peopleId")!=null){
				tem.setMemberId(peopleId);	
				}
				tem.setStatus(new Long(1));
				commonService.updateObject(tem);
			}
			
			
		}
	}
	
	/**
	 * 设备台帐维护--修改图片
	 */
	public void updateEquImage(String equId,List<String> listdocStorePath){			
			TEquipmentInfo ec  = null;
			if(!StringUtils.isEmpty(equId)){
				ec=commonService.get(TEquipmentInfo.class, Long.valueOf(equId));
				ec.setPath(ec.getEquSerialNo()+".png");
				commonService.updateObject(ec);	
			}
					
			
		
	}
	
	/**
	 * 设备成本维护--列表
	 */
	public List<Map<String, Object>> getEquipmentCostList(String roomId,String equTypeId,String equId,String code){
		//获取节点ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " tc.id as id,"      //设备台帐ID
				+ " t.equId as equId,"     //设备ID
				+ " t.equSerialNo as equSerialNo,"     //设备编码
//				+ " tc.symgMachineId as symgMachineId,"     //车间名称
				+ " ty.equipmentType as equipmentType,"     //设备类型
				+ " t.equName as equName,"                 //设备名称
				+ " tc.equPrice as equPrice,"     //设备采购全额
				+ " tc.periodDepreciation as periodDepreciation,"     //折旧年限
				+ " tc.processCost as processCost,"     //加工能耗因数
				+ " tc.prepareCost as prepareCost,"              //准备能耗因数 
				+ " tc.dryRunningCost as dryRunningCost,"              //空运行能耗因数
				+ " tc.idleCost as idleCost,"              //空闲能耗因数
				+ " tc.processAccessoriesCost as processAccessoriesCost,"     //加工时间辅料因数
				+ " tc.prepareAccessoriesCost as prepareAccessoriesCost,"     //准备时间辅料因数
				+ " tc.cuttingCost as cuttingCost)"         //切削能耗因数
		        + " FROM TEquipmentCostInfo tc, TEquipmentInfo t , TEquipmenttypeInfo ty  "
		        + " WHERE tc.equSerialNo = t.equSerialNo  AND t.equTypeId = ty.id   "
		        + " AND ty.isdel = '0' and t.equTypeId !='-999' ";
//		if(roomId!=null && !"".equals(roomId)){
//		        hql += " AND t.equId = '"+ roomId +"'   ";
//		}  
		if(equTypeId!=null && !"".equals(equTypeId)){
	        hql += " AND ty.id = '"+ equTypeId +"'   ";
     	} 
		if(equId!=null && !"".equals(equId)){
	        hql += " AND t.equId = '"+ equId +"'   ";
    	} 
		if(code!=null && !"".equals(code)){
	        hql += " AND t.equSerialNo = '"+ code +"'   ";
	    } 
		if(nodeid!=null && !"".equals(nodeid)){
	        hql += " AND t.TNodes.TNodes.nodeId = '"+ nodeid +"'   ";
    	} 
		return dao.executeQuery(hql);
	 } 

	/**
	 * 设备成本维护--得到车间集合
	 */
	public List<Map<String, Object>> getRoomMap(){
		return null;	
	}
	
	/**
	 * 设备成本维护--得到设备类型集合
	 */
	public List<Map<String, Object>> getEquTypeMap(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //设备类别ID
				+ " t.equipmentType as equipmentType,"    // 设备类别名称
				+ " t.description as description)"         //设备类别描述
		        + " FROM TEquipmenttypeInfo t ";
				if(nodeid!=null && !"".equals(nodeid)){
			        hql += " WHERE t.nodeid = '"+ nodeid +"'   ";
		    	}
		return dao.executeQuery(hql);		
	}
	
	/**
	 * 设备成本维护--得到设备集合
	 */
	public List<Map<String, Object>> getEquMap(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.equId as equId,"                   //设备ID
				+ " t.equSerialNo as equSerialNo,"
				+ " t.equName as equName,"               // 设备名称
				+ " t.equDesc as equDesc)"               //设备类别描述
		        + " FROM TEquipmentInfo t ";
				if(nodeid!=null && !"".equals(nodeid)){
			        hql += " WHERE t.TNodes.TNodes.nodeId = '"+ nodeid +"'   ";
		    	}
		return dao.executeQuery(hql);	
	}
	
	/**
	 * 设备成本维护--得到设备编码序列号集合
	 */
	public List<Map<String, Object>> getEquCodeMap(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.equId as equId,"                           //设备ID
				+ " t.equSerialNo as equSerialNo,"               // 设备编码即序列号
				+ " t.equDesc as equDesc)"                       //设备类别描述
		        + " FROM TEquipmentInfo t  ";
				if(nodeid!=null && !"".equals(nodeid)){
			        hql += " WHERE t.TNodes.TNodes.nodeId = '"+ nodeid +"'   ";
		    	}
		return dao.executeQuery(hql);
	}
	
	/**
	 * 设备成本维护--查询单个  ?????????
	 */
	public List<Map<String, Object>> getEquCost(){
		String hql =  "select new Map("
				+ " t.id as id,"                           //设备类别ID
				+ " t.equipmentType as equipmentType,"    // 设备类别名称
				+ " t.description as description)"         //设备类别描述
		        + " FROM TEquipmenttypeInfo t  ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 设备成本维护--保存
	 */
	public void addEquCost(EquipmentCostBean equipmentCostBean){
		commonService.saveObject(equipmentCostBean.getEquCostObj());	
	}	
	
	/**
	 * 设备成本维护--查询单个通过设备ID，查看是否重复
	 */
	public List<Map<String, Object>> getEquCostRepeat(String equSerialNo){
		String hql =  "select new Map("
				+ " t.equId as equId,"                           //设备ID
				+ " t.equSerialNo as equSerialNo,"               // 设备编码即序列号
				+ " t.equDesc as equDesc)"                       //设备类别描述
		        + " FROM  TEquipmentInfo t , TEquipmentCostInfo tc  "
		        + " WHERE t.equSerialNo = tc.equSerialNo AND t.equSerialNo  = '"+equSerialNo+"'   ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * 设备成本维护--删除成本
	 */
	public void delEquCost(EquipmentCostBean equipmentCostBean){
		for(Map<String, Object> part:equipmentCostBean.getSelectedEquipment()){
			TEquipmentCostInfo ec  = new TEquipmentCostInfo();
			Long id = new Long(part.get("id").toString());
			ec.setId(id);
			commonService.delete(ec);	
		}
	}
	
	/**
	 * 设备成本维护--修改成本
	 */
	public void updateEquCost(EquipmentCostBean equipmentCostBean){
		for(Map<String, Object> part:equipmentCostBean.getSelectedEquipment()){			
			TEquipmentCostInfo ec  = null;
			if(!StringUtils.isEmpty(part.get("id").toString())){
				ec=commonService.get(TEquipmentCostInfo.class, Long.valueOf(part.get("id").toString()));
			}
			ec.setEquPrice(Double.valueOf(part.get("equPrice").toString()));
			ec.setPeriodDepreciation(Double.valueOf(part.get("periodDepreciation").toString()));
			ec.setProcessCost(Double.valueOf(part.get("processCost").toString()));
			ec.setPrepareCost(Double.valueOf(part.get("prepareCost").toString()));
			ec.setDryRunningCost(Double.valueOf(part.get("dryRunningCost").toString()));
			ec.setCuttingCost(Double.valueOf(part.get("cuttingCost").toString()));	
			ec.setIdleCost(Double.valueOf(part.get("idleCost").toString()));
			ec.setProcessAccessoriesCost(Double.valueOf(part.get("processAccessoriesCost").toString()));
			ec.setPrepareAccessoriesCost(Double.valueOf(part.get("prepareAccessoriesCost").toString()));			
			commonService.updateObject(ec);	
		}
	}
	public TUserEquCurStatus getTUserEquCurStatus(String equSerialNo){
		return commonService.get(TUserEquCurStatus.class,equSerialNo);
	}
	/**
	 * 获取设备位置数据
	 * @return
	 */
	public String getEquData(String nodeIds,int type){
		String hql="select new Map("
				+ " a.equSerialNo as equSerialNo,"
				+ " a.xAxis as xAxis,"
				+ " a.yAxis as yAxis,"
				+ " a.path as path)"
				+ " from TEquipmentInfo a"
				+ " where a.TNodes.nodeId in("+nodeIds+")";
			if(type==1){
				hql+= " and (a.xAxis is not null or a.yAxis is not null)";
			}else if(type==2){
				hql+= " and (a.xAxis is null or a.yAxis is null)";
			}	
			
		List<Map<String,Object>> rs=dao.executeQuery(hql);
		JsonResponseResult json=new JsonResponseResult();
		json.setContent(rs);
		System.out.println(json.toJsonString());
		return json.toJsonString();
	}
	/**
	 * 保存机床位置数据
	 * @param equData
	 * @return
	 */
	public String saveEquData(String equData){
		try {
			String[] equlist=equData.split(",");
			for(int i=0;i<equlist.length;i++){
				String[] equ=equlist[i].split(":");
				String hql=" from TEquipmentInfo where equSerialNo='"+equ[0]+"'";
				List<TEquipmentInfo> equl=dao.executeQuery(hql);
				TEquipmentInfo eque=equl.get(0);
				eque.setxAxis(Double.parseDouble(equ[1].toString()));
				eque.setyAxis(Double.parseDouble(equ[2].toString()));
				dao.update(TEquipmentInfo.class, eque);
			}
			return "修改成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "修改失败";
	}
}

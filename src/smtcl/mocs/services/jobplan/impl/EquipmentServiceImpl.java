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
 * �豸����SERVICE�ӿ�ʵ����
 * @���ߣ�yyh
 * @����ʱ�䣺2013-08-06
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
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
	 * �豸����ά��--�豸������Ϣ�б�
	 */
	public List<Map<String, Object>> getEquipmentInfoAList(String equipmentType){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " ty.id as id,"      //�豸����ID
				+ " ty.equipmentType as equipmentType,"     //�豸�������� 
				+ " ty.typecode as typecode,"    //�������
				+ " ty.norm as norm,"        //�������ͺ�
				+ " ty.cnc as cnc,"                   //����ϵͳ
				+ " ty.description as description)"                 //�豸��������
		        + " FROM TEquipmenttypeInfo ty  "
		        + " WHERE ty.level = 1 AND ty.isdel = '0'  "
		        + " and ty.nodeid='"+nodeid+"'";
		if(equipmentType!=null && !"".equals(equipmentType)){
		        hql += " AND ty.id = '"+ equipmentType +"'   ";
		}      
		return dao.executeQuery(hql);
	}
	
	/**
	 * �豸����ά��--��������豸���
	 */
	public List<Map<String, Object>> getEquType(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //�豸���ID
				+ " t.equipmentType as equipmentType,"    // �豸�������
				+ " t.description as description)"         //�豸�������
		        + " FROM TEquipmenttypeInfo t WHERE t.level = 1 AND t.isdel = '0' "
		        + " and t.nodeid='"+nodeid+"'";
		
		return dao.executeQuery(hql);
	}
	
	/**
	 * �豸����ά��--��������豸���--���¼�
	 */
	public List<Map<String, Object>> getEquByTypeId(String pEqutypeId){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //�豸���ID
				+ " t.equipmentType as equipmentType,"    // �豸�������
				+ " t.description as description)"         //�豸�������
		        + " FROM TEquipmenttypeInfo t  "
		        + " WHERE  t.TEquipmenttypeInfo.id = '"+ pEqutypeId +"' AND t.isdel = '0' "
		        + " AND t.nodeid = '"+ nodeid +"'   ";
		return dao.executeQuery(hql);
	}
	/**
	 * �豸����ά��--ͨ������ID�õ�����
	 */
	public TEquipmenttypeInfo getgetEquTypeIdById(String typeId){
		TEquipmenttypeInfo ti =null;
		  if(!StringUtil.isEmpty(typeId)){
			  ti=commonService.get(TEquipmenttypeInfo.class, Long.valueOf(typeId));
		  }
		  return ti;
	}
	
	
	/**
	 * �豸����ά��--ͨ���������Ƶõ�����ID
	 */
	public String getEquTypeIdByName(String name){
		String hql =  "select new Map("
				+ " t.id as id,"                           //�豸���ID
				+ " t.equipmentType as equipmentType,"    // �豸�������
				+ " t.description as description)"         //�豸�������
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
	 * �豸����ά��--���������õ��Ҳ��б�
	 */
	public List<Map<String, Object>> getEquTypeByClick(String pEqutypeId){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " ty.id as id,"      //�豸����ID
				+ " ty.equipmentType as equipmentType,"     //�豸�������� 
				+ " ty.typecode as typecode,"    //�������
				+ " ty.norm as norm,"        //�������ͺ�
				+ " ty.cnc as cnc,"                   //����ϵͳ
				+ " ty.description as description,"
				+ " ty.erpResouceCode as erpResouceCode,"
				+ " ty.erpResouceDesc as erpResouceDesc)"                 //�豸��������
		        + " FROM TEquipmenttypeInfo ty  "
		        + " WHERE ty.TEquipmenttypeInfo.id = '"+pEqutypeId+"' AND ty.isdel = '0' "
		        + " and ty.nodeid = '"+ nodeid +"'   ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * �豸����ά��--�Ҳ��б��ֶθ��豸��������
	 */
	public List<Map<String, Object>> getParentNameById(String Id){
		String hql =  "select new Map("
				+ " ty.id as id,"      //�豸����ID
				+ " ty.equipmentType as equipmentType,"     //�豸�������� 
				+ " ty.description as description)"                 //�豸��������
		        + " FROM TEquipmenttypeInfo ty  "
		        + " WHERE ty.id = '"+Id+"'  ";        
		return dao.executeQuery(hql);
	}
	
	/**
	 * �豸����ά��--���ʱ������
	 */
	public List<Map<String, Object>> getParentIdMap(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //�豸���ID
				+ " t.equipmentType as equipmentType,"    // �豸�������
				+ " t.description as description)"         //�豸�������
		        + " FROM TEquipmenttypeInfo t  "
		        + " where t.isdel=0 "
		        + " and t.nodeid = '"+ nodeid +"'   or t.id='-999'";
		return dao.executeQuery(hql);	
	}
	
	/**
	 * �豸����ά��--����
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
	 * �豸����ά��--��ѯ����ͨ���豸ID���鿴�Ƿ��ظ�
	 */
	public List<Map<String, Object>> getEquTypeRepeat(String typecode){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //�豸ID
				+ " t.equipmentType as equipmentType,"               // �豸���뼴���к�
				+ " t.typecode as typecode)"                       //�豸�������
		        + " FROM  TEquipmenttypeInfo t   "
		        + " WHERE t.typecode  = '"+typecode.trim()+"' "
		        + " and isdel=0"
		        + " and t.nodeid = '"+ nodeid +"'   ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * �豸����ά��--ɾ��
	 */
	public void delEquType(EquipmentTypeBean equipmentTypeBean){
		for(Map<String, Object> part:equipmentTypeBean.getSelectedEquipment()){
			Long id = new Long(part.get("id").toString());
			TEquipmenttypeInfo ec  = null;
			if(!StringUtils.isEmpty(part.get("id").toString())){
				ec=commonService.get(TEquipmenttypeInfo.class, Long.valueOf(part.get("id").toString()));
			}
//			ec.setId(id);
//			commonService.delete(ec);	 //��Ϊ�߼�ɾ��
			ec.setIsdel("1"); //1Ϊɾ��
			commonService.updateObject(ec);
		}
	}
	
	/**
	 * �豸����ά��--ɾ�����ж��Ƿ��Ѿ�ʹ�ã����ڻ���������
	 */
	public List<Map<String, Object>> getEquTypeInEquSize(String equTypeId){
		String hql =  "select new Map("
				+ " t.equId as equId)"     //�豸ID
		        + " FROM TEquipmentInfo t , TEquipmenttypeInfo ty  "
		        + " WHERE t.equTypeId = ty.id  AND  ty.TEquipmenttypeInfo.id = '"+equTypeId+"'  ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * �豸����ά��--�޸�
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
	 * �豸̨��ά��--�豸��Ϣ�б�
	 */
	public List<Map<String, Object>> getMachineList(String equid){
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				
				+ " t.equId as equId,"     //�豸ID
				+ " t.TNodes.nodeName as nodeName,"     //�ڵ�����
				+ " ty.equipmentType as equipmentType,"            //�豸�������� 
				+ " t.equName as equName,"              //�豸���� 
				+ " t.norm as norm,"  // �ͺ�/�ƺ�
				+ " t.outfacNo as outfacNo,"         //�������
				+ " t.manufacturer as manufacturer,"        // ����
				+ " DATE_FORMAT(t.checktime,'%Y-%m-%d %T') as checktime,"    // ��/������
				+ " t.xAxis as xAxis,"             // xλ��
				+ " t.yAxis as yAxis,"             // yλ��
				+ " t.equSerialNo as equSerialNo,"             // �豸���к�
				+ " t.ipAddress as ipAddress,"             // IP��ַ
				+ " t.path as path,"             // ͼƬ·��
				+ " t.equDesc as equDesc)"         //��ע
		        + " FROM TEquipmentInfo t , TEquipmenttypeInfo ty  "
		        + " WHERE t.equTypeId = ty.id  ";
		if(equid!=null && !"".equals(equid)){
		        hql += " AND t.equId = '"+ equid +"'   ";
		} 
		if(!StringUtils.isEmpty(nodeid)){
		    hql += " AND ty.nodeid = '"+ nodeid +"'   ";
	    }
		List<Map<String, Object>> lst = dao.executeQuery(hql);	
		//������������
		String sql =  "select new Map("
					+ " t.id as peopleId,"         //��ԱID
					+ " t.name as peopleName"     //��Ա����
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
		//���Ҳ�����ID������
		for(Map<String, Object> map : lst){	
			map.put("allmap", allmap);
			//���Ҳ�����ID������
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
	 * �豸̨��ά��--��������豸���
	 */
	public List<Map<String, Object>> getEquTypeInAcc(){
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //�豸���ID
				+ " t.equipmentType as equipmentType,"    // �豸�������
				+ " t.description as description)"         //�豸�������
		        + " FROM TEquipmenttypeInfo t WHERE t.level = 1 AND t.isdel = '0' ";
		if(!StringUtils.isEmpty(nodeid)){
		    hql += " AND t.nodeid = '"+ nodeid +"'   ";
	    }
		return dao.executeQuery(hql);
	}
	
	/**
	 * �豸̨��ά��--������ĸ����豸���������豸���
	 */
	public List<Map<String, Object>> getEquByTypeIdInAcc(String pEqutypeId){
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //�豸���ID
				+ " t.equipmentType as equipmentType,"    // �豸�������
				+ " t.description as description)"         //�豸�������
		        + " FROM TEquipmenttypeInfo t  "
		        + " WHERE  t.TEquipmenttypeInfo.id = '"+ pEqutypeId +"' AND t.isdel = '0' ";
		if(!StringUtils.isEmpty(nodeid)){
		    hql += " AND t.nodeid = '"+ nodeid +"'   ";
	    }
		return dao.executeQuery(hql);
	}
	
	/**
	 * �豸̨��ά��--������ĸ����豸�������豸�������豸
	 */
	public List<Map<String, Object>> getEquByTypeInAcc(String typeId){
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("

				+ " t.equId as equId,"     //�豸ID
				+ " t.TNodes.nodeName as nodeName,"     //�ڵ�����
				+ " ty.equipmentType as equipmentType,"            //�豸�������� 
				+ " t.equName as equName,"              //�豸���� 
				+ " t.norm as norm,"  // �ͺ�/�ƺ�
				+ " t.outfacNo as outfacNo,"         //�������
				+ " t.manufacturer as manufacturer,"        // ����
				+ " DATE_FORMAT(t.checktime,'%Y-%m-%d %T') as checktime,"    // ��/������
				+ " t.xAxis as xAxis,"             // xλ��
				+ " t.yAxis as yAxis,"             // yλ��
				+ " t.equSerialNo as equSerialNo,"             // �豸���к�
				+ " t.ipAddress as ipAddress,"             // IP��ַ
				+ " t.path as path,"             // ͼƬ·��
				+ " t.equDesc as equDesc)"         //��ע
		        + " FROM TEquipmenttypeInfo ty , TEquipmentInfo t  "
		        + " WHERE ty.id = t.equTypeId   ";
		if(typeId != "0"){
			hql += "  AND  t.equTypeId = '"+ typeId +"'  ";
		}
		if(!StringUtils.isEmpty(nodeid)){
		    hql += " AND ty.nodeid = '"+ nodeid +"'   ";
	    }
		//������������
		List<Map<String, Object>> lst = dao.executeQuery(hql);		
		String sql =  "select new Map("
					+ " t.id as peopleId,"         //��ԱID
					+ " t.name as peopleName"     //��Ա����
				    + ")"         
				    + " FROM TMemberInfo t  ";
		List<Map<String, Object>> maplst = dao.executeQuery(sql);	
		Map<String, Object> allmap = new HashMap<String, Object>();
		for(Map<String, Object> map : maplst){	
			if(map.get("peopleId")!=null && !map.get("peopleId").equals("")){
				allmap.put(map.get("peopleName").toString(), map.get("peopleId").toString());
			}
		}
		//���Ҳ�����ID������
		for(Map<String, Object> map : lst){	
			map.put("allmap", allmap);
			//���Ҳ�����ID������
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
	 * �豸̨��ά��--����
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
	 * �豸̨��ά��--��ѯ����ͨ���豸equSerialNo���鿴�Ƿ��ظ�
	 */
	public List<Map<String, Object>> getEquRepeat(String equSerialNo){
		String hql =  "select new Map("
				+ " t.equId as equId,"                           //�豸ID
				+ " t.equName as equName,"                      // �豸����
				+ " t.equDesc as equDesc)"                       //�豸����
		        + " FROM  TEquipmentInfo t   "
		        + " WHERE t.equSerialNo  = '"+equSerialNo+"'   ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * �豸̨��ά��--ɾ��
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
	 * �豸̨��ά��--ͨ���豸ID�õ��豸
	 */
	public  TEquipmentInfo getTEquipmentInfoById(String equId){
		TEquipmentInfo ec  = null;
		if(!StringUtils.isEmpty(equId)){
			ec=commonService.get(TEquipmentInfo.class, Long.valueOf(equId));
		}
		return ec;
	}
	
	/**
	 * �豸̨��ά��--ͨ��������ID�ҵ�����������
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
	 * �豸̨��ά��--�޸�
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

			/*  //��ͬ�ı�ǩȡֵ�������ǲ�һ���ġ������P��ǩ��Date����
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
			// js�����String����
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
			if(temlst.size()==0){  // Ϊ0���ݿ�û�й����������
				TEquipmentMemberInfo tem = new TEquipmentMemberInfo();//������Ա
				if(part.get("equId")!=null){
				tem.setEquId(equId);
				}
				if(part.get("peopleId")!=null){
				tem.setMemberId(peopleId);	
				}
				tem.setStatus(new Long(1));
				commonService.save(tem);
			}else{ //����Ϊ0�������޸�
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
	 * �豸̨��ά��--�޸�ͼƬ
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
	 * �豸�ɱ�ά��--�б�
	 */
	public List<Map<String, Object>> getEquipmentCostList(String roomId,String equTypeId,String equId,String code){
		//��ȡ�ڵ�ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " tc.id as id,"      //�豸̨��ID
				+ " t.equId as equId,"     //�豸ID
				+ " t.equSerialNo as equSerialNo,"     //�豸����
//				+ " tc.symgMachineId as symgMachineId,"     //��������
				+ " ty.equipmentType as equipmentType,"     //�豸����
				+ " t.equName as equName,"                 //�豸����
				+ " tc.equPrice as equPrice,"     //�豸�ɹ�ȫ��
				+ " tc.periodDepreciation as periodDepreciation,"     //�۾�����
				+ " tc.processCost as processCost,"     //�ӹ��ܺ�����
				+ " tc.prepareCost as prepareCost,"              //׼���ܺ����� 
				+ " tc.dryRunningCost as dryRunningCost,"              //�������ܺ�����
				+ " tc.idleCost as idleCost,"              //�����ܺ�����
				+ " tc.processAccessoriesCost as processAccessoriesCost,"     //�ӹ�ʱ�丨������
				+ " tc.prepareAccessoriesCost as prepareAccessoriesCost,"     //׼��ʱ�丨������
				+ " tc.cuttingCost as cuttingCost)"         //�����ܺ�����
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
	 * �豸�ɱ�ά��--�õ����伯��
	 */
	public List<Map<String, Object>> getRoomMap(){
		return null;	
	}
	
	/**
	 * �豸�ɱ�ά��--�õ��豸���ͼ���
	 */
	public List<Map<String, Object>> getEquTypeMap(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.id as id,"                           //�豸���ID
				+ " t.equipmentType as equipmentType,"    // �豸�������
				+ " t.description as description)"         //�豸�������
		        + " FROM TEquipmenttypeInfo t ";
				if(nodeid!=null && !"".equals(nodeid)){
			        hql += " WHERE t.nodeid = '"+ nodeid +"'   ";
		    	}
		return dao.executeQuery(hql);		
	}
	
	/**
	 * �豸�ɱ�ά��--�õ��豸����
	 */
	public List<Map<String, Object>> getEquMap(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.equId as equId,"                   //�豸ID
				+ " t.equSerialNo as equSerialNo,"
				+ " t.equName as equName,"               // �豸����
				+ " t.equDesc as equDesc)"               //�豸�������
		        + " FROM TEquipmentInfo t ";
				if(nodeid!=null && !"".equals(nodeid)){
			        hql += " WHERE t.TNodes.TNodes.nodeId = '"+ nodeid +"'   ";
		    	}
		return dao.executeQuery(hql);	
	}
	
	/**
	 * �豸�ɱ�ά��--�õ��豸�������кż���
	 */
	public List<Map<String, Object>> getEquCodeMap(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid = (String)session.getAttribute("nodeid2");
		String hql =  "select new Map("
				+ " t.equId as equId,"                           //�豸ID
				+ " t.equSerialNo as equSerialNo,"               // �豸���뼴���к�
				+ " t.equDesc as equDesc)"                       //�豸�������
		        + " FROM TEquipmentInfo t  ";
				if(nodeid!=null && !"".equals(nodeid)){
			        hql += " WHERE t.TNodes.TNodes.nodeId = '"+ nodeid +"'   ";
		    	}
		return dao.executeQuery(hql);
	}
	
	/**
	 * �豸�ɱ�ά��--��ѯ����  ?????????
	 */
	public List<Map<String, Object>> getEquCost(){
		String hql =  "select new Map("
				+ " t.id as id,"                           //�豸���ID
				+ " t.equipmentType as equipmentType,"    // �豸�������
				+ " t.description as description)"         //�豸�������
		        + " FROM TEquipmenttypeInfo t  ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * �豸�ɱ�ά��--����
	 */
	public void addEquCost(EquipmentCostBean equipmentCostBean){
		commonService.saveObject(equipmentCostBean.getEquCostObj());	
	}	
	
	/**
	 * �豸�ɱ�ά��--��ѯ����ͨ���豸ID���鿴�Ƿ��ظ�
	 */
	public List<Map<String, Object>> getEquCostRepeat(String equSerialNo){
		String hql =  "select new Map("
				+ " t.equId as equId,"                           //�豸ID
				+ " t.equSerialNo as equSerialNo,"               // �豸���뼴���к�
				+ " t.equDesc as equDesc)"                       //�豸�������
		        + " FROM  TEquipmentInfo t , TEquipmentCostInfo tc  "
		        + " WHERE t.equSerialNo = tc.equSerialNo AND t.equSerialNo  = '"+equSerialNo+"'   ";
		return dao.executeQuery(hql);
	}
	
	/**
	 * �豸�ɱ�ά��--ɾ���ɱ�
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
	 * �豸�ɱ�ά��--�޸ĳɱ�
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
	 * ��ȡ�豸λ������
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
	 * �������λ������
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
			return "�޸ĳɹ�";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "�޸�ʧ��";
	}
}

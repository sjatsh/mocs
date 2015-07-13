package smtcl.mocs.beans.device;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.services.device.ICostManageService;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;

/**
 * �����ſ�
 * @����ʱ�� 2013-06-06
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name="factoryProfileBean")
@SessionScoped
public class FactoryProfileBean implements Serializable{
	private String loadmenu;
	/**
	 * �豸ҵ���߼�
	 */
	private	IDeviceService deviceService=(IDeviceService)ServiceFactory.getBean("deviceService");
	/**
	 * Ȩ�޽ӿ�ʵ��
	*/
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");//��ȡע��;
	private IAuthorizeService authorizeService = (IAuthorizeService) ServiceFactory.getBean("authorizeService");
	    
	/** 
	 * x������
	 */
	private String xz; 
	/**
	 * y������
	 */
	private String yz;
	/**
	 *  �豸����
	 */
	private String equipmentName;
	/**
	 * �豸ͼƬ
	 */
	private String image;
	/**
	 * �ƻ������
	 */
	private String ppcr;
	/**
	 * ���ռӹ���
	 */
	private String nodp;
	/**
	 * �豸Ч��
	 */
	private String equday;
	
	/**
	 * ��Դ�ɱ�
	 */
	private String rcr;
	
	/**
	 * ������Ϣ
	 */
	private List workshop;
	/**
	 * ��ǰѡ�еĳ���
	 */
	private String selectTNodesId;
	/**
	 * ��ά����ip��ַ
	 */
	private String ipAddress;
	/**
	 * ��ǰѡ�еĽڵ�
	 */
	private String selectName;
	/**
	 * ��ǰ�����Ҫ��ѯ���ݵ��豸���к�
	 */
	private String sef;//SeveralEquInformation ��̨�豸����Ϣ ��ѯ����
	
	/**
	 * ��ά����ҳ��������Ϣ
	 */
	private String equInformation;
	
	/**
	 * ��ʽ���ַ���
	 */
	DecimalFormat df=new DecimalFormat("0"); 
	
	/**
	 * ��ȡservice
	 */
	private ICostManageService costManageService=(ICostManageService)ServiceFactory.getBean("costManageService");

	/**
	 * ����URL
	 */
	private String syurl;
	/**
	 * �Ϻ�URL
	 */
	private String shurl;
	/**
	 * Ĭ����ʾ2���е��ĸ��ڵ�
	 */
	private String defnodeid;
	/**
	 * ��ά���汳��ͼ
	 */
	private String pathThree;
	
	private Boolean ssoControl;
	/**
	 *�û�userid 
	 */
	private String userId;
	/**
	 * ���ڵ�parentId
	 */
	private String parentId;
	private String bgpath;
	
	private List<Map<String,Object>> equInventory;
	
	/**
	 * ���췽��
	 */
	public FactoryProfileBean(){
		//��ȡ�û���Ȩ����֤ 
		 TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		 workshop=organizationService.returnWorkshop(user.getUserId(),Constants.SBZL_PAGE_ID);
		 //��ʼ������
	   	 ppcr="totle:0";
	   	 nodp="totle:0";
	   	 equday="totle:0";
	   	 rcr="0";
	   	 
	  	//�ѽڵ�ID����session
	     HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();		
      	 Map<String, String> mmm = StringUtils.getUrl(request.getServletContext());
//      defnodeid = (String)mmm.get("defaultNode");
      	
      	 String defaultNode=(String)mmm.get("defaultNode");
        
		 HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
      	 
	   	for (int i=0;i<workshop.size();i++) {
	   		TNodes t=(TNodes)workshop.get(i);
	   		String name = t.getNodeName();	
	   		if(name!=null && name.equals(defaultNode)){  //�õ�Ĭ�ϵĽڵ�
	   		selectName=t.getNodeName();
	   		bgpath=t.getPath();//��ȡ�ڵ㱳��ͼ
	   		String nodeid = t.getNodeId();
	   		session.setAttribute("nodeid", nodeid);
	   		break;
	   		}
	   	}
	    	 
   	if(StringUtils.listIsNull(workshop)){
   		 //TNodes t=(TNodes)workshop.get(0);
   		 String nodeid=(String)session.getAttribute("nodeid"); //�滻��һ��
   		 if(!StringUtils.isEmpty(nodeid))
   		 {
	        	 String nodeids=deviceService.getNodeAllId(nodeid);
	        	 ppcr=getpper(nodeid);
	        	 nodp=getDayNumber(nodeid);
	        	 equday=getEquEfficiency(nodeids);
	        	// rcr=df.format(Double.parseDouble(costManageService.getRCR(nodeid))*100);
	        	 selectTNodesId=nodeid;
	    		if(nodeids.length()>0){
	        	 List<Map<String, Object>> list=deviceService.getNodesAllEquNameStruts(nodeids);
	        	 if(null!=list&&list.size()>0){
	        		 setDate(list);
	        	 }
	    		}
   		}
   	 }
   	 equInventory=deviceService.getEquInventoryInfo(selectTNodesId);
   	 userId = user.getUserId(); //����ǰ��
   	 parentId = deviceService.getParentIdByNodeid(selectTNodesId);
	}
	/**
	 * �����ڵ��¼�(�µĽڵ���ʽ jquery)
	 * @param t
	 */
	public void getData1(){
	 
		System.err.println(this.selectTNodesId);
		if(!StringUtils.isEmpty(this.selectTNodesId))
		{
			 String nodeid=this.selectTNodesId;
		     equipmentName="";
		   	 xz="";
		   	 yz="";
		   	 image="";
		   	 String nodeids=deviceService.getNodeAllId(nodeid);
		   	 ppcr=getpper(nodeid);
		   	 nodp=getDayNumber(nodeid);
		   	 equday=getEquEfficiency(nodeids);
		   	 //rcr=df.format(Double.parseDouble(costManageService.getRCR(nodeid))*100);
		   	 selectTNodesId=nodeid;
		   	parentId = deviceService.getParentIdByNodeid(selectTNodesId);
			if(!StringUtils.isEmpty(nodeids)){
		   	 List<Map<String, Object>> list=deviceService.getNodesAllEquNameStruts(nodeids);
		   	 if(null!=list&&list.size()>0){
		   		 setDate(list);
		       }
			}
			List<TNodes> tnode=organizationService.getTNodesById(selectTNodesId);
			if(null!=tnode&&tnode.size()>0){
				TNodes td=tnode.get(0);
				bgpath=td.getPath();
			}
			 equInventory=deviceService.getEquInventoryInfo(selectTNodesId);
				//�ѽڵ�ID����session
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("nodeid", nodeid);
			
			HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			StringUtils.delCookie(request,response, "equSerialNo");
		}
	}
	
	/**
	 * �����ڵ��¼�
	 * @param t
	 */
	public void getData(TNodes t){
		String nodeid=t.getNodeId();
		 equipmentName="";
    	 xz="";
    	 yz="";
    	 image="";
    	 String nodeids=deviceService.getNodeAllId(nodeid);
    	 ppcr=getpper(nodeid);
    	 nodp=getDayNumber(nodeid);
    	 equday=getEquEfficiency(nodeids);
    	 //rcr=df.format(Double.parseDouble(costManageService.getRCR(nodeid))*100);
    	 selectTNodesId=nodeid;
    	 selectName=t.getNodeName();
		if(nodeids.length()>0){
    	 List<Map<String, Object>> list=deviceService.getNodesAllEquNameStruts(nodeids);
    	 if(null!=list&&list.size()>0){
    		 setDate(list);
    	 }
		}
		 equInventory=deviceService.getEquInventoryInfo(selectTNodesId);
		//�ѽڵ�ID����session
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("nodeid", nodeid);
	}
	
	/**
	 * ��ʱˢ�µ��÷���
	 * @param nodeid
	 */
	public void refreshData(){
		 System.out.println("----------------------------------��ʱˢ�·�������----------------------------------");
		 if(null!=selectTNodesId){
			 equipmentName="";
	    	 xz="";
	    	 yz="";
	    	 image="";
	    	 ppcr=getpper(selectTNodesId);
	    	 nodp=getDayNumber(selectTNodesId);
	    	 rcr="0";
	    	 //rcr=df.format(Double.parseDouble(costManageService.getRCR(selectTNodesId))*100);
			 String nodeids=deviceService.getNodeAllId(selectTNodesId);
			 List<Map<String, Object>> list=deviceService.getNodesAllEquNameStruts(nodeids);
			 equday=getEquEfficiency(nodeids);
			 if(null!=list&&list.size()>0){
				 setDate(list);
	    	 }
		 }
		System.out.println("----------------------------------��ʱˢ�·������ý���----------------------------------");
	}
	/**
	 * ��ʱˢ�µ��÷���
	 * @param nodeid
	 */
	public void refreshDataTwo(){
		 System.out.println("-----------------��ʱˢ�·�������-----------------");
		 if(null!=selectTNodesId){
	    	 ppcr=getpper(selectTNodesId);
	    	 nodp=getDayNumber(selectTNodesId);
	    	 rcr="0";
	    	 //rcr=df.format(Double.parseDouble(costManageService.getRCR(selectTNodesId))*100);
			 String nodeids=deviceService.getNodeAllId(selectTNodesId);
			 equday=getEquEfficiency(nodeids);
			 equInventory=deviceService.getEquInventoryInfo(selectTNodesId);
			
		 }	
	}
	
	/**
	 * ˫�������¼�
	 */
	public void getSeveralEquInformation(){
		Date date=new Date();
		String[] equ=sef.split(",");
		System.out.println("-----------˫�������¼�����sef:"+sef+"----------------"+equ[0]);
		List<Map<String,Object>> list=deviceService.getMachiningTask(equ[0]);
		if(StringUtils.listIsNull(list)){
			Map map=list.get(0);
			//���� �ӹ�����
			equInformation=df.format(Double.parseDouble(map.get("finishNum")+"")/Double.parseDouble(map.get("processNum")+"")*100);
			//�����ƶ��豸�ļӹ���
		}else{
			equInformation="0";
		}
		
		
		List appointday=deviceService.getappointdayEquNumber(equ, StringUtils.formatDate(date, 2));
		if(null!=appointday.get(0)){
			equInformation=equInformation+","+df.format(appointday.get(0));
		}else{
			equInformation=equInformation+","+0;
		}
		//�������ܺ�
		String equrcr=costManageService.getEquRCR(sef);
		equInformation=equInformation+","+equrcr;	
		
		List<Map<String,Object>> zmtMap=costManageService.queryLastPartCost(equ[0]);
		if(null!=zmtMap&&zmtMap.size()>0){
			 Map<String,Object> zmt=zmtMap.get(0);
			 double total_peopleCost=Double.valueOf(zmt.get("peopleCost").toString());
			 double total_mainMaterialCost=Double.valueOf(zmt.get("mainMaterialCost").toString());
			 double total_accMaterialCost=Double.valueOf(zmt.get("accMaterialCost").toString());
			 double total_oldCost=Double.valueOf(zmt.get("oldCost").toString());
			 double total_energyCost=Double.valueOf(zmt.get("energyCost").toString());	
			 equInformation=equInformation+","+StringUtils.doubleConvertFormat(total_peopleCost+total_mainMaterialCost+total_accMaterialCost+total_oldCost+total_energyCost);
		}else{
			equInformation=equInformation+",0";
		}
			
		
       System.out.println("------------------------"+equInformation+"-----------------------");
	}
	/**
	 * ���ݽڵ�id ��ȡ���¼ƻ������
	 * @param nodeid
	 * @return String
	 */
	public String getpper(String nodeid){
		String rStr="";
		Date date=new Date();
		List<Map<String, Object>> rs=deviceService.getppcr(nodeid, StringUtils.formatDate(date, 4));
		if(StringUtils.listIsNull(rs)){
			double totle=0;
			String ppers="";
			for(Map<String, Object> tt:rs){
				if(!"".equals(StringUtils.isEmpty2(tt.get("wcl")+"")))
				if(null!=tt.get("wcl"))
					totle=totle+Double.parseDouble(tt.get("wcl")+"");
				else
					totle=totle+0;
			}
			rStr="totle:"+df.format((totle))+ppers;
		}else{
			rStr="totle:0";
		}
		return rStr;
	}
	/**
	 * ��ȡ���ռӹ���
	 * @return
	 */
	public String getDayNumber(String nodeid){
		String rStr="";
		Date date=new Date();
		List<Map<String, Object>> rs=deviceService.getNumberOfDayProcessing(nodeid, StringUtils.formatDate(date,2));
		if(StringUtils.listIsNull(rs)){
			double totle=0;
			String ppers="";
			for(Map<String, Object> tt:rs){
				ppers=ppers+","+tt.get("uprodName")+":"+(Double.parseDouble(tt.get("dailyOutput")+""));
				totle=totle+Double.parseDouble(tt.get("dailyOutput")+"");
			}
			rStr="totle:"+df.format(totle)+ppers;
		}else{
			rStr="totle:0";
		}
		return rStr;
	}
	
	/**
	 * ��ȡ�豸Ч��
	 * @param nodeid
	 * @return
	 */
	public String getEquEfficiency(String nodeid){
		String rStr="";
		Date date=new Date();
		List<Map<String, Object>> rs=deviceService.getEquEfficiency(nodeid, StringUtils.formatDate(date,2));
		if(StringUtils.listIsNull(rs)){
			double totle=0;
			String ppers="";
			for(Map<String, Object> tt:rs){
				ppers=ppers+","+tt.get("equSerialNo")+":"+df.format(Double.parseDouble(tt.get("dayOeevalue")+"")*100);
				totle=totle+Double.parseDouble(tt.get("dayOeevalue")+"");
			}
			rStr="totle:"+df.format((totle/rs.size()*100))+ppers;
		}else{
			rStr="totle:0";
		}
		return rStr;
	}
	
	/**
	 * ��װҳ������
	 * @param list
	 */
	public void setDate(List list){
		 equipmentName="";
    	 xz="";
    	 yz="";
    	 image="";
    	 ipAddress="";
    	 pathThree="";
		for(int i=0;i<list.size();i++){
			 Map<String, Object> tf=(Map<String, Object>)list.get(i);
			 equipmentName=equipmentName+","+tf.get("equSerialNo");
			 ipAddress=ipAddress+","+tf.get("IPAddress");
			 String sta=tf.get("status")+"";
			 String path="";
			 if(null!=tf.get("path")&&!"".equals(tf.get("path"))){
				 String sbimgUrl=tf.get("path")+"";
				 //���Դ���
//				 String sbimgUrl="equ.png";
						 
				 pathThree=pathThree+","+sbimgUrl;
				 String timeZone=null==tf.get("timeZone")||"".equals(tf.get("timeZone"))?"0":tf.get("timeZone").toString();
				 Date da=new Date();
				 if(null==tf.get("updateTime")||"".equals(tf.get("updateTime"))){
 					 
 				 }else if(da.getTime()-((Date)tf.get("updateTime")).getTime()-Double.parseDouble(timeZone)>Constants.CONTROL_TUOJI_TIME){
					sta="�ѻ�";
				 }
 				 
				 if(sta.equals("����")||sta.equals("�ӹ�")){
					 sbimgUrl="yx"+sbimgUrl;
    			 }else if(sta.equals("�ѻ�")||sta.equals("�ػ�")){
    				 sbimgUrl="tj"+sbimgUrl;
    			 }else if(sta.equals("׼��")){
    				 sbimgUrl="zb"+sbimgUrl;
    			 } else if(sta.equals("����")||sta.equals("�ж�")||sta.equals("��ͣ")){
    				 sbimgUrl="gz"+sbimgUrl;
    			 }else if(sta.equals("����")){
    				 sbimgUrl="kx"+sbimgUrl;
    			 }else{
    				 sbimgUrl="kx"+sbimgUrl;
    			 }
				 image=image+","+sbimgUrl;
			 }else{
				 String sbimgUrl="ETC3650-SYB21-02.png";
				 pathThree=pathThree+","+sbimgUrl;
				 if(sta.equals("����")||sta.equals("�ӹ�")){
					 sbimgUrl="yx"+sbimgUrl; 
    			 }else if(sta.equals("�ѻ�")||sta.equals("�ػ�")){
    				 sbimgUrl="tj"+sbimgUrl;
    			 }else if(sta.equals("׼��")){
    				 sbimgUrl="zb"+sbimgUrl;
    			 } else if(sta.equals("����")||sta.equals("�ж�")||sta.equals("��ͣ")){
    				 sbimgUrl="gz"+sbimgUrl;
    			 }else if(sta.equals("����")){
    				 sbimgUrl="kx"+sbimgUrl;
    			 }else{
    				 sbimgUrl="kx"+sbimgUrl;
    			 }
				 image=image+","+sbimgUrl;
			 }
//   		 xz=xz+","+f.format(Double.parseDouble(tf.get("xAxis")+""));
//   		 yz=yz+","+f.format(Double.parseDouble(tf.get("yAxis")+""));
			 xz=xz+","+tf.get("xAxis");
			 yz=yz+","+tf.get("yAxis");
	   } 
		 if(equipmentName.length()>0){
			 System.out.println(equipmentName);
			 System.out.println(xz);
			 System.out.println(yz);
			 System.out.println(image);
			 System.out.println(ipAddress);
			 equipmentName=equipmentName.substring(1,equipmentName.length());
	   	     xz=xz.substring(1, xz.length());
			 yz=yz.substring(1, yz.length());
			 image=image.substring(1,image.length());
			 ipAddress=ipAddress.substring(1,ipAddress.length());
			 pathThree=pathThree.substring(1, pathThree.length());
		}
	}

	
	public IDeviceService getDeviceService() {
		return deviceService;
	}
	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	public String getYz() {
		return yz;
	}
	public void setYz(String yz) {
		this.yz = yz;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setNodeStr(String nodeStr) {
	}
	public String getPpcr() {
		return ppcr;
	}
	public void setPpcr(String ppcr) {
		this.ppcr = ppcr;
	}
	public String getNodp() {
		return nodp;
	}
	public void setNodp(String nodp) {
		this.nodp = nodp;
	}
	public String getEquday() {
		return equday;
	}
	public void setEquday(String equday) {
		this.equday = equday;
	}
	public IOrganizationService getOrganizationService() {
		return organizationService;
	}
	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	public List getWorkshop() {
		return workshop;
	}
	public void setWorkshop(List workshop) {
		this.workshop = workshop;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getSelectName() {
		return selectName;
	}
	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}
	public String getSef() {
		return sef;
	}
	public void setSef(String sef) {
		this.sef = sef;
	}
	public String getEquInformation() {
		return equInformation;
	}
	public void setEquInformation(String equInformation) {
		this.equInformation = equInformation;
	}
	public String getRcr() {
		return rcr;
	}
	public void setRcr(String rcr) {
		this.rcr = rcr;
	}
	public String getSyurl() {
		return syurl;
	}
	public void setSyurl(String syurl) {
		this.syurl = syurl;
	}
	public String getShurl() {
		return shurl;
	}
	public void setShurl(String shurl) {
		this.shurl = shurl;
	}
	public String getDefnodeid() {
		return defnodeid;
	}
	public void setDefnodeid(String defnodeid) {
		this.defnodeid = defnodeid;
	}
	public String getPathThree() {
		return pathThree;
	}
	public void setPathThree(String pathThree) {
		this.pathThree = pathThree;
	}

	public Boolean getSsoControl() {		
//		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();		
//		Map<String, String> urlMap=StringUtils.getUrl(request.getServletContext());
//		ssoControl=true;
//		if(urlMap!=null) 
//		 { 
//		    String str=urlMap.get("ssocontrol");
//		    if("1".equals(str))  ssoControl=true;
//		    if("0".equals(str))  ssoControl=false;
//		 }		
//		System.err.println("mocs---ssoControl:"+ssoControl);		
		return ssoControl;
	}

	public void setSsoControl(Boolean ssoControl) {
		this.ssoControl = ssoControl;
	}
	public String getSelectTNodesId() {
		return selectTNodesId;
	}
	public void setSelectTNodesId(String selectTNodesId) {
		this.selectTNodesId = selectTNodesId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getBgpath() {
		
		
		return bgpath;
	}
	public void setBgpath(String bgpath) {
		this.bgpath = bgpath;
	}
	public List<Map<String, Object>> getEquInventory() {
		return equInventory;
	}
	public void setEquInventory(List<Map<String, Object>> equInventory) {
		this.equInventory = equInventory;
	}
	public String getLoadmenu() {
		// ��ȡcook������Ĭ�Ͻڵ�
		TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		String defNode = StringUtils.getCookie(request, "defNode");
		if (null != defNode && !"".equals(defNode)) {
			try {
				List<smtcl.mocs.beans.authority.cache.TreeNode> list = authorizeService.getAuthorizedChildNodes(user.getUserId(), "mocs.sbgl.page.sbgl","-999");
				for (int i = 0; i < list.size(); i++) {
					smtcl.mocs.beans.authority.cache.TreeNode t = (smtcl.mocs.beans.authority.cache.TreeNode) list.get(i);
					String name = t.getNodeId();
					if (name != null && name.equals(defNode)) { // �õ�Ĭ�ϵĽڵ�
						selectName = t.getNodeName();
						
						//bgpath = t.getPath();// ��ȡ�ڵ㱳��ͼ
						String nodeid = t.getNodeId();
						session.setAttribute("nodeid", nodeid);
						List nlist=deviceService.getNodeName(nodeid);
						Map<String,Object> nMap=(Map<String,Object>)nlist.get(0);
						bgpath=null==nMap.get("path")?"":nMap.get("path").toString();
						break;
					}
				}
				
				if (StringUtils.listIsNull(list)) {
					// TNodes t=(TNodes)workshop.get(0);
					String nodeid = (String) session.getAttribute("nodeid"); // �滻��һ��
					if (!StringUtils.isEmpty(nodeid)) {
						String nodeids = deviceService.getNodeAllId(nodeid);
						ppcr = getpper(nodeid);
						nodp = getDayNumber(nodeid);
						equday = getEquEfficiency(nodeids);
						// rcr=df.format(Double.parseDouble(costManageService.getRCR(nodeid))*100);
						selectTNodesId = nodeid;
						if (nodeids.length() > 0) {
							List<Map<String, Object>> list2 = deviceService.getNodesAllEquNameStruts(nodeids);
							if (null != list2 && list2.size() > 0) {
								setDate(list2);
							} else {
								equipmentName = "";
								xz = "";
								yz = "";
								image = "";
								ipAddress = "";
								pathThree = "";
							}
						}
					}
					equInventory=deviceService.getEquInventoryInfo(selectTNodesId);
				   	parentId = deviceService.getParentIdByNodeid(selectTNodesId);
					
				}
				// StringUtils.se
				StringUtils.saveCookie(request, response, "defNode", "");
				// StringUtils.delCookie(request, response, "defNode");
				String dd = StringUtils.getCookie(request, "defNode");
				System.out.println("defNode" + dd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		return loadmenu;
	}
	public void setLoadmenu(String loadmenu) {
		this.loadmenu = loadmenu;
	}
	
}
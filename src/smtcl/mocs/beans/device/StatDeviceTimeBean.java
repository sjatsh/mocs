package smtcl.mocs.beans.device;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * ʱ��ͳ��
 * @����ʱ�� 2013-4-26
 * @���� gaozhoujun,liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 * */
@ManagedBean(name="StatDeviceTimeBean")
@ViewScoped
public class StatDeviceTimeBean {
	/**
	 * ��ʼʱ��
	 */
	private Date start;
	/**
	 * ����ʱ��
	 */
	private Date end;
	/**
	 * ���ݿ��ѯ����
	 */
	private Map<String, Object> map;
	/**
	 * ҳ����Ӧ����
	 */
	private Map<String, Object> mapa;
	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr;    
	/**
	 * �豸���к�
	 */
	private String equSerialNo;  
	/**
	 * �жϼ��ؽڵ��׸��豸���к�
	 * 
	 */
	private String equSerialNoPan;
	/**
	 * �ж��Ƿ�ǰ�ڵ�
	 */
	private String nodeValue;
	/**
	 * �豸ҵ���߼�
	 */
	private IDeviceService deviceService = (IDeviceService)ServiceFactory.getBean("deviceService");
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
	/**
	 * �豸�б�������
	 */
	private List<SelectItem> nameList = new ArrayList<SelectItem>();
	/**
	 * ��״ͼ��������
	 */
	public String jsonData;
	/**
	 * ��ͼ��������
	 */
	private String jsonPie;

	/**
	 *��ȡ��ǰ����ڵ� 
	 * @return String
	 */
	public String getNodeStr() {
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			String node=session.getAttribute("nodeid")+"";
				//this.nodeStr=FaceContextUtil.getContext().getSessionMap().get("nodeid").toString();
				this.nodeStr=node;
				List<TNodes> tn=organizationService.getTNodesById(node);
				String nodeids = organizationService.getAllTNodesId(tn.get(0));
				getnameList(nodeids);
				nodeValue = nodeStr;
			equSerialNoPan=null;//���
		 return nodeStr;
	}
	
	/**
	 * ��ͼ���������
	 * @param map
	 * @return List<Long>
	 */
	 public  List<Long> createDonutModel(Map map) { 
			List<Long> list=null;
			 list=new ArrayList<Long>();
	    	if(null != map && map.size() > 0){
	    		list.add((Long)map.get("runningTime"));//����ʱ��     2471051
		        list.add((Long)map.get("stopTime"));//�ػ�ʱ��        5564149
		        list.add((Long)map.get("prepareTime"));//׼��ʱ��      628843
		        list.add((Long)map.get("processTime"));//�ӹ�ʱ��      365907
		        list.add((Long)map.get("idleTime"));//����ʱ��      391137
		        list.add((Long)map.get("breakdownTime"));//����ʱ��      995282
		        list.add((Long)map.get("dryRunningTime"));//������ʱ��      87542
		        list.add((Long)map.get("cuttingTime"));//����ʱ��      193265
		        list.add((Long)map.get("manualRunningTime"));//�ֶ�����ʱ��     316777
		        list.add((Long)map.get("materialTime"));//������ʱ��     312066
		        list.add((Long)map.get("toolChangeTime"));//����ʱ��     85100
	    	}
	    	
	        return list;
	  }  
	/**
	 * ��ѯ��ť��Ӧ
	 */
	public void searchAction(){			
		if(null != map && map.size() > 0){
			map.clear();
		}
		if(null != mapa && mapa.size() > 0){
			mapa.clear();
		}
		//��ǰ�豸���к�����
		List<Map<String, Object>> list=deviceService.findIHisroryStatistics(equSerialNo,StringUtils.formatDate(start, 2),StringUtils.formatDate(end, 2));
		if(null!=list&&list.size()>0){
			 map=list.get(0);
			 mapa=new HashMap<String, Object>();
			 if(null != map && map.size() > 0){ //���ݿ��ѯ���ݷ�װ��mapa��
				 	if(null != map.get("runningTime")){
				 		mapa.put("runningTime", StringUtils.SecondIsDDmmss(map.get("runningTime")));//����ʱ��
				 	}else{
				 		mapa.put("runningTime", StringUtils.SecondIsDDmmss(0));//����ʱ��
				 	}
				 	if(null != map.get("stopTime")){
				 		mapa.put("stopTime", StringUtils.SecondIsDDmmss(map.get("stopTime")));//�ػ�ʱ��
				 	}else{
				 		mapa.put("stopTime", StringUtils.SecondIsDDmmss(0));//�ػ�ʱ��
				 	}
					if(null != map.get("prepareTime")){
						mapa.put("prepareTime", StringUtils.SecondIsDDmmss(map.get("prepareTime")));//׼��ʱ��
					}else{
						mapa.put("prepareTime", StringUtils.SecondIsDDmmss(0));//׼��ʱ��
					}
					if(null != map.get("processTime")){
						mapa.put("processTime", StringUtils.SecondIsDDmmss(map.get("processTime")));//�ӹ�ʱ��
					}else{
						mapa.put("processTime", StringUtils.SecondIsDDmmss(0));//�ӹ�ʱ��
						
					}
					if(null != map.get("idleTime")){
						mapa.put("idleTime", StringUtils.SecondIsDDmmss(map.get("idleTime")));//���д���ʱ��
					}else{
						mapa.put("idleTime", StringUtils.SecondIsDDmmss(0));//���д���ʱ��
					}
					if(null != map.get("breakdownTime")){
						mapa.put("breakdownTime", StringUtils.SecondIsDDmmss(map.get("breakdownTime")));//����ʱ��
					}else{
						mapa.put("breakdownTime", StringUtils.SecondIsDDmmss(0));//����ʱ��
					}
					if(null != map.get("dryRunningTime")){
						mapa.put("dryRunningTime", StringUtils.SecondIsDDmmss(map.get("dryRunningTime")));//������ʱ��
					}else{
						mapa.put("dryRunningTime", StringUtils.SecondIsDDmmss(0));//������ʱ��
					}
					if(null != map.get("cuttingTime")){
						mapa.put("cuttingTime", StringUtils.SecondIsDDmmss(map.get("cuttingTime")));//����ʱ��
					}else{
						mapa.put("cuttingTime", StringUtils.SecondIsDDmmss(0));//����ʱ��
					}
					if(null != map.get("manualRunningTime")){
						mapa.put("manualRunningTime", StringUtils.SecondIsDDmmss(map.get("manualRunningTime")));//�ֶ�����ʱ��
					}else{
						mapa.put("manualRunningTime", StringUtils.SecondIsDDmmss(0));//�ֶ�����ʱ��
					}
					if(null != map.get("materialTime")){
						mapa.put("materialTime", StringUtils.SecondIsDDmmss(map.get("materialTime")));//������ʱ��
					}else{
						mapa.put("materialTime", StringUtils.SecondIsDDmmss(0));//������ʱ��
					}
					if(null != map.get("otherTime")){
						mapa.put("otherTime", StringUtils.SecondIsDDmmss(map.get("otherTime")));//����ʱ��
					}else{
						mapa.put("otherTime", StringUtils.SecondIsDDmmss(0));//����ʱ��
					}
					if(null != map.get("toolChangeTime")){
						mapa.put("toolChangeTime", StringUtils.SecondIsDDmmss(map.get("toolChangeTime")));//����ʱ��
					}else{
						mapa.put("toolChangeTime", StringUtils.SecondIsDDmmss(0));//����ʱ��
					}
			 }
		}
		List<Long> listtu=null;
		listtu=this.createDonutModel(map);//��ͼ���������
		if(null == listtu || listtu.size() == 0){//��ѯ�������ݣ����г�ʼ��
			listtu=new ArrayList<Long>();
			for(int i=0;i<11;i++){
				listtu.add(Long.valueOf(0));
			}
		}
		if(null != list  && list.size() > 0){
			 Map<String,Object> barModel=new HashMap<String,Object>();
			 jsonData=this.toJSON(barModel,listtu); //������״ͼ��̨���÷���
		}else{
			jsonData=null;
		}
		 jsonPie=loadPieData(mapa);
	}
    
	/**
	 * ��״ͼ�������
	 * @param chartModel
	 * @param tudate
	 * @return String
	 */
	public String toJSON(Map<String,Object> chartModel,List<Long> tudate) {
		JSONObject dataToJSON = new JSONObject();
		String[] rows={"����ʱ��","�� �� ʱ ��","�ӹ�ʱ��","׼��ʱ�� ","����ʱ��","����ʱ��","����ʱ��","������ʱ��","����ʱ��","�ֶ�ʱ��","������ʱ��"};
		try {
			dataToJSON.put("day", "��");
			dataToJSON.put("hour", "Сʱ");
			dataToJSON.put("minute", "��");
			dataToJSON.put("second", "��");
			JSONArray rowKeys = new JSONArray(Arrays.asList(rows));
			dataToJSON.put("rowKeys", rowKeys);
			for (int i = 0; i < rowKeys.length(); i++) {
				double[] datas ={tudate.get(i)};
				JSONArray jsonArray =  new JSONArray();
				for (int j = 0; j < datas.length; j++) {
					jsonArray.put(datas[j]);
				}				
				dataToJSON.put("data" + i,jsonArray);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataToJSON.toString();
	}	
	
	/**
	 * ��ͼ�������
	 * @param deviceInfo
	 * @return String
	 */
	 public String loadPieData(Map deviceInfo){
		 if(null != deviceInfo && deviceInfo.size() > 0){
			 Map testjson = new HashMap();	
		 	 Map zmap=new HashMap();
		 	 Map mValue=new HashMap();
			 mValue.put("name", "�ػ�ʱ��");
			 mValue.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("stopTime").toString()));
			 Map mValue2=new HashMap();
			 mValue2.put("name", "����ʱ��");
			 mValue2.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("runningTime").toString()));
			 zmap.put("gj", mValue);
			 zmap.put("kj", mValue2);
			 testjson.put("data0", zmap);
				
			 Map zmap2=new HashMap();
			 Map mV2=new HashMap();
			 mV2.put("name", "�ػ�ʱ��");
			 mV2.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("stopTime").toString()));
			
			 Map mV3=new HashMap();
			 mV3.put("name", "����ʱ��");
			 mV3.put("y",StringUtils.DDMMssIsSecond(deviceInfo.get("breakdownTime").toString()));
			 
			 Map mV4=new HashMap();
			 mV4.put("name", "����ʱ��");
			 mV4.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("idleTime").toString()));
			 Map mV5=new HashMap();
			 mV5.put("name", "׼��ʱ��");
			 mV5.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("prepareTime").toString()));
			 Map mV6=new HashMap();
			 mV6.put("name", "�ӹ�ʱ��");
			 mV6.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("processTime").toString()));	
			 zmap2.put("gj", mV2);
			 zmap2.put("gz",mV3);
			 zmap2.put("kx",mV4);
			 zmap2.put("zb",mV5);
			 zmap2.put("jg",mV6);
			 testjson.put("data1", zmap2);
				
			 Map zmap3=new HashMap();
			 Map mVv2=new HashMap();
			 mVv2.put("name", "�ػ�ʱ��");
			 mVv2.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("stopTime").toString()));
			 Map mVv3=new HashMap();
			 mVv3.put("name", "����ʱ��");
			 mVv3.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("breakdownTime").toString()));
			 Map mVv4=new HashMap();
			 mVv4.put("name", "����ʱ��");
			 mVv4.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("idleTime").toString()));
			 Map mVv5=new HashMap();
			 mVv5.put("name", "����ʱ��");
			 mVv5.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("cuttingTime").toString()));
		     Map mVv6=new HashMap();
			 mVv6.put("name", "������ʱ��");
			 mVv6.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("dryRunningTime").toString()));
		     Map mVv7=new HashMap();
			 mVv7.put("name", "����ʱ��");
			 mVv7.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("toolChangeTime").toString()));	
		     Map mVv8=new HashMap();
			 mVv8.put("name", "�ֶ�����ʱ��");
			 mVv8.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("manualRunningTime").toString()));	
		     Map mVv9=new HashMap();
			 mVv9.put("name", "������ʱ��");
			 mVv9.put("y", StringUtils.DDMMssIsSecond(deviceInfo.get("materialTime").toString()));	
			 zmap3.put("gj",mVv2);
			 zmap3.put("gz",mVv3);
			 zmap3.put("kx",mVv4);
			 zmap3.put("qx",mVv5);
			 zmap3.put("ky",mVv6);
			 zmap3.put("hd",mVv7);
			 zmap3.put("sd",mVv8);
			 zmap3.put("sx",mVv9);
			 testjson.put("data2", zmap3);
			 Gson gson = new GsonBuilder().serializeNulls().create(); 
			 return gson.toJson(testjson);
		}
		 return null;
	 }
	 
	 /**
	  * ��ǰ�ڵ��µ��豸      ������ 
	  * @param nodeids
	  * 
	  */
    public void getnameList(String nodeids){
    	System.out.println("getnameList()====equSerialNo====="+equSerialNo);
    	if(null!=nodeStr&&!"".equals(nodeStr)){
			List<TEquipmentInfo> nlist=deviceService.getNodesAllEquName(nodeids);
			for(int i=0;i<nlist.size();i++){
				TEquipmentInfo teinfo=nlist.get(i);
				if(null!=teinfo){
					nameList.add(new SelectItem(teinfo.getEquSerialNo(),(String)teinfo.getEquName()));
				}
				if (nodeValue != nodeStr && null == equSerialNoPan) {// ���ڵ�
						equSerialNo = nlist.get(0).getEquSerialNo();
				}
			}
		}
    	searchAction();
    }
	
	/**
	 * ���췽��
	 */
	@SuppressWarnings("unchecked")
	public StatDeviceTimeBean(){
		System.out.println("StatDeviceTimeBean()====equSerialNo====="+equSerialNo);
		String equNo="";
		//����������תʹ�� URL������ start -----
		String para = FaceContextUtil.getContext().getRequestParameterMap().get("equSerialNo"); 
		if(!StringUtils.isEmpty(para)) 
		{
			equNo=para;
		    //��cookie
			HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			StringUtils.saveCookie(request,response, "equSerialNo", para);
			System.err.println("-----------------------------------"+StringUtils.getCookie(request,"equSerialNo"));
		}
		//����������תʹ�� URL������  end -------  
		else{
			//���߹���Cookie���� start -----
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			equNo=StringUtils.getCookie(request,"equSerialNo"); 
			//���߹���Cookie���� end ----- 
		}
		
		if(null!=equNo&&!"".equals(equNo)){
			List<TEquipmentInfo> tequipment=deviceService.getAllEquName("'"+equNo+"'");
			if(null != tequipment && tequipment.size() > 0){
				nameList=new ArrayList<SelectItem>();
				String equName=tequipment.get(0).getEquName();
				nameList.add(new SelectItem(equNo,equName));
			}
			equSerialNo = equNo;
			equSerialNoPan=equNo;
		}
		//Ĭ��ʱ��ֵ :���һ����
		if(null==start && null==end){
			Date[] datea=StringUtils.convertDatea(1);
			start=datea[0];
			end=datea[1];
			System.out.println("��ʱ�丳��ֵ  start=="+start+"end=="+end);	
		}
		searchAction();
	}
	
	
	public IOrganizationService getOrganizationService() {
		return organizationService;
	}
	
	public String getJsonData() {
		return jsonData;
	}
	
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	public List<SelectItem> getNameList() {
		return nameList;
	}
	
	public void setNameList(List<SelectItem> nameList) {
		this.nameList = nameList;
	}
	
	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}
	
	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}
			
	public String getEquSerialNo() {
		return equSerialNo;
	}
	
	public Map<String, Object> getMapa() {
		return mapa;
	}
	
	public void setMapa(Map<String, Object> mapa) {
		this.mapa = mapa;
	}
	
	public Map<String, Object> getMap() {
		return map;
	}
	
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	public IDeviceService getDeviceService() {
		return deviceService;
	}
	
	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
	public String getJsonPie() {
		return jsonPie;
	}
	
	public void setJsonPie(String jsonPie) {
		this.jsonPie = jsonPie;
	}
	
	public Date getStart() {
		return start;
	}
	
	public void setStart(Date start) {
		this.start = start;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public void setEnd(Date end) {
		this.end = end;
	}
    
}

package smtcl.mocs.beans.device;

import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//import net.sf.json.JSONObject;
import org.dreamwork.persistence.ServiceFactory;
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

//import org.json.JSONArray;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * ʱ��ͳ��
 * @����ʱ�� 2013-4-26
 * @���� gaozhoujun,liguoqiang
 * @�޸��ߣ� songkaiang
 * @�޸����ڣ� 2015-1-25
 * @�޸�˵��: ���ݹ��ʻ�
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
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String node = session.getAttribute("nodeid") + "";
        this.nodeStr = node;
        List<TNodes> tn = organizationService.getTNodesById(node);
        if (tn != null && tn.size() > 0) {
            String nodeids = organizationService.getAllTNodesId(tn.get(0));
            getnameList(nodeids);
        }

        nodeValue = nodeStr;
        equSerialNoPan = null;//���
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
        Locale locale = this.getLocale();
		if(null != map && map.size() > 0){
			map.clear();
		}
		if(null != mapa && mapa.size() > 0){
			mapa.clear();
		}
		//��ǰ�豸���к�����
		List<Map<String, Object>> list=deviceService.findIHisroryStatistics(equSerialNo,StringUtils.formatDate(start, 2),StringUtils.formatDate(end, 2));
		Map<String, Object> seconds = new HashMap<String, Object>();
        if(null!=list&&list.size()>0){
			 map=list.get(0);
			 mapa=new HashMap<String, Object>();
			 if(null != map && map.size() > 0){ //���ݿ��ѯ���ݷ�װ��mapa��
				 	if(null != map.get("runningTime")){
				 		mapa.put("runningTime", StringUtils.SecondIsDDmmss(map.get("runningTime"),locale));//����ʱ��
                        seconds.put("runningTime",map.get("runningTime"));
				 	}else{
				 		mapa.put("runningTime", StringUtils.SecondIsDDmmss(0,locale));//����ʱ��
                        seconds.put("runningTime",map.get("runningTime"));
				 	}
				 	if(null != map.get("stopTime")){
				 		mapa.put("stopTime", StringUtils.SecondIsDDmmss(map.get("stopTime"),locale));//�ػ�ʱ��
                        seconds.put("stopTime",map.get("stopTime"));
				 	}else{
				 		mapa.put("stopTime", StringUtils.SecondIsDDmmss(0,locale));//�ػ�ʱ��
                        seconds.put("stopTime",map.get("stopTime"));
				 	}
					if(null != map.get("prepareTime")){
						mapa.put("prepareTime", StringUtils.SecondIsDDmmss(map.get("prepareTime"),locale));//׼��ʱ��
                        seconds.put("prepareTime",map.get("prepareTime"));
					}else{
						mapa.put("prepareTime", StringUtils.SecondIsDDmmss(0,locale));//׼��ʱ��
                        seconds.put("prepareTime",map.get("prepareTime"));
					}
					if(null != map.get("processTime")){
						mapa.put("processTime", StringUtils.SecondIsDDmmss(map.get("processTime"),locale));//�ӹ�ʱ��
                        seconds.put("processTime",map.get("prepareTime"));
					}else{
						mapa.put("processTime", StringUtils.SecondIsDDmmss(0,locale));//�ӹ�ʱ��
                        seconds.put("processTime",map.get("processTime"));
						
					}
					if(null != map.get("idleTime")){
						mapa.put("idleTime", StringUtils.SecondIsDDmmss(map.get("idleTime"),locale));//���д���ʱ��
                        seconds.put("idleTime",map.get("idleTime"));
					}else{
						mapa.put("idleTime", StringUtils.SecondIsDDmmss(0,locale));//���д���ʱ��
                        seconds.put("idleTime",map.get("idleTime"));
					}
					if(null != map.get("breakdownTime")){
						mapa.put("breakdownTime", StringUtils.SecondIsDDmmss(map.get("breakdownTime"),locale));//����ʱ��
                        seconds.put("breakdownTime",map.get("breakdownTime"));
					}else{
						mapa.put("breakdownTime", StringUtils.SecondIsDDmmss(0,locale));//����ʱ��
                        seconds.put("breakdownTime",map.get("breakdownTime"));
					}
					if(null != map.get("dryRunningTime")){
						mapa.put("dryRunningTime", StringUtils.SecondIsDDmmss(map.get("dryRunningTime"),locale));//������ʱ��
                        seconds.put("dryRunningTime",map.get("dryRunningTime"));
					}else{
						mapa.put("dryRunningTime", StringUtils.SecondIsDDmmss(0,locale));//������ʱ��
                        seconds.put("dryRunningTime",map.get("dryRunningTime"));
					}
					if(null != map.get("cuttingTime")){
						mapa.put("cuttingTime", StringUtils.SecondIsDDmmss(map.get("cuttingTime"),locale));//����ʱ��
                        seconds.put("cuttingTime",map.get("cuttingTime"));
					}else{
						mapa.put("cuttingTime", StringUtils.SecondIsDDmmss(0,locale));//����ʱ��
                        seconds.put("cuttingTime",map.get("cuttingTime"));
					}
					if(null != map.get("manualRunningTime")){
						mapa.put("manualRunningTime", StringUtils.SecondIsDDmmss(map.get("manualRunningTime"),locale));//�ֶ�����ʱ��
                        seconds.put("manualRunningTime",map.get("manualRunningTime"));
					}else{
						mapa.put("manualRunningTime", StringUtils.SecondIsDDmmss(0,locale));//�ֶ�����ʱ��
                        seconds.put("manualRunningTime",map.get("manualRunningTime"));
					}
					if(null != map.get("materialTime")){
						mapa.put("materialTime", StringUtils.SecondIsDDmmss(map.get("materialTime"),locale));//������ʱ��
                        seconds.put("materialTime",map.get("materialTime"));
					}else{
						mapa.put("materialTime", StringUtils.SecondIsDDmmss(0,locale));//������ʱ��
                        seconds.put("materialTime",map.get("materialTime"));
					}
					if(null != map.get("otherTime")){
						mapa.put("otherTime", StringUtils.SecondIsDDmmss(map.get("otherTime"),locale));//����ʱ��
                        seconds.put("otherTime",map.get("otherTime"));
					}else{
						mapa.put("otherTime", StringUtils.SecondIsDDmmss(0,locale));//����ʱ��
                        seconds.put("otherTime",map.get("otherTime"));
					}
					if(null != map.get("toolChangeTime")){
						mapa.put("toolChangeTime", StringUtils.SecondIsDDmmss(map.get("toolChangeTime"),locale));//����ʱ��
                        seconds.put("toolChangeTime",map.get("toolChangeTime"));
					}else{
						mapa.put("toolChangeTime", StringUtils.SecondIsDDmmss(0,locale));//����ʱ��
                        seconds.put("toolChangeTime",map.get("toolChangeTime"));
					}
			 }
		}
		List<Long> listtu=null;
		listtu=this.createDonutModel(map);//��ͼ���������
		if(null == listtu || listtu.size() == 0){//��ѯ�������ݣ����г�ʼ��
			listtu=new ArrayList<Long>();
			for(int i=0;i<11;i++){
				listtu.add((long)0);
			}
		}
		if(null != list  && list.size() > 0){
			 Map<String,Object> barModel=new HashMap<String,Object>();
			 jsonData=this.toJSON(barModel,listtu); //������״ͼ��̨���÷���
		}else{
			jsonData=null;
		}
		 jsonPie=loadPieData(seconds);
	}
    
	/**
	 * ��״ͼ�������
	 * @param chartModel
	 * @param tudate
	 * @return String
	 */
	public String toJSON(Map<String,Object> chartModel,List<Long> tudate) {
        Locale locale = this.getLocale();
        JSONObject dataToJSON = new JSONObject();
        try {
            String[] rows;
            if (locale.toString().equals("en") || locale.toString().equals("en_US")) {
                rows = new String[]{"Power On Time", "Power Off Time", "Processing Time", "Preparation Time", "Idle Time", "Error Time", "Cutting Time", "Idle Running Time",
                        "Tool Changing Time", "Manual Operating Time", "Baiting time"};
                dataToJSON.put("day", "days");
                dataToJSON.put("hour", "hours");
                dataToJSON.put("minute", "minutes");
                dataToJSON.put("second", "seconds");
            } else {
                rows = new String[]{"����ʱ��", "�� �� ʱ ��", "�ӹ�ʱ��", "׼��ʱ�� ", "����ʱ��", "����ʱ��", "����ʱ��", "������ʱ��", "����ʱ��", "�ֶ�ʱ��", "������ʱ��"};
                dataToJSON.put("day", "��");
                dataToJSON.put("hour", "Сʱ");
                dataToJSON.put("minute", "��");
                dataToJSON.put("second", "��");
            }

            JSONArray rowKeys = new JSONArray(Arrays.asList(rows));
            dataToJSON.put("rowKeys", rowKeys);
            for (int i = 0; i < rowKeys.length(); i++) {
                double[] datas = {tudate.get(i)};
                JSONArray jsonArray = new JSONArray();
                for (int j = 0; j < datas.length; j++) {
                    jsonArray.put(datas[j]);
                }
                dataToJSON.put("data" + i, jsonArray);
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
         Locale locale = this.getLocale();
		 if(null != deviceInfo && deviceInfo.size() > 0){
			 Map<String,Object> testjson = new HashMap<String,Object>();
		 	 Map<String,Object> zmap=new HashMap<String,Object>();
		 	 Map<String,Object> mValue=new HashMap<String,Object>();//�ػ�ʱ��
             Map<String,Object> mValue2=new HashMap<String,Object>();//����ʱ��
             Map<String,Object> mV2=new HashMap<String,Object>();//�ػ�ʱ��
             if(locale.toString().equals("en") || locale.toString().equals("en_US")) {
                 mValue.put("name", "Shutdown Time");
                 mV2.put("name", "Shutdown Time");
                 mValue2.put("name", "Boot Time");
             }else{
                 mValue.put("name", "�ػ�ʱ��");
                 mV2.put("name", "�ػ�ʱ��");
                 mValue2.put("name", "����ʱ��");
             }

			 mValue.put("y", Integer.parseInt(deviceInfo.get("stopTime").toString()));
			 mValue2.put("y", Integer.parseInt(deviceInfo.get("runningTime").toString()));
			 zmap.put("gj", mValue);
			 zmap.put("kj", mValue2);
			 testjson.put("data0", zmap);

			 Map<String,Object> zmap2=new HashMap<String,Object>();


			 mV2.put("y", Integer.parseInt(deviceInfo.get("stopTime").toString()));

			 Map<String,Object> mV3=new HashMap<String,Object>();
			 mV3.put("name", "����ʱ��");
			 mV3.put("y",Integer.parseInt(deviceInfo.get("breakdownTime").toString()));

			 Map<String,Object> mV4=new HashMap<String,Object>();
			 mV4.put("name", "����ʱ��");
			 mV4.put("y", Integer.parseInt(deviceInfo.get("idleTime").toString()));
			 Map<String,Object> mV5=new HashMap<String,Object>();
			 mV5.put("name", "׼��ʱ��");
			 mV5.put("y", Integer.parseInt(deviceInfo.get("prepareTime").toString()));
			 Map<String,Object> mV6=new HashMap<String,Object>();
			 mV6.put("name", "�ӹ�ʱ��");
			 mV6.put("y", Integer.parseInt(deviceInfo.get("processTime").toString()));
			 zmap2.put("gj", mV2);
			 zmap2.put("gz",mV3);
			 zmap2.put("kx",mV4);
			 zmap2.put("zb",mV5);
			 zmap2.put("jg",mV6);
			 testjson.put("data1", zmap2);

			 Map<String,Object> zmap3=new HashMap<String,Object>();
			 Map<String,Object> mVv2=new HashMap<String,Object>();
			 mVv2.put("name", "�ػ�ʱ��");
			 mVv2.put("y", Integer.parseInt(deviceInfo.get("stopTime").toString()));
			 Map<String,Object> mVv3=new HashMap<String,Object>();
			 mVv3.put("name", "����ʱ��");
			 mVv3.put("y", Integer.parseInt(deviceInfo.get("breakdownTime").toString()));
			 Map<String,Object> mVv4=new HashMap<String,Object>();
			 mVv4.put("name", "����ʱ��");
			 mVv4.put("y", Integer.parseInt(deviceInfo.get("idleTime").toString()));
			 Map<String,Object> mVv5=new HashMap<String,Object>();
			 mVv5.put("name", "����ʱ��");
			 mVv5.put("y", Integer.parseInt(deviceInfo.get("cuttingTime").toString()));
		     Map<String,Object> mVv6=new HashMap<String,Object>();
			 mVv6.put("name", "������ʱ��");
			 mVv6.put("y", Integer.parseInt(deviceInfo.get("dryRunningTime").toString()));
		     Map<String,Object> mVv7=new HashMap<String,Object>();
			 mVv7.put("name", "����ʱ��");
			 mVv7.put("y", Integer.parseInt(deviceInfo.get("toolChangeTime").toString()));
		     Map<String,Object> mVv8=new HashMap<String,Object>();
			 mVv8.put("name", "�ֶ�����ʱ��");
			 mVv8.put("y", Integer.parseInt(deviceInfo.get("manualRunningTime").toString()));
		     Map<String,Object> mVv9=new HashMap<String,Object>();
			 mVv9.put("name", "������ʱ��");
			 mVv9.put("y", Integer.parseInt(deviceInfo.get("materialTime").toString()));
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
		String equNo;
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
				//nameList.add(new SelectItem(equNo,equName));
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
	
	private Locale getLocale(){
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Locale locale = SessionHelper.getCurrentLocale(session);
        return locale;
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

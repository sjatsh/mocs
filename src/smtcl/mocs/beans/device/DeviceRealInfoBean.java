package smtcl.mocs.beans.device;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.model.DeviceInfoModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;


/**
 * �û��鿴�豸��������ʵʱ����
 * @����ʱ�� 2012-12-06
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name="deviceRealInfoBean")
@ViewScoped
public class DeviceRealInfoBean {
	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr;   
	/**
	 * ��ʾҳ���ʵ��ģ��
	 */
	private DeviceInfoModel deviceInfo; 
	/**
	 * �豸����������ѡ����
	 */
    private String nameselected;
    /**
	 * �豸�����������б�
	 */
    private List<SelectItem> nameList;
    /**
	 * ��״ͼ����
	 */
    private String pieData;
    /**
	 * Ȩ�޽ӿ�ʵ��
	 */
    private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
    /**
	 * �豸ҵ���߼�
	 */
	private IDeviceService deviceService =(IDeviceService)ServiceFactory.getBean("deviceService");
	/**
	 * ������̿��Ʊ���
	 */
	private int init=0;
	
	/**
	 * ���췽��
	 */
	public DeviceRealInfoBean(){
		String equSerialNo="";
		//����������תʹ�� URL������ start -----
		String para = FaceContextUtil.getContext().getRequestParameterMap().get("equSerialNo"); 
		if(!StringUtils.isEmpty(para)) 
		{
			equSerialNo=para;
			//��cookie
			HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			StringUtils.saveCookie(request,response, "equSerialNo", para);
		}
		//����������תʹ�� URL������  end -------  
		else{
			//���߹���Cookie���� start -----
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		equSerialNo=StringUtils.getCookie(request,"equSerialNo"); 
		    //���߹���Cookie���� end ----- 
		}
		deviceService=(IDeviceService)ServiceFactory.getBean("deviceService"); //��ȡע��
		
		if(null!=equSerialNo&&!"".equals(equSerialNo)){
			System.err.println("equSerialNo  para:"+para);
			init=1;
			deviceInfo=deviceService.getDeviceInfoModelRealtime(equSerialNo);
			if(deviceInfo!=null&&null!=deviceInfo.getUpdateTime()){
				deviceInfo.setUpdateTime(deviceInfo.getUpdateTime().substring(0, 16));
				if(deviceInfo.getProgramm().contains("/"))
					deviceInfo.setProgramm(deviceInfo.getProgramm().substring(deviceInfo.getProgramm().lastIndexOf("/")+1)); //ȥ��·��
		    	deviceInfo.setProgramm(StringUtils.getSubString(deviceInfo.getProgramm(), "1"));//������ƹ�����ȡǰ10���ַ�
		    	deviceInfo.setEquSerialNo(StringUtils.getSubString(deviceInfo.getEquSerialNo(),"4"));
				searchAction(equSerialNo);
				
			}
			if(null==nameList||nameList.size()<1){
				nameList=new ArrayList<SelectItem>();
				List<TEquipmentInfo> list=deviceService.getAllEquName("'"+equSerialNo+"'");
				if(null!=list&&list.size()>0){
					TEquipmentInfo tf=(TEquipmentInfo)list.get(0);
					nameList.add(new SelectItem(equSerialNo+"",equSerialNo+""));
					nameselected=equSerialNo;
				}
			}
		}
	}
	
	/**
	 * ������ı�ʱ���ݼ���
	 */
	public void onChange(){
		init=1;
		String equSerialNo=nameselected;
		if(null!=equSerialNo&&!"".equals(equSerialNo)){
			deviceInfo=deviceService.getDeviceInfoModelRealtime(equSerialNo);
			if(deviceInfo!=null){
				if(null!=deviceInfo.getUpdateTime()&&deviceInfo.getUpdateTime().length()>16){
					deviceInfo.setUpdateTime(deviceInfo.getUpdateTime().substring(0, 16));
				}
				if(deviceInfo.getProgramm().contains("/"))
					deviceInfo.setProgramm(deviceInfo.getProgramm().substring(deviceInfo.getProgramm().lastIndexOf("/")+1)); //ȥ��·��
			    deviceInfo.setProgramm(StringUtils.getSubString(deviceInfo.getProgramm(), "1"));//������ƹ�����ȡǰ20���ַ�
				deviceInfo.setEquSerialNo(StringUtils.getSubString(deviceInfo.getEquSerialNo(),"4"));
				searchAction(equSerialNo);
				nameselected=equSerialNo;
			}
		}
	}
	
	/**
	 * �Զ�ˢ��ʱ���ݼ���
	 */
	public void refreshData(){
		init=1;
		String equSerialNo=deviceInfo.getEquSerialNo();
		if(null!=equSerialNo&&!"".equals(equSerialNo)){
			deviceInfo=deviceService.getDeviceInfoModelRealtime(equSerialNo);
			if(deviceInfo!=null){
				deviceInfo.setUpdateTime(deviceInfo.getUpdateTime().substring(0, 16));
				if(deviceInfo.getProgramm().contains("/"))
					deviceInfo.setProgramm(deviceInfo.getProgramm().substring(deviceInfo.getProgramm().lastIndexOf("/")+1)); //ȥ��·��
			    deviceInfo.setProgramm(StringUtils.getSubString(deviceInfo.getProgramm(), "1"));//������ƹ�����ȡǰ20���ַ�
				deviceInfo.setEquSerialNo(StringUtils.getSubString(deviceInfo.getEquSerialNo(),"4"));
				searchAction(equSerialNo);
				nameselected=equSerialNo;
			}
		}
	}
	
	/**
	 *��ȡ��ǰ����ڵ� 
	 * @return String
	 */
	public String getNodeStr() {
		TreeNode currentNode = (TreeNode) FaceContextUtil
				.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			String nodeids=organizationService.getAllTNodesId(currentNode); 
 	    	this.nodeStr=currentNode.getNodeName();
 	    	getnameList(nodeids);
		}
		return nodeStr;
	}
	 
	/**
	 * ��ȡ��ǰ�ڵ��������豸������
	*/
    public void getnameList(String nodeids){
    	if(null!=nodeStr&&!"".equals(nodeStr)){
			nameList=new ArrayList<SelectItem>();
			List<TEquipmentInfo> nlist=deviceService.getNodesAllEquName(nodeids);
			if(null!=nlist&&nlist.size()>0){
				for(TEquipmentInfo teinfo:nlist){
					nameList.add(new SelectItem(teinfo.getEquSerialNo()+"",teinfo.getEquName()+""));
				}
				if(init==0){
					if(null==nameselected){
						nameselected=nlist.get(0).getEquSerialNo();
					}
					deviceInfo=deviceService.getDeviceInfoModelRealtime(nameselected);
					if(deviceInfo!=null&&null!=deviceInfo.getEquSerialNo()){
						deviceInfo.setUpdateTime(deviceInfo.getUpdateTime().substring(0, 16));
						if(deviceInfo.getProgramm().contains("/"))
							deviceInfo.setProgramm(deviceInfo.getProgramm().substring(deviceInfo.getProgramm().lastIndexOf("/")+1)); //ȥ��·��
					    deviceInfo.setProgramm(StringUtils.getSubString(deviceInfo.getProgramm(), "1"));//������ƹ�����ȡǰ20���ַ�
						deviceInfo.setEquSerialNo(StringUtils.getSubString(deviceInfo.getEquSerialNo(),"4"));
					}
					searchAction(nameselected);
				}
				init=0;
			}
			
		}		
    }

    /**
     * ��ȡ��״ͼ����
     * @param equSerialNo
     */
    public void searchAction(String equSerialNo)
	{	
    	Map map=new HashMap();
		Collection<Parameter> parameters = new HashSet<Parameter> ();
		parameters.add (new Parameter ("tuser.equSerialNo", "equSerialNo", equSerialNo, Operator.EQ));
		List<Map<String, Object>> list=deviceService.getRealStatistics(parameters);
		if(null!=list&&list.size()>0){
			 map=list.get(0);
		}
		 
		 List<Long> listtu=this.createDonutModel(map);
		 if(null!=listtu&&listtu.size()>0){
			 pieData=this.toJSON(listtu); 
		 }else{
			 pieData=""; 
		 }
		
	}
    /**
	 * ��ͼ���������
	 * @param map
	 * @return
	 */
	 public  List<Long> createDonutModel(Map map) { 
			List<Long> list=new ArrayList<Long>();
	    	if(null != map && map.size() > 0){
	    		list.add((Long)map.get("runningTime"));//����ʱ��     
		        list.add((Long)map.get("stopTime"));//�ػ�ʱ��        
		        list.add((Long)map.get("prepareTime"));//׼��ʱ��      
		        list.add((Long)map.get("processTime"));//�ӹ�ʱ��      
		        list.add((Long)map.get("idleTime"));//����ʱ��      
		        list.add((Long)map.get("breakdownTime"));//����ʱ��      
		        list.add((Long)map.get("dryRunningTime"));//������ʱ��      
		        list.add((Long)map.get("cuttingTime"));//����ʱ��      
		        list.add((Long)map.get("manualRunningTime"));//�ֶ�����ʱ��    
		        list.add((Long)map.get("materialTime"));//������ʱ��    
		        list.add((Long)map.get("toolChangeTime"));//����ʱ��     
	    	}
	        return list;
	  }  
    /**
     * ��״ͼ��̨���ý��js
     * @exception JSONException
     * */
	public String toJSON(List<Long> tudate) {
		JSONObject dataToJSON = new JSONObject();
		String[] rows={"����ʱ�� "," �� �� ʱ �� ","�ӹ�ʱ��  ","׼��ʱ��  "," �� �� ʱ ��  ","����ʱ��  ","����ʱ��  ","������ʱ�� ","����ʱ��  ","�ֶ�ʱ��","������ʱ��"};
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

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

	public DeviceInfoModel getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfoModel deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getNameselected() {
		return nameselected;
	}

	public void setNameselected(String nameselected) {
		this.nameselected = nameselected;
	}

	public List<SelectItem> getNameList() {
		return nameList;
	}

	public void setNameList(List<SelectItem> nameList) {
		this.nameList = nameList;
	}

	public String getPieData() {
		return pieData;
	}

	public void setPieData(String pieData) {
		this.pieData = pieData;
	}
}

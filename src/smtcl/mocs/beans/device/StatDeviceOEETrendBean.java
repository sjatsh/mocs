package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import smtcl.mocs.common.device.pages.DataPage;
import smtcl.mocs.common.device.pages.PageListBaseBean;
import smtcl.mocs.common.device.pages.PageListDataModel;
import smtcl.mocs.model.DeviceInfoModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.StringUtils;

/**
 * ���û��鿴�豸��������ָ��ʱ��ε�OEE����
 * @����ʱ�� 2012-12-21
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name="statDeviceOEETrendBean")
@ViewScoped
public class StatDeviceOEETrendBean extends PageListBaseBean implements Serializable  {
	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr; 
	/**
	 * oee����
	 */
	private List<DeviceInfoModel> ooelist;
	/**
	 * ��ѯ���
	 */
	private IDataCollection<DeviceInfoModel> rs;
	/**
	 * ������̿��Ʊ���
	 */
	private int init=0; 
	/**
	 * �豸����������ѡ����
	 */
	private String nameselected;
	/**
	 * �豸�����������б�
	 */
	private List<SelectItem> nameList = new ArrayList<SelectItem>();
	/**
	 * ʱ��������ѡ����
	 */
	private String datelected;
	/**
	 * ʱ���������б�
	 */
	private List<SelectItem> dateList = new ArrayList<SelectItem>();
	/**
	 * ��ʼʱ��
	 */
	private Date start;
	/**
	 * ����ʱ��
	 */
	private Date end;
	/**
	 * ��ͼ����
	 */
	private String lineData;
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
	/**
	 * �豸ҵ���߼�
	 */
	private IDeviceService deviceService =(IDeviceService)ServiceFactory.getBean("deviceService");
	
	/**
	 * ��ȡ��ǰ����ڵ�
	 * @return String
	 */
	 public String getNodeStr(){
		 	HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			String node=session.getAttribute("nodeid")+"";
			this.nodeStr=node;
			List<TNodes> tn=organizationService.getTNodesById(node);
			String nodeids = organizationService.getAllTNodesId(tn.get(0));
			getnameList(nodeids);
			
		return nodeStr;
	}
	 /**
	  * ��ҳ����
	  */
	@Override
	public PageListDataModel getDefaultDataModel() {
		if (defaultDataModel == null) {
			defaultDataModel = new PageListDataModel(pageSize) {
				public DataPage fetchPage(int startRow, int pageSize) {
					rs= deviceService.getOEETEEP(startRow / pageSize+ 1,pageSize, start, end, nameselected,datelected);
					return new DataPage(rs.getTotalRows(), startRow,rs.getData());
				}
			};
		}
		return defaultDataModel;
	}

	@Override
	public PageListDataModel getExtendDataModel() {
		// TODO Auto-generated method stub
		return null;
	}  
	
	/**
	 * ��ͼ�������
	 * @param rs
	 * @return String
	 */
	 public String toJSON(IDataCollection<DeviceInfoModel> rs) {
	    	JSONObject dataToJSON = new JSONObject();
			if(null!=rs&&rs.getData().size()>0){
				String[] columns=new String[rs.getData().size()];
				String[] rows={"������","������ ","����","OEE","TEEP"};
				double[][] citydata=new double[5][columns.length];
				for(int i=0;i<columns.length;i++){
					DeviceInfoModel difm=(DeviceInfoModel)rs.getData().get(i);
					columns[i]=difm.getOeeTime();
					citydata[0][i]=Double.parseDouble(difm.getAvailability());
					citydata[1][i]=Double.parseDouble(difm.getExpressivenessOf());
					citydata[2][i]=Double.parseDouble(difm.getQuality());
					citydata[3][i]=Double.parseDouble(difm.getDayOeevalue());
					citydata[4][i]=Double.parseDouble(difm.getDayTeepvalue());
				}
					try {
						JSONArray rowKeys = new JSONArray(Arrays.asList(rows));
						dataToJSON.put("rowkeys", rowKeys);
						
						JSONArray columnKeys = new JSONArray(Arrays.asList(columns));
						dataToJSON.put("columnkeys", columnKeys);
						for (int i = 0; i < rowKeys.length(); i++) {
							double[] datas =citydata[i];
							JSONArray jsonArray =  new JSONArray();
							for (int j = 0; j < datas.length; j++) {
								jsonArray.put(datas[j]);
							}				
							dataToJSON.put("data" + i,jsonArray);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
				}
			}
			return dataToJSON.toString();
		}	
	
    /**
     * ��ȡ��ǰ�ڵ��������豸������
     */
    @SuppressWarnings("unchecked")
	public void getnameList(String nodeids){
    	if(null!=nodeStr&&!"".equals(nodeStr)){
			nameList=new ArrayList<SelectItem>();
			List<TEquipmentInfo> nlist=deviceService.getNodesAllEquName(nodeids);
			if(null!=nlist&nlist.size()>0){
				for(TEquipmentInfo teinfo:nlist){
					nameList.add(new SelectItem(teinfo.getEquSerialNo()+"",teinfo.getEquName()+""));
				}
				if(init==2){
					Date[] datea=StringUtils.convertDatea(1);
					start=datea[0];
					end=datea[1];
					nameselected=nlist.get(0).getEquSerialNo();
					System.out.println("\n nameselected:"+nameselected+"---nlist.get(0).getEquSerialNo():"+
					nlist.get(0).getEquSerialNo()+"---nlist.get(0).getEquName()"+nlist.get(0).getEquName());
					datelected="��";
					defaultDataModel = null;
				}
				init=0;
			}
		}		
    }
    
    /**
     * ��ѯʱ����ڵ�oee
     */
    public void getOEE(){
    		if(null!=nameselected&&!"".equals(nameselected)){
    		System.out.println("��ʼʱ�䣺" + start + "--����ʱ�䣺" + end + "--�豸���֣�" + nameselected+ "--ʱ�����ͣ�" + datelected);
    		defaultDataModel = null;
    		init=1;
    	}
    	this.lineData="testttt";	
    }   

    /**
     * ��ȡ�������б�ֵ
     */
   public void createSelectList(){   
	   dateList = new ArrayList<SelectItem>();
	   dateList.add(new SelectItem("��"));
	   dateList.add(new SelectItem("��"));
	   dateList.add(new SelectItem("��"));
	   dateList.add(new SelectItem("��"));
   }
   
   public String getLineData() {
		lineData=toJSON(rs);
		return lineData;
	}
	
	/**
	 * ���췽��
	 */
	public StatDeviceOEETrendBean(){
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String equSerialNo=StringUtils.getCookie(request,"equSerialNo");
		if(null!=equSerialNo&&""!=equSerialNo){
			if(null==start && null==end){
				Date[] datea=StringUtils.convertDatea(1);
				start=datea[0];
				end=datea[1];
			}
			nameselected=equSerialNo;
			datelected="��";
			defaultDataModel = null;
			init=1;
			nameList=new ArrayList<SelectItem>();
			nameList.add(new SelectItem(equSerialNo,equSerialNo));
		}else{
			init=2;
		}
		
		createSelectList();
		ooelist = new ArrayList<DeviceInfoModel>();   
	}
	
	public String getNameselected() {
		return nameselected;
	}

	public void setNameselected(String nameselected) {
		this.nameselected = nameselected;
	}

	public String getDatelected() {
		return datelected;
	}

	public void setDatelected(String datelected) {
		this.datelected = datelected;
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

	public IDeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public List<DeviceInfoModel> getOoelist() {
		return ooelist;
	}

	public List<SelectItem> getNameList() {
		return nameList;
	}

	public List<SelectItem> getDateList() {
		return dateList;
	}

	public void setLineData(String lineData) {
		this.lineData = lineData;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

}

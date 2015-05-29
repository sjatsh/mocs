package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.common.device.pages.DataPage;
import smtcl.mocs.common.device.pages.PageListBaseBean;
import smtcl.mocs.common.device.pages.PageListDataModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.StringUtils;



/**
 * �ӹ��¼�����
 * @���ߣ�JiangFeng
 * @����ʱ�䣺2013-04-26
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "StatDeviceProcessEventBean")
@ViewScoped
public class StatDeviceProcessEventBean extends PageListBaseBean implements Serializable {
    /**
     * ��ʼʱ��
     */
    private Date startTime;
    /**
     * ����ʱ��
     */
    private Date finishTime;
    /**
     * ��������
     */
    private String machineName;
    /**
     * �������Ƽ���
     */
    private List<SelectItem> machineNameList;
    /**
     * �������
     */
    private String partName;
    /**
     * �������
     */
    private List<SelectItem> partNameList = new ArrayList<SelectItem>();
    /**
     * ���ݽ����
     */
    private IDataCollection<Map<String, Object>> results;
    /**
     * ��ǰ�ڵ�
     */
    private String nodeStr;
    /**
     * ��ǰ�ڵ���ӽڵ� ID ����
     */
    private String nodeIdList;
    /**
     * �豸�ӿ�ʵ��
     */
    private IDeviceService deviceService=(IDeviceService) ServiceFactory.getBean("deviceService");
    /**
     * Ȩ�޽ӿ�ʵ��
     */
    private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");

    public String getNodeStr() {
//		com.swg.authority.cache.TreeNode currentNode = (com.swg.authority.cache.TreeNode) FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
//		if (currentNode != null) {
//			this.nodeStr = currentNode.getNodeId(); // �������ڵ��ֵ������ǰ�ڵ�nodeStr
//			nodeIdList = organizationService.getAllTNodesId(currentNode);
//		}
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String node=session.getAttribute("nodeid")+"";
        this.nodeStr=node;
        List<TNodes> tn=organizationService.getTNodesById(node);
        if(tn!=null && tn.size()>0)
        nodeIdList = organizationService.getAllTNodesId(tn.get(0));
        defaultDataModel = null;
        return nodeStr;
    }

    private Locale getLocale(){
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Locale locale = SessionHelper.getCurrentLocale(session);
        return locale;
    }
    public List<SelectItem> getMachineNameList() {
    	Locale locale = this.getLocale();
        if (null != nodeStr && !"".equals(nodeStr)) {
            machineNameList= new ArrayList<SelectItem>();
            if(locale.toString().equals("en") || locale.toString().equals("en_US")){
            	 machineNameList.add(new SelectItem("--ALL--"));
            }else{
            	 machineNameList.add(new SelectItem("--����--"));
            }
           
            List<TEquipmentInfo> nlist = deviceService.getNodesAllEquName(nodeIdList);
            for (TEquipmentInfo teinfo : nlist) {
                machineNameList.add(new SelectItem(teinfo.getEquSerialNo(),teinfo.getEquName()));
            }
            if(machineNameList.size()>0 && StringUtils.isEmpty(machineName)){
                machineName=(String) machineNameList.get(0).getValue();
                List<String> list = deviceService.getAllPartName();
                partNameList.clear();
                if(locale.toString().equals("en") || locale.toString().equals("en_US")){
                	partNameList.add(new SelectItem("--ALL--"));
               }else{
            	   partNameList.add(new SelectItem("--����--"));
               }
               
                for (String str : list) {
                    partNameList.add(new SelectItem(str));
                }
            }
        }
        return machineNameList;
    }

    /**
     * �ӹ��¼�������д�ķ�ҳ����
     */
    @Override
    public PageListDataModel getDefaultDataModel() {
        if (nodeIdList != null) {
            if (defaultDataModel == null) {
                defaultDataModel = new PageListDataModel(pageSize2) {
                    @Override
                    public DataPage fetchPage(int startRow, int pageSize) {
                        Collection<Parameter> parameters = new HashSet<Parameter>();
                        // �ж�ѡ����������Ƿ�ΪĬ��
                        if (!StringUtils.isEmpty(partName) && (partName.equals("--����--") || partName.equals("--ALL--"))) {
                            partName = "";
                        }
                        // �ж�ѡ����������Ƿ�ΪĬ��
                        if (!StringUtils.isEmpty(machineName) && (machineName.equals("--����--") || machineName.equals("--ALL--"))) {
                            machineName = "";
                        }
                        // ׷������
                        if (startTime != null) {
                            parameters.add(new Parameter("a.starttime","starttime", startTime, Operator.GE));
                        }
                        if (finishTime != null) {
                            parameters.add(new Parameter("a.finishtime","finishtime", finishTime, Operator.LE));
                        }
                        if (!StringUtils.isEmpty(machineName)) {
                            parameters.add(new Parameter("a.equSerialNo","equSerialNo", "%" + machineName + "%",Operator.LIKE));
                        }
                        if (!StringUtils.isEmpty(partName)) {
                            parameters.add(new Parameter("a.partNo","partName", "%" + partName + "%",Operator.LIKE));
                        }
                        results = deviceService.getDeviceProcessEventStat(startRow / pageSize + 1, pageSize2, parameters,nodeIdList);
                        IDataCollection<Map<String, Object>> temp = results;
                        for(int j=0;j<temp.getData().size();j++){
                            Map map = (Map) temp.getData().get(j);
                            if(map.get("cuttingTask") != null){
                                map.put("cuttingTask", StringUtils.getSubString(map.get("cuttingTask").toString(),"4"));
                            }
                        }
                        results = temp;
                        return new DataPage(results.getTotalRows(), startRow,results.getData());
                    }
                };
            }
            return defaultDataModel;
        }
        return null;
    }

    @Override
    public PageListDataModel getExtendDataModel() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * �ӹ��¼�����ҳ���ѯ��ť��Ӧ
     */
    public void getDeviceProcessEeventStartAction() {
        this.defaultDataModel = null;
    }

    /**
     * �����������
     */
    public void machineNameValueChange(ValueChangeEvent event){
        partName=event.getNewValue().toString();
        if(null!=partName&&partName!=""){
            partNameList=new ArrayList<SelectItem>();
        }else{
            partNameList.clear();
        }
        List<String> list = new ArrayList<String>();
        if(partName.trim().equals("--ALL--")){
            list = deviceService.getAllPartName();
        }else{
            Collection<Parameter> parameters = new HashSet<Parameter>();
            parameters.add(new Parameter("b.equSerialNo", "equSerialNo",partName, Operator.EQ));
            list = deviceService.getPartNameByEquName(partName);
        }
        partNameList.add(new SelectItem("--ALL--"));
        for (String str : list) {
            partNameList.add(new SelectItem(str));
        }
    }
    /**
     * �����������
     */
    public void machineNameValueChange(){
        if(null!=partName&&partName!=""){
            partNameList=new ArrayList<SelectItem>();
        }else{
            partNameList.clear();
        }
        List<String> list = new ArrayList<String>();
        if(partName.trim().equals("--ALL--")){
            list = deviceService.getAllPartName();
        }else{
            Collection<Parameter> parameters = new HashSet<Parameter>();
            parameters.add(new Parameter("b.equSerialNo", "equSerialNo",partName, Operator.EQ));
            list = deviceService.getPartNameByEquName(partName);
        }
        partNameList.add(new SelectItem("--ALL--"));
        for (String str : list) {
            partNameList.add(new SelectItem(str));
        }
    }

    /**
     * �޲ι��캯��
     */
    @SuppressWarnings("unchecked")
    public StatDeviceProcessEventBean() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String equSerialNo = StringUtils.getCookie(request, "equSerialNo");

        if (null != equSerialNo) {
            machineNameList= new ArrayList<SelectItem>();
            List<TEquipmentInfo> list = deviceService.getAllEquName("'" + equSerialNo+ "'");
            if (list != null && list.size()>0) {
                //machineNameList.add(new SelectItem(list.get(0).getEquSerialNo(), list.get(0).getEquName()));
                machineName=list.get(0).getEquSerialNo();
                System.err.println(list.get(0).getEquSerialNo());
            }
        }
        if (null == startTime && null == finishTime) {
            try {
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                Date dNow = new Date();   //��ǰʱ��
                Date dBefore = new Date();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(format.parse(format.format(dNow)));
                calendar.add(Calendar.DAY_OF_MONTH, +1);
                dBefore = calendar.getTime();
                startTime = format.parse(format.format(dNow));
                finishTime = dBefore;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        this.defaultDataModel = null;
    }

	/*-------------------------------------------------------------------------------------------------*/

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public List<SelectItem> getPartNameList() {
        return partNameList;
    }

    public IDataCollection<Map<String, Object>> getResults() {
        return results;
    }

    public void setResults(IDataCollection<Map<String, Object>> results) {
        this.results = results;
    }

    public IDeviceService getDeviceService() {
        return deviceService;
    }

    public void setMachineNameList(List<SelectItem> machineNameList) {
        this.machineNameList = machineNameList;
    }

    public void setPartNameList(List<SelectItem> partNameList) {
        this.partNameList = partNameList;
    }

    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public void setNodeStr(String nodeStr) {
        this.nodeStr = nodeStr;
    }

}

package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.common.device.JsonResponseResult;
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
 * �����¼�������Ӧ
 * @���� JiangFeng
 * @����ʱ�� 2013-04-26
 * @�޸���
 * @�޸�����
 * @�޸�˵��
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "StatDeviceMachineEventBean")
@ViewScoped
public class StatDeviceMachineEventBean extends PageListBaseBean implements Serializable {
    /**
     * ��ʼʱ��
     */
    private Date startTime;
    /**
     * ����ʱ��
     */
    private Date endTime;
    /**
     * ��������
     */
    private String machineName;
    /**
     * �������кż���
     */
    private List<SelectItem> machineNameList =new ArrayList<SelectItem>();
    /**
     * �¼�����
     */
    private String eventName;
    /**
     * �¼����Ƽ���
     */
    private List<SelectItem> eventNameList = new ArrayList<SelectItem>();
    /**
     * �����
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
     * ���ظ�ҳ��Ļ����¼���Ϣ
     */
    private String machineEvent;
    /**
     * ���ݿ�������ݵ�����
     */
    private List<String> dateChecked = new ArrayList<String>();
    /**
     * �豸�ӿ�ʵ��
     */
    private IDeviceService deviceService=(IDeviceService) ServiceFactory.getBean("deviceService");
    /**
     * Ȩ�޽ӿ�ʵ��
     */
    private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService"); // ��ȡע��

    private String dateTime=null;//��ǰͼ ��ʾ����
    private List<Map<String, Object>> datelist;//��ǰ��ѯ������ ��ļ���
    private List<Map<String, Object>> zonglist;//��ǰ��ѯ�������е����ݼ���
    @SuppressWarnings("unchecked")
    public List<SelectItem> getMachineNameList() {
        if (null != nodeStr && !"".equals(nodeStr)) {
            machineNameList =new ArrayList<SelectItem>();
            List<TEquipmentInfo> nlist = deviceService.getNodesAllEquName(nodeIdList);
            for (TEquipmentInfo teinfo : nlist) {
                machineNameList.add(new SelectItem(teinfo.getEquSerialNo(),teinfo.getEquName()));
            }
            if(machineNameList.size()>0 && StringUtils.isEmpty(machineName)){
                machineName=(String) machineNameList.get(0).getValue();
            }
        }
        System.out.println( machineName);
        return machineNameList;
    }

    private static Map<String,String> eventMap = new TreeMap<String, String>();

    static {
        eventMap.put("����","Power on");
        eventMap.put("����","Idle");
        eventMap.put("����","Running");
        eventMap.put("�ػ�","Power off");
        eventMap.put("��ͣ","Emergency");
        eventMap.put("����","Error");
        eventMap.put("��λ","Reset");
        eventMap.put("����","Maintain");
        eventMap.put("�ж�","Suspended");
//        eventMap.put("UNKNOW1","UNKNOW1");
//        eventMap.put("UNKNOW2","UNKNOW2");
    }

    public List<SelectItem> getEventNameList() {
        eventNameList.clear();
        Locale locale = this.getLocale();
        if(locale.toString().equals("en") || locale.toString().equals("en_US")){
            eventNameList.add(new SelectItem("--All--"));
            Set<String> keySet = eventMap.keySet();
            for(String key : keySet){
                eventNameList.add(new SelectItem(key,eventMap.get(key)));
            }
        }else {
            eventNameList.add(new SelectItem("--����--"));
            List<String> eventList = deviceService.getAllEventName();
            for (String str : eventList) {
                eventNameList.add(new SelectItem(str));
            }
        }
        return eventNameList;
    }

    private Locale getLocale(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return SessionHelper.getCurrentLocale(request.getSession());
    }

    public String getNodeStr() {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String node=session.getAttribute("nodeid")+"";
        this.nodeStr=node;
        List<TNodes> tn=organizationService.getTNodesById(node);
        if(tn!=null && tn.size()>0) {
            nodeIdList = organizationService.getAllTNodesId(tn.get(0));
        }
        return nodeStr;
    }

    /**
     * �����¼���д�ķ�ҳ����
     */
    @Override
    public PageListDataModel getDefaultDataModel() {
        if (defaultDataModel == null) {
            defaultDataModel = new PageListDataModel(pageSize) {
                @Override
                public DataPage fetchPage(int startRow, int pageSize) {
                    Collection<Parameter> parameters = new HashSet<Parameter>();

                    // �ж�ѡ����������Ƿ�ΪĬ��
                    if (!StringUtils.isEmpty(eventName) && (eventName.equals("--����--") || eventName.equals("--All--"))) {
                        eventName = null;
                    }
                    if (StringUtils.isEmpty(machineName)) {
                        machineName = null;
                    }
                    // ������׷��
                    if (startTime != null) {
                        parameters.add(new Parameter(" a.eventTime ","startTime", startTime, Operator.GE));
                    }
                    if (endTime != null) {
                        parameters.add(new Parameter(" a.eventTime ","endTime", endTime, Operator.LE));
                    }
                    parameters.add(new Parameter(" a.equSerialNo ","equSerialNo", machineName, Operator.LIKE));

                    if (!StringUtils.isEmpty(eventName)) {
                        parameters.add(new Parameter(" a.eventName ","eventName", eventName, Operator.LIKE));
                    }
                    results = deviceService.getDeviceMachineEventStat(getLocale(),startRow/ pageSize + 1, pageSize, parameters,eventMap);
                    return new DataPage(results.getTotalRows(), startRow,results.getData());
                }
            };
        }
        return defaultDataModel;
    }

    /**
     * �滭�����¼�ʱ��ֲ���״ͼ
     */
    public String MachineToolEventTimeDistributionChart() {
        Collection<Parameter> parameters = new HashSet<Parameter>();
        // �ж�ѡ����������Ƿ�ΪĬ��
        if (!StringUtils.isEmpty(eventName) && (eventName.equals("--����--") || eventName.equals("--All--")) ) {
            eventName = null;
        }
        if (StringUtils.isEmpty(machineName)) {
            machineName = null;
        }
        // ������׷��
        if (startTime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            //calendar.add(Calendar.DAY_OF_MONTH, -1);
            parameters.add(new Parameter(" a.eventTime ", "startTime",calendar.getTime(), Operator.GE));
        }
        if (endTime != null) {
            parameters.add(new Parameter(" a.eventTime ", "endTime", endTime,Operator.LE));
        }
        parameters.add(new Parameter(" a.equSerialNo ", "equSerialNo",machineName, Operator.LIKE));

        if (!StringUtils.isEmpty(eventName)) {
            parameters.add(new Parameter(" a.eventName ", "eventName",eventName, Operator.LIKE));
        }

        zonglist = deviceService.getDeviceMachineEventStatChart(getLocale(),parameters,eventMap);
        datelist =deviceService.getDeviceMachineEventStatChartDateCount(parameters);//ʱ���б�
        List<Map<String, Object>> rslist=new ArrayList<Map<String, Object>>();//��������

        if(null!=datelist&&datelist.size()>0){
            dateTime=datelist.get(0).get("EVENTTIME").toString();
        }
        for(Map<String, Object> map:zonglist){
            String eventtime=map.get("EVENTTIME").toString().substring(0, 10);
            if(eventtime.trim().equals(dateTime.trim())){
                rslist.add(map);
            }
        }
        JsonResponseResult tt=new JsonResponseResult();
        tt.setContent(rslist);
        return tt.toJsonString();
    }
    public void next(){
        List<Map<String, Object>> rslist=new ArrayList<Map<String, Object>>();//��������
        boolean bool=false;
        for(Map<String, Object> map:datelist){
            String eventtime=map.get("EVENTTIME").toString();
            if(eventtime.equals(dateTime)){
                bool=true;
                continue;
            }
            if(bool){
                dateTime=eventtime;
                bool=false;
                break;
            }
        }
        for(Map<String, Object> map:zonglist){
            String eventtime=map.get("EVENTTIME").toString().substring(0, 10);
            if(eventtime.equals(dateTime)){
                rslist.add(map);
            }
        }
        JsonResponseResult tt=new JsonResponseResult();
        tt.setContent(rslist);
        this.machineEvent= tt.toJsonString();

    }

    public void black(){
        List<Map<String, Object>> rslist=new ArrayList<Map<String, Object>>();//��������
        boolean bool=false;
        for(int i=0;i<datelist.size();i++){
            Map<String, Object> map=datelist.get(i);
            String eventtime=map.get("EVENTTIME").toString();
            if(eventtime.equals(dateTime)){
                if(i-1>-1){
                    dateTime=datelist.get(i-1).get("EVENTTIME").toString();
                    break;
                }
            }
        }
        for(Map<String, Object> map:zonglist){
            String eventtime=map.get("EVENTTIME").toString().substring(0, 10);
            if(eventtime.equals(dateTime)){
                rslist.add(map);
            }
        }
        JsonResponseResult tt=new JsonResponseResult();
        tt.setContent(rslist);
        this.machineEvent= tt.toJsonString();
    }
    @Override
    public PageListDataModel getExtendDataModel() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * �����¼�ҳ���ѯ��ť��Ӧ
     */
    public void getDeviceMachineEventStatAction() {
        this.machineEvent = MachineToolEventTimeDistributionChart();
        this.defaultDataModel = null;
    }

    /**
     * �޲ι��캯��
     */
    public StatDeviceMachineEventBean() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String equSerialNo = StringUtils.getCookie(request, "equSerialNo");
        if (!StringUtils.isEmpty(equSerialNo)) {
            machineNameList= new ArrayList<SelectItem>();
            List<TEquipmentInfo> list = deviceService.getAllEquName("'" + equSerialNo+ "'");
            if (list != null && list.size()>0) {
                machineNameList.add(new SelectItem(equSerialNo, list.get(0).getEquName()));
                machineName=equSerialNo;
            }
        }
        if (null == startTime && null == endTime) {
            try {
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                Date dNow = new Date();   //��ǰʱ��
                Date dBefore = new Date();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(format.parse(format.format(dNow)));
                calendar.add(Calendar.DAY_OF_MONTH, +1);
                dBefore = calendar.getTime();
                startTime = format.parse(format.format(dNow));
                endTime = dBefore;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        getDeviceMachineEventStatAction();
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public void setEventNameList(List<SelectItem> eventNameList) {
        this.eventNameList = eventNameList;
    }

    public String getEventName() {
        return eventName;
    }

    public String getMachineEvent() {
        return machineEvent;
    }

    public void setMachineEvent(String machineEvent) {
        this.machineEvent = machineEvent;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setMachineNameList(List<SelectItem> machineNameList) {
        this.machineNameList = machineNameList;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public IDeviceService getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public IDataCollection<Map<String, Object>> getResults() {
        return results;
    }

    public void setResults(IDataCollection<Map<String, Object>> results) {
        this.results = results;
    }

    public void setNodeStr(String nodeStr) {
        this.nodeStr = nodeStr;
    }

}

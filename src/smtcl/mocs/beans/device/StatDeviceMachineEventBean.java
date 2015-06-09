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
 * 机床事件分析对应
 * @作者 JiangFeng
 * @创建时间 2013-04-26
 * @修改者
 * @修改日期
 * @修改说明
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "StatDeviceMachineEventBean")
@ViewScoped
public class StatDeviceMachineEventBean extends PageListBaseBean implements Serializable {
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 机床名称
     */
    private String machineName;
    /**
     * 机床序列号集合
     */
    private List<SelectItem> machineNameList =new ArrayList<SelectItem>();
    /**
     * 事件名称
     */
    private String eventName;
    /**
     * 事件名称集合
     */
    private List<SelectItem> eventNameList = new ArrayList<SelectItem>();
    /**
     * 结果集
     */
    private IDataCollection<Map<String, Object>> results;
    /**
     * 当前节点
     */
    private String nodeStr;
    /**
     * 当前节点的子节点 ID 集合
     */
    private String nodeIdList;
    /**
     * 返回给页面的机床事件信息
     */
    private String machineEvent;
    /**
     * 数据库存在数据的日期
     */
    private List<String> dateChecked = new ArrayList<String>();
    /**
     * 设备接口实例
     */
    private IDeviceService deviceService=(IDeviceService) ServiceFactory.getBean("deviceService");
    /**
     * 权限接口实例
     */
    private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService"); // 获取注入

    private String dateTime=null;//当前图 显示的天
    private List<Map<String, Object>> datelist;//当前查询条件的 天的集合
    private List<Map<String, Object>> zonglist;//当前查询条件所有的数据集合
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
        eventMap.put("开机","Power on");
        eventMap.put("空闲","Idle");
        eventMap.put("运行","Running");
        eventMap.put("关机","Power off");
        eventMap.put("急停","Emergency");
        eventMap.put("故障","Error");
        eventMap.put("复位","Reset");
        eventMap.put("保持","Maintain");
        eventMap.put("中断","Suspended");
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
            eventNameList.add(new SelectItem("--所有--"));
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
     * 机床事件重写的分页方法
     */
    @Override
    public PageListDataModel getDefaultDataModel() {
        if (defaultDataModel == null) {
            defaultDataModel = new PageListDataModel(pageSize) {
                @Override
                public DataPage fetchPage(int startRow, int pageSize) {
                    Collection<Parameter> parameters = new HashSet<Parameter>();

                    // 判断选择的下拉框是否为默认
                    if (!StringUtils.isEmpty(eventName) && (eventName.equals("--所有--") || eventName.equals("--All--"))) {
                        eventName = null;
                    }
                    if (StringUtils.isEmpty(machineName)) {
                        machineName = null;
                    }
                    // 条件的追加
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
     * 绘画机床事件时间分布柱状图
     */
    public String MachineToolEventTimeDistributionChart() {
        Collection<Parameter> parameters = new HashSet<Parameter>();
        // 判断选择的下拉框是否为默认
        if (!StringUtils.isEmpty(eventName) && (eventName.equals("--所有--") || eventName.equals("--All--")) ) {
            eventName = null;
        }
        if (StringUtils.isEmpty(machineName)) {
            machineName = null;
        }
        // 条件的追加
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
        datelist =deviceService.getDeviceMachineEventStatChartDateCount(parameters);//时间列表
        List<Map<String, Object>> rslist=new ArrayList<Map<String, Object>>();//返回数据

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
        List<Map<String, Object>> rslist=new ArrayList<Map<String, Object>>();//返回数据
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
        List<Map<String, Object>> rslist=new ArrayList<Map<String, Object>>();//返回数据
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
     * 机床事件页面查询按钮响应
     */
    public void getDeviceMachineEventStatAction() {
        this.machineEvent = MachineToolEventTimeDistributionChart();
        this.defaultDataModel = null;
    }

    /**
     * 无参构造函数
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
                Date dNow = new Date();   //当前时间
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

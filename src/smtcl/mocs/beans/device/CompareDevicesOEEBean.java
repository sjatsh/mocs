package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.model.DeviceInfoModel;
import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 用户查询多台机床之间的OEE走势比较
 * @作者 JiangFeng
 * @创建时间 2013-04-26
 * @修改者
 * @修改日期
 * @修改说明
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "CompareDevicesOEEBean")
@ViewScoped
public class CompareDevicesOEEBean implements Serializable {
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 时间单位条件
     */
    private String dateTime="天";
    /**
     * 时间单位条件集合
     */
    private List<SelectItem> dateTimeList = new ArrayList<SelectItem>();
    /**
     * 机床序列号
     */
    private String[] serNames;
    /**
     * 机床序列号集合
     */
    private List<SelectItem> serNamesList = new ArrayList<SelectItem>();
    /**
     * 节点集
     */
    private String nodeids;
    /**
     * 显示的折线图类型
     */
    private String otBy = "OEE";
    /**
     * 显示的折线图类型集合
     */
    private List<SelectItem> otByList = new ArrayList<SelectItem>();
    /**
     * 设备接口实例
     */
    private IDeviceService deviceService=(IDeviceService)ServiceFactory.getBean("deviceService");
    /**
     * 结果集
     */
    private List<DeviceInfoModel> result;
    /**
     * 当前节点
     */
    private String nodeStr;
    /**
     * 曲线图的加载
     */
    private String chartData;
    /**
     * 权限接口实例
     */
    private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");

    public List<SelectItem> getOtByList() {
        otByList.clear();
        otByList.add(new SelectItem("OEE"));
        otByList.add(new SelectItem("TEEP"));
        return otByList;
    }

    public List<SelectItem> getDateTimeList() {
        Locale locale = this.getLocale();
        dateTimeList.clear();// 为时间单位条件添加数据
        if(locale.toString().equals("en") || locale.toString().equals("en_US")){
            dateTimeList.add(new SelectItem("Day"));
            dateTimeList.add(new SelectItem("Week"));
            dateTimeList.add(new SelectItem("Month"));
            dateTimeList.add(new SelectItem("Year"));
        }else {
            dateTimeList.add(new SelectItem("天"));
            dateTimeList.add(new SelectItem("周"));
            dateTimeList.add(new SelectItem("月"));
            dateTimeList.add(new SelectItem("年"));
        }
        return dateTimeList;
    }

    public String getNodeStr() {
		/*com.swg.authority.cache.TreeNode currentNode = (com.swg.authority.cache.TreeNode) FaceContextUtil.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			this.nodeStr = currentNode.getNodeId(); // 将单机节点的值赋给当前节点nodeStr
			nodeids = organizationService.getAllTNodesId(currentNode);
		}*/
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String node=session.getAttribute("nodeid")+"";
        this.nodeStr=node;
        List<TNodes> tn=organizationService.getTNodesById(node);
        if(tn!=null && tn.size()>0) {
            nodeids = organizationService.getAllTNodesId(tn.get(0));
        }
        return nodeStr;
    }

    @SuppressWarnings("unchecked")
    public List<SelectItem> getSerNamesList() {
        serNames=null;
        // 设备总览页面跳转所需要的数据加载
        String equSerialStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("equSerialStr");
        if (null != equSerialStr && !"".equals(equSerialStr)) {
            serNames = null;
            serNamesList.clear();
            String[] st = equSerialStr.split(",");
            String s = "";
            for (String aSt : st) {
                s += "'" + aSt + "',";
            }
            List<TEquipmentInfo> strList = deviceService.getAllEquName(s.substring(0, s.length() - 1));
            for (TEquipmentInfo str : strList) {
                serNamesList.add(new SelectItem(str.getEquSerialNo() + "", str.getEquSerialNo() + ""));
            }
        }

        if (null != nodeStr && !"".equals(nodeStr)) {
            serNames = null;
            serNamesList.clear();
            List<TEquipmentInfo> strList = deviceService.getNodesAllEquName(nodeids);
            for (TEquipmentInfo str : strList) {
                serNamesList.add(new SelectItem(str.getEquSerialNo() + "",str.getEquSerialNo() + ""));
            }
        }
        return serNamesList;
    }

    /**
     * 页面点击查询按钮响应
     */
    public void getDevicesCompareAction() {
        if (null!=startTime  && null != endTime  && serNames.length != 0) {
            result = deviceService.getDevicesOEECompare(startTime, endTime, dateTime, serNames);
            // diagramOnLoad(result);
            compareDevicesOeeChart();
        }else{
            chartData=null;
        }
    }

    /**
     * 点击复选框与单选按钮
     */
    public void selectNo() {
        if(serNames.length > 5) {
            return;
        }
        getDevicesCompareAction();
    }

    /**
     * 获取查询条件中的数据信息
     * @return 集合DeviceInfoModel数据
     */
    public Map<String, List<DeviceInfoModel>> chartData() {
        Map<String, List<DeviceInfoModel>> map = new HashMap<String, List<DeviceInfoModel>>();
        for (String serName : serNames) {
            List<DeviceInfoModel> mList = new ArrayList<DeviceInfoModel>();
            for (DeviceInfoModel de : result) {
                if (null!=de.getEquSerialNo()&&null!=serName&&serName.equals(de.getEquSerialNo())) {
                    DeviceInfoModel dInfoModel = new DeviceInfoModel();
                    dInfoModel.setOeeTime(de.getOeeTime());
                    dInfoModel.setDayOeevalue(de.getDayOeevalue());
                    dInfoModel.setDayTeepvalue(de.getDayTeepvalue());
                    mList.add(dInfoModel);
                }
            }
            map.put(serName, mList);
        }
        return map;
    }

    /**
     * 加载曲线图形
     */
    public String compareDevicesOeeChart() {
        Map<String, Object> data = new HashMap<String, Object>();

        List<String> list = new ArrayList<String>();
        for (DeviceInfoModel de : result) {
            System.out.println(de.getOeeTime());
            if (!list.contains(de.getOeeTime())) {
                list.add(de.getOeeTime());
            }
        }

        // 横坐标日期数组 2013
        String[] colDate = list.toArray(new String[1]);

        double[][] devicesOEE = new double[serNames.length][colDate.length];
        double[][] devicesTEEP = new double[serNames.length][colDate.length];
        Map<String, List<DeviceInfoModel>> dMap = this.chartData();
        for (int i = 0; i < serNames.length; i++) {
            int j = 0;


                for (DeviceInfoModel dev : dMap.get(serNames[i])) {
                    if(j < colDate.length){
                        if (dev.getOeeTime() == null || dev.getOeeTime().equals("")) {
                            devicesOEE[i][j] = 0;
                            devicesTEEP[i][j] = 0;
                            j++;
                        } else {
                            devicesOEE[i][j] = Double.parseDouble(dev.getDayOeevalue());
                            devicesTEEP[i][j] = Double.parseDouble(dev.getDayTeepvalue());
                            j++;
                        }

                    }
            }

        }
        Locale locale = this.getLocale();
        data.put("colData", colDate);
        if(otBy.trim().toUpperCase().trim().equals("OEE")){
            data.put("otValue", devicesOEE);

            dateTimeList.clear();// 为时间单位条件添加数据
            if(locale.toString().equals("en") || locale.toString().equals("en_US")){
                data.put("title", "Comparison of OEE machine tools");
            }else {
                data.put("title", "多台机床OEE比较");
            }

        }else{
            data.put("otValue", devicesTEEP);
            if(locale.toString().equals("en") || locale.toString().equals("en_US")){
                data.put("title", "Comparison of TEEP machine tools");
            }else {
                data.put("title", "多台机床TEEP比较");
            }

        }
        data.put("serNames", serNames);
        data.put("devicesSize", serNames.length);

        Gson gson = new GsonBuilder().serializeNulls().create();
        chartData = gson.toJson(data);
        System.out.println(chartData);
        return gson.toJson(data);
    }

    /**
     * 无参构造函数
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public CompareDevicesOEEBean() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String equSerialNo = StringUtils.getCookie(request, "str");
        IDeviceService ds = (IDeviceService) ServiceFactory.getBean("deviceService"); // 获取注入;
        if (null != equSerialNo) {
            /*String equ[] = equSerialNo.split("%2C");
            List li = new ArrayList();
            for (String tt : equ) {
                System.out.println(tt + "---");
                li.add(tt);
            }
            String hqlvalve = StringUtils.returnHqlIN(li);
            List<TEquipmentInfo> list = ds.getAllEquName(hqlvalve);
            if (list != null) {
                serNames = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    serNamesList.add(new SelectItem(list.get(i).getEquSerialNo(), list.get(i).getEquSerialNo()));
                    serNames[i] = list.get(i).getEquSerialNo();
                    System.err.println(list.get(i).getEquSerialNo());
                }
            }*/
            getNodeStr();
            List<TEquipmentInfo> strList = deviceService.getNodesAllEquName(nodeids);
            serNames = new String[strList.size()];
            int i=0;
            for (TEquipmentInfo str : strList) {
                serNamesList.add(new SelectItem(str.getEquSerialNo() + "",str.getEquSerialNo() + ""));
                if(i<5){
                	serNames[i]=str.getEquSerialNo();
                	i++;
                }
            }
        }

        if (null == startTime && null == endTime) {
            Date[] datea = StringUtils.convertDatea(1);
            startTime = datea[0];
            endTime = datea[1];
        }
        getDevicesCompareAction();
    }

    private Locale getLocale(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return SessionHelper.getCurrentLocale(request.getSession());
    }

	/*----------------------------------------------------------------------------------*/

    public void setSerNamesList(List<SelectItem> serNamesList) {
        this.serNamesList = serNamesList;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setDateTimeList(List<SelectItem> dateTimeList) {
        this.dateTimeList = dateTimeList;
    }

    public String getChartData() {
        return chartData;
    }

    public void setChartData(String chartData) {
        this.chartData = chartData;
    }

    public String[] getSerNames() {
        return serNames;
    }

    public void setSerNames(String[] serNames) {
        this.serNames = serNames;
    }

    public String getOtBy() {
        return otBy;
    }

    public void setOtBy(String otBy) {
        this.otBy = otBy;
    }

    public void setOtByList(List<SelectItem> otByList) {
        this.otByList = otByList;
    }

    public IDeviceService getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public List<DeviceInfoModel> getResult() {
        return result;
    }

    public void setResult(List<DeviceInfoModel> result) {
        this.result = result;
    }

    public void setNodeStr(String nodeStr) {
        this.nodeStr = nodeStr;
    }

}

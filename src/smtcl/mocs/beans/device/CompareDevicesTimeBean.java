package smtcl.mocs.beans.device;

import java.text.DecimalFormat;
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
import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.StringUtils;



/**
 * ʱ��Ƚ�
 * @����ʱ�� 2012-04-26
 * @���� gaozhoujun
 * @�޸���
 * @�޸�����
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name="CompareDevicesTimeBean")
@ViewScoped
public class CompareDevicesTimeBean {
    /**
     * ��ʼʱ��
     */
    private Date start;
    /**
     * ����ʱ��
     */
    private Date end;
    /**
     * ��ǰ�ڵ�
     */
    private String nodeStr;
    /**
     * �豸ҵ���߼�
     */
    private IDeviceService deviceService = (IDeviceService)ServiceFactory.getBean("deviceService");

    /**
     * Ȩ�޽ӿ�ʵ��
     */
    private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");

    /**
     * ��ѡ����ʾҳ������
     */
    private List<SelectItem> serNamesList = new ArrayList<SelectItem>();

    /**
     * ��ѡ���������
     */
    private String[] serNames;
    /**
     * �豸�������
     */
    private Map<String, Object> map;

    /**
     * ��״ͼ��������
     */
    private  String jsonData;

    /**
     * ͼ���豸���Ƽ���
     */
    private  List<String>  allequName =new ArrayList<String>();

    /**
     * ͼ�����ݼ���
     */
    private  double[][] arraylist=new double[5][];

    /**
     * ��ǰ�ڵ������нڵ�id
     */
    private String nodeids;

    /**
     * �ж��Ƿ�ǰ�ڵ�
     * */
    private String nodeValue;

    /**
     * ��ѡ���������
     */
    private List<String> selectedDevices=new ArrayList<String>();

    /**
     * ��ѡ��ѡ���¼�
     */
    public void itemSelect(){
        if(serNames.length > 5) {
            return;
        }
        //��ȡ��ѡ���ֵ
        queryAction();
    }
    /**
     * ͼ���������
     * @param list list
     */
    public void createCartesianChartModel(List<Map<String, Object>> list){
        if(null != allequName && allequName.size() > 0){//���ͼ���豸���Ƽ���
            allequName.clear();
        }

        double[] stop;
        stop=new double[list.size()];//�ػ�
        double[] prepare;
        prepare=new double[list.size()];//׼��
        double[] process;
        process=new double[list.size()];//�ӹ�
        double[] idle;
        idle=new double[list.size()];//����
        double[] breakdown;
        breakdown=new double[list.size()];//����

        DecimalFormat decimal = new DecimalFormat("#.##");// ��С����2λ
        Long total=null;//ͼ��չ��ʱ���
        double numbera=0;//׼��ʱ�䴦��
        double numberb=0;//�ӹ�ʱ�䴦��
        double numberc=0;//���д���ʱ�䴦��
        double numbere=0;//�ػ�ʱ�䴦��
        double numberf=0;//����ʱ�䴦��
        for(int i=0;i<list.size();i++){
            total=(Long)list.get(i).get("prepareTime")+(Long)list.get(i).get("processTime")+(Long)list.get(i).get("idleTime")+(Long)list.get(i).get("stopTime")
                    +(Long)list.get(i).get("breakdownTime");
            System.out.println("total=="+total);
            numbera=Double.parseDouble(list.get(i).get("prepareTime").toString());
            numberb=Double.parseDouble(list.get(i).get("processTime").toString());
            numberc=Double.parseDouble(list.get(i).get("idleTime").toString());
            numbere=Double.parseDouble(list.get(i).get("stopTime").toString());
            numberf=Double.parseDouble(list.get(i).get("breakdownTime").toString());
            prepare[i]=Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numbera/total))*100));
            process[i]=Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numberb/total))*100));
            idle[i]=Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numberc/total))*100));
            stop[i]=Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numbere/total))*100));
            breakdown[i]=100-(Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numbera/total))*100)))-(Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numberb/total))*100)))-(Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numberc/total))*100)))
                    -(Double.parseDouble(decimal.format(Double.parseDouble(decimal.format(numbere/total))*100)));
            allequName.add(list.get(i).get("equName").toString());//��װ�豸����
        }
        if(null != arraylist && arraylist.length > 0){
            arraylist=new double[5][];
        }
        //��װ2ά����
        for(int i=0;i<arraylist.length;i++){
            if(i == 0){
                arraylist[i]=stop;
            }
            if(i == 1){
                arraylist[i]=prepare;
            }
            if(i == 2){
                arraylist[i]=process;
            }
            if(i == 3){
                arraylist[i]=idle;
            }
            if(i == 4){
                arraylist[i]=breakdown;
            }
        }
    }
    /**
     * ��ǰ�ڵ��µ��豸
     * @param nodeids �ڵ�ID
     */
    @SuppressWarnings("unchecked")
    public void getnameList(String nodeids){

        if(null != serNamesList && serNamesList.size() > 0){
            serNamesList.clear();
        }
        if(null != serNames && serNames.length > 0){
            serNames=null;
        }
        if(null != selectedDevices && selectedDevices.size() > 0){
            selectedDevices.clear();
        }

        if(null != nodeStr && !"".equals(nodeStr)){
            List<TEquipmentInfo> nlist=deviceService.getNodesAllEquName(nodeids);
            for(int i=0;i<nlist.size();i++){
                TEquipmentInfo teinfo=nlist.get(i);
                if(null!=teinfo){
                    serNamesList.add(new SelectItem(teinfo.getEquSerialNo(),teinfo.getEquSerialNo()));//�����ݽ�����ѡ����ʾ
                    if(i < 5){
                        selectedDevices.add(teinfo.getEquSerialNo());
                    }
                }
            }
            /**
             * �����ڵ㣬��ʾ5����ѡ��
             */
            serNames=new String[selectedDevices.size()];
            for (int i = 0; i < selectedDevices.size(); i++) {
                serNames[i] = selectedDevices.get(i);
            }
        }
    }
    /**
     * ��ѯ����
     */
    public void queryAction(){

        Collection<Parameter> parameters = new HashSet<Parameter>();
        //ʱ�����
        for(String tt:serNames){
            System.out.println(tt);
        }
        //��ѡ��Ĺ���
        if(null!=serNames && serNames.length > 0){//��ѡ��ѡ��
            parameters.add(new Parameter("te.equSerialNo","equSerialNo", serNames, Operator.IN));
        }else{//��ѡ��ѡ��
            parameters.add (new Parameter ("te.equSerialNo", "equSerialNo", null, Operator.IN));//��ǰ�ڵ����豸����
        }

        List<Map<String, Object>> list=deviceService.compareDevicesTime(parameters,StringUtils.formatDate(start, 2),StringUtils.formatDate(end, 2));
        if(null != list && list.size()>0){
            createCartesianChartModel(list);//����ͼ��������ݷ���
        }else{
            arraylist[0]=null;
        }
        Map<String,Object> barModel=new HashMap<String,Object>();
        if(null != arraylist[0]){
            jsonData=this.toJSON(barModel,arraylist); //������״ͼ��̨���÷���
        }else{
            jsonData=null;
        }
    }

    /**
     * ��״ͼ��̨���ý��js
     * */
    public String toJSON(Map<String,Object> chartModel,double[][] tudate) {
        Locale locale = this.getLocale();
        JSONObject dataToJSON = new JSONObject();

        try {String[] rows;
            if(locale.toString().equals("en") || locale.toString().equals("en_US")){
                rows = new String[]{"Ready","Processing","Idle","Shutdown","Fault"};
                dataToJSON.put("title", "Time comparison");
            }else {
                rows = new String[]{"׼��", "�ӹ�", "����", "�ػ�", "����"};
                dataToJSON.put("title", "��̨�豸ʱ��Ƚ�");
            }


            JSONArray rowKeys = new JSONArray(Arrays.asList(rows));
            dataToJSON.put("rowKeys", rowKeys);

            JSONArray columnKeys = new JSONArray(allequName);
            dataToJSON.put("columnKeys", columnKeys);
            for (int i = 0; i < rowKeys.length(); i++) {
                double[] datas =tudate[i];
                JSONArray jsonArray =  new JSONArray();
                for (double data : datas) {
                    jsonArray.put(data);
                }
                dataToJSON.put("data" + i,jsonArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataToJSON.toString();
    }

    /**
     * ��ȡ������ �б�
     * @return  List<SelectItem>
     */
    public List<SelectItem> getSerNamesList() {
        if(null != nodeids){
            getnameList(nodeids);
        }
        return serNamesList;
    }
    /**
     * ��ȡ��ǰ����ڵ�
     * @return String
     */
    public String getNodeStr() {

        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String node=session.getAttribute("nodeid")+"";
        this.nodeStr=node;
        List<TNodes> tn=organizationService.getTNodesById(node);
        if(tn != null && tn.size()>0) {
            nodeids = organizationService.getAllTNodesId(tn.get(0));
        }
        if(!nodeStr.equals(nodeValue)){
            getnameList(nodeids);
            queryAction();
        }
        nodeValue=nodeStr;
        return nodeStr;
    }
    /**
     * ���췽��
     */
    @SuppressWarnings("unchecked")
    public CompareDevicesTimeBean(){

        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();
        String equSerialNo = StringUtils.getCookie(request, "str");
        IDeviceService ds = (IDeviceService) ServiceFactory
                .getBean("deviceService"); // ��ȡע��;
        if (null != equSerialNo) {
            String equ[] = equSerialNo.split("%2C");
            List li = new ArrayList();
            for (String tt : equ) {
                System.out.println(tt + "---");
                li.add(tt);
            }
            String hqlvalve = StringUtils.returnHqlIN(li);
            List<TEquipmentInfo> list = ds.getAllEquName(hqlvalve);
            if (list != null) {
                serNamesList.clear();
                serNames = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    serNamesList.add(new SelectItem(list.get(i).getEquSerialNo(), list.get(i).getEquSerialNo()));
                    serNames[i] = list.get(i).getEquSerialNo();
                }
            }
        }
        //Ĭ��ʱ��ֵ :���һ����
        if(null==start && null==end){
            Date[] datea=StringUtils.convertDatea(1);
            start=datea[0];
            end=datea[1];
        }
        queryAction();
    }

    private Locale getLocale(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return SessionHelper.getCurrentLocale(request.getSession());
    }


    public void setSerNamesList(List<SelectItem> serNamesList) {
        this.serNamesList = serNamesList;
    }

    public String[] getSerNames() {
        return serNames;
    }

    public void setSerNames(String[] serNames) {
        this.serNames = serNames;
    }

    public String getJsonData() {
        return jsonData;
    }

//    public List<String> getAllequName() {
//        return allequName;
//    }
//
//    public void setAllequName(List<String> allequName) {
//        this.allequName = allequName;
//    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public IOrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(IOrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public void setNodeStr(String nodeStr) {
        this.nodeStr = nodeStr;
    }

    public IDeviceService getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
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

    public String getNodeids() {
        return nodeids;
    }

    public void setNodeids(String nodeids) {
        this.nodeids = nodeids;
    }

//    public List<String> getSelectedDevices() {
//        return selectedDevices;
//    }
//
//    public void setSelectedDevices(List<String> selectedDevices) {
//        this.selectedDevices = selectedDevices;
//    }

}

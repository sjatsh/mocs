package smtcl.mocs.beans.device;

import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
 * ��̨�豸ʱ���OEE����
 * @����ʱ�� 2012-04-26
 * @���� gaozhoujun
 * @�޸��� songkaiang
 * @�޸����� 2015-1-30
 * @�޸�˵�� ��̨���ݹ��ʻ�
 * @version V1.0
 */
@ManagedBean(name = "StatDeviceOEEBean")
@ViewScoped
public class StatDeviceOEEBean {
    /**
     * ��ʼʱ��
     */
    private Date start;
    /**
     * ����ʱ��
     */
    private Date end;
    /**
     * ���ݿ�����
     */
    private Map<String, Object> map;
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
     * �豸�б�������
     */
    private List<SelectItem> nameList = new ArrayList<SelectItem>();
    /**
     * ��״ͼ��������
     */
    public String jsonData;
    /**
     * �ж��Ƿ�ǰ�ڵ�
     */
    private String nodeValue;
    /**
     * ��ʱ��
     */
    private Long totalTime;
    /**
     * Ȩ�޽ӿ�ʵ��
     */
    private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");
    /**
     *�豸ҵ���߼�
     */
    private IDeviceService deviceService = (IDeviceService) ServiceFactory.getBean("deviceService");

    /**
     * ��ȡ��ǰ����ڵ�
     * @return
     */
    public String getNodeStr() {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String node=session.getAttribute("nodeid")+"";
        if (null != nameList && nameList.size() > 0) {
            nameList.clear();
        }
        this.nodeStr = node; // �������ڵ��ֵ������ǰ�ڵ�nodeStr
        List<TNodes> tn=organizationService.getTNodesById(node);
        if(tn !=null && tn.size()>0) {
            String nodeids = organizationService.getAllTNodesId(tn.get(0));
            getnameList(nodeids);
        }
        nodeValue = nodeStr;
        equSerialNoPan=null;//���
        return nodeStr;
    }

    /**
     * ��ǰ�ڵ��µ��豸 ������
     *
     * @param nodeids
     */
    @SuppressWarnings("unchecked")
    public void getnameList(String nodeids) {
        if (null != nodeStr && !"".equals(nodeStr)) {
            List<TEquipmentInfo> nlist = deviceService.getNodesAllEquName(nodeids);
            for (TEquipmentInfo teinfo : nlist) {
                if (null != teinfo) {
                    nameList.add(new SelectItem(teinfo.getEquSerialNo(), teinfo.getEquName()));
                }
                if (!nodeStr.equals(nodeValue) && null == equSerialNoPan) {// ���ڵ�
                    equSerialNo = nlist.get(0).getEquSerialNo();
                }
            }
        }
        selectBeanOEE();
    }


    /**
     * ��ѯOEE����
     */
    public void selectBeanOEE() {
        System.out.println("selectBeanOEE()====");
        System.out.println("selectBeanOEE()=====ʱ��֮��===="+(end.getTime()-start.getTime())/1000);
        totalTime=(end.getTime()-start.getTime())/1000;
        if (null != map && map.size() > 0) {
            map.clear();
        }
//        Collection<Parameter> parameters = new HashSet<Parameter>();
//        // ��ʼʱ������
//        if (null != start) {
//            parameters.add(new Parameter("tuserOne.caclDate", "start", start,Operator.GE));
//        }
//        // ����ʱ������
//        if (null != end) {
//            parameters.add(new Parameter("tuserOne.caclDate", "end", end,Operator.LE));
//        }
//        parameters.add(new Parameter("tuserOne.equSerialNo", "equSerialNo",equSerialNo, Operator.EQ));
        List<Map<String, Object>> list;
        list = deviceService.selectServiceOEE(StringUtils.formatDate(start, 2),StringUtils.formatDate(end, 2),equSerialNo);
        double[][] tudate = { { 0.0, 0.0, 0.0, 0.0 }, { 0.0, 0.0, 0.0, 0.0 },{ 0.0, 0.0, 0.0, 0.0 }, { 0.0, 0.0, 0.0, 0.0 } };
        if (null != list && list.size() > 0) {
            map = list.get(0);
            if (null != map && map.size() > 0) {
                Locale locale = this.getLocale();
                map.put("totalTimea", StringUtils.SecondIsDDmmss(totalTime,locale)); // ҳ����ʾ��ʹ��ʱ��
                map.put("totalTime",Double.parseDouble(totalTime.toString()));
                deviceService.convertData(map,locale);// ��������ת������
                tudate = createHistogram(map);// ������״ͼ���ݼ��ط���
            }
        }
        if (null != list && list.size() > 0) {
            Map<String, Object> barModel = new HashMap<String, Object>();
            jsonData = this.toJSON(barModel, tudate); // ������״ͼ��̨���÷���
        } else {
            jsonData = null;
        }
    }

    /**
     * ��״ͼ���ݼ���
     * @return double[][]
     */
    private double[][] createHistogram(Map<String, Object> map) {
        double  display=Double.parseDouble(map.get("display").toString());
        double availability=Double.parseDouble(map.get("availability").toString());
        double quality=Double.parseDouble(map.get("quality").toString());
        int availabilityint=(int)(100 - (availability * 100));
        int playBilityint=(int)(100 - (display * (availability* 100))-availabilityint);
        int quaAvaPlayint=(int)(100 - (quality * (display * (availability * 100)))- availabilityint - playBilityint);
        // ʵ������ Ч����ʧ ������ʧ ͣ����ʧ
        return new double[][]{
                {100 - playBilityint - quaAvaPlayint - availabilityint,	100 - playBilityint - availabilityint,	100 - availabilityint,100},
                {playBilityint,playBilityint,0,0 },
                {quaAvaPlayint,0,0,0 },
                {availabilityint,availabilityint,availabilityint,0}
        };
    }

    /**
     * ��״ͼ��̨���ý��js
     * @return String
     */
    public String toJSON(Map<String, Object> chartModel, double[][] tudate) {
        JSONObject dataToJSON = new JSONObject();
        Locale locale = this.getLocale();
        String[] columns;
        String[] rows;
        if(locale.toString().equals("en") || locale.toString().equals("en_US")){
            columns = new String[]{ "Quality Analysis", "Efficiency Analysis", "Usable Rate", "Theoretical Yield" };
            rows = new String[]{ "Actual Production", "Efficiency Loss", "Quality Loss", "Stop Loss" };
        }else{
            columns = new String[]{ "��������", "Ч�ʷ���", "���ñ���", "���۲���" };
            rows = new String[]{ "ʵ������", "Ч����ʧ", "������ʧ", "ͣ����ʧ" };
        }

        try {
            JSONArray columnKeys = new JSONArray(Arrays.asList(columns));
            dataToJSON.put("columnKeys", columnKeys);

            JSONArray rowKeys = new JSONArray(Arrays.asList(rows));
            dataToJSON.put("rowKeys", rowKeys);
            dataToJSON.put("title", "�豸ȫ��Ч�ʷ���");

//            for (int i = 0; i < tudate.length; i++) {
//                for (int y = 0; y < tudate[i].length; y++) {
//                    System.out.print("tudate[][]==" + tudate[i][y] + "  ");
//                }
//                System.out.print("\n");
//            }

            for (int i = 0; i < rowKeys.length(); i++) {
                double[] datas = tudate[i];
                JSONArray jsonArray = new JSONArray();
                for (double data : datas) {
                    jsonArray.put(data);
                }
                dataToJSON.put("data" + i, jsonArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataToJSON.toString();
    }

    /**
     * ���췽��
     */
    @SuppressWarnings("unchecked")
    public StatDeviceOEEBean() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String equNo = StringUtils.getCookie(request, "equSerialNo");
        if (null != equNo && !"".equals(equNo)) {
            List<TEquipmentInfo> tequipment = deviceService.getAllEquName("'" + equNo+ "'");
            if (null != tequipment && tequipment.size() > 0) {
                String equName = tequipment.get(0).getEquName();
                nameList.add(new SelectItem(equNo, equName));
            }
            equSerialNo = equNo;
            equSerialNoPan=equNo;
        }
        // Ĭ��ʱ��ֵ :���һ����
        if (null == start && null == end) {
            Date[] datea = StringUtils.convertDatea(1);
            start = datea[0];
            end = datea[1];
            System.out.println("��ʱ�丳��ֵ  start==" + start + "end==" + end);
        }
        selectBeanOEE();
    }

    private Locale getLocale(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return SessionHelper.getCurrentLocale(request.getSession());
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
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

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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

    public void setNodeStr(String nodeStr) {
        this.nodeStr = nodeStr;
    }

    public String getEquSerialNo() {
        return equSerialNo;
    }
}

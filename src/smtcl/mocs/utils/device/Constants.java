package smtcl.mocs.utils.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @����˵��  ���ñ���
 * @���� YuTao
 * @����ʱ�� 2012-11-5 ����1:31:34
 * @�޸���
 * @�޸�����
 * @�޸�˵��
 * @�汾 V1.0
 */
public class Constants {
	
	public static final String configPath = "/erp/erpReturn.txt";
//	/**
//	 * ��¼����תҳ��
//	 */
//	public static final String LOGIN_LOCATION = "authority.login.location";


	/**
	 * ��¼SESSION��ʶ
	 */
	public static final String USER_SESSION_KEY = "mocs.session.user";

	/**
	 * NC��¼Ĭ������
	 */
	public static String LOGIN_PWD="123456";  
	

	/**
	 * ��ҳ��С
	 */
	public static final int PAGE_SIZE = 10;
//	public static final int PAGE_SIZE2 = 14;
	//SSO �ڵ��л�
	public static String SSO_CONTROL="1";
	
	//t_userEquCurstatus�� ������������ʱ��û�в�������Ϊͣ��
	public static final int CONTROL_STOP_TIME=60000*30;
	
	public static final int CONTROL_TUOJI_TIME= 3600000;
	 
	public static int LOADCOUNT=1;
	/*
	 * ��¼��Ϣ��ʾ 0:�˻������� 1��������� 2����¼�ɹ�
	 */
	public static final String[] USER_LOGIN_MSG = { "�û��������ڣ�", "���벻��ȷ��", "��¼�ɹ���","��¼ʧ�ܣ�" }	;
	
	public static final String[] USER_QUERY_MSG = { "��ѯ�ɹ���", "û�м�¼��", "��ѯʧ�ܣ�" }	;

	public static final String[] USER_UPDATE_MSG = { "���³ɹ���", "����ʧ�ܣ�"};
	/**
	 * �ӹ������Ƿ����
	 */
	public static final String[] IS_OK_NOTOK={"���ýӿ�ʧ�ܣ�","Ok","NOK"};

	/**
	 * ϵͳ��ʶ
	 */
	public static final String APPLICATION_ID = "mocs";
	
	/**
	 * �豸����PAGE_ID
	 */
	public static final String SBZL_PAGE_ID = "mocs.cjgl.page.cjgl";

	/**
	 * ����ģ��
	 */
	public static final Map<String,Object> VIRTUALPAGES=new HashMap<String, Object>();
	/**
	 * ����ҳ��������ģ���ӳ���ϵ
	 */
	public static final Map<String,Object> P_V_PAGES=new HashMap<String, Object>();
	/**
	 * ������ģ�������ַ���߼���ַ�Ķ�Ӧ��ϵ
	 */
	public static final Map<String,String> MOCS_PATH_MAP = new HashMap<String, String>();
	public static final Map<String,String> MOCS_KCGL_TMK = new HashMap<String, String>();//TMK (three menu key)
    /**
     * ��ťID����
     */
    public static final String[] BUTTONS_ID ={
            "button.view",	 //�鿴
            "button.manage",	//����
            "button.new",      //����
            "button.delete",   //ɾ��
            "button.save",    //����
            "button.edit",     //�༭
            "button.supreme"		//��������
    };

    /**
     * ��������ҳ��ID
     */
    public static final String SCDD_PAGE_ID = "mocs.scdd.page.scdd";
    /**
     * ��ҵ�ƻ�ҳ��ID
     */
    public static final String ZYJH_PAGE_ID = "mocs.zyjh.page.zyjh";

    public static final Map<String,String> statusMap = new HashMap<String, String>();

	public static final List<Map<String,Object>> MATERIELTYPE=new ArrayList<Map<String,Object>>();
		static{

            statusMap.put("����", "Created");
            statusMap.put("�½�", "Created");
            statusMap.put("����", "Forwarded");
            statusMap.put("���", "Finished");
            statusMap.put("����", "Closed");
            statusMap.put("�ɹ�", "to be Dispatched");
            statusMap.put("���ɹ�", "Dispatched");
            statusMap.put("���ɹ�", "to be Dispatched");
            statusMap.put("�ӹ�", "Processing");
            statusMap.put("��ͣ", "Pause");

			VIRTUALPAGES.put("��ʷ����", "");//key ����ҳ����       value urlĬ��ֵ
            VIRTUALPAGES.put("Historical Analysis","");
            P_V_PAGES.put("ʱ��ͳ��", "��ʷ����");
            P_V_PAGES.put("Time Statistics","Historical Analysis");
			P_V_PAGES.put("oee���Ʒ���", "��ʷ����");
            P_V_PAGES.put("OEE Trend Analysis","Historical Analysis");
			P_V_PAGES.put("�����¼�����", "��ʷ����");
            P_V_PAGES.put("Machine Events","Historical Analysis");
			P_V_PAGES.put("oee����", "��ʷ����");
            P_V_PAGES.put("OEE Analysis","Historical Analysis");
			P_V_PAGES.put("�ӹ��¼�����", "��ʷ����");
            P_V_PAGES.put("Processing Events","Historical Analysis");

			P_V_PAGES.put("���ķ���", "��ʷ����");
            P_V_PAGES.put("Takt Time Analysis","Historical Analysis");
			
			VIRTUALPAGES.put("��̨�豸�Ƚ�", "");//key ����ҳ����       value urlĬ��ֵ
            VIRTUALPAGES.put("Equipment Comparison","");
			P_V_PAGES.put("oee���ƱȽ�", "��̨�豸�Ƚ�");
            P_V_PAGES.put("OEE Trend Comparison","Equipment Comparison");
			P_V_PAGES.put("ʱ��Ƚ�", "��̨�豸�Ƚ�");
            P_V_PAGES.put("Time Comparison","Equipment Comparison");
			
			
			MOCS_PATH_MAP.put("/mocs/parts/part_class_config.faces", "mocs.gcjm.ljpz.ljlbpz");
			MOCS_PATH_MAP.put("/mocs/parts/part_type_config.faces", "mocs.gcjm.ljpz.ljxxpz");
			MOCS_PATH_MAP.put("/mocs/parts/part_process_config.faces", "mocs.gcjm.ljpz.ljgxpz");
			MOCS_PATH_MAP.put("/mocs/resource/materrail_class_config.faces", "mocs.gcjm.zypz.wllbwh");
			MOCS_PATH_MAP.put("/mocs/product/product_process_list.faces", "mocs.gcjm.cpgl.cpgcsj");		
			
			MOCS_PATH_MAP.put("/mocs/costmanage/product_cost.xhtml","mocs.cbhs.page.cbhs");
			MOCS_PATH_MAP.put("/mocs/device/factory_profile.xhtml","mocs.cjgl.page.cjgl");
			MOCS_PATH_MAP.put("/mocs/jobdispatch/jobdispatch.xhtml","mocs.scdd.page.scdd");
			MOCS_PATH_MAP.put("/mocs/jobplan/jobplan_detail.xhtml","mocs.zyjh.page.zyjh");
			MOCS_PATH_MAP.put("/mocs/productInProgress/product_in_progress_composite.xhtml","mocs.zzpcx.page.zzpcx");
			MOCS_PATH_MAP.put("/mocs/device/workshop_management.xhtml","mocs.sbgl.page.sbgl");
			
		    
			MOCS_PATH_MAP.put("/mocs/storage/storage_manage.xhtml","mocs.ckgl.page.kfgl");
			MOCS_PATH_MAP.put("/mocs/storage/materiel_position_manage.xhtml","mocs.ckgl.page.kwgl");
			
			MOCS_PATH_MAP.put("/mocs/map/map.faces", "mocs.dtgl.page.dtgl");
			
			
			Map<String,Object> typeMap1=new HashMap<String, Object>();
			typeMap1.put("id", "1");
			typeMap1.put("value", "ԭ����");
			MATERIELTYPE.add(typeMap1);
			Map<String,Object> typeMap2=new HashMap<String, Object>();
			typeMap2.put("id", "2");
			typeMap2.put("value", "���Ʒ");
			MATERIELTYPE.add(typeMap2);
			Map<String,Object> typeMap3=new HashMap<String, Object>();
			typeMap3.put("id", "3");
			typeMap3.put("value", "��Ʒ");
			MATERIELTYPE.add(typeMap3);
			Map<String,Object> typeMap4=new HashMap<String, Object>();
			typeMap4.put("id", "4");
			typeMap4.put("value", "����Ʒ");
			MATERIELTYPE.add(typeMap4);
			Map<String,Object> typeMap5=new HashMap<String, Object>();
			typeMap5.put("id", "5");
			typeMap5.put("value", "��̨ά��");
			MATERIELTYPE.add(typeMap5);
			
			MOCS_KCGL_TMK.put("��������", "�������");
			MOCS_KCGL_TMK.put("�깤���", "�������");
			MOCS_KCGL_TMK.put("��������", "�������");
			MOCS_KCGL_TMK.put("�ɹ����", "�������");
			MOCS_KCGL_TMK.put("��������", "���ϳ���");
			MOCS_KCGL_TMK.put("���˵�����", "���ϳ���");
			MOCS_KCGL_TMK.put("�ӿ��ת��", "���ϳ���");
			MOCS_KCGL_TMK.put("�����ӷ�", "���ϳ���");
		}
}

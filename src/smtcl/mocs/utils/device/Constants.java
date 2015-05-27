package smtcl.mocs.utils.device;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @����˵���� ���ñ���
 * @���ߣ�YuTao
 * @����ʱ�䣺2012-11-5 ����1:31:34
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @�汾��V1.0
 */
public class Constants {

	/**
	 * ��¼����תҳ��
	 */
	public static final String LOGIN_LOCATION = "authority.login.location";

	/**
	 * ��¼SESSION��ʶ
	 */
	public static final String USER_SESSION_KEY = "mocs.session.user";
	
	/**
	 * NC��¼Ĭ������
	 */
	public static String LOGIN_PWD="123456";
	/**
	 * Ĭ����Կ
	 */
	public static String KEY_STRING="smtcl-i5";  
	
	/**
	 * Ĭ���˻�
	 */
	public static String KEY_ACCOUNT="fwmh+vIhdGD8I4n99WrArQ=="; 
	
	/**
	 * ���̱��
	 */
	public static String KEY_COMPANY_NO="euwAx8veU5AHMHOZNXuJtA=="; 
	
	/**
	 * ����ģʽ
	 */
	public static Boolean IS_DEV_MODE=false;
	
	/**
	 * ��ҳ��С
	 */
	public static final int PAGE_SIZE = 10;
	public static final int PAGE_SIZE2 = 14;
	//SSO �ڵ��л�
	public static String SSO_CONTROL="1";
	
	//t_userEquCurstatus�� ������������ʱ��û�в�������Ϊͣ��
	public static final int CONTROL_STOP_TIME=60000*30;
	
	public static final int CONTROL_TUOJI_TIME=3600000*1;
	
	public static int LOADCOUNT=1;
	/*
	 * ��¼��Ϣ��ʾ 0:�˻������� 1��������� 2����¼�ɹ�
	 */
	public static final String[] USER_LOGIN_MSG = { "�û��������ڣ�", "���벻��ȷ��", "��¼�ɹ���","��¼ʧ�ܣ�" }	;
	
	public static final String[] USER_QUERY_MSG = { "��ѯ�ɹ���", "û�м�¼��", "��ѯʧ�ܣ�" }	;
	
	public static final String[] USER_ADD_MSG = { "��ӳɹ���", "�����ظ���ӣ�", "���ʧ�ܣ�" }	;
	
	public static final String[] USER_DEL_MSG = { "ɾ���ɹ���", "ɾ��ʧ�ܣ�"};
	
	public static final String[] USER_UPDATE_MSG = { "���³ɹ���", "����ʧ�ܣ�"};

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
		static{
			VIRTUALPAGES.put("��ʷ����", "");//key ����ҳ����       value urlĬ��ֵ
			P_V_PAGES.put("ʱ��ͳ��", "��ʷ����");
			P_V_PAGES.put("oee���Ʒ���", "��ʷ����");
			P_V_PAGES.put("�����¼�����", "��ʷ����");
			P_V_PAGES.put("oee����", "��ʷ����");
			P_V_PAGES.put("�ӹ��¼�����", "��ʷ����");
			P_V_PAGES.put("���ķ���", "��ʷ����");
			
			VIRTUALPAGES.put("��̨�豸�Ƚ�", "");//key ����ҳ����       value urlĬ��ֵ
			P_V_PAGES.put("oee���ƱȽ�", "��̨�豸�Ƚ�");
			P_V_PAGES.put("ʱ��Ƚ�", "��̨�豸�Ƚ�");
			
			
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
				
		
		}
}

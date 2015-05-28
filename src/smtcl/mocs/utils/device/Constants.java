package smtcl.mocs.utils.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @功能说明： 常用变量
 * @作者：YuTao
 * @创建时间：2012-11-5 下午1:31:34
 * @修改者：
 * @修改日期：
 * @修改说明：
 * @版本：V1.0
 */
public class Constants {
	
	public static final String configPath = "/erp/erpReturn.txt";
	/**
	 * 登录后跳转页面
	 */
	public static final String LOGIN_LOCATION = "authority.login.location";

	/**
	 * 登录SESSION标识
	 */
	public static final String USER_SESSION_KEY = "mocs.session.user";

	/**
	 * NC登录默认密码
	 */
	public static String LOGIN_PWD="123456";

    /**
     * 生产调度页面ID
     */
    public static final String SCDD_PAGE_ID = "mocs.scdd.page.scdd";
    /**
     * 作业计划页面ID
     */
    public static final String ZYJH_PAGE_ID = "mocs.zyjh.page.zyjh";
    /**
     * 按钮ID集合
     */
    public static final String[] BUTTONS_ID ={
            "button.view",	 //查看
            "button.manage",	//管理
            "button.new",      //新增
            "button.delete",   //删除
            "button.save",    //保存
            "button.edit",     //编辑
            "button.supreme"		//超级管理
    };

	/**
	 * 分页大小
	 */
	public static final int PAGE_SIZE = 10;
	public static final int PAGE_SIZE2 = 14;
	//SSO 节点切换
	public static String SSO_CONTROL="1";
	
	//t_userEquCurstatus表 如果超过下面的时间没有操作则设为停机
	public static final int CONTROL_STOP_TIME=60000*30;
	
	public static final int CONTROL_TUOJI_TIME=3600000*1;
	
	public static int LOADCOUNT=1;
	/*
	 * 登录消息提示 0:账户不存在 1：密码错误 2：登录成功
	 */
	public static final String[] USER_LOGIN_MSG = { "用户名不存在！", "密码不正确！", "登录成功！","登录失败！" }	;
	
	public static final String[] USER_QUERY_MSG = { "查询成功！", "没有记录！", "查询失败！" }	;
	
	public static final String[] USER_ADD_MSG = { "添加成功！", "不能重复添加！", "添加失败！" }	;
	
	public static final String[] USER_DEL_MSG = { "删除成功！", "删除失败！"};
	
	public static final String[] USER_UPDATE_MSG = { "更新成功！", "更新失败！"};
	/**
	 * 加工工单是否合理
	 */
	public static final String[] IS_OK_NOTOK={"调用接口失败！","Ok","NOK"};

	/**
	 * 系统标识
	 */
	public static final String APPLICATION_ID = "mocs";
	
	/**
	 * 设备总览PAGE_ID
	 */
	public static final String SBZL_PAGE_ID = "mocs.cjgl.page.cjgl";

	/**
	 * 虚拟模块
	 */
	public static final Map<String,Object> VIRTUALPAGES=new HashMap<String, Object>();
	/**
	 * 物理页面与虚拟模块的映射关系
	 */
	public static final Map<String,Object> P_V_PAGES=new HashMap<String, Object>();
	/**
	 * 工厂建模，物理地址跟逻辑地址的对应关系
	 */
	public static final Map<String,String> MOCS_PATH_MAP = new HashMap<String, String>();
	public static final List<Map<String,Object>> MATERIELTYPE=new ArrayList<Map<String,Object>>();
		static{
			VIRTUALPAGES.put("历史分析", "");//key 虚拟页面名       value url默认值
			P_V_PAGES.put("时间统计", "历史分析");
			P_V_PAGES.put("oee趋势分析", "历史分析");
			P_V_PAGES.put("机床事件分析", "历史分析");
			P_V_PAGES.put("oee分析", "历史分析");
			P_V_PAGES.put("加工事件分析", "历史分析");
			P_V_PAGES.put("节拍分析", "历史分析");
			
			VIRTUALPAGES.put("多台设备比较", "");//key 虚拟页面名       value url默认值
			P_V_PAGES.put("oee趋势比较", "多台设备比较");
			P_V_PAGES.put("时间比较", "多台设备比较");
			
			
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
			
			Map<String,Object> typeMap1=new HashMap<String, Object>();
			typeMap1.put("id", "1");
			typeMap1.put("value", "原材料");
			MATERIELTYPE.add(typeMap1);
			Map<String,Object> typeMap2=new HashMap<String, Object>();
			typeMap2.put("id", "2");
			typeMap2.put("value", "半成品");
			MATERIELTYPE.add(typeMap2);
			Map<String,Object> typeMap3=new HashMap<String, Object>();
			typeMap3.put("id", "3");
			typeMap3.put("value", "成品");
			MATERIELTYPE.add(typeMap3);
			Map<String,Object> typeMap4=new HashMap<String, Object>();
			typeMap4.put("id", "4");
			typeMap4.put("value", "消耗品");
			MATERIELTYPE.add(typeMap4);
			Map<String,Object> typeMap5=new HashMap<String, Object>();
			typeMap5.put("id", "5");
			typeMap5.put("value", "后台维护");
			MATERIELTYPE.add(typeMap5);
		}
}

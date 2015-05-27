package smtcl.mocs.utils.device;

import java.util.HashMap;
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
	 * 默认密钥
	 */
	public static String KEY_STRING="smtcl-i5";  
	
	/**
	 * 默认账户
	 */
	public static String KEY_ACCOUNT="fwmh+vIhdGD8I4n99WrArQ=="; 
	
	/**
	 * 厂商编号
	 */
	public static String KEY_COMPANY_NO="euwAx8veU5AHMHOZNXuJtA=="; 
	
	/**
	 * 开发模式
	 */
	public static Boolean IS_DEV_MODE=false;
	
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
				
		
		}
}

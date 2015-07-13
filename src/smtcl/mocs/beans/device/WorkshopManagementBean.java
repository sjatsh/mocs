package smtcl.mocs.beans.device;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 设备管理
 * @创建时间 2013-06-06
 * @作者 liguoqiang
 * @修改者
 * @修改日期
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name="workshopManagementBean")
@ViewScoped
public class WorkshopManagementBean { 

	private String nodeSwitchLoad;
    /**
	 * 设备业务逻辑
	 */
    private IDeviceService deviceService =(IDeviceService)ServiceFactory.getBean("deviceService"); //获取注入;
    
    /**
     * 机床状态
     */
    private Map<String,Object> mts;//MachineToolStatus 机床状态
    
    /**
     * 加工任务
     */
    private Map mt;//MachiningTask 加工任务
    
    /**
     * 成本数据
     */
    private Map energyCost;
    /**
     * 设备效率
     */
    private Map ee;//EquipmentEfficiency 设备效率
    
    /**
     * 机床状态饼图数据
     */
    private String pie; //
    /**
     * 成本分析饼图数据1
     */
    private String piecb; 
    /**
     * 本分析饼图数据2
     */
    private String piecbtwo; 
    /**
     * 设备序列号
     */
    private String equSerialNo;
    /**
     * 三维仿真ip地址
     */
    private String ipAddress;
    
    /**
     * 格式化字符串
     */
    DecimalFormat df=new DecimalFormat("0.00");
    DecimalFormat d=new DecimalFormat("0");
  
	/**
	 * 构造方法
	 */
	public WorkshopManagementBean() {
		loadData();
	} 
	/**
	 * 用于构造方法加载数据或者节点切换的时候重新加载数据
	 */
	public void loadData(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String startnodeid=session.getAttribute("nodeid")+"";
		if(null==nodeSwitchLoad){
			nodeSwitchLoad=startnodeid;
		}
		 if(!startnodeid.equals(nodeSwitchLoad)){
			 nodeSwitchLoad=startnodeid;
			 equSerialNo="";
			 if(!StringUtils.isEmpty(startnodeid)){
					List tt=deviceService.getTEquipmentInfoByPId(startnodeid);
					if(null!=tt&&tt.size()>0){
						equSerialNo=tt.get(0)+"";
					}
				}
		 }else{
			  String equ=StringUtils.getCookie(request, "equSerialNo");
				if(null!=equ&&!"".equals(equ)){
					equSerialNo=equ;
				}else{
					if(!StringUtils.isEmpty(startnodeid)){
						List tt=deviceService.getTEquipmentInfoByPId(startnodeid);
						if(null!=tt&&tt.size()>0){
							equSerialNo=tt.get(0)+"";
						}
					}
				}
		 }
			
		
		if(!StringUtils.isEmpty(equSerialNo)){
			List iplist=deviceService.getipAddress(equSerialNo);
			if(null!=iplist&&iplist.size()>0)
			ipAddress=(null==iplist.get(0)?"":iplist.get(0)+"");
			else
			ipAddress=null;
			System.out.println(ipAddress);
			System.out.println("equSerialNo:"+equSerialNo);
			List<Map<String,Object>> mtsrs=deviceService.getMachineToolStatus(equSerialNo);
			mts=StringUtils.listIsNull(mtsrs)?mtsrs.get(0):null;
			if(StringUtils.listIsNull(mtsrs)){
				Date da=new Date();
				String status=mts.get("status").toString();
				String timeZone=null==mts.get("timeZone")||"".equals(mts.get("timeZone"))?"0":mts.get("timeZone").toString();
				if(da.getTime()-((Date)mts.get("updateTime")).getTime()-Double.parseDouble(timeZone)>Constants.CONTROL_TUOJI_TIME){
					status="脱机";
				}
				
				if(status.equals("运行")){
					/*f(StringUtils.isValid(ipAddress)<0){
						ipAddress=null;
					}*/
				}
				mts.put("cuttingTime", df.format(Double.parseDouble(mts.get("cuttingTime")+"")/3600));
				mts.put("assistedTime", df.format(Double.parseDouble(mts.get("assistedTime")+"")/3600));
				mts.put("prepareTime", df.format(Double.parseDouble(mts.get("prepareTime")+"")/3600));
				mts.put("idleTime", df.format(Double.parseDouble(mts.get("idleTime")+"")/3600));
				
				// 计算停机时间
				String ddate=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Calendar.getInstance().getTime());
				ddate=ddate.substring(ddate.length()-8,ddate.length());
				double totol=Double.parseDouble(ddate.substring(0,2))+(Double.parseDouble(ddate.substring(3,5))*60+Double.parseDouble(ddate.substring(6,8)))/3600;
					   totol=totol-Double.parseDouble(mts.get("cuttingTime").toString())-Double.parseDouble(mts.get("assistedTime").toString())
							   -Double.parseDouble(mts.get("prepareTime").toString())-Double.parseDouble(mts.get("idleTime").toString());
				mts.put("stopTime", df.format(totol));
				
			}else{
				mts =new HashMap<String,Object>();
				mts.put("equSerialNo", equSerialNo);
			}
			List<Map<String,Object>> mtrs=deviceService.getMachiningTask(equSerialNo);
			mt=StringUtils.listIsNull(mtrs)?mtrs.get(0):null;
			if(StringUtils.listIsNull(mtrs)){
				if(null==mt.get("estimateLastTime")){
					mt.put("estimateLastTime", d.format(0));
				}else{
					if(Integer.parseInt(mt.get("estimateLastTime").toString())<0){
						mt.put("estimateLastTime", d.format(0));	
					}else{
						mt.put("estimateLastTime", d.format(Double.parseDouble(mt.get("estimateLastTime")+"")/60));
					}
					
				}

			}
			ee=deviceService.getEquipmentEfficiency(equSerialNo);
			pie=loadPieData(mts);
			if(null!=mt&&null!=mt.get("jobDispatchListNo")&&!"".equals(mt.get("jobDispatchListNo").toString())){
				piecbtwo=loadRCLData(equSerialNo,1);//日产量
				piecb=loadRCLData(equSerialNo,2);//月产量
			}else{
				piecbtwo=loadRCLData2(equSerialNo,1);//日产量
				piecb=loadRCLData2(equSerialNo,2);//月产量
			}
			
		}else{
			ipAddress=null;
			mts=null;
			mt=null;
			ee=null;
			pie=null;
			piecbtwo=null;
			piecb=null;
			
		}
	}
	/**
	 * 节点切换数据更换
	 * @return
	 */
	public String getNodeSwitchLoad() {
		 loadData();
		return nodeSwitchLoad;
	}
	/**
	 * 定时刷新
	 */
	public void refreshData(){
		if(!StringUtils.isEmpty(equSerialNo)){
			List<Map<String,Object>> mtsrs=deviceService.getMachineToolStatus(equSerialNo);
			mts=StringUtils.listIsNull(mtsrs)?mtsrs.get(0):null;
			//System.out.println("xfeed:"+mts.get("xfeed")+"     zfeed:"+mts.get("zfeed"));
			//System.out.println("feedSpeed:"+mts.get("feedSpeed")+"     axisspeed:"+mts.get("axisspeed"));
			List<Map<String,Object>> mtrs=deviceService.getMachiningTask(equSerialNo);
			mt=StringUtils.listIsNull(mtrs)?mtrs.get(0):null;
			if(StringUtils.listIsNull(mtrs)){
				if(null==mt.get("estimateLastTime")){
					mt.put("estimateLastTime", d.format(0));
				}else{
					if(Integer.parseInt(mt.get("estimateLastTime").toString())<0){
						mt.put("estimateLastTime", d.format(0));	
					}else{
						mt.put("estimateLastTime", d.format(Double.parseDouble(mt.get("estimateLastTime")+"")/60));
					}
				}
			}
		}
	}
	
	/**
	 * 机床状态饼图数据填充
	 * @param mts
	 * @return String
	 */
	 public String loadPieData(Map mts){
		 if(null != mts && mts.size() > 0){	
			 Map zmap2=new HashMap();
			 Map mV2=new HashMap();
			 mV2.put("name", "切削时间");
			 mV2.put("y", ""+mts.get("cuttingTime"));
			 Map mV3=new HashMap();
			 mV3.put("name", "辅助时间");
			 mV3.put("y",""+mts.get("assistedTime"));
			 Map mV4=new HashMap();
			 mV4.put("name", "准备时间");
			 mV4.put("y", ""+mts.get("prepareTime"));
			 Map mV5=new HashMap();
			 mV5.put("name", "空闲时间");
			 mV5.put("y", ""+mts.get("idleTime"));
			 Map mV6=new HashMap();
			 mV6.put("name", "停止时间");
			 mV6.put("y", ""+mts.get("stopTime"));	
			 zmap2.put("qx", mV2);
			 zmap2.put("fz",mV3);
			 zmap2.put("zb",mV4);
			 zmap2.put("kx",mV5);
			 zmap2.put("tz",mV6);
			 Gson gson = new GsonBuilder().serializeNulls().create(); 
			 return gson.toJson(zmap2);
		}
		 return null;
	 }
	 
	 /**
	  * 成本饼图数据加载
	  * @param mts
	  * @return String
	  */
	 public String loadPieDataTwo(Map mts){
		 if(null != mts && mts.size() > 0){
			 List<Map<String,Object>> temp=(List<Map<String,Object>>)mts.get("energycostDetailList");
			 if(temp!=null&&temp.size()>0)
			 {
				 energyCost=temp.get(0);
				 Map zmap2=new HashMap();
				 Map mV2=new HashMap();
				 mV2.put("name", "切削损耗");
				 System.err.println(temp.get(0).get("energyCost_cutting"));
				 mV2.put("y", temp.get(0).get("energyCost_cutting"));
				 Map mV3=new HashMap();
				 mV3.put("name", "辅助损耗");
				 mV3.put("y",temp.get(0).get("energyCost_dryRunning"));
				 Map mV4=new HashMap();
				 mV4.put("name", "准备损耗");
				 mV4.put("y", temp.get(0).get("energyCost_prepare"));
				 zmap2.put("qx", mV2);
				 zmap2.put("fz",mV3);
				 zmap2.put("zb",mV4);
				 Gson gson = new GsonBuilder().serializeNulls().create(); 
				 return gson.toJson(zmap2);
			 }
		}
		 return null;
	 }
	 
	 /**
	  * 成本饼图数据加载
	  * @param mts
	  * @return String
	  */
	 public String loadPieDataThree(Map mts){
		 if(null != mts && mts.size() > 0){	
			 Map zmap2=new HashMap();
			 Map mV2=new HashMap();
			 mV2.put("name", "人工");
			 mV2.put("y", mts.get("peopleCost"));
			 Map mV3=new HashMap();
			 mV3.put("name", "辅料");
			 mV3.put("y",mts.get("accMaterialCost"));
			 Map mV4=new HashMap();
			 mV4.put("name", "能源");
			 mV4.put("y", mts.get("energyCost"));
			 Map mV5=new HashMap();
			 mV5.put("name", "折旧");
			 mV5.put("y", mts.get("oldCost"));
			 Map mV6=new HashMap();
			 mV6.put("name", "主料");
			 mV6.put("y", mts.get("mainMaterialCost"));	
			 zmap2.put("qx", mV2);
			 zmap2.put("fz",mV3);
			 zmap2.put("zb",mV4);
			 zmap2.put("kx",mV5);
			 zmap2.put("tz",mV6);
			 Gson gson = new GsonBuilder().serializeNulls().create(); 
			 return gson.toJson(zmap2);
		}
		 return null;
	 }
	 private Locale getLocale(){
	        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	        return SessionHelper.getCurrentLocale(request.getSession());
	 }
	 
	 public String loadRCLData(String equSerialNo,int type){
		 Locale locale = this.getLocale();
		 //var abc="[{name:'Chrome',y:12.8},{name:'test1',y:12.8},{name:'test2',y:12.8}]";
		 List<Map<String,Object>> rs=deviceService.getRCLData(equSerialNo,type);
		 String[] color={"rgba(108,204,71,1)","rgba(108,204,71,0.4)","rgba(0,129,206,1)","rgba(0,129,206,0.4)","rgba(160,160,160,1)"};
		 int i=0;
		 String result="[";
		 int other=0;
		 for(Map<String,Object> map:rs){
			 if(i<4){
				 String name=map.get("processName")+","+map.get("partName");
				 String y=map.get("jgNum").toString();
				 result+="{name:'"+name+"',y:"+y+",color:'"+color[i]+"'},";
			 }else{
				 other=other+Integer.parseInt(map.get("jgNum").toString());
			 }
			 i++;
		 }
		 if(other>0){
			 if(locale.toString().equals("en") || locale.toString().equals("en_US")){
				 result+="{name:'Other Part,Other Process',y:"+other+",color:'"+color[4]+"'}";
			 }else{
				 result+="{name:'其他零件,其他工序',y:"+other+",color:'"+color[4]+"'}";
			 }
			 
		 }
		 //result=result.substring(0, result.length()-1);
		 result+="]";
		 return result;
	 }
	 
	 public String loadRCLData2(String equSerialNo,int type){
		 Locale locale = this.getLocale();
		 List<Map<String,Object>> rs=deviceService.getWorkEvent(equSerialNo,type);
		 String[] color={"rgba(108,204,71,1)","rgba(108,204,71,0.4)","rgba(0,129,206,1)","rgba(0,129,206,0.4)","rgba(160,160,160,1)"};
		 int i=0;
		 String result="[";
		 int other=0;
		 for(Map<String,Object> map:rs){
			 if(i<4){
				 String name=null==map.get("NCprogramm")?"test":map.get("NCprogramm").toString()+",未定义";
				 String y=null==map.get("total")?"0":map.get("total").toString();
				 result+="{name:'"+name+"',y:"+y+",color:'"+color[i]+"'},";
			 }else{
				 other=other+Integer.parseInt(map.get("total").toString());
			 }
			 i++;
		 }
		 if(other>0){
			 if(locale.toString().equals("en") || locale.toString().equals("en_US")){
				 result+="{name:'Other Part,Other Process',y:"+other+",color:'"+color[4]+"'}";
			 }else{
				 result+="{name:'其他零件,其他工序',y:"+other+",color:'"+color[4]+"'}";
			 }
			 
		 }
		 result+="]";
		 return result;
	 }

	public Map<String, Object> getMts() {
		return mts;
	}

	public void setMts(Map<String, Object> mts) {
		this.mts = mts;
	}

	public Map getMt() {
		return mt;
	}

	public void setMt(Map mt) {
		this.mt = mt;
	}

	public Map getEe() {
		return ee;
	}

	public void setEe(Map ee) {
		this.ee = ee;
	}

	public String getPie() {
		return pie;
	}

	public void setPie(String pie) {
		this.pie = pie;
	}

	public String getPiecb() {
		return piecb;
	}

	public void setPiecb(String piecb) {
		this.piecb = piecb;
	}

	public String getPiecbtwo() {
		return piecbtwo;
	}

	public void setPiecbtwo(String piecbtwo) {
		this.piecbtwo = piecbtwo;
	}

	public Map getEnergyCost() {
		return energyCost;
	}

	public void setEnergyCost(Map energyCost) {
		this.energyCost = energyCost;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	

	public void setNodeSwitchLoad(String nodeSwitchLoad) {
		this.nodeSwitchLoad = nodeSwitchLoad;
	}


}  
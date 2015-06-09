package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.utils.BundleUtils;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 
 * 工单添加Bean
 * 作者 yyh
 * 创建时间 2014-2-23
 * 修改者 yyh,songkaiang
 * 修改日期 2015-3-13
 * 修改说明 使用jsf的国际化
 * @version V1.0
 */
@ManagedBean(name="JobdispatchAdd")
@ViewScoped
public class JobdispatchAddBean implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 工单接口实例
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	/**
	 * 作业计划接口实例
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	/**
	 * 零件类型名称集合
	 */
	private Map<String,Object> partTypeMap = new HashMap<String,Object>();
	/**
	 * 设备类型名称集合
	 */
	private Map<String,Object> eduTypeMapforupdata = new HashMap<String,Object>();
	/**
	 * 设备类型名称初始化
	 */
	private String eduTypeMapforupdataname;
	/**
	 * 设备类型ID初始化
	 */
	private String  eduTypeMapforupdataId;
	/**
	 * 零件类型ID
	 */
	private String partTypeId;
	/**
	 * 投料数量
	 */
	private String num;
	/**
	 * 任务号
	 */
	private String taskNum;
	/**
	 * 计划开始时间
	 */
	private Date startDate;
	/**
	 * 计划结束时间
	 */
	private Date endDate;
	
	/**
	 * 工序dataTable数据    ====>工艺
	 */
	private List<Map<String,Object>> processlist = new ArrayList<Map<String,Object>>();
	/**
	 * 工序dataTable数据  外层封装
	 */
	private TJobDispatchDataModel mediumProcessModel = new TJobDispatchDataModel();	
	/**
	 * 工序选中的行
	 */
	private Map<String,Object>[] selectedProcess; 	
	/**
	 * 工序选中的行的ID
	 */
	private String selected;
	
	/**
	 * 工单dataTable数据   ====》工单
	 */
	private List<Map<String,Object>> jobdispatchlist = new ArrayList<Map<String,Object>>();
	/**
	 * 工单dataTable数据  外层封装
	 */
	private TJobDispatchDataModel jobdispatchModel = new TJobDispatchDataModel();
	/**
	 * 工单选中的行
	 */
	private Map<String,Object>[] selectedJobdispatch;
	/**
	 * 节点ID
	 */
	private String nodeid;
	/**
	 * 作业计划号
	 */
	private String planjobId;
	/**
	 * 作业计划号集合
	 */
	private Map<String,Object> planjobMap = new TreeMap<String,Object>();
	/**
	 * 提示信息
	 */
	private String isSuccess;
	/****************************************************************/
	/**
	 * 作业清单的选中框
	 */
	private boolean oneBool = true; //作业清单的菜单栏选中框，默认为选中
	
	public List<String> complete(String query){
		List<String> results = new ArrayList<String>();
		List<TPartTypeInfo> list = partService.getAllPartType(this.getNodeId(), query);
		for(TPartTypeInfo t:list){
			results.add(t.getName());
		}
		return results;
	}
	
	/**
	 * 控制多选框的全选与否
	 */
	public void editCheckBox(){
		if(oneBool){
			for (Map<String, Object> gg : jobdispatchlist) {
				gg.put("bool", true);
			}
		}else{
			for (Map<String, Object> gg : jobdispatchlist) {
				gg.put("bool", false);
			}
		}
	}
	List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
	/**
	 * 点击选中生成的工单清单列表
	 */
	public  void editList(String jobNo){
		System.out.println("----------->"+jobNo);
		for(Map<String,Object> map : jobdispatchlist){
			String jn = map.get("dno").toString();
			boolean b = Boolean.parseBoolean(map.get("bool").toString());	
			if(jn.equals(jobNo) && !b){  // 确定了哪个和 b是否选中
				lst.add(map); //不能遍历的时候删除，只能把要删除的放入list,然后在删除这个list
			}
		}

	}
	/**
	 * 删除点击的工单清单列表
	 */
//	public void delList(){
//		jobdispatchlist.removeAll(lst);
//		lst.clear();
//		jobdispatchModel = new TJobDispatchDataModel(jobdispatchlist);
//	}
	
	/****************************************************************/	
	
	/**
	 * 获取节点id
	 * @return 返回nodeid
	 */
	private String getNodeId(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}
	
	/**
	 * 构造函数
	 */
	public JobdispatchAddBean() {
		nodeid = this.getNodeId();
		List<Map<String, Object>> lst = jobDispatchService.getPartTypeMap(this
				.getNodeId());
		for (Map<String, Object> map : lst) {
			if (map.get("id") != null && !"".equals(map.get("id"))) {// 零件类型名称
				partTypeMap.put((String) map.get("name"), map.get("id").toString() + "@#" + (String) map.get("name"));
			}
		}
	}
	
	private String partTypeId00;   //零件类型ID--真正
	/**
	 * 查询<span>不在使用</span>
	 */
	public void searchList() {
		
	}
	
    /**
 	 * 更新方法
	 * @param event
     */
    public void onEdit(RowEditEvent event) {  
    	System.out.println("更新----------->"+jobdispatchlist.size());
    	for(Map<String, Object> disp:jobdispatchlist){	
    		String equtypeid = disp.get("equTypeId").toString();
    		String parttypename = jobDispatchService.getPartTypeNameById(equtypeid);
    		disp.put("eduTypeName", parttypename);
    	}
    }
    
    /**
     * 取消
     * @param event
     */
    public void onCancel(RowEditEvent event) {  

    }
    
    /**
     * 行选中的时候
     */
    public void onRowSelect(SelectEvent event) {  
    	System.out.println("行选中----------->"+jobdispatchlist.size());
    	for(Map<String, Object> disp:jobdispatchlist){	
    		String equtypeid = disp.get("equTypeId").toString();
    		System.out.println("设备类型ID----------->"+equtypeid);
    	}
    }

    /**
     * 生成工单
     */
   public void produceJobDispathList(){	
	   jobdispatchlist.clear();
	   SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
	   String time = sdf1.format(new Date());
	    String fristDispath ="";  //得到首工位工单号
		List<TPartTypeInfo> list = partService.getPartTypeInfo(partTypeId);
		for (TPartTypeInfo t : list) {
			partTypeId00 = t.getId().toString();
			break;
		}
	   for(Map<String, Object> gg:selectedProcess){
		   Map<String, Object> pMap = new HashMap<String, Object>();
		   String num11 = String.valueOf((int)(Math.random()*100));
		   pMap.put("dno","WO_"+gg.get("no")+"_"+partTypeId00+"_"+time+"_"+num11);     //工单编号
		   pMap.put("dname","WO_"+gg.get("cname")+"_"+partTypeId00+"_"+time+"_"+num11);//工单名称
		    if("".equals(fristDispath)){  //只进1次
			    fristDispath = "WO_"+gg.get("no")+"_"+partTypeId00+"_"+time+"_"+num11;
			   }
		   pMap.put("batchNo",fristDispath);               //首工位工单号
		   pMap.put("taskNum",taskNum);               //任务号
		   pMap.put("id",gg.get("id"));               //工序ID
		   pMap.put("no",gg.get("no"));               //工序编码
		   pMap.put("cname",gg.get("cname"));         //工序名称
		   pMap.put("num",num);                       //数量

		   pMap.put("equTypeId",gg.get("eduTypeMapforupdataId"));                           //设备类型ID
		   pMap.put("eduTypeName",gg.get("eduTypeMapforupdataname"));          //设备类型名称
		   pMap.put("eduTypeMapforupdata",gg.get("eduTypeMapforupdata"));      //设备类型集合
		   
		   jobdispatchlist.add(pMap);
	   }
		for (Map<String, Object> gg : jobdispatchlist) {   //工单全部作为已经选中
			gg.put("bool", true);
		}
	   jobdispatchModel = new TJobDispatchDataModel(jobdispatchlist);
   }
   
	/**
	 * 选中得到ID操作
	 */
   public void onSelected(){
	for(Map<String,Object> tt:selectedJobdispatch){
		selected=(String)tt.get("id");
	}
   }
   
   /**
    * 取消工单
    */
	public void delJobDispathList() {
		System.out.println("取消----------->" + jobdispatchlist.size());
		jobdispatchlist.clear();
		/*for (Map<String, Object> disp : jobdispatchlist) {
			String equtypeid = disp.get("equTypeId").toString();
		}*/
	}
   
   /**
    * 保存工单
    */
   public String saveJobDispatch(){
       isSuccess = BundleUtils.getStringFromBundle("faild.add", this.getLocale(), "jobdispatch");
       if(this.getJobdispatchlist().size()<=0){
           return isSuccess;
       }else{
           jobDispatchService.saveDispatch(this);
           isSuccess = BundleUtils.getStringFromBundle("add.success", this.getLocale(), "jobdispatch");
           return isSuccess;
       }

   }
   /**
    * 选择作业号后，获取投料数量和任务号，并自动填充
    */
   public void autoComplete(){
	   num = this.getNum();
	   if(!planjobId.isEmpty()){
           TJobplanInfo t = jobPlanService.geTJobplanInfoById(Long.parseLong(planjobId));
           this.setTaskNum(t.getName());
       }
	   
   }
   	/*************************private**********************************/
   private String getPartId(String partName){
	   List<TPartTypeInfo> list = partService.getPartTypeInfo(partName);
		for(TPartTypeInfo t : list ){
			return t.getId().toString();
		}
		return null;
   }

   private String getSkillid(){
       HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
       List<Map<String, Object>> lst2;
       if(request.getParameter("partId")!=null){
           lst2 = jobPlanService.getProcessplanMap(request.getParameter("partId").trim());
       }else{
           lst2 = jobPlanService.getProcessplanMap(this.getPartId(partTypeId));
       }


		// 默认选择1个工艺方案
		if (lst2.size() > 0) {
			Map<String, Object> map = lst2.get(0);
			return map.get("id").toString();
		}
		return null;
   }
   private Locale getLocale(){
       HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
       return SessionHelper.getCurrentLocale(request.getSession());
   }
    /********************set,get方法************************************/    

	public IJobPlanService getJobPlanService() {
		return jobPlanService;
	}
	
	public void setJobPlanService(IJobPlanService jobPlanService) {
		this.jobPlanService = jobPlanService;
	}
	
	public Map<String, Object> getPartTypeMap() {
		return partTypeMap;
	}
	
	public String getPartTypeId() {
		if(partTypeId==null){
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			if (request.getParameter("partId") != null
					&& !"".equals(request.getParameter("partId"))
					&& !request.getParameter("partId").equals("undefined")) {
				
				List<Map<String, Object>> lst = jobDispatchService.getPartTypeMap(this.getNodeId());
				String partId = request.getParameter("partId").trim();
				startDate = StringUtils.convertDate(request.getParameter("StartDate").trim(),"yyyy-MM-dd");
				endDate = StringUtils.convertDate(request.getParameter("EndDate").trim(), "yyyy-MM-dd");
				for(Map<String,Object> map:lst){
					if(map.get("id") != null && map.get("id").toString().equals(partId)){
						partTypeId = map.get("name").toString();
						break;
					}
				}
				String planid = request.getParameter("planId").trim();
				List<TJobplanInfo> jobPlanList = jobPlanService.getJobPlan(this.getNodeId(),partId);
				for(TJobplanInfo t:jobPlanList){
					if(planid.equals(t.getId().toString())){
						planjobId = t.getId().toString();
						
						taskNum = t.getName();
						break;
					}
				}
			}
			
		}
		return partTypeId;
	}
	
	public void setPartTypeId(String partTypeId) {
		this.partTypeId = partTypeId;
	}

	public String getNum() {
		if(planjobId != null && !planjobId.isEmpty()){
			TJobplanInfo t = jobPlanService.geTJobplanInfoById(Long.parseLong(planjobId));
			return t == null ? null : t.getPlanNum().toString();
		}
		return num;
	}
	
	public void setNum(String num) {
		this.num = num;
	}
	
	public String getTaskNum() {
		return taskNum;
	}
	
	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}

	@SuppressWarnings("unchecked")
	public TJobDispatchDataModel getMediumProcessModel() {
		if(partTypeId!=null && !partTypeId.isEmpty()){
			// 工艺清单
			processlist = jobDispatchService.getProcessByProcessPlanId(this.getNodeId(),
					this.getSkillid());
			mediumProcessModel = new TJobDispatchDataModel(processlist); // 封装都是用的1个
			selectedProcess = new Map[processlist.size()];
			for (int i = 0; i < processlist.size(); i++) { // 设为默认全选
				Map<String, Object> tj = processlist.get(i);
				selectedProcess[i] = tj;
			}
			for (Map<String, Object> mapp : selectedProcess) {
				String processId = mapp.get("id").toString(); // 工序ID
				// 设备类型集合
				List<Map<String, Object>> lst1 = jobDispatchService
						.getEquTypeMap(processId);
				eduTypeMapforupdata.clear(); // 清空上次的数据
				/* 修改内容start */
				List<Map<String, Object>> sblist = new ArrayList<Map<String, Object>>();
				/* 修改内容end */
				for (Map<String, Object> map : lst1) {
					Map<String, Object> sb = new HashMap<String, Object>();
					if (map.get("id") != null && !"".equals(map.get("id"))) {// 设备类型名称,为了修改
						eduTypeMapforupdata.put((String) map.get("name"),
								map.get("id").toString());
						sb.put("sbname", (String) map.get("name"));
						sb.put("sbvalue", map.get("id").toString());
						sblist.add(sb);
					}
				}
				mapp.put("eduTypeMapforupdata", sblist);
				// 进去时候的初始设备类型名称
				if (lst1.size() > 0) {
					Map<String, Object> map = lst1.get(0);
					eduTypeMapforupdataname = (String) map.get("name");
					eduTypeMapforupdataId = map.get("id").toString();
				}
				mapp.put("eduTypeMapforupdataname", eduTypeMapforupdataname);
				mapp.put("eduTypeMapforupdataId", eduTypeMapforupdataId);
			}
		}
		return mediumProcessModel;
	}
	
	public void setMediumProcessModel(TJobDispatchDataModel mediumProcessModel) {
		this.mediumProcessModel = mediumProcessModel;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object>[] getSelectedProcess() {
		if(partTypeId!=null && !partTypeId.isEmpty()){
			// 工艺清单
			processlist = jobDispatchService.getProcessByProcessPlanId(this.getNodeId(),
					this.getSkillid());
			selectedProcess = new Map[processlist.size()];
			for (int i = 0; i < processlist.size(); i++) { // 设为默认全选
				Map<String, Object> tj = processlist.get(i);
				selectedProcess[i] = tj;
			}
			jobdispatchlist.clear(); // 刷新左侧同时清空右侧
		}
		
		return selectedProcess;
	}
	
	public void setSelectedProcess(Map<String, Object>[] selectedProcess) {
		this.selectedProcess = selectedProcess;
	}
	
	public String getSelected() {
		return selected;
	}
	
	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	public List<Map<String, Object>> getJobdispatchlist() {
		return jobdispatchlist;
	}
	
	public TJobDispatchDataModel getJobdispatchModel() {
		return jobdispatchModel;
	}
	
	public void setJobdispatchModel(TJobDispatchDataModel jobdispatchModel) {
		this.jobdispatchModel = jobdispatchModel;
	}
	
	public Map<String, Object>[] getSelectedJobdispatch() {
		return selectedJobdispatch;
	}
	
	public void setSelectedJobdispatch(Map<String, Object>[] selectedJobdispatch) {
		this.selectedJobdispatch = selectedJobdispatch;
	}
	
	public String getNodeid() {
		return nodeid;
	}
	
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isOneBool() {
		return oneBool;
	}
	public void setOneBool(boolean oneBool) {
		this.oneBool = oneBool;
	}
	public List<Map<String, Object>> getLst() {
		return lst;
	}
	public void setLst(List<Map<String, Object>> lst) {
		this.lst = lst;
	}
	public String getPlanjobId() {
		return planjobId;
	}
	public void setPlanjobId(String planjobId) {
		this.getPlanjobMap();
		this.planjobId = planjobId;
	}
	public Map<String, Object> getPlanjobMap() {
		planjobMap.clear();
		if(partTypeId!=null && !partTypeId.isEmpty()){
            HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            List<TJobplanInfo> jobPlanList;
            if(request.getParameter("partId")!=null){
                jobPlanList = jobPlanService.getJobPlan(this.getNodeId(),request.getParameter("partId"));
            }else{
                jobPlanList = jobPlanService.getJobPlan(this.getNodeId(),this.getPartId(partTypeId));
            }

			for (TJobplanInfo job : jobPlanList) {
				
				planjobMap.put(job.getName(), job.getId());
			}
		}
		
		return planjobMap;
	}
	public void setPlanjobMap(Map<String, Object> planjobMap) {
		this.planjobMap = planjobMap;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
   
}

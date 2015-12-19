package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;

import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.job.TJobInfo;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.services.jobplan.IJobPlanService;

/**
 * 
 * 作业计划控制Bean
 * @作者：yyh
 * @创建时间：2013-7-2 下午13:05:16
 * @修改者：yyh
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="JobPlanControl")
@ViewScoped
public class JobPlanControlBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 作业计划接口实例
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	/**
	 * @描述：作业计划信息数据集
	 */	
	private Map<String, Object> jobPlanResults = new HashMap<String,Object>();
	/**
	 * 作业计划名称集合
	 */
	private Map<String,Object> jobPlanNameMap = new HashMap<String,Object>();
	/**
	 * 作业计划ID
	 */
	private String jobplabid;
	/**
	 * 零件类型名称集合
	 */
	private Map<String,Object> partTypeMap = new HashMap<String,Object>();
	/**
	 * 零件类型ID
	 */
	private String partTypeId;
	/**
	 * 工艺方案名称集合
	 */
	private Map<String,Object> skillMap = new HashMap<String,Object>();
	/**
	 * 工艺方案ID
	 */
	private String skillId;
	/**
	 * 计划数量
	 */
	private String planNum;
	/**
	 * 本次投料数量
	 */
	private String thisPlanNum;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 历次投料批次列表
	 */
	private List<Map<String,Object>> batchlist = new ArrayList<Map<String,Object>>();


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
	 * 作业dataTable数据   ====》工单
	 */
	private List<Map<String,Object>> jobdispatchlist = new ArrayList<Map<String,Object>>();
	/**
	 * 作业dataTable数据  外层封装
	 */
	private TJobDispatchDataModel jobdispatchModel = new TJobDispatchDataModel();
	/**
	 * 作业选中的行
	 */
	private Map<String,Object>[] selectedJobdispatch;

	/**
	 * 节点ID
	 */
	private String nodeid;
	/**
	 * 判断下拉是否可以动
	 */
	private String selectDisble;
	/**
	 * 是否同时启动工单
	 */
	private boolean booleanValue = true;

	/**
	 * 构造函数
	 */
	@SuppressWarnings("unchecked")
	public  JobPlanControlBean(){
		//获取节点ID
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid = (String)session.getAttribute("nodeid");
		
		List<Map<String,Object>> lst = jobPlanService.getPartTypeMap();
		for(Map<String,Object> map : lst){
			if(map.get("id")!=null && !"".equals(map.get("id"))){//零件类型名称
				partTypeMap.put((String)map.get("name"),map.get("id").toString());
			}
		}
		List<Map<String,Object>> lst1 = jobPlanService.getJobPlanMap();
		for(Map<String,Object> map : lst1){
			if(map.get("id")!=null && !"".equals(map.get("id"))){//作业计划
				jobPlanNameMap.put((String)map.get("name"),map.get("id").toString());
			}
		}
		
		/**==============================**/
		
		//零件类型ID,下拉框
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();	
		String id = "";
		if(request.getParameter("editPId")!=null){  //判断是否为空
		id = request.getParameter("editPId").trim();
		}
		if(id!=null && !id.equals("") && !id.equals("undefined")){  //页面url没有传值过来就是原来的
		jobplabid = id;
		List<Map<String, Object>> partlst =jobPlanService.getPartIdByJobPlanId(jobplabid);
		if(partlst.size()>0){
			Map<String, Object>  map = partlst.get(0);
			partTypeId = map.get("partId").toString();
		}
		//没有ID说明没有点击作业计划，下拉是可以动的
		if(jobplabid!=null&& !"".equals(jobplabid)&&!"-1".equals(jobplabid)){
			selectDisble = "yes";
		 }
		}
		//通过零件过盧工艺方案
		List<Map<String,Object>> lst2 = jobPlanService.getProcessplanMap(partTypeId);
		for(Map<String,Object> map : lst2){
			if(map.get("id")!=null && !"".equals(map.get("id"))){//工艺方案名称
				skillMap.put((String)map.get("name"),map.get("id").toString());
			}
		}	
		//默认选择1个工艺方案
		if(lst2.size()>0){
			Map<String,Object> map = lst2.get(0);
			skillId = map.get("id").toString();
		}
		
		//投料批次列表
		batchlist = jobPlanService.getBatchList(skillId,jobplabid);
		
		//作业计划信息
		jobPlanResults.clear();
		List<Map<String, Object>> lstlst = jobPlanService.getJobPlanByJobIdAndPartId(nodeid,jobplabid, partTypeId);
		if(lstlst.size()>0){
		jobPlanResults = lstlst.get(0);   //不要默认选中第一个
		planNum = jobPlanResults.get("planNum").toString();
		}
		
		//工艺清单
		processlist = jobPlanService.getProcessByProcessPlanId(nodeid,skillId);
		mediumProcessModel = new TJobDispatchDataModel(processlist); //封装都是用的1个
		selectedProcess = new Map[processlist.size()];
		for(int i=0; i<processlist.size();i++){  //设为默认全选
			Map<String, Object> tj = processlist.get(i);
		   selectedProcess[i] = tj;
		}
		
	}
	/**
	 * 作业计划下拉查询
	 */
	public void searchListByjobplan(){
		//通过作业计划得到零件Map
		partTypeMap.clear();
		List<Map<String,Object>> lst = jobPlanService.getPartTypeMapByJobPlanid(jobplabid);
		for(Map<String,Object> map : lst){
			if(map.get("id")!=null && !"".equals(map.get("id"))){//零件类型名称
				partTypeMap.put((String)map.get("name"),map.get("id").toString());
			}
		}

	}
	
	/**
	 * 零件类型下拉查询
	 */
	public void searchListBypart(){
		//通过零件过盧工艺方案
		skillMap.clear();
		List<Map<String,Object>> lst2 = jobPlanService.getProcessplanMap(partTypeId);
		for(Map<String,Object> map : lst2){
			if(map.get("id")!=null && !"".equals(map.get("id"))){//工艺方案名称
				skillMap.put((String)map.get("name"),map.get("id").toString());
			}
		}
	}
	
	
	/**
	 * 工艺方案下拉查询
	 */
	@SuppressWarnings("unchecked")
	public void searchList(){   //有互动的还是要放到互动的方法里面，没有互动的就是放在 get方法里面，各种ajax有冲突,
		planNum = null;
		
		//投料批次列表
		batchlist = jobPlanService.getBatchList(skillId,jobplabid);
		
		//作业计划信息
		jobPlanResults.clear();
		List<Map<String, Object>> lstlst = jobPlanService.getJobPlanByJobIdAndPartId(nodeid,jobplabid, partTypeId);
		if(lstlst.size()>0){
		jobPlanResults = lstlst.get(0);   //不要默认选中第一个
		planNum = jobPlanResults.get("planNum").toString();
		}
		
		//工艺清单
		processlist = jobPlanService.getProcessByProcessPlanId(nodeid,skillId);
		mediumProcessModel = new TJobDispatchDataModel(processlist); //封装都是用的1个
		selectedProcess = new Map[processlist.size()];
		for(int i=0; i<processlist.size();i++){  //设为默认全选
			Map<String, Object> tj = processlist.get(i);
		   selectedProcess[i] = tj;
		}
	
	}	

    /**
 	 * 更新方法
	 * @param event
     */
    public void onEdit(RowEditEvent event) {  
    	System.out.println("更新----------->"+jobdispatchlist.size());
    	for(Map<String, Object> disp:jobdispatchlist){		
    		String proid = disp.get("processId").toString();
    		String equid = disp.get("equId").toString();
    		String processNum = disp.get("processNum").toString();
    		String planStarttime = disp.get("planStarttime").toString();
    		String planEndtime = disp.get("planEndtime").toString();
    		
			String equName =  jobPlanService.getEquNameByEquId(equid);
			disp.put("equName", equName); //设备名称返回
    		for(Map<String, Object> job:jobMaplst){
    			String id = job.get("id").toString();
    			if(proid.equals(id)){//相同的工序ID,-->从工序列表返回给作业列表
    				job.put("equId", equid);  //把selectedJobdispatch中的设备id改入入jobMaplst中
    				job.put("processNum", processNum);
    				job.put("planStarttime", planStarttime);
    				job.put("planEndtime", planEndtime);
    			}
    		}
    	}
    	
    	
    	
    }
    
    /**
     * 取消
     * @param event
     */
    public void onCancel(RowEditEvent event) {  

    }

   private List<TJobInfo>  joblst = new  ArrayList<TJobInfo>(); //用来放对象的数据
   List<Map<String, Object>>  jobMaplst = new  ArrayList<Map<String, Object>>(); //用来放MAP的数据
   private List<TJobdispatchlistInfo>  jobdispatchlst = new  ArrayList<TJobdispatchlistInfo>();
    /**
     * 生成工单
     */
   public void produceJobDispathList(){	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
	//工序生成作业
    joblst.clear();
//	List<Map<String, Object>> lst = jobPlanService.getJobByJobPlanId(jobplabid); //作业清单= 数据库中+生成的
	jobMaplst.clear();
   	for(Map<String, Object> gg:selectedProcess){
   		//生成对象放入列表--无
   		TJobInfo p = new TJobInfo();
   		p.setNo("JOB_"+gg.get("no")+"_"+jobplabid);//作业编号
   		p.setName(gg.get("name")+"_作业_"+jobplabid);//作业名称
   		p.setTheoryWorktime(Integer.parseInt(gg.get("theoryWorktime").toString()));//理论工时
   		TJobplanInfo tji = new TJobplanInfo();
   		tji.setId(new Long(gg.get("id").toString()));
   		p.setTJobplanInfo(tji);
   		//生成MAP放入列表
   		String num = String.valueOf((int)(Math.random()*100));
   		Map<String, Object> pMap = new HashMap<String, Object>();
   		pMap.put("no","JOB_"+gg.get("no")+"_"+jobplabid+"_"+num);//作业编号
   		pMap.put("name",gg.get("cname")+"_作业_"+jobplabid+"_"+num);//作业名称
   		pMap.put("theoryWorktime",Integer.parseInt(gg.get("theoryWorktime").toString()));//理论工时
   		pMap.put("equName", gg.get("equName")); //设备名称
   		pMap.put("equId", gg.get("equId")); //设备ID
   		pMap.put("id",gg.get("id").toString()); //工序ID
//   		pMap.put("cid",gg.get("cid").toString());//作业计划ID
   		pMap.put("bianma",gg.get("no").toString());            //工序编码，为拼凑所用
   		pMap.put("xuliehao",gg.get("equSerialNo").toString()); //工序序列号，为拼凑所用
   		pMap.put("processNum",thisPlanNum);              //计划数量
   		pMap.put("planStarttime",sdf.format(startTime));//开始时间
   		pMap.put("planEndtime",sdf.format(endTime));     //结束时间   

   		/*
		boolean b = false;
		for (Map<String, Object> g : lst) {
			if (g.get("jobNo") != null) {
				String no = (String) g.get("jobNo");
				if(no.equals(pMap.get("no"))){  //判断生成的NO和数据库查出来的NO重复
					b = true;
					break;
				}
			}
		}
		if(b== false){ //放成为2种类型的2份
		joblst.add(p); 
		jobMaplst.add(pMap);
		}	
		*/
		joblst.add(p); 
		jobMaplst.add(pMap);   //可以多次生成
   		
	}
   	//作业生成工单
    String time = sdf1.format(new Date());
    jobdispatchlst.clear();
    jobdispatchlist.clear();
   	for(Map<String, Object> pMap : jobMaplst){
   	//生成对象放入列表--无
   		TJobdispatchlistInfo tp = new TJobdispatchlistInfo();
   		tp.setNo("WO_"+pMap.get("no")+"_"+time); //工单编号
   		tp.setName("WO_"+pMap.get("name")+"_"+time);//工单名称
   		tp.setTheoryWorktime(Integer.parseInt(pMap.get("theoryWorktime").toString()));//理论工时
   		tp.setProcessNum(Integer.parseInt(thisPlanNum)); //计划数量
   		tp.setPlanStarttime(startTime);                  //开始时间
   		tp.setPlanEndtime(endTime);                      //结束时间
   		TEquipmentInfo te = new TEquipmentInfo();
   		te.setEquId(new Long(pMap.get("equId").toString()));
   		tp.setTEquipmentInfo(te);
//   		TJobInfo tj = new TJobInfo();
//   		tj.setId(id);
//   		tp.setTJobInfo(tj);//作业ID还没有成
   	//生成MAP放入列表
   		Map<String, Object> tpMap = new HashMap<String, Object>();
   		tpMap.put("no","WO_"+pMap.get("bianma")+"_"+time+"_"+pMap.get("id")); //工单编号 
   		tpMap.put("processId",pMap.get("id")); //工序ID
   		tpMap.put("processNo",pMap.get("bianma"));//工序编码,作业编码的剪切,可以直接pMap.get("bianma")得到
   		tpMap.put("name",pMap.get("name").toString());//作业名称
   		tpMap.put("equId",pMap.get("equId").toString());//设备ID
   		tpMap.put("equName",pMap.get("equName").toString());//设备名称
   		tpMap.put("processNum",thisPlanNum);              //计划数量
		tpMap.put("planStarttime",sdf.format(startTime));//开始时间
		tpMap.put("planEndtime",sdf.format(endTime));     //结束时间   
		
		String no = pMap.get("id").toString(); //工序ID
		List<Map<String,Object>> nolst =  jobPlanService.getSerNoByProcessId(no);
		Map<String,Object> noMap = new HashMap<String,Object>();
		for(Map<String,Object> n : nolst){
			if(n.get("equId")!=null && !"".equals(n.get("equId"))){//设备ID
				noMap.put((String)n.get("equName"),n.get("equId").toString());
			}
		}
		tpMap.put("euqmap", noMap);  //设备名称Map,修改是用
   		
   	//放成为2种类型的2份	
   		jobdispatchlst.add(tp);  		
   		jobdispatchlist.add(tpMap);   //生成的+
    	}
        	/*    //数据库原有的不显示
	   	    List<Map<String, Object>> skillst = jobPlanService.getDispatchList(skillId);
			for(Map<String, Object> m : skillst){
			jobdispatchlist.add(m);      //加上数据库原有的
			    }
			*/
			jobdispatchModel = new TJobDispatchDataModel(jobdispatchlist);
   }
   
   /**
    * 保存工单
    */
   public void saveJobDispatch(){
	   jobPlanService.saveJobDispatch(this);
	   
   }
    
	/**==================================set,get方法====================================================*/

	public IJobPlanService getJobPlanService() {
		return jobPlanService;
	}

	public void setJobPlanService(IJobPlanService jobPlanService) {
		this.jobPlanService = jobPlanService;
	}

	public Map<String, Object> getJobPlanNameMap() {
		return jobPlanNameMap;
	}

	public void setJobPlanNameMap(Map<String, Object> jobPlanNameMap) {
		this.jobPlanNameMap = jobPlanNameMap;
	}

	public String getJobplabid() {
		return jobplabid;
	}


	public void setJobplabid(String jobplabid) {
		this.jobplabid = jobplabid;
	}

	public Map<String, Object> getPartTypeMap() {
		return partTypeMap;
	}

	public void setPartTypeMap(Map<String, Object> partTypeMap) {
		this.partTypeMap = partTypeMap;
	}


	public String getPartTypeId() {
		return partTypeId;
	}

	public void setPartTypeId(String partTypeId) {
		this.partTypeId = partTypeId;
	}

	public Map<String, Object> getSkillMap() {
		return skillMap;
	}

	public void setSkillMap(Map<String, Object> skillMap) {
		this.skillMap = skillMap;
	}

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}


	public String getThisPlanNum() {
		return thisPlanNum;
	}

	public void setThisPlanNum(String thisPlanNum) {
		this.thisPlanNum = thisPlanNum;
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

	public List<Map<String, Object>> getBatchlist() {
		return batchlist;
	}

	public void setBatchlist(List<Map<String, Object>> batchlist) {
		this.batchlist = batchlist;
	}

	public TJobDispatchDataModel getMediumProcessModel() {
		return mediumProcessModel;
	}

	public void setMediumProcessModel(TJobDispatchDataModel mediumProcessModel) {
		this.mediumProcessModel = mediumProcessModel;
	}

	public Map<String, Object>[] getSelectedProcess() {
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

	public List<Map<String, Object>> getJobMaplst() {
		return jobMaplst;
	}

	public String getPlanNum() {
		return planNum;
	}

	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}
	
	public String getSelectDisble() {
		return selectDisble;
	}
	
	public void setSelectDisble(String selectDisble) {
		this.selectDisble = selectDisble;
	}
	
	public boolean isBooleanValue() {
		return booleanValue;
	}
	
	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}		
    
}

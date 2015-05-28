package smtcl.mocs.beans.jobplan;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.RowEditEvent;

import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.jobplan.IJobPlanService;

/**
 *
 * 作业计划添加Bean
 * @作者：yyh
 * @创建时间：2013-7-2 下午13:05:16
 * @修改者：songkaiang
 * @修改日期：
 * @修改说明：
 * @version V1.0
 */
@ManagedBean(name="JobPlanAdd")
@ViewScoped
public class JobPlanAddBean implements Serializable {

    /**
     * 作业计划接口实例
     */
    private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
    /**
     * 权限接口实例
     */
    private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
    /**
     * 生产计划集
     */
    protected Map<String,Object> producePlanMap; //变量首字母小写
    /**
     * 零件名称集
     */
    private List<Map<String,Object>> partMap = new ArrayList<Map<String,Object>>();
    /**
     * 作业计划ID
     */
    private String id;
    /**
     * 状态
     */
    private String status="新建";
    /**
     * 从属生产计划ID
     */
    private String uplanId;
    /**
     * 作业计划名称
     */
    private String name;
    /**
     * 作业计划编号
     */
    private String no;
    /**
     * 计划开始时间
     */
    private Date planStarttime;
    /**
     * 计划结束时间
     */
    private Date planEndtime;
    /**
     * 计划数量
     */
    private String planNum;

    /**
     * 零件编号ID
     */
    private String partTypeId;
    /**
     * 零件编号
     */
    private String partTypeNo;
    /**
     * 零件名称
     */
    protected String partTypeName;
    /**
     * 零件图纸号
     */
    protected String partTypeDrawingno;
    /**
     * 零件版本号
     */
    protected String partTypeVersion;
    /**
     * 优先级
     */
    private String priority;
    /**
     * 页面值清空
     */
    private String valueToNul;
    /**
     * A3--生产计划编号
     */
    private String proPlanNo;
    /**
     * 交货日期
     */
    private Date submitTime;
    /**
     * 计划类型
     */
    private String planType;
    /**
     * 父作业计划
     */
    private List<TJobplanInfo> pJobPlan;
    /**
     * 父作业计划id
     */
    private String pJobPlanId;

    private String isSuccess;

    private String allocatedNum;//已分配任务数量
    private List<Map<String,Object>> addTask=new ArrayList<Map<String,Object>>();//分配任务临时存储数据
    private List<Map<String,Object>> realaddTask=new ArrayList<Map<String,Object>>();//分配任务临时存储数据
    private String addTaskNo;//添加任务编号
    private String addTaskNum;//添加任务数量
    private boolean addbool=false;//是否显示添加按钮
    /**
     * 构造
     */
    public JobPlanAddBean(){
        //获取节点ID
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String nodeid = (String)session.getAttribute("nodeid");
        List<Map<String,Object>> lst = jobPlanService.getPlanAndForList(nodeid);//生产计划下拉列表
        producePlanMap = new HashMap<String,Object>();
        for(Map<String,Object> map : lst){
            if(map.get("cid")!=null && !"".equals(map.get("cid"))){
                producePlanMap.put((String)map.get("uplanName"),map.get("cid").toString());
            }
        }
        partMap = jobPlanService.getPartAndForList(nodeid);	//零件下拉里诶包
        //零件类型ID
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(request.getParameter("addpNo")!=null && !"".equals(request.getParameter("addpNo"))  && !request.getParameter("addpNo").equals("undefined")){
            partTypeId = request.getParameter("addpNo").trim();

            List<Map<String,Object>> lstpart =jobPlanService.getPartTypeByIdFor(partTypeId);
            if(lstpart.size()>0){
                Map<String,Object>  map = lstpart.get(0);
                if(map.get("bno")!=null){
                    partTypeNo = map.get("bno").toString();
                }
                if(map.get("bname")!=null){
                    partTypeName = map.get("bname").toString();
                }
                if(map.get("drawingno")!=null){
                    partTypeDrawingno = map.get("drawingno").toString();
                }
                if(map.get("version")!=null){
                    partTypeVersion = map.get("version").toString();
                }
            }
            status="新建";
        }
        //作业计划编号
        List<Map<String,Object>> partlst =  jobPlanService.getPartTypeByIdFor(partTypeId);
        if(partlst.size()>0){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            String dd  = sdf.format(new Date());
            no ="WP_"+dd;
        }
        addTask=new ArrayList<Map<String,Object>>();
        realaddTask=new ArrayList<Map<String,Object>>();
    }

    private String dialog;
    /**
     * 添加作业计划
     */
    public String addJobAction(){
        isSuccess="添加失败";
        if(no==null || "".equals(no)){
            int  radnum = (int)(Math.random()*1000000);  //生成6位随机数
            if(radnum<99999){
                radnum = radnum+100000;
            }
            String autoNo  = String.valueOf(radnum);
            no = "WP"+autoNo;   //作业计划编号NO不输入，自动生成给他
        }
        List<Map<String,Object>> lst = jobPlanService.getPlanByName(no);
        if(lst.size()==0){   //此名称的作业计划不存在，提交
            isSuccess=jobPlanService.addJobPlan(this);
        }else{
            dialog = "show";
            HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            request.setAttribute("dialog", dialog);
        }
        return "jobplanadd";
    }

    private String show; //检测编号返回显示信息
    /**
     * 检测编号方法
     */
    public void getShowInfo(){
        List<Map<String,Object>> lst = jobPlanService.getPlanByName(no);
        if(lst.size()==0){ //不存在
            show = "编号不存在,可用";
        }else{             //已存在
            show = "编号已经存在,不可用!";
        }
    }


    /**
     * 作业计划编号的模糊查询
     * @param query 作业计划编号
     * @return 作业计划编号集合
     */
    public List<String> complete(String query) {
        return jobPlanService.getTJobplanInfoNo(query);
    }

    public void getPTJobPlanInfo(){
        System.out.println("planType:"+planType);
        if(null==planType||"".equals(planType)||planType.equals("1")){
            pJobPlan=new ArrayList<TJobplanInfo>();
            addbool=false;
        }else if(planType.equals("2")){
            pJobPlan=jobPlanService.getTJobplanInfoByBatchNo(planType,partTypeId);
            addbool=true;
        }else{
            pJobPlan=jobPlanService.getTJobplanInfoByBatchNo(planType,partTypeId);
            addbool=false;
        }

    }
    public void getProPlanNoValue(){
        int sum=0;
        for(Map<String,Object> map:addTask){
            sum=Integer.parseInt(null==map.get("addTaskNum")?"0":map.get("addTaskNum").toString())+sum;
        }
        allocatedNum=(Integer.parseInt(planNum)-sum)+"";//计算待分配任务数
    }
    /**
     * 为分配任务临时数据 添加数据
     */
    public void addTaskData(){
        int i=0;//唯一id
        int sum=0;//已分配总数
        boolean addbool=true; //是否重复   是 不重复   否 重复
        for(Map<String,Object> map:addTask){
            sum=Integer.parseInt(null==map.get("addTaskNum")?"0":map.get("addTaskNum").toString())+sum;
            i=Integer.parseInt(map.get("id")+"");
            if(addTaskNo.equals(map.get("addTaskNo")+"")){
                addbool=false;
            }
        }//判断下一个id值 和 总已分配的数量

        if(addbool){//如果不重复
            Map<String,Object> at=new HashMap<String, Object>();
            at.put("id", (i+1)+"");//设置id唯一
            at.put("addTaskNo", addTaskNo);
            at.put("addTaskNum", addTaskNum);
            at.put("reportNum", 0);

            Map<String,Object> realat=new HashMap<String, Object>();
            realat.put("id", (i+1)+"");//设置id唯一
            realat.put("addTaskNo", addTaskNo);
            realat.put("addTaskNum", addTaskNum);
            realat.put("reportNum", 0);

            if((Integer.parseInt(planNum)-(sum+Integer.parseInt(addTaskNum)))<0){//如果设置分配数量大于  计划数量
                //提示可分配数量不足
                FacesMessage msg = new FacesMessage("作业计划新增","新增失败,可分配数量不足！");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }else{
                allocatedNum=(Integer.parseInt(planNum)-(sum+Integer.parseInt(addTaskNum)))+""; //否则 正常计算 可分配数
                addTask.add(at);
                realaddTask.add(realat);
                addTaskNo="";
                addTaskNum="";
            }
        }else{
            FacesMessage msg = new FacesMessage("作业计划新增","新增失败,ERP任务号重复！");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }
    public void onRowCancel(RowEditEvent event){
    }
    /**
     * 为分配任务临时数据 修改数据
     * @param event 选择数据
     */
    public void updateTaskData(RowEditEvent event){
        Map<String,Object> map=((Map<String,Object>) event.getObject());
        int sum=0;//已分配总数
        for(Map<String,Object> mp:addTask){
            sum=Integer.parseInt(null==mp.get("addTaskNum")?"0":mp.get("addTaskNum").toString())+sum;
        }//判断 总已分配的数量

        for(Map<String,Object> mm:realaddTask){
            if(mm.get("id").toString().equals(map.get("id").toString())){
                System.out.println("addTask:"+mm.get("addTaskNo")+"--"+mm.get("addTaskNum"));
                System.out.println("event:"+map.get("addTaskNo")+"--"+map.get("addTaskNum"));
                if((Integer.parseInt(planNum)-sum)<0){//如果设置分配数量大于  计划数量
                    //提示可分配数量不足
                    FacesMessage msg = new FacesMessage("作业计划新增","新增失败,可分配数量不足！");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    map.put("addTaskNum", mm.get("addTaskNum"));
                }else if(Integer.parseInt(map.get("reportNum").toString())>Integer.parseInt(map.get("addTaskNum").toString())){//判断 任务分配数是否大于上报数
                    FacesMessage msg = new FacesMessage("作业计划新增","新增失败,可分配数量不能小于上报数!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    map.put("addTaskNum", mm.get("addTaskNum"));
                }else{
                    mm.put("addTaskNum", map.get("addTaskNum"));
                }
            }
        }

    }
    /**
     * 为分配任务临时数据 删除
     */
    public void delTaskData(Map<String,Object> selectedTask){
        allocatedNum=(Integer.parseInt(allocatedNum)+Integer.parseInt(selectedTask.get("addTaskNum").toString()))+""; //将删除的数量重新分配给 可分配数量
        addTask.remove(selectedTask);//删除数据
        realaddTask.remove(selectedTask);
    }

    /**=====================================set,get方法=================================================**/

    public String getDialog() {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        dialog = (String)request.getAttribute("dialog");
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    public IJobPlanService getJobPlanService() {
        return jobPlanService;
    }

    public void setJobPlanService(IJobPlanService jobPlanService) {
        this.jobPlanService = jobPlanService;
    }

    public IOrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(IOrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public List<Map<String, Object>> getPartMap() {
        return partMap;
    }

    public void setPartMap(List<Map<String, Object>> partMap) {
        this.partMap = partMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        status="新建";
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPlanStarttime() {
        return planStarttime;
    }

    public void setPlanStarttime(Date planStarttime) {
        this.planStarttime = planStarttime;
    }

    public Date getPlanEndtime() {
        return planEndtime;
    }

    public void setPlanEndtime(Date planEndtime) {
        this.planEndtime = planEndtime;
    }

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum;
    }

    public String getPartTypeId() {
        if(uplanId==null && partTypeId==null){
            HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (request.getParameter("planId") != null
                    && !"".equals(request.getParameter("planId"))
                    && !request.getParameter("planId").equals("undefined")) {
                id = request.getParameter("planId").trim();
                initDataByPlanId();
            }

            if(request.getParameter("partId") != null
                    && !"".equals(request.getParameter("partId"))
                    && !request.getParameter("partId").equals("undefined")){

                partTypeId = request.getParameter("partId").trim();
                planType = "1";//作业计划
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
                String dd  = format.format(new Date());
                no ="WP_"+dd;
                name = "WP_"+dd;
                priority = "1";
            }

        }

        return partTypeId;
    }

    private void initDataByPlanId() {
        if(id!=null && !id.isEmpty()){
            List<Map<String,Object>> maplst = jobPlanService.getPlanAndPartList(id);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(maplst.size()>0){
                Map<String,Object> map = maplst.get(0);
                if(map.get("status")!=null){
                    status = map.get("status").toString();
                }
                try {
                    if(map.get("planStarttime")!=null){
                        planStarttime = sdf.parse(map.get("planStarttime").toString());
                    }
                    if(map.get("planEndtime")!=null){
                        planEndtime = sdf.parse(map.get("planEndtime").toString());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(map.get("bid")!=null){
                    partTypeId = map.get("bid").toString();
                }
                if(map.get("bno")!=null){
                    partTypeNo = map.get("bno").toString();
                }
                if(map.get("bname")!=null){
                    partTypeName = map.get("bname").toString();
                }
                if(map.get("drawingno")!=null){
                    partTypeDrawingno = map.get("drawingno").toString();
                }
                if(map.get("version")!=null){
                    partTypeVersion = map.get("version").toString();
                }
                if(map.get("planNo")!=null){
                    proPlanNo = map.get("planNo").toString();
                }

                if(map.get("priority")!=null){
                    priority = map.get("priority").toString();
                }
                //作业计划编号
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
                String dd  = format.format(new Date());
                no ="WP_"+dd;
                name = "WP_"+dd;
                if(null!=map.get("planType")){
                    planType="2";
                    if(planType.equals("2")){
                        getPTJobPlanInfo();
                    }
                }
                if(map.get("id") != null){
                    pJobPlanId = map.get("id").toString();
                }
            }
        }
    }

    public void setPartTypeId(String partTypeId) {
        this.partTypeId = partTypeId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * 跳出页面后清空页面的值
     * @return valueToNul
     */
    public String getValueToNul() {
        id = null;
        status = null;
        uplanId = null;
        name = null;
        planStarttime = null;
        planEndtime = null;
        planNum = null;
        priority = null;
        return valueToNul;
    }

    public void setValueToNul(String valueToNul) {
        this.valueToNul = valueToNul;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPartTypeNo() {
        return partTypeNo;
    }

    public void setPartTypeNo(String partTypeNo) {
        this.partTypeNo = partTypeNo;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getProPlanNo() {
        return proPlanNo;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public List<TJobplanInfo> getpJobPlan() {
        return pJobPlan;
    }

    public void setpJobPlan(List<TJobplanInfo> pJobPlan) {
        this.pJobPlan = pJobPlan;
    }

    public String getpJobPlanId() {
        return pJobPlanId;
    }

    public void setpJobPlanId(String pJobPlanId) {
        this.pJobPlanId = pJobPlanId;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public List<Map<String, Object>> getAddTask() {
        return addTask;
    }

    public void setAddTask(List<Map<String, Object>> addTask) {
        this.addTask = addTask;
    }

    public String getAddTaskNo() {
        return addTaskNo;
    }

    public void setAddTaskNo(String addTaskNo) {
        this.addTaskNo = addTaskNo;
    }

    public String getAddTaskNum() {
        return addTaskNum;
    }

    public void setAddTaskNum(String addTaskNum) {
        this.addTaskNum = addTaskNum;
    }

    public String getAllocatedNum() {
        return allocatedNum;
    }

    public void setAllocatedNum(String allocatedNum) {
        this.allocatedNum = allocatedNum;
    }

    public boolean isAddbool() {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request.getParameter("planId") != null
                && !"".equals(request.getParameter("planId"))
                && !request.getParameter("planId").equals("undefined")) {
            addbool=true;
        }
        return addbool;
    }

    public List<Map<String, Object>> getRealaddTask() {
        return realaddTask;
    }

}

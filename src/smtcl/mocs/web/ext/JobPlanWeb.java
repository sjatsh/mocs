package smtcl.mocs.web.ext;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.faces.bean.ManagedProperty;
import javax.servlet.http.HttpServletRequest;

import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.authority.ICommonService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.StringUtils;

@Controller
public class JobPlanWeb {

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	/**
	 * �豸�ӿ�ʵ��
	 */
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	
	@ManagedProperty("#{commonService1}")
	private ICommonService commonService;
	
	/**
	 * ��ȡ��Ʒ������Ϣ
	 * @param request request
	 * @param partid ���ID
	 * @param planStatus ��ҵ�����Σ��ƻ�״̬
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @return ��Ʒ������Ϣ
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobplan/getjobplanName.action")
	public @ResponseBody Map<String, Object> getTestsExtData(HttpServletRequest request,String partid,String planStatus,String startTime,String endTime,String isexpand) throws Exception{
		String nodeid=(String)request.getSession().getAttribute("nodeid");
        Locale locale = SessionHelper.getCurrentLocale(request.getSession());
        return jobPlanService.getAllJobPlanAndPartInfo(nodeid,partid,planStatus,startTime,endTime,isexpand,locale.toString());
	}

    /**
     * ��ȡ��ҵ�ƻ���Ϣ
     * @param request request
     * @param ishighlight �Ƿ����
     * @param partid ���ID
     * @param planStatus ��ҵ�����Σ��ƻ�״̬
     * @param startTime ��ҵ�����Σ��ƻ���ʼʱ��
     * @param endTime ��ҵ�����Σ��ƻ�����ʱ��
     * @return ���ز�ѯ����ҵ�ƻ������μƻ�
     * @throws Exception
     */
	@RequestMapping(value ="/jobplan/getjobEvent.action")
	@ResponseBody
	public Map<String, Object> getTestsEvent(HttpServletRequest request,
			String ishighlight, String partid, String planStatus,String startTime, String endTime) throws Exception {
        Locale locale = SessionHelper.getCurrentLocale(request.getSession());
		List<Map<String, Object>> tempData = new ArrayList<Map<String, Object>>();
		// ��ʱ����
		String nodeid = (String) request.getSession().getAttribute("nodeid");
		List<Map<String, Object>> temp = jobPlanService
				.findAllJobPlan(nodeid, partid, planStatus, startTime, endTime);
		if (temp != null && temp.size() > 0) {
            Map<String,String> statusMap = Constants.statusMap;
			for (Map<String, Object> map : temp) {
                if(locale.toString().equals("en") || locale.toString().equals("en_US")){
                    String key = map.get("statusName").toString();
                    map.put("statusName",statusMap.get(key));
                }
				Integer start = Integer.parseInt(map.get("Status").toString());
				map.remove("Start");
                switch (start) {
					case 10:// ����(�����ҵ�ƻ�����ҵ�ƻ��ĳ�ʼ״̬Ϊ�½�)
						map.put("Cls", "ext_chuangjian");
						break;
					case 20:// ���ɹ�
						map.put("Cls", "ext_daipaigong");
						break;
					case 30:// ���ɹ�
						map.put("Cls", "ext_yipaigong");
						break;
					case 40:// ����
						map.put("Cls", "ext_shangxian");
						break;
					case 50:// �ӹ�
						map.put("Cls", "ext_jiagong");
						break;
					case 60:// ����
						map.put("Cls", "ext_jieshu");
						break;
					case 70:// ���
						map.put("Cls", "ext_wancheng");
						break;
                    case 80://��ͣ/�ָ�
                        map.put("Cls", "ext_pause");
                        break;
					default:
						map.put("Cls", "ext_default");
						break;
				}
				tempData.add(map);
			}
		}
		return getListMap(tempData);
	}
	
	/**
	 * ��ȡ��ҵ�ƻ���ϸ
	 * @param p ����
	 * @return ������ϸ��Ϣ
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobplan/jobDetail.action")
	public @ResponseBody Map<String, Object> getJobDetail(String p) throws Exception{
        return jobPlanService.findJobPlanDetail(p);
	}
	
	/**
	 * ��ȡ��ҵ�ƻ���ϸ��Ĭ��ȡt_jobplan_info�е�ID���ļ�¼��
	 * @param p ����
	 * @return ������ϸ��Ϣ
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobplan/findJobPlanDetailDefault.action")
	public @ResponseBody Map<String, Object> findJobPlanDetailDefault(String p) throws Exception{
        return jobPlanService.findJobPlanDetailDefault();
	}
	
	/**
	 * �����ҵ�ƻ�
	 */
	@RequestMapping(value="/jobplan/save.action")
	public @ResponseBody Map<String, Object> saveJobplan(String startTime,String startDate,String durationTime,String id,String resourceId,String name,String no,String planId,String planNum,String priority,String planNo,HttpServletRequest request){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			String nodeid=(String)request.getSession().getAttribute("nodeid");
		   
			//��ȡʱ��
			Date str = formatDate.parse(startTime);
			
			//��ȡ����	
			Date sDate = formatDate.parse(startDate);
			
			//ƴװ���ڸ�ʱ��
			String temp = StringUtils.formatDate(sDate, 2)+" "+StringUtils.formatDate(str, 5);
			
			//��ʼʱ��
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date tempDate = format1.parse(temp);			
			Long end = tempDate.getTime()+(Long.parseLong(durationTime)*3600000);			
			Date endDate = new Date(end);
			
			TPartTypeInfo tPartTypeInfo=commonService.get(TPartTypeInfo.class,Long.parseLong(resourceId));
			TJobplanInfo jobplanInfo=new TJobplanInfo();
			jobplanInfo.setTPartTypeInfo(tPartTypeInfo);
			jobplanInfo.setNo(no);
			jobplanInfo.setName(name);
			jobplanInfo.setStatus(10);//����״̬
			jobplanInfo.setPlanEndtime(endDate);
			jobplanInfo.setPlanStarttime(tempDate);
			if(StringUtils.isEmpty(planNum)) planNum="-1";
			jobplanInfo.setPlanNum(Integer.parseInt(planNum));
			jobplanInfo.setPriority(Integer.parseInt(priority));
			jobplanInfo.setNodeid(nodeid);
			jobplanInfo.setFinishNum(0);
			jobplanInfo.setQualifiedNum(0);
			jobplanInfo.setPlanNo(planNo); //�����ƻ����

			jobPlanService.addJobPlanInfo(jobplanInfo);
			
		  } catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	
	/**
	 * �޸���ҵ�ƻ�
	 */
	@RequestMapping(value="/jobplan/updateBasicJobPlanInfo.action")
	public @ResponseBody Map<String, Object> updateBasicJobPlanInfo(String startTime,String startDate,String durationTime,String id,String resourceId,String name,String no,String planNum,String planNo,String priority,HttpServletRequest request){
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			String nodeid=(String)request.getSession().getAttribute("nodeid");
		    //��ȡʱ��
			Date str = formatDate.parse(startTime);
			//��ȡ����
			Date sDate = formatDate.parse(startDate);
			//ƴװ���ڸ�ʱ��
			String temp = StringUtils.formatDate(sDate, 2)+" "+StringUtils.formatDate(str, 5);
			//��ʼʱ��
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date tempDate = format1.parse(temp);			
			Long end = tempDate.getTime()+(Long.parseLong(durationTime)*3600000);			
			Date endDate = new Date(end);			
			Long planId= (long) 0;
			if(!StringUtils.isEmpty(id)) planId=Long.parseLong(id);			
			TJobplanInfo jobplanInfo=commonService.get(TJobplanInfo.class,planId);//���º���ӵ���������
			
			jobplanInfo.setNo(no);
			jobplanInfo.setName(name);
			jobplanInfo.setPlanEndtime(endDate);
			jobplanInfo.setPlanStarttime(tempDate);
			if(StringUtils.isEmpty(planNum)) planNum="-1";
			jobplanInfo.setPlanNum(Integer.parseInt(planNum));
			jobplanInfo.setPriority(Integer.parseInt(priority));
			jobplanInfo.setPlanNo(planNo); //�����ƻ����
			jobplanInfo.setNodeid(nodeid);
			jobPlanService.updateJobPlanInfo(jobplanInfo);
			
		  } catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	
	}
	
	
	/**
	 * �޸���ҵ�ƻ�
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @param id ��ҵ�����Σ��ƻ�ID
	 * @param resourceId resourceID
	 */
	@RequestMapping(value="/jobplan/update.action")
	public @ResponseBody Map<String, Object> updateJobplan(String startTime ,String endTime , String id,String resourceId,String statusFlag){
		try {
			if(startTime!=null && endTime!=null && id!=null){
				TJobplanInfo tJobplanInfo = jobPlanService.geTJobplanInfoById(Long.parseLong(id));
				if(statusFlag.equals("40") || statusFlag.equals("50"))
				{
					tJobplanInfo.setPlanEndtime(format.parse(endTime));
				}
				else {
					tJobplanInfo.setPlanStarttime(format.parse(startTime));
					tJobplanInfo.setPlanEndtime(format.parse(endTime));
				}
				if(resourceId!=null){
					TPartTypeInfo tPartTypeInfo = commonService.get(TPartTypeInfo.class,Long.valueOf(resourceId));
					tJobplanInfo.setTPartTypeInfo(tPartTypeInfo);
				}
				jobPlanService.updateJobPlanInfo(tJobplanInfo);
				return getMap(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getModelMapError("�����쳣!");
		} 
		return getModelMapError("��������!");
	}
	
	/**
	 * ���Ԥ������
	 * @param id ��ҵ�ƻ�ID
	 * @param time ����ʱ��
	 */
	@RequestMapping(value="/jobplan/split.action")
	public void SplitJobPlan(String id,String time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			TJobplanInfo tJobplanInfo = jobPlanService.geTJobplanInfoById(Long.parseLong(id));
			Date tempEndDate = tJobplanInfo.getPlanEndtime();
			
			//�����޸Ĳ���
			tJobplanInfo.setPlanEndtime(format.parse(time));
			jobPlanService.updateJobPlanInfo(tJobplanInfo);
			
			//���д�������
			tJobplanInfo.setId(null);
			
			String tempNo = tJobplanInfo.getNo();
			
			//��ҵ�ƻ�No�ֶ��账��
			tJobplanInfo.setNo(tempNo);
			tJobplanInfo.setPlanStarttime(format.parse(time));
			tJobplanInfo.setPlanEndtime(tempEndDate);
			jobPlanService.addJobPlanInfo(tJobplanInfo);
			System.out.println("success��");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ɾ����ҵ�ƻ�
	 * @param id ��ҵ(����)�ƻ�ID
	 * @return ���ز�����Ϣ
	 */
	@RequestMapping(value="/jobplan/delete.action")
	public @ResponseBody Map<String, Object> deleteJobplan(String id){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		try {
			TJobplanInfo tJobplanInfo = jobPlanService.geTJobplanInfoById1(Long.parseLong(id));
			if(tJobplanInfo==null) {
				modelMap.put("data", "");
				modelMap.put("success", false);
			}else{
				jobPlanService.deleteJobPlanInfoById(tJobplanInfo);
				modelMap.put("data", "");
				modelMap.put("success", true);
			}
		} catch (NumberFormatException e) {
			return getModelMapError("����ʧ��!");
		}
		return modelMap;
	}
	
	
	/**
	 * ��ȡ��ҵ�ƻ����е�ǰ����ID
	 */
	@RequestMapping(value="/jobplan/getMaxJobPlanInfoId.action")
	public @ResponseBody String getMaxJobPlanInfoId(){
		try{
            return jobPlanService.getMaxJobPlanInfoId();
		} catch (Exception ignored) {
			
		}
		
		return null;
	}
	
	private Map<String,Object> getListMap1(List<Map<String, Object>> tempMap){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("total", tempMap.size());
		modelMap.put("root", tempMap);
		return modelMap;
	}
	
	private Map<String,Object> getListMap(List<Map<String, Object>> tempMap){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//modelMap.put("total", tempMap.size());
		modelMap.put("data", tempMap);
		modelMap.put("success", true);	
		return modelMap;
	}
	
	private Map<String,Object> getMap(Map<String, Object> tempMap){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("data", tempMap);
		modelMap.put("success", true);
		return modelMap;
	}
	
	private Map<String,Object> getModelMapError(String msg){
		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", false);
		return modelMap;
	} 
	
	/**
	 * ��ȡ���������ƻ�����
	 */
	@RequestMapping(value="/jobplan/getSubordinatePlanInfoMap.action")
	public @ResponseBody Map<String, Object> getSubordinatePlanInfo(HttpServletRequest request){
		try {
			String nodeid=(String)request.getSession().getAttribute("nodeid");
			List<Map<String, Object>> tJobplanInfo = jobPlanService.getSubordinatePlanInfoMap(nodeid);
			return getListMap1(tJobplanInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	/**
	 * ��ȡ���ȼ�
	 */
	@RequestMapping(value="/jobplan/getPriority.action")
	public @ResponseBody Map<String, Object> getPriorityInfo(){
		try {
			List<Map<String,Object>> lst = jobPlanService.getPriority();		
			return getListMap1(lst);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	/**
	 * ��ȡ�豸��Դ��Ϣ
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobplan/updateJobPlanInfoStatus.action")
	public @ResponseBody Boolean updateJobdispatchStatus(String jobPlanId,String status) throws Exception{
		try
		{
			jobPlanService.updateJobPlanInfoStatus(jobPlanId,status);
	        return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * �õ���ҵ�ƻ����
	 */
	@RequestMapping(value ="/jobplan/getJobplanNo.action")
	public @ResponseBody String getJobplanNo(String partTypeId){
		String no="";
		List<Map<String,Object>> partlst =  jobPlanService.getPartTypeByIdFor(partTypeId);
		String maxId  = jobPlanService.getMaxJobPlanId();
		if(partlst.size()>0){
			Map<String,Object> m = partlst.get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String dd  = sdf.format(new Date());
			no ="WP_"+m.get("bno")+"_"+dd+"_"+maxId;
		}
		return no;
	}
	
/*-----------------------------------------------------------------------------------------------------*/
//	public IJobPlanService getJobPlanService() {
//		return jobPlanService;
//	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
//
//	public void setJobPlanService(IJobPlanService jobPlanService) {
//		this.jobPlanService = jobPlanService;
//	}
	
	
}

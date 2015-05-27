package smtcl.mocs.web.ext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedProperty;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import smtcl.mocs.pojos.device.TUserProdctionPlan;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.authority.ICommonService;
import smtcl.mocs.services.jobplan.IJobPlanService;
import smtcl.mocs.utils.device.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class JobPlanWeb {

	private  Gson gson = new GsonBuilder().serializeNulls().create();
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	/**
	 * �豸�ӿ�ʵ��
	 */
	@ManagedProperty("#{deviceService}")
	private IJobPlanService jobPlanService;
	
	@ManagedProperty("#{commonService1}")
	private ICommonService commonService;
	
	/**
	 * ��ȡ��Ʒ������Ϣ
	 * @param request
	 * @param partName
	 * @param planStatus
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobplan/getjobplanName.action")
	public @ResponseBody Map<String, ? extends Object> getTestsExtData(HttpServletRequest request,String partid,String planStatus,String startTime,String endTime,String isexpand) throws Exception{
		String nodeid=(String)request.getSession().getAttribute("nodeid");
		
		Map<String, Object> tempData2 = jobPlanService.getAllJobPlanAndPartInfo(nodeid,partid,planStatus,startTime,endTime,isexpand);
		return tempData2;
	}

	/**
	 * ��ȡ��ҵ�ƻ���Ϣ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobplan/getjobEvent.action")
	@ResponseBody
	public Map<String, ? extends Object> getTestsEvent(HttpServletRequest request,
			String ishighlight, String partid, String planStatus,String startTime, String endTime) throws Exception {
		
		List<Map<String, Object>> tempData = new ArrayList<Map<String, Object>>();
		// ��ʱ����
		String nodeid = (String) request.getSession().getAttribute("nodeid");
		List<Map<String, Object>> temp = (List<Map<String, Object>>) jobPlanService
				.findAllJobPlan(nodeid, partid, planStatus, startTime, endTime);
		long maxId = 0;
		if (temp.size() > 0 && temp != null) {
			String newplanid = "";//�½���׼������ҵ��id,ͨ�����ζ�λ����ҵ��Ϣ
			for (Map<String, Object> map : temp) {
				Integer start = Integer.parseInt(map.get("Status").toString());
				if (maxId == 0){
					maxId = Long.parseLong(map.get("Id").toString()); // ��ȡ���ֵ
					if(map.get("pid") != null && !map.get("pid").equals(""))
						newplanid = map.get("pid").toString();//��λ����ҵ��id
				}
				map.remove("Start");

				// ��ҵ�ƻ���ʱͳһΪ��ɫ
				String planType = map.get("planType").toString();
				if ("1".equals(planType)) {
					if(!StringUtils.isEmpty(newplanid) && map.get("Id").toString().equals(newplanid)){
						if(ishighlight!=null){
							map.put("Cls", "ext_jobplan_maxId");//��λ����ҵ�ƻ�����Ӹ�����ʾ
						}else{
							map.put("Cls", "ext_jobplan");
						}
					}else{
						map.put("Cls", "ext_jobplan");
					}
				} else {
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
					default:
						map.put("Cls", "ext_defult");
						break;
					}
				}
				tempData.add(map);
			}
		}
		return getListMap(tempData);
	}
	
	/**
	 * ��ȡ��ҵ�ƻ���ϸ
	 * @param p
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobplan/jobDetail.action")
	public @ResponseBody Map<String, ? extends Object> getJobDetail(String p) throws Exception{
		Map<String, Object> tempData= jobPlanService.findJobPlanDetail(p);
		return tempData;
	}
	
	/**
	 * ��ȡ��ҵ�ƻ���ϸ��Ĭ��ȡt_jobplan_info�е�ID���ļ�¼��
	 * @param 
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value ="/jobplan/findJobPlanDetailDefault.action")
	public @ResponseBody Map<String, ? extends Object> findJobPlanDetailDefault(String p) throws Exception{
		Map<String, Object> tempData= jobPlanService.findJobPlanDetailDefault();
		return tempData;
	}
	
	/**
	 * �����ҵ�ƻ�
	 * @return
	 */
	@RequestMapping(value="/jobplan/save.action")
	public @ResponseBody Map<String, ? extends Object> saveJobplan(String startTime,String startDate,String durationTime,String id,String resourceId,String name,String no,String planId,String planNum,String priority,String planNo,HttpServletRequest request){
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
			if(StringUtils.isEmpty(planId)) planId="-1";
			TUserProdctionPlan tUserProdctionPlan=commonService.get(TUserProdctionPlan.class,Long.parseLong(planId));
			TJobplanInfo jobplanInfo=new TJobplanInfo();			
			jobplanInfo.setTPartTypeInfo(tPartTypeInfo);
//			jobplanInfo.setTUserProdctionPlan(tUserProdctionPlan);
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
	 * @return
	 */
	@RequestMapping(value="/jobplan/updateBasicJobPlanInfo.action")
	public @ResponseBody Map<String, ? extends Object> updateBasicJobPlanInfo(String startTime,String startDate,String durationTime,String id,String resourceId,String name,String no,String planNum,String planNo,String priority,HttpServletRequest request){
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
			//TPartTypeInfo tPartTypeInfo=commonService.get(TPartTypeInfo.class,Long.parseLong(resourceId)); //û�д�ֱ�϶�			
			Long planId=new Long(0);
			if(!StringUtils.isEmpty(id)) planId=Long.parseLong(id);			
			TJobplanInfo jobplanInfo=commonService.get(TJobplanInfo.class,planId);//���º���ӵ���������
			
//			jobplanInfo.setTPartTypeInfo(tPartTypeInfo);
			jobplanInfo.setNo(no);
			jobplanInfo.setName(name);
//			jobplanInfo.setStatus(10);//����״̬
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
	 * @param startTime
	 * @param endTime
	 * @param id
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value="/jobplan/update.action")
	public @ResponseBody Map<String, ? extends Object> updateJobplan(String startTime ,String endTime , String id,String resourceId,String statusFlag){
		try {
			if(startTime!=null && endTime!=null && id!=null){
				TJobplanInfo tJobplanInfo = jobPlanService.geTJobplanInfoById(Long.parseLong(id));
				if(statusFlag=="40"||statusFlag=="50")
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
	 * @param id
	 * @param time
	 * @return
	 */
	@RequestMapping(value="/jobplan/split.action")
	public @ResponseBody Map<String, Object> SplitJobPlan(String id,String time){
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
		
		return null;
	}
	
	/**
	 * ɾ����ҵ�ƻ�
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/jobplan/delete.action")
	public @ResponseBody Map<String, ? extends Object> deleteJobplan(String id){
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
	 * @return
	 */
	@RequestMapping(value="/jobplan/getMaxJobPlanInfoId.action")
	public @ResponseBody String getMaxJobPlanInfoId(){
		try{
			String mID=jobPlanService.getMaxJobPlanInfoId();
			return mID;
		} catch (Exception e) {
			
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
	
	private Map<String,Object> getMap1(String tempMap){
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
	 * @return
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
	 * @return
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
			no ="WP_"+(String)m.get("bno")+"_"+dd+"_"+maxId;
		}
		return no;
	}
	
/*-----------------------------------------------------------------------------------------------------*/
	public IJobPlanService getJobPlanService() {
		return jobPlanService;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public void setJobPlanService(IJobPlanService jobPlanService) {
		this.jobPlanService = jobPlanService;
	}
	
	
}

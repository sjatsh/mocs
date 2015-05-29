package smtcl.mocs.web.ext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.services.storage.ISetUnitServcie;

/**
 * ��ҵ�ƻ�-��ѯ����-ajax����
 * @author songkaiang
 *
 */
@Controller
public class JobPlanDetailWeb {
	
	/**
	 * ���Service�ӿ�
	 */
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	/**
	 * �����ӿ�ʵ��
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	
	private IDeviceService baogongService=(IDeviceService)ServiceFactory.getBean("deviceService");
	/**
	 * ��λ���ýӿ�
	 */
	private  ISetUnitServcie setunitService=(ISetUnitServcie) ServiceFactory.getBean("setunitService");
	/**
	 * ͨ��ajax��ȡ�������
	 * @param request
	 * @param partName
	 * @return
	 */
	@RequestMapping(value ="/jobplan/getPartList.action")
	public @ResponseBody List<Map<String,Object>> getPartList(HttpServletRequest request,String partName){
		String nodeid = (String)request.getSession().getAttribute("nodeid");
		List<TPartTypeInfo> list = partService.getAllPartType(nodeid, partName);
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		for(TPartTypeInfo t : list){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", t.getId());
			map.put("name", t.getName());
			listMap.add(map);
		}
		return listMap;
	}

	/**
	 * ͨ��ajax��ȡ�����
	 * @param request
	 * @param taskNum
	 * @return
	 */
	@RequestMapping(value ="/jobplan/getTaskNumList.action")
	public @ResponseBody List<Map<String,Object>> getTaskNumList(HttpServletRequest request,String taskNum){
		String nodeid = (String)request.getSession().getAttribute("nodeid");
		return jobDispatchService.getBatchNoList(nodeid, taskNum);
	}
	
	@RequestMapping(value ="/jobplan/getequNameList.action")
	public @ResponseBody List<Map<String,Object>> getequNameList(HttpServletRequest request,String equName){
		String nodeid = (String)request.getSession().getAttribute("nodeid");
		return jobDispatchService.getequNameList(nodeid, equName);
	}
	
	@RequestMapping(value ="/jobplan/getequSerialNoList.action")
	public @ResponseBody List<Map<String,Object>> getequSerialNoList(HttpServletRequest request,String equSerialNo){
		
		return baogongService.getEquSerialNoMap(equSerialNo);
	}
	
	@RequestMapping(value ="/jobplan/getequNameWithoutNodeList.action")
	public @ResponseBody List<Map<String,Object>> getequNameWithoutNodeList(HttpServletRequest request,String equName){
		
		return baogongService.getEquTypeNameMap(equName);
	}
	
}

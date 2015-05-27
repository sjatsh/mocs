package smtcl.mocs.web.ext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.services.jobplan.UpdataJobDispatch;

/**
 * 在制品查询页面获取数据类
 * @author songkaiang
 *
 */
@Controller
public class ReportFormsWeb {
	
	/**
	 * 工单接口实例
	 */
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	private UpdataJobDispatch updataJobDispat = (UpdataJobDispatch)ServiceFactory.getBean("updataJobDispatch");
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	
	@RequestMapping(value = "/productInProgress/getReportData.action")
	public @ResponseBody Map<String, Object> getReportData(HttpServletRequest request,
			String descParam, String startTime, String endTime,
			String partName, String batchNo) {
		String nodeid = (String)request.getSession().getAttribute("nodeid");
		List<Map<String, Object>> list1 = jobDispatchService.getJobDispatchReportData(nodeid,descParam,startTime,endTime,partName,batchNo);
		if(descParam != null && !descParam.isEmpty()){
			return getListMap(list1);
		}
		List<Map<String,Object>> list2 = updataJobDispat.getBatchNoByPartName2(nodeid,startTime,endTime,partName,batchNo);
		List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();
		int size = list2.size();
		for(int i=0;i<size;i++){
			Map<String,Object> map1 = list2.get(i);
			if(map1.get("flag").toString().equals("4"))
				continue;
			Map<String,Object> tempMap = new HashMap<String,Object>();
			if(map1.get("flag").toString().equals("1")){
				tempMap.put("workInNum", Integer.parseInt(map1.get("finishNum").toString())-Integer.parseInt(map1.get("finishNum").toString()));
				tempMap.put("taskNum", map1.get("taskNum"));
				tempMap.put("partName", map1.get("partName"));
				tempMap.put("finishNum", map1.get("finishNum"));
				tempMap.put("processName", "");
				tempMap.put("equSerialNo", "");
				tempMap.put("planNum", map1.get("planNum"));
				list3.add(tempMap);
			}
			if(map1.get("flag").toString().equals("2")){
				if(i<size-1){
					Map<String,Object> map2 = list2.get(i+1);
					if(map2.get("flag").toString().equals("3")){
						tempMap.put("taskNum", map1.get("taskNum"));
						tempMap.put("partName", map1.get("partName"));
						tempMap.put("finishNum", map1.get("finishNum"));
						tempMap.put("planNum", map1.get("planNum"));
						tempMap.put("processName", "");
						tempMap.put("equSerialNo", "");
						tempMap.put("workInNum", Integer.parseInt(map2.get("finishNum").toString())-Integer.parseInt(map1.get("finishNum").toString()));
						list3.add(tempMap);
						i++;
					}
				}
			}
			else if(map1.get("flag").toString().equals("3")){
				tempMap.put("workInNum", Integer.parseInt(map1.get("finishNum").toString())-Integer.parseInt(map1.get("finishNum").toString()));
				tempMap.put("taskNum", map1.get("taskNum"));
				tempMap.put("partName", map1.get("partName"));
				tempMap.put("finishNum", map1.get("finishNum"));
				tempMap.put("processName", "");
				tempMap.put("equSerialNo", "");
				tempMap.put("planNum", map1.get("planNum"));
				list3.add(tempMap);
			}
		}
		list1.addAll(list3);
		Collections.sort(list1, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				String str1 = o1.get("partName") + "" + o1.get("taskNum") + ""
						+ o1.get("processName") + "";
				String str2 = o2.get("partName") + "" + o2.get("taskNum") + ""
						+ o2.get("processName") + "";
				return str1.compareTo(str2);
			}
		});
		
		return getListMap(list1);
	}
	
	@RequestMapping(value = "/productInProgress/getDataByproductName")
	public @ResponseBody Map<String, Object> getDataByproductName(HttpServletRequest request,String partName){
		List<TPartTypeInfo> listPart = partService.getPartTypeInfo(partName);
		String id = "";
		for(TPartTypeInfo t : listPart){
			id = t.getId().toString();
			break;
		}
		Map<String,Object> list = this.getReportData(request, "", "", "", id, "");
		return list;
	}

	
	private Map<String,Object> getListMap(List<Map<String, Object>> tempMap){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("data", tempMap);
		modelMap.put("success", true);	
		return modelMap;
	}
}

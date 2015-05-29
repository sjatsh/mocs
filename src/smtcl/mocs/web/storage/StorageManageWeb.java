package smtcl.mocs.web.storage;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedProperty;
import javax.servlet.http.HttpServletRequest;

import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import smtcl.mocs.services.storage.IMaterielManageService;

@Controller
public class StorageManageWeb {
	
	IMaterielManageService materielService = (IMaterielManageService)ServiceFactory.getBean("materielManageService");;
	
	/**
	 * 模糊查询获取库房信息
	 * @param storageValue
	 * @return
	 */
	@RequestMapping(value ="/storage/getStorageIdValue.action")
	@ResponseBody
	public List<Map<String,Object>> getStorageIdValue(HttpServletRequest request,String storageValue){
		String nodeid=(String)request.getSession().getAttribute("nodeid");
		return materielService.findStrogeNo(nodeid, storageValue);
	}
	
	/**
	 * 模糊查询获取库房信息
	 * @param storageValue
	 * @return
	 */
	@RequestMapping(value ="/storage/getMaterialIdValue.action")
	@ResponseBody
	public List<Map<String,Object>> getMaterialIdValue(HttpServletRequest request,String materialValue,String flag){
		String nodeid=(String)request.getSession().getAttribute("nodeid");
		return materielService.findMaterialInfo(nodeid, materialValue,flag);
	}
	
	/**
	 * 
	 * @param request
	 * @param materialValue
	 * @param flag
	 * @return
	 */
	@RequestMapping(value ="/storage/getPositionIdValue.action")
	@ResponseBody
	public List<Map<String,Object>> getPositionIdValue(HttpServletRequest request,String query){
		String nodeid=(String)request.getSession().getAttribute("nodeid");
		return materielService.findPositionInfo(nodeid, query);
	}

	
	//-----------------getter---setter--------------------------------------
//	public IMaterielManageService getMaterielService() {
//		return materielService;
//	}
//
//	public void setMaterielService(IMaterielManageService materielService) {
//		this.materielService = materielService;
//	}
	
	

}

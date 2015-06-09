package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.device.IResourceService;

/**
 * @锟斤拷锟斤拷锟斤拷锟斤拷
 * @锟斤拷锟斤拷锟剿ｏ拷 FW
 * @锟斤拷锟斤拷锟斤拷锟节ｏ拷 2015-03-03
 * @version锟斤拷 V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name="ProgramDownLoadBean")
@ViewScoped
public class ProgramDownLoadBean implements Serializable{

	/**
	 * 锟斤拷源锟接匡拷实锟斤拷
	 */
	private IResourceService resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
    /**
     * 锟斤拷锟较硷拷锟斤拷List
     */
	 List<Map<String,Object>> materialList = new ArrayList<Map<String,Object>>();
	/**
     * 锟斤拷锟津集猴拷List
     */
	 List<Map<String,Object>> processList = new ArrayList<Map<String,Object>>();
	/**
     * 锟斤拷锟津集猴拷List
     */
	 List<Map<String,Object>> programList = new ArrayList<Map<String,Object>>();
	/**
     * 锟斤拷锟斤拷Id
     */
	String materialId;
	/**
	 * 锟斤拷锟斤拷No
	 */
	String materialNo;
	/**
	 * 锟斤拷锟斤拷锟斤拷锟�
	 */
	String materialName;
	/**
	 * 锟斤拷锟斤拷Id
	 */
	String processId;
	/**
	 * 锟斤拷锟斤拷锟斤拷锟�
	 */
	String processName;
	/**
	 * 锟节碉拷
	 */
	String nodeId;
	/**
	 * 
	 */
	String success;
	
	/**
	 * 锟斤拷锟届方锟斤拷
	 */
	public ProgramDownLoadBean(){
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeId=(String)session.getAttribute("nodeid2");
		materialList = resourceService.getMaterialInfo(nodeId);
		getBingProgramList();
	}
	
	/**
	 * 锟斤拷锟斤拷谋锟�
	 */
	public void materialChange(){
		if(null==materialId){
			return;
		}
		
		processList= resourceService.getProcessInfo(materialId,nodeId);
		getBingProgramList();
	}
	
	
	/**
	 * 锟斤拷锟斤拷谋锟�
	 */
	public void processChange(){
		if(null==processId ||processId.equals("")){
			return;
		}
		getBingProgramList();
	}
	
	/**
	 * 锟斤拷取锟襟定筹拷锟斤拷
	 */
	private void getBingProgramList(){
        programList.clear();
		
		//锟窖绑定筹拷锟斤拷(锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
		programList =resourceService.getBingProgramInfo(materialId,processId,nodeId);
		
		for(Map<String,Object> map:programList){
			map.put("isBing", false);
		}
	}
	
	/**
	 * 锟襟定筹拷锟斤拷前check
	 */
	public void checkData(){
		success ="";
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:programList){
			if(map.get("isBing").toString().equals("true")){
				dataList.add(map);
			}
		}
		
		if(dataList.size()>0){
			if(dataList.size()>1){
				success ="锟斤拷选锟斤拷一锟斤拷锟斤拷录锟斤拷锟斤拷锟斤拷锟截ｏ拷";
			}
		}else{
			success ="锟斤拷选锟斤拷要锟斤拷锟截的筹拷锟斤拷";
		}
		
	}
	
	/**
	 * 锟斤拷锟斤拷锟斤拷
	 * @return
	 */
	public void downLoadProgram(){
		success="";
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:programList){
			if(map.get("isBing").toString().equals("true")){
				dataList.add(map);
			}
		}
		
		if(dataList.size()>0){
			if(dataList.size()>1){
				success ="锟斤拷选锟斤拷一锟斤拷锟斤拷录锟斤拷锟斤拷锟斤拷锟截ｏ拷";
//				FacesMessage msg = new FacesMessage("程序下载","请选择一个文件进行下载！");
//				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}else{
			success ="锟斤拷选锟斤拷要锟斤拷锟截的筹拷锟斤拷";
//			FacesMessage msg = new FacesMessage("程序下载","文件不存在！");
//			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		if(dataList.size()>0){
			success = resourceService.downLoadProgram(dataList.get(0).get("id").toString());
		}
	}

	public List<Map<String, Object>> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Map<String, Object>> materialList) {
		this.materialList = materialList;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getNodeId() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeId=(String)session.getAttribute("nodeid2");
		materialList = resourceService.getMaterialInfo(nodeId);
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public List<Map<String, Object>> getProcessList() {
		return processList;
	}

	public void setProcessList(List<Map<String, Object>> processList) {
		this.processList = processList;
	}

	public List<Map<String, Object>> getProgramList() {
		return programList;
	}

	public void setProgramList(List<Map<String, Object>> programList) {
		this.programList = programList;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
    
	
    
	
}

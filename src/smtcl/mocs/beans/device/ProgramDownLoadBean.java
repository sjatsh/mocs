package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.device.IResourceService;

/**
 * @程序下载
 * @创建人： FW
 * @创建日期： 2015-03-03
 * @version： V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name="ProgramDownLoadBean")
@ViewScoped
public class ProgramDownLoadBean implements Serializable{

	/**
	 * 资源接口实例
	 */
	private IResourceService resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
    /**
     * 物料集合List
     */
	 List<Map<String,Object>> materialList = new ArrayList<Map<String,Object>>();
	/**
     * 工序集合List
     */
	 List<Map<String,Object>> processList = new ArrayList<Map<String,Object>>();
	/**
     * 程序集合List
     */
	 List<Map<String,Object>> programList = new ArrayList<Map<String,Object>>();
	/**
     * 物料Id
     */
	String materialId;
	/**
	 * 物料No
	 */
	String materialNo;
	/**
	 * 物料名称
	 */
	String materialName;
	/**
	 * 工序Id
	 */
	String processId;
	/**
	 * 工序名称
	 */
	String processName;
	/**
	 * 节点
	 */
	String nodeId;
	/**
	 * 
	 */
	String success;
	
	/**
	 * 构造方法
	 */
	public ProgramDownLoadBean(){
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeId=(String)session.getAttribute("nodeid2");
		materialList = resourceService.getMaterialInfo(nodeId);
		getBingProgramList();
	}
	
	/**
	 * 零件改变
	 */
	public void materialChange(){
		if(null==materialId ||materialId.equals("")){
			return;
		}
		
		processList= resourceService.getProcessInfo(materialId,nodeId);
		getBingProgramList();
	}
	
	
	/**
	 * 工序改变
	 */
	public void processChange(){
		if(null==processId ||processId.equals("")){
			return;
		}
		getBingProgramList();
	}
	
	/**
	 * 获取绑定程序
	 */
	private void getBingProgramList(){
        programList.clear();
		
		//已绑定程序(包括参数程序)
		programList =resourceService.getBingProgramInfo(materialId,processId,nodeId);
		
		for(Map<String,Object> map:programList){
			map.put("isBing", false);
		}
	}
	
	/**
	 * 绑定程序前check
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
				success ="请选择一条记录进行下载！";
			}
		}else{
			success ="请选择要下载的程序！";
		}
		
	}
	
	/**
	 * 绑定下载
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
				success ="请选择一条记录进行下载！";
				return;
			}
		}else{
			success ="请选择要下载的程序！";
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

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
 * @�����
 * @�����ˣ� FW
 * @�������ڣ� 2015-02-26
 * @version�� V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name="ProgramBindingBean")
@ViewScoped
public class ProgramBindingBean implements Serializable{

	/**
	 * ��Դ�ӿ�ʵ��
	 */
	private IResourceService resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
    /**
     * ���ϼ���List
     */
	 List<Map<String,Object>> materialList = new ArrayList<Map<String,Object>>();
	/**
     * ���򼯺�List
     */
	 List<Map<String,Object>> processList = new ArrayList<Map<String,Object>>();
	/**
     * ���򼯺�List
     */
	 List<Map<String,Object>> programList = new ArrayList<Map<String,Object>>();
	/**
     * ����Id
     */
	String materialId;
	/**
	 * ����No
	 */
	String materialNo;
	/**
	 * ��������
	 */
	String materialName;
	/**
	 * ����Id
	 */
	String processId;
	/**
	 * ��������
	 */
	String processName;
	/**
	 * �ڵ�
	 */
	String nodeId;
	/**
	 * 
	 */
	String success;
	
	/**
	 * ���췽��
	 */
	public ProgramBindingBean(){
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeId=(String)session.getAttribute("nodeid2");
		materialList = resourceService.getMaterialInfo(nodeId);
	}
	
	/**
	 * ����ı�
	 */
	public void materialChange(){
		if(null==materialId ||materialId.equals("")){
			return;
		}
		
		processList= resourceService.getProcessInfo(materialId,nodeId);
		
	}
	
	
	/**
	 * ����ı�
	 */
	public void processChange(){
		if(null==processId ||processId.equals("")){
			return;
		}
		programList.clear();
		//��ȡ����
		//���Ѱ󶨳���(������������)
		List<Map<String,Object>> list1 =resourceService.getBingProgramInfo(materialId,processId,nodeId);
		//���Ѱ󶨳���(��������������)
		List<Map<String,Object>> list2 =resourceService.getBingProgramInfo2(materialId,processId);
		//�ڳ�����Ϣ
		List<Map<String,Object>> list3 =resourceService.getProgramList(nodeId);
		
		for(Map<String,Object> map:list3){
			map.put("isBing", false);
			map.put("isMainProgram", "N");
			for(Map<String,Object> map2:list1){
				if(map2.get("id").toString().equals(map.get("id").toString())){
					map.put("isBing", true);
					map.put("isMainProgram", map2.get("isMainProgram").toString());
				}
			}
			
		}
		
		for(Map<String,Object> map:list3){
			Boolean flag =false;
			for(Map<String,Object> map2:list2){
				if(map2.get("id").toString().equals(map.get("id").toString())){
					flag =true;
				}
			}
			if(!flag){
				programList.add(map);
			}
		}
		
		//ð������
		for(int i=0;i<programList.size()-1;i++){
			for(int j=i+1;j<programList.size();j++){
				if(programList.get(i).get("isBing").toString().compareTo(programList.get(j).get("isBing").toString())<0)
			    {
			      Map<String,Object> temp=new HashMap<String, Object>();
			      temp = programList.get(i);
			      programList.set(i, programList.get(j));
			      programList.set(j, temp);
			    }
			}
		}
	}
	
	/**
	 * �󶨳���ǰcheck
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
			int i=0;
			for(Map<String, Object> map:dataList){
				if(map.get("isMainProgram").toString().equals("Y")){
					i++;
				}
			}
			if(i>1){
				success ="�����򲻿����ж������ȷ�ϡ�";
			}
		}else{
			success ="��ѡ��Ҫ�󶨵ĳ���";
		}
		
	}
	
	/**
	 * �󶨳���
	 * @return
	 */
	public void bingProgramInfo(){
		success="";
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:programList){
			if(map.get("isBing").toString().equals("true")){
				dataList.add(map);
			}
			
		}
		if(dataList.size()>0){
			success = resourceService.bingProgram(dataList,materialId,processId);
		}
		if(success.equals("�󶨳ɹ�")){
			processChange();
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

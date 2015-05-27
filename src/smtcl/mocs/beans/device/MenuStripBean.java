package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.json.JSONObject;

import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.device.IWSBZService;
import smtcl.mocs.utils.device.JsonUtil;
@ManagedBean(name="MenuStripBean")
@ViewScoped
public class MenuStripBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6324810135147152711L;
	/**
	 * 权限接口实例
	 */
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");
	/**
	 *北展逻辑层接口实例
	 */
	private IWSBZService wsbzService=(IWSBZService)ServiceFactory.getBean("wsBZService");
	
	private String hiddenvalue;
	
	private String component;
	private String hiddencomponentvalue;
	
	public IOrganizationService getOrganizationService() {
		return organizationService;
	}
	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	public List<Map<String, Object>> getTemplink() {
		System.out.println("^&^&^&^&^&^&^&^&^&^&^");
		Collection<Parameter> parameters = new HashSet<Parameter>();
		if(!templink.isEmpty()){
			templink.clear();
		}else{
			parameters.add(new Parameter("t.TNodeType.nodeclass", "TNodeType",12+"", Operator.EQ));
			templink=organizationService.get_All_Node_By_nodeClass(parameters);
		}
		return templink;
	}
	public void setTemplink(List<Map<String, Object>> templink) {
		this.templink = templink;
	}
	public String getHiddenvalue() {
		getTemplink();
		
		hiddenvalue="{ "+"myvalue : "+JsonUtil.list2json(templink)+"}";
		
		return hiddenvalue;
	}
	public void setHiddenvalue(String hiddenvalue) {
		this.hiddenvalue = hiddenvalue;
	}
	public List<Map<String, Object>> getDiagnostic_Message() {
		
		//diagnostic_Message=wsbzService.get_Diagnostic_Message(component);
		//diagnostic_Message=wsbzService.get_Diagnostic_Message("安全门");
		return diagnostic_Message;
	}
	public void setDiagnostic_Message(List<Map<String, Object>> diagnostic_Message) {
		this.diagnostic_Message = diagnostic_Message;
	}
	public IWSBZService getWsbzService() {
		return wsbzService;
	}
	public void setWsbzService(IWSBZService wsbzService) {
		this.wsbzService = wsbzService;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getHiddencomponentvalue() {
		
		
		if(hiddencomponentvalue==null||hiddencomponentvalue.length()<=0){
			return hiddencomponentvalue;
		}else if(hiddenfind(hiddencomponentvalue)){
			hiddencomponentvalue=wsbzService.get_Diagnostic_Message_for_xml_by_nodename(hiddencomponentvalue.split("@")[0],"",hiddencomponentvalue.split("@")[1]);
			hiddencomponentvalue=hiddencomponentvalue.replaceAll("\\n", "").substring(36);
			System.out.println("^^^^^^^^^^^^--"+hiddencomponentvalue);
		}
		
		
		return hiddencomponentvalue;
	}
	private static boolean hiddenfind(String value){
		boolean flag=false;
		char flagcahr='@';
		for(int i=0;i<value.length();i++){
			if(flagcahr==value.charAt(i)){
				flag=true;
				break;
			}
		}
		
		return flag;
	}
	public void setHiddencomponentvalue(String hiddencomponentvalue) {
		this.hiddencomponentvalue = hiddencomponentvalue;
	}
	public void savePerson(ActionEvent actionEvent) {  
      System.out.println("call by p");
    }
	
	

	private List<Map<String, Object>> templink=new ArrayList<Map<String,Object>>();
	private List<Map<String, Object>> diagnostic_Message=new ArrayList<Map<String,Object>>();
}

package smtcl.mocs.beans.common;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.StringUtils;

/**
 * ���ؿ�����
 * @author songkaiang
 *
 */
@ManagedBean(name="controllerSwitchBean")
@SessionScoped
public class ControllerSwitchBean {
	/**
	 * erp�Ƿ����ش�����:0ʱ�أ�1�ǿ�
	 */
	private String erpReturn;
	
	private String base;
	public ControllerSwitchBean(){
		
	}
	
	public void changFile(){
		if(erpReturn.equals("0")){//�ص�ʱ��ɾ���ļ�
			File file = new File(base+Constants.configPath);
			if(file.exists()){
				file.delete();
			}
		}else if(erpReturn.equals("1")){//����ʱ�򴴽��ļ�
			File file = new File(base+Constants.configPath);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getErpReturn() {
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();		
		ServletContext context = request.getServletContext();
		base = context.getRealPath ("/");
		File file = new File(base+Constants.configPath);
		if(file.exists()){
			erpReturn = "1";
		}else{
			erpReturn = "0";
		}
		return erpReturn;
	}

	public void setErpReturn(String erpReturn) {
		this.erpReturn = erpReturn;
	}
	
	
}

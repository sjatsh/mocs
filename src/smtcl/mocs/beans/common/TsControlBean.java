package smtcl.mocs.beans.common;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.device.IProductService;

@ManagedBean(name = "tsControlBean")
@SessionScoped
public class TsControlBean implements Serializable{
	private String ts;//工单提示信息
	private String prams;
	private IProductService productService=(IProductService)ServiceFactory.getBean("productService");
	public void refreshData(){
		ts=productService.getJobdispatchTS();
		System.out.println(ts);
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getPrams() {
		return prams;
	}
	public void setPrams(String prams) {
		this.prams = prams;
	}
}

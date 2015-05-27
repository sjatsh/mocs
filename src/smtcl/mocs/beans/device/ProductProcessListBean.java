package smtcl.mocs.beans.device;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.model.ProductProcessDataModel;
import smtcl.mocs.model.ProductProcessModel;
import smtcl.mocs.model.TPartBasicInfoModel;
import smtcl.mocs.services.device.IProductService;

/**
 * 产品过程数据
 * @创建时间 2013-10-30
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name="productProcessListBean")
@ViewScoped
public class ProductProcessListBean {
	private IProductService productService=(IProductService)ServiceFactory.getBean("productService");
	private String productSerial;//产品序列号
	private ProductProcessDataModel ppdm;
	private ProductProcessModel[] ppm;
	private List<ProductProcessModel> ppmlist=new ArrayList<ProductProcessModel>();
	private String pram;
	/**
	 * 构造方法
	 */
	public ProductProcessListBean(){
		
	}
	/**
	 * 查询方法
	 */
	public void Search(){
		ppmlist=productService.getProductProcessList(productSerial.trim());
		ppdm=new ProductProcessDataModel(ppmlist);
	}
	
	public String getProductSerial() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		if(null!=session.getAttribute("productSerial")&&!"".equals(session.getAttribute("productSerial").toString())){
			String ps =session.getAttribute("productSerial").toString();
			productSerial=ps;
			ppmlist=productService.getProductProcessList(ps);
			ppdm=new ProductProcessDataModel(ppmlist);
		}
		return productSerial;
	}
	public void GoProcessQualityBean(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		System.out.println("ppm.length:"+ppm.length);
		if(ppm.length<1){
			String ps =session.getAttribute("productSerial").toString();
			if(null!=ps&&!"".equals(ps)){
				pram="gczlsj_product_process_list.xhtml";
				session.setAttribute("processId","");
			}
		}else if(ppm.length==1){
			pram="gczlsj_product_process_list.xhtml";
			String str="";
			String process="";
			for(ProductProcessModel tbim:ppm){
				str=tbim.getBasicNo();
				process=tbim.getProcessId();
			}
			session.setAttribute("productSerial", str);
			session.setAttribute("processId", process);
		}else{
			 FacesMessage msg = new FacesMessage("产品基础数据跳转","跳转失败只能选择一条数据！");  
		     FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
	}
	
	public void setProductSerial(String productSerial) {
		this.productSerial = productSerial;
		
	}
	public ProductProcessDataModel getPpdm() {
		
		return ppdm;
	}
	public void setPpdm(ProductProcessDataModel ppdm) {
		this.ppdm = ppdm;
	}
	public ProductProcessModel[] getPpm() {
		return ppm;
	}
	public void setPpm(ProductProcessModel[] ppm) {
		this.ppm = ppm;
	}
	public List<ProductProcessModel> getPpmlist() {
		return ppmlist;
	}
	public void setPpmlist(List<ProductProcessModel> ppmlist) {
		this.ppmlist = ppmlist;
	}
	public String getPram() {
		return pram;
	}
	public void setPram(String pram) {
		this.pram = pram;
	}
	
}

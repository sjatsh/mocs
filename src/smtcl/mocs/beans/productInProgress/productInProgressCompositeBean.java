package smtcl.mocs.beans.productInProgress;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.model.TreeNode;

import smtcl.mocs.common.device.pages.DataPage;
import smtcl.mocs.common.device.pages.PageListBaseBean;
import smtcl.mocs.common.device.pages.PageListDataModel;
import smtcl.mocs.model.productInProgressModel;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.device.IProductService;
import smtcl.mocs.utils.device.StringUtils;
/**
 * @date  2014-02-26
 * @author liguoqiang
 *在制品批综合统计报表
 */
@ManagedBean(name="pIpCompositeBean")
@ViewScoped
public class productInProgressCompositeBean extends PageListBaseBean implements Serializable {
	private IProductService productService=(IProductService)ServiceFactory.getBean("productService");
	private List<TPartTypeInfo> procuctlist=new ArrayList<TPartTypeInfo>(); //产品下拉框列表
	private String procuctSelected;//选中的产品
	private List<Map<String,Object>> batchNolist=new ArrayList<Map<String,Object>>();//批次列表
	private String batchNoSelected;//选中的批次
	
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	
	private TreeNode root;//节点树  
	private String load="tab1";
	private String nodeid;
	private List<productInProgressModel> pro=new ArrayList<productInProgressModel>();
	
	public productInProgressCompositeBean(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	nodeid=session.getAttribute("nodeid")+"";
		Date[] dates=StringUtils.convertDatea(1);
		startTime=dates[0];
		endTime=dates[1];
		procuctlist=productService.getTPartTypeInfoByNodeId(nodeid,null);
		String start=StringUtils.formatDate(startTime, 2);
		String end=StringUtils.formatDate(endTime, 2);
		root=productService.getProductInProgress(start,end,procuctSelected,batchNoSelected,nodeid);
	}
	public void selectOnChange(){  
		System.out.println("partId:"+procuctSelected);
		batchNolist=productService.getbatchNo(procuctSelected);
    }
	
	public void SubmitSearch(){
		System.out.println(load);
		String start=StringUtils.formatDate(startTime, 2);
		String end=StringUtils.formatDate(endTime, 2);
		if("tab1".equals(load)){
			root=productService.getProductInProgress(start,end,procuctSelected,batchNoSelected,nodeid);
		}else{
			this.defaultDataModel = null;
		}
	}
	public void linkSearch(String bathcNo,String productName){
		batchNoSelected="";
		procuctSelected="";
		this.defaultDataModel=null;
		batchNoSelected=bathcNo;
		if(null!=productName&&!"".equals(productName)){
			List<TPartTypeInfo> tpilist=productService.getTPartTypeInfoByName(productName);
			if(null!=tpilist&&tpilist.size()>0){
				procuctSelected=tpilist.get(0).getId()+"";	
			}
		}
		load="tab2";
	}
	
	@Override
	public PageListDataModel getDefaultDataModel() {
		if (defaultDataModel == null) {
			defaultDataModel = new PageListDataModel(pageSize) {
				@Override
				public DataPage fetchPage(int startRow, int pageSize) {
					String start=StringUtils.formatDate(startTime, 2);
					String end=StringUtils.formatDate(endTime, 2);
					pro = productService.getProcessstaticesById(startRow / pageSize + 1, pageSize,start,end,procuctSelected,batchNoSelected,nodeid);
					return new DataPage(pro.size(), startRow,pro);
				}
			};
			return defaultDataModel;
		}
		return null;

	}
	@Override
	public PageListDataModel getExtendDataModel() {
		
		return null;
	}
	
	public List<TPartTypeInfo> getProcuctlist() {
		return procuctlist;
	}
	public void setProcuctlist(List<TPartTypeInfo> procuctlist) {
		this.procuctlist = procuctlist;
	}
	public String getProcuctSelected() {
		return procuctSelected;
	}
	public void setProcuctSelected(String procuctSelected) {
		this.procuctSelected = procuctSelected;
	}
	public List<Map<String, Object>> getBatchNolist() {
		return batchNolist;
	}
	public void setBatchNolist(List<Map<String, Object>> batchNolist) {
		this.batchNolist = batchNolist;
	}
	public String getBatchNoSelected() {
		return batchNoSelected;
	}
	public void setBatchNoSelected(String batchNoSelected) {
		this.batchNoSelected = batchNoSelected;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public TreeNode getRoot() {
		return root;
	}
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	public String getLoad() {
		return load;
	}
	public void setLoad(String load) {
		this.load = load;
	}
	public List<productInProgressModel> getPro() {
		return pro;
	}
	public void setPro(List<productInProgressModel> pro) {
		this.pro = pro;
	}
	
	
}

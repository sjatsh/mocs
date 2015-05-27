package smtcl.mocs.beans.device;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.model.TPartBasicInfoDataModel;
import smtcl.mocs.model.TPartBasicInfoModel;
import smtcl.mocs.pojos.job.TPartBasicInfo;
import smtcl.mocs.pojos.job.TPartClassInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.device.IProductService;
import smtcl.mocs.utils.device.StringUtils;

/**
 * ��Ʒ��������
 * @����ʱ�� 2013-10-30
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name="productBasicListBean")
@ViewScoped
public class ProductBasicListBean {
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	private IProductService productService=(IProductService)ServiceFactory.getBean("productService");
	private List<TPartTypeInfo> partType=new ArrayList<TPartTypeInfo>();//��������������б�
	private List<TPartClassInfo> partlist=new ArrayList<TPartClassInfo>();//������������
	private String selectPartType;//�������ѡ����
	private String selectPartClass;//������ѡ����
	private String batchNumber;//���κ�
	private Date onlineStartTime;//���߿�ʼʱ��
	private Date onlineEndTime;//���߽���ʱ��
	private String hardTag;//Ӳ��
	private String productSerial;//��Ʒ���к�
	private Date offlineStartTime;//���߿�ʼʱ��
	private Date offlineEndTime;//���߽���ʱ��
	private List<TPartBasicInfoModel> productList;//������ѯ���ݼ��� 
	private TPartBasicInfoDataModel tpbidm;//datable������ʾ����
	private TPartBasicInfoModel[] tpbilist;//��ǰѡ�е�row����
	private String pram;
	
	public ProductBasicListBean(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	String nodeid=session.getAttribute("nodeid2")+"";
		partlist=partService.getTPartClassInfo(null,nodeid);
	}
	/**
	 * ������ı��¼�
	 */
	public void PartClassChange(){
		partType=partService.getTPartTypeInfoByTypeNo(selectPartClass);
//		productList=productService.getProductBasicList(selectPartType, selectPartClass, batchNumber, onlineStartTime, onlineEndTime, 
//				offlineStartTime, offlineEndTime, productSerial);
//		tpbidm=new TPartBasicInfoDataModel(productList);
	}
	public void Search(){
		String onst=StringUtils.formatDate(onlineStartTime,3); 
		String onet=StringUtils.formatDate(onlineEndTime,3); 
		String ofst=StringUtils.formatDate(offlineStartTime,3); 
		String ofet=StringUtils.formatDate(offlineEndTime,3); 
		productList=productService.getProductBasicList(selectPartType.trim(), selectPartClass.trim(), batchNumber.trim(), onst, onet, 
				ofst, ofet, productSerial.trim());
		tpbidm=new TPartBasicInfoDataModel(productList);
	}
	
	public void GoProductProcessBean(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		System.out.println("tpbilist.length:"+tpbilist.length);
		if(tpbilist.length<1){
			 FacesMessage msg = new FacesMessage("��Ʒ����������ת","��תʧ����ѡ��һ�����ݣ�");  
		     FacesContext.getCurrentInstance().addMessage(null, msg); 
		}else if(tpbilist.length==1){
			pram="cpgcsj_product_basic_list.xhtml";
			String str="";
			for(TPartBasicInfoModel tbim:tpbilist){
				str=tbim.getNo();
			}
			session.setAttribute("productSerial", str);
			session.setAttribute("processId","");
		}else{
			 FacesMessage msg = new FacesMessage("��Ʒ����������ת","��תʧ��ֻ��ѡ��һ�����ݣ�");  
		     FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
		
	}
	public void GoProcessQualityBean(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		System.out.println("tpbilist.length:"+tpbilist.length);
		if(tpbilist.length<1){
			 FacesMessage msg = new FacesMessage("��Ʒ����������ת","��תʧ����ѡ��һ�����ݣ�");  
		     FacesContext.getCurrentInstance().addMessage(null, msg); 
		}else if(tpbilist.length==1){
			pram="gczlsj_product_basic_list.xhtml";
			String str="";
			for(TPartBasicInfoModel tbim:tpbilist){
				str=tbim.getNo();
			}
			session.setAttribute("productSerial", str);
		}else{
			 FacesMessage msg = new FacesMessage("��Ʒ����������ת","��תʧ��ֻ��ѡ��һ�����ݣ�");  
		     FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
	}
	public IPartService getPartService() {
		return partService;
	}
	public void setPartService(IPartService partService) {
		this.partService = partService;
	}
	public IProductService getProductService() {
		return productService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public List<TPartTypeInfo> getPartType() {
		return partType;
	}
	public void setPartType(List<TPartTypeInfo> partType) {
		this.partType = partType;
	}
	public List<TPartClassInfo> getPartlist() {
		return partlist;
	}
	public void setPartlist(List<TPartClassInfo> partlist) {
		this.partlist = partlist;
	}
	public String getSelectPartType() {
		return selectPartType;
	}
	public void setSelectPartType(String selectPartType) {
		this.selectPartType = selectPartType;
	}
	public String getSelectPartClass() {
		return selectPartClass;
	}
	public void setSelectPartClass(String selectPartClass) {
		this.selectPartClass = selectPartClass;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getHardTag() {
		return hardTag;
	}
	public void setHardTag(String hardTag) {
		this.hardTag = hardTag;
	}
	public String getProductSerial() {
		return productSerial;
	}
	public void setProductSerial(String productSerial) {
		this.productSerial = productSerial;
	}
	public List<TPartBasicInfoModel> getProductList() {
		return productList;
	}
	public void setProductList(List<TPartBasicInfoModel> productList) {
		this.productList = productList;
	}
	public TPartBasicInfoDataModel getTpbidm() {
		return tpbidm;
	}
	public void setTpbidm(TPartBasicInfoDataModel tpbidm) {
		this.tpbidm = tpbidm;
	}
	public TPartBasicInfoModel[] getTpbilist() {
		return tpbilist;
	}
	public void setTpbilist(TPartBasicInfoModel[] tpbilist) {
		this.tpbilist = tpbilist;
	}
	public Date getOnlineStartTime() {
		return onlineStartTime;
	}
	public void setOnlineStartTime(Date onlineStartTime) {
		this.onlineStartTime = onlineStartTime;
	}
	public Date getOnlineEndTime() {
		return onlineEndTime;
	}
	public void setOnlineEndTime(Date onlineEndTime) {
		this.onlineEndTime = onlineEndTime;
	}
	public Date getOfflineStartTime() {
		return offlineStartTime;
	}
	public void setOfflineStartTime(Date offlineStartTime) {
		this.offlineStartTime = offlineStartTime;
	}
	public Date getOfflineEndTime() {
		return offlineEndTime;
	}
	public void setOfflineEndTime(Date offlineEndTime) {
		this.offlineEndTime = offlineEndTime;
	}
	public String getPram() {
		return pram;
	}
	public void setPram(String pram) {
		this.pram = pram;
	}
	
}

package smtcl.mocs.beans.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.storage.TMaterialExtendOrder;
import smtcl.mocs.pojos.storage.TMaterialExtendPlan;
import smtcl.mocs.pojos.storage.TMaterialExtendStorage;
import smtcl.mocs.pojos.storage.TMaterialVersion;
import smtcl.mocs.services.device.IResourceService;
import smtcl.mocs.utils.device.Constants;


/**
 * 
 * @author liguoqiang
 * @date 2014/08/28
 */
@ManagedBean(name="organizeMaterielUpdateBean")
@ViewScoped
public class OrganizeMaterielUpdateBean {
	private IResourceService resourceService=(IResourceService)ServiceFactory.getBean("resourceService");
	/**
	 * �ڵ�id
	 */
	private String nodeid;
	/**
	 * �ڵ�����
	 */
	private String nodeName;
	/**
	 * ��Ϣ
	 */
	private String message;
//---------------��Ҫ����start------------------
	private TMaterailTypeInfo tti=new TMaterailTypeInfo();

	//��׼��λ�б�
	private List<Map<String,Object>> baseUnitList=new ArrayList<Map<String,Object>>();
	//������λ�б�
	private List<Map<String,Object>> assistUnitList=new ArrayList<Map<String,Object>>();
	//��������list
	private List<Map<String,Object>> typelist=new ArrayList<Map<String,Object>>();
	// �������list
	private List<Map<String,Object>> classList=new ArrayList<Map<String,Object>>();
	private List isBom;
//---------------��Ҫ����      end------------------
	private TMaterialVersion tmv=new TMaterialVersion();
//---------------�������start------------------
	private TMaterialExtendStorage tmes=new TMaterialExtendStorage();
	//����һЩ�Ƿ���Ƶ�����
	private List<String> isKuCun=new ArrayList<String>();
	//�ӿ���б�
	private List<Map<String,Object>> childrenStockList=new ArrayList<Map<String,Object>>();
	private List isStockCtrl;
	//�����ӿ��
	private List isPostionCtrl;
	//���ƿ�λ���
	private List<Map<String,Object>> childrenPostionList=new ArrayList<Map<String,Object>>();
//---------------�������      end------------------
//---------------�ɹ�����start------------------	
	private TMaterialExtendOrder tmio=new TMaterialExtendOrder();
	private String buyer;
	private String price;
	private List<Map<String,Object>> buyerList=new ArrayList<Map<String,Object>>();
//---------------�ɹ�����      end------------------
//---------------�ƻ�����start------------------	
	TMaterialExtendPlan tmep=new TMaterialExtendPlan();
//---------------�ƻ�����      end------------------
	//�汾���ϱ�Ų�ѯ����
	private String vmno;
	//�汾����
	private List<Map<String,Object>> versionList=new ArrayList<Map<String,Object>>();
	
	/**
	 * ���췽��
	 */
	public OrganizeMaterielUpdateBean(){
		/*HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid="8a8abc8d44d361fd0144d3c76dd60001";
		String organizeMateriel=session.getAttribute("organizeMateriel").toString();
		System.out.println("organizeMateriel:"+organizeMateriel);
		nodeName="����";
		baseUnitList=resourceService.getUnitInfo("1");//��׼��λ
		typelist=Constants.MATERIELTYPE;//��������
		classList=resourceService.getTMaterialClassByAll();//�������
		buyerList=resourceService.getBuyerList();
		tti=(TMaterailTypeInfo)resourceService.getObject(organizeMateriel,"id",tti);
		tmv=(TMaterialVersion )resourceService.getObject(organizeMateriel,"materialId",tmv);
		tmes=(TMaterialExtendStorage)resourceService.getObject(organizeMateriel,"materialId",tmes);
		//isKuCun;
		if(tmes.getIsStock()==1){
			isKuCun.add("�������");
		}
		if(tmes.getIsVersionCtrl()==1){
			isKuCun.add("�汾����");
		}
		if(tmes.getIsStorage()==1){
			isKuCun.add("�ɴ洢");
		}
		if(tmes.getIsProcess()==1){
			isKuCun.add("�ɴ���");
		}
		if(tmes.getIsRetain()==1){
			isKuCun.add("�ɱ���");
		}
		childrenStockList=resourceService.getStockOnAll();
		childrenPostionList=resourceService.getPostionByStockNo(tmes.getStockNo());
		
		tmio=(TMaterialExtendOrder )resourceService.getObject(organizeMateriel,"materialId",tmio);
		if(null!=tmio&&null!=tmio.getBuyer()){
			buyer=tmio.getBuyer();
		}
		if(null!=tmio&&null!=tmio.getPrice()){
			price=tmio.getPrice().toString();
		}
		tmep=(TMaterialExtendPlan)resourceService.getObject(organizeMateriel,"materialId",tmep);*/
		
	}

	/**
	 * ��ȡ������λ����
	 */
	public void getAssistUnitData(){
		assistUnitList=resourceService.getUnitInfoOnAssis(tti.getUnit());//������λ
	}	
	/**
	 *�����ӿ�����
	 */
	public void ChangeIsStockCtrl(){
		childrenStockList=resourceService.getStockOnAll();	
	}
	/**
	 * ���ƿ�λ
	 */
	public void ChangeIsPostionCtrl(){
		childrenPostionList=resourceService.getPostionByStockNo(tmes.getStockNo());
		
	}
	/**
	 * �汾��ѯ
	 */
	public void VersionQueryData(){
		if(null==vmno||"".equals(vmno)){
			vmno=tti.getNo();
		}
		versionList=resourceService.getMaterielVersion(vmno);
	}
	/**
	 * ������֯����
	 */
	public void UpdateOrganizeMateriel(){
		message=resourceService.UpdateOrganizeMateriel(this);
	}
	
	
	public String getNodeid() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		//nodeid="8a8abc8d44d361fd0144d3c76dd60001";
		nodeid=session.getAttribute("nodeid2")+"";
		String organizeMateriel=session.getAttribute("organizeMateriel").toString();
		System.out.println("organizeMateriel:"+organizeMateriel);
		nodeName="����";
		baseUnitList=resourceService.getUnitInfo("1");//��׼��λ
		typelist=Constants.MATERIELTYPE;//��������
		classList=resourceService.getTMaterialClassByAll();//�������
		buyerList=resourceService.getBuyerList();
		tti=(TMaterailTypeInfo)resourceService.getObject(organizeMateriel,"id",tti);
		tmv=(TMaterialVersion )resourceService.getObject(organizeMateriel,"materialId",tmv);
		tmes=(TMaterialExtendStorage)resourceService.getObject(organizeMateriel,"materialId",tmes);
		//isKuCun;
		if(tmes.getIsStock()==1){
			isKuCun.add("�������");
		}
		if(tmes.getIsVersionCtrl()==1){
			isKuCun.add("�汾����");
		}
		if(tmes.getIsStorage()==1){
			isKuCun.add("�ɴ洢");
		}
		if(tmes.getIsProcess()==1){
			isKuCun.add("�ɴ���");
		}
		if(tmes.getIsRetain()==1){
			isKuCun.add("�ɱ���");
		}
		childrenStockList=resourceService.getStockOnAll();
		childrenPostionList=resourceService.getPostionByStockNo(tmes.getStockNo());
		
		tmio=(TMaterialExtendOrder )resourceService.getObject(organizeMateriel,"materialId",tmio);
		if(null!=tmio&&null!=tmio.getBuyer()){
			buyer=tmio.getBuyer();
		}
		if(null!=tmio&&null!=tmio.getPrice()){
			price=tmio.getPrice().toString();
		}
		tmep=(TMaterialExtendPlan)resourceService.getObject(organizeMateriel,"materialId",tmep);
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public List<Map<String, Object>> getBaseUnitList() {
		return baseUnitList;
	}
	public void setBaseUnitList(List<Map<String, Object>> baseUnitList) {
		this.baseUnitList = baseUnitList;
	}
	public List<Map<String, Object>> getAssistUnitList() {
		return assistUnitList;
	}
	public void setAssistUnitList(List<Map<String, Object>> assistUnitList) {
		this.assistUnitList = assistUnitList;
	}
	public List<Map<String, Object>> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<Map<String, Object>> typelist) {
		this.typelist = typelist;
	}
	public List<Map<String, Object>> getClassList() {
		return classList;
	}
	public void setClassList(List<Map<String, Object>> classList) {
		this.classList = classList;
	}
	public List<String> getIsKuCun() {
		return isKuCun;
	}
	public void setIsKuCun(List<String> isKuCun) {
		this.isKuCun = isKuCun;
	}
	public List<Map<String, Object>> getChildrenStockList() {
		return childrenStockList;
	}
	public void setChildrenStockList(List<Map<String, Object>> childrenStockList) {
		this.childrenStockList = childrenStockList;
	}
	public List getIsPostionCtrl() {
		return isPostionCtrl;
	}
	public void setIsPostionCtrl(List isPostionCtrl) {
		this.isPostionCtrl = isPostionCtrl;
	}
	public List<Map<String, Object>> getChildrenPostionList() {
		return childrenPostionList;
	}
	public void setChildrenPostionList(List<Map<String, Object>> childrenPostionList) {
		this.childrenPostionList = childrenPostionList;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public List<Map<String, Object>> getBuyerList() {
		return buyerList;
	}
	public void setBuyerList(List<Map<String, Object>> buyerList) {
		this.buyerList = buyerList;
	}
	public TMaterailTypeInfo getTti() {
		return tti;
	}
	public void setTti(TMaterailTypeInfo tti) {
		this.tti = tti;
	}
	public TMaterialExtendStorage getTmes() {
		return tmes;
	}
	public void setTmes(TMaterialExtendStorage tmes) {
		this.tmes = tmes;
	}
	public List getIsBom() {
		return isBom;
	}
	public void setIsBom(List isBom) {
		this.isBom = isBom;
	}
	public TMaterialExtendPlan getTmep() {
		return tmep;
	}
	public void setTmep(TMaterialExtendPlan tmep) {
		this.tmep = tmep;
	}
	public List getIsStockCtrl() {
		return isStockCtrl;
	}
	public void setIsStockCtrl(List isStockCtrl) {
		this.isStockCtrl = isStockCtrl;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TMaterialVersion getTmv() {
		return tmv;
	}
	public void setTmv(TMaterialVersion tmv) {
		this.tmv = tmv;
	}

	public TMaterialExtendOrder getTmio() {
		return tmio;
	}

	public void setTmio(TMaterialExtendOrder tmio) {
		this.tmio = tmio;
	}

	public String getVmno() {
		return vmno;
	}

	public void setVmno(String vmno) {
		this.vmno = vmno;
	}

	public List<Map<String, Object>> getVersionList() {
		return versionList;
	}

	public void setVersionList(List<Map<String, Object>> versionList) {
		this.versionList = versionList;
	}
	
}

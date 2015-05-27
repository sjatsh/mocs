package smtcl.mocs.beans.device;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.model.ProcessQualityDataModel;
import smtcl.mocs.model.ProcessQualityModel;
import smtcl.mocs.model.ProductProcessDataModel;
import smtcl.mocs.pojos.job.TPartBasicInfo;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.device.IProductService;
import smtcl.mocs.utils.device.StringUtils;

/**
 * ������������
 * @����ʱ�� 2013-10-30
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name="processQualityListBean")
@ViewScoped
public class ProcessQualityListBean {
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	private IProductService productService=(IProductService)ServiceFactory.getBean("productService");
	private String selectPartType;//�������
	private List<TPartTypeInfo> partType=new ArrayList<TPartTypeInfo>();//��������������б�
	private String productSerial;//��Ʒ���к�
	private List<TPartBasicInfo> qualityList=new ArrayList<TPartBasicInfo>();
	private Date onStartTime;
	private Date onEndTime;
	private String selectProcess;//��Ʒ����
	private List<Map<String,Object>> processList=new ArrayList<Map<String,Object>>();//��Ʒ�����������б�
	private ProcessQualityDataModel pqdm;//dataTable�������
	private ProcessQualityModel[] pqm;//dataTable����ѡ��
	
	public ProcessQualityListBean(){
		
	}
	/**
	 * �������������ı��¼�
	 */
	public void PartClassChange(){
		qualityList=productService.getTPartBasicInfoByPartTypeId(selectPartType);
		if(null!=qualityList&&qualityList.size()>0){
			String inhql=qualityList.get(0).getId()+"";
			processList=productService.getTProcessInfoByPartBasicId(inhql);
		}
		
	}
	/**
	 * ��Ʒ���к�������ı��¼�
	 */
	public void ProductSerialChange(){
		processList=productService.getTProcessInfoByPartBasicId(productSerial);
	}
	/**
	 * ��ѯ
	 */
	public void Search(){
		String start=StringUtils.formatDate(onStartTime, 3);
		String end=StringUtils.formatDate(onEndTime, 3);
		List<ProcessQualityModel> rs=productService.getProcessQualityList(selectPartType, productSerial, selectProcess, start, end);
		pqdm=new ProcessQualityDataModel(rs);
	}
	public String getSelectPartType() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		partType=partService.getTPartTypeInfoByTypeNo(null);
		if(null!=session.getAttribute("productSerial")&&!"".equals(session.getAttribute("productSerial").toString())){
			String ps =session.getAttribute("productSerial").toString();
			productSerial=ps;
			if(null!=session.getAttribute("processId")&&!"".equals(session.getAttribute("processId").toString())){
				String pid=session.getAttribute("processId").toString();
				selectProcess=pid;
			}else{
				selectProcess=null;
			}
			String start=StringUtils.formatDate(onStartTime, 3);
			String end=StringUtils.formatDate(onEndTime, 3);
			List<ProcessQualityModel> rs=productService.getProcessQualityList(null, productSerial, selectProcess, start, end);
			pqdm=new ProcessQualityDataModel(rs);
		}
		session.setAttribute("processId", "");
		
		return selectPartType;
	}
	public void setSelectPartType(String selectPartType) {
		this.selectPartType = selectPartType;
	}
	public List<TPartTypeInfo> getPartType() {
		return partType;
	}
	public void setPartType(List<TPartTypeInfo> partType) {
		this.partType = partType;
	}
	public String getProductSerial() {
		return productSerial;
	}
	public void setProductSerial(String productSerial) {
		this.productSerial = productSerial;
	}
	public List<TPartBasicInfo> getQualityList() {
		return qualityList;
	}
	public void setQualityList(List<TPartBasicInfo> qualityList) {
		this.qualityList = qualityList;
	}
	public Date getOnStartTime() {
		return onStartTime;
	}
	public void setOnStartTime(Date onStartTime) {
		this.onStartTime = onStartTime;
	}
	public Date getOnEndTime() {
		return onEndTime;
	}
	public void setOnEndTime(Date onEndTime) {
		this.onEndTime = onEndTime;
	}
	public String getSelectProcess() {
		return selectProcess;
	}
	public void setSelectProcess(String selectProcess) {
		this.selectProcess = selectProcess;
	}
	public List<Map<String, Object>> getProcessList() {
		return processList;
	}
	public void setProcessList(List<Map<String, Object>> processList) {
		this.processList = processList;
	}
	public ProcessQualityDataModel getPqdm() {
		return pqdm;
	}
	public void setPqdm(ProcessQualityDataModel pqdm) {
		this.pqdm = pqdm;
	}
	public ProcessQualityModel[] getPqm() {
		return pqm;
	}
	public void setPqm(ProcessQualityModel[] pqm) {
		this.pqm = pqm;
	}
}

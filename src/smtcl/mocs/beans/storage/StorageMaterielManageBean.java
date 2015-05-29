package smtcl.mocs.beans.storage;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.storage.TMaterialVersion;
import smtcl.mocs.pojos.storage.TStorageInfo;
import smtcl.mocs.services.storage.IMaterielManageService;
import smtcl.mocs.utils.authority.DateUtil;
import smtcl.mocs.utils.device.StringUtils;

/**
 * ������ϴ�����ѯ
 * @author songkaiang
 *
 */
@ManagedBean(name="storageMaterielBean")
@ViewScoped
public class StorageMaterielManageBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private IMaterielManageService materielService = (IMaterielManageService)ServiceFactory.getBean("materielManageService");
	
	/**
	 * �ڵ����ݼ���
	 */
	private List<Map<String,Object>> nodelist = new ArrayList<Map<String,Object>>();
	/**
	 * �ӿ��(�ⷿ)id
	 */
	private String storageId;//storageNo;
	/**
	 * �ӿ�����ݼ���
	 */
	private List<TStorageInfo> storageNoList = new ArrayList<TStorageInfo>();
	/**
	 * ��λid
	 */
	private String positionNo;
	/**
	 * ��λ���� 
	 */
	private List<Map<String,Object>> positionNolist = new ArrayList<Map<String,Object>>();
	/**
	 * ���ϱ��
	 */
	private String materielId;
	/**
	 * ���ϱ��뼯��
	 */
	private List<TMaterailTypeInfo> materiellist = new ArrayList<TMaterailTypeInfo>();
	/**
	 * ���ϰ汾
	 */
	private String materielVersion;
	/**
	 * ���ϰ汾����
	 */
	private List<TMaterialVersion> materielVersionList = new ArrayList<TMaterialVersion>();
	/**
	 * ����
	 */
	private String materielDesc;
	/**
	 * ��������
	 */
	private List<Map<String,Object>> materielDescList = new ArrayList<Map<String,Object>>();
	/**
	 * ״̬
	 */
	private String status;
	/**
	 * ״̬����
	 */
	private static List<Map<String,Object>> statusList = new ArrayList<Map<String,Object>>();
	
	/**
	 * ���ϴ�����Ϣ
	 */
	private List<Map<String,Object>> materielStock = new ArrayList<Map<String,Object>>();
	/**
	 * ���ϴ�����ϸ��Ϣ
	 */
	private List<Map<String,Object>> materielDetailStock = new ArrayList<Map<String,Object>>();
	/**
	 * ���δ�����ѯ
	 */
	private List<Map<String,Object>> materielStockBatch = new ArrayList<Map<String,Object>>();
	/**
	 * ���д�����ѯ
	 */
	private List<Map<String,Object>> materielStockSeq = new ArrayList<Map<String,Object>>();
	
	/**
	 * ��ʼ����
	 */
	private String batchStart;
	/**
	 * ��������
	 */
	private String batchEnd;
	/**
	 * ���ο�ʼʱ��
	 */
	private Date startTime;
	/**
	 * ���ο�ʼʱ��
	 */
	private Date endTime;
	/**
	 * ���μ�����Ϣ
	 */
	private List<Map<String,Object>> batchList = new ArrayList<Map<String,Object>>();
	/**
	 * ���п�ʼ
	 */
	private String seqStart;
	/**
	 * ���н���
	 */
	private String seqEnd;
	/**
	 * ���м���
	 */
	private List<Map<String,Object>> seqNoList = new ArrayList<Map<String,Object>>();
	/**
	 * �Ƿ���ʾ��Э
	 */
	private String waixieid;
	
	/**
	 * �����ʹ�����ϸ�л�
	 * ����=stock,������ϸ=detail
	 */
	private String tabChange="stock";
	
	static{
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("label", "����");
		map1.put("value", "0");
		statusList.add(map1);
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("label","δ����");
		map2.put("value", "1");
		statusList.add(map2);
	}
	
	public StorageMaterielManageBean(){
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();
		try {
			startTime = df.parse(DateUtil.getData(date, 0, 1));
			endTime = df.parse(DateUtil.getData(date, 1, 1));
		} catch (ParseException e) {
			startTime = date;
			endTime = date;
		}
		
		//��λ��Ϣ��ȡ
		storageNoList = materielService.getStorageInfo(this.getnodeid());
		//������Ϣ
		materiellist = materielService.getMaterailTypeInfo(this.getnodeid());
		
		batchList = materielService.findbatchNo();
		seqNoList = materielService.findseqNo();
		this.materielQuery();
		this.positionQuery();
		this.materialVersionQuery();
	}
	
	//��λ��Ϣ��ȡ
	public void positionQuery(){
		positionNolist.clear();
		positionNolist = materielService.getMaterielPositionInfo(storageId);
	}
	
	//��ȡ���ϰ汾
	public void materialVersionQuery(){
		materielVersionList.clear();
		materielVersionList = materielService.getMaterialVersion(materielId);
	}
	
	/**
	 * ���ϲ�ѯ
	 */
	public void materielQuery(){
		if(tabChange.equals("detail")){
			this.materielDetailQuery();
		}else{
			materielStock.clear();
			materielStock = materielService.materielQuery(this.getnodeid(),
					storageId, positionNo, materielId, materielVersion,
					materielDesc, status);
		}
	}
	
	public void materielDetailQuery(){
		materielDetailStock.clear();
		materielDetailStock = materielService.materielDetailQuery(
				this.getnodeid(), storageId, positionNo, materielId,
				materielVersion, materielDesc, status);
	}
	/**
	 * ���β�ѯ
	 */
	public void batchQuery(){
		materielStockBatch.clear();
		materielStockBatch = materielService.materielBatchQuery(
				this.getnodeid(), materielId, storageId, positionNo,
				batchStart, batchEnd, materielDesc,waixieid);
	}
	/**
	 * ���в�ѯ
	 */
	public void seqNoQuery(){
		materielStockSeq.clear();
		materielStockSeq = materielService.materielSeqQuery(this.getnodeid(),
				materielId, storageId, positionNo, seqStart, seqEnd,
				materielDesc);
	}

	//--------------------------------private start--------------------------------------
	private String getnodeid(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}
	
	//--------------------------------private end--------------------------------------
	
	public List<Map<String, Object>> getNodelist() {
		return nodelist;
	}

	public void setNodelist(List<Map<String, Object>> nodelist) {
		this.nodelist = nodelist;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public List<TStorageInfo> getStorageNoList() {
		return storageNoList;
	}

	public void setStorageNoList(List<TStorageInfo> storageNoList) {
		this.storageNoList = storageNoList;
	}

	public String getPositionNo() {
		return positionNo;
	}

	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

	public List<Map<String, Object>> getPositionNolist() {
		return positionNolist;
	}

	public void setPositionNolist(List<Map<String, Object>> positionNolist) {
		this.positionNolist = positionNolist;
	}

	public String getMaterielId() {
		return materielId;
	}

	public void setMaterielId(String materielId) {
		this.materielId = materielId;
	}

	public List<TMaterailTypeInfo> getMateriellist() {
		return materiellist;
	}

	public void setMateriellist(List<TMaterailTypeInfo> materiellist) {
		this.materiellist = materiellist;
	}

	public String getMaterielDesc() {
		return materielDesc;
	}

	public void setMaterielDesc(String materielDesc) {
		this.materielDesc = materielDesc;
	}

	public List<Map<String, Object>> getMaterielDescList() {
		return materielDescList;
	}

	public void setMaterielDescList(List<Map<String, Object>> materielDescList) {
		this.materielDescList = materielDescList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Map<String, Object>> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Map<String, Object>> statusList) {
		this.statusList = statusList;
	}

	public String getMaterielVersion() {
		return materielVersion;
	}

	public void setMaterielVersion(String materielVersion) {
		this.materielVersion = materielVersion;
	}

	public List<TMaterialVersion> getMaterielVersionList() {
		return materielVersionList;
	}

	public void setMaterielVersionList(List<TMaterialVersion> materielVersionList) {
		this.materielVersionList = materielVersionList;
	}

	public List<Map<String, Object>> getMaterielStock() {
		return materielStock;
	}

	public void setMaterielStock(List<Map<String, Object>> materielStock) {
		this.materielStock = materielStock;
	}

	public List<Map<String, Object>> getMaterielDetailStock() {
		return materielDetailStock;
	}

	public void setMaterielDetailStock(List<Map<String, Object>> materielDetailStock) {
		this.materielDetailStock = materielDetailStock;
	}

	public List<Map<String, Object>> getMaterielStockBatch() {
		return materielStockBatch;
	}

	public void setMaterielStockBatch(List<Map<String, Object>> materielStockBatch) {
		this.materielStockBatch = materielStockBatch;
	}

	public List<Map<String, Object>> getMaterielStockSeq() {
		return materielStockSeq;
	}

	public void setMaterielStockSeq(List<Map<String, Object>> materielStockSeq) {
		this.materielStockSeq = materielStockSeq;
	}

	public String getBatchStart() {
		return batchStart;
	}

	public void setBatchStart(String batchStart) {
		this.batchStart = batchStart;
	}

	public String getBatchEnd() {
		return batchEnd;
	}

	public void setBatchEnd(String batchEnd) {
		this.batchEnd = batchEnd;
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

	public List<Map<String, Object>> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<Map<String, Object>> batchList) {
		this.batchList = batchList;
	}

	public String getSeqStart() {
		return seqStart;
	}

	public void setSeqStart(String seqStart) {
		this.seqStart = seqStart;
	}

	public String getSeqEnd() {
		return seqEnd;
	}

	public void setSeqEnd(String seqEnd) {
		this.seqEnd = seqEnd;
	}

	public List<Map<String, Object>> getSeqNoList() {
		return seqNoList;
	}

	public void setSeqNoList(List<Map<String, Object>> seqNoList) {
		this.seqNoList = seqNoList;
	}

	public String getTabChange() {
		return tabChange;
	}

	public void setTabChange(String tabChange) {
		this.tabChange = tabChange;
	}

	public String getWaixieid() {
		return waixieid;
	}

	public void setWaixieid(String waixieid) {
		this.waixieid = waixieid;
	}
	
}

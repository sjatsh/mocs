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
 * 库存物料存量查询
 * @author songkaiang
 *
 */
@ManagedBean(name="storageMaterielBean")
@ViewScoped
public class StorageMaterielManageBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private IMaterielManageService materielService = (IMaterielManageService)ServiceFactory.getBean("materielManageService");
	
	/**
	 * 节点数据集合
	 */
	private List<Map<String,Object>> nodelist = new ArrayList<Map<String,Object>>();
	/**
	 * 子库存(库房)id
	 */
	private String storageId;//storageNo;
	/**
	 * 子库存数据集合
	 */
	private List<TStorageInfo> storageNoList = new ArrayList<TStorageInfo>();
	/**
	 * 库位id
	 */
	private String positionNo;
	/**
	 * 库位集合 
	 */
	private List<Map<String,Object>> positionNolist = new ArrayList<Map<String,Object>>();
	/**
	 * 物料编号
	 */
	private String materielId;
	/**
	 * 物料编码集合
	 */
	private List<TMaterailTypeInfo> materiellist = new ArrayList<TMaterailTypeInfo>();
	/**
	 * 物料版本
	 */
	private String materielVersion;
	/**
	 * 物料版本集合
	 */
	private List<TMaterialVersion> materielVersionList = new ArrayList<TMaterialVersion>();
	/**
	 * 描述
	 */
	private String materielDesc;
	/**
	 * 描述集合
	 */
	private List<Map<String,Object>> materielDescList = new ArrayList<Map<String,Object>>();
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 状态集合
	 */
	private static List<Map<String,Object>> statusList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 物料存量信息
	 */
	private List<Map<String,Object>> materielStock = new ArrayList<Map<String,Object>>();
	/**
	 * 物料存量详细信息
	 */
	private List<Map<String,Object>> materielDetailStock = new ArrayList<Map<String,Object>>();
	/**
	 * 批次存量查询
	 */
	private List<Map<String,Object>> materielStockBatch = new ArrayList<Map<String,Object>>();
	/**
	 * 序列存量查询
	 */
	private List<Map<String,Object>> materielStockSeq = new ArrayList<Map<String,Object>>();
	
	/**
	 * 开始批次
	 */
	private String batchStart;
	/**
	 * 结束批次
	 */
	private String batchEnd;
	/**
	 * 批次开始时间
	 */
	private Date startTime;
	/**
	 * 批次开始时间
	 */
	private Date endTime;
	/**
	 * 批次集合信息
	 */
	private List<Map<String,Object>> batchList = new ArrayList<Map<String,Object>>();
	/**
	 * 序列开始
	 */
	private String seqStart;
	/**
	 * 序列结束
	 */
	private String seqEnd;
	/**
	 * 序列集合
	 */
	private List<Map<String,Object>> seqNoList = new ArrayList<Map<String,Object>>();
	/**
	 * 是否显示外协
	 */
	private String waixieid;
	
	/**
	 * 存量和存量明细切换
	 * 存量=stock,存量明细=detail
	 */
	private String tabChange="stock";
	
	static{
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("label", "开启");
		map1.put("value", "0");
		statusList.add(map1);
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("label","未开启");
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
		
		//库位信息获取
		storageNoList = materielService.getStorageInfo(this.getnodeid());
		//物料信息
		materiellist = materielService.getMaterailTypeInfo(this.getnodeid());
		
		batchList = materielService.findbatchNo();
		seqNoList = materielService.findseqNo();
		this.materielQuery();
		this.positionQuery();
		this.materialVersionQuery();
	}
	
	//货位信息获取
	public void positionQuery(){
		positionNolist.clear();
		positionNolist = materielService.getMaterielPositionInfo(storageId);
	}
	
	//获取物料版本
	public void materialVersionQuery(){
		materielVersionList.clear();
		materielVersionList = materielService.getMaterialVersion(materielId);
	}
	
	/**
	 * 物料查询
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
	 * 批次查询
	 */
	public void batchQuery(){
		materielStockBatch.clear();
		materielStockBatch = materielService.materielBatchQuery(
				this.getnodeid(), materielId, storageId, positionNo,
				batchStart, batchEnd, materielDesc,waixieid);
	}
	/**
	 * 序列查询
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

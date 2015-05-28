package smtcl.mocs.beans.erp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.model.erp.JobPlanDataModel;
import smtcl.mocs.model.erp.JobPlanImportInfo;
import smtcl.mocs.services.erp.IImportService;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 任务批次导入功能控制bean
 * @author songkaiang
 *
 */
@ManagedBean(name="importAddBean")
@ViewScoped
public class ImportAddBean implements Serializable{
	
	private static final long serialVersionUID = -3109061148374638774L;

	private IImportService importService = (IImportService)ServiceFactory.getBean("importService");
	
	//table显示的数据
	private List<JobPlanImportInfo> outData = new ArrayList<JobPlanImportInfo>();
	/**
	 * dataTable数据  实现了多行选中
	 */
	private JobPlanDataModel mediumModel;
	/**
	 * 选中的数据
	 */
	private JobPlanImportInfo[] selectedInfo;
	/**
	 * 查询开始时间和结束时间
	 */
	private Date startTime;
	private Date endTime;
	
	/**
	 * 处理标志
	 */
	private int flag;
	/**
	 * 处理标志数据集
	 */
	private Map<String,Object> flagMap = new TreeMap<String,Object>();
	/**
	 * 导入之后在页面上显示的信息
	 */
	private String isSuccess;
	
	public ImportAddBean(){
		//构造开始时间和结束时间
		Date[] dates=StringUtils.convertDatea(1);
		startTime=dates[0];
		endTime=dates[1];
		//构造处理标志信息集
		flagMap.clear();
		flagMap.put("未导入", 0);
		flagMap.put("已过滤", 1);
		flagMap.put("已导入", 2);
		
		this.SubmitSearch();
	}
	//数据查询
	public void SubmitSearch(){
		//每次查询清空数据
		outData.clear();
		//获取前天要展现的数据
		outData=importService.getJobPlanImportInfo(startTime, endTime, flag);
		//将数据封装成JobPlanDataModel
		mediumModel = new JobPlanDataModel(outData);
	}
	//数据导入
	public void importData(){
		boolean success = false;
		for(JobPlanImportInfo jp : selectedInfo){
			if(!jp.getFlag().equals("未导入")){
				isSuccess = "请选择处理标志为未导入。";
				return;
			}
			System.out.println(jp.getJobno());
			//将数据保存到TJobplanInfo（t_jobplan_info）表中
			//如果保存失败不对erp中间表进行更改
			success=importService.saveInfoToPartType(jp, this.getnodeid());
			if(success){
				//数据导入后将erp中间表的相应数据flag该为2
				importService.updateWISEntity(jp.getJobno(),jp.getUploadDate(), 2);
			}
		}
		//未勾选导入数据的提示信息
		if(selectedInfo.length<=0){
			isSuccess = "请选择要导入的数据。";
		}else{
			//成功与否的提示信息
			if(success){
				isSuccess = "导入成功";
				//导入完成后刷新数据
				this.SubmitSearch();
			}else{
				isSuccess = "导入失败，未在数据库中发现对应零件号。";
			}
		}
		
	}

	//--------------------------------private--------------------------------------
	private String getnodeid(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}
	
	//----------------------------set-------get------------------------------------

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

	public List<JobPlanImportInfo> getOutData() {
		return outData;
	}

	public void setOutData(List<JobPlanImportInfo> outData) {
		this.outData = outData;
	}
	
	public JobPlanImportInfo[] getSelectedInfo() {
		return selectedInfo;
	}
	public void setSelectedInfo(JobPlanImportInfo[] selectedInfo) {
		this.selectedInfo = selectedInfo;
	}
	public JobPlanDataModel getMediumModel() {
		return mediumModel;
	}
	public void setMediumModel(JobPlanDataModel mediumModel) {
		this.mediumModel = mediumModel;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public Map<String, Object> getFlagMap() {
		return flagMap;
	}
	public void setFlagMap(Map<String, Object> flagMap) {
		this.flagMap = flagMap;
	}
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	
}

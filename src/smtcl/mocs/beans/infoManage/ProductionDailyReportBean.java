package smtcl.mocs.beans.infoManage;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.report.IDailyService;
import smtcl.mocs.services.report.IReportService;
import smtcl.mocs.utils.device.StringUtils;

@ManagedBean(name = "productionDailyReportBean")
@SessionScoped
public class ProductionDailyReportBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	IDailyService dailyService = (IDailyService) ServiceFactory
			.getBean("dailyService");

	private Date searchTime;
	private List<Map<String, Object>> dailyReportData = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> amount = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> totalData = new ArrayList<Map<String, Object>>();

	public ProductionDailyReportBean() {
		searchTime = new Date();
		amount = dailyService.getAmountData(StringUtils.formatDate(new Date(),
				2));
		totalData = dailyService.getTotalData(StringUtils.formatDate(new Date(), 2));
		dailyReportData = dailyService.getProductionDailyReport(StringUtils
				.formatDate(new Date(), 2));
	}

	public void Search() {
		amount = dailyService.getAmountData(StringUtils.formatDate(searchTime,2));
		totalData = dailyService.getTotalData(StringUtils.formatDate(searchTime, 2));
		dailyReportData = dailyService.getProductionDailyReport(StringUtils.formatDate(searchTime, 2));

	}

	// get set

	public List<Map<String, Object>> getDailyReportData() {
		return dailyReportData;
	}


	public List<Map<String, Object>> getTotalData() {
		return totalData;
	}

	public void setTotalData(List<Map<String, Object>> totalData) {
		this.totalData = totalData;
	}

	public Date getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(Date searchTime) {
		this.searchTime = searchTime;
	}

	public void setDailyReportData(List<Map<String, Object>> dailyReportData) {
		this.dailyReportData = dailyReportData;
	}

	public List<Map<String, Object>> getAmount() {
		return amount;
	}

	public void setAmount(List<Map<String, Object>> amount) {
		this.amount = amount;
	}
	// 测试--------
	/*
	 * private List<DailyModel> list;
	 * 
	 * public List<DailyModel> getList() { return list; } public void
	 * setList(List<DailyModel> list) { this.list = list; } public
	 * List<DailyModel> getData1(){
	 * DailyModel test =new DailyModel(); test.setOrderNo("序号");
	 * test.setModelNo("型号"); test.setPartNo("零件");
	 * test.setLastMonthBalance("上月结存"); test.setRoughBefore("毛坯当日之前");
	 * test.setRoughToday("毛坯当日"); test.setRoughSum("毛坯和");
	 * test.setMonthFeedPlan("月投料计划"); test.setYieldBefore("投料产量当日之前");
	 * test.setYieldToday("当日投量"); test.setYieldSum("投料总量");
	 * test.setFeefPercent("%"); test.setMonthSubmitPlan("月提交计划");
	 * test.setSubmitBefore("今日之前提交量"); test.setSubmitToday("今日提交");
	 * test.setSubmitSum("总提交量"); test.setSumbitPercent("%");
	 * test.setScrapLiao("料废"); test.setScrapGong("工废"); test.setMaking("在制");
	 * test.setNeedProcess("进度"); test.setRealFinish("完成");
	 * test.setTypePlan("计划"); test.setTypeFinish("完成");
	 * test.setSubmitValue("产值"); list=new ArrayList<DailyModel>();
	 * list.add(test);
	 * 
	 * return list;
	 * 
	 * }
	 */
	// ----测试结束

}

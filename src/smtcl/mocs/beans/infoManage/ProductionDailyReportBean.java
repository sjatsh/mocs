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
	// ����--------
	/*
	 * private List<DailyModel> list;
	 * 
	 * public List<DailyModel> getList() { return list; } public void
	 * setList(List<DailyModel> list) { this.list = list; } public
	 * List<DailyModel> getData1(){
	 * DailyModel test =new DailyModel(); test.setOrderNo("���");
	 * test.setModelNo("�ͺ�"); test.setPartNo("���");
	 * test.setLastMonthBalance("���½��"); test.setRoughBefore("ë������֮ǰ");
	 * test.setRoughToday("ë������"); test.setRoughSum("ë����");
	 * test.setMonthFeedPlan("��Ͷ�ϼƻ�"); test.setYieldBefore("Ͷ�ϲ�������֮ǰ");
	 * test.setYieldToday("����Ͷ��"); test.setYieldSum("Ͷ������");
	 * test.setFeefPercent("%"); test.setMonthSubmitPlan("���ύ�ƻ�");
	 * test.setSubmitBefore("����֮ǰ�ύ��"); test.setSubmitToday("�����ύ");
	 * test.setSubmitSum("���ύ��"); test.setSumbitPercent("%");
	 * test.setScrapLiao("�Ϸ�"); test.setScrapGong("����"); test.setMaking("����");
	 * test.setNeedProcess("����"); test.setRealFinish("���");
	 * test.setTypePlan("�ƻ�"); test.setTypeFinish("���");
	 * test.setSubmitValue("��ֵ"); list=new ArrayList<DailyModel>();
	 * list.add(test);
	 * 
	 * return list;
	 * 
	 * }
	 */
	// ----���Խ���

}

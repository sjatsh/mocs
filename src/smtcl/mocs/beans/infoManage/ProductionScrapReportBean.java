package smtcl.mocs.beans.infoManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.report.IReportService;
import smtcl.mocs.utils.device.StringUtils;


/**
 * 报废报表
 * @author liguoqiang
 * @日期 2017-07-31
 *
 */
@ManagedBean(name="productionScrapReportBean")
@ViewScoped
public class ProductionScrapReportBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//报表服务接口实例
	IReportService reportService = (IReportService)ServiceFactory.getBean("reportService");
	//报表时间
	private Date startTime;
	private Date endTime;
	private String scrapType;
	
	private List<Map<String,Object>> scrapReportData=new ArrayList<Map<String,Object>>();
	
	public ProductionScrapReportBean(){
		scrapReportData=reportService.getProductionScrapReport(StringUtils.formatDate(new Date(), 2),
				StringUtils.formatDate(new Date(), 2),null);
	}
	public void Search(){
		scrapReportData=reportService.getProductionScrapReport(StringUtils.formatDate(startTime, 2),
				StringUtils.formatDate(endTime, 2),scrapType);
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public List<Map<String, Object>> getScrapReportData() {
		return scrapReportData;
	}

	public void setScrapReportData(List<Map<String, Object>> scrapReportData) {
		this.scrapReportData = scrapReportData;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getScrapType() {
		return scrapType;
	}
	public void setScrapType(String scrapType) {
		this.scrapType = scrapType;
	}
	
}

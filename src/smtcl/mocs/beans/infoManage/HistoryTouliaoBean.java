package smtcl.mocs.beans.infoManage;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.report.IReportService;
import smtcl.mocs.utils.authority.DateUtil;
import smtcl.mocs.utils.device.StringUtils;
/**
 * ��ʷͶ�����β�ѯ
 * @author songkaiang
 *
 */
@ManagedBean(name="historyTouliaoBean")
@ViewScoped
public class HistoryTouliaoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	//�������ӿ�ʵ��
	IReportService reportService = (IReportService)ServiceFactory.getBean("reportService");
	//�������ݼ�
	private List<Map<String,Object>> outData = new ArrayList<Map<String,Object>>();
	//�������ݿ�ʼʱ��
	private Date startTime;
	//�������ݽ���ʱ��
	private Date endTime;
	
	public HistoryTouliaoBean(){
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();
		try {
			startTime = df.parse(DateUtil.getData(date, 0, 1));
			endTime = df.parse(DateUtil.getData(date, 1, 1));
		} catch (ParseException e) {
			startTime = date;
			endTime = date;
		}
		this.SubmitSearch();
	}
	
	//��ѯ��������
	public void SubmitSearch(){
		outData.clear();
		outData = reportService.historyTouliaoData(this.getnodeid(),StringUtils.formatDate(startTime, 3), StringUtils.formatDate(endTime, 3));
	}
	//--------------------------------private--------------------------------------
	private String getnodeid(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}
	
	//----------------------------set-------get------------------------------------
	
	public List<Map<String, Object>> getOutData() {
		return outData;
	}

	public void setOutData(List<Map<String, Object>> outData) {
		this.outData = outData;
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
	
}

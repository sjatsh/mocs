package smtcl.mocs.beans.infoManage;

import java.io.Serializable;
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

/**
 * ��Ϣ����-�²�������
 * @author songkaiang
 *
 */
@ManagedBean(name="monthOutputBean")
@ViewScoped
public class MonthOutputBean implements Serializable{
	
	//�������ݼ�
	private List<Map<String,Object>> outData = new ArrayList<Map<String,Object>>();
	
	//�������ӿ�ʵ��
	IReportService reportService = (IReportService)ServiceFactory.getBean("reportService");
	
	//�����������ڵ��·�
	private Date startTime;
	
	public MonthOutputBean(){
		startTime = new Date();
		this.SubmitSearch();
	}

	//��ѯ��������
	public void SubmitSearch(){
		String stime = DateUtil.getData(startTime,0, 1);//��ȡ����ʱ���·ݵĵ�һ��
		String etime = DateUtil.getData(startTime, 1, 1);//��ȡ����ʱ�����µĵ�һ��
		outData.clear();
		outData = reportService.monthOutputData(this.getnodeid(),stime, etime);
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

}

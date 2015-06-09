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
 * ����������ܱ�
 * @author songkaiang
 *
 */
@ManagedBean(name="outputGatherBean")
@ViewScoped
public class OutputGatherBean implements Serializable{

	private static final long serialVersionUID = 1L;
	//�������ӿ�ʵ��
	IReportService reportService = (IReportService)ServiceFactory.getBean("reportService");
	//�������ݼ�
	private List<Map<String,Object>> outData = new ArrayList<Map<String,Object>>();
	//�������ݿ�ʼʱ��
	private Date startTime;
	//�������ݽ���ʱ��
	
	public OutputGatherBean(){
		startTime = new Date();
		this.SubmitSearch();
	}
	
	//��ѯ��������
	public void SubmitSearch(){
		String stime = DateUtil.getData(startTime, 0, 1);//��ȡ����ʱ���·ݵĵ�һ��
		String etime = DateUtil.getData(startTime, 1, 1);//��ȡ����ʱ�����µĵ�һ��
		//��ȡ����Ҫ��ʾ����ʱ����
		List<Map<String,Object>> tempData = reportService.monthOutputData(this.getnodeid(),stime, etime);
		List<Map<String,Object>> newData = new ArrayList<Map<String,Object>>();//���ص�����
		for(Map<String,Object> map : tempData){
			int partid = Integer.parseInt(map.get("partId").toString());
//			int planNumV = Integer.parseInt(map.get("planNum").toString());
			//��ȡ��ͬ�ƻ���������Ϣ���Է����������Ʒ��
			List<Map<String,Object>> listMap = reportService.getJobInfo(partid, stime,etime, 2);
			//����Ʒ����
			int onlineNum = 0;
			for(Map<String,Object> map1 : listMap){
				//�ƻ�����
				int planNum = Integer.parseInt(map1.get("planNum").toString());
				//�������
				int finishNum = Integer.parseInt(map1.get("finishNum").toString());
				//��������
				int scrapNum = 0;
				if(map1.get("scrapNum") != null && map1.get("scrapNum").toString()!="")
					scrapNum = Integer.parseInt(map1.get("scrapNum").toString());
				onlineNum += (planNum - finishNum - scrapNum);
			}
			map.put("onlineNum", onlineNum);
			newData.add(map);
			outData = newData;
		}
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

//	public Date getEndTime() {
//		return endTime;
//	}
//
//	public void setEndTime(Date endTime) {
//		this.endTime = endTime;
//	}
//	
}

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
 * 生产情况汇总表
 * @author songkaiang
 *
 */
@ManagedBean(name="outputGatherBean")
@ViewScoped
public class OutputGatherBean implements Serializable{

	private static final long serialVersionUID = 1L;
	//报表服务接口实例
	IReportService reportService = (IReportService)ServiceFactory.getBean("reportService");
	//报表数据集
	private List<Map<String,Object>> outData = new ArrayList<Map<String,Object>>();
	//报表数据开始时间
	private Date startTime;
	//报表数据结束时间
	
	public OutputGatherBean(){
		startTime = new Date();
		this.SubmitSearch();
	}
	
	//查询报表数据
	public void SubmitSearch(){
		String stime = DateUtil.getData(startTime, 0, 1);//获取给定时间月份的第一天
		String etime = DateUtil.getData(startTime, 1, 1);//获取给定时间下月的第一天
		//获取报表要显示的临时数据
		List<Map<String,Object>> tempData = reportService.monthOutputData(this.getnodeid(),stime, etime);
		List<Map<String,Object>> newData = new ArrayList<Map<String,Object>>();//返回的数据
		for(Map<String,Object> map : tempData){
			int partid = Integer.parseInt(map.get("partId").toString());
//			int planNumV = Integer.parseInt(map.get("planNum").toString());
			//获取相同计划的批次信息，以方便计算在制品数
			List<Map<String,Object>> listMap = reportService.getJobInfo(partid, stime,etime, 2);
			//在制品数量
			int onlineNum = 0;
			for(Map<String,Object> map1 : listMap){
				//计划数量
				int planNum = Integer.parseInt(map1.get("planNum").toString());
				//完成数据
				int finishNum = Integer.parseInt(map1.get("finishNum").toString());
				//报废数量
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

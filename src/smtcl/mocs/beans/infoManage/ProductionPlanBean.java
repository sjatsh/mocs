package smtcl.mocs.beans.infoManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.report.IMonthPlanService;

@ManagedBean(name="productionPlanBean")
@SessionScoped  //页面没有变动就不刷新
public class ProductionPlanBean implements Serializable{

	/**
	 * @author SunJun
	 * 月生产计划表的Bean
	 */
	private static final long serialVersionUID = 1L;

	private Date searchTime;
	private int month;
	private int partClass=-1;//零件大类类型
	private List<Map<String, Object>> totalTableHeadData=new ArrayList<Map<String,Object>>();//总的表头信息
	private List<Map<String, Object>> partClassAndHeadData=new ArrayList<Map<String,Object>>();//零件大类名称 和  大类 表头数据封装
	private List<Map<String, Object>> typeData=new ArrayList<Map<String,Object>>();//每个大类对应的 小类型数据封装
	private IMonthPlanService testService=(IMonthPlanService)ServiceFactory.getBean("monthPlanService");
	
	public ProductionPlanBean(){
		
		System.out.println("这里是ProductionMonthPlanBean");
		this.searchTime=new Date();
		this.setTotalTableHeadData(testService.getTotalTableHeadData("finishNum", "realEndtime",searchTime));//获取分厂总的表头信息
		this.setPartClassAndHeadData(testService.getPartClassAndHeadData("finishNum", "realEndtime",searchTime));//获取大类表头信息集合
		
	}
	
	public void setInfor(){
		
		System.out.println("这里是setInfor,searchTime="+(searchTime.getYear()+1900)+"-"+(searchTime.getMonth()+1));
		this.setTotalTableHeadData(null);
		this.setPartClassAndHeadData(null);
		this.setTypeData(null);
		
		this.setTotalTableHeadData(testService.getTotalTableHeadData("finishNum", "realEndtime",searchTime));//获取分厂总的表头信息
		this.setPartClassAndHeadData(testService.getPartClassAndHeadData("finishNum", "realEndtime",searchTime));//获取大类表头信息集合
	}
	
	
	//---------------------------get----------set----------------------------------
	
	public void dateChange(ValueChangeEvent e){
		
		this.searchTime=(Date)e.getNewValue();
		System.out.println("changeDate----"+(searchTime.getYear()+1900)+"-"+(searchTime.getMonth()+1));
	}
	
	public Date getSearchTime() {
		
		System.out.println("这里是getSearchTime");
		return searchTime;
	}
	public void setSearchTime(Date searchTime) {
		
		System.out.println("set searchTime 值为----"+(searchTime.getYear()+1900)+" "+(searchTime.getMonth()+1));
		this.searchTime = searchTime;
	}
	public IMonthPlanService getTestService() {
		return testService;
	}
	public void setTestService(IMonthPlanService testService) {
		this.testService = testService;
	}


	public List<Map<String, Object>> getTotalTableHeadData() {
		
		return totalTableHeadData;
	}

	public void setTotalTableHeadData(List<Map<String, Object>> list) {
		
		this.totalTableHeadData = list;
	}

	public List<Map<String, Object>> getPartClassAndHeadData() {
		
		return partClassAndHeadData;
	}

	public int getPartClass() {
		return partClass;
	}

	public void setPartClass(int partClass) {
		
		System.out.println("这里是setPartClass partClassName="+partClass);
		this.partClass = partClass;
	}

	public void setPartClassAndHeadData(List<Map<String, Object>> partClassAndData) {
		
		this.partClassAndHeadData = partClassAndData;
	}

	public List<Map<String, Object>> getTypeData() {
		
		return testService.getTypeData("finishNum", "realEndtime",searchTime, partClass);
	}

	public void setTypeData(List<Map<String, Object>> typeData) {
		this.typeData = typeData;
	}

	public int getMonth() {
		
		return (searchTime.getMonth()+1);
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
}
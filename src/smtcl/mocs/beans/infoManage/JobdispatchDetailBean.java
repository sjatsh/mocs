package smtcl.mocs.beans.infoManage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.*;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.http.impl.cookie.DateUtils;
import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.jobplan.IJobDispatchService;
import smtcl.mocs.services.report.IReportService;
import smtcl.mocs.utils.authority.SessionHelper;
import smtcl.mocs.utils.device.StringUtils;

/**
 * 信息管理-加工工单明细
 * @文件：JobdispatchDetailBean.java
 * @作者： songkaiang
 * @创建日期：2014年10月28日
 */
@ManagedBean(name="jobdispatchDetailBean")
@ViewScoped
public class JobdispatchDetailBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//报表服务接口实例
	private IReportService reportService = (IReportService)ServiceFactory.getBean("reportService");
	//工单实例接口
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	//报表数据集
	private List<Map<String,Object>> outData = new ArrayList<Map<String,Object>>();
	//批次状态
	private String jobplanStatus;
	//状态数据集
	private List<Map<String,Object>> statusList = new ArrayList<Map<String,Object>>();
	//零件名称
	private String partName;
	//零件编号数据集
	private List<Map<String,Object>> partList = new ArrayList<Map<String,Object>>();
	//工单建立时间
	private Date jobCreateDate;
	private Date jobCreateDateEnd;
	//工单开始时间
	private Date jobStartTime;
	private Date jobStartTimeEnd;
	//设备
	private String equSerialNo;
	//设备数据集
	private List<Map<String,Object>> equSerialNoList = new ArrayList<Map<String,Object>>();
	//人员
	private String person;
	//人员编号集合
	private List<Map<String,Object>> personList = new ArrayList<Map<String,Object>>();
	//导入Excel中的title的中文名和英文名，且顺序一致
	private static String[] ch_title = new String[]{"批次号","零件编号","零件名称","工单号","工序","工序名称","工单数量","分配数量","完成数量",
		"报废数量","设备号","计划开始","计划完成","实际开始","实际完成","工单状态","批次状态","ERP","人员"};
	
	private static String[] en_title = new String[]{"taskNum","partNo","partName","jobNo","processNo","processName","planNum",
		"finishNum","finishNum","scrapNum","equSerialNo","planStartTime","planEndTime","realStartTime","realEndTime","jobst","jobplanst","erp","person"};
	
	public JobdispatchDetailBean(){
		Date[] dates = StringUtils.convertDatea(1);
		jobCreateDate = dates[0];
		jobCreateDateEnd = dates[1];
		jobStartTime = dates[0];
		jobStartTimeEnd = dates[1];
		//加载零件编号数据集
        partList.clear();
        equSerialNoList.clear();
        personList.clear();
        if(this.getLocale().toString().equals("en") || this.getLocale().toString().equals("en_US")){
            Map<String,Object> newMap = new HashMap<String, Object>();
            newMap.put("Id", "");
            newMap.put("partName", "Select");
            partList.add(newMap);
            Map<String,Object> newMap1 = new HashMap<String, Object>();
            newMap1.put("Id", "");
            newMap1.put("Name", "Select");
            newMap1.put("equName","Select");
            equSerialNoList.add(newMap1);
            Map<String,Object> newMap2 = new HashMap<String, Object>();
            newMap2.put("personName", "Select");
            newMap2.put("personValue", "Select");
            personList.add(newMap2);
        }else {
            Map<String,Object> newMap = new HashMap<String, Object>();
            newMap.put("Id", "");
            newMap.put("partName", "请选择");
            partList.add(newMap);
            Map<String,Object> newMap1 = new HashMap<String, Object>();
            newMap1.put("Id", "");
            newMap1.put("Name", "请选择");
            newMap1.put("equName","请选择");
            equSerialNoList.add(newMap1);
            Map<String,Object> newMap2 = new HashMap<String, Object>();
            newMap2.put("personName", "请选择");
            newMap2.put("personValue", "请选择");
            personList.add(newMap2);
        }
        List<Map<String, Object>> list = jobDispatchService.getPartTypeMap(this.getnodeid());//map中是id，name
        for (Map<String, Object> map : list) {
            if (map.get("id") != null && !map.get("id").toString().equals("") && map.get("name") != null && !map.get("name").toString().equals("")) {
                map.put("Id", map.get("id").toString());
                map.put("partName", map.get("name").toString());
                partList.add(map);
            }
        }
		equSerialNoList.addAll(jobDispatchService.getDevicesInfo(this.getnodeid()));
		personList.addAll(reportService.getPersonList(this.getnodeid()));
		this.SubmitSearch();
	}

	//查询报表数据
	public void SubmitSearch(){
        outData.clear();
        outData = reportService.dispatchDetailData(this.getnodeid(), jobplanStatus, partName, jobCreateDate,
                jobCreateDateEnd, jobStartTime, jobStartTimeEnd, equSerialNo, person, this.getLocale());
    }
	
	//将数据导出Excel到本地
	public void downloadFile(){
		FacesContext ctx = FacesContext.getCurrentInstance();
	    ctx.responseComplete();//防止报getOutputStream()错误
		//获取request,response
		HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();
		//文件名称
		String fileName=DateUtils.formatDate(new Date(), "yyyyMMddhhmmss")+".xls";
		//获取文件存储的路径
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + "excel\\"+fileName;
		this.writeDataToExcel(ctxPath);//将数据写入到Excel文件中,存放到服务器上
		response.reset(); 
		response.setCharacterEncoding("utf-8");    
		response.setContentType("multipart/form-data");  
		response.setHeader("Content-Disposition", "attachment;fileName="+fileName);    
		
		BufferedInputStream bis = null;  
        BufferedOutputStream bos = null; 
		
        System.out.println(ctxPath);  
        try {  
            long fileLength = new File(ctxPath).length();  
            response.setContentType("application/x-msdownload;");  
            response.setHeader("Content-disposition", "attachment; filename="  
                    + new String(fileName.getBytes("utf-8"), "ISO8859-1"));  
            response.setHeader("Content-Length", String.valueOf(fileLength));  
            bis = new BufferedInputStream(new FileInputStream(ctxPath));  
            bos = new BufferedOutputStream(response.getOutputStream());  
            byte[] buff = new byte[2048];  
            int bytesRead;  
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
                bos.write(buff, 0, bytesRead);  
            } 
            bos.flush();
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
        	try{
	            if (bis != null)  
	                bis.close();  
	            if (bos != null){ 
	            	bos.close();
	            }
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
	}
	
	//将数据写入到Excel文件中,存放到服务器上
    @SuppressWarnings("unchecked")
	public void writeDataToExcel(String filePath){
		File exportFile;
		 try {
			 exportFile = new File(filePath);
			 //新建一个excel文件
			 boolean falg = exportFile.createNewFile();
			 //新建excel文件
			 WritableWorkbook book = Workbook.createWorkbook(exportFile);
			 WritableSheet sheet = book.createSheet("加工工单明细", 0); //新建一个sheet
			 //3.添加数据
			 int rowNum = 1;
			 int colNum;
			 
			 //将数据循环写入到Excel文件中
			 for(Map<String,Object> map: outData){
				 colNum=0;
				 int count = ch_title.length;
				 if(rowNum==1){//第一行写入的是title
                     for (String value : ch_title) {
                         try {
                             Label rowDataLabel = new Label(colNum, rowNum - 1, value);
                             sheet.addCell(rowDataLabel);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                         colNum++;
                     }
					 colNum = 0;
				 }
				 for(int i=0;i<count;i++){//第二行开始正式写入数据
//					 if(rowNum > 0){
						 String key = en_title[i];
						 Object value = map.get(key);
						 try{
							 Label rowDataLabel;
							 if(value!=null){
								 rowDataLabel = new Label(colNum, rowNum, value.toString());
							 }else{
								 rowDataLabel = new Label(colNum, rowNum, "");
							 }
							 sheet.addCell(rowDataLabel);
						 }catch(Exception e){
							 e.printStackTrace();
						 }
						 colNum++;
//					 }
					 
				 }
				 rowNum++;
			 }
			 book.write();
			 book.close();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	}
	
	//--------------------------------private--------------------------------------
	//获取nodeid
	private String getnodeid(){
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (String)session.getAttribute("nodeid");
	}

    private Locale getLocale(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return SessionHelper.getCurrentLocale(request.getSession());
    }
	
	//----------------------------set-------get------------------------------------
	
	public List<Map<String, Object>> getOutData() {
		return outData;
	}

	public void setOutData(List<Map<String, Object>> outData) {
		this.outData = outData;
	}

	public String getJobplanStatus() {
		return jobplanStatus;
	}

	public void setJobplanStatus(String jobplanStatus) {
		this.jobplanStatus = jobplanStatus;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Date getJobCreateDate() {
		return jobCreateDate;
	}

	public void setJobCreateDate(Date jobCreateDate) {
		this.jobCreateDate = jobCreateDate;
	}

	public Date getJobStartTime() {
		return jobStartTime;
	}

	public void setJobStartTime(Date jobStartTime) {
		this.jobStartTime = jobStartTime;
	}

	public List<Map<String, Object>> getStatusList() {
        if(this.getLocale().toString().equals("en") || this.getLocale().toString().equals("en_US")){
            statusList.clear();
            Map<String,Object> statusMap0 = new HashMap<String,Object>();
            statusMap0.put("Id", 0);
            statusMap0.put("Name", "Select");
            statusList.add(statusMap0);
            Map<String,Object> statusMap = new HashMap<String,Object>();
            statusMap.put("Id", 10);
            statusMap.put("Name", "Created");
            statusList.add(statusMap);
            Map<String,Object> statusMap11 = new HashMap<String,Object>();
            statusMap11.put("Id", 20);
            statusMap11.put("Name", "to be Dispatched");
            statusList.add(statusMap11);
            Map<String,Object> statusMap13 = new HashMap<String,Object>();
            statusMap13.put("Id", 30);
            statusMap13.put("Name", "Dispatched");
            statusList.add(statusMap13);
            Map<String,Object> statusMap1 = new HashMap<String,Object>();
            statusMap1.put("Id", 40);
            statusMap1.put("Name", "Forwarded");
            statusList.add(statusMap1);
            Map<String,Object> statusMap5 = new HashMap<String,Object>();
            statusMap5.put("Id", 50);
            statusMap5.put("Name", "Processing");
            statusList.add(statusMap5);
            Map<String,Object> statusMap2 = new HashMap<String,Object>();
            statusMap2.put("Id", 70);
            statusMap2.put("Name", "Finished");
            statusList.add(statusMap2);
            Map<String,Object> statusMap3 = new HashMap<String,Object>();
            statusMap3.put("Id", 60);
            statusMap3.put("Name", "Closed");
            statusList.add(statusMap3);
            Map<String,Object> statusMap8 = new HashMap<String,Object>();
            statusMap8.put("Id", 80);
            statusMap8.put("Name", "Pause");
            statusList.add(statusMap8);
        }else{
            //状态
            statusList.clear();
            Map<String,Object> statusMap0 = new HashMap<String,Object>();
            statusMap0.put("Id", 0);
            statusMap0.put("Name", "请选择");
            statusList.add(statusMap0);
            statusList.addAll(jobDispatchService.getJobStatus());
        }
		return statusList;
	}

	public void setStatusList(List<Map<String, Object>> statusList) {
		this.statusList = statusList;
	}

	public String getEquSerialNo() {
		return equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public List<Map<String, Object>> getPartList() {
		return partList;
	}

	public void setPartList(List<Map<String, Object>> partList) {
		this.partList = partList;
	}

	public List<Map<String, Object>> getEquSerialNoList() {
		return equSerialNoList;
	}

	public void setEquSerialNoList(List<Map<String, Object>> equSerialNoList) {
		this.equSerialNoList = equSerialNoList;
	}

	public List<Map<String, Object>> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Map<String, Object>> personList) {
		this.personList = personList;
	}

	public Date getJobCreateDateEnd() {
		return jobCreateDateEnd;
	}

	public void setJobCreateDateEnd(Date jobCreateDateEnd) {
		this.jobCreateDateEnd = jobCreateDateEnd;
	}

	public Date getJobStartTimeEnd() {
		return jobStartTimeEnd;
	}

	public void setJobStartTimeEnd(Date jobStartTimeEnd) {
		this.jobStartTimeEnd = jobStartTimeEnd;
	}

}

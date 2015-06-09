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
 * ��Ϣ����-�ӹ�������ϸ
 * @�ļ���JobdispatchDetailBean.java
 * @���ߣ� songkaiang
 * @�������ڣ�2014��10��28��
 */
@ManagedBean(name="jobdispatchDetailBean")
@ViewScoped
public class JobdispatchDetailBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//�������ӿ�ʵ��
	private IReportService reportService = (IReportService)ServiceFactory.getBean("reportService");
	//����ʵ���ӿ�
	private IJobDispatchService jobDispatchService = (IJobDispatchService)ServiceFactory.getBean("jobDispatchService");
	//�������ݼ�
	private List<Map<String,Object>> outData = new ArrayList<Map<String,Object>>();
	//����״̬
	private String jobplanStatus;
	//״̬���ݼ�
	private List<Map<String,Object>> statusList = new ArrayList<Map<String,Object>>();
	//�������
	private String partName;
	//���������ݼ�
	private List<Map<String,Object>> partList = new ArrayList<Map<String,Object>>();
	//��������ʱ��
	private Date jobCreateDate;
	private Date jobCreateDateEnd;
	//������ʼʱ��
	private Date jobStartTime;
	private Date jobStartTimeEnd;
	//�豸
	private String equSerialNo;
	//�豸���ݼ�
	private List<Map<String,Object>> equSerialNoList = new ArrayList<Map<String,Object>>();
	//��Ա
	private String person;
	//��Ա��ż���
	private List<Map<String,Object>> personList = new ArrayList<Map<String,Object>>();
	//����Excel�е�title����������Ӣ��������˳��һ��
	private static String[] ch_title = new String[]{"���κ�","������","�������","������","����","��������","��������","��������","�������",
		"��������","�豸��","�ƻ���ʼ","�ƻ����","ʵ�ʿ�ʼ","ʵ�����","����״̬","����״̬","ERP","��Ա"};
	
	private static String[] en_title = new String[]{"taskNum","partNo","partName","jobNo","processNo","processName","planNum",
		"finishNum","finishNum","scrapNum","equSerialNo","planStartTime","planEndTime","realStartTime","realEndTime","jobst","jobplanst","erp","person"};
	
	public JobdispatchDetailBean(){
		Date[] dates = StringUtils.convertDatea(1);
		jobCreateDate = dates[0];
		jobCreateDateEnd = dates[1];
		jobStartTime = dates[0];
		jobStartTimeEnd = dates[1];
		//�������������ݼ�
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
            newMap.put("partName", "��ѡ��");
            partList.add(newMap);
            Map<String,Object> newMap1 = new HashMap<String, Object>();
            newMap1.put("Id", "");
            newMap1.put("Name", "��ѡ��");
            newMap1.put("equName","��ѡ��");
            equSerialNoList.add(newMap1);
            Map<String,Object> newMap2 = new HashMap<String, Object>();
            newMap2.put("personName", "��ѡ��");
            newMap2.put("personValue", "��ѡ��");
            personList.add(newMap2);
        }
        List<Map<String, Object>> list = jobDispatchService.getPartTypeMap(this.getnodeid());//map����id��name
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

	//��ѯ��������
	public void SubmitSearch(){
        outData.clear();
        outData = reportService.dispatchDetailData(this.getnodeid(), jobplanStatus, partName, jobCreateDate,
                jobCreateDateEnd, jobStartTime, jobStartTimeEnd, equSerialNo, person, this.getLocale());
    }
	
	//�����ݵ���Excel������
	public void downloadFile(){
		FacesContext ctx = FacesContext.getCurrentInstance();
	    ctx.responseComplete();//��ֹ��getOutputStream()����
		//��ȡrequest,response
		HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();
		//�ļ�����
		String fileName=DateUtils.formatDate(new Date(), "yyyyMMddhhmmss")+".xls";
		//��ȡ�ļ��洢��·��
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + "excel\\"+fileName;
		this.writeDataToExcel(ctxPath);//������д�뵽Excel�ļ���,��ŵ���������
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
	
	//������д�뵽Excel�ļ���,��ŵ���������
    @SuppressWarnings("unchecked")
	public void writeDataToExcel(String filePath){
		File exportFile;
		 try {
			 exportFile = new File(filePath);
			 //�½�һ��excel�ļ�
			 boolean falg = exportFile.createNewFile();
			 //�½�excel�ļ�
			 WritableWorkbook book = Workbook.createWorkbook(exportFile);
			 WritableSheet sheet = book.createSheet("�ӹ�������ϸ", 0); //�½�һ��sheet
			 //3.�������
			 int rowNum = 1;
			 int colNum;
			 
			 //������ѭ��д�뵽Excel�ļ���
			 for(Map<String,Object> map: outData){
				 colNum=0;
				 int count = ch_title.length;
				 if(rowNum==1){//��һ��д�����title
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
				 for(int i=0;i<count;i++){//�ڶ��п�ʼ��ʽд������
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
	//��ȡnodeid
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
            //״̬
            statusList.clear();
            Map<String,Object> statusMap0 = new HashMap<String,Object>();
            statusMap0.put("Id", 0);
            statusMap0.put("Name", "��ѡ��");
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

package smtcl.mocs.beans.infoManage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.report.IMonthPlanService;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@ManagedBean(name = "downloadExcel")
@SessionScoped
public class DownloadExcel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date saveTime;
	private WritableWorkbook wwb;
	private WritableSheet sheet;
	private WritableFont font1;
	private WritableFont font2;
	private WritableFont font3;
	private WritableCellFormat cellFormat1;
	private WritableCellFormat cellFormat2;
	private WritableCellFormat cellFormat3;
	private WritableCellFormat cellFormat4;
	private WritableCellFormat cellFormat5;
	
	private int row=12;//默认从13行开始绘制大类表头以及表体信息
	
	private IMonthPlanService monthPlanService=(IMonthPlanService)ServiceFactory.getBean("monthPlanService");
	
	/*
	 * @作者 JunSun
	 * 下载Excel
	 */
	public void downloadExcel() throws ParseException {
		
		this.setCellFormat();//设置样式
		// 创建excel表格
		this.createSheet(this.formatTime(0));
	}
	/**
	 * @作者 JunSun
	 * 另一张表Excel下载
	 */
	public void downloadNewExcel(){
		
		this.setCellFormat();//设置样式
		//创建excel
		this.createNewSheet(this.formatTime(1));
	}
	/**
	 * @作者 JunSun
	 * 格式化获得的日期
	 * @return
	 */
	private String formatTime(int type){
		
		String excelName=null;
		// 获得JSF上下文环境
		FacesContext context = FacesContext.getCurrentInstance();

		String time = (String) context.getExternalContext()
				.getRequestParameterMap().get("saveTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// saveTime = sdf.parse(time);
		// Date saveTime1=sdf1.parse(time);
		// String formatTime=sdf.format(saveTime1);

		String[] month = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		System.out.println(time);
		String[] splitTime = time.split(" ");// 拆分时间
		String formatTime = splitTime[5];
		for (int i = 0; i < 12; i++) {

			if (splitTime[1].equals(month[i])) {
				if (i < 9) {

					formatTime += "-0" + (i + 1);
				} else {

					formatTime += "-" + (i + 1);
				}

			}
		}
		// 根据当前日期生成文件名
		
		if(type==0){
			
			excelName = formatTime + "月零件生产计划（准备）.xls";
			
		}else if(type==1){
			
			excelName = formatTime + "月零件生产计划.xls";
		}
		

		formatTime += "-" + splitTime[2];

		try {
			saveTime = sdf.parse(formatTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		System.out.println("downloadExcel得到的当前日期是----" + formatTime);

		return excelName;
	}
	/**
	 * @作者 在工作簿中创建sheet
	 * @param excelName
	 */
	private void createSheet(String excelName) {

			this.setHead(this.creatNewFile(excelName));//设置总的分厂表头
			this.setTypeHeadAndData();//设置每个大类的表头
			
			try {
				wwb.write();
				wwb.close();
			
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
			
			downloadFile(excelName);
		
	}
	/**
	 * @作者 JunSun
	 * @param excelName
	 * 创建另一张表的sheet
	 */
	private void createNewSheet(String excelName) {
		
		this.setNewHead(this.creatNewFile(excelName));
		this.setNewTypeHeadAndData();
		
		try {
			wwb.write();
			wwb.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		
		downloadFile(excelName);
	}
	/**
	 * @作者 JunSun
	 * @param excelName
	 * 在工作簿中新建excel文件并保存
	 */
	private String[] creatNewFile(String excelName){
		
		//取出excelName中的文件名
		String[] head=excelName.split("-");
		String[] name=head[1].split("\\.");
		// 获得JSF上下文环境
		FacesContext context = FacesContext.getCurrentInstance();
		// 获得ServletContext对象
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		// 取得文件的绝对路径
		String path = servletContext.getRealPath("/excel")+"/"+excelName;
		System.out.println("生成文件的路径是：" + path);
		// 创建Excel工作薄
				
		//在webroot下新建一个excel文件
		OutputStream os;
		try {
			os = new FileOutputStream(path);
			wwb = Workbook.createWorkbook(os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	/**
	 * @作者 JunSun
	 * 表格样式
	 */
	private void setCellFormat(){
		
		//设置字体    定义格式    字体   下划线    斜体    粗体      颜色
		font1 = new WritableFont(WritableFont.createFont("宋体"),16,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK); 
		font2 = new WritableFont(WritableFont.createFont("宋体"),12,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
		font3 = new WritableFont(WritableFont.createFont("宋体"),14,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
		//单元格定义
		cellFormat1=new WritableCellFormat(font1);
		try {
			//cellFormat1.setBackground(Colour.GREY_25_PERCENT);
			//设置字体居中对齐
			cellFormat1.setAlignment(Alignment.CENTRE);
			//设置垂直居中
			cellFormat1.setVerticalAlignment(VerticalAlignment.CENTRE);		
			
			//单元格定义
			cellFormat2=new WritableCellFormat(font2);
			//cellFormat2.setBackground(Colour.RED);
			//设置字体居中对齐
			cellFormat2.setAlignment(Alignment.CENTRE);
			//设置垂直居中
			cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);	
			
			//单元格定义
			cellFormat3=new WritableCellFormat(font3);
			//cellFormat3.setBackground(Colour.RED);
			//设置字体居中对齐
			cellFormat3.setAlignment(Alignment.CENTRE);
			//设置垂直居中
			cellFormat3.setVerticalAlignment(VerticalAlignment.CENTRE);	
			//设置边框
			cellFormat3.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			
			//单元格定义
			cellFormat4=new WritableCellFormat(font2);
			cellFormat4.setBackground(Colour.WHITE);
			//设置字体居中对齐
			cellFormat4.setAlignment(Alignment.CENTRE);
			//设置垂直居中
			cellFormat4.setVerticalAlignment(VerticalAlignment.CENTRE);	
			//设置边框
			cellFormat4.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			
			//单元格定义
			cellFormat5=new WritableCellFormat(font2);
			//cellFormat5.setBackground(Colour.RED);
			//设置字体居中对齐
			cellFormat5.setAlignment(Alignment.CENTRE);
			//设置垂直居中
			cellFormat5.setVerticalAlignment(VerticalAlignment.CENTRE);	
			//设置边框
			cellFormat5.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @作者 JunSun
	 * @param time
	 * 添加表头
	 */
	private void setHead(String[] head){
			
		try {
			//向工作簿中添加一个sheet
			sheet=wwb.createSheet("车削分厂"+head[0], 0);
			for(int i=0;i<12;i++){
				
				sheet.setColumnView(i, 10);
			}
				
			//合并单元格
			sheet.mergeCells(0, 0, 12, 0);
			sheet.mergeCells(0, 1, 2, 1);
			sheet.mergeCells(6, 1, 7, 1);
			sheet.mergeCells(0, 2, 2, 8);
			sheet.mergeCells(3, 2, 3, 8);
			sheet.mergeCells(4, 2, 4, 8);
			sheet.mergeCells(5, 2, 7, 7);
			sheet.mergeCells(8, 2, 12, 7);
			
			Label label1=new Label(0, 0, "车削分厂"+head[0], cellFormat1);
			Label label2=new Label(6, 1, "单位：千件",cellFormat2);
			Label label3=new Label(0, 2, "型号",cellFormat3);
			Label label4=new Label(3, 2, "重要度",cellFormat3);
			Label label5=new Label(4, 2, "零件",cellFormat3);
			Label label6=new Label(5, 2, "数量",cellFormat3);
			Label label7=new Label(8, 2, "交期",cellFormat3);
			
			Label label8=new Label(5, 8, "投料",cellFormat3);
			Label label9=new Label(6, 8, "提交",cellFormat3);
			Label label10=new Label(7, 8, "过热",cellFormat3);
			
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
			sheet.addCell(label4);
			sheet.addCell(label5);
			sheet.addCell(label6);
			sheet.addCell(label7);
			
			sheet.addCell(label8);
			sheet.addCell(label9);
			sheet.addCell(label10);
			
			//查询总表头信息
			List<Map<String, Object>> totalHead=monthPlanService.getTotalTableHeadData("planNum", "planEndtime",saveTime);
			Map<String, Object> headMap=totalHead.get(0);
			
			Label delivery1=new Label(8, 8, (String) headMap.get("delivery1"),cellFormat4);
			Label delivery2=new Label(9, 8, (String) headMap.get("delivery2"),cellFormat4);
			Label delivery3=new Label(10, 8, (String) headMap.get("delivery3"),cellFormat4);
			Label delivery4=new Label(11, 8, (String) headMap.get("delivery4"),cellFormat4);
			Label delivery5=new Label(12, 8, (String) headMap.get("delivery5"),cellFormat4);
			
			
			sheet.addCell(delivery1);
			sheet.addCell(delivery2);
			sheet.addCell(delivery3);
			sheet.addCell(delivery4);	
			sheet.addCell(delivery5);	
			
			sheet.mergeCells(0, 9, 2, 9);
			sheet.mergeCells(0, 10, 2, 10);
			Label total=new Label(0,9,"车削分厂合计：",cellFormat5);
			sheet.addCell(total);
			sheet.addCell(new Label(3,9," ",cellFormat4));
			sheet.addCell(new Label(4,9," ",cellFormat4));
			
			Label totalType=new Label(0,10,"品种（个）",cellFormat5);
			sheet.addCell(totalType);
			sheet.addCell(new Label(3,10," ",cellFormat4));
			sheet.addCell(new Label(4,10," ",cellFormat4));
			//设置 投料 提交 过热 数量
			Label totalFeeding=new Label(5,9, headMap.get("feedingNum").toString(),cellFormat4);
			Label totalSubmit=new Label(6,9, headMap.get("submitNum").toString(),cellFormat4);
			Label totalOverHeat=new Label(7,9, headMap.get("overHeat").toString(),cellFormat4);
			
			sheet.addCell(totalFeeding);
			sheet.addCell(totalSubmit);
			sheet.addCell(totalOverHeat);
			
			//分产能周计划数量
			Label week1=new Label(8,9,headMap.get("deliveryNum1").toString(),cellFormat4);
			Label week2=new Label(9,9,headMap.get("deliveryNum2").toString(),cellFormat4);
			Label week3=new Label(10,9,headMap.get("deliveryNum3").toString(),cellFormat4);
			Label week4=new Label(11,9,headMap.get("deliveryNum4").toString(),cellFormat4);
			Label week5=new Label(12,9,headMap.get("deliveryNum5").toString(),cellFormat4);
			
			sheet.addCell(week1);
			sheet.addCell(week2);
			sheet.addCell(week3);
			sheet.addCell(week4);
			sheet.addCell(week5);
			
			//设置总表头的种类个数
			Label totalTypeOfFeeding=new Label(5,10,headMap.get("typeOfFeeding").toString(),cellFormat4);
			Label totalTypeOfSubmit=new Label(6,10,headMap.get("typeOfSubmit").toString(),cellFormat4);
			Label totalTypeOfOverheat=new Label(7,10,headMap.get("typeOfOverHeat").toString(),cellFormat4);
			
			Label typeOfWeek1=new Label(8,10,headMap.get("typeOfDelivery1").toString(),cellFormat4);
			Label typeOfWeek2=new Label(9,10,headMap.get("typeOfDelivery2").toString(),cellFormat4);
			Label typeOfWeek3=new Label(10,10,headMap.get("typeOfDelivery3").toString(),cellFormat4);
			Label typeOfWeek4=new Label(11,10,headMap.get("typeOfDelivery4").toString(),cellFormat4);
			Label typeOfWeek5=new Label(12,10,headMap.get("typeOfDelivery5").toString(),cellFormat4);
			
			sheet.addCell(totalTypeOfFeeding);
			sheet.addCell(totalTypeOfSubmit);
			sheet.addCell(totalTypeOfOverheat);
			sheet.addCell(typeOfWeek1);
			sheet.addCell(typeOfWeek2);
			sheet.addCell(typeOfWeek3);
			sheet.addCell(typeOfWeek4);
			sheet.addCell(typeOfWeek5);
			
			
			
		} catch (RowsExceededException e) {
			e.printStackTrace();
		}catch (WriteException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setNewHead(String[] head){
		
		try {
			//向工作簿中添加一个sheet
			sheet=wwb.createSheet("车削分厂"+head[0], 0);
			for(int i=0;i<10;i++){
				//设置单元格lie
				sheet.setColumnView(i, 11);
			}
				
			//合并单元格
			sheet.mergeCells(0, 0, 10, 0);
			sheet.mergeCells(0, 1, 2, 1);
			sheet.mergeCells(5, 1, 6, 1);
			sheet.mergeCells(0, 2, 2, 8);
			sheet.mergeCells(3, 2, 3, 8);
			sheet.mergeCells(4, 2, 5, 7);
			sheet.mergeCells(6, 2, 10, 7);
			
			Label label1=new Label(0, 0, "车削分厂"+head[0], cellFormat1);
			Label label2=new Label(5, 1, "单位：千件",cellFormat2);
			Label label3=new Label(0, 2, "型号",cellFormat3);
			Label label4=new Label(3, 2, "零件",cellFormat3);
			Label label6=new Label(4, 2, "本月计划",cellFormat3);
			Label label7=new Label(6, 2, "提交进度",cellFormat3);
			
			Label label8=new Label(4, 8, "投料",cellFormat3);
			Label label9=new Label(5, 8, "提交",cellFormat3);
			
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
			sheet.addCell(label4);
			sheet.addCell(label6);
			sheet.addCell(label7);
			
			sheet.addCell(label8);
			sheet.addCell(label9);
			
			//查询总表头信息
			List<Map<String, Object>> totalHead=monthPlanService.getTotalTableHeadData("finishNum", "realEndtime",saveTime);
			Map<String, Object> headMap=totalHead.get(0);
			
			Label delivery1=new Label(6, 8, (String) headMap.get("delivery1"),cellFormat4);
			Label delivery2=new Label(7, 8, (String) headMap.get("delivery2"),cellFormat4);
			Label delivery3=new Label(8, 8, (String) headMap.get("delivery3"),cellFormat4);
			Label delivery4=new Label(9, 8, (String) headMap.get("delivery4"),cellFormat4);
			Label delivery5=new Label(10, 8, (String) headMap.get("delivery5"),cellFormat4);
			
			sheet.addCell(delivery1);
			sheet.addCell(delivery2);
			sheet.addCell(delivery3);
			sheet.addCell(delivery4);	
			sheet.addCell(delivery5);
			
			sheet.mergeCells(0, 9, 2, 9);
			sheet.mergeCells(0, 10, 2, 10);
			Label total=new Label(0,9,"车削分厂合计：",cellFormat5);
			sheet.addCell(total);
			sheet.addCell(new Label(3,9," ",cellFormat4));
			sheet.addCell(new Label(4,9," ",cellFormat4));
			
			//设置 投料 提交  数量
			Label totalFeeding=new Label(4,9, headMap.get("feedingNum").toString(),cellFormat4);
			Label totalSubmit=new Label(5,9, headMap.get("submitNum").toString(),cellFormat4);
			
			sheet.addCell(totalFeeding);
			sheet.addCell(totalSubmit);
			
			//分产能周计划数量
			Label week1=new Label(6,9,headMap.get("deliveryNum1").toString(),cellFormat4);
			Label week2=new Label(7,9,headMap.get("deliveryNum2").toString(),cellFormat4);
			Label week3=new Label(8,9,headMap.get("deliveryNum3").toString(),cellFormat4);
			Label week4=new Label(9,9,headMap.get("deliveryNum4").toString(),cellFormat4);
			Label week5=new Label(10,9,headMap.get("deliveryNum5").toString(),cellFormat4);
			
			sheet.addCell(week1);
			sheet.addCell(week2);
			sheet.addCell(week3);
			sheet.addCell(week4);
			sheet.addCell(week5);
			
			
		} catch (RowsExceededException e) {
			e.printStackTrace();
		}catch (WriteException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @作者 JunSun
	 * 添加大类表头以及大类表体数据
	 */
	@SuppressWarnings("unchecked")
	private void setTypeHeadAndData(){
		
		List<Map<String, Object>>typeAndData=monthPlanService.getPartClassAndHeadData( "planNum", "planEndtime",saveTime);
		
		row=12;
		for (Map<String, Object> map : typeAndData) {
			
			try {
				sheet.mergeCells(0, row, 2, row);
				Label typeName=new Label(0,row,"其中"+(String)map.get("partClass")+"合计：",cellFormat5);
				Label feeding=new Label(5,row,map.get("feedingNum").toString(),cellFormat4);
				Label submit=new Label(6,row,map.get("submitNum").toString(),cellFormat4);
				Label overHeat=new Label(7,row,map.get("overHeat").toString(),cellFormat4);
				
				Label deliveryNum1=new Label(8,row,map.get("deliveryNum1").toString(),cellFormat4);
				Label deliveryNum2=new Label(9,row,map.get("deliveryNum2").toString(),cellFormat4);
				Label deliveryNum3=new Label(10,row,map.get("deliveryNum3").toString(),cellFormat4);
				Label deliveryNum4=new Label(11,row,map.get("deliveryNum4").toString(),cellFormat4);
				Label deliveryNum5=new Label(12,row,map.get("deliveryNum5").toString(),cellFormat4);
				sheet.addCell(new Label(3,row," ",cellFormat4));
				sheet.addCell(new Label(4,row," ",cellFormat4));
				//下一行
				row+=1;
				
				sheet.mergeCells(0, row, 2, row);
		
				Label typeNumName=new Label(0,row,"品种（个）",cellFormat5);
				Label feedingType=new Label(5,row,map.get("typeOfFeeding").toString(),cellFormat4);
				Label submitType=new Label(6,row,map.get("typeOfSubmit").toString(),cellFormat4);
				Label overHeatType=new Label(7,row,map.get("typeOfOverHeat").toString(),cellFormat4);
				
				Label deliveryNum1Type=new Label(8,row,map.get("typeOfDelivery1").toString(),cellFormat4);
				Label deliveryNum2Type=new Label(9,row,map.get("typeOfDelivery2").toString(),cellFormat4);
				Label deliveryNum3Type=new Label(10,row,map.get("typeOfDelivery3").toString(),cellFormat4);
				Label deliveryNum4Type=new Label(11,row,map.get("typeOfDelivery4").toString(),cellFormat4);
				Label deliveryNum5Type=new Label(12,row,map.get("typeOfDelivery5").toString(),cellFormat4);
				
				sheet.addCell(new Label(3,row," ",cellFormat4));
				sheet.addCell(new Label(4,row," ",cellFormat4));
				//加入大类表头
				sheet.addCell(typeName);
				
				sheet.addCell(feeding);
				sheet.addCell(submit);
				sheet.addCell(overHeat);
				sheet.addCell(deliveryNum1);
				sheet.addCell(deliveryNum2);
				sheet.addCell(deliveryNum3);
				sheet.addCell(deliveryNum4);
				sheet.addCell(deliveryNum5);
				
				sheet.addCell(typeNumName);
				sheet.addCell(feedingType);
				sheet.addCell(submitType);
				sheet.addCell(overHeatType);
				sheet.addCell(deliveryNum1Type);
				sheet.addCell(deliveryNum2Type);
				sheet.addCell(deliveryNum3Type);
				sheet.addCell(deliveryNum4Type);
				sheet.addCell(deliveryNum5Type);
				
				
				//下一行
				row+=1;
				
				//获得这个大类的所有零件信息
				List<Map<String, Object>> typeData=(List<Map<String,Object>>)map.get("partClassData");
				for (Map<String, Object> map2 : typeData) {
					
					sheet.mergeCells(0, row, 2, row);
					Label classNo=new Label(0,row,(String)map2.get("typeNo"),cellFormat4);
					Label part=new Label(4,row,(String)map2.get("part"),cellFormat4);
					Label partFeeding=new Label(5,row,map2.get("typeFeeding").toString(),cellFormat4);
					Label partSubmit=new Label(6,row,map2.get("typeSubmit").toString(),cellFormat4);
					Label partOverHeat=new Label(7,row,map2.get("typeOverHeat").toString(),cellFormat4);
					Label partDelivery1=new Label(8,row,map2.get("typeDelivery1").toString(),cellFormat4);
					Label partDelivery2=new Label(9,row,map2.get("typeDelivery2").toString(),cellFormat4);
					Label partDelivery3=new Label(10,row,map2.get("typeDelivery3").toString(),cellFormat4);
					Label partDelivery4=new Label(11,row,map2.get("typeDelivery4").toString(),cellFormat4);
					Label partDelivery5=new Label(12,row,map2.get("typeDelivery5").toString(),cellFormat4);
					
					sheet.addCell(classNo);
					
					sheet.addCell(new Label(3,row," ",cellFormat4));
					
					sheet.addCell(part);
					sheet.addCell(partFeeding);
					sheet.addCell(partSubmit);
					sheet.addCell(partOverHeat);
					sheet.addCell(partDelivery1);
					sheet.addCell(partDelivery2);
					sheet.addCell(partDelivery3);
					sheet.addCell(partDelivery4);
					sheet.addCell(partDelivery5);
					
					//下一行
					row+=1;
				}
				row+=1;
				
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
			
		}
		row=12;//导出一个Excel之后row重置
	
	}

	private void setNewTypeHeadAndData(){
		
		row=11;
		List<Map<String, Object>>typeAndData=monthPlanService.getPartClassAndHeadData( "finishNum", "realEndtime",saveTime);
		
		for (Map<String, Object> map : typeAndData) {
			
			try {
				sheet.mergeCells(0, row, 2, row);
				Label typeName=new Label(0,row,"其中"+(String)map.get("partClass")+"合计：",cellFormat5);
				Label feeding=new Label(4,row,map.get("feedingNum").toString(),cellFormat4);
				Label submit=new Label(5,row,map.get("submitNum").toString(),cellFormat4);
				
				Label deliveryNum1=new Label(6,row,map.get("deliveryNum1").toString(),cellFormat4);
				Label deliveryNum2=new Label(7,row,map.get("deliveryNum2").toString(),cellFormat4);
				Label deliveryNum3=new Label(8,row,map.get("deliveryNum3").toString(),cellFormat4);
				Label deliveryNum4=new Label(9,row,map.get("deliveryNum4").toString(),cellFormat4);
				Label deliveryNum5=new Label(10,row,map.get("deliveryNum5").toString(),cellFormat4);
				sheet.addCell(new Label(3,row," ",cellFormat4));
				
				//加入大类表头
				sheet.addCell(typeName);
				
				sheet.addCell(feeding);
				sheet.addCell(submit);
				sheet.addCell(deliveryNum1);
				sheet.addCell(deliveryNum2);
				sheet.addCell(deliveryNum3);
				sheet.addCell(deliveryNum4);	
				sheet.addCell(deliveryNum5);
				//下一行
				row+=1;
				
				//获得这个大类的所有零件信息
				List<Map<String, Object>> typeData=(List<Map<String,Object>>)map.get("partClassData");
				for (Map<String, Object> map2 : typeData) {
					
					sheet.mergeCells(0, row, 2, row);
					Label classNo=new Label(0,row,(String)map2.get("typeNo"),cellFormat4);
					Label part=new Label(3,row,(String)map2.get("part"),cellFormat4);
					Label partFeeding=new Label(4,row,map2.get("typeFeeding").toString(),cellFormat4);
					Label partSubmit=new Label(5,row,map2.get("typeSubmit").toString(),cellFormat4);
					Label partDelivery1=new Label(6,row,map2.get("typeDelivery1").toString(),cellFormat4);
					Label partDelivery2=new Label(7,row,map2.get("typeDelivery2").toString(),cellFormat4);
					Label partDelivery3=new Label(8,row,map2.get("typeDelivery3").toString(),cellFormat4);
					Label partDelivery4=new Label(9,row,map2.get("typeDelivery4").toString(),cellFormat4);
					Label partDelivery5=new Label(10,row,map2.get("typeDelivery5").toString(),cellFormat4);
					
					sheet.addCell(classNo);
					sheet.addCell(part);
					sheet.addCell(partFeeding);
					sheet.addCell(partSubmit);		
					sheet.addCell(partDelivery1);
					sheet.addCell(partDelivery2);
					sheet.addCell(partDelivery3);
					sheet.addCell(partDelivery4);
					sheet.addCell(partDelivery5);
					
					//下一行
					row+=1;
				}
				row+=1;
				
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
			
		}
		row=11;//导出一个Excel之后row重置
	}
	/**
	 * <p>
	 * 功能说明：根据提供的文件名下载文件,不支持中文文件名
	 * </p>
	 * 此方法由yongtree添加,实现文件生成后的下载
	 * 
	 * @param strfileName
	 *            String
	 * @return void
	 */
	private static void downloadFile(String strfileName) {
		try {
			// 获得JSF上下文环境
			FacesContext context = FacesContext.getCurrentInstance();
			// 获得ServletContext对象
			ServletContext servletContext = (ServletContext) context
					.getExternalContext().getContext();
			
			// 取得文件的绝对路径
			String excelName = servletContext.getRealPath("/excel") + "/"+ strfileName;
			File exportFile = new File(excelName);
			System.out.println("下载文件路径是："+excelName);
			
			URLEncoder.encode(strfileName,"UTF-8");
			strfileName=new String(strfileName.getBytes("UTF-8"),"iso-8859-1");
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = httpServletResponse
					.getOutputStream();
			httpServletResponse.reset();
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setHeader("Content-disposition",
					"attachment; filename=" + strfileName);
			httpServletResponse.setContentLength((int) exportFile.length());
			httpServletResponse.setContentType("application/x-download");
			// httpServletResponse.setContentType("application/vnd.ms-excel");

			byte[] b = new byte[1024];
			int i = 0;
			FileInputStream fis = new FileInputStream(exportFile);
			while ((i = fis.read(b)) > 0) {
				servletOutputStream.write(b, 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().responseComplete();
	}

	public Date getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

}
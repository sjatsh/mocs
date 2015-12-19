package smtcl.mocs.beans.infoManage;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.services.report.IDailyService;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@ManagedBean(name = "Excel")
@SessionScoped
public class ExcelUtils2 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int row = 4;
	private Date outTime;
	private WritableSheet sheetOne;
	private WritableWorkbook book;
	private WritableFont wf_title;
	private WritableFont wf_title2;
	private WritableFont wf_head;
	private WritableFont wf_table;

	// 单元格定义
	private WritableCellFormat wcf_title;
	private WritableCellFormat wcf_title2;
	private WritableCellFormat wcf_head1;
	private WritableCellFormat wcf_body;
	private IDailyService dailyService = (IDailyService) ServiceFactory
			.getBean("dailyService");

	public void downloadExcel() throws IOException, ParseException,
			WriteException {
		// 获得JSF上下文环境
		FacesContext context = FacesContext.getCurrentInstance();

		String time = (String) context.getExternalContext()
				.getRequestParameterMap().get("outTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		/*
		 * if (time.length()<0) { time = StringUtils.formatDate(new Date(), 2);
		 * String excelName = time; this.createSheet(excelName); } else {
		 */
		String[] month = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		System.out.println(time);
		String[] splitTime = time.split(" ");// 拆分时间

		String formatTime = splitTime[5];
		for (int i = 0; i < 12; i++) {
			String str = splitTime[1];
			String str2 = month[i];
			if (str.equals(str2)) {
				if (i < 9) {
					formatTime += "-0" + (i + 1);
				} else {

					formatTime += "-" + (i + 1);
				}
			}
		}
		formatTime += "-" + splitTime[2];
		outTime = sdf.parse(formatTime);
		System.out.println("downloadExcel得到的当前日期是----" + formatTime);
		// 根据当前日期生成文件名
		String excelName = formatTime + ".xls";
		// 创建excel表格
		this.createSheet(excelName);
		// }
	}

	public void createSheet(String excelName) throws IOException,
			WriteException {
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context
				.getExternalContext().getContext();
		String path = servletContext.getRealPath("/excel") + "/" + excelName;
		System.out.println("生成文件的路径是：" + path);
		try {
			OutputStream os = new FileOutputStream(path);
			book = Workbook.createWorkbook(os);
			this.creatHead(excelName.substring(0, 10));
			this.setData(excelName.substring(0, 10));
			book.write();
			book.close();
			downloadFile(excelName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void creatHead(String excelName) throws WriteException {

		try {
			sheetOne = book.createSheet("车削分厂月内生产" + excelName + "日报表", 0);
			wf_title = new WritableFont(WritableFont.ARIAL, 14,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
			wf_head = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
			wf_table = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);

			wf_title2 = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);

			wcf_title2 = new WritableCellFormat(wf_title2);
			wcf_title2.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色
			wcf_title2.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
			wcf_title2
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf_title2.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 设置边框

			wcf_title = new WritableCellFormat(wf_title);
			wcf_title.setBackground(jxl.format.Colour.RED); // 设置单元格的背景颜色
			wcf_title.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
			wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf_title.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 设置边框

			wcf_head1 = new WritableCellFormat(wf_head);
			wcf_head1.setBackground(jxl.format.Colour.RED);
			wcf_head1.setAlignment(jxl.format.Alignment.CENTRE);
			wcf_head1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf_head1.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);

			wcf_body = new WritableCellFormat(wf_head);
			wcf_body.setBackground(jxl.format.Colour.WHITE);
			wcf_body.setAlignment(jxl.format.Alignment.CENTRE);
			wcf_body.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf_body.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			// sheetOne.setRowView(0, 500, false);// 设置第一行行高

			sheetOne.setColumnView(0, 12); // 设置列的宽度
			sheetOne.setColumnView(1, 12); // 设置列的宽度
			sheetOne.setColumnView(2, 12); // 设置列的宽度
			sheetOne.setColumnView(3, 12); // 设置列的宽度
			sheetOne.setColumnView(4, 12); // 设置列的宽度
			sheetOne.setColumnView(5, 12); // 设置列的宽度
			sheetOne.setColumnView(6, 12); // 设置列的宽度
			sheetOne.setColumnView(7, 12); // 设置列的宽度
			sheetOne.setColumnView(8, 12); // 设置列的宽度
			sheetOne.setColumnView(9, 12); // 设置列的宽度
			sheetOne.setColumnView(10, 12); // 设置列的宽度
			sheetOne.setColumnView(11, 12); // 设置列的宽度
			sheetOne.setColumnView(12, 12); // 设置列的宽度
			sheetOne.setColumnView(13, 12); // 设置列的宽度
			sheetOne.setColumnView(14, 12); // 设置列的宽度
			sheetOne.setColumnView(15, 12); // 设置列的宽度
			sheetOne.setColumnView(16, 12); // 设置列的宽度
			sheetOne.setColumnView(17, 12); // 设置列的宽度
			sheetOne.setColumnView(18, 12); // 设置列的宽度
			sheetOne.setColumnView(19, 12); // 设置列的宽度
			sheetOne.setColumnView(20, 12); // 设置列的宽度
			sheetOne.setColumnView(21, 12); // 设置列的宽度
			sheetOne.setColumnView(22, 12); // 设置列的宽度
			sheetOne.setColumnView(23, 12); // 设置列的宽度
			sheetOne.setColumnView(24, 12); // 设置列的宽度
			sheetOne.setColumnView(25, 12); // 设置列的宽度

			Label title = new Label(0, 0, "车削分厂" + excelName.substring(0, 4)
					+ "年" + excelName.substring(5, 7) + "月"
					+ excelName.substring(8, 10) + "日" + "生产日报", wcf_title);

			Label column1 = new Label(0, 1, "序号", wcf_head1);
			Label column2 = new Label(1, 1, "型号", wcf_head1);
			Label column3 = new Label(2, 1, "零件", wcf_head1);
			Label column4 = new Label(3, 1, "上月结存", wcf_head1);
			Label column5 = new Label(4, 1, "", wcf_head1);
			Label column6 = new Label(5, 1, "月度作业计划", wcf_head1);
			Label column7 = new Label(20, 1, "急需进度", wcf_head1);
			Label column8 = new Label(21, 1, "实际完成", wcf_head1);
			Label column9 = new Label(22, 1, "品种", wcf_head1);
			Label column10 = new Label(24, 1, "提交产值", wcf_head1);

			Label column2_1 = new Label(4, 2, "", wcf_head1);
			Label column2_2 = new Label(5, 2, "毛坯购进", wcf_head1);
			Label column2_3 = new Label(7, 2, "月投料计划", wcf_head1);
			Label column2_4 = new Label(8, 2, "", wcf_head1);
			Label column2_5 = new Label(9, 2, "投料产量", wcf_head1);
			Label column2_6 = new Label(11, 2, "%", wcf_head1);
			Label column2_7 = new Label(12, 2, "月提交计划", wcf_head1);
			Label column2_8 = new Label(13, 2, "", wcf_head1);
			Label column2_9 = new Label(14, 2, "提交产量", wcf_head1);
			Label column2_10 = new Label(16, 2, "%", wcf_head1);
			Label column2_11 = new Label(17, 2, "废品", wcf_head1);
			Label column2_12 = new Label(19, 2, "在制", wcf_head1);
			Label column2_13 = new Label(22, 2, "计划", wcf_head1);
			Label column2_14 = new Label(23, 2, "完成", wcf_head1);

			Label column3_1 = new Label(5, 3, "当日", wcf_head1);
			Label column3_2 = new Label(6, 3, "累计", wcf_head1);
			Label column3_3 = new Label(9, 3, "当日", wcf_head1);
			Label column3_4 = new Label(10, 3, "累计", wcf_head1);
			Label column3_5 = new Label(14, 3, "当日", wcf_head1);
			Label column3_6 = new Label(15, 3, "累计", wcf_head1);
			Label column3_7 = new Label(17, 3, "料废", wcf_head1);
			Label column3_8 = new Label(18, 3, "工废", wcf_head1);

			sheetOne.addCell(title);
			sheetOne.addCell(column1);
			sheetOne.addCell(column2);
			sheetOne.addCell(column3);
			sheetOne.addCell(column4);
			sheetOne.addCell(column5);
			sheetOne.addCell(column6);
			sheetOne.addCell(column7);
			sheetOne.addCell(column8);
			sheetOne.addCell(column9);
			sheetOne.addCell(column10);

			sheetOne.addCell(column2_1);
			sheetOne.addCell(column2_2);
			sheetOne.addCell(column2_3);
			sheetOne.addCell(column2_4);
			sheetOne.addCell(column2_5);
			sheetOne.addCell(column2_6);
			sheetOne.addCell(column2_7);
			sheetOne.addCell(column2_8);
			sheetOne.addCell(column2_9);
			sheetOne.addCell(column2_10);
			sheetOne.addCell(column2_11);
			sheetOne.addCell(column2_12);
			sheetOne.addCell(column2_13);
			sheetOne.addCell(column2_14);

			sheetOne.addCell(column3_1);
			sheetOne.addCell(column3_2);
			sheetOne.addCell(column3_3);
			sheetOne.addCell(column3_4);
			sheetOne.addCell(column3_5);
			sheetOne.addCell(column3_6);
			sheetOne.addCell(column3_7);
			sheetOne.addCell(column3_8);

			sheetOne.mergeCells(0, 0, 24, 0);// 表头

			sheetOne.mergeCells(0, 1, 0, 3);// 序号
			sheetOne.mergeCells(1, 1, 1, 3);// 型号
			sheetOne.mergeCells(2, 1, 2, 3);// 零件
			sheetOne.mergeCells(3, 1, 3, 3);// 上月结存

			sheetOne.mergeCells(4, 2, 4, 3);//
			sheetOne.mergeCells(5, 1, 19, 1);// 月度作业计划

			sheetOne.mergeCells(5, 2, 6, 2);// 毛坯购进
			sheetOne.mergeCells(7, 2, 7, 3);// 月投料计划
			sheetOne.mergeCells(8, 2, 8, 3);//
			sheetOne.mergeCells(9, 2, 10, 2);// 投料产量
			sheetOne.mergeCells(11, 2, 11, 3);// %
			sheetOne.mergeCells(12, 2, 12, 3);// 月提交计划
			sheetOne.mergeCells(13, 2, 13, 3);//
			sheetOne.mergeCells(14, 2, 15, 2);// 提交产量
			sheetOne.mergeCells(16, 2, 16, 3);// %
			sheetOne.mergeCells(17, 2, 18, 2);// 废品
			sheetOne.mergeCells(19, 2, 19, 3);// 在制
			sheetOne.mergeCells(20, 1, 20, 3);// 急需进度
			sheetOne.mergeCells(21, 1, 21, 3);// 实际完成
			sheetOne.mergeCells(22, 1, 23, 1);// 品种
			sheetOne.mergeCells(22, 2, 22, 3);// 计划
			sheetOne.mergeCells(23, 2, 23, 3);// 完成
			sheetOne.mergeCells(24, 1, 24, 3);// 提交产量

		} catch (WriteException e) {
			e.printStackTrace();
		}

	}

	private void downloadFile(String fileName) {

		try {
			FacesContext context = FacesContext.getCurrentInstance();
			// 获得ServletContext对象
			ServletContext servletContext = (ServletContext) context
					.getExternalContext().getContext();
			// 取得文件的绝对路径
			String excelName = servletContext.getRealPath("/excel") + "/"
					+ fileName;
			File exportFile = new File(excelName);
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			ServletOutputStream servletOutputStream = httpServletResponse
					.getOutputStream();
			httpServletResponse.setHeader("Content-disposition",
					"attachment; filename=" + fileName);
			httpServletResponse.setContentLength((int) exportFile.length());
			httpServletResponse.setContentType("application/x-download");

			byte[] b = new byte[1024];
			int i = 0;
			FileInputStream fis = new java.io.FileInputStream(exportFile);
			while ((i = fis.read(b)) > 0) {
				servletOutputStream.write(b, 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void setData(String time) throws RowsExceededException,
			WriteException {
		List<Map<String, Object>> list1 = dailyService.getAmountData(time);
		List<Map<String, Object>> list2 = dailyService.getTotalData(time);
		List<Map<String, Object>> list3 = dailyService
				.getProductionDailyReport(time);
		if(list1!=null){
			for (Map<String, Object> map : list1) {
				Label col0 = new Label(0, row, "", wcf_body);
				Label col1 = new Label(1, row, map.get("partClassName").toString(),
						wcf_title2);
				Label col2 = new Label(2, row, "", wcf_body);
				Label col3 = new Label(3, row, "", wcf_body);
				Label col4 = new Label(4, row, "", wcf_body);
				Label col5 = new Label(5, row, "", wcf_body);
				Label col6 = new Label(6, row, "", wcf_body);
				Label col7 = new Label(7, row, map.get("sumMonthFeedPlan")
						.toString(), wcf_body);
				Label col8 = new Label(8, row,
						map.get("sumYieldBefore").toString(), wcf_body);
				Label col9 = new Label(9, row, map.get("sumYieldToday").toString(),
						wcf_body);
				Label col10 = new Label(10, row, map.get("sumYieldSum").toString(),
						wcf_body);
				Label col11 = new Label(11, row, map.get("sumFeedPercent")
						.toString(), wcf_body);
				Label col12 = new Label(12, row, map.get("sumMonthSubmitPlan")
						.toString(), wcf_body);
				Label col13 = new Label(13, row, map.get("sumSubmitBefore")
						.toString(), wcf_body);
				Label col14 = new Label(14, row, map.get("sumSubmitToday")
						.toString(), wcf_body);
				Label col15 = new Label(15, row,
						map.get("sumSubmitSum").toString(), wcf_body);
				Label col16 = new Label(16, row, map.get("sumSumbitPercent")
						.toString(), wcf_body);
				Label col17 = new Label(17, row, "", wcf_body);
				Label col18 = new Label(18, row, "", wcf_body);
				Label col19 = new Label(19, row, "", wcf_body);
				Label col20 = new Label(20, row, "", wcf_body);
				Label col21 = new Label(21, row, "", wcf_body);
				Label col22 = new Label(22, row, "", wcf_body);
				Label col23 = new Label(23, row, "", wcf_body);
				Label col24 = new Label(24, row, "", wcf_body);

				sheetOne.addCell(col0);
				sheetOne.addCell(col1);

				sheetOne.addCell(col2);
				sheetOne.addCell(col3);
				sheetOne.addCell(col4);
				sheetOne.addCell(col5);
				sheetOne.addCell(col6);

				sheetOne.addCell(col7);
				sheetOne.addCell(col8);
				sheetOne.addCell(col9);
				sheetOne.addCell(col10);
				sheetOne.addCell(col11);
				sheetOne.addCell(col12);
				sheetOne.addCell(col13);
				sheetOne.addCell(col14);
				sheetOne.addCell(col15);
				sheetOne.addCell(col16);

				sheetOne.addCell(col17);
				sheetOne.addCell(col18);
				sheetOne.addCell(col19);
				sheetOne.addCell(col20);
				sheetOne.addCell(col21);
				sheetOne.addCell(col22);
				sheetOne.addCell(col23);
				sheetOne.addCell(col24);

				row = row + 1;
			}
		}
		//System.out.println(list1.size());
		if(list1!=null){
			row=4+list1.size();
		}
		else{
			row=4;
		}
		//row = 8;
		if(list2!=null){
			for (Map<String, Object> map : list2) {

				Label col0 = new Label(0, row, "", wcf_body);
				Label col1 = new Label(1, row, map.get("total").toString(),
						wcf_title2);

				Label col2 = new Label(2, row, "", wcf_body);
				Label col3 = new Label(3, row, "", wcf_body);
				Label col4 = new Label(4, row, "", wcf_body);
				Label col5 = new Label(5, row, "", wcf_body);
				Label col6 = new Label(6, row, "", wcf_body);

				Label col7 = new Label(7, row, map.get("totalMonthFeedPlan")
						.toString(), wcf_body);
				Label col8 = new Label(8, row, map.get("totalYieldBefore")
						.toString(), wcf_body);
				Label col9 = new Label(9, row, map.get("totalYieldToday")
						.toString(), wcf_body);
				Label col10 = new Label(10, row, map.get("totalYieldSum")
						.toString(), wcf_body);
				Label col11 = new Label(11, row, map.get("totalFeedPercent")
						.toString(), wcf_body);
				Label col12 = new Label(12, row, map.get("totalMonthSubmitPlan")
						.toString(), wcf_body);
				Label col13 = new Label(13, row, map.get("totalSubmitBefore")
						.toString(), wcf_body);
				Label col14 = new Label(14, row, map.get("totalSubmitToday")
						.toString(), wcf_body);
				Label col15 = new Label(15, row, map.get("totalSubmitSum")
						.toString(), wcf_body);
				Label col16 = new Label(16, row, map.get("totalSubmitPercent")
						.toString(), wcf_body);

				Label col17 = new Label(17, row, "", wcf_body);
				Label col18 = new Label(18, row, "", wcf_body);
				Label col19 = new Label(19, row, "", wcf_body);
				Label col20 = new Label(20, row, "", wcf_body);
				Label col21 = new Label(21, row, "", wcf_body);
				Label col22 = new Label(22, row, "", wcf_body);
				Label col23 = new Label(23, row, "", wcf_body);
				Label col24 = new Label(24, row, "", wcf_body);

				sheetOne.addCell(col0);
				sheetOne.addCell(col1);
				sheetOne.addCell(col2);
				sheetOne.addCell(col3);
				sheetOne.addCell(col4);
				sheetOne.addCell(col5);
				sheetOne.addCell(col6);
				sheetOne.addCell(col7);
				sheetOne.addCell(col8);
				sheetOne.addCell(col9);
				sheetOne.addCell(col10);
				sheetOne.addCell(col11);
				sheetOne.addCell(col12);
				sheetOne.addCell(col13);
				sheetOne.addCell(col14);
				sheetOne.addCell(col15);
				sheetOne.addCell(col16);

				sheetOne.addCell(col17);
				sheetOne.addCell(col18);
				sheetOne.addCell(col19);
				sheetOne.addCell(col20);
				sheetOne.addCell(col21);
				sheetOne.addCell(col22);
				sheetOne.addCell(col23);
				sheetOne.addCell(col24);
			}
		}
		
		if(list1!=null&&list2!=null){
			row=4+list1.size()+list2.size();
		}
		else{
			row=4;
		}
		
		if(list3!=null){
			for (Map<String, Object> map2 : list3) {

				Label col0 = new Label(0, row, map2.get("orderNo").toString(),
						wcf_body);
				Label col1 = new Label(1, row, map2.get("modelNo").toString(),
						wcf_body);
				Label col2 = new Label(2, row, map2.get("partNo").toString(),
						wcf_body);

				Label col3 = new Label(3, row, "", wcf_body);
				Label col4 = new Label(4, row, "", wcf_body);
				Label col5 = new Label(5, row, "", wcf_body);
				Label col6 = new Label(6, row, "", wcf_body);
				Label col7 = new Label(7, row,
						map2.get("monthFeedPlan").toString(), wcf_body);
				Label col8 = new Label(8, row, map2.get("yieldBefore").toString(),
						wcf_body);
				Label col9 = new Label(9, row, map2.get("yieldToday").toString(),
						wcf_body);
				Label col10 = new Label(10, row, map2.get("yieldSum").toString(),
						wcf_body);
				Label col11 = new Label(11, row,
						map2.get("feedPercent").toString(), wcf_body);
				Label col12 = new Label(12, row, map2.get("monthSubmitPlan")
						.toString(), wcf_body);
				Label col13 = new Label(13, row, map2.get("submitBefore")
						.toString(), wcf_body);
				Label col14 = new Label(14, row,
						map2.get("submitToday").toString(), wcf_body);
				Label col15 = new Label(15, row, map2.get("submitSum").toString(),
						wcf_body);
				Label col16 = new Label(16, row, map2.get("sumbitPercent")
						.toString(), wcf_body);

				Label col17 = new Label(17, row, "", wcf_body);
				Label col18 = new Label(18, row, "", wcf_body);
				Label col19 = new Label(19, row, "", wcf_body);
				Label col20 = new Label(20, row, "", wcf_body);
				Label col21 = new Label(21, row, "", wcf_body);
				Label col22 = new Label(22, row, "", wcf_body);
				Label col23 = new Label(23, row, "", wcf_body);
				Label col24 = new Label(24, row, "", wcf_body);

				sheetOne.addCell(col0);
				sheetOne.addCell(col1);
				sheetOne.addCell(col2);
				sheetOne.addCell(col3);
				sheetOne.addCell(col4);
				sheetOne.addCell(col5);
				sheetOne.addCell(col6);
				sheetOne.addCell(col7);
				sheetOne.addCell(col8);
				sheetOne.addCell(col9);
				sheetOne.addCell(col10);
				sheetOne.addCell(col11);
				sheetOne.addCell(col12);
				sheetOne.addCell(col13);
				sheetOne.addCell(col14);
				sheetOne.addCell(col15);
				sheetOne.addCell(col16);

				sheetOne.addCell(col17);
				sheetOne.addCell(col18);
				sheetOne.addCell(col19);
				sheetOne.addCell(col20);
				sheetOne.addCell(col21);
				sheetOne.addCell(col22);
				sheetOne.addCell(col23);
				sheetOne.addCell(col24);
				row = row + 1;
			}
		}
		
		row=4;//重置数据开始行
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

}
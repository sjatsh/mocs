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

	// ��Ԫ����
	private WritableCellFormat wcf_title;
	private WritableCellFormat wcf_title2;
	private WritableCellFormat wcf_head1;
	private WritableCellFormat wcf_body;
	private IDailyService dailyService = (IDailyService) ServiceFactory
			.getBean("dailyService");

	public void downloadExcel() throws IOException, ParseException,
			WriteException {
		// ���JSF�����Ļ���
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
		String[] splitTime = time.split(" ");// ���ʱ��

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
		System.out.println("downloadExcel�õ��ĵ�ǰ������----" + formatTime);
		// ���ݵ�ǰ���������ļ���
		String excelName = formatTime + ".xls";
		// ����excel���
		this.createSheet(excelName);
		// }
	}

	public void createSheet(String excelName) throws IOException,
			WriteException {
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context
				.getExternalContext().getContext();
		String path = servletContext.getRealPath("/excel") + "/" + excelName;
		System.out.println("�����ļ���·���ǣ�" + path);
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
			sheetOne = book.createSheet("�����ֳ���������" + excelName + "�ձ���", 0);
			wf_title = new WritableFont(WritableFont.ARIAL, 14,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // �����ʽ ���� �»��� б�� ���� ��ɫ
			wf_head = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // �����ʽ ���� �»��� б�� ���� ��ɫ
			wf_table = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);

			wf_title2 = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);

			wcf_title2 = new WritableCellFormat(wf_title2);
			wcf_title2.setBackground(jxl.format.Colour.WHITE); // ���õ�Ԫ��ı�����ɫ
			wcf_title2.setAlignment(jxl.format.Alignment.CENTRE); // ���ö��뷽ʽ
			wcf_title2
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf_title2.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // ���ñ߿�

			wcf_title = new WritableCellFormat(wf_title);
			wcf_title.setBackground(jxl.format.Colour.RED); // ���õ�Ԫ��ı�����ɫ
			wcf_title.setAlignment(jxl.format.Alignment.CENTRE); // ���ö��뷽ʽ
			wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf_title.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // ���ñ߿�

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
			// sheetOne.setRowView(0, 500, false);// ���õ�һ���и�

			sheetOne.setColumnView(0, 12); // �����еĿ��
			sheetOne.setColumnView(1, 12); // �����еĿ��
			sheetOne.setColumnView(2, 12); // �����еĿ��
			sheetOne.setColumnView(3, 12); // �����еĿ��
			sheetOne.setColumnView(4, 12); // �����еĿ��
			sheetOne.setColumnView(5, 12); // �����еĿ��
			sheetOne.setColumnView(6, 12); // �����еĿ��
			sheetOne.setColumnView(7, 12); // �����еĿ��
			sheetOne.setColumnView(8, 12); // �����еĿ��
			sheetOne.setColumnView(9, 12); // �����еĿ��
			sheetOne.setColumnView(10, 12); // �����еĿ��
			sheetOne.setColumnView(11, 12); // �����еĿ��
			sheetOne.setColumnView(12, 12); // �����еĿ��
			sheetOne.setColumnView(13, 12); // �����еĿ��
			sheetOne.setColumnView(14, 12); // �����еĿ��
			sheetOne.setColumnView(15, 12); // �����еĿ��
			sheetOne.setColumnView(16, 12); // �����еĿ��
			sheetOne.setColumnView(17, 12); // �����еĿ��
			sheetOne.setColumnView(18, 12); // �����еĿ��
			sheetOne.setColumnView(19, 12); // �����еĿ��
			sheetOne.setColumnView(20, 12); // �����еĿ��
			sheetOne.setColumnView(21, 12); // �����еĿ��
			sheetOne.setColumnView(22, 12); // �����еĿ��
			sheetOne.setColumnView(23, 12); // �����еĿ��
			sheetOne.setColumnView(24, 12); // �����еĿ��
			sheetOne.setColumnView(25, 12); // �����еĿ��

			Label title = new Label(0, 0, "�����ֳ�" + excelName.substring(0, 4)
					+ "��" + excelName.substring(5, 7) + "��"
					+ excelName.substring(8, 10) + "��" + "�����ձ�", wcf_title);

			Label column1 = new Label(0, 1, "���", wcf_head1);
			Label column2 = new Label(1, 1, "�ͺ�", wcf_head1);
			Label column3 = new Label(2, 1, "���", wcf_head1);
			Label column4 = new Label(3, 1, "���½��", wcf_head1);
			Label column5 = new Label(4, 1, "", wcf_head1);
			Label column6 = new Label(5, 1, "�¶���ҵ�ƻ�", wcf_head1);
			Label column7 = new Label(20, 1, "�������", wcf_head1);
			Label column8 = new Label(21, 1, "ʵ�����", wcf_head1);
			Label column9 = new Label(22, 1, "Ʒ��", wcf_head1);
			Label column10 = new Label(24, 1, "�ύ��ֵ", wcf_head1);

			Label column2_1 = new Label(4, 2, "", wcf_head1);
			Label column2_2 = new Label(5, 2, "ë������", wcf_head1);
			Label column2_3 = new Label(7, 2, "��Ͷ�ϼƻ�", wcf_head1);
			Label column2_4 = new Label(8, 2, "", wcf_head1);
			Label column2_5 = new Label(9, 2, "Ͷ�ϲ���", wcf_head1);
			Label column2_6 = new Label(11, 2, "%", wcf_head1);
			Label column2_7 = new Label(12, 2, "���ύ�ƻ�", wcf_head1);
			Label column2_8 = new Label(13, 2, "", wcf_head1);
			Label column2_9 = new Label(14, 2, "�ύ����", wcf_head1);
			Label column2_10 = new Label(16, 2, "%", wcf_head1);
			Label column2_11 = new Label(17, 2, "��Ʒ", wcf_head1);
			Label column2_12 = new Label(19, 2, "����", wcf_head1);
			Label column2_13 = new Label(22, 2, "�ƻ�", wcf_head1);
			Label column2_14 = new Label(23, 2, "���", wcf_head1);

			Label column3_1 = new Label(5, 3, "����", wcf_head1);
			Label column3_2 = new Label(6, 3, "�ۼ�", wcf_head1);
			Label column3_3 = new Label(9, 3, "����", wcf_head1);
			Label column3_4 = new Label(10, 3, "�ۼ�", wcf_head1);
			Label column3_5 = new Label(14, 3, "����", wcf_head1);
			Label column3_6 = new Label(15, 3, "�ۼ�", wcf_head1);
			Label column3_7 = new Label(17, 3, "�Ϸ�", wcf_head1);
			Label column3_8 = new Label(18, 3, "����", wcf_head1);

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

			sheetOne.mergeCells(0, 0, 24, 0);// ��ͷ

			sheetOne.mergeCells(0, 1, 0, 3);// ���
			sheetOne.mergeCells(1, 1, 1, 3);// �ͺ�
			sheetOne.mergeCells(2, 1, 2, 3);// ���
			sheetOne.mergeCells(3, 1, 3, 3);// ���½��

			sheetOne.mergeCells(4, 2, 4, 3);//
			sheetOne.mergeCells(5, 1, 19, 1);// �¶���ҵ�ƻ�

			sheetOne.mergeCells(5, 2, 6, 2);// ë������
			sheetOne.mergeCells(7, 2, 7, 3);// ��Ͷ�ϼƻ�
			sheetOne.mergeCells(8, 2, 8, 3);//
			sheetOne.mergeCells(9, 2, 10, 2);// Ͷ�ϲ���
			sheetOne.mergeCells(11, 2, 11, 3);// %
			sheetOne.mergeCells(12, 2, 12, 3);// ���ύ�ƻ�
			sheetOne.mergeCells(13, 2, 13, 3);//
			sheetOne.mergeCells(14, 2, 15, 2);// �ύ����
			sheetOne.mergeCells(16, 2, 16, 3);// %
			sheetOne.mergeCells(17, 2, 18, 2);// ��Ʒ
			sheetOne.mergeCells(19, 2, 19, 3);// ����
			sheetOne.mergeCells(20, 1, 20, 3);// �������
			sheetOne.mergeCells(21, 1, 21, 3);// ʵ�����
			sheetOne.mergeCells(22, 1, 23, 1);// Ʒ��
			sheetOne.mergeCells(22, 2, 22, 3);// �ƻ�
			sheetOne.mergeCells(23, 2, 23, 3);// ���
			sheetOne.mergeCells(24, 1, 24, 3);// �ύ����

		} catch (WriteException e) {
			e.printStackTrace();
		}

	}

	private void downloadFile(String fileName) {

		try {
			FacesContext context = FacesContext.getCurrentInstance();
			// ���ServletContext����
			ServletContext servletContext = (ServletContext) context
					.getExternalContext().getContext();
			// ȡ���ļ��ľ���·��
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
		
		row=4;//�������ݿ�ʼ��
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

}
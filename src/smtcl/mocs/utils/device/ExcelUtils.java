package smtcl.mocs.utils.device;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import smtcl.mocs.pojos.job.TEquipmenttypeInfo;

public class ExcelUtils {

	private static String sheetName = "data";

	  private HSSFWorkbook wb;

	  private HSSFSheet sheet;

	  private HSSFRow row;

	  private HSSFCell cell;

	  private HSSFFont font;

	  private HSSFCellStyle cellStyle;

	  private FileOutputStream fileOut;

	  public ExcelUtils() {
	    wb = new HSSFWorkbook();
	  }

	  /**
	   * @param excelName
	   *            excel���ơ�
	   * @param list
	   *            ���list�����ŵ��Ƕ������顣����Ԫ�ؿ���ת��Ϊ�ַ�����ʾ�ġ������������һ���Ӧ���ݿ���ļ��С�
	   * @param firstRowValue
	   */
	  public void outputExcel(String excelName, List list, String[] firstRowValue) {
	    try {
	      this.createSheet(firstRowValue);
	      this.setValueToRow(excelName, list);
	    } catch (Exception ex) {
	      System.out.print(ex);
	    }
	    // System.out.println("�ļ�����:" + excelName);
	    downloadFile(excelName);
	  }

	  public void outputExcel(String excelName, List list) {
	    try {
	      this.setValueToRow(excelName, list);
	    } catch (Exception e) {
	      // TODO: handle exception
	    }
	    downloadFile(excelName);
	  }

	  private void setValueToRow(String excelName, List list) {
	    // ���JSF�����Ļ���
	    FacesContext context = FacesContext.getCurrentInstance();
	    // ���ServletContext����
	    ServletContext servletContext = (ServletContext) context
	        .getExternalContext().getContext();
	    // ȡ���ļ��ľ���·��
//	    excelName = servletContext.getRealPath("/static/UploadFile") + "/" + excelName;
	    excelName = servletContext.getRealPath("/static/images") + "/" + excelName;
	    System.out.println("�����ļ���·���ǣ�" + excelName);
	    Object[] obj;
	    try {
//	      sheet = wb.createSheet(); //�õ�2������������ʱ�����Ҫ�������
	      for (int i = 0; i < list.size(); i++) {	    	  
	        row = sheet.createRow(i + 1);
	     //   obj = (Object[]) list.get(i);
	        Map<String,Object> map = (Map)list.get(i);
	        obj  = new Object[]{
	        map.get("id").toString(),
	        (String)map.get("equipmentType"),
	        (String)map.get("typecode"),
	        (String)map.get("norm"),
	        (String)map.get("cnc"),
	        (String)map.get("description")
	        };
	        
	        this.createCell(row, obj);
	      }
	     System.out.println("����----------->"+excelName); 
	      fileOut = new FileOutputStream(excelName);
	      wb.write(fileOut);

	    } catch (Exception ex) {
	      System.out.print("���ɱ�������:" + ex);
	    } finally {
	      try {
	        fileOut.flush();
	        fileOut.close();
	      } catch (Exception e) {
	        System.out.println("ExcelUtil.setValueToRow()");
	      }
	    }
	  }

	  private void createSheet(String[] firstRowValue) {
	    try {
	      sheet = wb.createSheet(ExcelUtils.sheetName);
	      row = sheet.createRow(0);
	      font = wb.createFont();
	      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	      cellStyle = wb.createCellStyle();
	      cellStyle.setFont(font);
	      for (int i = 0; i < firstRowValue.length; i++) {
	        cell = row.createCell((short) i);
	        cell.setCellStyle(cellStyle);
	        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
	        cell.setCellValue(firstRowValue[i]);
	      }
	    } catch (Exception ex) {
	      System.out.print(ex);
	    }
	  }

	  private void createCell(HSSFRow row, Object[] obj) {
	    try {
	      for (int i = 0; i < obj.length; i++) {
	        cell = row.createCell((short) i);
	        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
	        cell.setCellValue(obj[i].toString());
	      }
	    } catch (Exception ex) {
	      System.out.print(ex);
	    }
	  }

	  /**
	   * <p>
	   * ����˵���������ṩ���ļ��������ļ�,��֧�������ļ���
	   * </p>
	   * �˷�����yongtree���,ʵ���ļ����ɺ������
	   * 
	   * @param strfileName
	   *            String
	   * @return void
	   */
	  private static void downloadFile(String strfileName) {
	    try {
	      // ���JSF�����Ļ���
	      FacesContext context = FacesContext.getCurrentInstance();
	      // ���ServletContext����
	      ServletContext servletContext = (ServletContext) context
	          .getExternalContext().getContext();
	      // ȡ���ļ��ľ���·��
//	      String excelName = servletContext.getRealPath("/static/UploadFile") + "/"
//	          + strfileName;
	      String excelName = servletContext.getRealPath("/static/images") + "/"
		          + strfileName;
	      File exportFile = new File(excelName);
	      HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
	          .getCurrentInstance().getExternalContext().getResponse();
	      ServletOutputStream servletOutputStream = httpServletResponse
	          .getOutputStream();
	      httpServletResponse.setHeader("Content-disposition",
	          "attachment; filename=" + strfileName);
	      httpServletResponse.setContentLength((int) exportFile.length());
	      httpServletResponse.setContentType("application/x-download");
	      // httpServletResponse.setContentType("application/vnd.ms-excel");

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
	  
	  /**===================�豸����=======================================**/
	  
	  public void outputEquExcel(String excelName, List list, String[] firstRowValue) {
		    try {
		      this.createSheet(firstRowValue);
		      this.setEquValueToRow(excelName, list); 
		    } catch (Exception ex) {
		      System.out.print(ex);
		    }
		    // System.out.println("�ļ�����:" + excelName);
		    downloadFile(excelName);
		  }
	  
	  private void setEquValueToRow(String excelName, List list) {
		    // ���JSF�����Ļ���
		    FacesContext context = FacesContext.getCurrentInstance();
		    // ���ServletContext����
		    ServletContext servletContext = (ServletContext) context
		        .getExternalContext().getContext();
		    // ȡ���ļ��ľ���·��
		    excelName = servletContext.getRealPath("/static/images") + "/" + excelName;
		 //   excelName = servletContext.getRealPath("/static/UploadFile") + "/" + excelName; //����
		    System.out.println("�����ļ���·���ǣ�" + excelName);
		    Object[] obj;
		    try {
//		      sheet = wb.createSheet(); //�õ�2������������ʱ�����Ҫ�������
		      for (int i = 0; i < list.size(); i++) {	  
		        row = sheet.createRow(i + 1);
		     //   obj = (Object[]) list.get(i);
		        Map<String,Object> map = (Map)list.get(i);
		        obj  = new Object[]{
		        map.get("equId").toString(),
		        (String)map.get("equSerialNo"),
		        (String)map.get("equipmentType"),
		        (String)map.get("equName"),
		        null==map.get("norm")?"":map.get("norm").toString(),
		        null==map.get("outfacNo")?"":map.get("outfacNo").toString(),	
		        null==map.get("manufacturer")?"":map.get("manufacturer").toString(),
		        null==map.get("checktime")?"":map.get("checktime").toString(),
		        null==map.get("xAxis")?"":map.get("xAxis").toString(),
		        null==map.get("yAxis")?"":map.get("yAxis").toString(),
		        null==map.get("ipAddress")?"":map.get("ipAddress").toString(),
		        null==map.get("equDesc")?"":map.get("equDesc").toString(),
		        null==map.get("peopleName")?"":map.get("peopleName").toString(),

		        };
		        this.createCell(row, obj);
		      }
		     System.out.println("����----------->"+excelName); 
		      fileOut = new FileOutputStream(excelName);
		      wb.write(fileOut);

		    } catch (Exception ex) {
		      System.out.print("���ɱ�������:" + ex);
		    } finally {
		      try {
		        fileOut.flush();
		        fileOut.close();
		      } catch (Exception e) {
		        System.out.println("ExcelUtil.setEquValueToRow()");
		      }
		    }
		  } 

}

package smtcl.mocs.utils.device;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class FileUtils {
	/*
	 * ��ȡ�ļ�
	 */
	 public static String readFile(String fileName) {  
		 String FileContent = ""; // �ļ��ܳ��Ļ�����ʹ��StringBuffer 
		    try { 
		        FileInputStream fis = new FileInputStream(fileName); 
		        InputStreamReader isr = new InputStreamReader(fis, "UTF-8"); 
		        BufferedReader br = new BufferedReader(isr); 
		        String line = null; 
		        while ((line = br.readLine()) != null) { 
		            FileContent += line; 
		            FileContent += "\n"; // ���ϻ��з� 
		        } 
		    } catch (Exception e) { 
		        e.printStackTrace(); 
		    }
			return FileContent;
	     }  
	/*
	 *д�ļ�
	 */
	public static boolean write(String fileName, String fileContent) {
	    boolean result = false;
	    File f = new File(fileName);
	    try {
	        FileOutputStream fs = new FileOutputStream(f);
	        
	        OutputStreamWriter osw  =   new  OutputStreamWriter(fs, "UTF-8"); 
            osw.write(fileContent); 
            osw.flush(); 
            osw.close();
            fs.close();
	        result = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	/*
	 * ׷�����ݵ��ļ�
	 */
	public static boolean append(String fileName, String fileContent) {
	    boolean result = false;
	    File f = new File(fileName);
	    try {
	        if (f.exists()) {
	            FileInputStream fsIn = new FileInputStream(f);
	            byte[] bIn = new byte[fsIn.available()];
	            fsIn.read(bIn);
	            String oldFileContent = new String(bIn);
	            //System.out.println("������:" + oldFileContent);
	            fsIn.close();
	            if (!oldFileContent.equalsIgnoreCase("")) {
	                fileContent = oldFileContent + "\r\n" + fileContent;
	                //System.out.println("������:" + fileContent);
	            }
	        }

	        FileOutputStream fs = new FileOutputStream(f);
	        byte[] b = fileContent.getBytes();
	        fs.write(b);
	        fs.flush();
	        fs.close();
	        result = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	/** 
     * ׷���ļ���ʹ��FileOutputStream���ڹ���FileOutputStreamʱ���ѵڶ���������Ϊtrue 
     *  
     * @param fileName 
     * @param content 
     */  
    public static void method1(String file, String conent) {  
        BufferedWriter out = null;  
        try {  
            out = new BufferedWriter(new OutputStreamWriter(  
                    new FileOutputStream(file, true)));  
            out.write(conent);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                out.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /** 
     * ׷���ļ���ʹ��FileWriter 
     *  
     * @param fileName 
     * @param content 
     */  
    public static void method2(String fileName, String content) {  
        try {  
            // ��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�  
            FileWriter writer = new FileWriter(fileName, true);  
            writer.write(content);  
            writer.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * ׷���ļ���ʹ��RandomAccessFile 
     *  
     * @param fileName 
     *            �ļ��� 
     * @param content 
     *            ׷�ӵ����� 
     */  
    public static void method3(String fileName, String content) {  
        try {  
            // ��һ����������ļ���������д��ʽ  
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");  
            // �ļ����ȣ��ֽ���  
            long fileLength = randomFile.length();  
            // ��д�ļ�ָ���Ƶ��ļ�β��  
            randomFile.seek(fileLength);  
            randomFile.writeBytes(content);  
            randomFile.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}

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
	 * 读取文件
	 */
	 public static String readFile(String fileName) {  
		 String FileContent = ""; // 文件很长的话建议使用StringBuffer 
		    try { 
		        FileInputStream fis = new FileInputStream(fileName); 
		        InputStreamReader isr = new InputStreamReader(fis, "UTF-8"); 
		        BufferedReader br = new BufferedReader(isr); 
		        String line = null; 
		        while ((line = br.readLine()) != null) { 
		            FileContent += line; 
		            FileContent += "\n"; // 补上换行符 
		        } 
		    } catch (Exception e) { 
		        e.printStackTrace(); 
		    }
			return FileContent;
	     }  
	/*
	 *写文件
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
	 * 追加内容到文件
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
	            //System.out.println("旧内容:" + oldFileContent);
	            fsIn.close();
	            if (!oldFileContent.equalsIgnoreCase("")) {
	                fileContent = oldFileContent + "\r\n" + fileContent;
	                //System.out.println("新内容:" + fileContent);
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
     * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true 
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
     * 追加文件：使用FileWriter 
     *  
     * @param fileName 
     * @param content 
     */  
    public static void method2(String fileName, String content) {  
        try {  
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件  
            FileWriter writer = new FileWriter(fileName, true);  
            writer.write(content);  
            writer.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 追加文件：使用RandomAccessFile 
     *  
     * @param fileName 
     *            文件名 
     * @param content 
     *            追加的内容 
     */  
    public static void method3(String fileName, String content) {  
        try {  
            // 打开一个随机访问文件流，按读写方式  
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");  
            // 文件长度，字节数  
            long fileLength = randomFile.length();  
            // 将写文件指针移到文件尾。  
            randomFile.seek(fileLength);  
            randomFile.writeBytes(content);  
            randomFile.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}

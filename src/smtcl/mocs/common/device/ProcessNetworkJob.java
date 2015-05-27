package smtcl.mocs.common.device;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import smtcl.mocs.utils.device.Constants;

/**
 * ��̬��������
 * @����ʱ�� 2013-8-27
 * @���� yutao
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 * 
 */
public class ProcessNetworkJob {
	public void run() {  
	Runtime runtime = Runtime.getRuntime();
	try {
		Process process = runtime.exec("ping " + "10.10.10.13");
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
			// System.out.println("����ֵΪ:"+line);
		}
		is.close();
		isr.close();
		br.close();

		if (null != sb && !sb.toString().equals("")) {
			if (sb.toString().indexOf("TTL") > 0) {
				// ���糩ͨ
				Constants.SSO_CONTROL="1";
				LogHelper.log("��������", getCurrentTime());
			} else {
				// ���粻��ͨ
				Constants.SSO_CONTROL="0";
				LogHelper.log("��������쳣", getCurrentTime());
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	public String getCurrentTime() {		  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	    String time = sdf.format(new Date());
		return time;
		
	}
}

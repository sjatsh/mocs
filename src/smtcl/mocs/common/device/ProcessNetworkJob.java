package smtcl.mocs.common.device;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import smtcl.mocs.utils.device.Constants;

/**
 * 动态链接网络
 * @创建时间 2013-8-27
 * @作者 yutao
 * @修改者： 
 * @修改日期： 
 * @修改说明
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
			// System.out.println("返回值为:"+line);
		}
		is.close();
		isr.close();
		br.close();

		if (null != sb && !sb.toString().equals("")) {
			if (sb.toString().indexOf("TTL") > 0) {
				// 网络畅通
				Constants.SSO_CONTROL="1";
				LogHelper.log("网络正常", getCurrentTime());
			} else {
				// 网络不畅通
				Constants.SSO_CONTROL="0";
				LogHelper.log("网络出现异常", getCurrentTime());
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

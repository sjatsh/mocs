package smtcl.mocs.utils.device;

/**
 * @author 
 * @功能：持续检测网络是否连通
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetworkState implements Runnable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NetworkState ns = new NetworkState();
		new Thread(ns).start();// 启动线程
	}

	// 判断网络状态
	public void isConnect() {
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
				String logString = "";
				if (sb.toString().indexOf("TTL") > 0) {
					// 网络畅通
					System.err.println("网络正常，时间 ");
				} else {
					// 网络不畅通
					System.err.println("网络断开，时间 ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			this.isConnect();
			try {
				// 每隔3秒钟测试一次网络是否连通
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

package smtcl.mocs.utils.device;

/**
 * @author 
 * @���ܣ�������������Ƿ���ͨ
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetworkState implements Runnable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NetworkState ns = new NetworkState();
		new Thread(ns).start();// �����߳�
	}

	// �ж�����״̬
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
				// System.out.println("����ֵΪ:"+line);
			}
			is.close();
			isr.close();
			br.close();

			if (null != sb && !sb.toString().equals("")) {
				String logString = "";
				if (sb.toString().indexOf("TTL") > 0) {
					// ���糩ͨ
					System.err.println("����������ʱ�� ");
				} else {
					// ���粻��ͨ
					System.err.println("����Ͽ���ʱ�� ");
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
				// ÿ��3���Ӳ���һ�������Ƿ���ͨ
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

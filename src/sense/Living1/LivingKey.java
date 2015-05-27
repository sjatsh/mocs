package sense.Living1;

import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.DESUtil;


/**
 * 加密狗验证
 * @author YT
 *
 */
public class LivingKey {
	
  public static String getKey(){
	   //捕捉异常
	try {
			Living1 liv1 = new Living1();
			int res;
			int[] handle = new int[1];
			LIV_software_info sinfo = new LIV_software_info();
			LIV_hardware_info hinfo = new LIV_hardware_info();
	
			// 取软件信息
			res = liv1.LIV_get_software_info(sinfo);
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("取软件信息失败， 错误代码是： " + res);
				return null;
			}
			//System.out.println("取软件信息成功. software version : " + sinfo.version);
	
			// 打开设备
			res = liv1.LIV_open(Integer.valueOf(DESUtil.getDesString(Constants.KEY_COMPANY_NO)), 0, handle); 
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("打开设备失败， 错误代码是： " + res);
				return null;
			}
			//System.out.println("打开设备成功");
			
			// 取硬件信息
			res = liv1.LIV_get_hardware_info(handle[0],hinfo);
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("取硬件信息失败， 错误代码是： " + res);
				return null;
			}
			//System.out.println("取硬件信息成功 ");
			
			// 校验普通用户密码
			res = liv1.LIV_passwd(handle[0], 1, (DESUtil.getDesString(Constants.KEY_ACCOUNT)).getBytes());
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("校验普通用户密码失败， 错误代码是： " + res);
				//LogHelper.log("校验普通用户密码失败", "校验普通用户密码失败");
				return null;
			}
			//System.out.println("校验普通用户密码成功 ");
		
			// 读区块0
			byte[] outdata = new byte[512];
			
			res = liv1.LIV_read(handle[0], 0, outdata);
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("读区块0失败， 错误代码是： " + res);
				return null;
			}
			//System.out.println("读区块0成功 ");
			// 解密数据
			byte[] plaintext= new byte[512];	
			res = liv1.LIV_decrypt(handle[0], outdata, plaintext);
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("解密数据失败， 错误代码是： " + res);
				return null;
			}
			//System.out.println("解密数据成功 ");
			String key=new String(plaintext);
			
			// 关闭设备
			res = liv1.LIV_close(handle[0]);
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("关闭设备失败， 错误代码是： " + res);
				return null;
			}
			//System.out.println("关闭设备成功");
			return key.trim();
	   }catch(Exception e)
	     {
		    e.printStackTrace();		   
		    return null;
	     }
	}
}

class Living1 {
	// 错误码
	public static int LIV_SUCCESS = 0; // 成功
	public static int LIV_OPEN_DEVICE_FAILED = 1; // 打开设备失败
	public static int LIV_FIND_DEVICE_FAILED = 2; // 未找到符合条件的设备
	public static int LIV_INVALID_PARAMETER = 3; // 参数错误
	public static int LIV_INVALID_BLOCK_NUMBER = 4; // 块号错误
	public static int LIV_HARDWARE_COMMUNICATE_ERROR = 5; // 与硬件通信错误
	public static int LIV_INVALID_PASSWORD = 6; // 密码错误
	public static int LIV_ACCESS_DENIED = 7; // 没有权限
	public static int LIV_ALREADY_OPENED = 8; // 设备已经打开
	public static int LIV_ALLOCATE_MEMORY_FAILED = 9; // 内存分配失败
	public static int LIV_INVALID_UPDATE_PACKAGE = 10; // 不合法的升级包
	public static int LIV_SYN_ERROR = 11; // 线程同步错误
	public static int LIV_OTHER_ERROR = 12; // 其它未知异常、错误

	// API 函数
	public native int LIV_open(int vendor, int index, int[] handle);

	public native int LIV_close(int handle);

	public native int LIV_passwd(int handle, int type, byte[] passwd);

	public native int LIV_read(int handle, int block, byte[] buffer);

	public native int LIV_write(int handle, int block, byte[] buffer);

	public native int LIV_encrypt(int handle, byte[] plaintext,
			byte[] ciphertext);

	public native int LIV_decrypt(int handle, byte[] ciphertext,
			byte[] plaintext);

	public native int LIV_set_passwd(int handle, int type, byte[] newpasswd,
			int retries);

	public native int LIV_change_passwd(int handle, int type, byte[] oldpasswd,
			byte[] newpasswd);

	public native int LIV_get_hardware_info(int handle, LIV_hardware_info info);

	public native int LIV_get_software_info(LIV_software_info info);

	public native int LIV_hmac(int handle, byte[] text, int textlen,
			byte[] digest);

	public native int LIV_hmac_software(byte[] text, int textlen, byte[] key,
			byte[] digest);

	static {
		try {
			System.loadLibrary("living1_java_pkg");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Cannot find library living1_java_pkg.dll");
			e.printStackTrace();
		}
	}

}

class LIV_software_info {
	public int version;
                public int reservation;
}

class LIV_hardware_info {
	public int developerNumber;
	public byte[] serialNumber;
	public int setDate;
	public int reservation;
	LIV_hardware_info() {
		serialNumber = new byte[8];
	}
}

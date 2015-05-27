package sense.Living1;

import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.DESUtil;


/**
 * ���ܹ���֤
 * @author YT
 *
 */
public class LivingKey {
	
  public static String getKey(){
	   //��׽�쳣
	try {
			Living1 liv1 = new Living1();
			int res;
			int[] handle = new int[1];
			LIV_software_info sinfo = new LIV_software_info();
			LIV_hardware_info hinfo = new LIV_hardware_info();
	
			// ȡ�����Ϣ
			res = liv1.LIV_get_software_info(sinfo);
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("ȡ�����Ϣʧ�ܣ� ��������ǣ� " + res);
				return null;
			}
			//System.out.println("ȡ�����Ϣ�ɹ�. software version : " + sinfo.version);
	
			// ���豸
			res = liv1.LIV_open(Integer.valueOf(DESUtil.getDesString(Constants.KEY_COMPANY_NO)), 0, handle); 
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("���豸ʧ�ܣ� ��������ǣ� " + res);
				return null;
			}
			//System.out.println("���豸�ɹ�");
			
			// ȡӲ����Ϣ
			res = liv1.LIV_get_hardware_info(handle[0],hinfo);
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("ȡӲ����Ϣʧ�ܣ� ��������ǣ� " + res);
				return null;
			}
			//System.out.println("ȡӲ����Ϣ�ɹ� ");
			
			// У����ͨ�û�����
			res = liv1.LIV_passwd(handle[0], 1, (DESUtil.getDesString(Constants.KEY_ACCOUNT)).getBytes());
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("У����ͨ�û�����ʧ�ܣ� ��������ǣ� " + res);
				//LogHelper.log("У����ͨ�û�����ʧ��", "У����ͨ�û�����ʧ��");
				return null;
			}
			//System.out.println("У����ͨ�û�����ɹ� ");
		
			// ������0
			byte[] outdata = new byte[512];
			
			res = liv1.LIV_read(handle[0], 0, outdata);
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("������0ʧ�ܣ� ��������ǣ� " + res);
				return null;
			}
			//System.out.println("������0�ɹ� ");
			// ��������
			byte[] plaintext= new byte[512];	
			res = liv1.LIV_decrypt(handle[0], outdata, plaintext);
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("��������ʧ�ܣ� ��������ǣ� " + res);
				return null;
			}
			//System.out.println("�������ݳɹ� ");
			String key=new String(plaintext);
			
			// �ر��豸
			res = liv1.LIV_close(handle[0]);
			if (res != Living1.LIV_SUCCESS) {
				//System.out.print("�ر��豸ʧ�ܣ� ��������ǣ� " + res);
				return null;
			}
			//System.out.println("�ر��豸�ɹ�");
			return key.trim();
	   }catch(Exception e)
	     {
		    e.printStackTrace();		   
		    return null;
	     }
	}
}

class Living1 {
	// ������
	public static int LIV_SUCCESS = 0; // �ɹ�
	public static int LIV_OPEN_DEVICE_FAILED = 1; // ���豸ʧ��
	public static int LIV_FIND_DEVICE_FAILED = 2; // δ�ҵ������������豸
	public static int LIV_INVALID_PARAMETER = 3; // ��������
	public static int LIV_INVALID_BLOCK_NUMBER = 4; // ��Ŵ���
	public static int LIV_HARDWARE_COMMUNICATE_ERROR = 5; // ��Ӳ��ͨ�Ŵ���
	public static int LIV_INVALID_PASSWORD = 6; // �������
	public static int LIV_ACCESS_DENIED = 7; // û��Ȩ��
	public static int LIV_ALREADY_OPENED = 8; // �豸�Ѿ���
	public static int LIV_ALLOCATE_MEMORY_FAILED = 9; // �ڴ����ʧ��
	public static int LIV_INVALID_UPDATE_PACKAGE = 10; // ���Ϸ���������
	public static int LIV_SYN_ERROR = 11; // �߳�ͬ������
	public static int LIV_OTHER_ERROR = 12; // ����δ֪�쳣������

	// API ����
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

package smtcl.mocs.model;

public class MaterialsModel {
	/**
	 * id��ʶ
	 */
	private String id;
	/**
	 * ���ϱ��
	 */
	private String wlNo;
	/**
	 * ��������
	 */
	private String wlName;
	/**
	 * ���Ϲ��
	 */
	private String wlnorm;
	
	/**
	 * ��������
	 */
	private String wlType;
	
	/**
	 * ��������
	 */
	private String wlNumber;
	
	private String did="";

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWlNo() {
		return wlNo;
	}

	public void setWlNo(String wlNo) {
		this.wlNo = wlNo;
	}

	public String getWlName() {
		return wlName;
	}

	public void setWlName(String wlName) {
		this.wlName = wlName;
	}

	public String getWlnorm() {
		return wlnorm;
	}

	public void setWlnorm(String wlnorm) {
		this.wlnorm = wlnorm;
	}

	public String getWlType() {
		return wlType;
	}

	public void setWlType(String wlType) {
		this.wlType = wlType;
	}

	public String getWlNumber() {
		return wlNumber;
	}

	public void setWlNumber(String wlNumber) {
		this.wlNumber = wlNumber;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}
	
	
}

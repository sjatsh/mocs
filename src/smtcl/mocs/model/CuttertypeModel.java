package smtcl.mocs.model;

public class CuttertypeModel {
	
	private String id; //��������id
	private String name; //������������
	private String no; //�������ͱ��
	private String norm;//���ߴ�
	private String remark;//��ע
	private String cutterclass;//�������
	private String requirementNum;
	private String did = "";
	private String nodeid;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNorm() {
		return norm;
	}
	public void setNorm(String norm) {
		this.norm = norm;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCutterclass() {
		return cutterclass;
	}
	public void setCutterclass(String cutterclass) {
		this.cutterclass = cutterclass;
	}

	public String getRequirementNum() {
		return requirementNum;
	}
	public void setRequirementNum(String requirementNum) {
		this.requirementNum = requirementNum;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	
}

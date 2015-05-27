package smtcl.mocs.pojos.job;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TMemberInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_member_info")
public class TMemberInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String no;
	private String idcard;
	private String groupno;
	private Double salary;
	private Integer age;
	private Date birthday;
	private String sex;
	private String marriage;
	private String education;
	private String domicileOfOrigin;
	private String address;
	private String partyAffiliation;
	private String phoneNumber;
	private String nodeid;
	private String memo;
	private Long teamid;
	private Long positionid;
	private String email;
	private Integer status;

	// Constructors

	/** default constructor */
	public TMemberInfo() {
		
	}

	/** minimal constructor */
	public TMemberInfo(Long id) {
		
		this.id = id;
	}

	/** full constructor */
	public TMemberInfo(Long id, String name, String no, String idcard,
			String groupno, Double salary, Integer age, Date birthday,
			String sex, String marriage, String education,
			String domicileOfOrigin, String address, String partyAffiliation,
			String phoneNumber, String nodeid, String memo, Long teamid,
			Long positionid,Integer status) {
		this.id = id;
		this.name = name;
		this.no = no;
		this.idcard = idcard;
		this.groupno = groupno;
		this.salary = salary;
		this.age = age;
		this.birthday = birthday;
		this.sex = sex;
		this.marriage = marriage;
		this.education = education;
		this.domicileOfOrigin = domicileOfOrigin;
		this.address = address;
		this.partyAffiliation = partyAffiliation;
		this.phoneNumber = phoneNumber;
		this.nodeid = nodeid;
		this.memo = memo;
		this.teamid = teamid;
		this.positionid = positionid;
		this.email=email;
		this.status=status;
	}



	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "no", length = 50)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "idcard", length = 20)
	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "groupno", length = 20)
	public String getGroupno() {
		return this.groupno;
	}

	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}

	@Column(name = "salary")
	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", length = 0)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "sex", length = 4)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "marriage", length = 4)
	public String getMarriage() {
		return this.marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	@Column(name = "education", length = 10)
	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "domicile_of_origin", length = 20)
	public String getDomicileOfOrigin() {
		return this.domicileOfOrigin;
	}

	public void setDomicileOfOrigin(String domicileOfOrigin) {
		this.domicileOfOrigin = domicileOfOrigin;
	}

	@Column(name = "address", length = 50)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "party_affiliation", length = 20)
	public String getPartyAffiliation() {
		return this.partyAffiliation;
	}

	public void setPartyAffiliation(String partyAffiliation) {
		this.partyAffiliation = partyAffiliation;
	}

	@Column(name = "phone_number")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "nodeid", length = 50)
	public String getNodeid() {
		return this.nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	@Column(name = "memo", length = 100)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "teamid")
	public Long getTeamid() {
		return this.teamid;
	}

	public void setTeamid(Long teamid) {
		this.teamid = teamid;
	}

	@Column(name = "positionid")
	public Long getPositionid() {
		return this.positionid;
	}

	public void setPositionid(Long positionid) {
		this.positionid = positionid;
	}
	
	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
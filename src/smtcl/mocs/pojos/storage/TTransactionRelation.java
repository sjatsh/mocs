package smtcl.mocs.pojos.storage;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * KC_301 �����ϵ��
 */
@Entity
@Table(name = "t_transaction_relation")
public class TTransactionRelation implements java.io.Serializable {

	// Fields

	private Integer id;//��ˮ��ID
	private String tansType;//��������
	private String tansFrom;//��Դ����
	private String tansActivity;//����
	private Set<TTransaction> TTransactions = new HashSet<TTransaction>(0);

	// Constructors

	/** default constructor */
	public TTransactionRelation() {
	}

	/** full constructor */
	public TTransactionRelation(String tansType, String tansFrom,
			String tansActivity, Set<TTransaction> TTransactions) {
		this.tansType = tansType;
		this.tansFrom = tansFrom;
		this.tansActivity = tansActivity;
		this.TTransactions = TTransactions;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "tans_type", length = 30)
	public String getTansType() {
		return this.tansType;
	}

	public void setTansType(String tansType) {
		this.tansType = tansType;
	}

	@Column(name = "tans_from", length = 30)
	public String getTansFrom() {
		return this.tansFrom;
	}

	public void setTansFrom(String tansFrom) {
		this.tansFrom = tansFrom;
	}

	@Column(name = "tans_activity", length = 30)
	public String getTansActivity() {
		return this.tansActivity;
	}

	public void setTansActivity(String tansActivity) {
		this.tansActivity = tansActivity;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TTransactionRelation")
	public Set<TTransaction> getTTransactions() {
		return this.TTransactions;
	}

	public void setTTransactions(Set<TTransaction> TTransactions) {
		this.TTransactions = TTransactions;
	}

}
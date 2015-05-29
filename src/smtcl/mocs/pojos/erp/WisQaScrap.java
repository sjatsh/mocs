package smtcl.mocs.pojos.erp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WisQaScrap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WIS_QA_SCRAP" )
public class WisQaScrap implements java.io.Serializable {

	// Fields

	private WisQaScrapId id;

	// Constructors

	/** default constructor */
	public WisQaScrap() {
	}

	/** full constructor */
	public WisQaScrap(WisQaScrapId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "entityName", column = @Column(name = "ENTITY_NAME", length = 240)),
			@AttributeOverride(name = "itemCode", column = @Column(name = "ITEM_CODE", length = 240)),
			@AttributeOverride(name = "itemDesc", column = @Column(name = "ITEM_DESC", length = 240)),
			@AttributeOverride(name = "tzdCode", column = @Column(name = "TZD_CODE", length = 240)),
			@AttributeOverride(name = "invoiceDate", column = @Column(name = "INVOICE_DATE", length = 240)),
			@AttributeOverride(name = "toOperationNum", column = @Column(name = "TO_OPERATION_NUM", length = 240)),
			@AttributeOverride(name = "zrOperationNum", column = @Column(name = "ZR_OPERATION_NUM", length = 240)),
			@AttributeOverride(name = "qaResult", column = @Column(name = "QA_RESULT", length = 240)),
			@AttributeOverride(name = "quantity", column = @Column(name = "QUANTITY", length = 240)),
			@AttributeOverride(name = "hour", column = @Column(name = "HOUR", length = 240)),
			@AttributeOverride(name = "outsourcingFee", column = @Column(name = "OUTSOURCING_FEE", length = 240)),
			@AttributeOverride(name = "reson", column = @Column(name = "RESON", length = 240)),
			@AttributeOverride(name = "fpType", column = @Column(name = "FP_TYPE", length = 240)),
			@AttributeOverride(name = "responser", column = @Column(name = "RESPONSER", length = 240)),
			@AttributeOverride(name = "vendor", column = @Column(name = "VENDOR", length = 240)),
			@AttributeOverride(name = "gd", column = @Column(name = "GD", length = 240)),
			@AttributeOverride(name = "checker", column = @Column(name = "CHECKER", length = 240)),
			@AttributeOverride(name = "uploadDate", column = @Column(name = "UPLOAD_DATE", length = 7)),
			@AttributeOverride(name = "organizationId", column = @Column(name = "ORGANIZATION_ID", precision = 22, scale = 0)),
			@AttributeOverride(name = "flag", column = @Column(name = "FLAG", precision = 22, scale = 0)),
			@AttributeOverride(name = "reqQaId", column = @Column(name = "req_qa_id", length = 240)),
			@AttributeOverride(name = "a1", column = @Column(name = "A1", length = 240)),
			@AttributeOverride(name = "a2", column = @Column(name = "A2", length = 240)),
			@AttributeOverride(name = "a3", column = @Column(name = "A3", length = 240)),
			@AttributeOverride(name = "a4", column = @Column(name = "A4", length = 240)),
			@AttributeOverride(name = "a5", column = @Column(name = "A5", length = 240)) })
	public WisQaScrapId getId() {
		return this.id;
	}

	public void setId(WisQaScrapId id) {
		this.id = id;
	}

}
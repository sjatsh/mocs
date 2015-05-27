package smtcl.mocs.pojos.job;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TEquipmentCostInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_equipment_cost_info")
public class TEquipmentCostInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String  equSerialNo;
	private Double equPrice;
	private Double periodDepreciation;
	private Double processCost;
	private Double idleCost;
	private Double prepareCost;
	private Double dryRunningCost;
	private Double cuttingCost;
	private Double processAccessoriesCost;
	private Double prepareAccessoriesCost;

	// Constructors

	/** default constructor */
	public TEquipmentCostInfo() {
	}

	/** minimal constructor */
	public TEquipmentCostInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TEquipmentCostInfo(Long id, String equSerialNo, Double equPrice,
			Double periodDepreciation, Double processCost, Double idleCost,
			Double prepareCost, Double dryRunningCost, Double cuttingCost,
			Double processAccessoriesCost, Double prepareAccessoriesCost) {
		this.id = id;
		this.equSerialNo = equSerialNo;
		this.equPrice = equPrice;
		this.periodDepreciation = periodDepreciation;
		this.processCost = processCost;
		this.idleCost = idleCost;
		this.prepareCost = prepareCost;
		this.dryRunningCost = dryRunningCost;
		this.cuttingCost = cuttingCost;
		this.processAccessoriesCost = processAccessoriesCost;
		this.prepareAccessoriesCost = prepareAccessoriesCost;
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

	@Column(name = "equ_serialNo", length = 30)
	public String getEquSerialNo() {
		return equSerialNo;
	}

	public void setEquSerialNo(String equSerialNo) {
		this.equSerialNo = equSerialNo;
	}

	@Column(name = "equ_price", precision = 10, scale = 0)
	public Double getEquPrice() {
		return this.equPrice;
	}

	public void setEquPrice(Double equPrice) {
		this.equPrice = equPrice;
	}

	@Column(name = "period_depreciation", precision = 10, scale = 0)
	public Double getPeriodDepreciation() {
		return this.periodDepreciation;
	}

	public void setPeriodDepreciation(Double periodDepreciation) {
		this.periodDepreciation = periodDepreciation;
	}

	@Column(name = "process_cost", precision = 10, scale = 0)
	public Double getProcessCost() {
		return this.processCost;
	}

	public void setProcessCost(Double processCost) {
		this.processCost = processCost;
	}

	@Column(name = "idle_cost", precision = 10, scale = 0)
	public Double getIdleCost() {
		return this.idleCost;
	}

	public void setIdleCost(Double idleCost) {
		this.idleCost = idleCost;
	}

	@Column(name = "prepare_cost", precision = 10, scale = 0)
	public Double getPrepareCost() {
		return this.prepareCost;
	}

	public void setPrepareCost(Double prepareCost) {
		this.prepareCost = prepareCost;
	}

	@Column(name = "dryRunning_cost", precision = 10, scale = 0)
	public Double getDryRunningCost() {
		return this.dryRunningCost;
	}

	public void setDryRunningCost(Double dryRunningCost) {
		this.dryRunningCost = dryRunningCost;
	}

	@Column(name = "cutting_cost", precision = 10, scale = 0)
	public Double getCuttingCost() {
		return this.cuttingCost;
	}

	public void setCuttingCost(Double cuttingCost) {
		this.cuttingCost = cuttingCost;
	}

	@Column(name = "process_accessories_cost", precision = 10, scale = 0)
	public Double getProcessAccessoriesCost() {
		return this.processAccessoriesCost;
	}

	public void setProcessAccessoriesCost(Double processAccessoriesCost) {
		this.processAccessoriesCost = processAccessoriesCost;
	}

	@Column(name = "prepare_accessories_cost", precision = 10, scale = 0)
	public Double getPrepareAccessoriesCost() {
		return this.prepareAccessoriesCost;
	}

	public void setPrepareAccessoriesCost(Double prepareAccessoriesCost) {
		this.prepareAccessoriesCost = prepareAccessoriesCost;
	}

}
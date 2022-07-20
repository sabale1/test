package in.ecgc.smile.erp.accounts.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class SectionOfTds {

	@NotNull(message = "srNo Cannot be Empty")
	private int srNo;

	@NotNull(message = "tdsSection Cannot be Empty")
	private String tdsSection;

	@NotNull(message = "particulars Cannot be Empty")
	private String particulars;

	@NotNull(message = "subParticular Cannot be Empty")
	private String subParticular;

	private double thresholdLimit;

	private double tdsRate;

	private Date createdDt;

	private String createdBy;

	private Date lastUpdatedDt;

	private String lastUpdatedBy;

	@NotNull(message = "metaStatus Cannot be Empty")
	private Boolean metaStatus;

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setTdsSection(String tdsSection) {
		this.tdsSection = tdsSection;
	}

	public String getTdsSection() {
		return tdsSection;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setSubParticular(String subParticular) {
		this.subParticular = subParticular;
	}

	public String getSubParticular() {
		return subParticular;
	}

	public void setThresholdLimit(double thresholdLimit) {
		this.thresholdLimit = thresholdLimit;
	}

	public double getThresholdLimit() {
		return thresholdLimit;
	}

	public void setTdsRate(double tdsRate) {
		this.tdsRate = tdsRate;
	}

	public double getTdsRate() {
		return tdsRate;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedDt(Date lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	public Date getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setMetaStatus(Boolean metaStatus) {
		this.metaStatus = metaStatus;
	}

	public Boolean getMetaStatus() {
		return metaStatus;
	}

	@Override
	public String toString() {
		return "Tds [srNo=" + srNo + ",tdsSection=" + tdsSection + ",particulars=" + particulars + ",subParticular="
				+ subParticular + ",thresholdLimit=" + thresholdLimit + ",tdsRate=" + tdsRate + ",createdDt="
				+ createdDt + ",createdBy=" + createdBy + ",lastUpdatedDt=" + lastUpdatedDt + ",lastUpdatedBy="
				+ lastUpdatedBy + ",metaStatus=" + metaStatus + "]";
	}
}

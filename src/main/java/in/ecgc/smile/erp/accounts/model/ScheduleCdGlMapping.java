package in.ecgc.smile.erp.accounts.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
//import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class ScheduleCdGlMapping {

	private int seqNo;

	@NotNull(message = "mainglCd Cannot be Empty")
	private String mainglCd;

	@NotNull(message = "subglCd Cannot be Empty")
	private String subglCd;

	@NotNull(message = "scheduleCd Cannot be Empty")
	private String scheduleCd;

	@NotNull(message = "scheduleItemCd Cannot be Empty")
	private String scheduleItemCd;

	@Size(min = 0, max = 50, message = "Please provide createdBy between 0 to 50 characters")
	private String createdBy;

	@Size(min = 0, max = 50, message = "Please provide lastUpdatedBy between 0 to 50 characters")
	private String lastUpdatedBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastUpdatedDt;

	@Size(min = 0, max = 300, message = "Please provide remarks between 0 to 300 characters")
	private String remarks;

	@Size(min = 0, max = 1, message = "Please provide delFlag between 0 to 1 characters")
	private String delFlag;

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setMainglCd(String mainglCd) {
		this.mainglCd = mainglCd;
	}

	public String getMainglCd() {
		return mainglCd;
	}

	public void setSubglCd(String subglCd) {
		this.subglCd = subglCd;
	}

	public String getSubglCd() {
		return subglCd;
	}

	public void setScheduleCd(String scheduleCd) {
		this.scheduleCd = scheduleCd;
	}

	public String getScheduleCd() {
		return scheduleCd;
	}

	public void setScheduleItemCd(String scheduleItemCd) {
		this.scheduleItemCd = scheduleItemCd;
	}

	public String getScheduleItemCd() {
		return scheduleItemCd;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setLastUpdatedDt(Date lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	public Date getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getDelFlag() {
		return delFlag;
	}

	@Override
	public String toString() {
		return "ScheduleCdGlMapping [seqNo=" + seqNo + ",mainglCd=" + mainglCd + ",subglCd=" + subglCd + ",scheduleCd="
				+ scheduleCd + ",scheduleItemCd=" + scheduleItemCd + ",createdBy=" + createdBy + ",lastUpdatedBy="
				+ lastUpdatedBy + ",createdDt=" + createdDt + ",lastUpdatedDt=" + lastUpdatedDt + ",remarks=" + remarks
				+ ",delFlag=" + delFlag + "]";
	}
}

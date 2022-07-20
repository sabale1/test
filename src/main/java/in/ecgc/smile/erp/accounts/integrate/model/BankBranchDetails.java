package in.ecgc.smile.erp.accounts.integrate.model;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class BankBranchDetails {

	  private String bankBranchCode;
	    private String ifscCode;
	    private String bankCode;
	    private String bankName;
	    private String bankBranchName;
//	    @JsonDeserialize(using = CustomDateDeSerializer.class)
	    private Date bankBranchEffectiveFrom;
	    private String bankBranchOldCode;
	    private String bankBranchAreaCode;
	    private String bankBranchSatelliteCenterCode;
	    private String bankBranchType;
	    private String bankBranchRegionZone;
//	    @JsonDeserialize(using = CustomDateDeSerializer.class)
	    private Date dateOfAssociation;
	    private String logicalLocationCode;
	    private String bankBranchAddrLineOne;
	    private String bankBranchAddrLineTwo;
	    private String bankBranchAddrLineThree;
	    private String bankBranchAddrLineFour;
	    private String bankBranchCity;
	    private String bankBranchState;
	    private String bankBranchCountry;
	    private Integer bankBranchPincode;
	    private String bankBranchPrimaryContactNumber;
	    private String bankBranchSecondaryContactNumber;
	    private String bankBranchEmailId;
	    private String bankBranchHname;
	    private String bankBranchHaddrLineOne;
	    private String bankBranchHaddrLineTwo;
	    private String bankBranchHaddrLineThree;
	    private String bankBranchHaddrLineFour;
	    private String bankBranchHcity;
	    private String bankBranchHstate;
	    private String bankBranchHcountry;
	    private String bankBranchHpincode;
	    private String bankBranchFaxNumber;
	    private String bankBranchContactPersonName;
	    private String contactPersonContactNumber;
	    private String contactPersonEmail;
	    private String bankBranchManagerName;
	    private String bankBranchContactNumber;
	    private String bankBranchManagerEmail;
	    private boolean eligibleForWtSubmission;
	    private String ecgcStatus;
	    private boolean bankBranchRegionZoneFlag;
	    private String bankBranchWebsite;
	    private String createdBy;
//	    @JsonDeserialize(using = CustomDateDeSerializer.class)
	    private Date createdDate;
	    private String lastUpdatedBy;
//	    @JsonDeserialize(using = CustomDateDeSerializer.class)
	    private Date lastUpdatedDate;
	    private String metaRemarks;
		public String getBankBranchCode() {
			return bankBranchCode;
		}
		public void setBankBranchCode(String bankBranchCode) {
			this.bankBranchCode = bankBranchCode;
		}
		public String getIfscCode() {
			return ifscCode;
		}
		public void setIfscCode(String ifscCode) {
			this.ifscCode = ifscCode;
		}
		public String getBankCode() {
			return bankCode;
		}
		public void setBankCode(String bankCode) {
			this.bankCode = bankCode;
		}
		public String getBankBranchName() {
			return bankBranchName;
		}
		public void setBankBranchName(String bankBranchName) {
			this.bankBranchName = bankBranchName;
		}
		public Date getBankBranchEffectiveFrom() {
			return bankBranchEffectiveFrom;
		}
		public void setBankBranchEffectiveFrom(Date bankBranchEffectiveFrom) {
			this.bankBranchEffectiveFrom = bankBranchEffectiveFrom;
		}
		public String getBankBranchOldCode() {
			return bankBranchOldCode;
		}
		public void setBankBranchOldCode(String bankBranchOldCode) {
			this.bankBranchOldCode = bankBranchOldCode;
		}
		public String getBankBranchAreaCode() {
			return bankBranchAreaCode;
		}
		public void setBankBranchAreaCode(String bankBranchAreaCode) {
			this.bankBranchAreaCode = bankBranchAreaCode;
		}
		public String getBankBranchSatelliteCenterCode() {
			return bankBranchSatelliteCenterCode;
		}
		public void setBankBranchSatelliteCenterCode(String bankBranchSatelliteCenterCode) {
			this.bankBranchSatelliteCenterCode = bankBranchSatelliteCenterCode;
		}
		public String getBankBranchType() {
			return bankBranchType;
		}
		public void setBankBranchType(String bankBranchType) {
			this.bankBranchType = bankBranchType;
		}
		public String getBankBranchRegionZone() {
			return bankBranchRegionZone;
		}
		public void setBankBranchRegionZone(String bankBranchRegionZone) {
			this.bankBranchRegionZone = bankBranchRegionZone;
		}
		public Date getDateOfAssociation() {
			return dateOfAssociation;
		}
		public void setDateOfAssociation(Date dateOfAssociation) {
			this.dateOfAssociation = dateOfAssociation;
		}
		public String getLogicalLocationCode() {
			return logicalLocationCode;
		}
		public void setLogicalLocationCode(String logicalLocationCode) {
			this.logicalLocationCode = logicalLocationCode;
		}
		public String getBankBranchAddrLineOne() {
			return bankBranchAddrLineOne;
		}
		public void setBankBranchAddrLineOne(String bankBranchAddrLineOne) {
			this.bankBranchAddrLineOne = bankBranchAddrLineOne;
		}
		public String getBankBranchAddrLineTwo() {
			return bankBranchAddrLineTwo;
		}
		public void setBankBranchAddrLineTwo(String bankBranchAddrLineTwo) {
			this.bankBranchAddrLineTwo = bankBranchAddrLineTwo;
		}
		public String getBankBranchAddrLineThree() {
			return bankBranchAddrLineThree;
		}
		public void setBankBranchAddrLineThree(String bankBranchAddrLineThree) {
			this.bankBranchAddrLineThree = bankBranchAddrLineThree;
		}
		public String getBankBranchAddrLineFour() {
			return bankBranchAddrLineFour;
		}
		public void setBankBranchAddrLineFour(String bankBranchAddrLineFour) {
			this.bankBranchAddrLineFour = bankBranchAddrLineFour;
		}
		public String getBankBranchCity() {
			return bankBranchCity;
		}
		public void setBankBranchCity(String bankBranchCity) {
			this.bankBranchCity = bankBranchCity;
		}
		public String getBankBranchState() {
			return bankBranchState;
		}
		public void setBankBranchState(String bankBranchState) {
			this.bankBranchState = bankBranchState;
		}
		public String getBankBranchCountry() {
			return bankBranchCountry;
		}
		public void setBankBranchCountry(String bankBranchCountry) {
			this.bankBranchCountry = bankBranchCountry;
		}
		public Integer getBankBranchPincode() {
			return bankBranchPincode;
		}
		public void setBankBranchPincode(Integer bankBranchPincode) {
			this.bankBranchPincode = bankBranchPincode;
		}
		public String getBankBranchPrimaryContactNumber() {
			return bankBranchPrimaryContactNumber;
		}
		public void setBankBranchPrimaryContactNumber(String bankBranchPrimaryContactNumber) {
			this.bankBranchPrimaryContactNumber = bankBranchPrimaryContactNumber;
		}
		public String getBankBranchSecondaryContactNumber() {
			return bankBranchSecondaryContactNumber;
		}
		public void setBankBranchSecondaryContactNumber(String bankBranchSecondaryContactNumber) {
			this.bankBranchSecondaryContactNumber = bankBranchSecondaryContactNumber;
		}
		public String getBankBranchEmailId() {
			return bankBranchEmailId;
		}
		public void setBankBranchEmailId(String bankBranchEmailId) {
			this.bankBranchEmailId = bankBranchEmailId;
		}
		public String getBankBranchHname() {
			return bankBranchHname;
		}
		public void setBankBranchHname(String bankBranchHname) {
			this.bankBranchHname = bankBranchHname;
		}
		public String getBankBranchHaddrLineOne() {
			return bankBranchHaddrLineOne;
		}
		public void setBankBranchHaddrLineOne(String bankBranchHaddrLineOne) {
			this.bankBranchHaddrLineOne = bankBranchHaddrLineOne;
		}
		public String getBankBranchHaddrLineTwo() {
			return bankBranchHaddrLineTwo;
		}
		public void setBankBranchHaddrLineTwo(String bankBranchHaddrLineTwo) {
			this.bankBranchHaddrLineTwo = bankBranchHaddrLineTwo;
		}
		public String getBankBranchHaddrLineThree() {
			return bankBranchHaddrLineThree;
		}
		public void setBankBranchHaddrLineThree(String bankBranchHaddrLineThree) {
			this.bankBranchHaddrLineThree = bankBranchHaddrLineThree;
		}
		public String getBankBranchHaddrLineFour() {
			return bankBranchHaddrLineFour;
		}
		public void setBankBranchHaddrLineFour(String bankBranchHaddrLineFour) {
			this.bankBranchHaddrLineFour = bankBranchHaddrLineFour;
		}
		public String getBankBranchHcity() {
			return bankBranchHcity;
		}
		public void setBankBranchHcity(String bankBranchHcity) {
			this.bankBranchHcity = bankBranchHcity;
		}
		public String getBankBranchHstate() {
			return bankBranchHstate;
		}
		public void setBankBranchHstate(String bankBranchHstate) {
			this.bankBranchHstate = bankBranchHstate;
		}
		public String getBankBranchHcountry() {
			return bankBranchHcountry;
		}
		public void setBankBranchHcountry(String bankBranchHcountry) {
			this.bankBranchHcountry = bankBranchHcountry;
		}
		public String getBankBranchHpincode() {
			return bankBranchHpincode;
		}
		public void setBankBranchHpincode(String bankBranchHpincode) {
			this.bankBranchHpincode = bankBranchHpincode;
		}
		public String getBankBranchFaxNumber() {
			return bankBranchFaxNumber;
		}
		public void setBankBranchFaxNumber(String bankBranchFaxNumber) {
			this.bankBranchFaxNumber = bankBranchFaxNumber;
		}
		public String getBankBranchContactPersonName() {
			return bankBranchContactPersonName;
		}
		public void setBankBranchContactPersonName(String bankBranchContactPersonName) {
			this.bankBranchContactPersonName = bankBranchContactPersonName;
		}
		public String getContactPersonContactNumber() {
			return contactPersonContactNumber;
		}
		public void setContactPersonContactNumber(String contactPersonContactNumber) {
			this.contactPersonContactNumber = contactPersonContactNumber;
		}
		public String getContactPersonEmail() {
			return contactPersonEmail;
		}
		public void setContactPersonEmail(String contactPersonEmail) {
			this.contactPersonEmail = contactPersonEmail;
		}
		public String getBankBranchManagerName() {
			return bankBranchManagerName;
		}
		public void setBankBranchManagerName(String bankBranchManagerName) {
			this.bankBranchManagerName = bankBranchManagerName;
		}
		public String getBankBranchContactNumber() {
			return bankBranchContactNumber;
		}
		public void setBankBranchContactNumber(String bankBranchContactNumber) {
			this.bankBranchContactNumber = bankBranchContactNumber;
		}
		public String getBankBranchManagerEmail() {
			return bankBranchManagerEmail;
		}
		public void setBankBranchManagerEmail(String bankBranchManagerEmail) {
			this.bankBranchManagerEmail = bankBranchManagerEmail;
		}
		public boolean isEligibleForWtSubmission() {
			return eligibleForWtSubmission;
		}
		public void setEligibleForWtSubmission(boolean eligibleForWtSubmission) {
			this.eligibleForWtSubmission = eligibleForWtSubmission;
		}
		public String getEcgcStatus() {
			return ecgcStatus;
		}
		public void setEcgcStatus(String ecgcStatus) {
			this.ecgcStatus = ecgcStatus;
		}
		public boolean isBankBranchRegionZoneFlag() {
			return bankBranchRegionZoneFlag;
		}
		public void setBankBranchRegionZoneFlag(boolean bankBranchRegionZoneFlag) {
			this.bankBranchRegionZoneFlag = bankBranchRegionZoneFlag;
		}
		public String getBankBranchWebsite() {
			return bankBranchWebsite;
		}
		public void setBankBranchWebsite(String bankBranchWebsite) {
			this.bankBranchWebsite = bankBranchWebsite;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public String getLastUpdatedBy() {
			return lastUpdatedBy;
		}
		public void setLastUpdatedBy(String lastUpdatedBy) {
			this.lastUpdatedBy = lastUpdatedBy;
		}
		public Date getLastUpdatedDate() {
			return lastUpdatedDate;
		}
		public void setLastUpdatedDate(Date lastUpdatedDate) {
			this.lastUpdatedDate = lastUpdatedDate;
		}
		public String getMetaRemarks() {
			return metaRemarks;
		}
		public void setMetaRemarks(String metaRemarks) {
			this.metaRemarks = metaRemarks;
		}
		
		public BankBranchDetails() {
			super();
		}
		
		@Override
		public String toString() {
			return "BankBranch [bankBranchCode=" + bankBranchCode + ", ifscCode=" + ifscCode + ", bankCode=" + bankCode
					+ ", bankBranchName=" + bankBranchName + ", bankBranchEffectiveFrom=" + bankBranchEffectiveFrom
					+ ", bankBranchOldCode=" + bankBranchOldCode + ", bankBranchAreaCode=" + bankBranchAreaCode
					+ ", bankBranchSatelliteCenterCode=" + bankBranchSatelliteCenterCode + ", bankBranchType="
					+ bankBranchType + ", bankBranchRegionZone=" + bankBranchRegionZone + ", dateOfAssociation="
					+ dateOfAssociation + ", logicalLocationCode=" + logicalLocationCode + ", bankBranchAddrLineOne="
					+ bankBranchAddrLineOne + ", bankBranchAddrLineTwo=" + bankBranchAddrLineTwo
					+ ", bankBranchAddrLineThree=" + bankBranchAddrLineThree + ", bankBranchAddrLineFour="
					+ bankBranchAddrLineFour + ", bankBranchCity=" + bankBranchCity + ", bankBranchState="
					+ bankBranchState + ", bankBranchCountry=" + bankBranchCountry + ", bankBranchPincode="
					+ bankBranchPincode + ", bankBranchPrimaryContactNumber=" + bankBranchPrimaryContactNumber
					+ ", bankBranchSecondaryContactNumber=" + bankBranchSecondaryContactNumber + ", bankBranchEmailId="
					+ bankBranchEmailId + ", bankBranchHname=" + bankBranchHname + ", bankBranchHaddrLineOne="
					+ bankBranchHaddrLineOne + ", bankBranchHaddrLineTwo=" + bankBranchHaddrLineTwo
					+ ", bankBranchHaddrLineThree=" + bankBranchHaddrLineThree + ", bankBranchHaddrLineFour="
					+ bankBranchHaddrLineFour + ", bankBranchHcity=" + bankBranchHcity + ", bankBranchHstate="
					+ bankBranchHstate + ", bankBranchHcountry=" + bankBranchHcountry + ", bankBranchHpincode="
					+ bankBranchHpincode + ", bankBranchFaxNumber=" + bankBranchFaxNumber
					+ ", bankBranchContactPersonName=" + bankBranchContactPersonName + ", contactPersonContactNumber="
					+ contactPersonContactNumber + ", contactPersonEmail=" + contactPersonEmail
					+ ", bankBranchManagerName=" + bankBranchManagerName + ", bankBranchContactNumber="
					+ bankBranchContactNumber + ", bankBranchManagerEmail=" + bankBranchManagerEmail
					+ ", eligibleForWtSubmission=" + eligibleForWtSubmission + ", ecgcStatus=" + ecgcStatus
					+ ", bankBranchRegionZoneFlag=" + bankBranchRegionZoneFlag + ", bankBranchWebsite="
					+ bankBranchWebsite + ", createdBy=" + createdBy + ", createdDate=" + createdDate
					+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedDate=" + lastUpdatedDate + ", metaRemarks="
					+ metaRemarks + "]";
		}
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
	    
	
}


package in.ecgc.smile.erp.accounts.model;

import javax.validation.constraints.Size;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
public class PayinslipCustCode
{
	
	@NotNull(message="logicallocCd Cannot be Empty")
	private String logicallocCd;
	
	@NotNull(message="customerCd Cannot be Empty")
	private String customerCd;
	
	@Size(min=0,max=100,message="Please provide bankNamebetween 0 to 100 characters")
	private String bankName;
	
	@Size(min=0,max=500,message="Please provide bankAddressbetween 0 to 500 characters")
	private String bankAddress;
	
	@Size(min=0,max=15,message="Please provide bAcctNobetween 0 to 15 characters")
	private String bAcctNo;
	
	@Size(min=0,max=15,message="Please provide neftCodebetween 0 to 15 characters")
	private String neftCode;
	
	@Size(min=0,max=15,message="Please provide ifscCodebetween 0 to 15 characters")
	private String ifscCode;
	
	@Size(min=0,max=4,message="Please provide drMainglCdbetween 0 to 4 characters")
	private String drMainglCd;
	
	@Size(min=0,max=3,message="Please provide drSubglCdbetween 0 to 3 characters")
	private String drSubglCd;
	
	@Size(min=0,max=4,message="Please provide crMainglCdbetween 0 to 4 characters")
	private String crMainglCd;
	
	@Size(min=0,max=3,message="Please provide crSubglCdbetween 0 to 3 characters")
	private String crSubglCd;
	
	@Size(min=0,max=4,message="Please provide bankMainglCdbetween 0 to 4 characters")
	private String bankMainglCd;
	
	@Size(min=0,max=3,message="Please provide bankSubglCdbetween 0 to 3 characters")
	private String bankSubglCd;
	
	@Size(min=0,max=50,message="Please provide createdBybetween 0 to 50 characters")
	private String createdBy;
	
	@Size(min=0,max=50,message="Please provide lastUpdatedBybetween 0 to 50 characters")
	private String lastUpdatedBy;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastUpdatedDt;
	
	@Size(min=0,max=2147483647,message="Please provide metaStatusbetween 0 to 2147483647 characters")
	private String metaStatus;
	
	@Size(min=0,max=2147483647,message="Please provide metaRemarksbetween 0 to 2147483647 characters")
	private String metaRemarks;
	
	@NotNull(message="gstin Cannot be Empty")
	private String gstin;
	
	@NotNull(message="gstinstate Cannot be Empty")
	private String gstinstate;
	
	@NotNull(message="beneficiaryAcctName Cannot be Empty")
	private String beneficiaryAcctName;
	
	@NotNull(message="beneficiaryCodeIbank Cannot be Empty")
	private String beneficiaryCodeIbank;
	
	public void setLogicallocCd(String  logicallocCd)
	{
	this.logicallocCd=logicallocCd;
	}
	
	public String getLogicallocCd()
	{
	return logicallocCd;
	}

	public void setCustomerCd(String  customerCd)
	{
	this.customerCd=customerCd;
	}
	
	public String getCustomerCd()
	{
	return customerCd;
	}

	public void setBankName(String  bankName)
	{
	this.bankName=bankName;
	}
	
	public String getBankName()
	{
	return bankName;
	}

	public void setBankAddress(String  bankAddress)
	{
	this.bankAddress=bankAddress;
	}
	
	public String getBankAddress()
	{
	return bankAddress;
	}

	public void setBAcctNo(String  bAcctNo)
	{
	this.bAcctNo=bAcctNo;
	}
	
	public String getBAcctNo()
	{
	return bAcctNo;
	}

	public void setNeftCode(String  neftCode)
	{
	this.neftCode=neftCode;
	}
	
	public String getNeftCode()
	{
	return neftCode;
	}

	public void setIfscCode(String  ifscCode)
	{
	this.ifscCode=ifscCode;
	}
	
	public String getIfscCode()
	{
	return ifscCode;
	}

	public void setDrMainglCd(String  drMainglCd)
	{
	this.drMainglCd=drMainglCd;
	}
	
	public String getDrMainglCd()
	{
	return drMainglCd;
	}

	public void setDrSubglCd(String  drSubglCd)
	{
	this.drSubglCd=drSubglCd;
	}
	
	public String getDrSubglCd()
	{
	return drSubglCd;
	}

	public void setCrMainglCd(String  crMainglCd)
	{
	this.crMainglCd=crMainglCd;
	}
	
	public String getCrMainglCd()
	{
	return crMainglCd;
	}

	public void setCrSubglCd(String  crSubglCd)
	{
	this.crSubglCd=crSubglCd;
	}
	
	public String getCrSubglCd()
	{
	return crSubglCd;
	}

	public void setBankMainglCd(String  bankMainglCd)
	{
	this.bankMainglCd=bankMainglCd;
	}
	
	public String getBankMainglCd()
	{
	return bankMainglCd;
	}

	public void setBankSubglCd(String  bankSubglCd)
	{
	this.bankSubglCd=bankSubglCd;
	}
	
	public String getBankSubglCd()
	{
	return bankSubglCd;
	}

	public void setCreatedBy(String  createdBy)
	{
	this.createdBy=createdBy;
	}
	
	public String getCreatedBy()
	{
	return createdBy;
	}

	public void setLastUpdatedBy(String  lastUpdatedBy)
	{
	this.lastUpdatedBy=lastUpdatedBy;
	}
	
	public String getLastUpdatedBy()
	{
	return lastUpdatedBy;
	}

	public void setCreatedDt(Date  createdDt)
	{
	this.createdDt=createdDt;
	}
	
	public Date getCreatedDt()
	{
	return createdDt;
	}

	public void setLastUpdatedDt(Date  lastUpdatedDt)
	{
	this.lastUpdatedDt=lastUpdatedDt;
	}
	
	public Date getLastUpdatedDt()
	{
	return lastUpdatedDt;
	}

	public void setMetaStatus(String  metaStatus)
	{
	this.metaStatus=metaStatus;
	}
	
	public String getMetaStatus()
	{
	return metaStatus;
	}

	public void setMetaRemarks(String  metaRemarks)
	{
	this.metaRemarks=metaRemarks;
	}
	
	public String getMetaRemarks()
	{
	return metaRemarks;
	}

	public void setGstin(String  gstin)
	{
	this.gstin=gstin;
	}
	
	public String getGstin()
	{
	return gstin;
	}

	public void setGstinstate(String  gstinstate)
	{
	this.gstinstate=gstinstate;
	}
	
	public String getGstinstate()
	{
	return gstinstate;
	}

	public void setBeneficiaryAcctName(String  beneficiaryAcctName)
	{
	this.beneficiaryAcctName=beneficiaryAcctName;
	}
	
	public String getBeneficiaryAcctName()
	{
	return beneficiaryAcctName;
	}

	public void setBeneficiaryCodeIbank(String  beneficiaryCodeIbank)
	{
	this.beneficiaryCodeIbank=beneficiaryCodeIbank;
	}
	
	public String getBeneficiaryCodeIbank()
	{
	return beneficiaryCodeIbank;
	}

	@Override
	public String toString() {
	return "PayinslipCustCode [logicallocCd=" + logicallocCd + "customerCd=" + customerCd + "bankName=" + bankName + "bankAddress=" + bankAddress + "bAcctNo=" + bAcctNo + "neftCode=" + neftCode + "ifscCode=" + ifscCode + "drMainglCd=" + drMainglCd + "drSubglCd=" + drSubglCd + "crMainglCd=" + crMainglCd + "crSubglCd=" + crSubglCd + "bankMainglCd=" + bankMainglCd + "bankSubglCd=" + bankSubglCd + "createdBy=" + createdBy + "lastUpdatedBy=" + lastUpdatedBy + "createdDt=" + createdDt + "lastUpdatedDt=" + lastUpdatedDt + "metaStatus=" + metaStatus + "metaRemarks=" + metaRemarks + "gstin=" + gstin + "gstinstate=" + gstinstate + "beneficiaryAcctName=" + beneficiaryAcctName + "beneficiaryCodeIbank=" + beneficiaryCodeIbank + "]";
	}
	}

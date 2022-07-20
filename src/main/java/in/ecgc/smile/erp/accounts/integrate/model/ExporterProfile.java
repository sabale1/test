package in.ecgc.smile.erp.accounts.integrate.model;

import java.sql.Timestamp;
import java.util.Date;


import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ExporterProfile {

//public interface ModifyBasicDetails{};
	
	private int exporterCode;
	private String regCompanyName;
	private String ieCode;
	private String ieDocDmsId;
	private String groupCode;
	private boolean groupBenefits;
	private boolean mlt;
	/*
	 * Does the task of WebDataBinder - converting string to Date*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date companyIncorporationDate;
	
	private String panNum;
	private String panDocDmsId;
	private String cin;
	private String eia;
	//private ExporterLegalConstitution legalConstitution;
	private boolean statusHolder;
	private boolean eou;
	private boolean sez;
	private boolean msme;
	private boolean inRbiCautionList;
	private String rbiRemarks;
	private Date rbiEffectiveDate;
	private boolean epc;
	private boolean kycVerified;
	private boolean profileVerified;
	private String prefBranchCode;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date inceptionDate;
	
	private String igst;
	private String cgst;
	private String sgst;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date gstEffectiveDate;
	
	private boolean enteredByExporter;
	private boolean updateByExporter;
	private char status;
	private String createdBy;
	private Timestamp createdDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	
//	@NotEmpty(groups = {ModifyBasicDetails.class}, message = "*Required")
	private String metaRemarks;
	
	private int versionNo;
//	private List<ExporterContactPerson> contacts;
//	private List<ExporterCommodity> commodities;
//	private List<NatureOfBusiness> natureOfBusiness;
//	private List<ExporterBank> banks;
//	private List<ExporterAddress> addresses;
//	private List<ExporterSisterConcernIndia> sisterConcernsIndia;
//	private List<ExporterManagement> managementDetails;
//	private List<BuyerAssociate> buyerAssociate;
//	private ExporterMsme msmeDetails;
	private String logicalLocCd;
	private String userId;
//	private SAL salStatus;
	private String salStatusExporter;
	
}
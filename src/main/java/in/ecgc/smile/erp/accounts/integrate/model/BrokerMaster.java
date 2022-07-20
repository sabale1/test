package in.ecgc.smile.erp.accounts.integrate.model;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;import lombok.Data;
import lombok.ToString;
@ToString
@Data
public class BrokerMaster {

//@NotEmpty(message = "{attachment.privateAgencyCd.required}" , groups = { Attachment.class })
@NotBlank(message = "{renewal.privateAgencyCd.required}")
private String privateAgencyCd;
//
private String brokerageAgencyId;
//private BrokerageAgencyMaster brokerageAgencyMaster;
private String username;
private String password;
@DateTimeFormat(pattern= "yyyy-MM-dd")
private Date dtOfExpiry;
//private MailTemplate MailTemplate;
@NotBlank(message = "{brokerName.required}" )
@NotBlank(message = "{brokerName.required}")
private String brokerName;
//
@NotBlank(message = "{brokerageAgencyClassification.required}")
@NotBlank(message = "{brokerageAgencyClassification.required}")
//private String brokerageAgencyClassification;
@NotBlank(message = "{licenceNoByIrdai.required}")
@NotBlank(message = "{licenceNoByIrdai.required}")
private String licenceNoByIrdai;
@NotNull(message = "{licenceValidityFromDt.required}")
@NotNull(message = "{licenceValidityFromDt.required}")
@DateTimeFormat(pattern= "yyyy-MM-dd")
private Date licenceValidityFromDt;
@NotNull(message = "{licenceValidityToDt.required}")
@NotNull(message = "{licenceValidityToDt.required}")
@DateTimeFormat(pattern= "yyyy-MM-dd")
private Date licenceValidityToDt;
@NotBlank(message = "{category.required}")
@NotBlank(message = "{category.required}")
private String category;
//to get or add address
@Valid
//private Address address;
private String addressId;
//
@NotBlank(message = "{contactPerson.required}")
@NotBlank(message = "{contactPerson.required}")
private String contactPerson;
//
@NotBlank(message = "{website.required}")
@NotBlank(message = "{website.required}")
private String website;
@NotBlank(message = "{telephoneNo.required}")
@NotBlank(message = "{telephoneNo.required}")
private String telephoneNo;
@NotBlank(message = "{fax.required}" )
@NotBlank(message = "{fax.required}" )
private String fax;
@NotBlank(message = "{emailId.required}" )
@NotBlank(message = "{emailId.required}" )
private String emailId;
@NotBlank(message = "{mobileNo.required}" )
@NotBlank(message = "{mobileNo.required}" )
private String mobileNo;
@NotBlank(message = "{nameOfPrincipalOfficer.required}" )
@NotBlank(message = "{nameOfPrincipalOfficer.required}" )
private String nameOfPrincipalOfficer;
@NotBlank(message = "{contactNoOfPrincipalOfficer.required}" )
@NotBlank(message = "{contactNoOfPrincipalOfficer.required}" )
private String contactNoOfPrincipalOfficer;
@NotBlank(message = "{emailIdOfPrincipalOfficer.required}" )
@NotBlank(message = "{emailIdOfPrincipalOfficer.required}" )
private String emailIdOfPrincipalOfficer;
private Integer numberOfBranches;
@NotBlank(message = "{bankAccountNo.required}" )
@NotBlank(message = "{bankAccountNo.required}" )
private String bankAccountNo;
@NotBlank(message = "{bankName.required}" )
@NotBlank(message = "{bankName.required}" )
private String bankName;
@NotBlank(message = "{bankBranch.required}" )
@NotBlank(message = "{bankBranch.required}" )
private String bankBranch;
@NotBlank(message = "{beneficiaryName.required}" )
@NotBlank(message = "{beneficiaryName.required}" )
private String beneficiaryName;
@NotBlank(message = "{ifscNo.required}" )
@NotBlank(message = "{ifscNo.required}" )
private String ifscNo;
//
@NotBlank(message = "{taxExemptionCertificateNo.required}" )
@NotBlank(message = "{taxExemptionCertificateNo.required}" )
private String taxExemptionCertificateNo;
@NotBlank(message = "{taxExemptionCertificate.required}" )
@NotBlank(message = "{taxExemptionCertificate.required}" )
private String taxExemptionCertificate;
/*
@NotBlank(message = "{panNo.required}" )
@NotBlank(message = "{panNo.required}" )
private String panNo;
*/
//
@NotBlank(message = "{pan.required}" )
@NotBlank(message = "{pan.required}" )
private String pan;
//
@NotBlank(message = "{panCertificate.required}" )
@NotBlank(message = "{panCertificate.required}" )
private String panCertificate;
@NotBlank(message = "{gstNoOfBroker.required}" )
@NotBlank(message = "{gstNoOfBroker.required}" )
private String gstNoOfBroker;
//
@NotBlank(message = "{gstCertificate.required}" )
@NotBlank(message = "{gstCertificate.required}" )
private String gstCertificate;
@NotBlank(message = "{cancelledCheque.required}" )
@NotBlank(message = "{cancelledCheque.required}" )
private String cancelledCheque;
@NotBlank(message = "{irdaCertificate.required}" )
@NotBlank(message = "{irdaCertificate.required}" )
private String irdaCertificate;
@NotNull(message = "{registrationDt.required}" )
@NotNull(message = "{registrationDt.required}" )
@DateTimeFormat(pattern= "yyyy-MM-dd")
private Date registrationDt;
//To manipulate BrokerApplicant
@Valid
//private BrokerApplicant brokerApplicant;
private String brokerApplicationId; //FK Broker Applicant
@NotBlank(message = "{remarks.required}" )
private String remarks;
//Generic in whole database
private String metaStatus;
//private String status;
private String createdBy; // FK HR
private Date createdDt;
@NotBlank(message = "{lastUpdatedBy.required}" )
private String lastUpdatedBy; //FK HR
private Date lastUpdatedDt;
private String metaRemarks;
//@Valid
//private BrokerManagement brokerManagement; //To manipulate BrokerManagement @Valid
//private BrokerRenewal brokerRenewal; //To manipulate BrokerRenewal

}

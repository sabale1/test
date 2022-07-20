package in.ecgc.smile.erp.accounts.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class PaymentEmployeeDirectCreditDtl {

	private String requestNo;
	private Integer requestSerialNumber;
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="request date is required")
	private LocalDate requestDate;
	@NotBlank(message="employee no is required")
	private Integer employeeNo;
	@NotBlank(message="employee name is required")
	private String employeeName;
	@NotBlank(message="branch code is required")
	private String branchCode;
	@NotBlank(message="debit credit is required")
	private String debitCredit;
	@NotBlank(message="account subtype is required")
	private String accountSubType;
	@NotBlank(message="account type is required")
	private String accountType;
	@NotBlank(message="account is required")
	private Long accountNumber ;
	private Double amount;
	private String remarks;
	private String lastUpdatedBy;
	//@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate lastUpdatedDate;
	private String maiSentFlag; 
	private String status;
	private Integer approved_by;
	//@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate approvedDate;
	//@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate createdDate;
	
	private String createdBy ;

	
	
}

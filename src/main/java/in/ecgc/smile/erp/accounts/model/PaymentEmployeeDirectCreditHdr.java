package in.ecgc.smile.erp.accounts.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class PaymentEmployeeDirectCreditHdr {

	
	private String  requestNo ;
	//@NotBlank(message="department is required")
	private String departmentCode ;
	@NotBlank(message="requestlogical location is required")
	private String 	requestedLogicalLoc ;
	private Integer requestCreatedBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	//@NotNull(message="request date is required")
	private LocalDate requestDate;
	private String lastUpdatedBy;
//	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate lastUpdateDt ;
	private String createdBy ;
	//@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate createdDate;
	private String metaStatus;
	private String metaRemarks;
	private List<PaymentEmployeeDirectCreditDtl> pymtEmpList;

}

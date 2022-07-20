package in.ecgc.smile.erp.accounts.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class GLReportIn {

	@NotBlank(message = "Main GL Code is Required")
	@Size(min = 4, max = 4)
	private String mainGl;

	@NotBlank(message = "Sub GL Code is Required")
	@Size(min = 3, max = 3)
	private String subGl;

	@NotBlank(message = "Personal Ledger Code is Required")
	private String plCd;

	private String subBfCd;

	@NotBlank(message = "Logical Location Code is Required")
	private String logicalLocCd;

	@NotBlank(message = "From Date is Required")
	private LocalDate fromDt;

	@NotBlank(message = "To Date is Required")
	private LocalDate toDt;
}

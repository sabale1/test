package in.ecgc.smile.erp.accounts.model;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class FiscalYearModel {
	
	@NotBlank(message = "{currFiscalYear.required}")
	@Size(max = 9)
	private String currFiscalYear;
	@NotNull(message = "{currFiscalYearStartDt.required}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate currFiscalYearStartDt;
	@NotBlank(message = "{prevFiscalYear.required}")
	@Size(max = 9)
	private String prevFiscalYear;
	@NotNull(message = "{prevFiscalYearClosedDt.required}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate prevFiscalYearClosedDt;
	@NotBlank(message = "{metaStatus.required}")
	@Size(max = 1)
	private Character metaStatus;
	private String metaRemarks;
	private String createdBy;
	private Date createdDt;
	private String lastUpdatedBy;
	private Date lastUpdatedDt;
	
	
}

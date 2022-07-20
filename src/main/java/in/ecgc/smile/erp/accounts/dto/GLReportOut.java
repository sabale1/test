package in.ecgc.smile.erp.accounts.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class GLReportOut {

	List<GLReport> glReport;

	private BigDecimal openingBal;
	private String openingBalFlag;
	private BigDecimal closingBal;
	private String closingBalFlag;
	private BigDecimal totalCrBal;
	private BigDecimal totalDrBal;
}

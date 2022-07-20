package in.ecgc.smile.erp.accounts.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class TrialBalanceSingleLocation {

	private String logicalLoc;
	private LocalDate balanceDt;
	private BigDecimal totalAsstCr;
	private BigDecimal totalIncmCr;
	private BigDecimal totalLiabCr;
	private BigDecimal totalExpdCr;
	private BigDecimal totalAsstDr;
	private BigDecimal totalIncmDr;
	private BigDecimal totalLiabDr;
	private BigDecimal totalExpdDr;
	private BigDecimal totalDr;
	private BigDecimal totalCr;
	private BigDecimal totalDiff;
	private List<TrialBalance> trialBal;

}

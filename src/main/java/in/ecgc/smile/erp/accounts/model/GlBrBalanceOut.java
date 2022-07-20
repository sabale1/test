
package in.ecgc.smile.erp.accounts.model;

import lombok.Data;

@Data
public class GlBrBalanceOut {
	private String logicallocCd;
	private String mainglCd;
	private String subglCd;
	private String personalLedgerCd;
	private String fiscalYr;
	private double openDrBal;
	private double openCrBal;
	private double lastCrBal;
	private double lastDrBal;
	private String glType;
	private double balance;
}

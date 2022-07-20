package in.ecgc.smile.erp.accounts.model;

import lombok.Data;

@Data
public class GlBrBalanceIn {
	private String logicallocCd;
	private String mainglCd;
	private String subglCd;
	private String personalLedgerCd;
}

package in.ecgc.smile.erp.accounts.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TrialBalance {

	private String mainGlCd;
	private String subGlCd;
	private String glName;
	private String gltype;
	private BigDecimal dr_amt;
	private BigDecimal cr_amt;
	

}

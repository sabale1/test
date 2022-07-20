package in.ecgc.smile.erp.accounts.repository;

import java.util.List;

import javax.validation.constraints.NotBlank;

import in.ecgc.smile.erp.accounts.model.TrialBalance;

public interface TrialBalanceDao {


	TrialBalance getByLogicalLoc(@NotBlank String logicalloc, @NotBlank String balancedate, @NotBlank String mainglcd,
			@NotBlank String subglcd, @NotBlank String glname, @NotBlank String gltype);

	TrialBalance getForAllLocation(@NotBlank String balancedate, String mainglCd, String subglCd, String glName,
			String glType);

}

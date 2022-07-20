package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import javax.validation.constraints.NotBlank;

import in.ecgc.smile.erp.accounts.model.TrialBalance;
import in.ecgc.smile.erp.accounts.model.TrialBalanceAllLocation;
import in.ecgc.smile.erp.accounts.model.TrialBalanceSingleLocation;

/**
 * Fiscal year Service Interface
 * 
 * @version 1.0 15-September-2020
 * @author Sanjali Kesarkar
 */
public interface TrialBalanceService {

	TrialBalanceSingleLocation getByLogicalLoc(@NotBlank String logicalloc, @NotBlank String balancedate);

	TrialBalanceAllLocation getForAllLocation(@NotBlank String balancedate);

}

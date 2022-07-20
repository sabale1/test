package in.ecgc.smile.erp.accounts.util;

public interface TrialBalanceSqlQueries {
	
	  public static final String GET_TRIAL_BAL_BY_LOGLOC ="call accounts.get_trial_balance_gl_loc_wise(:maingl_cd,:subgl_cd,:balancedt,:logicalloc,'1','1')";
	 
	  public static final String GET_TRIAL_BAL_FOR_ALL_LOGLOC ="call accounts.get_trial_balance_gl_wise(:maingl_cd,:subgl_cd,:balancedt,'1','1')";
		 

}

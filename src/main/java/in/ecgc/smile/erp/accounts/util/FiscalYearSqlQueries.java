package in.ecgc.smile.erp.accounts.util;

public interface FiscalYearSqlQueries {
	
	public static final String GET_CURRENT_FISCAL_YEAR =
	"SELECT * FROM ecgc_acct_fiscal_yr where meta_status = :status";
	
	public static final String ALL_FISCAL_YR =
	"SELECT distinct(curr_fisc_yr) FROM ecgc_acct_fiscal_yr";
	
	public static final String GET_FISCAL_YRS = "SELECT * FROM ecgc_acct_fiscal_yr";
	
	public static final String GET_FISCALYR_DATA_BY_ID = "SELECT * FROM ecgc_acct_fiscal_yr where curr_fisc_yr = :currFiscYr";

	public static final String UPADATE_PRE_FISCAL_YEAR_ENTRY = "UPDATE accounts.ecgc_acct_fiscal_yr SET meta_status='I' WHERE curr_fisc_yr=:prevFiscYr";
	
	public static final String CREATE_CURRENT_FISCAL_YEAR_ENTRY =
			"INSERT INTO accounts.ecgc_acct_fiscal_yr(curr_fisc_yr_start_dt,curr_fisc_yr,prev_fisc_yr,prev_fisc_yr_closed_dt,meta_status,created_by,created_dt,meta_remarks,short_yr,entity_cd) VALUES (:currFiscYrStartDt,:currFiscYr,:prevFiscYr,:prevFiscYrClosedDt,:metaStatus,:createdBy,now(),:metaRemarks,:shortYr,'ECGC')";

	public static final String CREATE_FISCAL_YEAR_ENTRY =
			"INSERT INTO accounts.ecgc_acct_fiscal_yr(curr_fisc_yr_start_dt,curr_fisc_yr,prev_fisc_yr,prev_fisc_yr_closed_dt,meta_status,created_by,created_dt,meta_remarks) VALUES (:currFiscYrStartDt,:currFiscYr,:prevFiscYr,:prevFiscYrClosedDt,:metaStatus,:createdBy,now(),:metaRemarks)";

	public static final String CLOSE_PREVIOUS_FISCAL_YEAR = 
	"update accounts.ecgc_acct_fiscal_yr set meta_status = 'I', last_updated_by = :last_updated_by, last_updated_dt =now() where meta_status = 'V' and curr_fisc_yr = :prevFiscalYr ";

	public static final String UPDATE_FISCAL_YEAR_ENTRY_ACTIVE = "Update  accounts.ecgc_acct_fiscal_yr set meta_status = 'I' where curr_fisc_yr=:currFiscYr ";

	public static final String DELETE_FISCAL_YEAR_ENTRY = "DELETE FROM accounts.ecgc_acct_fiscal_yr WHERE curr_fisc_yr <> :currFiscYr";


}

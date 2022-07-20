package in.ecgc.smile.erp.accounts.util;

/**
 * 
 * Stamp Parameter
 * 
 * @author Amitesh Banerjee
 * @version 1.0 27-April-2020
 *
 */

public class StampSqlQueries {
	
	/*
	 * public static final String ADD_STAMP_PARAMETER =
	 * "INSERT INTO acct_stamp_amt_param (sr_no, from_amt, to_amt, stamp_amt, description,"
	 * + " effective_dt, active," + " ecgc_status," +
	 * " created_by, created_on, updated_by, updated_on," + " meta_remarks)" +
	 * "	VALUES (:srNo, :fromAmount, :toAmount, :stampAmount, :description," +
	 * " :effectiveDate, :active," + " :ecgcStatus," +
	 * " :createdBy, now(), :updatedBy, now()," + " :metaRemarks)";
	 
	 *
	 */
	
	public static final String ADD_STAMP_PARAMETER = 
			"INSERT INTO ecgc_acct_stamp_parameter_mst (sr_no, from_amt, to_amt, stmp_amt, description, "
				+ " effective_dt,created_by, created_dt , active) "		 
				+ "	VALUES (accounts.ecgc_acct_int_seq_no_func('ecgc_acct_stamp_mst_seq'), :fromAmount, :toAmount, :stampAmount, :description," 
				+ " :effectiveDate,:createdBy,:createdOn,:active "
				+ " )";

	
	public static final String UPDATE_STAMP_PARAMETER = "UPDATE ecgc_acct_stamp_parameter_mst SET from_amt= :fromAmount, "
			+"to_amt= :toAmount, stmp_amt= :stampAmount, description = :description,effective_dt=:effectiveDate,"
			+"last_updated_by= :updatedBy, last_updated_dt= :updatedOn, meta_remarks= :metaRemarks, active= :active where sr_no=:stampCode";
	
	public static final String VIEW_STAMP_PARAMETER = "select * from ecgc_acct_stamp_parameter_mst ";
	
	/*
	 * public static final String VIEW_STAMP_PARAMETER_BY_ID =
	 * "SELECT sr_no, from_amt, to_amt, stamp_amt, description,"
	 * +" effective_dt, active, ecgc_status, created_by, created_on, updated_on, updated_by, meta_remarks"
	 * +" FROM acct_stamp_amt_param where sr_no=:stampCode";
	 */
	public static final String VIEW_STAMP_PARAMETER_BY_ID ="SELECT * from ecgc_acct_stamp_parameter_mst WHERE sr_no=:stampCode";
	
	//public static final String VIEW_STAMP_AMT_BY_RECEIPT ="SELECT stmp_amt from ecgc_acct_stamp_parameter_mst where stmp_amt=:receiptAmount";

/*	
	public static final String CHECK_FROM_AMT_TO_AMT = "SELECT count(sr_no) as count FROM accounts.ecgc_acct_stamp_parameter_mst\n"
			+ "where active = 'Y' and from_amt  <= :fAmount and to_amt >= :tAmount or to_amt =:fAmount";
*/
	public static final String CHECK_FROM_AMT_TO_AMT =  "SELECT count(sr_no) as count FROM accounts.ecgc_acct_stamp_parameter_mst "
		+ "WHERE active ='Y' and"
		+ " (:fAmount  BETWEEN   from_amt and to_amt or "
			+ ":tAmount  BETWEEN   from_amt and to_amt or "
			+ "from_amt in ( SELECT from_amt FROM accounts.ecgc_acct_stamp_parameter_mst  "
				+ "WHERE from_amt BETWEEN :fAmount AND :tAmount))";
	
	public static final String VIEW_SEQ_NO = "SELECT sr_no FROM accounts.ecgc_acct_stamp_parameter_mst\r\n"
			+ "where from_amt = :fromAmount and  to_amt = :toAmount and  stmp_amt= :stampAmount and  description= :description and  effective_dt= :effectiveDate and active= :active";
	
}

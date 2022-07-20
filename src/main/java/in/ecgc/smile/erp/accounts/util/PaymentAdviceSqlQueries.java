package in.ecgc.smile.erp.accounts.util;

public interface PaymentAdviceSqlQueries {

	public static final String GET_SEQ_NO =
			"SELECT   ecgc_acct_gen_pymtadvcno_func(:logicalLocCd,:sectionCd,:fiscalYear)";

	public static final String UPDATE_SEQ_NO =
			"UPDATE  ecgc_acct_int_pymtadvc_seq_no_tbl set advice_no = :adviceNo"
			+ " WHERE logical_loc_cd = :logicalLocCd and section_cd = :sectionCd and financial_yr =:fiscalYear ";

	public static final String ADD_PAYMENT_ADVICE_DTL =
			"INSERT INTO  ecgc_acct_pymt_advice_dtl "
			+ "(entity_cd, expense_head, logical_loc_cd, section_cd, advice_no, advice_dt, pay_to_type, pymt_party_cd, pymt_party_name, curr_cd, bill_no, bill_dt, bill_iwd_dt, bill_desc, advice_amt, advice_desc, dec_by, dec_dt, dec_rmk, aprv_amt, advice_stat, tds_applicable, tax_rate, surcharge_rate, tax_deducted, fiscal_yr, cess_amt, cess_rate, old_cd, advice_amt_oth_curr_inr, curr_rate, user_name, appr_base_amt, service_tax_amt, oth_amt, pymt_aprv_id, pymt_aprv_name, pymt_year, no_deduction_reason, taxinfo_flg, provision_flg, no_provision_reason, provisional_amt,liability_gl_txn_no,liability_gl_txn_type, created_dt, created_by, meta_status, branch_cd, meta_remarks, del_flag, module_cd, mapping_cd)"
			+ "VALUES(:entityCd,:expenseHead,:logicalLocCd,:sectionCd,:adviceNo,:adviceDate,:payToType,:pymtPartyCd,:pymtPartyName,:currCd,:billNo,:billDt,:billIwdDt,:billDesc,:adviceAmt,:adviceDesc,:decBy,:decDt,:decRemark,:aprvAmt,:adviceStat,:tdsApplicable,:taxRate,:surchargeRate,:taxDeducted,:fiscalYear,:cessAmt,:cessRate,:oldCd,:adviceAmtOtherCurrINR,:currRate,:userName,:apprBaseAmt,:serviceTaxAmt,:othAmt,:pymtAprvId,:pymtAprvName,:pymtYear,:noDeductionReason,:taxInfoFlag,:provisionFlag,:noProvisionReason,:provisionalAmt,:liabilityGlTxnNo, :liabilityGlTxnType,timestamp 'today',:createdBy,:metaStatus,:branchCd,:metaRemarks,false,:moduleCode,:mappingCode)";

	public static final String LIST_ALL_PAYMENT_ADVICE_DTL =
			"SELECT * FROM  ecgc_acct_pymt_advice_dtl where del_flag = false";

	public static final String GET_PAYMENT_ADVICE_BY_ADVICE_DTL =
			"SELECT * FROM  ecgc_acct_pymt_advice_dtl WHERE advice_no = (:adviceNo)::numeric and logical_loc_cd = :logicalLocCd and section_cd = :sectionCd and del_flag = false";

	public static final String GET_PAYMENT_ADVICE_BY_LOGICALlOC_SECCODE =
			"SELECT advice_no , advice_amt,advice_desc ,advice_stat , created_dt FROM  ecgc_acct_pymt_advice_dtl WHERE logical_loc_cd = :logicalLocCd and section_cd = :sectionCd  and del_flag = false and advice_dt  between :fromDt and :toDt ";

	public static final String DISABLE_PAYMENT_ADVICE_BY_ADVICE_DTL =
			"UPDATE  ecgc_acct_pymt_advice_dtl set del_flag = true where advice_no = (:adviceNo)::numeric and logical_loc_cd = :logicalLocCd and section_cd = :sectionCd ";

	public static final String UPDATE_PAYMENT_ADVICE_BY_ADVICE_NO =
			 "UPDATE  ecgc_acct_pymt_advice_dtl set pay_to_type = :payToType, pymt_party_cd = :pymtPartyCd , curr_cd = :currCd ,advice_amt = :adviceAmt, pymt_year =:pymtYear , advice_desc = :adviceDesc, bill_no = :billNo, bill_dt = :billDt, bill_iwd_dt = :billIwdDt, bill_desc = :billDesc, last_updated_by = :lastUpdatedBy, last_updated_dt = now() where advice_no = (:adviceNo)::numeric and logical_loc_cd = :logicalLocCd and section_cd = :sectionCd";

	public static final String UPDATE_PAYMENT_ADVICE_TCODES =
			"UPDATE  ecgc_acct_pymt_advice_tcodes SET t1=:t1, t2=:t2, t3=:t3, t4=:t4, t5=:t5, t6=:t6, t7=:t7, t8=:t8, t9=:t9, t10=:t10, t11=:t11, t12=:t12,t13=:t13, t14=:t14, t15=:t15, t16=:t16, t17=:t17, t18=:t18, t19=:t19,"
			+ " t20=:t20,t21=:t21, t22=:t22, t23=:t23, t24=:t24, t25=:t25, t26=:t26, t27=:t27, t28=:t28, t29=:t29, t30=:t30,t31=:t31, t32=:t32, t33=:t33, ad1=:ad1, ad2=:ad2, ad3=:ad3, ad4=:ad4, entity_cd=:entityCd where advice_no = (:adviceNo)::numeric and logical_loc_cd = :logicalLocCd and section_cd = :sectionCd ";

	public static final String ADD_PAYMENT_ADVICE_TCODES_DTL =
			"INSERT INTO  ecgc_acct_pymt_advice_tcodes "
			+ "(entity_cd,logical_loc_cd, section_cd, advice_no, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24,t25,t26,t27,t28,t29,t30,t31,t32,t33, ad1, ad2, ad3, ad4, del_flag) "
			+ "VALUES(:entityCd,:logicalLocCd, :sectionCd, :adviceNo::numeric, :t1, :t2, :t3, :t4, :t5, :t6, :t7, :t8, :t9, :t10, :t11, :t12,:t13,:t14,:t15,:t16,:t17,:t18,:t19,:t20,:t21,:t22,:t23,:t24,:t25,:t26,:t27,:t28,:t29,:t30,:t31,:t32,:t33,:ad1,:ad2,:ad3,:ad4,false)";

	public static final String DISABLE_PAYMENT_ADVICE_TCODES_DTL = "UPDATE  ecgc_acct_pymt_advice_tcodes "
			+ "SET del_flag=true WHERE advice_no= :adviceNo::numeric and logical_loc_cd = :logicalLocCd and section_cd = :sectionCd";

	public static final String LIST_ALL_PAYMENT_ADVICE_TCODES_DTL = "SELECT * FROM  ecgc_acct_pymt_advice_tcodes where del_flag = false";

	public static final String LIST_PAYMENT_ADVICE_TCODES_DTL_BY_ADVICE_DTL = "SELECT * FROM  ecgc_acct_pymt_advice_tcodes WHERE advice_no = (:adviceNo)::numeric and logical_loc_cd = :logicalLocCd and section_cd = :sectionCd";

	public static final String GET_PAYMENT_ADVICE_REQUEST = "SELECT advice_no , logical_loc_cd , section_cd , advice_amt , advice_desc, advice_dt  FROM  ecgc_acct_pymt_advice_dtl WHERE logical_loc_cd = :logicalLocCd and SECTION_CD = :sectionCd and advice_stat = :adviceStat and del_flag = false";

	public static final String TAKE_PAYMENT_ADVICE_DECISION = "update  ecgc_acct_pymt_advice_dtl set advice_stat = :adviceStat, dec_by = :decBy, dec_dt = :decDt, dec_rmk = :decRemark, aprv_amt = :aprvAmt"
			+ " where logical_loc_cd = :logicalLocCd and section_cd = :sectionCd and advice_no =:adviceNo ";

	public static final String UPDATE_PAYMET_ADVICE_STATUS = "update ecgc_acct_pymt_advice_dtl set advice_stat = :adviceStat, last_updated_by=:lastUpdatedBy, last_updated_dt=now() where logical_loc_cd = :logicalLocCd and section_cd = :sectionCd and advice_no =:adviceNo ";

	public static final String DELETE_PAYMENT_ADVICE = "delete from ecgc_acct_pymt_advice_dtl where logical_loc_cd = :logicalLocCd and section_cd =:sectionCd and advice_no =:adviceNo";

	public static final String DELETE_PAYMENT_ADVICE_TCODES = "delete from ecgc_acct_pymt_advice_tcodes where logical_loc_cd = :logicalLocCd and section_cd =:sectionCd and advice_no =:adviceNo";

	public static final String GET_PAYMENT_ADVICE_NUMBER = "select section_cd , advice_no,advice_dt,advice_amt,advice_stat ,advice_desc from ecgc_acct_pymt_advice_dtl where section_cd =:sectionCd and logical_loc_cd = :logicalLocCd and del_flag = false";

	public static final String GET_PAYMENT_ADVICE_NUMBER_BET_DT = "select section_cd , advice_no,advice_dt,advice_amt,advice_stat, advice_desc from ecgc_acct_pymt_advice_dtl where section_cd =:sectionCd and logical_loc_cd = :logicalLocCd and del_flag = false and advice_dt between :fromDt and :toDt";

	public static final String GET_APPR_PAYMENT_ADVICE_BY_DTL = "select advice_no , advice_amt , advice_dt ,advice_desc ,advice_stat , advice_desc, logical_loc_cd , section_cd  from ecgc_acct_pymt_advice_dtl eapad where section_cd =:sectionCd and logical_loc_cd = :logicalLocCd and fiscal_yr=:fiscalYear and del_flag = false and advice_stat = 'APRV'";

	public static final String GET_APRV_PAYMENT_ADVICE_DTL_BY_LOGLOC_AND_SECTION_CD =  "select section_cd , advice_no,advice_dt,advice_amt,advice_stat from ecgc_acct_pymt_advice_dtl where section_cd =:sectionCd and logical_loc_cd = :logicalLocCd and del_flag = false and advice_stat ='APRV'";

	public static final String GET_APRV_PAYMENT_ADVICE_NUMBER_BET_DT = "select section_cd , advice_no,advice_dt,advice_amt,advice_stat from ecgc_acct_pymt_advice_dtl where section_cd =:sectionCd and logical_loc_cd = :logicalLocCd and del_flag = false and advice_dt between :fromDt and :toDt and advice_stat ='APRV'";

	public static final String UPDATE_PAYMENT_ADVICE_TO_UNPAID = "UPDATE ecgc_acct_pymt_advice_dtl SET advice_stat = 'UNPAID',last_updated_by=:lastUpdatedBy, where logical_loc_cd = :logicalLocCd and section_cd = :sectionCd and advice_no = :adviceNo";

}

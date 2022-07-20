package in.ecgc.smile.erp.accounts.util;

public interface PaymentsQueries{
	
	public static String GET_PAYMENT_SEQ_NO="select ecgc_acct_gen_payments_func(:logicallocCd,:sectionCd,:fiscalYr)";
	
	public static String ADD_Payments_DATA="INSERT INTO ecgc_acct_payments(entity_cd,logicalloc_cd,pymt_no,pymt_dt,section_cd,advice_no,pay_to_type,pymt_party_cd,pymt_party_name,amt_paid,remarks,instrument_type,instrument_no,drawn_on,favouring,pymt_to_employee,pymt_in_forex,exchrate_at_bill_iwd,exchrate_at_pymt,module_cd,mapping_cd,gl_flg,gl_txn_type,gl_txn_no,fiscal_yr,old_cd,created_by,created_dt,meta_status,meta_remarks) VALUES (:entityCd,:logicallocCd,:paymentSeqNo,:pymtDt,:sectionCd,:adviceNo,:payToType,:pymtPartyCd,:pymtPartyName,:amtPaid,:remarks,:instrumentType,:instrumentNo,:drawnOn,:favouring,:pymtToEmployee,:pymtInForex,:exchrateAtBillIwd,:exchrateAtPymt,:moduleCd,:mappingCd,:glFlg,:glTxnType,:glTxnNo,:fiscalYr,:oldCd,:createdBy,now(),:metaStatus,:metaRemarks)";

	
	public static final String ADD_PAYMENTS_TCODES_DTL = 
			"INSERT INTO  ecgc_acct_payments_tcodes "
			+ "(entity_cd,logical_loc_cd, section_cd, payment_no, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, ad1, ad2, ad3, ad4, del_flag) "
			+ "VALUES(:entityCd,:logicalLocCd, :sectionCd, :paymentNo, :t1, :t2, :t3, :t4, :t5, :t6, :t7, :t8, :t9, :t10, :t11, :t12,:ad1,:ad2,:ad3,:ad4,false)";

	static String GET_PAYMENTS_BY_PAYMENT_NO = "SELECT * FROM ecgc_acct_payments  WHERE pymt_no=:pymtNo and logicalloc_cd = :logicalLocCd and section_cd = :sectionCd ";
	
	public static final String GET_PAYMENTS_TCODES_BY_PAYMENTS_DTL = "SELECT * FROM  ecgc_acct_payments_tcodes WHERE payment_no = :paymentNo and logical_loc_cd = :logicalLocCd and section_cd = :sectionCd";
	
	static String GET_Payments_DATA="SELECT * FROM ecgc_acct_payments";
	
	static String UPDATE_PAYMENTS_DATA="UPDATE ecgc_acct_payments SET pymt_dt=:pymtDt, advice_no=:adviceNo, pay_to_type=:payToType, pymt_party_cd=:pymtPartyCd, pymt_party_name=:pymtPartyName, amt_paid=:amtPaid, remarks=:remarks, instrument_type=:instrumentType, instrument_no=:instrumentNo, drawn_on=:drawnOn, favouring=:favouring, pymt_to_employee=:pymtToEmployee, pymt_in_forex=:pymtInForex, exchrate_at_bill_iwd=:exchrateAtBillIwd, exchrate_at_pymt=:exchrateAtPymt, old_cd=:oldCd, created_by=:createdBy, last_updated_by=:lastUpdatedBy, created_dt=:createdDt, last_updated_dt=:lastUpdatedDt, meta_status=:metaStatus, meta_remarks=:metaRemarks WHERE pymt_no=:pymtNo and entity_cd=:entityCd and logicalloc_cd=:logicallocCd";
	

	static String GET_PAYMENTS_BY_LOC_SEC_DT = "SELECT * FROM ecgc_acct_payments  WHERE logicalloc_cd = :logicalLocCd and section_cd = :sectionCd and pymt_dt between :fromDt and :toDt";
	
	static String GET_PAYMENTS_BY_DT = "select * from ecgc_acct_payments where pymt_dt between :fromDate and :toDate";
	
	static String GET_PAYMENTS_BY_LOC_SEC_FIS_YR = "SELECT * FROM ecgc_acct_payments  WHERE logicalloc_cd = :logicalLocCd and section_cd = :sectionCd and fiscal_yr=:fiscalYr";
	
	public static final String UPDATE_PAYMENTS_TCODES_DTL = "UPDATE accounts.ecgc_acct_payments_tcodes SET entity_cd= :entityCd, t1= :t1, t2= :t2, t3= :t3, t4= :t4, t5= :t5, t6= :t6, t7= :t7, t8= :t8, t9= :t9, t10= :t10, t11= :t11, t12= :t12, ad1= :ad1, ad2= :ad2, ad3= :ad3, ad4= :ad4 where logical_loc_cd = :logicalLocCd and section_cd = :sectionCd and payment_no = :paymentNo";

	public static final String GET_PAYMENTS_BY_PYMTNO_LOC_SEC = "select * from accounts.ecgc_acct_payments eap where pymt_no = :paymentNo and logicalloc_cd = :logicalLocCd and section_cd = :sectionCd ";
}

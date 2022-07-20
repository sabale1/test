package in.ecgc.smile.erp.accounts.util;

public interface GlTxnDtlPymtEntQueries{

	static String ADD_GLTXNDTLPYMTENT_DATA="INSERT INTO ecgc_acct_gl_txn_dtl_pymt_ent"
			+ "(payment_no,advice_no,section_cd,logicalloc_cd,gl_txn_type,sr_no,maingl_cd,subgl_cd,personal_ledger_cd, personal_ledger_level, sub_bifurcation_level, sub_bifurcation_code,dr_cr_flg,txn_amt,txn_rmk,user_id,created_by,created_dt,meta_status,meta_remarks,entity_cd) "
			+ "VALUES (:paymentNo,:adviceNo,:sectionCd,:logicallocCd,:glTxnType,:srNo,:mainglCd,:subglCd,:personalLedgerCd, :personalLedgerLevel, :subBifurcationLevel, :subBifurcationCode, :drCrFlg,:txnAmt,:txnRmk,:userId,:createdBy,now(),:metaStatus,:metaRemarks,:entityCd)";

	static String GET_GLTXNDTLPYMTENT_DATA="SELECT * FROM ecgc_acct_gl_txn_dtl_pymt_ent";

	static String UPDATE_GLTXNDTLPYMTENT_DATA="UPDATE ecgc_acct_gl_txn_dtl_pymt_ent SET payment_no=:paymentNo, advice_no=:adviceNo, section_cd=:sectionCd, logicalloc_cd=:logicallocCd, gl_txn_type=:glTxnType, sr_no=:srNo, maingl_cd=:mainglCd, subgl_cd=:subglCd, personal_ledger_cd=:personalLedgerCd, dr_cr_flg=:drCrFlg, txn_amt=:txnAmt, txn_rmk=:txnRmk, user_id=:userId,  last_updated_by=:lastUpdatedBy,  last_updated_dt=:lastUpdatedDt, meta_status=:metaStatus, meta_remarks=:metaRemarks, entity_cd=:entityCd WHERE payment_no=:paymentNo";

	static String GET_GLTXNDTLPYMTENT_BY_PAYMENT_DTL="SELECT a.*, b.gl_name\r\n"
			+ "FROM accounts.ecgc_acct_gl_txn_dtl_pymt_ent a\r\n"
			+ "inner join\r\n"
			+ "accounts.ecgc_acct_entity_gl_mst b\r\n"
			+ "on\r\n"
			+ "a.maingl_cd = b.maingl_cd and a.subgl_cd = b.subgl_cd "
			+ "WHERE payment_no=:paymentNo and logicalloc_cd =:logicallocCd and section_cd =:sectionCd";
}

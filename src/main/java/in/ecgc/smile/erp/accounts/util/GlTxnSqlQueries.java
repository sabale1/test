package in.ecgc.smile.erp.accounts.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;

public interface GlTxnSqlQueries {

	public static final String GET_ALL_GL_TXN_HDR = "select * from ecgc_acct_gl_txn_hdr where logical_loc_cd = :logicalLoc";

	public static final String GET_ALL_GL_TXN_HDR_2 = "select * from ecgc_acct_gl_txn_hdr";

	public static final String GET_ALL_GL_TXN_HDR_BY_TXNTYPE_AND_LOCATION = "select * from ecgc_acct_gl_txn_hdr where logical_loc_cd = :logicalLoc and gl_txn_type =:gltxntype;";

	public static final String GET_ALL_GL_TXN_DTL = "select * from ecgc_acct_gl_txn_dtl;";

	public static final String GET_TXN_HDR_BY_ID = "select * from ecgc_acct_gl_txn_hdr where gl_txn_no=:glTxnNo and logical_loc_cd = :logicalLoc and gl_txn_type= :glTxnType";

	public static final String GET_TXN_DTL_BY_ID = "select * from ecgc_acct_gl_txn_dtl where gl_txn_no=:glTxnNo and logical_loc_cd = :logicalLoc and gl_txn_type= :glTxnType";

	public static final String GET_TXN_BY_FROM_TO_DT = "select * from ecgc_acct_gl_txn_hdr where txn_dt between :fromDt and :toDt;";

	public static String GET_SEQ = "select ecgc_acct_gen_gltxnno_func(:logicalLoc,:glTxnType,:fiscalYr);";

	public static String UPDATE_SEQ = "UPDATE ecgc_acct_int_gl_seq_no_tbl"
			+ "	SET gl_txn_no=:glTxnNo where financial_yr =:fiscalYr and logical_loc_cd =:logicalLoc  and gl_txn_type=:glTxnType";

	public static final String UPDATE_HDR_FOR_REVARSAL = "UPDATE ecgc_acct_gl_txn_hdr "
			+ "SET  reversal_txn_type=:revarsalGlTxnType, reversal_txn_no=:revarsalGlTxnNo, reversal_reason=:reversalReason, last_updated_by=101, last_updated_dt='now()' "
			+ "WHERE logical_loc_cd=:logicalLoc AND gl_txn_type=:glTxnType AND gl_txn_no=:glTxnNo;";

	public static String UPDATE_GL_TXN = "UPDATE ecgc_acct_gl_txn_dtl"
			+ " SET  last_updated_by=101, last_updated_dt=now(), txn_remarks=:remarks"
			+ " WHERE gl_txn_type=:glTxnType AND gl_txn_no=:glTxnNo AND sr_no=:srNo AND logical_loc_cd=:logicalLoc;";

	public static final String GET_TOTAL_DEBIT_BAL = "select sum(txn_amt ) from ecgc_acct_gl_txn_dtl eagtd where maingl_cd = :mainGlCd and subgl_cd = :subGlCd and dr_cr_flg='Dr' ;";

	public static final String GET_TOTAL_CREDIT_BAL = "select sum(txn_amt ) from ecgc_acct_gl_txn_dtl eagtd where maingl_cd = :mainGlCd and subgl_cd = :subGlCd and dr_cr_flg='Cr' ;";

	public static final String DELETE_GL_TXN_HDR_BY_GL_TXN_NO = "DELETE FROM ecgc_acct_gl_txn_hdr WHERE gl_txn_no=:gl_txn_no";

	public static final String DELETE_GL_TXN_DTL_BY_GL_TXN_NO = "DELETE FROM ecgc_acct_gl_txn_dtl WHERE gl_txn_no=:gl_txn_no";

	public static final String ADD_GL_BR_BAL_YR = "INSERT INTO ecgc_acct_gl_br_bal_yr"
			+ " (entity_cd, logicalloc_cd, maingl_cd, subgl_cd, personal_ledger_cd, fiscal_yr,"
			+ " open_dr_bal, open_cr_bal, last_cr_bal, last_dr_bal, meta_status, created_by,"
			+ " created_dt, last_updated_by, last_updated_dt, meta_remarks, old_cd, code_type) "
			+ "VALUES('ECGC', :logicalLoc, :mainGlCd, :subGlCd, :plCd, :fiscalYr, :openDrBal, :openCrBal, :lastCrBal,"
			+ " :lastCrBal, :metaStatus, :createdBy, now(), :lastUpdatedBy, now(), :metaRemarks, :oldCd, :codeType);";

	public static GlTxnHdr mapRow(ResultSet rs, int rowNum) throws SQLException {
		GlTxnHdr glTxnHdr = new GlTxnHdr();
		try {
			glTxnHdr.setEntityCd("ECGC");
			glTxnHdr.setGlTxnNo(rs.getInt("gl_txn_no"));
			glTxnHdr.setGlTxnType(rs.getString("gl_txn_type"));
			glTxnHdr.setLogicalLocCd(rs.getString("logical_loc_cd"));
			if (rs.getDate("txn_dt") != null)
				glTxnHdr.setTxnDt(rs.getDate("txn_dt").toLocalDate());
			glTxnHdr.setReference(rs.getString("reference"));
			glTxnHdr.setRevarsalGlTxnType(rs.getString("reversal_txn_type"));
			glTxnHdr.setRevarsalGlTxnNo(rs.getInt("reversal_txn_no"));
			glTxnHdr.setReversalReason(rs.getString("reversal_reason"));
			glTxnHdr.setFiscalYr(rs.getString("fiscal_yr"));
			glTxnHdr.setCreatedBy(rs.getString("created_by"));
			glTxnHdr.setCreatedDt(rs.getDate("created_dt"));
			glTxnHdr.setLastUpdatedBy(rs.getString("last_updated_by"));
			glTxnHdr.setLastUpdatedDt(rs.getDate("last_updated_dt"));
			glTxnHdr.setMetaRemarks(rs.getString("meta_remarks"));
			glTxnHdr.setMetaStatus(rs.getString("meta_status"));
			return glTxnHdr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static GlTxnDtl mapRowForDtl(ResultSet rs, int rowNum) throws SQLException {
		GlTxnDtl glTxnDtl = new GlTxnDtl();
		try {
			glTxnDtl.setMainGlCd(rs.getString("maingl_cd"));
			glTxnDtl.setSubGlCd(rs.getString("subgl_cd"));
			glTxnDtl.setGlTxnType(rs.getString("gl_txn_type"));
			glTxnDtl.setGlTxnNo(rs.getInt("gl_txn_no"));
			glTxnDtl.setSrNo(rs.getInt("sr_no"));
			glTxnDtl.setPlCd(rs.getString("personal_ledger_cd"));
			glTxnDtl.setDrCrFlag(rs.getString("dr_cr_flg"));
			glTxnDtl.setTxnAmt(rs.getDouble("txn_amt"));
			glTxnDtl.setLogicalLocCd(rs.getString("logical_loc_cd"));
			glTxnDtl.setT1(rs.getString("t1"));
			glTxnDtl.setT2(rs.getString("t2"));
			glTxnDtl.setT3(rs.getString("t3"));
			glTxnDtl.setT4(rs.getString("t4"));
			glTxnDtl.setT5(rs.getString("t5"));
			glTxnDtl.setT6(rs.getString("t6"));
			glTxnDtl.setT7(rs.getString("t7"));
			glTxnDtl.setT8(rs.getString("t8"));
			glTxnDtl.setT9(rs.getString("t9"));
			glTxnDtl.setT10(rs.getString("t10"));
			glTxnDtl.setT11(rs.getString("t11"));
			glTxnDtl.setT12(rs.getString("t12"));
			glTxnDtl.setT13(rs.getString("t13"));
			glTxnDtl.setT14(rs.getString("t14"));

			glTxnDtl.setCodeType(rs.getString("code_type"));
			glTxnDtl.setSubBiFurcationCd(rs.getString("sub_bifurcation"));
			if (rs.getDate("ad4") != null)
				glTxnDtl.setAd4(rs.getDate("ad4").toLocalDate());
			if (rs.getDate("ad3") != null)
				glTxnDtl.setAd3(rs.getDate("ad3").toLocalDate());
			if (rs.getDate("ad2") != null)
				glTxnDtl.setAd2(rs.getDate("ad2").toLocalDate());
			if (rs.getDate("ad1") != null)
				glTxnDtl.setAd1(rs.getDate("ad1").toLocalDate());
			glTxnDtl.setCreatedBy(rs.getString("created_by"));
			glTxnDtl.setCreatedDt(rs.getDate("created_dt"));
			glTxnDtl.setLastUpdatedBy(rs.getString("last_updated_by"));
			glTxnDtl.setLastUpdatedDt(rs.getDate("last_updated_dt"));
			glTxnDtl.setMetaRemarks(rs.getString("meta_remarks"));
			glTxnDtl.setMetaStatus(rs.getString("meta_status"));
			glTxnDtl.setRemarks(rs.getString("txn_remarks"));
			return glTxnDtl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final String ADD_GL_TXN_HDR = "INSERT INTO ecgc_acct_gl_txn_hdr\r\n"
			+ "(entity_cd, logical_loc_cd, gl_txn_type, fiscal_yr, gl_txn_no, txn_dt, reference, reversal_txn_type, reversal_txn_no, reversal_reason, created_by, created_dt, last_updated_by, last_updated_dt, meta_status, meta_remarks)"
			+ "	VALUES('ECGC',:logicalLoc,:glTxnType,:fiscalYr,:glTxnNo,:txnDt,:reference,:revarsalGlTxnType,:revarsalGlTxnNo,:reversalReason,:createdBy,now(),:lastUpdatedBy,now(),:metaStatus,:metaRemarks);"
			+ "";

	public static Map<String, Object> getParamMapForHdr(GlTxnHdr glTxnHdr) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", 'V');
		paramMap.put("logicalLoc", glTxnHdr.getLogicalLocCd());
		paramMap.put("glTxnType", glTxnHdr.getGlTxnType());
		paramMap.put("glTxnNo", glTxnHdr.getGlTxnNo());
		paramMap.put("txnDt", glTxnHdr.getTxnDt());
		paramMap.put("reference", glTxnHdr.getReference());
		paramMap.put("revarsalGlTxnType", glTxnHdr.getRevarsalGlTxnType());
		paramMap.put("revarsalGlTxnNo", glTxnHdr.getRevarsalGlTxnNo());
		paramMap.put("reversalReason", glTxnHdr.getReversalReason());
		paramMap.put("fiscalYr", glTxnHdr.getFiscalYr());
		paramMap.put("metaStatus", glTxnHdr.getMetaStatus());
		paramMap.put("metaRemarks", glTxnHdr.getMetaRemarks());
		paramMap.put("createdBy", glTxnHdr.getCreatedBy());
		paramMap.put("createdDt", glTxnHdr.getCreatedDt());
		paramMap.put("lastUpdatedBy", glTxnHdr.getLastUpdatedBy());
		paramMap.put("lastUpdatedDt", glTxnHdr.getLastUpdatedDt());
		return paramMap;
	}

	public static final String ADD_GL_TXN_DTL = "INSERT INTO ecgc_acct_gl_txn_dtl\r\n"
			+ "(entity_cd, maingl_cd, subgl_cd, gl_txn_type, gl_txn_no, sr_no, personal_ledger_cd, dr_cr_flg, txn_amt, logical_loc_cd, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13,"
			+ "t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24, t25, t26, t27, t28, t29, t30, t31, t32, t33,"
			+ " ad1, ad2, created_by, created_dt, last_updated_by, last_updated_dt, meta_status, meta_remarks, ad3, ad4, code_type, sub_bifurcation,txn_remarks)"
			+ "VALUES('ECGC',:mainGlCd,:subGlCd,:glTxnType,:glTxnNo,:srNo,:plCd,:drCrFlag,:txnAmt,:logicalLoc,:t1,:t2,:t3,:t4,:t5,:t6,:t7,:t8,:t9,:t10,:t11,:t12,:t13,"
			+ ":t14,:t15,:t16,:t17,:t18,:t19,:t20,:t21,:t22,:t23,:t24,:t25,:t26,:t27,:t28,:t29,:t30,:t31,:t32,:t33,"
			+ " :ad1, :ad2, :createdBy,now(),:lastUpdatedBy,now(),:metaStatus,:metaRemarks,:ad3,:ad4,:codeType,:subBiFurcationCd,:remarks);"
			+ "";

	public static Map<String, Object> getParamMapForDtl(GlTxnDtl glTxnDtl) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", 'V');
		paramMap.put("logicalLoc", glTxnDtl.getLogicalLocCd());
		paramMap.put("glTxnType", glTxnDtl.getGlTxnType());
		paramMap.put("glTxnNo", glTxnDtl.getGlTxnNo());
		paramMap.put("srNo", glTxnDtl.getSrNo());
		paramMap.put("drCrFlag", glTxnDtl.getDrCrFlag());
		paramMap.put("txnAmt", glTxnDtl.getTxnAmt());
		paramMap.put("remarks", glTxnDtl.getRemarks());
		paramMap.put("oldCode", glTxnDtl.getOldCode());
		paramMap.put("codeType", glTxnDtl.getCodeType());
		paramMap.put("mainGlCd", glTxnDtl.getMainGlCd());
		paramMap.put("subGlCd", glTxnDtl.getSubGlCd());
		paramMap.put("plCd", glTxnDtl.getPlCd());
		paramMap.put("subBiFurcationCd", glTxnDtl.getSubBiFurcationCd());
		paramMap.put("t1", glTxnDtl.getT1());
		paramMap.put("t2", glTxnDtl.getT2());
		paramMap.put("t3", glTxnDtl.getT3());
		paramMap.put("t4", glTxnDtl.getT4());
		paramMap.put("t5", glTxnDtl.getT5());
		paramMap.put("t6", glTxnDtl.getT6());
		paramMap.put("t7", glTxnDtl.getT7());
		paramMap.put("t8", glTxnDtl.getT8());
		paramMap.put("t9", glTxnDtl.getT9());
		paramMap.put("t10", glTxnDtl.getT10());
		paramMap.put("t11", glTxnDtl.getT11());
		paramMap.put("t12", glTxnDtl.getT12());
		paramMap.put("t13",glTxnDtl.getT13());
		paramMap.put("t14",glTxnDtl.getT14());
		paramMap.put("t15",glTxnDtl.getT15());
		paramMap.put("t16",glTxnDtl.getT16());
		paramMap.put("t17",glTxnDtl.getT17());
		paramMap.put("t18",glTxnDtl.getT18());
		paramMap.put("t19",glTxnDtl.getT19());
		paramMap.put("t20",glTxnDtl.getT20());
		paramMap.put("t21",glTxnDtl.getT21());
		paramMap.put("t22",glTxnDtl.getT22());
		paramMap.put("t23",glTxnDtl.getT23());
		paramMap.put("t24",glTxnDtl.getT24());
		paramMap.put("t25",glTxnDtl.getT25());
		paramMap.put("t26",glTxnDtl.getT26());
		paramMap.put("t27",glTxnDtl.getT27());
		paramMap.put("t28",glTxnDtl.getT28());
		paramMap.put("t29",glTxnDtl.getT29());
		paramMap.put("t30",glTxnDtl.getT30());
		paramMap.put("t31",glTxnDtl.getT31());
		paramMap.put("t32",glTxnDtl.getT32());
		paramMap.put("t33",glTxnDtl.getT33());

		paramMap.put("ad1", glTxnDtl.getAd1());
		paramMap.put("ad2", glTxnDtl.getAd2());
		paramMap.put("ad3", glTxnDtl.getAd3());
		paramMap.put("ad4", glTxnDtl.getAd4());
		paramMap.put("metaStatus", glTxnDtl.getMetaStatus());
		paramMap.put("metaRemarks", glTxnDtl.getMetaRemarks());
		paramMap.put("createdBy", glTxnDtl.getCreatedBy());
		paramMap.put("createdDt", glTxnDtl.getCreatedDt());
		paramMap.put("lastUpdatedBy", glTxnDtl.getLastUpdatedBy());
		paramMap.put("lastUpdatedDt", glTxnDtl.getLastUpdatedDt());
		return paramMap;
	}

	public static final String CHECK_GL_BR_ENRTY = "SELECT * FROM accounts.ecgc_acct_gl_br_bal where maingl_cd=:mainGlCd and subgl_cd=:subGlCd and \"month\" =:month "
			+ " and logical_loc_cd =:logicalLoc and fiscal_yr =:fiscalYr and personal_ledger_cd =:plCd ";

	public static final String INSERT_CURR_CR_BAL = "INSERT INTO accounts.ecgc_acct_gl_br_bal"
			+ "(entity_cd, logical_loc_cd, personal_ledger_cd, fiscal_yr, \"month\", open_dr_bal, open_cr_bal, curr_cr_bal, curr_dr_bal, created_by, old_cd, code_type, created_dt, last_updated_by, last_updated_dt, meta_status, meta_remarks, maingl_cd, subgl_cd)"
			+ " VALUES('ECGC', :logicalLoc, :plCd, :fiscalYr, :month, 0, 0, 0, 0, :user,NULL,NULL,now(),:user,now(),'Y',NULL,:mainGlCd,:subGlCd)";

	public static final String CHECK_GL_BR_YR_ENRTY = "SELECT * FROM accounts.ecgc_acct_gl_br_bal_yr where maingl_cd=:mainGlCd and subgl_cd=:subGlCd"
			+ " and logicalloc_cd =:logicalLoc and fiscal_yr =:fiscalYr and personal_ledger_cd =:plCd ";

	public static final String INSERT_CURR_CR_BAL_YR = "INSERT INTO accounts.ecgc_acct_gl_br_bal_yr"
			+ "(entity_cd, logicalloc_cd, maingl_cd, subgl_cd, personal_ledger_cd, fiscal_yr, open_dr_bal, open_cr_bal, last_cr_bal, last_dr_bal, meta_status, created_by, created_dt, last_updated_by, last_updated_dt, meta_remarks, old_cd, code_type)"
			+ " VALUES('ECGC', :logicalLoc, :mainGlCd,:subGlCd,:plCd, :fiscalYr, 0, 0, 0, 0, 'Y',:user,now(),:user,now(),NULL,null,null)";

	public static final String UPDATE_CURR_CR_BAL = "update ecgc_acct_gl_br_bal set curr_cr_bal = curr_cr_bal+:amt where maingl_cd=:mainGlCd and subgl_cd=:subGlCd and \"month\" =:month "
			+ " and logical_loc_cd =:logicalLoc and fiscal_yr =:fiscalYr and personal_ledger_cd =:plCd ;";

	public static final String UPDATE_CURR_DR_BAL = "update ecgc_acct_gl_br_bal set curr_dr_bal = curr_dr_bal+ :amt where maingl_cd=:mainGlCd and subgl_cd=:subGlCd and \"month\" =:month "
			+ " and logical_loc_cd =:logicalLoc and fiscal_yr =:fiscalYr and personal_ledger_cd =:plCd ";

	public static final String UPDATE_CURR_DR_BAL_YR = "UPDATE ecgc_acct_gl_br_bal_yr SET "
			+ "last_dr_bal= last_dr_bal+:amt, last_updated_dt=now() WHERE maingl_cd=:mainGlCd AND logicalloc_cd=:logicalLoc AND subgl_cd=:subGlCd AND personal_ledger_cd=:plCd AND fiscal_yr=:fiscalYr;";

	public static final String UPDATE_CURR_CR_BAL_YR = "UPDATE ecgc_acct_gl_br_bal_yr SET "
			+ "last_cr_bal= last_cr_bal+:amt, last_updated_dt=now() WHERE maingl_cd=:mainGlCd AND logicalloc_cd=:logicalLoc AND subgl_cd=:subGlCd AND personal_ledger_cd=:plCd AND fiscal_yr=:fiscalYr;";

	public static final String GET_TXN_BY_FROM_TO_DT_AND_LOC = "select * from accounts.ecgc_acct_gl_txn_hdr where logical_loc_cd = :logicalLoc and txn_dt between :fromDt and :toDt;";

	public static final String GET_ALL_GL_TXN_BY_TXNNO_TXNTYPE_LOC = "select * from ecgc_acct_gl_txn_hdr where gl_txn_no=:glTxnNo and gl_txn_type= :glTxnType and logical_loc_cd = :logicalLoc";

	public static final String GET_GL_BR_BAL_YR = "select * from ecgc_acct_gl_br_bal_yr where maingl_cd=:mainGlCd and subgl_cd= :subGlCd and personal_ledger_cd = :plCd and fiscal_yr= :fiscalYr and logicalloc_cd = :logicalLoc";

	public static final String GET_TXN_TYPE_GLCODE = "select * from accounts.ecgc_acct_entity_gl_mst eaegm where maingl_cd =:mainGlCd and subgl_cd =:subGlCd";

	public static final String GET_POLICY_REPORT = ""
			+ "select h.txn_dt , d.txn_remarks , d.gl_txn_type , d.gl_txn_no , d.txn_amt , d.dr_cr_flg  from "
			+ "accounts.ecgc_acct_gl_txn_dtl d "
			+ "inner join  "
			+ "accounts.ecgc_acct_gl_txn_hdr h  "
			+ "on  "
			+ "h.gl_txn_no = d.gl_txn_no and  "
			+ "h.logical_loc_cd  = d.logical_loc_cd and  "
			+ "h.gl_txn_type = d.gl_txn_type  "
			+ "where  "
			+ "d.maingl_cd  = :mainGl and d.subgl_cd  = :subGl and "
			+ "d.personal_ledger_cd = :plCd and d.logical_loc_cd  = :logLocCd and  "
			+ "d.sub_bifurcation =:subBfCd and  "
			+ "h.txn_dt between :fromDt and :toDt ";

}

package in.ecgc.smile.erp.accounts.util;

public interface PayInSlipSqlQueries {

	public static final String VIEW_ALL_PAY_IN_SLIP_RECEIPTS = "SELECT * FROM accounts.ecgc_acct_rcpts where instrument_type not in ('HDFC','NEFT','RTGS','SWIFT','opmt') and logical_loc_cd = :logicalLocCode";
	
	public static final String VIEW_BY_LOGICAL_LOC_CODE_AND_RECEIPT_NO = "SELECT * FROM accounts.ecgc_acct_rcpts where instrument_type not in ('HDFC','NEFT','RTGS','SWIFT','opmt') and logical_loc_cd = :logicalLocCode and rcpt_no = :receiptNumber";
	
//	public static final String VIEW_BY_FROMDT_TODT_AND_INSRUMENTTYPE = "SELECT * FROM accounts.ecgc_acct_rcpts where instrument_type in (:instr0, :instr1, :instr2, :instr3, :instr4) and rcpt_dt between :fDate and :tDate";
}

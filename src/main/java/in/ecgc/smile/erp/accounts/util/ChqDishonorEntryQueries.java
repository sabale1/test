package in.ecgc.smile.erp.accounts.util;


public interface ChqDishonorEntryQueries{
static String ADD_ChqDishonorEntry_DATA="INSERT INTO ecgc_acct_chq_dishonor_entry(logical_loc_cd,rcpt_no,dishonor_dt,instrument_no,instrument_type,dishonor_reason,fiscal_yr,created_by,created_dt,gl_txn_no,reversal_txn_no,old_rcpt_no,remarks,last_updated_by,last_updated_dt)"
		+ " VALUES (:logicalLocCd,:rcptNo,:dishonorDt,:instrumentNo,:instrumentType,:dishonorReason,:fiscalYr,:createdBy,now(),:glTxnNumber,:revarsalGlTxnNo,:oldReceiptNumber,:chremark,:lastUpdatedBy,now())";
static String GET_ChqDishonorEntry_DATA="SELECT * FROM ecgc_acct_chq_dishonor_entry";
static String UPDATE_ChqDishonorEntry_DATA="UPDATE ecgc_acct_chq_dishonor_entry SET logical_loc_cd=:logicalLocCd, dishonor_dt=:dishonorDt, instrument_no=:instrumentNo, instrument_type=:instrumentType, dishonor_reason=:dishonorReason,  fiscal_yr=:fiscalYr, remarks=:chremark, last_updated_by=:lastUpdatedBy, last_updated_dt=now() WHERE logical_loc_cd =:logicalLocCd and rcpt_no=:rcptNo";
static String GET_ChqDishonorEntry_BY_CHEQUE_NO="SELECT * FROM ecgc_acct_chq_dishonor_entry WHERE instrument_no=:instrumentNo";
static String GET_ALL_RECEIPT_DETAILS_BY_INSTRUMENT_TYPE="SELECT * FROM ecgc_acct_rcpts WHERE instrument_type=:instrumentType";
static String GET_RECEIPT_DETAILS="SELECT * FROM accounts.ecgc_acct_rcpts WHERE instrument_type = :instrumentType and instrument_no = :instrumentNumber and instrument_dt = :instrumentDate and rcpt_no = :receiptNumber ";
public static final String VIEW_BY_LOGICAL_LOC_CODE_AND_RECEIPT_NO="select  * from ecgc_acct_chq_dishonor_entry where logical_loc_cd =:logicalLocCode and rcpt_no=:receiptNumber;";
public static final String UPDATE_Gl_FLAG="update accounts.ecgc_acct_rcpts set gl_flg ='R' where logical_loc_cd =:logicalLocCd and rcpt_no=:rcptNo; ";


}

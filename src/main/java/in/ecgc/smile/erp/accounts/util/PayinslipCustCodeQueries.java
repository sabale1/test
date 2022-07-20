package in.ecgc.smile.erp.accounts.util;

public interface PayinslipCustCodeQueries{
static String ADD_PayinslipCustCode_DATA="INSERT INTO accounts.ecgc_acct_payinslip_cust_code(logicalloc_cd,customer_cd,bank_name,bank_address,b_acct_no,neft_code,ifsc_code,dr_maingl_cd,dr_subgl_cd,cr_maingl_cd,cr_subgl_cd,bank_maingl_cd,bank_subgl_cd,created_by,last_updated_by,created_dt,last_updated_dt,meta_status,meta_remarks,gstin,gstinstate,beneficiary_acct_name,beneficiary_code_ibank) VALUES (:logicallocCd,:customerCd,:bankName,:bankAddress,:bAcctNo,:neftCode,:ifscCode,:drMainglCd,:drSubglCd,:crMainglCd,:crSubglCd,:bankMainglCd,:bankSubglCd,:createdBy,:lastUpdatedBy,:createdDt,:lastUpdatedDt,:metaStatus,:metaRemarks,:gstin,:gstinstate,:beneficiaryAcctName,:beneficiaryCodeIbank)";
static String GET_PayinslipCustCode_DATA="SELECT * FROM ecgc_acct_payinslip_cust_code";
static String UPDATE_PayinslipCustCode_DATA="UPDATE ecgc_acct_payinslip_cust_code SET  customer_cd=:customerCd, bank_name=:bankName, bank_address=:bankAddress,"
		+ " b_acct_no=:bAcctNo,"
		+ " neft_code=:neftCode, ifsc_code=:ifscCode, dr_maingl_cd=:drMainglCd, dr_subgl_cd=:drSubglCd,"
		+ " cr_maingl_cd=:crMainglCd, cr_subgl_cd=:crSubglCd,"
		+ " bank_maingl_cd=:bankMainglCd, bank_subgl_cd=:bankSubglCd, created_by=:createdBy, last_updated_by=:lastUpdatedBy, "
		+ "created_dt=:createdDt, last_updated_dt=:lastUpdatedDt, meta_status=:metaStatus, meta_remarks=:metaRemarks, gstin=:gstin, "
		+ "gstinstate=:gstinstate, beneficiary_acct_name=:beneficiaryAcctName, beneficiary_code_ibank=:beneficiaryCodeIbank"
		+ " WHERE logicalloc_cd=:logicallocCd";

static String GET_PayinslipCustCode_DATA_BY_ID="SELECT * FROM ecgc_acct_payinslip_cust_code WHERE customer_cd=:customerCd";
static String VIEW_ALL_LOGICAL_LOC="SELECT * FROM accounts.ecgc_acct_payinslip_cust_code WHERE logicalloc_cd=:logicallocCd";

}

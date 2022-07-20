package in.ecgc.smile.erp.accounts.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditDtl;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;

public interface PaymentEmployeeDirectCreditSqlQueries {

	public static final String VIEW_ALL_LOGICAL_LOC = "select * from accounts.ecgc_acct_pymt_emp_req_hdr where pe_req_loc = :logicalLoc;";

	public static PaymentEmployeeDirectCreditHdr mapRow(ResultSet rs, int rowNum) throws SQLException {
		PaymentEmployeeDirectCreditHdr pymtEmpHdr = new PaymentEmployeeDirectCreditHdr();

		try {
			pymtEmpHdr.setRequestNo(rs.getString("pe_no"));
			pymtEmpHdr.setRequestedLogicalLoc(rs.getString("pe_req_loc"));
			pymtEmpHdr.setDepartmentCode(rs.getString("pe_dpt_code"));

			if (rs.getDate("pe_req_dt") != null)
				pymtEmpHdr.setRequestDate(rs.getDate("pe_req_dt").toLocalDate());
			pymtEmpHdr.setCreatedBy(rs.getString("created_by"));
			if (rs.getDate("created_dt") != null)
				pymtEmpHdr.setCreatedDate(rs.getDate("created_dt").toLocalDate());
			pymtEmpHdr.setLastUpdatedBy(rs.getString("last_updated_by"));
			if (rs.getDate("last_updated_dt") != null)
				pymtEmpHdr.setLastUpdateDt(rs.getDate("last_updated_dt").toLocalDate());
			
			return pymtEmpHdr;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static final String VIEW_ALL_DIRECT_CREDITS = "select * from accounts.ecgc_acct_pymt_emp_req_hdr;";

	public static final String ADD_PAYMENTEMPLOYMENTDIRECTCREDIT_HDR = "INSERT INTO "
			+ "accounts.ecgc_acct_pymt_emp_req_hdr(pe_no,pe_dpt_code,pe_req_loc,pe_created_by,pe_req_dt,"
			+ "created_by,last_updated_by,created_dt,last_updated_dt,meta_status,meta_remarks)"
			+ " VALUES(:requestNo,:departmentCode,:requestedLogicalLoc,:requestCreatedBy,:requestDate,:createdBy,:lastUpdatedBy,:createdDate,:lastUpdateDt,:metaStatus,:metaRemarks)";

	public static final String ADD_PAYMENTEMPLOYMENTDIRECTCREDIT_DTL = /*
																		 * " INSERT INTO " +
																		 * "accounts.ecgc_acct_pymt_emp_req_dtl(pe_no,pe_req_srno,pe_req_dt,pe_emp_no,pe_emp_name,pe_brch_cd,pe_dbt_crd,pe_acc_subtype,pe_acc_type,pe_acc_no,"
																		 * +
																		 * "pe_amt,pe_remark,mail_sent,status,pe_approved_by,pe_approved_date,created_by,last_updated_by,created_dt,last_updated_dt)"
																		 * +"VALUES (:requestNo,:requestSerialNumber,:requestDate,:employeeNo,:employeeName,:branchCode,:debitCredit,:accountSubType,:accountType,:accountNumber,:amount,"
																		 * +
																		 * ":remarks,:maiSentFlag,:status,:approved_by,:approvedDate,:createdBy,:lastUpdatedBy,:createdDate,:lastUpdatedDate)";
																		 */
			"INSERT INTO accounts.ecgc_acct_pymt_emp_req_dtl(pe_no,pe_req_srno,pe_req_dt,pe_emp_no,"
					+ "pe_emp_name,pe_brch_cd,pe_dbt_crd,pe_acc_subtype,pe_acc_type,pe_acc_no,pe_amt,"
					+ "pe_remark,mail_sent,status,pe_approved_by,pe_approved_date,created_by,last_updated_by,created_dt,last_updated_dt)"
					+ "VALUES (:requestNo,:requestSerialNumber,:requestDate,:employeeNo,:employeeName,:branchCode,:debitCredit,:accountSubType,:accountType,:accountNumber,:amount,"
					+ ":remarks,:maiSentFlag,:status,:approved_by,:approvedDate,:createdBy,:lastUpdatedBy,:createdDate,:lastUpdatedDate)";

	public static final String GET_SEQ_NO = "SELECT ecgc_acct_paymt_emp_direct_credit_func(:requestedLogicalLoc) as seq_text;";

	public static Map<String, Object> getParamMapforHdr(PaymentEmployeeDirectCreditHdr payEmp) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("requestNo", payEmp.getRequestNo());
		paramMap.put("departmentCode", payEmp.getDepartmentCode());
		paramMap.put("requestedLogicalLoc", payEmp.getRequestedLogicalLoc());
		paramMap.put("requestCreatedBy", payEmp.getRequestCreatedBy());
		paramMap.put("requestDate", payEmp.getRequestDate());
		paramMap.put("createdBy", payEmp.getCreatedBy());
		paramMap.put("lastUpdatedBy", payEmp.getLastUpdatedBy());
		paramMap.put("createdDate", payEmp.getCreatedDate().now());
		paramMap.put("lastUpdateDt", payEmp.getLastUpdateDt());
		paramMap.put("metaStatus", payEmp.getMetaStatus());
		paramMap.put("metaRemarks", payEmp.getMetaRemarks());
		paramMap.put("PymtEmpDtl", payEmp.getPymtEmpList());
	
		return paramMap;
	}

	public static Map<String, Object> getParamMapforDtl(PaymentEmployeeDirectCreditDtl pymtEmpDtl) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("requestNo", pymtEmpDtl.getRequestNo());
		paramMap.put("requestSerialNumber", pymtEmpDtl.getRequestSerialNumber());
		paramMap.put("branchCode", pymtEmpDtl.getBranchCode());
		paramMap.put("debitCredit", pymtEmpDtl.getDebitCredit());
		paramMap.put("requestDate", pymtEmpDtl.getRequestDate());
		paramMap.put("employeeNo", pymtEmpDtl.getEmployeeNo());
		paramMap.put("employeeName", pymtEmpDtl.getEmployeeName());
		paramMap.put("accountSubType", pymtEmpDtl.getAccountSubType());
		paramMap.put("accountType", pymtEmpDtl.getAccountType());
		paramMap.put("accountNumber", pymtEmpDtl.getAccountNumber());
		paramMap.put("amount", pymtEmpDtl.getAmount());
		paramMap.put("remarks", pymtEmpDtl.getRemarks());
		paramMap.put("lastUpdatedBy", pymtEmpDtl.getLastUpdatedBy());
		paramMap.put("lastUpdatedDate", pymtEmpDtl.getLastUpdatedDate());
		paramMap.put("maiSentFlag", pymtEmpDtl.getMaiSentFlag());
		paramMap.put("status", pymtEmpDtl.getStatus());
		paramMap.put("approved_by", pymtEmpDtl.getApproved_by());
		paramMap.put("approvedDate", pymtEmpDtl.getApprovedDate());
		paramMap.put("createdDate", pymtEmpDtl.getCreatedDate());
		paramMap.put("createdBy", pymtEmpDtl.getCreatedBy());

		return paramMap;
	}

	public static final String PYMT_EMP_HDR_VIEWALL = "select * from accounts.ecgc_acct_pymt_emp_req_hdr where pe_no=:requestNo and pe_req_loc =:requestedLogicalLoc and  pe_dpt_code =:departmentCode";

	public static final String PYMT_EMP_DTL_VIEWALL = "select * from accounts.ecgc_acct_pymt_emp_req_dtl where pe_no=:requestNo ";

	
	  public static final String UPDATE_PYMT_EMP_DETAIL =
	  "UPDATE accounts.ecgc_acct_pymt_emp_req_dtl " +
	  "SET pe_approved_date=now(), status='A', pe_approved_by=:approved_by  WHERE pe_req_srno=:requestSerialNumber"
	  ;
	  
	  
	 
	/*
	 * public static final String UPDATE_PYMT_EMP_DETAIL =
	 * "select * FROM accounts.ecgc_acct_pymt_emp_req_dtl   INNER JOIN accounts.ecgc_acct_pymt_emp_req_hdr "
	 * +
	 * "  ON accounts.ecgc_acct_pymt_emp_req_dtl.pe_no = accounts.ecgc_acct_pymt_emp_req_hdr.pe_no  AND accounts.ecgc_acct_pymt_emp_req_dtl.pe_req_srno=:requestSerialNumber"
	 * ;
	 */
	public static final String PYMT_EMP_HDR_VIEWALL_NULL = "select * from accounts.ecgc_acct_pymt_emp_req_hdr where pe_no=:requestNo and pe_req_loc =:requestedLogicalLoc and pe_dpt_code is null ";

	public static final String PYMT_EMP_HDR_VIEWBYREQUESTNO = "select * from accounts.ecgc_acct_pymt_emp_req_hdr where pe_no=:requestNo";

	public static PaymentEmployeeDirectCreditDtl mapRowDtl(ResultSet rs, int rowNum) throws SQLException {
		PaymentEmployeeDirectCreditDtl pymtEmpDtl = new PaymentEmployeeDirectCreditDtl();
		try {
			pymtEmpDtl.setRequestNo(rs.getString("pe_no"));
			pymtEmpDtl.setRequestSerialNumber(rs.getInt("pe_req_srno"));
			pymtEmpDtl.setEmployeeNo(rs.getInt("pe_emp_no"));
			pymtEmpDtl.setAccountSubType(rs.getString("pe_acc_subtype"));
			pymtEmpDtl.setAccountType(rs.getString("pe_acc_type"));
			pymtEmpDtl.setBranchCode(rs.getString("pe_brch_cd"));
			pymtEmpDtl.setDebitCredit(rs.getString("pe_dbt_crd"));
			pymtEmpDtl.setEmployeeName(rs.getString("pe_emp_name"));
			pymtEmpDtl.setAccountNumber(rs.getLong("pe_acc_no"));
			pymtEmpDtl.setAmount(rs.getDouble("pe_amt"));
			pymtEmpDtl.setMaiSentFlag(rs.getString("mail_sent"));
			 
			pymtEmpDtl.setStatus(rs.getString("status"));
			pymtEmpDtl.setApproved_by(rs.getInt("pe_approved_by"));
			

			pymtEmpDtl.setLastUpdatedBy(rs.getString("last_updated_by"));
			pymtEmpDtl.setCreatedBy(rs.getString("created_by"));
			if (rs.getDate("pe_req_dt") != null)
				pymtEmpDtl.setRequestDate(rs.getDate("pe_req_dt").toLocalDate());

			if (rs.getDate("created_dt") != null)
				pymtEmpDtl.setCreatedDate(rs.getDate("created_dt").toLocalDate());
			if (rs.getDate("last_updated_dt") != null)
				pymtEmpDtl.setLastUpdatedDate(rs.getDate("last_updated_dt").toLocalDate());

			return pymtEmpDtl;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}

package in.ecgc.smile.erp.accounts.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.exception.DatabaseOperationFailException;
import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.util.CommonFunctions;
import in.ecgc.smile.erp.accounts.util.PayInSlipSqlQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public class PayInSlipDaoImpl implements PayInSlipDao {

	@Autowired
	private CommonFunctions commonFunctions;
	private JdbcOperations jdbcOperations;
	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	UserInfoService userInfoService;
	
	public PayInSlipDaoImpl(DataSource dataSource) {
		jdbcOperations = new JdbcTemplate(dataSource);
		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public List<Receipt> listAllReceipts() {
		log.info("Inside PayInSlipDaoImpl#listAllReceipts");
		List<Receipt> receipts = new ArrayList<Receipt>();
		Map<String, String> paramMap = new HashMap<>();
		String logicalLocCode = userInfoService.getPrimaryOffice();
		paramMap.put("logicalLocCode", logicalLocCode);
		
		try {
			receipts = namedParameterJdbcTemplate.query(PayInSlipSqlQueries.VIEW_ALL_PAY_IN_SLIP_RECEIPTS,paramMap, new RowMapper<Receipt>() {

				@Override
				public Receipt mapRow(ResultSet rs, int rowNum) throws SQLException {
					Receipt receipt = new Receipt();
					receipt.setLogicalLocCode(rs.getString("logical_loc_cd"));
					receipt.setGlFlag(rs.getString("gl_flg"));
					receipt.setMetaRemarks(rs.getString("meta_remarks"));
					receipt.setCreatedBy( rs.getString("created_by"));
					if(rs.getDate("created_dt") !=null)
					receipt.setCreatedDate(rs.getDate("created_dt").toLocalDate());
					receipt.setDrawnOn(rs.getString("drawn_on"));
					receipt.setMetaStatus(rs.getString("meta_status"));
					receipt.setFiscalYear(rs.getString("fiscal_yr"));
					receipt.setGlTxnNumber(rs.getInt("gl_txn_no"));
					receipt.setGlTxnNumberOld(rs.getInt("gl_txn_no_old"));
					receipt.setGlTxnType(rs.getString("gl_txn_type"));
					if(rs.getDate("instrument_dt") !=null)
					receipt.setInstrumentDate(rs.getDate("instrument_dt").toLocalDate());
					receipt.setInstrumentNumber(rs.getString("instrument_no"));
					receipt.setInstrumentType(rs.getString("instrument_type"));
					receipt.setLastUpdatedBy(rs.getString("last_updated_by"));
					receipt.setStampAmount(rs.getDouble("stamp_amt"));
					receipt.setRemarks(rs.getString("remarks"));
					receipt.setReceivedfromType(rs.getString("rcvd_from_type"));
					receipt.setReceivedFromName(rs.getString("rcvd_from_name"));
					receipt.setReceivedFromCode(rs.getString("rcvd_from_cd"));
					receipt.setReceivedFromAddress(rs.getString("rcvd_from_addr"));
					receipt.setReceiptNumber(rs.getInt("rcpt_no"));
					if(rs.getDate("rcpt_dt") !=null)
						receipt.setReceiptDate(rs.getDate("rcpt_dt").toLocalDate());
					receipt.setReceiptAmount(rs.getDouble("rcpt_amt"));
					receipt.setIwdNumber(rs.getString("iwd_no"));
					receipt.setPayInSlip(rs.getString("pay_in_slip"));
					receipt.setOldReceiptNumber(rs.getInt("old_rcpt_no"));
					receipt.setEntityCd(rs.getString("entity_cd"));
					receipt.setProductDescription(rs.getString("product_description"));
					receipt.setHsn(rs.getDouble("hsn"));
					receipt.setUom(rs.getDouble("uom"));
					receipt.setQty(rs.getDouble("qty"));
					receipt.setRate(rs.getDouble("rate"));
					receipt.setAmount(rs.getDouble("amount"));
					receipt.setDiscount(rs.getDouble("discount"));
					receipt.setTaxableDiscount(rs.getDouble("taxable_discount"));
					receipt.setCustomerGstNo(rs.getString("customer_gst_no"));
					receipt.setEcgcGstNo(rs.getString("ecgc_gst_no"));
					receipt.setInvoiceNo(rs.getString("invoice_no"));
					receipt.setCgstAmount(rs.getDouble("cgst_amount"));
					receipt.setSgstAmount(rs.getDouble("sgst_amount"));
					receipt.setIgstAmount(rs.getDouble("igst_amount"));
					receipt.setUtgstAmount(rs.getDouble("utgst_amount"));
					receipt.setCgstTaxPer(rs.getDouble("cgst_tax_per"));
					receipt.setSgstTaxPer(rs.getDouble("sgst_tax_per"));
					receipt.setIgstTaxPer(rs.getDouble("igst_tax_per"));
					receipt.setUtgstTaxPer(rs.getDouble("utgst_tax_per"));
					receipt.setExpoState(rs.getString("expo_state"));
					receipt.setBranchState(rs.getString("branch_state"));
					receipt.setTaxType(rs.getString("tax_type"));
					receipt.setOldInvoiceNo(rs.getString("old_invoice_no"));
					receipt.setUpdateFlag(rs.getString("update_flg"));
					receipt.setSez(rs.getString("sez"));
					
					return receipt;				
				}

				
			});
			return receipts;
		}catch(DataAccessException e) {
			throw new DataAccessResourceFailureException("Failed to fetch data from database");
		}catch(Exception e) {
			throw new DatabaseOperationFailException("Failed to perform database operation");
		}
	
	}

	@Override
	public Receipt viewPayInSlipByLogicalLocCodeAndReceiptNo(String logicalLocCode, Integer receiptNumber) {
		log.info("Inside PayInSlipDaoImpl#viewByLogicalLocCodeAndReceiptNo");
		Receipt receiptlist = new Receipt();
		Map<String, Object> paraMmap = new HashMap<>();
		paraMmap.put("logicalLocCode", logicalLocCode);
		paraMmap.put("receiptNumber", receiptNumber);
		System.err.println(logicalLocCode +" "+receiptNumber);
		try {
			receiptlist = namedParameterJdbcTemplate.queryForObject(PayInSlipSqlQueries.VIEW_BY_LOGICAL_LOC_CODE_AND_RECEIPT_NO, paraMmap, new RowMapper<Receipt>() {

				@Override
				public Receipt mapRow(ResultSet rs, int rowNum) throws SQLException {
					Receipt receipt = new Receipt();
					receipt.setLogicalLocCode(rs.getString("logical_loc_cd"));
					receipt.setGlFlag(rs.getString("gl_flg"));
					receipt.setMetaRemarks(rs.getString("meta_remarks"));
					receipt.setCreatedBy( rs.getString("created_by"));
					if(rs.getDate("created_dt") !=null)
					receipt.setCreatedDate(rs.getDate("created_dt").toLocalDate());
					receipt.setDrawnOn(rs.getString("drawn_on"));
					receipt.setMetaStatus(rs.getString("meta_status"));
					receipt.setFiscalYear(rs.getString("fiscal_yr"));
					receipt.setGlTxnNumber(rs.getInt("gl_txn_no"));
					receipt.setGlTxnNumberOld(rs.getInt("gl_txn_no_old"));
					receipt.setGlTxnType(rs.getString("gl_txn_type"));
					if(rs.getDate("instrument_dt") !=null)
					receipt.setInstrumentDate(rs.getDate("instrument_dt").toLocalDate());
					receipt.setInstrumentNumber(rs.getString("instrument_no"));
					receipt.setInstrumentType(rs.getString("instrument_type"));
					receipt.setLastUpdatedBy(rs.getString("last_updated_by"));
					receipt.setStampAmount(rs.getDouble("stamp_amt"));
					receipt.setRemarks(rs.getString("remarks"));
					receipt.setReceivedfromType(rs.getString("rcvd_from_type"));
					receipt.setReceivedFromName(rs.getString("rcvd_from_name"));
					receipt.setReceivedFromCode(rs.getString("rcvd_from_cd"));
					receipt.setReceivedFromAddress(rs.getString("rcvd_from_addr"));
					receipt.setReceiptNumber(rs.getInt("rcpt_no"));
					if(rs.getDate("rcpt_dt") !=null)
						receipt.setReceiptDate(rs.getDate("rcpt_dt").toLocalDate());
					receipt.setReceiptAmount(rs.getDouble("rcpt_amt"));
					receipt.setIwdNumber(rs.getString("iwd_no"));
					receipt.setPayInSlip(rs.getString("pay_in_slip"));
					receipt.setOldReceiptNumber(rs.getInt("old_rcpt_no"));
					receipt.setEntityCd(rs.getString("entity_cd"));
					receipt.setProductDescription(rs.getString("product_description"));
					receipt.setHsn(rs.getDouble("hsn"));
					receipt.setUom(rs.getDouble("uom"));
					receipt.setQty(rs.getDouble("qty"));
					receipt.setRate(rs.getDouble("rate"));
					receipt.setAmount(rs.getDouble("amount"));
					receipt.setDiscount(rs.getDouble("discount"));
					receipt.setTaxableDiscount(rs.getDouble("taxable_discount"));
					receipt.setCustomerGstNo(rs.getString("customer_gst_no"));
					receipt.setEcgcGstNo(rs.getString("ecgc_gst_no"));
					receipt.setInvoiceNo(rs.getString("invoice_no"));
					receipt.setCgstAmount(rs.getDouble("cgst_amount"));
					receipt.setSgstAmount(rs.getDouble("sgst_amount"));
					receipt.setIgstAmount(rs.getDouble("igst_amount"));
					receipt.setUtgstAmount(rs.getDouble("utgst_amount"));
					receipt.setCgstTaxPer(rs.getDouble("cgst_tax_per"));
					receipt.setSgstTaxPer(rs.getDouble("sgst_tax_per"));
					receipt.setIgstTaxPer(rs.getDouble("igst_tax_per"));
					receipt.setUtgstTaxPer(rs.getDouble("utgst_tax_per"));
					receipt.setExpoState(rs.getString("expo_state"));
					receipt.setBranchState(rs.getString("branch_state"));
					receipt.setTaxType(rs.getString("tax_type"));
					receipt.setOldInvoiceNo(rs.getString("old_invoice_no"));
					receipt.setUpdateFlag(rs.getString("update_flg"));
					receipt.setSez(rs.getString("sez"));
					System.err.println("Receipt : "+receipt);
					return receipt;				
				}
			});
		}catch(DataAccessException e) {
			throw new DataAccessResourceFailureException("Failed to fetch data from database");
		}catch(Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
		return receiptlist;
	}

	@Override
	public List<Receipt> listByFromDtToDtAndInstrumentTypePayInSlip(String fromDate, String toDate,
			String[] instrumentType) {
		log.info("Inside PayInSlipDaoImpl#listByFromDtToDtAndInstrumentTypePayInSlip");
		LocalDate fDate = LocalDate.parse(fromDate);
		LocalDate tDate = LocalDate.parse(toDate);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("fDate", fDate);
		paramMap.put("tDate", tDate);
		
		List<String> instList = Arrays.asList(instrumentType);
		SqlParameterSource parameters = new MapSqlParameterSource("instList", instList);
		
		String logicalLocCode = userInfoService.getPrimaryOffice();
		List<Receipt> list = new ArrayList<Receipt>();
		String queryForInstTypeDate = "SELECT * FROM accounts.ecgc_acct_rcpts where instrument_type in (:instList) and rcpt_dt between "+"\'"+fDate+"\'"+" and "+"\'"+tDate+"\' and logical_loc_cd = "+"\'"+logicalLocCode+"\'";
		try {
			list = namedParameterJdbcTemplate.query(queryForInstTypeDate, parameters, new RowMapper<Receipt>() {

				@Override
				public Receipt mapRow(ResultSet rs, int rowNum) throws SQLException {
					Receipt receipt = new Receipt();
					receipt.setLogicalLocCode(rs.getString("logical_loc_cd"));
					receipt.setGlFlag(rs.getString("gl_flg"));
					receipt.setMetaRemarks(rs.getString("meta_remarks"));
					receipt.setCreatedBy( rs.getString("created_by"));
					if(rs.getDate("created_dt") !=null)
					receipt.setCreatedDate(rs.getDate("created_dt").toLocalDate());
					receipt.setDrawnOn(rs.getString("drawn_on"));
					receipt.setMetaStatus(rs.getString("meta_status"));
					receipt.setFiscalYear(rs.getString("fiscal_yr"));
					receipt.setGlTxnNumber(rs.getInt("gl_txn_no"));
					receipt.setGlTxnNumberOld(rs.getInt("gl_txn_no_old"));
					receipt.setGlTxnType(rs.getString("gl_txn_type"));
					if(rs.getDate("instrument_dt") !=null)
					receipt.setInstrumentDate(rs.getDate("instrument_dt").toLocalDate());
					receipt.setInstrumentNumber(rs.getString("instrument_no"));
					receipt.setInstrumentType(rs.getString("instrument_type"));
					receipt.setLastUpdatedBy(rs.getString("last_updated_by"));
					receipt.setStampAmount(rs.getDouble("stamp_amt"));
					receipt.setRemarks(rs.getString("remarks"));
					receipt.setReceivedfromType(rs.getString("rcvd_from_type"));
					receipt.setReceivedFromName(rs.getString("rcvd_from_name"));
					receipt.setReceivedFromCode(rs.getString("rcvd_from_cd"));
					receipt.setReceivedFromAddress(rs.getString("rcvd_from_addr"));
					receipt.setReceiptNumber(rs.getInt("rcpt_no"));
					if(rs.getDate("rcpt_dt") !=null)
						receipt.setReceiptDate(rs.getDate("rcpt_dt").toLocalDate());
					receipt.setReceiptAmount(rs.getDouble("rcpt_amt"));
					receipt.setIwdNumber(rs.getString("iwd_no"));
					receipt.setPayInSlip(rs.getString("pay_in_slip"));
					receipt.setOldReceiptNumber(rs.getInt("old_rcpt_no"));
					receipt.setEntityCd(rs.getString("entity_cd"));
					receipt.setProductDescription(rs.getString("product_description"));
					receipt.setHsn(rs.getDouble("hsn"));
					receipt.setUom(rs.getDouble("uom"));
					receipt.setQty(rs.getDouble("qty"));
					receipt.setRate(rs.getDouble("rate"));
					receipt.setAmount(rs.getDouble("amount"));
					receipt.setDiscount(rs.getDouble("discount"));
					receipt.setTaxableDiscount(rs.getDouble("taxable_discount"));
					receipt.setCustomerGstNo(rs.getString("customer_gst_no"));
					receipt.setEcgcGstNo(rs.getString("ecgc_gst_no"));
					receipt.setInvoiceNo(rs.getString("invoice_no"));
					receipt.setCgstAmount(rs.getDouble("cgst_amount"));
					receipt.setSgstAmount(rs.getDouble("sgst_amount"));
					receipt.setIgstAmount(rs.getDouble("igst_amount"));
					receipt.setUtgstAmount(rs.getDouble("utgst_amount"));
					receipt.setCgstTaxPer(rs.getDouble("cgst_tax_per"));
					receipt.setSgstTaxPer(rs.getDouble("sgst_tax_per"));
					receipt.setIgstTaxPer(rs.getDouble("igst_tax_per"));
					receipt.setUtgstTaxPer(rs.getDouble("utgst_tax_per"));
					receipt.setExpoState(rs.getString("expo_state"));
					receipt.setBranchState(rs.getString("branch_state"));
					receipt.setTaxType(rs.getString("tax_type"));
					receipt.setOldInvoiceNo(rs.getString("old_invoice_no"));
					receipt.setUpdateFlag(rs.getString("update_flg"));
					receipt.setSez(rs.getString("sez"));
					
					return receipt;		
				}
			});
		}catch(DataAccessException e) {
			//e.printStackTrace();
			throw new DataAccessResourceFailureException("Failed to fetch data from database");
		}catch(Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
		log.info("List of Pay In Slip receipts is : {}",list);
		return list;
	}

}

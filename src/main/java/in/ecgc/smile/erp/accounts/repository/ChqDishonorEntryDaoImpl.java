package in.ecgc.smile.erp.accounts.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import in.ecgc.smile.erp.accounts.exception.ReceiptNotFoundException;
import in.ecgc.smile.erp.accounts.exception.RecordNotFoundException;
import in.ecgc.smile.erp.accounts.model.ChqDishonorEntry;
import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.util.ChqDishonorEntryQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ChqDishonorEntryDaoImpl implements ChqDishonorEntryDao {
	@Autowired
	UserInfoService userInfoService;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private NamedParameterJdbcOperations namedParameterJdbcOperations;

	public ChqDishonorEntryDaoImpl(DataSource dataSource) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<ChqDishonorEntry> getChqDishonorEntryList() {
		log.info("inside ChqDishonorEntryDaoImpl  -  getChqDishonorEntryList()");
		List<ChqDishonorEntry> chqDishonorEntryList = new ArrayList<ChqDishonorEntry>();
		chqDishonorEntryList = namedParameterJdbcTemplate.query(ChqDishonorEntryQueries.GET_ChqDishonorEntry_DATA,
				new RowMapper<ChqDishonorEntry>() {

					@Override
					public ChqDishonorEntry mapRow(ResultSet rs, int rowNum) throws SQLException {

						ChqDishonorEntry chqDishonorEntry = new ChqDishonorEntry();
						chqDishonorEntry.setLogicalLocCode(rs.getString("logical_loc_cd"));
						chqDishonorEntry.setReceiptNumber(rs.getInt("rcpt_no"));
						if (rs.getDate("dishonor_dt") != null)
							chqDishonorEntry.setDishonorDt(rs.getDate("dishonor_dt").toLocalDate());
						chqDishonorEntry.setInstrumentNumber(rs.getString("instrument_no"));
						chqDishonorEntry.setInstrumentType(rs.getString("instrument_type"));
						chqDishonorEntry.setDishonorReason(rs.getString("dishonor_reason"));
						chqDishonorEntry.setGlTxnNumber(rs.getInt("gl_txn_no"));
						chqDishonorEntry.setFiscalYr(rs.getString("fiscal_yr"));
						chqDishonorEntry.setCreatedBy(rs.getString("created_by"));
						if (rs.getDate("created_dt") != null)
							chqDishonorEntry.setCreatedDt(rs.getDate("created_dt").toLocalDate());
						chqDishonorEntry.setLastUpdatedBy(rs.getString("last_updated_by"));
						if (rs.getDate("last_updated_dt") != null)
							chqDishonorEntry.setLastUpdatedDt(rs.getDate("last_updated_dt").toLocalDate());
						chqDishonorEntry.setMetaStatus(rs.getString("meta_status"));
						chqDishonorEntry.setMetaRemarks(rs.getString("meta_remarks"));
						chqDishonorEntry.setRevarsalGlTxnNo(rs.getInt("reversal_txn_no"));
						chqDishonorEntry.setRemarks(rs.getString("remarks"));
						log.info("chqDishonorEntry {}", chqDishonorEntry);
						return chqDishonorEntry;
					}
				});

		return chqDishonorEntryList;
	}

	@Override
	public boolean addChqDishonorEntryData(ChqDishonorEntry chqDishonorEntry) {
		log.info("inside ChqDishonorEntryDaoImpl  -  addChqDishonorEntryData()");

		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("logicalLocCd", chqDishonorEntry.getLogicalLocCode());
			param.put("dishonorDt", chqDishonorEntry.getDishonorDt());
			param.put("dishonorReason", chqDishonorEntry.getDishonorReason());
			param.put("rcptNo", chqDishonorEntry.getReceiptNumber());
			param.put("instrumentNo", chqDishonorEntry.getInstrumentNumber());
			param.put("instrumentType", chqDishonorEntry.getInstrumentType());
			param.put("fiscalYr", chqDishonorEntry.getFiscalYr());
			param.put("createdBy", userInfoService.getUser());
			param.put("createdDt", chqDishonorEntry.getCreatedDt());
			param.put("glTxnNumber", chqDishonorEntry.getGlTxnNumber());
			param.put("revarsalGlTxnNo", chqDishonorEntry.getRevarsalGlTxnNo());
			param.put("oldReceiptNumber", chqDishonorEntry.getOldReceiptNumber());
			param.put("lastUpdatedBy", userInfoService.getUser());
			param.put("lastUpdatedDt", chqDishonorEntry.getLastUpdatedDt());
			param.put("chremark", chqDishonorEntry.getRemarks());

			log.info("fiscal Year in Dao :: {}", chqDishonorEntry.getFiscalYr());
			int rowCount = namedParameterJdbcTemplate.update(ChqDishonorEntryQueries.ADD_ChqDishonorEntry_DATA, param);
			int updateGLFlag = namedParameterJdbcOperations.update(ChqDishonorEntryQueries.UPDATE_Gl_FLAG, param);
			if (rowCount == 1 && updateGLFlag == 1) {
				return true;
			}
		} catch (Exception e) {
			log.error("Exception Occured {}", e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateChqDishonorEntryData(ChqDishonorEntry chqDishonorEntry) {
		log.info("inside ChqDishonorEntryDaoImpl  -  updateChqDishonorEntryData()");
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("logicalLocCd", chqDishonorEntry.getLogicalLocCode());
			param.put("dishonorDt", chqDishonorEntry.getDishonorDt());
			param.put("dishonorReason", chqDishonorEntry.getDishonorReason());
			param.put("rcptNo", chqDishonorEntry.getReceiptNumber());
			param.put("instrumentNo", chqDishonorEntry.getInstrumentNumber());
			param.put("instrumentType", chqDishonorEntry.getInstrumentType());
			param.put("fiscalYr", chqDishonorEntry.getFiscalYr());
			param.put("lastUpdatedBy", userInfoService.getUser());
			param.put("lastUpdatedDt", chqDishonorEntry.getLastUpdatedDt());
			param.put("chremark", chqDishonorEntry.getRemarks());

			int rowCount = namedParameterJdbcTemplate.update(ChqDishonorEntryQueries.UPDATE_ChqDishonorEntry_DATA,
					param);
			if (rowCount == 1) {
				return true;
			}
		} catch (Exception e) {
			log.error("Exception Occured {}", e.getMessage());
		}
		return false;
	}

	@Override
	public ChqDishonorEntry getChqDishonorEntryByChequeNo(String instrumentNo) {
		log.info("inside ChqDishonorEntryDaoImpl  -  getChqDishonorEntryByChequeNo()");
		ChqDishonorEntry chqDishonorEntry = new ChqDishonorEntry();
		Map<String, Object> paraMmap = new HashMap<>();
		paraMmap.put("instrumentNo", instrumentNo);

		try {
			chqDishonorEntry = namedParameterJdbcOperations.queryForObject(
					ChqDishonorEntryQueries.GET_ChqDishonorEntry_BY_CHEQUE_NO, paraMmap,
					new RowMapper<ChqDishonorEntry>() {
						@Override
						public ChqDishonorEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
							ChqDishonorEntry chequeDishonorEntry = new ChqDishonorEntry();
							chequeDishonorEntry.setLogicalLocCode(rs.getString("logical_loc_cd"));
							chequeDishonorEntry.setDishonorDt(rs.getDate("dishonor_dt").toLocalDate());
							chequeDishonorEntry.setDishonorReason(rs.getString("dishonor_reason"));
							chequeDishonorEntry.setMetaRemarks(rs.getString("meta_remarks"));
							chequeDishonorEntry.setCreatedBy(rs.getString("created_by"));
							chequeDishonorEntry.setCreatedDt(rs.getDate("created_dt").toLocalDate());
							chequeDishonorEntry.setMetaStatus(rs.getString("meta_status"));
							chequeDishonorEntry.setFiscalYr(rs.getString("fiscal_yr"));
							chequeDishonorEntry.setGlTxnNumber(rs.getInt("gl_txn_no"));
							chequeDishonorEntry.setInstrumentNumber(rs.getString("instrument_no"));
							chequeDishonorEntry.setInstrumentType(rs.getString("instrument_type"));
							chequeDishonorEntry.setLastUpdatedBy(rs.getString("last_updated_by"));
							chequeDishonorEntry.setReceiptNumber(rs.getInt("rcpt_no"));
							chequeDishonorEntry.setRevarsalGlTxnNo(rs.getInt("reversal_txn_no"));
							chequeDishonorEntry.setOldReceiptNumber(rs.getInt("old_rcpt_no"));
							chequeDishonorEntry.setRemarks(rs.getString("remarks"));
							return chequeDishonorEntry;
						}
					});
		} catch (Exception e) {
			chqDishonorEntry = null;
			throw new RecordNotFoundException();
		}
		return chqDishonorEntry;
	}

	@Override
	public Receipt viewByChqNoChqTypeChqDtRcptNo(String instrumentType, String instrumentNumber,
			LocalDate instrumentDate, Integer receiptNumber) {
		log.info("inside ChqDishonorEntryDaoImpl  -  viewByChqNoChqTypeChqDtRcptNo chq dao {} {} {} {}", instrumentType,
				instrumentNumber, instrumentDate, receiptNumber);

		Receipt receiptList = new Receipt();
		Map<String, Object> paraMmap = new HashMap<>();
		paraMmap.put("instrumentType", instrumentType);
		paraMmap.put("instrumentNumber", instrumentNumber);
		paraMmap.put("instrumentDate", instrumentDate);
		paraMmap.put("receiptNumber", receiptNumber);

		try {
			receiptList = namedParameterJdbcOperations.queryForObject(ChqDishonorEntryQueries.GET_RECEIPT_DETAILS,
					paraMmap, new RowMapper<Receipt>() {
						@Override
						public Receipt mapRow(ResultSet rs, int rowNum) throws SQLException {
							Receipt receipt = new Receipt();
							receipt.setLogicalLocCode(rs.getString("logical_loc_cd"));
							receipt.setGlFlag(rs.getString("gl_flg"));
							receipt.setMetaRemarks(rs.getString("meta_remarks"));
							receipt.setCreatedBy(rs.getString("created_by"));
							if (rs.getDate("created_dt") != null)
								receipt.setCreatedDate(rs.getDate("created_dt").toLocalDate());
							receipt.setDrawnOn(rs.getString("drawn_on"));
							receipt.setMetaStatus(rs.getString("meta_status"));
							receipt.setFiscalYear(rs.getString("fiscal_yr"));
							receipt.setGlTxnNumber(rs.getInt("gl_txn_no"));
							receipt.setGlTxnNumberOld(rs.getInt("gl_txn_no_old"));
							receipt.setGlTxnType(rs.getString("gl_txn_type"));
							if (rs.getDate("instrument_dt") != null)
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
							// receipt.setReceiptId(rs.getInt("rcpt_id"));
							if (rs.getDate("rcpt_dt") != null)
								receipt.setReceiptDate(rs.getDate("rcpt_dt").toLocalDate());
							receipt.setReceiptAmount(rs.getDouble("rcpt_amt"));
							receipt.setIwdNumber(rs.getString("iwd_no"));
							receipt.setOldReceiptNumber(rs.getInt("old_rcpt_no"));
							receipt.setPayInSlip(rs.getString("pay_in_slip"));
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
		} catch (Exception e) {
			log.info("Exception occured {}", e.getMessage());
			receiptList = null;
			throw new ReceiptNotFoundException();

		}
		return receiptList;
	}

	@Override
	public List<Receipt> viewByInstrumentType(String instrumentType) {
		log.info("inside ChqDishonorEntryDaoImpl  -  viewByInstrumentType ");
		List<Receipt> receiptList = new ArrayList<Receipt>();
		Map<String, Object> paraMmap = new HashMap<>();
		paraMmap.put("instrumentType", instrumentType);
		try {
			receiptList = namedParameterJdbcTemplate.query(
					ChqDishonorEntryQueries.GET_ALL_RECEIPT_DETAILS_BY_INSTRUMENT_TYPE, paraMmap,
					new RowMapper<Receipt>() {
						@Override
						public Receipt mapRow(ResultSet rs, int rowNum) throws SQLException {
							Receipt receipt = new Receipt();
							receipt.setLogicalLocCode(rs.getString("logical_loc_cd"));
							receipt.setGlFlag(rs.getString("gl_flg"));
							receipt.setMetaRemarks(rs.getString("meta_remarks"));
							receipt.setCreatedBy(rs.getString("created_by"));
							if (rs.getDate("created_dt") != null)
								receipt.setCreatedDate(rs.getDate("created_dt").toLocalDate());
							receipt.setDrawnOn(rs.getString("drawn_on"));
							receipt.setMetaStatus(rs.getString("meta_status"));
							receipt.setFiscalYear(rs.getString("fiscal_yr"));
							receipt.setGlTxnNumber(rs.getInt("gl_txn_no"));
							receipt.setGlTxnNumberOld(rs.getInt("gl_txn_no_old"));
							receipt.setGlTxnType(rs.getString("gl_txn_type"));
							if (rs.getDate("instrument_dt") != null)
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
							// receipt.setReceiptId(rs.getInt("rcpt_id"));
							if (rs.getDate("rcpt_dt") != null)
								receipt.setReceiptDate(rs.getDate("rcpt_dt").toLocalDate());
							receipt.setReceiptAmount(rs.getDouble("rcpt_amt"));
							receipt.setIwdNumber(rs.getString("iwd_no"));
							receipt.setOldReceiptNumber(rs.getInt("old_rcpt_no"));
							receipt.setPayInSlip(rs.getString("pay_in_slip"));
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
		} catch (Exception e) {
			receiptList = null;
			throw new ReceiptNotFoundException();
		}
		return receiptList;

	}

	@Override
	public ChqDishonorEntry viewByLogicalLocCodeAndReceiptNo(String logicalLocCode, Integer receiptNumber) {
		log.info("inside ChqDishonorEntryDaoImpl  - viewByLogicalLocCodeAndReceiptNo");
		ChqDishonorEntry chqDishonorEntry = new ChqDishonorEntry();
		Map<String, Object> paraMmap = new HashMap<>();
		paraMmap.put("logicalLocCode", logicalLocCode);
		paraMmap.put("receiptNumber", receiptNumber);

		try {
			chqDishonorEntry = namedParameterJdbcOperations.queryForObject(
					ChqDishonorEntryQueries.VIEW_BY_LOGICAL_LOC_CODE_AND_RECEIPT_NO, paraMmap,
					new RowMapper<ChqDishonorEntry>() {
						@Override
						public ChqDishonorEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
							ChqDishonorEntry chequeDishonorEntry = new ChqDishonorEntry();
							chequeDishonorEntry.setLogicalLocCode(rs.getString("logical_loc_cd"));
							chequeDishonorEntry.setDishonorDt(rs.getDate("dishonor_dt").toLocalDate());
							chequeDishonorEntry.setDishonorReason(rs.getString("dishonor_reason"));
							chequeDishonorEntry.setMetaRemarks(rs.getString("meta_remarks"));
							chequeDishonorEntry.setCreatedBy(rs.getString("created_by"));
							chequeDishonorEntry.setCreatedDt(rs.getDate("created_dt").toLocalDate());
							chequeDishonorEntry.setMetaStatus(rs.getString("meta_status"));
							chequeDishonorEntry.setFiscalYr(rs.getString("fiscal_yr"));
							chequeDishonorEntry.setGlTxnNumber(rs.getInt("gl_txn_no"));
							chequeDishonorEntry.setInstrumentNumber(rs.getString("instrument_no"));
							chequeDishonorEntry.setInstrumentType(rs.getString("instrument_type"));
							chequeDishonorEntry.setLastUpdatedBy(rs.getString("last_updated_by"));
							chequeDishonorEntry.setReceiptNumber(rs.getInt("rcpt_no"));
							chequeDishonorEntry.setRevarsalGlTxnNo(rs.getInt("reversal_txn_no"));
							chequeDishonorEntry.setOldReceiptNumber(rs.getInt("old_rcpt_no"));
							chequeDishonorEntry.setRemarks(rs.getString("remarks"));
							return chequeDishonorEntry;
						}
					});
		} catch (Exception e) {
			log.info("Exception occured {}", e);
			chqDishonorEntry = null;
		}
		return chqDishonorEntry;
	}
}

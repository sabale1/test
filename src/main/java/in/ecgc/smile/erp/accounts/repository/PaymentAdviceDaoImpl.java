
package in.ecgc.smile.erp.accounts.repository;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.exception.DatabaseOperationFailException;
import in.ecgc.smile.erp.accounts.exception.FailToInsertDataException;
import in.ecgc.smile.erp.accounts.exception.PaymentAdviceCreateException;
import in.ecgc.smile.erp.accounts.model.PaymentAdvice;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceGstTdsDtl;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceTcodes;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceTdsDtl;
import in.ecgc.smile.erp.accounts.util.PaymentAdviceSqlQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;

@Repository
@Transactional
public class PaymentAdviceDaoImpl implements PaymentAdviceDao{

	@Autowired
	PaymentAdviceTDSDao adviceTDSDao;

	@Autowired
	UserInfoService userInfoService;

	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentAdviceDaoImpl.class);

	public DataSource ds = null;

	@Autowired
	public PaymentAdviceDaoImpl(DataSource dataSource) {
		ds = dataSource;

		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}



	@Override
	public Integer getAdviceNo(String logicalLocCd,String sectionCd,String fiscalYear) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#getAdviceNo");
		Map<String, String> param = new HashMap<String, String>();
		param.put("logicalLocCd", logicalLocCd);
		param.put("sectionCd", sectionCd);
		param.put("fiscalYear", fiscalYear);

		return namedParameterJdbcOperations.queryForObject(PaymentAdviceSqlQueries.GET_SEQ_NO,param,
				new RowMapper<Integer>() {

					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt(1);
					}
				});
	}

	@Override
	public Integer updateSeqNo(String logicalLocCd, String sectionCd, String fiscalYear, Integer adviceNo) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#updateSeqNo");
		Map<String, String> param = new HashMap<String, String>();
		param.put("logicalLocCd", logicalLocCd);
		param.put("sectionCd", sectionCd);
		param.put("fiscalYear", fiscalYear);
		param.put("adviceNo", adviceNo.toString());

		return namedParameterJdbcOperations.update(PaymentAdviceSqlQueries.UPDATE_SEQ_NO,param);
	}

	@Override
	public Integer addPaymentAdvice(PaymentAdvice paymentAdvice) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#addPaymentAdvice");
		Map<String, Object> param;
		param = new HashMap<>();
		try {

			param.put("entityCd", paymentAdvice.getEntityCd());
			param.put("expenseHead", paymentAdvice.getExpenseHead());
			param.put("logicalLocCd", paymentAdvice.getLogicalLocCd());
			param.put("sectionCd", paymentAdvice.getSectionCd());
			param.put("adviceNo", paymentAdvice.getAdviceNo());
			param.put("adviceDate", paymentAdvice.getAdviceDate());
			param.put("payToType", paymentAdvice.getPayToType());
			param.put("pymtPartyCd", paymentAdvice.getPymtPartyCd());
			param.put("pymtPartyName", paymentAdvice.getPymtPartyName());
			param.put("currCd", paymentAdvice.getCurrCd());
			param.put("billNo", paymentAdvice.getBillNo());
			param.put("billDt", paymentAdvice.getBillDt());
			param.put("billIwdDt", paymentAdvice.getBillIwdDt());
			param.put("billDesc", paymentAdvice.getBillDesc());
			param.put("adviceAmt", paymentAdvice.getAdviceAmt());
			param.put("adviceDesc", paymentAdvice.getAdviceDesc());
			param.put("decBy", paymentAdvice.getDecBy());
			param.put("decDt", paymentAdvice.getDecDt());
			param.put("decRemark", paymentAdvice.getDecRemark());
			param.put("aprvAmt", paymentAdvice.getAprvAmt());
			param.put("adviceStat", paymentAdvice.getAdviceStatus());
			param.put("tdsApplicable", paymentAdvice.getTdsApplicable());
			param.put("taxRate", paymentAdvice.getTaxRate());
			param.put("surchargeRate", paymentAdvice.getSurchargeRate());
			param.put("taxDeducted", paymentAdvice.getTaxDeducted());
			param.put("fiscalYear", paymentAdvice.getFiscalYear());
			param.put("cessAmt", paymentAdvice.getCessAmt());
			param.put("cessRate", paymentAdvice.getCessRate());
			param.put("oldCd", paymentAdvice.getOldCd());
			param.put("adviceAmtOtherCurrINR", paymentAdvice.getAdviceAmtOtherCurrINR());
			param.put("currRate", paymentAdvice.getCurrRate());
			param.put("userName", paymentAdvice.getUserName());
			param.put("apprBaseAmt", paymentAdvice.getApprBaseAmt());
			param.put("serviceTaxAmt", paymentAdvice.getServiceTaxAmt());
			param.put("othAmt", paymentAdvice.getOthAmt());
			param.put("pymtAprvId", paymentAdvice.getPymtAprvId());
			param.put("pymtAprvName", paymentAdvice.getPymtAprvName());
			param.put("pymtYear", paymentAdvice.getPymtYear());
			param.put("noDeductionReason", paymentAdvice.getNoDeductionReason());
			param.put("taxInfoFlag", paymentAdvice.getTaxInfoFlag());
			param.put("provisionFlag", paymentAdvice.getProvisionFlag());
			param.put("noProvisionReason", paymentAdvice.getNoProvisionReason());
			param.put("provisionalAmt", paymentAdvice.getProvisionalAmt());
			param.put("liabilityGlTxnNo", paymentAdvice.getLiabilityGlTxnNo());
			param.put("liabilityGlTxnType", paymentAdvice.getLiabilityGlTxnType());
			param.put("createdDate", paymentAdvice.getCreatedDate());
			param.put("createdBy", userInfoService.getUser());
			param.put("metaStatus", paymentAdvice.getMetaStatus());
			param.put("branchCd", paymentAdvice.getBranchCd());
			param.put("metaRemarks", paymentAdvice.getMetaRemarks());
			param.put("delFlag", paymentAdvice.getDelFlag());
			param.put("moduleCode", paymentAdvice.getModuleCode());
			param.put("mappingCode", paymentAdvice.getMappingCode());

			return namedParameterJdbcTemplate.update(PaymentAdviceSqlQueries.ADD_PAYMENT_ADVICE_DTL, param);

		} catch (PaymentAdviceCreateException e) {
			LOGGER.info("Failed to create payment advice entry :{}",e);
			throw new PaymentAdviceCreateException(e.getMessage());
		}

	}

	@Override
	public List<PaymentAdvice> listAllPaymentAdvice() {

		LOGGER.info("Inside PaymentAdviceDaoImpl#listAllPaymentAdvice");

		List<PaymentAdvice> payAdviceList = null;

		try {
			payAdviceList = namedParameterJdbcTemplate.query(PaymentAdviceSqlQueries.LIST_ALL_PAYMENT_ADVICE_DTL,
					new RowMapper<PaymentAdvice>() {

						@Override
						public PaymentAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {

							PaymentAdvice payAdvice = new PaymentAdvice();
							payAdvice.setAdviceAmt(rs.getDouble("advice_amt"));
							payAdvice.setAdviceAmtOtherCurrINR(rs.getDouble("advice_amt_oth_curr_inr"));
							payAdvice.setAdviceDesc(rs.getString("advice_desc"));
							if (rs.getDate("advice_dt")==null) {
								payAdvice.setAdviceDate(null);
							} else {
								payAdvice.setAdviceDate(rs.getDate("advice_dt").toLocalDate());
							}
							payAdvice.setAdviceNo(rs.getInt("advice_no"));
							payAdvice.setAdviceStatus(rs.getString("advice_stat"));
							payAdvice.setApprBaseAmt(rs.getDouble("appr_base_amt"));
							payAdvice.setAprvAmt(rs.getDouble("aprv_amt"));
							payAdvice.setBranchCd(rs.getString("branch_cd"));
							payAdvice.setCessAmt(rs.getDouble("cess_amt"));
							payAdvice.setCessRate(rs.getDouble("cess_rate"));
							payAdvice.setCreatedBy(rs.getInt("created_by"));
							if (rs.getDate("created_dt")==null) {
								payAdvice.setCreatedDate(null);
							} else {
								payAdvice.setCreatedDate(rs.getDate("created_dt").toLocalDate());
							}
							payAdvice.setCurrCd(rs.getString("curr_cd"));
							payAdvice.setCurrRate(rs.getInt("curr_rate"));
							payAdvice.setDecRemark(rs.getString("dec_rmk"));
							payAdvice.setDecBy(rs.getString("dec_by"));
							if (rs.getDate("dec_dt")==null) {
								payAdvice.setDecDt(null);
							} else {
								payAdvice.setDecDt(rs.getDate("dec_dt").toLocalDate());
							}
							payAdvice.setBillNo(rs.getString("bill_no"));
							payAdvice.setBillDesc(rs.getString("bill_desc"));
							if (rs.getDate("bill_dt")==null) {
								payAdvice.setBillDt(null);
							} else {
								payAdvice.setBillDt(rs.getDate("bill_dt").toLocalDate());
							}
							if (rs.getDate("bill_iwd_dt")==null) {
								payAdvice.setBillIwdDt(null);
							} else {
								payAdvice.setBillIwdDt(rs.getDate("bill_iwd_dt").toLocalDate());
							}
							payAdvice.setDelFlag(rs.getBoolean("del_flag"));
							payAdvice.setEntityCd(rs.getString("entity_cd"));
							payAdvice.setExpenseHead(rs.getString("expense_head"));
							payAdvice.setFiscalYear(rs.getString("fiscal_yr"));
							payAdvice.setLogicalLocCd(rs.getString("logical_loc_cd"));
							payAdvice.setMetaRemarks(rs.getString("meta_remarks"));
							payAdvice.setMetaStatus(rs.getString("meta_status"));
							payAdvice.setNoDeductionReason(rs.getString("no_deduction_reason"));
							payAdvice.setNoProvisionReason(rs.getString("no_provision_reason"));
							payAdvice.setOldCd(rs.getString("old_cd"));
							payAdvice.setOthAmt(rs.getDouble("oth_amt"));
							payAdvice.setPayToType(rs.getString("pay_to_type"));
							if(rs.getString("provision_flg")==null)
							{
								payAdvice.setProvisionFlag(' ');
							}
							else
								payAdvice.setProvisionFlag(rs.getString("provision_flg").charAt(0));
							payAdvice.setProvisionalAmt(rs.getDouble("provisional_amt"));
							payAdvice.setPymtAprvId(rs.getInt("pymt_aprv_id"));
							payAdvice.setPymtAprvName(rs.getString("pymt_aprv_name"));
							payAdvice.setPymtPartyCd(rs.getString("pymt_party_cd"));
							payAdvice.setPymtPartyName(rs.getString("pymt_party_name"));
							payAdvice.setPymtYear(rs.getString("pymt_year"));
							payAdvice.setSectionCd(rs.getString("section_cd"));
							payAdvice.setServiceTaxAmt(rs.getDouble("service_tax_amt"));
							payAdvice.setSurchargeRate(rs.getDouble("surcharge_rate"));
							payAdvice.setTaxDeducted(rs.getDouble("tax_deducted"));
							payAdvice.setTaxRate(rs.getDouble("tax_rate"));
							if(rs.getString("taxinfo_flg")==null)
							{
								payAdvice.setTaxInfoFlag(' ');
							}
							else
								payAdvice.setTaxInfoFlag(rs.getString("taxinfo_flg").charAt(0));

							if(rs.getString("tds_applicable")==null)
							{
								payAdvice.setTdsApplicable(' ');
							}
							else
								payAdvice.setTdsApplicable(rs.getString("tds_applicable").charAt(0));
							payAdvice.setUserName(rs.getString("user_name"));
							payAdvice.setModuleCode(rs.getString("module_cd"));
							payAdvice.setMappingCode(rs.getString("mapping_cd"));
							return payAdvice;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			payAdviceList = null;
			LOGGER.info("Exception Occured {}",e);
		}
		return payAdviceList;

	}

	@Override
	public PaymentAdvice getPaymentAdviceByAdviceDtl(String logicalLocCd,String sectionCd,Integer adviceNo) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#getPaymentAdviceByAdviceDtl");
		PaymentAdvice paymentAdvice = null;

		try {
			Map<String, String> param = new HashMap<>();
			param.put("adviceNo", adviceNo.toString());
			param.put("sectionCd", sectionCd);
			param.put("logicalLocCd", logicalLocCd);
			paymentAdvice = namedParameterJdbcTemplate.queryForObject(PaymentAdviceSqlQueries.GET_PAYMENT_ADVICE_BY_ADVICE_DTL,param,
					new RowMapper<PaymentAdvice>() {

						@Override
						public PaymentAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {

							PaymentAdvice payAdvice = new PaymentAdvice();
							payAdvice.setAdviceAmt(rs.getDouble("advice_amt"));
							payAdvice.setAdviceAmtOtherCurrINR(rs.getDouble("advice_amt_oth_curr_inr"));
							payAdvice.setAdviceDesc(rs.getString("advice_desc"));
							if (rs.getDate("advice_dt")==null) {
								payAdvice.setAdviceDate(null);
							} else {
								payAdvice.setAdviceDate(rs.getDate("advice_dt").toLocalDate());
							}
							payAdvice.setAdviceNo(rs.getInt("advice_no"));
							payAdvice.setAdviceStatus(rs.getString("advice_stat"));
							payAdvice.setApprBaseAmt(rs.getDouble("appr_base_amt"));
							payAdvice.setAprvAmt(rs.getDouble("aprv_amt"));
							payAdvice.setBranchCd(rs.getString("branch_cd"));
							payAdvice.setCessAmt(rs.getDouble("cess_amt"));
							payAdvice.setCessRate(rs.getDouble("cess_rate"));
							payAdvice.setCreatedBy(rs.getInt("created_by"));
							if (rs.getDate("created_dt")==null) {
								payAdvice.setCreatedDate(null);
							} else {
								payAdvice.setCreatedDate(rs.getDate("created_dt").toLocalDate());
							}
							payAdvice.setCurrCd(rs.getString("curr_cd"));
							payAdvice.setCurrRate(rs.getInt("curr_rate"));
							payAdvice.setDecRemark(rs.getString("dec_rmk"));
							payAdvice.setDecBy(rs.getString("dec_by"));
							if (rs.getDate("dec_dt")==null) {
								payAdvice.setDecDt(null);
							} else {
								payAdvice.setDecDt(rs.getDate("dec_dt").toLocalDate());
							}
							payAdvice.setBillNo(rs.getString("bill_no"));
							payAdvice.setBillDesc(rs.getString("bill_desc"));
							if (rs.getDate("bill_dt")==null) {
								payAdvice.setBillDt(null);
							} else {
								payAdvice.setBillDt(rs.getDate("bill_dt").toLocalDate());
							}
							if (rs.getDate("bill_iwd_dt")==null) {
								payAdvice.setBillIwdDt(null);
							} else {
								payAdvice.setBillIwdDt(rs.getDate("bill_iwd_dt").toLocalDate());
							}
							payAdvice.setDelFlag(rs.getBoolean("del_flag"));
							payAdvice.setEntityCd(rs.getString("entity_cd"));
							payAdvice.setExpenseHead(rs.getString("expense_head"));
							payAdvice.setFiscalYear(rs.getString("fiscal_yr"));
							payAdvice.setLogicalLocCd(rs.getString("logical_loc_cd"));
							payAdvice.setMetaRemarks(rs.getString("meta_remarks"));
							payAdvice.setMetaStatus(rs.getString("meta_status"));
							payAdvice.setNoDeductionReason(rs.getString("no_deduction_reason"));
							payAdvice.setNoProvisionReason(rs.getString("no_provision_reason"));
							payAdvice.setOldCd(rs.getString("old_cd"));
							payAdvice.setOthAmt(rs.getDouble("oth_amt"));
							payAdvice.setPayToType(rs.getString("pay_to_type"));
							if(rs.getString("provision_flg")==null)
							{
								payAdvice.setProvisionFlag(' ');
							}
							else
								payAdvice.setProvisionFlag(rs.getString("provision_flg").charAt(0));
							payAdvice.setProvisionalAmt(rs.getDouble("provisional_amt"));
							payAdvice.setPymtAprvId(rs.getInt("pymt_aprv_id"));
							payAdvice.setPymtAprvName(rs.getString("pymt_aprv_name"));
							payAdvice.setPymtPartyCd(rs.getString("pymt_party_cd"));
							payAdvice.setPymtPartyName(rs.getString("pymt_party_name"));
							payAdvice.setPymtYear(rs.getString("pymt_year"));
							payAdvice.setSectionCd(rs.getString("section_cd"));
							payAdvice.setServiceTaxAmt(rs.getDouble("service_tax_amt"));
							payAdvice.setSurchargeRate(rs.getDouble("surcharge_rate"));
							payAdvice.setTaxDeducted(rs.getDouble("tax_deducted"));
							payAdvice.setTaxRate(rs.getDouble("tax_rate"));
							if(rs.getString("taxinfo_flg")==null)
							{
								payAdvice.setTaxInfoFlag(' ');
							}
							else
								payAdvice.setTaxInfoFlag(rs.getString("taxinfo_flg").charAt(0));

							if(rs.getString("tds_applicable")==null)
							{
								payAdvice.setTdsApplicable(' ');
							}
							else
								payAdvice.setTdsApplicable(rs.getString("tds_applicable").charAt(0));
							payAdvice.setUserName(rs.getString("user_name"));
							payAdvice.setLiabilityGlTxnNo(rs.getString("liability_gl_txn_no"));
							payAdvice.setLiabilityGlTxnType(rs.getString("liability_gl_txn_type"));
							payAdvice.setModuleCode(rs.getString("module_cd"));
							payAdvice.setMappingCode(rs.getString("mapping_cd"));
							return payAdvice;
						}
					});
		} catch (EmptyResultDataAccessException e) {

			LOGGER.info("Exception Occured {}",e);
		}
		return paymentAdvice;
	}


	@Override
	public PaymentAdvice getPaymentAdviceAndTaxDtl(String logicalLocCd, String sectionCd, Integer adviceNo) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#getPaymentAdviceAndTaxDtl");
		PaymentAdvice paymentAdvice = new PaymentAdvice();
		PaymentAdviceTcodes adviceTcodes = new PaymentAdviceTcodes();
		PaymentAdviceTdsDtl adviceTdsDtl = new PaymentAdviceTdsDtl();
		PaymentAdviceGstTdsDtl adviceGstTdsDtl = new PaymentAdviceGstTdsDtl();


		try {
			Map<String, String> param = new HashMap<>();
			param.put("adviceNo", adviceNo.toString());
			param.put("sectionCd", sectionCd);
			param.put("logicalLocCd", logicalLocCd);
			paymentAdvice = namedParameterJdbcTemplate.queryForObject(PaymentAdviceSqlQueries.GET_PAYMENT_ADVICE_BY_ADVICE_DTL,param,
					new RowMapper<PaymentAdvice>() {

						@Override
						public PaymentAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {

							PaymentAdvice payAdvice = new PaymentAdvice();
							payAdvice.setAdviceAmt(rs.getDouble("advice_amt"));
							payAdvice.setAdviceAmtOtherCurrINR(rs.getDouble("advice_amt_oth_curr_inr"));
							payAdvice.setAdviceDesc(rs.getString("advice_desc"));
							if (rs.getDate("advice_dt")==null) {
								payAdvice.setAdviceDate(null);
							} else {
								payAdvice.setAdviceDate(rs.getDate("advice_dt").toLocalDate());
							}
							payAdvice.setAdviceNo(rs.getInt("advice_no"));
							payAdvice.setAdviceStatus(rs.getString("advice_stat"));
							payAdvice.setApprBaseAmt(rs.getDouble("appr_base_amt"));
							payAdvice.setAprvAmt(rs.getDouble("aprv_amt"));
							payAdvice.setBranchCd(rs.getString("branch_cd"));
							payAdvice.setCessAmt(rs.getDouble("cess_amt"));
							payAdvice.setCessRate(rs.getDouble("cess_rate"));
							payAdvice.setCreatedBy(rs.getInt("created_by"));
							if (rs.getDate("created_dt")==null) {
								payAdvice.setCreatedDate(null);
							} else {
								payAdvice.setCreatedDate(rs.getDate("created_dt").toLocalDate());
							}
							payAdvice.setCurrCd(rs.getString("curr_cd"));
							payAdvice.setCurrRate(rs.getInt("curr_rate"));
							payAdvice.setDecRemark(rs.getString("dec_rmk"));
							payAdvice.setDecBy(rs.getString("dec_by"));
							if (rs.getDate("dec_dt")==null) {
								payAdvice.setDecDt(null);
							} else {
								payAdvice.setDecDt(rs.getDate("dec_dt").toLocalDate());
							}
							payAdvice.setBillNo(rs.getString("bill_no"));
							payAdvice.setBillDesc(rs.getString("bill_desc"));
							if (rs.getDate("bill_dt")==null) {
								payAdvice.setBillDt(null);
							} else {
								payAdvice.setBillDt(rs.getDate("bill_dt").toLocalDate());
							}
							if (rs.getDate("bill_iwd_dt")==null) {
								payAdvice.setBillIwdDt(null);
							} else {
								payAdvice.setBillIwdDt(rs.getDate("bill_iwd_dt").toLocalDate());
							}
							payAdvice.setDelFlag(rs.getBoolean("del_flag"));
							payAdvice.setEntityCd(rs.getString("entity_cd"));
							payAdvice.setExpenseHead(rs.getString("expense_head"));
							payAdvice.setFiscalYear(rs.getString("fiscal_yr"));
							payAdvice.setLogicalLocCd(rs.getString("logical_loc_cd"));
							payAdvice.setMetaRemarks(rs.getString("meta_remarks"));
							payAdvice.setMetaStatus(rs.getString("meta_status"));
							payAdvice.setNoDeductionReason(rs.getString("no_deduction_reason"));
							payAdvice.setNoProvisionReason(rs.getString("no_provision_reason"));
							payAdvice.setOldCd(rs.getString("old_cd"));
							payAdvice.setOthAmt(rs.getDouble("oth_amt"));
							payAdvice.setPayToType(rs.getString("pay_to_type"));
							if(rs.getString("provision_flg")==null)
							{
								payAdvice.setProvisionFlag(' ');
							}
							else
								payAdvice.setProvisionFlag(rs.getString("provision_flg").charAt(0));
							payAdvice.setProvisionalAmt(rs.getDouble("provisional_amt"));
							payAdvice.setPymtAprvId(rs.getInt("pymt_aprv_id"));
							payAdvice.setPymtAprvName(rs.getString("pymt_aprv_name"));
							payAdvice.setPymtPartyCd(rs.getString("pymt_party_cd"));
							payAdvice.setPymtPartyName(rs.getString("pymt_party_name"));
							payAdvice.setPymtYear(rs.getString("pymt_year"));
							payAdvice.setSectionCd(rs.getString("section_cd"));
							payAdvice.setServiceTaxAmt(rs.getDouble("service_tax_amt"));
							payAdvice.setSurchargeRate(rs.getDouble("surcharge_rate"));
							payAdvice.setTaxDeducted(rs.getDouble("tax_deducted"));
							payAdvice.setTaxRate(rs.getDouble("tax_rate"));
							if(rs.getString("taxinfo_flg")==null)
							{
								payAdvice.setTaxInfoFlag(' ');
							}
							else
								payAdvice.setTaxInfoFlag(rs.getString("taxinfo_flg").charAt(0));

							if(rs.getString("tds_applicable")==null)
							{
								payAdvice.setTdsApplicable(' ');
							}
							else
								payAdvice.setTdsApplicable(rs.getString("tds_applicable").charAt(0));
							payAdvice.setUserName(rs.getString("user_name"));
							payAdvice.setModuleCode(rs.getString("module_cd"));
							payAdvice.setMappingCode(rs.getString("mapping_cd"));
							return payAdvice;
						}
					});
		} catch (EmptyResultDataAccessException e) {

			LOGGER.info("Exception Occured {}",e);
		}

		adviceTcodes = getPaymentAdviceTcodesDtl(logicalLocCd, sectionCd, adviceNo);

		if (paymentAdvice.getTdsApplicable().equals('Y')) {
			adviceTdsDtl = adviceTDSDao.getPaymentAdviceTDSDtl(logicalLocCd, sectionCd, adviceNo);
			if (adviceTdsDtl.getRevChargeApp().equals('N')) {
				adviceGstTdsDtl = adviceTDSDao.getPaymentAdviceGSTTDSDtl(logicalLocCd, sectionCd, adviceNo);
			}else {
				adviceGstTdsDtl = null;
			}
		}else {
			adviceTdsDtl = null;
		}

		paymentAdvice.setPayTcodes(adviceTcodes);
		paymentAdvice.setAdviceTdsDtl(adviceTdsDtl);
		paymentAdvice.setAdviceGstTdsDtl(adviceGstTdsDtl);

		return paymentAdvice;

	}



	@Override
	public Integer disablePaymentAdvice(String logicalLocCd,String sectionCd,Integer adviceNo) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#disablePaymentAdvice");
		Map<String, String> param = new HashMap<String, String>();
		param.put("adviceNo", adviceNo.toString());
		param.put("sectionCd", sectionCd);
		param.put("logicalLocCd", logicalLocCd);

		return namedParameterJdbcOperations.update(PaymentAdviceSqlQueries.DISABLE_PAYMENT_ADVICE_BY_ADVICE_DTL, param);
	}

	@Override
	public Integer updatePaymentAdvice(PaymentAdvice paymentAdvice) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#updatePaymentAdvice");
		Map<String, Object> param = new HashMap<>();
		param.put("billDesc", paymentAdvice.getBillDesc());
		param.put("billIwdDt", paymentAdvice.getBillIwdDt());
		param.put("billDt", paymentAdvice.getBillDt());
		param.put("billNo", paymentAdvice.getBillNo());
		param.put("adviceDesc", paymentAdvice.getAdviceDesc());
		param.put("pymtYear", paymentAdvice.getPymtYear());
		param.put("adviceAmt", paymentAdvice.getAdviceAmt());
		param.put("currCd", paymentAdvice.getCurrCd());
		param.put("pymtPartyCd", paymentAdvice.getPymtPartyCd());
		param.put("payToType", paymentAdvice.getPayToType());
		param.put("adviceNo", paymentAdvice.getAdviceNo());
		param.put("logicalLocCd", paymentAdvice.getLogicalLocCd());
		param.put("sectionCd", paymentAdvice.getSectionCd());
		param.put("lastUpdatedBy", userInfoService.getUser());

		return namedParameterJdbcOperations.update(PaymentAdviceSqlQueries.UPDATE_PAYMENT_ADVICE_BY_ADVICE_NO, param);
	}


	@Override
	public Integer updatePaymentAdviceTcodes(PaymentAdvice paymentAdvice) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#updatePaymentAdviceTcodes");
		Map<String, Object> param = new HashMap<>();

			param.put("entityCd", paymentAdvice.getPayTcodes().getEntityCd());
			param.put("logicalLocCd", paymentAdvice.getPayTcodes().getLogicalLocCd());
			param.put("sectionCd", paymentAdvice.getPayTcodes().getSectionCd());
			param.put("adviceNo", paymentAdvice.getPayTcodes().getAdviceNo());
			param.put("t1", paymentAdvice.getPayTcodes().getT1());
			param.put("t2", paymentAdvice.getPayTcodes().getT2());
			param.put("t3", paymentAdvice.getPayTcodes().getT3());
			param.put("t4", paymentAdvice.getPayTcodes().getT4());
			param.put("t5", paymentAdvice.getPayTcodes().getT5());
			param.put("t6", paymentAdvice.getPayTcodes().getT6());
			param.put("t7", paymentAdvice.getPayTcodes().getT7());
			param.put("t8", paymentAdvice.getPayTcodes().getT8());
			param.put("t9", paymentAdvice.getPayTcodes().getT9());
			param.put("t10", paymentAdvice.getPayTcodes().getT10());
			param.put("t11", paymentAdvice.getPayTcodes().getT11());
			param.put("t12", paymentAdvice.getPayTcodes().getT12());
			param.put("t13", paymentAdvice.getPayTcodes().getT13());
			param.put("t14", paymentAdvice.getPayTcodes().getT14());
			param.put("t15", paymentAdvice.getPayTcodes().getT15());
			param.put("t16", paymentAdvice.getPayTcodes().getT16());
			param.put("t17", paymentAdvice.getPayTcodes().getT17());
			param.put("t18", paymentAdvice.getPayTcodes().getT18());
			param.put("t19", paymentAdvice.getPayTcodes().getT19());
			param.put("t20", paymentAdvice.getPayTcodes().getT20());
			param.put("t21", paymentAdvice.getPayTcodes().getT21());
			param.put("t22", paymentAdvice.getPayTcodes().getT22());
			param.put("t23", paymentAdvice.getPayTcodes().getT23());
			param.put("t24", paymentAdvice.getPayTcodes().getT24());
			param.put("t25", paymentAdvice.getPayTcodes().getT25());
			param.put("t26", paymentAdvice.getPayTcodes().getT26());
			param.put("t27", paymentAdvice.getPayTcodes().getT27());
			param.put("t28", paymentAdvice.getPayTcodes().getT28());
			param.put("t29", paymentAdvice.getPayTcodes().getT29());
			param.put("t30", paymentAdvice.getPayTcodes().getT30());
			param.put("t31", paymentAdvice.getPayTcodes().getT31());
			param.put("t32", paymentAdvice.getPayTcodes().getT32());
			param.put("t33", paymentAdvice.getPayTcodes().getT33());
			param.put("ad1", paymentAdvice.getPayTcodes().getAd1());
			param.put("ad2", paymentAdvice.getPayTcodes().getAd2());
			param.put("ad3", paymentAdvice.getPayTcodes().getAd3());
			param.put("ad4", paymentAdvice.getPayTcodes().getAd4());

		return namedParameterJdbcTemplate.update(PaymentAdviceSqlQueries.UPDATE_PAYMENT_ADVICE_TCODES, param);
	}



	@Override
	public Integer addPaymentAdviceTcodes(PaymentAdvice paymentAdvice) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#addPaymentAdviceTcodes");
		Map<String, Object> param;
		param = new HashMap<>();
		try {

			param.put("entityCd", paymentAdvice.getPayTcodes().getEntityCd());
			param.put("logicalLocCd", paymentAdvice.getPayTcodes().getLogicalLocCd());
			param.put("sectionCd", paymentAdvice.getPayTcodes().getSectionCd());
			param.put("adviceNo", paymentAdvice.getPayTcodes().getAdviceNo());
			param.put("t1", paymentAdvice.getPayTcodes().getT1());
			param.put("t2", paymentAdvice.getPayTcodes().getT2());
			param.put("t3", paymentAdvice.getPayTcodes().getT3());
			param.put("t4", paymentAdvice.getPayTcodes().getT4());
			param.put("t5", paymentAdvice.getPayTcodes().getT5());
			param.put("t6", paymentAdvice.getPayTcodes().getT6());
			param.put("t7", paymentAdvice.getPayTcodes().getT7());
			param.put("t8", paymentAdvice.getPayTcodes().getT8());
			param.put("t9", paymentAdvice.getPayTcodes().getT9());
			param.put("t10", paymentAdvice.getPayTcodes().getT10());
			param.put("t11", paymentAdvice.getPayTcodes().getT11());
			param.put("t12", paymentAdvice.getPayTcodes().getT12());
			param.put("t13", paymentAdvice.getPayTcodes().getT13());
			param.put("t14", paymentAdvice.getPayTcodes().getT14());
			param.put("t15", paymentAdvice.getPayTcodes().getT15());
			param.put("t16", paymentAdvice.getPayTcodes().getT16());
			param.put("t17", paymentAdvice.getPayTcodes().getT17());
			param.put("t18", paymentAdvice.getPayTcodes().getT18());
			param.put("t19", paymentAdvice.getPayTcodes().getT19());
			param.put("t20", paymentAdvice.getPayTcodes().getT20());
			param.put("t21", paymentAdvice.getPayTcodes().getT21());
			param.put("t22", paymentAdvice.getPayTcodes().getT22());
			param.put("t23", paymentAdvice.getPayTcodes().getT23());
			param.put("t24", paymentAdvice.getPayTcodes().getT24());
			param.put("t25", paymentAdvice.getPayTcodes().getT25());
			param.put("t26", paymentAdvice.getPayTcodes().getT26());
			param.put("t27", paymentAdvice.getPayTcodes().getT27());
			param.put("t28", paymentAdvice.getPayTcodes().getT28());
			param.put("t29", paymentAdvice.getPayTcodes().getT29());
			param.put("t30", paymentAdvice.getPayTcodes().getT30());
			param.put("t31", paymentAdvice.getPayTcodes().getT31());
			param.put("t32", paymentAdvice.getPayTcodes().getT32());
			param.put("t33", paymentAdvice.getPayTcodes().getT33());
			param.put("ad1", paymentAdvice.getPayTcodes().getAd1());
			param.put("ad2", paymentAdvice.getPayTcodes().getAd2());
			param.put("ad3", paymentAdvice.getPayTcodes().getAd3());
			param.put("ad4", paymentAdvice.getPayTcodes().getAd4());

			namedParameterJdbcTemplate.update(PaymentAdviceSqlQueries.ADD_PAYMENT_ADVICE_TCODES_DTL, param);

			return paymentAdvice.getAdviceNo();
		} catch (Exception e) {
			LOGGER.info("Failed to create payment advice T-Codes entry! {}",e);
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public List<PaymentAdvice> listAllPaymentAdviceTcodes() {
		LOGGER.info("Inside PaymentAdviceDaoImpl#listAllPaymentAdviceTcodes");
		List<PaymentAdvice> payAdviceTcodesList = null;

		try {
			payAdviceTcodesList = namedParameterJdbcTemplate.query(PaymentAdviceSqlQueries.LIST_ALL_PAYMENT_ADVICE_TCODES_DTL,
					new RowMapper<PaymentAdvice>() {

						@Override
						public PaymentAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
							PaymentAdviceTcodes payAdvice = new PaymentAdviceTcodes();
							PaymentAdvice payAdviceTcodes = new PaymentAdvice(payAdvice);

							payAdviceTcodes.getPayTcodes().setLogicalLocCd(rs.getString("logical_loc_cd"));
							payAdviceTcodes.getPayTcodes().setEntityCd(rs.getString("entity_cd"));
							payAdviceTcodes.getPayTcodes().setSectionCd(rs.getString("section_cd"));
							payAdviceTcodes.getPayTcodes().setAdviceNo(rs.getInt("advice_no"));
							payAdviceTcodes.getPayTcodes().setT1(rs.getString("t1"));
							payAdviceTcodes.getPayTcodes().setT2(rs.getString("t2"));
							payAdviceTcodes.getPayTcodes().setT3(rs.getString("t3"));
							payAdviceTcodes.getPayTcodes().setT4(rs.getString("t4"));
							payAdviceTcodes.getPayTcodes().setT5(rs.getString("t5"));
							payAdviceTcodes.getPayTcodes().setT6(rs.getString("t6"));
							payAdviceTcodes.getPayTcodes().setT7(rs.getString("t7"));
							payAdviceTcodes.getPayTcodes().setT8(rs.getString("t8"));
							payAdviceTcodes.getPayTcodes().setT9(rs.getString("t9"));
							payAdviceTcodes.getPayTcodes().setT10(rs.getString("t10"));
							payAdviceTcodes.getPayTcodes().setT11(rs.getString("t11"));
							payAdviceTcodes.getPayTcodes().setT12(rs.getString("t12"));
							payAdviceTcodes.getPayTcodes().setT13(rs.getString("t13"));
							payAdviceTcodes.getPayTcodes().setT14(rs.getString("t14"));
							payAdviceTcodes.getPayTcodes().setT15(rs.getString("t15"));
							payAdviceTcodes.getPayTcodes().setT16(rs.getString("t16"));
							payAdviceTcodes.getPayTcodes().setT17(rs.getString("t17"));
							payAdviceTcodes.getPayTcodes().setT18(rs.getString("t18"));
							payAdviceTcodes.getPayTcodes().setT19(rs.getString("t19"));
							payAdviceTcodes.getPayTcodes().setT20(rs.getString("t20"));
							payAdviceTcodes.getPayTcodes().setT21(rs.getString("t21"));
							payAdviceTcodes.getPayTcodes().setT22(rs.getString("t22"));
							payAdviceTcodes.getPayTcodes().setT23(rs.getString("t23"));
							payAdviceTcodes.getPayTcodes().setT24(rs.getString("t24"));
							payAdviceTcodes.getPayTcodes().setT25(rs.getString("t25"));
							payAdviceTcodes.getPayTcodes().setT26(rs.getString("t26"));
							payAdviceTcodes.getPayTcodes().setT27(rs.getString("t27"));
							payAdviceTcodes.getPayTcodes().setT28(rs.getString("t28"));
							payAdviceTcodes.getPayTcodes().setT29(rs.getString("t29"));
							payAdviceTcodes.getPayTcodes().setT30(rs.getString("t30"));
							payAdviceTcodes.getPayTcodes().setT31(rs.getString("t31"));
							payAdviceTcodes.getPayTcodes().setT32(rs.getString("t32"));
							payAdviceTcodes.getPayTcodes().setT33(rs.getString("t33"));
							payAdviceTcodes.getPayTcodes().setAd1(rs.getDate("ad1").toLocalDate());
							payAdviceTcodes.getPayTcodes().setAd2(rs.getDate("ad2").toLocalDate());
							payAdviceTcodes.getPayTcodes().setAd3(rs.getDate("ad3").toLocalDate());
							payAdviceTcodes.getPayTcodes().setAd4(rs.getDate("ad4").toLocalDate());
							return payAdviceTcodes;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			payAdviceTcodesList = null;
			LOGGER.info("Exception Occured {}",e);
		}
		return payAdviceTcodesList;

	}

	@Override
	public PaymentAdvice getPaymentAdviceTcodesByAdviceDtl(String logicalLocCd,String sectionCd,Integer adviceNo) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#getPaymentAdviceTcodesByAdviceDtl");
		Map<String, String> param = new HashMap<>();


		PaymentAdvice payAdviceTcodes = null;

		try {
			param.put("adviceNo", adviceNo.toString());
			param.put("sectionCd", sectionCd);
			param.put("logicalLocCd", logicalLocCd);
			payAdviceTcodes = namedParameterJdbcTemplate.queryForObject(PaymentAdviceSqlQueries.LIST_PAYMENT_ADVICE_TCODES_DTL_BY_ADVICE_DTL,param,
					new RowMapper<PaymentAdvice>() {

						@Override
						public PaymentAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {

							PaymentAdviceTcodes payAdvice = new PaymentAdviceTcodes();
							PaymentAdvice payAdviceTcodes = new PaymentAdvice(payAdvice);

							payAdviceTcodes.getPayTcodes().setLogicalLocCd(rs.getString("logical_loc_cd"));
							payAdviceTcodes.getPayTcodes().setEntityCd(rs.getString("entity_cd"));
							payAdviceTcodes.getPayTcodes().setSectionCd(rs.getString("section_cd"));
							payAdviceTcodes.getPayTcodes().setAdviceNo(rs.getInt("advice_no"));
							payAdviceTcodes.getPayTcodes().setT1(rs.getString("t1"));
							payAdviceTcodes.getPayTcodes().setT2(rs.getString("t2"));
							payAdviceTcodes.getPayTcodes().setT3(rs.getString("t3"));
							payAdviceTcodes.getPayTcodes().setT4(rs.getString("t4"));
							payAdviceTcodes.getPayTcodes().setT5(rs.getString("t5"));
							payAdviceTcodes.getPayTcodes().setT6(rs.getString("t6"));
							payAdviceTcodes.getPayTcodes().setT7(rs.getString("t7"));
							payAdviceTcodes.getPayTcodes().setT8(rs.getString("t8"));
							payAdviceTcodes.getPayTcodes().setT9(rs.getString("t9"));
							payAdviceTcodes.getPayTcodes().setT10(rs.getString("t10"));
							payAdviceTcodes.getPayTcodes().setT11(rs.getString("t11"));
							payAdviceTcodes.getPayTcodes().setT12(rs.getString("t12"));
							payAdviceTcodes.getPayTcodes().setT13(rs.getString("t13"));
							payAdviceTcodes.getPayTcodes().setT14(rs.getString("t14"));
							payAdviceTcodes.getPayTcodes().setT15(rs.getString("t15"));
							payAdviceTcodes.getPayTcodes().setT16(rs.getString("t16"));
							payAdviceTcodes.getPayTcodes().setT17(rs.getString("t17"));
							payAdviceTcodes.getPayTcodes().setT18(rs.getString("t18"));
							payAdviceTcodes.getPayTcodes().setT19(rs.getString("t19"));
							payAdviceTcodes.getPayTcodes().setT20(rs.getString("t20"));
							payAdviceTcodes.getPayTcodes().setT21(rs.getString("t21"));
							payAdviceTcodes.getPayTcodes().setT22(rs.getString("t22"));
							payAdviceTcodes.getPayTcodes().setT23(rs.getString("t23"));
							payAdviceTcodes.getPayTcodes().setT24(rs.getString("t24"));
							payAdviceTcodes.getPayTcodes().setT25(rs.getString("t25"));
							payAdviceTcodes.getPayTcodes().setT26(rs.getString("t26"));
							payAdviceTcodes.getPayTcodes().setT27(rs.getString("t27"));
							payAdviceTcodes.getPayTcodes().setT28(rs.getString("t28"));
							payAdviceTcodes.getPayTcodes().setT29(rs.getString("t29"));
							payAdviceTcodes.getPayTcodes().setT30(rs.getString("t30"));
							payAdviceTcodes.getPayTcodes().setT31(rs.getString("t31"));
							payAdviceTcodes.getPayTcodes().setT32(rs.getString("t32"));
							payAdviceTcodes.getPayTcodes().setT33(rs.getString("t33"));
							payAdviceTcodes.getPayTcodes().setAd1(rs.getDate("ad1").toLocalDate());
							payAdviceTcodes.getPayTcodes().setAd2(rs.getDate("ad2").toLocalDate());
							payAdviceTcodes.getPayTcodes().setAd3(rs.getDate("ad3").toLocalDate());
							payAdviceTcodes.getPayTcodes().setAd4(rs.getDate("ad4").toLocalDate());

							return payAdviceTcodes;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			payAdviceTcodes = null;
			LOGGER.info("Exception Occured {}",e);
		}
		return payAdviceTcodes;
	}

	@Override
	public PaymentAdviceTcodes getPaymentAdviceTcodesDtl(String logicalLocCd,String sectionCd,Integer adviceNo) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#getPaymentAdviceTcodesDtl");
		Map<String, String> param = new HashMap<>();

		PaymentAdviceTcodes payAdviceTcodes = null;

		try {
			param.put("adviceNo", adviceNo.toString());
			param.put("sectionCd", sectionCd);
			param.put("logicalLocCd", logicalLocCd);
			payAdviceTcodes = namedParameterJdbcTemplate.queryForObject(PaymentAdviceSqlQueries.LIST_PAYMENT_ADVICE_TCODES_DTL_BY_ADVICE_DTL,param,
					new RowMapper<PaymentAdviceTcodes>() {

						@Override
						public PaymentAdviceTcodes mapRow(ResultSet rs, int rowNum) throws SQLException {

							PaymentAdviceTcodes payAdviceTcodes = new PaymentAdviceTcodes();

							payAdviceTcodes.setLogicalLocCd(rs.getString("logical_loc_cd"));
							payAdviceTcodes.setEntityCd(rs.getString("entity_cd"));
							payAdviceTcodes.setSectionCd(rs.getString("section_cd"));
							payAdviceTcodes.setAdviceNo(rs.getInt("advice_no"));
							payAdviceTcodes.setT1(rs.getString("t1"));
							payAdviceTcodes.setT2(rs.getString("t2"));
							payAdviceTcodes.setT3(rs.getString("t3"));
							payAdviceTcodes.setT4(rs.getString("t4"));
							payAdviceTcodes.setT5(rs.getString("t5"));
							payAdviceTcodes.setT6(rs.getString("t6"));
							payAdviceTcodes.setT7(rs.getString("t7"));
							payAdviceTcodes.setT8(rs.getString("t8"));
							payAdviceTcodes.setT9(rs.getString("t9"));
							payAdviceTcodes.setT10(rs.getString("t10"));
							payAdviceTcodes.setT11(rs.getString("t11"));
							payAdviceTcodes.setT12(rs.getString("t12"));
							payAdviceTcodes.setT13(rs.getString("t13"));
							payAdviceTcodes.setT14(rs.getString("t14"));
							payAdviceTcodes.setT15(rs.getString("t15"));
							payAdviceTcodes.setT16(rs.getString("t16"));
							payAdviceTcodes.setT17(rs.getString("t17"));
							payAdviceTcodes.setT18(rs.getString("t18"));
							payAdviceTcodes.setT19(rs.getString("t19"));
							payAdviceTcodes.setT20(rs.getString("t20"));
							payAdviceTcodes.setT21(rs.getString("t21"));
							payAdviceTcodes.setT22(rs.getString("t22"));
							payAdviceTcodes.setT23(rs.getString("t23"));
							payAdviceTcodes.setT24(rs.getString("t24"));
							payAdviceTcodes.setT25(rs.getString("t25"));
							payAdviceTcodes.setT26(rs.getString("t26"));
							payAdviceTcodes.setT27(rs.getString("t27"));
							payAdviceTcodes.setT28(rs.getString("t28"));
							payAdviceTcodes.setT29(rs.getString("t29"));
							payAdviceTcodes.setT30(rs.getString("t30"));
							payAdviceTcodes.setT31(rs.getString("t31"));
							payAdviceTcodes.setT32(rs.getString("t32"));
							payAdviceTcodes.setT33(rs.getString("t33"));
							

							if (rs.getDate("ad1")==null) {
								payAdviceTcodes.setAd1(null);
							} else {
								payAdviceTcodes.setAd1(rs.getDate("ad1").toLocalDate());
							}
							if (rs.getDate("ad2")==null) {
								payAdviceTcodes.setAd2(null);
							} else {
								payAdviceTcodes.setAd2(rs.getDate("ad2").toLocalDate());
							}
							if (rs.getDate("ad3")==null) {
								payAdviceTcodes.setAd3(null);
							} else {
								payAdviceTcodes.setAd3(rs.getDate("ad3").toLocalDate());
							}
							if (rs.getDate("ad4")==null) {
								payAdviceTcodes.setAd4(null);
							} else {
								payAdviceTcodes.setAd4(rs.getDate("ad4").toLocalDate());
							}


							return payAdviceTcodes;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			payAdviceTcodes = null;
			LOGGER.info("Exception Occured : {}",e.getMessage());
		}
		return payAdviceTcodes;
	}

	@Override
	public Integer disablePaymentAdviceTcodes(String logicalLocCd,String sectionCd,Integer adviceNo) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#disablePaymentAdviceTcodes");
		Map<String, String> param = new HashMap<String, String>();
		param.put("adviceNo", adviceNo.toString());
		param.put("sectionCd", sectionCd);
		param.put("logicalLocCd", logicalLocCd);
		return namedParameterJdbcOperations.update(PaymentAdviceSqlQueries.DISABLE_PAYMENT_ADVICE_TCODES_DTL, param);
	}

	@Override
	public List<PaymentAdvice> getPaymentAdviceByLogicalLocSectionCd(String logicalLocCd, String sectionCd,LocalDate fromDt, LocalDate toDt) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#getPaymentAdviceByLogicalLocSectionCd");
		List<PaymentAdvice> paymentAdviceList = new ArrayList<>();

		try {
			Map<String, Object> param = new HashMap<>();
			param.put("sectionCd", sectionCd);
			param.put("logicalLocCd", logicalLocCd);
			param.put("fromDt", fromDt);
			param.put("toDt", toDt);
			paymentAdviceList = namedParameterJdbcTemplate.query(PaymentAdviceSqlQueries.GET_PAYMENT_ADVICE_BY_LOGICALlOC_SECCODE,param,
					new RowMapper<PaymentAdvice>() {

				//advice_no , advice_amt,advice_desc ,advice_stat , created_dt



						@Override
						public PaymentAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {

							PaymentAdvice payAdvice = new PaymentAdvice();
							payAdvice.setAdviceNo(rs.getInt("advice_no"));
						//	payAdvice.setLogicalLocCd(rs.getString("logical_loc_cd"));
							//payAdvice.setSectionCd(rs.getString("section_cd"));
							payAdvice.setAdviceAmt(rs.getDouble("advice_amt"));
							payAdvice.setAdviceDesc(rs.getString("advice_desc"));
							payAdvice.setAdviceStatus(rs.getString("advice_stat"));
							if (rs.getDate("created_dt")==null) {
								payAdvice.setCreatedDate(null);
								} else {
								payAdvice.setCreatedDate(rs.getDate("created_dt").toLocalDate());
							}


							return payAdvice;
						}
					});
		} catch (EmptyResultDataAccessException e) {

			LOGGER.info("Exception Occured {}",e.getMessage());
		}
		return paymentAdviceList;
	}

	@Override
	public List<PaymentAdvice> getEntrPaymentAdvice(String logicalLocCd, String sectionCd, String adviceStat) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#getEntrPaymentAdvice");
		List<PaymentAdvice> entrPayAdviceList = null;

		try {
				Map<String, Object> param = new HashMap<>();
				param.put("sectionCd", sectionCd);
				param.put("logicalLocCd", logicalLocCd);
				param.put("adviceStat", adviceStat);
			entrPayAdviceList = namedParameterJdbcTemplate.query(PaymentAdviceSqlQueries.GET_PAYMENT_ADVICE_REQUEST,param,
					new RowMapper<PaymentAdvice>() {

						@Override
						public PaymentAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {

							PaymentAdvice payAdvice = new PaymentAdvice();
							payAdvice.setAdviceAmt(rs.getDouble("advice_amt"));
							payAdvice.setAdviceDesc(rs.getString("advice_desc"));
							if (rs.getDate("advice_dt")==null) {
								payAdvice.setAdviceDate(null);
							} else {
								payAdvice.setAdviceDate(rs.getDate("advice_dt").toLocalDate());
							}
							payAdvice.setAdviceNo(rs.getInt("advice_no"));
							payAdvice.setSectionCd(rs.getString("section_cd"));
							payAdvice.setLogicalLocCd(rs.getString("logical_loc_cd"));
							return payAdvice;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			entrPayAdviceList = null;
			LOGGER.info("Exception Occured {}",e.getMessage());
		}

		return entrPayAdviceList;
	}


	@Override
	public Integer takeDecisionOnPaymentAdvice(PaymentAdvice paymentAdvice) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#takeDecisionOnPaymentAdvice");
		Map<String, Object> param = new HashMap<>();
		param.put("adviceStat", paymentAdvice.getAdviceStatus());
		param.put("decBy", paymentAdvice.getDecBy());
		param.put("decDt", paymentAdvice.getBillDt());
		param.put("decRemark", paymentAdvice.getDecRemark());
		param.put("aprvAmt", paymentAdvice.getAprvAmt());
		param.put("adviceNo", paymentAdvice.getAdviceNo());
		param.put("logicalLocCd", paymentAdvice.getLogicalLocCd());
		param.put("sectionCd", paymentAdvice.getSectionCd());

		return namedParameterJdbcOperations.update(PaymentAdviceSqlQueries.TAKE_PAYMENT_ADVICE_DECISION, param);
	}


	@Override
	public List<PaymentAdvice> getAdviceNumberByLocSecDt(String logicalLocCd, String sectionCd,LocalDate fromDt, LocalDate toDt) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#getAdviceNumberByLocSecDt");
		List<PaymentAdvice> paymentAdviceList = new ArrayList<>();

		try {
			Map<String, Object> param = new HashMap<>();
			param.put("sectionCd", sectionCd);
			param.put("logicalLocCd", logicalLocCd);
			param.put("fromDt", fromDt);
			param.put("toDt", toDt);
			paymentAdviceList = namedParameterJdbcTemplate.query(PaymentAdviceSqlQueries.GET_PAYMENT_ADVICE_NUMBER_BET_DT,param,
					new RowMapper<PaymentAdvice>() {

						@Override
						public PaymentAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {

							PaymentAdvice payAdvice = new PaymentAdvice();
							payAdvice.setSectionCd(rs.getString("section_cd"));
							payAdvice.setAdviceNo(rs.getInt("advice_no"));
							if (rs.getDate("advice_dt")==null) {
								payAdvice.setAdviceDate(null);
								} else {
								payAdvice.setAdviceDate(rs.getDate("advice_dt").toLocalDate());
							}
							payAdvice.setAdviceAmt(rs.getDouble("advice_amt"));
							payAdvice.setAdviceStatus(rs.getString("advice_stat"));
							payAdvice.setAdviceDesc(rs.getString("advice_desc"));

							return payAdvice;
						}
					});
		} catch (EmptyResultDataAccessException e) {

			LOGGER.info("Exception Occured {}",e.getMessage());
		}
		return paymentAdviceList;
	}

	@Override
	public List<PaymentAdvice> getAdviceNumberByLocSec(String logicalLocCd, String sectionCd) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#getAdviceNumberByLocSec");
		List<PaymentAdvice> paymentAdviceList = new ArrayList<>();

		try {
			Map<String, Object> param = new HashMap<>();
			param.put("sectionCd", sectionCd);
			param.put("logicalLocCd", logicalLocCd);
			paymentAdviceList = namedParameterJdbcTemplate.query(PaymentAdviceSqlQueries.GET_PAYMENT_ADVICE_NUMBER,param,
						new RowMapper<PaymentAdvice>() {

							@Override
							public PaymentAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {

								PaymentAdvice payAdvice = new PaymentAdvice();
								payAdvice.setSectionCd(rs.getString("section_cd"));
								payAdvice.setAdviceNo(rs.getInt("advice_no"));
								if (rs.getDate("advice_dt")==null) {
									payAdvice.setAdviceDate(null);
									} else {
									payAdvice.setAdviceDate(rs.getDate("advice_dt").toLocalDate());
								}
								payAdvice.setAdviceAmt(rs.getDouble("advice_amt"));
								payAdvice.setAdviceStatus(rs.getString("advice_stat"));
								payAdvice.setAdviceDesc(rs.getString("advice_desc"));
								return payAdvice;
							}
						});

		} catch (EmptyResultDataAccessException e) {

			LOGGER.info("Exception Occured {}",e.getMessage());
		}
		return paymentAdviceList;
	}



	@Override
	public List<PaymentAdvice> getApprPaymentAdviceByDtl(String logicalLocCd, String sectionCd,String fiscalYear) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#getApprPaymentAdviceByDtl");
		List<PaymentAdvice> paymentAdviceList = new ArrayList<>();

		try {
			Map<String, Object> param = new HashMap<>();
			param.put("sectionCd", sectionCd);
			param.put("logicalLocCd", logicalLocCd);
			param.put("fiscalYear", fiscalYear);
			paymentAdviceList = namedParameterJdbcTemplate.query(PaymentAdviceSqlQueries.GET_APPR_PAYMENT_ADVICE_BY_DTL ,param,
					new RowMapper<PaymentAdvice>() {

						@Override
						public PaymentAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {

							PaymentAdvice payAdvice = new PaymentAdvice();
							payAdvice.setSectionCd(rs.getString("section_cd"));
							payAdvice.setAdviceNo(rs.getInt("advice_no"));
							if (rs.getDate("advice_dt")==null) {
								payAdvice.setAdviceDate(null);
								} else {
								payAdvice.setAdviceDate(rs.getDate("advice_dt").toLocalDate());
							}
							payAdvice.setAdviceAmt(rs.getDouble("advice_amt"));
							payAdvice.setAdviceStatus(rs.getString("advice_stat"));
							payAdvice.setAdviceDesc(rs.getString("advice_desc"));
							payAdvice.setLogicalLocCd(rs.getString("logical_loc_cd"));
							return payAdvice;
						}
					});
		} catch (EmptyResultDataAccessException e) {

			LOGGER.info("Exception occured {}",e.getMessage());
		}
		return paymentAdviceList;

	}



	@Override
	public List<PaymentAdvice> getAprvAdviceNumberByLocSec(String logicalLocCd, String sectionCd) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#getAprvAdviceNumberByLocSec");
		List<PaymentAdvice> paymentAdviceList = new ArrayList<>();
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("sectionCd", sectionCd);
			param.put("logicalLocCd", logicalLocCd);
			paymentAdviceList = namedParameterJdbcTemplate.query(PaymentAdviceSqlQueries.GET_APRV_PAYMENT_ADVICE_DTL_BY_LOGLOC_AND_SECTION_CD,param,
						new RowMapper<PaymentAdvice>() {

							@Override
							public PaymentAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {

								PaymentAdvice payAdvice = new PaymentAdvice();
								payAdvice.setSectionCd(rs.getString("section_cd"));
								payAdvice.setAdviceNo(rs.getInt("advice_no"));
								if (rs.getDate("advice_dt")==null) {
									payAdvice.setAdviceDate(null);
									} else {
									payAdvice.setAdviceDate(rs.getDate("advice_dt").toLocalDate());
								}
								payAdvice.setAdviceAmt(rs.getDouble("advice_amt"));
								payAdvice.setAdviceStatus(rs.getString("advice_stat"));

								return payAdvice;
							}
						});

		} catch (EmptyResultDataAccessException e) {

			LOGGER.info("Exception Occured {} ",e.getMessage());
		}
		return paymentAdviceList;
	}



	@Override
	public List<PaymentAdvice> getAprvAdviceNumberByLocSecDt(String logicalLocCd, String sectionCd, LocalDate fromDt,
			LocalDate toDt) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#getAprvAdviceNumberByLocSecDt");
List<PaymentAdvice> paymentAdviceList = new ArrayList<>();

		try {
			Map<String, Object> param = new HashMap<>();
			param.put("sectionCd", sectionCd);
			param.put("logicalLocCd", logicalLocCd);
			param.put("fromDt", fromDt);
			param.put("toDt", toDt);
			paymentAdviceList = namedParameterJdbcTemplate.query(PaymentAdviceSqlQueries.GET_APRV_PAYMENT_ADVICE_NUMBER_BET_DT,param,
					new RowMapper<PaymentAdvice>() {

						@Override
						public PaymentAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {

							PaymentAdvice payAdvice = new PaymentAdvice();
							payAdvice.setSectionCd(rs.getString("section_cd"));
							payAdvice.setAdviceNo(rs.getInt("advice_no"));
							if (rs.getDate("advice_dt")==null) {
								payAdvice.setAdviceDate(null);
								} else {
								payAdvice.setAdviceDate(rs.getDate("advice_dt").toLocalDate());
							}
							payAdvice.setAdviceAmt(rs.getDouble("advice_amt"));
							payAdvice.setAdviceStatus(rs.getString("advice_stat"));


							return payAdvice;
						}
					});
		} catch (EmptyResultDataAccessException e) {

			LOGGER.info("Exception occured {}",e.getMessage());
		}
		return paymentAdviceList;
	}



	@Override
	public Integer updatePaymentAdviceDtltoUNPAID(String logicalLocCd, String sectionCd,Integer adviceNo) {
		LOGGER.info("Inside PaymentAdviceDaoImpl#updatePaymentAdviceDtltoUNPAID");
		Map<String, Object> param = new HashMap<String, Object>();
		try {
		param.put("adviceNo", adviceNo);
		param.put("sectionCd", sectionCd);
		param.put("logicalLocCd", logicalLocCd);

		return namedParameterJdbcOperations.update(PaymentAdviceSqlQueries.UPDATE_PAYMENT_ADVICE_TO_UNPAID, param);
		}
		catch(DatabaseOperationFailException e) {
			LOGGER.info("Exception occured {}",e.getMessage());
			return 0;
		}
	}


    @Override
	public Integer updatePaymentAdviceStatus(String logicalLocCd, String sectionCd, Integer adviceNo, String status) {
    	LOGGER.info("Inside PaymentAdviceDaoImpl#updatePaymentAdviceStatus");
		Map<String, Object> param = new HashMap<>();

		param.put("adviceStat", status);
		param.put("adviceNo", adviceNo);
		param.put("logicalLocCd", logicalLocCd);
		param.put("sectionCd", sectionCd);
		param.put("lastUpdatedBy", userInfoService.getUser());

		try {
			return namedParameterJdbcOperations.update(PaymentAdviceSqlQueries.UPDATE_PAYMET_ADVICE_STATUS, param);
		} catch (Exception e) {
			LOGGER.info("Failed to update payment advice status for logical loc : {}, section code : {} and advice no : {}",logicalLocCd,sectionCd,adviceNo);
			throw new FailToInsertDataException(e.getMessage());
		}


	}




}


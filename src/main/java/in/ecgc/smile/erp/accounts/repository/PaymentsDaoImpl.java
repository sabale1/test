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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.exception.FailToInsertDataException;
import in.ecgc.smile.erp.accounts.exception.PaymentAdviceCreateException;
import in.ecgc.smile.erp.accounts.exception.PaymentAdviceEmptyListException;
import in.ecgc.smile.erp.accounts.exception.PaymentAdviceRecordNotFoundException;
import in.ecgc.smile.erp.accounts.model.Payments;
import in.ecgc.smile.erp.accounts.model.PaymentsTcodes;
import in.ecgc.smile.erp.accounts.service.GlTxnService;
import in.ecgc.smile.erp.accounts.util.PaymentsQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class PaymentsDaoImpl implements PaymentsDao{
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	GlTxnService glTxnService;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	
	public  PaymentsDaoImpl(DataSource dataSource){
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public MapSqlParameterSource getPaymentsParamsMap(Payments payments){
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("entityCd" , payments.getEntityCd());
		paramSource.addValue("logicallocCd" , payments.getLogicallocCd());
		paramSource.addValue("pymtNo" , payments.getPymtNo());
		paramSource.addValue("pymtDt" , payments.getPymtDt());
		paramSource.addValue("sectionCd" , payments.getSectionCd());
		paramSource.addValue("adviceNo" , payments.getAdviceNo());
		paramSource.addValue("payToType" , payments.getPayToType());
		paramSource.addValue("pymtPartyCd" , payments.getPymtPartyCd());
		paramSource.addValue("pymtPartyName" , payments.getPymtPartyName());
		paramSource.addValue("amtPaid" , payments.getAmtPaid());
		paramSource.addValue("remarks" , payments.getRemarks());
		paramSource.addValue("instrumentType" , payments.getInstrumentType());
		paramSource.addValue("instrumentNo" , payments.getInstrumentNo());
		paramSource.addValue("drawnOn" , payments.getDrawnOn());
		paramSource.addValue("favouring" , payments.getFavouring());
		paramSource.addValue("pymtToEmployee" , payments.getPymtToEmployee());
		paramSource.addValue("pymtInForex" , payments.getPymtInForex());
		paramSource.addValue("exchrateAtBillIwd" , payments.getExchrateAtBillIwd());
		paramSource.addValue("exchrateAtPymt" , payments.getExchrateAtPymt());
		paramSource.addValue("moduleCd" , payments.getModuleCd());
		paramSource.addValue("mappingCd" , payments.getMappingCd());
		paramSource.addValue("glFlg" , payments.getGlFlg());
		paramSource.addValue("glTxnType" , payments.getGlTxnType());
		paramSource.addValue("glTxnNo" , payments.getGlTxnNo());
		paramSource.addValue("fiscalYr" , payments.getFiscalYr());
		paramSource.addValue("oldCd" , payments.getOldCd());
		paramSource.addValue("createdBy" , payments.getCreatedBy());
		paramSource.addValue("lastUpdatedBy" , payments.getLastUpdatedBy());
		paramSource.addValue("createdDt" , payments.getCreatedDt());
		paramSource.addValue("lastUpdatedDt" , payments.getLastUpdatedDt());
		paramSource.addValue("metaStatus" , payments.getMetaStatus());
		paramSource.addValue("metaRemarks" , payments.getMetaRemarks());
	return paramSource;
	}
	
	@Override
	public Integer getPaymentNo(String logicalLocCd,String sectionCd,String fiscalYear) {
		log.info("Inside PaymentsDaoImpl#getPaymentNo");
		Map<String, String> param = new HashMap<String, String>();
		param.put("logicallocCd", logicalLocCd);
		param.put("sectionCd", sectionCd);
		param.put("fiscalYr", fiscalYear);
		
		return namedParameterJdbcOperations.queryForObject(PaymentsQueries.GET_PAYMENT_SEQ_NO,param,
				new RowMapper<Integer>() {

					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt(1);
					}
				});
	}
	
	
	@Override
	@Transactional
	public Integer createPaymentEntry(Payments  payments){
		
		log.info("inside paymentsDaoImpl#createPaymentEntry");
		
		Integer paymentSeqNo = getPaymentNo(payments.getLogicallocCd(), payments.getSectionCd(), payments.getFiscalYr());
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("entityCd" , "ECGC");
		paramSource.addValue("logicallocCd" , payments.getLogicallocCd());
		paramSource.addValue("paymentSeqNo" , paymentSeqNo);
		paramSource.addValue("pymtDt" , payments.getPymtDt());
		paramSource.addValue("sectionCd" , payments.getSectionCd());
		paramSource.addValue("adviceNo" , payments.getAdviceNo());
		paramSource.addValue("payToType" , payments.getPayToType());
		paramSource.addValue("pymtPartyCd" , payments.getPymtPartyCd());
		paramSource.addValue("pymtPartyName" , payments.getPymtPartyName());
		paramSource.addValue("amtPaid" , payments.getAmtPaid());
		paramSource.addValue("remarks" , payments.getRemarks());
		paramSource.addValue("instrumentType" , payments.getInstrumentType());
		paramSource.addValue("instrumentNo" , payments.getInstrumentNo());
		paramSource.addValue("drawnOn" , payments.getDrawnOn());
		paramSource.addValue("favouring" , payments.getFavouring());
		paramSource.addValue("pymtToEmployee" , payments.getPymtToEmployee());
		paramSource.addValue("pymtInForex" , payments.getPymtInForex());
		paramSource.addValue("exchrateAtBillIwd" , payments.getExchrateAtBillIwd());
		paramSource.addValue("exchrateAtPymt" , payments.getExchrateAtPymt());
		paramSource.addValue("moduleCd" , payments.getModuleCd());
		paramSource.addValue("mappingCd" , payments.getMappingCd());
		paramSource.addValue("glFlg" , payments.getGlFlg());
		paramSource.addValue("glTxnType" , payments.getGlTxnType());
		paramSource.addValue("glTxnNo" , payments.getGlTxnNo());
		paramSource.addValue("fiscalYr" , payments.getFiscalYr());
		paramSource.addValue("oldCd" , payments.getOldCd());
		paramSource.addValue("createdBy" , userInfoService.getUser());
		paramSource.addValue("metaStatus" , payments.getMetaStatus());
		paramSource.addValue("metaRemarks" , payments.getMetaRemarks());
		
		try {
		
			namedParameterJdbcTemplate.update(PaymentsQueries.ADD_Payments_DATA,paramSource);
			
			payments.getPaymentsTcodes().setPaymentNo(paymentSeqNo);
			Integer paymentTcodeNo = createPaymentTcodesEntry(payments.getPaymentsTcodes());
			if (paymentSeqNo==paymentTcodeNo) {
				return paymentSeqNo;
			}else {
					throw new PaymentAdviceCreateException("Failed to create Payments entry");
				}
			
		} catch (Exception e) {
			log.error("ERROR: DAO Module {}", e.getMessage());
			throw new FailToInsertDataException("Failed to create Payments entry");
		}
	}
	
	@Override
	public Integer createPaymentTcodesEntry(PaymentsTcodes paymentsTcodes) {

		log.info("inside paymentsDaoImpl#createPaymentTcodesEntry");
		
		Map<String, Object> param;
			param = new HashMap<>();
			
			param.put("entityCd", "ECGC");
			param.put("logicalLocCd", paymentsTcodes.getLogicalLocCd());
			param.put("sectionCd", paymentsTcodes.getSectionCd());
			param.put("paymentNo", paymentsTcodes.getPaymentNo());
			param.put("t1", paymentsTcodes.getT1());
			param.put("t2", paymentsTcodes.getT2());
			param.put("t3", paymentsTcodes.getT3());
			param.put("t4", paymentsTcodes.getT4());
			param.put("t5", paymentsTcodes.getT5());
			param.put("t6", paymentsTcodes.getT6());
			param.put("t7", paymentsTcodes.getT7());
			param.put("t8", paymentsTcodes.getT8());
			param.put("t9", paymentsTcodes.getT9());
			param.put("t10", paymentsTcodes.getT10());
			param.put("t11", paymentsTcodes.getT11());
			param.put("t12", paymentsTcodes.getT12());
			param.put("ad1", paymentsTcodes.getAd1());
			param.put("ad2", paymentsTcodes.getAd2());
			param.put("ad3", paymentsTcodes.getAd3());
			param.put("ad4", paymentsTcodes.getAd4());
		try {	
			namedParameterJdbcTemplate.update(PaymentsQueries.ADD_PAYMENTS_TCODES_DTL, param);
			
			return paymentsTcodes.getPaymentNo();
		} catch (DuplicateKeyException e) {
			log.error("ERROR: DAO Module {}", e.getMessage());
			throw new PaymentAdviceCreateException("Failed to create payments entry!");
		}
	}	
		
	@Override
	public Payments getPaymentsByPaymentDtl(Integer pymtNo,String logicalLocCd, String sectionCd){
		
		log.info("inside PaymentsDaoImpl#getPaymentsByPaymentNo");
		
		Map<String, Object>paramsMap= new HashMap<>();
		Payments  payments = new Payments();
		
		try {
			paramsMap.put("pymtNo", pymtNo);
			paramsMap.put("sectionCd", sectionCd);
			paramsMap.put("logicalLocCd", logicalLocCd);
			
			  payments=namedParameterJdbcTemplate.queryForObject(PaymentsQueries.GET_PAYMENTS_BY_PAYMENT_NO, paramsMap, new RowMapper<Payments>() {

				@Override
				public Payments mapRow(ResultSet rs, int rowNum) throws SQLException {

					Payments payments = new Payments();
					
					payments.setEntityCd(rs.getString("entity_cd"));
					payments.setLogicallocCd(rs.getString("logicalloc_cd"));
					payments.setPymtNo(rs.getInt("pymt_no"));
					payments.setPymtDt(rs.getDate("pymt_dt").toLocalDate());
					payments.setSectionCd(rs.getString("section_cd"));
					payments.setAdviceNo(rs.getInt("advice_no"));
					payments.setPayToType(rs.getString("pay_to_type"));
					payments.setPymtPartyCd(rs.getString("pymt_party_cd"));
					payments.setPymtPartyName(rs.getString("pymt_party_name"));
					payments.setAmtPaid(rs.getDouble("amt_paid"));
					payments.setRemarks(rs.getString("remarks"));
					payments.setInstrumentType(rs.getString("instrument_type"));
					payments.setInstrumentNo(rs.getString("instrument_no"));
					payments.setDrawnOn(rs.getString("drawn_on"));
					payments.setFavouring(rs.getString("favouring"));
					payments.setPymtToEmployee(rs.getString("pymt_to_employee"));
					payments.setPymtInForex(rs.getString("pymt_in_forex").charAt(0));
					payments.setExchrateAtBillIwd(rs.getDouble("exchrate_at_bill_iwd"));
					payments.setExchrateAtPymt(rs.getDouble("exchrate_at_pymt"));
					payments.setModuleCd(rs.getString("module_cd"));
					payments.setMappingCd(rs.getString("mapping_cd"));
					payments.setGlFlg(rs.getString("gl_flg"));
					payments.setGlTxnType(rs.getString("gl_txn_type"));
					payments.setGlTxnNo(rs.getInt("gl_txn_no"));
					payments.setFiscalYr(rs.getString("fiscal_yr"));
					payments.setOldCd(rs.getString("old_cd"));
					payments.setCreatedBy(rs.getString("created_by"));
					payments.setLastUpdatedBy(rs.getString("last_updated_by"));
					if(rs.getDate("created_dt")==null) 
						payments.setCreatedDt(null);
					else
						payments.setCreatedDt(rs.getDate("created_dt").toLocalDate());
					if(rs.getDate("last_updated_dt")==null) 
						payments.setLastUpdatedDt(null);
					else
					    payments.setLastUpdatedDt(rs.getDate("last_updated_dt").toLocalDate());
					payments.setMetaStatus(rs.getString("meta_status"));
					payments.setMetaRemarks(rs.getString("meta_remarks"));
					return payments;
				}
			  });
				 
				 return payments;
			} catch (Exception e) {
				log.error("ERROR: DAO Module {}", e.getMessage());
				throw new PaymentAdviceRecordNotFoundException("Failed to get Payments details by payment number");
			}
		}
	
	
	
	@Override
	public PaymentsTcodes getPaymentsTcodesByPaymentsDtl(Integer paymentNo, String logicalLoc, String sectionCd) {
		
		log.info("inside PaymentsDaoImpl#getPaymentsTcodesByPaymentsDtl");
		log.info("inside paymentNo : {}",paymentNo);
		log.info("inside logicalLoc : {}",logicalLoc);
		log.info("inside sectionCd : {}",sectionCd);
		Map<String, Object> param = new HashMap<>();
		PaymentsTcodes paymentsTcodes = new PaymentsTcodes();
		
		try {
			
			param.put("paymentNo", paymentNo);
			param.put("sectionCd", sectionCd);
			param.put("logicalLocCd", logicalLoc);
			
			paymentsTcodes = namedParameterJdbcTemplate.queryForObject(PaymentsQueries.GET_PAYMENTS_TCODES_BY_PAYMENTS_DTL ,param,
					new RowMapper<PaymentsTcodes>() {

						@Override
						public PaymentsTcodes mapRow(ResultSet rs, int rowNum) throws SQLException {

							PaymentsTcodes paymentsTcodes = new PaymentsTcodes();
							
							paymentsTcodes.setLogicalLocCd(rs.getString("logical_loc_cd"));
							paymentsTcodes.setEntityCd(rs.getString("entity_cd"));
							paymentsTcodes.setSectionCd(rs.getString("section_cd"));
							paymentsTcodes.setPaymentNo(rs.getInt("payment_no"));
							paymentsTcodes.setT1(rs.getString("t1"));
							paymentsTcodes.setT2(rs.getString("t2"));
							paymentsTcodes.setT3(rs.getString("t3"));
							paymentsTcodes.setT4(rs.getString("t4"));
							paymentsTcodes.setT5(rs.getString("t5"));
							paymentsTcodes.setT6(rs.getString("t6"));
							paymentsTcodes.setT7(rs.getString("t7"));
							paymentsTcodes.setT8(rs.getString("t8"));
							paymentsTcodes.setT9(rs.getString("t9"));
							paymentsTcodes.setT10(rs.getString("t10"));
							paymentsTcodes.setT11(rs.getString("t11"));
							paymentsTcodes.setT12(rs.getString("t12"));
							
							if (rs.getDate("ad1")==null) {
								paymentsTcodes.setAd1(null);
							} else {
								paymentsTcodes.setAd1(rs.getDate("ad1").toLocalDate());
							}
							if (rs.getDate("ad2")==null) {
								paymentsTcodes.setAd2(null);
							} else {
								paymentsTcodes.setAd2(rs.getDate("ad2").toLocalDate());
							}
							if (rs.getDate("ad3")==null) {
								paymentsTcodes.setAd3(null);
							} else {
								paymentsTcodes.setAd3(rs.getDate("ad3").toLocalDate());
							}
							if (rs.getDate("ad4")==null) {
								paymentsTcodes.setAd4(null);
							} else {
								paymentsTcodes.setAd4(rs.getDate("ad4").toLocalDate());
							}
							return paymentsTcodes;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			paymentsTcodes = null;
			log.error("ERROR: DAO Module {}", e.getMessage());
		}
		catch (Exception e) {
			log.error("ERROR: DAO Module {}", e.getMessage());
			throw new PaymentAdviceRecordNotFoundException("Failed to get Payments T Codes by payment details");
		}
		return paymentsTcodes;
	}
	
	
	///////////////////////////////////
	
	
	@Override
	public List<Payments> getPaymentsList(){
	log.info("inside PaymentsDaoImpl#getPaymentsList");
	List<Payments> list=new ArrayList<>();
	try {
		 String sql = PaymentsQueries.GET_Payments_DATA;
		 list=(List<Payments>) namedParameterJdbcTemplate.query(sql, new PaymentsMapper() );
		 log.info("PaymentsList : {}",list);
		 return list;
	} catch (Exception e) {
		log.error("ERROR: DAO Module {}", e.getMessage());
		throw new PaymentAdviceEmptyListException("Failed to get Payments list");
	}
	
	}
	


	@Override
	public List<Payments> getpaymentsbylocsecdt(String logicalLocCd, String sectionCd, String fromDt, String toDt) {
		log.info("inside PaymentsDaoImpl#getpaymentsbylocsecdt");
		log.info("logicalLocCd : {}",logicalLocCd);
		log.info("sectionCd : {}",sectionCd);
		log.info("fromDt : {}",fromDt);
		log.info("toDt : {}",toDt);
		LocalDate fromDtlocal = LocalDate.parse(fromDt);
		LocalDate toDtlocal = LocalDate.parse(toDt);

		Map<String, Object> paramsMap= new HashMap<>();
		List<Payments> list=new ArrayList<>();
		try {
			paramsMap.put("logicalLocCd", logicalLocCd);
			paramsMap.put("sectionCd", sectionCd);
			paramsMap.put("fromDt", fromDtlocal);
			paramsMap.put("toDt", toDtlocal);
			
			list= namedParameterJdbcTemplate.query(PaymentsQueries.GET_PAYMENTS_BY_LOC_SEC_DT,paramsMap, new RowMapper<Payments>() {

				@Override
				public Payments mapRow(ResultSet rs, int rowNum) throws SQLException {

					Payments payments = new Payments();
					
					payments.setEntityCd(rs.getString("entity_cd"));
					payments.setLogicallocCd(rs.getString("logicalloc_cd"));
					payments.setPymtNo(rs.getInt("pymt_no"));
					payments.setPymtDt(rs.getDate("pymt_dt").toLocalDate());
					payments.setSectionCd(rs.getString("section_cd"));
					payments.setAdviceNo(rs.getInt("advice_no"));
					payments.setPayToType(rs.getString("pay_to_type"));
					payments.setPymtPartyCd(rs.getString("pymt_party_cd"));
					payments.setPymtPartyName(rs.getString("pymt_party_name"));
					payments.setAmtPaid(rs.getDouble("amt_paid"));
					payments.setRemarks(rs.getString("remarks"));
					payments.setInstrumentType(rs.getString("instrument_type"));
					payments.setInstrumentNo(rs.getString("instrument_no"));
					payments.setDrawnOn(rs.getString("drawn_on"));
					payments.setFavouring(rs.getString("favouring"));
					payments.setPymtToEmployee(rs.getString("pymt_to_employee"));
					payments.setPymtInForex(rs.getString("pymt_in_forex").charAt(0));
					payments.setExchrateAtBillIwd(rs.getDouble("exchrate_at_bill_iwd"));
					payments.setExchrateAtPymt(rs.getDouble("exchrate_at_pymt"));
					payments.setModuleCd(rs.getString("module_cd"));
					payments.setMappingCd(rs.getString("mapping_cd"));
					payments.setGlFlg(rs.getString("gl_flg"));
					payments.setGlTxnType(rs.getString("gl_txn_type"));
					payments.setGlTxnNo(rs.getInt("gl_txn_no"));
					payments.setFiscalYr(rs.getString("fiscal_yr"));
					payments.setOldCd(rs.getString("old_cd"));
					payments.setCreatedBy(rs.getString("created_by"));
					payments.setLastUpdatedBy(rs.getString("last_updated_by"));
					if(rs.getDate("created_dt")==null) 
						payments.setCreatedDt(null);
					else
						payments.setCreatedDt(rs.getDate("created_dt").toLocalDate());
					if(rs.getDate("last_updated_dt")==null) 
						payments.setLastUpdatedDt(null);
					else
					payments.setLastUpdatedDt(rs.getDate("last_updated_dt").toLocalDate());
					payments.setMetaStatus(rs.getString("meta_status"));
					payments.setMetaRemarks(rs.getString("meta_remarks"));
					return payments;
				}
			}	);
			 return list;
		} catch (Exception e) {
			log.error("ERROR: DAO Module {}", e.getMessage());
			throw new PaymentAdviceEmptyListException("Failed to get Payments list by location ,section and payment date");
		}
	}


	@Override
	public List<Payments> getpaymentsbydt(String fromDt, String toDt) {
		log.info("inside PaymentsDaoImpl#getpaymentsbydt");
		log.info("fromDt : {}",fromDt);
		log.info("toDt : {}",toDt);
		LocalDate fromDtlocal = LocalDate.parse(fromDt);
		LocalDate toDtlocal = LocalDate.parse(toDt);
		log.info("fromDt : {}",fromDtlocal);
		log.info("toDt : {}",fromDtlocal);

		Map<String, Object> paramsMap= new HashMap<>();
		List<Payments> list=new ArrayList<>();
		try {
			paramsMap.put("fromDate", fromDtlocal);
			paramsMap.put("toDate", toDtlocal);
			
			list= namedParameterJdbcTemplate.query(PaymentsQueries.GET_PAYMENTS_BY_DT,paramsMap, new RowMapper<Payments>() {

				@Override
				public Payments mapRow(ResultSet rs, int rowNum) throws SQLException {

					Payments payments = new Payments();
					
					payments.setEntityCd(rs.getString("entity_cd"));
					payments.setLogicallocCd(rs.getString("logicalloc_cd"));
					payments.setPymtNo(rs.getInt("pymt_no"));
					payments.setPymtDt(rs.getDate("pymt_dt").toLocalDate());
					payments.setSectionCd(rs.getString("section_cd"));
					payments.setAdviceNo(rs.getInt("advice_no"));
					payments.setPayToType(rs.getString("pay_to_type"));
					payments.setPymtPartyCd(rs.getString("pymt_party_cd"));
					payments.setPymtPartyName(rs.getString("pymt_party_name"));
					payments.setAmtPaid(rs.getDouble("amt_paid"));
					payments.setRemarks(rs.getString("remarks"));
					payments.setInstrumentType(rs.getString("instrument_type"));
					payments.setInstrumentNo(rs.getString("instrument_no"));
					payments.setDrawnOn(rs.getString("drawn_on"));
					payments.setFavouring(rs.getString("favouring"));
					payments.setPymtToEmployee(rs.getString("pymt_to_employee"));
					payments.setPymtInForex(rs.getString("pymt_in_forex").charAt(0));
					payments.setExchrateAtBillIwd(rs.getDouble("exchrate_at_bill_iwd"));
					payments.setExchrateAtPymt(rs.getDouble("exchrate_at_pymt"));
					payments.setModuleCd(rs.getString("module_cd"));
					payments.setMappingCd(rs.getString("mapping_cd"));
					payments.setGlFlg(rs.getString("gl_flg"));
					payments.setGlTxnType(rs.getString("gl_txn_type"));
					payments.setGlTxnNo(rs.getInt("gl_txn_no"));
					payments.setFiscalYr(rs.getString("fiscal_yr"));
					payments.setOldCd(rs.getString("old_cd"));
					payments.setCreatedBy(rs.getString("created_by"));
					payments.setLastUpdatedBy(rs.getString("last_updated_by"));
					if(rs.getDate("created_dt")==null) 
						payments.setCreatedDt(null);
					else
						payments.setCreatedDt(rs.getDate("created_dt").toLocalDate());
					if(rs.getDate("last_updated_dt")==null) 
						payments.setLastUpdatedDt(null);
					else
					payments.setLastUpdatedDt(rs.getDate("last_updated_dt").toLocalDate());
					payments.setMetaStatus(rs.getString("meta_status"));
					payments.setMetaRemarks(rs.getString("meta_remarks"));
					return payments;
				}
			}	);
			 return list;
		} catch (Exception e) {
			log.error("ERROR: DAO Module {}", e.getMessage());
			throw new PaymentAdviceEmptyListException("Failed to get Payments list by payment date");
		}
	}

	
	@Override
	public List<Payments> getpaymentsbyLocSecYr(String logicalLocCd, String sectionCd, String fiscalYr) {
		log.info("inside PaymentsDaoImpl#getpaymentsbyLocSecYr");
		log.info("logicalLocCd : {}",logicalLocCd);
		log.info("sectionCd : {}",sectionCd);
		log.info("fiscalYr : {}",fiscalYr);

		Map<String, Object> paramsMap= new HashMap<>();
		List<Payments> list=new ArrayList<>();
		try {
			paramsMap.put("logicalLocCd", logicalLocCd);
			paramsMap.put("sectionCd", sectionCd);
			paramsMap.put("fiscalYr", fiscalYr);
			
			list= namedParameterJdbcTemplate.query(PaymentsQueries.GET_PAYMENTS_BY_LOC_SEC_FIS_YR,paramsMap, new RowMapper<Payments>() {

				@Override
				public Payments mapRow(ResultSet rs, int rowNum) throws SQLException {

					Payments payments = new Payments();
					
					payments.setEntityCd(rs.getString("entity_cd"));
					payments.setLogicallocCd(rs.getString("logicalloc_cd"));
					payments.setPymtNo(rs.getInt("pymt_no"));
					payments.setPymtDt(rs.getDate("pymt_dt").toLocalDate());
					payments.setSectionCd(rs.getString("section_cd"));
					payments.setAdviceNo(rs.getInt("advice_no"));
					payments.setPayToType(rs.getString("pay_to_type"));
					payments.setPymtPartyCd(rs.getString("pymt_party_cd"));
					payments.setPymtPartyName(rs.getString("pymt_party_name"));
					payments.setAmtPaid(rs.getDouble("amt_paid"));
					payments.setRemarks(rs.getString("remarks"));
					payments.setInstrumentType(rs.getString("instrument_type"));
					payments.setInstrumentNo(rs.getString("instrument_no"));
					payments.setDrawnOn(rs.getString("drawn_on"));
					payments.setFavouring(rs.getString("favouring"));
					payments.setPymtToEmployee(rs.getString("pymt_to_employee"));
					payments.setPymtInForex(rs.getString("pymt_in_forex").charAt(0));
					payments.setExchrateAtBillIwd(rs.getDouble("exchrate_at_bill_iwd"));
					payments.setExchrateAtPymt(rs.getDouble("exchrate_at_pymt"));
					payments.setModuleCd(rs.getString("module_cd"));
					payments.setMappingCd(rs.getString("mapping_cd"));
					payments.setGlFlg(rs.getString("gl_flg"));
					payments.setGlTxnType(rs.getString("gl_txn_type"));
					payments.setGlTxnNo(rs.getInt("gl_txn_no"));
					payments.setFiscalYr(rs.getString("fiscal_yr"));
					payments.setOldCd(rs.getString("old_cd"));
					payments.setCreatedBy(rs.getString("created_by"));
					payments.setLastUpdatedBy(rs.getString("last_updated_by"));
					if(rs.getDate("created_dt")==null) 
						payments.setCreatedDt(null);
					else
						payments.setCreatedDt(rs.getDate("created_dt").toLocalDate());
					if(rs.getDate("last_updated_dt")==null) 
						payments.setLastUpdatedDt(null);
					else
					payments.setLastUpdatedDt(rs.getDate("last_updated_dt").toLocalDate());
					payments.setMetaStatus(rs.getString("meta_status"));
					payments.setMetaRemarks(rs.getString("meta_remarks"));
					return payments;
				}
			}	);
			 return list;
		} catch (Exception e) {
			log.error("ERROR: DAO Module {}", e.getMessage());
			throw new PaymentAdviceEmptyListException("Failed to get Payments list by location ,section and ficcal year");
		}
	}

	@Override
	public boolean updatePaymentsData(Payments payments) {
		log.info("Inside PaymentsDaoImpl#updatePaymentsData");
		Map<String, Object> param = new HashMap<>();
		param.put("entityCd" , payments.getEntityCd());
		param.put("logicallocCd" , payments.getLogicallocCd());
		param.put("pymtNo" , payments.getPymtNo());
		param.put("pymtDt" , payments.getPymtDt());
		param.put("adviceNo" , payments.getAdviceNo());
		param.put("payToType" , payments.getPayToType());
		param.put("pymtPartyCd" , payments.getPymtPartyCd());
		param.put("pymtPartyName" , payments.getPymtPartyName());
		param.put("amtPaid" , payments.getAmtPaid());
		param.put("remarks" , payments.getRemarks());
		param.put("instrumentType" , payments.getInstrumentType());
		param.put("instrumentNo" , payments.getInstrumentNo());
		param.put("drawnOn" , payments.getDrawnOn());
		param.put("favouring" , payments.getFavouring());
		param.put("pymtToEmployee" , payments.getPymtToEmployee());
		param.put("pymtInForex" , payments.getPymtInForex());
		param.put("exchrateAtBillIwd" , payments.getExchrateAtBillIwd());
		param.put("exchrateAtPymt" , payments.getExchrateAtPymt());
		param.put("moduleCd" , payments.getModuleCd());
		param.put("mappingCd" , payments.getMappingCd());
		param.put("oldCd" , payments.getOldCd());
		param.put("createdBy" , payments.getCreatedBy());
		param.put("lastUpdatedBy" , payments.getLastUpdatedBy());
		param.put("createdDt" , payments.getCreatedDt());
		param.put("lastUpdatedDt" , LocalDate.now());
		param.put("metaStatus" , payments.getMetaStatus());
		param.put("metaRemarks" , payments.getMetaRemarks());
		log.info("param data is :{}",param);
		
		try {
			int result= namedParameterJdbcTemplate.update(PaymentsQueries.UPDATE_PAYMENTS_DATA, param);
			log.info("result is : {}",result);
			if(result==1)
				return true;
			else 
				return false;
		}catch (Exception e) {
			log.error("ERROR: DAO Module {}", e.getMessage());
			return false;
		}
		
		
	}

	@Override
	public boolean updatePaymentTcodes(Payments payments) {
		log.info("Inside PaymentsDaoImpl#updatePaymentTcodes");
		Map<String, Object> param = new HashMap<>();
		param.put("entityCd", "ECGC");
		param.put("logicalLocCd", payments.getPaymentsTcodes().getLogicalLocCd());
		param.put("sectionCd", payments.getPaymentsTcodes().getSectionCd());
		param.put("paymentNo", payments.getPaymentsTcodes().getPaymentNo());
		param.put("t1", payments.getPaymentsTcodes().getT1());
		param.put("t2", payments.getPaymentsTcodes().getT2());
		param.put("t3", payments.getPaymentsTcodes().getT3());
		param.put("t4", payments.getPaymentsTcodes().getT4());
		param.put("t5", payments.getPaymentsTcodes().getT5());
		param.put("t6", payments.getPaymentsTcodes().getT6());
		param.put("t7", payments.getPaymentsTcodes().getT7());
		param.put("t8", payments.getPaymentsTcodes().getT8());
		param.put("t9", payments.getPaymentsTcodes().getT9());
		param.put("t10", payments.getPaymentsTcodes().getT10());
		param.put("t11", payments.getPaymentsTcodes().getT11());
		param.put("t12", payments.getPaymentsTcodes().getT12());
		param.put("ad1", payments.getPaymentsTcodes().getAd1());
		param.put("ad2", payments.getPaymentsTcodes().getAd2());
		param.put("ad3", payments.getPaymentsTcodes().getAd3());
		param.put("ad4", payments.getPaymentsTcodes().getAd4());
		log.info("Param data is : {}",param);
		try {
			int result = namedParameterJdbcTemplate.update(PaymentsQueries.UPDATE_PAYMENTS_TCODES_DTL, param);
			if(result==1)
				return true;
			else 
				return false;
		}catch (Exception e) {
			log.error("ERROR: DAO Module {}", e.getMessage());
			return false;
		}
		
		}

	@Override
	public Payments getPaymentsByPymtNo(Integer paymentNo, String logicalLocCd, String sectionCd) {
		log.info("inside PaymentsDaoImpl#getPaymentsByPymtNo");
		log.info("logicalLocCd : {}",logicalLocCd);
		log.info("sectionCd : {}",sectionCd);
		log.info("paymentNo : {}",paymentNo);

		Map<String, Object> paramsMap= new HashMap<>();
		Payments payments = new Payments();
		try {
			paramsMap.put("logicalLocCd", logicalLocCd);
			paramsMap.put("sectionCd", sectionCd);
			paramsMap.put("paymentNo", paymentNo);
			
			payments= namedParameterJdbcTemplate.queryForObject(PaymentsQueries.GET_PAYMENTS_BY_PYMTNO_LOC_SEC,paramsMap, new RowMapper<Payments>() {

				@Override
				public Payments mapRow(ResultSet rs, int rowNum) throws SQLException {

					log.info("Inside rowMapper");
					Payments payments = new Payments();
					payments.setEntityCd(rs.getString("entity_cd"));
					payments.setLogicallocCd(rs.getString("logicalloc_cd"));
					payments.setPymtNo(rs.getInt("pymt_no"));
					payments.setPymtDt(rs.getDate("pymt_dt").toLocalDate());
					payments.setSectionCd(rs.getString("section_cd"));
					payments.setAdviceNo(rs.getInt("advice_no"));
					payments.setPayToType(rs.getString("pay_to_type"));
					payments.setPymtPartyCd(rs.getString("pymt_party_cd"));
					payments.setPymtPartyName(rs.getString("pymt_party_name"));
					payments.setAmtPaid(rs.getDouble("amt_paid"));
					payments.setRemarks(rs.getString("remarks"));
					payments.setInstrumentType(rs.getString("instrument_type"));
					payments.setInstrumentNo(rs.getString("instrument_no"));
					payments.setDrawnOn(rs.getString("drawn_on"));
					payments.setFavouring(rs.getString("favouring"));
					payments.setPymtToEmployee(rs.getString("pymt_to_employee"));
					payments.setPymtInForex(rs.getString("pymt_in_forex").charAt(0));
					payments.setExchrateAtBillIwd(rs.getDouble("exchrate_at_bill_iwd"));
					payments.setExchrateAtPymt(rs.getDouble("exchrate_at_pymt"));
					payments.setModuleCd(rs.getString("module_cd"));
					payments.setMappingCd(rs.getString("mapping_cd"));
					payments.setGlFlg(rs.getString("gl_flg"));
					payments.setGlTxnType(rs.getString("gl_txn_type"));
					payments.setGlTxnNo(rs.getInt("gl_txn_no"));
					payments.setFiscalYr(rs.getString("fiscal_yr"));
					payments.setOldCd(rs.getString("old_cd"));
					payments.setCreatedBy(rs.getString("created_by"));
					payments.setLastUpdatedBy(rs.getString("last_updated_by"));
					if(rs.getDate("created_dt")==null) 
						payments.setCreatedDt(null);
					else
						payments.setCreatedDt(rs.getDate("created_dt").toLocalDate());
					if(rs.getDate("last_updated_dt")==null) 
						payments.setLastUpdatedDt(null);
					else
						payments.setLastUpdatedDt(rs.getDate("last_updated_dt").toLocalDate());
					payments.setMetaStatus(rs.getString("meta_status"));
					payments.setMetaRemarks(rs.getString("meta_remarks"));
					
					log.info("Payments obj is : {}", payments);
					return payments;
				}
			}	);
			
		} catch (Exception e) {
			log.error("ERROR: DAO Module {}", e.getMessage());
			throw new PaymentAdviceRecordNotFoundException("Failed to get Payments by location ,section and Payment Number");
		}
		log.info("Required Payments object is : {}",payments);
		return payments;
	}
	
	


	

}


class PaymentsMapper implements RowMapper<Payments> {
	@Override
		
		public Payments mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Payments payments = new Payments();
	
		payments.setEntityCd(rs.getString("entity_cd"));
		payments.setLogicallocCd(rs.getString("logicalloc_cd"));
		payments.setPymtNo(rs.getInt("pymt_no"));
		payments.setPymtDt(rs.getDate("pymt_dt").toLocalDate());
		payments.setSectionCd(rs.getString("section_cd"));
		payments.setAdviceNo(rs.getInt("advice_no"));
		payments.setPayToType(rs.getString("pay_to_type"));
		payments.setPymtPartyCd(rs.getString("pymt_party_cd"));
		payments.setPymtPartyName(rs.getString("pymt_party_name"));
		payments.setAmtPaid(rs.getDouble("amt_paid"));
		payments.setRemarks(rs.getString("remarks"));
		payments.setInstrumentType(rs.getString("instrument_type"));
		payments.setInstrumentNo(rs.getString("instrument_no"));
		payments.setDrawnOn(rs.getString("drawn_on"));
		payments.setFavouring(rs.getString("favouring"));
		payments.setPymtToEmployee(rs.getString("pymt_to_employee"));
		payments.setPymtInForex(rs.getString("pymt_in_forex").charAt(0));
		payments.setExchrateAtBillIwd(rs.getDouble("exchrate_at_bill_iwd"));
		payments.setExchrateAtPymt(rs.getDouble("exchrate_at_pymt"));
		payments.setModuleCd(rs.getString("module_cd"));
		payments.setMappingCd(rs.getString("mapping_cd"));
		payments.setGlFlg(rs.getString("gl_flg"));
		payments.setGlTxnType(rs.getString("gl_txn_type"));
		payments.setGlTxnNo(rs.getInt("gl_txn_no"));
		payments.setFiscalYr(rs.getString("fiscal_yr"));
		payments.setOldCd(rs.getString("old_cd"));
		payments.setCreatedBy(rs.getString("created_by"));
		payments.setLastUpdatedBy(rs.getString("last_updated_by"));
		payments.setCreatedDt(rs.getDate("created_dt").toLocalDate());
		payments.setLastUpdatedDt(rs.getDate("last_updated_dt").toLocalDate());
		payments.setMetaStatus(rs.getString("meta_status"));
		payments.setMetaRemarks(rs.getString("meta_remarks"));
		return payments;
	}
	}
class PaymentsResultSetExtractor implements ResultSetExtractor<Payments> {
	@Override
public Payments extractData(ResultSet rs) throws SQLException,DataAccessException {
	Payments payments = null;
	if (rs.next()) {
		payments = new Payments();
		payments.setEntityCd(rs.getString("entity_cd"));
		payments.setLogicallocCd(rs.getString("logicalloc_cd"));
		payments.setPymtNo(rs.getInt("pymt_no"));
		payments.setPymtDt(rs.getDate("pymt_dt").toLocalDate());
		payments.setSectionCd(rs.getString("section_cd"));
		payments.setAdviceNo(rs.getInt("advice_no"));
		payments.setPayToType(rs.getString("pay_to_type"));
		payments.setPymtPartyCd(rs.getString("pymt_party_cd"));
		payments.setPymtPartyName(rs.getString("pymt_party_name"));
		payments.setAmtPaid(rs.getDouble("amt_paid"));
		payments.setRemarks(rs.getString("remarks"));
		payments.setInstrumentType(rs.getString("instrument_type"));
		payments.setInstrumentNo(rs.getString("instrument_no"));
		payments.setDrawnOn(rs.getString("drawn_on"));
		payments.setFavouring(rs.getString("favouring"));
		payments.setPymtToEmployee(rs.getString("pymt_to_employee"));
		payments.setPymtInForex(rs.getString("pymt_in_forex").charAt(0));
		payments.setExchrateAtBillIwd(rs.getDouble("exchrate_at_bill_iwd"));
		payments.setExchrateAtPymt(rs.getDouble("exchrate_at_pymt"));
		payments.setModuleCd(rs.getString("module_cd"));
		payments.setMappingCd(rs.getString("mapping_cd"));
		payments.setGlFlg(rs.getString("gl_flg"));
		payments.setGlTxnType(rs.getString("gl_txn_type"));
		payments.setGlTxnNo(rs.getInt("gl_txn_no"));
		payments.setFiscalYr(rs.getString("fiscal_yr"));
		payments.setOldCd(rs.getString("old_cd"));
		payments.setCreatedBy(rs.getString("created_by"));
		payments.setLastUpdatedBy(rs.getString("last_updated_by"));
		payments.setCreatedDt(rs.getDate("created_dt").toLocalDate());
		payments.setLastUpdatedDt(rs.getDate("last_updated_dt").toLocalDate());
		payments.setMetaStatus(rs.getString("meta_status"));
		payments.setMetaRemarks(rs.getString("meta_remarks"));
			
		}
	return payments;
	}
	
	

}


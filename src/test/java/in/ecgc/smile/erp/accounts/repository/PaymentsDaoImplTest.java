/**
 * 
 */
package in.ecgc.smile.erp.accounts.repository;

import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.Payments;
import in.ecgc.smile.erp.accounts.model.PaymentsTcodes;
import in.ecgc.smile.erp.accounts.util.PaymentsQueries;

/**
 * @author CMJ-SENG-LAPTOP9
 *
 */
public class PaymentsDaoImplTest {


	private MockMvc mockMvc;

	@Mock
	Payments payments;

	@Mock
	GlTxnHdr glTxnHdr;

	@Mock
	GlTxnDtl glTxnDtl;

	@Mock
	PaymentsTcodes paymentsTcodes;

	/*
	 * @Mock PaymentsService service;
	 * 
	 * @InjectMocks PaymentsController paymentsController;
	 */
	
	@Mock
	private DataSource dataSource;
	
	@Mock
	private JdbcOperations jdbcOperations;
	
	@Mock
	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	
	@Mock
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@InjectMocks
	private PaymentsDaoImpl paymentsDaoImpl;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
	}
	
	@BeforeTest
	public void initPayments() {

		payments = new Payments();

		payments.setEntityCd("ECGC");
		payments.setLogicallocCd("MUMBAILOG1");
		payments.setPymtNo(2002000035);
		payments.setPymtDt(LocalDate.of(2022, 01, 03));
		payments.setSectionCd("ACC");
		payments.setAdviceNo(2002000038);
		payments.setPayToType("adm");
		payments.setPymtPartyCd("3");
		payments.setPymtPartyName("CDAC");
		payments.setAmtPaid(1000.00);
		payments.setRemarks("testing");
		payments.setInstrumentType("onl");
		payments.setInstrumentNo("SBIN000001");
		payments.setDrawnOn("SBI");
		payments.setFavouring("ECGC");
		payments.setPymtToEmployee("N");
		payments.setPymtInForex('n');
		payments.setExchrateAtBillIwd(0.00);
		payments.setExchrateAtPymt(0.00);
		payments.setGlFlg("n");
		payments.setGlTxnType("PV");
		payments.setGlTxnNo(2021000072);
		payments.setFiscalYr("2021-2022");
		payments.setOldCd(null);
		payments.setCreatedBy("1229");
		payments.setLastUpdatedBy("121");
		payments.setCreatedDt(LocalDate.of(2022, 01, 05));
		payments.setLastUpdatedDt(LocalDate.of(2022, 01, 11));
		payments.setMetaStatus("V");
		payments.setMetaRemarks("P_Entry");
		payments.setModuleCd("Admin");
		payments.setMappingCd("ADVT_ADVANCE");

	}
	
	@BeforeTest
	public void initGlTxnHdr() {
		glTxnHdr = new GlTxnHdr();
		glTxnHdr.setEntityCd("ECGC");
		glTxnHdr.setGlTxnNo(2021000072);
		glTxnHdr.setGlTxnType("PV");
		glTxnHdr.setTxnDt(LocalDate.of(2022, 01, 05));
		glTxnHdr.setReference("NA");
		glTxnHdr.setRevarsalGlTxnType("NA");
		glTxnHdr.setRevarsalGlTxnNo(0);
		glTxnHdr.setReversalReason("NA");
		glTxnHdr.setFiscalYr("2020-21");
		glTxnHdr.setMetaStatus("V");
		glTxnHdr.setMetaRemarks("pymt");
		glTxnHdr.setCreatedBy("121");
		glTxnHdr.setCreatedDt(null);
		glTxnHdr.setLastUpdatedBy("1229");
		glTxnHdr.setLastUpdatedDt(null);

		List<GlTxnDtl> list = new ArrayList<>();
		GlTxnDtl dtl = new GlTxnDtl();
		dtl.setGlTxnNo(2021000072);
		dtl.setGlTxnType("PV");
		dtl.setLogicalLocCd("MUMBAILOG1");
		dtl.setSrNo(2);
		dtl.setTxnAmt(120001.00);
		dtl.setMainGlCd("3400");
		dtl.setSubGlCd("002");
		dtl.setDrCrFlag("cr");
		dtl.setTxnAmt(5640.0);
		dtl.setOldCode(null);
		dtl.setCodeType(null);
		dtl.setEntityGlCd(null);
		dtl.setPlCd(null);
		dtl.setSubBiFurcationCd(null);
		dtl.setRemarks(null);
		dtl.setT1("Testing");
		dtl.setT2("Testing");
		dtl.setT3("Testing");
		dtl.setT4("Testing");
		dtl.setT5("Testing");
		dtl.setT6("Testing");
		dtl.setT7("Testing");
		dtl.setT8("Testing");
		dtl.setT9("Testing");
		dtl.setT10("Testing");
		dtl.setT11("Testing");
		dtl.setT12("Testing");
		dtl.setAd1(LocalDate.of(2022, 01, 05));
		dtl.setAd2(LocalDate.of(2022, 01, 05));
		dtl.setAd3(LocalDate.of(2022, 01, 05));
		dtl.setAd4(LocalDate.of(2022, 01, 05));
		dtl.setMetaStatus(null);
		dtl.setMetaRemarks(null);
		dtl.setCreatedBy("1229");
		dtl.setCreatedDt(null);
		dtl.setLastUpdatedBy("1229");
		dtl.setLastUpdatedDt(null);
		dtl.setLogicalLocCd("MUMBAILOG1");
		list.add(dtl);
		glTxnHdr.setGlTxnDtls(list);

	}

	@BeforeTest
	public void initPaymentsTcodes() {
		paymentsTcodes = new PaymentsTcodes();
		paymentsTcodes.setEntityCd("ECGC");
		paymentsTcodes.setLogicalLocCd("MUMBAILOG1");
		paymentsTcodes.setSectionCd("ACC");
		paymentsTcodes.setPaymentNo(2002000035);
		paymentsTcodes.setT1("Testing");
		paymentsTcodes.setT2("Testing");
		paymentsTcodes.setT3("Testing");
		paymentsTcodes.setT4("Testing");
		paymentsTcodes.setT5("Testing");
		paymentsTcodes.setT6("Testing");
		paymentsTcodes.setT7("Testing");
		paymentsTcodes.setT8("Testing");
		paymentsTcodes.setT9("Testing");
		paymentsTcodes.setT10("Testing");
		paymentsTcodes.setT11("Testing");
		paymentsTcodes.setT12("Testing");
		paymentsTcodes.setAd1(null);
		paymentsTcodes.setAd2(null);
		paymentsTcodes.setAd3(null);
		paymentsTcodes.setAd4(null);
		paymentsTcodes.setDelFlag(null);

	}
	
	@Test
	private void createPaymentEntryTest() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("entityCd" , payments.getEntityCd());
		paramSource.addValue("logicallocCd" , payments.getLogicallocCd());
		paramSource.addValue("paymentSeqNo" , "2002000035");
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
		paramSource.addValue("metaStatus" , payments.getMetaStatus());
		paramSource.addValue("metaRemarks" , payments.getMetaRemarks());
		when(namedParameterJdbcTemplate.update(PaymentsQueries.ADD_Payments_DATA,paramSource))
		.thenReturn(new Integer(1));
	}
	
	@Test
	public void updatePaymentsDataTest() {
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
	
//		when(namedParameterJdbcTemplate.update(PaymentsQueries.UPDATE_Payments_DATA, paramSource))
//		.thenReturn(new Integer(1));
	}
	
	
	@Test
	public void getPaymentsByPaymentDtlTest() {
		Map<String, Object> paramsMap= new HashMap<>();
		paramsMap.put("pymtNo", "2002000035");
		paramsMap.put("sectionCd", "ACC");
		paramsMap.put("logicalLocCd", "MUMBAILOG1");
		when(namedParameterJdbcTemplate.queryForObject(PaymentsQueries.GET_PAYMENTS_BY_PAYMENT_NO, paramsMap, new RowMapper<Payments>() {
			
			@Override
			public Payments mapRow(ResultSet rs , int rownum) throws SQLException{
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
		})).thenReturn(payments);
	}
	
	@Test
	public void getPaymentsListlTest() {
		List<Payments> paylist = new ArrayList<>();
		when(namedParameterJdbcTemplate.query(PaymentsQueries.GET_Payments_DATA, new RowMapper<Payments>() {
			@Override
			public Payments mapRow(ResultSet rs,int rowNum) throws SQLException{
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
		})).thenReturn(paylist);
	}
	
	@Test
	public void getpaymentsbylocsecdtTest() {
		List<Payments> paylist = new ArrayList<>();
		Map<String, Object> paramsMap= new HashMap<>();
		paramsMap.put("sectionCd", "ACC");
		paramsMap.put("logicalLocCd", "MUMBAILOG1");
		paramsMap.put("fromDt", "2022-01-01");
		paramsMap.put("toDt", "2022-01-10");
		when(namedParameterJdbcTemplate.query(PaymentsQueries.GET_PAYMENTS_BY_LOC_SEC_DT,paramsMap, new RowMapper<Payments>() {
			@Override
			public Payments mapRow(ResultSet rs,int rowNum) throws SQLException{
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
		})).thenReturn(paylist);
	}
	
	@Test
	public void getpaymentsbydtTest() {
		List<Payments> paylist = new ArrayList<>();
		Map<String, Object> paramsMap= new HashMap<>();
		paramsMap.put("fromDt", "2022-01-01");
		paramsMap.put("toDt", "2022-01-10");
		when(namedParameterJdbcTemplate.query(PaymentsQueries.GET_PAYMENTS_BY_DT,paramsMap,new RowMapper<Payments>() {
			@Override
			public Payments mapRow(ResultSet rs,int rowNum) throws SQLException{
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
		})).thenReturn(paylist);
	}

	@Test
	public void getpaymentsbyLocSecYrTest() {
		List<Payments> paylist = new ArrayList<>();
		Map<String, Object> paramsMap= new HashMap<>();
		paramsMap.put("sectionCd", "ACC");
		paramsMap.put("logicalLocCd", "MUMBAILOG1");
		paramsMap.put("fiscalYr", "2021-2022");
		when(namedParameterJdbcTemplate.query(PaymentsQueries.GET_PAYMENTS_BY_LOC_SEC_FIS_YR,paramsMap, new RowMapper<Payments>() {
			@Override
			public Payments mapRow(ResultSet rs,int rowNum) throws SQLException{
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
		})).thenReturn(paylist);
	}
}

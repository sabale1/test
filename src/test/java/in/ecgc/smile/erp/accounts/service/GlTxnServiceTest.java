package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.Calendar;
import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.model.FiscalYearModel;
import in.ecgc.smile.erp.accounts.model.GlBrBalanceIn;
import in.ecgc.smile.erp.accounts.model.GlBrBalanceOut;
import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.repository.GlTxnDaoImpl;

public class GlTxnServiceTest {

	@Mock
	GlTxnHdr glTxnHdr;
	@Mock
	GlTxnDtl glTxnDtl;
	
	@Mock
	GlBrBalanceIn balanceIn;
	@Mock
	GlBrBalanceOut balanceOut;

	@Mock
	FiscalYearModel fiscalYear;
	@Mock
	EntityGL entityGL;

	@Mock
	GlTxnDaoImpl dao;

	@Mock
	Calendar calendar;

	@Mock
	CalendarService calendarService;
	@Mock
	FiscalYearService fiscalYrService;
	@Mock
	EntityGLMasterService entityGlService;

	@InjectMocks
	GlTxnServiceImpl service;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	public void initGlTxnHdr() {
		this.glTxnHdr = new GlTxnHdr();
		glTxnHdr.setGlTxnNo(2021000001);
		glTxnHdr.setGlTxnType("AG");
		glTxnHdr.setFiscalYr("2020-21");
		glTxnHdr.setLogicalLocCd("MUMBAILOG1");
		glTxnHdr.setTxnDt(LocalDate.of(2021, 02, 17));
		List<GlTxnDtl> list = new ArrayList<>();
		GlTxnDtl dtl = new GlTxnDtl();
		dtl.setGlTxnNo(2021000001);
		dtl.setGlTxnType("AG");
		dtl.setLogicalLocCd("MUMBAILOG1");
		dtl.setSrNo(1);
		dtl.setTxnAmt(120001.00);
		dtl.setMainGlCd("1700");
		dtl.setSubGlCd("001");
		dtl.setDrCrFlag("cr");
		list.add(dtl);
		dtl.setGlTxnNo(2021000001);
		dtl.setGlTxnType("AG");
		dtl.setLogicalLocCd("MUMBAILOG1");
		dtl.setSrNo(2);
		dtl.setTxnAmt(120001.00);
		dtl.setMainGlCd("3800");
		dtl.setSubGlCd("001");
		dtl.setDrCrFlag("dr");
		list.add(dtl);
		glTxnHdr.setGlTxnDtls(list);
	}

	@BeforeTest
	void initFiscalYear() {
		fiscalYear = new FiscalYearModel();
		fiscalYear.setCurrFiscalYear("2020-21");
		fiscalYear.setCurrFiscalYearStartDt(LocalDate.of(2020, 01, 03));
		fiscalYear.setPrevFiscalYear("2019-20");
		fiscalYear.setPrevFiscalYearClosedDt(LocalDate.of(2020, 01, 03));
		fiscalYear.setMetaStatus('V');
	}

	@BeforeTest
	public void initEntityGl() {

		entityGL = new EntityGL();

		entityGL.setEntityGlCd("ECGC");
		entityGL.setActive('Y');
		entityGL.setBalInd("na");
		entityGL.setCashaccount(982348123);
		entityGL.setGlIsGroup('N');
		entityGL.setGlName("BANK");
		entityGL.setMainglCd("1700");
		entityGL.setOldCd("1001");
		entityGL.setSubglCd("003");
	}

	@BeforeTest
	public void initCalendar() {
		calendar = new Calendar();
//		calendar.setBranchCode("028BO080");
		calendar.setCalendarId("2010");
		calendar.setClosedStatus('n');
		calendar.setConfigFlag(true);
		calendar.setEcgcStatus('y');
		calendar.setFiscalYear("2020-21");
		calendar.setGlTxnType("ECGC");
		calendar.setLogicalLocCode("MUMBAILOG1");
		calendar.setMonth("feb");

	}

	@Test
	public void getAllGlTxnHdrsTest() throws Exception {
		List<GlTxnHdr> list = new ArrayList<>();
		list.add(glTxnHdr);
		when(dao.getAllGlTxn(Mockito.any())).thenReturn(list);
		Assert.assertEquals(service.getAllGlTxnHdrs("MUMBAILOG1"), list);
		when(dao.getAllGlTxn(Mockito.any())).thenReturn(null);
		Assert.assertEquals(service.getAllGlTxnHdrs("MUMBAILOG1"), null);
	}

	@Test
	public void getAllGlTxnHdrsByLogicatiobAndGlTest() throws Exception {
		List<GlTxnHdr> list = new ArrayList<>();
		list.add(glTxnHdr);
		when(dao.getAllGlTxn(Mockito.any(), Mockito.any())).thenReturn(list);
		Assert.assertEquals(service.getAllGlTxnHdrs("MUMBAILOG1", "AG"), list);
		when(dao.getAllGlTxn(Mockito.any(), Mockito.any())).thenReturn(null);
		Assert.assertEquals(service.getAllGlTxnHdrs("MUMBAILOG1", "AG"), null);
	}

	@Test
	public void getGlTxnTest() throws Exception {
		when(dao.getGlTxn(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(glTxnHdr);
		Assert.assertEquals(service.getGlTxn(2021000001, "MUMBAILOG1", "AG"), glTxnHdr);
		when(dao.getGlTxn(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);
		Assert.assertEquals(service.getGlTxn(2021000001, "MUMBAILOG1", "AG"), null);
	}

	//@Test(enabled = true)
	public void addGlTxnTest() throws Exception {

		when(fiscalYrService.findCurrentFiscalYear()).thenReturn(fiscalYear);
		when(dao.getGlTxnNo("MUMBAILOG1", "AG", "2020-21")).thenReturn(new Integer(2021000001));
		when(calendarService.getByGlTypeLogicalLoc(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(calendar);
		when(entityGlService.findGLByGLCode(Mockito.any(), Mockito.any())).thenReturn(entityGL);
		when(dao.addGlTxn(glTxnHdr)).thenReturn(3);
		when(dao.updateGlTxnNo(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(1);
		when(dao.updateDrCrBrbal(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(1);
		when(dao.updateDrCrBrbalYr(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(1);
		Assert.assertEquals(service.addGlTxn(glTxnHdr), new Integer(2021000001));
	}

	//@Test(enabled = false)
	public void isValidTest() throws Exception {

		when(entityGlService.findGLByGLCode(Mockito.any(), Mockito.any())).thenReturn(entityGL);
		Assert.assertEquals(service.isValid(glTxnHdr), new Boolean(true));

		List<GlTxnDtl> list = new ArrayList<>();
		GlTxnDtl dtl = new GlTxnDtl();
		dtl.setGlTxnNo(2021000001);
		dtl.setGlTxnType("AG");
		dtl.setLogicalLocCd("MUMBAILOG1");
		dtl.setSrNo(1);
		dtl.setTxnAmt(120003.00);
		dtl.setMainGlCd("1700");
		dtl.setSubGlCd("001");
		dtl.setDrCrFlag("cr");
		list.add(dtl);
		dtl.setGlTxnNo(2021000001);
		dtl.setGlTxnType("AG");
		dtl.setLogicalLocCd("MUMBAILOG1");
		dtl.setSrNo(2);
		dtl.setTxnAmt(120001.00);
		dtl.setMainGlCd("3800");
		dtl.setSubGlCd("001");
		dtl.setDrCrFlag("dr");
		list.add(dtl);

		glTxnHdr.setGlTxnDtls(list);
		when(entityGlService.findGLByGLCode(Mockito.any(), Mockito.any())).thenReturn(entityGL);
		Assert.assertEquals(service.isValid(glTxnHdr), new Boolean(true));
	}

	@Test
	public void getAllGltxnByFromDtLocTest() throws Exception {
		List<GlTxnHdr> list = new ArrayList<>();
		list.add(glTxnHdr);
		when(dao.getAllGltxnByFromDtLoc(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(list);
		Assert.assertEquals(service.getAllGltxnByFromDtLoc(LocalDate.parse("2021-07-01"), LocalDate.parse("2021-07-02"),
				"MUMBAILOG1"), list);

		when(dao.getAllGltxnByFromDtLoc(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);
		Assert.assertEquals(service.getAllGltxnByFromDtLoc(LocalDate.parse("2021-07-01"), LocalDate.parse("2021-07-02"),
				"MUMBAILOG1"), null);
	}

	@Test
	public void getAllGlTxnByTxnNoTxnTypeLoc() throws Exception {
		List<GlTxnHdr> list = new ArrayList<>();
		list.add(glTxnHdr);
		when(dao.getAllGlTxnByTxnNoTxnTypeLoc(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(list);
		Assert.assertEquals(service.getAllGlTxnByTxnNoTxnTypeLoc(2021000001, "AG", "MUMBAILOG1"), list);
		when(dao.getAllGlTxnByTxnNoTxnTypeLoc(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);
		Assert.assertEquals(service.getAllGlTxnByTxnNoTxnTypeLoc(2021000001, "AG", "MUMBAILOG1"), null);
	}

	@Test
	public void updateGlTxnDtlTest() throws Exception {

		when(dao.updateGlTxnDtl(glTxnDtl)).thenReturn(new Integer(1));
		assertEquals(service.updateGlTxnDtl(glTxnDtl), Integer.valueOf(1));
	}

	@Test
	public void getTotalCreditAmtTest() throws Exception {
		BigDecimal num = new BigDecimal("123");
		when(dao.getTotalCreditAmt(Mockito.any(), Mockito.any())).thenReturn(num);
		Assert.assertEquals(service.getTotalCreditAmt("MUMBAILOG1", "AG"), num);
	}

	@Test
	public void getTotalAmtTest() throws Exception {
		BigDecimal num = new BigDecimal("123");
		when(dao.getTotalAmt(Mockito.any(), Mockito.any())).thenReturn(num);
		Assert.assertEquals(service.getTotalAmt("MUMBAILOG1", "AG"), num);
	}

	@Test
	public void getAllGlTxnByFromDtToDtTest() throws Exception {
		List<GlTxnHdr> list = new ArrayList<>();
		list.add(glTxnHdr);
		when(dao.getAllGlTxnByFromDtToDt(LocalDate.parse("2021-07-01"), LocalDate.parse("2021-07-02")))
				.thenReturn(list);
		Assert.assertEquals(
				service.getAllGlTxnByFromDtToDt(LocalDate.parse("2021-07-01"), LocalDate.parse("2021-07-02")), list);
	}

	@Test(enabled = false)
	public void reverseTransactionTest() throws Exception {

		when(dao.updateHdrOnRevarsal(glTxnHdr)).thenReturn(new Integer(1));
		assertEquals(service.reverseTransaction(glTxnHdr), Integer.valueOf(1));

		/*
		 * when(dao.updateHdrOnRevarsal(glTxnHdr)).thenReturn(new Integer(0));
		 * assertEquals(service.reverseTransaction(glTxnHdr), new
		 * InvalidGLTxnDataException("Transaction not updated"));
		 */
	}
	
	@Test
	public void getBrBalanceTest() throws Exception {
		GlBrBalanceIn balanceIn = new GlBrBalanceIn();
		GlBrBalanceOut balanceOut = new GlBrBalanceOut();
		when(dao.getBrBalance(balanceIn)).thenReturn(balanceOut);
		Assert.assertEquals(service.getBrBalance(balanceIn), balanceOut);
	}

}

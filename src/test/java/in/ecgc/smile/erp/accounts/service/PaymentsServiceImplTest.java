/**
 *
 */
package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.LiabilityGLMapping;
import in.ecgc.smile.erp.accounts.model.Payments;
import in.ecgc.smile.erp.accounts.model.PaymentsTcodes;
import in.ecgc.smile.erp.accounts.repository.LiabilityDao;
import in.ecgc.smile.erp.accounts.repository.PaymentsDao;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;

/**
 * @author CMJ-SENG-LAPTOP9
 *
 */
public class PaymentsServiceImplTest extends AbstractTestNGSpringContextTests {

	@Mock
	Payments payments;

	@Mock
	GlTxnHdr glTxnHdr;

	@Mock
	GlTxnDtl glTxnDtl;

	@Mock
	LiabilityDao liabilityDao;

	@Mock
	private GlTxnService glTxnService;

	@Mock
	private UserInfoService userInfoService;

	@Mock
	LiabilityGLMapping liabilityGLMapping;


	@Mock
	PaymentsTcodes paymentsTcodes;

	@Mock
	private PaymentsDao paymentsDao;

	@InjectMocks
	private PaymentsServiceImpl paymentsService;

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
		glTxnHdr.setTxnDt(LocalDate.now());
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
		paymentsTcodes.setAd1(LocalDate.of(2022, 01, 05));
		paymentsTcodes.setAd2(LocalDate.of(2022, 01, 05));
		paymentsTcodes.setAd3(LocalDate.of(2022, 01, 05));
		paymentsTcodes.setAd4(LocalDate.of(2022, 01, 05));
		paymentsTcodes.setDelFlag(null);

	}

	@BeforeTest
	public void initLiabilityGLMapping() {
		this.liabilityGLMapping = new LiabilityGLMapping();
		liabilityGLMapping.setModuleCd("Admin");
		liabilityGLMapping.setMappingCd("ADVT_ADVANCE");
		liabilityGLMapping.setMappingName("Advance for Marketing Advertisement Expense");
		liabilityGLMapping.setSrNo(1);
		liabilityGLMapping.setMainGL("1700");
		liabilityGLMapping.setSubGL("001");
		liabilityGLMapping.setDrCrFlag("dr");
		liabilityGLMapping.setAmtCalc((float) 333.3);
		liabilityGLMapping.setRemarks("rfemarks");
		liabilityGLMapping.setSubBifurcation("AG");
		liabilityGLMapping.setCreatedBy("1229");
		liabilityGLMapping.setCreatedDt(LocalDate.of(2021, 01, 01));
		liabilityGLMapping.setLastUpdatedBy("1229");
		liabilityGLMapping.setLastUpdatedDt(LocalDate.of(2021, 01, 01));
		liabilityGLMapping.setMetaRemarks("mremarks");
		liabilityGLMapping.setMetaStatus("mstat");
		liabilityGLMapping.setEntityCd("ECGC");
		liabilityGLMapping.setTdsApplicable('N');
		liabilityGLMapping.setRcApplicable('N');
		liabilityGLMapping.setGstTdsApplicable('Y');
	}

	// @Test
	public void createPaymentEntryTest() throws Exception {
		when(paymentsDao.createPaymentEntry(payments)).thenReturn(new Integer(1));
		Assert.assertEquals(paymentsService.createPaymentEntry(payments), new Integer(1));

		/*
		 * when(paymentsDao.createPaymentEntry(payments)).thenReturn(null);
		 * Assert.assertEquals(paymentsService.createPaymentEntry(payments), null);
		 */

	}

	@Test
	public void updatePaymentsDataTest() throws Exception {
		when(paymentsDao.updatePaymentsData(payments)).thenReturn(new Boolean(true));
		Assert.assertEquals(paymentsService.updatePaymentsData(payments), true);

		when(paymentsDao.updatePaymentsData(payments)).thenReturn(new Boolean(false));
		Assert.assertEquals(paymentsService.updatePaymentsData(payments), false);

	}

	//@Test
	public void getPaymentsByPaymentDtlTest() {
		when(paymentsDao.getPaymentsByPaymentDtl(2002000035, "MUMBAILOG1", "ACC")).thenReturn(payments);
		Assert.assertEquals(paymentsService.getPaymentsByPaymentDtl(2002000035, "MUMBAILOG1", "ACC"), payments);

	}

	@Test
	public void getPaymentsListlTest() throws Exception {
		List<Payments> paylist = new ArrayList<>();
		paylist.add(payments);
		when(paymentsDao.getPaymentsList()).thenReturn(paylist);
		Assert.assertEquals(paymentsService.getPaymentsList().size(), 1);

	}

	@Test
	public void getpaymentsbylocsecdtTest() throws Exception {
		List<Payments> paylist = new ArrayList<>();
		paylist.add(payments);
		when(paymentsDao.getpaymentsbylocsecdt("MUMBAILOG1", "ACC", "2022-01-01", "2022-01-10")).thenReturn(paylist);
		Assert.assertEquals(paymentsService.getpaymentsbylocsecdt("MUMBAILOG1", "ACC", "2022-01-01", "2022-01-10"),
				paylist);

	}

	@Test
	public void getpaymentsbydtTest() throws Exception {
		List<Payments> paylist = new ArrayList<>();
		paylist.add(payments);
		when(paymentsDao.getpaymentsbydt("2022-01-01", "2022-01-10")).thenReturn(paylist);
		Assert.assertEquals(paymentsService.getpaymentsbydt("2022-01-01", "2022-01-10"), paylist);

	}

	@Test
	public void getpaymentsbyLocSecYrTest() throws Exception {
		List<Payments> paylist = new ArrayList<>();
		paylist.add(payments);
		when(paymentsDao.getpaymentsbyLocSecYr("MUMBAILOG1", "ACC", "2021-2022")).thenReturn(paylist);
		Assert.assertEquals(paymentsService.getpaymentsbyLocSecYr("MUMBAILOG1", "ACC", "2021-2022"), paylist);

	}

	@Test
	public void updatePaymentTcodesTest() throws Exception {
		when(paymentsDao.updatePaymentTcodes(payments)).thenReturn(new Boolean(true));
		Assert.assertEquals(paymentsService.updatePaymentTcodes(payments), true);
		when(paymentsDao.updatePaymentTcodes(payments)).thenReturn(new Boolean(false));
		Assert.assertEquals(paymentsService.updatePaymentTcodes(payments), false);

	}

	@Test
	public void createGLTxnTest() throws Exception {
		List<LiabilityGLMapping> mappingList = new ArrayList<>();
		mappingList.add(liabilityGLMapping);
		payments.setPaymentsTcodes(paymentsTcodes);
		when(liabilityDao.getMAppingListforModule("Admin","ADVT_ADVANCE")).thenReturn(mappingList);
		when(userInfoService.getUser()).thenReturn("1229");
		when(glTxnService.addGlTxn(glTxnHdr)).thenReturn(new Integer(0));
		Assert.assertEquals(paymentsService.createGLTxn(payments), new Integer(0));

	}

}

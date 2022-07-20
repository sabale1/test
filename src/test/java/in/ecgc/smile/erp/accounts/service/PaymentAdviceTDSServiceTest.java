package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.PaymentAdvice;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceGstTdsDtl;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceTcodes;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceTdsDtl;
import in.ecgc.smile.erp.accounts.repository.PaymentAdviceTDSDao;

public class PaymentAdviceTDSServiceTest {

	@Mock
	private PaymentAdvice payAdv;

	@Mock
	private PaymentAdviceGstTdsDtl adviceGstTdsDtl;

	@Mock
	private PaymentAdviceTdsDtl adviceTdsDtl;

	@Mock
	private PaymentAdviceTcodes payTcodes;
	@Mock
	private PaymentAdviceTDSDao payAdviceTdsDao;

	@InjectMocks
	private PaymentAdviceTDSServiceImpl payAdviceService;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	public void initPayAdviceTDS() {
		payAdv = new PaymentAdvice();
		payAdv.setEntityCd("ECGC");
		payAdv.setExpenseHead("ACCT");
		payAdv.setLogicalLocCd("MUMBAILOG1");
		payAdv.setSectionCd("HRD");
		payAdv.setCurrCd("INR");
		payAdv.setAdviceStatus("ENTR");

	}

	@BeforeTest
	public void initPaymentAdviceTcodes() {
		payTcodes = new PaymentAdviceTcodes();
		payTcodes.setEntityCd("ECGC");
		payTcodes.setLogicalLocCd("MUMBAILOG1");
		payTcodes.setSectionCd("HRD");
		payTcodes.setAdviceNo(2021000001);
	}

	@BeforeTest
	public void initPaymentAdviceTdsDtl() {
		adviceTdsDtl = new PaymentAdviceTdsDtl();
		adviceTdsDtl.setEntityCd("ECGC");
		adviceTdsDtl.setExpenseHead("ACCT");
		adviceTdsDtl.setSectionCd("HRD");
		adviceTdsDtl.setLogicalLocCd("MUMBAILOG1");
	}

	@BeforeTest
	public void initPaymentAdviceGstTdsDtl() throws Exception {
		adviceGstTdsDtl = new PaymentAdviceGstTdsDtl();
		adviceGstTdsDtl.setEntityCd("ECGC");
		adviceGstTdsDtl.setLogicalLocCd("MUMBAILOG1");
		adviceGstTdsDtl.setSectionCd("HRD");
	}

	@Test
	public void getApprovedPaymentAdviceDtl() throws Exception {
		when(payAdviceTdsDao.getApprovedPaymentAdviceDtl("MUMBAILOG1", "HRD", 2002000035)).thenReturn(payAdv);
		Assert.assertEquals(payAdviceService.getApprovedPaymentAdviceDtl("MUMBAILOG1", "HRD", 2002000035), payAdv);
	}

	@Test
	public void getApprovedPaymentAdvices() throws Exception {
		LocalDate fromDt = LocalDate.parse("2022-01-01");
		LocalDate toDt = LocalDate.parse("2022-01-27");
		List<PaymentAdvice> payAdvlist = new ArrayList<>();
		payAdvlist.add(payAdv);

		when(payAdviceTdsDao.getApprovedPaymentAdvices("MUMBAILOG1", "HRD", fromDt, toDt)).thenReturn(payAdvlist);
		Assert.assertEquals(payAdviceService.getApprovedPaymentAdvices("MUMBAILOG1", "HRD", fromDt, toDt), payAdvlist);
	}

	@Test
	public void getApprovedPaymentAdvicesbyNo() throws Exception {
		List<PaymentAdvice> payAdvlist = new ArrayList<>();
		payAdvlist.add(payAdv);
		when(payAdviceTdsDao.getApprovedPaymentAdvicesbyNo("MUMBAILOG1", "HRD", 2002000035)).thenReturn(payAdvlist);
		Assert.assertEquals(payAdviceService.getApprovedPaymentAdvicesbyNo("MUMBAILOG1", "HRD", 2002000035),
				payAdvlist);
	}

	@Test
	public void updatePaymentAdviceTdsNOTAppliacble() throws Exception {
		when(payAdviceTdsDao.updatePaymentAdviceTdsNOTAppliacble(payAdv)).thenReturn(new Integer(1));
		Assert.assertEquals(payAdviceService.updatePaymentAdviceTdsNOTAppliacble(payAdv), new Integer(1));

	}

	@Test
	public void updatePaymentAdviceRCAppliacble() throws Exception {
		when(payAdviceTdsDao.updatePaymentAdviceRCAppliacble(payAdv)).thenReturn(new Integer(1));
		Assert.assertEquals(payAdviceService.updatePaymentAdviceRCAppliacble(payAdv), new Integer(1));

	}

	@Test
	public void addPaymentAdviceTDSDtl() throws Exception {
		when(payAdviceTdsDao.addPaymentAdviceTDSDtl(adviceTdsDtl)).thenReturn(new Integer(1));
		Assert.assertEquals(payAdviceService.addPaymentAdviceTDSDtl(adviceTdsDtl), new Integer(1));

	}

	@Test
	public void addPaymentAdviceGSTTDSDtl() throws Exception {
		when(payAdviceTdsDao.addPaymentAdviceGSTTDSDtl(adviceGstTdsDtl)).thenReturn(new Integer(1));
		Assert.assertEquals(payAdviceService.addPaymentAdviceGSTTDSDtl(adviceGstTdsDtl), new Integer(1));

	}

	@Test
	public void getPaymentAdviceTDSDtl() throws Exception {
		when(payAdviceTdsDao.getPaymentAdviceTDSDtl("MUMBAILOG1", "HRD", 2002000035)).thenReturn(adviceTdsDtl);
		Assert.assertEquals(payAdviceService.getPaymentAdviceTDSDtl("MUMBAILOG1", "HRD", 2002000035), adviceTdsDtl);

	}

	@Test
	public void getPaymentAdviceGSTTDSDtl() throws Exception {
		when(payAdviceTdsDao.getPaymentAdviceGSTTDSDtl("MUMBAILOG1", "HRD", 2002000035)).thenReturn(adviceGstTdsDtl);
		Assert.assertEquals(payAdviceService.getPaymentAdviceGSTTDSDtl("MUMBAILOG1", "HRD", 2002000035),
				adviceGstTdsDtl);

	}

}

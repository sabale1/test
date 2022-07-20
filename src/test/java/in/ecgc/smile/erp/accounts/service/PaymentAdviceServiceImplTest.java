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
import in.ecgc.smile.erp.accounts.repository.PaymentAdviceDao;

public class PaymentAdviceServiceImplTest {

	@Mock
	private PaymentAdvice payAdvice;

	@Mock
	private PaymentAdviceTcodes payAdviceTcodes;

	@Mock
	private PaymentAdviceTdsDtl payAdvTdsDtl;

	@Mock
	private PaymentAdviceGstTdsDtl paymentAdviceGstTdsDtl;

	@Mock
	private PaymentAdviceDao dao;

	@InjectMocks
	PaymentAdviceServiceImpl service;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	public void initPaymentAdvice() {
		payAdvice = new PaymentAdvice();
		payAdvice.setAdviceAmt(200.00);
		payAdvice.setAdviceAmtOtherCurrINR(20.0);
		payAdvice.setAdviceDesc("test");
		payAdvice.setAdviceDate(null);
		payAdvice.setAdviceNo(2021000006);
		payAdvice.setAdviceStatus("ENTR");
		payAdvice.setApprBaseAmt(200.0);
		payAdvice.setAprvAmt(1000.00);
		payAdvice.setBranchCd("MUMLOG1");
		payAdvice.setCessAmt(100.00);
		payAdvice.setCessRate(5.0);
		payAdvice.setCreatedBy(1229);
		payAdvice.setDecDt(null);
		payAdvice.setUserName("Tester");
		payAdvice.setModuleCode("ACC");
		payAdvice.setMappingCode("ACC");
		payAdvice.setEntityCd("ECGC");
		payAdvice.setExpenseHead("TP");
		payAdvice.setLogicalLocCd("MUMBAILOG1");
		payAdvice.setCurrCd("CD");
		payAdvice.setSectionCd("CUD");

		payAdviceTcodes.setEntityCd("ECGC");
		payAdviceTcodes.setLogicalLocCd("MUMBAILOG1");
		payAdviceTcodes.setSectionCd("CUD");
		payAdviceTcodes.setAdviceNo(2021000006);

		payAdvice.setPayTcodes(payAdviceTcodes);

		payAdvTdsDtl.setEntityCd("ECGC");
		payAdvTdsDtl.setExpenseHead("CDAC");
		payAdvTdsDtl.setLogicalLocCd("MUMBAILOG1");
		payAdvTdsDtl.setAdviceNo(2021000006);
		payAdvTdsDtl.setSectionCd("CUD");

		payAdvice.setAdviceTdsDtl(payAdvTdsDtl);

		paymentAdviceGstTdsDtl.setEntityCd("ECGC");
		paymentAdviceGstTdsDtl.setLogicalLocCd("MUMBAILOG1");
		paymentAdviceGstTdsDtl.setSectionCd("CUD");

		payAdvice.setAdviceGstTdsDtl(paymentAdviceGstTdsDtl);

	}

	@BeforeTest
	public void initPaymentAdviceTcodes() {
		payAdviceTcodes = new PaymentAdviceTcodes();
		payAdviceTcodes.setEntityCd("ECGC");
		payAdviceTcodes.setLogicalLocCd("MUMBAILOG1");
		payAdviceTcodes.setSectionCd("HRD");
		payAdviceTcodes.setAdviceNo(2021000001);
	}

	@BeforeTest
	public void initPaymentAdviceTdsDtl() {
		payAdvTdsDtl = new PaymentAdviceTdsDtl();
		payAdvTdsDtl.setEntityCd("ECGC");
		payAdvTdsDtl.setExpenseHead("ACCT");
		payAdvTdsDtl.setSectionCd("HRD");
		payAdvTdsDtl.setLogicalLocCd("MUMBAILOG1");
	}

	@BeforeTest
	public void initPaymentAdviceGstTdsDtl() throws Exception {
		paymentAdviceGstTdsDtl = new PaymentAdviceGstTdsDtl();
		paymentAdviceGstTdsDtl.setEntityCd("ECGC");
		paymentAdviceGstTdsDtl.setLogicalLocCd("MUMBAILOG1");
		paymentAdviceGstTdsDtl.setSectionCd("HRD");
	}

	//@Test
	public void addPaymentAdviceTest() throws Exception {
		when(dao.getAdviceNo(payAdvice.getLogicalLocCd(), payAdvice.getSectionCd(), payAdvice.getFiscalYear()))
				.thenReturn(new Integer(2021000006));
		when(dao.addPaymentAdvice(payAdvice)).thenReturn(1);
		when(dao.addPaymentAdviceTcodes(payAdvice)).thenReturn(1);
		Assert.assertEquals(service.addPaymentAdvice(payAdvice), Integer.valueOf(1));

		when(dao.getAdviceNo(payAdvice.getLogicalLocCd(), payAdvice.getSectionCd(), payAdvice.getFiscalYear()))
				.thenReturn(new Integer(2021000006));
		when(dao.addPaymentAdvice(payAdvice)).thenReturn(0);
		Assert.assertEquals(service.addPaymentAdvice(payAdvice), Integer.valueOf(0));
	}

	@Test
	public void disablePaymentAdviceTest() throws Exception {
		when(dao.disablePaymentAdvice("MUMBAILOG1","CUD",2021000006)).thenReturn(new Integer(1));
		when(dao.disablePaymentAdviceTcodes("MUMBAILOG1","CUD",2021000006)).thenReturn(1);
		Assert.assertEquals(service.disablePaymentAdvice("MUMBAILOG1","CUD",2021000006), Integer.valueOf(1));

		when(dao.disablePaymentAdvice("MUMBAILOG1","CUD",2021000006)).thenReturn(new Integer(0));
		Assert.assertEquals(service.disablePaymentAdvice("MUMBAILOG1","CUD",2021000006), Integer.valueOf(0));
	}

	@Test
	public void disablePaymentAdviceTcodesTest() throws Exception {
		when(dao.disablePaymentAdviceTcodes("MUMBAILOG1","CUD",2021000006)).thenReturn(new Integer(1));
		Assert.assertEquals(service.disablePaymentAdviceTcodes("MUMBAILOG1","CUD",2021000006), Integer.valueOf(1));
	}

	@Test
	public void getAdviceNoTest() throws Exception {
		when(dao.getAdviceNo("MUMBAILOG1","CUD","2020-21")).thenReturn(new Integer(1));
		Assert.assertEquals(service.getAdviceNo("MUMBAILOG1","CUD","2020-21"), Integer.valueOf(1));
	}

	@Test
	public void getAdviceNumberByLocSecTest() throws Exception {
		List<PaymentAdvice> list = new ArrayList<>();
		list.add(payAdvice);
		when(dao.getAdviceNumberByLocSec("MUMBAILOG1","CUD")).thenReturn(list);
		Assert.assertEquals(service.getAdviceNumberByLocSec("MUMBAILOG1","CUD"), list);
	}

	@Test
	public void getAdviceNumberByLocSecDtTest() throws Exception {
		List<PaymentAdvice> list = new ArrayList<>();
		list.add(payAdvice);
		when(dao.getAdviceNumberByLocSecDt("MUMBAILOG1","CUD",LocalDate.now(),LocalDate.now())).thenReturn(list);
		Assert.assertEquals(service.getAdviceNumberByLocSecDt("MUMBAILOG1","CUD",LocalDate.now(),LocalDate.now()), list);
	}

	@Test
	public void getApprPaymentAdviceByDtlTest() throws Exception {
		List<PaymentAdvice> list = new ArrayList<>();
		list.add(payAdvice);
		when(dao.getApprPaymentAdviceByDtl("MUMBAILOG1","CUD","2020-21")).thenReturn(list);
		Assert.assertEquals(service.getApprPaymentAdviceByDtl("MUMBAILOG1","CUD","2020-21"), list);
	}

	@Test
	public void getEntrPaymentAdviceTest() throws Exception {
		List<PaymentAdvice> list = new ArrayList<>();
		list.add(payAdvice);
		when(dao.getEntrPaymentAdvice("MUMBAILOG1","CUD","ENTR")).thenReturn(list);
		Assert.assertEquals(service.getEntrPaymentAdvice("MUMBAILOG1","CUD","ENTR"), list);

	}

	@Test
	public void getPaymentAdviceAndTaxDtlTest() throws Exception {

		when(dao.getPaymentAdviceAndTaxDtl("MUMBAILOG1","CUD",2021000006)).thenReturn(payAdvice);
		Assert.assertEquals(service.getPaymentAdviceAndTaxDtl("MUMBAILOG1","CUD",2021000006), payAdvice);
	}

	@Test
	public void getPaymentAdviceByAdviceDtlTest() throws Exception {
		when(dao.getPaymentAdviceByAdviceDtl("MUMBAILOG1","CUD",2021000006)).thenReturn(payAdvice);
		Assert.assertEquals(service.getPaymentAdviceByAdviceDtl("MUMBAILOG1","CUD",2021000006), payAdvice);
	}

	@Test
	public void getPaymentAdviceByLogicalLocSectionCdTest() throws Exception {
		List<PaymentAdvice> list = new ArrayList<>();
		list.add(payAdvice);
		when(dao.getPaymentAdviceByLogicalLocSectionCd("MUMBAILOG1","CUD",LocalDate.now(),LocalDate.now())).thenReturn(list);
		Assert.assertEquals(service.getPaymentAdviceByLogicalLocSectionCd("MUMBAILOG1","CUD",LocalDate.now(),LocalDate.now()), list);
	}

	@Test
	public void getPaymentAdviceTcodesByAdviceDtlTest() throws Exception {
		when(dao.getPaymentAdviceTcodesByAdviceDtl("MUMBAILOG1","CUD",2021000006)).thenReturn(payAdvice);
		Assert.assertEquals(service.getPaymentAdviceTcodesByAdviceDtl("MUMBAILOG1","CUD",2021000006), payAdvice);
	}

	@Test
	public void getPaymentAdviceTcodesDtlTest() throws Exception {
		when(dao.getPaymentAdviceTcodesDtl("MUMBAILOG1","CUD",2021000006)).thenReturn(payAdviceTcodes);
		Assert.assertEquals(service.getPaymentAdviceTcodesDtl("MUMBAILOG1","CUD",2021000006), payAdviceTcodes);
	}

	@Test
	public void listAllPaymentAdviceTest() throws Exception {
		List<PaymentAdvice> list = new ArrayList<>();
		list.add(payAdvice);
		when(dao.listAllPaymentAdvice()).thenReturn(list);
		Assert.assertEquals(service.listAllPaymentAdvice(), list);
	}

	@Test
	public void listAllPaymentAdviceTcodesTest() throws Exception {
		List<PaymentAdvice> list = new ArrayList<>();
		list.add(payAdvice);
		when(dao.listAllPaymentAdviceTcodes()).thenReturn(list);
		Assert.assertEquals(service.listAllPaymentAdviceTcodes(), list);
	}

	@Test
	public void takeDecisionOnPaymentAdviceTest() throws Exception {
		when(dao.takeDecisionOnPaymentAdvice(payAdvice)).thenReturn(1);
		Assert.assertEquals(service.takeDecisionOnPaymentAdvice(payAdvice), Integer.valueOf(1));
	}

	@Test
	public void updatePaymentAdviceTest() throws Exception {
		when(dao.updatePaymentAdvice(payAdvice)).thenReturn(1);
		Assert.assertEquals(service.updatePaymentAdvice(payAdvice), Integer.valueOf(1));
	}

	@Test
	public void updatePaymentAdviceTcodesTest() throws Exception {
		when(dao.updatePaymentAdviceTcodes(payAdvice)).thenReturn(1);
		Assert.assertEquals(service.updatePaymentAdviceTcodes(payAdvice), Integer.valueOf(1));

	}

	@Test
	public void updateSeqNoTest() throws Exception {
		when(dao.updateSeqNo("MUMBAILOG1","CUD","2020-21",2021000006)).thenReturn(1);
		Assert.assertEquals(service.updateSeqNo("MUMBAILOG1","CUD","2020-21",2021000006), Integer.valueOf(1));

	}
}

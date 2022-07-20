package in.ecgc.smile.erp.accounts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ecgc.smile.erp.accounts.model.PaymentAdvice;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceAll;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceGstTdsDtl;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceTdsDtl;
import in.ecgc.smile.erp.accounts.service.PaymentAdviceTDSService;

public class PaymentAdviceTDSControllerTest {

	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Mock
	private PaymentAdviceTDSService payAdviceTdsService;

	@Mock
	private PaymentAdvice payAdvice;

	@Mock
	private PaymentAdviceTdsDtl payAdvTdsDtl;

	@Mock
	private PaymentAdviceGstTdsDtl paymentAdviceGstTdsDtl;

	@InjectMocks
	PaymentAdviceTDSController paymentAdviceTDSController;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(paymentAdviceTDSController).build();
		mapper = new ObjectMapper();
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

		
	}

	@Test(enabled = false)
	public void createPaymentAdviceTDSTest() throws Exception {
		PaymentAdviceAll payAdvAll = new PaymentAdviceAll();
		payAdvAll.setPaymentAdvice(payAdvice);
		payAdvAll.setPayAdvTdsDtl(payAdvTdsDtl);
		payAdvTdsDtl.setRevChargeApp('N');
		payAdvAll.setPayAdvGstTdsDtl(paymentAdviceGstTdsDtl);
		paymentAdviceGstTdsDtl.setGstTdsApplicable('N');

		String payAdvAllstr = mapper.writeValueAsString(payAdvAll);

		when(payAdviceTdsService.updatePaymentAdviceRCAppliacble(payAdvAll.getPaymentAdvice()))
				.thenReturn(new Integer(-1));
		when(payAdviceTdsService.addPaymentAdviceTDSDtl(payAdvAll.getPayAdvTdsDtl())).thenReturn(new Integer(-1));
		when(payAdviceTdsService.addPaymentAdviceGSTTDSDtl(payAdvAll.getPayAdvGstTdsDtl())).thenReturn(new Integer(-1));
		mockMvc.perform(MockMvcRequestBuilders.post("/payment-advice-tds/insert-tds-dtl").content(payAdvAllstr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isNoContent())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void getApprovedPaymentAdviceDtlTest() throws Exception {
		when(payAdviceTdsService.getApprovedPaymentAdviceDtl("MUMBAILOG1", "CUD", 2021000006)).thenReturn(payAdvice);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice-tds/get/{logicalLocCd}/{sectionCd}/{adviceNo}", "MUMBAILOG1", "CUD", 2021000006)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceTdsService.getApprovedPaymentAdviceDtl("MUMBAILOG2", "CUD", 2021000006)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice-tds/get/{logicalLocCd}/{sectionCd}/{adviceNo}", "MUMBAILOG2", "CUD", 2021000006)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isNoContent());
	}

	@Test
	public void getApprovedPaymentAdvicesTest() throws Exception {
		LocalDate fromDt = LocalDate.parse("2022-01-01");
		LocalDate toDt = LocalDate.parse("2022-01-27");

		List<PaymentAdvice> payAdviceList = new ArrayList<>();
		payAdviceList.add(payAdvice);

		when(payAdviceTdsService.getApprovedPaymentAdvices("MUMBAILOG1", "CUD", fromDt, toDt))
				.thenReturn(payAdviceList);
		mockMvc.perform(
				MockMvcRequestBuilders
						.get("/payment-advice-tds/get-approved-list/{logicalLocCd}/{sectionCd}/{fromDt}/{toDt}",
								"MUMBAILOG1", "CUD", "2022-01-01", "2022-01-27")
						.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceTdsService.getApprovedPaymentAdvices("MUMBAILOG2", "CUD", fromDt, toDt)).thenReturn(null);
		mockMvc.perform(
				MockMvcRequestBuilders
						.get("/payment-advice-tds/get-approved-list/{logicalLocCd}/{sectionCd}/{fromDt}/{toDt}",
								"MUMBAILOG2", "CUD", "2022-01-01", "2022-01-27")
						.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isNoContent());

	}

	@Test
	public void getApprovedPaymentAdvicesbyNoTest() throws Exception {
		List<PaymentAdvice> payAdviceList = new ArrayList<>();
		payAdviceList.add(payAdvice);

		when(payAdviceTdsService.getApprovedPaymentAdvicesbyNo("MUMBAILOG1", "CUD", 2021000006))
				.thenReturn(payAdviceList);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice-tds/get-approved-list-byadviceno/{logicalLocCd}/{sectionCd}/{adviceNo}",
						"MUMBAILOG1", "CUD", 2021000006)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceTdsService.getApprovedPaymentAdvicesbyNo("MUMBAILOG2", "CUD", 2021000006)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice-tds/get-approved-list-byadviceno/{logicalLocCd}/{sectionCd}/{adviceNo}",
						"MUMBAILOG2", "CUD", 2021000006)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isNoContent());

	}

	@Test
	public void getPayAdvGstTdsDtlTest() throws Exception {
		PaymentAdviceGstTdsDtl payAdvGstTdsDtl = new PaymentAdviceGstTdsDtl();
		when(payAdviceTdsService.getPaymentAdviceGSTTDSDtl("MUMBAILOG1", "CUD", 2021000006))
				.thenReturn(payAdvGstTdsDtl);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice-tds/get-payAdv-gst-tds-dtl/{logicalLocCd}/{sectionCd}/{adviceNo}", "MUMBAILOG1",
						"CUD", 2021000006)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceTdsService.getPaymentAdviceGSTTDSDtl("MUMBAILOG2", "CUD", 2021000006)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice-tds/get-payAdv-gst-tds-dtl/{logicalLocCd}/{sectionCd}/{adviceNo}", "MUMBAILOG2",
						"CUD", 2021000006)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isNoContent());
	}

	@Test
	public void getPayAdvTdsDtlTest() throws Exception {
		PaymentAdviceTdsDtl payAdvTdsDtl = new PaymentAdviceTdsDtl();
		when(payAdviceTdsService.getPaymentAdviceTDSDtl("MUMBAILOG1", "CUD", 2021000006)).thenReturn(payAdvTdsDtl);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice-tds/get-payAdv-tds-dtl/{logicalLocCd}/{sectionCd}/{adviceNo}", "MUMBAILOG1",
						"CUD", 2021000006)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceTdsService.getPaymentAdviceTDSDtl("MUMBAILOG2", "CUD", 2021000006)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice-tds/get-payAdv-tds-dtl/{logicalLocCd}/{sectionCd}/{adviceNo}", "MUMBAILOG2",
						"CUD", 2021000006)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isNoContent());

	}

	@Test(enabled = true)
	public void updatePaymentAdviceTdsNOTAppliacbleTest() throws Exception {
		String payAdvicestr = mapper.writeValueAsString(payAdvice);

		when(payAdviceTdsService.updatePaymentAdviceTdsNOTAppliacble(payAdvice)).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.put("/payment-advice-tds/updateNotApplicable").content(payAdvicestr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceTdsService.updatePaymentAdviceTdsNOTAppliacble(payAdvice)).thenReturn(0);
		mockMvc.perform(MockMvcRequestBuilders.put("/payment-advice-tds/updateNotApplicable").content(payAdvicestr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isNoContent());

	}
}

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
import in.ecgc.smile.erp.accounts.model.PaymentAdviceGstTdsDtl;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceTcodes;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceTdsDtl;
import in.ecgc.smile.erp.accounts.service.PaymentAdviceService;

public class PaymentAdviceControllerTest {

	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Mock
	private PaymentAdvice payAdvice;

	@Mock
	private PaymentAdviceTcodes payAdviceTcodes;

	@Mock
	private PaymentAdviceTdsDtl payAdvTdsDtl;

	@Mock
	private PaymentAdviceGstTdsDtl paymentAdviceGstTdsDtl;

	@Mock
	PaymentAdviceService payAdviceService;

	@InjectMocks
	PaymentAdviceController paymentAdvicController;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(paymentAdvicController).build();
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

		/*
		 * payAdviceTcodes.setEntityCd("ECGC");
		 * payAdviceTcodes.setLogicalLocCd("MUMBAILOG1");
		 * payAdviceTcodes.setSectionCd("CUD"); payAdviceTcodes.setAdviceNo(2021000006);
		 * 
		 * payAdvice.setPayTcodes(payAdviceTcodes);
		 * 
		 * payAdvTdsDtl.setEntityCd("ECGC"); payAdvTdsDtl.setExpenseHead("CDAC");
		 * payAdvTdsDtl.setLogicalLocCd("MUMBAILOG1");
		 * payAdvTdsDtl.setAdviceNo(2021000006); payAdvTdsDtl.setSectionCd("CUD");
		 * 
		 * payAdvice.setAdviceTdsDtl(payAdvTdsDtl);
		 * 
		 * paymentAdviceGstTdsDtl.setEntityCd("ECGC");
		 * paymentAdviceGstTdsDtl.setLogicalLocCd("MUMBAILOG1");
		 * paymentAdviceGstTdsDtl.setSectionCd("CUD");
		 * 
		 * payAdvice.setAdviceGstTdsDtl(paymentAdviceGstTdsDtl);
		 */

	}

	@Test(enabled = false)
	public void addPaymentAdviceTest() throws Exception {

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

		String payAdvicestr = mapper.writeValueAsString(payAdvice);

		when(payAdviceService.addPaymentAdvice(payAdvice)).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.put("/payment-advice/add").content(payAdvicestr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void disablePaymentAdviceTest() throws Exception {

		when(payAdviceService.disablePaymentAdvice("MUMBAILOG1", "CUD", 2021000006)).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders
				.put("/payment-advice/disable/{logicalLocCd}/{sectionCd}/{adviceNo}", "MUMBAILOG1", "CUD", 2021000006)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceService.disablePaymentAdvice("MUMBAILOG2", "CUD", 2021000006)).thenReturn(0);
		mockMvc.perform(MockMvcRequestBuilders
				.put("/payment-advice/disable/{logicalLocCd}/{sectionCd}/{adviceNo}", "MUMBAILOG2", "CUD", 2021000006)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isNoContent());
	}

	@Test
	public void getAdviceNumberByLocSecTest() throws Exception {

		List<PaymentAdvice> payAdviceList = new ArrayList<>();
		payAdviceList.add(payAdvice);

		when(payAdviceService.getAdviceNumberByLocSec("MUMBAILOG1", "CUD")).thenReturn(payAdviceList);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice/getAdviceNo/{logicalLocCd}/{sectionCd}", "MUMBAILOG1", "CUD")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceService.getAdviceNumberByLocSec("MUMBAILOG2", "CUD")).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice/getAdviceNo/{logicalLocCd}/{sectionCd}", "MUMBAILOG2", "CUD")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isNoContent());

	}

	@Test
	public void getAdviceNumberByLocSecDtTest() throws Exception {

		LocalDate fromDt = LocalDate.parse("2022-01-01");
		LocalDate toDt = LocalDate.parse("2022-01-27");

		List<PaymentAdvice> payAdviceList = new ArrayList<>();
		payAdviceList.add(payAdvice);

		when(payAdviceService.getAdviceNumberByLocSecDt("MUMBAILOG1", "CUD", fromDt, toDt)).thenReturn(payAdviceList);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/payment-advice/getAdviceNo/{logicalLocCd}/{sectionCd}/{fromDt}/{toDt}",
						"MUMBAILOG1", "CUD", "2022-01-01", "2022-01-27").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceService.getAdviceNumberByLocSecDt("MUMBAILOG2", "CUD", fromDt, toDt)).thenReturn(null);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/payment-advice/getAdviceNo/{logicalLocCd}/{sectionCd}/{fromDt}/{toDt}",
						"MUMBAILOG2", "CUD", "2022-01-01", "2022-01-27").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isNoContent());

	}

	@Test
	public void getApprPaymentAdviceByLocSecTest() throws Exception {

		List<PaymentAdvice> payAdviceList = new ArrayList<>();
		payAdviceList.add(payAdvice);

		when(payAdviceService.getApprPaymentAdviceByDtl("MUMBAILOG1", "CUD", "2021-2022")).thenReturn(payAdviceList);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/payment-advice/get-approved/{logicalLocCd}/{sectionCd}/{fiscalYear}",
						"MUMBAILOG1", "CUD", "2021-2022").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void getEnteredPaymentAdviceTest() throws Exception {

		List<PaymentAdvice> payAdviceList = new ArrayList<>();
		payAdviceList.add(payAdvice);

		when(payAdviceService.getEntrPaymentAdvice("MUMBAILOG1", "CUD", "ENTR")).thenReturn(payAdviceList);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice/getEntered/{logicalLocCd}/{sectionCd}/{adviceStat}", "MUMBAILOG1", "CUD", "ENTR")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceService.getEntrPaymentAdvice("MUMBAILOG1", "CUD", "ENTR")).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice/getEntered/{logicalLocCd}/{sectionCd}/{adviceStat}", "MUMBAILOG1", "CUD", "ENTR")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isNoContent());
	}

	@Test
	public void getPaymentAdviceAndTaxTest() throws Exception {

		when(payAdviceService.getPaymentAdviceAndTaxDtl("MUMBAILOG1", "CUD", 2021000006)).thenReturn(payAdvice);
		mockMvc.perform(MockMvcRequestBuilders.get("/payment-advice/get-tax-info/{logicalLocCd}/{sectionCd}/{adviceNo}",
				"MUMBAILOG1", "CUD", 2021000006).contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void getPaymentAdviceByAdviceDtlTest() throws Exception {

		when(payAdviceService.getPaymentAdviceByAdviceDtl("MUMBAILOG1", "CUD", 2021000006)).thenReturn(payAdvice);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/payment-advice/get/{logicalLocCd}/{sectionCd}/{adviceNo}", "MUMBAILOG1", "CUD", 2021000006)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void getPaymentAdviceByLogicalLocSectionCdTest() throws Exception {

		LocalDate fromDt = LocalDate.parse("2022-01-01");
		LocalDate toDt = LocalDate.parse("2022-01-27");

		List<PaymentAdvice> payAdviceList = new ArrayList<>();
		payAdviceList.add(payAdvice);

		when(payAdviceService.getPaymentAdviceByLogicalLocSectionCd("MUMBAILOG1", "CUD", fromDt, toDt))
				.thenReturn(payAdviceList);
		mockMvc.perform(MockMvcRequestBuilders.get("/payment-advice/get/{logicalLocCd}/{sectionCd}/{fromDt}/{toDt}",
				"MUMBAILOG1", "CUD", "2022-01-01", "2022-01-27").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceService.getPaymentAdviceByLogicalLocSectionCd("MUMBAILOG2", "CUD", fromDt, toDt))
				.thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/payment-advice/get/{logicalLocCd}/{sectionCd}/{fromDt}/{toDt}",
				"MUMBAILOG2", "CUD", "2022-01-01", "2022-01-27").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isNoContent());

	}

	@Test
	public void getSeqNoTest() throws Exception {

		when(payAdviceService.getAdviceNo("MUMBAILOG1", "CUD", "2021-2022")).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.get("/payment-advice/getSeqNo/{logicalLocCd}/{sectionCd}/{fiscalYear}",
				"MUMBAILOG1", "CUD", "2021-2022").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void listAllPaymentAdviceTcodesTest() throws Exception {
		List<PaymentAdvice> payAdviceList = new ArrayList<>();
		payAdviceList.add(payAdvice);

		when(payAdviceService.listAllPaymentAdviceTcodes()).thenReturn(payAdviceList);
		mockMvc.perform(MockMvcRequestBuilders.get("/payment-advice/payTcodes/getAll")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceService.listAllPaymentAdviceTcodes()).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/payment-advice/payTcodes/getAll")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isNoContent());

	}

	@Test
	public void listAllPaymentAdvicesTest() throws Exception {
		List<PaymentAdvice> payAdviceList = new ArrayList<>();
		payAdviceList.add(payAdvice);

		when(payAdviceService.listAllPaymentAdvice()).thenReturn(payAdviceList);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/payment-advice/getAll").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceService.listAllPaymentAdvice()).thenReturn(null);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/payment-advice/getAll").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	public void takePaymentAdviceDecisionTest() throws Exception {

		String payAdvicestr = mapper.writeValueAsString(payAdvice);

		when(payAdviceService.takeDecisionOnPaymentAdvice(payAdvice)).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/payment-advice/takeDecision").content(payAdvicestr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(payAdviceService.takeDecisionOnPaymentAdvice(payAdvice)).thenReturn(0);
		mockMvc.perform(MockMvcRequestBuilders.post("/payment-advice/takeDecision").content(payAdvicestr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isNoContent());

	}

	@Test(enabled = false)
	public void updatePaymentAdviceTest() throws Exception {
		String payAdvicestr = mapper.writeValueAsString(payAdvice);

		when(payAdviceService.updatePaymentAdvice(payAdvice)).thenReturn(0);

		mockMvc.perform(MockMvcRequestBuilders.put("/payment-advice/update").content(payAdvicestr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		/*
		 * when(payAdviceTdsService.getPaymentAdviceTDSDtl("MUMBAILOG2", "CUD",
		 * 2021000006)).thenReturn(null); mockMvc.perform(MockMvcRequestBuilders .get(
		 * "/payment-advice-tds/get-payAdv-tds-dtl/{logicalLocCd}/{sectionCd}/{adviceNo}",
		 * "MUMBAILOG2", "CUD", 2021000006)
		 * .contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().
		 * isNoContent());
		 */
	}

	@Test
	public void updateSeqNoTest() throws Exception {
		when(payAdviceService.updateSeqNo("MUMBAILOG1", "CUD", "2021-2022", 2021000006)).thenReturn(1);
		mockMvc.perform(
				MockMvcRequestBuilders
						.put("/payment-advice/updateSeqNo/{logicalLocCd}/{sectionCd}/{fiscalYear}/{adviceNo}",
								"MUMBAILOG1", "CUD", "2021-2022", 2021000006)
						.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
}

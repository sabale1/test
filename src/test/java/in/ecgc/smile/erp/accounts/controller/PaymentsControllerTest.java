package in.ecgc.smile.erp.accounts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.Payments;
import in.ecgc.smile.erp.accounts.model.PaymentsTcodes;
import in.ecgc.smile.erp.accounts.service.PaymentsService;

public class PaymentsControllerTest extends AbstractTestNGSpringContextTests {

	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Mock
	Payments payments;

	@Mock
	GlTxnHdr glTxnHdr;

	@Mock
	GlTxnDtl glTxnDtl;

	@Mock
	PaymentsTcodes paymentsTcodes;

//	@Mock
//	FtrDtl dtl;

	@Mock
	PaymentsService service;

	@InjectMocks
	PaymentsController paymentsController;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(paymentsController).build();
		mapper = new ObjectMapper();
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

	/*
	 * @Test public void createPaymentEntryTest() throws Exception {
	 * payments.setGlTxnHdr(glTxnHdr); payments.setPaymentsTcodes(paymentsTcodes);
	 * String paymentString = mapper.writeValueAsString(payments);
	 * when(service.createPaymentEntry(Mockito.any())).thenReturn(new Integer(1));
	 * mockMvc.perform(MockMvcRequestBuilders.post("/payment/create-payment-entry").
	 * content(paymentString)
	 * .contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isCreated
	 * ())
	 * .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	 * 
	 * }
	 */

	/*
	 * @Test private void updatePaymentsDataTest() throws Exception {
	 * payments.setGlTxnHdr(glTxnHdr); payments.setPaymentsTcodes(paymentsTcodes);
	 * 
	 * String paymentsStr = mapper.writeValueAsString(payments);
	 * 
	 * when(service.updatePaymentsData(Mockito.any())).thenReturn(true);
	 * mockMvc.perform(MockMvcRequestBuilders.put("/payment/update-payments-data").
	 * content(paymentsStr)
	 * .contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
	 * .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	 * 
	 * }
	 */

	@Test
	public void getPaymentsByPaymentDtlTest() throws Exception {
		when(service.getPaymentsByPaymentDtl(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(payments);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/payment/get-payments-by-dtl/{paymentNo}/{logicalLocCd}/{sectionCd}",
						"2002000035", "MUMBAILOG1", "ACC").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void getPaymentsListlTest() throws Exception {
		List<Payments> paylist = new ArrayList<>();
		paylist.add(payments);
		when(service.getPaymentsList()).thenReturn(paylist);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/payment/get-payments-data").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void getpaymentsbylocsecdtTest() throws Exception {
		List<Payments> paylist = new ArrayList<>();
		paylist.add(payments);
		when(service.getpaymentsbylocsecdt(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(paylist);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/payment/getpaymentsbylocsecdt/{logicalLocCd}/{sectionCd}/{fromDt}/{toDt}",
						"MUMBAILOG1", "ACC", "2022-01-01", "2022-01-10").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void getpaymentsbydtTest() throws Exception {
		List<Payments> paylist = new ArrayList<>();
		paylist.add(payments);
		when(service.getpaymentsbydt(Mockito.any(), Mockito.any())).thenReturn(paylist);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/payment/getpaymentsbydt/{fromDt}/{toDt}", "2022-01-01", "2022-01-10")
						.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void getpaymentsbyLocSecYrTest() throws Exception {
		List<Payments> paylist = new ArrayList<>();
		paylist.add(payments);
		when(service.getpaymentsbyLocSecYr(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(paylist);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/payment/getpaymentsbylocsecyr/{logicalLocCd}/{sectionCd}/{fiscalYr}",
						"MUMBAILOG1", "ACC", "2021-2022").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void createPaymentJsonTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/payment/create-payment-json")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk());
	}

}

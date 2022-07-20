package in.ecgc.smile.erp.accounts.controller;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ecgc.smile.erp.accounts.model.PayinslipCustCode;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditDtl;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;
import in.ecgc.smile.erp.accounts.service.PayinslipCustCodeService;
import in.ecgc.smile.erp.accounts.service.PaymentEmployeeDirectCreditService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

public class PayinSlipCustCodeControllerTest {

	private ObjectMapper mapper;
	private MockMvc mockMvc;
	@Mock
	PayinslipCustCode payinSlipCustCode;

	@Mock
	PayinslipCustCodeService payinSlipCustCodeservice;
	@InjectMocks
	PayinslipCustCodeController payinSlipCustCodeController;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(payinSlipCustCodeController).build();
		mapper = new ObjectMapper();
	}

	@BeforeTest
	public void initPayinSlipCustCode() {
		payinSlipCustCode = new PayinslipCustCode();
		payinSlipCustCode.setLogicallocCd("HOLOG02");
		payinSlipCustCode.setGstin("HOLOG02");
		payinSlipCustCode.setGstinstate("7657632hDSFFSDG");
		payinSlipCustCode.setNeftCode("SBIN0567890");
		payinSlipCustCode.setIfscCode("SBIN0567890");
		payinSlipCustCode.setBeneficiaryAcctName("ABC123");
		payinSlipCustCode.setBeneficiaryCodeIbank("WTVBJ1344");
		payinSlipCustCode.setCustomerCd("ABC123T");
	}

	@Test
	public void getPayinslipCustCodeList() throws Exception {
		List<PayinslipCustCode> payinSlipList = new ArrayList<>();
		payinSlipList.add(payinSlipCustCode);
		when(payinSlipCustCodeservice.getPayinslipCustCodeList()).thenReturn(payinSlipList);
		mockMvc.perform(MockMvcRequestBuilders.get("/payInSlipCust/get-payinslipCustCode-data")).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	

	@Test
	public void getPayinslipCustCodeDataById() throws Exception {
		
		when(payinSlipCustCodeservice.getPayinslipCustCodeDataById("ABC123T")).thenReturn(payinSlipCustCode);
		mockMvc.perform(MockMvcRequestBuilders.get("/payInSlipCust/get-payinslipCustCode-data-by-id/{id}","ABC123T")).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void getPayinSlipCustCodeByLogicalLoc() throws Exception {
		List<PayinslipCustCode> payinSlipList = new ArrayList<>();
		payinSlipList.add(payinSlipCustCode);
		
		 when(payinSlipCustCodeservice.getPayinSlipCustCodeByLogicalLoc("HOLOLOG2")).thenReturn(payinSlipList);
		  mockMvc.perform(MockMvcRequestBuilders.get("/payInSlipCust/view-all/{logicalloc}","HOLOLOG2")
		  .contentType(MediaType.APPLICATION_STREAM_JSON)) .andExpect(status().isOk());
		  
		  when(payinSlipCustCodeservice.getPayinSlipCustCodeByLogicalLoc("HOLOLOG2")).thenReturn(null);
		  mockMvc.perform(MockMvcRequestBuilders.get("/payInSlipCust/view-all/{logicalloc}","HOLOLOG2"))
		  .andDo(print()).andExpect(status().isNoContent());
	        }

	@Test
	public void addPayinslipCustCodeData() throws Exception
	{
	
		Boolean res=true;
		String str = mapper.writeValueAsString(payinSlipCustCode);	
		when(payinSlipCustCodeservice.addPayinslipCustCodeData(payinSlipCustCode)).thenReturn(res);
		mockMvc.perform(MockMvcRequestBuilders.post("/payInSlipCust/save-payinslipCustCode-data", payinSlipCustCode).content(str)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk());

	}
	
	
	@Test
	public void updatePayinslipCustCodeData() throws Exception
	{
		Boolean res=true;
		String str = mapper.writeValueAsString(payinSlipCustCode);	
		when(payinSlipCustCodeservice.updatePayinslipCustCodeData(payinSlipCustCode)).thenReturn(res);
		mockMvc.perform(MockMvcRequestBuilders.post("/payInSlipCust/update-payinslipCustCode-data", payinSlipCustCode).content(str)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk());

	}
	
	

}



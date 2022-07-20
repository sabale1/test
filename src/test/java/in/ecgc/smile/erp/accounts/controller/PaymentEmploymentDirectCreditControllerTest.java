package in.ecgc.smile.erp.accounts.controller;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditDtl;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;
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

public class PaymentEmploymentDirectCreditControllerTest {

	private ObjectMapper mapper;
	private MockMvc mockMvc;

	@Mock
	PaymentEmployeeDirectCreditHdr pymtEmpHdr;
	@Mock
	PaymentEmployeeDirectCreditService pymtEmpSerivce;
	@Mock
	PaymentEmployeeDirectCreditDtl pymtEmpDtl;
	@InjectMocks
	PaymentEmployeeDirectCreditController pymtEmpController;
	

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(pymtEmpController).build();
		mapper = new ObjectMapper();
	}

	@BeforeTest
	public void initPaymentEmployment() {
		pymtEmpHdr = new PaymentEmployeeDirectCreditHdr();
		pymtEmpHdr.setRequestedLogicalLoc("HOLOG02");
		pymtEmpHdr.setRequestNo("HOLOG02202001081032");
		pymtEmpHdr.setDepartmentCode("ACCOUNTS");
		List<PaymentEmployeeDirectCreditDtl> paymentEmpList=new ArrayList< PaymentEmployeeDirectCreditDtl>();
		PaymentEmployeeDirectCreditDtl pymtDtl=new PaymentEmployeeDirectCreditDtl();
		pymtDtl.setAccountNumber(null);
		pymtDtl.setAccountSubType(null);
		pymtDtl.setAccountType(null);
		pymtDtl.setAmount(null);
		pymtDtl.setApproved_by(null);
		pymtDtl.setBranchCode(null);
		pymtDtl.setCreatedBy(null);
		pymtDtl.setDebitCredit(null);
		pymtDtl.setEmployeeName(null);
		pymtDtl.setEmployeeNo(null);
		pymtDtl.setLastUpdatedBy(null);
		pymtDtl.setMaiSentFlag(null);
		pymtDtl.setRemarks(null);
		pymtDtl.setRequestNo(null);
		pymtDtl.setRequestSerialNumber(null);
		pymtDtl.setStatus(null);
		paymentEmpList.add(pymtDtl);
		
		}

	@Test
	public void viewAll() throws Exception {
		List<PaymentEmployeeDirectCreditHdr> pymtEmpList = new ArrayList<>();
		pymtEmpList.add(pymtEmpHdr);
		when(pymtEmpSerivce.viewAll()).thenReturn(pymtEmpList);
		mockMvc.perform(MockMvcRequestBuilders.get("/directCredit/view-all")).andDo(print())
				// .contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(pymtEmpSerivce.viewAll()).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/directCredit/view-all").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andDo(print()).andExpect(status().isNoContent());
       }

	@Test
	public void getPaymentAdviceByLogicalLoc() throws Exception {
		
		  List<PaymentEmployeeDirectCreditHdr> list = new ArrayList<>();
		  list.add(pymtEmpHdr);
		  when(pymtEmpSerivce.getPaymentAdviceByLogicalLoc("HOLOLOG2")).thenReturn(list);
		  mockMvc.perform(MockMvcRequestBuilders.get("/directCredit/view-all/{logicalloc}","HOLOLOG2")
		  .contentType(MediaType.APPLICATION_STREAM_JSON)) .andExpect(status().isOk());
		  
		  when(pymtEmpSerivce.getPaymentAdviceByLogicalLoc("HOLOLOG2")).thenReturn(null);
		  mockMvc.perform(MockMvcRequestBuilders.get("/directCredit/view-all/{logicalloc}","HOLOLOG2"))
		  .andDo(print()).andExpect(status().isNoContent());
		 
			}

	
	@Test
	public void addPaymentEmployment() throws Exception
	{
		String res=new String();
		res="Success";
		String str = mapper.writeValueAsString(pymtEmpHdr);	
		when(pymtEmpSerivce.addPaymentEmployment(pymtEmpHdr)).thenReturn(res);
		mockMvc.perform(MockMvcRequestBuilders.post("/directCredit/add", pymtEmpHdr).content(str)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isCreated());

	}
	
	@Test
	
	public void getPaymentEmpDirectCredit() throws Exception {
		
		/*
		 * List<PaymentEmployeeDirectCreditHdr> list = new ArrayList<>();
		 * list.add(pymtEmpHdr);
		 */
		  when(pymtEmpSerivce.getPaymentEmpDirectCredit("HOLOG02202001081032","HOLOLOG2","ACCOUNTS")).thenReturn(pymtEmpHdr);
		  mockMvc.perform(MockMvcRequestBuilders.get("/directCredit/view/{requestNo}/{logicalloc}/{departmentCode}","HOLOG02202001081032","HOLOLOG2","ACCOUNTS")
		  .contentType(MediaType.APPLICATION_STREAM_JSON)) .andExpect(status().isOk());
		  
			/*
			 * when(pymtEmpSerivce.getPaymentAdviceByLogicalLoc("HOLOLOG2")).thenReturn(null
			 * ); mockMvc.perform(MockMvcRequestBuilders.get(
			 * "/directCredit/view-all/{logicalloc}","HOLOLOG2"))
			 * .andDo(print()).andExpect(status().isNoContent());
			 */
			}
	
	@Test
public void getPaymentEmpDirectCreditByRequestNo() throws Exception {
		
		
		  when(pymtEmpSerivce.getPaymentEmpDirectCreditByRequestNo("HOLOG02202001081032")).thenReturn(pymtEmpHdr);
		  mockMvc.perform(MockMvcRequestBuilders.get("/directCredit/viewByRequestNo/{requestNo}","HOLOG02202001081032")
		  .contentType(MediaType.APPLICATION_STREAM_JSON)) .andExpect(status().isOk());
		  }
	
	@Test
	public void updatePaymentEmpDirectCredit() throws Exception
	{
		
		String str = mapper.writeValueAsString(pymtEmpHdr);	
		when(pymtEmpSerivce.updatePaymentEmpDirectCredit(pymtEmpHdr)).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/directCredit/update", pymtEmpHdr).content(str)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isCreated());

	}

}



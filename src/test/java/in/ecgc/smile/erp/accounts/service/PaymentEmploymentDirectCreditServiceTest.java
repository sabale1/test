package in.ecgc.smile.erp.accounts.service;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ecgc.smile.erp.accounts.model.FTR;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditDtl;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;
import in.ecgc.smile.erp.accounts.repository.PaymentEmployeeDirectCreditDao;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

public class PaymentEmploymentDirectCreditServiceTest {
	
	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Mock
	PaymentEmployeeDirectCreditHdr pymtEmpHdr;

	@Mock
	PaymentEmployeeDirectCreditDtl pymtEmpDtl;
	
	@Mock
	private PaymentEmployeeDirectCreditDao pymtEmpDao;
	
	@InjectMocks
	private PaymentEmployeeDirectCreditServiceImpl pymtEmpSerivce;
	
	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
		mapper = new ObjectMapper();
	}
	 @BeforeTest
	  public void initPaymentEmployment()
	  {
		  pymtEmpHdr=new PaymentEmployeeDirectCreditHdr();
		  pymtEmpHdr.setRequestedLogicalLoc("HOLOG02");
		  pymtEmpHdr.setRequestNo("HOLOG02202001081032");
		  pymtEmpHdr.setDepartmentCode("ACCOUNTS");
		  List<PaymentEmployeeDirectCreditDtl>pymtEmpList=new ArrayList<PaymentEmployeeDirectCreditDtl>();
		  pymtEmpList.add(pymtEmpDtl);
		  pymtEmpHdr.setPymtEmpList(pymtEmpList);
	  }
	  
	 @Test
		public void viewAll() throws Exception{
			List<PaymentEmployeeDirectCreditHdr> list = new ArrayList<PaymentEmployeeDirectCreditHdr>();
			list.add(pymtEmpHdr);
			when(pymtEmpDao.viewAll()).thenReturn(list);
			Assert.assertEquals(pymtEmpSerivce.viewAll().size(), 1);
					
		}
	 
	 @Test
		public void getPaymentAdviceByLogicalLoc() throws Exception{
			List<PaymentEmployeeDirectCreditHdr> list = new ArrayList<PaymentEmployeeDirectCreditHdr>();
			list.add(pymtEmpHdr);
			String logicalLoc = "HOLOG02";
			when(pymtEmpDao.getPaymentAdviceByLogicalLoc(Mockito.any())).thenReturn(list);
			Assert.assertEquals(pymtEmpSerivce.getPaymentAdviceByLogicalLoc(logicalLoc),list);
					
		}
	 
	 @Test
		public void addPaymentEmployment() throws Exception{
		 String res=new String();
			res="Success";
			String requestedLogicalLoc = "HOLOG02";
			
			when(pymtEmpDao.getRequestNo(requestedLogicalLoc)).thenReturn("Success");
			when(pymtEmpDao.addPaymentEmployment(Mockito.any())).thenReturn("Success");
			Assert.assertEquals(pymtEmpSerivce.addPaymentEmployment(pymtEmpHdr),"Success");
					
		}
	 
	 @Test
		public void getPaymentEmpDirectCredit() throws Exception{
						String requestedLogicalLoc = "HOLOG02";
						String requestNo="HOLOG02202001081032";
						String department="ACCOUNTS";
			when(pymtEmpDao.getPaymentEmpDirectCredit(requestedLogicalLoc,requestNo, department)).thenReturn(pymtEmpHdr);
			Assert.assertEquals(pymtEmpSerivce.getPaymentEmpDirectCredit(requestedLogicalLoc,requestNo,department), pymtEmpHdr);
					
		}
	 
	 @Test
		public void getPaymentEmpDirectCreditByRequestNo() throws Exception{
		        String requestNo="HOLOG02202001081032";
			when(pymtEmpDao.getPaymentEmpDirectCreditByRequestNo(requestNo)).thenReturn(pymtEmpHdr);
			Assert.assertEquals(pymtEmpSerivce.getPaymentEmpDirectCreditByRequestNo(requestNo), pymtEmpHdr);
					
		}
	 
	 @Test
		public void updatePaymentEmpDirectCredit() throws Exception{
		
		 when(pymtEmpDao.updatePaymentEmpDirectCredit(Mockito.any())).thenReturn(1);
			Assert.assertEquals(pymtEmpSerivce.updatePaymentEmpDirectCredit(pymtEmpHdr),Integer.valueOf(1));
					
		}
}

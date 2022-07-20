package in.ecgc.smile.erp.accounts.service;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ecgc.smile.erp.accounts.controller.PayinSlipCustCodeControllerTest;
import in.ecgc.smile.erp.accounts.exception.BankBranchNotFoundException;
import in.ecgc.smile.erp.accounts.exception.ImproperFtrDataProvidedException;
import in.ecgc.smile.erp.accounts.model.FTR;
import in.ecgc.smile.erp.accounts.model.PayinslipCustCode;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditDtl;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;
import in.ecgc.smile.erp.accounts.repository.PayinslipCustCodeDao;
import in.ecgc.smile.erp.accounts.repository.PaymentEmployeeDirectCreditDao;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

public class PayinSlipCustCodeServiceTest {

	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Mock
	PayinslipCustCode payinSlipCustCode;

	@Mock
	PayinslipCustCodeDao payinslipCustCodeDao;

	@InjectMocks
	private PayinslipCustCodeBeServiceImpl payinSlipCustCodeService;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
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
		List<PayinslipCustCode> list = new ArrayList<PayinslipCustCode>();
		list.add(payinSlipCustCode);
		when(payinslipCustCodeDao.getPayinslipCustCodeList()).thenReturn(list);
		Assert.assertEquals(payinSlipCustCodeService.getPayinslipCustCodeList().size(), 1);

	}

	@Test
	public void getPayinSlipCustCodeByLogicalLoc() throws Exception {
		List<PayinslipCustCode> list = new ArrayList<PayinslipCustCode>();
		list.add(payinSlipCustCode);
		String logicalLoc = "HOLOG02";
		when(payinslipCustCodeDao.getPayinSlipCustCodeByLogicalLoc(Mockito.any())).thenReturn(list);
		Assert.assertEquals(payinSlipCustCodeService.getPayinSlipCustCodeByLogicalLoc(logicalLoc), list);

	}

	@Test
	public void getPayinslipCustCodeDataById() throws Exception {
		String customerId = "ABC123";
		when(payinslipCustCodeDao.getPayinslipCustCodeDataById(Mockito.any())).thenReturn(payinSlipCustCode);
		Assert.assertEquals(payinSlipCustCodeService.getPayinslipCustCodeDataById(customerId), payinSlipCustCode);

		when(payinslipCustCodeDao.getPayinslipCustCodeDataById(Mockito.any())).thenReturn(null);
		Assert.assertEquals(payinSlipCustCodeService.getPayinslipCustCodeDataById(customerId), null);

	}

	@Test
	public void addPayinslipCustCodeData() throws Exception {

		boolean res = true;
		when(payinslipCustCodeDao.addPayinslipCustCodeData(Mockito.any())).thenReturn(res);
		Assert.assertEquals(payinSlipCustCodeService.addPayinslipCustCodeData(payinSlipCustCode), res);

	}

	

	@Test
	public void updatePayinslipCustCodeData() throws Exception {

		boolean res = true;
		when(payinslipCustCodeDao.updatePayinslipCustCodeData(Mockito.any())).thenReturn(res);
		Assert.assertEquals(payinSlipCustCodeService.updatePayinslipCustCodeData(payinSlipCustCode), res);

		
	}
}

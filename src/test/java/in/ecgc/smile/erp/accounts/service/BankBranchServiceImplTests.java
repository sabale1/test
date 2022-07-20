package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.exception.BankBranchNotFoundException;
import in.ecgc.smile.erp.accounts.model.BankBranch;
import in.ecgc.smile.erp.accounts.repository.BankBranchDao;

public class BankBranchServiceImplTests {
	private MockMvc mockMvc;
	
	@Mock
	BankBranchDao bankBranchDao;
	
	@Mock
	private BankBranch bankBranch;
	
	@InjectMocks
	BankBranchServiceImpl bankBranchServiceImpl;
	
	@BeforeTest
	private void init() {
		MockitoAnnotations.initMocks(this);;
		mockMvc = MockMvcBuilders.standaloneSetup(bankBranchServiceImpl).build();
		
	}
	@BeforeTest
	public void initBankBranch()
	{
		bankBranch= new BankBranch();
		bankBranch.setExpensebankName("Mumbai");
		bankBranch.setExpenseAccountNumber("1209001509004024");
		bankBranch.setExpenseAccountIfscCode("PUNB0120900");
		bankBranch.setExpenseAcctNeftCode("PUNB0120900");
		bankBranch.setLogicalLocCode("MUM");
		bankBranch.setActive(true);
		bankBranch.setClientId("1234");
		bankBranch.setGstin("123456789123456");
		bankBranch.setCrsubbifurcationCd("AEC");
		bankBranch.setExpencebanksubbifurcationCd("AEC");
		bankBranch.setExpensebankmainGlCd("1700");
		bankBranch.setExpensebanksubGlCd("001");
		bankBranch.setDribmainGlCd("1700");
		bankBranch.setDribsubGlCd("001");
		bankBranch.setCribmainGlCd("1700");
		bankBranch.setCribsubGlCd("001");
		bankBranch.setExpensebankAddr("ADC");
	}
	
	@Test
	public void addBankBranchServiceImplTest() {
		when(bankBranchDao.addBankBranch(bankBranch)).thenReturn(1);
		Assert.assertEquals(bankBranchServiceImpl.addBankBranch(bankBranch), new Boolean(true));
		
		when(bankBranchDao.addBankBranch(bankBranch)).thenReturn(new Integer(0));
		Assert.assertEquals(bankBranchServiceImpl.addBankBranch(bankBranch), new Boolean(false));
	}
	
	@Test(expectedExceptions = BankBranchNotFoundException.class)
	public void findBankBranchByLogicalLocAndBankNameTest() {
		when(bankBranchDao.findBankByLogicalLocationAndBankName("MUM", "SBI")).thenReturn(bankBranch);
		Assert.assertEquals(bankBranchServiceImpl.findBankByLogicalLocationAndBankName("MUM", "SBI"), bankBranch);
		
		when(bankBranchDao.findBankByLogicalLocationAndBankName("MUM", "SBI")).thenReturn(null);
		Assert.assertEquals(bankBranchServiceImpl.findBankByLogicalLocationAndBankName("MUM", "SBI"), new BankBranchNotFoundException());
	
	}
	
	@Test
	public void listAllBankBranchesServiceImplTest() {
		List<BankBranch> bankBranchList = new ArrayList<>();
		bankBranchList.add(bankBranch);
		when(bankBranchDao.listAllBankBranches()).thenReturn(bankBranchList);
		Assert.assertEquals(bankBranchServiceImpl.listAllBankBranches(), bankBranchList);
	}

	@Test(expectedExceptions = BankBranchNotFoundException.class)
	public void updateBankBranchServiceImplTest() {
		when(bankBranchDao.updateBankBranch("MUM","SBI", bankBranch)).thenReturn(1);
		when(bankBranchDao.findBankByLogicalLocationAndBankName("MUM", "SBI")).thenReturn(bankBranch);
		Assert.assertEquals(bankBranchServiceImpl.updateBankBranch("MUM", "SBI", bankBranch), new Integer(1));
		
		when(bankBranchDao.updateBankBranch("MUM","SBI", bankBranch)).thenReturn(null);
		when(bankBranchDao.findBankByLogicalLocationAndBankName("MUM", "SBI")).thenReturn(null);

		Assert.assertEquals(bankBranchServiceImpl.updateBankBranch("MUM", "SBI", bankBranch), new BankBranchNotFoundException());
	
	}
	
	@Test(expectedExceptions = BankBranchNotFoundException.class)
	public void disableBankBranchServiceImplTest() {
		when(bankBranchDao.disableBankBranch("MUM","SBI")).thenReturn(1);
		when(bankBranchDao.findBankByLogicalLocationAndBankName("MUM", "SBI")).thenReturn(bankBranch);
		Assert.assertEquals(bankBranchServiceImpl.disableBankBranch("MUM","SBI"),new Integer(1));
		
		when(bankBranchDao.disableBankBranch("MUM","SBI")).thenReturn(1);
		when(bankBranchDao.findBankByLogicalLocationAndBankName("MUM", "SBI")).thenReturn(null);
		Assert.assertEquals(bankBranchServiceImpl.disableBankBranch("MUM","SBI"),new BankBranchNotFoundException());
	}
	
	@Test
	public void listActiveBankBranchServiceTest() {
		List<BankBranch>bankBranchList =  new ArrayList<>();
		bankBranchList.add(bankBranch);
		when(bankBranchDao.listActiveBankBranches()).thenReturn(bankBranchList);
		Assert.assertEquals(bankBranchServiceImpl.listActiveBankBranches(),	bankBranchList);
	}
	@Test
	public void getGstinByLogicalLoc() {
		String gstin =""; 
		when(bankBranchDao.getGstinByLogicalLoc("MUM")).thenReturn(gstin);
		Assert.assertEquals(bankBranchServiceImpl.getGstinByLogicalLoc("MUM"),gstin );
		
	}
}


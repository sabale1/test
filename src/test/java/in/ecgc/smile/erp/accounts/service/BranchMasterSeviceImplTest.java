package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.exception.BankBranchNotFoundException;
import in.ecgc.smile.erp.accounts.model.BranchMaster;
import in.ecgc.smile.erp.accounts.repository.BranchMasterDao;

public class BranchMasterSeviceImplTest {

	BranchMaster branchMaster;

	@Mock
	BranchMasterDao branchMasterDao;

	@InjectMocks
	BranchMasterSeviceImpl branchMasterService;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	public void initBranchMaster() {
		this.branchMaster = new BranchMaster();

		branchMaster.setLogicalLocCode("MUMBAILOG1");
		branchMaster.setBranchCode("MUMBAILOG1");
		branchMaster.setBranchName("Bandra West");
		branchMaster.setBankName("PNB");
		branchMaster.setBankBranchAddress("Mumbai");
		branchMaster.setGstinNumber("SBIJ1234567");
		branchMaster.setGstStateUt("SBIJ1234567");
		branchMaster.setGstStateUtCode("SBIJ1234567");
		branchMaster.setExpenseAccountNumber("122222000");
		branchMaster.setExpenseAccountsNeftCode("SBIJ1234567");
		branchMaster.setExpenseAccountIfscCode("SBIJ1234567");
		branchMaster.setClientId("123");
		branchMaster.setVirtualId("123");
		branchMaster.setActive(true);
		branchMaster.setCreatedBy("user");
		branchMaster.setLastUpdatedBy("user");
		branchMaster.setMetaStatus("metastatus");
		branchMaster.setMetaRemarks("metaRemarks");
	}

	@Test
	public void addBranchTest() {
		when(branchMasterDao.addBranch(branchMaster)).thenReturn(1);
		Assert.assertEquals(branchMasterService.addBranch(branchMaster), new Boolean(true));

		when(branchMasterDao.addBranch(branchMaster)).thenReturn(0);
		Assert.assertEquals(branchMasterService.addBranch(branchMaster), new Boolean(false));
	}

	@Test(expectedExceptions = { BankBranchNotFoundException.class })
	public void disableBranchTest() {
		when(branchMasterDao.findBranchByLogicalLocationAndBankCode("MUMBAILOG1", "MUMBAILOG1"))
				.thenReturn(branchMaster);
		when(branchMasterDao.disableBranch("MUMBAILOG1", "MUMBAILOG1")).thenReturn(new Integer(1));
		Assert.assertEquals(branchMasterService.disableBranch("MUMBAILOG1", "MUMBAILOG1"), Integer.valueOf(1));

		when(branchMasterDao.findBranchByLogicalLocationAndBankCode("MUMBAILOG1", "MUMBAILOG1")).thenReturn(null);
		Assert.assertEquals(branchMasterService.disableBranch("MUMBAILOG1", "MUMBAILOG1"),
				new BankBranchNotFoundException());
	}

	@Test
	public void listAllBranchesTest() {
		List<BranchMaster> list = new ArrayList<>();
		list.add(branchMaster);
		when(branchMasterDao.listAllBranches()).thenReturn(list);
		Assert.assertEquals(branchMasterService.listAllBranches(), list);
	}

	@Test(expectedExceptions = { BankBranchNotFoundException.class })
	public void updateBranchTest() {
		when(branchMasterDao.findBranchByLogicalLocationAndBankCode("MUMBAILOG1", "MUMBAILOG1"))
				.thenReturn(branchMaster);
		when(branchMasterDao.updateBranch("MUMBAILOG1", "MUMBAILOG1", branchMaster)).thenReturn(new Integer(1));
		Assert.assertEquals(branchMasterService.updateBranch("MUMBAILOG1", "MUMBAILOG1", branchMaster),
				Integer.valueOf(1));

		when(branchMasterDao.findBranchByLogicalLocationAndBankCode("MUMBAILOG1", "MUMBAILOG1")).thenReturn(null);
		Assert.assertEquals(branchMasterService.updateBranch("MUMBAILOG1", "MUMBAILOG1", branchMaster),
				new BankBranchNotFoundException());

	}
}

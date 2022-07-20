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

import in.ecgc.smile.erp.accounts.model.AutoGLTxnRequestModel;
import in.ecgc.smile.erp.accounts.repository.AutoGLTxnDao;

public class AutoGLTxnServiceImplTest {

	@Mock
	private AutoGLTxnRequestModel autoGLTxn;

	@Mock
	private AutoGLTxnDao autoGLTxnDao;

	@InjectMocks
	private AutoGLTxnServiceImpl autoGLTxnService;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	public void initAutoGLTxn() {
		autoGLTxn.setAccountCd("Test1");
		autoGLTxn.setLogicalLogCd("MUMBAILOG1");
		autoGLTxn.setRequestNo(2);
		autoGLTxn.setFiscalYr("2021-2022");
		autoGLTxn.setAdvanceAmt(2500.00);
		autoGLTxn.setBaseAmt(2500.00);
		autoGLTxn.setBillAmt(3000.00);
		autoGLTxn.setModuleCd("Test");
		autoGLTxn.setRemarks("Test");
		autoGLTxn.setReqStatus("P");
	}

	@Test(expectedExceptions = { Exception.class})
	public void createTxnRequest() throws Exception {
		when(autoGLTxnDao.createTxnRequest(autoGLTxn)).thenReturn(new Integer(1));
		Assert.assertEquals(autoGLTxnService.createTxnRequest(autoGLTxn), new Integer(1));
		
		when(autoGLTxnDao.createTxnRequest(autoGLTxn)).thenReturn(new Integer(0));
		autoGLTxnService.createTxnRequest(autoGLTxn);

	}

	@Test
	public void getAllAutoTxnRequest() throws Exception {
		List<AutoGLTxnRequestModel> autoGLTxnList = new ArrayList<>();
		autoGLTxnList.add(autoGLTxn);
		when(autoGLTxnDao.getAllAutoTxnRequest()).thenReturn(autoGLTxnList);
		Assert.assertEquals(autoGLTxnService.getAllAutoTxnRequest(), autoGLTxnList);

	}

	@Test
	public void getAllAutoTxnRequestById() throws Exception {

		when(autoGLTxnDao.getAllAutoTxnRequestById("2")).thenReturn(autoGLTxn);
		Assert.assertEquals(autoGLTxnService.getAllAutoTxnRequestById("2"), autoGLTxn);

	}

	@Test
	public void updateRequestStatus() throws Exception {
		when(autoGLTxnDao.updateRequestStatus("2", "Test")).thenReturn(new Integer(1));
		Assert.assertEquals(autoGLTxnService.updateRequestStatus("2", "Test"), new Integer(1));

	}
	
	@Test
	public void updateAutoTxnRequest() throws Exception {
		Assert.assertEquals(autoGLTxnService.updateAutoTxnRequest(autoGLTxn), null);

	}
}

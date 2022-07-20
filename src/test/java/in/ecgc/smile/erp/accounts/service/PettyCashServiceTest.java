package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.exception.PettyCashDetailsNotFound;
import in.ecgc.smile.erp.accounts.model.PettyCashMaster;
import in.ecgc.smile.erp.accounts.repository.PettyCashMasterDaoImpl;
import in.ecgc.smile.erp.accounts.service.sts.StateTransitionService;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;

public class PettyCashServiceTest {

	@Mock
	private PettyCashMaster pettyCashMaster;
	@Mock
	private List<PettyCashMaster> pettyCashMstList;

	@Mock
	UserInfoService userInfoService;

	@Mock
	private PettyCashMasterDaoImpl pettyCashMasterDaoImpl;

	@InjectMocks
	PettyCashMasterServiceImpl pettyCashService;

	@Mock
	StateTransitionService stateTransitionService;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	public void initPettyCash() {
		PettyCashMaster pettyCashMaster = new PettyCashMaster();
		pettyCashMaster.setCashAmt(0.0);
		pettyCashMaster.setCreatedBy("ACCOUNTS");

		pettyCashMaster.setEntityCd("ECGC");
		pettyCashMaster.setFiscalYr("2021");

		pettyCashMaster.setLogicalLocCode("MUMBAILOG1");
		pettyCashMaster.setRemark("Com");
		pettyCashMaster.setReqStatus("Requested");
		pettyCashMaster.setRequestDt(LocalDate.parse("2021-02-04"));
		pettyCashMaster.setRequisitionNo(2021000002);
	}

	@Test
	public void addPettyCashDetailsTest() {
		String startState = "request_generation";
		String userId = userInfoService.getUser();
		String status = "request_submited";

		when(userInfoService.getUser()).thenReturn("ACCOUNTS");
		when(stateTransitionService.generateProcessInstance(userId, "petty_cash", "accounts", startState))
				.thenReturn(5678L);
		when(pettyCashMasterDaoImpl.addPettyCashDetails(pettyCashMaster)).thenReturn(1);
		when(pettyCashMasterDaoImpl.updateProcessInstanceId(pettyCashMaster.getRequisitionNo(),
				pettyCashMaster.getLogicalLocCode(), 5678L)).thenReturn(1);
		when(stateTransitionService.updateProcessInstance(5678L, status, userId)).thenReturn("approve/reject_status");
		Assert.assertEquals(pettyCashService.addPettyCashDetails(pettyCashMaster), new Integer(0));

		when(userInfoService.getUser()).thenReturn(null);
		when(stateTransitionService.generateProcessInstance(userId, "petty_cash", "accounts", startState))
				.thenReturn(5678L);
		when(pettyCashMasterDaoImpl.addPettyCashDetails(pettyCashMaster)).thenReturn(0);
		when(pettyCashMasterDaoImpl.updateProcessInstanceId(pettyCashMaster.getRequisitionNo(),
				pettyCashMaster.getLogicalLocCode(), 5678L)).thenReturn(1);
		when(stateTransitionService.updateProcessInstance(5678L, status, userId)).thenReturn("approve/reject_status");
		Assert.assertEquals(pettyCashService.addPettyCashDetails(pettyCashMaster), new Integer(0));

	}

	@Test
	public void listAllPettyCashMasterTest() {
		when(pettyCashMasterDaoImpl.listAllPettyCash()).thenReturn(pettyCashMstList);
		Assert.assertEquals(pettyCashService.listAllPettyCashMaster(), pettyCashMstList);
	}

	@Test
	public void approvedStatusTest() {
		when(userInfoService.getUser()).thenReturn("1229");
		when(pettyCashMasterDaoImpl.getProcessInstanceId(2021000002, "MUMBAILOG1")).thenReturn(Long.valueOf(100));
		when(pettyCashMasterDaoImpl.approvedStatus(pettyCashMaster)).thenReturn(1);
		when(pettyCashMasterDaoImpl.updateProcessInstanceId(2021000002, "MUMBAILOG1", Long.valueOf(100)))
				.thenReturn(new Integer(1));
		when(stateTransitionService.updateProcessInstance(Long.valueOf(100), "request_approved_or_rejected", "1229"))
				.thenReturn("res");
		Assert.assertEquals(pettyCashService.approvedStatus(pettyCashMaster), new Integer(1));

		when(userInfoService.getUser()).thenReturn(null);
		when(pettyCashMasterDaoImpl.getProcessInstanceId(2021000002, "MUMBAILOG1")).thenReturn(Long.valueOf(100));
		when(pettyCashMasterDaoImpl.approvedStatus(pettyCashMaster)).thenReturn(1);
		when(pettyCashMasterDaoImpl.updateProcessInstanceId(2021000002, "MUMBAILOG1", Long.valueOf(100)))
				.thenReturn(new Integer(1));
		when(stateTransitionService.updateProcessInstance(Long.valueOf(100), "request_approved_or_rejected", "1229"))
				.thenReturn("res");
		Assert.assertEquals(pettyCashService.approvedStatus(pettyCashMaster), new Integer(1));
	}

	@Test(expectedExceptions = { PettyCashDetailsNotFound.class })
	public void findByRequisitionNoTest() {
		when(pettyCashMasterDaoImpl.findByRequisitionNo(2021000001, "MUMBAILOG1")).thenReturn(pettyCashMaster);
		Assert.assertEquals(pettyCashService.findByRequisitionNo(2021000001, "MUMBAILOG1"), pettyCashMaster);

		when(pettyCashMasterDaoImpl.findByRequisitionNo(2021000001, "MUMBAILOG1")).thenReturn(null);
		Assert.assertEquals(pettyCashService.findByRequisitionNo(2021000001, "MUMBAILOG1"),
				new PettyCashDetailsNotFound());
	}

	@Test
	public void getProcessInstanceIdTest() {
		when(pettyCashMasterDaoImpl.getProcessInstanceId(2021000001, "MUMBAILOG1")).thenReturn(5678L);
		Assert.assertEquals(pettyCashService.getProcessInstanceId(2021000001, "MUMBAILOG1"), new Long(5678));
	}

	@Test
	public void updateProcessInstanceIdTest() {
		when(pettyCashMasterDaoImpl.updateProcessInstanceId(2021000001, "MUMBAILOG1", 5678L)).thenReturn(1);
		Assert.assertEquals(pettyCashService.updateProcessInstanceId(2021000001, "MUMBAILOG1", 5678L), new Integer(1));
	}

	@Test(expectedExceptions = { PettyCashDetailsNotFound.class })
	public void updatePettyCashTest() {

		when(pettyCashMasterDaoImpl.findByRequisitionNo(2021000001, "MUMBAILOG1")).thenReturn(pettyCashMaster);
		when(pettyCashMasterDaoImpl.updatePettyCash(2021000001, "MUMBAILOG1", pettyCashMaster)).thenReturn(1);
		Assert.assertEquals(pettyCashService.updatePettyCash(2021000001, "MUMBAILOG1", pettyCashMaster),
				new Integer(1));

		when(pettyCashMasterDaoImpl.findByRequisitionNo(2021000001, "MUMBAILOG1")).thenReturn(null);
		Assert.assertEquals(pettyCashService.updatePettyCash(2021000001, "MUMBAILOG1", pettyCashMaster),
				new PettyCashDetailsNotFound());
	}

	@Test
	public void approveMultipleRequestTest() {
		List<PettyCashMaster> list = new ArrayList<>();
		list.add(pettyCashMaster);
		when(pettyCashMasterDaoImpl.approvedStatus(pettyCashMaster)).thenReturn(1);
		Assert.assertEquals(pettyCashService.approveMultipleRequest(list), new Integer(1));
	}
}

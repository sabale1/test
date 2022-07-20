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

import in.ecgc.smile.erp.accounts.exception.ImproperFtrDataProvidedException;
import in.ecgc.smile.erp.accounts.model.FTR;
import in.ecgc.smile.erp.accounts.model.FtrDtl;
import in.ecgc.smile.erp.accounts.repository.FTRDaoImpl;
import in.ecgc.smile.erp.accounts.repository.FtrRefundDaoImpl;

public class FtrRefundServiceTest {

	@Mock
	FTR ftr;

	@Mock
	FtrDtl dtl;

	@Mock
	FtrRefundDaoImpl dao;

	@Mock
	FTRDaoImpl ftrDao;

	@InjectMocks
	FtrRefundServiceImpl service;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	public void initFtr() {
		ftr = new FTR();
		ftr.setFtrReqNo(21);
		ftr.setFtrType("Fund Transfer");
		ftr.setLogicalLocCode("HOLOG1");
		ftr.setFtrReqBranchCd("HO");
		ftr.setFtrReqBy(101);
		ftr.setFtrTrfAmt(60000.00);
		ftr.setFtrReqDeptCd("ADMIN");
		ftr.setFtrReqDt(LocalDate.now());
		ftr.setFtrApprBy(102);
		ftr.setFtrReqStatus("P");
		ftr.setFtrTrfDt(LocalDate.now());
		ftr.setPvStatus("P");
		ftr.setReqTo("MUMBAILOG1");

		FtrDtl ftrdtl = new FtrDtl();
		ftrdtl.setFTRRequestNo(21);
		ftrdtl.setFTRRequestAmount((float) 60000.00);
		ftrdtl.setFTRRequestReason("Test");
		ftrdtl.setFTRRequestType("ADM");
		ftrdtl.setFTRRequestSrNo(1);
		ftrdtl.setFtrFor("Other");
		List<FtrDtl> list = new ArrayList<FtrDtl>();
		list.add(ftrdtl);
		ftr.setFtrDtl(list);

	}

	@Test(expectedExceptions = { ImproperFtrDataProvidedException.class,Exception.class})
	public void addRefundRequestTest() throws ImproperFtrDataProvidedException {
		System.out.println(ftr.getFtrDtl().get(0));
		when(ftrDao.getSeq()).thenReturn(21);
		when(dao.addRefundRequest(ftr)).thenReturn(1);
		when(dao.addRefundRequestDtl(ftr.getFtrDtl().get(0))).thenReturn(21);
		Assert.assertEquals(service.addRefundRequest(ftr), new Integer(21));
		
		when(ftrDao.getSeq()).thenReturn(21);
		when(dao.addRefundRequest(ftr)).thenReturn(0);
		service.addRefundRequest(ftr);
		
		ftr.setFtrTrfAmt(65000.00);
		when(ftrDao.getSeq()).thenReturn(21);
		service.addRefundRequest(ftr);

	}

	@Test
	public void getAllFTRRequestTest() {
		List<FTR> ftrs = new ArrayList<FTR>();
		ftrs.add(ftr);
		String logicalLoc = "HOLOG1";
		when(dao.getAllFTRRequest(logicalLoc)).thenReturn(ftrs);
		Assert.assertEquals(service.getAllFTRRequest(logicalLoc), ftrs);
	}

	@Test
	public void getFTRRequestDTLTest() {
		when(ftrDao.findFtrDtl("21")).thenReturn(ftr);
		Assert.assertEquals(service.getFTRRequestDTL("21"), ftr);

	}

	@Test(expectedExceptions = { ImproperFtrDataProvidedException.class})
	public void decisionOnFTRRequestTest() throws ImproperFtrDataProvidedException {
		when(ftrDao.decisionOnFTRRequest(ftr)).thenReturn(1);
		Assert.assertEquals(service.decisionOnFTRRequest(ftr), new Integer(1));
		
		ftr.setReqTo("MUMBAILOG2");
		service.decisionOnFTRRequest(ftr);
	}

}

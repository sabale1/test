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

import in.ecgc.smile.erp.accounts.exception.ReceiptNotFoundException;
import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.model.States;
import in.ecgc.smile.erp.accounts.repository.ReceiptDao;

public class ReceiptMasterServiceImplTests {

	@Mock
	ReceiptDao receiptDao;

	@Mock
	private Receipt receipt;

	@InjectMocks
	ReceiptServiceImpl receiptServiceImpl;

	@Mock
	List<Receipt> receipts;

	@Mock
	States states;

	@BeforeTest
	private void init() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	public void initReceipt() {
		LocalDate receiptDate = LocalDate.parse("2018-04-01");
		LocalDate instrumentDate = LocalDate.parse("2018-04-01");

		receipt = new Receipt();
		receipt.setLogicalLocCode("MUM");
		receipt.setReceivedFromCode("abc");
		receipt.setReceiptAmount(2000.00);
		receipt.setFiscalYear("2018");
		receipt.setReceiptNumber(100);
		receipt.setIwdNumber("abc100");
		receipt.setReceiptDate(receiptDate);
		receipt.setRemarks("final");
		receipt.setReceivedFromName("Super");
		receipt.setReceivedFromAddress("22 street road");
		receipt.setStampAmount(1000.00);
		receipt.setInstrumentType("NEFT");
		receipt.setInstrumentNumber("123456");
		receipt.setInstrumentDate(instrumentDate);
		receipt.setDrawnOn("2019-04-01");
		receipt.setGlTxnNumberOld(13452);
		receipt.setGlFlag("N");
		receipt.setOldReceiptNumber(1245);
		receipt.setPayInSlip("submitted");
		receipt.setMetaRemarks("abc");
		receipt.setMetaStatus("T");
		receipt.setInvoiceNo("TI100");
		
		receipt.setUtgstAmount(100);
		receipt.setSgstAmount(100);
		receipt.setIgstAmount(100);
		receipt.setCgstAmount(100);
		

	}

	@Test
	public void initState() {
		states.setStateUtCode(2);
		states.setStateUtName("Himachal Pradesh");
		states.setStateUtType("State");
	}

	@Test
	public void addReceiptServiceImplTest() {
		when(receiptDao.getReceiptNo("MUM","2018")).thenReturn(100);
		when(receiptDao.getTaxInvoiceNo("MUM","2018")).thenReturn("100");
		when(receiptDao.addReceipt(receipt)).thenReturn(1);
		Assert.assertEquals(receiptServiceImpl.addReceipt(receipt), receipt.getReceiptNumber());
		
		receipt.setUtgstAmount(0);
		receipt.setSgstAmount(0);
		receipt.setIgstAmount(0);
		receipt.setCgstAmount(0);
		receipt.setInvoiceNo("BS100");
		when(receiptDao.getReceiptNo("MUM","2018")).thenReturn(100);
		when(receiptDao.getBSInvoiceNo("MUM","2018")).thenReturn("100");
		when(receiptDao.addReceipt(receipt)).thenReturn(1);
		Assert.assertEquals(receiptServiceImpl.addReceipt(receipt), receipt.getReceiptNumber());
		
	}

	@Test(expectedExceptions = { ReceiptNotFoundException.class })
	public void viewReceiptByLogicalLocAndReceiptNoServiceImplTest() {
		when(receiptDao.viewByLogicalLocCodeAndReceiptNo("MUMBAILOG1", 2021000002)).thenReturn(receipt);
		Assert.assertEquals(receiptServiceImpl.viewByLogicalLocCodeAndReceiptNo("MUMBAILOG1", 2021000002), receipt);

		when(receiptDao.viewByLogicalLocCodeAndReceiptNo("MUMBAILOG1", 2021000002)).thenReturn(null);
		Assert.assertEquals(receiptServiceImpl.viewByLogicalLocCodeAndReceiptNo("MUMBAILOG1", 2021000002),
				new ReceiptNotFoundException());
	}

	@Test
	public void listAllReceiptServiceImplTest() {
		when(receiptDao.listAllReceipts()).thenReturn(receipts);
		Assert.assertEquals(receiptServiceImpl.listAllReceipts(), receipts);
	}

	@Test(expectedExceptions = { ReceiptNotFoundException.class})
	public void updateReceiptSericeImplTest() {
		when(receiptDao.viewByLogicalLocCodeAndReceiptNo("MUMBAILOG1", 100)).thenReturn(receipt);
		when(receiptDao.updateReceipt("MUMBAILOG1", 100, receipt)).thenReturn(1);
		Assert.assertEquals(receiptServiceImpl.updateReceipt("MUMBAILOG1", 100, receipt), new Integer(1));

		when(receiptDao.updateReceipt("MUMBAILOG1", 100, receipt)).thenReturn(1);
		when(receiptDao.viewByLogicalLocCodeAndReceiptNo("MUMBAILOG1", 100)).thenReturn(null);
		Assert.assertEquals(receiptServiceImpl.updateReceipt("MUMBAILOG1", 100, receipt),
				new ReceiptNotFoundException());

	}

	@Test
	public void viewByLogicalLocCode() {
		when(receiptDao.viewByLogicalLocationCode("MUMBAILOG1")).thenReturn(receipts);
		Assert.assertEquals(receiptServiceImpl.viewByLogicalLocCode("MUMBAILOG1"), receipts);
	}

	@Test
	public void getAllStates() {
		List<States> stateList = new ArrayList<>();
		stateList.add(states);
		when(receiptDao.getAllStates()).thenReturn(stateList);
		Assert.assertEquals(receiptServiceImpl.getAllStates(), stateList);
	}

	@Test
	public void updatePrintFlag() throws Exception {
		when(receiptDao.updatePrintFlag("PUNELOG01", 2021000001, "true")).thenReturn(new Integer(1));
		Assert.assertEquals(receiptServiceImpl.updatePrintFlag("PUNELOG01", 2021000001, "true"), new Integer(1));
	}

	@Test // (expectedExceptions = { ReceiptNotFoundException.class})
	public void getFlag() {
		String str = new String();
		str = "Success";
		when(receiptDao.getFlag("PUNELOG01", 2021000001)).thenReturn(str);
		Assert.assertEquals(receiptServiceImpl.getFlag("PUNELOG01", 2021000001), str);

		when(receiptDao.getFlag("PUNELOG01", 2021000001)).thenReturn(null);
		Assert.assertEquals(receiptServiceImpl.getFlag("PUNELOG01", 2021000001), null);

	}
}

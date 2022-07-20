package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.exception.ReceiptNotFoundException;
import in.ecgc.smile.erp.accounts.model.ChqDishonorEntry;
import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.repository.ChqDishonorEntryDao;

public class ChqDishonorEntryServiceImplTest {

	@InjectMocks
	private ChqDishonorEntryServiceImpl service;

	@Mock
	private List<ChqDishonorEntry> list;

	@Mock
	private ChqDishonorEntryDao chqDishonorEntryDao;

	@Mock
	private ChqDishonorEntry chqDishonorEntry;

	@BeforeTest
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
		MockMvcBuilders.standaloneSetup(service).build();
	}

	@BeforeTest
	public void initObjects() {
		chqDishonorEntry = new ChqDishonorEntry();
		// chqDishonorEntry.setDishonorDt(LocalDate.parse("2021-12-23"));
		chqDishonorEntry.setDishonorReason("Test");
		chqDishonorEntry.setFiscalYr("2021-22");
		chqDishonorEntry.setGlTxnNumber(2021000002);
		chqDishonorEntry.setInstrumentNumber("123456789");
		chqDishonorEntry.setInstrumentType("DD");
		chqDishonorEntry.setLogicalLocCode("PUNELOG1");
		chqDishonorEntry.setReceiptNumber(202100002);
		chqDishonorEntry.setOldReceiptNumber(202100002);
	}

	@Test
	public void addChqDishonorEntryDataTest() throws Exception {
		when(chqDishonorEntryDao.addChqDishonorEntryData(chqDishonorEntry)).thenReturn(true);
		Assert.assertEquals(service.addChqDishonorEntryData(chqDishonorEntry), true);
	}

	@Test
	public void updateChqDishonorEntryDataTest() throws Exception {
		when(chqDishonorEntryDao.updateChqDishonorEntryData(chqDishonorEntry)).thenReturn(true);
		Assert.assertEquals(service.updateChqDishonorEntryData(chqDishonorEntry), true);
	}

	@Test
	public void getChqDishonorEntryListTest() throws Exception {
		when(chqDishonorEntryDao.getChqDishonorEntryList()).thenReturn(list);
		Assert.assertEquals(service.getChqDishonorEntryList(), list);
	}

	@Test
	public void getChqDishonorEntryDataByChequeNoTest() throws Exception {
		when(chqDishonorEntryDao.getChqDishonorEntryByChequeNo("10111567")).thenReturn(chqDishonorEntry);
		Assert.assertEquals(service.getChqDishonorEntryByChequeNo("10111567"), chqDishonorEntry);
		
		when(chqDishonorEntryDao.getChqDishonorEntryByChequeNo("10111567")).thenReturn(null);
		Assert.assertEquals(service.getChqDishonorEntryByChequeNo("10111567"), null);
	}
	
	@Test
	public void viewByInstrumentTypeTest() throws Exception {
		List<Receipt> list = new ArrayList<>();
		Receipt rec = new Receipt();
		list.add(rec);
		when(chqDishonorEntryDao.viewByInstrumentType("DD")).thenReturn(list);
		Assert.assertEquals(service.viewByInstrumentType("DD"), list);
	}
	
	@Test(expectedExceptions = { ReceiptNotFoundException.class})
	public void viewByChqNoChqTypeChqDtRcptNoTest() throws Exception {
		Receipt rec = new Receipt();
		when(chqDishonorEntryDao.viewByChqNoChqTypeChqDtRcptNo("DD","123456789",LocalDate.now(),202100002)).thenReturn(rec);
		Assert.assertEquals(service.viewByChqNoChqTypeChqDtRcptNo("DD","123456789",LocalDate.now(),202100002), rec);
		
		when(chqDishonorEntryDao.viewByChqNoChqTypeChqDtRcptNo("DD","123456789",LocalDate.now(),202100002)).thenReturn(null);
		Assert.assertEquals(service.viewByChqNoChqTypeChqDtRcptNo("DD","123456789",LocalDate.now(),202100002), null);
	}
}

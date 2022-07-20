package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.exception.LOVMstEntryNotFound;
import in.ecgc.smile.erp.accounts.model.LOVMaster;
import in.ecgc.smile.erp.accounts.repository.LOVMasterDao;

public class LovMasterServiceImplTests {

	@Mock
	LOVMasterDao lovMasterDao;
	@Mock
	private LOVMaster lovMaster;
	@InjectMocks
	LOVMasterServiceImpl lovMasterServiceImpl;

	@BeforeTest
	private void init() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	private void initLovMaster() {
		lovMaster = new LOVMaster();
		lovMaster.setLovCd("ACCT");
		lovMaster.setLovDesc("Income");
		lovMaster.setLovSubCd("ACCOUNTS");
		lovMaster.setLovValue("INCM");

	}

	@Test
	public void listAllLovMasterServiceImplTest() {
		List<LOVMaster> lovMasterList = new ArrayList<>();
		lovMasterList.add(lovMaster);
		when(lovMasterDao.viewallLOVMstEntries()).thenReturn(lovMasterList);
		Assert.assertEquals(lovMasterServiceImpl.viewallLOVMstEntries(), lovMasterList);
	}

	@Test
	public void addLovMasterServiceImplTest() {
		when(lovMasterDao.addLovMstEntry(lovMaster)).thenReturn(1);
		Assert.assertEquals(lovMasterServiceImpl.addLovMstEntry(lovMaster), 1);
	}

	@Test
	public void t1CodesTest() {
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		when(lovMasterDao.getT1codes()).thenReturn(mapParam);
		Assert.assertEquals(lovMasterServiceImpl.t1Codes(), mapParam);
	}

	@Test
	public void t2CodesTest() {
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		when(lovMasterDao.getT2codes()).thenReturn(mapParam);
		Assert.assertEquals(lovMasterServiceImpl.t2Codes(), mapParam);
	}

	@Test
	public void viewLovEntryTest() {
		when(lovMasterDao.viewLovEntry("ACCT", "Accounts", "ASST")).thenReturn(lovMaster);
		Assert.assertEquals(lovMasterServiceImpl.viewLovEntry("ACCT", "Accounts", "ASST"), lovMaster);
	}

	@Test
	public void modifyLovEntryTest() {
		when(lovMasterDao.modifyLovEntry(lovMaster)).thenReturn(1);
		Assert.assertEquals(lovMasterServiceImpl.modifyLovEntry(lovMaster), 1);
	}

	//@Test(expectedExceptions = { LOVMstEntryNotFound.class})
	public void disableLovEntryTest() {
		when(lovMasterServiceImpl.viewLovEntry("ACCT", "ACCOUNTS", "ASST")).thenReturn(lovMaster);
		when(lovMasterDao.disableLovEntry("ACCT", "ACCOUNTS", "ASST")).thenReturn(new Integer(1));
		Assert.assertEquals(lovMasterServiceImpl.disableLovEntry("ACCT", "ACCOUNTS", "ASST"), Integer.valueOf(1));
		
		when(lovMasterServiceImpl.viewLovEntry("ACCT", "ACCOUNTS", "ASST")).thenReturn(null);
		lovMasterServiceImpl.disableLovEntry("ACCT", "ACCOUNTS", "ASST");
	}

	@Test
	public void payToTest() {
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		when(lovMasterDao.getpayTo()).thenReturn(mapParam);
		Assert.assertEquals(lovMasterServiceImpl.payTo(), mapParam);
	}

	@Test
	public void currencyValTest() {
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		when(lovMasterDao.getcurrencyVal()).thenReturn(mapParam);
		Assert.assertEquals(lovMasterServiceImpl.currencyVal(), mapParam);
	}

	@Test
	public void sectionCodesTest() {
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		when(lovMasterDao.getsectionCodes()).thenReturn(mapParam);
		Assert.assertEquals(lovMasterServiceImpl.sectionCodes(), mapParam);
	}
	
	@Test
	public void getInstrumentTypesTest() {
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		when(lovMasterDao.getInstrumentTypes()).thenReturn(mapParam);
		Assert.assertEquals(lovMasterServiceImpl.getInstrumentTypes(), mapParam);
	}
	
	@Test
	public void requestTypesTest() {
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		when(lovMasterDao.getrequestTypes()).thenReturn(mapParam);
		Assert.assertEquals(lovMasterServiceImpl.requestTypes(), mapParam);
	}
	
	@Test
	public void frtForTest() {
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		when(lovMasterDao.getfrtFor()).thenReturn(mapParam);
		Assert.assertEquals(lovMasterServiceImpl.frtFor(), mapParam);
	}
	
	@Test
	public void dishonorReasonsTest() {
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		when(lovMasterDao.getdishonorReasons()).thenReturn(mapParam);
		Assert.assertEquals(lovMasterServiceImpl.dishonorReasons(), mapParam);
	}
	
	@Test
	public void getScheduleTitleDetailsTest() {
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		when(lovMasterDao.getScheduleTitleDetails()).thenReturn(mapParam);
		Assert.assertEquals(lovMasterServiceImpl.getScheduleTitleDetails(), mapParam);
	}
	
	@Test
	public void getPrefixTypesTest() {
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		when(lovMasterDao.getPrefixTypes()).thenReturn(mapParam);
		Assert.assertEquals(lovMasterServiceImpl.getPrefixTypes(), mapParam);
	}

}

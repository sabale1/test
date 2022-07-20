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

import in.ecgc.smile.erp.accounts.model.FiscalYearModel;
import in.ecgc.smile.erp.accounts.repository.FiscalYearDaoImpl;

public class FiscalYearServiceTest {

	@Mock
	FiscalYearModel fiscalYear;
	
	@Mock
	List<FiscalYearModel> list;

	@Mock
	FiscalYearDaoImpl dao;

	@InjectMocks
	FiscalYearServiceImpl service;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	void initFiscalYear() {
		fiscalYear = new FiscalYearModel();
		fiscalYear.setCurrFiscalYear("2020-2021");
		fiscalYear.setCurrFiscalYearStartDt(LocalDate.of(2020, 01, 03));
		fiscalYear.setPrevFiscalYear("2019-2020");
		fiscalYear.setPrevFiscalYearClosedDt(LocalDate.of(2020, 01, 03));
		fiscalYear.setMetaStatus('V');
	}

	@Test
	public void findCurrentFiscalYearTest() {
		when(dao.findCurrentFiscalYear()).thenReturn(fiscalYear);
		Assert.assertEquals(service.findCurrentFiscalYear(), fiscalYear);
		
		when(dao.findCurrentFiscalYear()).thenReturn(null);
		Assert.assertEquals(service.findCurrentFiscalYear(), null);

	}

	@Test
	public void getFiscalYearListTest() {
		List<String> list = new ArrayList<String>();
		list.add("2019-20");
		list.add("2020-21");
		when(dao.getFiscalYearList()).thenReturn(list);
		Assert.assertEquals(service.getFiscalYearList(), list);
		
		when(dao.getFiscalYearList()).thenReturn(null);
		Assert.assertEquals(service.getFiscalYearList(), null);

	}
	
	
	@Test
	public void getFiscalYrModelListTest() {
		//List<FiscalYearModel> list = new ArrayList<FiscalYearModel>();
		list.add(fiscalYear);
		when(dao.getFiscalYrModelList()).thenReturn(list);
		Assert.assertEquals(service.getFiscalYrModelList(), list);
		
		when(dao.getFiscalYrModelList()).thenReturn(null);
		Assert.assertEquals(service.getFiscalYrModelList(), list);
		
	}
	
	@Test
	public void getFiscalYrDataByIdTest() {
		when(dao.getFiscalYrDataById("2021-2022")).thenReturn(fiscalYear);
		Assert.assertEquals(service.getFiscalYrDataById("2021-2022"), fiscalYear);

	}
	
	@Test
	public void getfiscalYearSchedularAutoTest() {
		
		when(dao.findCurrentFiscalYear()).thenReturn(fiscalYear);
		when(dao.updateCurrentFiscalYear(2021,2022,"2019-2020")).thenReturn(1);
		Assert.assertEquals(service.fiscalYearSchedularAuto(), "2021-2022");
		
		when(dao.findCurrentFiscalYear()).thenReturn(null);
		Assert.assertEquals(service.fiscalYearSchedularAuto(), null);

	}

}

package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.exception.PDCEntryAlreadyExists;
import in.ecgc.smile.erp.accounts.model.PostDatedCheque;
import in.ecgc.smile.erp.accounts.repository.PDCDao;

public class PostDatedChequeServiceTest {

	@Mock
	PDCDao pdcDao;

	@Mock
	private PostDatedCheque pdc;

	@InjectMocks
	PDCServiceImpl pdcServiceImpl;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	public void initPostDatedCheque() {
		pdc = new PostDatedCheque();

		pdc.setDrawnOn("SBI");
		pdc.setInstrumentAmount(123456.00f);
		pdc.setInstrumentDate(new Date(2020 - 12 - 02));
		pdc.setInstrumentNo("123456");
		pdc.setInstrumentStatus('n');
		pdc.setInstrumentType("chq");
		pdc.setLogicalLocCode("PUNE123");
		pdc.setReceivedFromaddr("Pune");
		pdc.setReceivedFromCode("abcd");
		pdc.setReceivedFromName("ANN");
		pdc.setSrNo(1);
	}

	@Test(expectedExceptions = { PDCEntryAlreadyExists.class })
	public void createPDCEntryTest() {
		when(pdcDao.viewByChequeNo(1)).thenReturn(null);
		when(pdcDao.createPDCEntry(pdc)).thenReturn(1);
		Assert.assertEquals(pdcServiceImpl.createPDCEntry(pdc), new Boolean(true));

		when(pdcDao.viewByChequeNo(1)).thenReturn(null);
		when(pdcDao.createPDCEntry(pdc)).thenReturn(0);
		Assert.assertEquals(pdcServiceImpl.createPDCEntry(pdc), new Boolean(false));

		when(pdcDao.viewByChequeNo(1)).thenReturn(pdc);
		Assert.assertEquals(pdcServiceImpl.createPDCEntry(pdc), new Boolean(false));
	}

	@Test
	public void listAllPDCTest() {
		List<PostDatedCheque> allPdc = new ArrayList<PostDatedCheque>();
		allPdc.add(pdc);
		when(pdcDao.listAllPDC()).thenReturn(allPdc);
		Assert.assertEquals(pdcServiceImpl.listAllPDC(), allPdc);
	}

	@Test
	public void viewByChequeNoTest() {
		when(pdcDao.viewByChequeNo(123456)).thenReturn(pdc);
		Assert.assertEquals(pdcServiceImpl.viewByChequeNo(123456), pdc);
	}

	@Test
	public void viewByStatusTest() {
		List<PostDatedCheque> allPdc = new ArrayList<PostDatedCheque>();
		allPdc.add(pdc);
		when(pdcDao.viewByStatus('n')).thenReturn(allPdc);
		Assert.assertEquals(pdcServiceImpl.viewByStatus('n'), allPdc);
	}

	@Test
	public void checkUniqueTest() {
		when(pdcDao.checkUnique("123456", "2020-12-02")).thenReturn(pdc);
		Assert.assertEquals(pdcServiceImpl.checkUnique("123456", "2020-12-02"), pdc);
	}

	@Test
	public void changeStatusTest() {
		when(pdcDao.changeStatus("123456", pdc)).thenReturn(new Integer(1));
		Assert.assertEquals(pdcServiceImpl.changeStatus("123456", pdc), Integer.valueOf(1));
	}
}

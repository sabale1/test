package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.ScheduleCdGlMapping;
import in.ecgc.smile.erp.accounts.repository.ScheduleCdGlMappingDao;

public class ScheduleCdGlMappingBeServiceImplTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Mock
	ScheduleCdGlMappingDao scheduleCdGlMappingDao;
	@InjectMocks
	private ScheduleCdGlMappingBeServiceImpl scheduleCdGlMappingBeServiceImpl;
	@Mock
	ScheduleCdGlMapping scheduleCdGlMapping;
	@Mock
	List<ScheduleCdGlMapping> scheduleCdGlMappingList;

	@BeforeTest
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
		MockMvcBuilders.standaloneSetup(scheduleCdGlMappingBeServiceImpl).build();
	}

	@BeforeTest
	public void initObjects() {
		// "initialize Your Object Here";
	}

	@Test
	public void addScheduleCdGlMappingDataTest() throws Exception {
		when(scheduleCdGlMappingDao.addScheduleCdGlMappingData(scheduleCdGlMapping)).thenReturn(true);
		Boolean response = scheduleCdGlMappingDao.addScheduleCdGlMappingData(scheduleCdGlMapping);
		when(scheduleCdGlMappingBeServiceImpl.addScheduleCdGlMappingData(scheduleCdGlMapping)).thenReturn(response);
		Assert.assertEquals(response, true);
	}

	@Test
	public void updateScheduleCdGlMappingDataTest() throws Exception {
		when(scheduleCdGlMappingDao.updateScheduleCdGlMappingData(scheduleCdGlMapping)).thenReturn(true);
		Boolean response = scheduleCdGlMappingDao.updateScheduleCdGlMappingData(scheduleCdGlMapping);
		when(scheduleCdGlMappingBeServiceImpl.updateScheduleCdGlMappingData(scheduleCdGlMapping)).thenReturn(response);
		Assert.assertEquals(response, true);
	}

	@Test
	public void getScheduleCdGlMappingListTest() throws Exception {
		when(scheduleCdGlMappingDao.getScheduleCdGlMappingList()).thenReturn(scheduleCdGlMappingList);
		List<ScheduleCdGlMapping> response = scheduleCdGlMappingDao.getScheduleCdGlMappingList();
		when(scheduleCdGlMappingBeServiceImpl.getScheduleCdGlMappingList()).thenReturn(response);
		Assert.assertEquals(response, scheduleCdGlMappingList);
	}

	@Test
	public void getScheduleCdGlMappingDataByIdTest() throws Exception {
		int seqNo = 12;
		when(scheduleCdGlMappingDao.getScheduleCdGlMappingDataById(seqNo)).thenReturn(scheduleCdGlMapping);
		ScheduleCdGlMapping response = scheduleCdGlMappingDao.getScheduleCdGlMappingDataById(seqNo);
		when(scheduleCdGlMappingBeServiceImpl.getScheduleCdGlMappingDataById(seqNo)).thenReturn(response);
		Assert.assertEquals(response, scheduleCdGlMapping);
	}
}

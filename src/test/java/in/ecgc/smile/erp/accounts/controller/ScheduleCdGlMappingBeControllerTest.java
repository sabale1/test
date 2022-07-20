package in.ecgc.smile.erp.accounts.controller;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.ScheduleCdGlMapping;
import in.ecgc.smile.erp.accounts.service.ScheduleCdGlMappingService;

public class ScheduleCdGlMappingBeControllerTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@InjectMocks
	private ScheduleCdGlMappingBeController scheduleCdGlMappingController;
	@Mock
	private List<ScheduleCdGlMapping> scheduleCdGlMappingList;
	@Mock
	private ScheduleCdGlMappingService scheduleCdGlMappingService;
	@Mock
	private ScheduleCdGlMapping scheduleCdGlMapping;

	@BeforeTest
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
		MockMvcBuilders.standaloneSetup(scheduleCdGlMappingController).build();
	}

	@BeforeTest
	public void initObjects() {
		// "initialize Object here";
	}

	@Test
	public void addScheduleCdGlMappingDataTest() throws Exception {
		logger.info("Inside ScheduleCdGlMappingBeControllerTest - addScheduleCdGlMappingDataTest");
		when(scheduleCdGlMappingService.addScheduleCdGlMappingData(scheduleCdGlMapping)).thenReturn(true);
		ResponseEntity<Boolean> response = scheduleCdGlMappingController
				.addScheduleCdGlMappingData(scheduleCdGlMapping);
		Assert.assertEquals(true, response.getBody());
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	@Test
	public void updateScheduleCdGlMappingDataTest() throws Exception {
		logger.info("Inside ScheduleCdGlMappingBeControllerTest - updateScheduleCdGlMappingDataTest");
		when(scheduleCdGlMappingService.updateScheduleCdGlMappingData(scheduleCdGlMapping)).thenReturn(true);
		ResponseEntity<Boolean> response = scheduleCdGlMappingController
				.updateScheduleCdGlMappingData(scheduleCdGlMapping);
		Assert.assertEquals(true, response.getBody());
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	@Test
	public void getScheduleCdGlMappingListTest() throws Exception {
		logger.info("Inside ScheduleCdGlMappingBeControllerTest - getScheduleCdGlMappingListTest");
		when(scheduleCdGlMappingService.getScheduleCdGlMappingList()).thenReturn(scheduleCdGlMappingList);
		ResponseEntity<List<ScheduleCdGlMapping>> response = scheduleCdGlMappingController.getScheduleCdGlMappingList();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		Assert.assertEquals(scheduleCdGlMappingList.size(), response.getBody().size());
	}

	@Test
	public void getScheduleCdGlMappingDataByIdTest() throws Exception {
		logger.info("Inside ScheduleCdGlMappingBeControllerTest - getScheduleCdGlMappingDataByIdTest");
		int seqNo = 1234;
		when(scheduleCdGlMappingService.getScheduleCdGlMappingDataById(seqNo)).thenReturn(scheduleCdGlMapping);
		ResponseEntity<ScheduleCdGlMapping> response = scheduleCdGlMappingController
				.getScheduleCdGlMappingDataById(seqNo);
		Assert.assertEquals(scheduleCdGlMapping, response.getBody());
		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}
}

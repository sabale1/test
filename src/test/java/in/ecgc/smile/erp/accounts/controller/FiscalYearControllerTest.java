package in.ecgc.smile.erp.accounts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ecgc.smile.erp.accounts.model.FiscalYearModel;
import in.ecgc.smile.erp.accounts.service.FiscalYearServiceImpl;

public class FiscalYearControllerTest {

	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Mock
	FiscalYearModel fiscalYear;
	
	@Mock
	FiscalYearServiceImpl service;
	
	@InjectMocks
	FiscalYearController controller;
	
	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mapper = new ObjectMapper();
	}
	
	@BeforeTest
	void initFiscalYear() {
		fiscalYear = new FiscalYearModel();
		fiscalYear.setCurrFiscalYear("2020-21");
		fiscalYear.setCurrFiscalYearStartDt(LocalDate.of(2020, 1, 3));
		fiscalYear.setPrevFiscalYear("2019-20");
		fiscalYear.setPrevFiscalYearClosedDt(LocalDate.of(2020, 1, 3));
		fiscalYear.setMetaStatus('V');
		
	}
	
	@Test
	public void findCurrentFiscalYearTest() throws Exception{
		when(service.findCurrentFiscalYear()).thenReturn(fiscalYear);
		mockMvc.perform(MockMvcRequestBuilders.get("/fiscal-year/view/current-fiscal-year")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath(".currFiscalYear").exists())
				.andExpect(MockMvcResultMatchers.jsonPath(".currFiscalYearStartDt").exists())
				.andExpect(MockMvcResultMatchers.jsonPath(".prevFiscalYear").exists())
				.andExpect(MockMvcResultMatchers.jsonPath(".prevFiscalYearClosedDt").exists())
				.andExpect(MockMvcResultMatchers.jsonPath(".metaStatus").exists());				
	}
	@Test
	public void getFiscalYearListTest() throws Exception{
		List<String> list = new ArrayList<>();
		list.add("2019-20");
		list.add("2020-21");
		when(service.getFiscalYearList()).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/fiscal-year/list")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void testmethodTest() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/fiscal-year/test")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
		.andExpect(status().isOk());
		
	}
	
	
	@Test
	public void getFiscalYrModelListTest() throws Exception{
		List<FiscalYearModel> fislist = new ArrayList<>();
		when(service.getFiscalYrModelList()).thenReturn(fislist);
		mockMvc.perform(MockMvcRequestBuilders.get("/fiscal-year/get-fiscal-yrs")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());			
	}
	
	
	@Test
	public void getFiscalYrDataByIdTest() throws Exception{
		when(service.getFiscalYrDataById("2021-2022")).thenReturn(fiscalYear);
		mockMvc.perform(MockMvcRequestBuilders.get("/fiscal-year/get-fiscal-yr-by-id/{id}","2021-2022")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());			
	}
	
	
}

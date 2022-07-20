package in.ecgc.smile.erp.accounts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.exception.GlobalExceptionHandler;
import in.ecgc.smile.erp.accounts.model.SubBifurcations;
import in.ecgc.smile.erp.accounts.service.SubBiFurcationMasterService;
import in.ecgc.smile.erp.accounts.util.CommonTestUtil;

public class SubBifurcationMasterControllerTest {

	private MockMvc mockMvc;
	private SubBifurcations subBifurcationMaster;

	@InjectMocks
	SubBifurcationMasterController subBifurcationMasterController;

	@Mock
	SubBiFurcationMasterService subBifurcationService;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(subBifurcationMasterController)
				.setControllerAdvice(new GlobalExceptionHandler()).build();
	}

	@BeforeTest
	public void initSubBifurcationMasterValue() {
		subBifurcationMaster = new SubBifurcations();
		subBifurcationMaster.setSubBifurcationLevel("ELC");
		subBifurcationMaster.setDescription("Electricity Charges");
		subBifurcationMaster.setActive('V');
		subBifurcationMaster.setCreatedBy("ACCT");
		subBifurcationMaster.setCreatedDt(LocalDate.now());
		subBifurcationMaster.setLastUpdatedBy("ACCT");
		subBifurcationMaster.setLastUpdatedDt(LocalDate.now());
	}

	@Test
	public void getAllSubBifurcationsTest() throws Exception {
		List<SubBifurcations> mockList = new ArrayList<>();
		mockList.add(subBifurcationMaster);

		when(subBifurcationService.getSubBifurcations()).thenReturn(mockList);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/sub-bifurcations/view-all").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void getAllSubBifurcationLevelsTest() throws Exception {
		List<String> mockList = new ArrayList<>();
		mockList.add("ELC");
		mockList.add("TCE");
		when(subBifurcationService.getSubBifurcationsLevel()).thenReturn(mockList);
		mockMvc.perform(MockMvcRequestBuilders.get("/sub-bifurcations/view-all-level")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk());

	}

	@Test
	public void getAllSubBifurcationsByLEvelTest() throws Exception {
		when(subBifurcationService.getSubBifurcationsByLevel(Mockito.anyString())).thenReturn(subBifurcationMaster);
		mockMvc.perform(MockMvcRequestBuilders.get("/sub-bifurcations/view-all/{subbifurcationLevel}", "ELC")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk());

	}

	@Test
	public void addSubBifurcationTest_whenValidThenSuccess() throws Exception {
		String inputJson = CommonTestUtil.convertToJSON(subBifurcationMaster);
		when(subBifurcationService.addSubBifurcation(subBifurcationMaster)).thenReturn(Integer.valueOf(1));
		mockMvc.perform(MockMvcRequestBuilders.post("/sub-bifurcations/add")
				.contentType(MediaType.APPLICATION_STREAM_JSON).content(inputJson)).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void updateSubBifurcationLevelTest() throws Exception {
		String inputJson = CommonTestUtil.convertToJSON(subBifurcationMaster);
		when(subBifurcationService.getSubBifurcationsByLevel("ELC")).thenReturn(subBifurcationMaster);
		when(subBifurcationService.updatedSubBifurcations(subBifurcationMaster, subBifurcationMaster))
				.thenReturn(subBifurcationMaster);

		mockMvc.perform(MockMvcRequestBuilders.put("/sub-bifurcations/update/{subBifurcationLevel}", "ELC")
				.contentType(MediaType.APPLICATION_STREAM_JSON).content(inputJson)).andExpect(status().isAccepted())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

}

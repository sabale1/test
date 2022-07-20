
package in.ecgc.smile.erp.accounts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.Temp_Exporter;
import in.ecgc.smile.erp.accounts.model.Temp_TDS_DTL;
import in.ecgc.smile.erp.accounts.service.Temp_ExternalService;

public class Temp_ExternalControllerTest {

	private MockMvc mockMvc;

	@Mock
	private Temp_Exporter temp_Exporter;

	@Mock
	private Temp_TDS_DTL temp_TDS_DTL;

	@Mock
	Temp_ExternalService externalService;

	@InjectMocks
	Temp_ExternalController temp_ExternalController;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(temp_ExternalController).build();
	}

	@BeforeTest
	public void initTempExt() {
		temp_Exporter = new Temp_Exporter("TEST", "TEST", "TEST", "TEST", 1234, "TEST", "TEST", "TEST", "TEST", "TEST",
				1234);

		List<String> sectionOfTDS = new ArrayList<String>();
		sectionOfTDS.add("Test");
		Map<String, String> stateOfVendor = new HashMap<>();
		stateOfVendor.put("1", "TestVender");
		Map<String, String> stateOfSupply = new HashMap<>();
		stateOfSupply.put("1", "TestSupply");
		Map<String, String> natureOfService = new HashMap<>();
		natureOfService.put("1", "TestServ");
		Map<String, String> gstType = new HashMap<>();
		gstType.put("1", "TestGST");
		temp_TDS_DTL = new Temp_TDS_DTL(sectionOfTDS, stateOfVendor, stateOfSupply, natureOfService, gstType);
	}

	@Test
	public void getExporterListTest() throws Exception {

		List<Temp_Exporter> tmpextList = new ArrayList<>();
		tmpextList.add(temp_Exporter);

		when(externalService.getExporterList()).thenReturn(tmpextList);

		mockMvc.perform(MockMvcRequestBuilders.get("/ext-service/exporter/getList/{logicalLocCd}", "MUMBAILOG1")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void getTDSDtlTest() throws Exception {

		when(externalService.getTDSDtl()).thenReturn(temp_TDS_DTL);

		mockMvc.perform(MockMvcRequestBuilders.get("/ext-service/exporter/tdsDtl")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
}

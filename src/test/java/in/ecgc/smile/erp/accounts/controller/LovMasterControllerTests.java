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

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ecgc.smile.erp.accounts.model.LOVMaster;
import in.ecgc.smile.erp.accounts.service.LOVMasterService;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;


public class LovMasterControllerTests {

	private ObjectMapper mapper;
	private MockMvc mockMvc;

	@Mock
	private LOVMaster lovMaster;
	
	@Mock
	private UserInfoService userInfo;
	
	@Mock
	private LOVMasterService lovMasterService;
	@InjectMocks
	LOVMasterController lovMasterController;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(lovMasterController).build();
		mapper = new ObjectMapper();
	}

	@BeforeTest
	private void initLovMaster() {
		lovMaster = new LOVMaster();
		lovMaster.setLovCd("ACCT");
		lovMaster.setLovDesc("Income");
		lovMaster.setLovSubCd("Accounts");
		lovMaster.setLovValue("INCM");
		
	}

	@Test
	public void listAllLovMaster() throws Exception {
		ArrayList<LOVMaster> lovMasterList = new ArrayList<LOVMaster>();
		lovMasterList.add(lovMaster);
		when(lovMasterService.viewallLOVMstEntries()).thenReturn(lovMasterList);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/lov-service/list").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("Application/json"))
				.andExpect(MockMvcResultMatchers.jsonPath(".lovCd").exists())
				.andExpect(MockMvcResultMatchers.jsonPath(".lovSubCd").exists())
				.andExpect(MockMvcResultMatchers.jsonPath(".lovValue").exists())
				.andExpect(MockMvcResultMatchers.jsonPath(".lovDesc").exists());
	}

	@Test
	public void addLovMasterControllerTest() throws Exception {
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.addLovMstEntry(lovMaster)).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/lov-service/add").content(lovMasters)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void viewLovEntryTest() throws Exception
	{
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.viewLovEntry("ACCT","Accounts","ASST")).thenReturn(lovMaster);
		mockMvc.perform(MockMvcRequestBuilders.get("/lov-service/view/{lovCd}/{lovSubCd}/{lovValue}","ACCT","Accounts","ASST").content(lovMasters)
		.contentType(MediaType.APPLICATION_STREAM_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void getT1CodesTest() throws Exception
	{
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.t1Codes()).thenReturn(mapParam);
		mockMvc.perform(MockMvcRequestBuilders.get("/lov-service/lov-get/t1Codes").content(lovMasters)
		.contentType(MediaType.APPLICATION_STREAM_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void getT2CodesTest() throws Exception
	{
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.t2Codes()).thenReturn(mapParam);
		mockMvc.perform(MockMvcRequestBuilders.get("/lov-service/lov-get/t2Codes").content(lovMasters)
		.contentType(MediaType.APPLICATION_STREAM_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void modifyLovEntryTest() throws Exception
	{
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.modifyLovEntry(lovMaster)).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.put("/lov-service/modify").content(lovMasters)
		.contentType(MediaType.APPLICATION_STREAM_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	
	@Test
	public void disableLovEntryTest() throws Exception
	{
		when(lovMasterService.disableLovEntry("ACCT", "Accounts", "INCM")).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.put("/lov-service/disable/{lovCd}/{lovSubCd}/{lovValue}","ACCT","Accounts","INCM")
		.contentType(MediaType.APPLICATION_STREAM_JSON))
		.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	
	@Test
	public void getPayToType() throws Exception
	{
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.payTo()).thenReturn(mapParam);
		mockMvc.perform(MockMvcRequestBuilders.get("/lov-service/lov-get/payTo").content(lovMasters)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	
	}
	
	@Test
	public void getCurrencyValue() throws Exception
	{
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.currencyVal()).thenReturn(mapParam);
		mockMvc.perform(MockMvcRequestBuilders.get("/lov-service/lov-get/currencyVal").content(lovMasters)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void getSectionCodes() throws Exception
	{
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.sectionCodes()).thenReturn(mapParam);
		mockMvc.perform(MockMvcRequestBuilders.get("/lov-service/lov-get/sectionCodes").content(lovMasters)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void userInfoDetails() throws Exception
	{
		when(userInfo.getApplicableDepartmentForSession()).thenReturn("Session");
		when(userInfo.getApplicableDesignationForSession()).thenReturn("Session");
		when(userInfo.getApplicableOfficeTypeForSession()).thenReturn("Session");
		when(userInfo.getApplicableRankForSession()).thenReturn("Session");
		when(userInfo.getAZP()).thenReturn("Session");
		when(userInfo.getEmail()).thenReturn("Session");
		when(userInfo.getEmployeeId()).thenReturn("Session");
		when(userInfo.getName()).thenReturn("Session");
		when(userInfo.getPrimaryDepartment()).thenReturn("Session");
		when(userInfo.getPrimaryDesignation()).thenReturn("Session");
		when(userInfo.getPrimaryOffice()).thenReturn("Session");
		when(userInfo.getPrimaryOfficeType()).thenReturn("Session");
		when(userInfo.getPrimaryRank()).thenReturn("Session");
		when(userInfo.getUser()).thenReturn("Session");
		when(userInfo.getCurrentAdditionalChargeList()).thenReturn(new String[]{ "a", "b", "c" });
		
		
		
	}
	
	@Test
	public void getrequestTypes() throws Exception
	{
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.requestTypes()).thenReturn(mapParam);
		mockMvc.perform(MockMvcRequestBuilders.get("/lov-service/lov-get/reuestTypes").content(lovMasters)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void getfrtFor() throws Exception
	{
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.frtFor()).thenReturn(mapParam);
		mockMvc.perform(MockMvcRequestBuilders.get("/lov-service/lov-get/ftrFor").content(lovMasters)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void getdishonorReasons() throws Exception
	{
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.dishonorReasons()).thenReturn(mapParam);
		mockMvc.perform(MockMvcRequestBuilders.get("/lov-service/lov-get/dishonorReasons").content(lovMasters)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void getInstrumentTypes() throws Exception
	{
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.getInstrumentTypes()).thenReturn(mapParam);
		mockMvc.perform(MockMvcRequestBuilders.get("/lov-service/lov-get/instrumentTypes").content(lovMasters)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void getScheduleTitleDetails() throws Exception
	{
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.getScheduleTitleDetails()).thenReturn(mapParam);
		mockMvc.perform(MockMvcRequestBuilders.get("/lov-service/lov-get/scheduleTitleDetails").content(lovMasters)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void getPrefixTypes() throws Exception
	{
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("Income", "INCM");
		String lovMasters = mapper.writeValueAsString(lovMaster);
		when(lovMasterService.getPrefixTypes()).thenReturn(mapParam);
		mockMvc.perform(MockMvcRequestBuilders.get("/lov-service/lov-get/prefixTypes").content(lovMasters)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
}

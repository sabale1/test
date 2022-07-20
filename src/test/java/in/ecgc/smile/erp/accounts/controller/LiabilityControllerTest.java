package in.ecgc.smile.erp.accounts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.Liability;
import in.ecgc.smile.erp.accounts.model.LiabilityGLMapping;
import in.ecgc.smile.erp.accounts.service.LiabilityServiceImpl;

public class LiabilityControllerTest {

	private ObjectMapper mapper;

	private MockMvc mockMvc;
	
	@Mock
	LiabilityServiceImpl liabilityService;
	
	@Mock
	Liability liability;
	
	@Mock
	GlTxnHdr glTxnHdr;
	
	@Mock
	LiabilityGLMapping liabilityGLMapping;
	
	@InjectMocks
	LiabilityController controller;
	
	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mapper = new ObjectMapper();
	}
	
	@BeforeTest
	public void initGlTxnHdr() {
		this.glTxnHdr = new GlTxnHdr();
		glTxnHdr.setGlTxnNo(2021000001);
		glTxnHdr.setGlTxnType("AG");
		glTxnHdr.setFiscalYr("2020-21");
		glTxnHdr.setLogicalLocCd("MUMBAILOG1");
		glTxnHdr.setGlTxnDtls(null);
		List<GlTxnDtl> list = new ArrayList<>();
		GlTxnDtl dtl = new GlTxnDtl();
		dtl.setGlTxnNo(2021000001);
		dtl.setGlTxnType("AG");
		dtl.setLogicalLocCd("MUMBAILOG1");
		dtl.setSrNo(1);
		dtl.setTxnAmt(120001.00);
		dtl.setMainGlCd("1700");
		dtl.setSubGlCd("001");
		dtl.setDrCrFlag("cr");
		list.add(dtl);
		dtl.setGlTxnNo(2021000001);
		dtl.setGlTxnType("AG");
		dtl.setLogicalLocCd("MUMBAILOG1");
		dtl.setSrNo(2);
		dtl.setTxnAmt(120001.00);
		dtl.setMainGlCd("3800");
		dtl.setSubGlCd("001");
		dtl.setDrCrFlag("dr");
		list.add(dtl);
	}
	
	@BeforeTest
	public void initLiability() {
		this.liability=new Liability();
		liability.setModuleCd("MKT");
		liability.setMappingCd("ADVT_ADVANCE");
		liability.setBaseAmt(1000.00);
		liability.setLogicalLogCd("MUMBAILOG01");
		liability.setFiscalYr("2021-2022");
		liability.setIsAmtInInr(true);
		liability.setCurrCode("$");
		liability.setCurrName("Dollar");
		liability.setExchangeRate((float) 33.12233);
		liability.setT1("t1");
		liability.setT2("t2");
		liability.setT3("t3");
		liability.setT4("t4");
		liability.setT5("t5");
		liability.setT6("t6");
		liability.setT7("t7");
		liability.setT8("t8");
		liability.setT9("t9");
		liability.setT10("t10");
		liability.setT11("t11");
		liability.setT12("t12");
		liability.setRemarks("rfemarks");
		liability.setUserId(1);
	}
	
	@Test
	public void createLiabilityTest() throws Exception
	{
		String str = mapper.writeValueAsString(glTxnHdr);
		when(liabilityService.createLiability(Mockito.any())).thenReturn(glTxnHdr);
		mockMvc.perform(MockMvcRequestBuilders.post("/liability/add")
		    	.content(str)
		    	.contentType(MediaType.APPLICATION_STREAM_JSON))
	       .andExpect(status().isCreated())
	       .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		when(liabilityService.createLiability(Mockito.any())).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.post("/liability/add")
		    	.content(str)
		    	.contentType(MediaType.APPLICATION_STREAM_JSON))
	       .andExpect(status().isInternalServerError());
		
	}
	
	@Test
	public void getModuleWiseMappingTest() throws Exception
	{
		List<LiabilityGLMapping> list = new ArrayList<>();
		list.add(liabilityGLMapping);
		when(liabilityService.getMapping()).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/liability/get-mapping/{moduleCd}/{mappingCd}","MKT","ADVT_ADVANCE")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getAllGltxnByFromDtTest() throws Exception
	{
		List<LiabilityGLMapping> list = new ArrayList<>();
		list.add(liabilityGLMapping);
		when(liabilityService.getMapping()).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/liability/get-mapping/{moduleCd}","MKT")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getallmappingTest()  throws Exception
	{
		List<LiabilityGLMapping> liabList = new ArrayList<>();
		LiabilityGLMapping mapping = new LiabilityGLMapping();
		liabList.add(mapping);
		when(liabilityService.getMapping()).thenReturn(liabList);
		mockMvc.perform(MockMvcRequestBuilders.get("/liability/get-all-mapping")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());

	}
	
	@Test
	public void addGLTxnTest()  throws Exception
	{
		List<LiabilityGLMapping> list = new ArrayList<>();
		LiabilityGLMapping mapping = new LiabilityGLMapping();
		list.add(mapping);
		String str = mapper.writeValueAsString(list);
		when(liabilityService.addMApping(list)).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/liability/add-mapping")
				.content(str)
		    	.contentType(MediaType.APPLICATION_STREAM_JSON))
	       .andExpect(status().isCreated())
	       .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void getAllModuleCdsTest()  throws Exception
	{
		List<String> list = new ArrayList<>();
		list.add("MKT");
		when(liabilityService.distinctModuleCd()).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/liability/distinct-module-cd")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getMappingCdAndMappingNameForModuleCdTest() throws Exception
	{
		Map<String, String> map =new HashMap<>();
		map.put("loc", "MUMBAILOG01");
		when(liabilityService.getAllMappingCodeAndMappingNameforModuleCd(Mockito.any())).thenReturn(map);
		mockMvc.perform(MockMvcRequestBuilders.get("/liability/getMappingCd-name/{moduleCd}","MKT")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getApplicableTcodesTest() throws Exception
	{
		List<String> list = new ArrayList<>();
		list.add("MKT");
		when(liabilityService.getRequiredTCodes(Mockito.any(), Mockito.any())).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/liability/getApplicableTcodes/{moduleCd}/{mappingCd}","MKT","MKT")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
	}
}

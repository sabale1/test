package in.ecgc.smile.erp.accounts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.Provision;
import in.ecgc.smile.erp.accounts.model.ProvisionGLMapping;
import in.ecgc.smile.erp.accounts.service.ProvisionService;

public class ProvisionControllerTest {

	private ObjectMapper mapper;
	private MockMvc mockMvc;

	@Mock
	GlTxnHdr glTxnHdr;

	@Mock
	GlTxnDtl glTxnDtl;

	@Mock
	ProvisionGLMapping provisionGLMapping;

	@Mock
	ProvisionService provisionService;

	@Mock
	Provision provision;

	@InjectMocks
	ProvisionController provisionController;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(provisionController).build();
		mapper = new ObjectMapper();
	}

	@BeforeTest
	public void initProvision() {
		provisionGLMapping = new ProvisionGLMapping();
		provisionGLMapping.setDrCrFlag("Dr");
		provisionGLMapping.setMainGL("1810");
		provisionGLMapping.setMappingCd("ADVT_ADVANCE");
		provisionGLMapping.setMappingName("Advance for Marketing Advertisement Expense");
		provisionGLMapping.setMetaRemarks("Advance twrds");
		provisionGLMapping.setMetaStatus(" ");
		provisionGLMapping.setModuleCd("MKT");
		provisionGLMapping.getRemarks();
		provisionGLMapping.setSrNo(1);
		provisionGLMapping.setSubBifurcation("Advance-Others");
		provisionGLMapping.setSubGL("001");
		provisionGLMapping.setTxnType("PV");

	}

	@BeforeTest
	public void initProvision1() {
		provision = new Provision();
		provision.setModuleCd("MKT");
		provision.setMappingCd("ADVT_ADVANCE");
		provision.setBaseAmt(100.00);
		provision.setLogicalLogCd("MUMBAILOG1");
		provision.setIsAmtInInr(true);

	}

	@BeforeTest
	public void intGlTxnHdr() {
		glTxnHdr = new GlTxnHdr();
		glTxnHdr.setLogicalLocCd("MUMBAILOG1");
		glTxnHdr.setGlTxnType("PV");
		glTxnHdr.setTxnDt(LocalDate.of(2021, 01, 02));
		List<GlTxnDtl> list = new ArrayList<GlTxnDtl>();

		list.add(glTxnDtl);
		glTxnHdr.setGlTxnDtls(list);
	}

	@Test
	public void getAllGltxnByFromDt() throws Exception {
		List<ProvisionGLMapping> provision = new ArrayList<ProvisionGLMapping>();
		provision.add(provisionGLMapping);
		when(provisionService.getMapping()).thenReturn(provision);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/provision/get-all-mapping").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());

		when(provisionService.getMapping()).thenReturn(null);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/provision/get-all-mapping").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void getAllGltxnByModuleCd() throws Exception {
		List<ProvisionGLMapping> provision = new ArrayList<ProvisionGLMapping>();
		provision.add(provisionGLMapping);
		// String moduleCd="MKT";
		when(provisionService.getMapping("MKT")).thenReturn(provision);
		mockMvc.perform(MockMvcRequestBuilders.get("/provision/get-mapping/{moduleCd}", "MKT")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk());

		when(provisionService.getMapping("MKT")).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/provision/get-mapping/{moduleCd}", "MKT")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	public void getAllGltxnByModuleCdMappingCd() throws Exception {
		List<ProvisionGLMapping> provision = new ArrayList<ProvisionGLMapping>();
		provision.add(provisionGLMapping);
		when(provisionService.getMapping("MKT", "ADVT_ADVANCE")).thenReturn(provision);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/provision/get-mapping/{moduleCd}/{mappingCd}", "MKT", "ADVT_ADVANCE")
						.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());

		when(provisionService.getMapping("MKT", "ADVT_ADVANCE")).thenReturn(null);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/provision/get-mapping/{moduleCd}/{mappingCd}", "MKT", "ADVT_ADVANCE")
						.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void createProvision() throws Exception {

		List<GlTxnDtl> list = new ArrayList<GlTxnDtl>();
		GlTxnDtl glTxnDtl = new GlTxnDtl();
		glTxnDtl.setSrNo(123);
		glTxnDtl.setDrCrFlag("Dr");
		glTxnDtl.setTxnAmt(1200.0);
		glTxnDtl.setMainGlCd("1810");
		glTxnDtl.setSubGlCd("001");
		glTxnDtl.setGlTxnType("PV");
		glTxnDtl.setLogicalLocCd("MUMBAILOG1");
		list.add(glTxnDtl);

		GlTxnHdr glTxnHDr = new GlTxnHdr();
		glTxnHDr.setLogicalLocCd("MUMBAILOG1");
		glTxnHDr.setGlTxnType("PV");
		glTxnHDr.setTxnDt(LocalDate.of(2021, 01, 02));

		glTxnHDr.setGlTxnDtls(list);

		String ftrstr = mapper.writeValueAsString(provision);

		when(provisionService.createProvision(provision)).thenReturn(glTxnHDr);
		mockMvc.perform(MockMvcRequestBuilders.post("/provision/add", provision).content(ftrstr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(provisionService.createProvision(provision)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.post("/provision/add").content(ftrstr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isBadRequest());

		/*
		 * when(provisionService.createProvision(provision)).thenThrow(Exception.class);
		 * mockMvc.perform(MockMvcRequestBuilders.post("/provision/add").content(ftrstr)
		 * .contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().
		 * isBadRequest());
		 */

	}

	@Test
	private void addGLTxn() throws Exception {
		List<ProvisionGLMapping> provision = new ArrayList<ProvisionGLMapping>();
		provision.add(provisionGLMapping);
		String ftrstr = mapper.writeValueAsString(provision);
		when(provisionService.addMApping(provision)).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/provision/add-mapping", provisionGLMapping).content(ftrstr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isCreated());

		when(provisionService.addMApping(provision)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.post("/provision/add-mapping", provisionGLMapping).content(ftrstr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isBadRequest());

		when(provisionService.addMApping(provision)).thenReturn(0);
		mockMvc.perform(MockMvcRequestBuilders.post("/provision/add-mapping", provisionGLMapping).content(ftrstr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isBadRequest());

	}
}

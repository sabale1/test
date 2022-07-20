package in.ecgc.smile.erp.accounts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import in.ecgc.smile.erp.accounts.model.GLTxnType;
import in.ecgc.smile.erp.accounts.service.GLTxnTypeService;

public class GLTxnTypeMasterControllerTest {
	private ObjectMapper mapper;
	private MockMvc mockMvc;

	GLTxnType glTxnType;

	@Mock
	GLTxnTypeService glTxnTypeService;

	@InjectMocks
	GLTxnTypeMasterController glTxnTypeMasterController;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(glTxnTypeMasterController).build();
		mapper = new ObjectMapper();
		glTxnType = new GLTxnType();
		glTxnType.setGlTxnType("PRV");
		glTxnType.setTxnTypeName("Provision");
		glTxnType.setActive(Boolean.TRUE);
		glTxnType.setIsConfigurable(Boolean.FALSE);
		glTxnType.setOpeningDay(3);
	}

	@Test
	public void listAllGlTxnTypeCodesTest() throws Exception {
		List<GLTxnType> glTxnTypeList = new ArrayList<>();
		glTxnTypeList.add(glTxnType);
		when(glTxnTypeService.listAllGLTxnTypeCodes()).thenReturn(glTxnTypeList);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/gl-txn-type-master").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void listAllGlTxnTypeCodesTest_whenListEmpty_thenNoContent() throws Exception {
		when(glTxnTypeService.listAllGLTxnTypeCodes()).thenReturn(null);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/gl-txn-type-master").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	public void getGLTypeListTest() throws Exception {
		List<String> glTxnTypeNameList = new ArrayList<>();
		glTxnTypeNameList.add("PRV");
		glTxnTypeNameList.add("AG");
		glTxnTypeNameList.add("JV");
		when(glTxnTypeService.getGLTxnType()).thenReturn(glTxnTypeNameList);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/gl-txn-type-master/gltype").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void addGlTxnTypeCode_whenValid_thenSuccess() throws Exception {
		String inputString = mapper.writeValueAsString(glTxnType);
		when(glTxnTypeService.addGLTxnTypeCode(glTxnType)).thenReturn(Boolean.TRUE);

		mockMvc.perform(MockMvcRequestBuilders.post("/gl-txn-type-master")
				.contentType(MediaType.APPLICATION_STREAM_JSON).content(inputString)).andExpect(status().isCreated());
	}

	@Test
	public void updateGLTxnTypeCodeTest() throws Exception {
		String inputString = mapper.writeValueAsString(glTxnType);

		when(glTxnTypeService.updateGLTxnTypeCode("PRV", glTxnType)).thenReturn(new Integer(1));

		mockMvc.perform(MockMvcRequestBuilders.put("/gl-txn-type-master/{glTxnTypeCode}", "PRV").content(inputString)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void findGlTxnTypeByGlTxnTypeCodeTest() throws Exception {

		when(glTxnTypeService.findGlTxnTypeByGlTxnTypeCode("PRV")).thenReturn(glTxnType);
		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn-type-master/{glTxnTypeCode}", "PRV")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void disableGlTxnTypeCodeTest() throws Exception {

		when(glTxnTypeService.disableGLTxnTypeCode("PRV")).thenReturn(new Integer(1));
		mockMvc.perform(MockMvcRequestBuilders.delete("/gl-txn-type-master/{glTxnTypeCode}", "PRV")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isAccepted())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

}

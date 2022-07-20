package in.ecgc.smile.erp.accounts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ecgc.smile.erp.accounts.model.AutoGLTxnRequestModel;
import in.ecgc.smile.erp.accounts.service.AutoGLTxnServiceImpl;

public class AutoGLTxnControllerTest {

	private ObjectMapper mapper;

	private MockMvc mockMvc;
	
	@Mock
	 AutoGLTxnServiceImpl autoGLTxnService;
	
	@InjectMocks
	AutoGLTxnController controller;
	
	@Mock
	AutoGLTxnRequestModel glTxnRequestModel;
	
	
	
	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mapper = new ObjectMapper();
	}
	
	@BeforeTest
	public void initAutoGLTxnRequestModel() {
		this.glTxnRequestModel = new AutoGLTxnRequestModel();
		
		glTxnRequestModel.setRequestNo(1223);
		glTxnRequestModel.setModuleCd("MKT");
		glTxnRequestModel.setAccountCd("ADVT_ADVANCE");
		glTxnRequestModel.setBaseAmt(2345.78);
		glTxnRequestModel.setAdvanceAmt(34545.785);
		glTxnRequestModel.setBillAmt(1234.56);
		glTxnRequestModel.setLogicalLogCd("MUMBAILOG1");
		glTxnRequestModel.setFiscalYr("2021-2022");
		glTxnRequestModel.setReqStatus("P");
		glTxnRequestModel.setT1("t1");
		glTxnRequestModel.setT2("t2");
		glTxnRequestModel.setT3("t3");
		glTxnRequestModel.setT4("t4");
		glTxnRequestModel.setT5("t5");
		glTxnRequestModel.setT6("t6");
		glTxnRequestModel.setT7("t7");
		glTxnRequestModel.setT8("t8");
		glTxnRequestModel.setT9("t9");
		glTxnRequestModel.setT10("t10");
		glTxnRequestModel.setT11("t11");
		glTxnRequestModel.setT12("t12");
		glTxnRequestModel.setRemarks("remarks");
		glTxnRequestModel.setCreatedBy("user");
		glTxnRequestModel.setLastUpdatedBy("user");
		glTxnRequestModel.setMetaRemarks("metaremarks");
	}
	
	@Test
	public void addGlTxnRequestTest() throws Exception
	{
		String str = mapper.writeValueAsString(glTxnRequestModel);
		when(autoGLTxnService.createTxnRequest(Mockito.any())).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/auto-gl-txn/add-request")
		    	.content(str)
		    	.contentType(MediaType.APPLICATION_STREAM_JSON))
	       .andExpect(status().isCreated())
	       .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		/*
		 * mockMvc.perform(MockMvcRequestBuilders.post("/auto-gl-txn/add-request")
		 * .content(str) .contentType(MediaType.APPLICATION_STREAM_JSON))
		 * .andExpect(status().isBadRequest())
		 * .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		 */
	}
	
	@Test
	public void getAllAutoGLTxnRequestTest() throws Exception
	{
		List<AutoGLTxnRequestModel>  list = new ArrayList<>();
		list.add(glTxnRequestModel);
		when(autoGLTxnService.getAllAutoTxnRequest()).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/auto-gl-txn/get-all-req")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
		
		when(autoGLTxnService.getAllAutoTxnRequest()).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/auto-gl-txn/get-all-req")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void getAutoGLTxnRequestByIdTest() throws Exception
	{
		when(autoGLTxnService.getAllAutoTxnRequestById(Mockito.any())).thenReturn(glTxnRequestModel);
		mockMvc.perform(MockMvcRequestBuilders.get("/auto-gl-txn/get-req/{reqNo}","P")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
		
		when(autoGLTxnService.getAllAutoTxnRequestById(Mockito.any())).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/auto-gl-txn/get-req/{reqNo}","P")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateGlTxnRequestStatusTest() throws Exception
	{
		String str = mapper.writeValueAsString(glTxnRequestModel);
		when(autoGLTxnService.updateAutoTxnRequest(Mockito.any())).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/auto-gl-txn/update-request-status")
		    	.content(str)
		    	.contentType(MediaType.APPLICATION_STREAM_JSON))
	       .andExpect(status().isCreated())
	       .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
}

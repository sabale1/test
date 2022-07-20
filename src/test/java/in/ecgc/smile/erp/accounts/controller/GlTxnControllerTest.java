package in.ecgc.smile.erp.accounts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ecgc.smile.erp.accounts.exception.FtrNotFoundException;
import in.ecgc.smile.erp.accounts.exception.GlTxnNotFoundException;
import in.ecgc.smile.erp.accounts.model.AutoGLTxnRequestModel;
import in.ecgc.smile.erp.accounts.model.GlBrBalanceIn;
import in.ecgc.smile.erp.accounts.model.GlBrBalanceOut;
import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.service.GlTxnServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlTxnControllerTest {

	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Mock
	GlTxnHdr glTxnHdr;
	@Mock
	GlTxnDtl glTxnDtl;
	
	@Mock
	GlBrBalanceIn balanceIn;
	@Mock
	GlBrBalanceOut balanceOut;
	
	@Mock
	GlTxnServiceImpl service;
	
	@InjectMocks
	GlTxnController controller;
	
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
	
	

	@Test
	public void addGlTxnTest() throws Exception{
		String str = mapper.writeValueAsString(glTxnHdr);
		log.info("Date is : {}",LocalDate.now());
		when(service.addGlTxn(Mockito.any())).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/gl-txn/add")
		    	.content(str)
		    	.contentType(MediaType.APPLICATION_STREAM_JSON))
	       .andExpect(status().isCreated())
	       .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
		
		/*
		when(service.addGlTxn(Mockito.any())).thenReturn(0);
		mockMvc.perform(MockMvcRequestBuilders.post("/gl-txn/add")
		    	.content(str)
		    	.contentType(MediaType.APPLICATION_STREAM_JSON))
	       .andExpect(status().isBadRequest())
	       .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	       */		
	}
	
	@Test 
	public void getAllGltxn() throws Exception{
		List<GlTxnHdr> list = new ArrayList<>();
		list.add(glTxnHdr);
		when(service.getAllGlTxnHdrs(Mockito.any())).thenReturn(list);		
		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/view-all/{logicalloc}","MUMBAILOG1")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
		
		/*
		 * when(service.getAllGlTxnHdrs(Mockito.any())).thenReturn(null);
		 * mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/view-all/{logicalloc}",
		 * "MUMBAILOG1") .contentType(MediaType.APPLICATION_STREAM_JSON))
		 * .andExpect(status().isBadRequest());
		 * 
		 * when(service.getAllGlTxnHdrs(Mockito.any())).thenReturn(null);
		 * mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/view-all/{logicalloc}",
		 * "MUMBAILOG1") .contentType(MediaType.APPLICATION_STREAM_JSON))
		 * .andExpect(status().isBadRequest());
		 */
		}
	
	@Test 
	public void getAllGltxnByTxnType() throws Exception{
		List<GlTxnHdr> list = new ArrayList<>();
		list.add(glTxnHdr);;
		when(service.getAllGlTxnHdrs(Mockito.any(),Mockito.any())).thenReturn(list);		
		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/view-all/{logicalloc}/{gltxntype}","MUMBAILOG1","AG")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
		
//		when(service.getAllGlTxnHdrs(Mockito.any(),Mockito.any())).thenReturn(null);		
//		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn//view-all/{logicalloc}","MUMBAILOG1")
//				.contentType(MediaType.APPLICATION_STREAM_JSON))
//				.andExpect(status().isBadRequest());
//		
//		when(service.getAllGlTxnHdrs(Mockito.any(),Mockito.any())).thenReturn(null);		
//		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn//view-all/{logicalloc}","MUMBAILOG1")
//				.contentType(MediaType.APPLICATION_STREAM_JSON))
//				.andExpect(status().isBadRequest());
		}
	@Test 
	public void getGlTxnTest() throws Exception{
		when(service.getGlTxn(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(glTxnHdr);		
		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/view/{gltxnno}/{logicalloc}/{gltxntype}","2018000001","MUMBAILOG1","AG")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
		.andExpect(status().isOk());
		
		/*
		 * when(service.getGlTxn(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(
		 * null); mockMvc.perform(MockMvcRequestBuilders.get(
		 * "/gl-txn/view/{gltxnno}/{logicalloc}/{gltxntype}","2018000001","MUMBAILOG1",
		 * "AG") .contentType(MediaType.APPLICATION_STREAM_JSON))
		 * .andExpect(status().isBadRequest());
		 */
		
	}
	
	@Test
	public void getAllGltxnByFromDtLocTest() throws Exception{
		List<GlTxnHdr> list = new ArrayList<>();
		list.add(glTxnHdr);
		when(service.getAllGltxnByFromDtLoc(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(list);		
		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/get-txn/{fromDt}/{toDt}/{logicallocation}","2021-07-01","2021-07-02","MUMBAILOG1")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
		
//		when(service.getGlTxn(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(null);		
//		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/get-txn/{fromDt}/{toDt}/{logicallocation}","2021-07-01","2021-07-02","MUMBAILOG1")
//				.contentType(MediaType.APPLICATION_STREAM_JSON))
//				.andExpect(status().isBadRequest());
//		
	}
	
	@Test
	public void getAllGlTxnByTxnNoTxnTypeLocTest() throws Exception{
		List<GlTxnHdr> list = new ArrayList<>();
		list.add(glTxnHdr);
		when(service.getAllGlTxnByTxnNoTxnTypeLoc(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/viewby/{glTxnNo}/{glTxnType}/{logicalLoc}",2018000001,"AG","MUMBAILOG1")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
		
//		when(service.getAllGlTxnByTxnNoTxnTypeLoc(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);
//		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/viewby/{glTxnNo}/{glTxnType}/{logicalLoc}",2018000001,"AG","MUMBAILOG1")
//				.contentType(MediaType.APPLICATION_STREAM_JSON))
//				.andExpect(status().isBadRequest());

	}
	
	@Test
	public void getAllGltxnByFromDtTest() throws Exception
	{
		List<GlTxnHdr> list = new ArrayList<>();
		list.add(glTxnHdr);
		when(service.getAllGlTxnByFromDtToDt(Mockito.any(), Mockito.any())).thenReturn(list);
		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/get-txn/{fromDt}/{toDt}","2021-07-01","2022-07-02")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getGltxnTest() throws Exception
	{
		when(service.getGlTxn(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(glTxnHdr);
		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/view/{gltxnno}/{logicalloc}/{gltxntype}",2021000001,"MUMBAILOG1","AG")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void updateGLTxnDtlTest() throws Exception
	{
		String str = mapper.writeValueAsString(glTxnHdr);
		when(service.updateGlTxnDtl(Mockito.any())).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/gl-txn/update")
		    	.content(str)
		    	.contentType(MediaType.APPLICATION_STREAM_JSON))
	       .andExpect(status().isCreated())
	       .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void reverseTxnTest() throws Exception
	{
		String str = mapper.writeValueAsString(glTxnHdr);
		when(service.reverseTransaction(Mockito.any())).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/gl-txn/reverse")
				.content(str)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
	
	@Test
	public void getbalanceTest() throws Exception
	{
		when(service.getTotalAmt(Mockito.any(), Mockito.any())).thenReturn(new BigDecimal("124567890.0987654321"));
		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/getbalance/{mainGlCd}/{subGlCd}","3800","001")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getCrbalanceTest() throws Exception
	{
		when(service.getTotalCreditAmt(Mockito.any(), Mockito.any())).thenReturn(new BigDecimal("124567890.0987654321"));
		mockMvc.perform(MockMvcRequestBuilders.get("/gl-txn/getcrbalance/{mainGlCd}/{subGlCd}","3800","001")
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getBrBalanceTest() throws Exception
	{
		GlBrBalanceIn balanceIn = new GlBrBalanceIn();
		GlBrBalanceOut balanceOut = new GlBrBalanceOut();
		String str = mapper.writeValueAsString(balanceIn);
		
		when(service.getBrBalance(balanceIn)).thenReturn(balanceOut);
		mockMvc.perform(MockMvcRequestBuilders.post("/gl-txn/getBrBalance")
				.content(str)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());
	}
}

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

import in.ecgc.smile.erp.accounts.model.PettyCashMaster;
import in.ecgc.smile.erp.accounts.service.PettyCashMasterService;

public class PettyCashMasterControllerTest {

	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Mock
	private PettyCashMasterService pettyCashMasterService;

	@Mock
	PettyCashMaster pettyCashMaster;

	@InjectMocks
	PettyCashMasterController pettyCashMasterController;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(pettyCashMasterController).build();
		mapper = new ObjectMapper();
	}

	@BeforeTest
	public void initPaymentAdvice() {

		pettyCashMaster = new PettyCashMaster();
		pettyCashMaster.setLogicalLocCode("MUMBAILOG1");
		pettyCashMaster.setProcessIsntanceId((long) 5989);
		pettyCashMaster.setRemark("aaaa");
		pettyCashMaster.setCashAmt(1111.00);
		pettyCashMaster.setEntityCd("ECGC");
		pettyCashMaster.setFiscalYr("2021-2022");
		// pettyCashMaster.setRequestDt(LocalDate.now());
		pettyCashMaster.setRequisitionNo(1234);
		pettyCashMaster.setCreatedBy("1229");
		// pettyCashMaster.setCreatedDt(LocalDate.now());
		pettyCashMaster.setReqStatus("Requested");
		pettyCashMaster.setLastUpdatedBy("1229");
		// pettyCashMaster.setLastUpdatedDt(LocalDate.now());
	}

	@Test
	public void addPettyCashDetailsTest() throws Exception {

		String pettyCashMasterstr = mapper.writeValueAsString(pettyCashMaster);

		when(pettyCashMasterService.addPettyCashDetails(pettyCashMaster)).thenReturn(new Integer(1));

		mockMvc.perform(MockMvcRequestBuilders.post("/petty-cash/add").content(pettyCashMasterstr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(pettyCashMasterService.addPettyCashDetails(pettyCashMaster)).thenReturn(new Integer(0));

		mockMvc.perform(MockMvcRequestBuilders.post("/petty-cash/add").content(pettyCashMasterstr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void approveMultipleRequestTest() throws Exception {
		List<PettyCashMaster> pettyCashMasters = new ArrayList<PettyCashMaster>();
		pettyCashMasters.add(pettyCashMaster);

		String pettyCashMastersstr = mapper.writeValueAsString(pettyCashMasters);

		when(pettyCashMasterService.approveMultipleRequest(pettyCashMasters)).thenReturn(new Integer(1));
		mockMvc.perform(MockMvcRequestBuilders.post("/petty-cash/mul-approve").content(pettyCashMastersstr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isAccepted())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(pettyCashMasterService.approveMultipleRequest(pettyCashMasters)).thenReturn(new Integer(0));
		mockMvc.perform(MockMvcRequestBuilders.post("/petty-cash/mul-approve").content(pettyCashMastersstr)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void approvedStatusTest() throws Exception {

		PettyCashMaster pettyobj = pettyCashMaster;

		when(pettyCashMasterService.findByRequisitionNo(pettyCashMaster.getRequisitionNo(),
				pettyCashMaster.getLogicalLocCode())).thenReturn(pettyCashMaster);

		when(pettyCashMasterService.approvedStatus(pettyCashMaster)).thenReturn(new Integer(1));

		mockMvc.perform(
				MockMvcRequestBuilders.put("/petty-cash/approve/{requisitionNo}/{logicalLocCode}", 1234, "MUMBAILOG1")
						.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		pettyobj.setReqStatus("Approved");

		when(pettyCashMasterService.findByRequisitionNo(pettyobj.getRequisitionNo(), pettyobj.getLogicalLocCode()))
				.thenReturn(pettyobj);

		when(pettyCashMasterService.approvedStatus(pettyobj)).thenReturn(new Integer(0));

		mockMvc.perform(
				MockMvcRequestBuilders.put("/petty-cash/approve/{requisitionNo}/{logicalLocCode}", 1234, "MUMBAILOG1")
						.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void findByRequisitionNoTest() throws Exception {

		when(pettyCashMasterService.findByRequisitionNo(1234, "MUMBAILOG1")).thenReturn(pettyCashMaster);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/petty-cash/view/{requisitionNo}/{logicalLocCode}", 1234, "MUMBAILOG1")
						.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(pettyCashMasterService.findByRequisitionNo(1234, "MUMBAILOG2")).thenReturn(null);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/petty-cash/view/{requisitionNo}/{logicalLocCode}", 1234, "MUMBAILOG2")
						.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isNoContent());

	}

	@Test
	public void getProcessInstanceIdTest() throws Exception {

		Integer reqInt = 1234;
		Long pid = (long) 5989;

		when(pettyCashMasterService.getProcessInstanceId(reqInt, "MUMBAILOG1")).thenReturn(pid);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/petty-cash/get-pid/{requisitionNo}/{logicalLocCode}", reqInt, "MUMBAILOG1")
						.param("logicalLocCode", "MUMBAILOG1").param("requisitionNo", "1234")
						.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void listAllPettyCashDetailsTest() throws Exception {
		List<PettyCashMaster> pettyList = new ArrayList<>();
		pettyList.add(pettyCashMaster);

		when(pettyCashMasterService.listAllPettyCashMaster()).thenReturn(pettyList);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/petty-cash/view-all").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(pettyCashMasterService.listAllPettyCashMaster()).thenReturn(null);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/petty-cash/view-all").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isNotFound());

	}

	@Test
	public void updatePettyCashTest() throws Exception {
		String pettyCashMasterstr = mapper.writeValueAsString(pettyCashMaster);
		Integer reqInt = 1234;

		when(pettyCashMasterService.updatePettyCash(reqInt, "MUMBAILOG1", pettyCashMaster)).thenReturn(new Integer(1));

		mockMvc.perform(
				MockMvcRequestBuilders.put("/petty-cash/update/{requisitionNo}/{logicalLocCode}", reqInt, "MUMBAILOG1")
						.content(pettyCashMasterstr).contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void updateProcessInstanceIdTest() throws Exception {
		Integer reqInt = 1234;
		Long pid = (long) 5989;

		when(pettyCashMasterService.updateProcessInstanceId(reqInt, "MUMBAILOG1", pid)).thenReturn(new Integer(1));
		mockMvc.perform(MockMvcRequestBuilders
				.put("/petty-cash/update-pid/{requisitionNo}/{logicalLocCode}/{pid}", reqInt, "MUMBAILOG1", pid)
				.param("logicalLocCode", "MUMBAILOG1").param("requisitionNo", "1234").param("pid", "5989")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}
}

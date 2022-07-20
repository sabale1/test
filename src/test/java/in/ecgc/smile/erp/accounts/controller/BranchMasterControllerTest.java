
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

import in.ecgc.smile.erp.accounts.model.BranchMaster;
import in.ecgc.smile.erp.accounts.service.BranchMasterSeviceImpl;

public class BranchMasterControllerTest {

	private ObjectMapper mapper;

	private MockMvc mockMvc;

	BranchMaster branchMaster;

	@Mock
	BranchMasterSeviceImpl branchMasterService;

	@InjectMocks
	BranchMasterController controller;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mapper = new ObjectMapper();
	}

	@BeforeTest
	public void initBranchMaster() {
		this.branchMaster = new BranchMaster();

		branchMaster.setLogicalLocCode("MUMBAILOG1");
		branchMaster.setBranchCode("MUMBAILOG1");
		branchMaster.setBranchName("Bandra West");
		branchMaster.setBankName("PNB");
		branchMaster.setBankBranchAddress("Mumbai");
		branchMaster.setGstinNumber("SBIJ1234567");
		branchMaster.setGstStateUt("SBIJ1234567");
		branchMaster.setGstStateUtCode("SBIJ1234567");
		branchMaster.setExpenseAccountNumber("122222000");
		branchMaster.setExpenseAccountsNeftCode("SBIJ1234567");
		branchMaster.setExpenseAccountIfscCode("SBIJ1234567");
		branchMaster.setClientId("123");
		branchMaster.setVirtualId("123");
		branchMaster.setActive(true);
		branchMaster.setCreatedBy("user");
		branchMaster.setLastUpdatedBy("user");
		branchMaster.setMetaStatus("metastatus");
		branchMaster.setMetaRemarks("metaRemarks");
	}

	@Test
	public void addBranchTest() throws Exception {
		String str = mapper.writeValueAsString(branchMaster);
		when(branchMasterService.addBranch(branchMaster)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/branch-master/add").content(str)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void listAllBranchesTest() throws Exception {
		List<BranchMaster> list = new ArrayList<>();
		list.add(branchMaster);
		when(branchMasterService.listAllBranches()).thenReturn(list);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/branch-master/view-all").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk());

		when(branchMasterService.listAllBranches()).thenReturn(null);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/branch-master/view-all").contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	public void updateBranchTest() throws Exception {
		String str = mapper.writeValueAsString(branchMaster);
		when(branchMasterService.updateBranch(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders
				.put("/branch-master/update/{logicalLocCode}/{branchCode}", "MUMBAILOG1", "MUMBAILOG1").content(str)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void disableBranchTest() throws Exception {
		String str = mapper.writeValueAsString(branchMaster);
		when(branchMasterService.disableBranch("MUMBAILOG1", "MUMBAILOG1")).thenReturn(1);
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/branch-master/delete/{logicalLocCode}/{branchCode}", "MUMBAILOG1", "MUMBAILOG1").content(str)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isAccepted())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}
}

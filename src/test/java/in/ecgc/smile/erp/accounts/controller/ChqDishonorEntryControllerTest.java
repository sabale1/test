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

import in.ecgc.smile.erp.accounts.model.ChqDishonorEntry;
import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.service.ChqDishonorEntryServiceImpl;

public class ChqDishonorEntryControllerTest {

	private ObjectMapper mapper;
	private MockMvc mockMvc;

	@Mock
	private ChqDishonorEntry chqDishonorEntry;

	@Mock
	private Receipt rec;

	@Mock
	private ChqDishonorEntryServiceImpl chqDishonorEntryServiceImpl;
	@InjectMocks
	ChqDishonorEntryController chqDishonorEntryController;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(chqDishonorEntryController).build();
		mapper = new ObjectMapper();
	}

	@BeforeTest
	private void initLovMaster() {
		chqDishonorEntry = new ChqDishonorEntry();
		// chqDishonorEntry.setDishonorDt(LocalDate.parse("2021-12-23"));
		chqDishonorEntry.setDishonorReason("Test");
		chqDishonorEntry.setFiscalYr("2021-22");
		chqDishonorEntry.setGlTxnNumber(2021000002);
		chqDishonorEntry.setInstrumentNumber("123456789");
		chqDishonorEntry.setInstrumentType("DD");
		chqDishonorEntry.setLogicalLocCode("PUNELOG1");
		chqDishonorEntry.setOldReceiptNumber(202100002);
		chqDishonorEntry.setReceiptNumber(202100002);

		rec = new Receipt();
		rec.setLogicalLocCode("PUNELOG1");
		rec.setReceiptNumber(202100002);
		rec.setIwdNumber("123");
		rec.setRemarks("TST");
		rec.setStampAmount(100.00);
		rec.setInstrumentType("DD");
		rec.setInstrumentNumber("123456789");
		rec.setReceivedfromType("EMP");
		rec.setReceiptAmount(1000.00);
	}

	@Test
	public void listChqDishonorEntry() throws Exception {
		ArrayList<ChqDishonorEntry> chqList = new ArrayList<ChqDishonorEntry>();
		chqList.add(chqDishonorEntry);
		when(chqDishonorEntryServiceImpl.getChqDishonorEntryList()).thenReturn(chqList);
		mockMvc.perform(MockMvcRequestBuilders.get("/chq-dishonor-entry/view-all")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("Application/json"));

	}

	@Test
	public void updateChqDishonorEntryDataTest() throws Exception {
		String chqString = mapper.writeValueAsString(chqDishonorEntry);
		when(chqDishonorEntryServiceImpl.updateChqDishonorEntryData(chqDishonorEntry)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/chq-dishonor-entry/update").content(chqString)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(chqDishonorEntryServiceImpl.updateChqDishonorEntryData(chqDishonorEntry)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.post("/chq-dishonor-entry/update").content(chqString)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void addChqDishonorEntryDataTest() throws Exception {
		String chqString = mapper.writeValueAsString(chqDishonorEntry);
		when(chqDishonorEntryServiceImpl.addChqDishonorEntryData(chqDishonorEntry)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/chq-dishonor-entry/add").content(chqString)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(chqDishonorEntryServiceImpl.addChqDishonorEntryData(chqDishonorEntry)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.post("/chq-dishonor-entry/add").content(chqString)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void getChqDishonorEntryDataByChequeNoTest() throws Exception {
		String chqString = mapper.writeValueAsString(chqDishonorEntry);
		when(chqDishonorEntryServiceImpl.getChqDishonorEntryByChequeNo("1104400110")).thenReturn(chqDishonorEntry);
		mockMvc.perform(MockMvcRequestBuilders.get("/chq-dishonor-entry/view/{instrumentNo}", "1104400110")
				.param("instrumentNo", "1104400110").content(chqString).contentType(MediaType.APPLICATION_STREAM_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void viewByLogicalLocCodeAndReceiptNoTest() throws Exception {
		LocalDate localDate = LocalDate.parse("2021-01-01");

		when(chqDishonorEntryServiceImpl.viewByChqNoChqTypeChqDtRcptNo("DD", "123456789", localDate, 202100002))
				.thenReturn(rec);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/chq-dishonor-entry/view/{instrumentType}/{instrumentNumber}/{instrumentDate}/{receiptNumber}",
						"DD", "123456789", localDate, 202100002)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void viewByInstrumentTypeTest() throws Exception {
		List<Receipt> recList = new ArrayList<>();
		recList.add(rec);

		when(chqDishonorEntryServiceImpl.viewByInstrumentType("DD")).thenReturn(recList);
		mockMvc.perform(MockMvcRequestBuilders.get("/chq-dishonor-entry/viewInstType/{instrumentType}", "DD")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(chqDishonorEntryServiceImpl.viewByInstrumentType("DD")).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/chq-dishonor-entry/viewInstType/{instrumentType}", "DD")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isNoContent());

	}

}

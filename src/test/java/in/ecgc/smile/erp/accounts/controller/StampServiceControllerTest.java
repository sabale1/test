package in.ecgc.smile.erp.accounts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

import in.ecgc.smile.erp.accounts.exception.StampIncompleteDataException;
import in.ecgc.smile.erp.accounts.model.StampParameterModel;
import in.ecgc.smile.erp.accounts.service.StampServiceImpl;

public class StampServiceControllerTest {
	private ObjectMapper mapper;
	private MockMvc mockMvc;

	@Mock
	StampParameterModel stampModel;

	@Mock
	StampServiceImpl stampService;

	@InjectMocks
	StampServiceController stampController;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(stampController).build();
		mapper = new ObjectMapper();
	}

	@BeforeTest
	public void initStampParameter() {
		stampModel = new StampParameterModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date effectiveDate = new Date();

		try {
			effectiveDate = sdf.parse("2018-04-01");

		} catch (Exception e) {
			System.out.println(e);
		}

		LocalDate localDate = LocalDate.parse("2019-05-08");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ldate = LocalDate.parse(formatter.format(localDate));

		stampModel.setDescription("abc");
		stampModel.setFromAmount(2000.0);
		stampModel.setSrNo(1);
		stampModel.setStampAmount(1000.0);
		stampModel.setToAmount(3000.0);
		stampModel.setEffectiveDate(LocalDate.of(2020, 01, 03));
	}

	@Test(enabled = false)
	public void addTest() throws Exception {

		String stamString = mapper.writeValueAsString(stampModel);
		when(stampService.addStampParameter(stampModel)).thenReturn(new Integer(1));
		mockMvc.perform(MockMvcRequestBuilders.post("/stamp-parameter/add").content(stamString)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

		when(stampService.addStampParameter(stampModel)).thenReturn(new Integer(0));
		mockMvc.perform(MockMvcRequestBuilders.post("/stamp-parameter/add").content(stamString)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isCreated());

		when(stampService.addStampParameter(stampModel)).thenThrow(StampIncompleteDataException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/stamp-parameter/add")
				.content(stamString)
				.contentType(MediaType.APPLICATION_STREAM_JSON))
		.andExpect(status().isCreated());
	}

	@Test(enabled = false)
	public void updateStampParameterTest() throws Exception {
		String stamString = mapper.writeValueAsString(stampModel);

		when(stampService.updateStampParameter(new Integer(13), stampModel)).thenReturn(stampModel);
		mockMvc.perform(MockMvcRequestBuilders.post("/stamp-parameter/update/{stampCode}", 13).content(stamString)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));
	}

	@Test
	public void allStampParameterTest() throws Exception {
		List<StampParameterModel> stampList = new ArrayList<>();
		stampList.add(stampModel);
		when(stampService.allStampParameter()).thenReturn(stampList);
		mockMvc.perform(MockMvcRequestBuilders.get("/stamp-parameter/viewStampParameter")
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"));

	}

	@Test
	public void viewByStampCodeTest() throws Exception {

		when(stampService.viewByStampCode(1)).thenReturn(stampModel);
		mockMvc.perform(MockMvcRequestBuilders.get("/stamp-parameter/view/{stampCode}", 1)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk());
	}

	@Test
	public void getStampAmtByFromAndToAmtTest() throws Exception {

		when(stampService.getStampAmtByFromAndToAmt(100.00)).thenReturn(new Double(1));
		mockMvc.perform(MockMvcRequestBuilders.get("/stamp-parameter/view-stamp-amt/{receiptAmount}", 1)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk());
	}

	@Test
	public void checkFromAmtTOToAmtTest() throws Exception {
		Double fr = (double) 100;
		Double to = (double) 2000;

		when(stampService.checkFromAmtTOToAmt(fr, to)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.get("/stamp-parameter/viewstamp/{fromAmount}/{toAmount}", fr, to)
				.contentType(MediaType.APPLICATION_STREAM_JSON)).andExpect(status().isOk());
	}

}

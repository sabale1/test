package in.ecgc.smile.erp.accounts.repository;

import static org.mockito.Mockito.when;

import java.util.List;

import javax.sql.DataSource;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.ScheduleCdGlMapping;

public class ScheduleCdGlMappingBeDaoImplTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Mock
	ScheduleCdGlMappingDaoImpl scheduleCdGlMappingDaoImpl;
	@Mock
	DataSource dataSource;
	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Mock
	ScheduleCdGlMapping scheduleCdGlMapping;
	@Mock
	List<ScheduleCdGlMapping> scheduleCdGlMappingList;

	@BeforeTest
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
		MockMvcBuilders.standaloneSetup(scheduleCdGlMappingDaoImpl).build();
	}

	@BeforeTest
	public void initObjects() {
		// "initialize Your Object Here";
	}

	@Test
	public void addScheduleCdGlMappingDataTest() throws Exception {
		logger.info("Inside ScheduleCdGlMappingBeDaoImplTest - addScheduleCdGlMappingDataTest");
		when(namedParameterJdbcTemplate.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class),
				Mockito.any())).thenReturn(1);
		boolean response = scheduleCdGlMappingDaoImpl.addScheduleCdGlMappingData(scheduleCdGlMapping);
		AssertJUnit.assertEquals(response, false);
	}

	@Test
	public void updateScheduleCdGlMappingDataTest() throws Exception {
		logger.info("Inside ScheduleCdGlMappingBeDaoImplTest - updateScheduleCdGlMappingDataTest");
		when(namedParameterJdbcTemplate.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class),
				Mockito.any())).thenReturn(1);
		boolean response = scheduleCdGlMappingDaoImpl.updateScheduleCdGlMappingData(scheduleCdGlMapping);
		AssertJUnit.assertEquals(response, false);
	}

	@Test
	public void getScheduleCdGlMappingListTest() throws Exception {
		logger.info("Inside DaoImplTest - getScheduleCdGlMappingListTest");
		when(namedParameterJdbcTemplate.query(Mockito.anyString(), Mockito.any(ScheduleCdGlMappingMapper.class)))
				.thenReturn(scheduleCdGlMappingList);
		List<ScheduleCdGlMapping> response = scheduleCdGlMappingDaoImpl.getScheduleCdGlMappingList();
		AssertJUnit.assertEquals(scheduleCdGlMappingList.size(), response.size());
	}

	//@Test
	public void getScheduleCdGlMappingDataByIdTest() throws Exception {
		logger.info("Inside DaoImplTest - getScheduleCdGlMappingDataByIdTest");
		int seqNo = 11;
		when(namedParameterJdbcTemplate.query(Mockito.anyString(),
				Mockito.any(ScheduleCdGlMappingResultSetExtractor.class))).thenReturn(scheduleCdGlMapping);
		ScheduleCdGlMapping response = scheduleCdGlMappingDaoImpl.getScheduleCdGlMappingDataById(seqNo);
		AssertJUnit.assertEquals(response, scheduleCdGlMapping);
	}
}

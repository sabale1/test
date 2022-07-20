package in.ecgc.smile.erp.accounts.repository;

import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.exception.ReadEntityGLFailException;
import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.service.EntityGLMasterServiceImpl;
import in.ecgc.smile.erp.accounts.util.EntityGLSqlQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;

public class EntityGLMasterDaoImplTest {
	
	private MockMvc mockMvc;
	@Mock
	private DataSource dataSource;
	@Mock
	private JdbcOperations jdbcOperations;
	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcOperations;	
	
	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;	
	
	@Mock
	EntityGL entityGL;
	
	@InjectMocks
	EntityGLMasterDaoImpl entityGLMasterDaoImpl;
	
	@Mock
	List<EntityGL> entityGlList;
	
	@Mock
	List<String> list;
	
	@Mock
	private UserInfoService userInfoService;
	
	
	String levelCode;
	
	@BeforeTest
	public void init()
	{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(entityGLMasterDaoImpl).build();
	}
	
	@BeforeTest
	public void initEntityGlCode()
	{
		entityGL = new EntityGL();
		entityGL.setActive('Y');
		entityGL.setBalInd("test");
		entityGL.setCashaccount(1100);
		entityGL.setCashFlow(1000);
		entityGL.setCreatedBy("101");
		entityGL.setCreatedDt(LocalDate.parse("2021-09-04"));
		entityGL.setEntityGlCd("1700");
		entityGL.setFinancialFormName("Y");
		entityGL.setGlIsGroup('n');
		
		entityGL.setGlName("abc");
		entityGL.setZeroBalFlg('y');
		entityGL.setSubglCd("028");
		entityGL.setLogicalLocCd("MUMBAILOG1");
		entityGL.setMainglCd("1700");
		entityGL.setGlSubtype("PV");
		entityGL.setIrdaBpaCd("N");
		entityGL.setIrdaCd("N");
		entityGL.setOldCd("1800");
		entityGL.setPlLevel("test");
		entityGL.setSchedule6("y");
		entityGL.setSubBifurcationLevel("test");
		entityGL.setT1('N');
		entityGL.setT2('N');
		entityGL.setT3('N');
		entityGL.setT4('N');
		entityGL.setT5('N');
		entityGL.setT6('N');
		entityGL.setT7('N');
		entityGL.setT8('N');
		entityGL.setT9('N');
		entityGL.setT10('N');
		entityGL.setT11('N');
		entityGL.setT12('N');
		
		entityGlList = new ArrayList<>();
		entityGlList.add(entityGL);
	}

	@Test
	public void addGLCodeTest()
	{
		Map<String, Object> GLnamedParameters = new HashMap<String, Object>();
		GLnamedParameters.put("entityGLCd", entityGL.getEntityGlCd());
		GLnamedParameters.put("mainglCd", entityGL.getMainglCd());
		GLnamedParameters.put("subglCd", entityGL.getSubglCd());
		GLnamedParameters.put("glName", entityGL.getGlName());
		GLnamedParameters.put("glIsGroup",entityGL.getGlIsGroup());
		GLnamedParameters.put("glType", entityGL.getGlType());
		GLnamedParameters.put("glSubtype", entityGL.getGlSubtype());
		GLnamedParameters.put("balInd", entityGL.getBalInd());
		GLnamedParameters.put("zeroBalFlg",entityGL.getZeroBalFlg());
		GLnamedParameters.put("active",entityGL.getActive());
		GLnamedParameters.put("personalLedgerLevel", entityGL.getPlLevel());
		GLnamedParameters.put("cashaccount", entityGL.getCashaccount());
		GLnamedParameters.put("cashFlow", entityGL.getCashFlow());
		GLnamedParameters.put("logicalLocCd", entityGL.getLogicalLocCd());
		GLnamedParameters.put("plLevel", entityGL.getPlLevel());
		GLnamedParameters.put("metaRemarks", entityGL.getMetaRemarks());
		GLnamedParameters.put("t1", entityGL.getT1());
		GLnamedParameters.put("t2", entityGL.getT2());
		GLnamedParameters.put("t3", entityGL.getT3());
		GLnamedParameters.put("t4", entityGL.getT4());
		GLnamedParameters.put("t5", entityGL.getT5());
		GLnamedParameters.put("t6", entityGL.getT6());
		GLnamedParameters.put("t7", entityGL.getT7());
		GLnamedParameters.put("t8", entityGL.getT8());
		GLnamedParameters.put("t9", entityGL.getT9());
		GLnamedParameters.put("t10", entityGL.getT10());
		GLnamedParameters.put("t11", entityGL.getT11());
		GLnamedParameters.put("t12", entityGL.getT12());
		GLnamedParameters.put("t13", entityGL.getT13());
		GLnamedParameters.put("t14", entityGL.getT14());
		GLnamedParameters.put("irdaCd", entityGL.getIrdaCd());
		GLnamedParameters.put("irdaBapCd", entityGL.getIrdaBpaCd());
		GLnamedParameters.put("schedule6", entityGL.getSchedule6());
		GLnamedParameters.put("finFormName", entityGL.getFinancialFormName());
		GLnamedParameters.put("oldCd", entityGL.getOldCd());
		GLnamedParameters.put("subBifurcationLevel", entityGL.getSubBifurcationLevel());
		GLnamedParameters.put("createdBy", userInfoService.getUser());
		GLnamedParameters.put("lastUpdatedBy", userInfoService.getUser());
		
		when(namedParameterJdbcTemplate.update(EntityGLSqlQueries.ADD_GL_CODE, GLnamedParameters)).thenReturn(1);
	}
	
	@Test
	public void listAllGLCodesTest()
	{
		List<EntityGL> entityGLCodes = new ArrayList<EntityGL>();
		
		RowMapper<EntityGL> rowMapper = EntityGLSqlQueries::mapRow;
		when(namedParameterJdbcTemplate.query(EntityGLSqlQueries.ALL_GL_CODES,rowMapper)).thenReturn(entityGLCodes);
		
	}
	
	@Test
	public void findGLByGLCodeTest()
	{
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("mainGLCode", entityGL.getMainglCd());
		paramMap.put("subGLCode", entityGL.getSubglCd());
		RowMapper<EntityGL> rowMapper = EntityGLSqlQueries::mapRow;
		when(namedParameterJdbcTemplate.queryForObject(EntityGLSqlQueries.LOAD_GLCODE, paramMap,rowMapper)).thenReturn(entityGL);
	}
	
	@Test
	public void updateGLCodeTest()
	{
		Map<String, Object> GLnamedParameters = new HashMap<String, Object>();
		
		GLnamedParameters.put("entityGLCd", entityGL.getEntityGlCd());
		GLnamedParameters.put("mainglCd", entityGL.getMainglCd());
		GLnamedParameters.put("subglCd", entityGL.getSubglCd());
		GLnamedParameters.put("glName", entityGL.getGlName());
		GLnamedParameters.put("glIsGroup",entityGL.getGlIsGroup());
		GLnamedParameters.put("glType", entityGL.getGlType());
		GLnamedParameters.put("glSubtype", entityGL.getGlSubtype());
		GLnamedParameters.put("balInd", entityGL.getBalInd());
		GLnamedParameters.put("zeroBalFlg",entityGL.getZeroBalFlg());
		GLnamedParameters.put("active",entityGL.getActive());
		GLnamedParameters.put("personalLedgerLevel", entityGL.getPlLevel());
		GLnamedParameters.put("cashaccount", entityGL.getCashaccount());
		GLnamedParameters.put("cashFlow", entityGL.getCashFlow());
		GLnamedParameters.put("logicalLocCd", entityGL.getLogicalLocCd());
		GLnamedParameters.put("plLevel", entityGL.getPlLevel());
		GLnamedParameters.put("metaRemarks", entityGL.getMetaRemarks());
		GLnamedParameters.put("t1", entityGL.getT1());
		GLnamedParameters.put("t2", entityGL.getT2());
		GLnamedParameters.put("t3", entityGL.getT3());
		GLnamedParameters.put("t4", entityGL.getT4());
		GLnamedParameters.put("t5", entityGL.getT5());
		GLnamedParameters.put("t6", entityGL.getT6());
		GLnamedParameters.put("t7", entityGL.getT7());
		GLnamedParameters.put("t8", entityGL.getT8());
		GLnamedParameters.put("t9", entityGL.getT9());
		GLnamedParameters.put("t10", entityGL.getT10());
		GLnamedParameters.put("t11", entityGL.getT11());
		GLnamedParameters.put("t12", entityGL.getT12());
		GLnamedParameters.put("t13", entityGL.getT13());
		GLnamedParameters.put("t14", entityGL.getT14());
		GLnamedParameters.put("irdaCd", entityGL.getIrdaCd());
		GLnamedParameters.put("irdaBapCd", entityGL.getIrdaBpaCd());
		GLnamedParameters.put("schedule6", entityGL.getSchedule6());
		GLnamedParameters.put("finFormName", entityGL.getFinancialFormName());
		GLnamedParameters.put("oldCd", entityGL.getOldCd());
		GLnamedParameters.put("subBifurcationLevel", entityGL.getSubBifurcationLevel());
		GLnamedParameters.put("createdBy", entityGL.getCreatedBy());
		GLnamedParameters.put("lastUpdatedBy", entityGL.getLastUpdatedBy());
		when(namedParameterJdbcTemplate.update(EntityGLSqlQueries.UPDATE_GLCODE, GLnamedParameters)).thenReturn(1);
	}
	
	@Test
	public void disableGLCodeTest()
	{
		Map<String, Object> GLnamedParameters = new HashMap<String, Object>();
		
		GLnamedParameters.put("entityGLCd", entityGL.getEntityGlCd());
		GLnamedParameters.put("mainglCd", entityGL.getMainglCd());
		GLnamedParameters.put("subglCd", entityGL.getSubglCd());
		when(namedParameterJdbcTemplate.update(EntityGLSqlQueries.DISABLE_GLCODE, GLnamedParameters)).thenReturn(1);
	}
	
	@Test
	public void getRegularGLTypesByOpeningDayTest()
	{
		Map<String, Object> params = new HashMap<String, Object>();
		when(namedParameterJdbcOperations.queryForObject(EntityGLSqlQueries.GET_REGULAR_GL_COUNT,params,
					new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num =rs.getInt(1);
							return num;
						}
					})).thenReturn(1);
			
	}
	
	@Test
	public void getConfiguredGLTypesByOpeningDayTest()
	{
	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(null,null);
		when(namedParameterJdbcOperations.queryForObject(EntityGLSqlQueries.GET_CONFIGURED_GL_COUNT,params,
					new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num =rs.getInt(1);
							return num;
						}
					})).thenReturn(1);
			
	}
	
	@Test
	public void getsubGLCodebyMainGLCodeTest()
	{
		List<EntityGL> entityList = new ArrayList<>(); 
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("mainGLCode", entityGL.getMainglCd());
		RowMapper<EntityGL> rowMapper = EntityGLSqlQueries::mapRow;
		when(namedParameterJdbcTemplate.query(EntityGLSqlQueries.GET_ALL_BY_MAIN_GL_CODE,paramMap, rowMapper)).thenReturn(entityList);
		
	}
	
	@Test
	public void getAllGlByIsGlGroupTest()
	{
		List<EntityGL> entityGLCodes = new ArrayList<EntityGL>();
		RowMapper<EntityGL> rowMapper = EntityGLSqlQueries::mapRow;
		when(namedParameterJdbcTemplate.query(EntityGLSqlQueries.GET_ALL_GL_IS_GL_NAME,rowMapper)).thenReturn(entityGLCodes);
	
	}
	

}

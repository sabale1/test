package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.repository.EntityGLMasterDaoImpl;



public class EntityGLMasterServiceTest {
	
	MockMvc mockMvc ;
	
	@InjectMocks
	EntityGLMasterServiceImpl entityGLMasterService;
	
	@Mock
	EntityGL entityGl;
	
	@Mock
	EntityGLMasterDaoImpl entityGLMasterDaoImpl;
	
	@Mock
	List<EntityGL> entityGlList;
	
	@Mock
	List<String> list;
	
	
	String levelCode;
	
	@BeforeTest
	public void init()
	{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(entityGLMasterService).build();
	}
	
	@BeforeTest
	public void initEntityGlCode()
	{
		entityGl = new EntityGL();
		entityGl.setActive('Y');
		entityGl.setBalInd("test");
		entityGl.setCashaccount(1100);
		entityGl.setCashFlow(1000);
		entityGl.setCreatedBy("101");
		entityGl.setCreatedDt(LocalDate.parse("2021-09-04"));
		entityGl.setEntityGlCd("1700");
		entityGl.setFinancialFormName("Y");
		entityGl.setGlIsGroup('n');
		
		entityGl.setGlName("abc");
		entityGl.setZeroBalFlg('y');
		entityGl.setSubglCd("028");
		entityGl.setLogicalLocCd("MUMBAILOG1");
		entityGl.setMainglCd("1700");
		entityGl.setGlSubtype("PV");
		entityGl.setIrdaBpaCd("N");
		entityGl.setIrdaCd("N");
		entityGl.setOldCd("1800");
		entityGl.setPlLevel("test");
		entityGl.setSchedule6("y");
		entityGl.setSubBifurcationLevel("test");
		entityGl.setT1('N');
		entityGl.setT2('N');
		entityGl.setT3('N');
		entityGl.setT4('N');
		entityGl.setT5('N');
		entityGl.setT6('N');
		entityGl.setT7('N');
		entityGl.setT8('N');
		entityGl.setT9('N');
		entityGl.setT10('N');
		entityGl.setT11('N');
		entityGl.setT12('N');
		
		entityGlList = new ArrayList<>();
		entityGlList.add(entityGl);
	}
	
	@Test
	public void addGLCodeTest()
	{
		when(entityGLMasterDaoImpl.addGLCode(entityGl)).thenReturn(1);
		Assert.assertEquals(entityGLMasterService.addGLCode(entityGl),new Integer(1) );
		
	}

	@Test
	public void listAllGLCodesTest()
	{
		when(entityGLMasterDaoImpl.listAllGLCodes()).thenReturn(entityGlList);
		Assert.assertEquals(entityGLMasterService.listAllGLCodes(), entityGlList);
		
		when(entityGLMasterDaoImpl.listAllGLCodes()).thenReturn(null);
		Assert.assertEquals(entityGLMasterService.listAllGLCodes(), null);
		
	}

	@Test
	public void findGLByGLCodeTest()
	{
		when(entityGLMasterDaoImpl.findGLByGLCode("1700", "028")).thenReturn(entityGl);
		Assert.assertEquals(entityGLMasterService.findGLByGLCode("1700", "028"),entityGl );
		
	}

	@Test
	public void updateGLCodeTest()
	{
		when(entityGLMasterDaoImpl.updateGLCode(entityGl)).thenReturn(entityGl);
		Assert.assertEquals(entityGLMasterService.updateGLCode(entityGl, entityGl), entityGl);
		
	}

	@Test
	public void disableGLCodeTest()
	{
		when(entityGLMasterDaoImpl.disableGLCode(entityGl)).thenReturn(1);
		Assert.assertEquals(entityGLMasterService.disableGLCode(entityGl), new Integer(1));
		
		entityGl.setGlIsGroup('Y');
		Assert.assertEquals(entityGLMasterService.disableGLCode(entityGl), new Integer(-1));	
		
		entityGl.setGlIsGroup('N');
		when(entityGLMasterDaoImpl.disableGLCode(entityGl)).thenReturn(1);
		Assert.assertEquals(entityGLMasterService.disableGLCode(entityGl), new Integer(1));
		
	}

	@Test
	public void getRegularGLTypesByOpeningDayTest()
	{
		when(entityGLMasterDaoImpl.getRegularGLTypesByOpeningDay()).thenReturn(1);
		Assert.assertEquals(entityGLMasterService.getRegularGLTypesByOpeningDay(), new Integer(1));
		
	}

	@Test
	public void getConfiguredGLTypesByOpeningDayTest()
	{
		when(entityGLMasterDaoImpl.getConfiguredGLTypesByOpeningDay()).thenReturn(1);
		Assert.assertEquals(entityGLMasterService.getConfiguredGLTypesByOpeningDay(),new Integer(1) );
		
	}

	@Test
	public void getsubGLCodebyMainGLCodeTest()
	{
		when(entityGLMasterDaoImpl.getsubGLCodebyMainGLCode("1700")).thenReturn(entityGlList);
		Assert.assertEquals(entityGLMasterService.getsubGLCodebyMainGLCode("1700"),entityGlList );
		
	}
	
	
	@Test
	public void getAllGlByIsGlGroup()
	{
		when(entityGLMasterDaoImpl.getAllGlByIsGlGroup()).thenReturn(entityGlList);
		Assert.assertEquals(entityGLMasterService.getAllGlByIsGlGroup(),entityGlList );
		
	}

}

package in.ecgc.smile.erp.accounts.service;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.exception.GLTxnTypeAlreadyExistException;
import in.ecgc.smile.erp.accounts.exception.GLTxnTypeCodeNotFoundException;
import in.ecgc.smile.erp.accounts.model.GLTxnType;
import in.ecgc.smile.erp.accounts.repository.GLTxnTypeDao;

public class GLTxnTypeServiceImplTest {
	private MockMvc mockMvc;
	
	@InjectMocks
	GLTxnTypeServiceImpl service;
	
	@Mock
	GLTxnTypeDao dao;
	
	GLTxnType glTxnType;
	
	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(service).build();
	}
	@BeforeTest
	public void initializeObject() {
		glTxnType = new GLTxnType();
		glTxnType.setGlTxnType("PRV");
		glTxnType.setTxnTypeName("Provision");
		glTxnType.setActive(Boolean.TRUE);
		glTxnType.setIsConfigurable(Boolean.FALSE);
		glTxnType.setOpeningDay(3);
	}
	@Test
	public void listAllGLTxnTypeCodesTest() {
		List<GLTxnType> txnTypeList = new ArrayList<>();
		txnTypeList.add(glTxnType);
		when(dao.listAllGLTxnTypeCodes()).thenReturn(txnTypeList);
		Assert.assertEquals(service.listAllGLTxnTypeCodes(),txnTypeList);
		
	}
	@Test
	public void getGLTxnTypeTest() {
		List<String> txnTypeNameList = new ArrayList<>();
		txnTypeNameList.add("PRV");
		txnTypeNameList.add("JV");
		txnTypeNameList.add("AG");
		when(dao.getGLTxnType()).thenReturn(txnTypeNameList);
		Assert.assertEquals(service.getGLTxnType(), txnTypeNameList);
	}
	@Test
	public void disableGLTxnTypeCodeTest_whenPresentThenDisable() {
		when(dao.findGlTxnTypeByGlTxnTypeCode("PRV")).thenReturn(glTxnType);
		when(dao.disableGLTxnTypeCode("PRV")).thenReturn(Integer.valueOf(1));
		Assert.assertEquals(service.disableGLTxnTypeCode("PRV"),Integer.valueOf(1));
	}
	@Test(expectedExceptions = GLTxnTypeCodeNotFoundException.class)
	public void disableGLTxnTypeCodeTest_whenNotPresentThenException() {
		when(dao.findGlTxnTypeByGlTxnTypeCode("PRV")).thenReturn(null);
		service.disableGLTxnTypeCode("PRV");
	}
	@Test
	public void addGLTxnTypeCode_whenAlreadyNotPresentThenSuccess() {
		when(dao.findGlTxnTypeByGlTxnTypeCode("PRV")).thenReturn(null);
		when(dao.addGLTxnTypeCode(Mockito.any())).thenReturn(Integer.valueOf(1));
		Assert.assertEquals(service.addGLTxnTypeCode(glTxnType), Boolean.TRUE);
		
		//Following code added to test return false from service layer when dao layer does not return 1;
		when(dao.findGlTxnTypeByGlTxnTypeCode("PRV")).thenReturn(null);
		when(dao.addGLTxnTypeCode(Mockito.any())).thenReturn(Integer.valueOf(-1));
		Assert.assertEquals(service.addGLTxnTypeCode(glTxnType), Boolean.FALSE);
	}
	@Test(expectedExceptions = GLTxnTypeAlreadyExistException.class)
	public void addGLTxnTypeCode_whenAlreadyPresentThenException() {
		when(dao.findGlTxnTypeByGlTxnTypeCode("PRV")).thenReturn(glTxnType);
		service.addGLTxnTypeCode(glTxnType);
		
	}
	@Test
	public void findGlTxnTypeByGlTxnTypeCode_whenPresentThenSuccess() {
		when(dao.findGlTxnTypeByGlTxnTypeCode("PRV")).thenReturn(glTxnType);
		Assert.assertEquals(service.findGlTxnTypeByGlTxnTypeCode("PRV"), glTxnType);
	}
	@Test(expectedExceptions = GLTxnTypeCodeNotFoundException.class)
	public void findGlTxnTypeByGlTxnTypeCode_whenNotPresentThenException() {
		when(dao.findGlTxnTypeByGlTxnTypeCode("PRV")).thenReturn(null);
		service.findGlTxnTypeByGlTxnTypeCode("PRV");
	}
	@Test
	public void updateGLTxnTypeCode_whenPresentThenSuccess() {
		when(dao.findGlTxnTypeByGlTxnTypeCode("PRV")).thenReturn(glTxnType);
		when(dao.updateGLTxnTypeCode("PRV", glTxnType)).thenReturn(Integer.valueOf(1));
		assertEquals(service.updateGLTxnTypeCode("PRV", glTxnType), Integer.valueOf(1));
	}
	
	@Test(expectedExceptions = GLTxnTypeCodeNotFoundException.class)
	public void updateGLTxnTypeCode_whenPresentThenSuccess1() {
		when(dao.findGlTxnTypeByGlTxnTypeCode("PRV")).thenReturn(null);
		service.updateGLTxnTypeCode("PRV", glTxnType);
	}
}

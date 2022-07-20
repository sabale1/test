package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.Liability;
import in.ecgc.smile.erp.accounts.model.LiabilityGLMapping;
import in.ecgc.smile.erp.accounts.repository.GlTxnDaoImpl;
import in.ecgc.smile.erp.accounts.repository.LiablityDaoImpl;

public class LiabilityServiceImplTest {

	@Mock
	Liability liability;

	@Mock
	GlTxnHdr glTxnHdr;

	@Mock
	EntityGL entityGL;

	@Mock
	LiabilityGLMapping liabilityGLMapping;

	@Mock
	GlTxnDaoImpl gldao;

	@Mock
	GlTxnServiceImpl glservice;

	@Mock
	LiablityDaoImpl dao;

	@InjectMocks
	LiabilityServiceImpl service;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	public void initGlTxnHdr() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Date date = null;
		try {
			date = simpleDateFormat.parse("2021-01-01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.glTxnHdr = new GlTxnHdr();
		glTxnHdr.setEntityCd("ECGC");
		glTxnHdr.setGlTxnNo(2021000001);
		glTxnHdr.setLogicalLocCd("MUMBAILOG1");
		glTxnHdr.setTxnDt(LocalDate.of(2021, 01, 01));
		glTxnHdr.setReference("MUMBAILOG1");
		glTxnHdr.setRevarsalGlTxnType("AG");
		glTxnHdr.setRevarsalGlTxnNo(2021000001);
		glTxnHdr.setReversalReason("Test");
		glTxnHdr.setGlTxnType("AG");
		glTxnHdr.setFiscalYr("2021-2022");
		glTxnHdr.setMetaStatus("mstat");
		glTxnHdr.setMetaRemarks("mremarks");
		glTxnHdr.setCreatedBy("1229");
		glTxnHdr.setCreatedDt(date);
		glTxnHdr.setLastUpdatedBy("1229");
		glTxnHdr.setLastUpdatedDt(date);

		List<GlTxnDtl> list = new ArrayList<>();
//		GlTxnDtl dtl = new GlTxnDtl();
//		dtl.setGlTxnNo(2021000001);
//		dtl.setGlTxnType("AG");
//		dtl.setLogicalLocCd("MUMBAILOG1");
//		dtl.setSrNo(1);
//		dtl.setTxnAmt(120001.00);
//		dtl.setMainGlCd("1700");
//		dtl.setSubGlCd("001");
//		dtl.setDrCrFlag("cr");
//		list.add(dtl);

		GlTxnDtl dtl1 = new GlTxnDtl();
		dtl1.setGlTxnNo(2021000001);
		dtl1.setSrNo(1);
		dtl1.setDrCrFlag("dr");
		dtl1.setTxnAmt(120001.00);
		dtl1.setOldCode("MUMBAILOG1");
		dtl1.setCodeType("AG");
		dtl1.setEntityGlCd("ECGC");
		dtl1.setMainGlCd("1700");
		dtl1.setSubGlCd("001");
		dtl1.setGlTxnType("AG");
		dtl1.setLogicalLocCd("MUMBAILOG1");
		dtl1.setPlCd("1001");
		dtl1.setSubBiFurcationCd("none");
		dtl1.setRemarks("remark");
		dtl1.setT1("t1");
		dtl1.setT2("t2");
		dtl1.setT3("t3");
		dtl1.setT4("t4");
		dtl1.setT5("t5");
		dtl1.setT6("t6");
		dtl1.setT7("t7");
		dtl1.setT8("t8");
		dtl1.setT9("t9");
		dtl1.setT10("t10");
		dtl1.setT11("t11");
		dtl1.setT12("t12");
		dtl1.setT13("t13");
		dtl1.setT14("t14");
		dtl1.setT15("t15");
		dtl1.setT16("t16");
		dtl1.setT17("t17");
		dtl1.setT18("t18");
		dtl1.setT19("t19");
		dtl1.setT20("t20");
		dtl1.setT21("t21");
		dtl1.setT22("t22");
		dtl1.setT23("t23");
		dtl1.setT24("t24");
		dtl1.setT25("t25");
		dtl1.setT26("t26");
		dtl1.setT27("t27");
		dtl1.setT28("t28");
		dtl1.setT29("t29");
		dtl1.setT30("t30");
		dtl1.setT31("t31");
		dtl1.setT32("t32");
		dtl1.setT33("t33");
		
		dtl1.setAd1(LocalDate.of(2021, 01, 01));
		dtl1.setAd2(LocalDate.of(2021, 01, 01));
		dtl1.setAd3(LocalDate.of(2021, 01, 01));
		dtl1.setAd4(LocalDate.of(2021, 01, 01));
		dtl1.setCreatedBy("1229");
		dtl1.setCreatedDt(date);
		dtl1.setLastUpdatedBy("1229");
		dtl1.setLastUpdatedDt(date);
		dtl1.setMetaRemarks("mremarks");
		dtl1.setMetaStatus("mstat");

		list.add(dtl1);
		glTxnHdr.setGlTxnDtls(list);
	}

	@BeforeTest
	public void initLiabilityGLMapping() {
		this.liabilityGLMapping = new LiabilityGLMapping();
		liabilityGLMapping.setModuleCd("MKT");
		liabilityGLMapping.setMappingCd("ADVT_ADVANCE");
		liabilityGLMapping.setMappingName("Advance for Marketing Advertisement Expense");
		liabilityGLMapping.setSrNo(1);
		liabilityGLMapping.setMainGL("1700");
		liabilityGLMapping.setSubGL("001");
		liabilityGLMapping.setDrCrFlag("dr");
		liabilityGLMapping.setAmtCalc((float) 333.3);
		liabilityGLMapping.setRemarks("rfemarks");
		liabilityGLMapping.setSubBifurcation("AG");
		liabilityGLMapping.setCreatedBy("1229");
		liabilityGLMapping.setCreatedDt(LocalDate.of(2021, 01, 01));
		liabilityGLMapping.setLastUpdatedBy("1229");
		liabilityGLMapping.setLastUpdatedDt(LocalDate.of(2021, 01, 01));
		liabilityGLMapping.setMetaRemarks("mremarks");
		liabilityGLMapping.setMetaStatus("mstat");
		liabilityGLMapping.setEntityCd("ECGC");
		liabilityGLMapping.setTdsApplicable('N');
		liabilityGLMapping.setRcApplicable('N');
		liabilityGLMapping.setGstTdsApplicable('Y');
	}

	@BeforeTest
	public void initLiability() {
		this.liability = new Liability();
		liability.setModuleCd("MKT");
		liability.setMappingCd("ADVT_ADVANCE");
		liability.setBaseAmt(1000.00);
		liability.setLogicalLogCd("MUMBAILOG1");
		liability.setTxnDt(LocalDate.of(2021, 01, 01));
		liability.setFiscalYr("2021-2022");
		liability.setIsAmtInInr(true);
		liability.setCurrCode("$");
		liability.setCurrName("Dollar");
		liability.setExchangeRate((float) 33.12233);
		liability.setT1("t1");
		liability.setT2("t2");
		liability.setT3("t3");
		liability.setT4("t4");
		liability.setT5("t5");
		liability.setT6("t6");
		liability.setT7("t7");
		liability.setT8("t8");
		liability.setT9("t9");
		liability.setT10("t10");
		liability.setT11("t11");
		liability.setT12("t12");
		liability.setT13("t13");
		liability.setT14("t14");
		liability.setT15("t15");
		liability.setT16("t16");
		liability.setT17("t17");
		liability.setT18("t18");
		liability.setT19("t19");
		liability.setT20("t20");
		liability.setT21("t21");
		liability.setT22("t22");
		liability.setT23("t23");
		liability.setT24("t24");
		liability.setT25("t25");
		liability.setT26("t26");
		liability.setT27("t27");
		liability.setT28("t28");
		liability.setT29("t29");
		liability.setT30("t30");
		liability.setT31("t31");
		liability.setT32("t32");
		liability.setT33("t33");
		liability.setAd1(LocalDate.of(2021, 01, 01));
		liability.setAd2(LocalDate.of(2021, 01, 01));
		liability.setAd3(LocalDate.of(2021, 01, 01));
		liability.setAd4(LocalDate.of(2021, 01, 01));
		liability.setRemarks("rfemarks");
		liability.setUserId(1229);
	}

	@BeforeTest
	public void initEntityGl() {

		entityGL = new EntityGL();

		entityGL.setEntityGlCd("ECGC");
		entityGL.setActive('Y');
		entityGL.setBalInd("na");
		entityGL.setCashaccount(982348123);
		entityGL.setGlIsGroup('N');
		entityGL.setGlName("BANK");
		entityGL.setMainglCd("1700");
		entityGL.setOldCd("1001");
		entityGL.setSubglCd("001");
		entityGL.setGlSubtype("002");
		entityGL.setGlType("Asset");
		entityGL.setGlName("PV");
		entityGL.setIrdaBpaCd("test1");
		entityGL.setIrdaCd("test1");
		entityGL.setSchedule6("11123L");
		entityGL.setSubBifurcationLevel("ACCT");
		entityGL.setFinancialFormName("abc");
		entityGL.setT1('Y');
		entityGL.setT2('Y');
		entityGL.setT3('Y');
		entityGL.setT4('Y');
		entityGL.setT5('Y');
		entityGL.setT6('Y');
		entityGL.setT7('Y');
		entityGL.setT8('Y');
		entityGL.setT9('Y');
		entityGL.setT10('Y');
		entityGL.setT11('Y');
		entityGL.setT12('Y');
		entityGL.setT13('Y');
		entityGL.setT14('Y');
		entityGL.setT15('Y');
		entityGL.setT16('Y');
		entityGL.setT17('Y');
		entityGL.setT18('Y');
		entityGL.setT19('Y');
		entityGL.setT20('Y');
		entityGL.setT21('Y');
		entityGL.setT22('Y');
		entityGL.setT23('Y');
		entityGL.setT24('Y');
		entityGL.setT25('Y');
		entityGL.setT26('Y');
		entityGL.setT27('Y');
		entityGL.setT28('Y');
		entityGL.setT29('Y');
		entityGL.setT30('Y');
		entityGL.setT31('Y');
		entityGL.setT32('Y');
		entityGL.setT33('Y');

	}

	@Test
	public void addMAppingTest() throws Exception {
		List<LiabilityGLMapping> glMapping = new ArrayList<LiabilityGLMapping>();
		glMapping.add(liabilityGLMapping);

		when(dao.addGlMapping(Mockito.any())).thenReturn(1);
		Assert.assertEquals(service.addMApping(glMapping), new Integer(1));

	}

	@Test
	public void createLiabilityTest() throws Exception {
		List<LiabilityGLMapping> glMapping = new ArrayList<LiabilityGLMapping>();
		glMapping.add(liabilityGLMapping);

		when(dao.getMAppingListforModule(Mockito.any(), Mockito.any())).thenReturn(glMapping);
		when(glservice.addGlTxn(glTxnHdr)).thenReturn(2021000001);
		Assert.assertEquals(service.createLiability(liability), null);
	}

	@Test
	public void distinctModuleCdTest() throws Exception {
		List<String> strList = new ArrayList<>();
		String str = "TEST";
		strList.add(str);
		when(dao.distinctModuleCd()).thenReturn(strList);
		Assert.assertEquals(service.distinctModuleCd(), strList);
	}

	@Test
	public void getAllMappingCodeAndMappingNameforModuleCdTest() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ADVT_ADVANCE", "Advance for Marketing Advertisement Expense");
		when(dao.getAllMappingCodeAndMappingNameforModuleCd(Mockito.any())).thenReturn(map);

		Assert.assertEquals(service.getAllMappingCodeAndMappingNameforModuleCd("MKT"), map);
	}

	@Test
	public void getAllMappingCodeforModuleCdTest() throws Exception {
		Map<String, String> map = new HashMap<>();
		when(dao.getAllMappingCodeforModuleCd("MKT")).thenReturn(map);
		Assert.assertEquals(service.getAllMappingCodeforModuleCd("MKT"), map);
	}

	@Test
	public void getMappingTestStringString() throws Exception {
		List<LiabilityGLMapping> list = new ArrayList<LiabilityGLMapping>();
		list.add(liabilityGLMapping);
		when(dao.getMAppingListforModule("MKT", "ADVT_ADVANCE")).thenReturn(list);
		Assert.assertEquals(service.getMapping("MKT", "ADVT_ADVANCE"), list);
		when(dao.getMAppingListforModule("MKT", "ADVT_ADVANCE")).thenReturn(null);
		Assert.assertEquals(service.getMapping("MKT", "ADVT_ADVANCE"), null);
	}

	@Test
	public void getMappingTest() throws Exception {
		List<LiabilityGLMapping> list = new ArrayList<LiabilityGLMapping>();
		list.add(liabilityGLMapping);
		when(dao.getMAppingList()).thenReturn(list);
		Assert.assertEquals(service.getMapping(), list);
		when(dao.getMAppingList()).thenReturn(null);
		Assert.assertEquals(service.getMapping(), null);
	}

	@Test
	public void getMappingTestString() throws Exception {

		List<LiabilityGLMapping> list = new ArrayList<LiabilityGLMapping>();
		list.add(liabilityGLMapping);
		when(dao.getMAppingListforModule("MKT")).thenReturn(list);
		Assert.assertEquals(service.getMapping("MKT"), list);

		when(dao.getMAppingListforModule("MKT")).thenReturn(null);
		Assert.assertEquals(service.getMapping("MKT"), null);

	}

	@Test
	public void getRequiredTCodesTest() throws Exception {
		List<String> strList = Arrays.asList("t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "t9", "t10", "t11", "t12","t13","t14","t15", "t16", "t17", "t18", "t19", "t20", "t21", "t22","t23","t24","t25", "t26", "t27", "t28", "t29", "t30", "t31", "t32","t33");
		List<EntityGL> list = new ArrayList<EntityGL>();
		list.add(entityGL);
		when(dao.getRequiredTCodes("MKT", "ADVT_ADVANCE")).thenReturn(list);
		Assert.assertEquals(service.getRequiredTCodes("MKT", "ADVT_ADVANCE"), strList);

	}
}

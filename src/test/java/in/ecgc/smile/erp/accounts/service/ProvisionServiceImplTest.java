package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.Provision;
import in.ecgc.smile.erp.accounts.model.ProvisionGLMapping;
import in.ecgc.smile.erp.accounts.repository.ProvisionDao;

public class ProvisionServiceImplTest {
	@Mock
	GlTxnHdr glTxnHdr;

	@Mock
	GlTxnDtl glTxnDtl;

	@Mock
	ProvisionGLMapping provisionGLMapping;

	@Mock
	Provision provision;

	@Mock
	private GlTxnService glTxnService;

	@Mock
	private ProvisionDao dao;

	@InjectMocks
	ProvisionServiceImpl service;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	public void initProvision() {
		provisionGLMapping = new ProvisionGLMapping();
		provisionGLMapping.setDrCrFlag("Dr");
		provisionGLMapping.setMainGL("1810");
		provisionGLMapping.setMappingCd("ADVT_ADVANCE");
		provisionGLMapping.setMappingName("Advance for Marketing Advertisement Expense");
		provisionGLMapping.setMetaRemarks("Advance twrds");
		provisionGLMapping.setMetaStatus(" ");
		provisionGLMapping.setModuleCd("MKT");
		provisionGLMapping.setRemarks("remark");
		provisionGLMapping.setSrNo(1);
		provisionGLMapping.setSubBifurcation("Advance-Others");
		provisionGLMapping.setSubGL("001");
		provisionGLMapping.setTxnType("PV");
		provisionGLMapping.setAmtCalc(100.00f);

	}

	@BeforeTest
	public void initProvision1() {
		provision = new Provision();
		provision.setModuleCd("MKT");
		provision.setMappingCd("ADVT_ADVANCE");
		provision.setBaseAmt(100.00);
		provision.setLogicalLogCd("MUMBAILOG1");
		provision.setIsAmtInInr(true);
		provision.setUserId(1229);
		provision.setExchangeRate(5.0f);

	}

	@BeforeTest
	public void intGlTxnHdr() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Date date = null;
		try {
			date = simpleDateFormat.parse("2021-01-01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		glTxnHdr = new GlTxnHdr();
		glTxnHdr.setLogicalLocCd("MUMBAILOG1");
		glTxnHdr.setGlTxnType("PV");
		glTxnHdr.setTxnDt(LocalDate.of(2021, 01, 02));
		glTxnHdr.setGlTxnNo(2021000001);
		glTxnHdr.setEntityCd("ECGC");
		glTxnHdr.setReference("MUMBAILOG1");
		glTxnHdr.setRevarsalGlTxnType("PV");
		glTxnHdr.setRevarsalGlTxnNo(2021000001);
		glTxnHdr.setReversalReason("Test");
		glTxnHdr.setFiscalYr("2021-2022");
		glTxnHdr.setMetaStatus("mstat");
		glTxnHdr.setMetaRemarks("mremarks");
		glTxnHdr.setCreatedBy("1229");
		glTxnHdr.setCreatedDt(date);
		glTxnHdr.setLastUpdatedBy("1229");
		glTxnHdr.setLastUpdatedDt(date);

		List<GlTxnDtl> list = new ArrayList<GlTxnDtl>();

		GlTxnDtl dtl1 = new GlTxnDtl();
		dtl1.setGlTxnNo(2021000001);
		dtl1.setSrNo(1);
		dtl1.setDrCrFlag("dr");
		dtl1.setTxnAmt(120001.00);
		dtl1.setOldCode("MUMBAILOG1");
		dtl1.setCodeType("PV");
		dtl1.setEntityGlCd("ECGC");
		dtl1.setMainGlCd("1700");
		dtl1.setSubGlCd("001");
		dtl1.setGlTxnType("PV");
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
		dtl1.setT10("t13");
		dtl1.setT11("t14");
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

	@Test
	public void addMAppingTest() throws Exception {
		List<ProvisionGLMapping> glMapping = new ArrayList<>();
		glMapping.add(provisionGLMapping);
		when(dao.addGlMapping(glMapping)).thenReturn(1);
		Assert.assertEquals(service.addMApping(glMapping), Integer.valueOf(1));
	}

	@Test
	public void createProvisionTest() throws Exception {
		List<ProvisionGLMapping> glMapping = new ArrayList<>();
		glMapping.add(provisionGLMapping);

		provision.setIsAmtInInr(false);
		when(dao.getMAppingListforModule(provision.getModuleCd(), provision.getMappingCd())).thenReturn(glMapping);
		when(glTxnService.addGlTxn(glTxnHdr)).thenReturn(0);
		Assert.assertEquals(service.createProvision(provision), null);

		provision.setIsAmtInInr(true);
		glTxnHdr.setGlTxnDtls(null);
		when(dao.getMAppingListforModule(provision.getModuleCd(), provision.getMappingCd())).thenReturn(glMapping);
		when(glTxnService.addGlTxn(Mockito.any())).thenReturn(2021000001);
		glTxnHdr.setTxnDt(LocalDate.now());
		glTxnHdr.setReference(null);
		glTxnHdr.setRevarsalGlTxnType(null);
		glTxnHdr.setRevarsalGlTxnNo(null);
		glTxnHdr.setReversalReason(null);
		glTxnHdr.setFiscalYr(null);
		glTxnHdr.setMetaStatus(null);
		glTxnHdr.setMetaRemarks(null);
		glTxnHdr.setCreatedDt(null);
		glTxnHdr.setLastUpdatedBy(null);
		glTxnHdr.setLastUpdatedDt(null);
		Assert.assertEquals(service.createProvision(provision), glTxnHdr);

	}

	@Test
	public void getMappingTestStringString() throws Exception {
		List<ProvisionGLMapping> glMapping = new ArrayList<>();
		glMapping.add(provisionGLMapping);
		when(dao.getMAppingListforModule("MKT", "ADVT_ADVANCE")).thenReturn(glMapping);
		Assert.assertEquals(service.getMapping("MKT", "ADVT_ADVANCE"), glMapping);

		when(dao.getMAppingListforModule("MKT", "ADVT_ADVANCE")).thenReturn(null);
		Assert.assertEquals(service.getMapping("MKT", "ADVT_ADVANCE"), null);
	}

	@Test
	public void getMappingTestString() throws Exception {
		List<ProvisionGLMapping> glMapping = new ArrayList<>();
		glMapping.add(provisionGLMapping);
		when(dao.getMAppingListforModule("MKT")).thenReturn(glMapping);
		Assert.assertEquals(service.getMapping("MKT"), glMapping);

		when(dao.getMAppingListforModule("MKT")).thenReturn(null);
		Assert.assertEquals(service.getMapping("MKT"), null);
	}

	@Test
	public void getMappingTest() throws Exception {
		List<ProvisionGLMapping> glMapping = new ArrayList<>();
		glMapping.add(provisionGLMapping);
		when(dao.getMAppingList()).thenReturn(glMapping);
		Assert.assertEquals(service.getMapping(), glMapping);

		when(dao.getMAppingList()).thenReturn(null);
		Assert.assertEquals(service.getMapping(), null);

	}
}

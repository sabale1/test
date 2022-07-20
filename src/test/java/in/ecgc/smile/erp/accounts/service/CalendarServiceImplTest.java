package in.ecgc.smile.erp.accounts.service;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.integrate.model.OfficeMaster;
import in.ecgc.smile.erp.accounts.integrate.model.User;
import in.ecgc.smile.erp.accounts.integrate.proxy.NotificationFeignClient;
import in.ecgc.smile.erp.accounts.integrate.service.OrgStructService;
import in.ecgc.smile.erp.accounts.model.Calendar;
import in.ecgc.smile.erp.accounts.model.CalendarMstAnnual;
import in.ecgc.smile.erp.accounts.model.CalendarRequestModel;
import in.ecgc.smile.erp.accounts.model.DateOperation;
import in.ecgc.smile.erp.accounts.model.FiscalYearModel;
import in.ecgc.smile.erp.accounts.repository.CalendarDaoImpl;
import in.ecgc.smile.erp.accounts.repository.FiscalYearDao;

public class CalendarServiceImplTest {

	@Mock
	Calendar calendar;

	@Mock
	List<Calendar> calList;

	@Mock
	List<OfficeMaster> officeList;

	@Mock
	private List<CalendarMstAnnual> marchlist;

	@Mock
	private CalendarMstAnnual calendarMstAnnual;

	@Mock
	FiscalYearModel fiscalYear;

	@Mock
	FiscalYearDao fiscalYearDao;

	@Mock
	FiscalYearServiceImpl fyservice;

	@Mock
	OrgStructService orgStructService;
	
	@Mock
	NotificationFeignClient notificationFeignClient;

	@Mock
	CalendarDaoImpl dao;

	@InjectMocks
	CalendarServiceImpl service;

	@BeforeTest
	public void init() {

		MockitoAnnotations.initMocks(this);
	}

	@BeforeTest
	public void initCalendar() {
		calendar = new Calendar();
//		calendar.setBranchCode("028BO080");
		calendar.setCalendarId("2010");
		calendar.setClosedStatus('n');
		calendar.setConfigFlag(true);
		calendar.setEcgcStatus('y');
		calendar.setFiscalYear("2019-20");
		calendar.setGlTxnType("ECGC");
		calendar.setLogicalLocCode("MUMBAILOG1");
		calendar.setMonth("jan");

		calList = new ArrayList<Calendar>();
		calList.add(calendar);
	}

	@BeforeTest
	public void initMarchclosed() {
		calendarMstAnnual = new CalendarMstAnnual();
		calendarMstAnnual.setCalendarAnnualId("10");
		calendarMstAnnual.setLogicalLocCd("AHMDLOG02");
		calendarMstAnnual.setFiscalYr("2020-2021");
		calendarMstAnnual.setMonth("mar");
		calendarMstAnnual.setUserId("1229");
		calendarMstAnnual.setClosedDt(null);
		calendarMstAnnual.setLastTransDt(null);

		marchlist = new ArrayList<CalendarMstAnnual>();
		marchlist.add(calendarMstAnnual);
	}

	@BeforeTest
	public void initFiscalYear() {
		fiscalYear = new FiscalYearModel();
		fiscalYear.setCurrFiscalYear("2020-21");
		fiscalYear.setCurrFiscalYearStartDt(LocalDate.of(2020, 01, 03));
		fiscalYear.setPrevFiscalYear("2019-20");
		fiscalYear.setPrevFiscalYearClosedDt(LocalDate.of(2020, 01, 03));
	}

	@BeforeTest
	public void initOfficedtl() {
		officeList = new ArrayList<OfficeMaster>();

	}

	@Test
	public void addCalendarTest() throws Exception {
		when(dao.addCalendar(calendar)).thenReturn(1);
		Assert.assertEquals(service.addCalendar(calendar), new Integer(1));
	}

	@Test
	public void dailyOpeningClosingTest() throws Exception {
		List<String> strList = Arrays.asList("t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "t9", "t10", "t11",
				"HOLOG1");
		when(fiscalYearDao.findCurrentFiscalYear()).thenReturn(new FiscalYearModel());
		when(dao.dailyOpening("april", "2021-2022", "HOLOG1")).thenReturn(new Integer(1));
		when(dao.dailyOpening("mar", "2021-2022", "t1")).thenReturn(new Integer(1));
		when(dao.dailyOpening("feb", "2021-2022", "t1")).thenReturn(new Integer(1));
		Assert.assertEquals(service.dailyOpeningClosing(strList), new Integer(1));
	}

	@Test
	public void deleteCalendarTest() throws Exception {
		when(dao.deleteCalendar("2010")).thenReturn(1);
		Assert.assertEquals(service.deleteCalendar("2010"), new Integer(1));
	}

	@Test
	public void getAllCalendarTest() throws Exception {
		List<Calendar> list = new ArrayList<>();
		list.add(calendar);
		when(dao.getAllCalendar("2019-20", "jan")).thenReturn(list);
		Assert.assertEquals(service.getAllCalendar("2019-20", "jan"), list);
	}

	@Test
	public void getByFiscalYrTest() throws Exception {
		List<Calendar> list = new ArrayList<>();
		list.add(calendar);
		when(dao.getByFiscalYr("2019-20", "jan", "MUMBAILOG1")).thenReturn(list);
		Assert.assertEquals(service.getByFiscalYr("2019-20", "jan", "MUMBAILOG1"), list);
	}

	@Test
	public void getByGlTypeLogicalLocTest() throws Exception {
		when(dao.getByGlTypeLogicalLoc("2019-20", "jan", "MUMBAILOG1", "RV")).thenReturn(calendar);
		Assert.assertEquals(service.getByGlTypeLogicalLoc("2019-20", "jan", "MUMBAILOG1", "RV"), calendar);
	}

	@Test
	public void getCalendarTest() throws Exception {
		when(dao.getCalendar("2010")).thenReturn(calendar);
		Assert.assertEquals(service.getCalendar("2010"), calendar);
	}

	@Test
	public void getCalendarMstAnnualListTest() throws Exception {
		List<CalendarMstAnnual> list = new ArrayList<CalendarMstAnnual>();
		list.add(calendarMstAnnual);
		when(dao.getCalendarMstAnnualList("2019-20", "MUMBAILOG1")).thenReturn(list);
		Assert.assertEquals(service.getCalendarMstAnnualList("2019-20", "MUMBAILOG1"), list);
	}

	@Test
	public void marchClosingTest() throws Exception {
		when(dao.marchClosing("MUMBAILOG1", "2019-20")).thenReturn(new Integer(1));
		Assert.assertEquals(service.marchClosing("MUMBAILOG1", "2019-20"), new Integer(1));
	}

	@Test
	public void marchClosingStatusTest() throws Exception {
		when(dao.marchClosingStatus("MUMBAILOG1", "2019-20")).thenReturn(new Boolean(true));
		Assert.assertEquals(service.marchClosingStatus("MUMBAILOG1", "2019-20"), new Boolean(true));
	}

	@Test
	public void monthlyOpeningClosingConfiguredTest() throws Exception {
		List<OfficeMaster> officeList = new ArrayList<OfficeMaster>();

		when(orgStructService.getOfficeList()).thenReturn(officeList);
		when(fiscalYearDao.findCurrentFiscalYear()).thenReturn(new FiscalYearModel());
		when(dao.monthlyOpeningConfigured("MUMBAILOG1", "april", "2019-20")).thenReturn(new Integer(1));
		when(dao.monthlyOpeningConfigured("MUMBAILOG1", "mar", "2019-20")).thenReturn(new Integer(1));
		when(dao.monthlyClosingConfigured("MUMBAILOG1", "mar", "2019-20")).thenReturn(new Integer(1));
		Assert.assertEquals(service.monthlyOpeningClosingConfigured(), new Integer(1));
	}

	@Test
	public void monthlyOpeningClosingRegularTest() throws Exception {
		List<OfficeMaster> officeList = new ArrayList<OfficeMaster>();

		when(orgStructService.getOfficeList()).thenReturn(officeList);
		when(fiscalYearDao.findCurrentFiscalYear()).thenReturn(new FiscalYearModel());
		when(dao.monthlyOpeningRegular("MUMBAILOG1", "april", "2019-20")).thenReturn(new Integer(1));
		when(dao.monthlyOpeningRegular("MUMBAILOG1", "mar", "2019-20")).thenReturn(new Integer(1));
		when(dao.monthlyClosingRegular("MUMBAILOG1", "mar", "2019-20")).thenReturn(new Integer(1));
		Assert.assertEquals(service.monthlyOpeningClosingRegular(), new Integer(1));
	}

	@Test
	public void scheduleDemoTest() throws Exception {
		service.scheduleDemo();

	}

	@Test
	public void updatePrevTest() throws Exception {
		when(dao.updatePrev("2019", "Y")).thenReturn(new Integer(1));
		Assert.assertEquals(service.updatePrev("2019", "Y"), new Integer(1));
	}

	@Test
	public void updateStatus1Test() throws Exception {
		when(dao.updateStatus1("2019", "Y")).thenReturn(new Integer(1));
		Assert.assertEquals(service.updateStatus1("2019", "Y"), new Integer(1));
	}

	@Test
	public void updateStatus2Test() throws Exception {
		when(dao.updateStatus2("2019", "Y")).thenReturn(new Integer(1));
		Assert.assertEquals(service.updateStatus2("2019", "Y"), new Integer(1));
	}
	
	
	@Test
	public void DailOpeningClosingAutoTest() throws Exception {
		FiscalYearModel fismod = new FiscalYearModel();
		fismod.setCurrFiscalYear("2021-2022");
		LocalDateTime now = LocalDateTime.now();
		DateOperation dateOperation = new DateOperation(now.getMonthValue());
		when(fiscalYearDao.findCurrentFiscalYear()).thenReturn(fismod);
		when(dao.DailOpeningClosingAuto(now.getDayOfMonth(),dateOperation.currentMonth,dateOperation.prevMonth,"2021-2022")).thenReturn(new Integer(1));
		Assert.assertEquals(service.DailOpeningClosingAuto(), new Integer(1));
	}
	
	@Test
	public void DailyCalendarRequestClosingAutoTest() throws Exception {
		when(dao.DailyCalendarRequestClosingAuto()).thenReturn(new Integer(1));
		Assert.assertEquals(service.DailyCalendarRequestClosingAuto(), new Integer(1));
	}
	
	@Test
	public void ReqClosedStatTest() throws Exception {
		List<CalendarRequestModel> calreq = new ArrayList<CalendarRequestModel>();
		when(dao.ReqClosedStat("MUMBAILOG1","2021-2022")).thenReturn(calreq);
		Assert.assertEquals(service.ReqClosedStat("MUMBAILOG1","2021-2022"), calreq);
	}
	
	@Test
	public void ReqOpenedStatTest() throws Exception {
		List<CalendarRequestModel> calreq = new ArrayList<CalendarRequestModel>();
		when(dao.ReqOpenedStat("MUMBAILOG1","2021-2022")).thenReturn(calreq);
		Assert.assertEquals(service.ReqOpenedStat("MUMBAILOG1","2021-2022"), calreq);
	}
	
	@Test
	public void updateReqStatTest() throws Exception {
		when(dao.updateReqStat("5730")).thenReturn(Integer.valueOf(1));
		Assert.assertEquals(service.updateReqStat("5730"), Integer.valueOf(1));
	}
	
	@Test
	public void notificationSchedularAutoTest() throws Exception {
		List<CalendarRequestModel> calreq = new ArrayList<CalendarRequestModel>();
		CalendarRequestModel cal = new CalendarRequestModel();
		cal.setRequestedBy(1229);
		cal.setMonth("Mar");
		cal.setGlTxnType("PV");
		calreq.add(cal);
		User user = new User();
		List<User> targetedUser = new ArrayList<User>();
		user.setId("1229"); 
		user.setUsername("1229");
		targetedUser.add(user); 
		when(dao.OpenedRequests()).thenReturn(calreq);
		when(notificationFeignClient.generateCustomNotification("254",
				  "254",
				  "Please Close Approved Calendar Requests ",
				  "Please Close Approved Calendar Requests ",
				  Timestamp.from(Instant.now()),
				  targetedUser)).thenReturn("SUCCESS");
		Assert.assertEquals(service.notificationSchedularAuto(),Integer.valueOf(1));
	}
}

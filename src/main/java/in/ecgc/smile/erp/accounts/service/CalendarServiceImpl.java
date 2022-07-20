package in.ecgc.smile.erp.accounts.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.integrate.model.Employee;
import in.ecgc.smile.erp.accounts.integrate.model.OfficeMaster;
import in.ecgc.smile.erp.accounts.integrate.model.User;
import in.ecgc.smile.erp.accounts.integrate.proxy.NotificationFeignClient;
import in.ecgc.smile.erp.accounts.integrate.service.EmployeeService;
import in.ecgc.smile.erp.accounts.integrate.service.OrgStructService;
import in.ecgc.smile.erp.accounts.model.Calendar;
import in.ecgc.smile.erp.accounts.model.CalendarMstAnnual;
import in.ecgc.smile.erp.accounts.model.CalendarRequestModel;
import in.ecgc.smile.erp.accounts.model.DateOperation;
import in.ecgc.smile.erp.accounts.model.FiscalYearModel;
import in.ecgc.smile.erp.accounts.repository.CalendarDao;
import in.ecgc.smile.erp.accounts.repository.FiscalYearDao;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CalendarServiceImpl implements CalendarService {

	@Autowired
	CalendarDao calendarDao;

	@Autowired
	FiscalYearDao fiscalYearDao;

	@Autowired
	OrgStructService orgStructService;

	@Autowired
	NotificationFeignClient notificationFeignClient;

	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	EmployeeService employeeService;

	@Override
	public Integer addCalendar(Calendar calendar) {
		log.info("Inside CalendarServiceImpl#addCalendar");
		return calendarDao.addCalendar(calendar);
	}

	@Override
	public Calendar getCalendar(String calendarID) {
		log.info("Inside CalendarServiceImpl#getCalendar");
		return calendarDao.getCalendar(calendarID);
	}

	@Override
	public List<Calendar> getAllCalendar(String fiscalYr, String month) {
		log.info("Inside CalendarServiceImpl#getAllCalendar");
		return calendarDao.getAllCalendar(fiscalYr, month);
	}

	@Override
	public Integer deleteCalendar(String calendarId) {
		log.info("Inside CalendarServiceImpl#deleteCalendar");
		return calendarDao.deleteCalendar(calendarId);
	}

	@Override
	public List<Calendar> getByFiscalYr(String fiscalYr, String month, String logicalLocCode) {
		log.info("Inside CalendarServiceImpl#getByFiscalYr");
		return calendarDao.getByFiscalYr(fiscalYr, month, logicalLocCode);
	}

	@Override
	public Integer updateStatus1(String first, String status) {
		log.info("Inside CalendarServiceImpl#updateStatus1");
		return calendarDao.updateStatus1(first, status);
	}

	@Override
	public Integer updateStatus2(String second, String status) {
		log.info("Inside CalendarServiceImpl#updateStatus2");
		return calendarDao.updateStatus2(second, status);
	}

	@Override
	public Integer updatePrev(String prevyr, String status) {
		log.info("Inside CalendarServiceImpl#updatePrev");
		return calendarDao.updatePrev(prevyr, status);
	}

	@Override
	public Integer monthlyOpeningClosingRegular() {
		log.info("Inside CalendarServiceImpl#monthlyOpeningClosingRegular");
		log.info("Monthly opening and closing activity for all branches and HO except PV, JV and IB");

		List<OfficeMaster> officeList = orgStructService.getOfficeList();
		List<String> logicalLocList = officeList.stream().map(OfficeMaster::getOfficeId).collect(Collectors.toList());
		log.info("Logical location list : {}", logicalLocList);

		LocalDateTime now = LocalDateTime.now();
		// LocalDateTime now = LocalDateTime.of(2021, 12, 1, 11, 50);
		DateOperation dateOperation = new DateOperation(now.getMonthValue());

		FiscalYearModel fiscalYr = fiscalYearDao.findCurrentFiscalYear();
		log.info("Current month : {} and Previous month : {} and Fiscal yr : {}", dateOperation.currentMonth,
				dateOperation.prevMonth, fiscalYr.toString());
		for (String logicalLoc : logicalLocList) {
			log.info(logicalLoc);
			if (dateOperation.prevMonth.equalsIgnoreCase("mar")) {
				log.info("Previous month is march so only opening activity will happen!");
				calendarDao.monthlyOpeningRegular(logicalLoc, dateOperation.currentMonth, fiscalYr.getCurrFiscalYear());
			} else {
				log.info("Previous month is {} so both opening and closing activity will happen!",
						dateOperation.prevMonth);
				calendarDao.monthlyOpeningRegular(logicalLoc, dateOperation.currentMonth, fiscalYr.getCurrFiscalYear());
				calendarDao.monthlyClosingRegular(logicalLoc, dateOperation.prevMonth, fiscalYr.getCurrFiscalYear());
			}
		}
		return 1;
	}

	@Override
	public Integer monthlyOpeningClosingConfigured() {
		log.info("Inside ScheduleCodeMasterServiceImpl#monthlyOpeningClosingConfigured");
		log.info("Monthly opening and closing activity for all branches and HO except PV, JV and IB");

		List<OfficeMaster> officeList = orgStructService.getOfficeList();
		List<String> logicalLocList = officeList.stream().map(OfficeMaster::getOfficeId).collect(Collectors.toList());
		log.info("Logical location list : {}", logicalLocList);

		LocalDateTime now = LocalDateTime.now();
		// LocalDateTime now = LocalDateTime.of(2021, 4, 1, 11, 50);
		DateOperation dateOperation = new DateOperation(now.getMonthValue());

		FiscalYearModel fiscalYr = fiscalYearDao.findCurrentFiscalYear();
		log.info("Current month : {} and Previous month : {} and Fiscal yr : {}", dateOperation.currentMonth,
				dateOperation.prevMonth, fiscalYr.toString());
		for (String logicalLoc : logicalLocList) {
			log.info("Logical Location : {}",logicalLoc);
			if (dateOperation.prevMonth.equalsIgnoreCase("mar")) {
				log.info("Previous month is march so only opening activity will happen!");
				calendarDao.monthlyOpeningConfigured(logicalLoc, dateOperation.currentMonth,
						fiscalYr.getCurrFiscalYear());
			} else {
				log.info("Previous month is {} so both opening and closing activity will happen!",
						dateOperation.prevMonth);
				calendarDao.monthlyOpeningConfigured(logicalLoc, dateOperation.currentMonth,
						fiscalYr.getCurrFiscalYear());
				calendarDao.monthlyClosingConfigured(logicalLoc, dateOperation.prevMonth, fiscalYr.getCurrFiscalYear());
			}
		}
		return 1;

	}

	@Override
	public Integer dailyOpeningClosing(List<String> logicalCode) {
		log.info("Inside CalendarServiceImpl#dailyOpeningClosing");
		LocalDateTime now = LocalDateTime.now();
		DateOperation dateOperation = new DateOperation(now.getMonthValue());
		log.info("Current Month : {}",dateOperation.currentMonth);
		FiscalYearModel fiscalYr = fiscalYearDao.findCurrentFiscalYear();
		log.info("Fiscal Year : {}",fiscalYr.toString());
		for (String logCd : logicalCode) {
			log.info(logCd);
			if (dateOperation.prevMonth.equalsIgnoreCase("mar") && logCd.equalsIgnoreCase("HOLOG1")) {
				calendarDao.dailyOpening(dateOperation.currentMonth, fiscalYr.getCurrFiscalYear(), logCd);
			} else {
				calendarDao.dailyClosing(dateOperation.prevMonth, fiscalYr.getCurrFiscalYear(), logCd);
				calendarDao.dailyOpening(dateOperation.currentMonth, fiscalYr.getCurrFiscalYear(), logCd);
			}
		}
		return 1;
	}

	@Override
	public void scheduleDemo() {
		log.info("Schedule Demo");
	}

	@Override
	public Calendar getByGlTypeLogicalLoc(String fiscalYr, String month, String logicalLocCode, String glTxnType) {
		log.info("Inside CalendarServiceImpl#getByGlTypeLogicalLoc");
		return calendarDao.getByGlTypeLogicalLoc(fiscalYr, month, logicalLocCode, glTxnType);
	}

	@Override
	public Integer marchClosing(String logicalCode, String currentFiscalyr) {
		log.info("Inside CalendarServiceImpl#marchClosing");
		return calendarDao.marchClosing(logicalCode, currentFiscalyr);

	}

	@Override
	public Boolean marchClosingStatus(String logicalCode, String currentFiscalyr) {
		log.info("Inside CalendarServiceImpl#marchClosingStatus");
		return calendarDao.marchClosingStatus(logicalCode, currentFiscalyr);
	}

	@Override
	public List<CalendarMstAnnual> getCalendarMstAnnualList(String fiscalYr, String logicalloc) {
		log.info("Inside CalendarServiceImpl#getCalendarMstAnnualList");
		return calendarDao.getCalendarMstAnnualList(fiscalYr, logicalloc);
	}

	@Override
	public Integer DailOpeningClosingAuto() {
		log.info("Inside CalendarServiceImpl#DailOpeningClosingAuto");
		LocalDateTime now = LocalDateTime.now();
		log.info("Current DayofMonth & Month {} -- {}  ", now.getDayOfMonth(), now.getMonth());
		Integer currentDay = now.getDayOfMonth();
		DateOperation dateOperation = new DateOperation(now.getMonthValue());
		String currentMonth = dateOperation.currentMonth;
		String prevMonth = dateOperation.prevMonth;
		FiscalYearModel fiscalYr = fiscalYearDao.findCurrentFiscalYear();

		return calendarDao.DailOpeningClosingAuto(currentDay, currentMonth, prevMonth, fiscalYr.getCurrFiscalYear());
	}

	@Override
	public Integer DailyCalendarRequestClosingAuto() {
		log.info("Inside CalendarServiceImpl#DailyCalendarRequestClosingAuto");
		return calendarDao.DailyCalendarRequestClosingAuto();
	}

	@Override
	public List<CalendarRequestModel> ReqClosedStat(@NotBlank String logicalCode, @NotBlank String currentFiscalyr) {
		log.info("Inside CalendarServiceImpl#ReqClosedStat");
		return calendarDao.ReqClosedStat(logicalCode, currentFiscalyr);
	}

	@Override
	public List<CalendarRequestModel> ReqOpenedStat(@NotBlank String logicalCode, @NotBlank String currentFiscalyr) {
		log.info("Inside CalendarServiceImpl#ReqOpenedStat");
		List<CalendarRequestModel> openlist = new ArrayList<CalendarRequestModel>();
		openlist = calendarDao.ReqOpenedStat(logicalCode, currentFiscalyr);
		return openlist;
	}
	
	@Override
	public Integer notificationSchedularAuto() {
		log.info("Inside CalendarServiceImpl#notificationSchedularAuto");
		try
		{
		List<CalendarRequestModel> openlist = new ArrayList<CalendarRequestModel>();
		openlist = calendarDao.OpenedRequests();
		log.info("Open list {} ",openlist);

		for (CalendarRequestModel calendarRequestModel : openlist) {
			
		  User user = new User(); 
		  user.setId(calendarRequestModel.getRequestedBy().toString()); 
		  user.setUsername(calendarRequestModel.getRequestedBy().toString());
		  String userName = "";
			/*
			 * if (user.getId() != null) { Employee emp =
			 * employeeService.getEmployeeById(calendarRequestModel.getRequestedBy()); if
			 * (emp != null) { userName = emp.getFirstName() + " " + emp.getMidName() + " "
			 * + emp.getLastName(); } }
			 */
		  user.setName(userName);
		  
		  List<User> targetedUser = new ArrayList<User>();
		  
		  
		  targetedUser.add(user); 
		  String result = notificationFeignClient.generateCustomNotification("254",
		  "254", "Please Close Approved Calendar Requests For "+calendarRequestModel.getMonth()+" month of "+calendarRequestModel.getGlTxnType(),
		  "Please Close Approved Calendar Requests For "+calendarRequestModel.getMonth()+" month of "+calendarRequestModel.getGlTxnType(), Timestamp.from(Instant.now()),
		  targetedUser);
		 
		 log.info("nofification id {}",result);
		 
		}

		return openlist.size();
		}
		catch (Exception e) {
			log.info("Exception to Genearte Calendar Notification :  {}", e);
			return 0;
		}
	}
	
	@Override
	public Integer updateReqStat(String calid) {
		log.info("Inside CalendarServiceImpl#updateReqStat");
		return calendarDao.updateReqStat(calid);
	}
	

}

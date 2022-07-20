package in.ecgc.smile.erp.accounts.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import in.ecgc.smile.erp.accounts.integrate.service.OrgStructService;
import in.ecgc.smile.erp.accounts.service.CalendarService;
import in.ecgc.smile.erp.accounts.service.EntityGLMasterService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DailyCalendarRequestClosingTsakSchedular {

	@Autowired
	CalendarService calendarService;
	
	@Autowired
	OrgStructService orgStructService;

	 @Scheduled(cron="0 30 23 * * *") // Every day at 11:30PM
	// @Scheduled(cron = "0 42 13 * * *") // Every day at 12:40 PM
	// @Scheduled(cron = "${calendar.dailySchedule}")
	public void DailyCalendarRequestClosingAuto() {

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		log.info("DailyCalendarRequestClosingTsakSchedular#DailyCalendarRequestClosingAuto called at : " + dtf.format(now));
		
		Integer c = calendarService.DailyCalendarRequestClosingAuto();
		
		log.info("Calendar Requests Closed : {}",c);
		 
	}

}

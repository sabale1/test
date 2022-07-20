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
public class DailyCalendarOpeningTsakSchedular {

	@Autowired
	CalendarService calendarService;

	@Autowired
	EntityGLMasterService entityGLMasterService;

	@Autowired
	OrgStructService orgStructService;

	 @Scheduled(cron="0 05 0 * * *") // Every day at 12:05AM
	// @Scheduled(cron = "0 * * * * *") // Every day at 8 am
	// @Scheduled(cron = "${calendar.dailySchedule}")
	public void DailOpeningClosingAuto() {

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		log.info("dailyCalendarOpeningTsakSchedular#DailOpeningClosingAuto called at : " + dtf.format(now));

			Integer c = calendarService.DailOpeningClosingAuto();
			log.info("counter c {}", c);


	}

}

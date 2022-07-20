package in.ecgc.smile.erp.accounts.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import in.ecgc.smile.erp.accounts.integrate.service.OrgStructService;
import in.ecgc.smile.erp.accounts.service.CalendarService;
import in.ecgc.smile.erp.accounts.service.EntityGLMasterService;
import in.ecgc.smile.erp.sys.auth.be.service.ServiceAccountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationSchedular {
	
	@Autowired
	private ServiceAccountService serviceAccountService;

	@Autowired
	CalendarService calendarService;
	
	@Autowired
	OrgStructService orgStructService;

//	 @Scheduled(cron="0 0 16 * * *") // Every day at [4 to 12PM]
	 @Scheduled(cron = "0 0 16,18,20,22,0 * * *") // Every day at 4:00 PM
//	 @Scheduled(cron = "0 */2 10 * * *") // Every day at 4:00 PM
//	 @Scheduled(cron = "${calendar.dailySchedule}")
	public void DailyCalendarRequestClosingAuto() {

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		log.info("NotificationSchedular#NotificationSchedularAuto called at : " + dtf.format(now));
		
		try {
			serviceAccountService.authenticate();
			
		} catch (Exception e) {
			log.info("Exception in Authetication : {}",e);
		}
		
		Integer c = calendarService.notificationSchedularAuto();
		log.info("Notification Generated : {}",c);

		 
	}

}

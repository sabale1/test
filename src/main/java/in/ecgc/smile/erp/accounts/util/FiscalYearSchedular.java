package in.ecgc.smile.erp.accounts.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import in.ecgc.smile.erp.accounts.service.FiscalYearService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FiscalYearSchedular {

	@Autowired
	FiscalYearService fiscalYearService;

	@Scheduled(cron="0 0 0 1 4 ?") // Every 1st april
	//@Scheduled(cron = "0 */2 12 * * *") // Every day at 4:00 PM
	// @Scheduled(cron = "${calendar.dailySchedule}")
	public void FiscalYearSchedularAuto() {

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		log.info("FiscalYearSchedular#FiscalYearSchedularAuto called at : " + dtf.format(now));

		String c = fiscalYearService.fiscalYearSchedularAuto();

		log.info("Fiscal Year  : {}", c);

	}

}

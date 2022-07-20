package in.ecgc.smile.erp.accounts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import in.ecgc.smile.erp.accounts.util.DailyCalendarOpeningTsakSchedular;
import in.ecgc.smile.erp.accounts.util.DailyCalendarRequestClosingTsakSchedular;
import in.ecgc.smile.erp.accounts.util.FiscalYearSchedular;
import in.ecgc.smile.erp.accounts.util.NotificationSchedular;

@Configuration
@EnableScheduling
public class ScheduleConfig {

	// @Bean public MonthlyCalendarOpeningTsakSchedular getScheduler() { return new
	// MonthlyCalendarOpeningTsakSchedular(); }

	@Bean
	public DailyCalendarOpeningTsakSchedular getScheduler() {
		return new DailyCalendarOpeningTsakSchedular();
	}

	@Bean
	public DailyCalendarRequestClosingTsakSchedular getSchedulerDailyCalendarRequestClosingTsakSchedular() {
		return new DailyCalendarRequestClosingTsakSchedular();
	}

	@Bean
	public NotificationSchedular getNotificationSchedular() {
		return new NotificationSchedular();
	}
	
	@Bean
	public FiscalYearSchedular getFiscalYearSchedular() {
		return new FiscalYearSchedular();
	}

}

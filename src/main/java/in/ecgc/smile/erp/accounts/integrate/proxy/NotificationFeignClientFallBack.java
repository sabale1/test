package in.ecgc.smile.erp.accounts.integrate.proxy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
/**
 * Feign Client Fall Back For Notification Front End Application
 * @author Rajat Garg, Rohan Nadkarni, Shuchi Malaviya
 *
 */
@Component
public class NotificationFeignClientFallBack implements FallbackFactory<NotificationFeignClient>{

	private static final Logger logger = LoggerFactory.getLogger(NotificationFeignClientFallBack.class);

	@Override
	public NotificationFeignClient create(Throwable cause) {
		logger.error("Notification service down");
		logger.info("Cause {} =>",cause);
		return null;
	}

}

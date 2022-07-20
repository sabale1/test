package in.ecgc.smile.erp.accounts.integrate.proxy;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import in.ecgc.smile.erp.accounts.integrate.model.CustomNotification;
import in.ecgc.smile.erp.accounts.integrate.model.Notification;
import in.ecgc.smile.erp.accounts.integrate.model.User;

/**
 * Feign Client For Notification Front End Application
 * 
 * @author Rajat Garg, Rohan Nadkarni, Shuchi Malaviya
 *
 */
//@FeignClient(name = "${api.gateway}",url="${feign.client.notification-url}", contextId = "NotificationFeignClient" ,fallbackFactory = NotificationFeignClientFallBack.class)
//@FeignClient(name = "${api.gateway}", fallbackFactory = NotificationFeignClientFallBack.class)
@FeignClient(name = "erp-sys-apigateway", contextId = "NotificationFeignClient", fallbackFactory = NotificationFeignClientFallBack.class)
//@FeignClient(name = "erp-sys-apigateway",url="http://10.210.0.140:31335/", contextId = "NotificationFeignClient" ,fallbackFactory = NotificationFeignClientFallBack.class)
public interface NotificationFeignClient {

	@GetMapping("/erp-bs-notfn-be/notifications/{userid}")
//	@GetMapping("/${feign.client.notification}/notifications/{userid}")
	public List<Notification> getUserNotifications(@PathVariable(name = "userid", required = true) String userId);

	@PutMapping("/erp-bs-notfn-be/notification/read/{notificationid}")
//	@PutMapping("/${feign.client.notification}/notification/read/{notificationid}")
	public Boolean readNotification(@PathVariable(name = "notificationid", required = true) Long notificationId);

	@PutMapping("/erp-bs-notfn-be/notifications/read/all/{userid}")
//	@PutMapping("/${feign.client.notification}/notifications/read/all/{userid}")
	public Boolean readAllNotifications(@PathVariable(name = "userid", required = true) String userId);

	@GetMapping("/erp-bs-notfn-be/custom-notifications/{userid}")
//	@GetMapping("/${feign.client.notification}/custom-notifications/{userid}")
	public List<CustomNotification> getCustomNotifications(
			@PathVariable(name = "userid", required = true) String userId);

	@PutMapping("/erp-bs-notfn-be/custom-notification/read/{notificationid}")
//	@PutMapping("/${feign.client.notification}/custom-notification/read/{notificationid}")
	public Boolean readCustomNotification(@PathVariable("notificationid") Long notificationId);

	@DeleteMapping("/erp-bs-notfn-be/custom-notification/delete/{notificationid}")
//	@DeleteMapping("/${feign.client.notification}/custom-notification/delete/{notificationid}")
	public Boolean deleteCustomNotification(@PathVariable("notificationid") Long notificationId);

	@PutMapping("/erp-bs-notfn-be/custom-notifications/read/all/{userid}")
//	@PutMapping("/${feign.client.notification}/custom-notifications/read/all/{userid}")
	public Boolean readAllCustomNotifications(@PathVariable(name = "userid", required = true) String userId);

	@PutMapping("/erp-bs-notfn-be/notification/unread/{notificationid}")
//	@PutMapping("/${feign.client.notification}/notification/unread/{notificationid}")
	public Boolean unreadNotification(@PathVariable(name = "notificationid", required = true) Long notificationId);

	@GetMapping("/erp-bs-notfn-be/notification/user-id/{notificationid}")
//	@GetMapping("/${feign.client.notification}/notification/user-id/{notificationid}")
	public String getNotificationUserId(@PathVariable("notificationid") Long notificationId);

	@GetMapping("/erp-bs-notfn-be/custom-notification/user-id/{notificationid}")
//	@GetMapping("/${feign.client.notification}/custom-notification/user-id/{notificationid}")
	public String getCustomNotificationUserId(@PathVariable("notificationid") Long notificationId);

	@GetMapping("/erp-bs-notfn-be/notifications/alert/{userid}")
//	@GetMapping("/${feign.client.notification}/notifications/alert/{userid}")
	public List<Notification> getAlertNotifications(@PathVariable(name = "userid", required = true) String userId);

	@GetMapping("/erp-bs-notfn-be/notifications/unread/count/{userid}")
//	@GetMapping("/${feign.client.notification}/notifications/unread/count/{userid}")
	public Long getUnreadNotificationsCount(@PathVariable(name = "userid", required = true) String userId);

	@GetMapping("/erp-bs-notfn-be/custom-notifications/alert/{userid}")
//	@GetMapping("/${feign.client.notification}/custom-notifications/alert/{userid}")
	public List<CustomNotification> getAlertCustomNotifications(
			@PathVariable(name = "userid", required = true) String userId);

	@GetMapping("/erp-bs-notfn-be/custom-notifications/unread/count/{userid}")
//	@GetMapping("/${feign.client.notification}/custom-notifications/unread/count/{userid}")
	public Long getUnreadCustomNotificationsCount(@PathVariable(name = "userid", required = true) String userId);

	@PostMapping("/erp-bs-notfn-be/custom-notification")
//	@PostMapping("/${feign.client.notification}/erp-bs-notfn-be/custom-notification")
	public String generateCustomNotification(@RequestParam(required = true) String notificationGeneratedByUserId,
			@RequestParam(required = true) String notificationGeneratedByUserName,
			@RequestParam(required = true) String notificationSubject,
			@RequestParam(required = true) String notificationMessageDescription,
			@RequestParam(required = true) Timestamp notificationGenerationDt,
			@RequestBody(required = true) List<User> targatedUserList);

}

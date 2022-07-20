package in.ecgc.smile.erp.accounts.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;

@Component
public class UserInfoServiceUtil {

	@Autowired
	private UserInfoService userInfoService;

	public final String getCurrentUser() {
		String userId = userInfoService.getUser();
		if(Strings.isNullOrEmpty(userId))
			return "ACCT";
		else
			return userId;
	}
}
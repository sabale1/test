package in.ecgc.smile.erp.accounts.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;

@Component
public class CommonFunctions {


@Autowired
		private UserInfoService userInfoService;


		
//		@Autowired
//		public CommonFunctions(UserInfoService userInfoService) {
//		this.userInfoService = userInfoService;
//		}
			
		
		public final String getCurrentUser() {
//		String userName = userInfoService.getUserId();
		if(userInfoService== null) {
			return "101";
		}else
			return userInfoService.getUsername();
//		return (userName == null || StringUtils.isEmpty(userName) ? "101" : userName);
		}
}

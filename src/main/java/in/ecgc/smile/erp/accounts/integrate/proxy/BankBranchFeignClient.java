package in.ecgc.smile.erp.accounts.integrate.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import in.ecgc.smile.erp.accounts.integrate.model.BankBranchDetails;


//@FeignClient(name = "erp-hrd-emp-mgmt-be",url="http://k8master0.ecgcindia.com:31920", contextId = "HrdFeignClient", fallbackFactory = HrdFeignClientFallback.class)
//@FeignClient(name = "${api.gateway}",url="http://10.210.0.140:31335", contextId = "HrdFeignClient", fallbackFactory = HrdFeignClientFallback.class)
//@FeignClient(name = "${apigw.service}", contextId = "HrdFeignClient", fallbackFactory = HrdFeignClientFallback.class)
//@FeignClient(name = "erp-hrd-emp-mgmt-be ",url="http://kmaster.cdacmumbai.in:31920/", contextId = "HrdFeignClient", fallbackFactory = HrdFeignClientFallback.class)
@FeignClient(name = "${api.gateway}",  contextId="BankBranchFeignClient",fallbackFactory = BankBranchDetailsFallback.class)
//@FeignClient(name = "erp-ecib-uw-be",contextId="BankBranchFeignClient", url="http://10.210.0.140:31852" , fallbackFactory = BankBranchDetailsFallback.class)
public interface BankBranchFeignClient {

	@GetMapping("/bankbranch/fetch-bankbranchlist")
		public List<BankBranchDetails> fetchBankBranchList();

}

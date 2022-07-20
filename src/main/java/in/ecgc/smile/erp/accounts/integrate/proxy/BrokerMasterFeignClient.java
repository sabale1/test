package in.ecgc.smile.erp.accounts.integrate.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import in.ecgc.smile.erp.accounts.integrate.model.BankBranchDetails;
import in.ecgc.smile.erp.accounts.integrate.model.BrokerMaster;


//@FeignClient(name ="erp-mktg-brokerage-be", url = "http://10.210.0.140:31335", contextId = "BrokerMasterFallback")
@FeignClient(name = "${apigw.service}", contextId = "BrokerMasterFeignClient", fallbackFactory = BrokerMasterFallback.class)

public interface BrokerMasterFeignClient {

	@GetMapping("erp-mktg-brokerage-be/empanelled-brokers")
	public List<BrokerMaster> getBrokerMasterList();

}

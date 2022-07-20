package in.ecgc.smile.erp.accounts.integrate.proxy;

import java.util.List;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
import in.ecgc.smile.erp.accounts.integrate.model.BankBranchDetails;
import in.ecgc.smile.erp.accounts.integrate.model.BrokerMaster;

@Component
public class BrokerMasterFallback implements FallbackFactory<BrokerMasterFeignClient>{

	@Override
	public BrokerMasterFeignClient create(Throwable cause) {
		return new BrokerMasterFeignClient() {

			@Override
			public List<BrokerMaster> getBrokerMasterList() {
				// TODO Auto-generated method stub
				System.err.println("inside BE FallBackClient getBrokerMasterList ");
				return null;
			}
		
		};
	}



}

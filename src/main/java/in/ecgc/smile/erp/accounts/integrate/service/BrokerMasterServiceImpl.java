package in.ecgc.smile.erp.accounts.integrate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.integrate.model.BrokerMaster;
import in.ecgc.smile.erp.accounts.integrate.proxy.BankBranchFeignClient;
import in.ecgc.smile.erp.accounts.integrate.proxy.BrokerMasterFeignClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BrokerMasterServiceImpl implements BrokerMasterService {
 
	@Autowired
	private BrokerMasterFeignClient brokerMasterFeignClient;
	
	public List<BrokerMaster> getBrokerMasterList() {
		// TODO Auto-generated method stub
		log.info("inside BE service getBrokerMasterList");
		return brokerMasterFeignClient.getBrokerMasterList();
		
		
	}

}

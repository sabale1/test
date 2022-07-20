package in.ecgc.smile.erp.accounts.integrate.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.integrate.model.BankBranchDetails;
import in.ecgc.smile.erp.accounts.integrate.model.BrokerMaster;
import in.ecgc.smile.erp.accounts.integrate.service.BrokerMasterServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/broker-master")
@Api(value = "BrokerMaster details from Marketting")
@Slf4j
public class BrokerMasterController {
	
	@Autowired
	BrokerMasterServiceImpl brokerMasterService;
	
	@GetMapping(value = "/getBrokerMasterList")
	@ApiOperation(value = "Get All Broker Master List", response = ResponseEntity.class)
	public ResponseEntity<List<BrokerMaster>> getBrokerMasterList(){
		List<BrokerMaster> brokerMasterList = new ArrayList<BrokerMaster>();
		try {
			brokerMasterList = brokerMasterService.getBrokerMasterList();
			log.info("Broker master list is :{}",brokerMasterList.toArray());
			log.info("Size of Broker Master list received {}",brokerMasterList.size());
			
		} catch (Exception e) {
			log.error("Error while fetching Broker Master List",e);
		}
		
		return new ResponseEntity<>(brokerMasterList,HttpStatus.OK);
	}

}

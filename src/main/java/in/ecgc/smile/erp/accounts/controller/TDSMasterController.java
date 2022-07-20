package in.ecgc.smile.erp.accounts.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.TDSMaster;
import in.ecgc.smile.erp.accounts.service.TDSMasterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tds-master")
@Api(value = "tds master service")
@Slf4j
public class TDSMasterController {

	@Autowired
	TDSMasterService tdsMasterService;
	
	@PostMapping("/add")
	@ApiOperation(value = "Add New Tds Detail", response = ResponseEntity.class)
	public ResponseEntity<TDSMaster> addTdsDetails(@Valid @RequestBody TDSMaster tdsMaster) {
		log.info("Inside TDSMasterController#addTdsDetails ");
		log.info("New Tds Entry {}", tdsMaster);
		tdsMasterService.addTdsDetails(tdsMaster);
		if (tdsMaster != null)
			return new ResponseEntity<>(tdsMaster, HttpStatus.ACCEPTED);
		else 
			return new ResponseEntity<>(tdsMaster, HttpStatus.NOT_ACCEPTABLE);
		
	}
	
	@GetMapping("/view-all")
	@ApiOperation(value = "View All Tds Details", response = ResponseEntity.class)
	public ResponseEntity<List<TDSMaster>> viewAllTds() {
		log.info("Inside TDSMasterController#viewAllTds ");
		List<TDSMaster> list = tdsMasterService.viewAllTds();
		log.info("List of All Tds Entries {}", list);
		if (list != null)
			return new ResponseEntity<>(list, HttpStatus.OK);
		else 
			return new ResponseEntity<List<TDSMaster>>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/view/{fromAmount}/{toAmount}/{sex}/{fiscalYr}")
	@ApiOperation(value = "View TDS Details ", response = ResponseEntity.class)
	public ResponseEntity<TDSMaster> find(
			@ApiParam(value = "From Amount", required = true) 
			@PathVariable("fromAmount")@NotBlank double fromAmount, 
			@ApiParam(value = "To Amount", required = true) 
			@PathVariable("toAmount")@NotBlank double toAmount,
			@ApiParam(value = "Gender", required = true) 
			@PathVariable("sex")@NotBlank char sex,
			@ApiParam(value = "Fiscal Year", required = true) 
			@PathVariable("fiscalYr")@NotBlank String fiscalYr){
		log.info("Inside TDSMasterController#find ");
		TDSMaster tdsMaster = tdsMasterService.find(fromAmount,toAmount,sex,fiscalYr);
		log.info("Tds Entry {}", tdsMaster);
		return new ResponseEntity<>(tdsMaster, HttpStatus.OK);
	}
	
	@GetMapping("/checkamt/{fromAmount}/{toAmount}")
	@ApiOperation(value = "Check From And To Amount", response = ResponseEntity.class)
	public ResponseEntity<Boolean> checkFromAmtTOToAmt(
			@ApiParam(value = "From Amount", required = true) @PathVariable("fromAmount") @NotBlank Double fromAmount,
			@ApiParam(value = "To Amount", required = true) @PathVariable("toAmount") @NotBlank Double toAmount) {
		log.info("Inside TDSMasterServiceController#checkFromAmtTOToAmt");
		Boolean result = tdsMasterService.checkFromAmtTOToAmt(fromAmount, toAmount);
		log.info("Result is : {}",result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}

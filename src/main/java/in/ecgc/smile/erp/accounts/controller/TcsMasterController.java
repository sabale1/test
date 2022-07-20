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

import in.ecgc.smile.erp.accounts.model.TcsMaster;
import in.ecgc.smile.erp.accounts.service.TcsMasterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tcs-master")
@Api(value = "tcs Master service")
@Slf4j
public class TcsMasterController {

	@Autowired
	TcsMasterService tcsMasterService;

	@GetMapping("/view-all")
	@ApiOperation(value = "View All Tcs Masters", response = ResponseEntity.class)
	public ResponseEntity<List<TcsMaster>> listAllTCs() {
		log.info("Inside TcsMasterController#listAllTCs ");
		List<TcsMaster> listTcs = tcsMasterService.listAllTcs();
		log.info("List of All Tcs Details {}", listTcs);
		if (listTcs != null) {
			return new ResponseEntity<>(listTcs, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<TcsMaster>>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("/add")
	@ApiOperation(value = "add tcs", response = ResponseEntity.class)
	public ResponseEntity<TcsMaster> addTcs(@Valid @RequestBody TcsMaster tcsMaster) {
		log.info("Inside TcsMasterController#addTcs ");
		log.info("New Tcs Entry {}", tcsMaster);
		tcsMasterService.addTcsMaster(tcsMaster);
		return new ResponseEntity<>(tcsMaster, HttpStatus.CREATED);
	}
	
	@GetMapping("/checkamt/{fromAmount}/{toAmount}")
	@ApiOperation(value = "Check From And To Amount", response = ResponseEntity.class)
	public ResponseEntity<Boolean> checkFromAmtTOToAmt(
			@ApiParam(value = "From Amount", required = true) @PathVariable("fromAmount") @NotBlank Double fromAmount,
			@ApiParam(value = "To Amount", required = true) @PathVariable("toAmount") @NotBlank Double toAmount) {
		log.info("Inside TCSMasterServiceController#checkFromAmtTOToAmt");
		Boolean result = tcsMasterService.checkFromAmtTOToAmt(fromAmount, toAmount);
		log.info("Result is : {}",result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}

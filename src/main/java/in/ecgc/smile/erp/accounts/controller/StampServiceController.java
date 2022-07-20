package in.ecgc.smile.erp.accounts.controller;

import java.util.ArrayList;
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

import in.ecgc.smile.erp.accounts.model.StampParameterModel;
import in.ecgc.smile.erp.accounts.service.StampService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Stamp Parameter
 * 
 * @author Amitesh Banerjee
 * @version 1.0 28-April-2020
 *
 */

@Slf4j
@RestController
@RequestMapping("/stamp-parameter")
@Api(value = "Stamp Parameter Service")
public class StampServiceController {

	@Autowired
	StampService stampService;

	@PostMapping("/add")
	@ApiOperation(value = "Add New Stamp Parameter", response = ResponseEntity.class)
	public ResponseEntity<StampParameterModel> addStampParameter(
			@Valid @RequestBody StampParameterModel stampParameter) {
		log.info("Inside StampServiceController#addStampParameter");
		Integer res = stampService.addStampParameter(stampParameter);
		log.info("Result is :{}", res);
		if (res > 0) {
			stampParameter.setSrNo(res);
			log.info("Stamp Parameter model is :{}",stampParameter);
			return new ResponseEntity<StampParameterModel>(stampParameter, HttpStatus.CREATED);
		}
		else
		return null;
	}

	@PostMapping("/update/{stampCode}")
	@ApiOperation(value = "Update Stamp Parameter", response = ResponseEntity.class)
	public ResponseEntity<StampParameterModel> updateStampParameter(
			@ApiParam(value = "Stamp Code", required = true) @PathVariable("stampCode") @NotBlank Integer stampCode,
			@Valid @RequestBody StampParameterModel stampParameterUpdate) {
		log.info("Inside StampServiceController#updateStampParameter");
		StampParameterModel modifiedStamp = stampService.updateStampParameter(stampCode, stampParameterUpdate);
		return new ResponseEntity<StampParameterModel>(modifiedStamp, HttpStatus.CREATED);
	}

	@GetMapping("/viewStampParameter")
	@ApiOperation(value = "View All Stamp Codes", response = ResponseEntity.class)
	public ResponseEntity<List<StampParameterModel>> allStampParameter() {
		log.info("Inside StampServiceController#allStampParameter");
		List<StampParameterModel> stampParameterlist = new ArrayList<>();
		stampParameterlist = stampService.allStampParameter();
		log.info("Stamp Parameter list return by controller {}", stampParameterlist);
		return new ResponseEntity<>(stampParameterlist, HttpStatus.OK);
	}

	@GetMapping("/view/{stampCode}")
	@ApiOperation(value = "View Stamp Parameter by Stamp Code", response = ResponseEntity.class)
	public ResponseEntity<StampParameterModel> viewByStampCode(
			@ApiParam(value = "Stamp Code", required = true) @PathVariable("stampCode") @NotBlank Integer stampCode) {
		log.info("Inside StampServiceController#viewByStampCode");
		StampParameterModel stampModel = stampService.viewByStampCode(stampCode);
		log.info("Stamp Parameter return by controller {}", stampModel.toString());
		return new ResponseEntity<>(stampModel, HttpStatus.OK);
	}

	@GetMapping("/view-stamp-amt/{receiptAmount}")
	@ApiOperation(value = "Get Stamp Amount by From and To Amount", response = ResponseEntity.class)
	public ResponseEntity<Double> getStampAmtByFromAndToAmt(
			@ApiParam(value = "receiptAmount", required = true) @PathVariable("receiptAmount") @NotBlank Double receiptAmount) {
		log.info("Inside StampServiceController#getStampAmtByFromAndToAmt");
		Double stampAmt = stampService.getStampAmtByFromAndToAmt(receiptAmount);
		log.info("Stamp Amount returned by controller {}", stampAmt);
		return new ResponseEntity<>(stampAmt, HttpStatus.OK);
	}

	@GetMapping("/viewstamp/{fromAmount}/{toAmount}")
	@ApiOperation(value = "Get Stamp Amount by From And To Amount", response = ResponseEntity.class)
	public ResponseEntity<Boolean> checkFromAmtTOToAmt(
			@ApiParam(value = "From Amount", required = true) @PathVariable("fromAmount") @NotBlank Double fromAmount,
			@ApiParam(value = "To Amount", required = true) @PathVariable("toAmount") @NotBlank Double toAmount) {
		log.info("Inside StampServiceController#checkFromAmtTOToAmt");
		Boolean result = stampService.checkFromAmtTOToAmt(fromAmount, toAmount);
		log.info("Result is : {}",result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
}

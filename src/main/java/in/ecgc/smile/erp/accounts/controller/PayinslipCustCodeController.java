package in.ecgc.smile.erp.accounts.controller;

import java.util.List;

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

import in.ecgc.smile.erp.accounts.model.PayinslipCustCode;
import in.ecgc.smile.erp.accounts.service.PayinslipCustCodeService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payInSlipCust")
@Api(value = "Pay In Slip Customer")
@Slf4j
public class PayinslipCustCodeController {

	@Autowired
	private PayinslipCustCodeService payinslipCustCodeservice;

	@GetMapping(value = "/view-all/{logicalLoc}")
	@ApiOperation(value = "View All PayinslipCustCode by Logical Location Code ", response = ResponseModel.class)
	public ResponseEntity<List<PayinslipCustCode>> getPayinSlipCustCodeByLogicalLoc(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLoc") @NotBlank String logicalLoc) {
		log.info("Inside PaymentinSlip Controller#getPayinSlipCustCodeByLogicalLoc ");
		List<PayinslipCustCode> payinSlipList = payinslipCustCodeservice.getPayinSlipCustCodeByLogicalLoc(logicalLoc);
		log.info("List of All Payment Employee Direct Credit Hdrs by Logical Location {}", payinSlipList);
		if (payinSlipList != null) {
			return new ResponseEntity<>(payinSlipList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<PayinslipCustCode>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/get-payinslipCustCode-data")
	@ApiOperation(value = "View All PayinslipCustCode", response = ResponseModel.class)
	public ResponseEntity<List<PayinslipCustCode>> getPayinslipCustCodeList() {
		log.info("Inside PaymentinSlip Controller#getPayinslipCustCodeList ");
		List<PayinslipCustCode> result = payinslipCustCodeservice.getPayinslipCustCodeList();
		log.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/get-payinslipCustCode-data-by-id/{id}")
	@ApiOperation(value = "View All PayinslipCustCode by Customer Code", response = ResponseModel.class)
	public ResponseEntity<PayinslipCustCode> getPayinslipCustCodeDataById(
			@ApiParam(value = "Customer Code", required = true) @PathVariable("id") String customerCd) {
		log.info("Inside PaymentinSlip Controller#getPayinslipCustCodeDataById ");
		PayinslipCustCode result = payinslipCustCodeservice.getPayinslipCustCodeDataById(customerCd);
		log.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/save-payinslipCustCode-data")
	@ApiOperation(value = "Add New PayinslipCustCode", response = ResponseModel.class)
	public ResponseEntity<Boolean> addPayinslipCustCodeData(@RequestBody PayinslipCustCode payinslipCustCode) {
		log.info("Inside PaymentinSlip Controller#addPayinslipCustCodeData ");
		boolean result = payinslipCustCodeservice.addPayinslipCustCodeData(payinslipCustCode);
		log.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/update-payinslipCustCode-data")
	@ApiOperation(value = "Update PayinslipCustCode", response = ResponseModel.class)
	public ResponseEntity<Boolean> updatePayinslipCustCodeData(@RequestBody PayinslipCustCode payinslipCustCode) {
		log.info("Inside PaymentinSlip Controller#updatePayinslipCustCodeData ");
		boolean result = payinslipCustCodeservice.updatePayinslipCustCodeData(payinslipCustCode);
		log.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}

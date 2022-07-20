package in.ecgc.smile.erp.accounts.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.PaymentAdvice;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditDtl;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;
import in.ecgc.smile.erp.accounts.service.PaymentEmployeeDirectCreditService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/directCredit")
@Api(value = "Payment Employee Direct Credit")
@Slf4j
public class PaymentEmployeeDirectCreditController {

	@Autowired
	PaymentEmployeeDirectCreditService pymtEmpSerivce;

	@GetMapping(value = "/view-all/{logicalLoc}")
	@ApiOperation(value = "Get Payment Employee Direct Credit Hdrs by Logical Location Code", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentEmployeeDirectCreditHdr>> getPaymentAdviceByLogicalLoc(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLoc") @NotBlank String logicalLoc) {
		log.info("Inside PaymentEmployeeDirectCreditController#getPaymentAdviceByLogicalLoc ");
		List<PaymentEmployeeDirectCreditHdr> pymtEmpList = pymtEmpSerivce.getPaymentAdviceByLogicalLoc(logicalLoc);
		log.info("List of All Payment Employee Direct Credit Hdrs by Logical Location {}", pymtEmpList);
		if (pymtEmpList != null) {
			return new ResponseEntity<>(pymtEmpList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<PaymentEmployeeDirectCreditHdr>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/view-all")
	@ApiOperation(value = "View All Payment Employee Direct Credit Hdrs", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentEmployeeDirectCreditHdr>> viewAll() {
		log.info("Inside PaymentEmployeeDirectCreditController#viewAll ");
		List<PaymentEmployeeDirectCreditHdr> pymtEmpList = pymtEmpSerivce.viewAll();
		log.info("List of All Payment Employee Direct Credit Hdrs {}", pymtEmpList);
		if (pymtEmpList != null) {
			return new ResponseEntity<>(pymtEmpList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<PaymentEmployeeDirectCreditHdr>>(HttpStatus.NO_CONTENT);

		}
	}

	@PostMapping(value = "/add")
	@ApiOperation(value = "Create new payment employment entry", response = ResponseEntity.class)
	public ResponseEntity<String> addPaymentEmployment( @RequestBody @Valid PaymentEmployeeDirectCreditHdr payEmp){
		log.info("inside controller addPaymentEmployment");
			//return new ResponseEntity<>(pymtEmpSerivce.addPaymentEmployment(payEmp),HttpStatus.CREATED);
			String result=pymtEmpSerivce.addPaymentEmployment(payEmp);
			log.info("RESULT OF CREDIT {}",result);
				return new ResponseEntity<>(result, HttpStatus.CREATED);
	}	
	
	
	
	@GetMapping("/view/{requestNo}/{requestedLogicalLoc}/{departmentCode}")
	@ApiOperation(value = "View AllPaymentEmployee by Requested Logical Location ,Request Number and Department Code", response = ResponseEntity.class)
	public ResponseEntity<PaymentEmployeeDirectCreditHdr> getPaymentEmpDirectCredit(
			@ApiParam(value = "Request Number", required = true) @PathVariable("requestNo") @NotBlank String  requestNo,
			@ApiParam(value = "Requested Logical Location", required = true) @PathVariable("requestedLogicalLoc") @NotBlank String requestedLogicalLoc,
			@ApiParam(value = "Department Code") @PathVariable("departmentCode") String departmentCode) {

		log.info("Inside controller getPaymentEmpDirectCredit" +"requestNo :"+requestNo+" requestedLogicalLoc :"+requestedLogicalLoc+" departmentCode is "+departmentCode);

		return new ResponseEntity<PaymentEmployeeDirectCreditHdr>(pymtEmpSerivce.getPaymentEmpDirectCredit(requestNo, requestedLogicalLoc, departmentCode), HttpStatus.OK);
	}
		
	@GetMapping("/viewByRequestNo/{requestNo}")
	@ApiOperation(value = "View AllPaymentEmployee by Requested Logical Location ", response = ResponseEntity.class)
	public ResponseEntity<PaymentEmployeeDirectCreditHdr> getPaymentEmpDirectCreditByRequestNo(
	@ApiParam(value = "Request Number", required = true) @PathVariable("requestNo") @NotBlank String  requestNo)
	{
    log.info("Inside controller getPaymentEmpDirectCredit" +"requestNo :"+requestNo);

		return new ResponseEntity<PaymentEmployeeDirectCreditHdr>(pymtEmpSerivce.getPaymentEmpDirectCreditByRequestNo(requestNo), HttpStatus.OK);
	}
	

	
	@PostMapping("/update")
	@ApiOperation(value = "Update Payment Employee Details", response = ResponseModel.class)
	public ResponseEntity<Integer> updatePaymentEmpDirectCredit(@RequestBody PaymentEmployeeDirectCreditHdr payEmp) {
         
         log.info("Inside controller updatePaymentEmpDirectCredit "+payEmp);
 	return new ResponseEntity<>(pymtEmpSerivce.updatePaymentEmpDirectCredit(payEmp), HttpStatus.CREATED);
	}
		}

package in.ecgc.smile.erp.accounts.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.service.PayInSlipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/pay-in-slip")
@Api(value = "Pay In Slip Service")
@Slf4j
public class PayInSlipController {

	@Autowired
	PayInSlipService paySlipService;
	
	@GetMapping("/view-all")
	@ApiOperation(value = "View All Pay In Slip Receipts" ,response = ResponseEntity.class)
	public ResponseEntity<List<Receipt>> viewAllPayInSlipReceipts(){
		log.info("Inside PayInSlipController#viewAllPayInSlipReceipts");
		List<Receipt> listAllPayInSlipReceipts = paySlipService.listAllPayInSlipReceipts();
		return new ResponseEntity<>(listAllPayInSlipReceipts ,HttpStatus.OK);
	}
	
	@GetMapping("/view/{logicalLocCode}/{receiptNumber}")
	@ApiOperation(value = "View Pay In Slip by Logical Location Code and Receipt Number", response = ResponseEntity.class)
	public ResponseEntity<Receipt> viewPayInSlipByLogLocAndReceiptNo(
			@ApiParam(value = "Logical Location Code", required = true)
			@PathVariable("logicalLocCode") @NotBlank String logicalLocCode,
			@ApiParam(value = "Receipt Number", required = true)
			@PathVariable("receiptNumber")@NotBlank Integer receiptNumber){
				
		log.info("Inside PayInSlipController#viewPayInSlipByLogLocAndReceiptNo");
		Receipt receipt = paySlipService.viewPayInSlipByLogicalLocCodeAndReceiptNo(logicalLocCode, receiptNumber);
		System.err.println("Receipt :"+receipt);
		return new ResponseEntity<Receipt>(receipt,HttpStatus.OK);
	}
	
	@GetMapping("/view/{fromDate}/{toDate}/{instrumentType}")
	@ApiOperation(value = "View Pay In Slip by From Date, To Date and Instrument Type", response = ResponseEntity.class)
	public ResponseEntity<List<Receipt>> listByFromDtToDtAndInstrumentTypePayInSlip(
			@ApiParam(value = "From Date", required = true)
			@PathVariable("fromDate") @NotBlank String fromDate,
			@ApiParam(value = "To Date", required = true)
			@PathVariable("toDate") @NotBlank String toDate,
			@ApiParam(value = "Instrument Type", required = true)
			@PathVariable("instrumentType") @NotBlank String[] instrumentType){
				
		log.info("Inside PayInSlipController#listByFromDtToDtAndInstrumentTypePayInSlip");
		List<Receipt> list = paySlipService.listByFromDtToDtAndInstrumentTypePayInSlip(fromDate, toDate, instrumentType);
		return new ResponseEntity<>(list,HttpStatus.OK);
		
	}
}

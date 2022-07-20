package in.ecgc.smile.erp.accounts.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.ChqDishonorEntry;
import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.service.ChqDishonorEntryService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "Cheque Dishonor Entry")
@Slf4j
@RequestMapping("/chq-dishonor-entry")
public class ChqDishonorEntryController {

	@Autowired
	private ChqDishonorEntryService chqDishonorEntryservice;

	@GetMapping("/view-all")
	@ApiOperation(value = "List All Cheque Dishonor Details", response = ResponseModel.class)
	public ResponseEntity<List<ChqDishonorEntry>> getChqDishonorEntryList() {
		log.info("Inside ChqDishonorEntryController#getChqDishonorEntryList ");
		List<ChqDishonorEntry> result = chqDishonorEntryservice.getChqDishonorEntryList();
		log.info("List of All Cheque Dishonor Details {}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/add")
	@ApiOperation(value = "Add Cheque Dishonor Details", response = ResponseModel.class)
	public ResponseEntity<Boolean> addChqDishonorEntryData(@RequestBody @Valid ChqDishonorEntry chqDishonorEntry) {
		log.info("Inside ChqDishonorEntryController#addChqDishonorEntryData ");
		log.info("Add New ChqDishonorEntry {}", chqDishonorEntry);
		boolean result = chqDishonorEntryservice.addChqDishonorEntryData(chqDishonorEntry);
		log.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/update")
	@ApiOperation(value = "Update Cheque Dishonor Details", response = ResponseModel.class)
	public ResponseEntity<Boolean> updateChqDishonorEntryData(@RequestBody ChqDishonorEntry chqDishonorEntry) {
		log.info("Inside ChqDishonorEntryController#updateChqDishonorEntryData ");
		log.info("Modified Cheque Dishonor Details {}", chqDishonorEntry);
		boolean result = chqDishonorEntryservice.updateChqDishonorEntryData(chqDishonorEntry);
		log.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/view/{instrumentNo}")
	@ApiOperation(value = "Get Cheque Dishonor Details By Instrument No", response = ResponseModel.class)
	public ResponseEntity<ChqDishonorEntry> getChqDishonorEntryDataByChequeNo(
			@ApiParam(value = "Instrument No", required = true) @PathVariable("instrumentNo") @NotBlank String instrumentNo) {
		log.info("Inside ChqDishonorEntryController#getChqDishonorEntryDataByChequeNo ");
		ChqDishonorEntry result = chqDishonorEntryservice.getChqDishonorEntryByChequeNo(instrumentNo);
		log.info("Cheque Dishonor Details {}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/view/{instrumentType}/{instrumentNumber}/{instrumentDate}/{receiptNumber}")
	@ApiOperation(value = "View  Cheque Details by LogicalLocCode and ReceiptNumber", response = ResponseModel.class)
	public ResponseEntity<Receipt> viewByLogicalLocCodeAndReceiptNo(
			@ApiParam(value = "Instrument Type", required = true) @PathVariable("instrumentType") @NotBlank String instrumentType,
			@ApiParam(value = "Instrument Number", required = true) @PathVariable("instrumentNumber") @NotBlank String instrumentNumber,
			@ApiParam(value = "Instrument Date", required = true) @PathVariable("instrumentDate") @NotBlank String instrumentDate,
			@ApiParam(value = "Receipt Number", required = true) @PathVariable("receiptNumber") @NotBlank Integer receiptNumber) {
		log.info("Inside ChqDishonorEntryController#viewByLogicalLocCodeAndReceiptNo ");
		LocalDate localDate = LocalDate.parse(instrumentDate);
		Receipt receiptDetails = chqDishonorEntryservice.viewByChqNoChqTypeChqDtRcptNo(instrumentType, instrumentNumber,
				localDate, receiptNumber);
		log.info("Receipt Details {}", receiptDetails);
		return new ResponseEntity<Receipt>(receiptDetails, HttpStatus.OK);
	}

	@GetMapping("/viewInstType/{instrumentType}")
	@ApiOperation(value = "View Receipt by InstrumentType", response = ResponseModel.class)
	public ResponseEntity<List<Receipt>> viewByInstrumentType(
			@ApiParam(value = "InstrumentType", required = true) @PathVariable("instrumentType") @NotBlank String instrumentType) {
		log.info("Inside ChqDishonorEntryController#viewByInstrumentType ");
		List<Receipt> recieptList = chqDishonorEntryservice.viewByInstrumentType(instrumentType);
		log.info("List of Receipts {}", recieptList);
		if (recieptList != null) {
			return new ResponseEntity<>(recieptList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Receipt>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/view/{logicalLocCode}/{receiptNumber}")
	@ApiOperation(value = "View Cheque Details by Logical Location Code and Receipt Number", response = ResponseEntity.class)
	public ResponseEntity<ChqDishonorEntry>viewByLogicalLocCodeAndReceiptNo(
			@ApiParam(value = "Logical Location Code", required = true)
			@PathVariable("logicalLocCode") @NotBlank String logicalLocCode,
			@ApiParam(value = "Receipt Number", required = true)
			@PathVariable("receiptNumber")@NotBlank Integer receiptNumber
			)
	{
	
		log.info("Inside ChqDishonorEntryController#getChqDishonorEntryDataByChequeNo ");
		ChqDishonorEntry result = chqDishonorEntryservice.viewByLogicalLocCodeAndReceiptNo(logicalLocCode, receiptNumber);
		log.info("Cheque Dishonor Details {}", result);
		if(result !=null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else 
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
	
	

}

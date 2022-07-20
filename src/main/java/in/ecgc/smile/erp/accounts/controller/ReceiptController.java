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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.model.States;
import in.ecgc.smile.erp.accounts.repository.ReceiptDao;
import in.ecgc.smile.erp.accounts.service.EntityGLMasterService;
import in.ecgc.smile.erp.accounts.service.ReceiptService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/receipt")
@Api(value = "Receipt service")
@Slf4j
public class ReceiptController {

	@Autowired
	ReceiptService receiptService;
	@Autowired
	ReceiptDao receiptDao;
	
	@Autowired 
	EntityGLMasterService entityGlService;
	
	@PostMapping("/add")
	@ApiOperation(value = "Add Receipt Detail", response = ResponseModel.class)
	public ResponseEntity<Integer> addReceipt(@Valid @RequestBody Receipt receipt) {
		log.info("Inside ReceiptController#addReceipt ");
		log.info("New Receipt Details {} ",receipt);
		ResponseEntity<Integer> model ;
		Integer result;
		try {
			result = receiptService.addReceipt(receipt);
			if(result !=0) {
				model = new ResponseEntity<>(result,HttpStatus.CREATED);
			}else {
				model = new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);
			}
		} 
		catch (Exception e) {
			model = new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);
			log.error("Controller Level, Exception while adding receipt {}",e);
		}
		return model;
	}

	@GetMapping("/view-states")
	@ApiOperation(value="View All States" ,response = ResponseEntity.class)
	public ResponseEntity<List<States>>getAllStates(){
		log.info("Inside ReceiptController#getAllStates ");
		List<States> stateList= receiptService.getAllStates();
		log.info("List of All  States {} ",stateList);
		if (stateList != null) {
			return new ResponseEntity<>(stateList ,HttpStatus.OK);		
		}else {
			return new ResponseEntity<List<States>>(HttpStatus.NO_CONTENT);
		}		
	}
	
	@GetMapping("/view-all")
	@ApiOperation(value = "View All Receipts" ,response = ResponseEntity.class)
	public ResponseEntity<List<Receipt>> listAllReceipts(){
		
	log.info("Inside ReceiptController#listAllReceipts ");
	List<Receipt> allReceipts = receiptService.listAllReceipts();
	
		if (allReceipts != null) {
			return new ResponseEntity<>(allReceipts ,HttpStatus.OK);
			
			
		}else {
			return new ResponseEntity<List<Receipt>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/viewReceipt/{logicalLocCode}")
	@ApiOperation(value = "View Receipt by Logical Location Code", response = ResponseEntity.class)
	public ResponseEntity<List<Receipt>>viewByLogicalLocCode(
			@ApiParam(value = "Logical Location Code" ,required = true)
			@PathVariable("logicalLocCode") @NotBlank String logicalLocCode){
		log.info("Inside ReceiptController#viewByLogicalLocCode ");
		List<Receipt> recieptList= receiptService.viewByLogicalLocCode(logicalLocCode);
		log.info("List of All Receipt Details {} ",recieptList);
		if(recieptList!= null) {
			return new ResponseEntity<>(recieptList, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<Receipt>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/view/{logicalLocCode}/{receiptNumber}")
	@ApiOperation(value = "View Receipt by Logical Location Code and Receipt Number", response = ResponseEntity.class)
	public ResponseEntity<Receipt>viewByLogicalLocCodeAndReceiptNo(
			@ApiParam(value = "Logical Location Code", required = true)
			@PathVariable("logicalLocCode") @NotBlank String logicalLocCode,
			@ApiParam(value = "Receipt Number", required = true)
			@PathVariable("receiptNumber")@NotBlank Integer receiptNumber
			)
	{
		log.info("Inside ReceiptController#viewByLogicalLocCodeAndReceiptNo ");
		Receipt receiptDetails = receiptService.viewByLogicalLocCodeAndReceiptNo(logicalLocCode, receiptNumber);
		log.info("Receipt Details {} ",receiptDetails);
		return new ResponseEntity<Receipt>(receiptDetails , HttpStatus.OK);
	}
	
	@PutMapping(value ="/update/{logicalLocCode}/{receiptNumber}")
	@ApiOperation(value = "Update Receipt" , response = ResponseEntity.class)
	public ResponseEntity<Integer> updateReceipt(
			@ApiParam(value = "Logical Location Code" , required = true)
			@PathVariable("logicalLocCode")@NotBlank String logicalLocCode,
			@ApiParam(value = "Receipt Number" , required = true)			
			@PathVariable("receiptNumber")@NotBlank Integer receiptNumber,
			@RequestBody Receipt receiptUpdate)
	{
		log.info("Inside ReceiptController#updateReceipt ");
		Integer result = receiptService.updateReceipt(logicalLocCode, receiptNumber, receiptUpdate);
		log.info("Update Status {} ",result);
		return new ResponseEntity<Integer> (result ,HttpStatus.OK);
	}
	
	@PutMapping(value ="/update-flag/{logicalLocCode}/{receiptNumber}/{printFlag}")
	@ApiOperation(value = "Update Flag" , response = ResponseEntity.class)
	public ResponseEntity<Integer> updatePrintFlag(
			@ApiParam(value = "Logical Location Code" , required = true)
			@PathVariable("logicalLocCode")@NotBlank String logicalLocCode,
			@ApiParam(value = "Receipt Number" , required = true)			
			@PathVariable("receiptNumber")@NotBlank Integer receiptNumber,
			@ApiParam(value = "Print Flag" , required = true)			
			@PathVariable("printFlag")@NotBlank String printFlag)
	{
		log.info("Inside ReceiptController#updatePrintFlag ");
		Integer result = receiptService.updatePrintFlag(logicalLocCode, receiptNumber, printFlag);
		log.info("Update Status {} ",result);
		return new ResponseEntity<Integer> (result ,HttpStatus.OK);
	}
	
	@GetMapping(value="/get-flag/{logicalLocCode}/{receiptNumber}")
	@ApiOperation(value="Get Print flag",response = ResponseEntity.class)
	public ResponseEntity<String> getFlag(
			@ApiParam(value = "Logical Location Code" , required = true)
			@PathVariable("logicalLocCode")@NotBlank String logicalLocCode,
			@ApiParam(value = "Receipt Number" , required = true)			
			@PathVariable("receiptNumber")@NotBlank Integer receiptNumber)
	{
		log.info("Inside ReceiptController#getFlag ");
		String result = receiptService.getFlag(logicalLocCode, receiptNumber);
		log.info("Get Flag {} ",result);
		return new ResponseEntity<String> (result ,HttpStatus.OK);
	}
	
	@GetMapping("getglcode/{branchGstin}/{customergstin}")
	public ResponseEntity<List<EntityGL>> getGlCode(
			@ApiParam(value="branchGstin" , required = true)
			@PathVariable("branchGstin")@NotBlank String branchGstin, 
			@ApiParam(value="customergstin" , required = true)
			@PathVariable ("customergstin") @NotBlank String customergstin)
	{		
		log.info("branch Gstin {} , Customer GSting {} ",branchGstin, customergstin ); 
		
		return new ResponseEntity<List<EntityGL>>(entityGlService.getGstGlCodes(branchGstin, customergstin), HttpStatus.OK);	
		
	}
	
	
	
}

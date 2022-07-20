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

import in.ecgc.smile.erp.accounts.exception.ImproperFtrDataProvidedException;
import in.ecgc.smile.erp.accounts.model.FTR;
import in.ecgc.smile.erp.accounts.service.FtrRefundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ftr-refund")
@Api(value = "Fund transfer refund service")
@Slf4j
public class FtrRefundController {
	
	@Autowired
	FtrRefundService ftrRefundService; 

	@PostMapping("/add")
	@ApiOperation(value = "Add New FTR Refund Request", response = ResponseEntity.class)
	public ResponseEntity<Integer> addRefundRequest(@RequestBody FTR ftr ){
		log.info("Inside FtrRefundController#addRefundRequest ");
		ResponseEntity<Integer> model;
		try {
			log.info("New FTR Refund Request {} ",ftr);	
			int reqNo=0;
			if ((reqNo = ftrRefundService.addRefundRequest(ftr)) > 0) {
				model = new ResponseEntity<Integer>(reqNo ,HttpStatus.ACCEPTED);
				log.debug("Request Number {}",reqNo);
				}
			else {
				model = new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
			}
		}
		catch (Exception e) {
			model = new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRException ..Incomplete FTR Data provided : {}",e);
		}
		return model;
	}
		
	@GetMapping("/getallFTR/{logicalLoc}")
	@ApiOperation(value = "Get All FTR", response = ResponseEntity.class)
	public ResponseEntity<List<FTR>> getAllFTRForBranch(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLoc") @NotBlank String logicalLoc) {
		log.info("Inside FtrRefundController#getAllFTRForBranch ");
		ResponseEntity<List<FTR>> model;
		List<FTR> allFTR = null ;
		try {
			if ((allFTR = ftrRefundService.getAllFTRRequest(logicalLoc)) != null) {
				model = new ResponseEntity<List<FTR>>(allFTR,HttpStatus.OK);
				log.debug("List of All FTR {}",allFTR);
				}
			else {
				model = new ResponseEntity<List<FTR>>(HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			model = new ResponseEntity<List<FTR>>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRCodeException ..failed to fetch : {}",e);
		}
		return model;
	}

	@GetMapping("/getftr/{ftrReqNo}")
	@ApiOperation(value = "Get FTR Refund Details", response = ResponseEntity.class)
	public ResponseEntity<FTR> getFtrDtl(
			@ApiParam(value = "Fund Transfer Request Number", required = true) @PathVariable("ftrReqNo") @NotBlank String FTRId) {
		log.info("Inside FtrRefundController#getFtrDtl ");
		ResponseEntity<FTR> model;
		FTR ftrDtl = null;
		try {
			if ((ftrDtl = ftrRefundService.getFTRRequestDTL(FTRId)) != null) {
				model = new ResponseEntity<FTR>(ftrDtl,HttpStatus.OK);
				log.debug("FTR Request Details {} ",ftrDtl );
				}
			else {
				model = new ResponseEntity<FTR>(HttpStatus.BAD_REQUEST);
			}
		}
		catch (Exception e) {
			model = new ResponseEntity<FTR>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRException ..failed to fetch : {}",e.getMessage());
		}
		return model;


	}
	
	@PostMapping("/decision")
	@ApiOperation(value = "Decision on FTR Refung Request", response = ResponseEntity.class)
	public ResponseEntity<Integer> decisionOnFtrRefund(@RequestBody FTR ftr ){
		log.info("Inside FtrRefundController#decisionOnFtrRefund ");
		ResponseEntity<Integer> model;
		try {
			log.info("FTR Deatils {}",ftr);
			if ( ftrRefundService.decisionOnFTRRequest(ftr) == 1) {
				model = new ResponseEntity<Integer>(1,HttpStatus.ACCEPTED);
				log.debug("Desion Taken Successfully");
				}
			else {
				model= new ResponseEntity<Integer>(0,HttpStatus.BAD_REQUEST);
				log.debug("Unable To Take Desion");
			}
		}
		catch (ImproperFtrDataProvidedException e) {  
			model= new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRException ..Incomplete FTR Data provided : {}",e);
		}
		catch (Exception e) {  
			model= new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRException ..Incomplete FTR Data provided : {} ",e);
			
		}
		return model;
	}
	
	
}

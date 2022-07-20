/**
 * 
 */
package in.ecgc.smile.erp.accounts.controller;

import java.util.List;
import java.util.Map;

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

import in.ecgc.smile.erp.accounts.exception.GlTxnNotFoundException;
import in.ecgc.smile.erp.accounts.exception.RecordNotFoundException;
import in.ecgc.smile.erp.accounts.exception.TCodeNotPresentException;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.Liability;
import in.ecgc.smile.erp.accounts.model.LiabilityGLMapping;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;
import in.ecgc.smile.erp.accounts.service.LiabilityService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * @author CMJ-SENG-LAPTOP9
 *
 */

@RestController
@RequestMapping("/liability")
@Api(value = "Liability service")
@Slf4j
public class LiabilityController {


	@Autowired
	LiabilityService liabilityService;
	
	@PostMapping("/add")
	@ApiOperation(value = "Create Liability", response = ResponseEntity.class)
	@ApiResponses(value = @ApiResponse(code = 500,message = "Something went wrong"))
	public ResponseEntity<GlTxnHdr> createLiability(@RequestBody Liability liability) throws RecordNotFoundException, TCodeNotPresentException {
		log.info("Inside LiabilityController#createLiability ");
		log.info("Received Liability Obj : {}",liability);
		
		GlTxnHdr glTxnHdr = liabilityService.createLiability(liability);
		if (glTxnHdr!=null) {
			return new ResponseEntity<>(glTxnHdr,HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/get-mapping/{moduleCd}/{mappingCd}")
	@ApiOperation(value = "View All Liability GL Mappings for Module Code and Mapping Code", response = ResponseEntity.class)
	public ResponseEntity<List<LiabilityGLMapping>> getModuleWiseMapping(
			@ApiParam(value = "Module Code", required = true) @PathVariable("moduleCd") @NotBlank String moduleCd,
			@ApiParam(value = "Mapping Code", required = true) @PathVariable("mappingCd") @NotBlank String mappingCd) throws GlTxnNotFoundException{
		log.info("Inside LiabilityController#getModuleWiseMapping ");
		return new ResponseEntity<List<LiabilityGLMapping>>(liabilityService.getMapping(moduleCd, mappingCd),HttpStatus.OK);
	}
	
	@GetMapping("/get-mapping/{moduleCd}")
	@ApiOperation(value = "View All Liability GL Mappings for Module Code", response = ResponseEntity.class)
	public ResponseEntity<List<LiabilityGLMapping>> getAllGltxnByFromDt(
			@ApiParam(value = "Module Code", required = true) @PathVariable("moduleCd") @NotBlank String moduleCd) throws GlTxnNotFoundException{
			log.info("Inside LiabilityController#getAllGltxnByFromDt ");
			return new ResponseEntity<List<LiabilityGLMapping>>(liabilityService.getMapping(moduleCd),HttpStatus.OK);
	}
	
	@GetMapping("/get-all-mapping")
	@ApiOperation(value = "View All Liability GL Mappings", response = ResponseEntity.class)
	public ResponseEntity<List<LiabilityGLMapping>> getAllGltxnByFromDt() throws GlTxnNotFoundException{
		log.info("Inside LiabilityController#getAllGltxnByFromDt ");
		return new ResponseEntity<List<LiabilityGLMapping>>(liabilityService.getMapping(),HttpStatus.OK);
	}
	
	@PostMapping("/add-mapping")
	@ApiOperation(value = "Create Liability GL Mappings", response = ResponseEntity.class)
	public ResponseEntity<Integer> addGLTxn(@RequestBody List<LiabilityGLMapping> glMapping) {
		log.info("Inside LiabilityController#addGLTxn ");
		System.err.println("Liability object is "+glMapping);
		return new ResponseEntity<>(liabilityService.addMApping(glMapping),HttpStatus.CREATED);
	}
	
	@GetMapping("/distinct-module-cd")
	@ApiOperation(value = "View All Module Codes", response = ResponseEntity.class)
	public ResponseEntity<List<String>> getAllModuleCds(){
		log.info("Inside LiabilityController#getAllModuleCds ");
		return new ResponseEntity<List<String>>(liabilityService.distinctModuleCd(),HttpStatus.OK);
	}
	

	@GetMapping("getMappingCd-name/{moduleCd}")	
	@ApiOperation(value = "View Mapping Code and Mapping Name for Module Codes", response = ResponseEntity.class)
	public ResponseEntity<Map<String,String>> getMappingCdAndMappingNameForModuleCd(
		@ApiParam(value = "Module Code", required = true) @PathVariable("moduleCd") @NotBlank String moduleCd){
		log.info("Inside LiabilityController#getMappingCdAndMappingNameForModuleCd ");
		return new ResponseEntity<Map<String,String>>(liabilityService.getAllMappingCodeAndMappingNameforModuleCd(moduleCd), HttpStatus.OK);
	}
	
	@GetMapping("getApplicableTcodes/{moduleCd}/{mappingCd}")
	@ApiOperation(value = "Get All Applicable Tcodes", response = ResponseEntity.class)
	public ResponseEntity<List<String>> getApplicableTcodes(
			@ApiParam (value = "Module Code", required = true) @PathVariable("moduleCd") @NotBlank String moduleCd,
			@ApiParam (value = "Mapping Code", required = true) @PathVariable("mappingCd") @NotBlank String mappingCd){
		log.info("Inside LiabilityController#getApplicableTcodes ");
		return new ResponseEntity<List<String>>(liabilityService.getRequiredTCodes(moduleCd, mappingCd),HttpStatus.OK);
	}
	
	@GetMapping("getRequiredTCodesByGLCodes/{mainGlCd}/{subGlCd}")
	@ApiOperation(value = "Get All Applicable Tcodes by mainGl and subGl", response = ResponseEntity.class)
	public ResponseEntity<List<String>> getRequiredTCodesByGLCodes(
			@ApiParam (value = "maingl Code", required = true) @PathVariable("mainGlCd") @NotBlank String mainGlCd,
			@ApiParam (value = "subgl Code", required = true) @PathVariable("subGlCd") @NotBlank String subGlCd){
	
		
			log.info("Inside LiabilityController#getApplicableTcodesbyGlCodes");
			return new ResponseEntity<List<String>>(liabilityService.getRequiredTCodesByGLCodes(mainGlCd, subGlCd),HttpStatus.OK);
		}
		
	
	@PostMapping("/update")
	@ApiOperation(value = "Update Module and Mapping Code Details")
	public ResponseEntity<Integer> updateAccountGLMapping(@RequestBody List<LiabilityGLMapping> moduleMappingCodes ) {
         
         log.info("Inside controller updateTCodes "+moduleMappingCodes);
 	return new ResponseEntity<Integer>(liabilityService.updateAccountGLMapping(moduleMappingCodes), HttpStatus.CREATED);
	}
	
}

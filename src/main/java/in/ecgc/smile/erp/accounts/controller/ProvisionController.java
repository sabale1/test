package in.ecgc.smile.erp.accounts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.exception.GlTxnNotFoundException;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.Provision;
import in.ecgc.smile.erp.accounts.model.ProvisionGLMapping;
import in.ecgc.smile.erp.accounts.service.ProvisionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/provision")
@Api(value = "Provision service")
@Slf4j
public class ProvisionController {

	@Autowired
	ProvisionService provisionService;

	@PostMapping("/add")
	@ApiOperation(value = "Create Provision", response = ResponseEntity.class)
	public ResponseEntity<GlTxnHdr> createProvision(@RequestBody Provision provision) {
		log.info("Inside ProvisionController#createProvision ");
		log.info("New Provision Entry : {}", provision);
		ResponseEntity<GlTxnHdr> model;
		GlTxnHdr result;
		try {
			result = provisionService.createProvision(provision);
			log.info("GlTxnHdr : {}", result);
			if (result != null) {
				model = new ResponseEntity<>(result, HttpStatus.CREATED);
			} else {
				model = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			model = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			log.error("Exception while creating provision : {}", e);
			log.error("Exception thrown- GLTxnIncompleteDataException..Incomplete GL Data Data provided");
		}
		return model;

	}

	@GetMapping("/get-mapping/{moduleCd}/{mappingCd}")
	@ApiOperation(value = "View Provision GL Mapping for Module by Module Code and Mapping Code", response = ResponseEntity.class)
	public ResponseEntity<List<ProvisionGLMapping>> getAllGltxnByFromDt(
			@ApiParam(value = "Module Code", required = true) @PathVariable("moduleCd") String moduleCd,
			@ApiParam(value = "Mapping Code", required = true) @PathVariable("mappingCd") String mappingCd)
			throws GlTxnNotFoundException {
		log.info("Inside ProvisionController#getAllGltxnByFromDt ");
		ResponseEntity<List<ProvisionGLMapping>> responseModel;
		List<ProvisionGLMapping> mapping = provisionService.getMapping(moduleCd, mappingCd);
		log.info("List of  Provision Gl Mappings : {}", mapping);
		if (mapping != null) {
			responseModel = new ResponseEntity<List<ProvisionGLMapping>>(mapping, HttpStatus.OK);
			log.info("success: response returning Provision mapping");
		} else {
			responseModel = new ResponseEntity<List<ProvisionGLMapping>>(HttpStatus.BAD_REQUEST);
			log.info("No entries found");
		}
		return responseModel;
	}

	@GetMapping("/get-mapping/{moduleCd}")
	@ApiOperation(value = "View Provision GL Mapping for Module by Module Code", response = ResponseEntity.class)
	public ResponseEntity<List<ProvisionGLMapping>> getAllGltxnByFromDt(
			@ApiParam(value = "Module Code", required = true) @PathVariable("moduleCd") String moduleCd)
			throws GlTxnNotFoundException {
		log.info("Inside ProvisionController#getAllGltxnByFromDt ");
		ResponseEntity<List<ProvisionGLMapping>> responseModel;
		List<ProvisionGLMapping> mapping = provisionService.getMapping(moduleCd);
		log.info("List of  Provision Gl Mappings : {}", mapping);
		if (mapping != null) {
			responseModel = new ResponseEntity<List<ProvisionGLMapping>>(mapping, HttpStatus.OK);
			log.info("success: response returning provision mapping");
		} else {
			responseModel = new ResponseEntity<List<ProvisionGLMapping>>(HttpStatus.BAD_REQUEST);
			log.info("No entries found");
		}
		return responseModel;
	}

	@GetMapping("/get-all-mapping")
	@ApiOperation(value = "View All Provision GL Mapping for Module", response = ResponseEntity.class)
	public ResponseEntity<List<ProvisionGLMapping>> getAllGltxnByFromDt() throws GlTxnNotFoundException {
		log.info("Inside ProvisionController#getAllGltxnByFromDt ");
		ResponseEntity<List<ProvisionGLMapping>> responseModel;
		List<ProvisionGLMapping> mapping = provisionService.getMapping();
		log.info("List of  Provision Gl Mappings : {}", mapping);
		if (mapping != null) {
			responseModel = new ResponseEntity<List<ProvisionGLMapping>>(mapping, HttpStatus.OK);
			log.info("success: response returning provision mapping");
		} else {
			responseModel = new ResponseEntity<List<ProvisionGLMapping>>(HttpStatus.BAD_REQUEST);
			log.info("No entries found");
		}
		return responseModel;
	}

	@PostMapping("/add-mapping")
	@ApiOperation(value = "Create Provosion GL Mapping", response = ResponseEntity.class)
	public ResponseEntity<Integer> addGLTxn(@RequestBody List<ProvisionGLMapping> glMapping) {
		log.info("Inside ProvisionController#addGLTxn ");
		log.info("List of New Provision Gl Mapping Entries : {}", glMapping);
		ResponseEntity<Integer> model;
		Integer result;
		try {
			result = provisionService.addMApping(glMapping);
			if (result >= 1) {
				model = new ResponseEntity<>(result, HttpStatus.CREATED);
			} else {
				model = new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			model = new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
			log.error("Exception while adding mapping {}", e);
			log.error("Exception thrown- MAppingNotCreatedException..Incomplete Data Data provided");
		}
		return model;

	}

}

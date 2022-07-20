package in.ecgc.smile.erp.accounts.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.service.EntityGLMasterService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gl-master")
@Api(value = "GL Master service")
@Slf4j

public class EntityGlMasterController {

	@Autowired
	EntityGLMasterService entityGLMasterService;

	/**
	 * Add new GL code
	 * 
	 * @RequestBody {@link in.ecgc.smile.erp.accounts.model.EntityGL} Model Object
	 * @return {@link in.ecgc.smile.erp.accounts.util} ResponseModel Class Object
	 */
	@PostMapping("/add")
	@ApiOperation(value = "Add New GL code", response = ResponseModel.class)
	public ResponseEntity<Integer> addGLCode(@RequestBody @Valid EntityGL entityGL) {
		log.info("Inside EntityGlMasterController#addGLCode ");
		log.info("Adding New GL Code {}", entityGL);
		return new ResponseEntity<>(entityGLMasterService.addGLCode(entityGL), HttpStatus.CREATED);
	}

	/**
	 * Update GL code
	 * 
	 * @PathVariable entityGLCode which is to be updated
	 * @RequestBody entity GL model which modified properties
	 * @return {@link in.ecgc.smile.erp.accounts.util} ResponseModel Class Object
	 */
	@PutMapping("/update/{mainGLCode}/{subGLCode}")
	@ApiOperation(value = "Update GL code", response = ResponseModel.class)
	public ResponseEntity<EntityGL> updateGLCode(
			@ApiParam(value = "Main GL Code", required = true) @PathVariable("mainGLCode") @Size(min = 4, max = 4, message = "Main GL Should be of 4 Characters") String mainGLCode,
			@ApiParam(value = "Sub GL Code", required = true) @PathVariable("subGLCode") @Size(min = 3, max = 3, message = "Sub GL Should be of 3 Characters") String subGLCode,
			@RequestBody EntityGL updatedEntityGL) {
		log.info("Inside EntityGlMasterController#updateGLCode ");
		log.info("Update GL Code for Main GL {} Sub GL {}", mainGLCode, subGLCode);
		log.info("Updated Entity GL Code {}", updatedEntityGL);
		EntityGL currentEntityGL = entityGLMasterService.findGLByGLCode(mainGLCode, subGLCode);
		EntityGL modifiedEntity = entityGLMasterService.updateGLCode(currentEntityGL, updatedEntityGL);
		return new ResponseEntity<>(modifiedEntity, HttpStatus.ACCEPTED);
	}

	/**
	 * View all GL codes return List of all GL codes present in the GL master table
	 * 
	 * @return {@link in.ecgc.smile.erp.accounts.util} ResponseModel Class Object
	 */
	@GetMapping("/view-all")
	@ApiOperation(value = "View All GL Codes", response = ResponseModel.class)
	public ResponseEntity<List<EntityGL>> listAllGLCodes() {
		log.info("Inside EntityGlMasterController#listAllGLCodes ");
		log.info("Reading All GL Codes");
		return new ResponseEntity<>(entityGLMasterService.listAllGLCodes(), HttpStatus.OK);
	}

	/**
	 * disable given GL code
	 * 
	 * @PathVariable entityGLCode which is to be deleted return entityGLCode which
	 *               is deleted
	 * @return {@link in.ecgc.smile.erp.accounts.util} ResponseModel Class Object
	 */
	@DeleteMapping("/delete/{mainGLCode}/{subGLCode}")
	@ApiOperation(value = "Delete Given GL Code", response = ResponseModel.class)
	public ResponseEntity<Integer> disableGLCode(
			@ApiParam(value = "Main Gl Code", required = true) @PathVariable("mainGLCode") @Size(min = 4, max = 4, message = "Main GL Should be of 4 Characters") String mainGLCode,
			@ApiParam(value = "Sub Gl Code", required = true) @PathVariable("subGLCode") @Size(min = 3, max = 3, message = "Sub GL Should be of 3 Characters") String subGLCode) {
		log.info("Inside EntityGlMasterController#disableGLCode ");
		log.info("Disable GL Code for Main GL {} Sub GL {}", mainGLCode, subGLCode);
		EntityGL entityGL = entityGLMasterService.findGLByGLCode(mainGLCode, subGLCode);
		return new ResponseEntity<>(entityGLMasterService.disableGLCode(entityGL), HttpStatus.OK);
	}

	/**
	 * View GL code details by entityGLCode return details of GL code present in the
	 * GL master table
	 * 
	 * @return {@link in.ecgc.smile.erp.accounts.util} ResponseModel Class Object
	 */
	@GetMapping("/view/{mainGLCode}/{subGLCode}")
	@ApiOperation(value = "View  by  GL Code", response = ResponseModel.class)
	public ResponseEntity<EntityGL> findGLByEntityGLCode(
			@ApiParam(value = "Main Gl Code", required = true) @PathVariable("mainGLCode") @Size(min = 4, max = 4, message = "Main GL Should be of 4 Characters") String mainGLCode,
			@ApiParam(value = "Sub Gl Code", required = true) @PathVariable("subGLCode") @Size(min = 3, max = 3, message = "Sub GL Should be of 3 Characters") String subGLCode) {
		log.info("Inside EntityGlMasterController#findGLByEntityGLCode ");
		log.info("Read GL Code for Main GL {} Sub GL {}", mainGLCode, subGLCode);
		return new ResponseEntity<>(entityGLMasterService.findGLByGLCode(mainGLCode, subGLCode), HttpStatus.OK);
	}

	@GetMapping("/get-all-by-mainGLCode/{mainGlCode}")
	@ApiOperation(value = "View all GL codes", response = ResponseModel.class)
	public ResponseEntity<List<EntityGL>> getAllMainGLCodes(
			@ApiParam(value = "Main Gl Code", required = true) @PathVariable("mainGlCode") @NotBlank String mainGlCode) {
		log.info("Inside EntityGlMasterController#getAllMainGLCodes ");
		log.info("Reading All GL Codes");
		return new ResponseEntity<>(entityGLMasterService.getsubGLCodebyMainGLCode(mainGlCode), HttpStatus.OK);
	}
	//TODO Method Name it returns all non group GL Codes
	@GetMapping("/view-all-isglname")
	@ApiOperation(value = "View All GL Codes", response = ResponseModel.class)
	public ResponseEntity<List<EntityGL>> listAllGLbyIsGlName() {
		log.info("Inside EntityGlMasterController#listAllGLbyIsGlName ");
		log.info("Reading All GL Codes");
		return new ResponseEntity<>(entityGLMasterService.getAllGlByIsGlGroup(), HttpStatus.OK);
	}
	
	@PutMapping("/activate/{mainGLCode}/{subGLCode}")
	@ApiOperation(value = "Activate Given GL Code", response = ResponseModel.class)
	public ResponseEntity<Integer> activateGLCode(
			@ApiParam(value = "Main Gl Code", required = true) @PathVariable("mainGLCode") @Size(min = 4, max = 4, message = "Main GL Should be of 4 Characters") String mainGLCode,
			@ApiParam(value = "Sub Gl Code", required = true) @PathVariable("subGLCode") @Size(min = 3, max = 3, message = "Sub GL Should be of 3 Characters") String subGLCode) {
		log.info("Inside EntityGlMasterController#activateGLCode ");
		log.info("Activate GL Code for Main GL {} Sub GL {}", mainGLCode, subGLCode);
		EntityGL entityGL = entityGLMasterService.findGLByGLCode(mainGLCode, subGLCode);
		return new ResponseEntity<>(entityGLMasterService.activateGLCode(entityGL), HttpStatus.OK);
	}

	@GetMapping("/getGlTxnDtl/{mainGLCode}/{subGLCode}")
	@ApiOperation(value = "Activate Given GL Code", response = ResponseModel.class)
	public ResponseEntity<List<GlTxnDtl>> getAllGLtxnDtl(
			@ApiParam(value = "Main Gl Code", required = true) @PathVariable("mainGLCode") @Size(min = 4, max = 4, message = "Main GL Should be of 4 Characters") String mainGLCode,
			@ApiParam(value = "Sub Gl Code", required = true) @PathVariable("subGLCode") @Size(min = 3, max = 3, message = "Sub GL Should be of 3 Characters") String subGLCode) {
		log.info("Inside EntityGlMasterController# getAllGLtxnDtl ");
		log.info("GET all Details for Main GL {} Sub GL {}", mainGLCode, subGLCode);
	
		return new ResponseEntity<>(entityGLMasterService.getAllGlTxn(mainGLCode, subGLCode), HttpStatus.OK);
	}

}

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

import in.ecgc.smile.erp.accounts.model.GLTxnType;
import in.ecgc.smile.erp.accounts.service.GLTxnTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gl-txn-type-master")
@Api(value = "GL Transaction type Master service")
@Slf4j
public class GLTxnTypeMasterController {
	@Autowired
	GLTxnTypeService glTxnTypeService;

	/**
	 * Add new GL transaction type code
	 * 
	 * @RequestBody {@link in.ecgc.smile.erp.accounts.model.GLTxnType} Model Object
	 */
	@PostMapping()
	@ApiOperation(value = "Add New GL Txn Type Code", response = ResponseEntity.class)
	public ResponseEntity<GLTxnType> addGlTxnTypeCode(@Valid @RequestBody GLTxnType glTxnType) {
		log.info("Inside GLTxnTypeMasterController#addGlTxnTypeCode");
		log.info("Add New GL Txn Type : {}", glTxnType.toString());
		glTxnTypeService.addGLTxnTypeCode(glTxnType);
		return new ResponseEntity<>(glTxnType, HttpStatus.CREATED);
	}

	/**
	 * View GL transaction type code details by glTxnType return details of GL
	 * transaction type code present in the GL transaction type master table
	 */
	@GetMapping("/{glTxnTypeCode}")
	@ApiOperation(value = "View GL Transaction Type Code by GL Transaction Type Code", response = ResponseEntity.class)
	public ResponseEntity<GLTxnType> findGlTxnTypeByGlTxnTypeCode(
			@ApiParam(value = "GL Transaction Type Code", required = true) @PathVariable("glTxnTypeCode") @NotBlank @Size(max = 3) String glTxnTypeCode) {
		log.info("Inside GLTxnTypeMasterController#findGlTxnTypeByGlTxnTypeCode");
		log.info("View GL Txn Type Details for : {}", glTxnTypeCode);
		GLTxnType glTxnType = glTxnTypeService.findGlTxnTypeByGlTxnTypeCode(glTxnTypeCode);
		log.info("View GL Txn Type returned : {}", glTxnType);
		return new ResponseEntity<>(glTxnType, HttpStatus.OK);
	}

	/**
	 * View all GL transaction types codes return List of all GL transaction types
	 * codes present in the GL txn type master table
	 */
	@GetMapping()
	@ApiOperation(value = "View All GL Transaction Type Codes", response = ResponseEntity.class)
	public ResponseEntity<List<GLTxnType>> listAllGlTxnTypeCodes() {
		log.info("Inside GLTxnTypeMasterController#listAllGlTxnTypeCodes");
		List<GLTxnType> allGlTxnTypeCodes = glTxnTypeService.listAllGLTxnTypeCodes();
		log.info("List of ALl GL Txn Type Codes {}", allGlTxnTypeCodes);
		if (allGlTxnTypeCodes != null) {
			log.info("View ALl GL Txn Types returned list with size: {}", allGlTxnTypeCodes.size());
			return new ResponseEntity<>(allGlTxnTypeCodes, HttpStatus.OK);
		} else {
			log.info("View ALl GL Txn Types returned Empty List");
			return new ResponseEntity<List<GLTxnType>>(HttpStatus.NO_CONTENT);
		}

	}

	/**
	 * Update GL transaction type code
	 * 
	 * @PathVariable glTxnTypeCode which is to be updated
	 * @RequestBody GLTxnType model which modified properties
	 */
	@PutMapping("/{glTxnTypeCode}")
	@ApiOperation(value = "Update GL Transaction Type Code", response = ResponseEntity.class)
	public ResponseEntity<Integer> updateGLTxnTypeCode(
			@ApiParam(value = "GL Transaction Type Code", required = true) @PathVariable("glTxnTypeCode") @NotBlank @Size(max = 3) String glTxnTypeCode,
			@RequestBody GLTxnType updatedGlTxnType) {
		// FIXME Add and Test @Valid attribute for updatedGlTxnType
		log.info("Inside GLTxnTypeMasterController#updateGLTxnTypeCode");
		log.info("Update GL Txn Type Details for : {} with new details: {}", glTxnTypeCode, updatedGlTxnType);
		Integer result = glTxnTypeService.updateGLTxnTypeCode(glTxnTypeCode, updatedGlTxnType);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	/**
	 * disable given GL transaction type code
	 * 
	 * @PathVariable glTxnTypeCode which is to be deleted
	 */
	@DeleteMapping("/{glTxnTypeCode}")
	@ApiOperation(value = "Delete Given GL Transaction Type Code", response = ResponseEntity.class)
	public ResponseEntity<Integer> disableGlTxnTypeCode(
			@ApiParam(value = "GL Transaction Type Code", required = true) @PathVariable("glTxnTypeCode") String glTxnTypeCode) {
		log.info("Inside GLTxnTypeMasterController#disableGlTxnTypeCode");
		log.info("Delete GL Txn Type : {}", glTxnTypeCode);
		Integer result = glTxnTypeService.disableGLTxnTypeCode(glTxnTypeCode);
		log.info("Delete GL Txn Type : {} returned : {}", glTxnTypeCode, result);
		return new ResponseEntity<Integer>(result, HttpStatus.ACCEPTED);

	}

	@GetMapping("/gltype")
	@ApiOperation(value = "Get List of GlTxn Type", response = ResponseEntity.class)
	public List<String> getGLTypeList() {
		log.info("Inside GLTxnTypeMasterController#getGLTypeList");
		List<String> glTxnType = glTxnTypeService.getGLTxnType();
		log.info("List of Gl Txn Type {}", glTxnType);
		return glTxnType;
	}

}

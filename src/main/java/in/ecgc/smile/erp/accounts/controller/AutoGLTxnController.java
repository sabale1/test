package in.ecgc.smile.erp.accounts.controller;

import java.util.List;

import javax.validation.Valid;
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

import in.ecgc.smile.erp.accounts.model.AutoGLTxnRequestModel;
import in.ecgc.smile.erp.accounts.service.AutoGLTxnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auto-gl-txn")
@Api(value = "Automatic GL Transaction service")
@Slf4j
public class AutoGLTxnController {
	
	@Autowired
	AutoGLTxnService autoGLTxnService;
	
	@PostMapping("/add-request")
	@ApiOperation(value = "Add Auto GL Txn Request", response = ResponseEntity.class)
	public ResponseEntity<Integer> addGlTxnRequest(@Valid @RequestBody AutoGLTxnRequestModel autoGLTxnRequestModel) {
		log.info("Inside AutoGLTxnController#addGlTxnRequest ");
		log.info("New AutoGl Transaction Request {}",autoGLTxnRequestModel);
		Integer result;
		try {
			result = autoGLTxnService.createTxnRequest(autoGLTxnRequestModel);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception in Adding Auto GL Txn {}",e.getMessage());
			return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/get-all-req")
	@ApiOperation(value = "Get All Auto GL Transaction Request", response = ResponseEntity.class)
	public ResponseEntity<List<AutoGLTxnRequestModel>> getAllAutoGLTxnRequest() {
		log.info("Inside AutoGLTxnController#getAllAutoGLTxnRequest ");
		ResponseEntity<List<AutoGLTxnRequestModel>> model;
		List<AutoGLTxnRequestModel> allAutoGLTxnRequestModel = null ;
		try {
			if ((allAutoGLTxnRequestModel = autoGLTxnService.getAllAutoTxnRequest()) != null) {
				model = new ResponseEntity<List<AutoGLTxnRequestModel>>(allAutoGLTxnRequestModel,HttpStatus.OK);
				log.info("List of All AutoGl Transaction Requests {}",allAutoGLTxnRequestModel);
				}
			else {
				model = new ResponseEntity<List<AutoGLTxnRequestModel>>(HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			model = new ResponseEntity<List<AutoGLTxnRequestModel>>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- AutoGLTxnRequestException ..failed to fetch {}",e);
		}
		return model;
	}
	
	@GetMapping("/get-req/{reqNo}")
	@ApiOperation(value = "Get AutoGLTxnRequest Details", response = ResponseEntity.class)
	public ResponseEntity<AutoGLTxnRequestModel> getAutoGLTxnRequestById(
			@ApiParam(value = "Request Number", required = true) @PathVariable("reqNo") @NotBlank String reqNo) {
		log.info("Inside AutoGLTxnController#getAutoGLTxnRequestById ");
		ResponseEntity<AutoGLTxnRequestModel> model;
		AutoGLTxnRequestModel autoGLTxnRequestModelDtl = null;
		try {
			if ((autoGLTxnRequestModelDtl = autoGLTxnService.getAllAutoTxnRequestById(reqNo)) != null) {
				model = new ResponseEntity<AutoGLTxnRequestModel>(autoGLTxnRequestModelDtl,HttpStatus.OK);
				log.info("AutoGl Transaction Request {}",autoGLTxnRequestModelDtl);
				}
			else {
				model = new ResponseEntity<AutoGLTxnRequestModel>(HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			model = new ResponseEntity<AutoGLTxnRequestModel>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- AutoGLTxnRequestModelException ..failed to fetch {}",e.getMessage());
		}
		return model;


	}
	@PostMapping("/update-request-status")
	@ApiOperation(value = "Update Auto GL Txn Request Status", response = ResponseEntity.class)
	public ResponseEntity<Integer> updateGlTxnRequestStatus(@Valid @RequestBody AutoGLTxnRequestModel autoGLTxnRequestModel) {

		log.info("Inside AutoGLTxnController#updateGlTxnRequestStatus ");
		log.info("Updated AutoGl Transaction Request {}",autoGLTxnRequestModel);
		Integer result;
		try {
			result = autoGLTxnService.updateRequestStatus(autoGLTxnRequestModel.getRequestNo().toString(),autoGLTxnRequestModel.getReqStatus());
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception thrown- updateGlTxnRequestStatus {}",e.getMessage());
			return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
		}
		
	}


}

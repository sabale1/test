package in.ecgc.smile.erp.accounts.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.PettyCashMaster;
import in.ecgc.smile.erp.accounts.service.PettyCashMasterService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/petty-cash")
@Api(value = "petty cash master service")
public class PettyCashMasterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PettyCashMasterController.class);

	@Autowired
	PettyCashMasterService pettyCashMasterService;
	
	

	@PostMapping("/add")
	@ApiOperation(value = "Add petty cash details", response = ResponseModel.class)
	public ResponseEntity<Integer> addPettyCashDetails(@RequestBody @Valid PettyCashMaster pettyCashMaster) {
		LOGGER.info("Inside PettyCashMasterController#addPettyCashDetails ");
		ResponseEntity<Integer> model;
		LOGGER.info("PettyCashMaster Object {}", pettyCashMaster);

		if (pettyCashMasterService.addPettyCashDetails(pettyCashMaster) >= 1) {
			model = new ResponseEntity<>(pettyCashMaster.getRequisitionNo(), HttpStatus.CREATED);
			LOGGER.info("Data Successsfully added");
		} else {
			model = new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
			LOGGER.error("Failed to insert data");
		}

		return model;
	}

	@GetMapping("/view-all")
	@ApiOperation(value = "View all cash details", response = ResponseEntity.class)
	public ResponseEntity<List<PettyCashMaster>> listAllPettyCashDetails() {
		LOGGER.info("Inside PettyCashMasterController#listAllPettyCashDetails ");
		ResponseEntity<List<PettyCashMaster>> model;
		List<PettyCashMaster> list = null;
		try {
			if ((list = pettyCashMasterService.listAllPettyCashMaster()) != null) {
				LOGGER.info("List of All PettyCashMasters {}", list);
				model = new ResponseEntity<List<PettyCashMaster>>(list, HttpStatus.OK);
			} else {
				model = new ResponseEntity<List<PettyCashMaster>>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			model = new ResponseEntity<List<PettyCashMaster>>(HttpStatus.BAD_REQUEST);
			LOGGER.error("Exception thrown- Petty Cash ..failed to fetch {}", e);
		}
		return model;
	}

	@GetMapping(value = "/view/{requisitionNo}/{logicalLocCode}")
	@ApiOperation(value = "View cash details by requisition no and location", response = ResponseEntity.class)
	public ResponseEntity<PettyCashMaster> findByRequisitionNo(
			@ApiParam(value = "Requisition Number", required = true) @PathVariable("requisitionNo") @NotBlank Integer requisitionNo,
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCode") @NotBlank String logicalLocCode) {
		LOGGER.info("Inside PettyCashMasterController#findByRequisitionNo ");
		PettyCashMaster pettyCashMaster = pettyCashMasterService.findByRequisitionNo(requisitionNo, logicalLocCode);
		LOGGER.info("Get PettyCashMaster by RequisitionNo and Location {}", pettyCashMaster);
		if (pettyCashMaster != null)
			return new ResponseEntity<PettyCashMaster>(pettyCashMaster, HttpStatus.OK);
		else
			return new ResponseEntity<PettyCashMaster>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/get-pid/{requisitionNo}/{logicalLocCode}")
	@ApiOperation(value = "Get PID by requisition no and location", response = ResponseEntity.class)
	public ResponseEntity<Long> getProcessInstanceId(
			@ApiParam(value = "Requisition Number", required = true) @RequestParam("requisitionNo") Integer requisitionNo,
			@ApiParam(value = "Logical Location Code", required = true) @RequestParam("logicalLocCode") String logicalLocCode) {
		LOGGER.info("Inside PettyCashMasterController#getProcessInstanceId ");
		ResponseEntity<Long> responseEntity = null;
		try {
			Long pid = pettyCashMasterService.getProcessInstanceId(requisitionNo, logicalLocCode);
			LOGGER.info("Get PID {}", pid);
			responseEntity = new ResponseEntity<Long>(pid, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error in getProcessInstanceId {}", e.fillInStackTrace());
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@PutMapping(value = "/update-pid/{requisitionNo}/{logicalLocCode}/{pid}")
	@ApiOperation(value = "Update PID by requisition no and location", response = ResponseEntity.class)
	public ResponseEntity<Integer> updateProcessInstanceId(
			@ApiParam(value = "Requisition Number", required = true) @RequestParam("requisitionNo") Integer requisitionNo,
			@ApiParam(value = "Logical Location Code", required = true) @RequestParam("logicalLocCode") String logicalLocCode,
			@ApiParam(value = "PID", required = true) @RequestParam("pid") Long pid) {
		LOGGER.info("Inside PettyCashMasterController#updateProcessInstanceId ");
		ResponseEntity<Integer> responseEntity = null;
		try {
			Integer res = pettyCashMasterService.updateProcessInstanceId(requisitionNo, logicalLocCode, pid);
			responseEntity = new ResponseEntity<Integer>(res, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error in updateProcessInstanceId {}", e.fillInStackTrace());
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@PutMapping("/approve/{requisitionNo}/{logicalLocCode}")
	@ApiOperation(value = "Approve pettyCash", response = ResponseModel.class)
	public ResponseEntity<Integer> approvedStatus(
			@ApiParam(value = "Requisition Number", required = true) @PathVariable("requisitionNo") @NotBlank Integer requisitionNo,
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCode") @NotBlank String logicalLocCode) {
		ResponseEntity<Integer> responseModel;
		try {
			LOGGER.info("Inside PettyCashMasterController#approvedStatus ");
			PettyCashMaster pettyCashMaster = pettyCashMasterService.findByRequisitionNo(requisitionNo, logicalLocCode);
			LOGGER.info("PettyCashMaster Object {}", pettyCashMaster);

			if (pettyCashMaster.getReqStatus().equals("Requested")) {
				// System.err.println("in if true");
				pettyCashMasterService.approvedStatus(pettyCashMaster);
				responseModel = new ResponseEntity<Integer>(1, HttpStatus.OK);
				LOGGER.info("Request status is approved");
			} else {
				responseModel = new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
				LOGGER.info("Failed to approved status");
			}
		} catch (Exception e) {
			responseModel = new ResponseEntity<Integer>(-1, HttpStatus.BAD_REQUEST);
			LOGGER.error("Exception in approve status {}",e.getMessage());
		}
		return responseModel;
	}

	@PutMapping(value = "/update/{requisitionNo}/{logicalLocCode}")
	@ApiOperation(value = "Update pettyCash", response = ResponseEntity.class)
	public ResponseEntity<Integer> updatePettyCash(
			@ApiParam(value = "Requisition Number", required = true) @PathVariable("requisitionNo") @NotBlank Integer requisitionNo,
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCode") @NotBlank String logicalLocCode,
			@RequestBody PettyCashMaster pettyCashUpdate) {
		LOGGER.info("Inside PettyCashMasterController#updatePettyCash ");
		Integer result = pettyCashMasterService.updatePettyCash(requisitionNo, logicalLocCode, pettyCashUpdate);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@PostMapping("/mul-approve")
	@ApiOperation(value = "Approve multiple pettycash requests", response = ResponseEntity.class)
	public ResponseEntity<Integer> approveMultipleRequest(@RequestBody List<PettyCashMaster> pettyCashMasters) {
		LOGGER.info("Inside PettyCashMasterController#approveMultipleRequest ");
		ResponseEntity<Integer> model;
		try {
			LOGGER.info("List of All PettyCashMasters {}", pettyCashMasters);
			if (pettyCashMasterService.approveMultipleRequest(pettyCashMasters) >= 1) {
				model = new ResponseEntity<Integer>(1, HttpStatus.ACCEPTED);
				LOGGER.info("List of pettycashmasters requests status is approved");
			} else {
				model = new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
				LOGGER.info("Failed to approve status");
			}
		} catch (Exception e) {
			model = new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
			LOGGER.error("Exception thrown -..Incomplete Data provided {}", e);
		}
		return model;
	}
}
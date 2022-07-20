package in.ecgc.smile.erp.accounts.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.FTR;
import in.ecgc.smile.erp.accounts.service.FTRService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ftr")
@Api(value = "ftr service")
@Slf4j
public class FTRController {

	@Autowired
	FTRService FTRService;

	@PostMapping("/add")
	@ApiOperation(value = "Add New FTR", response = ResponseEntity.class)
	public ResponseEntity<Integer> addFTR(@RequestBody FTR ftr) {
		log.info("Inside FTRController#addFTR ");
		ResponseEntity<Integer> model;
		try {
			log.info("New FTR Entry {}", ftr);
			int reqNo = 0;
			if ((reqNo = FTRService.addRequest(ftr)) > 0) {
				model = new ResponseEntity<Integer>(reqNo, HttpStatus.ACCEPTED);
				log.info("Request Number {}", reqNo);
			} else {
				model = new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			model = new ResponseEntity<Integer>(HttpStatus.BAD_GATEWAY);
			log.error("Exception while adding new FTR : {}", e);
		}
		return model;
	}

	@GetMapping("/getftr/{ftrReqNo}")
	@ApiOperation(value = "Get FTR Details", response = ResponseEntity.class)
	public ResponseEntity<FTR> getFtrDtl(
			@ApiParam(value = "Fund Transfer Request Number", required = true) @PathVariable("ftrReqNo") @NotBlank String FTRId) {
		log.info("Inside FTRController#getFtrDtl ");
		ResponseEntity<FTR> model;
		FTR ftrDtl = null;
		try {
			if ((ftrDtl = FTRService.getFTRRequestDTL(FTRId)) != null) {
				model = new ResponseEntity<FTR>(ftrDtl, HttpStatus.OK);
				log.info("FTR Details {}", ftrDtl);
			} else {
				model = new ResponseEntity<FTR>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			model = new ResponseEntity<FTR>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRException ..failed to fetch :  {}", e.getMessage());
		}
		return model;

	}

	@GetMapping("/getallFTR")
	@ApiOperation(value = "Get All FTR", response = ResponseEntity.class)
	public ResponseEntity<List<FTR>> getAllFTR() {
		log.info("Inside FTRController#getAllFTR ");
		ResponseEntity<List<FTR>> model;
		List<FTR> allFTR = null;
		try {

			if ((allFTR = FTRService.getAllFTRRequest()) != null) {
				model = new ResponseEntity<List<FTR>>(allFTR, HttpStatus.OK);
				log.debug("List of All FTR  {}", allFTR);
			} else {
				model = new ResponseEntity<List<FTR>>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			model = new ResponseEntity<List<FTR>>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRCodeException ..failed to fetch : {}", e);
		}
		return model;
	}

	@GetMapping("/get-pending")
	@ApiOperation(value = "Get All Pending FTR", response = ResponseEntity.class)
	public ResponseEntity<List<FTR>> getAllPendingFTR() {
		log.info("Inside FTRController#getAllPendingFTR ");
		ResponseEntity<List<FTR>> model;
		List<FTR> allFTR = null;
		try {
			if ((allFTR = FTRService.getAllPendingFTRRequest()) != null) {
				model = new ResponseEntity<List<FTR>>(allFTR, HttpStatus.OK);
				log.debug("List of All FTR  {}", allFTR);
			} else {
				model = new ResponseEntity<List<FTR>>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			model = new ResponseEntity<List<FTR>>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRCodeException ..failed to fetch : {}", e);
		}
		return model;
	}

	@GetMapping("/get-approved")
	@ApiOperation(value = "Get All Approved FTR", response = ResponseEntity.class)
	public ResponseEntity<List<FTR>> getAllApprovedFTR() {
		log.info("Inside FTRController#getAllApprovedFTR ");
		ResponseEntity<List<FTR>> model;
		List<FTR> allFTR = null;
		try {
			if ((allFTR = FTRService.getAllApprovedFTRRequest()) != null) {
				model = new ResponseEntity<List<FTR>>(allFTR, HttpStatus.OK);
				log.debug("List of All Approved FTR  {}", allFTR);
			} else {
				model = new ResponseEntity<List<FTR>>(HttpStatus.NOT_FOUND);
				log.info(" No Approved FTR Found ");
			}
		} catch (Exception e) {
			model = new ResponseEntity<List<FTR>>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRCodeException ..failed to fetch : {}", e);
		}
		return model;
	}

	@GetMapping("/getallFTR/{logicalLoc}")
	@ApiOperation(value = "Get All FTR", response = ResponseEntity.class)
	public ResponseEntity<List<FTR>> getAllFTRForBranch(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLoc") @NotBlank String logicalLoc) {
		log.info("Inside FTRController#getAllFTRForBranch ");
		ResponseEntity<List<FTR>> model;
		List<FTR> allFTR = null;
		try {
			if ((allFTR = FTRService.getAllFTRRequest(logicalLoc)) != null) {
				model = new ResponseEntity<List<FTR>>(allFTR, HttpStatus.OK);
				log.debug("All Branch Wise FTR Requests {}", allFTR);

			} else {
				model = new ResponseEntity<List<FTR>>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			model = new ResponseEntity<List<FTR>>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRCodeException ..failed to fetch : {}", e);
		}
		return model;
	}

	@PostMapping("/decision")
	@ApiOperation(value = "Decision on FTR Request", response = ResponseEntity.class)
	public ResponseEntity<Integer> decisionOnFtr(@RequestBody FTR ftr) {
		log.info("Inside FTRController#decisionOnFtr ");
		ResponseEntity<Integer> model;
		try {
			log.info("Desion For FTR Request {}", ftr);
			if (FTRService.decisionOnFTRRequest(ftr) == 1) {
				model = new ResponseEntity<Integer>(1, HttpStatus.ACCEPTED);
				log.debug("Desion Taken Successfully");
			} else {
				model = new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
				log.debug("Unable To Take Desion");
			}
		} catch (Exception e) {
			model = new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRException ..Incomplete FTR Data provided : {}", e);
		}
		return model;
	}

	@PostMapping("/mul-decision")
	@ApiOperation(value = "Decision on Multiple FTR Request", response = ResponseEntity.class)
	public ResponseEntity<Integer> decisionOnMultipleFtr(@RequestBody List<FTR> ftrs) {
		log.info("Inside FTRController#decisionOnMultipleFtr ");
		ResponseEntity<Integer> model;
		try {
			log.info("List of FTR Requests {} ", ftrs);
			if (FTRService.decisionOnMultipleFTRRequest(ftrs) >= 1) {
				model = new ResponseEntity<Integer>(1, HttpStatus.ACCEPTED);
				log.debug("Desion Taken Successfully");
			} else {
				model = new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
				log.debug("Unable To Take Desion");
			}
		} catch (Exception e) {
			model = new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRException ..Incomplete FTR Data provided : {}", e);
		}
		return model;
	}

	@PostMapping("/update")
	@ApiOperation(value = "Update FTR Request", response = ResponseEntity.class)
	public ResponseEntity<Integer> updateFtr(@RequestBody FTR ftr) {
		log.info("Inside FTRController#updateFtr ");
		ResponseEntity<Integer> model;
		try {
			log.info("Update FTR Request {}", ftr);
			if (FTRService.updateFTRRequest(ftr) == 1) {
				model = new ResponseEntity<Integer>(1, HttpStatus.ACCEPTED);
				log.debug("Updated Successfully");
			} else {
				model = new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
				log.debug("Unable To Update");
			}
		} catch (Exception e) {
			model = new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRException ..Incomplete FTR Data provided : {}", e);
		}
		return model;
	}

	@DeleteMapping("/delete/{ftrReqNo}")
	@ApiOperation(value = "Delete FTR Request", response = ResponseEntity.class)
	public ResponseEntity<Integer> deleteFtr(
			@ApiParam(value = "Fund Transfer Request Number", required = true) @PathVariable("ftrReqNo") @NotBlank String ftrReqNo) {
		log.info("Inside FTRController#deleteFtr ");
		Integer result = FTRService.deleteFTRRequest(Integer.parseInt(ftrReqNo));

		return new ResponseEntity<Integer>(result, HttpStatus.ACCEPTED);

	}

	@PostMapping("/generate-excel/{bank}/{ftrCds}")
	@ApiOperation(value = "Generate Excel for FTR Request", response = ResponseEntity.class)
	public ResponseEntity<Integer> generateExcelForFtr(
			@ApiParam(value = "Bank", required = true) @PathVariable("bank") @NotBlank String bank,
			@ApiParam(value = "FTR Codes", required = true) @PathVariable("ftrCds") @NotBlank String[] ftrCds) {
		log.info("Inside FTRController#generateExcelForFtr ");
		ResponseEntity<Integer> model;
		try {
			if (FTRService.generateExcelForFTRRequest(ftrCds, bank) >= 1) {
				model = new ResponseEntity<Integer>(1, HttpStatus.ACCEPTED);
			} else {
				model = new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			model = new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRException ..Something Went Wrong {}", e);
		}
		return model;
	}

	@GetMapping("/update-trf-status/{ftrReqNo}/{paymentdt}/{remark}")
	@ApiOperation(value = "Update FTR Transfer Status", response = ResponseEntity.class)
	public ResponseEntity<List<List<Integer>>> updateFtrTrfStatus(
			@ApiParam(value = "Fund Transfer Request Number", required = true) @PathVariable("ftrReqNo") @NotBlank String[] ftrReqNo,
			@ApiParam(value = "Payment Date", required = true) @PathVariable("paymentdt") @NotBlank String paymentdt,
			@ApiParam(value = "Remark", required = true) @PathVariable("remark") @NotBlank String remark) {
		log.info("Inside FTRController#updateFtrTrfStatus ");
		List<List<Integer>> lists = new ArrayList<>();
		List<Integer> gltxnadd = new ArrayList<Integer>();
		try {
			for (String ftr : ftrReqNo) {
				try {
					gltxnadd = FTRService.getGlHdrDtl(ftr);
					log.info("gltxnadd    {} ", gltxnadd);
					if (gltxnadd.size() > 1) {
						if (FTRService.changeStatusToTransfer(ftr, paymentdt, remark, gltxnadd) >= 1) {
							gltxnadd.add(Integer.parseInt(ftr));
							lists.add(gltxnadd);
						}
					} else {
						gltxnadd.add(Integer.parseInt(ftr));
						lists.add(gltxnadd);
					}
				} catch (Exception e) {
					gltxnadd.add(Integer.parseInt(ftr));
					lists.add(gltxnadd);
					log.error("Exception thrown- FTRException ..Something Went wrong {}", e);
				}
			}
		} catch (Exception e) {
			log.error("Exception thrown- FTRException ..Something Went wrong in loop {}", e);
		}
		return new ResponseEntity<>(lists, HttpStatus.ACCEPTED);
	}

	@GetMapping("/generateExelFTR/{ftrlist}")
	@ApiOperation(value = "Get FTR Details For ExcelSheet", response = ResponseEntity.class)
	public ResponseEntity<List<FTR>> generateSheet(
			@ApiParam(value = "Fund Transfer Request Number", required = true) @PathVariable("ftrlist") @NotBlank String[] ftrlist) {
		log.info("Inside FTRController#generateExelFTR ");
		ResponseEntity<List<FTR>> model;
		List<FTR> ftrDtl = null;
		try {
			if ((ftrDtl = FTRService.generateExelFTR(ftrlist)) != null) {
				model = new ResponseEntity<List<FTR>>(ftrDtl, HttpStatus.OK);
				log.info("FTR Details {}", ftrDtl);
			} else {
				model = new ResponseEntity<List<FTR>>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			model = new ResponseEntity<List<FTR>>(HttpStatus.BAD_REQUEST);
			log.error("Exception thrown- FTRException ..failed to fetch :  {}", e.getMessage());
		}
		return model;

	}

}

package in.ecgc.smile.erp.accounts.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.SubBifurcations;
import in.ecgc.smile.erp.accounts.service.SubBiFurcationMasterService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sub-bifurcations")
@Api(value = "SubBifurcation masterservice")
@Slf4j
@Validated
public class SubBifurcationMasterController {

	@Autowired
	SubBiFurcationMasterService subBifurcationService;

	@GetMapping("/view-all")
	@ApiOperation(value = "View All Sub bifurcation Levels", response = ResponseEntity.class)
	public ResponseEntity<List<SubBifurcations>> getAllSubBifurcations() {
		log.info("Inside SubBifurcationMasterController#getAllSubBifurcations ");
		log.info("Returning all Sub Bifurcation Levels");
		return new ResponseEntity<List<SubBifurcations>>(subBifurcationService.getSubBifurcations(), HttpStatus.OK);
	}

	@GetMapping("/view-all-level")
	@ApiOperation(value = "View All Sub Bifurcation Levels", response = ResponseEntity.class)
	public ResponseEntity<List<String>> getAllSubBifurcationLevels() {
		log.info("Inside SubBifurcationMasterController#getAllSubBifurcationLevels ");
		log.info("Returning all Sub Bifurcation Level Names only");
		return new ResponseEntity<List<String>>(subBifurcationService.getSubBifurcationsLevel(), HttpStatus.OK);
	}

	@GetMapping("/view-all/{subbifurcationLevel}")
	@ApiOperation(value = "View Sub Bifurcations Level By Level Code", response = ResponseEntity.class)
	public ResponseEntity<SubBifurcations> getAllSubBifurcationsByLEvel(
			@ApiParam(value = "Sub Bifurcation Level", required = true) @PathVariable("subbifurcationLevel") @Size(min = 3, max = 3, message = "Sub Bifurcation Level Code should be of 3 Characters") String subBifurcationLevel) {
		log.info("Inside SubBifurcationMasterController#getAllSubBifurcationsByLEvel ");
		log.info("View Sub Bifurcations Level By Level Code {}", subBifurcationLevel);
		return new ResponseEntity<SubBifurcations>(subBifurcationService.getSubBifurcationsByLevel(subBifurcationLevel),
				HttpStatus.OK);
	}

	@PostMapping("/add")
	@ApiOperation(value = "Create Sub Bifurcation Level Code", response = ResponseEntity.class)
	public ResponseEntity<Integer> addSubBifurcation(@Valid @RequestBody SubBifurcations subBifurcations) {
		log.info("Inside SubBifurcationMasterController#addSubBifurcation ");
		log.info("Add New Sub Bifurcations Level {}", subBifurcations);
		return new ResponseEntity<>(subBifurcationService.addSubBifurcation(subBifurcations), HttpStatus.CREATED);
	}

	@PutMapping("/update/{subBifurcationLevel}")
	@ApiOperation(value = "Update Sub Bifurcation Level", response = ResponseModel.class)
	public ResponseEntity<SubBifurcations> updateSubBifurcationLevel(
			@ApiParam(value = "Sub Bifurcation Level", required = true)
			@PathVariable("subBifurcationLevel") @Size(min = 3, max = 3, message = "Sub Bifurcation Level Should be of 3 Characters") String subBifurcationLevel,
			@RequestBody SubBifurcations updatedSubBifurcations) {
		log.info("Inside SubBifurcationMasterController#updateSubBifurcationLevel ");
		log.info("Update SubBifurcation Level {}", subBifurcationLevel);
		SubBifurcations currentSubBifurcations = subBifurcationService.getSubBifurcationsByLevel(subBifurcationLevel);
		SubBifurcations modifiedSubBifurcations = subBifurcationService.updatedSubBifurcations(currentSubBifurcations,
				updatedSubBifurcations);
		return new ResponseEntity<>(modifiedSubBifurcations, HttpStatus.ACCEPTED);
	}
}

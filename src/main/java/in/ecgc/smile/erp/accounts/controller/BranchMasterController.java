package in.ecgc.smile.erp.accounts.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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

import in.ecgc.smile.erp.accounts.model.BranchMaster;
import in.ecgc.smile.erp.accounts.service.BranchMasterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/branch-master")
@Api(value = "Branch Master service")
@Slf4j	
public class BranchMasterController {
	
	@Autowired
	BranchMasterService branchMasterService;

	@PostMapping("/add")
	@ApiOperation(value= "Add New Branch Details", response = ResponseEntity.class)
	public ResponseEntity<BranchMaster> addBranch(@Valid @RequestBody BranchMaster branchMaster)
		{	
			log.info("Inside BranchMasterController#addBranch ");
			log.info("Add New Branch Details {} ",branchMaster);
			branchMasterService.addBranch(branchMaster);
			return new ResponseEntity<>(branchMaster, HttpStatus.CREATED);		
	}		
		
	@GetMapping("/view-all")
	@ApiOperation(value = "View All Branches", response = ResponseEntity.class)
	public ResponseEntity<List<BranchMaster>> listAllBranches(){
		log.info("Inside BranchMasterController#listAllBranches ");
		List<BranchMaster> allBranches = branchMasterService.listAllBranches();
		log.info("List of All Branch Details {} ",allBranches);
		if (allBranches != null) {
			return new ResponseEntity<>(allBranches, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<BranchMaster>>(HttpStatus.NO_CONTENT);
			}
		}
	@PutMapping("/update/{logicalLocCode}/{branchCode}")
	@ApiOperation(value = "Update Branch Detail by Logical Location Code and Branch Code ", response = ResponseEntity.class)
	public ResponseEntity<Integer> updateBranch(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCode") @NotBlank String logicalLocCode, 
			@ApiParam(value = "Branch Code", required = true)@PathVariable("branchCode") @NotBlank String branchCode, 
			@RequestBody BranchMaster updateBranch ) {
			log.info("Inside BranchMasterController#updateBranch ");
			log.info("Updated Branch Details {} ",updateBranch);
			Integer result = branchMasterService.updateBranch(logicalLocCode, branchCode, updateBranch);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
			}	

	@DeleteMapping("/delete/{logicalLocCode}/{branchCode}")
	@ApiOperation(value = "Delete Branch", response = ResponseEntity.class)
	public ResponseEntity<Integer> disableBranch(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCode") @NotBlank String logicalLocCode,
			@ApiParam(value = "Branch Code", required = true) @PathVariable("branchCode") @NotBlank String branchCode)
			{	
			log.info("Inside BranchMasterController#disableBranch ");
			Integer result = branchMasterService.disableBranch(logicalLocCode, branchCode);
			return new ResponseEntity<Integer>(result, HttpStatus.ACCEPTED);
	}	
}

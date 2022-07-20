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

import in.ecgc.smile.erp.accounts.model.BankBranch;
import in.ecgc.smile.erp.accounts.service.BankBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bank-branch-master")
@Api(value = "Bank Branch Master service")
@Slf4j
public class BankBranchMasterController {

	@Autowired
	BankBranchService bankBranchService;

	@PostMapping("/add")
	@ApiOperation(value = "Add New Bank Branch ", response = ResponseEntity.class)
	public ResponseEntity<BankBranch> addBankBranch(@Valid @RequestBody BankBranch bankBranch) {
		log.info("Inside BankBranchMasterController#addBankBranch ");
		log.info("New Bank Branch {}", bankBranch);
		bankBranchService.addBankBranch(bankBranch);
		return new ResponseEntity<>(bankBranch, HttpStatus.CREATED);

	}

	@GetMapping("/view-all")
	@ApiOperation(value = "View All Bank Branches", response = ResponseEntity.class)
	public ResponseEntity<List<BankBranch>> listAllBankBranches() {
		log.info("Inside BankBranchMasterController#listAllBankBranches ");
		List<BankBranch> allBankBranches = bankBranchService.listAllBankBranches();
		log.info("List of All Bank Branches {}", allBankBranches);
		if (allBankBranches != null) {
			return new ResponseEntity<>(allBankBranches, HttpStatus.OK);
		} else {

			return new ResponseEntity<List<BankBranch>>(HttpStatus.NO_CONTENT);

		}
	}

	@PutMapping("/update/{logicalLocCode}/{bankName}")
	@ApiOperation(value = "Update Bank Branch Detail by Logical Location Code ", response = ResponseEntity.class)
	public ResponseEntity<Integer> updateBankBranch(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCode") @NotBlank String logicalLocCode,
			@ApiParam(value = "Bank Name", required = true) @PathVariable("bankName") @NotBlank String bankName,
			@RequestBody BankBranch updateBankBranch) {
		log.info("Inside BankBranchMasterController#updateBankBranch ");
		log.info("Updated Bank Branch Details {}", updateBankBranch);
		Integer result = bankBranchService.updateBankBranch(logicalLocCode, bankName, updateBankBranch);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@GetMapping("/view/{logicalLocCode}/{bankName}")
	@ApiOperation(value = "View Bank Details by Bank Name and Logical Location", response = ResponseEntity.class)
	public ResponseEntity<BankBranch> findBankByLogicalLocationAndBankName(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCode") @NotBlank String logicalLocCode,
			@ApiParam(value = "Bank Name", required = true) @PathVariable("bankName") @NotBlank String bankName) {
		log.info("Inside BankBranchMasterController#findBankByLogicalLocationAndBankName ");
		BankBranch bankBranchList = bankBranchService.findBankByLogicalLocationAndBankName(logicalLocCode, bankName);
		log.info("Bank Branch Details {}", bankBranchList);
		return new ResponseEntity<>(bankBranchList, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{logicalLocCode}/{bankName}")
	@ApiOperation(value = "Delete Given Bank Branch Head Code", response = ResponseEntity.class)
	public ResponseEntity<Integer> disableBankBranch(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCode") @NotBlank String logicalLocCode,
			@ApiParam(value = "Bank Name", required = true) @PathVariable("bankName") @NotBlank String bankName) {
		log.info("Inside BankBranchMasterController#disableBankBranch ");
		Integer result = bankBranchService.disableBankBranch(logicalLocCode, bankName);
		return new ResponseEntity<Integer>(result, HttpStatus.ACCEPTED);

	}

	@GetMapping("/active/view-all")
	@ApiOperation(value = "View All Active Bank Branches", response = ResponseEntity.class)
	public ResponseEntity<List<BankBranch>> listActiveBankBranches() {
		log.info("Inside BankBranchMasterController#listActiveBankBranches ");
		List<BankBranch> allActiveBankBranches = bankBranchService.listActiveBankBranches();
		log.info("List of All Active Bank Branches {}", allActiveBankBranches);
		if (allActiveBankBranches != null)
			return new ResponseEntity<>(allActiveBankBranches, HttpStatus.OK);
		else
			return new ResponseEntity<List<BankBranch>>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/view-gstin/{logicalLocCode}")

	@ApiOperation(value = "View Bank Details by Bank Name and Logical Location", response = ResponseEntity.class)
	public ResponseEntity<String> viewGstinByLogicalLocation(

			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCode") @NotBlank String logicalLocCode) {
		log.info("Inside BankBranchMasterController#listActiveBankBranches ");
		String gstiNumber = bankBranchService.getGstinByLogicalLoc(logicalLocCode);
		log.info(" GST Number {}", gstiNumber);
		return new ResponseEntity<>(gstiNumber, HttpStatus.OK);
	}
	
	@GetMapping("/view/{logicalLocCode}")
	@ApiOperation(value = "View Bank Details by Logical Location", response = ResponseEntity.class)
	public ResponseEntity<BankBranch> findBankByLogicalLocation(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCode") @NotBlank String logicalLocCode) {
		log.info("Inside BankBranchMasterController#findBankByLogicalLocation ");
		BankBranch bankBranchList = bankBranchService.findBankByLogicalLocation(logicalLocCode);
		log.info("Bank Branch Details {}", bankBranchList);
		return new ResponseEntity<>(bankBranchList, HttpStatus.OK);
	}

}

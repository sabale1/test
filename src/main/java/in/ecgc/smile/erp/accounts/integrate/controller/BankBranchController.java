package in.ecgc.smile.erp.accounts.integrate.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.integrate.model.BankBranchDetails;
import in.ecgc.smile.erp.accounts.integrate.service.BankBranchDetailsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/bank-branch")
@Api(value = "Bank Branch details from ECIB")
public class BankBranchController {

	@Autowired
	BankBranchDetailsServiceImpl bankBranchService;
	
	@GetMapping(value = "/fetch-bankbranchlist")
	@ApiOperation(value = "Get All Bank Branch List", response = ResponseEntity.class)
	ResponseEntity<List<BankBranchDetails>> fetchBankBranchList(){
		List<BankBranchDetails> bankBranchList = new ArrayList<BankBranchDetails>();
		
		try {
			bankBranchList = bankBranchService.fetchBankBranchList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(bankBranchList,HttpStatus.OK);
	}
}
	
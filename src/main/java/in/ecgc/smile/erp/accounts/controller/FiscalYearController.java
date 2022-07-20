package in.ecgc.smile.erp.accounts.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.FiscalYearModel;
import in.ecgc.smile.erp.accounts.service.FiscalYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Fiscal Year master REST Controller
 * 
 * @version 1.0 15-September-2020
 * @author Sanjali Kesarkar
 *
 */
@Slf4j
@RestController
@RequestMapping("/fiscal-year")
@Api(value = "Fiscal Year service")
public class FiscalYearController {

	@Autowired
	FiscalYearService fiscalYearService;

	/**
	 * Get current fiscal year details return details of current fiscal year present
	 * in the fiscal year table
	 */
	@GetMapping("/view/current-fiscal-year")
	@ApiOperation(value = "View Current Fiscal Year", response = ResponseEntity.class)
	public ResponseEntity<FiscalYearModel> findCurrentFiscalYear() {
		log.info("Inside FiscalYearController#findCurrentFiscalYear ");
		FiscalYearModel fiscalYearModel = fiscalYearService.findCurrentFiscalYear();
		log.info("Current Fiscal Year {} ", fiscalYearModel);
		return new ResponseEntity<>(fiscalYearModel, HttpStatus.OK);
	}

	/**
	 * Get list of fiscal years details return details of current fiscal year
	 * present in the fiscal year table
	 */
	@GetMapping("/list")
	@ApiOperation(value = "Get List of Fiscal Years", response = ResponseEntity.class)
	public ResponseEntity<List<String>> getFiscalYearList() {
		log.info("Inside FiscalYearController#getFiscalYearList");
		List<String> fiscalYearList = fiscalYearService.getFiscalYearList();
		log.info("FiscaL Year List is {}", fiscalYearList);
		return new ResponseEntity<>(fiscalYearList, HttpStatus.OK);
	}

	@GetMapping("/test")
	@ApiOperation(value = "Get List of Fiscal Years", response = ResponseEntity.class)
	public ResponseEntity<String> getTest(HttpServletRequest request, HttpServletResponse response) {
		log.info("Inside FiscalYearController#getTest ");
		return new ResponseEntity<>(request.getParameter("date"), HttpStatus.OK);
	}

	@GetMapping("/get-fiscal-yrs")
	@ApiOperation(value = "Get All Fiscal Years", response = ResponseEntity.class)
	public ResponseEntity<List<FiscalYearModel>> getFiscalYrModelList() {
		log.info("Inside FiscalYearController#getFiscalYrModelList");
		List<FiscalYearModel> result = fiscalYearService.getFiscalYrModelList();
		log.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/get-fiscal-yr-by-id/{id}")
	@ApiOperation(value = "Get Fiscal Year By Id", response = ResponseEntity.class)
	public ResponseEntity<FiscalYearModel> getFiscalYrDataById(
			@ApiParam(value = "Current Fiscal Year", required = true) @PathVariable("id") String currFiscYr) {
		log.info("Inside FiscalYearController#getFiscalYrDataById");
		FiscalYearModel result = fiscalYearService.getFiscalYrDataById(currFiscYr);
		log.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Integer> addCurrentFiscalYear(@RequestBody FiscalYearModel fiscalYr){
		log.info("Inside FiscalYearController#addCurrentFiscalYear");
		log.info("Parameter fiscalYr: {} " ,fiscalYr);
		Integer res = null;
		ResponseEntity<Integer> response = null;
		
		
		try {
			
			res = fiscalYearService.createCurrentFiscalYearEntry(fiscalYr);
			log.info("res is "+res);
		} 
		catch (Exception e) {
			log.error("Error in viewAlllovList {}", e.fillInStackTrace());
			response=new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (res>=1) {
			response = new ResponseEntity<Integer>(res,HttpStatus.CREATED);
		}else {
			response = new ResponseEntity<Integer>(HttpStatus.EXPECTATION_FAILED);
		}
		return response;
	}
	
	
	

}

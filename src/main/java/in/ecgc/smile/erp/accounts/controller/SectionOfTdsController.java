package in.ecgc.smile.erp.accounts.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.SectionOfTds;

import in.ecgc.smile.erp.accounts.service.SectionOfTdsService;

import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sectionOfTDS")
public class SectionOfTdsController {
	
	@Autowired
	private SectionOfTdsService tdsservice;
	private static final Logger logger = LoggerFactory.getLogger(SectionOfTdsController.class);

	@ApiOperation(value = "Get All Tds Data")
	@GetMapping("/list")
	public ResponseEntity<List<SectionOfTds>> getTdsList() {
		List<SectionOfTds> result = tdsservice.getTdsList();
		logger.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "Get Tds Data By Id")
	@GetMapping("/get-by-id/{id}")
	public ResponseEntity<SectionOfTds> getTdsDataById(@PathVariable("id") int srNo) {
		SectionOfTds result = tdsservice.getTdsDataById(srNo);
		logger.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "Save Tds Data")
	@PostMapping("/add")
	public ResponseEntity<Boolean> addTdsData(@RequestBody SectionOfTds tds) {
		boolean result = tdsservice.addTdsData(tds);
		logger.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "Update Tds Data")
	@PostMapping("/update")
	public ResponseEntity<Boolean> updateTdsData(@RequestBody SectionOfTds tds) {
		logger.info("Updated Object ::{}", tds);
		boolean result = tdsservice.updateTdsData(tds);
		logger.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}

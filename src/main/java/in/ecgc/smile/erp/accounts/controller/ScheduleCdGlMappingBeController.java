package in.ecgc.smile.erp.accounts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.ScheduleCdGlMapping;
import in.ecgc.smile.erp.accounts.service.ScheduleCdGlMappingService;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/schdl-gl-mapping")
@RestController
public class ScheduleCdGlMappingBeController {
	@Autowired
	private ScheduleCdGlMappingService scheduleCdGlMappingservice;
	private static final Logger logger = LoggerFactory.getLogger(ScheduleCdGlMappingBeController.class);

	@ApiOperation(value = "Get All ScheduleCdGlMapping Data")
	@GetMapping("/get-schedulecdglmapping-data")
	public ResponseEntity<List<ScheduleCdGlMapping>> getScheduleCdGlMappingList() {
		List<ScheduleCdGlMapping> result = scheduleCdGlMappingservice.getScheduleCdGlMappingList();
		logger.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "Get ScheduleCdGlMapping Data By Id")
	@GetMapping("/get-schedulecdglmapping-data-by-id/{id}")
	public ResponseEntity<ScheduleCdGlMapping> getScheduleCdGlMappingDataById(@PathVariable("id") int seqNo) {
		ScheduleCdGlMapping result = scheduleCdGlMappingservice.getScheduleCdGlMappingDataById(seqNo);
		logger.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "Save ScheduleCdGlMapping Data")
	@PostMapping("/save-schedulecdglmapping-data")
	public ResponseEntity<Boolean> addScheduleCdGlMappingData(@RequestBody ScheduleCdGlMapping scheduleCdGlMapping) {
		boolean result = scheduleCdGlMappingservice.addScheduleCdGlMappingData(scheduleCdGlMapping);
		logger.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "Update ScheduleCdGlMapping Data")
	@PostMapping("/update-schedulecdglmapping-data")
	public ResponseEntity<Boolean> updateScheduleCdGlMappingData(@RequestBody ScheduleCdGlMapping scheduleCdGlMapping) {
		logger.info("ScheduleCdeGLMapping object is :{}",scheduleCdGlMapping);
		boolean result = scheduleCdGlMappingservice.updateScheduleCdGlMappingData(scheduleCdGlMapping);
		logger.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}

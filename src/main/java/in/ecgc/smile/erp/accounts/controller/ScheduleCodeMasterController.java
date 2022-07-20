package in.ecgc.smile.erp.accounts.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import in.ecgc.smile.erp.accounts.exception.DataAlreadyExist;
import in.ecgc.smile.erp.accounts.model.ScheduleCodeMst;
import in.ecgc.smile.erp.accounts.service.ScheduleCodeMasterService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Faisal-LAPTOP9
 *
 */
@RestController
@RequestMapping("/schedule-mst")
@Api(value = "Schedule Master service")
@Slf4j
public class ScheduleCodeMasterController {
	@Autowired
	ScheduleCodeMasterService scheduleCodeMstService;

	@GetMapping
	@ApiOperation(value = "Get All Schedule", response = ResponseModel.class)
	public ResponseEntity<List<ScheduleCodeMst>> getAllSchedule() {
		log.info("Inside ScheduleCodeMasterController#getAllSchedule ");
		List<ScheduleCodeMst> allSchedule = new ArrayList<ScheduleCodeMst>();
		allSchedule = scheduleCodeMstService.getAllSchedule();
		log.info("List of All Schedule Codes {} ",allSchedule);
		return new ResponseEntity<>(allSchedule, HttpStatus.OK);
	}

	@GetMapping("/{schedule_cd}/{schedule_item_cd}")
	@ApiOperation(value = "Get Schedule by Schedule Code and Schedule Item Code ", response = ResponseEntity.class)
	public ResponseEntity<ScheduleCodeMst> getSchedule(
			@ApiParam(value = "Schedule Code", required = true) @PathVariable("schedule_cd") @NotBlank String scheduleCode,
			@ApiParam(value = "Schedule Item Code", required = true) @PathVariable("schedule_item_cd") @NotBlank String scheduleItemCode) {
		log.info("Inside ScheduleCodeMasterController#getSchedule ");
		return new ResponseEntity<>(scheduleCodeMstService.getScheduleByScheduleCd(scheduleCode, scheduleItemCode),
				HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "Add New Schedule", response = ResponseModel.class)
	public ResponseEntity<Integer> addSchedule(@Valid @RequestBody ScheduleCodeMst scheduleCodeMst) {
		log.info("Inside ScheduleCodeMasterController#addSchedule ");
		log.info("New Schedule Code Entry {} ",scheduleCodeMst);
		Integer result=0;
		try
		{
			result=scheduleCodeMstService.addSchedule(scheduleCodeMst);
			System.err.println("result is "+result);
			}
		catch(DataIntegrityViolationException e)
		{
			e.getMessage();
			result=-1;
		}
		return new ResponseEntity<>(result, HttpStatus.CREATED);
		
		
		}

	@PutMapping
	@ApiOperation(value = "Update Schedule", response = ResponseModel.class)
	public ResponseEntity<Integer> updateSchedule(@Valid @RequestBody ScheduleCodeMst scheduleCodeMst) {
		log.info("Inside ScheduleCodeMasterController#updateSchedule ");
		log.info("Update Schedule Code Entry {} ",scheduleCodeMst);
		return new ResponseEntity<>(scheduleCodeMstService.updateSchedule(scheduleCodeMst), HttpStatus.OK);
	}

	@DeleteMapping("/{scheduleCode}/{scheduleItemcd}")
	@ApiOperation(value = "Delete Schedule Code", response = ResponseModel.class)
	public ResponseEntity<Integer> deleteSchedule(
			@ApiParam(value = "Schedule Code", required = true) @PathVariable("scheduleCode") @NotBlank String scheduleCode,
			@ApiParam(value = "Schedule Item Code", required = true) @PathVariable("scheduleItemcd") @NotBlank String scheduleItemCode) {
		log.info("Inside ScheduleCodeMasterController#deleteSchedule ");
		return new ResponseEntity<>(scheduleCodeMstService.deleteSchedule(scheduleCode, scheduleItemCode),
				HttpStatus.OK);
	}
	
	@GetMapping("/getScheduleCodes")
	@ApiOperation(value = "Get all Schedule Codes", response = ResponseModel.class)
	public ResponseEntity<List<String>> getScheduleCodes(){
		log.info("Inside ScheduleCodeMasterController#getScheduleCodes ");
		return new ResponseEntity<>(scheduleCodeMstService.getScheduleCodes(),HttpStatus.ACCEPTED.OK);
		
	}

	@GetMapping("/getScheduleItemCodes")
	@ApiOperation(value = "Get all Schedule Item Codes", response = ResponseModel.class)
	public ResponseEntity<List<String>> getScheduleItemCodes(){
		log.info("Inside ScheduleCodeMasterController#getScheduleItemCodes ");
		return new ResponseEntity<>(scheduleCodeMstService.getScheduleItemCodes(),HttpStatus.ACCEPTED.OK);
		
	}
}

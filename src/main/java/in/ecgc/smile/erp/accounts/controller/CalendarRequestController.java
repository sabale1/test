package in.ecgc.smile.erp.accounts.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.exception.CalendarExceptionHandling;
import in.ecgc.smile.erp.accounts.model.CalendarRequestModel;
import in.ecgc.smile.erp.accounts.service.CalendarRequestService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/calendar-request")
@Api(value = "Calendar Request Service")
@Slf4j
public class CalendarRequestController {

	@Autowired
	CalendarRequestService calendarService;

	@PostMapping("/add")
	@ApiOperation(value = "Add New Calendar Request", response = ResponseModel.class)
	public ResponseEntity<CalendarRequestModel> generateRequest(@RequestBody CalendarRequestModel requestModel) {
		log.info("Inside CalendarRequestController#generateRequest ");
		log.info("New Calendar Request Obj {}", requestModel);
		ResponseEntity<CalendarRequestModel> responseEntity;
		if (calendarService.generateRequest(requestModel) == 1) {
			responseEntity = new ResponseEntity<CalendarRequestModel>(requestModel, HttpStatus.CREATED);
			return responseEntity;
		} else {
			return null;
		}

	}

	@PostMapping("/generate")
	@ApiOperation(value = "Add New Calendar Request", response = ResponseModel.class)
	public ResponseEntity<Integer> generateCalRequest(@RequestBody CalendarRequestModel requestModel) {
		log.info("Inside CalendarRequestController#generateCalRequest");
		log.info("New Calendar Request Obj {}", requestModel);
		Integer result =calendarService.generateCalRequest(requestModel);
		return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
	}

	@PostMapping("/update/{reqId}")
	@ApiOperation(value = "Update Calendar Request", response = ResponseEntity.class)
	public ResponseEntity<CalendarRequestModel> updateCalendarRequest(
			@ApiParam(value = "Request Id", required = true) @PathVariable("reqId") @NotBlank Integer reqId,
			@RequestBody CalendarRequestModel updateCalendarRequest) {
		log.info("Inside CalendarRequestController#updateCalendarRequest ");
		log.info("Modified Calendar Request Obj {}", updateCalendarRequest);
		return new ResponseEntity<CalendarRequestModel>(
				calendarService.updateCalendarRequest(reqId, updateCalendarRequest), HttpStatus.CREATED);
	}

	@GetMapping("/viewAllRequest")
	@ApiOperation(value = "View all Calendar Request", response = ResponseEntity.class)
	public ResponseEntity<List<CalendarRequestModel>> viewAllRequest() {
		log.info("Inside CalendarRequestController#viewAllRequest ");

		List<CalendarRequestModel> calendarRequest = calendarService.viewAllRequest();
		log.info("List of Calendar Requests {}", calendarRequest);

		if (calendarRequest != null) {
			return new ResponseEntity<>(calendarRequest, HttpStatus.OK);
		} else {
			return null;
		}
	}

	@GetMapping("/view/{reqId}")
	@ApiOperation(value = "View Calendar Request by Request Id", response = ResponseEntity.class)
	public ResponseEntity<CalendarRequestModel> viewRequestById(
			@ApiParam(value = "Request Id", required = true) @PathVariable("reqId") @NotBlank Integer reqId)
			throws CalendarExceptionHandling {
		log.info("Inside CalendarRequestController#viewRequestById ");

		return new ResponseEntity<>(calendarService.viewRequestById(reqId), HttpStatus.OK);
	}

	@PostMapping("/decision")
	@ApiOperation(value = "Decision On Single Request", response = ResponseEntity.class)
	public ResponseEntity<Integer> decisionOnCalendarRequest(@RequestBody CalendarRequestModel requestModel) {
		log.info("Inside CalendarRequestController#decisionOnCalendarRequest ");
		log.info("Taking Decision on Calendar Request  {}", requestModel);
		return new ResponseEntity<Integer>(calendarService.decisionOnRequest(requestModel), HttpStatus.OK);
	}

	@PostMapping("/mul-decision")
	@ApiOperation(value = "Decision On Multiple Request", response = ResponseEntity.class)
	public ResponseEntity<Integer> decisionOnMultipleCalendarRequest(
			@RequestBody List<CalendarRequestModel> requestModelList) {
		log.info("Inside CalendarRequestController#decisionOnMultipleCalendarRequest ");
		log.info("Taking Decision on List of Calendar Requests  {}", requestModelList);
		return new ResponseEntity<Integer>(calendarService.decisionMultipleRequest(requestModelList), HttpStatus.OK);
	}

}

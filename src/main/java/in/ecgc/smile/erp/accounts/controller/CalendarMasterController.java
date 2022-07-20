package in.ecgc.smile.erp.accounts.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
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

import in.ecgc.smile.erp.accounts.integrate.model.OfficeMaster;
import in.ecgc.smile.erp.accounts.integrate.service.OrgStructService;
import in.ecgc.smile.erp.accounts.model.Calendar;
import in.ecgc.smile.erp.accounts.model.CalendarMstAnnual;
import in.ecgc.smile.erp.accounts.model.CalendarRequestModel;
import in.ecgc.smile.erp.accounts.service.CalendarService;
import in.ecgc.smile.erp.accounts.service.FiscalYearService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/calendar")
@Api(value = "Calendar Master service")
@Slf4j
public class CalendarMasterController {

	@Autowired
	CalendarService calendarService;

	@Autowired
	OrgStructService orgStructService;

	@Autowired
	FiscalYearService fiscalYearService;

	@PostMapping("/add")
	@ApiOperation(value = "Add New Calendar", response = ResponseEntity.class)
	public ResponseEntity<String> addCalender(@Valid @RequestBody Calendar calender) {
		log.info("Inside CalendarMasterController#addCalender ");
		log.info("New Calendar Entry {}", calender);
		Integer calId = calendarService.addCalendar(calender);
		if (calId == 1)
			return new ResponseEntity<>(calender.getCalendarId(), HttpStatus.OK);
		return null;
	}

	@GetMapping("/getcalendar/{cd}")
	@ApiOperation(value = "Get Calendar", response = ResponseModel.class)
	public ResponseEntity<Calendar> getCalendar(
			@ApiParam(value = "Calendar Code", required = true) @PathVariable("cd") @NotBlank String calendarCode) {
		log.info("Inside CalendarMasterController#getCalendar ");
		return new ResponseEntity<Calendar>(calendarService.getCalendar(calendarCode), HttpStatus.OK);
	}

	@GetMapping("/get/{fiscalYr}/{month}")
	@ApiOperation(value = "Get All Calendars by Fiscal Year and Month", response = ResponseModel.class)
	public ResponseEntity<List<Calendar>> getAllCalendar(
			@ApiParam(value = "Fiscal Year", required = true) @PathVariable @NotBlank String fiscalYr,
			@ApiParam(value = "Month", required = true) @PathVariable @NotBlank String month) {
		log.info("Inside CalendarMasterController#getAllCalendar ");
		return new ResponseEntity<List<Calendar>>(calendarService.getAllCalendar(fiscalYr, month), HttpStatus.OK);
	}

	@DeleteMapping("/deletecalendar/{cd}")
	@ApiOperation(value = "Delete Calendar", response = ResponseModel.class)
	public ResponseEntity<Integer> deletecalendar(
			@ApiParam(value = "Calendar Code", required = true) @PathVariable("cd") @NotBlank String calendarCode) {
		log.info("Inside CalendarMasterController#deletecalendar ");
		return new ResponseEntity<Integer>(calendarService.deleteCalendar(calendarCode), HttpStatus.OK);
	}

	@GetMapping("/get/{fiscalYr}/{month}/{logicalLocCode}")
	@ApiOperation(value = "Get All Calendars by Fiscal Year, Month and Logical Location Code", response = ResponseEntity.class)
	public ResponseEntity<List<Calendar>> getByFiscalYr(
			@ApiParam(value = "Fiscal Year", required = true) @PathVariable("fiscalYr") @NotBlank String fiscalYr,
			@ApiParam(value = "Month", required = true) @PathVariable("month") @NotBlank String month,
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCode") @NotBlank String logicalLocCode) {
		log.info("Inside CalendarMasterController#getByFiscalYr ");
		return new ResponseEntity<List<Calendar>>(calendarService.getByFiscalYr(fiscalYr, month, logicalLocCode),
				HttpStatus.OK);
	}

	@GetMapping("/update/{first}/{second}/{status}")
	@ApiOperation(value = "Update Calendar Status", response = ResponseEntity.class)
	public Integer updateStatus(
			@ApiParam(value = "First", required = true) @PathVariable("first") @NotBlank String first,
			@ApiParam(value = "Second", required = true) @PathVariable("second") @NotBlank String second,
			@ApiParam(value = "Status", required = true) @PathVariable("status") @NotBlank String status) {
		log.info("Inside CalendarMasterController#updateStatus ");

		if (status.equals("open")) {
			calendarService.updateStatus1(first, "Y");
			calendarService.updateStatus2(second, "N");
			calendarService.updateReqStat(first);
		} else if (status.equals("close")) {
			calendarService.updateStatus1(first, "N");
			calendarService.updateStatus2(second, "Y");
			calendarService.updateReqStat(second);
		}

		return new Integer(1);
	}

	@GetMapping("/update/{prevyr}/{status}")
	@ApiOperation(value = "Update Calendar", response = ResponseEntity.class)
	public Integer updateprv(
			@ApiParam(value = "Previous Year", required = true) @PathVariable("prevyr") @NotBlank String prevyr,
			@ApiParam(value = "Status", required = true) @PathVariable("status") @NotBlank String status) {
		log.info("Inside CalendarMasterController#updateprv ");
		Integer res = null;

		if (status.equals("open")) {
			log.info("If Open Called");
			res = calendarService.updatePrev(prevyr, "Y");
		} else if (status.equals("close")) {
			log.info("If Close Called");
			res = calendarService.updatePrev(prevyr, "N");
		}

		return res;
	}

	// Monthly opening and closing working (Except PV,JV and IB)
	@GetMapping("/monthlyopeningclosing/regular")
	@ApiOperation(value = "Monthly Calendar Opening", response = ResponseEntity.class)
	public ResponseEntity<Integer> monthlyOpeningClosingRegular() {
		log.info("Inside CalendarMasterController#monthlyOpeningClosingRegular ");
		return new ResponseEntity<Integer>(calendarService.monthlyOpeningClosingRegular(), HttpStatus.OK);
	}

	// Monthly opening and closing working (For PV,JV and IB)
	@GetMapping("/monthlyopeningclosing/configured")
	@ApiOperation(value = "Monthly Calendar Opening", response = ResponseEntity.class)
	public ResponseEntity<Integer> monthlyOpeningClosingConfigured() {
		log.info("Inside CalendarMasterController#monthlyOpeningClosingConfigured ");
		return new ResponseEntity<Integer>(calendarService.monthlyOpeningClosingConfigured(), HttpStatus.OK);

	}

	@GetMapping("/listLocations")
	@ApiOperation(value = "Get List of All Office Locations", response = ResponseEntity.class)
	public ResponseEntity<List<String>> listAllHeaders() {
		log.info("Inside CalendarMasterController#listAllHeaders ");
		List<OfficeMaster> officeList = orgStructService.getOfficeList();
		List<String> logicalLocList = officeList.stream().map(OfficeMaster::getOfficeId).collect(Collectors.toList());
		log.info("Logical Location List : {}", logicalLocList);
		return new ResponseEntity<>(logicalLocList, HttpStatus.OK);
	}

	@GetMapping("/marchClose/{logicalCode}/{currentFiscalyr}")
	@ApiOperation(value = "March Calendar Closing", response = ResponseEntity.class)
	public ResponseEntity<Integer> marchClosing(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalCode") @NotBlank String logicalCode,
			@ApiParam(value = "Current Fiscal Year", required = true) @PathVariable("currentFiscalyr") @NotBlank String currentFiscalyr) {
		log.info("Inside CalendarMasterController#marchClosing ");
		return new ResponseEntity<Integer>(calendarService.marchClosing(logicalCode, currentFiscalyr), HttpStatus.OK);
	}

	@GetMapping("/marchCloseStatus/{logicalCode}/{currentFiscalyr}")
	@ApiOperation(value = "March Calendar Closing Status", response = ResponseEntity.class)
	public ResponseEntity<Boolean> marchClosingStatus(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalCode") @NotBlank String logicalCode,
			@ApiParam(value = "Current Fiscal Year", required = true) @PathVariable("currentFiscalyr") @NotBlank String currentFiscalyr) {
		log.info("Inside CalendarMasterController#marchClosingStatus ");
		return new ResponseEntity<Boolean>(calendarService.marchClosingStatus(logicalCode, currentFiscalyr),
				HttpStatus.OK);
	}

	@GetMapping("/getByGlType/{fiscalYr}/{month}/{logicalLocCode}/{glTxnType}")
	@ApiOperation(value = "Get Calendar by GL Type", response = ResponseEntity.class)
	public ResponseEntity<Calendar> getByFiscalYr(
			@ApiParam(value = "Fiscal Year", required = true) @PathVariable("fiscalYr") @NotBlank String fiscalYr,
			@ApiParam(value = "Month", required = true) @PathVariable("month") @NotBlank String month,
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCode") @NotBlank String logicalLocCode,
			@ApiParam(value = "Gl Txn Type", required = true) @PathVariable("glTxnType") @NotBlank String glTxnType) {
		log.info("Inside CalendarMasterController#getByFiscalYr ");
		return new ResponseEntity<Calendar>(
				calendarService.getByGlTypeLogicalLoc(fiscalYr, month, logicalLocCode, glTxnType), HttpStatus.OK);
	}

	@GetMapping("/getmarchclosed/{fiscalYr}/{logicalloc}")
	@ApiOperation(value = "Get All March Closed Calendars", response = ResponseEntity.class)
	public ResponseEntity<List<CalendarMstAnnual>> getCalendarMstAnnualList(
			@ApiParam(value = "Fiscal Year", required = true) @PathVariable("fiscalYr") @NotBlank String fiscalYr,
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalloc") @NotBlank String logicalloc) {
		log.info("Inside CalendarMasterController#getCalendarMstAnnualList ");
		List<CalendarMstAnnual> result = calendarService.getCalendarMstAnnualList(fiscalYr, logicalloc);
		log.info("List of March Closed Calendars {}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/reqclosedstat/{logicalCode}/{currentFiscalyr}")
	@ApiOperation(value = "Calendar Request Closing Status", response = ResponseEntity.class)
	public ResponseEntity<List<CalendarRequestModel>> ReqClosedStat(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalCode") @NotBlank String logicalCode,
			@ApiParam(value = "Current Fiscal Year", required = true) @PathVariable("currentFiscalyr") @NotBlank String currentFiscalyr) {
		log.info("Inside CalendarMasterController#ReqClosedStat ");
		return new ResponseEntity<List<CalendarRequestModel>>(
				calendarService.ReqClosedStat(logicalCode, currentFiscalyr), HttpStatus.OK);
	}

	@GetMapping("/reqopenedstat/{logicalCode}/{currentFiscalyr}")
	@ApiOperation(value = "Calendar Request Opening Status", response = ResponseEntity.class)
	public ResponseEntity<List<CalendarRequestModel>> ReqOpenedStat(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalCode") @NotBlank String logicalCode,
			@ApiParam(value = "Current Fiscal Year", required = true) @PathVariable("currentFiscalyr") @NotBlank String currentFiscalyr) {
		log.info("Inside CalendarMasterController#ReqOpenedStat ");
		return new ResponseEntity<List<CalendarRequestModel>>(
				calendarService.ReqOpenedStat(logicalCode, currentFiscalyr), HttpStatus.OK);
	}

}

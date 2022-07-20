package in.ecgc.smile.erp.accounts.controller;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.LOVMaster;
import in.ecgc.smile.erp.accounts.service.LOVMasterService;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/lov-service")
@Api(value = "LOV Service")
@Slf4j
public class LOVMasterController {

	@Autowired(required = true)
	LOVMasterService lovService;

	/*
	 * @Autowired(required = true) HRDService hrdService;
	 */

	@Autowired
	private UserInfoService userInfo;

	@GetMapping("/user-info")
	@ApiOperation(value = "User Info", response = ResponseEntity.class)
	public void userInfoDetails() {
		log.info("Inside LOVMasterController#userInfoDetails");
		log.info("ApplicableDepartmentForSession :{} ",  userInfo.getApplicableDepartmentForSession());
		log.info("ApplicableDesignationForSession :{} ", userInfo.getApplicableDesignationForSession());
		log.info("ApplicableOfficeTypeForSession:{} ",  userInfo.getApplicableOfficeTypeForSession());
		log.info("ApplicableRankForSession : {} " , userInfo.getApplicableRankForSession());
		log.info("AZP : {}" ,userInfo.getAZP());
		log.info("Email : {}", userInfo.getEmail());
		log.info("EmployeeId : {} ", userInfo.getEmployeeId());
		log.info("Name : {}" , userInfo.getName());
		log.info("PrimaryDepartment : {} " , userInfo.getPrimaryDepartment());
		log.info("PrimaryDesignation : {} " , userInfo.getPrimaryDesignation());
		log.info("PrimaryOffice : {} " , userInfo.getPrimaryOffice());
		log.info("PrimaryOfficeType : {} " , userInfo.getPrimaryOfficeType());
		log.info("PrimaryRank : {} ", userInfo.getPrimaryRank());
		log.info("User :{} " , userInfo.getUser());
		log.info("CurrentAdditionalChargeList : {}" , userInfo.getCurrentAdditionalChargeList());

	}

	@ApiOperation(value = "Add LOV Entry", response = ResponseEntity.class)
	@PostMapping("/add")
	public ResponseEntity<Integer> addLOVMstEntry(@RequestBody LOVMaster lovmst) {
		log.info("Inside LOVMasterController#addLOVMstEntry");
		log.info("lovmst is :{}", lovmst);
		int result = 0;
		result = lovService.addLovMstEntry(lovmst);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@ApiOperation(value = "View LOV Entries", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<LOVMaster>> viewAlllovList() {
		log.info("Inside LOVMasterController#viewAlllovList");
		ResponseEntity<List<LOVMaster>> responseEntity = null;

			List<LOVMaster> lovList = lovService.viewallLOVMstEntries();
			responseEntity = new ResponseEntity<>(lovList, HttpStatus.OK);
			return responseEntity;
	}

	@ApiOperation(value = "View LOV Entry", response = ResponseEntity.class)
	@GetMapping("/view/{lovCd}/{lovSubCd}/{lovValue}")
	public ResponseEntity<LOVMaster> viewLovEntry(
			@ApiParam(value = "LOV Code", required = true) @PathVariable("lovCd") @NotBlank String lovCd,
			@ApiParam(value = "LOV Sub Code", required = true) @PathVariable("lovSubCd") @NotBlank String lovSubCd,
			@ApiParam(value = "LOV Value", required = true) @PathVariable("lovValue") @NotBlank String lovValue) {
		log.info("Inside LOVMasterController#viewLovEntry");
		ResponseEntity<LOVMaster> responseEntity = null;
			LOVMaster lov = lovService.viewLovEntry(lovCd, lovSubCd, lovValue);
			responseEntity = new ResponseEntity<>(lov, HttpStatus.OK);
			return responseEntity;
	}

	@ApiOperation(value = "Get T1 codes", response = ResponseEntity.class)
	@GetMapping("/lov-get/t1Codes")
	public ResponseEntity<Map<String, String>> getT1Codes() {
		log.info("Inside LOVMasterController#getT1Codes");
		return new ResponseEntity<Map<String, String>>(lovService.t1Codes(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get T2 codes", response = ResponseEntity.class)
	@GetMapping("/lov-get/t2Codes")
	public ResponseEntity<Map<String, String>> getT2Codes() {
		log.info("Inside LOVMasterController#getT2Codes");
		return new ResponseEntity<Map<String, String>>(lovService.t2Codes(), HttpStatus.OK);
	}

	@ApiOperation(value = "Modify LOV entry", response = ResponseEntity.class)
	@PutMapping("/modify")
	public ResponseEntity<Integer> modifyLovEntry(@RequestBody LOVMaster lov) {
		log.info("Inside LOVMasterController#modifyLovEntry");
		int result = lovService.modifyLovEntry(lov);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "Disable LOV entry", response = ResponseEntity.class)
	@PutMapping("/disable/{lovCd}/{lovSubCd}/{lovValue}")
	public ResponseEntity<Integer> disableLovEntry(
			@ApiParam(value = "LOV Code", required = true) @PathVariable("lovCd") @NotBlank String lovCd,
			@ApiParam(value = "LOV Sub Code", required = true) @PathVariable("lovSubCd") @NotBlank String lovSubCd,
			@ApiParam(value = "LOV Value", required = true) @PathVariable("lovValue") @NotBlank String lovValue) {
			log.info("Inside LOVMasterController#disableLovEntry");
			Integer result = lovService.disableLovEntry(lovCd, lovSubCd, lovValue);
			return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "Get Pay To Type", response = ResponseEntity.class)
	@GetMapping("/lov-get/payTo")
	public ResponseEntity<Map<String, String>> getPayToType() {
		log.info("Inside LOVMasterController#getPayToType");
		return new ResponseEntity<Map<String, String>>(lovService.payTo(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get Currency Values", response = ResponseEntity.class)
	@GetMapping("/lov-get/currencyVal")
	public ResponseEntity<Map<String, String>> getCurrencyValue() {
		log.info("Inside LOVMasterController#getCurrencyValue");
		return new ResponseEntity<Map<String, String>>(lovService.currencyVal(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get Section Codes", response = ResponseEntity.class)
	@GetMapping("/lov-get/sectionCodes")
	public ResponseEntity<Map<String, String>> getSectionCodes() {
		log.info("Inside LOVMasterController#getSectionCodes");
		return new ResponseEntity<Map<String, String>>(lovService.sectionCodes(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get Request Types", response = ResponseEntity.class)
	@GetMapping("/lov-get/reuestTypes")
	public ResponseEntity<Map<String, String>> getrequestTypes() {
		log.info("Inside LOVMasterController#getrequestTypes");
		return new ResponseEntity<Map<String, String>>(lovService.requestTypes(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get FTR For", response = ResponseEntity.class)
	@GetMapping("/lov-get/ftrFor")
	public ResponseEntity<Map<String, String>> getfrtFor() {
		log.info("Inside LOVMasterController#getfrtFor");
		return new ResponseEntity<Map<String, String>>(lovService.frtFor(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get Dishonor Reasons", response = ResponseEntity.class)
	@GetMapping("/lov-get/dishonorReasons")
	public ResponseEntity<Map<String, String>> getdishonorReasons() {
		log.info("Inside LOVMasterController#getdishonorReasons");
		return new ResponseEntity<Map<String, String>>(lovService.dishonorReasons(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get Instrument Types", response = ResponseEntity.class)
	@GetMapping("/lov-get/instrumentTypes")
	public ResponseEntity<Map<String, String>> getInstrumentTypes() {
		log.info("Inside LOVMasterController#getInstrumentTypes");
		return new ResponseEntity<Map<String, String>>(lovService.getInstrumentTypes(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get Schedule Title Details", response = ResponseEntity.class)
	@GetMapping("/lov-get/scheduleTitleDetails")
	public ResponseEntity<Map<String, String>> getScheduleTitleDetails() {
		log.info("Inside LOVMasterController#getScheduleTitleDetails");
		return new ResponseEntity<Map<String, String>>(lovService.getScheduleTitleDetails(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get Prefix list", response = ResponseEntity.class)
	@GetMapping("/lov-get/prefixTypes")
	public ResponseEntity<Map<String, String>> getPrefixTypes() {
		log.info("Inside LOVMasterController#getPrefixTypes");
		return new ResponseEntity<Map<String, String>>(lovService.getPrefixTypes(), HttpStatus.OK);
	}
}

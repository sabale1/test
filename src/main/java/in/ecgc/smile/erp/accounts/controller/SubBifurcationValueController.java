package in.ecgc.smile.erp.accounts.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

import in.ecgc.smile.erp.accounts.model.SubBifurcationValue;
import in.ecgc.smile.erp.accounts.service.SubBifurcationValueService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sub-bifurcation-value")
@Api(value = "Sub Bifurcation Value ")
@Slf4j
public class SubBifurcationValueController {
	@Autowired
	private SubBifurcationValueService subBifurcationValueService;

	@GetMapping("/view-all")
	@ApiOperation(value = "View All Sub Bifurcation Values", response = ResponseModel.class)
	public ResponseEntity<List<SubBifurcationValue>> getSubBifurcationsDtlList() {
		log.info("Inside SubBifurcationValueController#getSubBifurcationsDtlList ");
		List<SubBifurcationValue> result = subBifurcationValueService.getSubBifurcationsDtlList();
		log.info("List of  Sub Bifurcation Values{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/get-by-id/{bifurcationLevelCode}/{bifurcationValueCode}")
	@ApiOperation(value = "View Sub Bifurcation Value by Sub Bifurcation Level Code and Value Code ", response = ResponseModel.class)
	public ResponseEntity<SubBifurcationValue> getSubBifurcationsDtlDataById(
			@ApiParam(value = "Sub Bifurcation Level Code", required = true) @PathVariable("bifurcationLevelCode") @Size(min = 3, max = 3, message = "Sub Bifurcation Level Code should be of 3 Characters") @NotBlank String bifurcationLevelCode,
			@ApiParam(value = "Sub Bifurcation Value Code", required = true) @PathVariable("bifurcationValueCode") @Size(min = 6, max = 6, message = "Sub Bifurcation Value Code should be of 3 Characters") @NotBlank String bifurcationValueCode) {
		log.info("Inside SubBifurcationValueController#getSubBifurcationsDtlDataById ");
		SubBifurcationValue result = subBifurcationValueService.getSubBifurcationsDtlDataById(bifurcationLevelCode,
				bifurcationValueCode);
		log.info("Sub Bifurcation Value {}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/add")
	@ApiOperation(value = "Add Sub Bifurcation Value ", response = ResponseModel.class)
	public ResponseEntity<Boolean> addSubBifurcationsDtlData(
			@Valid @RequestBody SubBifurcationValue subBifurcationsDtl) {
		log.info("Inside SubBifurcationValueController#addSubBifurcationsDtlData ");
		boolean result = subBifurcationValueService.addSubBifurcationsDtlData(subBifurcationsDtl);
		log.info("Add status of Sub Bifurcation Value {}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/update/{bifurcationlevelCode}/{bifurcationValueCode}")
	@ApiOperation(value = "Update Sub Bifurcation Value ", response = ResponseModel.class)
	public ResponseEntity<Boolean> updateSubBifurcationsDtlData(
			@ApiParam(value = "Sub Bifurcation Level Code", required = true) @PathVariable("bifurcationlevelCode") @Size(min = 3, max = 3, message = "Sub Bifurcation Level Code should be of 3 Characters") @NotBlank String bifurcationlevelCode,
			@ApiParam(value = "Sub Bifurcation Value Code", required = true) @PathVariable("bifurcationValueCode") @Size(min = 6, max = 6, message = "Sub Bifurcation Value Code should be of 3 Characters") @NotBlank String bifurcationValueCode,
			@RequestBody SubBifurcationValue subBifurcationValue) {
		log.info("Inside SubBifurcationValueController#updateSubBifurcationsDtlData ");
		boolean result = subBifurcationValueService.updateSubBifurcationsDtlData(bifurcationlevelCode,
				bifurcationValueCode, subBifurcationValue);
		log.info("Update status of Sub Bifurcation Value {}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/delete/{bifurcationlevelCode}/{bifurcationValueCode}")
	@ApiOperation(value = "Delete Sub Bifurcation Value", response = ResponseEntity.class)
	public ResponseEntity<Boolean> disableSubBifurcationValue(
			@ApiParam(value = "Sub Bifurcation Level Code", required = true) @PathVariable("bifurcationlevelCode") @Size(min = 3, max = 3, message = "Sub Bifurcation Level Code should be of 3 Characters") @NotBlank String bifurcationlevelCode,
			@ApiParam(value = "Sub Bifurcation Value Code", required = true) @PathVariable("bifurcationValueCode") @Size(min = 6, max = 6, message = "Sub Bifurcation Value Code should be of 3 Characters") @NotBlank String bifurcationValueCode) {
		log.info("Inside SubBifurcationValueController#disableSubBifurcationValue ");
		boolean result = subBifurcationValueService.disableSubBifurcationValue(bifurcationlevelCode,
				bifurcationValueCode);
		return new ResponseEntity<Boolean>(result, HttpStatus.ACCEPTED);

	}

	@GetMapping("/get-value-code/{levelCode}")
	@ApiOperation(value = "View Sub Bifurcation Value Code by Sub Bifurcation Level Code ", response = ResponseModel.class)
	public ResponseEntity<String> getValueCode(
			@ApiParam(value = "Sub Bifurcation Level Code", required = true) @PathVariable("levelCode") @Size(min = 3, max = 3, message = "Sub Bifurcation Level Code should be of 3 Characters") @NotBlank String bifurcationlevelCode) {
		log.info("Inside SubBifurcationValueController#getValueCode ");
		String result = subBifurcationValueService.getBifurcationCode(bifurcationlevelCode);
		log.info("Sub Bifurcation Value Code {}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/valueList/{levelCode}")
	@ApiOperation(value = "View List of Sub Bifurcation Values by Sub Bifurcation Level Code ", response = ResponseModel.class)
	public ResponseEntity<List<SubBifurcationValue>> getValueList(@ApiParam(value = "Sub Bifurcation Level Code", required = true)
	@PathVariable("levelCode") @NotBlank String levelCode) {
		log.info("Inside SubBifurcationValueController#getValueList ");
		List<SubBifurcationValue> valueList = new ArrayList<>();
		valueList = subBifurcationValueService.getAllSubBifurcationValueCodeByLevelCode(levelCode);
		log.info("List of Sub Bifurcation Values {}", valueList);
		return new ResponseEntity<>(valueList, HttpStatus.OK);
	}

	@GetMapping("/get-sub-bifurcations/{mainGLCode}/{subGLCode}")
	@ApiOperation(value = "View Sub Bifurcation Values by GL code", response = ResponseModel.class)
	public ResponseEntity<List<SubBifurcationValue>> findSubBifurcations(
			@ApiParam(value = "Main GL Code", required = true) @PathVariable("mainGLCode") @Size(min = 4, max = 4, message = "Main GL Should be of 4 Characters") @NotBlank String mainGLCode,
			@ApiParam(value = "Sub GL Code", required = true) @PathVariable("subGLCode") @Size(min = 3, max = 3, message = "Sub GL Should be of 3 Characters") @NotBlank String subGLCode) {
		log.info("Inside SubBifurcationValueController#findSubBifurcations ");		
		log.info("Fetching SubBifurcations for MainGL: {} SubGL: {}", mainGLCode, subGLCode);
//		System.err.println("on controller" + mainGLCode+"  "+ subGLCode);
		log.info("List Of SubNifurcation values is :: {}",subBifurcationValueService.findSubBifurcationValueList(mainGLCode, subGLCode));
		return new ResponseEntity<>(subBifurcationValueService.findSubBifurcationValueList(mainGLCode, subGLCode),HttpStatus.OK);
	}
}

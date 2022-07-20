package in.ecgc.smile.erp.accounts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import in.ecgc.smile.erp.accounts.model.Temp_Exporter;
import in.ecgc.smile.erp.accounts.model.Temp_TDS_DTL;
import in.ecgc.smile.erp.accounts.service.Temp_ExternalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/ext-service")
@Api(value = "Temporary Controller")
@Slf4j
public class Temp_ExternalController {

	@Autowired
	Temp_ExternalService externalService;

	@GetMapping(value = "/exporter/getList/{logicalLocCd}")
	@ApiOperation(value = "Get exporter list", response = ResponseEntity.class)
	public ResponseEntity<List<Temp_Exporter>> getExporterList(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") String logicalLocCd) {
		log.info("Inside Temp_ExternalController#getExporterList ");
		List<Temp_Exporter> expCode = externalService.getExporterList();
		log.info("List of Exporters {}", expCode);
		return new ResponseEntity<List<Temp_Exporter>>(expCode, HttpStatus.OK);
	}

	@GetMapping(value = "/exporter/tdsDtl")
	@ApiOperation(value = "Get TDS details", response = ResponseEntity.class)
	public ResponseEntity<Temp_TDS_DTL> getTDSDtl() {
		log.info("Inside Temp_ExternalController#getTDSDtl ");
		Temp_TDS_DTL tdsDtls = externalService.getTDSDtl();
		log.info("TDS Details {}", tdsDtls);
		return new ResponseEntity<Temp_TDS_DTL>(tdsDtls, HttpStatus.OK);
	}
}

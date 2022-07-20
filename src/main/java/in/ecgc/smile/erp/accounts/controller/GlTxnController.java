package in.ecgc.smile.erp.accounts.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import in.ecgc.smile.erp.accounts.dto.GLReportIn;
import in.ecgc.smile.erp.accounts.dto.GLReportOut;
import in.ecgc.smile.erp.accounts.model.GlBrBalanceIn;
import in.ecgc.smile.erp.accounts.model.GlBrBalanceOut;
import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.service.CalendarService;
import in.ecgc.smile.erp.accounts.service.GlTxnService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gl-txn")
@Api(value = "GL Transaction service")
@Slf4j
public class GlTxnController {

	@Autowired
	GlTxnService txnService;
	@Autowired
	CalendarService calenderService;

	@GetMapping("/view-all/{logicalloc}")
	@ApiOperation(value = "View All GL Codes by Logical Location Code", response = ResponseEntity.class)
	public ResponseEntity<List<GlTxnHdr>> getAllGltxn(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalloc") @NotBlank String logicalloc) {
		log.info("Inside GlTxnController#getAllGltxn");
		return new ResponseEntity<List<GlTxnHdr>>(txnService.getAllGlTxnHdrs(logicalloc), HttpStatus.OK);
	}
	
	@GetMapping("/view-all")
	@ApiOperation(value = "View All GL Codes", response = ResponseEntity.class)
	public ResponseEntity<List<GlTxnHdr>> getAllGltxnDtl(){
		log.info("Inside GlTxnController#getAllGltxnDtl");
		return new ResponseEntity<List<GlTxnHdr>>(txnService.getAllGlTxnDtl(), HttpStatus.OK);
	}
	

	@GetMapping("/view-all/{logicalloc}/{gltxntype}")
	@ApiOperation(value = "View All GL Codes by Logical Location Code and Gl Txn Type ", response = ResponseEntity.class)
	public ResponseEntity<List<GlTxnHdr>> getAllGltxnByTxnType(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalloc") @NotBlank String logicalloc,
			@ApiParam(value = "Gl Transaction Type", required = true) @PathVariable("gltxntype") @NotBlank String glTxntype) {

		log.info("Inside GlTxnController#getAllGltxnByTxnType");
		return new ResponseEntity<List<GlTxnHdr>>(txnService.getAllGlTxnHdrs(logicalloc, glTxntype), HttpStatus.OK);
	}

	@GetMapping("/get-txn/{fromDt}/{toDt}")
	@ApiOperation(value = "View All GL Codes Between Dates", response = ResponseEntity.class)
	public ResponseEntity<List<GlTxnHdr>> getAllGltxnByFromDt(
			@ApiParam(value = "From Date", required = true) @PathVariable("fromDt") @NotBlank String fromDt,
			@ApiParam(value = "To Date", required = true) @PathVariable("toDt") @NotBlank String toDt) {

		log.info("Inside GlTxnController#getAllGltxnByFromDt");

		return new ResponseEntity<List<GlTxnHdr>>(
				txnService.getAllGlTxnByFromDtToDt(LocalDate.parse(fromDt), LocalDate.parse(toDt)), HttpStatus.OK);
	}

	@GetMapping("/view/{gltxnno}/{logicalloc}/{gltxntype}")
	@ApiOperation(value = "View All GL Codes by Logical Location ,GL Txn Number and Type", response = ResponseEntity.class)
	public ResponseEntity<GlTxnHdr> getGltxn(
			@ApiParam(value = "Gl Transaction Number", required = true) @PathVariable("gltxnno") @NotBlank Integer glTxnNo,
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalloc") @NotBlank String logicalLoc,
			@ApiParam(value = "Gl Transaction Type", required = true) @PathVariable("gltxntype") @NotBlank String glTxnType) {

		log.info("Inside GlTxnController#getGltxn");

		return new ResponseEntity<GlTxnHdr>(txnService.getGlTxn(glTxnNo, logicalLoc, glTxnType), HttpStatus.OK);
	}

	@PostMapping("/add")
	@ApiOperation(value = "Create GL Txn", response = ResponseEntity.class)
	public ResponseEntity<Integer> addGLTxn(@RequestBody GlTxnHdr glTxnHdr) {
		log.info("Inside GlTxnController#addGLTxn");
		return new ResponseEntity<>(txnService.addGlTxn(glTxnHdr), HttpStatus.CREATED);
	}


	@PostMapping("/update")
	@ApiOperation(value = "Update GL Txn", response = ResponseModel.class)
	public ResponseEntity<Integer> updateGLTxnDtl(@RequestBody GlTxnDtl glTxnDtl) {

		log.info("Inside GlTxnController#updateGLTxnDtl");

		return new ResponseEntity<>(txnService.updateGlTxnDtl(glTxnDtl), HttpStatus.CREATED);
	}

	@PostMapping("/reverse")
	@ApiOperation(value = "GL Txn Reversal", response = ResponseModel.class)
	public ResponseEntity<Integer> reverseTxn(@RequestBody GlTxnHdr glTxnhdr) {

		log.info("Inside GlTxnController#reverseTxn");
		log.info("GlTxnHdr object is : {}",glTxnhdr);
		Integer result = txnService.reverseTransaction(glTxnhdr);
		log.info("Update Status {}", result);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@GetMapping("/getbalance/{mainGlCd}/{subGlCd}")
	@ApiOperation(value = "Get Balance", response = ResponseModel.class)
	public ResponseEntity<BigDecimal> getbalance(
			@ApiParam(value = "Main Gl Code", required = true) @PathVariable("mainGlCd") @NotBlank String mainGlCd,
			@ApiParam(value = "Sub Gl Code", required = true) @PathVariable("subGlCd") @NotBlank String subGlCd) {

		log.info("Inside GlTxnController#getbalance");

		return new ResponseEntity<>(txnService.getTotalAmt(mainGlCd, subGlCd), HttpStatus.OK);

	}

	@GetMapping("/getcrbalance/{mainGlCd}/{subGlCd}")
	@ApiOperation(value = "Get Total Credit Amount", response = ResponseModel.class)
	public ResponseEntity<BigDecimal> getCrbalance(
			@ApiParam(value = "Main Gl Code", required = true) @PathVariable("mainGlCd") @NotBlank String mainGlCd,
			@ApiParam(value = "Sub Gl Code", required = true) @PathVariable("subGlCd") @NotBlank String subGlCd) {

		log.info("Inside GlTxnController#getCrbalance");

		return new ResponseEntity<>(txnService.getTotalCreditAmt(mainGlCd, subGlCd), HttpStatus.OK);
	}

	@GetMapping("/get-txn/{fromDt}/{toDt}/{logicallocation}")
	@ApiOperation(value = "View All GL Codes between Dates by Logical Location Code ", response = ResponseEntity.class)
	public ResponseEntity<List<GlTxnHdr>> getAllGltxnByFromDtLoc(
			@ApiParam(value = "From Date", required = true) @PathVariable("fromDt") @NotBlank String fromDt,
			@ApiParam(value = "To Date", required = true) @PathVariable("toDt") @NotBlank String toDt,
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicallocation") @NotBlank String logicallocation) {

		log.info("Inside GlTxnController#getAllGltxnByFromDtLoc");
		List<GlTxnHdr> txnlist = txnService.getAllGltxnByFromDtLoc(LocalDate.parse(fromDt), LocalDate.parse(toDt),
				logicallocation);
		log.info("List Of All Gl Transactions {}", txnlist);
		return new ResponseEntity<List<GlTxnHdr>>(txnlist, HttpStatus.OK);
	}

	@GetMapping("/viewby/{glTxnNo}/{glTxnType}/{logicalLoc}")
	@ApiOperation(value = "View All GL Codes by Logical Location ,GL Txn Number and Type", response = ResponseEntity.class)
	public ResponseEntity<List<GlTxnHdr>> getAllGlTxnByTxnNoTxnTypeLoc(
			@ApiParam(value = "Gl Transaction Number", required = true)	@PathVariable("glTxnNo") @NotBlank Integer glTxnNo,
			@ApiParam(value = "Gl Transaction Type", required = true) @PathVariable("glTxnType") @NotBlank String glTxnType,
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLoc") @NotBlank String logicalLoc) {

		log.info("Inside GlTxnController#getAllGlTxnByTxnNoTxnTypeLoc");

		return new ResponseEntity<>(txnService.getAllGlTxnByTxnNoTxnTypeLoc(glTxnNo, glTxnType, logicalLoc),
				HttpStatus.OK);

	}

	@PostMapping("/getBrBalance")
	@ApiOperation(value = "View Balance by Main Gl Code, Sub GL Code, PL code", response = ResponseEntity.class)
	public ResponseEntity<GlBrBalanceOut> getBrBalance(@RequestBody GlBrBalanceIn balanceIn) {
		log.info("Inside GlTxnController#getBrBalance");
		GlBrBalanceOut balanceOut = txnService.getBrBalance(balanceIn);
		log.info("Return obj {}",balanceOut );
		return new ResponseEntity<>(balanceOut, HttpStatus.OK);

	}

	@PostMapping("/getPolicyReport")
	public ResponseEntity<GLReportOut> getPolicyReport(@RequestBody GLReportIn glReportIn){
		log.info("Inside GlTxnController#getPolicyReport");
		return new ResponseEntity<GLReportOut>(txnService.getPolicyReport(glReportIn),HttpStatus.OK);
	}

}

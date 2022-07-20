package in.ecgc.smile.erp.accounts.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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

import in.ecgc.smile.erp.accounts.model.PaymentAdvice;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceTcodes;
import in.ecgc.smile.erp.accounts.service.PaymentAdviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payment-advice")
@Api(value = "Payment advice controller")
@Slf4j
public class PaymentAdviceController {

	@Autowired
	PaymentAdviceService payAdviceService;

	@GetMapping(value = "/getSeqNo/{logicalLocCd}/{sectionCd}/{fiscalYear}")
	@ApiOperation(value = "Get current sequence number for payment advice number", response = ResponseEntity.class)
	public ResponseEntity<Integer> getSeqNo(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "Fiscal Year", required = true) @PathVariable("fiscalYear") @NotBlank String fiscalYear) {
		log.info("Inside PaymentAdviceController#getSeqNo ");
		return new ResponseEntity<Integer>(payAdviceService.getAdviceNo(logicalLocCd, sectionCd, fiscalYear),
				HttpStatus.OK);
	}

	@PutMapping(value = "/updateSeqNo/{logicalLocCd}/{sectionCd}/{fiscalYear}/{adviceNo}")
	@ApiOperation(value = "Update new sequence number for payment advice number", response = ResponseEntity.class)
	public ResponseEntity<Integer> updateSeqNo(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "Fiscal Year", required = true) @PathVariable("fiscalYear") @NotBlank String fiscalYear,
			@ApiParam(value = "Advice Number", required = true) @PathVariable("adviceNo") @NotBlank Integer adviceNo) {
		log.info("Inside PaymentAdviceController#updateSeqNo ");
		return new ResponseEntity<Integer>(payAdviceService.updateSeqNo(logicalLocCd, sectionCd, fiscalYear, adviceNo),
				HttpStatus.OK);
	}

	@PostMapping(value = "/add")
	@ApiOperation(value = "Create new payment advice entry", response = ResponseEntity.class)
	public ResponseEntity<Integer> addPaymentAdvice(@Valid @RequestBody PaymentAdvice payAdvice) {
		log.info("Inside PaymentAdviceController#addPaymentAdvice ");
		log.info("Add PaymentAdvice Object {}", payAdvice);
		return new ResponseEntity<Integer>(payAdviceService.addPaymentAdvice(payAdvice), HttpStatus.OK);
	}

	@GetMapping(value = "/getAll")
	@ApiOperation(value = "Get all payment advices", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentAdvice>> listAllPaymentAdvices() {
		log.info("Inside PaymentAdviceController#listAllPaymentAdvices ");

		List<PaymentAdvice> allPayAdvice = payAdviceService.listAllPaymentAdvice();
		log.info("List of All PaymentAdvices {}", allPayAdvice);
		if (allPayAdvice != null) {
			return new ResponseEntity<List<PaymentAdvice>>(allPayAdvice, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<PaymentAdvice>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/payTcodes/getAll")
	@ApiOperation(value = "Get all payment advice Tcodes", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentAdvice>> listAllPaymentAdviceTcodes() {
		log.info("Inside PaymentAdviceController#listAllPaymentAdviceTcodes ");

		List<PaymentAdvice> allPayAdviceTcodes = payAdviceService.listAllPaymentAdviceTcodes();
		log.info("List of All PaymentAdviceTcodes {}", allPayAdviceTcodes);
		if (allPayAdviceTcodes != null) {
			return new ResponseEntity<List<PaymentAdvice>>(allPayAdviceTcodes, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<PaymentAdvice>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/get/{logicalLocCd}/{sectionCd}/{adviceNo}")
	@ApiOperation(value = "Get payment advice by location, section and advice number", response = ResponseEntity.class)
	public ResponseEntity<PaymentAdvice> getPaymentAdviceByAdviceDtl(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "Advice Number", required = true) @PathVariable("adviceNo") @NotBlank Integer adviceNo) {

		log.info("Inside PaymentAdviceController#getPaymentAdviceByAdviceDtl ");

		PaymentAdviceTcodes payTcodes = new PaymentAdviceTcodes();
		PaymentAdvice payAdvice = new PaymentAdvice(payTcodes);

		payTcodes = payAdviceService.getPaymentAdviceTcodesDtl(logicalLocCd, sectionCd, adviceNo);
		log.info("Get PayTcodes Details : {} ", payTcodes);
		payAdvice = payAdviceService.getPaymentAdviceByAdviceDtl(logicalLocCd, sectionCd, adviceNo);
		log.info("Get PayAdvice Details : {}", payAdvice);
		payAdvice.setPayTcodes(payTcodes);

		return new ResponseEntity<PaymentAdvice>(payAdvice, HttpStatus.OK);
	}

	@GetMapping(value = "/get-tax-info/{logicalLocCd}/{sectionCd}/{adviceNo}")
	@ApiOperation(value = "Get payment advice tax info by location, section and advice number", response = ResponseEntity.class)
	public ResponseEntity<PaymentAdvice> getPaymentAdviceAndTax(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "Advice Number", required = true) @PathVariable("adviceNo") @NotBlank Integer adviceNo) {

		log.info("Inside PaymentAdviceController#getPaymentAdviceAndTax ");

		PaymentAdvice payAdvice = payAdviceService.getPaymentAdviceAndTaxDtl(logicalLocCd, sectionCd, adviceNo);
		log.info("Get PaymentAdviceAndTax {}", payAdvice);

		return new ResponseEntity<PaymentAdvice>(payAdvice, HttpStatus.OK);
	}

	@GetMapping(value = "/get/{logicalLocCd}/{sectionCd}/{fromDt}/{toDt}")
	@ApiOperation(value = "Get payment advices by location, section and date", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentAdvice>> getPaymentAdviceByLogicalLocSectionCd(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "From date", required = true) @PathVariable("fromDt") @NotBlank String fromDtStr,
			@ApiParam(value = "To date", required = true) @PathVariable("toDt") @NotBlank String toDtStr) {
		log.info("Inside PaymentAdviceController#getPaymentAdviceByLogicalLocSectionCd ");

		LocalDate fromDt = LocalDate.parse(fromDtStr);
		LocalDate toDt = LocalDate.parse(toDtStr);

		List<PaymentAdvice> payAdviceList = payAdviceService.getPaymentAdviceByLogicalLocSectionCd(logicalLocCd,
				sectionCd, fromDt, toDt);
		log.info("List of PaymentAdvices by LogicalLocCd, SectionCd and Date {}", payAdviceList);
		if (payAdviceList != null) {
			return new ResponseEntity<List<PaymentAdvice>>(payAdviceList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<PaymentAdvice>>(HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping(value = "/disable/{logicalLocCd}/{sectionCd}/{adviceNo}")
	@ApiOperation(value = "Disable the payment advice entry from the table", response = ResponseEntity.class)
	public ResponseEntity<Integer> disablePaymentAdvice(
			@ApiParam(value = "Advice Number", required = true) @PathVariable("adviceNo") @NotBlank Integer adviceNo,
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd) {
		log.info("Inside PaymentAdviceController#disablePaymentAdvice ");

		Integer res = payAdviceService.disablePaymentAdvice(logicalLocCd, sectionCd, adviceNo);

		if (res == 1) {
			return new ResponseEntity<Integer>(res, HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping(value = "/update")
	@ApiOperation(value = "Update payment advice", response = ResponseEntity.class)
	public ResponseEntity<Integer> updatePaymentAdvice(@Valid @RequestBody PaymentAdvice payAdvice) {
		log.info("Inside PaymentAdviceController#updatePaymentAdvice ");
		log.info("Modified PaymentAdvice Obj : {}", payAdvice);

		Integer res = payAdviceService.updatePaymentAdvice(payAdvice);

		if (res == 1) {
			Integer res1 = payAdviceService.updatePaymentAdviceTcodes(payAdvice);
			if (res1 == 1) {
				return new ResponseEntity<Integer>(res, HttpStatus.OK);
			} else {
				return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
			}

		} else {
			return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/getEntered/{logicalLocCd}/{sectionCd}/{adviceStat}")
	@ApiOperation(value = "Get payment advice entered status list", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentAdvice>> getEnteredPaymentAdvice(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "Advice Status", required = true) @PathVariable("adviceStat") @NotBlank String adviceStat) {
		log.info("Inside PaymentAdviceController#getEnteredPaymentAdvice ");

		List<PaymentAdvice> entrPayAdviceList = new ArrayList<PaymentAdvice>();

		entrPayAdviceList = payAdviceService.getEntrPaymentAdvice(logicalLocCd, sectionCd, adviceStat);
		log.info("List of Entered PaymentAdvices by LogicalLocCd, SectionCd {}", entrPayAdviceList);
		if (entrPayAdviceList != null) {
			return new ResponseEntity<>(entrPayAdviceList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping(value = "/takeDecision")
	@ApiOperation(value = "Take decision on payment advice", response = ResponseEntity.class)
	public ResponseEntity<Integer> takePaymentAdviceDecision(@Valid @RequestBody PaymentAdvice payAdvice) {
		log.info("Inside PaymentAdviceController#takePaymentAdviceDecision ");

		Integer res = payAdviceService.takeDecisionOnPaymentAdvice(payAdvice);

		if (res == 1) {
			return new ResponseEntity<Integer>(res, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/getAdviceNo/{logicalLocCd}/{sectionCd}/{fromDt}/{toDt}")
	@ApiOperation(value = "Get payment adviceNo by location, section and  and date", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentAdvice>> getAdviceNumberByLocSecDt(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "From date", required = true) @PathVariable("fromDt") @NotBlank String fromDtStr,
			@ApiParam(value = "To date", required = true) @PathVariable("toDt") @NotBlank String toDtStr) {
		log.info("Inside PaymentAdviceController#getAdviceNumberByLocSecDt ");

		LocalDate fromDt = LocalDate.parse(fromDtStr);
		LocalDate toDt = LocalDate.parse(toDtStr);

		List<PaymentAdvice> payAdviceList = payAdviceService.getAdviceNumberByLocSecDt(logicalLocCd, sectionCd, fromDt,
				toDt);
		log.info("List of PaymentAdvices For AdviceNo by LogicalLocCd, SectionCd and Date {}", payAdviceList);
		if (payAdviceList != null) {
			return new ResponseEntity<List<PaymentAdvice>>(payAdviceList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<PaymentAdvice>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/getAdviceNo/{logicalLocCd}/{sectionCd}")
	@ApiOperation(value = "Get payment adviceNo by location and section", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentAdvice>> getAdviceNumberByLocSec(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd) {
		log.info("Inside PaymentAdviceController#getAdviceNumberByLocSec ");
		List<PaymentAdvice> payAdviceList = payAdviceService.getAdviceNumberByLocSec(logicalLocCd, sectionCd);
		log.info("List of PaymentAdvices For AdviceNo by LogicalLocCd, SectionCd {}", payAdviceList);
		if (payAdviceList != null) {
			return new ResponseEntity<List<PaymentAdvice>>(payAdviceList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<PaymentAdvice>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/get-approved/{logicalLocCd}/{sectionCd}/{fiscalYear}")
	@ApiOperation(value = "Get approved payment advice by location, section and fiscal year", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentAdvice>> getApprPaymentAdviceByLocSec(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "Fiscal Year", required = true) @PathVariable("fiscalYear") @NotBlank String fiscalYear) {
		log.info("Inside PaymentAdviceController#getApprPaymentAdviceByLocSec ");
		List<PaymentAdvice> payAdviceList = payAdviceService.getApprPaymentAdviceByDtl(logicalLocCd, sectionCd,
				fiscalYear);
		log.info("List of Approved PaymentAdvices by LogicalLocCd, SectionCd {}", payAdviceList);
		return new ResponseEntity<>(payAdviceList, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAprvAdviceNo/{logicalLocCd}/{sectionCd}")
	@ApiOperation(value = "Get payment adviceNo by location and section", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentAdvice>> getAprvAdviceNumberByLocSec(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd) {
		log.info("Inside PaymentAdviceController#getAprvAdviceNumberByLocSec ");
		List<PaymentAdvice> payAdviceList = payAdviceService.getAprvAdviceNumberByLocSec(logicalLocCd, sectionCd);
		log.info("List of Approved PaymentAdvices For AdviceNo by LogicalLocCd, SectionCd {}", payAdviceList);
		if (payAdviceList != null) {
			return new ResponseEntity<List<PaymentAdvice>>(payAdviceList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<PaymentAdvice>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping(value = "/getAprvAdviceNo/{logicalLocCd}/{sectionCd}/{fromDt}/{toDt}")
	@ApiOperation(value = "Get payment adviceNo by location, section and  and date", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentAdvice>> getAprvAdviceNumberByLocSecDt(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "From date", required = true) @PathVariable("fromDt") @NotBlank String fromDtStr,
			@ApiParam(value = "To date", required = true) @PathVariable("toDt") @NotBlank String toDtStr) {
		log.info("Inside PaymentAdviceController#getAprvAdviceNumberByLocSecDt ");

		LocalDate fromDt = LocalDate.parse(fromDtStr);
		LocalDate toDt = LocalDate.parse(toDtStr);

		List<PaymentAdvice> payAdviceList = payAdviceService.getAprvAdviceNumberByLocSecDt(logicalLocCd, sectionCd, fromDt,
				toDt);
		log.info("List of PaymentAdvices For AdviceNo by LogicalLocCd, SectionCd and Date {}", payAdviceList);
		if (payAdviceList != null) {
			return new ResponseEntity<List<PaymentAdvice>>(payAdviceList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<PaymentAdvice>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping(value = "/updateToUnpaid/{logicalLocCd}/{sectionCd}/{adviceNo}")
	@ApiOperation(value = "Update the payment advice advice_stat to UNPAID", response = ResponseEntity.class)
	public ResponseEntity<Integer> updatePaymentAdviceDtltoUNPAID(
			@ApiParam(value = "Advice Number", required = true) @PathVariable("adviceNo") @NotBlank Integer adviceNo,
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd) {
		log.info("Inside PaymentAdviceController#updatePaymentAdviceDtltoUNPAID ");

		Integer res = payAdviceService.updatePaymentAdviceDtltoUNPAID(logicalLocCd, sectionCd, adviceNo);

		if (res == 1) {
			return new ResponseEntity<Integer>(res, HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
		}
	}
}

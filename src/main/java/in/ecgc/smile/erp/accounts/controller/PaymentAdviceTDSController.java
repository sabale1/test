package in.ecgc.smile.erp.accounts.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import in.ecgc.smile.erp.accounts.model.PaymentAdviceAll;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceGstTdsDtl;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceTdsDtl;
import in.ecgc.smile.erp.accounts.service.PaymentAdviceTDSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/payment-advice-tds")
@Api(value = "Payment advice TDS controller")
public class PaymentAdviceTDSController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentAdviceController.class);

	@Autowired
	PaymentAdviceTDSService payAdviceTdsService;

	@GetMapping(value = "/get/{logicalLocCd}/{sectionCd}/{adviceNo}")
	@ApiOperation(value = "Get Approved Payment Advice by Advice Number", response = ResponseEntity.class)
	public ResponseEntity<PaymentAdvice> getApprovedPaymentAdviceDtl(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "Advice Number", required = true) @PathVariable("adviceNo") @NotBlank Integer adviceNo) {

		LOGGER.info("Inside PaymentAdviceTDSController#getApprovedPaymentAdviceDtl");

		PaymentAdvice payAdvice = new PaymentAdvice();

		payAdvice = payAdviceTdsService.getApprovedPaymentAdviceDtl(logicalLocCd, sectionCd, adviceNo);
		LOGGER.info("Get PayAdvice by logicalLocCd, sectionCd, adviceNo : {} ", payAdvice);

		if (payAdvice != null) {
			return new ResponseEntity<PaymentAdvice>(payAdvice, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/get-approved-list/{logicalLocCd}/{sectionCd}/{fromDt}/{toDt}")
	@ApiOperation(value = "Get All Approved Payment Advices by Location, Section and Date", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentAdvice>> getApprovedPaymentAdvices(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "From date", required = true) @PathVariable("fromDt") @NotBlank String fromDtStr,
			@ApiParam(value = "To date", required = true) @PathVariable("toDt") @NotBlank String toDtStr) {

		LOGGER.info("Inside PaymentAdviceTDSController#getApprovedPaymentAdvices");

		LocalDate fromDt = LocalDate.parse(fromDtStr);
		LocalDate toDt = LocalDate.parse(toDtStr);

		List<PaymentAdvice> payAdviceList = new ArrayList<>();

		payAdviceList = payAdviceTdsService.getApprovedPaymentAdvices(logicalLocCd, sectionCd, fromDt, toDt);
		LOGGER.info("Get PayAdvice Approved List by logicalLocCd, sectionCd, fromDt, toDt : {} ", payAdviceList);
		if (payAdviceList != null) {
			return new ResponseEntity<List<PaymentAdvice>>(payAdviceList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/get-approved-list-byadviceno/{logicalLocCd}/{sectionCd}/{adviceNo}")
	@ApiOperation(value = "Get All Approved Payment Advices by Location, Section, Advice Number", response = ResponseEntity.class)
	public ResponseEntity<List<PaymentAdvice>> getApprovedPaymentAdvicesbyNo(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "adviceNo", required = true) @PathVariable("adviceNo") @NotBlank Integer adviceNo) {

		LOGGER.info("Inside PaymentAdviceTDSController#getApprovedPaymentAdvicesbyNo");

		List<PaymentAdvice> payAdviceList = new ArrayList<>();

		payAdviceList = payAdviceTdsService.getApprovedPaymentAdvicesbyNo(logicalLocCd, sectionCd, adviceNo);
		LOGGER.info("Get PayAdvice Approved List by logicalLocCd, sectionCd, adviceNo : {} ", payAdviceList);
		if (payAdviceList != null) {
			return new ResponseEntity<List<PaymentAdvice>>(payAdviceList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping(value = "/updateNotApplicable")
	@ApiOperation(value = "Update Payment Advice When TDS is NOT Applicable", response = ResponseEntity.class)
	public ResponseEntity<Integer> updatePaymentAdviceTdsNOTAppliacble(
			@Valid @RequestBody PaymentAdvice paymentAdvice) {

		LOGGER.info("Inside PaymentAdviceTDSController#updatePaymentAdviceTdsNOTAppliacble");

		Integer res = payAdviceTdsService.updatePaymentAdviceTdsNOTAppliacble(paymentAdvice);

		if (res == 1) {
			return new ResponseEntity<Integer>(res, HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping(value = "/insert-tds-dtl")
	@ApiOperation(value = "Insert Payment Advice TDS Details When TDS is Applicable", response = ResponseEntity.class)
	public ResponseEntity<Integer> createPaymentAdviceTDS(@Valid @RequestBody PaymentAdviceAll payAdvAll) {

		LOGGER.info("Inside PaymentAdviceTDSController#createPaymentAdviceTDS");

		Integer res3;
		Integer res1;
		Integer res2;

		Integer rcy = 0;
		Integer rcygsty = 0;
		Integer rcygstn = 0;

		LOGGER.info("PayAdviceAll : {} ", payAdvAll);

		LOGGER.info("is RC APP ? : {}", payAdvAll.getPayAdvTdsDtl().getRevChargeApp());
		LOGGER.info("is GST APP ? : {}", payAdvAll.getPayAdvGstTdsDtl().getGstTdsApplicable());
		if (payAdvAll.getPayAdvTdsDtl().getRevChargeApp() == 'Y') {
			res1 = payAdviceTdsService.updatePaymentAdviceRCAppliacble(payAdvAll.getPaymentAdvice());
			res2 = payAdviceTdsService.addPaymentAdviceTDSDtl(payAdvAll.getPayAdvTdsDtl());
			LOGGER.info("01 : RES1 : {} , RES2 : {} ", res1, res2);
			if (res1 == 1) {
				if (res2 == 1) {
					rcy = 1;
				} else {
					rcy = -1;
				}

			} else {
				rcy = -1;
			}
		} else if (payAdvAll.getPayAdvTdsDtl().getRevChargeApp() == 'N') {
			if (payAdvAll.getPayAdvGstTdsDtl().getGstTdsApplicable() == 'Y') {
				res1 = payAdviceTdsService.updatePaymentAdviceRCAppliacble(payAdvAll.getPaymentAdvice());
				res2 = payAdviceTdsService.addPaymentAdviceTDSDtl(payAdvAll.getPayAdvTdsDtl());
				res3 = payAdviceTdsService.addPaymentAdviceGSTTDSDtl(payAdvAll.getPayAdvGstTdsDtl());
				LOGGER.info("02 : RES1 : {} , RES2 : {} , RES3 : {}", res1, res2, res3);
				if (res1 == 1) {
					rcygsty = 1;
				} else if (res2 == 1) {
					rcygsty = 1;
				} else if (res3 == 1) {
					rcygsty = 1;
				} else {
					rcygsty = -1;
				}
			} else if (payAdvAll.getPayAdvGstTdsDtl().getGstTdsApplicable() == 'N') {
				res1 = payAdviceTdsService.updatePaymentAdviceRCAppliacble(payAdvAll.getPaymentAdvice());
				res2 = payAdviceTdsService.addPaymentAdviceTDSDtl(payAdvAll.getPayAdvTdsDtl());
				LOGGER.info("03 : RES1 : {} , RES2 : {} ", res1, res2);
				if (res1 == 1) {
					rcygstn = 1;
				} else if (res2 == 1) {
					rcygstn = 1;
				} else {
					rcygstn = -1;
				}
			}
		}

		if (rcy == 1) {
			LOGGER.info("04 : RCY : {} ", rcy);
			return new ResponseEntity<Integer>(new Integer(1), HttpStatus.OK);
		} else if (rcygsty == 1) {
			LOGGER.info("04 :  RCYGSTY : {} ", rcygsty);
			return new ResponseEntity<Integer>(new Integer(1), HttpStatus.OK);
		} else if (rcygstn == 1) {
			LOGGER.info("04 : RCYGSTN : {} ", rcygstn);
			return new ResponseEntity<Integer>(new Integer(1), HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(new Integer(-1), HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/get-payAdv-tds-dtl/{logicalLocCd}/{sectionCd}/{adviceNo}")
	@ApiOperation(value = "Get Payment Advice TDS Dtl", response = ResponseEntity.class)
	public ResponseEntity<PaymentAdviceTdsDtl> getPayAdvTdsDtl(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "Advice Number", required = true) @PathVariable("adviceNo") @NotBlank Integer adviceNo) {
		LOGGER.info("Inside PaymentAdviceTDSController#getPayAdvTdsDtl");
		PaymentAdviceTdsDtl payAdvTdsDtl = new PaymentAdviceTdsDtl();

		payAdvTdsDtl = payAdviceTdsService.getPaymentAdviceTDSDtl(logicalLocCd, sectionCd, adviceNo);
		LOGGER.info("Get PaymentAdviceTdsDtl : {} ", payAdvTdsDtl);
		if (payAdvTdsDtl != null) {
			return new ResponseEntity<PaymentAdviceTdsDtl>(payAdvTdsDtl, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/get-payAdv-gst-tds-dtl/{logicalLocCd}/{sectionCd}/{adviceNo}")
	@ApiOperation(value = "Get Payment Advice GST TDS Dtl", response = ResponseEntity.class)
	public ResponseEntity<PaymentAdviceGstTdsDtl> getPayAdvGstTdsDtl(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
			@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
			@ApiParam(value = "Advice Number", required = true) @PathVariable("adviceNo") @NotBlank Integer adviceNo) {
		LOGGER.info("Inside PaymentAdviceTDSController#getPayAdvGstTdsDtl");
		PaymentAdviceGstTdsDtl payAdvGstTdsDtl = new PaymentAdviceGstTdsDtl();

		payAdvGstTdsDtl = payAdviceTdsService.getPaymentAdviceGSTTDSDtl(logicalLocCd, sectionCd, adviceNo);
		LOGGER.info("Get PaymentAdviceGstTdsDtl : {} ", payAdvGstTdsDtl);
		if (payAdvGstTdsDtl != null) {
			return new ResponseEntity<PaymentAdviceGstTdsDtl>(payAdvGstTdsDtl, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}

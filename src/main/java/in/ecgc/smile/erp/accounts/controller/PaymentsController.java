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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.ecgc.smile.erp.accounts.model.Payments;
import in.ecgc.smile.erp.accounts.model.PaymentsTcodes;
import in.ecgc.smile.erp.accounts.service.PaymentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/payment")
@Api(value = "payments service")
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    @PostMapping("/create-payment-entry")
    @ApiOperation(value = "Create Payment Entry", response = ResponseEntity.class)
    public ResponseEntity<Integer> createPaymentEntry(@RequestBody Payments payments) {
        log.info("Inside PaymentsController#createPaymentEntry ");
        log.info("Payments Object : {}"+payments);
        Integer paymentNo = paymentsService.createPaymentEntry(payments);
        log.info("Payment number is : {}", paymentNo);
        return new ResponseEntity<>(paymentNo, HttpStatus.OK);
    }

    @GetMapping("/get-payments-by-dtl/{paymentNo}/{logicalLocCd}/{sectionCd}")
    @ApiOperation(value = "Get Payments Details By PaymentNo, Location and Section", response = ResponseEntity.class)
    public ResponseEntity<Payments> getPaymentsByPaymentDtl(
            @ApiParam(value = "Payment Number", required = true) @PathVariable("paymentNo") @NotBlank Integer pymtNo,
            @ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
            @ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd) {
        log.info("Inside PaymentsController#getPaymentsByPaymentDtl ");
        Payments result = paymentsService.getPaymentsByPaymentDtl(pymtNo, logicalLocCd, sectionCd);
        log.info("Get PaymentDtls {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get-payments-data")
    @ApiOperation(value = "Get Payments List", response = ResponseEntity.class)
    public ResponseEntity<List<Payments>> getPaymentsList() {
        log.info("Inside PaymentsController#getPaymentsList ");
        List<Payments> result = paymentsService.getPaymentsList();
        log.info("List of All  Payments {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PostMapping("/update-payments-data")
    @ApiOperation(value = "Update Payments Data", response = ResponseEntity.class)
    public ResponseEntity<Boolean> updatePaymentsData(@RequestBody Payments payments) {
        log.info("Inside PaymentsController#updatePaymentsData ");
        log.info("Modified Payments Object: {}", payments);
        boolean result = paymentsService.updatePaymentsData(payments);
        log.info("Update Status :{}", result);

        if (result == true) {
            log.info("To update PaymentTcode data");
            boolean res1 = paymentsService.updatePaymentTcodes(payments);
            log.info("res1 :{}", res1);
            if (res1 == true) {
                return new ResponseEntity<Boolean>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
            }

        } else {
            return new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
        }

    }

    @PostMapping("/create-payment-json")
    @ApiOperation(value = "Create Payment Json", response = ResponseEntity.class)
    public ResponseEntity<String> createPaymentJson() {

        log.info("Inside PaymentsController#createPaymentEntryJson ");

        // Integer paymentNo = paymentsService.createPaymentEntry(payments);

        Integer paymentNo = 1;
        PaymentsTcodes paymentsTcodes = new PaymentsTcodes();
        Payments payments = new Payments(paymentsTcodes);

        ObjectMapper mapper = new ObjectMapper();
        String paymentJson = null;
        try {
            paymentJson = mapper.writeValueAsString(payments);

            log.info("JSON String : {}", paymentJson);

        } catch (JsonProcessingException e) {
            log.error("Error While Processing json {}",e);
        }

        log.info("Paayment Number {}", paymentNo);
        return new ResponseEntity<>(paymentJson, HttpStatus.OK);
    }

    @GetMapping("/getpaymentsbylocsecdt/{logicalLocCd}/{sectionCd}/{fromDt}/{toDt}")
    @ApiOperation(value = "Get Payments By Location, Section and Date", response = ResponseEntity.class)
    public ResponseEntity<List<Payments>> getpaymentsbylocsecdt(
            @ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
            @ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
            @ApiParam(value = "From Date", required = true) @PathVariable("fromDt") @NotBlank String fromDt,
            @ApiParam(value = "To Date", required = true) @PathVariable("toDt") @NotBlank String toDt) {
        log.info("Inside PaymentsController#getpaymentsbylocsecdt ");
        List<Payments> result = paymentsService.getpaymentsbylocsecdt(logicalLocCd, sectionCd, fromDt, toDt);
        log.info("List of All  Payments By Logical Location, Section Code and Date {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getpaymentsbydt/{fromDt}/{toDt}")
    @ApiOperation(value = "Get Payments By FromDate and ToDate", response = ResponseEntity.class)
    public ResponseEntity<List<Payments>> getpaymentsbydt(
            @ApiParam(value = "From Date", required = true) @PathVariable("fromDt") @NotBlank String fromDt,
            @ApiParam(value = "To Date", required = true) @PathVariable("toDt") @NotBlank String toDt) {
        log.info("Inside PaymentsController#getpaymentsbydt ");
        List<Payments> result = paymentsService.getpaymentsbydt(fromDt, toDt);
        log.info("List of All  Payments By Date {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


	
	@GetMapping("/getpaymentsbypymtno/{paymentNo}/{logicalLocCd}/{sectionCd}")
	@ApiOperation(value = "Get Payments By Payments Number,Location, and Section", response = ResponseEntity.class)
	public ResponseEntity<Payments> getPaymentsByPymtNo(
		@ApiParam(value = "Payment Number", required = true) @PathVariable("paymentNo") @NotBlank Integer pymtNo,
		@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
		@ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd) {
		log.info("Inside PaymentsController#getPaymentsByPymtNo ");
		Payments payments = paymentsService.getPaymentsByPymtNo(pymtNo, logicalLocCd, sectionCd);
		log.info("Payments data is : {}", payments);
		return new ResponseEntity<Payments>(payments, HttpStatus.OK);
		
	}

    @GetMapping("/getpaymentsbylocsecyr/{logicalLocCd}/{sectionCd}/{fiscalYr}")
    @ApiOperation(value = "Get Payments By Location, Section and Fiscal Year", response = ResponseEntity.class)
    public ResponseEntity<List<Payments>> getpaymentsbyLocSecYr(
            @ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
            @ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
            @ApiParam(value = "Fiscal Year", required = true) @PathVariable("fiscalYr") @NotBlank String fiscalYr) {
        log.info("Inside PaymentsController#getpaymentsbyLocSecYr ");
        List<Payments> result = paymentsService.getpaymentsbyLocSecYr(logicalLocCd, sectionCd, fiscalYr);
        log.info("List of All  Payments By Logical Location, Section Code and Fiscal Year {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/takedecision/{logicalLocCd}/{sectionCd}/{paymentNo}/{decision}")
    @ApiOperation(value = "Take decision on payment", response = ResponseEntity.class)
    public ResponseEntity<Integer> takeDecision(
    		 @ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalLocCd") @NotBlank String logicalLocCd,
             @ApiParam(value = "Section Code", required = true) @PathVariable("sectionCd") @NotBlank String sectionCd,
             @ApiParam(value = "Payment Number", required = true) @PathVariable("paymentNo") @NotBlank Integer paymentNo,
             @ApiParam(value = "Decision", required = true) @PathVariable("decision") @NotBlank String decision){
    	 log.info("Inside PaymentsController#takeDecision");

    	 return new ResponseEntity<Integer>(paymentsService.takeDecision(logicalLocCd, sectionCd, paymentNo, decision), HttpStatus.OK);
    }


}

package in.ecgc.smile.erp.accounts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.model.ReceiptDto;
import in.ecgc.smile.erp.accounts.service.ExternalReceiptService;
import in.ecgc.smile.erp.accounts.util.ResponseModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("extreceipt")
@Slf4j
public class ExternalReceiptController {
	
	@Autowired
	ExternalReceiptService receiptService; 
	
	
	
	@PostMapping("/add")
	@ApiOperation(value = "Add Receipt Detail", response = ResponseModel.class)
	public ResponseEntity<Integer> addReceipt(@Valid @RequestBody ReceiptDto receipt) {
		log.info("Inside ReceiptController#addReceipt ");
		log.info("New Receipt Details {} ",receipt);
		ResponseEntity<Integer> model ;
		Integer result;
		try {
			result = receiptService.addReceipt(receipt);
			if(result !=0) {
				model = new ResponseEntity<>(result,HttpStatus.CREATED);
			}else {
				model = new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);
			}
		} 
		catch (Exception e) {
			model = new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);
			log.error("Controller Level, Exception while adding receipt {}",e);
		}
		return model;
	}

	
	
}

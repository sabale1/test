package in.ecgc.smile.erp.accounts.controller;

import java.text.ParseException;
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

import in.ecgc.smile.erp.accounts.model.PostDatedCheque;
import in.ecgc.smile.erp.accounts.service.PDCService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/post-dated-cheque")
@Api(value = "Post dated cheque service")
@Slf4j
public class PDCController {

	@Autowired
	PDCService pdcService;

	@PostMapping(value = "/add")
	@ApiOperation(value = "Create new post dated cheque entry", response = ResponseEntity.class)
	public ResponseEntity<PostDatedCheque> createPDCEntry(@Valid @RequestBody PostDatedCheque pdc){
		log.info("Inside PDCController#createPDCEntry ");
		log.info("New post dated cheque entry Obj {}", pdc);
		pdcService.createPDCEntry(pdc);
		return new ResponseEntity<>(pdc, HttpStatus.CREATED);
	}	
	
	@GetMapping(value = "/view-all-entries")
	@ApiOperation(value = "View all post dated cheque entries", response = ResponseEntity.class)
	public ResponseEntity<List<PostDatedCheque>> viewAllPDCEntry(){
		log.info("Inside PDCController#viewAllPDCEntry ");
		List<PostDatedCheque> allPDC = pdcService.listAllPDC();
		log.info("List of All Post Dated Cheque Entries {}", allPDC);
		if (allPDC != null) {
			return new ResponseEntity<List<PostDatedCheque>>(allPDC, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<PostDatedCheque>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/view-by-chq-no/{cheqId}")
	@ApiOperation(value = "View cheque entry by cheque ID", response = ResponseEntity.class)
	public ResponseEntity<PostDatedCheque> viewByChequeNo(
			@ApiParam(value = "Cheque Number", required = true)
			@PathVariable("cheqId") @NotBlank Integer cheqId){
		log.info("Inside PDCController#viewByChequeNo ");
		log.info("Chq no : {} ", cheqId);
		PostDatedCheque pdc = pdcService.viewByChequeNo(cheqId);
		log.info("Post dated cheque {}", pdc);
		return new ResponseEntity<PostDatedCheque>(pdc,HttpStatus.OK);
	}

	@GetMapping("/check-unique/{chequeNo}/{chequeDate}")
	@ApiOperation(value = "View cheque entry by cheque ID", response = ResponseEntity.class)
	public ResponseEntity<PostDatedCheque> checkUnique(
			@ApiParam(value = "Cheque Number", required = true)
			@PathVariable("chequeNo")  @NotBlank String chequeNo,
			@ApiParam(value = "Cheque Date", required = true)
			@PathVariable("chequeDate")  @NotBlank String chequeDate) throws ParseException{
			log.info("Inside PDCController#checkUnique ");
			log.info("Chq dt :"+chequeDate);
			chequeDate = chequeDate.replaceAll("[-+^]*", "");
			log.info("Chq dt :"+chequeDate);
			
		PostDatedCheque pdc = pdcService.checkUnique(chequeNo,chequeDate );
		log.info("Post dated cheque {}", pdc);
		return new ResponseEntity<PostDatedCheque>(pdc,HttpStatus.OK);
	}
	
	@GetMapping("/view-by-chq-status/{chqstatus}")
	@ApiOperation(value = "View cheque entry by cheque status", response = ResponseEntity.class)
	public ResponseEntity<List<PostDatedCheque>> viewByChequeStatus(
			@ApiParam(value = "Cheque Status", required = true)
			@PathVariable("chqstatus")@NotBlank Character chqstatus){
		log.info("Inside PDCController#viewByChequeStatus ");
		List<PostDatedCheque> pdc = pdcService.viewByStatus(chqstatus);
		log.info("List of Post dated cheques by cheque status {}", pdc);
		return new ResponseEntity<List<PostDatedCheque>>(pdc,HttpStatus.OK);
	}

	@PutMapping("update/{chqno}")
	@ApiOperation(value = "Change cheque status", response = ResponseEntity.class)
	public ResponseEntity<Integer> updateChqStatus(
			@ApiParam(value = "Cheque Number", required = true)
			@PathVariable("chqno") @NotBlank String chqno,
			@Valid @RequestBody PostDatedCheque pdc) {
		log.info("Inside PDCController#updateChqStatus ");
		Integer result = pdcService.changeStatus(chqno,pdc);
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}
}

package in.ecgc.smile.erp.accounts.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.ecgc.smile.erp.accounts.model.FiscalYearModel;
import in.ecgc.smile.erp.accounts.model.LOVMaster;
import in.ecgc.smile.erp.accounts.model.TrialBalance;
import in.ecgc.smile.erp.accounts.model.TrialBalanceAllLocation;
import in.ecgc.smile.erp.accounts.model.TrialBalanceSingleLocation;
import in.ecgc.smile.erp.accounts.service.FiscalYearService;
import in.ecgc.smile.erp.accounts.service.LOVMasterService;
import in.ecgc.smile.erp.accounts.service.TrialBalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

@Slf4j
@RestController
@RequestMapping("/trial-bal")
@Api(value = "Trial Balance service")
public class TrialBalanceController {

	@Autowired
	TrialBalanceService trialBalanceService;
	
	@Autowired(required = true)
	LOVMasterService lovService;

	@GetMapping("/getByLogicalLoc/{logicalloc}/{balancedate}")
	@ApiOperation(value = "Get Trial Balance By Logical Location", response = ResponseEntity.class)
	public ResponseEntity<TrialBalanceSingleLocation> getByLogicalLoc(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalloc") @NotBlank String logicalloc,
			@ApiParam(value = "Balance Date", required = true) @PathVariable("balancedate") @NotBlank String balancedate) {
		log.info("Inside TrialBalanceController#getFiscalYrDataById");
		TrialBalanceSingleLocation result = trialBalanceService.getByLogicalLoc(logicalloc,balancedate);
		log.info("RESULT::{}", result);
	//	this.getTrialBalancePDF(result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	
	@GetMapping("/getForAllLocation/{balancedate}")
	@ApiOperation(value = "Get Trial Balance For All Location", response = ResponseEntity.class)
	public ResponseEntity<TrialBalanceAllLocation> getForAllLocation(
			@ApiParam(value = "Balance Date", required = true) @PathVariable("balancedate") @NotBlank String balancedate) {
		log.info("Inside TrialBalanceController#getForAllLocation");
		TrialBalanceAllLocation result = trialBalanceService.getForAllLocation(balancedate);
		log.info("RESULT::{}", result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	
	
	
	
	@GetMapping(value = "/pdf/{logicalloc}/{balancedate}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getTrialBalancePDF(
			@ApiParam(value = "Logical Location Code", required = true) @PathVariable("logicalloc") @NotBlank String logicalloc,
			@ApiParam(value = "Balance Date", required = true) @PathVariable("balancedate") @NotBlank String balancedate ) throws JRException, IOException {
		TrialBalanceSingleLocation result = trialBalanceService.getByLogicalLoc(logicalloc,balancedate);
		List<TrialBalance> list = result.getTrialBal();

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list, false);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("total", "7000");
		parameters.put("balancedt", balancedate);
		parameters.put("logloc", result.getLogicalLoc());
		parameters.put("totalAsstCr", result.getTotalAsstCr());
		parameters.put("totalAsstDr", result.getTotalAsstDr());
		parameters.put("totalExpdCr", result.getTotalExpdCr());
		parameters.put("totalExpdDr", result.getTotalExpdDr());
		parameters.put("totalIncmCr", result.getTotalIncmCr());
		parameters.put("totalIncmDr", result.getTotalIncmDr());
		parameters.put("totalLiabCr", result.getTotalLiabCr());
		parameters.put("totalLiabDr", result.getTotalLiabDr());
		parameters.put("totalDr", result.getTotalDr());
		parameters.put("totalCr", result.getTotalCr());
		parameters.put("diff", result.getTotalDiff());
		

		InputStream input = new ClassPathResource("trial_balance.jrxml").getInputStream();
		JasperDesign jasperDesign = JRXmlLoader.load(input);


		JasperReport compileReport = JasperCompileManager
				.compileReport(jasperDesign);


		JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, beanCollectionDataSource);

		// JasperExportManager.exportReportToPdfFile(jasperPrint,
		// System.currentTimeMillis() + ".pdf");

		byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

		System.err.println(data);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=trial_balance_"+logicalloc +".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}
	
	
	@GetMapping(value = "/pdfforall/{balancedate}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getTrialBalancePDFForAll(
			@ApiParam(value = "Balance Date", required = true) @PathVariable("balancedate") @NotBlank String balancedate ) throws JRException, IOException {
		TrialBalanceAllLocation result = trialBalanceService.getForAllLocation(balancedate);
		List<TrialBalance> list = result.getTrialBal();

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list, false);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("total", "7000");
		parameters.put("balancedt", balancedate);
		parameters.put("logloc", "All");
		parameters.put("totalAsstCr", result.getTotalAsstCr());
		parameters.put("totalAsstDr", result.getTotalAsstDr());
		parameters.put("totalExpdCr", result.getTotalExpdCr());
		parameters.put("totalExpdDr", result.getTotalExpdDr());
		parameters.put("totalIncmCr", result.getTotalIncmCr());
		parameters.put("totalIncmDr", result.getTotalIncmDr());
		parameters.put("totalLiabCr", result.getTotalLiabCr());
		parameters.put("totalLiabDr", result.getTotalLiabDr());
		parameters.put("totalDr", result.getTotalDr());
		parameters.put("totalCr", result.getTotalCr());
		parameters.put("diff", result.getTotalDiff());
		
		
		InputStream input = new ClassPathResource("trial_balance.jrxml").getInputStream();
		JasperDesign jasperDesign = JRXmlLoader.load(input);


		JasperReport compileReport = JasperCompileManager
				.compileReport(jasperDesign);

		JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, beanCollectionDataSource);

		// JasperExportManager.exportReportToPdfFile(jasperPrint,
		// System.currentTimeMillis() + ".pdf");

		byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

		System.err.println(data);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=trial_balance_all_locations.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}

	
}

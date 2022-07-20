package in.ecgc.smile.erp.accounts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.model.FiscalYearModel;
import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.model.ReceiptDto;
import in.ecgc.smile.erp.accounts.repository.ReceiptDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExternalReceiptServiceImpl implements ExternalReceiptService {

	@Autowired
	FiscalYearService fiscalYearService;
	@Autowired
	GlTxnService glTxnService;
	@Autowired
	ReceiptDao receiptDao;
	
	
	@Override
	public Integer addReceipt(ReceiptDto receiptDto) {
			
		Integer glTxnNumber;
		Integer receiptNumber; 
		int result=0;
		log.info("receipt service called");		
		try {
		Receipt receipt = new Receipt();			
			GlTxnHdr glTxnHdr = new GlTxnHdr();
			
			
			FiscalYearModel fiscalYearModel = fiscalYearService.findCurrentFiscalYear();
			log.info(fiscalYearModel.getCurrFiscalYear());
			
			List<GlTxnDtl> glTxnDtlList= new ArrayList<>();
			
			receipt.setAmount(receiptDto.getAmount());
			receipt.setBranchState(receiptDto.getBranchState());
			receipt.setCgstAmount(receiptDto.getCgstAmount());
			receipt.setCgstTaxPer(receiptDto.getCgstTaxPer());
			receipt.setCustomerGstNo(receiptDto.getCustomerGstNo());
			receipt.setDiscount(receiptDto.getDiscount());
			receipt.setDrawnOn(receiptDto.getDrawnOn());
			receipt.setEcgcGstNo(receiptDto.getEcgcGstNo());
			receipt.setEntityCd(receiptDto.getEntityCd());
			receipt.setExpoState(receiptDto.getExpoState());
			receipt.setFiscalYear(fiscalYearModel.getCurrFiscalYear());
			receipt.setGlFlag(receiptDto.getGlFlag());
			receipt.setGlTxnNumber(receiptDto.getGlTxnNumber());
			receipt.setGlTxnNumberOld(receiptDto.getGlTxnNumberOld());
			receipt.setGlTxnType("RE");
			receipt.setHsn(receiptDto.getHsn());
			receipt.setIgstAmount(receiptDto.getIgstAmount());
			receipt.setIgstTaxPer(receiptDto.getIgstTaxPer());
			receipt.setInstrumentDate(receiptDto.getInstrumentDate());
			receipt.setInstrumentNumber(receiptDto.getInstrumentNumber());
			receipt.setInstrumentType(receiptDto.getInstrumentType());
			receipt.setInvoiceNo(receiptDto.getInvoiceNo());
			receipt.setIwdNumber(receiptDto.getIwdNumber());
			receipt.setLogicalLocCode(receiptDto.getLogicalLocCode());
			receipt.setOldInvoiceNo(receiptDto.getOldInvoiceNo());
			receipt.setOldReceiptNumber(receiptDto.getOldReceiptNumber());
			receipt.setPayInSlip(receiptDto.getPayInSlip());
			receipt.setProductDescription(receiptDto.getProductDescription());
			receipt.setQty(receiptDto.getQty());
			receipt.setRate(receiptDto.getRate());
			receipt.setReceiptAmount(receiptDto.getReceiptAmount());
			receipt.setReceiptDate(receiptDto.getReceiptDate());
			receipt.setReceiptNumber(receiptDto.getReceiptNumber());
			receipt.setReceivedFromAddress(receiptDto.getReceivedFromAddress());
			receipt.setReceivedFromCode(receiptDto.getReceivedFromCode());
			receipt.setReceivedFromName(receiptDto.getReceivedFromName());
			receipt.setReceivedfromType(receiptDto.getReceivedfromType());
			receipt.setRemarks(receiptDto.getRemarks());
			receipt.setSez(receiptDto.getSez());
			receipt.setSgstAmount(receiptDto.getSgstAmount());
			receipt.setSgstTaxPer(receiptDto.getSgstTaxPer());
			receipt.setStampAmount(receiptDto.getStampAmount());
			receipt.setTaxableDiscount(receiptDto.getTaxableDiscount());
			receipt.setTaxType(receiptDto.getTaxType());
			receipt.setUom(receiptDto.getUom());
			receipt.setUtgstAmount(receiptDto.getUtgstAmount());
			receipt.setUtgstTaxPer(receiptDto.getUtgstTaxPer());			
			
			glTxnHdr.setFiscalYr(fiscalYearModel.getCurrFiscalYear());
			glTxnHdr.setEntityCd(receiptDto.getEntityCd());
			glTxnHdr.setFiscalYr(receiptDto.getFiscalYr());			
			glTxnHdr.setReference(receiptDto.getReference());
			glTxnHdr.setGlTxnType("RE");
			glTxnHdr.setLogicalLocCd(receiptDto.getLogicalLocCode());
			glTxnHdr.setReference(receiptDto.getReference());
			glTxnHdr.setRevarsalGlTxnNo(receiptDto.getRevarsalGlTxnNo());
			glTxnHdr.setRevarsalGlTxnType(receiptDto.getRevarsalGlTxnType());		
			glTxnHdr.setReversalReason(receiptDto.getReversalReason());
			glTxnHdr.setTxnDt(LocalDate.now());	
			glTxnHdr.setGlTxnDtls(receiptDto.getGlTxnDtls());
			
			//Code to enter GL entry for Revenue stamp
			
			 GlTxnDtl glTxnDtl = new GlTxnDtl();
			GlTxnDtl glTxnDtl1 = new GlTxnDtl();
			
			glTxnDtl1.setTxnAmt(receiptDto.getStampAmount());
			glTxnDtl1.setMainGlCd("8480");
			glTxnDtl1.setSubGlCd("001");
			glTxnDtl1.setDrCrFlag("Dr");
			glTxnDtl1.setGlTxnType(receiptDto.getGlTxnType());
			glTxnDtl1.setTxnAmt(receiptDto.getStampAmount());
			glTxnDtl1.setLogicalLocCd(receiptDto.getLogicalLocCode());
			glTxnDtl1.setGlTxnNo(receiptDto.getGlTxnNumber());			
			glTxnDtl1.setEntityGlCd(receiptDto.getEntityCd());
			glTxnDtl1.setT1(receiptDto.getT1());
			glTxnDtl1.setT2(receiptDto.getT2());
			glTxnDtl1.setT3(receiptDto.getT3());
			glTxnDtl1.setT4(receiptDto.getT4());
			glTxnDtl1.setT5(receiptDto.getT5());
			glTxnDtl1.setT6(receiptDto.getT6());
			glTxnDtl1.setT7(receiptDto.getT7());
			glTxnDtl1.setT8(receiptDto.getT8());
			glTxnDtl1.setT9(receiptDto.getT9());
			glTxnDtl1.setT10(receiptDto.getT10());
			glTxnDtl1.setT11(receiptDto.getT11());
			glTxnDtl1.setT12(receiptDto.getT12());
			glTxnDtl1.setT13(receiptDto.getT13());
			glTxnDtl1.setT14(receiptDto.getT14());	
			
			
			glTxnDtl.setTxnAmt(receiptDto.getStampAmount());
			glTxnDtl.setMainGlCd("1600");
			glTxnDtl.setSubGlCd("003");
			glTxnDtl.setDrCrFlag("Cr");
			glTxnDtl.setGlTxnType(receiptDto.getGlTxnType());
			glTxnDtl.setTxnAmt(receiptDto.getStampAmount());
			glTxnDtl.setLogicalLocCd(receiptDto.getLogicalLocCode());
			glTxnDtl.setGlTxnNo(receiptDto.getGlTxnNumber());			
			glTxnDtl.setEntityGlCd(receiptDto.getEntityCd());
			glTxnDtl.setT1(receiptDto.getT1());
			glTxnDtl.setT2(receiptDto.getT2());
			glTxnDtl.setT3(receiptDto.getT3());
			glTxnDtl.setT4(receiptDto.getT4());
			glTxnDtl.setT5(receiptDto.getT5());
			glTxnDtl.setT6(receiptDto.getT6());
			glTxnDtl.setT7(receiptDto.getT7());
			glTxnDtl.setT8(receiptDto.getT8());
			glTxnDtl.setT9(receiptDto.getT9());
			glTxnDtl.setT10(receiptDto.getT10());
			glTxnDtl.setT11(receiptDto.getT11());
			glTxnDtl.setT12(receiptDto.getT12());
			glTxnDtl.setT13(receiptDto.getT13());
			glTxnDtl.setT14(receiptDto.getT14());				
			
//			glTxnDtlList.add(glTxnDtl1);
//			glTxnDtlList.add(glTxnDtl);	
			for(GlTxnDtl glDtl : receiptDto.getGlTxnDtls()) {
				glTxnDtlList.add(glDtl);
			}
			
			glTxnHdr.setGlTxnDtls(glTxnDtlList);
			
			
			int glResult = glTxnService.addGlTxn(glTxnHdr);
			if(glResult >= 1) {
				//log.info("print Result "+glTxnNumber );
				receipt.setGlTxnNumber(glTxnHdr.getGlTxnNo());
//				receiptNumber = receiptDao.addReceipt(receipt);
			
			receipt.setReceiptNumber(receiptDao.getReceiptNo(receipt.getLogicalLocCode(), receipt.getFiscalYear()));
			String invoiceNo = null;
			if (receipt.getSgstAmount() != 0 || receipt.getIgstAmount() != 0 || receipt.getCgstAmount() != 0
					|| receipt.getUtgstAmount() != 0) {
				invoiceNo = receiptDao.getTaxInvoiceNo(receipt.getLogicalLocCode(), receipt.getFiscalYear());
				invoiceNo = "TI".concat(invoiceNo);
				receipt.setInvoiceNo(invoiceNo);
			 } else {
				invoiceNo = receiptDao.getBSInvoiceNo(receipt.getLogicalLocCode(), receipt.getFiscalYear());
				invoiceNo = "BS".concat(invoiceNo);
				receipt.setInvoiceNo(invoiceNo);
				log.info("invoice number on service is :: {}  ",invoiceNo);
			}
			 result = receiptDao.addReceipt(receipt);
			log.info("Receipt Number {}",receipt.getReceiptNumber());
			}
			if (result == 1)
				return receipt.getReceiptNumber();
			else
				return 0;
			
		}catch (Exception e) {
			return 0;
		}
		
		

	}

}
package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import in.ecgc.smile.erp.accounts.model.Receipt;

public interface PayInSlipService {

	List<Receipt> listAllPayInSlipReceipts();
	
	Receipt viewPayInSlipByLogicalLocCodeAndReceiptNo(String logicalLocCode, Integer receiptNumber);
	
	List<Receipt> listByFromDtToDtAndInstrumentTypePayInSlip(String fromDate, String toDate, String[] instrumentType);
}

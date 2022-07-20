package in.ecgc.smile.erp.accounts.repository;

import java.time.LocalDate;
import java.util.List;

import in.ecgc.smile.erp.accounts.model.Receipt;

public interface PayInSlipDao {

	List<Receipt> listAllReceipts();
	
	Receipt viewPayInSlipByLogicalLocCodeAndReceiptNo(String logicalLocCode, Integer receiptNumber);
	
	List<Receipt> listByFromDtToDtAndInstrumentTypePayInSlip(String fromDate, String toDate, String[] instrumentType);
}

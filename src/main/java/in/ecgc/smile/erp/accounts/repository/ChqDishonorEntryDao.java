package in.ecgc.smile.erp.accounts.repository;

import java.time.LocalDate;
import java.util.List;

import in.ecgc.smile.erp.accounts.model.ChqDishonorEntry;
import in.ecgc.smile.erp.accounts.model.Receipt;
public interface ChqDishonorEntryDao{
	public List<ChqDishonorEntry> getChqDishonorEntryList()throws Exception;
	public boolean addChqDishonorEntryData(ChqDishonorEntry  chqDishonorEntry);
	public boolean updateChqDishonorEntryData(ChqDishonorEntry  chqDishonorEntry);
	public ChqDishonorEntry getChqDishonorEntryByChequeNo(String instrumentNo);
	public Receipt viewByChqNoChqTypeChqDtRcptNo(String instrumentType,String instrumentNumber, LocalDate instrumentDate, Integer receiptNumber);
	public List<Receipt> viewByInstrumentType(String instrumentType);
	public ChqDishonorEntry viewByLogicalLocCodeAndReceiptNo(String logicalLocCode, Integer receiptNumber);


}

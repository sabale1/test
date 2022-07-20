package in.ecgc.smile.erp.accounts.service;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.exception.FailToInsertDataException;
import in.ecgc.smile.erp.accounts.exception.ReceiptNotFoundException;
import in.ecgc.smile.erp.accounts.exception.RecordNotFoundException;
import in.ecgc.smile.erp.accounts.model.ChqDishonorEntry;
import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.repository.ChqDishonorEntryDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ChqDishonorEntryServiceImpl implements ChqDishonorEntryService{
	
	@Autowired
	ChqDishonorEntryDao chqDishonorEntryDao;
	
	@Override
	public List<ChqDishonorEntry> getChqDishonorEntryList(){
		log.info("Inside ChqDishonorEntryServiceImpl#getChqDishonorEntryList");
		List<ChqDishonorEntry> chqDishonorEntrylist=new ArrayList<>();
		try {
			chqDishonorEntrylist=chqDishonorEntryDao.getChqDishonorEntryList();
		}
		catch (Exception e) {
			log.error("ERROR: Service getChqDishonorEntryList() {}", e.getMessage());
			throw new RecordNotFoundException("List is empty");

		}
		return chqDishonorEntrylist; 
	}
	
	@Override
	public boolean addChqDishonorEntryData(ChqDishonorEntry  chqDishonorEntry){
		log.info("Inside ChqDishonorEntryServiceImpl#addChqDishonorEntryData");
		Boolean result=false;
		try {
			result=chqDishonorEntryDao.addChqDishonorEntryData(chqDishonorEntry);
		 }
		catch (Exception e) {
			log.error("ERROR: Service addChqDishonorEntryData() {}", e.getMessage());
			throw new FailToInsertDataException("Unable to insert data",result);

		}
		return result;
	}
	
	@Override
	public boolean updateChqDishonorEntryData(ChqDishonorEntry  chqDishonorEntry){
		log.info("Inside ChqDishonorEntryServiceImpl#updateChqDishonorEntryData");
		Boolean result=false;
		try {
			result=chqDishonorEntryDao.updateChqDishonorEntryData(chqDishonorEntry);
		}
		 catch (Exception e) {
			log.error("ERROR: Service updateChqDishonorEntryData() {}", e.getMessage());
			throw new FailToInsertDataException("Unable to insert data",result);	
		}
		return result;
	}
	
	@Override
	public  ChqDishonorEntry getChqDishonorEntryByChequeNo(String instrumentNo){
		log.info("Inside ChqDishonorEntryServiceImpl#getChqDishonorEntryByChequeNo");
		ChqDishonorEntry chqDishonorEntry=chqDishonorEntryDao.getChqDishonorEntryByChequeNo(instrumentNo);
		if(chqDishonorEntry!=null)
			return chqDishonorEntry;
		else
			return null;
	}
	
	@Override
	public Receipt viewByChqNoChqTypeChqDtRcptNo(String instrumentType,String instrumentNumber, LocalDate instrumentDate, Integer receiptNumber) {
		log.info("Inside ChqDishonorEntryServiceImpl#viewByChqNoChqTypeChqDtRcptNo");
		Receipt receipt= chqDishonorEntryDao.viewByChqNoChqTypeChqDtRcptNo(instrumentType, instrumentNumber, instrumentDate, receiptNumber);
		if(receipt== null) {
			throw new ReceiptNotFoundException("No Receipt details found with the given Receipt Id");
		}		
		return receipt;
	}
	
	@Override
	public List<Receipt> viewByInstrumentType(String instrumentType){
		log.info("Inside ChqDishonorEntryServiceImpl#viewByInstrumentType");
		return chqDishonorEntryDao.viewByInstrumentType(instrumentType);
	}

	@Override
	public ChqDishonorEntry viewByLogicalLocCodeAndReceiptNo(String logicalLocCode, Integer receiptNo) {
		log.info("Inside ChqDishonorEntryServiceImpl#viewByLogicalLocCodeAndReceiptNo");
		ChqDishonorEntry chqDishonorEntry=chqDishonorEntryDao.viewByLogicalLocCodeAndReceiptNo(logicalLocCode, receiptNo);
		if(chqDishonorEntry!=null)
			return chqDishonorEntry;
		else
			return null;
	}
}

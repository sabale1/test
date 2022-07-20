package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.repository.PayInSlipDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PayInSlipServiceImpl implements PayInSlipService {

	@Autowired
	PayInSlipDao payInSlipDao;

	@Override
	public List<Receipt> listAllPayInSlipReceipts() {
		log.info("Inside PayInSlipServiceImpl#listAllPayInSlipReceipts");
		return payInSlipDao.listAllReceipts();
	}

	@Override
	public Receipt viewPayInSlipByLogicalLocCodeAndReceiptNo(String logicalLocCode, Integer receiptNumber) {
		log.info("Inside PayInSlipServiceImpl#viewByLogicalLocCodeAndReceiptNo");
		return payInSlipDao.viewPayInSlipByLogicalLocCodeAndReceiptNo(logicalLocCode, receiptNumber);
	}

	@Override
	public List<Receipt> listByFromDtToDtAndInstrumentTypePayInSlip(String fromDate, String toDate,
			String[] instrumentType) {
		log.info("Inside PayInSlipServiceImpl#listByFromDtToDtAndInstrumentTypePayInSlip");
		return payInSlipDao.listByFromDtToDtAndInstrumentTypePayInSlip(fromDate, toDate, instrumentType);
	}
	


}

package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.exception.ReceiptNotFoundException;
import in.ecgc.smile.erp.accounts.model.Receipt;
import in.ecgc.smile.erp.accounts.model.States;
import in.ecgc.smile.erp.accounts.repository.ReceiptDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReceiptServiceImpl implements ReceiptService {

	@Autowired
	ReceiptDao receiptDao;
	@Autowired
	GlTxnService glTxnServive;

	@Override
	public Integer addReceipt(Receipt receipt) {
		try {
			log.info("Inside ReceiptServiceImpl#addReceipt");
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
			int result = receiptDao.addReceipt(receipt);
			log.info("Receipt Number {}",receipt.getReceiptNumber());

			if (result == 1)
				return receipt.getReceiptNumber();
			else
				return 0;
		} catch (Exception e) {
			log.info("Exception in receipt add :  {}",e);
			 return 0;
		}
	}

	@Override
	public List<Receipt> listAllReceipts() {
		log.info("Inside ReceiptServiceImpl#listAllReceipts");
		return receiptDao.listAllReceipts();
	}

	@Override
	public Receipt viewByLogicalLocCodeAndReceiptNo(String logicalLocCode, Integer receiptNumber) {
		log.info("Inside ReceiptServiceImpl#viewByLogicalLocCodeAndReceiptNo");
		Receipt receipt = receiptDao.viewByLogicalLocCodeAndReceiptNo(logicalLocCode, receiptNumber);
		if (receipt == null) {
			throw new ReceiptNotFoundException("No Receipt details found with the given Receipt Id");
		}
		return receiptDao.viewByLogicalLocCodeAndReceiptNo(logicalLocCode, receiptNumber);
	}

	@Override
	public Integer updateReceipt(String logicalLocCode, Integer receiptNumber, Receipt receiptUpdate) {
		log.info("Inside ReceiptServiceImpl#updateReceipt");
		Receipt receipt = receiptDao.viewByLogicalLocCodeAndReceiptNo(logicalLocCode, receiptNumber);
		if (receipt == null) {
			throw new ReceiptNotFoundException("No Receipt details found with the given Receipt Id",
					new Integer[] { receiptNumber });
		}
		return receiptDao.updateReceipt(logicalLocCode, receiptNumber, receiptUpdate);
	}

	@Override
	public List<Receipt> viewByLogicalLocCode(String logicalLocCode) {
		log.info("Inside ReceiptServiceImpl#viewByLogicalLocCode");
		return receiptDao.viewByLogicalLocationCode(logicalLocCode);
	}

	@Override
	public List<States> getAllStates() {
		log.info("Inside ReceiptServiceImpl#getAllStates");
		return receiptDao.getAllStates();
	}

	@Override
	public Integer updatePrintFlag(String logicalLocCode, Integer receiptNumber, String printFlag) {
		log.info("Inside ReceiptServiceImpl#updatePrintFlag");
		return receiptDao.updatePrintFlag(logicalLocCode, receiptNumber, printFlag);
	}

	@Override
	public String getFlag(String logicalLocCode, Integer receiptNumber) {
		log.info("Inside ReceiptServiceImpl#getFlag");
		String result = receiptDao.getFlag(logicalLocCode, receiptNumber);

		if (result == null)
			return null;
		else
			return result;
	}

	@Override
	public States getAllstatesBystateCode(Integer stateCode) {
		
		return receiptDao.getAllstatesBystateCode(stateCode);
	}

}

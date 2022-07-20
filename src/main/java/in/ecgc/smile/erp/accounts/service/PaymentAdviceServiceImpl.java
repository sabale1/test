package in.ecgc.smile.erp.accounts.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.model.PaymentAdvice;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceTcodes;
import in.ecgc.smile.erp.accounts.repository.PaymentAdviceDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentAdviceServiceImpl implements PaymentAdviceService{

	@Autowired
	PaymentAdviceDao payAdviceDao;
	
	@Override
	public Integer getAdviceNo(String logicalLocCd, String sectionCd, String fiscalYear) {
		log.info("Inside PaymentAdviceServiceImpl#getAdviceNo");
		return payAdviceDao.getAdviceNo(logicalLocCd, sectionCd, fiscalYear);
	}

	@Override
	@Transactional
	public Integer addPaymentAdvice(PaymentAdvice paymentAdvice) {
		log.info("Inside PaymentAdviceServiceImpl#addPaymentAdvice");
		Integer adviceSeq = payAdviceDao.getAdviceNo(paymentAdvice.getLogicalLocCd(), paymentAdvice.getSectionCd(), paymentAdvice.getFiscalYear());
			
		paymentAdvice.setAdviceNo(adviceSeq);
		paymentAdvice.getPayTcodes().setAdviceNo(adviceSeq);
		
		payAdviceDao.addPaymentAdvice(paymentAdvice);
		payAdviceDao.addPaymentAdviceTcodes(paymentAdvice);	
		return adviceSeq;
	}

	@Override
	public List<PaymentAdvice> listAllPaymentAdvice() {
		log.info("Inside PaymentAdviceServiceImpl#listAllPaymentAdvice");
		return payAdviceDao.listAllPaymentAdvice();
	}

	@Override
	public PaymentAdvice getPaymentAdviceByAdviceDtl(String logicalLocCd,String sectionCd,Integer adviceNo) {
		log.info("Inside PaymentAdviceServiceImpl#getPaymentAdviceByAdviceDtl");
		return payAdviceDao.getPaymentAdviceByAdviceDtl(logicalLocCd, sectionCd, adviceNo);
	}

	@Override
	public Integer updateSeqNo(String logicalLocCd, String sectionCd, String fiscalYear, Integer adviceNo) {
		log.info("Inside PaymentAdviceServiceImpl#updateSeqNo");
		return payAdviceDao.updateSeqNo(logicalLocCd, sectionCd, fiscalYear, adviceNo);
	}

	@Override
	public Integer disablePaymentAdvice(String logicalLocCd,String sectionCd,Integer adviceNo) {
		log.info("Inside PaymentAdviceServiceImpl#disablePaymentAdvice");
		Integer res =  payAdviceDao.disablePaymentAdvice(logicalLocCd, sectionCd, adviceNo);
		if (res == 1) {
			return payAdviceDao.disablePaymentAdviceTcodes(logicalLocCd, sectionCd, adviceNo);
		} else {
			return 0;
		}
	}

	@Override
	public Integer updatePaymentAdvice(PaymentAdvice paymentAdvice) {
		log.info("Inside PaymentAdviceServiceImpl#updatePaymentAdvice");
		return  payAdviceDao.updatePaymentAdvice(paymentAdvice);
	}
	
	@Override
	public Integer updatePaymentAdviceTcodes(PaymentAdvice paymentAdvice) {
		log.info("Inside PaymentAdviceServiceImpl#updatePaymentAdviceTcodes");
		return  payAdviceDao.updatePaymentAdviceTcodes(paymentAdvice);
	}

	@Override
	public List<PaymentAdvice> listAllPaymentAdviceTcodes() {
		log.info("Inside PaymentAdviceServiceImpl#listAllPaymentAdviceTcodes");
		return payAdviceDao.listAllPaymentAdviceTcodes();
	}

	@Override
	public PaymentAdvice getPaymentAdviceTcodesByAdviceDtl(String logicalLocCd,String sectionCd,Integer adviceNo) {
		log.info("Inside PaymentAdviceServiceImpl#getPaymentAdviceTcodesByAdviceDtl");
		return payAdviceDao.getPaymentAdviceTcodesByAdviceDtl(logicalLocCd, sectionCd, adviceNo);
	}

	@Override
	public Integer disablePaymentAdviceTcodes(String logicalLocCd,String sectionCd,Integer adviceNo) {
		log.info("Inside PaymentAdviceServiceImpl#disablePaymentAdviceTcodes");
		return payAdviceDao.disablePaymentAdviceTcodes(logicalLocCd, sectionCd, adviceNo);
	}

	@Override
	public List<PaymentAdvice> getPaymentAdviceByLogicalLocSectionCd(String logicalLocCd, String sectionCd,LocalDate fromDt, LocalDate toDt) {
		log.info("Inside PaymentAdviceServiceImpl#getPaymentAdviceByLogicalLocSectionCd");
		return payAdviceDao.getPaymentAdviceByLogicalLocSectionCd(logicalLocCd, sectionCd, fromDt, toDt);
	}

	@Override
	public PaymentAdviceTcodes getPaymentAdviceTcodesDtl(String logicalLocCd, String sectionCd, Integer adviceNo) {
		log.info("Inside PaymentAdviceServiceImpl#getPaymentAdviceTcodesDtl");
		return payAdviceDao.getPaymentAdviceTcodesDtl(logicalLocCd, sectionCd, adviceNo);
	}

	@Override
	public List<PaymentAdvice> getEntrPaymentAdvice(String logicalLocCd, String sectionCd, String adviceStat) {
		log.info("Inside PaymentAdviceServiceImpl#getEntrPaymentAdvice");
		return payAdviceDao.getEntrPaymentAdvice(logicalLocCd, sectionCd, adviceStat);
	}

	@Override
	public Integer takeDecisionOnPaymentAdvice(PaymentAdvice paymentAdvice) {
		log.info("Inside PaymentAdviceServiceImpl#takeDecisionOnPaymentAdvice");
		return payAdviceDao.takeDecisionOnPaymentAdvice(paymentAdvice);
	}

	@Override
	public List<PaymentAdvice> getAdviceNumberByLocSecDt(String logicalLocCd, String sectionCd,LocalDate fromDt, LocalDate toDt) {
		log.info("Inside PaymentAdviceServiceImpl#getAdviceNumberByLocSecDt");
		return payAdviceDao.getAdviceNumberByLocSecDt(logicalLocCd, sectionCd, fromDt, toDt);
	}
	
	@Override
	public List<PaymentAdvice> getAdviceNumberByLocSec(String logicalLocCd, String sectionCd) {
		log.info("Inside PaymentAdviceServiceImpl#getAdviceNumberByLocSec");
		return payAdviceDao.getAdviceNumberByLocSec(logicalLocCd, sectionCd);
	}

	@Override
	public PaymentAdvice getPaymentAdviceAndTaxDtl(String logicalLocCd, String sectionCd, Integer adviceNo) {
		log.info("Inside PaymentAdviceServiceImpl#getPaymentAdviceAndTaxDtl");
		return payAdviceDao.getPaymentAdviceAndTaxDtl(logicalLocCd, sectionCd, adviceNo);
	}

	@Override
	public List<PaymentAdvice> getApprPaymentAdviceByDtl(String logicalLocCd, String sectionCd, String fiscalYear) {
		log.info("Inside PaymentAdviceServiceImpl#getApprPaymentAdviceByDtl");
		return payAdviceDao.getApprPaymentAdviceByDtl(logicalLocCd, sectionCd, fiscalYear);
	}

	@Override
	public List<PaymentAdvice> getAprvAdviceNumberByLocSec(String logicalLocCd, String sectionCd) {
		log.info("Inside PaymentAdviceServiceImpl#getAprvAdviceNumberByLocSec");
		return payAdviceDao.getAprvAdviceNumberByLocSec(logicalLocCd, sectionCd);
	}

	@Override
	public List<PaymentAdvice> getAprvAdviceNumberByLocSecDt(String logicalLocCd, String sectionCd, LocalDate fromDt,
			LocalDate toDt) {
		log.info("Inside PaymentAdviceServiceImpl#getAprvAdviceNumberByLocSecDt");
		return payAdviceDao.getAprvAdviceNumberByLocSecDt(logicalLocCd, sectionCd, fromDt, toDt);
	}

	@Override
	public Integer updatePaymentAdviceDtltoUNPAID(String logicalLocCd, String sectionCd, Integer adviceNo) {
		log.info("Inside PaymentAdviceServiceImpl#updatePaymentAdviceDtltoUNPAID");
		return payAdviceDao.updatePaymentAdviceDtltoUNPAID(logicalLocCd, sectionCd, adviceNo);
	}
	
	@Override
	public Integer updatePaymentAdviceStatus(String logicalLocCd, String sectionCd, Integer adviceNo, String status) {
		log.info("Inside PaymentAdviceServiceImpl#updatePaymentAdviceStatus");
		return payAdviceDao.updatePaymentAdviceStatus(logicalLocCd, sectionCd, adviceNo, status);

	}
	
}

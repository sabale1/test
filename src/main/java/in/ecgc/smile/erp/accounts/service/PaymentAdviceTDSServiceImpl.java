package in.ecgc.smile.erp.accounts.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.model.PaymentAdvice;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceGstTdsDtl;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceTdsDtl;
import in.ecgc.smile.erp.accounts.repository.PaymentAdviceTDSDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentAdviceTDSServiceImpl implements PaymentAdviceTDSService {

	@Autowired
	PaymentAdviceTDSDao payAdviceTdsDao;

	@Override
	public PaymentAdvice getApprovedPaymentAdviceDtl(String logicalLocCd, String sectionCd, Integer adviceNo) {
		log.info("Inside PaymentAdviceTDSServiceImpl#getApprovedPaymentAdviceDtl");
		return payAdviceTdsDao.getApprovedPaymentAdviceDtl(logicalLocCd, sectionCd, adviceNo);
	}

	@Override
	public List<PaymentAdvice> getApprovedPaymentAdvices(String logicalLocCd, String sectionCd, LocalDate fromDt,
			LocalDate toDt) {
		log.info("Inside PaymentAdviceTDSServiceImpl#getApprovedPaymentAdvices");
		return payAdviceTdsDao.getApprovedPaymentAdvices(logicalLocCd, sectionCd, fromDt, toDt);
	}

	@Override
	public List<PaymentAdvice> getApprovedPaymentAdvicesbyNo(String logicalLocCd, String sectionCd, Integer adviceNo) {
		log.info("Inside PaymentAdviceTDSServiceImpl#getApprovedPaymentAdvicesbyNo");
		return payAdviceTdsDao.getApprovedPaymentAdvicesbyNo(logicalLocCd, sectionCd, adviceNo);
	}

	@Override
	public Integer updatePaymentAdviceTdsNOTAppliacble(PaymentAdvice paymentAdvice) {
		log.info("Inside PaymentAdviceTDSServiceImpl#updatePaymentAdviceTdsNOTAppliacble");
		return payAdviceTdsDao.updatePaymentAdviceTdsNOTAppliacble(paymentAdvice);
	}

	@Override
	public Integer updatePaymentAdviceRCAppliacble(PaymentAdvice paymentAdvice) {
		log.info("Inside PaymentAdviceTDSServiceImpl#updatePaymentAdviceRCAppliacble");
		return payAdviceTdsDao.updatePaymentAdviceRCAppliacble(paymentAdvice);
	}

	@Override
	public Integer addPaymentAdviceTDSDtl(PaymentAdviceTdsDtl payAdvTdsDtl) {
		log.info("Inside PaymentAdviceTDSServiceImpl#addPaymentAdviceTDSDtl");
		return payAdviceTdsDao.addPaymentAdviceTDSDtl(payAdvTdsDtl);
	}

	@Override
	public Integer addPaymentAdviceGSTTDSDtl(PaymentAdviceGstTdsDtl payAdvGstTdsDtl) {
		log.info("Inside PaymentAdviceTDSServiceImpl#addPaymentAdviceGSTTDSDtl");
		return payAdviceTdsDao.addPaymentAdviceGSTTDSDtl(payAdvGstTdsDtl);
	}

	@Override
	public PaymentAdviceTdsDtl getPaymentAdviceTDSDtl(String logicalLocCd, String sectionCd, Integer adviceNo) {
		log.info("Inside PaymentAdviceTDSServiceImpl#getPaymentAdviceTDSDtl");
		return payAdviceTdsDao.getPaymentAdviceTDSDtl(logicalLocCd, sectionCd, adviceNo);
	}

	@Override
	public PaymentAdviceGstTdsDtl getPaymentAdviceGSTTDSDtl(String logicalLocCd, String sectionCd, Integer adviceNo) {
		log.info("Inside PaymentAdviceTDSServiceImpl#getPaymentAdviceGSTTDSDtl");
		return payAdviceTdsDao.getPaymentAdviceGSTTDSDtl(logicalLocCd, sectionCd, adviceNo);
	}
}

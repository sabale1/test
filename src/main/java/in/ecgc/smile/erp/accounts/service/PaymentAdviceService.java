package in.ecgc.smile.erp.accounts.service;

import java.time.LocalDate;
import java.util.List;

import in.ecgc.smile.erp.accounts.model.PaymentAdvice;
import in.ecgc.smile.erp.accounts.model.PaymentAdviceTcodes;

public interface PaymentAdviceService {
	public Integer getAdviceNo(String logicalLocCd,String sectionCd,String fiscalYear);
	public Integer updateSeqNo(String logicalLocCd,String sectionCd,String fiscalYear, Integer adviceNo);
	public Integer addPaymentAdvice(PaymentAdvice paymentAdvice);
	public List<PaymentAdvice> listAllPaymentAdvice();
	public List<PaymentAdvice> listAllPaymentAdviceTcodes();
	List<PaymentAdvice> getPaymentAdviceByLogicalLocSectionCd(String logicalLocCd,String sectionCd,LocalDate fromDt, LocalDate toDt);
	PaymentAdvice getPaymentAdviceByAdviceDtl(String logicalLocCd,String sectionCd,Integer adviceNo);
	PaymentAdvice getPaymentAdviceTcodesByAdviceDtl(String logicalLocCd,String sectionCd,Integer adviceNo);
	public Integer disablePaymentAdvice(String logicalLocCd,String sectionCd,Integer adviceNo);
	public Integer disablePaymentAdviceTcodes(String logicalLocCd,String sectionCd,Integer adviceNo);
	public Integer updatePaymentAdvice(PaymentAdvice paymentAdvice);
	PaymentAdviceTcodes getPaymentAdviceTcodesDtl(String logicalLocCd, String sectionCd, Integer adviceNo);
	Integer updatePaymentAdviceTcodes(PaymentAdvice paymentAdvice);
	List<PaymentAdvice> getEntrPaymentAdvice(String logicalLocCd, String sectionCd, String adviceStat);
	Integer takeDecisionOnPaymentAdvice(PaymentAdvice paymentAdvice);
	List<PaymentAdvice> getAdviceNumberByLocSecDt(String logicalLocCd, String sectionCd, LocalDate fromDt,LocalDate toDt); 
	List<PaymentAdvice> getAdviceNumberByLocSec(String logicalLocCd, String sectionCd); 
	PaymentAdvice getPaymentAdviceAndTaxDtl(String logicalLocCd, String sectionCd, Integer adviceNo);
	List<PaymentAdvice> getApprPaymentAdviceByDtl(String logicalLocCd, String sectionCd, String fiscalYear);

	
	public List<PaymentAdvice> getAprvAdviceNumberByLocSec(String logicalLocCd, String sectionCd);
	List<PaymentAdvice> getAprvAdviceNumberByLocSecDt(String logicalLocCd, String sectionCd, LocalDate fromDt,LocalDate toDt); 
	public Integer updatePaymentAdviceDtltoUNPAID(String logicalLocCd,String sectionCd,Integer adviceNo);

	Integer updatePaymentAdviceStatus(String logicalLocCd, String sectionCd, Integer adviceNo, String status);

}

package in.ecgc.smile.erp.accounts.repository;

import java.util.List;

import in.ecgc.smile.erp.accounts.model.Payments;
import in.ecgc.smile.erp.accounts.model.PaymentsTcodes;

public interface PaymentsDao{
	
	public Integer getPaymentNo(String logicalLocCd,String sectionCd,String fiscalYear);
	
	public Integer createPaymentEntry(Payments  payments);
	
	public Integer createPaymentTcodesEntry(PaymentsTcodes  paymentsTcodes);
	
	public Payments getPaymentsByPaymentDtl(Integer paymetNo, String logicalLocCd, String sectionCd);
	
	public PaymentsTcodes getPaymentsTcodesByPaymentsDtl(Integer paymentNo, String logicalLoc, String sectionCd);
	
	public List<Payments> getPaymentsList()throws Exception;
	

	
	public boolean updatePaymentsData(Payments  payments);
	
	public boolean updatePaymentTcodes(Payments payments);
	public List<Payments> getpaymentsbylocsecdt(String logicalLocCd,String sectionCd,String fromDt,String toDt);

	public List<Payments> getpaymentsbydt(String fromDt, String toDt);

	public List<Payments> getpaymentsbyLocSecYr(String logicalLocCd, String sectionCd, String fiscalYr);
	
	public Payments getPaymentsByPymtNo(Integer paymentNo, String logicalLocCd, String sectionCd);


}

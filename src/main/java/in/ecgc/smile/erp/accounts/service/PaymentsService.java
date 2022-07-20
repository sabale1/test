package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import in.ecgc.smile.erp.accounts.model.Payments;

public interface PaymentsService{

	public Integer createGLTxn(Payments payments);

	public Integer createPaymentEntry(Payments  payments);

	public Payments getPaymentsByPaymentDtl(Integer pymtNo,String logicalLocCd, String sectionCd);

	public List<Payments> getPaymentsList();

	public boolean updatePaymentsData(Payments  payments);

	public boolean updatePaymentTcodes(Payments payments);

// NEW ADDED
	public List<Payments> getpaymentsbylocsecdt(String logicalLocCd,String sectionCd,String fromDt,String toDt);

	public List<Payments> getpaymentsbydt(String fromDt, String toDt);

	public List<Payments> getpaymentsbyLocSecYr(String logicalLocCd,String sectionCd,String fiscalYr);

	public Payments getPaymentsByPymtNo(Integer paymentNo, String logicalLocCd, String sectionCd);
	
	public Integer takeDecision(String logicalLocCd,String sectionCd,Integer paymentNo, String decision);


}

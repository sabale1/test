package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditDtl;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;

public interface PaymentEmployeeDirectCreditService {


	List<PaymentEmployeeDirectCreditHdr> getPaymentAdviceByLogicalLoc(@NotBlank String logicalLoc);

	List<PaymentEmployeeDirectCreditHdr> viewAll() ;

	String addPaymentEmployment(PaymentEmployeeDirectCreditHdr payEmp);

	PaymentEmployeeDirectCreditHdr getPaymentEmpDirectCredit(@NotBlank String requestNo,
			@NotBlank String requestedLogicalLoc, String departmentCode);

 
Integer updatePaymentEmpDirectCredit(PaymentEmployeeDirectCreditHdr payEmp);

PaymentEmployeeDirectCreditHdr getPaymentEmpDirectCreditByRequestNo(@NotBlank String requestNo);
	
	
		
	

}

package in.ecgc.smile.erp.accounts.repository;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditDtl;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;

@Repository
@Transactional
public interface PaymentEmployeeDirectCreditDao {

	List<PaymentEmployeeDirectCreditHdr> getPaymentAdviceByLogicalLoc(@NotBlank String logicalLoc);

	List<PaymentEmployeeDirectCreditHdr> viewAll();

		String addPaymentEmployment(PaymentEmployeeDirectCreditHdr payEmp);

		
		String getRequestNo(String requestedLogicalLoc);

		PaymentEmployeeDirectCreditHdr getPaymentEmpDirectCredit(@NotBlank String requestNo,
				@NotBlank String requestedLogicalLoc, String departmentCode);

		Integer updatePaymentEmpDirectCredit(PaymentEmployeeDirectCreditHdr payEmp);

		PaymentEmployeeDirectCreditHdr getPaymentEmpDirectCreditByRequestNo(@NotBlank String requestNo);

}

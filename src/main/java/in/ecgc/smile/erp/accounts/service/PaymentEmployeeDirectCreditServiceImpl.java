package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditDtl;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;
import in.ecgc.smile.erp.accounts.repository.PaymentEmployeeDirectCreditDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentEmployeeDirectCreditServiceImpl implements PaymentEmployeeDirectCreditService {

	@Autowired
	PaymentEmployeeDirectCreditDao pymtEmpDao;

	
	@Override
	public List<PaymentEmployeeDirectCreditHdr> getPaymentAdviceByLogicalLoc(String logicalLoc) {
		log.info("Inside PaymentEmployeeDirectCreditServiceImpl#getPaymentAdviceByLogicalLoc");
		return pymtEmpDao.getPaymentAdviceByLogicalLoc(logicalLoc);
	}

	@Override
	public List<PaymentEmployeeDirectCreditHdr> viewAll() {
		log.info("Inside PaymentEmployeeDirectCreditServiceImpl#viewAll");
		return pymtEmpDao.viewAll();

	}

	@Override
	public String addPaymentEmployment(PaymentEmployeeDirectCreditHdr payEmp) {

		log.info("Inside PaymentEmployeeDirectCreditServiceImpl#addPaymentEmployment");
		log.info(" PaymentEmployeeDirectCreditHdr obj --- {}" , payEmp);
		String reqNo = pymtEmpDao.getRequestNo(payEmp.getRequestedLogicalLoc());
		payEmp.setRequestNo(reqNo);
		log.info("in service reqNo {}" , reqNo);

		for (PaymentEmployeeDirectCreditDtl dtl : payEmp.getPymtEmpList()) {
			dtl.setRequestNo(reqNo);
		}
		String res = pymtEmpDao.addPaymentEmployment(payEmp);
		log.info("in service res payEmp {} -- {} " , res ,reqNo);
		return reqNo;
}

	@Override
	public PaymentEmployeeDirectCreditHdr getPaymentEmpDirectCredit(@NotBlank String requestNo,
			@NotBlank String requestedLogicalLoc, String departmentCode) {
		// TODO Auto-generated method stub
		log.info("Inside PaymentEmployeeDirectCreditServiceImpl#getPaymentEmpDirectCredit {} - {} - {}",requestNo ,requestedLogicalLoc ,departmentCode);
			return pymtEmpDao.getPaymentEmpDirectCredit(requestNo, requestedLogicalLoc,departmentCode);
		
	}
	
	@Override
	public PaymentEmployeeDirectCreditHdr getPaymentEmpDirectCreditByRequestNo(@NotBlank String requestNo) {
		// TODO Auto-generated method stub
		log.info("Inside PaymentEmployeeDirectCreditServiceImpl#getPaymentEmpDirectCredit  {}",requestNo);
			return pymtEmpDao.getPaymentEmpDirectCreditByRequestNo(requestNo);
		
	}

	@Override
	public Integer updatePaymentEmpDirectCredit(PaymentEmployeeDirectCreditHdr payEmp) {
		log.info("Inside PaymentEmployeeDirectCreditServiceImpl  #updatePaymentEmpDirectCredit : {}",payEmp);
        return pymtEmpDao.updatePaymentEmpDirectCredit(payEmp);
	
	}

}

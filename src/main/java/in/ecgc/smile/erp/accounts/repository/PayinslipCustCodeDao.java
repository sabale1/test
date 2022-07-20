package in.ecgc.smile.erp.accounts.repository;
import java.util.List;

import javax.validation.constraints.NotBlank;

import in.ecgc.smile.erp.accounts.model.PayinslipCustCode;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;
public interface PayinslipCustCodeDao{
	public List<PayinslipCustCode> getPayinslipCustCodeList()throws Exception;
	public PayinslipCustCode getPayinslipCustCodeDataById(String customerCd);
	public boolean addPayinslipCustCodeData(PayinslipCustCode  payinslipCustCode);
	public boolean updatePayinslipCustCodeData(PayinslipCustCode  payinslipCustCode);
	public List<PayinslipCustCode> getPayinSlipCustCodeByLogicalLoc(@NotBlank String logicalLoc);
}

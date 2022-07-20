package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import javax.validation.constraints.NotBlank;

import in.ecgc.smile.erp.accounts.model.PayinslipCustCode;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;
public interface PayinslipCustCodeService{
	public List<PayinslipCustCode> getPayinslipCustCodeList();
	public boolean addPayinslipCustCodeData(PayinslipCustCode  payinslipCustCode);
	public boolean updatePayinslipCustCodeData(PayinslipCustCode  payinslipCustCode);
	public PayinslipCustCode getPayinslipCustCodeDataById(String customerCd);
    public List<PayinslipCustCode> getPayinSlipCustCodeByLogicalLoc(@NotBlank String logicalLoc);
}

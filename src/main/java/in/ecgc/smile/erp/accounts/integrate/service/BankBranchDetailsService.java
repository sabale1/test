package in.ecgc.smile.erp.accounts.integrate.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import in.ecgc.smile.erp.accounts.integrate.model.BankBranchDetails;

public interface BankBranchDetailsService {
	 List<BankBranchDetails> fetchBankBranchList();

}

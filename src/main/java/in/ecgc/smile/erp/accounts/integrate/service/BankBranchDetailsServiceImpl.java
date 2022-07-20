package in.ecgc.smile.erp.accounts.integrate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.integrate.model.BankBranchDetails;
import in.ecgc.smile.erp.accounts.integrate.proxy.BankBranchFeignClient;

@Service
public class BankBranchDetailsServiceImpl implements BankBranchDetailsService {

	@Autowired
	private BankBranchFeignClient bankBranchFeignClient;
	
	@Override
	public List<BankBranchDetails> fetchBankBranchList() {
		return bankBranchFeignClient.fetchBankBranchList();
		
	}

}

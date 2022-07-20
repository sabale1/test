package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.exception.BankBranchNotFoundException;
import in.ecgc.smile.erp.accounts.model.BankBranch;
import in.ecgc.smile.erp.accounts.repository.BankBranchDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BankBranchServiceImpl implements BankBranchService {

	@Autowired
	BankBranchDao bankBranchDao;

	@Override
	public Boolean addBankBranch(BankBranch bankBranch) {
		log.info("Inside BankBranchServiceImpl#addBankBranch");
		int result = bankBranchDao.addBankBranch(bankBranch);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public List<BankBranch> listAllBankBranches() {
		log.info("Inside BankBranchServiceImpl#listAllBankBranches");
		return bankBranchDao.listAllBankBranches();
	}

	@Override
	public Integer updateBankBranch(String logicalLocCode, String bankName, BankBranch updateBankbranch) {
		log.info("Inside BankBranchServiceImpl#updateBankBranch");
		BankBranch bankBranch = bankBranchDao.findBankByLogicalLocationAndBankName(logicalLocCode, bankName);
		if (bankBranch == null) {
			throw new BankBranchNotFoundException(
					"No bank branch details found with the given location code and bank name ",
					new String[] { logicalLocCode, bankName });
		}
		return bankBranchDao.updateBankBranch(logicalLocCode, bankName, updateBankbranch);
	}

	@Override
	public BankBranch findBankByLogicalLocationAndBankName(String logicalLocCode, String bankName) {
		log.info("Inside BankBranchServiceImpl#findBankByLogicalLocationAndBankName");
		BankBranch bankBranch = bankBranchDao.findBankByLogicalLocationAndBankName(logicalLocCode, bankName);
		if (bankBranch == null) {
			throw new BankBranchNotFoundException(
					"No bank branch details found with the given location code and bank name ",
					new String[] { logicalLocCode, bankName });
		}
		return bankBranch;
	}

	@Override
	public Integer disableBankBranch(String logicalLocCode, String bankName) {
		log.info("Inside BankBranchServiceImpl#disableBankBranch");
		BankBranch bankBranch = bankBranchDao.findBankByLogicalLocationAndBankName(logicalLocCode, bankName);
		if (bankBranch == null) {
			throw new BankBranchNotFoundException(
					"No bank branch details found with the given location code and bank name ",
					new String[] { logicalLocCode, bankName });
		}
		return bankBranchDao.disableBankBranch(logicalLocCode, bankName);
	}

	@Override
	public List<BankBranch> listActiveBankBranches() {
		log.info("Inside BankBranchServiceImpl#listActiveBankBranches");
		return bankBranchDao.listActiveBankBranches();
	}

	@Override
	public String getGstinByLogicalLoc(String logicalLocCode) {
		log.info("Inside BankBranchServiceImpl#getGstinByLogicalLoc");
		return bankBranchDao.getGstinByLogicalLoc(logicalLocCode);
	}

	@Override
	public BankBranch findBankByLogicalLocation(String logicalLocCode) {
		log.info("Inside BankBranchServiceImpl#findBankByLogicalLocation");
		BankBranch bankBranch = bankBranchDao.findBankByLogicalLocation(logicalLocCode);
		if (bankBranch == null) {
			throw new BankBranchNotFoundException(
					"No bank branch details found with the given location code",
					new String[] { logicalLocCode });
		}
		return bankBranch;
	}

}

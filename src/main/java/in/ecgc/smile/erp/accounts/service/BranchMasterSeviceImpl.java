package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.exception.BankBranchNotFoundException;
import in.ecgc.smile.erp.accounts.model.BranchMaster;
import in.ecgc.smile.erp.accounts.repository.BranchMasterDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BranchMasterSeviceImpl implements BranchMasterService {

	@Autowired
	BranchMasterDao branchMasterDao;

	@Override
	public Boolean addBranch(BranchMaster branch) {
		log.info("Inside BranchMasterSeviceImpl#addBranch");
		int result = branchMasterDao.addBranch(branch);
		if (result == 1)
			return true;
		else
			return false;

	}

	@Override
	public List<BranchMaster> listAllBranches() {
		log.info("Inside BranchMasterSeviceImpl#listAllBranches");
		return branchMasterDao.listAllBranches();
	}

	@Override
	public Integer updateBranch(String logicalLocCode, String branchCode, BranchMaster updateBranch) {
		log.info("Inside BranchMasterSeviceImpl#updateBranch");
		BranchMaster branchMaster = branchMasterDao.findBranchByLogicalLocationAndBankCode(logicalLocCode, branchCode);
		if (branchMaster == null) {
			throw new BankBranchNotFoundException(
					"No bank branch details found with the given location code and bank name ",
					new String[] { logicalLocCode, branchCode });
		} else {
			return branchMasterDao.updateBranch(logicalLocCode, branchCode, updateBranch);
		}

	}

	@Override
	public Integer disableBranch(String logicalLocCode, String branchCode) {
		log.info("Inside BranchMasterSeviceImpl#disableBranch");
		BranchMaster branchMaster = branchMasterDao.findBranchByLogicalLocationAndBankCode(logicalLocCode, branchCode);
		if (branchMaster == null) {
			throw new BankBranchNotFoundException(
					"No bank branch details found with the given location code and bank name ",
					new String[] { logicalLocCode, branchCode });
		}
		return branchMasterDao.disableBranch(logicalLocCode, branchCode);
	}

}

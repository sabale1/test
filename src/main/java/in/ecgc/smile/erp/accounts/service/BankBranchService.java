package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import javax.validation.constraints.NotBlank;

import in.ecgc.smile.erp.accounts.model.BankBranch;

public interface BankBranchService {

	Boolean addBankBranch(BankBranch bankBranch);
	List<BankBranch> listAllBankBranches();
	Integer updateBankBranch(String logicalLocCode,String bankName , BankBranch updateBankbranch);
	BankBranch findBankByLogicalLocationAndBankName(String logicalLocCode ,  String bankName) ;
	Integer disableBankBranch(String logicalLocCode,String bankName );
    List<BankBranch> listActiveBankBranches();
	String getGstinByLogicalLoc(String logicalLocCode);
	BankBranch findBankByLogicalLocation(String logicalLocCode);

}

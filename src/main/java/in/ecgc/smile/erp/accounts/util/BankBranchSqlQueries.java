package in.ecgc.smile.erp.accounts.util;

public interface BankBranchSqlQueries {

	
	public static final String ADD_BANK_BRANCH=	"INSERT INTO ecgc_acct_bank_branch_mst(\r\n" + 
			"	logical_loc_cd, accounting_logical_loc_cd, expense_bank_name, expense_bank_address, expense_acct_number,"
			+ " expense_acct_neft_cd, expense_acct_ifsc_cd, dr_ib_maingl_cd, dr_ib_subgl_cd, cr_maingl_cd, "
			+ "cr_subgl_cd, cr_maingl_subgl_subbifurcation_cd, expense_bank_maingl_cd, expense_bank_subgl_cd, "
			+ "expense_bank_maingl_subgl_subbifurcation_cd, beneficiary_acct_name, beneficiary_code_ibank, client_id,gstin,active)\r\n" + 
			"	VALUES (:logicalLocCode,:accountinglogloc, :expensebankName, :expensebankAddr, :expenseAccountNumber, :expenseAcctNeftCode,"
			+ " :expenseAccountIfscCode, :dribmainGlCd, :dribsubGlCd, :cribmainGlCd, :cribsubGlCd,:crsubbifurcationCd ,:expensebankmainGlCd,:expensebanksubGlCd,"
			+ "  :expencebanksubbifurcationCd, :beneficiaryacctName, :beneficiarycodeIbank,"
			+ " :clientId,:gstin,:active)";

					
	public static final String UPDATE_BANK_BRACH="UPDATE accounts.ecgc_acct_bank_branch_mst\r\n"
	+ "SET accounting_logical_loc_cd=:accountinglogloc, expense_bank_name=:expensebankName, expense_bank_address=:expensebankAddr, expense_acct_number=:expenseAccountNumber,"
	+ " expense_acct_neft_cd=:expenseAcctNeftCode, expense_acct_ifsc_cd=:expenseAccountIfscCode, dr_ib_maingl_cd=:dribmainGlCd, dr_ib_subgl_cd=:dribsubGlCd, cr_maingl_cd=:cribmainGlCd,"
	+ " cr_subgl_cd=:cribsubGlCd, cr_maingl_subgl_subbifurcation_cd=:crsubbifurcationCd, expense_bank_maingl_cd=:expensebankmainGlCd, expense_bank_subgl_cd=:expensebanksubGlCd,"
	+ " expense_bank_maingl_subgl_subbifurcation_cd=:expencebanksubbifurcationCd, beneficiary_acct_name=:beneficiaryacctName, beneficiary_code_ibank=:beneficiarycodeIbank,"
	+ " client_id=:clientId,gstin=:gstin,active=:active"
	+ "	WHERE logical_loc_cd= :logicalLocCode and expense_bank_name=:expensebankName";
	
	public static final  String ALL_BANK_BRANCHES= "SELECT * FROM ecgc_acct_bank_branch_mst";
	
	public static final String LOAD_BANK_BRANCH_DETAILS ="select * from ecgc_acct_bank_branch_mst where "
			+ "logical_loc_cd=:logicalLocCode and expense_bank_name=:expensebankName";
	
	public static final String CHEKC_BANK_BRANCH_DETAILS ="select * from ecgc_acct_bank_branch_mst where "
			+ "logical_loc_cd=:logicalLocCode";

	public static final  String DISABLE_BANK_BRANCH="UPDATE ecgc_acct_bank_branch_mst SET active = false "
			+ "where logical_loc_cd=:logicalLocCode and expense_bank_name=:expensebankName";
	public static final String ALL_ACTIVE_BANK_BRANCHES =
			"select * from ecgc_acct_bank_branch_mst where active = 'true'";
	
	
	public static final String SELECT_GSTIN_BY_LOGICAL_LOC="select distinct gstin  from ecgc_acct_bank_branch_mst where " +
			"logical_loc_cd=:logicalLocCode";
	 
}


package in.ecgc.smile.erp.accounts.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.exception.BankBranchNotFoundException;
import in.ecgc.smile.erp.accounts.model.BankBranch;
import in.ecgc.smile.erp.accounts.util.BankBranchSqlQueries;

@Repository
@Transactional
public class BankBranchDaoImpl implements BankBranchDao {

	private JdbcOperations jdbcOperations;
	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private static final Logger LOGGER = LoggerFactory.getLogger(BankBranchDaoImpl.class);

	@Autowired
	public BankBranchDaoImpl(DataSource dataSource) {
		jdbcOperations = new JdbcTemplate(dataSource);
		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Integer disableBankBranch(String logicalLocCode, String bankName) {
		LOGGER.info("Inside BankBranchDaoImpl#disableBankBranch");
		int rowCount;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("logicalLocCode", logicalLocCode);
		paramMap.put("expensebankName", bankName);
		try {
			rowCount = namedParameterJdbcTemplate.update(BankBranchSqlQueries.DISABLE_BANK_BRANCH,
					new MapSqlParameterSource(paramMap));
		} catch (Exception e) {
			LOGGER.info("Exception occured while updating Bank Banch Details... {}", e);
			throw new BankBranchNotFoundException(
					"No bank branch details found with the given location code and bank name",
					new String[] { logicalLocCode, bankName });
		}
		return rowCount;
	}

	@Override
	public Integer addBankBranch(BankBranch bankBranch) {
		LOGGER.info("Inside BankBranchDaoImpl#addBankBranch");
		Map<String, Object> bankBranchNamedParameters = new HashMap<String, Object>();

		bankBranchNamedParameters.put("logicalLocCode", bankBranch.getLogicalLocCode());
		bankBranchNamedParameters.put("accountinglogloc", bankBranch.getAccountingLogicalLocCode());
		bankBranchNamedParameters.put("expensebankName", bankBranch.getExpensebankName());
		bankBranchNamedParameters.put("expensebankAddr", bankBranch.getExpensebankAddr());
		bankBranchNamedParameters.put("expenseAccountNumber", bankBranch.getExpenseAccountNumber());
		bankBranchNamedParameters.put("expenseAccountIfscCode", bankBranch.getExpenseAccountIfscCode());
		bankBranchNamedParameters.put("expenseAcctNeftCode", bankBranch.getExpenseAcctNeftCode());
		bankBranchNamedParameters.put("dribmainGlCd", bankBranch.getDribmainGlCd());
		bankBranchNamedParameters.put("dribsubGlCd", bankBranch.getCribsubGlCd());
		bankBranchNamedParameters.put("cribmainGlCd", bankBranch.getCribmainGlCd());
		bankBranchNamedParameters.put("cribsubGlCd", bankBranch.getCribsubGlCd());
		bankBranchNamedParameters.put("expensebankmainGlCd", bankBranch.getExpensebankmainGlCd());
		bankBranchNamedParameters.put("expensebanksubGlCd", bankBranch.getExpensebanksubGlCd());
		bankBranchNamedParameters.put("crsubbifurcationCd", bankBranch.getCrsubbifurcationCd());
		bankBranchNamedParameters.put("expencebanksubbifurcationCd", bankBranch.getExpencebanksubbifurcationCd());
		bankBranchNamedParameters.put("beneficiaryacctName", bankBranch.getBeneficiaryacctName());
		bankBranchNamedParameters.put("beneficiarycodeIbank", bankBranch.getBeneficiarycodeIbank());
		bankBranchNamedParameters.put("clientId", bankBranch.getClientId());
		bankBranchNamedParameters.put("gstin", bankBranch.getGstin());
		bankBranchNamedParameters.put("active", bankBranch.getActive());
		return namedParameterJdbcTemplate.update(BankBranchSqlQueries.ADD_BANK_BRANCH, bankBranchNamedParameters);

	}

	@Override
	public List<BankBranch> listAllBankBranches() throws DataAccessException {
		LOGGER.info("Inside BankBranchDaoImpl#listAllBankBranches");
		List<BankBranch> bankBranches = new ArrayList<BankBranch>();
		bankBranches = namedParameterJdbcTemplate.query(BankBranchSqlQueries.ALL_BANK_BRANCHES,
				new RowMapper<BankBranch>() {
					@Override
					public BankBranch mapRow(ResultSet rs, int rowNum) throws SQLException {
						BankBranch bankBranch = new BankBranch();

						bankBranch.setLogicalLocCode(rs.getString("logical_loc_cd"));
						bankBranch.setAccountingLogicalLocCode(rs.getString("accounting_logical_loc_cd"));
						bankBranch.setExpensebankName(rs.getString("expense_bank_name"));
						bankBranch.setExpensebankAddr(rs.getString("expense_bank_address"));
						bankBranch.setExpenseAccountNumber(rs.getString("expense_acct_number"));
						bankBranch.setExpenseAcctNeftCode(rs.getString("expense_acct_neft_cd"));
						bankBranch.setExpenseAccountIfscCode(rs.getString("expense_acct_ifsc_cd"));
						bankBranch.setDribmainGlCd(rs.getString("dr_ib_maingl_cd"));
						bankBranch.setDribsubGlCd(rs.getString("dr_ib_subgl_cd"));
						bankBranch.setCribmainGlCd(rs.getString("cr_maingl_cd"));
						bankBranch.setCribsubGlCd(rs.getString("cr_subgl_cd"));
						bankBranch.setCrsubbifurcationCd(rs.getString("cr_maingl_subgl_subbifurcation_cd"));
						bankBranch.setExpensebankmainGlCd(rs.getString("expense_bank_maingl_cd"));
						bankBranch.setExpensebanksubGlCd(rs.getString("expense_bank_subgl_cd"));
						bankBranch.setExpencebanksubbifurcationCd(
								rs.getString("expense_bank_maingl_subgl_subbifurcation_cd"));
						bankBranch.setBeneficiaryacctName(rs.getString("beneficiary_acct_name"));
						bankBranch.setBeneficiarycodeIbank(rs.getString("beneficiary_code_ibank"));
						bankBranch.setClientId(rs.getString("client_id"));
						bankBranch.setGstin(rs.getString("gstin"));
						bankBranch.setActive(rs.getBoolean("active"));

						return bankBranch;
					}
				});
		return bankBranches;
	}

	@Override
	public Integer updateBankBranch(String logicalLocCode, String bankName, BankBranch updateBankBranch) {
		LOGGER.info("Inside BankBranchDaoImpl#updateBankBranch");
		int rowCount;
		Map<String, Object> bankBranchNamedParameters = new HashMap<String, Object>();
		bankBranchNamedParameters.put("logicalLocCode", updateBankBranch.getLogicalLocCode());
		bankBranchNamedParameters.put("accountinglogloc", updateBankBranch.getAccountingLogicalLocCode());
		bankBranchNamedParameters.put("expensebankName", updateBankBranch.getExpensebankName());
		bankBranchNamedParameters.put("expensebankAddr", updateBankBranch.getExpensebankAddr());
		bankBranchNamedParameters.put("expenseAccountNumber", updateBankBranch.getExpenseAccountNumber());
		bankBranchNamedParameters.put("expenseAccountIfscCode", updateBankBranch.getExpenseAccountIfscCode());
		bankBranchNamedParameters.put("expenseAcctNeftCode", updateBankBranch.getExpenseAcctNeftCode());
		bankBranchNamedParameters.put("dribmainGlCd", updateBankBranch.getDribmainGlCd());
		bankBranchNamedParameters.put("dribsubGlCd", updateBankBranch.getCribsubGlCd());
		bankBranchNamedParameters.put("cribmainGlCd", updateBankBranch.getCribmainGlCd());
		bankBranchNamedParameters.put("cribsubGlCd", updateBankBranch.getCribsubGlCd());
		bankBranchNamedParameters.put("expensebankmainGlCd", updateBankBranch.getExpensebankmainGlCd());
		bankBranchNamedParameters.put("expensebanksubGlCd", updateBankBranch.getExpensebanksubGlCd());
		bankBranchNamedParameters.put("crsubbifurcationCd", updateBankBranch.getCrsubbifurcationCd());
		bankBranchNamedParameters.put("expencebanksubbifurcationCd", updateBankBranch.getExpencebanksubbifurcationCd());
		bankBranchNamedParameters.put("beneficiaryacctName", updateBankBranch.getBeneficiaryacctName());
		bankBranchNamedParameters.put("beneficiarycodeIbank", updateBankBranch.getBeneficiarycodeIbank());
		bankBranchNamedParameters.put("clientId", updateBankBranch.getClientId());
		bankBranchNamedParameters.put("gstin", updateBankBranch.getGstin());
		bankBranchNamedParameters.put("active", updateBankBranch.getActive());
		try {
			rowCount = namedParameterJdbcTemplate.update(BankBranchSqlQueries.UPDATE_BANK_BRACH,
					bankBranchNamedParameters);
		} catch (Exception e) {
			LOGGER.info("Exception occured while updating Bank Banch Details... {}", e);
			throw new BankBranchNotFoundException(
					"No bank branch details found with the given location code and bank name",
					new String[] { logicalLocCode, bankName });
		}
		return rowCount;
	}

	@Override
	public BankBranch findBankByLogicalLocationAndBankName(String logicalLocCode, String bankName) {
		LOGGER.info("Inside BankBranchDaoImpl#findBankByLogicalLocationAndBankName");
		BankBranch bankBranchList = new BankBranch();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("logicalLocCode", logicalLocCode);
		paramMap.put("expensebankName", bankName);

		try {
			bankBranchList = namedParameterJdbcOperations.queryForObject(BankBranchSqlQueries.LOAD_BANK_BRANCH_DETAILS,
					paramMap, new RowMapper<BankBranch>() {

						@Override
						public BankBranch mapRow(ResultSet rs, int rowNum) throws SQLException {

							BankBranch bankBranch = new BankBranch();
							bankBranch.setLogicalLocCode(rs.getString("logical_loc_cd"));
							bankBranch.setAccountingLogicalLocCode(rs.getString("accounting_logical_loc_cd"));
							bankBranch.setExpensebankName(rs.getString("expense_bank_name"));
							bankBranch.setExpensebankAddr(rs.getString("expense_bank_address"));
							bankBranch.setExpenseAccountNumber(rs.getString("expense_acct_number"));
							bankBranch.setExpenseAcctNeftCode(rs.getString("expense_acct_neft_cd"));
							bankBranch.setExpenseAccountIfscCode(rs.getString("expense_acct_ifsc_cd"));
							bankBranch.setDribmainGlCd(rs.getString("dr_ib_maingl_cd"));
							bankBranch.setDribsubGlCd(rs.getString("dr_ib_subgl_cd"));
							bankBranch.setCribmainGlCd(rs.getString("cr_maingl_cd"));
							bankBranch.setCribsubGlCd(rs.getString("cr_subgl_cd"));
							bankBranch.setCrsubbifurcationCd(rs.getString("cr_maingl_subgl_subbifurcation_cd"));
							bankBranch.setExpensebankmainGlCd(rs.getString("expense_bank_maingl_cd"));
							bankBranch.setExpensebanksubGlCd(rs.getString("expense_bank_subgl_cd"));
							bankBranch.setExpencebanksubbifurcationCd(
									rs.getString("expense_bank_maingl_subgl_subbifurcation_cd"));
							bankBranch.setBeneficiaryacctName(rs.getString("beneficiary_acct_name"));
							bankBranch.setBeneficiarycodeIbank(rs.getString("beneficiary_code_ibank"));
							bankBranch.setClientId(rs.getString("client_id"));
							bankBranch.setGstin(rs.getString("gstin"));
							bankBranch.setActive(rs.getBoolean("active"));
							return bankBranch;

						}
					});
		} catch (Exception e) {
			bankBranchList = null;
			throw new BankBranchNotFoundException(
					"No bank branch details found with the given location code and bank name ",
					new String[] { logicalLocCode, bankName });
		}
		return bankBranchList;
	}

	@Override
	public List<BankBranch> listActiveBankBranches() throws DataAccessException {
		LOGGER.info("Inside BankBranchDaoImpl#listActiveBankBranches");
		List<BankBranch> bankBranchList = new ArrayList<BankBranch>();

		bankBranchList = namedParameterJdbcTemplate.query(BankBranchSqlQueries.ALL_ACTIVE_BANK_BRANCHES,
				new RowMapper<BankBranch>() {

					@Override
					public BankBranch mapRow(ResultSet rs, int rowNum) throws SQLException {
						BankBranch bankBranch = new BankBranch();

						bankBranch.setLogicalLocCode(rs.getString("logical_loc_cd"));
						bankBranch.setAccountingLogicalLocCode(rs.getString("accounting_logical_loc_cd"));
						bankBranch.setExpensebankName(rs.getString("expense_bank_name"));
						bankBranch.setExpensebankAddr(rs.getString("expense_bank_address"));
						bankBranch.setExpenseAccountNumber(rs.getString("expense_acct_number"));
						bankBranch.setExpenseAcctNeftCode(rs.getString("expense_acct_neft_cd"));
						bankBranch.setExpenseAccountIfscCode(rs.getString("expense_acct_ifsc_cd"));
						bankBranch.setDribmainGlCd(rs.getString("dr_ib_maingl_cd"));
						bankBranch.setDribsubGlCd(rs.getString("dr_ib_subgl_cd"));
						bankBranch.setCribmainGlCd(rs.getString("cr_maingl_cd"));
						bankBranch.setCribsubGlCd(rs.getString("cr_subgl_cd"));
						bankBranch.setCrsubbifurcationCd(rs.getString("cr_maingl_subgl_subbifurcation_cd"));
						bankBranch.setExpensebankmainGlCd(rs.getString("expense_bank_maingl_cd"));
						bankBranch.setExpensebanksubGlCd(rs.getString("expense_bank_subgl_cd"));
						bankBranch.setExpencebanksubbifurcationCd(
								rs.getString("expense_bank_maingl_subgl_subbifurcation_cd"));
						bankBranch.setBeneficiaryacctName(rs.getString("beneficiary_acct_name"));
						bankBranch.setBeneficiarycodeIbank(rs.getString("beneficiary_code_ibank"));
						bankBranch.setClientId(rs.getString("client_id"));
						bankBranch.setGstin(rs.getString("gstin"));
						bankBranch.setActive(rs.getBoolean("active"));
						return bankBranch;
					}
				});
		return bankBranchList;
	}

	@Override
	public String getGstinByLogicalLoc(String logicalLocCode) {
		LOGGER.info("Inside BankBranchDaoImpl#getGstinByLogicalLoc");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("logicalLocCode", logicalLocCode);
		String gstiNumber = "";
		try {

			gstiNumber = namedParameterJdbcOperations.queryForObject(BankBranchSqlQueries.SELECT_GSTIN_BY_LOGICAL_LOC,
					paramMap, new RowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {

							String gstin = "";
							gstin = rs.getString("gstin");
							return gstin;

						}
					});
		} catch (EmptyResultDataAccessException e) {
			gstiNumber = "";
		}
		return gstiNumber;
	}

	@Override
	public BankBranch findBankByLogicalLocation(String logicalLocCode) {
		LOGGER.info("Inside BankBranchDaoImpl#findBankByLogicalLocation");
		BankBranch bankBranchList = new BankBranch();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("logicalLocCode", logicalLocCode);
		
		try {
			bankBranchList = namedParameterJdbcOperations.queryForObject(BankBranchSqlQueries.CHEKC_BANK_BRANCH_DETAILS,
					paramMap, new RowMapper<BankBranch>() {

						@Override
						public BankBranch mapRow(ResultSet rs, int rowNum) throws SQLException {

							BankBranch bankBranch = new BankBranch();
							bankBranch.setLogicalLocCode(rs.getString("logical_loc_cd"));
							bankBranch.setAccountingLogicalLocCode(rs.getString("accounting_logical_loc_cd"));
							bankBranch.setExpensebankName(rs.getString("expense_bank_name"));
							bankBranch.setExpensebankAddr(rs.getString("expense_bank_address"));
							bankBranch.setExpenseAccountNumber(rs.getString("expense_acct_number"));
							bankBranch.setExpenseAcctNeftCode(rs.getString("expense_acct_neft_cd"));
							bankBranch.setExpenseAccountIfscCode(rs.getString("expense_acct_ifsc_cd"));
							bankBranch.setDribmainGlCd(rs.getString("dr_ib_maingl_cd"));
							bankBranch.setDribsubGlCd(rs.getString("dr_ib_subgl_cd"));
							bankBranch.setCribmainGlCd(rs.getString("cr_maingl_cd"));
							bankBranch.setCribsubGlCd(rs.getString("cr_subgl_cd"));
							bankBranch.setCrsubbifurcationCd(rs.getString("cr_maingl_subgl_subbifurcation_cd"));
							bankBranch.setExpensebankmainGlCd(rs.getString("expense_bank_maingl_cd"));
							bankBranch.setExpensebanksubGlCd(rs.getString("expense_bank_subgl_cd"));
							bankBranch.setExpencebanksubbifurcationCd(
									rs.getString("expense_bank_maingl_subgl_subbifurcation_cd"));
							bankBranch.setBeneficiaryacctName(rs.getString("beneficiary_acct_name"));
							bankBranch.setBeneficiarycodeIbank(rs.getString("beneficiary_code_ibank"));
							bankBranch.setClientId(rs.getString("client_id"));
							bankBranch.setGstin(rs.getString("gstin"));
							bankBranch.setActive(rs.getBoolean("active"));
							return bankBranch;

						}
					});
		} catch (Exception e) {
			bankBranchList = null;
			throw new BankBranchNotFoundException(
					"No bank branch details found with the given location code  ",
					new String[] { logicalLocCode });
		}
		return bankBranchList;
	}

}
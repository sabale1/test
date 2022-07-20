package in.ecgc.smile.erp.accounts.repository;

import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.BankBranch;
import in.ecgc.smile.erp.accounts.util.BankBranchSqlQueries;

public class BankBranchDaoImplTest {

	
	private MockMvc mockMvc;

	@Mock
	private DataSource dataSource;

	@Mock
	private JdbcOperations jdbcOperations;

	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcOperations;

	@Mock
	private BankBranch bankBranch;

	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Mock
	private List<BankBranch> bankBranchList;
	
	@Mock
	private Map<String, Object> bankBranchNamedParameter;

	@InjectMocks
	private BankBranchDaoImpl bankBranchDaoImpl;

	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(bankBranchDaoImpl).build();
	}
	
	@BeforeTest
	public void initBankBranch()
	{
		bankBranch= new BankBranch();
		bankBranch.setExpensebankName("Mumbai");
		bankBranch.setExpenseAccountNumber("1209001509004024");
		bankBranch.setExpenseAccountIfscCode("PUNB0120900");
		bankBranch.setExpenseAcctNeftCode("PUNB0120900");
		bankBranch.setLogicalLocCode("MUM");
		bankBranch.setActive(true);
		bankBranch.setClientId("1234");
		bankBranch.setGstin("123456789123456");
		bankBranch.setCrsubbifurcationCd("AEC");
		bankBranch.setExpencebanksubbifurcationCd("AEC");
		bankBranch.setExpensebankmainGlCd("1700");
		bankBranch.setExpensebanksubGlCd("001");
		bankBranch.setDribmainGlCd("1700");
		bankBranch.setDribsubGlCd("001");
		bankBranch.setCribmainGlCd("1700");
		bankBranch.setCribsubGlCd("001");
		bankBranch.setExpensebankAddr("ADC");
	}

	@BeforeTest
	private void initBankBranchNamedParameters() {

		Map<String, Object> bankBranchNamedParameter = new HashMap<String , Object>();
		bankBranchNamedParameter.put("logicalLocCode", bankBranch.getLogicalLocCode());
		bankBranchNamedParameter.put("accountinglogloc", bankBranch.getAccountingLogicalLocCode());
		bankBranchNamedParameter.put("expensebankName", bankBranch.getExpensebankName());
		bankBranchNamedParameter.put("expensebankAddr", bankBranch.getExpensebankAddr());
		bankBranchNamedParameter.put("expenseAccountNumber", bankBranch.getExpenseAccountNumber());
		bankBranchNamedParameter.put("expenseAccountIfscCode", bankBranch.getExpenseAccountIfscCode());
		bankBranchNamedParameter.put("expenseAcctNeftCode", bankBranch.getExpenseAcctNeftCode());
		bankBranchNamedParameter.put("dribmainGlCd", bankBranch.getDribmainGlCd());
		bankBranchNamedParameter.put("dribsubGlCd", bankBranch.getCribsubGlCd());
		bankBranchNamedParameter.put("cribmainGlCd", bankBranch.getCribmainGlCd());
		bankBranchNamedParameter.put("cribsubGlCd", bankBranch.getCribsubGlCd());
		bankBranchNamedParameter.put("expensebankmainGlCd", bankBranch.getExpensebankmainGlCd());
		bankBranchNamedParameter.put("expensebanksubGlCd", bankBranch.getExpensebanksubGlCd());
		bankBranchNamedParameter.put("crsubbifurcationCd", bankBranch.getCrsubbifurcationCd());
		bankBranchNamedParameter.put("expencebanksubbifurcationCd", bankBranch.getExpencebanksubbifurcationCd());
		bankBranchNamedParameter.put("beneficiaryacctName", bankBranch.getBeneficiaryacctName());
		bankBranchNamedParameter.put("beneficiarycodeIbank", bankBranch.getBeneficiarycodeIbank());
		bankBranchNamedParameter.put("clientId", bankBranch.getClientId());
		bankBranchNamedParameter.put("gstin", bankBranch.getGstin());
		bankBranchNamedParameter.put("active", bankBranch.getActive());		
	}
		
	@Test
	public void listAllBankBranchesTest() {
		List<BankBranch> bankBranches = new ArrayList<BankBranch>();
		when(namedParameterJdbcOperations.query(BankBranchSqlQueries.ALL_BANK_BRANCHES, new RowMapper<BankBranch>() {
			@Override
			public BankBranch mapRow(ResultSet rs,int rowNum) throws SQLException{
				BankBranch bankBranch =  new BankBranch();

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
		})).thenReturn(bankBranchList);
	}
	
	@Test
	private void addBankBranchtest() {
		when(namedParameterJdbcOperations.update(BankBranchSqlQueries.ADD_BANK_BRANCH, bankBranchNamedParameter))
		.thenReturn(1);
	}

	@Test
	public void findBankBranchByLogicalLocAndBankNameDaoImplTest() {
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("logicalLocCode", "MUM");
		paraMap.put("bankName", "PNB");
		when(namedParameterJdbcOperations.queryForObject(BankBranchSqlQueries.LOAD_BANK_BRANCH_DETAILS, paraMap, new RowMapper<BankBranch>() {
			
			@Override
			public BankBranch mapRow(ResultSet rs , int rownum) throws SQLException{
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
		})).thenReturn(bankBranch);
	}
	
	@Test
	public void updateBankBranchDoaImplTest() {
		when(namedParameterJdbcOperations.update(BankBranchSqlQueries.UPDATE_BANK_BRACH, bankBranchNamedParameter))
		.thenReturn(1);
	}
	
	@Test
	public void disableBankBranchDaoImpleTest() {
		when(namedParameterJdbcOperations.update(BankBranchSqlQueries.DISABLE_BANK_BRANCH,
				new MapSqlParameterSource("logicalLocCode","MUM").addValue("bankName", "PNB"))).thenReturn(1);
	}
		
	@Test 
	public void listActiveBankBranches() {
		when(jdbcOperations.query(BankBranchSqlQueries.ALL_ACTIVE_BANK_BRANCHES, new RowMapper<BankBranch>() {
			
			@Override
			public BankBranch mapRow(ResultSet rs  ,int rowNum)throws SQLException{
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
		})).thenReturn(bankBranchList);
	}
	
	
}


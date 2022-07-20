package in.ecgc.smile.erp.accounts.repository;

import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import in.ecgc.smile.erp.accounts.model.PettyCashMaster;
import in.ecgc.smile.erp.accounts.util.PettyCashMasterSqlQueries;

public class PettyCashDaoImplTest {
	
	private MockMvc mockMvc;
	@Mock
	private DataSource dataSource;
	@Mock
	private JdbcOperations jdbcOperations;
	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcOperations;	
	
	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;	
	@Mock
	private PettyCashMaster pettyCashMaster;
	@Mock
	private List<PettyCashMaster> pettyCashMstList;
	
	@InjectMocks
	private PettyCashMasterDaoImpl pettyCashMasterDaoImpl;
	
	@BeforeTest
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(pettyCashMasterDaoImpl).build();
	}
	
	@BeforeTest
	public void initPettyCash()
	{
		PettyCashMaster pettyCashMaster = new PettyCashMaster();
		pettyCashMaster.setCashAmt(0.0);
		pettyCashMaster.setCreatedBy("ACCOUNTS");
		
		pettyCashMaster.setEntityCd("ECGC");
		pettyCashMaster.setFiscalYr("2021");
		
		pettyCashMaster.setLogicalLocCode("MUMBAILOG1");
		pettyCashMaster.setRemark("Com");
		pettyCashMaster.setReqStatus("Requested");
		pettyCashMaster.setRequestDt(LocalDate.parse("2021-02-04"));
		pettyCashMaster.setRequisitionNo(2021000002);
	}
	
	@Test
	public void addPettyCashDetailsTest() {
		Map<String, Object> pettyCashNamedParameter = new HashMap<String, Object>();
		pettyCashNamedParameter.put("logicalLocCode" ,pettyCashMaster.getLogicalLocCode());
		pettyCashNamedParameter.put("cashAmt",pettyCashMaster.getCashAmt());
		pettyCashNamedParameter.put("requestDt", pettyCashMaster.getRequestDt());
		pettyCashNamedParameter.put("remark",pettyCashMaster.getRemark() );
		pettyCashNamedParameter.put("requisitionNo", pettyCashMaster.getRequisitionNo());
		pettyCashNamedParameter.put("fiscalYr", pettyCashMaster.getFiscalYr());
		pettyCashNamedParameter.put("createdBy" ,pettyCashMaster.getCreatedBy());
		pettyCashNamedParameter.put("createdDt" , pettyCashMaster.getCreatedDt());
		pettyCashNamedParameter.put("entityCd", pettyCashMaster.getEntityCd());
		when(namedParameterJdbcOperations.update(PettyCashMasterSqlQueries.ADD_PETTY_CASH_DETAILS,pettyCashNamedParameter)).thenReturn(1);
		when(namedParameterJdbcOperations.update(PettyCashMasterSqlQueries.UPDATE_SEQ_TBL,pettyCashNamedParameter)).thenReturn(1);

	}
	
	@Test
	public void getRequisitionNoTest() {
		Integer seq = 2021000001;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("logicalLocCode",pettyCashMaster.getLogicalLocCode());
		param.put("fiscalYr",pettyCashMaster.getFiscalYr());
		when( namedParameterJdbcOperations.queryForObject(PettyCashMasterSqlQueries.GET_SEQ,param,
				new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						int num =rs.getInt(1);
						return num;
					}
				})).thenReturn(seq);
	}
	
	@Test
	public void listAllPettyCashTest() {
		List<PettyCashMaster> pettyCashMasters = new ArrayList<PettyCashMaster>();
		when(namedParameterJdbcTemplate.query(PettyCashMasterSqlQueries.VIEW_ALL, new RowMapper<PettyCashMaster>() {
			@Override
			public PettyCashMaster mapRow(ResultSet rs, int rowNum) throws SQLException{
				PettyCashMaster pettyCashMaster = new PettyCashMaster();
				
				pettyCashMaster.setRequisitionNo(rs.getInt("requisition_no"));
				pettyCashMaster.setLogicalLocCode(rs.getString("logical_loc_cd"));
				pettyCashMaster.setCashAmt(rs.getDouble("cash_amt"));
				pettyCashMaster.setCreatedBy(rs.getString("created_by"));
				if(rs.getDate("created_dt") != null)
				pettyCashMaster.setCreatedDt(rs.getDate("created_dt").toLocalDate());
				pettyCashMaster.setLastUpdatedBy(rs.getString("last_updated_by"));
				if(rs.getDate("last_updated_dt") != null)
				pettyCashMaster.setLastUpdatedDt(rs.getDate("last_updated_dt").toLocalDate());
				pettyCashMaster.setEntityCd(rs.getString("entity_cd"));
				pettyCashMaster.setFiscalYr(rs.getString("fiscal_yr"));
				pettyCashMaster.setRemark(rs.getString("remark"));
				if(rs.getDate("request_dt") != null)
				pettyCashMaster.setRequestDt(rs.getDate("request_dt").toLocalDate());
				pettyCashMaster.setReqStatus(rs.getString("req_status"));
				pettyCashMaster.setProcessIsntanceId(rs.getLong("process_instance_id"));
								
				return pettyCashMaster;				
			}
		})).thenReturn(pettyCashMasters);
	}
	
	@Test
	public void findByRequisitionNoTest() {
		PettyCashMaster pettyCash = new PettyCashMaster();
		 Map<String, Object> param = new HashMap<>();
		 param.put("requisitionNo", pettyCashMaster.getRequisitionNo());
		 param.put("logicalLocCode", pettyCashMaster.getLogicalLocCode());
		 when( namedParameterJdbcOperations.queryForObject(PettyCashMasterSqlQueries.FIND_BY_REQUI_NO, param, new RowMapper<PettyCashMaster>(){

				@Override
				public PettyCashMaster mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					PettyCashMaster pettyCashMaster = new PettyCashMaster();
					
					pettyCashMaster.setRequisitionNo(rs.getInt("requisition_no"));
					pettyCashMaster.setLogicalLocCode(rs.getString("logical_loc_cd"));
					pettyCashMaster.setCashAmt(rs.getDouble("cash_amt"));
					pettyCashMaster.setCreatedBy(rs.getString("created_by"));
					pettyCashMaster.setCreatedDt(rs.getDate("created_dt").toLocalDate());
					pettyCashMaster.setEntityCd(rs.getString("entity_cd"));
					pettyCashMaster.setFiscalYr(rs.getString("fiscal_yr"));
					pettyCashMaster.setRemark(rs.getString("remark"));
					pettyCashMaster.setRequestDt(rs.getDate("request_dt").toLocalDate());
					pettyCashMaster.setReqStatus(rs.getString("req_status"));
					
					return pettyCashMaster;
				}
			
			 })).thenReturn(pettyCash);
	}
	
	@Test
	public void updatePettyCashTest() {
		
		Map<String, Object> param = new HashMap<String ,Object>();
		param.put("logicalLocCode",pettyCashMaster.getLogicalLocCode());
		param.put("requisitionNo",pettyCashMaster.getRequisitionNo());
		param.put("remark", pettyCashMaster.getRemark());
		param.put("fiscalYr", pettyCashMaster.getFiscalYr());
		param.put("requestDt", pettyCashMaster.getRequestDt());
		param.put("lastUpdatedBy", pettyCashMaster.getLastUpdatedBy());
		param.put("lastUpdatedDt", pettyCashMaster.getLastUpdatedDt());
		param.put("entityCd", pettyCashMaster.getEntityCd());
		param.put("cashAmt", pettyCashMaster.getCashAmt());
		when(namedParameterJdbcTemplate.update(PettyCashMasterSqlQueries.UPDATE_PETTY_CASH_DETAILS, param)).thenReturn(1);
	}
	
	@Test
	public void approvedStatusTest() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requisitionNo", pettyCashMaster.getRequisitionNo());
		param.put("logicalLocCode", pettyCashMaster.getLogicalLocCode());
		
		when(namedParameterJdbcTemplate.update(PettyCashMasterSqlQueries.APPROVED_STS, param)).thenReturn(1);
	}
	
	@Test
	public void getProcessInstanceIdTest() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requisitionNo", pettyCashMaster.getRequisitionNo());
		param.put("logicalLocCode", pettyCashMaster.getLogicalLocCode());

		Long processInstanceId = 5678L;
		when(namedParameterJdbcOperations.queryForObject(PettyCashMasterSqlQueries.GET_PROCESS_INSTANCE_ID, param,
					Long.class)).thenReturn(processInstanceId);
		
	}
	
	@Test
	public void updateProcessInstanceIdTest() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requisitionNo", pettyCashMaster.getRequisitionNo());
		param.put("logicalLocCode", pettyCashMaster.getLogicalLocCode());
		param.put("processInstanceId", pettyCashMaster.getProcessIsntanceId());
		
		
		when(namedParameterJdbcTemplate.update(PettyCashMasterSqlQueries.UPDATE_PROCESS_INSTANCE_ID, param)).thenReturn(1);
		
	}
	
	
	
	

}

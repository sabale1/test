package in.ecgc.smile.erp.accounts.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import in.ecgc.smile.erp.accounts.exception.CalendarRecordupdateException;
import in.ecgc.smile.erp.accounts.exception.RecordNotFoundException;
import in.ecgc.smile.erp.accounts.model.FiscalYearModel;
import in.ecgc.smile.erp.accounts.model.GLTxnType;
import in.ecgc.smile.erp.accounts.util.CalendarSqlQueries;
import in.ecgc.smile.erp.accounts.util.FiscalYearSqlQueries;
import in.ecgc.smile.erp.accounts.util.GLTxnTypeSqlQueries;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record;

/**
 * Fiscal year DAO implementation
 * 
 * @version 1.0 15-September-2020
 * @author Sanjali Kesarkar
 *
 */
@Slf4j
@Repository
@Transactional
public class FiscalYearDaoImpl implements FiscalYearDao {
	
	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(FiscalYearDaoImpl.class);

	@Autowired
	public FiscalYearDaoImpl(DataSource dataSource) {
		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	/**
	 * Find details of current fiscal year
	 * 
	 * DAO implementation to find details of current fiscal year present
	 * in the database Table :acct_fiscal_yr returns model FiscalYearModel
	 * {@link in.ecgc.smile.erp.accounts.model.FiscalYearModel}
	 */
	@Override
	public FiscalYearModel findCurrentFiscalYear() {
		log.info("Inside FiscalYearDaoImpl#findCurrentFiscalYear");
		FiscalYearModel fiscalYearModel;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", 'V');
		try {
			fiscalYearModel = namedParameterJdbcOperations.queryForObject(FiscalYearSqlQueries.GET_CURRENT_FISCAL_YEAR,
					paramMap,
					new RowMapper<FiscalYearModel>() {

						@Override
						public FiscalYearModel mapRow(ResultSet rs, int rowNum) throws SQLException {
							FiscalYearModel fiscalYearModel = new FiscalYearModel();

							fiscalYearModel.setCurrFiscalYear(rs.getString("curr_fisc_yr"));
							fiscalYearModel.setCurrFiscalYearStartDt(rs.getDate("curr_fisc_yr_start_dt").toLocalDate());
							fiscalYearModel.setPrevFiscalYear(rs.getString("prev_fisc_yr"));
							fiscalYearModel.setPrevFiscalYearClosedDt(rs.getDate("prev_fisc_yr_closed_dt").toLocalDate());
							fiscalYearModel.setMetaStatus(rs.getString("meta_status").charAt(0));
							fiscalYearModel.setCreatedBy(rs.getString("created_by"));
							fiscalYearModel.setCreatedDt(rs.getDate("created_dt"));
							fiscalYearModel.setLastUpdatedBy(rs.getString("last_updated_by"));
							fiscalYearModel.setLastUpdatedDt(rs.getDate("last_updated_dt"));
							fiscalYearModel.setMetaRemarks(rs.getString("meta_remarks"));
							return fiscalYearModel;
						}
					});
			
			return fiscalYearModel;
		} catch (EmptyResultDataAccessException e) {
			log.error("ERROR: Unable to fetch current fiscal year {}", e.getMessage());
			throw new RecordNotFoundException("Failed to Find Current Fiscal Year");
		}
		catch (Exception e) {
			log.error("ERROR: Unable to fetch current fiscal year {}", e.getMessage());
			throw new RecordNotFoundException("Failed to Find Current Fiscal Year");
		}
	}
	/**
	 * Get all fiscal years
	 * 
	 * DAO implementation to get all fiscal years present in the
	 * database Table :acct_fiscal_yr returns list of model FiscalYearModel
	 * {@link in.ecgc.smile.erp.accounts.model.FiscalYearModel}
	 */
	@Override
	public List<String> getFiscalYearList() {
		log.info("Inside FiscalYearDaoImpl#getFiscalYearList");
		try {
		List<String> fiscalYearList = new ArrayList<String>();
		fiscalYearList = namedParameterJdbcTemplate.query(FiscalYearSqlQueries.ALL_FISCAL_YR,
				new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {

						return rs.getString("curr_fisc_yr");

					}
				});
		log.info("FiscaL year list is {}",fiscalYearList);
		return fiscalYearList;
		}
		catch (EmptyResultDataAccessException e) {
			log.error("ERROR: Unable to get fiscal year list {}", e.getMessage());
			throw new RecordNotFoundException("Failed to Get Fiscal Year List");
		}
		catch (Exception e) {
			log.error("ERROR: Unable to get fiscal year list {}", e.getMessage());
			throw new RecordNotFoundException("Failed to Get Fiscal Year List");
		}
	}
	
	@Override
	public List<FiscalYearModel> getFiscalYrModelList() {
		log.info("Inside FiscalYearDaoImpl#getFiscalYrModelList");
		List<FiscalYearModel> fiscalYears = new ArrayList<FiscalYearModel>();
		try
		{
		
		fiscalYears = namedParameterJdbcTemplate.query(FiscalYearSqlQueries.GET_FISCAL_YRS,
				new RowMapper<FiscalYearModel>() {

					@Override
					public FiscalYearModel mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						FiscalYearModel fiscalYearModel = new FiscalYearModel();
						fiscalYearModel.setCurrFiscalYear(rs.getString("curr_fisc_yr"));
						fiscalYearModel.setCurrFiscalYearStartDt(rs.getDate("curr_fisc_yr_start_dt").toLocalDate());
						fiscalYearModel.setPrevFiscalYear(rs.getString("prev_fisc_yr"));
						fiscalYearModel.setPrevFiscalYearClosedDt(rs.getDate("prev_fisc_yr_closed_dt").toLocalDate());
						fiscalYearModel.setMetaStatus(rs.getString("meta_status").charAt(0));
						fiscalYearModel.setCreatedBy(rs.getString("created_by"));
						fiscalYearModel.setCreatedDt(rs.getDate("created_dt"));
						fiscalYearModel.setLastUpdatedBy(rs.getString("last_updated_by"));
						fiscalYearModel.setLastUpdatedDt(rs.getDate("last_updated_dt"));
						fiscalYearModel.setMetaRemarks(rs.getString("meta_remarks"));
						return fiscalYearModel;
					}
				});
		log.info("FiscaL year list is {}",fiscalYears);
		return fiscalYears;
		}
		catch (Exception e) {
			log.error("ERROR: Unable to fetch fiscal years {}", e.getMessage());
			throw new RecordNotFoundException("Failed to Get List of Fiscal Years");
		}
		
		
	}
	@Override
	public FiscalYearModel getFiscalYrDataById(String currFiscYr) {
		log.info("inside FiscalYrDaoImpl  -  getFiscalYrDatById");
		Map<String, Object>paramsMap= new HashMap<>();
		paramsMap.put("currFiscYr", currFiscYr);
		FiscalYearModel fiscalYears = new FiscalYearModel();
		try
		{
		
		fiscalYears = namedParameterJdbcTemplate.queryForObject(FiscalYearSqlQueries.GET_FISCALYR_DATA_BY_ID,paramsMap,
				new RowMapper<FiscalYearModel>() {

					@Override
					public FiscalYearModel mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						FiscalYearModel fiscalYearModel = new FiscalYearModel();
						fiscalYearModel.setCurrFiscalYear(rs.getString("curr_fisc_yr"));
						fiscalYearModel.setCurrFiscalYearStartDt(rs.getDate("curr_fisc_yr_start_dt").toLocalDate());
						fiscalYearModel.setPrevFiscalYear(rs.getString("prev_fisc_yr"));
						fiscalYearModel.setPrevFiscalYearClosedDt(rs.getDate("prev_fisc_yr_closed_dt").toLocalDate());
						fiscalYearModel.setMetaStatus(rs.getString("meta_status").charAt(0));
						fiscalYearModel.setCreatedBy(rs.getString("created_by"));
						fiscalYearModel.setCreatedDt(rs.getDate("created_dt"));
						fiscalYearModel.setLastUpdatedBy(rs.getString("last_updated_by"));
						fiscalYearModel.setLastUpdatedDt(rs.getDate("last_updated_dt"));
						fiscalYearModel.setMetaRemarks(rs.getString("meta_remarks"));
						return fiscalYearModel;
					}
				});
		log.info("FiscaL year is {}",fiscalYears);
		return fiscalYears;
		}
		catch (Exception e) {
			log.error("ERROR: Unable to fetch fiscal year for {}  {}",currFiscYr, e.getMessage());
			throw new RecordNotFoundException("Failed to Fetch Fiscal Year For "+currFiscYr);
		}
	}
	@Override
	public Integer updateCurrentFiscalYear(Integer first, Integer sec, String prefisyr) {
		log.info("inside FiscalYrDaoImpl  -  updateCurrentFiscalYear");
		Integer count = 0;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("currFiscYrStartDt", new Date());
		paramMap.put("currFiscYr", first+"-"+sec);
		paramMap.put("prevFiscYr", prefisyr);
		paramMap.put("prevFiscYrClosedDt",new Date(new Date().getTime() - 24 * 3600 * 1000));
		paramMap.put("metaStatus", 'V');
		paramMap.put("createdBy", "102");
		paramMap.put("metaRemarks", "Test");
		paramMap.put("shortYr",first);

		try {
			count = namedParameterJdbcTemplate.update(FiscalYearSqlQueries.CREATE_CURRENT_FISCAL_YEAR_ENTRY, paramMap);
			if(count==1)
			{
			count = namedParameterJdbcTemplate.update(FiscalYearSqlQueries.UPADATE_PRE_FISCAL_YEAR_ENTRY, paramMap);
			}
		} catch (Exception e) {
			log.error("Exception in FiscalYrDaoImpl#updateCurrentFiscalYear {}", e.fillInStackTrace());
			return null;
		}
		return count;
	}
	@Override
	public Integer createCurrentFiscalYearEntry(FiscalYearModel fiscalYr) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("currFiscYrStartDt", fiscalYr.getCurrFiscalYearStartDt());
		paramMap.put("currFiscYr", fiscalYr.getCurrFiscalYear());
		paramMap.put("prevFiscYr", fiscalYr.getPrevFiscalYear());
		paramMap.put("prevFiscYrClosedDt", fiscalYr.getPrevFiscalYearClosedDt());
		paramMap.put("metaStatus", 'V');
		paramMap.put("metaRemarks", fiscalYr.getMetaRemarks());
		paramMap.put("createdBy", "102");
		paramMap.put("shortYr","first");
		
		//List<String> fiscalYrList=new ArrayList<>();
		//fiscalYrList.add();//17-18
		
		List<String> list = getFiscalYearList(); 
		
		/*for (String fiscalYear : list) {
			if (fiscalYear.equals(fiscalYr.getCurrFiscalYear())) {
				 namedParameterJdbcTemplate.update(FiscalYearSqlQueries.UPDATE_FISCAL_YEAR_ENTRY, paramMap);
				 return namedParameterJdbcTemplate.update(FiscalYearSqlQueries.UPDATE_FISCAL_YEAR_ENTRY_ACTIVE, paramMap);
				
			}

		}*/
		// namedParameterJdbcTemplate.update(FiscalYearSqlQueries.UPDATE_FISCAL_YEAR_ENTRY_ACTIVE, paramMap);
		
		  namedParameterJdbcTemplate.update(FiscalYearSqlQueries.DELETE_FISCAL_YEAR_ENTRY, paramMap);
		 
		return namedParameterJdbcTemplate.update(FiscalYearSqlQueries.CREATE_FISCAL_YEAR_ENTRY,paramMap);
		
		
		
		
			}
	@Override
	public Integer closePreviousFiscal(String prevFiscalYr) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("last_updated_by", 101);
		paramMap.put("prevFiscalYr", prevFiscalYr);
		return namedParameterJdbcTemplate.update(FiscalYearSqlQueries.CLOSE_PREVIOUS_FISCAL_YEAR,paramMap);

	}
}

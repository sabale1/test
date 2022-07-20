package in.ecgc.smile.erp.accounts.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.exception.CalendarRecordInsertException;
import in.ecgc.smile.erp.accounts.exception.CalendarRecordNotFoundException;
import in.ecgc.smile.erp.accounts.exception.CalendarRecordupdateException;
import in.ecgc.smile.erp.accounts.exception.ConfiguredClosingException;
import in.ecgc.smile.erp.accounts.exception.ConfiguredOpenException;
import in.ecgc.smile.erp.accounts.exception.PreviousMonthopeningException;
import in.ecgc.smile.erp.accounts.exception.RegularClosingException;
import in.ecgc.smile.erp.accounts.exception.RegularOpeningException;
import in.ecgc.smile.erp.accounts.model.Calendar;
import in.ecgc.smile.erp.accounts.model.CalendarMstAnnual;
import in.ecgc.smile.erp.accounts.model.CalendarRequestModel;
import in.ecgc.smile.erp.accounts.util.CalendarSqlQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public class CalendarDaoImpl implements CalendarDao {

	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(CalendarDaoImpl.class);

	@Autowired
	public CalendarDaoImpl(DataSource dataSource) {
		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	UserInfoService userInfoService;

	@Override
	public Integer addCalendar(Calendar calendar) {
		LOGGER.info("inside CalendarDaoImpl  -  addCalendar ");
		calendar.setEcgcStatus('V');
		Map<String, Object> calendarNamedParameters = new HashMap<String, Object>();
		calendarNamedParameters.put("calendarID", calendar.getCalendarId());
//		calendarNamedParameters.put("branchCd", calendar.getBranchCode());
		calendarNamedParameters.put("logicalLocCd", calendar.getLogicalLocCode());
		calendarNamedParameters.put("glTxnType", calendar.getGlTxnType());
		calendarNamedParameters.put("txnTypeName", calendar.getTxnTypeName());
		calendarNamedParameters.put("fiscalYear", calendar.getFiscalYear());
		calendarNamedParameters.put("month", calendar.getMonth());
		calendarNamedParameters.put("closedStatus", "y");
		calendarNamedParameters.put("ecgcStatus", calendar.getEcgcStatus());
		calendarNamedParameters.put("updatedBy", calendar.getUpdatedBy());
		calendarNamedParameters.put("updatedOn", calendar.getUpdatedOn());
		calendarNamedParameters.put("ecgcStatus", calendar.getEcgcStatus());
		calendarNamedParameters.put("metaRemarks", calendar.getMetaRemarks());
		calendarNamedParameters.put("createdOn", calendar.getCreatedOn());
		calendarNamedParameters.put("createdBy", calendar.getCreatedBy());

		int rowCount;
		try {
			rowCount = namedParameterJdbcTemplate.update(CalendarSqlQueries.ADD_CALENDAR, calendarNamedParameters);
		} catch (Exception e) {
			log.error("Failed to insert calendar record");
			log.error("Exception in CalendarDaoImpl#addCalendar {}", e.fillInStackTrace());
			throw new CalendarRecordInsertException("Failed to insert calendar record" + e.getMessage());
		}

		if (rowCount == 1)
			return 1;
		return 0;

	}

	@Override
	public Calendar getCalendar(String calendarId) {
		LOGGER.info("inside CalendarDaoImpl  -  getCalendar ");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("calendarId", calendarId);

		Calendar calendar = null;
		try {
			calendar = namedParameterJdbcOperations.queryForObject(CalendarSqlQueries.GET_CALENDAR, paramMap,
					new RowMapper<Calendar>() {
						@Override
						public Calendar mapRow(ResultSet rs, int rowNum) throws SQLException {
							LOGGER.info("inside row Mapper");
							Calendar calendar = new Calendar();
							calendar.setCalendarId(rs.getString("calendar_id"));
//							calendar.setBranchCode(rs.getString("branch_cd"));
							calendar.setLogicalLocCode(rs.getString("logical_loc_cd"));
							calendar.setFiscalYear(rs.getString("fiscal_yr"));
							calendar.setMonth(rs.getString("month"));
							calendar.setUpdatedBy(rs.getString("last_updated_by"));
							calendar.setUpdatedOn(rs.getTimestamp("last_updated_dt"));
							calendar.setGlTxnType(rs.getString("gl_txn_type"));
							calendar.setTxnTypeName(rs.getString("txn_type_name"));
							calendar.setConfigFlag(rs.getBoolean("config_flag"));
							if (rs.getString("closed_status") != null)
								calendar.setClosedStatus(rs.getString("closed_status").charAt(0));

							if (rs.getString("meta_status") != null)
								calendar.setEcgcStatus(rs.getString("meta_status").charAt(0));
							calendar.setMetaRemarks(rs.getString("meta_remarks"));
							calendar.setCreatedOn(rs.getTimestamp("created_dt"));
							calendar.setCreatedBy(rs.getString("created_by"));
							return calendar;
						}
					});
		} catch (Exception e) {
			log.error("Failed to fetch calendar record for calendar Id : {}", calendarId);
			log.error("Exception in CalendarDaoImpl#getCalendar {}", e.fillInStackTrace());
			throw new CalendarRecordNotFoundException(
					"Failed to fetch calendat record for calendar Id : " + calendarId + "" + e.getMessage());
		}

		if (calendar == null)
			return null;
		return calendar;

	}

	@Override
	public List<Calendar> getAllCalendar(String fiscalYr, String month) {
		LOGGER.info("inside CalendarDaoImpl  -  getAllCalendar ");
		List<Calendar> calendarList = new ArrayList<Calendar>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("fiscalYr", fiscalYr);
		paramMap.put("month", month);

		try {
			calendarList = namedParameterJdbcOperations.query(CalendarSqlQueries.GET_CALENDAR_FOR_MONTH, paramMap,

					new RowMapper<Calendar>() {

						@Override
						public Calendar mapRow(ResultSet rs, int rowNum) throws SQLException {

							Calendar calendar = new Calendar();
							calendar.setCalendarId(rs.getString("calendar_id"));
//							calendar.setBranchCode(rs.getString("branch_cd"));
							calendar.setLogicalLocCode(rs.getString("logical_loc_cd"));
							calendar.setFiscalYear(rs.getString("fiscal_yr"));
							calendar.setMonth(rs.getString("month"));
							calendar.setGlTxnType(rs.getString("gl_txn_type"));
							calendar.setTxnTypeName(rs.getString("txn_type_name"));
							calendar.setConfigFlag(rs.getBoolean("config_flag"));
							calendar.setUpdatedBy(rs.getString("last_updated_by"));
							calendar.setUpdatedOn(rs.getTimestamp("last_updated_dt"));
							if (rs.getString("closed_status") != null)
								calendar.setClosedStatus(rs.getString("closed_status").charAt(0));

							if (rs.getString("meta_status") != null)
								calendar.setEcgcStatus(rs.getString("meta_status").charAt(0));
							calendar.setMetaRemarks(rs.getString("meta_remarks"));
							calendar.setCreatedOn(rs.getTimestamp("created_dt"));
							calendar.setCreatedBy(rs.getString("created_by"));
							return calendar;
						}
					});
		} catch (Exception e) {
			log.error("Failed to fetch calendar record for FiscalYr : {} and Month : {}", fiscalYr, month);
			log.error("Exception in CalendarDaoImpl#getCalendar {}", e.fillInStackTrace());
			throw new CalendarRecordNotFoundException("Failed to fetch calendar record for FiscalYr : " + fiscalYr
					+ " and Month :" + month + "" + e.getMessage());
		}

		if (calendarList.isEmpty()) {
			log.error("Failed to fetch calendar record for FiscalYr : {} and Month : {}", fiscalYr, month);
			log.error("Exception in CalendarDaoImpl#getCalendar");
			throw new CalendarRecordNotFoundException(
					"Failed to fetch calendar record for FiscalYr : " + fiscalYr + " and Month :" + month);
		} else {
			return calendarList;
		}

	}

	@Override
	public Integer deleteCalendar(String calendarId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Calendar> getByFiscalYr(String fiscalYr, String month, String logicalLocCode) {
		LOGGER.info("inside CalendarDaoImpl  -  getByFiscalYr ");
		List<Calendar> calendarList;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("fiscalYr", fiscalYr);
		paramMap.put("month", month);
		paramMap.put("logicalLocCode", logicalLocCode);

		LOGGER.info("before get Calendar");

		try {
			calendarList = namedParameterJdbcTemplate.query(CalendarSqlQueries.GET_CALENDAR_FOR_MONTH_LOGICAL_LOC,
					paramMap,

					new RowMapper<Calendar>() {
						@Override
						public Calendar mapRow(ResultSet rs, int rowNum) throws SQLException {

							Calendar calendar = new Calendar();

							calendar.setCalendarId(rs.getString("calendar_id"));
//							calendar.setBranchCode(rs.getString("branch_cd"));
							calendar.setLogicalLocCode(rs.getString("logical_loc_cd"));
							calendar.setFiscalYear(rs.getString("fiscal_yr"));
							calendar.setMonth(rs.getString("month"));
							calendar.setGlTxnType(rs.getString("gl_txn_type"));
							calendar.setTxnTypeName(rs.getString("txn_type_name"));
							calendar.setConfigFlag(rs.getBoolean("config_flag"));
							calendar.setUpdatedBy(rs.getString("last_updated_by"));
							calendar.setUpdatedOn(rs.getTimestamp("last_updated_dt"));
							if (rs.getString("closed_status") != null)
								calendar.setClosedStatus(rs.getString("closed_status").charAt(0));

							if (rs.getString("meta_status") != null)
								calendar.setEcgcStatus(rs.getString("meta_status").charAt(0));
							calendar.setMetaRemarks(rs.getString("meta_remarks"));
							calendar.setCreatedOn(rs.getTimestamp("created_dt"));
							calendar.setCreatedBy(rs.getString("created_by"));
							return calendar;
						}
					});
		} catch (Exception e) {
			log.error("Failed to fetch calendar record for FiscalYr : {} and Month : {}, and logicalLocCd : {} ",
					fiscalYr, month, logicalLocCode);
			log.error("Exception in CalendarDaoImpl#getByFiscalYr {}", e.fillInStackTrace());
			throw new CalendarRecordNotFoundException("Failed to fetch calendar record for FiscalYr : " + fiscalYr
					+ " and Month :" + month + " and logicalLocCd : " + logicalLocCode + "" + e.getMessage());
		}
		if (calendarList.isEmpty()) {
			log.error("Failed to fetch calendar record for FiscalYr : {} and Month : {}, and logicalLocCd : {} ",
					fiscalYr, month, logicalLocCode);
			log.error("Exception in CalendarDaoImpl#getByFiscalYr");
			throw new CalendarRecordNotFoundException("Failed to fetch calendar record for FiscalYr : " + fiscalYr
					+ " and Month :" + month + " and logicalLocCd : " + logicalLocCode);

		} else {
			return calendarList;
		}

	}

	@Override
	public List<Calendar> getPrevFiscalYr(String fiscalYr, String logicalLocCode) {
		return null;
	}

	@Override
	public Integer updateStatus1(String first, String status) {
		LOGGER.info("inside CalendarDaoImpl  -  updateStatus1 ");
		Integer count = 0;

		Map<String, String> updateParam = new HashMap<>();
		updateParam.put("first", first);
		updateParam.put("status", status);

		LOGGER.info("Inside dao1  First :{} status :{}",first,status);

		try {
			count = namedParameterJdbcTemplate.update(CalendarSqlQueries.Update_Status_1, updateParam);
		} catch (Exception e) {
			log.error("Failed to update calendar status for Id : {} ", first);
			log.error("Exception in CalendarDaoImpl#updateStatus1 {}", e.fillInStackTrace());
			throw new CalendarRecordupdateException(
					"Failed to update calendar status for Id : " + first + "" + e.getMessage());
		}

		return count;
	}

	@Override
	public Integer updateStatus2(String second, String status) {
		LOGGER.info("inside CalendarDaoImpl  -  updateStatus2 ");
		Integer count = 0;

		Map<String, String> updateParam = new HashMap<>();
		updateParam.put("second", second);
		updateParam.put("status", status);

		try {
			count = namedParameterJdbcTemplate.update(CalendarSqlQueries.Update_Status_2, updateParam);
		} catch (Exception e) {
			log.error("Failed to update calendar status for Id : {} ", second);
			log.error("Exception in CalendarDaoImpl#updateStatus2 {}", e.fillInStackTrace());
			throw new CalendarRecordupdateException(
					"Failed to update calendar status for Id : " + second + "" + e.getMessage());
		}

		return count;
	}

	@Override
	public Integer updatePrev(String prevyr, String status) {
		LOGGER.info("inside CalendarDaoImpl  -  updatePrev ");
		Integer count = 0;

		Map<String, String> updateParam = new HashMap<>();
		updateParam.put("prevyr", prevyr);
		updateParam.put("status", status);

		try {
			count = namedParameterJdbcTemplate.update(CalendarSqlQueries.Update_last_yr, updateParam);
		} catch (Exception e) {
			log.error("Failed to update calendar status for Id : {} ", prevyr);
			log.error("Exception in CalendarDaoImpl#updatePrev {}", e.fillInStackTrace());
			throw new CalendarRecordupdateException(
					"Failed to update calendar status for Id : " + prevyr + "" + e.getMessage());
		}

		return count;
	}

	@Override
	public Integer openPrev(String logicalLoc, String glTxnType, String status, String month, String fiscalYr) {
		LOGGER.info("inside CalendarDaoImpl  -  openPrev ");
		Integer count = 0;
		Map<String, String> updateParam = new HashMap<>();
		updateParam.put("glTxnType", glTxnType);
		updateParam.put("month", month);
		updateParam.put("fiscalYr", fiscalYr);
		updateParam.put("logicalLoc", logicalLoc);
		updateParam.put("status", status);

		try {
			count = namedParameterJdbcTemplate.update(CalendarSqlQueries.Update, updateParam);
		} catch (Exception e) {
			log.error(
					"Failed to open previous month calendar for logicalLoc: {}, glTranxType : {}, status : {}, month : {}, year : {} ",
					logicalLoc, glTxnType, status, month, fiscalYr);
			log.error("Exception in CalendarDaoImpl#openPrev {}", e.fillInStackTrace());
			throw new PreviousMonthopeningException("Failed to open previous month calendar for logicalLoc: "
					+ logicalLoc + ", glTranxType : " + glTxnType + ", status : " + status + ", month : " + month
					+ ", year : " + fiscalYr + " " + e.getMessage());
		}
		return count;
	}

	@Override
	public Integer monthlyOpeningRegular(String logicalCode, String currMonth, String currentFiscalyr) {
		LOGGER.info("inside CalendarDaoImpl  -  monthlyOpeningRegular ");
		Map<String, Object> FTRNamedParameters = new HashMap<String, Object>();
		FTRNamedParameters.put("logicalCode", logicalCode);
		FTRNamedParameters.put("currentMonth", currMonth);
		FTRNamedParameters.put("currentFiscalYr", currentFiscalyr);

		Integer result;
		try {
			result = namedParameterJdbcOperations.queryForObject(CalendarSqlQueries.MONTHLY_OPENING_REGULAR,
					FTRNamedParameters, new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});
		} catch (Exception e) {
			log.error("Failed to open regular month calendar for logicalLoc: {}, month : {}, year : {} ", logicalCode,
					currMonth, currentFiscalyr);
			log.error("Exception in CalendarDaoImpl#monthlyOpeningRegular {}", e.fillInStackTrace());
			throw new RegularOpeningException("Failed to open regular month calendar for logicalLoc: " + logicalCode
					+ ",  month : " + currMonth + ", year : " + currentFiscalyr + " " + e.getMessage());
		}
		return result;
	}

	@Override
	public Integer monthlyClosingRegular(String logicalCode, String prevMonth, String currentFiscalyr) {
		LOGGER.info("inside CalendarDaoImpl  -  monthlyClosingRegular ");
		Map<String, Object> FTRNamedParameters = new HashMap<String, Object>();
		FTRNamedParameters.put("logicalCode", logicalCode);
		FTRNamedParameters.put("prevMonth", prevMonth);
		FTRNamedParameters.put("currentFiscalYr", currentFiscalyr);

		Integer result;
		try {
			result = namedParameterJdbcOperations.queryForObject(CalendarSqlQueries.MONTHLY_CLOSING_REGULAR,
					FTRNamedParameters, new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});
		} catch (Exception e) {
			log.error("Failed to close regular month calendar for logicalLoc: {}, month : {}, year : {} ", logicalCode,
					prevMonth, currentFiscalyr);
			log.error("Exception in CalendarDaoImpl#monthlyClosingRegular {}", e.fillInStackTrace());
			throw new RegularClosingException("Failed to close regular month calendar for logicalLoc: " + logicalCode
					+ ",  month : " + prevMonth + ", year : " + currentFiscalyr + " " + e.getMessage());
		}
		return result;

	}

	@Override
	public Integer monthlyOpeningConfigured(String logicalCode, String currMonth, String currentFiscalyr) {
		LOGGER.info("inside CalendarDaoImpl  -  monthlyOpeningConfigured ");
		Map<String, Object> FTRNamedParameters = new HashMap<String, Object>();
		FTRNamedParameters.put("logicalCode", logicalCode);
		FTRNamedParameters.put("currentMonth", currMonth);
		FTRNamedParameters.put("currentFiscalYr", currentFiscalyr);

		Integer result;
		try {
			result = namedParameterJdbcOperations.queryForObject(CalendarSqlQueries.MONTHLY_OPENING_CONFIGURED,
					FTRNamedParameters, new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});
		} catch (Exception e) {
			log.error("Failed to open configured month calendar for logicalLoc: {}, month : {}, year : {} ",
					logicalCode, currMonth, currentFiscalyr);
			log.error("Exception in CalendarDaoImpl#monthlyOpeningConfigured {}", e.fillInStackTrace());
			throw new ConfiguredOpenException("Failed to open configured month calendar for logicalLoc: " + logicalCode
					+ ",  month : " + currMonth + ", year : " + currentFiscalyr + " " + e.getMessage());
		}
		return result;
	}

	@Override
	public Integer monthlyClosingConfigured(String logicalCode, String prevMonth, String currentFiscalyr) {
		LOGGER.info("inside CalendarDaoImpl  -  monthlyClosingConfigured ");
		Map<String, Object> FTRNamedParameters = new HashMap<String, Object>();
		FTRNamedParameters.put("logicalCode", logicalCode);
		FTRNamedParameters.put("prevMonth", prevMonth);
		FTRNamedParameters.put("currentFiscalYr", currentFiscalyr);

		Integer result;
		try {
			result = namedParameterJdbcOperations.queryForObject(CalendarSqlQueries.MONTHLY_CLOSING_CONFIGURED,
					FTRNamedParameters, new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});
		} catch (Exception e) {
			log.error("Failed to close configured month calendar for logicalLoc: {}, month : {}, year : {} ",
					logicalCode, prevMonth, currentFiscalyr);
			log.error("Exception in CalendarDaoImpl#monthlyClosingRegular {}", e.fillInStackTrace());
			throw new ConfiguredClosingException("Failed to close configured month calendar for logicalLoc: "
					+ logicalCode + ",  month : " + prevMonth + ", year : " + currentFiscalyr + " " + e.getMessage());
		}
		return result;

	}

	@Override
	public Integer dailyClosing(String currMonth, String currentFiscalyr, String logicalCode) {
		LOGGER.info("inside CalendarDaoImpl  -  dailyClosing ");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("currentMonth", currMonth);
		parameters.put("currentFiscalyr", currentFiscalyr);
		parameters.put("logicalCode", logicalCode);

		Integer result;
		try {
			result = namedParameterJdbcOperations.queryForObject(CalendarSqlQueries.MONTHLY_CLOSING_CONFIGURED,
					parameters, new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});
		} catch (Exception e) {
			log.error("Failed to close configured month calendar for logicalLoc: {}, month : {}, year : {} ",
					logicalCode, currMonth, currentFiscalyr);
			log.error("Exception in CalendarDaoImpl#dailyClosing {}", e.fillInStackTrace());
			throw new ConfiguredClosingException("Failed to close configured month calendar for logicalLoc: "
					+ logicalCode + ",  month : " + currMonth + ", year : " + currentFiscalyr + " " + e.getMessage());
		}
		return result;
	}

	@Override
	public Integer dailyOpening(String currMonth, String currentFiscalyr, String logicalCode) {
		LOGGER.info("inside CalendarDaoImpl  -  dailyOpening ");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("currentMonth", currMonth);
		parameters.put("currentFiscalyr", currentFiscalyr);
		parameters.put("logicalCode", logicalCode);
		Integer result;
		try {
			result = namedParameterJdbcOperations.queryForObject(CalendarSqlQueries.OPENING, parameters,
					new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});
		} catch (Exception e) {
			log.error("Failed to open configured month calendar for logicalLoc: {}, month : {}, year : {} ",
					logicalCode, currMonth, currentFiscalyr);
			log.error("Exception in CalendarDaoImpl#dailyOpening {}", e.fillInStackTrace());
			throw new ConfiguredOpenException("Failed to open configured month calendar for logicalLoc: " + logicalCode
					+ ",  month : " + currMonth + ", year : " + currentFiscalyr + " " + e.getMessage());
		}
		return result;
	}

	@Override
	public Calendar getByGlTypeLogicalLoc(String fiscalYr, String month, String logicalLocCode, String glTxnType) {
		log.info("Inside CalendarDaoImpl#getByGlTypeLogicalLoc");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("fiscalYr", fiscalYr);
		paramMap.put("month", month);
		paramMap.put("logicalLocCode", logicalLocCode);
		paramMap.put("glTxnType", glTxnType);

		Calendar calendar = new Calendar();
		try {
			calendar = namedParameterJdbcOperations.queryForObject(CalendarSqlQueries.GET_CALENDAR_BY_GL_MON_LOGICALLOC,
					paramMap, new RowMapper<Calendar>() {

						@Override
						public Calendar mapRow(ResultSet rs, int rowNum) throws SQLException {
							LOGGER.info("inside row Mapper");
							Calendar calendar = new Calendar();
							calendar.setCalendarId(rs.getString("calendar_id"));
//							calendar.setBranchCode(rs.getString("branch_cd"));
							calendar.setLogicalLocCode(rs.getString("logical_loc_cd"));
							calendar.setFiscalYear(rs.getString("fiscal_yr"));
							calendar.setMonth(rs.getString("month"));
							calendar.setUpdatedBy(rs.getString("last_updated_by"));
							calendar.setUpdatedOn(rs.getTimestamp("last_updated_dt"));
							calendar.setGlTxnType(rs.getString("gl_txn_type"));
							calendar.setTxnTypeName(rs.getString("txn_type_name"));
							if (rs.getString("config_flag") != null)
								calendar.setConfigFlag(rs.getBoolean("config_flag"));
							if (rs.getString("closed_status") != null)
								calendar.setClosedStatus(rs.getString("closed_status").charAt(0));

							if (rs.getString("meta_status") != null)
								calendar.setEcgcStatus(rs.getString("meta_status").charAt(0));
							calendar.setMetaRemarks(rs.getString("meta_remarks"));
							calendar.setCreatedOn(rs.getTimestamp("created_dt"));
							calendar.setCreatedBy(rs.getString("created_by"));
							return calendar;
						}
					});
		} catch (Exception e) {
			// calendar =null;
			log.info(
					"Failed to fetch calendar record for FiscalYr : {} and Month : {}, and logicalLocCd : {}, and GlTxnType ",
					fiscalYr, month, logicalLocCode, glTxnType);
			log.info("Exception in CalendarDaoImpl#getByGlTypeLogicalLoc {}", e.fillInStackTrace());
			log.error("calander in exception is :: {} ", calendar);
			throw new CalendarRecordNotFoundException("Failed to fetch calendar record for FiscalYr : " + fiscalYr
					+ " and Month :" + month + " and logicalLocCd : " + logicalLocCode + " and Gl TXN type : "
					+ glTxnType + " " + e.getMessage());
		}

		if (calendar == null)
			return null;
		return calendar;

	}

	@Override
	public Integer marchClosing(String logicalCode, String currentFiscalyr) {
		LOGGER.info("inside CalendarDaoImpl  -  marchClosing ");
		LOGGER.info("Dao Parameter logicalCode {}   ", logicalCode);
		LOGGER.info("Dao Parameter currentFiscalyr {}   ", currentFiscalyr);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("logicalLocCd", logicalCode);
		paraMap.put("fiscalYear", currentFiscalyr);
		paraMap.put("lastupdatedBy", userInfoService.getUser());
//		paraMap.put("lastupdatedBy", "1229");
		Integer result = null;
		try {
			result = namedParameterJdbcOperations.queryForObject(CalendarSqlQueries.CLOSE_MARCH_CALENDAR, paraMap,
					new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});

		} catch (Exception e) {
			log.error("Failed to close march calendar for logicalLoc: {}, year : {} ", logicalCode, currentFiscalyr);
			log.error("Exception in CalendarDaoImpl#monthlyClosingRegular {}", e.fillInStackTrace());
			throw new RegularClosingException("Failed to close march calendar for logicalLoc: " + logicalCode
					+ ",  year : " + currentFiscalyr + " " + e.getMessage());
		}
		return result;

	}

	@Override
	public Boolean marchClosingStatus(String logicalCode, String currentFiscalyr) {
		LOGGER.info("inside CalendarDaoImpl  -  marchClosingStatus ");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fiscalYr", currentFiscalyr);
		parameters.put("logicalCode", logicalCode);

		try {
			namedParameterJdbcOperations.queryForObject(CalendarSqlQueries.GET_MARCH_CALENDAR_STATUS, parameters,
					new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});
		} catch (Exception e) {
			log.error("Failed to get march calendar status for logicalLoc: {},  year : {} ", logicalCode,
					currentFiscalyr);
			log.error("Exception in CalendarDaoImpl#marchClosingStatus {}", e.fillInStackTrace());
			// throw new ConfiguredClosingException("Failed to get march calendar status for
			// logicalLoc: "+logicalCode+", year : "+currentFiscalyr+" "+ e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public List<CalendarMstAnnual> getCalendarMstAnnualList(String fiscalYr, String logicalloc) // throws
																								// DataAccessException
	{
		LOGGER.info("inside CalendarDaoImpl  -  getCalendarMstAnnualList ");
		String SQLstr = "";
		String logical = logicalloc;
		String check = "ALL_LOGICAL_LOCATIONS";
		List<CalendarMstAnnual> callist = new ArrayList<>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fiscalYr", fiscalYr);
		try {
			if (logical.compareTo(check) == 0) {

				log.error("without location: {}", logical.compareTo(check));
				SQLstr = CalendarSqlQueries.ALL_MARCH_CLOSED_CALENDARS;
			} else {
				log.error("with location: {}", logical.compareTo(check));
				parameters.put("logicalloc", logicalloc);
				SQLstr = CalendarSqlQueries.ALL_MARCH_CLOSED_CALENDARS_LOC;
			}
			callist = namedParameterJdbcOperations.query(SQLstr, parameters, new RowMapper<CalendarMstAnnual>() {
				@Override
				public CalendarMstAnnual mapRow(ResultSet rs, int rowNum) throws SQLException {
					CalendarMstAnnual calendarMstAnnual = new CalendarMstAnnual();
					calendarMstAnnual.setCalendarAnnualId(rs.getString("calendar_annual_id"));
					calendarMstAnnual.setLogicalLocCd(rs.getString("logical_loc_cd"));
					calendarMstAnnual.setFiscalYr(rs.getString("fiscal_yr"));
					calendarMstAnnual.setMonth(rs.getString("month"));
					calendarMstAnnual.setUserId(rs.getString("user_id"));
					calendarMstAnnual.setClosedDt(rs.getDate("closed_dt"));
					log.error("fetch march closed calendar record DATEEEEE {}", calendarMstAnnual.getClosedDt());
					calendarMstAnnual.setLastTransDt(rs.getDate("last_trans_dt"));
					return calendarMstAnnual;
				}
			});

		} catch (Exception e) {
			log.error("Failed to fetch march closed calendar record");
			log.error("Exception in CalendarDaoImpl#getCalendarMstAnnualList {}", e.fillInStackTrace());
			throw new CalendarRecordNotFoundException("Failed to fetch march closed calendar record" + e.getMessage());
		}
		return callist;
	}

	@Override
	public Integer DailOpeningClosingAuto(Integer currentDay, String currentMonth, String prevMonth,
			String currFiscalYear) {
		LOGGER.info("inside CalendarDaoImpl  -  DailOpeningClosingAuto ");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("currentDay", currentDay);
		parameters.put("currentMonth", currentMonth);
		parameters.put("prevMonth", prevMonth);
		parameters.put("currentFiscalyr", currFiscalYear);
		Integer result = 0;
		try {

			result = namedParameterJdbcOperations.queryForObject(CalendarSqlQueries.DailyCloseOpenAuto, parameters,
					new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});
		} catch (Exception e) {
			log.error("Failed to open daily calendar for month : {}, year : {} ", currentMonth, currFiscalYear);
			log.error("Exception in CalendarDaoImpl#dailyOpening {}", e.fillInStackTrace());
			throw new ConfiguredOpenException("Failed to open daily calendar for month : " + currentMonth + ", year : "
					+ currFiscalYear + " " + e.getMessage());
		}
		return result;

	}

	@Override
	public Integer DailyCalendarRequestClosingAuto() {
		log.info("Inside CalendarDaoImpl#DailyCalendarRequestClosingAuto");
		Map<String, Object> parameters = new HashMap<String, Object>();
		Date currdt = new Date();
		log.info("Current Date {}", currdt);
		parameters.put("currdt", currdt);
		Integer result = 0;
		try {

			result = namedParameterJdbcOperations.queryForObject(CalendarSqlQueries.Daily_Cal_Req_Closing_Auto,
					parameters, new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});
			return result;
		} catch (Exception e) {
			log.error("Exception in CalendarDaoImpl#DailyCalendarRequestClosingAuto {}", e.fillInStackTrace());
			throw new ConfiguredOpenException("Failed to close daily calendar requests.");
		}

	}
	
	@Override
	public List<CalendarRequestModel> ReqClosedStat(String logicalCode, String currentFiscalyr) {
		log.info("Inside CalendarDaoImpl#ReqClosedStat");
		List<CalendarRequestModel> callist = new ArrayList<>();
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fiscalYr", currentFiscalyr);
			parameters.put("logicalCode", logicalCode);
			Date currdt = new Date(new Date().getTime() - 24 * 3600 * 1000);
			parameters.put("currdt", currdt);

			callist = namedParameterJdbcOperations.query(CalendarSqlQueries.ALL_CLOSED_CAL_REQS, parameters,
					new RowMapper<CalendarRequestModel>() {
						@Override
						public CalendarRequestModel mapRow(ResultSet rs, int rowNum) throws SQLException {
							CalendarRequestModel calendarReq = new CalendarRequestModel();
							calendarReq.setRequestId(rs.getInt("request_id"));
							calendarReq.setRequestedBy(rs.getInt("requested_by"));
							calendarReq.setApprovedBy(rs.getInt("approved_by"));
							calendarReq.setFiscalYr(rs.getString("fiscal_yr"));
							calendarReq.setMonth(rs.getString("month"));
							calendarReq.setGlTxnType(rs.getString("gl_txn_type"));
							calendarReq.setRequestStatus(rs.getString("status"));
							calendarReq.setRemark(rs.getString("remark"));
							calendarReq.setEcgcStatus(rs.getString("meta_status").charAt(0));
							calendarReq.setCreatedOn(rs.getDate("created_dt"));
							calendarReq.setUpdatedOn(rs.getDate("last_updated_dt"));
							calendarReq.setLogicalLocCode(rs.getString("logical_loc_cd"));
							calendarReq.setMetaRemarks(rs.getString("meta_remarks"));
							calendarReq.setBranchCode(rs.getString("branch_cd"));
							calendarReq.setCalendarId(rs.getString("calendar_id"));
							return calendarReq;
						}
					});
			return callist;

		} catch (Exception e) {
			log.error("Exception in CalendarDaoImpl#ReqClosedStat {}", e.fillInStackTrace());
			return null;
		}
	}
	
	@Override
	public List<CalendarRequestModel> ReqOpenedStat(String logicalCode, String currentFiscalyr) {
		log.info("Inside CalendarDaoImpl#ReqOpenedStat");
		List<CalendarRequestModel> callist = new ArrayList<>();
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fiscalYr", currentFiscalyr);
			parameters.put("logicalCode", logicalCode);
			//Date currdt = new Date(new Date().getTime() - 24 * 3600 * 1000);
			//parameters.put("currdt", new Date());

			callist = namedParameterJdbcOperations.query(CalendarSqlQueries.ALL_OPENED_CAL_REQS, parameters,
					new RowMapper<CalendarRequestModel>() {
						@Override
						public CalendarRequestModel mapRow(ResultSet rs, int rowNum) throws SQLException {
							CalendarRequestModel calendarReq = new CalendarRequestModel();
							calendarReq.setRequestId(rs.getInt("request_id"));
							calendarReq.setRequestedBy(rs.getInt("requested_by"));
							calendarReq.setApprovedBy(rs.getInt("approved_by"));
							calendarReq.setFiscalYr(rs.getString("fiscal_yr"));
							calendarReq.setMonth(rs.getString("month"));
							calendarReq.setGlTxnType(rs.getString("gl_txn_type"));
							calendarReq.setRequestStatus(rs.getString("status"));
							calendarReq.setRemark(rs.getString("remark"));
							calendarReq.setEcgcStatus(rs.getString("meta_status").charAt(0));
							calendarReq.setCreatedOn(rs.getDate("created_dt"));
							calendarReq.setUpdatedOn(rs.getDate("last_updated_dt"));
							calendarReq.setLogicalLocCode(rs.getString("logical_loc_cd"));
							calendarReq.setMetaRemarks(rs.getString("meta_remarks"));
							calendarReq.setBranchCode(rs.getString("branch_cd"));
							calendarReq.setCalendarId(rs.getString("calendar_id"));
							return calendarReq;
						}
					});
			return callist;

		} catch (Exception e) {
			log.error("Exception in CalendarDaoImpl#ReqOpenedStat {}", e.fillInStackTrace());
			return null;
		}
	}

	@Override
	public List<CalendarRequestModel> OpenedRequests() {
		log.info("Inside CalendarDaoImpl#OpenedRequests");
		List<CalendarRequestModel> callist = new ArrayList<>();
		try {
			//Map<String, Object> parameters = new HashMap<String, Object>();
			//Date currdt = new Date(new Date().getTime() - 24 * 3600 * 1000);
			//parameters.put("currdt", new Date());

			callist = namedParameterJdbcOperations.query(CalendarSqlQueries.OPENED_CAL_REQS,
					new RowMapper<CalendarRequestModel>() {
						@Override
						public CalendarRequestModel mapRow(ResultSet rs, int rowNum) throws SQLException {
							CalendarRequestModel calendarReq = new CalendarRequestModel();
							calendarReq.setRequestId(rs.getInt("request_id"));
							calendarReq.setRequestedBy(rs.getInt("requested_by"));
							calendarReq.setApprovedBy(rs.getInt("approved_by"));
							calendarReq.setFiscalYr(rs.getString("fiscal_yr"));
							calendarReq.setMonth(rs.getString("month"));
							calendarReq.setGlTxnType(rs.getString("gl_txn_type"));
							calendarReq.setRequestStatus(rs.getString("status"));
							calendarReq.setRemark(rs.getString("remark"));
							calendarReq.setEcgcStatus(rs.getString("meta_status").charAt(0));
							calendarReq.setCreatedOn(rs.getDate("created_dt"));
							calendarReq.setUpdatedOn(rs.getDate("last_updated_dt"));
							calendarReq.setLogicalLocCode(rs.getString("logical_loc_cd"));
							calendarReq.setMetaRemarks(rs.getString("meta_remarks"));
							calendarReq.setBranchCode(rs.getString("branch_cd"));
							calendarReq.setCalendarId(rs.getString("calendar_id"));
							return calendarReq;
						}
					});
			return callist;

		} catch (Exception e) {
			log.error("Exception in CalendarDaoImpl#OpenedRequests {}", e.fillInStackTrace());
			return null;
		}
	}
	
	@Override
	public Integer updateReqStat(String calid) {
		log.info("Inside CalendarDaoImpl#updateReqStat");
		Integer count = 0;

		Map<String, String> updateParam = new HashMap<>();
		updateParam.put("calid", calid);


		try {
			count = namedParameterJdbcTemplate.update(CalendarSqlQueries.UPDATE_REQ_STATUS, updateParam);
		} catch (Exception e) {
			log.error("Failed to update calendar status for Id : {} ", calid);
			log.error("Exception in CalendarDaoImpl#updateReqStat {}", e.fillInStackTrace());
			throw new CalendarRecordupdateException(
					"Failed to update calendar status for Id : " + calid + "" + e.getMessage());
		}

		return count;
	}


}

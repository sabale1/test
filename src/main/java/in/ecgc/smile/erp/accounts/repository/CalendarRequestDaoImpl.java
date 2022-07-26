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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.model.CalendarRequestModel;
import in.ecgc.smile.erp.accounts.util.CalendarRequestQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;


@Repository
@Transactional
public class CalendarRequestDaoImpl implements CalendarRequestDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CalendarRequestDaoImpl.class);

	@Autowired
	UserInfoService userInfoService;
	
	 @Autowired
	  private JdbcOperations jdbcOperations; 
	  @Autowired
	  JdbcTemplate jdbcTemplate;
	  private NamedParameterJdbcTemplate namedParameterJdbcOperations; 
	  @Autowired
	  NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	  
	  @Autowired 
	  public CalendarRequestDaoImpl(DataSource dataSource) { 
		  jdbcTemplate = new JdbcTemplate(dataSource); 
	  namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource); 
	  namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource); 
	  }
	  
	  public Map<String,Object> ObjectMapping(CalendarRequestModel calendarReq){
		
		  Map<String,Object> parameterMapping = new HashMap<String,Object>();

		  parameterMapping.put("requestId", calendarReq.getRequestId());
		  parameterMapping.put("calendarId", calendarReq.getCalendarId());
		  parameterMapping.put("requestedBy", calendarReq.getRequestedBy());
		  parameterMapping.put("approvedBy", calendarReq.getApprovedBy());
		  parameterMapping.put("fiscalYr", calendarReq.getFiscalYr());
		  parameterMapping.put("month", calendarReq.getMonth());
		  parameterMapping.put("glTxnType", calendarReq.getGlTxnType());
		  parameterMapping.put("status", "P");
		  parameterMapping.put("remark", calendarReq.getRemark());
		  parameterMapping.put("ecgcStatus", 'V');
		  parameterMapping.put("createdOn", calendarReq.getCreatedOn());
		  parameterMapping.put("updatedOn", calendarReq.getUpdatedOn());
		  parameterMapping.put("logicalLocCode", calendarReq.getLogicalLocCode());
		  parameterMapping.put("metaRemarks", calendarReq.getMetaRemarks());
		  parameterMapping.put("branchCode", calendarReq.getBranchCode());
		  parameterMapping.put("validityDt", calendarReq.getValidityDt());
		  return parameterMapping;
		  
	  }
	  
	  public CalendarRequestModel setValueToObject(ResultSet rs,CalendarRequestModel calendarReq) 
	    		throws SQLException{
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

	@Override
	public Integer generateRequest(CalendarRequestModel requestModel) {
		LOGGER.info("inside CalendarRequestDaoImpl  -  generateRequest ");
		try {
			return namedParameterJdbcTemplate.update(CalendarRequestQueries.GENERATE_CALENDAR_REQUEST, 
					   ObjectMapping(requestModel));
		} catch (Exception e) {
			LOGGER.info("Exception in CalendarRequestDaoImpl#generateRequest : {}",e.getMessage());
			return 0;
		}
	}

	@Override
	public CalendarRequestModel updateCalendarRequest(Integer reqId, CalendarRequestModel updateModel) {
		LOGGER.info("inside CalendarRequestDaoImpl  -  updateCalendarRequest ");
		Map<String, Object> parameterMapping = new HashMap<String, Object>();
		LOGGER.info("updates value {}",updateModel);
		  parameterMapping.put("requestId", updateModel.getRequestId());
		  parameterMapping.put("requestedBy", updateModel.getRequestedBy());
		  parameterMapping.put("approvedBy", updateModel.getApprovedBy());
		  parameterMapping.put("fiscalYr", updateModel.getFiscalYr());
		  parameterMapping.put("month", updateModel.getMonth());
		  parameterMapping.put("glTxnType", updateModel.getGlTxnType());
		  parameterMapping.put("status", updateModel.getRequestStatus());
		  parameterMapping.put("remark", updateModel.getRemark());
		  parameterMapping.put("updatedOn", updateModel.getUpdatedOn());
		  parameterMapping.put("logicalLocCode", updateModel.getLogicalLocCode());
		  parameterMapping.put("metaRemarks", updateModel.getMetaRemarks());
		  parameterMapping.put("branchCode", updateModel.getBranchCode());
		  
		  int rowCount = 0;
			try {
				rowCount = namedParameterJdbcTemplate.update(CalendarRequestQueries.UPDATE_CALENDAR_REQUEST, parameterMapping);
			} catch (Exception e) {
				LOGGER.info("Exception in : CalendarRequestDaoImpl#updateCalendarRequest : {}",e.getMessage());
			}
			if (rowCount == 1)
				return updateModel;
			else 
				return null;
	}

	@Override
	public List<CalendarRequestModel> viewAllRequest() {
		LOGGER.info("inside CalendarRequestDaoImpl  -  viewAllRequest ");
		List<CalendarRequestModel> calendarParameter = jdbcOperations.query(CalendarRequestQueries.VIEW_CALENDAR_REQUEST, 
				new ResultSetExtractor<List<CalendarRequestModel>>() {

					@Override
					public List<CalendarRequestModel> extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						List<CalendarRequestModel> listofRequest = new ArrayList<CalendarRequestModel>();
						while(rs.next())
						{
							CalendarRequestModel calendarReq = new CalendarRequestModel();
							listofRequest.add(setValueToObject(rs, calendarReq));
						}
					    return listofRequest;
					}
				});
		if (calendarParameter.isEmpty()) {
			return null;
		}else {
			return calendarParameter;	
		}
	}

	@Override
	public CalendarRequestModel viewRequestById(Integer reqId) {
		LOGGER.info("inside CalendarRequestDaoImpl  -  viewRequestById ");
		CalendarRequestModel calendarModel;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("requestId", reqId);
		try {
			calendarModel = namedParameterJdbcOperations.queryForObject(CalendarRequestQueries.VIEW_CALENDAR_REQUEST_BY_ID, paramMap,
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
							  calendarReq.setValidityDt(rs.getDate("validity_dt"));
							  return calendarReq;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			calendarModel = null;
		}
		return calendarModel;
		
	}
	
	@Override
	public Integer decisionOnRequest(CalendarRequestModel requestModel) {
		LOGGER.info("inside CalendarRequestDaoImpl  -  decisionOnRequest ");
		Map<String, Object> parameterMapping = new HashMap<String, Object>();
		LOGGER.info("Updated Value {}",requestModel);
			Date nextdt = new Date();
			nextdt.setDate(nextdt.getDate()+1);
		  parameterMapping.put("requestId", requestModel.getRequestId());
		  parameterMapping.put("approvedBy", Integer.parseInt(userInfoService.getUser()));
		  parameterMapping.put("status", requestModel.getRequestStatus());
		  parameterMapping.put("remark", requestModel.getRemark());
		  parameterMapping.put("updatedOn", requestModel.getUpdatedOn());
		  parameterMapping.put("metaRemarks", requestModel.getMetaRemarks());
		  parameterMapping.put("validitydt", nextdt);
		  
		  int rowCount = 0;
		try {
			rowCount = namedParameterJdbcTemplate.update(CalendarRequestQueries.UPDATE_CALENDAR_REQUEST1, parameterMapping);
		} catch (Exception e) {
			LOGGER.info("Exception in : CalendarRequestDaoImpl#decisionOnRequest : {} ",e);

		}
			if (rowCount == 1)
				return rowCount;
			else 
				return null;

	}

	public Integer getSeq() {
		LOGGER.info("inside CalendarRequestDaoImpl  -  getSeq ");
		Map<String, Object> FTRNamedParameters = new HashMap<String, Object>();
		FTRNamedParameters.put("seq_coloumn","ecgc_acct_cal_req_seq");
		try {
			Integer seq = namedParameterJdbcOperations.queryForObject(CalendarRequestQueries.GET_SEQ,FTRNamedParameters,
					new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num =rs.getInt(1);
							return num;
						}
					});
			return seq;
		} catch (Exception e) {
			LOGGER.info("Exception in CalendarRequestDaoImpl#getSeq : {} ",e);
			return null;
		}

	}
}

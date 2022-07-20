package in.ecgc.smile.erp.accounts.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import in.ecgc.smile.erp.accounts.exception.DatabaseOperationFailException;
import in.ecgc.smile.erp.accounts.model.LOVMaster;
import in.ecgc.smile.erp.accounts.util.LOVQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class LOVMasterDaoImpl  implements LOVMasterDao{
	

	@Autowired 
	private UserInfoService userInfo;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcOperations jdbcOperations;
	private NamedParameterJdbcOperations namedParameterJdbcOperations;

	@Autowired
	public LOVMasterDaoImpl(DataSource dataSource) {
		jdbcOperations = new JdbcTemplate(dataSource);
		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Integer addLovMstEntry(LOVMaster lovmst) {
		log.info("Inside LOVMasterDaoImpl#addLovMstEntry");
		int result = 0;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lovCd", lovmst.getLovCd());
		paramMap.put("lovSubCd", lovmst.getLovSubCd());
		paramMap.put("lovValue", lovmst.getLovValue());
		paramMap.put("lovDesc", lovmst.getLovDesc());
		paramMap.put("createdBy", userInfo.getUser());
		paramMap.put("delFlag", lovmst.getDelFlag());
		try {
			result = namedParameterJdbcTemplate.update(LOVQueries.INSERT_LOV_MST_ENTRY_DETAILS, paramMap);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Failed to insert data in database");
		}catch(Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
		
		return result;
	}

	@Override
	public List<LOVMaster> viewallLOVMstEntries() {
		log.info("Inside LOVMasterDaoImpl#viewallLOVMstEntries");
		List<LOVMaster> lovMaster;
		try {
			lovMaster = jdbcOperations.query(LOVQueries.GET_ALL_LOV_MST_ENTRIES, new ResultSetExtractor<List<LOVMaster>>() {
				@Override
				public List<LOVMaster> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<LOVMaster> lovMaster = new ArrayList<LOVMaster>();
					while (rs.next()) {
						LOVMaster lovData = new LOVMaster();
						lovData.setLovCd(rs.getString("lov_cd"));
						lovData.setLovSubCd(rs.getString("lov_sub_cd"));
						lovData.setLovValue(rs.getString("lov_value"));
						lovData.setLovDesc(rs.getString("lov_desc"));
						lovData.setDelFlag(rs.getBoolean("del_flag"));
						lovMaster.add(lovData);
					}
					return lovMaster;
				}
			});
		}catch(DataAccessException e) {
			throw new DataAccessResourceFailureException("Failed to fetch data");
		}catch(Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
		
		return lovMaster;
	}

	@Override
	public LOVMaster viewLovEntry(String lovCd, String lovSubCd, String lovValue) {
		log.info("Inside LOVMasterDaoImpl#viewLovEntry");
		LOVMaster lovMaster;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("lovCd", lovCd);
		paramMap.put("lovSubCd", lovSubCd);
		paramMap.put("lovValue", lovValue);
		
		try {
			lovMaster = namedParameterJdbcOperations.queryForObject(LOVQueries.GET_LOV_MST_ENTRY, paramMap,
					new RowMapper<LOVMaster>() {

						@Override
						public LOVMaster mapRow(ResultSet rs, int rowNum) throws SQLException {
							LOVMaster lovMaster = new LOVMaster();

							lovMaster.setLovCd(rs.getString("lov_cd"));
							lovMaster.setLovSubCd(rs.getString("lov_sub_cd"));
							lovMaster.setLovValue(rs.getString("lov_value"));
							lovMaster.setLovDesc(rs.getString("lov_desc"));		
							lovMaster.setDelFlag(rs.getBoolean("del_flag"));
							return lovMaster;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			throw new DataAccessResourceFailureException("No record found for entered details");
		} catch(Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
		return lovMaster;
	}

	@Override
	public int modifyLovEntry(LOVMaster lov) {
		log.info("Inside LOVMasterDaoImpl#modifyLovEntry");
		int modifyQueryStatus = 0;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lovCd", lov.getLovCd());
		paramMap.put("lovSubCd", lov.getLovSubCd());
		paramMap.put("lovValue", lov.getLovValue());
		paramMap.put("lovDesc", lov.getLovDesc());
		paramMap.put("delFlag", lov.getDelFlag());
		paramMap.put("lastUpdatedBy",userInfo.getUser());
		try {
			modifyQueryStatus = namedParameterJdbcTemplate.update(LOVQueries.UPDATE_LOV_MST_ENTRY, paramMap);
			return modifyQueryStatus;
		}catch(DataIntegrityViolationException e) {
			throw new DataAccessResourceFailureException("Failed to modify the record");
		}catch(Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
		
	}

	@Override
	public Integer disableLovEntry(String lovCd, String lovSubCd, String lovValue) {
		log.info("Inside LOVMasterDaoImpl#disableLovEntry");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lovCd", lovCd);
		paramMap.put("lovSubCd", lovSubCd);
		paramMap.put("lovValue", lovValue);
		paramMap.put("lastUpdatedBy",userInfo.getUser());
		try {
			return namedParameterJdbcTemplate.update(LOVQueries.DISABLE_LOV_ENTRY, paramMap);
		}catch(DataIntegrityViolationException e) {
			throw new DataAccessResourceFailureException("Failed to Disable the LOV entry");
		}catch(Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
		
	}

	
	@Override
	public Map<String,String> getT1codes() {
		log.info("Inside LOVMasterDaoImpl#getT1codes");
		Map<String,String> t1Codes = new HashMap<>();

		try {
			t1Codes = namedParameterJdbcOperations.query(LOVQueries.GET_T1_CODE_LIST,
					(ResultSet rs)->{
						HashMap<String, String> t1 = new HashMap<>();
						
						  while(rs.next()){
							  t1.put(rs.getString("lov_desc"),rs.getString("lov_value"));
					        }
						return t1;
					});
			log.info("T1 code list is : {}" ,t1Codes);
		} catch (EmptyResultDataAccessException e) {
			throw new DataAccessResourceFailureException("T1 codes are empty");
		} catch (Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
		return t1Codes;
			
				
	}

	@Override
	public Map<String,String> getT2codes() {
		log.info("Inside LOVMasterDaoImpl#getT2codes");
		Map<String,String> t2Codes = new HashMap<>();

		try {
			t2Codes = namedParameterJdbcOperations.query(LOVQueries.GET_T2_CODE_LIST,
					(ResultSet rs)->{
						HashMap<String, String> t2 = new HashMap<>();
						
						  while(rs.next()){
							  t2.put(rs.getString("lov_desc"),rs.getString("lov_value"));
					        }
						return t2;
					});
			log.info("T2 code list is : {}" ,t2Codes);
				return t2Codes;
		} catch (EmptyResultDataAccessException e) {
			throw new DataAccessResourceFailureException("T2 codes are empty");
		} catch (Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
	}


	
	
	@Override
	public Map<String,String> getpayTo() {
		log.info("Inside LOVMasterDaoImpl#getpayTo");
		Map<String,String> payTo = new HashMap<>();

		try {
			payTo = namedParameterJdbcOperations.query(LOVQueries.GET_PAY_TO_LIST,
					(ResultSet rs)->{
						HashMap<String, String> payto = new HashMap<>();
						
						  while(rs.next()){
							  payto.put(rs.getString("lov_value"),rs.getString("lov_desc"));
					        }
						return payto;
					});
			log.info("Pay To list is : {}" ,payTo);
				return payTo;
		} catch (DataAccessException e) {
			log.info("Pay To list is empty : {}" ,e.getMessage());
		}catch (Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
		return null;
	}

	@Override
	public Map<String,String> getcurrencyVal() {
		log.info("Inside LOVMasterDaoImpl#getcurrencyVal");
		Map<String,String> currencyVal = new HashMap<>();

		try {
			currencyVal = namedParameterJdbcOperations.query(LOVQueries.GET_CURRENCY_VAL_LIST,
					(ResultSet rs)->{
						HashMap<String, String> currency = new HashMap<>();
						
						  while(rs.next()){
							  currency.put(rs.getString("lov_value"),rs.getString("lov_desc"));
					        }
						return currency;
					});
			log.info("Currency list is : {}" ,currencyVal);
				return currencyVal;
		} catch (DataAccessException e) {
			log.info("Currency list is empty : {}" ,e.getMessage());
		}catch (Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}	
		return null;
	}

	

	@Override
	public Map<String, String> getsectionCodes() {
		log.info("Inside LOVMasterDaoImpl#getsectionCodes");
			Map<String,String> sectionCodes = new HashMap<>();

			try {
				sectionCodes = namedParameterJdbcOperations.query(LOVQueries.GET_SECTION_CODE_LIST,
						(ResultSet rs)->{
							HashMap<String, String> section = new HashMap<>();
							
							  while(rs.next()){
								  section.put(rs.getString("lov_value"),rs.getString("lov_desc"));
						        }
							return section;
						});
				log.info("Section Codes list is : {}" ,sectionCodes);
					return sectionCodes;
			} catch (DataAccessException e) {
				log.info("Section Codes list is empty : {}" ,e.getMessage());
			}catch (Exception e) {
				throw new DatabaseOperationFailException("Database operation failed");
			}	
			return null;
	}

	@Override
	public Map<String, String> getrequestTypes() {
		log.info("Inside LOVMasterDaoImpl#getrequestTypes");
			Map<String,String> requestTypes = new HashMap<>();

			try {
				requestTypes = namedParameterJdbcOperations.query(LOVQueries.GET_REQUEST_TYPE_LIST,
						(ResultSet rs)->{
							HashMap<String, String> reqType = new HashMap<>();
							
							  while(rs.next()){
								  reqType.put(rs.getString("lov_value"),rs.getString("lov_desc"));
						        }
							return reqType;
						});
				log.info("Request Types list is : {}" ,requestTypes);
					return requestTypes;
			} catch (DataAccessException e) {
				log.info("requestTypes list is empty : {}" ,e.getMessage());
			}catch (Exception e) {
				throw new DatabaseOperationFailException("Database operation failed");
			}	
			return null;
	}
	
	@Override
	public Map<String, String> getfrtFor() {
		log.info("Inside LOVMasterDaoImpl#getfrtFor");
			Map<String,String> frtFor = new HashMap<>();

			try {
				frtFor = namedParameterJdbcOperations.query(LOVQueries.GET_FTR_FOR_LIST,
						(ResultSet rs)->{
							HashMap<String, String> reqType = new HashMap<>();
							
							  while(rs.next()){
								  reqType.put(rs.getString("lov_value"),rs.getString("lov_desc"));
						        }
							return reqType;
						});
				log.info("FTR FOR list is : {}" ,frtFor);
					return frtFor;
			} catch (DataAccessException e) {
				log.info("frtFor list is empty : {}" ,e.getMessage());
			}catch (Exception e) {
				throw new DatabaseOperationFailException("Database operation failed");
			}	
			return null;
	}
	
	
	@Override
	public Map<String, String> getdishonorReasons() {
		log.info("Inside LOVMasterDaoImpl#getdishonorReasons");
			Map<String,String> dishonorReasons = new HashMap<>();

			try {
				dishonorReasons = namedParameterJdbcOperations.query(LOVQueries.GET_Dishonor_Reasons_LIST,
						(ResultSet rs)->{
							HashMap<String, String> reqType = new HashMap<>();
							
							  while(rs.next()){
								  reqType.put(rs.getString("lov_value"),rs.getString("lov_desc"));
						        }
							return reqType;
						});
				log.info("Dishonor Reasons list is : {}" ,dishonorReasons);
					return dishonorReasons;
			} catch (DataAccessException e) {
				log.info("Dishonor Reasons list is empty : {}" ,e.getMessage());
			}catch (Exception e) {
				throw new DatabaseOperationFailException("Database operation failed");
			}	
			return null;
	}

	@Override
	public Map<String, String> getInstrumentTypes() {
		log.info("Inside LOVMasterDaoImpl#getInstrumentTypes");
		Map<String,String> instrumentTypes = new HashMap<>();
		try {
			instrumentTypes = namedParameterJdbcOperations.query(LOVQueries.GET_INSTRUMENT_TYPE_LIST,
					(ResultSet rs)->{
						HashMap<String, String> instruments = new HashMap<>();
						 while(rs.next()){
							 instruments.put(rs.getString("lov_value"),rs.getString("lov_desc"));
					        }
						 return instruments;
					});
			log.info("Instruments list is : {}" ,instrumentTypes);
			return instrumentTypes;
		}
		 catch (DataAccessException e) {
				log.info("Instrument type list is empty : {}" ,e.getMessage());
				return null;
			}	
		catch (Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
	}


	@Override
	public Map<String, String> getScheduleTitleDetails() {
		log.info("Inside LOVMasterDaoImpl#getScheduleTitleDetails");
		Map<String,String> scheduleTitleDtls = new HashMap<>();
		try {
			scheduleTitleDtls = namedParameterJdbcOperations.query(LOVQueries.GET_SCHEDULE_TITLE_DETAIL,
					(ResultSet rs)->{
						HashMap<String, String> details = new HashMap<>();
						 while(rs.next()){
							 details.put(rs.getString("lov_value"),rs.getString("lov_desc"));
					        }
						 return details;
					});
			log.info("Schdule Tile details list is : {}" ,scheduleTitleDtls);
			return scheduleTitleDtls;
		}
		 catch (DataAccessException e) {
				log.info("Schdule Tile details list is empty : {}" ,e.getMessage());
				return null;
			}
		catch (Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
	}

	@Override
	public Map<String, String> getPrefixTypes() {
		log.info("Inside LOVMasterDaoImpl#getPrefixTypes");
		Map<String,String> prefixList = new HashMap<>();
		try {
			prefixList = namedParameterJdbcOperations.query(LOVQueries.GET_PREFIX_LIST,
					(ResultSet rs)->{
						HashMap<String, String> prefix = new HashMap<>();
						 while(rs.next()){
							 prefix.put(rs.getString("lov_value"),rs.getString("lov_desc"));
					        }
						 return prefix;
					});
			log.info("Prefix list is : {}" ,prefixList);
			return prefixList;
		}
		 catch (DataAccessException e) {
				log.info("Prefix list is empty : {}" ,e.getMessage());
				return null;
			}
		catch (Exception e) {
			throw new DatabaseOperationFailException("Database operation failed");
		}
	}

	



	

}
 
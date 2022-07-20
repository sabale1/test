package in.ecgc.smile.erp.accounts.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.exception.GLCodeNotFoundException;
import in.ecgc.smile.erp.accounts.exception.InsertEntityGLFailException;
import in.ecgc.smile.erp.accounts.exception.ReadEntityGLFailException;
import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.util.EntityGLSqlQueries;
import in.ecgc.smile.erp.accounts.util.GlTxnSqlQueries;
import in.ecgc.smile.erp.accounts.util.UserInfoServiceUtil;
import lombok.extern.slf4j.Slf4j;

@Repository
@Transactional
@Slf4j
public class EntityGLMasterDaoImpl implements EntityGLMasterDao {

	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private UserInfoServiceUtil userInfoService;

	@Autowired
	public EntityGLMasterDaoImpl(DataSource dataSource) {

		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

//	private static final Logger log = LoggerFactory.getLogger(EntityGLMasterDaoImpl.class);

	/**
	 * Add new GL code
	 * 
	 * DAO implementation to add new GL code into database Table :acct_entity_gl_mst
	 * returns 1 if insert operation succeeds returns -1 in case of failure
	 */

	@Override
	public Integer addGLCode(EntityGL entityGL) throws DataAccessException {
		log.info("Inside EntityGLMasterDaoImpl#addGLCode");
		int rowCount = 0;
		try {
			log.info("DAO: UserInfoService:{}", userInfoService);
			Map<String, Object> GLnamedParameters = new HashMap<String, Object>();
			GLnamedParameters.put("entityGLCd", entityGL.getEntityGlCd());
			GLnamedParameters.put("mainglCd", entityGL.getMainglCd());
			GLnamedParameters.put("subglCd", entityGL.getSubglCd());
			GLnamedParameters.put("glName", entityGL.getGlName());
			GLnamedParameters.put("glIsGroup", entityGL.getGlIsGroup());
			GLnamedParameters.put("glType", entityGL.getGlType());
			GLnamedParameters.put("glSubtype", entityGL.getGlSubtype());
			GLnamedParameters.put("balInd", entityGL.getBalInd());
			GLnamedParameters.put("zeroBalFlg", entityGL.getZeroBalFlg());
			GLnamedParameters.put("active", entityGL.getActive());
			GLnamedParameters.put("personalLedgerLevel", entityGL.getPlLevel());
			GLnamedParameters.put("cashaccount", entityGL.getCashaccount());
			GLnamedParameters.put("cashFlow", entityGL.getCashFlow());
			GLnamedParameters.put("logicalLocCd", entityGL.getLogicalLocCd());
			GLnamedParameters.put("plLevel", entityGL.getPlLevel());
			GLnamedParameters.put("metaRemarks", entityGL.getMetaRemarks());
			GLnamedParameters.put("t1", entityGL.getT1());
			GLnamedParameters.put("t2", entityGL.getT2());
			GLnamedParameters.put("t3", entityGL.getT3());
			GLnamedParameters.put("t4", entityGL.getT4());
			GLnamedParameters.put("t5", entityGL.getT5());
			GLnamedParameters.put("t6", entityGL.getT6());
			GLnamedParameters.put("t7", entityGL.getT7());
			GLnamedParameters.put("t8", entityGL.getT8());
			GLnamedParameters.put("t9", entityGL.getT9());
			GLnamedParameters.put("t10", entityGL.getT10());
			GLnamedParameters.put("t11", entityGL.getT11());
			GLnamedParameters.put("t12", entityGL.getT12());
			GLnamedParameters.put("t13", entityGL.getT13());
			GLnamedParameters.put("t14", entityGL.getT14());
			GLnamedParameters.put("t15", entityGL.getT15());
			GLnamedParameters.put("t16", entityGL.getT16());
			GLnamedParameters.put("t17", entityGL.getT17());
			GLnamedParameters.put("t18", entityGL.getT18());
			GLnamedParameters.put("t19", entityGL.getT19());
			GLnamedParameters.put("t20", entityGL.getT20());
			GLnamedParameters.put("t21", entityGL.getT21());
			GLnamedParameters.put("t22", entityGL.getT22());
			GLnamedParameters.put("t23", entityGL.getT23());
			GLnamedParameters.put("t24", entityGL.getT24());
			GLnamedParameters.put("t25", entityGL.getT25());
			GLnamedParameters.put("t26", entityGL.getT26());
			GLnamedParameters.put("t27", entityGL.getT27());
			GLnamedParameters.put("t28", entityGL.getT28());
			GLnamedParameters.put("t29", entityGL.getT29());
			GLnamedParameters.put("t30", entityGL.getT30());
			GLnamedParameters.put("t31", entityGL.getT31());
			GLnamedParameters.put("t32", entityGL.getT32());
			GLnamedParameters.put("t33", entityGL.getT33());
			GLnamedParameters.put("irdaCd", entityGL.getIrdaCd());
			GLnamedParameters.put("irdaBapCd", entityGL.getIrdaBpaCd());
			GLnamedParameters.put("schedule6", entityGL.getSchedule6());
			GLnamedParameters.put("finFormName", entityGL.getFinancialFormName());
			GLnamedParameters.put("oldCd", entityGL.getOldCd());
			GLnamedParameters.put("subBifurcationLevel", entityGL.getSubBifurcationLevel());
			GLnamedParameters.put("createdBy", userInfoService.getCurrentUser());
			GLnamedParameters.put("lastUpdatedBy", userInfoService.getCurrentUser());
			GLnamedParameters.put("scheduleItemCd", entityGL.getScheduleItemCd());
			GLnamedParameters.put("scheduleCd", entityGL.getScheduleCd());
			log.debug("DAO: Adding new Gl Code, ParameterList:{}", GLnamedParameters);
			rowCount = namedParameterJdbcTemplate.update(EntityGLSqlQueries.ADD_GL_CODE, GLnamedParameters);

		} catch (Exception e) {
			log.error("Exception occured while inserting new GL code {}", e);
			throw new InsertEntityGLFailException("Failed to Insert new GL Code" + e.getMessage());
		}
		if (rowCount == 1) {
			log.info("DAO: Added new GL Code with rowCount:{} returning 1", rowCount);
			return 1;
		} else {
			log.info("DAO: Added new GL Code with rowCount:{} returning -1", rowCount);
			return -1;
		}

	}

	/**
	 * View all GL codes
	 * 
	 * DAO implementation to list all GL codes present in the database Table
	 * :acct_entity_gl_mst returns list of model EntityGL
	 * {@link in.ecgc.smile.erp.accounts.model.EntityGL}
	 */
	@Override
	public List<EntityGL> listAllGLCodes() throws DataAccessException {
		log.info("Inside EntityGLMasterDaoImpl#listAllGLCodes");
		List<EntityGL> entityGLCodes = new ArrayList<EntityGL>();
		try {
			RowMapper<EntityGL> rowMapper = EntityGLSqlQueries::mapRow;
			entityGLCodes = namedParameterJdbcTemplate.query(EntityGLSqlQueries.ALL_GL_CODES, rowMapper);
		} catch (Exception e) {
			log.error(
					"DAO EntityGLMasterDaoImpl#listAllGLCodes Exception occured while reading List of All GL codes {}",
					e);
			throw new ReadEntityGLFailException("Failed to read GL Code" + e.getMessage());
		}
		return entityGLCodes;
	}

	/**
	 * Find details of given entityGLCode
	 * 
	 * DAO implementation to find details of gievn GL code present in the database
	 * Table : acct_entity_gl_mst returns model EntityGL
	 * {@link in.ecgc.smile.erp.accounts.model.EntityGL}
	 */
	public EntityGL findGLByGLCode(String mainGLCode, String subGLCode) {
		log.info("Inside EntityGLMasterDaoImpl#findGLByGLCode");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("mainCode", mainGLCode);
		paramMap.put("subCode", subGLCode);
		EntityGL entityGL = new EntityGL();
		try {
//			RowMapper<EntityGL> rowMapper = EntityGLSqlQueries::mapRow;
			entityGL = namedParameterJdbcOperations.queryForObject(EntityGLSqlQueries.GET_ENTITY_GL, paramMap,
					new RowMapper<EntityGL>() {

						@Override
						public EntityGL mapRow(ResultSet rs, int rowNum) throws SQLException {
							EntityGL entityGL = new EntityGL();
							// entityGL.setEntityGlCd(rs.getInt("entity_gl_cd"));
							entityGL.setMainglCd(rs.getString("maingl_cd"));
							entityGL.setSubglCd(rs.getString("subgl_cd"));
							entityGL.setGlName(rs.getString("gl_name"));
							entityGL.setEntityGlCd(rs.getString("entity_cd"));
							if (rs.getString("gl_is_group") != null)
								entityGL.setGlIsGroup(rs.getString("gl_is_group").charAt(0));
							entityGL.setGlType(rs.getString("gl_type"));
							entityGL.setGlSubtype(rs.getString("gl_subtype"));
							entityGL.setBalInd(rs.getString("bal_ind"));
							if (rs.getString("zero_bal_flag") != null)
								entityGL.setZeroBalFlg(rs.getString("zero_bal_flag").charAt(0));
							if (rs.getString("active") != null)
								entityGL.setActive(rs.getString("active").charAt(0));
							entityGL.setPlLevel(rs.getString("pl_level"));
							entityGL.setCashaccount(rs.getInt("cashaccount"));
							entityGL.setCashFlow(rs.getInt("cashflow"));
							entityGL.setLogicalLocCd(rs.getString("logical_loc_cd"));
							entityGL.setMetaRemarks(rs.getString("meta_remarks"));
							entityGL.setCreatedBy(rs.getString("created_by"));
							entityGL.setCreatedDt(rs.getObject("created_dt", LocalDate.class));
							entityGL.setLastUpdatedBy(rs.getString("last_updated_by"));
							entityGL.setLastUpdatedDt(rs.getObject("last_updated_dt", LocalDate.class));
							entityGL.setOldCd(rs.getString("old_cd"));
							if (rs.getString("t1") != null)
								entityGL.setT1(rs.getString("t1").charAt(0));
							if (rs.getString("t2") != null)
								entityGL.setT2(rs.getString("t2").charAt(0));
							if (rs.getString("t3") != null)
								entityGL.setT3(rs.getString("t3").charAt(0));
							if (rs.getString("t4") != null)
								entityGL.setT4(rs.getString("t4").charAt(0));
							if (rs.getString("t5") != null)
								entityGL.setT5(rs.getString("t5").charAt(0));
							if (rs.getString("t6") != null)
								entityGL.setT6(rs.getString("t6").charAt(0));
							if (rs.getString("t7") != null)
								entityGL.setT7(rs.getString("t7").charAt(0));
							if (rs.getString("t8") != null)
								entityGL.setT8(rs.getString("t8").charAt(0));
							if (rs.getString("t9") != null)
								entityGL.setT9(rs.getString("t9").charAt(0));
							if (rs.getString("t10") != null)
								entityGL.setT10(rs.getString("t10").charAt(0));
							if (rs.getString("t11") != null)
								entityGL.setT11(rs.getString("t11").charAt(0));
							if (rs.getString("t12") != null)
								entityGL.setT12(rs.getString("t12").charAt(0));
							if (rs.getString("t13") != null)
								entityGL.setT13(rs.getString("t13").charAt(0));
							if(rs.getString("t14") != null)
								entityGL.setT14(rs.getString("t14").charAt(0));
							if (rs.getString("t15") != null)
								entityGL.setT15(rs.getString("t15").charAt(0));
							if (rs.getString("t16") != null)
								entityGL.setT16(rs.getString("t16").charAt(0));
							if (rs.getString("t17") != null)
								entityGL.setT17(rs.getString("t17").charAt(0));
							if (rs.getString("t18") != null)
								entityGL.setT18(rs.getString("t18").charAt(0));
							if (rs.getString("t19") != null)
								entityGL.setT19(rs.getString("t19").charAt(0));
							if (rs.getString("t20") != null)
								entityGL.setT20(rs.getString("t20").charAt(0));
							if (rs.getString("t21") != null)
								entityGL.setT21(rs.getString("t21").charAt(0));
							if (rs.getString("t22") != null)
								entityGL.setT22(rs.getString("t22").charAt(0));
							if (rs.getString("t23") != null)
								entityGL.setT23(rs.getString("t23").charAt(0));
							if (rs.getString("t24") != null)
								entityGL.setT24(rs.getString("t24").charAt(0));
							if (rs.getString("t25") != null)
								entityGL.setT25(rs.getString("t25").charAt(0));
							if (rs.getString("t26") != null)
								entityGL.setT26(rs.getString("t26").charAt(0));
							if (rs.getString("t27") != null)
								entityGL.setT27(rs.getString("t27").charAt(0));
							if (rs.getString("t28") != null)
								entityGL.setT28(rs.getString("t28").charAt(0));
							if (rs.getString("t29") != null)
								entityGL.setT29(rs.getString("t29").charAt(0));
							if (rs.getString("t30") != null)
								entityGL.setT30(rs.getString("t30").charAt(0));
							if (rs.getString("t31") != null)
								entityGL.setT31(rs.getString("t31").charAt(0));
							if (rs.getString("t32") != null)
								entityGL.setT32(rs.getString("t32").charAt(0));
							if (rs.getString("t33") != null)
								entityGL.setT33(rs.getString("t33").charAt(0));
							if (rs.getString("irda_cd") != null)
								entityGL.setIrdaCd(rs.getString("irda_cd"));
							if (rs.getString("irda_bap_cd") != null)
								entityGL.setIrdaBpaCd(rs.getString("irda_bap_cd"));
							if (rs.getString("schedule6") != null)
								entityGL.setSchedule6(rs.getString("schedule6"));
							if (rs.getString("financial_form_name") != null)
								entityGL.setFinancialFormName(rs.getString("financial_form_name"));
							if (rs.getString("sub_bifurcation_level") != null)
								entityGL.setSubBifurcationLevel(rs.getString("sub_bifurcation_level"));
							entityGL.setBalInd(rs.getString("bal_ind"));
							entityGL.setScheduleItemCd(rs.getString("schedule_item_code"));
							entityGL.setScheduleCd(rs.getString("schedule_code"));
							log.info("entity gl is :{}", entityGL);
							return entityGL;
						}
					});
		} catch (Exception e) {
			log.error("exception in EntityGLMasterDaoImpl#findGLByGLCode : {}", e.getMessage());
			throw new GLCodeNotFoundException("No GL code details found for GL Code for MainGl : " + mainGLCode
					+ " and SubGl : " + subGLCode + "");
		}
		return entityGL;
	}

	/**
	 * Update details of given entityGLCode
	 * 
	 * DAO implementation to update details of given GL code present in the database
	 * Table : acct_entity_gl_mst returns 1 if update operation succeeds returns -1
	 * in case of failure
	 */
	@Override
	public EntityGL updateGLCode(EntityGL entityGL) {
		log.info("Inside EntityGLMasterDaoImpl#updateGLCode");
		int rowCount;
		try {
			Map<String, Object> GLnamedParameters = new HashMap<String, Object>();

			log.info("updated entity {}", entityGL);
			GLnamedParameters.put("entityGLCd", entityGL.getEntityGlCd());
			GLnamedParameters.put("mainglCd", entityGL.getMainglCd());
			GLnamedParameters.put("subglCd", entityGL.getSubglCd());
			GLnamedParameters.put("glName", entityGL.getGlName());
			GLnamedParameters.put("glIsGroup", entityGL.getGlIsGroup());
			GLnamedParameters.put("glType", entityGL.getGlType());
			GLnamedParameters.put("glSubtype", entityGL.getGlSubtype());
			GLnamedParameters.put("balInd", entityGL.getBalInd());
			GLnamedParameters.put("zeroBalFlg", entityGL.getZeroBalFlg());
			GLnamedParameters.put("active", entityGL.getActive());
			GLnamedParameters.put("personalLedgerLevel", entityGL.getPlLevel());
			GLnamedParameters.put("cashaccount", entityGL.getCashaccount());
			GLnamedParameters.put("cashFlow", entityGL.getCashFlow());
			GLnamedParameters.put("logicalLocCd", entityGL.getLogicalLocCd());
			GLnamedParameters.put("plLevel", entityGL.getPlLevel());
			GLnamedParameters.put("metaRemarks", entityGL.getMetaRemarks());
			GLnamedParameters.put("t1", entityGL.getT1());
			GLnamedParameters.put("t2", entityGL.getT2());
			GLnamedParameters.put("t3", entityGL.getT3());
			GLnamedParameters.put("t4", entityGL.getT4());
			GLnamedParameters.put("t5", entityGL.getT5());
			GLnamedParameters.put("t6", entityGL.getT6());
			GLnamedParameters.put("t7", entityGL.getT7());
			GLnamedParameters.put("t8", entityGL.getT8());
			GLnamedParameters.put("t9", entityGL.getT9());
			GLnamedParameters.put("t10", entityGL.getT10());
			GLnamedParameters.put("t11", entityGL.getT11());
			GLnamedParameters.put("t12", entityGL.getT12());
			GLnamedParameters.put("t13", entityGL.getT13());
			GLnamedParameters.put("t14", entityGL.getT14());
			GLnamedParameters.put("t15", entityGL.getT15());
			GLnamedParameters.put("t16", entityGL.getT16());
			GLnamedParameters.put("t17", entityGL.getT17());
			GLnamedParameters.put("t18", entityGL.getT18());
			GLnamedParameters.put("t19", entityGL.getT19());
			GLnamedParameters.put("t20", entityGL.getT20());
			GLnamedParameters.put("t21", entityGL.getT21());
			GLnamedParameters.put("t22", entityGL.getT22());
			GLnamedParameters.put("t23", entityGL.getT23());
			GLnamedParameters.put("t24", entityGL.getT24());
			GLnamedParameters.put("t25", entityGL.getT25());
			GLnamedParameters.put("t26", entityGL.getT26());
			GLnamedParameters.put("t27", entityGL.getT27());
			GLnamedParameters.put("t28", entityGL.getT28());
			GLnamedParameters.put("t29", entityGL.getT29());
			GLnamedParameters.put("t30", entityGL.getT30());
			GLnamedParameters.put("t31", entityGL.getT31());
			GLnamedParameters.put("t32", entityGL.getT32());
			GLnamedParameters.put("t33", entityGL.getT33());
			GLnamedParameters.put("irdaCd", entityGL.getIrdaCd());
			GLnamedParameters.put("irdaBapCd", entityGL.getIrdaBpaCd());
			GLnamedParameters.put("schedule6", entityGL.getSchedule6());
			GLnamedParameters.put("finFormName", entityGL.getFinancialFormName());
			GLnamedParameters.put("oldCd", entityGL.getOldCd());
			GLnamedParameters.put("subBifurcationLevel", entityGL.getSubBifurcationLevel());
			GLnamedParameters.put("createdBy", entityGL.getCreatedBy());
			GLnamedParameters.put("lastUpdatedBy", userInfoService.getCurrentUser());
			GLnamedParameters.put("scheduleItemCd", entityGL.getScheduleItemCd());
			GLnamedParameters.put("scheduleCd", entityGL.getScheduleCd());
			rowCount = namedParameterJdbcTemplate.update(EntityGLSqlQueries.UPDATE_GLCODE, GLnamedParameters);
		} catch (DataAccessException e) {
			log.info("Exception occured while updating new GL code... {} ", e);
			throw new InsertEntityGLFailException("Failed to Insert new GL Code" + e.getMessage());

		}
		if (rowCount == 1)
			return entityGL;
		else
			return null;
	}

	/**
	 * Disable given entityGLCode
	 * 
	 * DAO implementation to disable details of given GL code present in the
	 * database Table : acct_entity_gl_mst returns 1 if disable operation succeeds
	 * returns -1 in case of failure
	 */
	@Override
	public Integer disableGLCode(EntityGL entityGL) {
		log.info("Inside EntityGLMasterDaoImpl#disableGLCode");
		int rowCount;
		Map<String, Object> GLnamedParameters = new HashMap<String, Object>();

		log.info("updated entity {}", entityGL);
		GLnamedParameters.put("entityGLCd", entityGL.getEntityGlCd());
		GLnamedParameters.put("mainglCd", entityGL.getMainglCd());
		GLnamedParameters.put("subglCd", entityGL.getSubglCd());
		rowCount = namedParameterJdbcTemplate.update(EntityGLSqlQueries.DISABLE_GLCODE, GLnamedParameters);
		log.error("rowCount :{} ", rowCount);
		if (rowCount >= 1)
			return 1;
		else
			return -1;
	}

//	@Override
//	public String findSubBifurcations(String mainGLCode, String subGLCode) {
//	try {
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("mainGl", mainGLCode);
//			paramMap.put("subGl",subGLCode);
//			String levelCode;
//			levelCode =namedParameterJdbcTemplate.queryForObject(EntityGLSqlQueries.GET_SUB_BIFURCATION_CDS,paramMap,new RowMapper<String>() {
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//					return rs.getString("value");
//				}
//			});
//			System.err.println("inside dao "+ levelCode);
//			return levelCode;
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}

	@Override
	public Integer getRegularGLTypesByOpeningDay() {
		log.info("Inside EntityGLMasterDaoImpl#getRegularGLTypesByOpeningDay");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(null, null);
		Integer count;
		try {
			count = namedParameterJdbcOperations.queryForObject(EntityGLSqlQueries.GET_REGULAR_GL_COUNT, params,
					new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});
			return count;
		} catch (Exception e) {
			log.info("Exception in EntityGLMasterDaoImpl#getRegularGLTypesByOpeningDay {}", e);

		}
		return null;

	}

	@Override
	public Integer getConfiguredGLTypesByOpeningDay() {
		log.info("Inside EntityGLMasterDaoImpl#getConfiguredGLTypesByOpeningDay");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(null, null);
		try {
			Integer count = namedParameterJdbcOperations.queryForObject(EntityGLSqlQueries.GET_CONFIGURED_GL_COUNT,
					params, new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});

			return count;
		} catch (Exception e) {
			log.info("Exception in EntityGLMasterDaoImpl#getConfiguredGLTypesByOpeningDay {}", e);
		}
		return null;
	}

	@Override
	public List<EntityGL> getsubGLCodebyMainGLCode(String mainGLCode) {
		log.info("Inside EntityGLMasterDaoImpl#getsubGLCodebyMainGLCode");
		List<EntityGL> entityList = new ArrayList<>();
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("mainGLCode", mainGLCode);
		RowMapper<EntityGL> rowMapper = EntityGLSqlQueries::mapRow;
		entityList = namedParameterJdbcTemplate.query(EntityGLSqlQueries.GET_ALL_BY_MAIN_GL_CODE, paramMap, rowMapper);
		return entityList;
	}

	@Override
	public List<EntityGL> getAllGlByIsGlGroup() {
		log.info("Inside EntityGLMasterDaoImpl#getAllGlByIsGlGroup");
		List<EntityGL> entityGLCodes = new ArrayList<EntityGL>();
		try {
			RowMapper<EntityGL> rowMapper = EntityGLSqlQueries::mapRow;
			entityGLCodes = namedParameterJdbcTemplate.query(EntityGLSqlQueries.GET_ALL_GL_IS_GL_NAME, rowMapper);
		} catch (Exception e) {
			log.error(
					"DAO EntityGLMasterDaoImpl#listAllGLCodes Exception occured while reading List of All GL codes {}",
					e);
			throw new ReadEntityGLFailException("Failed to read GL Code" + e.getMessage());
		}
		return entityGLCodes;
	}

	@Override
	public Integer activateGLCode(EntityGL entityGL) {
		log.info("Inside EntityGLMasterDaoImpl#activateGLCode");
		int rowCount;
		Map<String, Object> GLnamedParameters = new HashMap<String, Object>();

		log.info("updated entity {}", entityGL);
		GLnamedParameters.put("entityGLCd", entityGL.getEntityGlCd());
		GLnamedParameters.put("mainglCd", entityGL.getMainglCd());
		GLnamedParameters.put("subglCd", entityGL.getSubglCd());
		rowCount = namedParameterJdbcTemplate.update(EntityGLSqlQueries.ACTIVATE_GLCODE, GLnamedParameters);
		if (rowCount >= 1)
			return 1;
		else
			return -1;
	}

	@Override
	public EntityGL getGlCodeByLocation(String Logicalloc) {
		log.info("Inside EntityGLMasterDaoImpl#getGlCodeByLocation");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("location", Logicalloc);
		EntityGL entityGLCode = null;
		try {
			RowMapper<EntityGL> rowMapper = EntityGLSqlQueries::mapRow;
			entityGLCode = namedParameterJdbcTemplate.queryForObject(EntityGLSqlQueries.GET_GLCODE_BY_LOCATION, params,
					rowMapper);
		} catch (Exception e) {
			log.error("DAO EntityGLMasterDaoImpl#GLCodes Exception occured while reading GL codes by location {}", e);
			throw new ReadEntityGLFailException("Failed to read GL Code" + e.getMessage());
		}
		return entityGLCode;
	}


	@Override
	public List<EntityGL> getGstGlCodes(String param1, String param2) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("param1", param1);
		paramMap.put("param2", param2);
		List<EntityGL> entityGlList = new ArrayList<>();
		try {
			RowMapper<EntityGL> rowMapper = EntityGLSqlQueries::mapRow;
			entityGlList = namedParameterJdbcTemplate.query(EntityGLSqlQueries.GET_GST_GL_CODES,paramMap, rowMapper);
			}
			catch(Exception e) {
				log.error("DAO EntityGLMasterDaoImpl#listAllGLCodes Exception occured while reading List of All GL codes",e);
				throw new ReadEntityGLFailException("Failed to read GL Code" + e.getMessage());
			}
		
		return entityGlList;
	}

	@Override
	public List<GlTxnDtl> getAllGlTxn(String mainGLCode, String subGLCode) {
		log.info("Inside EntityGLMasterDaoImpl#getAllGlTxn");
		List<GlTxnDtl> list = null;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mainglCd", mainGLCode);
		paramMap.put("subglCd", subGLCode);

		RowMapper<GlTxnDtl> rowMapperForDtl = GlTxnSqlQueries::mapRowForDtl;
		list = namedParameterJdbcTemplate.query(EntityGLSqlQueries.GET_GL_TXN_DTL_BY_MAIN_GL_CODE, paramMap, rowMapperForDtl);

		if (list == null) {
			log.error("Empty list found for this GL Code : {} and {}", mainGLCode, subGLCode);
			return list;
		} else {
			return list;
		}
	}
	
	
}


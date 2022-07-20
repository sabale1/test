package in.ecgc.smile.erp.accounts.repository;

import java.math.BigDecimal;
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

import in.ecgc.smile.erp.accounts.dto.GLReport;
import in.ecgc.smile.erp.accounts.dto.GLReportIn;
import in.ecgc.smile.erp.accounts.dto.GLReportOut;
import in.ecgc.smile.erp.accounts.exception.GLCodeNotFoundException;
import in.ecgc.smile.erp.accounts.exception.GLTxnDBFailedException;
import in.ecgc.smile.erp.accounts.exception.InvalidGLTxnDataException;
import in.ecgc.smile.erp.accounts.model.DateOperation;
import in.ecgc.smile.erp.accounts.model.FiscalYearModel;
import in.ecgc.smile.erp.accounts.model.GlBrBalanceIn;
import in.ecgc.smile.erp.accounts.model.GlBrBalanceOut;
import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.service.CalendarService;
import in.ecgc.smile.erp.accounts.service.EntityGLMasterService;
import in.ecgc.smile.erp.accounts.service.FiscalYearService;
import in.ecgc.smile.erp.accounts.util.GlTxnSqlQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Repository
@Transactional
@Slf4j
public class GlTxnDaoImpl implements GlTxnDao {

	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//	GlTxnDaoImpl txnDao;
	@Autowired
	EntityGLMasterService entitySrvice;
	@Autowired
	CalendarService calenderService;

	@Autowired
	UserInfoService userInfoService;

	@Autowired
	FiscalYearService fiscalYearService;

	@Autowired
	public GlTxnDaoImpl(DataSource dataSource) {
		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<GlTxnHdr> getAllGlTxn(String logicalLoc) {
		log.info("Inside GlTxnDaoImpl#getAllGlTxn");

		List<GlTxnHdr> glTxnHdrs = null;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", 'V');
		paramMap.put("logicalLoc", logicalLoc);

		String query = null;

		if (!logicalLoc.equals("ALL")) {
			query = GlTxnSqlQueries.GET_ALL_GL_TXN_HDR;
		} else {
			query = GlTxnSqlQueries.GET_ALL_GL_TXN_HDR_2;
		}

		RowMapper<GlTxnHdr> rowMapper = GlTxnSqlQueries::mapRow;
		glTxnHdrs = namedParameterJdbcTemplate.query(query, paramMap, rowMapper);

		if (glTxnHdrs.isEmpty()) {
			log.error("Empty list found for logical location : {}", logicalLoc);
			return glTxnHdrs;
		} else {
			return glTxnHdrs;
		}
	}

	@Override
	public List<GlTxnHdr> getAllGlTxn(String logicalLoc, String glTxnType) {
		log.info("Inside GlTxnDaoImpl#getAllGlTxn");
		List<GlTxnHdr> glTxnHdrs = null;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", 'V');
		paramMap.put("logicalLoc", logicalLoc);
		paramMap.put("gltxntype", glTxnType);

		RowMapper<GlTxnHdr> rowMapper = GlTxnSqlQueries::mapRow;
		glTxnHdrs = namedParameterJdbcTemplate.query(GlTxnSqlQueries.GET_ALL_GL_TXN_HDR_BY_TXNTYPE_AND_LOCATION,
				paramMap, rowMapper);
		if (glTxnHdrs == null) {
			log.error("Empty list found for logical location : {}", logicalLoc);
			return glTxnHdrs;
		} else {
			return glTxnHdrs;
		}
	}

	@Override
	public GlTxnHdr getGlTxn(Integer glTxnNo, String logicalLoc, String glTxnType) {
		log.info("Inside GlTxnDaoImpl#getGlTxn");
		GlTxnHdr glTxn;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", 'V');
		paramMap.put("logicalLoc", logicalLoc);
		paramMap.put("glTxnType", glTxnType);
		paramMap.put("glTxnNo", glTxnNo);
		try {
			RowMapper<GlTxnHdr> rowMapper = GlTxnSqlQueries::mapRow;
			RowMapper<GlTxnDtl> rowMapperForDtl = GlTxnSqlQueries::mapRowForDtl;
			glTxn = namedParameterJdbcOperations.queryForObject(GlTxnSqlQueries.GET_TXN_HDR_BY_ID, paramMap, rowMapper);
			glTxn.setGlTxnDtls(
					namedParameterJdbcOperations.query(GlTxnSqlQueries.GET_TXN_DTL_BY_ID, paramMap, rowMapperForDtl));
			return glTxn;
		} catch (Exception e) {
			log.info("Exception in GlTxnDaoImpl#getGlTxn : {}", e.getMessage());
			throw new GLTxnDBFailedException("No Gl transaction details found for logical location : " + logicalLoc
					+ " and GL transaction type : " + glTxnType + " and GL transaction number : " + glTxnNo + "");
		}
	}

	@Override
	public List<GlTxnHdr> getAllGlTxnByFromDtToDt(LocalDate fromDt, LocalDate toDt) {
		log.info("Inside GlTxnDaoImpl#getAllGlTxnByFromDtToDt");
		List<GlTxnHdr> glTxnHdrs = null;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", 'V');
		paramMap.put("fromDt", fromDt);
		paramMap.put("toDt", toDt);

		RowMapper<GlTxnHdr> rowMapper = GlTxnSqlQueries::mapRow;
		glTxnHdrs = namedParameterJdbcTemplate.query(GlTxnSqlQueries.GET_TXN_BY_FROM_TO_DT, paramMap, rowMapper);

		if (glTxnHdrs == null) {
			log.error("Empty list found for date between : {} and {}", fromDt, toDt);
			return glTxnHdrs;
		} else {
			return glTxnHdrs;
		}
	}

	@Override
	@Transactional
	public Integer addGlTxn(GlTxnHdr glTxnHdr) {
		log.info("Inside GlTxnDaoImpl#addGlTxn");
		Integer rowNum;

		try {
			log.info("Received GL txn header object :  {}", glTxnHdr.getGlTxnNo());
			Map<String, Object> paramMapForHdr = GlTxnSqlQueries.getParamMapForHdr(glTxnHdr);
			rowNum = namedParameterJdbcTemplate.update(GlTxnSqlQueries.ADD_GL_TXN_HDR, paramMapForHdr);
			if (rowNum > 0) {
				for (GlTxnDtl glDtl : glTxnHdr.getGlTxnDtls()) {
					Map<String, Object> paramMapForDtl = GlTxnSqlQueries.getParamMapForDtl(glDtl);
					paramMapForDtl.put("glTxnNo", glTxnHdr.getGlTxnNo());
					rowNum += namedParameterJdbcOperations.update(GlTxnSqlQueries.ADD_GL_TXN_DTL, paramMapForDtl);
				}
				return rowNum;
			} else {
				return 0;
			}
		} catch (Exception e) {
			log.error("Exception in GlTxnDaoImpl#getGlTxnNo : {}", e.fillInStackTrace());
			throw new GLTxnDBFailedException("Failed to add new GL transaction detail : " + e.getMessage());
		}
	}

	@Override
	public Integer getGlTxnNo(String logcalLoc, String glTxnType, String fiscalYr) {
		log.info("Inside GlTxnDaoImpl#getGlTxnNo");
		log.info("Generating GL Txn Sequence Number for LogicalLocation: {} TxnType: {} Fiscal Year: {}", logcalLoc,
				glTxnType, fiscalYr);
		try {
			Map<String, Object> namedParameters = new HashMap<String, Object>();
			namedParameters.put("logicalLoc", logcalLoc);
			namedParameters.put("glTxnType", glTxnType);
			namedParameters.put("fiscalYr", fiscalYr);
			Integer seq = namedParameterJdbcOperations.queryForObject(GlTxnSqlQueries.GET_SEQ, namedParameters,
					new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							int num = rs.getInt(1);
							return num;
						}
					});
			log.info("seq data is : {}", seq);
			return seq;
		} catch (Exception e) {
			log.error(
					"Exception in Generating GL Txn Sequence Number for LogicalLocation: {} TxnType: {} Fiscal Year: {}",
					logcalLoc, glTxnType, fiscalYr);
			log.error("Exception in GlTxnDaoImpl#getGlTxnNo", e.fillInStackTrace());
			throw new GLTxnDBFailedException("Failed to Generate Sequence Number" + e.getMessage());
		}
	}

	@Override
	public Integer updateGlTxnNo(String logcalLoc, String glTxnType, String fiscalYr, Integer glTxnNo) {
		log.info("Inside GlTxnDaoImpl#updateGlTxnNo");
		try {
			Map<String, Object> param = new HashMap<String, Object>();

			param.put("logicalLoc", logcalLoc);
			param.put("glTxnType", glTxnType);
			param.put("fiscalYr", fiscalYr);
			param.put("glTxnNo", glTxnNo);

			return namedParameterJdbcTemplate.update(GlTxnSqlQueries.UPDATE_SEQ, param);

		} catch (Exception e) {
			log.error(
					"Exception in Updating GL Txn Sequence Number for GLTxn No : {}, LogicalLocation: {}, TxnType: {}, Fiscal Year: {}",
					glTxnNo, logcalLoc, glTxnType, fiscalYr);
			log.error("Exception in GlTxnDaoImpl#updateGlTxnNo", e.fillInStackTrace());
			throw new GLTxnDBFailedException("Failed to Update Sequence Number" + e.getMessage());
		}
	}

	@Override
	public Integer updateGlTxnDtl(GlTxnDtl glTxnDtl) {
		log.info("Inside GlTxnDaoImpl#updateGlTxnDtl");
		Integer rowNum = 0;
		try {
			Map<String, Object> paramMapForDtl = GlTxnSqlQueries.getParamMapForDtl(glTxnDtl);
			rowNum = namedParameterJdbcTemplate.update(GlTxnSqlQueries.UPDATE_GL_TXN, paramMapForDtl);
			return rowNum;
		} catch (Exception e) {
			log.error("Exception in Updating GL transaction detail : {}", glTxnDtl);
			log.error("Exception in GlTxnDaoImpl#updateGlTxnNo", e.fillInStackTrace());
			throw new GLTxnDBFailedException("Failed to Update GL transaction detail" + e.getMessage());
		}
	}

	@Override
	public Integer updateHdrOnRevarsal(GlTxnHdr glTxnHdr) throws InvalidGLTxnDataException {
		log.info("Inside GlTxnDaoImpl#updateHdrOnRevarsal");
		Integer rowNum = 0;
		try {

		log.info("before reverse update");

			Map<String, Object> paramMapForHdr = GlTxnSqlQueries.getParamMapForHdr(glTxnHdr);
			
			log.info("Gl txn No is:: {} ,Gl reversal no is:: {} ,Gl type is :: {} ,Logical Location is ::{} " , glTxnHdr.getGlTxnNo(), glTxnHdr.getRevarsalGlTxnNo(),glTxnHdr.getGlTxnType(),
					glTxnHdr.getLogicalLocCd());

			rowNum = namedParameterJdbcTemplate.update(GlTxnSqlQueries.UPDATE_HDR_FOR_REVARSAL, paramMapForHdr);
			return rowNum;
		} catch (Exception e) {
			log.error("Exception in Updating GL header on reversal : {}", glTxnHdr);
			log.error("Exception in GlTxnDaoImpl#updateHdrOnRevarsal {}", e.fillInStackTrace());
			throw new GLTxnDBFailedException("Failed to Update GL header on reversal" + e.getMessage());
		}
	}

	public Integer updateGLBrBal(GlTxnDtl glTxnDtl) {

		return 0;
	}

	@Override
	public Integer updateDrCrBrbal(List<GlTxnDtl> glTxnDtls, String fiscalYr, LocalDate txnDt)
			throws InvalidGLTxnDataException {
		log.info("Inside GlTxnDaoImpl#updateDrCrBrbal");
		int rowNum = 0;
		DateOperation dt = new DateOperation(txnDt.getMonthValue());
		for (GlTxnDtl glDtl : glTxnDtls) {
			Map<String, Object> paramMap = GlTxnSqlQueries.getParamMapForDtl(glDtl);
			paramMap.put("logicalLoc", glDtl.getLogicalLocCd());
			paramMap.put("mainGlCd", glDtl.getMainGlCd());
			paramMap.put("subGlCd", glDtl.getSubGlCd());
			paramMap.put("fiscalYr", fiscalYr);
			paramMap.put("month", dt.currentMonth);
			paramMap.put("amt", glDtl.getTxnAmt());
		//	paramMap.put("plCd", glDtl.getPlCd());

			if (glDtl.getPlCd() != null) {
				paramMap.put("plCd", glDtl.getPlCd());
			} else {
				glDtl.setPlCd("");
				paramMap.put("plCd", "");
			}

//			paramMap.put("srNO", glDtl.getSrNo());
			Integer EntryStat = this.glbrbalnNo(glDtl, dt.currentMonth, fiscalYr, dt.currentMonth);
			log.info("Entry Stat is {}", EntryStat);
			log.info("Parameter is {}", paramMap);
			if (glDtl.getDrCrFlag().equalsIgnoreCase("Cr"))
				try {

					log.info("dr cr Flag of gl txn detail :: " ,glDtl.getDrCrFlag());

					rowNum += namedParameterJdbcOperations.update(GlTxnSqlQueries.UPDATE_CURR_CR_BAL, paramMap);
				} catch (Exception e) {
					log.error(
							"Exception in Updating current CR balance for GL detals : {} , Fical year : {} and transaction date : {}",
							glTxnDtls, fiscalYr, txnDt);
					log.error("Exception in GlTxnDaoImpl#updateDrCrBrbal {}", e.fillInStackTrace());
					throw new GLTxnDBFailedException("Failed to Update current CR balance" + e.getMessage());
				}
			else
				try {
					rowNum += namedParameterJdbcOperations.update(GlTxnSqlQueries.UPDATE_CURR_DR_BAL, paramMap);
				} catch (Exception e) {
					log.error(
							"Exception in Updating current DR balance for GL detals : {} , Fical year : {} and transaction date : {}",
							glTxnDtls, fiscalYr, txnDt);
					log.error("Exception in GlTxnDaoImpl#updateDrCrBrbal {}", e.fillInStackTrace());
					throw new GLTxnDBFailedException("Failed to Update current DR balance" + e.getMessage());
				}
		}
		return rowNum;
	}

	@Override
	public Integer updateDrCrBrbalYr(List<GlTxnDtl> glTxnDtls, String fiscalYr, LocalDate txnDt)
			throws InvalidGLTxnDataException {
		log.info("Inside GlTxnDaoImpl#updateDrCrBrbalYr");
		int rowNum = 0;
		DateOperation dt = new DateOperation(txnDt.getMonthValue());
		for (GlTxnDtl glDtl : glTxnDtls) {
			Map<String, Object> paramMap = GlTxnSqlQueries.getParamMapForDtl(glDtl);
			paramMap.put("logicalLoc", glDtl.getLogicalLocCd());
			paramMap.put("mainGlCd", glDtl.getMainGlCd());
			paramMap.put("subGlCd", glDtl.getSubGlCd());
			paramMap.put("fiscalYr", fiscalYr);
			// paramMap.put("month",dt.currentMonth);
			paramMap.put("amt", glDtl.getTxnAmt());
			paramMap.put("plCd", glDtl.getPlCd());
//			paramMap.put("srNO", glDtl.getSrNo());
			Integer entryStat = this.glbrbalnNo(glDtl, dt.currentMonth, fiscalYr, null);
			log.info("Entry Stat is {}", entryStat);
			if (glDtl.getDrCrFlag().equalsIgnoreCase("Cr"))
				try {
					log.info("dr cr Flag of gl txn detail :: {} ", glDtl.getDrCrFlag());

					rowNum += namedParameterJdbcOperations.update(GlTxnSqlQueries.UPDATE_CURR_CR_BAL_YR, paramMap);
				} catch (Exception e) {
					log.error(
							"Exception in Updating current CR balance for GL detals : {} , Fical year : {} and transaction date : {}",
							glTxnDtls, fiscalYr, txnDt);
					log.error("Exception in GlTxnDaoImpl#updateDrCrBrbal {}", e.fillInStackTrace());
					throw new GLTxnDBFailedException("Failed to Update current CR balance" + e.getMessage());
				}
			else
				try {
					rowNum += namedParameterJdbcOperations.update(GlTxnSqlQueries.UPDATE_CURR_DR_BAL_YR, paramMap);
				} catch (Exception e) {
					log.error(
							"Exception in Updating current DR Year balance for GL detals : {} , Fical year : {} and transaction date : {}",
							glTxnDtls, fiscalYr, txnDt);
					log.error("Exception in GlTxnDaoImpl#updateDrCrBrbal {}", e.fillInStackTrace());
					throw new GLTxnDBFailedException("Failed to Update current DR balance" + e.getMessage());
				}
		}
		return rowNum;
	}

	@Override
	public BigDecimal getTotalAmt(String mainGlCd, String subGlCd) {
		log.info("Inside GlTxnDaoImpl#getTotalAmt");
		Map<String, Object> paramMap = new HashMap<>();
		BigDecimal amount;
		paramMap.put("mainGlCd", mainGlCd);
		paramMap.put("subGlCd", subGlCd);
		if (entitySrvice.findGLByGLCode(mainGlCd, subGlCd) != null) {
			try {
				amount = namedParameterJdbcTemplate.queryForObject(GlTxnSqlQueries.GET_TOTAL_DEBIT_BAL, paramMap,
						new RowMapper<BigDecimal>() {
							@Override
							public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
								return rs.getBigDecimal("sum");
							}
						});
			} catch (Exception e) {
				log.info("Exception in GlTxnDaoImpl#getTotalAmt {}" ,e);
				throw new GLTxnDBFailedException("Failed to get total DR balance : " + e.fillInStackTrace());
			}
		} else {
			log.info("Inside  GlTxnDaoImpl#getTotalAmt else block");
			log.info("NO entity GL code found for Main GL Code : {} and Sub GL COde : {} ", mainGlCd, subGlCd);
			throw new GLCodeNotFoundException("Exception in GlTxnDaoImpl#getTotalAmt ");
		}
		amount = new BigDecimal(0);
		return amount;
	}

	@Override
	public BigDecimal getTotalCreditAmt(String mainGlCd, String subGlCd) {
		log.info("Inside GlTxnDaoImpl#getTotalCreditAmt");
		Map<String, Object> paramMap = new HashMap<>();
		BigDecimal amount;
		paramMap.put("mainGlCd", mainGlCd);
		paramMap.put("subGlCd", subGlCd);
		if (entitySrvice.findGLByGLCode(mainGlCd, subGlCd) != null) {
			try {
				amount = namedParameterJdbcTemplate.queryForObject(GlTxnSqlQueries.GET_TOTAL_CREDIT_BAL, paramMap,
						new RowMapper<BigDecimal>() {
							@Override
							public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
								return rs.getBigDecimal("sum");
							}
						});
			} catch (Exception e) {
				log.info("Exception in GlTxnDaoImpl#getTotalCreditAmt {}",e);
				throw new GLTxnDBFailedException("Failed to get total DR balance : " + e.fillInStackTrace());
			}
		} else {
			log.info("Inside  GlTxnDaoImpl#getTotalAmt else block");
			log.info("NO entity GL code found for Main GL Code : {} and Sub GL COde : {} ", mainGlCd, subGlCd);
			throw new GLCodeNotFoundException("Exception in GlTxnDaoImpl#getTotalCreditAmt ");
		}
		return amount;
	}

	@Override
	public Integer deleteGlTxnDtl(Integer glTxnNo) {

		log.info("Inside GlTxnDaoImpl#deleteGlTxnDtl");
		try {
			Map<String, Object> param = new HashMap<String, Object>();

			param.put("glTxnNo", glTxnNo);

			return namedParameterJdbcTemplate.update(GlTxnSqlQueries.ADD_GL_TXN_DTL, param);

		} catch (Exception e) {
			log.error("Exception in Delete GL Record from GlTxnDtl for GLTxn No :{} ", glTxnNo);
			log.error("Exception in GlTxnDaoImpl#deleteGlTxnDtl {}", e.fillInStackTrace());
			throw new GLTxnDBFailedException("Failed to Delete GL Record from GlTxnDtl" + e.getMessage());
		}
	}

	@Override
	public Integer deleteGlTxnHdr(Integer glTxnNo) {
		log.info("Inside GlTxnDaoImpl#deleteGlTxnHdr");
		try {
			Map<String, Object> param = new HashMap<String, Object>();

			param.put("glTxnNo", glTxnNo);

			return namedParameterJdbcTemplate.update(GlTxnSqlQueries.ADD_GL_TXN_HDR, param);

		} catch (Exception e) {
			log.error("Exception in Delete GL Record from GlTxnHdr for GLTxn No :{} ", glTxnNo);
			log.error("Exception in GlTxnDaoImpl#deleteGlTxnHdr {}", e.fillInStackTrace());
			throw new GLTxnDBFailedException("Failed to Delete GL Record from GlTxnHdr" + e.getMessage());
		}
	}

	@Override
	public List<GlTxnHdr> getAllGltxnByFromDtLoc(LocalDate fromDt, LocalDate toDt, String logicallocation) {
		log.info("Inside GlTxnDaoImpl#getAllGltxnByFromDtLoc");
		List<GlTxnHdr> glTxnHdrs = null;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("fromDt", fromDt);
		paramMap.put("toDt", toDt);
		paramMap.put("logicalLoc", logicallocation);

		try {
			// RowMapper<GlTxnHdr> rowMapper= GlTxnSqlQueries::mapRow;
			glTxnHdrs = namedParameterJdbcTemplate.query(GlTxnSqlQueries.GET_TXN_BY_FROM_TO_DT_AND_LOC, paramMap,
					new RowMapper<GlTxnHdr>() {

						@Override
						public GlTxnHdr mapRow(ResultSet rs, int rowNum) throws SQLException {
							GlTxnHdr glTxnHdr = new GlTxnHdr();
							glTxnHdr.setEntityCd(rs.getString("entity_cd"));
							glTxnHdr.setGlTxnNo(rs.getInt("gl_txn_no"));
							glTxnHdr.setGlTxnType(rs.getString("gl_txn_type"));
							glTxnHdr.setLogicalLocCd(rs.getString("logical_loc_cd"));
							if (rs.getDate("txn_dt") != null)
								glTxnHdr.setTxnDt(rs.getDate("txn_dt").toLocalDate());
							glTxnHdr.setReference(rs.getString("reference"));
							glTxnHdr.setRevarsalGlTxnType(rs.getString("reversal_txn_type"));
							glTxnHdr.setRevarsalGlTxnNo(rs.getInt("reversal_txn_no"));
							glTxnHdr.setReversalReason(rs.getString("reversal_reason"));
							glTxnHdr.setFiscalYr(rs.getString("fiscal_yr"));
							glTxnHdr.setCreatedBy(rs.getString("created_by"));
							glTxnHdr.setCreatedDt(rs.getDate("created_dt"));
							glTxnHdr.setLastUpdatedBy(rs.getString("last_updated_by"));
							glTxnHdr.setLastUpdatedDt(rs.getDate("last_updated_dt"));
							glTxnHdr.setMetaRemarks(rs.getString("meta_remarks"));
							glTxnHdr.setMetaStatus(rs.getString("meta_status"));
							return glTxnHdr;
						}
					});

		} catch (Exception e) {
			log.error("Exception in View GL Entry from GlTxnHdr by LogicalLocation {} And Date between : {} and {} ",
					logicallocation, fromDt, toDt);
			log.error("Exception in GlTxnDaoImpl#getAllGltxnByFromDtLoc {}", e.fillInStackTrace());
			throw new GLCodeNotFoundException("Exception in GlTxnDaoImpl#getAllGltxnByFromDtLoc ");
		}
		return glTxnHdrs;
	}

	@Override
	public List<GlTxnHdr> getAllGlTxnByTxnNoTxnTypeLoc(Integer glTxnNo, String glTxnType, String logicalLoc) {
		log.info("Inside GlTxnDaoImpl#getAllGlTxnByTxnNoTxnTypeLoc");
		List<GlTxnHdr> glTxnHdrs = null;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("glTxnNo", glTxnNo);
		paramMap.put("glTxnType", glTxnType);
		paramMap.put("logicalLoc", logicalLoc);

		try {
			glTxnHdrs = namedParameterJdbcTemplate.query(GlTxnSqlQueries.GET_ALL_GL_TXN_BY_TXNNO_TXNTYPE_LOC, paramMap,
					new RowMapper<GlTxnHdr>() {

						@Override
						public GlTxnHdr mapRow(ResultSet rs, int rowNum) throws SQLException {
							GlTxnHdr glTxnHdr = new GlTxnHdr();
							glTxnHdr.setEntityCd(rs.getString("entity_cd"));
							glTxnHdr.setGlTxnNo(rs.getInt("gl_txn_no"));
							glTxnHdr.setGlTxnType(rs.getString("gl_txn_type"));
							glTxnHdr.setLogicalLocCd(rs.getString("logical_loc_cd"));
							if (rs.getDate("txn_dt") != null)
								glTxnHdr.setTxnDt(rs.getDate("txn_dt").toLocalDate());
							glTxnHdr.setReference(rs.getString("reference"));
							glTxnHdr.setRevarsalGlTxnType(rs.getString("reversal_txn_type"));
							glTxnHdr.setRevarsalGlTxnNo(rs.getInt("reversal_txn_no"));
							glTxnHdr.setReversalReason(rs.getString("reversal_reason"));
							glTxnHdr.setFiscalYr(rs.getString("fiscal_yr"));
							glTxnHdr.setCreatedBy(rs.getString("created_by"));
							glTxnHdr.setCreatedDt(rs.getDate("created_dt"));
							glTxnHdr.setLastUpdatedBy(rs.getString("last_updated_by"));
							glTxnHdr.setLastUpdatedDt(rs.getDate("last_updated_dt"));
							glTxnHdr.setMetaRemarks(rs.getString("meta_remarks"));
							glTxnHdr.setMetaStatus(rs.getString("meta_status"));
							return glTxnHdr;

						}
					});
		} catch (Exception e) {
			log.error("Exception in view all GL Record from GlTxnHdr for GLTxn No  :{}, GLTxn Type :{},Location :{} ",
					glTxnNo, glTxnType, logicalLoc);
			log.error("Exception in GlTxnDaoImpl#deleteGlTxnHdr {}", e.fillInStackTrace());
		}

		if (glTxnHdrs == null) {
			log.error("Empty list found for gltxnno {}, gltxntype {} and gltaxno {}", glTxnNo, glTxnType, logicalLoc);
			return glTxnHdrs;
		} else {
			return glTxnHdrs;
		}

	}

	public Integer glbrbalnNo(GlTxnDtl glDtl, String dt, String fiscalYr, String month) {

		log.info("Inside GlTxnDaoImpl#glbrbalnNo");
		Map<String, Object> mapQ = new HashMap<>();
		mapQ.put("logicalLoc", glDtl.getLogicalLocCd());
		mapQ.put("mainGlCd", glDtl.getMainGlCd());
		mapQ.put("subGlCd", glDtl.getSubGlCd());
		mapQ.put("fiscalYr", fiscalYr);
		mapQ.put("month", month);
		mapQ.put("amt", glDtl.getTxnAmt());
		mapQ.put("plCd", glDtl.getPlCd());
	//	mapQ.put("srNO", glDtl.getSrNo());
		mapQ.put("user", userInfoService.getUser());

		log.info("Para Map {}", mapQ);
		String sql_for_checking = null;
		if (month != null) {
			sql_for_checking = GlTxnSqlQueries.CHECK_GL_BR_ENRTY;
			log.info("BL MONTH CHECK");
		} else {
			sql_for_checking = GlTxnSqlQueries.CHECK_GL_BR_YR_ENRTY;
		}

		try {
			List<String> Validrows = null;
			Validrows = namedParameterJdbcOperations.query(sql_for_checking, mapQ, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("entity_cd");
				}
			});
			log.info("List of str {}", Validrows);
			if (Validrows != null && Validrows.size() != 0) {
				return 1;
			} else {
				if (month != null) {
					log.info("BL MONTH CHECK Seq no");
					Integer num = namedParameterJdbcOperations.update(GlTxnSqlQueries.INSERT_CURR_CR_BAL, mapQ);
					return null;
				} else {
					Integer num = namedParameterJdbcOperations.update(GlTxnSqlQueries.INSERT_CURR_CR_BAL_YR, mapQ);
					return null;
				}
			}
		} catch (Exception e) {
			log.info("Exception in Inserting Row in Balance Table : {}", e);
			return 1;
		}
	}

	@Override
	public GlBrBalanceOut getBrBalance(GlBrBalanceIn balanceIn) {
		log.info("Inside GlTxnDaoImpl#getBrBalance");
		Map<String, Object> paramaP = new HashMap<>();
		paramaP.put("mainGlCd", balanceIn.getMainglCd());
		paramaP.put("subGlCd", balanceIn.getSubglCd());
		paramaP.put("plCd", balanceIn.getPersonalLedgerCd());
		FiscalYearModel currfisyr = fiscalYearService.findCurrentFiscalYear();
		paramaP.put("fiscalYr", currfisyr.getCurrFiscalYear());
		paramaP.put("logicalLoc", balanceIn.getLogicallocCd());
		GlBrBalanceOut balDetails = new GlBrBalanceOut();
		try {

			balDetails = namedParameterJdbcOperations.queryForObject(GlTxnSqlQueries.GET_GL_BR_BAL_YR, paramaP,
					new RowMapper<GlBrBalanceOut>() {
						@Override
						public GlBrBalanceOut mapRow(ResultSet rs, int rowNum) throws SQLException {
							GlBrBalanceOut balanceOut = new GlBrBalanceOut();
							balanceOut.setLogicallocCd(rs.getString("logicalloc_cd"));
							balanceOut.setFiscalYr(rs.getString("fiscal_yr"));
							balanceOut.setMainglCd(rs.getString("maingl_cd"));
							balanceOut.setSubglCd(rs.getString("subgl_cd"));
							balanceOut.setPersonalLedgerCd(rs.getString("personal_ledger_cd"));
							balanceOut.setOpenDrBal(rs.getDouble("open_dr_bal"));
							balanceOut.setOpenCrBal(rs.getDouble("open_cr_bal"));
							balanceOut.setLastCrBal(rs.getDouble("last_cr_bal"));
							balanceOut.setLastDrBal(rs.getDouble("last_dr_bal"));
							return balanceOut;
						}
					});

			String glType = namedParameterJdbcOperations.queryForObject(GlTxnSqlQueries.GET_TXN_TYPE_GLCODE, paramaP,
					new RowMapper<String>() {
						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							String res = rs.getString("gl_type");
							return res;
						}
					});
			double totalCr = 0;
			double totalDr = 0;
			totalCr = balDetails.getLastCrBal();
			totalDr = balDetails.getLastDrBal();
			double balance = 0;
			log.info("GL TYPE : ----{}", glType);
			if (glType.equals("ASST") || glType.equals("EXPD")) {
				balance = totalDr - totalCr;
			}
			if (glType.equals("LIAB") || glType.equals("INCM")) {
				balance = totalCr - totalDr;
			}
			balDetails.setGlType(glType);
			balDetails.setBalance(balance);
			// To be checked logic
			return balDetails;
		} catch (Exception e) {
			// log.error("Exception In Getting Balance {}", e);
		}
		return balDetails;
	}

	@Override
	public List<GLReport> getTransactionList(GLReportIn glReportIn) {
		log.info("Inside GlTxnDaoImpl#getTransactionList");
		List<GLReport> glReports = new ArrayList<GLReport>();

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("mainGl",glReportIn.getMainGl());
		paramMap.put("subGl",glReportIn.getSubGl());
		paramMap.put("plCd",glReportIn.getPlCd());
		paramMap.put("logLocCd",glReportIn.getLogicalLocCd());
		paramMap.put("subBfCd",glReportIn.getSubBfCd());
		paramMap.put("fromDt",glReportIn.getFromDt());
		paramMap.put("toDt",glReportIn.getToDt());

		try {
			glReports = namedParameterJdbcTemplate.query(GlTxnSqlQueries.GET_POLICY_REPORT, paramMap,
					new RowMapper<GLReport>() {

						@Override
						public GLReport mapRow(ResultSet rs, int rowNum) throws SQLException {

							GLReport glReport = new GLReport();

							glReport.setTxnDate(rs.getObject("txn_dt", LocalDate.class));
							glReport.setRemark(rs.getString("txn_remarks"));
							glReport.setTxnType(rs.getString("gl_txn_type"));
							glReport.setTxnAmt(rs.getBigDecimal("txn_amt"));
							glReport.setTxnNo(rs.getString("gl_txn_no"));
							glReport.setDrCrFlag(rs.getString("dr_cr_flg"));

							return glReport;
						}
					});
		} catch (DataAccessException e) {
			throw new RuntimeException("Failed to fetch GL transaction details : "+e.getMessage());
		}

		return glReports;
	}

	@Override
	public GLReportOut getPolicyReport(GLReportIn glReportIn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GlTxnHdr> getAllGlTxnDtl() {
		log.info("Inside GlTxnDaoImpl#getAllGlTxnDtl");
		List<GlTxnHdr> glTxnHdrs = null;
	
		

		RowMapper<GlTxnHdr> rowMapper = GlTxnSqlQueries::mapRow;
		glTxnHdrs = namedParameterJdbcTemplate.query(GlTxnSqlQueries.GET_ALL_GL_TXN_HDR_2, rowMapper);

		if (glTxnHdrs == null) {
			log.error("Empty list found");
			return glTxnHdrs;
		} else {
			return glTxnHdrs;
		}
	}

}

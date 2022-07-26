package in.ecgc.smile.erp.accounts.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.model.FTR;
import in.ecgc.smile.erp.accounts.model.FtrDtl;
import in.ecgc.smile.erp.accounts.util.FTRSqlQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;

@Repository
@Transactional
public class FTRDaoImpl implements FTRDao {

	@Autowired
	private UserInfoService userInfo;

	private JdbcOperations jdbcOperations;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private NamedParameterJdbcOperations namedParameterJdbcOperations;

	private static final Logger LOGGER = LoggerFactory.getLogger(FTRDaoImpl.class);

	public FTRDaoImpl(DataSource dataSource) {
		jdbcOperations = new JdbcTemplate(dataSource);
		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Integer addRequest(FTR ftr) {
		LOGGER.info("Inside FTRDaoImpl#addRequest");
		ftr.setEcgcStatus('V');
		try {
			Map<String, Object> FTRNamedParameters1 = new HashMap<String, Object>();

			FTRNamedParameters1.put("ftrReqNo", ftr.getFtrReqNo());
			FTRNamedParameters1.put("ftrReqDt", ftr.getFtrReqDt());
			FTRNamedParameters1.put("ftrReqBranchCd", ftr.getFtrReqBranchCd());
			FTRNamedParameters1.put("ftrReqBy", ftr.getFtrReqBy());
			FTRNamedParameters1.put("ftrReqDeptCd", ftr.getFtrReqDeptCd());
			FTRNamedParameters1.put("ftrReqStatus", ftr.getFtrReqStatus());
			FTRNamedParameters1.put("logicalLocCd", ftr.getLogicalLocCode());
			FTRNamedParameters1.put("ftrApprBy", ftr.getFtrApprBy());
			FTRNamedParameters1.put("ftrTrfDt", ftr.getFtrTrfDt());
			FTRNamedParameters1.put("ftrTrfAmt", ftr.getFtrTrfAmt());
			FTRNamedParameters1.put("pvStatus", ftr.getPvStatus());
			FTRNamedParameters1.put("updatedBy", ftr.getUpdatedBy());
			FTRNamedParameters1.put("updatedOn", ftr.getUpdatedOn());
			FTRNamedParameters1.put("ecgcStatus", ftr.getEcgcStatus());
			FTRNamedParameters1.put("metaRemarks", ftr.getMetaRemarks());
			FTRNamedParameters1.put("createdOn", ftr.getCreatedOn());
			FTRNamedParameters1.put("createdBy", ftr.getCreatedBy());
			int rowCount = namedParameterJdbcTemplate.update(FTRSqlQueries.ADD_FTR, FTRNamedParameters1);
			if (rowCount == 1) {
				return 1;
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured {}", e.getMessage());
		}
		return -1;

	}

	@Override
	public Integer addFtrDetail(FtrDtl ftr) {
		LOGGER.info("Inside FTRDaoImpl#addFtrDetail");
		Map<String, Object> FTRNamedParameters = new HashMap<String, Object>();
		FTRNamedParameters.put("FTRRequestNo", ftr.getFTRRequestNo());
		FTRNamedParameters.put("FTRRequestAmount", ftr.getFTRRequestAmount());
		FTRNamedParameters.put("FTRRequestType", ftr.getFTRRequestType());
		FTRNamedParameters.put("FTRRequestReason", ftr.getFTRRequestReason());
		FTRNamedParameters.put("ecibIntlBankName", ftr.getEcibIntlBankNAme());
		FTRNamedParameters.put("ecibClaimNo", ftr.getEcibClaimNo());
		FTRNamedParameters.put("ecibType", ftr.getEcibType());
		FTRNamedParameters.put("expPolicyDatePAyment", ftr.getExpPolicyDatePAyment());
		FTRNamedParameters.put("expEcibDatePayment", ftr.getExpEcibDatePayment());
		FTRNamedParameters.put("policyType", ftr.getPolicyType());
		FTRNamedParameters.put("policyClaimNo", ftr.getPolicyClaimNo());
		FTRNamedParameters.put("exporterName", ftr.getExporterName());
		FTRNamedParameters.put("ieCode", ftr.getIeCode());
		FTRNamedParameters.put("FTRRequestSrNo", ftr.getFTRRequestSrNo());
		FTRNamedParameters.put("branchCode", ftr.getBranchCode());
		FTRNamedParameters.put("logicalLocCode", ftr.getLogicalLocCode());
		FTRNamedParameters.put("updatedBy", ftr.getUpdatedBy());
		FTRNamedParameters.put("updatedOn", ftr.getUpdatedOn());
		FTRNamedParameters.put("ecgcStatus", ftr.getEcgcStatus());
		FTRNamedParameters.put("metaRemark", ftr.getMetaRemarks());
		FTRNamedParameters.put("createdOn", ftr.getCreatedOn());
		FTRNamedParameters.put("createdBy", ftr.getCreatedBy());
		int rowCount = namedParameterJdbcTemplate.update(FTRSqlQueries.ADD_FTR_DTL1, FTRNamedParameters);
		if (rowCount == 1)
			return 1;
		return 0;
	}

	@Override
	public List<FTR> getAllFTRRequest() {
		LOGGER.info("Inside FTRDaoImpl#getAllFTRRequest");
		List<FTR> ftr = new ArrayList<FTR>();
		ftr = namedParameterJdbcTemplate.query(FTRSqlQueries.VIEW_ALL_FTR, new RowMapper<FTR>() {

			@Override
			public FTR mapRow(ResultSet rs, int rowNum) throws SQLException {
				FTR ftrTemp = new FTR();
				ftrTemp.setFtrReqNo(rs.getInt("ftr_req_no"));
				ftrTemp.setFtrReqDt(rs.getDate("ftr_req_dt").toLocalDate());
				ftrTemp.setFtrReqBy(rs.getInt("ftr_req_by"));
				ftrTemp.setFtrReqBranchCd(rs.getString("ftr_req_branch_cd"));
				ftrTemp.setFtrReqDeptCd(rs.getString("ftr_req_dept_cd"));
				ftrTemp.setFtrReqStatus(rs.getString("ftr_req_status"));
				ftrTemp.setLogicalLocCode(rs.getString("logicalloc_cd"));
				ftrTemp.setFtrApprBy(rs.getInt("ftr_appr_by"));
				if (rs.getDate("ftr_trf_dt") != null)
					ftrTemp.setFtrTrfDt(rs.getDate("ftr_trf_dt").toLocalDate());
				ftrTemp.setFtrTrfAmt(rs.getDouble("ftr_trf_amt"));
				ftrTemp.setPvStatus(rs.getString("pv_status"));
				return ftrTemp;
			}
		});
		return ftr;

	}

	@Override
	public List<FTR> getAllPendingFTRRequest() {
		LOGGER.info("Inside FTRDaoImpl#getAllPendingFTRRequest");
		List<FTR> ftr = new ArrayList<FTR>();
		ftr = namedParameterJdbcTemplate.query(FTRSqlQueries.VIEW_ALL_PENDING_FTR, new RowMapper<FTR>() {

			@Override
			public FTR mapRow(ResultSet rs, int rowNum) throws SQLException {
				FTR ftrTemp = new FTR();
				ftrTemp.setFtrReqNo(rs.getInt("ftr_req_no"));
				if (rs.getDate("ftr_req_dt") != null)
					ftrTemp.setFtrReqDt(rs.getDate("ftr_req_dt").toLocalDate());
				ftrTemp.setFtrReqBy(rs.getInt("ftr_req_by"));
				ftrTemp.setFtrReqBranchCd(rs.getString("ftr_req_branch_cd"));
				ftrTemp.setFtrReqDeptCd(rs.getString("ftr_req_dept_cd"));
				ftrTemp.setFtrReqStatus(rs.getString("ftr_req_status"));
				ftrTemp.setLogicalLocCode(rs.getString("logicalloc_cd"));
				ftrTemp.setFtrApprBy(rs.getInt("ftr_appr_by"));
				if (rs.getDate("ftr_trf_dt") != null)
					ftrTemp.setFtrTrfDt(rs.getDate("ftr_trf_dt").toLocalDate());
				ftrTemp.setFtrTrfAmt(rs.getDouble("ftr_trf_amt"));
				ftrTemp.setPvStatus(rs.getString("pv_status"));
				return ftrTemp;
			}
		});
		return ftr;

	}

	@Override
	public List<FTR> getAllFTRRequest(String logicalLoc) {
		LOGGER.info("Inside FTRDaoImpl#getAllFTRRequest");
		List<FTR> ftr = new ArrayList<FTR>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("logicalLoc", logicalLoc);
		ftr = namedParameterJdbcOperations.query(FTRSqlQueries.VIEW_ALL_FTR_BRANCH, paramMap, new RowMapper<FTR>() {

			@Override
			public FTR mapRow(ResultSet rs, int rowNum) throws SQLException {
				FTR ftrTemp = new FTR();
				ftrTemp.setFtrReqNo(rs.getInt("ftr_req_no"));
				if (rs.getDate("ftr_req_dt") != null)
					ftrTemp.setFtrReqDt(rs.getDate("ftr_req_dt").toLocalDate());
				ftrTemp.setFtrReqBy(rs.getInt("ftr_req_by"));
				ftrTemp.setFtrReqBranchCd(rs.getString("ftr_req_branch_cd"));
				ftrTemp.setFtrReqDeptCd(rs.getString("ftr_req_dept_cd"));
				ftrTemp.setFtrReqStatus(rs.getString("ftr_req_status"));
				ftrTemp.setLogicalLocCode(rs.getString("logicalloc_cd"));
				ftrTemp.setFtrApprBy(rs.getInt("ftr_appr_by"));
				if (rs.getDate("ftr_trf_dt") != null)
					ftrTemp.setFtrTrfDt(rs.getDate("ftr_trf_dt").toLocalDate());
				ftrTemp.setFtrTrfAmt(rs.getDouble("ftr_trf_amt"));
				ftrTemp.setPvStatus(rs.getString("pv_status"));
				return ftrTemp;
			}
		});
		return ftr;

	}

	@Override
	public List<FtrDtl> getFTRRequestDtl(String ftrId) {
		LOGGER.info("Inside FTRDaoImpl#getFTRRequestDtl");
		List<FtrDtl> ftr = new ArrayList<FtrDtl>();
		ftr = jdbcOperations.query(FTRSqlQueries.GET_FTR_DTL, new Object[] { ftrId }, new RowMapper<FtrDtl>() {

			@Override
			public FtrDtl mapRow(ResultSet rs, int rowNum) throws SQLException {
				FtrDtl ftrTemp = new FtrDtl();
				ftrTemp.setFTRRequestNo(rs.getInt("ftr_req_no"));
				ftrTemp.setFTRRequestAmount(rs.getFloat("ftr_req_amt"));
				ftrTemp.setFTRRequestType(rs.getString("ftr_req_type"));
				ftrTemp.setFTRRequestReason(rs.getString("ftr_req_reason"));
				ftrTemp.setEcibIntlBankNAme(rs.getString("ecib_intlbnk_name"));
				ftrTemp.setEcibClaimNo(rs.getString("ecib_claim_no"));
				if (rs.getDate("exp_eci_date_pym") != null)
					ftrTemp.setExpEcibDatePayment(rs.getDate("exp_eci_date_pym").toLocalDate());
				ftrTemp.setEcibType(rs.getString("ecib_type"));
				if (rs.getDate("exp_pol_date_pym") != null)
					ftrTemp.setExpPolicyDatePAyment(rs.getDate("exp_pol_date_pym").toLocalDate());
				ftrTemp.setPolicyClaimNo(rs.getString("pol_claim_no"));
				ftrTemp.setPolicyType(rs.getString("pol_type"));
				ftrTemp.setExporterName(rs.getString("expo_name"));
				ftrTemp.setIeCode(rs.getString("ie_cd"));
				// ftrTemp.setLogicalLocCode(rs.getString("logical_loc_cd"));
				// ftrTemp.setBranchCode(rs.getString("branch_cd"));
				ftrTemp.setFTRRequestSrNo(rs.getInt("ftr_req_srno"));

				return ftrTemp;
			}
		});

		return null;
	}

	@Override
	public Integer decisionOnFTRRequest(FTR ftr) {
		LOGGER.info("Inside FTRDaoImpl#decisionOnFTRRequest");
		Map<String, Object> FTRNamedParameters = new HashMap<String, Object>();
		FTRNamedParameters.put("ftrReqNo",ftr.getFtrReqNo());
		FTRNamedParameters.put("ftrReqStatus",ftr.getFtrReqStatus());
		FTRNamedParameters.put("pvStatus",ftr.getPvStatus());
		if(ftr.getMetaRemarks() != null)
			FTRNamedParameters.put("ftrReqStatus",ftr.getFtrReqStatus().charAt(0));
		FTRNamedParameters.put("metaRemarks",ftr.getMetaRemarks());
		FTRNamedParameters.put("apprBy",ftr.getFtrApprBy());
		int rowCount = namedParameterJdbcTemplate.update(FTRSqlQueries.DECISION_ON_FTR,FTRNamedParameters);
		if(rowCount < 1)
			return -1;
		return 1;
	}

	public FTR findFtrDtl(String ftrid) {

		LOGGER.info("inside FTR Detail");
		FTR ftr = findFtrById(ftrid);
		List<FtrDtl> ftrDtl = null;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ftrRequestNo", ftrid);

		try {
			ftrDtl = namedParameterJdbcOperations.query(FTRSqlQueries.GET_FTR_DTL.concat(ftrid),
					new RowMapper<FtrDtl>() {

						@Override
						public FtrDtl mapRow(ResultSet rs, int rowNum) throws SQLException {
							FtrDtl ftrTemp = new FtrDtl();
							ftrTemp.setCreatedOn(rs.getDate("created_dt"));
							ftrTemp.setUpdatedOn(rs.getDate("last_updated_dt"));
							ftrTemp.setFTRRequestNo(rs.getInt("ftr_req_no"));
							ftrTemp.setFTRRequestAmount(rs.getFloat("ftr_req_amt"));
							ftrTemp.setFTRRequestType(rs.getString("ftr_req_type"));
							ftrTemp.setFTRRequestReason(rs.getString("ftr_req_reason"));
							ftrTemp.setEcibIntlBankNAme(rs.getString("ecib_intlbnk_name"));
							ftrTemp.setEcibClaimNo(rs.getString("ecib_claim_no"));
							if (rs.getDate("exp_eci_date_pym") != null)
								ftrTemp.setExpEcibDatePayment(rs.getDate("exp_eci_date_pym").toLocalDate());
							ftrTemp.setEcibType(rs.getString("ecib_type"));
							if (rs.getDate("exp_pol_date_pym") != null)
								ftrTemp.setExpPolicyDatePAyment(rs.getDate("exp_pol_date_pym").toLocalDate());
							ftrTemp.setPolicyClaimNo(rs.getString("pol_claim_no"));
							ftrTemp.setPolicyType(rs.getString("pol_type"));
							ftrTemp.setExporterName(rs.getString("expo_name"));
							ftrTemp.setIeCode(rs.getString("ie_cd"));
							// ftrTemp.setLogicalLocCode(rs.getString("logical_loc_cd"));
							ftrTemp.setFTRRequestSrNo(rs.getInt("ftr_req_srno"));
							ftrTemp.setMetaRemarks(rs.getString("meta_remarks"));

							return ftrTemp;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			ftr = null;
			LOGGER.error("Exception Occured {}", e);
		} catch (Exception e) {
			LOGGER.error("Exception Occured {}", e);
			ftr = null;
		}
		ftr.setFtrDtl(ftrDtl);
		return ftr;
	}

	@Override
	public FTR findFtrById(String ftrReqNo) {
		LOGGER.info("Inside FTRDaoImpl#findFtrById");
		FTR ftr;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ftrReqNo", Integer.parseInt(ftrReqNo));
		LOGGER.info("FTR Req No {}", ftrReqNo);

		try {
			ftr = namedParameterJdbcOperations.queryForObject(FTRSqlQueries.GET_FTR_HDR, paramMap,
					new RowMapper<FTR>() {

						@Override
						public FTR mapRow(ResultSet rs, int rowNum) throws SQLException {
							FTR ftrTemp = new FTR();
							ftrTemp.setCreatedOn(rs.getDate("created_dt"));
							ftrTemp.setUpdatedOn(rs.getDate("last_updated_dt"));
							ftrTemp.setFtrReqNo(rs.getInt("ftr_req_no"));
							if (rs.getDate("ftr_req_dt") != null)
								ftrTemp.setFtrReqDt(rs.getDate("ftr_req_dt").toLocalDate());
							ftrTemp.setFtrReqBy(rs.getInt("ftr_req_by"));
							ftrTemp.setFtrReqBranchCd(rs.getString("ftr_req_branch_cd"));
							ftrTemp.setFtrReqDeptCd(rs.getString("ftr_req_dept_cd"));
							ftrTemp.setFtrReqStatus(rs.getString("ftr_req_status"));
							ftrTemp.setLogicalLocCode(rs.getString("logicalloc_cd"));
							ftrTemp.setFtrApprBy(rs.getInt("ftr_appr_by"));
							if (rs.getDate("ftr_trf_dt") != null)
								ftrTemp.setFtrTrfDt(rs.getDate("ftr_trf_dt").toLocalDate());
							ftrTemp.setFtrTrfAmt(rs.getDouble("ftr_trf_amt"));
							ftrTemp.setPvStatus(rs.getString("pv_status"));
							ftrTemp.setMetaRemarks(rs.getString("meta_remarks"));
							ftrTemp.setReqTo(rs.getString("req_to"));
							ftrTemp.setGltxnnoho(rs.getString("gl_txn_no_ho"));
							ftrTemp.setGltxnnobr(rs.getString("gl_txn_no_br"));

							return ftrTemp;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			ftr = null;
		}
		return ftr;
	}

	@Override
	public Integer updateFtrhdr(FTR ftr) {
		LOGGER.info("Inside FTRDaoImpl#updateFtrhdr");
		ftr.setEcgcStatus('V');
		Map<String, Object> FTRNamedParameters1 = new HashMap<String, Object>();
		try {
			FTRNamedParameters1.put("ftrReqNo", ftr.getFtrReqNo());
			FTRNamedParameters1.put("ftrReqDt", ftr.getFtrReqDt());
			FTRNamedParameters1.put("ftrReqBranchCd", ftr.getFtrReqBranchCd());
			FTRNamedParameters1.put("ftrReqBy", ftr.getFtrReqBy());
			FTRNamedParameters1.put("ftrReqDeptCd", ftr.getFtrReqDeptCd());
			FTRNamedParameters1.put("ftrReqStatus", ftr.getFtrReqStatus());
			FTRNamedParameters1.put("logicalLocCd", ftr.getLogicalLocCode());
			FTRNamedParameters1.put("ftrApprBy", ftr.getFtrApprBy());
			FTRNamedParameters1.put("ftrTrfDt", ftr.getFtrTrfDt());
			FTRNamedParameters1.put("ftrTrfAmt", ftr.getFtrTrfAmt());
			FTRNamedParameters1.put("pvStatus", ftr.getPvStatus());
			FTRNamedParameters1.put("updatedBy", ftr.getUpdatedBy());
			FTRNamedParameters1.put("updatedOn", ftr.getUpdatedOn());
			FTRNamedParameters1.put("ecgcStatus", ftr.getEcgcStatus());
			FTRNamedParameters1.put("metaRemarks", ftr.getMetaRemarks());
			FTRNamedParameters1.put("createdOn", ftr.getCreatedOn());
			FTRNamedParameters1.put("createdBy", ftr.getCreatedBy());
			int rowCount = namedParameterJdbcTemplate.update(FTRSqlQueries.UPDATE_FTR_HDR, FTRNamedParameters1);
			if (rowCount == 1) {
				return 1;
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured {}", e.getMessage());
		}
		return -1;

	}

	@Override
	public Integer updateFtrDtl(FtrDtl ftr) {
		LOGGER.info("Inside FTRDaoImpl#updateFtrDtl");
		Map<String, Object> FTRNamedParameters = new HashMap<String, Object>();
		try {
			FTRNamedParameters.put("FTRRequestNo", ftr.getFTRRequestNo());
			FTRNamedParameters.put("FTRRequestAmount", ftr.getFTRRequestAmount());
			FTRNamedParameters.put("FTRRequestType", ftr.getFTRRequestType());
			FTRNamedParameters.put("FTRRequestReason", ftr.getFTRRequestReason());
			FTRNamedParameters.put("ecibIntlBankName", ftr.getEcibIntlBankNAme());
			FTRNamedParameters.put("ecibClaimNo", ftr.getEcibClaimNo());
			FTRNamedParameters.put("ecibType", ftr.getEcibType());
			FTRNamedParameters.put("expPolicyDatePAyment", ftr.getExpPolicyDatePAyment());
			FTRNamedParameters.put("expEcibDatePayment", ftr.getExpEcibDatePayment());
			FTRNamedParameters.put("policyType", ftr.getPolicyType());
			FTRNamedParameters.put("policyClaimNo", ftr.getPolicyClaimNo());
			FTRNamedParameters.put("exporterName", ftr.getExporterName());
			FTRNamedParameters.put("ieCode", ftr.getIeCode());
			FTRNamedParameters.put("FTRRequestSrNo", ftr.getFTRRequestSrNo());
			// FTRNamedParameters.put("logicalLocCode",ftr.getLogicalLocCode());
			FTRNamedParameters.put("updatedBy", ftr.getUpdatedBy());
			FTRNamedParameters.put("updatedOn", ftr.getUpdatedOn());
			FTRNamedParameters.put("ecgcStatus", ftr.getEcgcStatus());
			FTRNamedParameters.put("metaRemark", ftr.getMetaRemarks());
			FTRNamedParameters.put("createdOn", ftr.getCreatedOn());
			FTRNamedParameters.put("createdBy", ftr.getCreatedBy());
			int rowCount = namedParameterJdbcTemplate.update(FTRSqlQueries.UPDATE_FTR_DTL, FTRNamedParameters);
			if (rowCount == 1)
				return 1;
			else
			{
				ftr.setCreatedOn(new Date());
				ftr.setUpdatedOn(new Date());
				return addFtrDetail(ftr);
				
			}
				
		} catch (Exception e) {
			LOGGER.error("Exception Occured {}", e);
			return 0;
		}
	}

	public Integer deteleFtrReq(int ftrNo) {
		LOGGER.info("Inside FTRDaoImpl#deteleFtrReq");
		Map<String, Object> FTRNamedParameters = new HashMap<String, Object>();
		FTRNamedParameters.put("ftrReqNo", ftrNo);
		Integer seq = namedParameterJdbcOperations.queryForObject(FTRSqlQueries.DELETE_DTL_REQUEST, FTRNamedParameters,
				new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						int num = rs.getInt(1);
						return num;
					}
				});
		return seq;

	}

	@Override
	public Integer getSeq() {
		LOGGER.info("Inside FTRDaoImpl#getSeq");
		Map<String, Object> FTRNamedParameters = new HashMap<String, Object>();
		FTRNamedParameters.put("seq_coloumn", "ecgc_acct_ftr_req_seq");
		Integer seq = namedParameterJdbcOperations.queryForObject(FTRSqlQueries.GET_SEQ, FTRNamedParameters,
				new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						int num = rs.getInt(1);
						return num;
					}
				});
		return seq;

	}

	@Override
	public Integer getMaxSrNo(int ftrNo) {
		LOGGER.info("Inside FTRDaoImpl#getMaxSrNo");
		Map<String, Object> FTRNamedParameters = new HashMap<String, Object>();
		FTRNamedParameters.put("ftrNo", ftrNo);
		try
		{
		Integer max = namedParameterJdbcOperations.queryForObject(FTRSqlQueries.GET_MAX_SRNO, FTRNamedParameters,
				new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						int num = rs.getInt(1);
						return num;
					}
				});
		return max;
		}
		catch (Exception e) {
			return new Integer(0);
		}

	}

	@Override
	public Integer deteleFtrReqDtl(int ftrNo, int srNo) {
		LOGGER.info("Inside FTRDaoImpl#deteleFtrReqDtl");
		Map<String, Object> FTRNamedParameters = new HashMap<String, Object>();
		try {
			FTRNamedParameters.put("ftrNo", ftrNo);
			FTRNamedParameters.put("srNo", srNo);
			int rowCount = namedParameterJdbcTemplate.update(FTRSqlQueries.DELETE_FTR_DTL, FTRNamedParameters);
			if (rowCount == 1)
				return 1;
			return 0;
		} catch (Exception e) {
			LOGGER.info("Exception Occured {}", e);
			return -1;
		}
	}

	@Override
	public List<FTR> getAllApprovedFTRRequest() {
		LOGGER.info("Inside FTRDaoImpl#getAllApprovedFTRRequest");
		List<FTR> ftr = new ArrayList<FTR>();
		ftr = namedParameterJdbcTemplate.query(FTRSqlQueries.VIEW_ALL_APPROVED_FTR, new RowMapper<FTR>() {

			@Override
			public FTR mapRow(ResultSet rs, int rowNum) throws SQLException {
				FTR ftrTemp = new FTR();
				ftrTemp.setFtrReqNo(rs.getInt("ftr_req_no"));
				if (rs.getDate("ftr_req_dt") != null)
					ftrTemp.setFtrReqDt(rs.getDate("ftr_req_dt").toLocalDate());
				ftrTemp.setFtrReqBy(rs.getInt("ftr_req_by"));
				ftrTemp.setFtrReqBranchCd(rs.getString("ftr_req_branch_cd"));
				ftrTemp.setFtrReqDeptCd(rs.getString("ftr_req_dept_cd"));
				ftrTemp.setFtrReqStatus(rs.getString("ftr_req_status"));
				ftrTemp.setLogicalLocCode(rs.getString("logicalloc_cd"));
				ftrTemp.setFtrApprBy(rs.getInt("ftr_appr_by"));
				if (rs.getDate("ftr_trf_dt") != null)
					ftrTemp.setFtrTrfDt(rs.getDate("ftr_trf_dt").toLocalDate());
				ftrTemp.setFtrTrfAmt(rs.getDouble("ftr_trf_amt"));
				ftrTemp.setPvStatus(rs.getString("pv_status"));
				return ftrTemp;
			}
		});
		return ftr;

	}

	@Override
	public Integer changeStatusToTransfer(String FTRId,String paymentdt, String remark, List<Integer> gltxnadd) {
		LOGGER.info("Inside FTRDaoImpl#changeStatusToTransfer");
		Integer count = 0;
		try
		{
		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(paymentdt);  
			    LOGGER.info("Payment Date {} - {}",paymentdt,date1);  
		Map<String, Object> updateParamStat = new HashMap<String, Object>();
		updateParamStat.put("ftrReqNo",Integer.parseInt(FTRId));
		updateParamStat.put("uName",userInfo.getUserId());
		updateParamStat.put("lastupdateddt",date1);
		updateParamStat.put("remark",remark);
		updateParamStat.put("hogl",gltxnadd.get(0));
		updateParamStat.put("brgl",gltxnadd.get(1));
		count = namedParameterJdbcTemplate.update(FTRSqlQueries.TRANSFER_FTR_REQ, updateParamStat);
		return count;
		}
		catch (Exception e) {
			LOGGER.error("Exception thrown- {}",e);
		}
		return count;
	}

	@Override
	public FTR generateExelFTR(String ftrs) {
			LOGGER.info("Inside FTRDaoImpl#generateExelFTR");
			FTR ftr;
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("ftrReqNo",Integer.parseInt(ftrs));

				try {
					ftr = namedParameterJdbcOperations.queryForObject(FTRSqlQueries.GET_FTR_HDR, paramMap,
							new RowMapper<FTR>() {

								@Override
								public FTR mapRow(ResultSet rs, int rowNum) throws SQLException {
									FTR ftrTemp = new FTR();
									ftrTemp.setFtrReqNo(rs.getInt("ftr_req_no"));
									if(rs.getDate("ftr_req_dt") != null)
									ftrTemp.setFtrReqDt(rs.getDate("ftr_req_dt").toLocalDate());
									ftrTemp.setFtrReqBy(rs.getInt("ftr_req_by"));
									ftrTemp.setFtrReqBranchCd(rs.getString("ftr_req_branch_cd"));
									ftrTemp.setFtrReqDeptCd(rs.getString("ftr_req_dept_cd"));
									ftrTemp.setFtrReqStatus(rs.getString("ftr_req_status"));
									ftrTemp.setLogicalLocCode(rs.getString("logicalloc_cd"));
									ftrTemp.setFtrApprBy(rs.getInt("ftr_appr_by"));
									if(rs.getDate("ftr_trf_dt") != null)
									ftrTemp.setFtrTrfDt(rs.getDate("ftr_trf_dt").toLocalDate());
									ftrTemp.setFtrTrfAmt(rs.getDouble("ftr_trf_amt"));
									ftrTemp.setPvStatus(rs.getString("pv_status"));
									return ftrTemp;			
								}
							});
				} catch (EmptyResultDataAccessException e) {
					ftr = null;
				}
				return ftr;
	}

}

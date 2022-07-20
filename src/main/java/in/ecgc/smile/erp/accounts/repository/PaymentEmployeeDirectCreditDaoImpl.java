package in.ecgc.smile.erp.accounts.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.exception.PaymentEmployeeDirectCreditException;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditDtl;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;
import in.ecgc.smile.erp.accounts.util.PaymentEmployeeDirectCreditSqlQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Repository
@Transactional

@Slf4j
public class PaymentEmployeeDirectCreditDaoImpl implements PaymentEmployeeDirectCreditDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcOperations jdbcOperations;
	private NamedParameterJdbcOperations namedParameterJdbcOperations;

	@Autowired
	private UserInfoService useInfoService;

	@Autowired
	public PaymentEmployeeDirectCreditDaoImpl(DataSource dataSource) {
		jdbcOperations = new JdbcTemplate(dataSource);
		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<PaymentEmployeeDirectCreditHdr> getPaymentAdviceByLogicalLoc(String logicalLoc) {
		log.info("Inside PaymentEmployeeDirectCreditDaoImpl#getPaymentAdviceByLogicalLoc");
		List<PaymentEmployeeDirectCreditHdr> pymtEmpList = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("logicalLoc", logicalLoc);

		try {
			RowMapper<PaymentEmployeeDirectCreditHdr> rowMapper = PaymentEmployeeDirectCreditSqlQueries::mapRow;
			pymtEmpList = namedParameterJdbcTemplate.query(PaymentEmployeeDirectCreditSqlQueries.VIEW_ALL_LOGICAL_LOC,
					paramMap, rowMapper);
			return pymtEmpList;
		} catch (DataAccessException e) {
			throw new PaymentEmployeeDirectCreditException();

//					return pymtEmpList;
		}
	}

	@Override
	public List<PaymentEmployeeDirectCreditHdr> viewAll() {
		log.info("Inside PaymentEmployeeDirectCreditDaoImpl#viewAll");
		RowMapper<PaymentEmployeeDirectCreditHdr> rowMapper = PaymentEmployeeDirectCreditSqlQueries::mapRow;
		List<PaymentEmployeeDirectCreditHdr> pymtEmpList = new ArrayList<PaymentEmployeeDirectCreditHdr>();

		try {
			pymtEmpList = namedParameterJdbcTemplate
					.query(PaymentEmployeeDirectCreditSqlQueries.VIEW_ALL_DIRECT_CREDITS, rowMapper);
			return pymtEmpList;
		} catch (Exception e) {
			pymtEmpList = null;
			log.info("Error Occured while fetching the data {}",e);
			return pymtEmpList;
		}

	}

	@Override
	public String addPaymentEmployment(PaymentEmployeeDirectCreditHdr payEmp) {
		log.info("Inside PaymentEmployeeDirectCreditDaoImpl#addPaymentEmployment");
		log.info("inside dao addPaymentEmployment: {}", payEmp);
		log.info("inside dao addPaymentEmployment: {}", payEmp.getRequestNo());

		Integer requestNo = null;

		Map<String, Object> paymntEmpl = PaymentEmployeeDirectCreditSqlQueries.getParamMapforHdr(payEmp);

		try {
			requestNo = namedParameterJdbcTemplate
					.update(PaymentEmployeeDirectCreditSqlQueries.ADD_PAYMENTEMPLOYMENTDIRECTCREDIT_HDR, paymntEmpl);
		} catch (Exception e) {
			throw new PaymentEmployeeDirectCreditException(
					"Failed to add new payment entry transaction detail : " + e.getMessage());
		}

		log.info("request no: {}", requestNo);
		if (requestNo > 0) {
			for (PaymentEmployeeDirectCreditDtl pymtEmpDtl : payEmp.getPymtEmpList()) {

				Map<String, Object> paramMapForDtl = PaymentEmployeeDirectCreditSqlQueries
						.getParamMapforDtl(pymtEmpDtl);
				paramMapForDtl.put("RequestNo", payEmp.getRequestNo());
				log.info("Inside dao DTL {}", paramMapForDtl);
				try {
					requestNo += namedParameterJdbcOperations.update(
							PaymentEmployeeDirectCreditSqlQueries.ADD_PAYMENTEMPLOYMENTDIRECTCREDIT_DTL,
							paramMapForDtl);
				} catch (Exception e) {
					throw new PaymentEmployeeDirectCreditException(
							"Failed to add new payment entry transaction detail : " + e.getMessage());
				}
			}
		}

		if (requestNo > 0) {
			return "in success : " + requestNo;
		} else {
			throw new PaymentEmployeeDirectCreditException("Failed to add new payment entry transaction detail :");
		}
	}

	@Override
	public String getRequestNo(String requestedLogicalLoc) {
		log.info("Inside PaymentEmployeeDirectCreditDaoImpl#getRequestNo");
		log.info("in dao getRequestNo");
		log.info("generating seqNo for logical loc {}" , requestedLogicalLoc);
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("requestedLogicalLoc", requestedLogicalLoc);
			String seqNo = namedParameterJdbcOperations.queryForObject(PaymentEmployeeDirectCreditSqlQueries.GET_SEQ_NO,
					param, new RowMapper<String>() {
						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString("seq_text");
						}

					});
			log.info("seqNo in dao getRequestNo {} " , seqNo);
			return seqNo;
		} catch (Exception e) {
			log.error("Exception in Generating RequestNo for requestedLogicalLoc {}" , requestedLogicalLoc);
			throw new PaymentEmployeeDirectCreditException("Failed to Generate Sequence Number" + e.getMessage());

		}
	}

	@Override
	public PaymentEmployeeDirectCreditHdr getPaymentEmpDirectCredit(@NotBlank String requestNo,
			@NotBlank String requestedLogicalLoc, String departmentCode) {

		log.info("inside  PaymentEmployeeDirectCreditDaoImpl#getPaymentEmpDirectCredit {} {} {}" ,requestNo, requestedLogicalLoc , departmentCode);
		PaymentEmployeeDirectCreditHdr payEmpHdr;

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("requestNo", requestNo);
		paramMap.put("requestedLogicalLoc", requestedLogicalLoc);
		String sql = " ";
		if (departmentCode.equals("null")) {
			sql = PaymentEmployeeDirectCreditSqlQueries.PYMT_EMP_HDR_VIEWALL_NULL;
		} else {
			sql = PaymentEmployeeDirectCreditSqlQueries.PYMT_EMP_HDR_VIEWALL;
			paramMap.put("departmentCode", departmentCode);
		}

		try {

			RowMapper<PaymentEmployeeDirectCreditHdr> rowMapper = PaymentEmployeeDirectCreditSqlQueries::mapRow;
			RowMapper<PaymentEmployeeDirectCreditDtl> rowMapperForDtl = PaymentEmployeeDirectCreditSqlQueries::mapRowDtl;
			payEmpHdr = namedParameterJdbcOperations.queryForObject(sql, paramMap, rowMapper);
			payEmpHdr.setPymtEmpList(namedParameterJdbcOperations
					.query(PaymentEmployeeDirectCreditSqlQueries.PYMT_EMP_DTL_VIEWALL, paramMap, rowMapperForDtl));
			return payEmpHdr;
		} catch (Exception e) {
			log.error("Exception in Generating RequestNo for requestedLogicalLoc {} ", requestedLogicalLoc);
			throw new PaymentEmployeeDirectCreditException("Failed to Generate Sequence Number" + e.getMessage());

		}
	}

	@Override
	public Integer updatePaymentEmpDirectCredit(PaymentEmployeeDirectCreditHdr payEmp) {
		log.info("inside PaymentEmployeeDirectCreditDaoImpl#updatePaymentEmpDirectCredit {}", payEmp);
		int rowNum = 0;
		{

			for (PaymentEmployeeDirectCreditDtl pymtEmpDtl : payEmp.getPymtEmpList()) {
				Map<String, Object> paramMapForDtl = PaymentEmployeeDirectCreditSqlQueries
						.getParamMapforDtl(pymtEmpDtl);
				paramMapForDtl.put("requestNo", payEmp.getRequestNo());
				paramMapForDtl.put("approveBy", pymtEmpDtl.getApproved_by());
				paramMapForDtl.put("status", pymtEmpDtl.getStatus());
				try {
					rowNum = namedParameterJdbcOperations
							.update(PaymentEmployeeDirectCreditSqlQueries.UPDATE_PYMT_EMP_DETAIL, paramMapForDtl);
				} catch (Exception e) {
					throw new PaymentEmployeeDirectCreditException(
							"Failed to add new payment entry transaction detail : " + e.getMessage());
				}
			}
		}
		return rowNum;

	}

	@Override
	public PaymentEmployeeDirectCreditHdr getPaymentEmpDirectCreditByRequestNo(@NotBlank String requestNo) {
		log.info("inside PaymentEmployeeDirectCreditDaoImpl#getPaymentEmpDirectCredit {}", requestNo);
		PaymentEmployeeDirectCreditHdr payEmpHdr;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("requestNo", requestNo);

		try {

			RowMapper<PaymentEmployeeDirectCreditHdr> rowMapper = PaymentEmployeeDirectCreditSqlQueries::mapRow;
			RowMapper<PaymentEmployeeDirectCreditDtl> rowMapperForDtl = PaymentEmployeeDirectCreditSqlQueries::mapRowDtl;
			payEmpHdr = namedParameterJdbcOperations.queryForObject(
					PaymentEmployeeDirectCreditSqlQueries.PYMT_EMP_HDR_VIEWBYREQUESTNO, paramMap, rowMapper);
			payEmpHdr.setPymtEmpList(namedParameterJdbcOperations
					.query(PaymentEmployeeDirectCreditSqlQueries.PYMT_EMP_DTL_VIEWALL, paramMap, rowMapperForDtl));
			return payEmpHdr;
		} catch (Exception e) {
			log.error("Exception {}",e);
			throw new PaymentEmployeeDirectCreditException("Failed to Generate Sequence Number" + e.getMessage());
		}

	}
}
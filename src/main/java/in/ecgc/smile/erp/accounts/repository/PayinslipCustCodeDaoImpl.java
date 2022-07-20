package in.ecgc.smile.erp.accounts.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.exception.PaymentEmployeeDirectCreditException;
import in.ecgc.smile.erp.accounts.model.PayinslipCustCode;
import in.ecgc.smile.erp.accounts.model.PaymentEmployeeDirectCreditHdr;
import in.ecgc.smile.erp.accounts.util.PayinslipCustCodeQueries;
import in.ecgc.smile.erp.accounts.util.PaymentEmployeeDirectCreditSqlQueries;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import javax.validation.constraints.NotBlank;

@Repository
@Transactional
@Slf4j
	public class PayinslipCustCodeDaoImpl implements PayinslipCustCodeDao
    {
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	public  PayinslipCustCodeDaoImpl(DataSource dataSource)
	 {
	namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	public MapSqlParameterSource getPayinslipCustCodeParamsMap(PayinslipCustCode payinslipCustCode){
	MapSqlParameterSource paramSource = new MapSqlParameterSource();
	paramSource.addValue("logicallocCd" , payinslipCustCode.getLogicallocCd());
	
	paramSource.addValue("customerCd" , payinslipCustCode.getCustomerCd());
	
	paramSource.addValue("bankName" , payinslipCustCode.getBankName());
	
	paramSource.addValue("bankAddress" , payinslipCustCode.getBankAddress());
	
	paramSource.addValue("bAcctNo" , payinslipCustCode.getBAcctNo());
	
	paramSource.addValue("neftCode" , payinslipCustCode.getNeftCode());
	
	paramSource.addValue("ifscCode" , payinslipCustCode.getIfscCode());
	
	paramSource.addValue("drMainglCd" , payinslipCustCode.getDrMainglCd());
	
	paramSource.addValue("drSubglCd" , payinslipCustCode.getDrSubglCd());
	
	paramSource.addValue("crMainglCd" , payinslipCustCode.getCrMainglCd());
	
	paramSource.addValue("crSubglCd" , payinslipCustCode.getCrSubglCd());
	
	paramSource.addValue("bankMainglCd" , payinslipCustCode.getBankMainglCd());
	
	paramSource.addValue("bankSubglCd" , payinslipCustCode.getBankSubglCd());
	
	paramSource.addValue("createdBy" , payinslipCustCode.getCreatedBy());
	
	paramSource.addValue("lastUpdatedBy" , payinslipCustCode.getLastUpdatedBy());
	
	paramSource.addValue("createdDt" , payinslipCustCode.getCreatedDt());
	
	paramSource.addValue("lastUpdatedDt" , payinslipCustCode.getLastUpdatedDt());
	
	paramSource.addValue("metaStatus" , payinslipCustCode.getMetaStatus());
	
	paramSource.addValue("metaRemarks" , payinslipCustCode.getMetaRemarks());
	
	paramSource.addValue("gstin" , payinslipCustCode.getGstin());
	
	paramSource.addValue("gstinstate" , payinslipCustCode.getGstinstate());
	
	paramSource.addValue("beneficiaryAcctName" , payinslipCustCode.getBeneficiaryAcctName());
	
	paramSource.addValue("beneficiaryCodeIbank" , payinslipCustCode.getBeneficiaryCodeIbank());
	
	
	return paramSource;
	}
	@Override
	public List<PayinslipCustCode> getPayinslipCustCodeList(){
	log.info("inside PayinslipCustCodeDaoImpl  -  getPayinslipCustCodeList()");
	List<PayinslipCustCode> list=new ArrayList<>();
	try {
		 String sql = PayinslipCustCodeQueries.GET_PayinslipCustCode_DATA;
		  list=(List<PayinslipCustCode>) namedParameterJdbcTemplate.query(sql, new PayinslipCustCodeMapper() );
		 log.info("List : {}",list);
		 return list;
	} catch (Exception e) {
		log.error("ERROR: DAO Module {}", e.getMessage());
		throw e;
	}
	}
	
	public PayinslipCustCode getPayinslipCustCodeDataById(String customerCd){
	log.info("inside PayinslipCustCodeDaoImpl  -  getPayinslipCustCodeDatById()");
	Map<String, Object>paramsMap= new HashMap<>();
	paramsMap.put("customerCd", customerCd);
	PayinslipCustCode  payinslipCustCode;
	try {
		 String sql = PayinslipCustCodeQueries.GET_PayinslipCustCode_DATA_BY_ID;
		  payinslipCustCode=namedParameterJdbcTemplate.query(sql, paramsMap,new PayinslipCustCodeResultSetExtractor() );
		  log.info("payinslipCustCode : {}",payinslipCustCode);
		 return payinslipCustCode;
	} catch (Exception e) {
		log.error("ERROR: DAO Module {}", e.getMessage());
		throw e;
	}
	}
	@Override
	public boolean addPayinslipCustCodeData(PayinslipCustCode  payinslipCustCode){
	log.info("inside PayinslipCustCodeDaoImpl  -  addPayinslipCustCodeData()");
	try {
		String sql =PayinslipCustCodeQueries.ADD_PayinslipCustCode_DATA;
	
	if(namedParameterJdbcTemplate.update(sql,getPayinslipCustCodeParamsMap(payinslipCustCode))>0){
		return true;
	}
	else{
	return false;
	}
	} catch (Exception e) {
		log.error("ERROR: DAO Module {}", e.getMessage());
		throw e;
	}
	}
	@Override
	public boolean updatePayinslipCustCodeData(PayinslipCustCode  payinslipCustCode){
	log.info("inside PayinslipCustCodeDaoImpl  -  updatePayinslipCustCodeData()");
	
	try {
		 String sql = PayinslipCustCodeQueries.UPDATE_PayinslipCustCode_DATA;
	
	if(namedParameterJdbcTemplate.update(sql,getPayinslipCustCodeParamsMap(payinslipCustCode))>0){
		return true;
	}
	else{
	return false;
	}
	} catch (Exception e) {
		log.error("ERROR: DAO Module {}", e.getMessage());
		throw e;
	}
	}
	
	@Override
	public List<PayinslipCustCode> getPayinSlipCustCodeByLogicalLoc(@NotBlank String logicalLoc) {
		List<PayinslipCustCode> payinSlipList = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("logicallocCd", logicalLoc);
		log.info("inside PayinslipDao #getPayinSlipCustCodeByLogicalLoc"+logicalLoc);
		try {
			
			String sql = PayinslipCustCodeQueries.VIEW_ALL_LOGICAL_LOC;
			log.info("in dao sql "+sql );
			//RowMapper<PayinslipCustCode> rowMapper = PayinslipCustCodeQueries::mapRow;
			payinSlipList =  (List<PayinslipCustCode>) namedParameterJdbcTemplate.query(sql,paramMap, new PayinslipCustCodeMapper());
			return payinSlipList;		
		}
		catch (Exception e) {
			log.error("ERROR: DAO Module {}", e.getMessage());
			throw e;
		}	
}
	
	

	class PayinslipCustCodeMapper implements RowMapper<PayinslipCustCode> {
	@Override
public PayinslipCustCode mapRow(ResultSet rs, int rowNum) throws SQLException {
	PayinslipCustCode payinslipCustCode = new PayinslipCustCode();
	payinslipCustCode.setLogicallocCd(rs.getString("logicalloc_cd"));
		payinslipCustCode.setCustomerCd(rs.getString("customer_cd"));
		payinslipCustCode.setBankName(rs.getString("bank_name"));
		payinslipCustCode.setBankAddress(rs.getString("bank_address"));
		payinslipCustCode.setBAcctNo(rs.getString("b_acct_no"));
		payinslipCustCode.setNeftCode(rs.getString("neft_code"));
		payinslipCustCode.setIfscCode(rs.getString("ifsc_code"));
		payinslipCustCode.setDrMainglCd(rs.getString("dr_maingl_cd"));
		payinslipCustCode.setDrSubglCd(rs.getString("dr_subgl_cd"));
		payinslipCustCode.setCrMainglCd(rs.getString("cr_maingl_cd"));
		payinslipCustCode.setCrSubglCd(rs.getString("cr_subgl_cd"));
		payinslipCustCode.setBankMainglCd(rs.getString("bank_maingl_cd"));
		payinslipCustCode.setBankSubglCd(rs.getString("bank_subgl_cd"));
		payinslipCustCode.setCreatedBy(rs.getString("created_by"));
		payinslipCustCode.setLastUpdatedBy(rs.getString("last_updated_by"));
		payinslipCustCode.setCreatedDt(rs.getDate("created_dt"));
	payinslipCustCode.setLastUpdatedDt(rs.getDate("last_updated_dt"));
	payinslipCustCode.setMetaStatus(rs.getString("meta_status"));
		payinslipCustCode.setMetaRemarks(rs.getString("meta_remarks"));
		payinslipCustCode.setGstin(rs.getString("gstin"));
		payinslipCustCode.setGstinstate(rs.getString("gstinstate"));
		payinslipCustCode.setBeneficiaryAcctName(rs.getString("beneficiary_acct_name"));
		payinslipCustCode.setBeneficiaryCodeIbank(rs.getString("beneficiary_code_ibank"));
		return payinslipCustCode;
	}
	}
class PayinslipCustCodeResultSetExtractor implements ResultSetExtractor<PayinslipCustCode> {
	@Override
public PayinslipCustCode extractData(ResultSet rs) throws SQLException,DataAccessException {
	PayinslipCustCode payinslipCustCode = null;
	if (rs.next()) {
	payinslipCustCode = new PayinslipCustCode();
	payinslipCustCode.setLogicallocCd(rs.getString("logicalloc_cd"));
		payinslipCustCode.setCustomerCd(rs.getString("customer_cd"));
		payinslipCustCode.setBankName(rs.getString("bank_name"));
		payinslipCustCode.setBankAddress(rs.getString("bank_address"));
		payinslipCustCode.setBAcctNo(rs.getString("b_acct_no"));
		payinslipCustCode.setNeftCode(rs.getString("neft_code"));
		payinslipCustCode.setIfscCode(rs.getString("ifsc_code"));
		payinslipCustCode.setDrMainglCd(rs.getString("dr_maingl_cd"));
		payinslipCustCode.setDrSubglCd(rs.getString("dr_subgl_cd"));
		payinslipCustCode.setCrMainglCd(rs.getString("cr_maingl_cd"));
		payinslipCustCode.setCrSubglCd(rs.getString("cr_subgl_cd"));
		payinslipCustCode.setBankMainglCd(rs.getString("bank_maingl_cd"));
		payinslipCustCode.setBankSubglCd(rs.getString("bank_subgl_cd"));
		payinslipCustCode.setCreatedBy(rs.getString("created_by"));
		payinslipCustCode.setLastUpdatedBy(rs.getString("last_updated_by"));
		payinslipCustCode.setCreatedDt(rs.getDate("created_dt"));
		payinslipCustCode.setLastUpdatedDt(rs.getDate("last_updated_dt"));
		payinslipCustCode.setMetaStatus(rs.getString("meta_status"));
		payinslipCustCode.setMetaRemarks(rs.getString("meta_remarks"));
		payinslipCustCode.setGstin(rs.getString("gstin"));
		payinslipCustCode.setGstinstate(rs.getString("gstinstate"));
		payinslipCustCode.setBeneficiaryAcctName(rs.getString("beneficiary_acct_name"));
		payinslipCustCode.setBeneficiaryCodeIbank(rs.getString("beneficiary_code_ibank"));
		
	}return payinslipCustCode;
	}
}

}
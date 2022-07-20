package in.ecgc.smile.erp.accounts.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import in.ecgc.smile.erp.accounts.exception.FailToInsertDataException;
import in.ecgc.smile.erp.accounts.model.GlTxnDtlPymtEnt;
import in.ecgc.smile.erp.accounts.util.GlTxnDtlPymtEntQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class GlTxnDtlPymtEntDaoImpl implements GlTxnDtlPymtEntDao{


	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//	private NamedParameterJdbcOperations namedParameterJdbcOperations;

	public  GlTxnDtlPymtEntDaoImpl(DataSource dataSource)
	 {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		//namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
	 }

	@Autowired
	UserInfoService userInfoService;

	public MapSqlParameterSource getGlTxnDtlPymtEntParamsMap(GlTxnDtlPymtEnt glTxnDtlPymtEnt){
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("paymentNo" , glTxnDtlPymtEnt.getPaymentNo());
		paramSource.addValue("adviceNo" , glTxnDtlPymtEnt.getAdviceNo());
		paramSource.addValue("sectionCd" , glTxnDtlPymtEnt.getSectionCd());
		paramSource.addValue("logicallocCd" , glTxnDtlPymtEnt.getLogicallocCd());
		paramSource.addValue("glTxnType" , glTxnDtlPymtEnt.getGlTxnType());
		paramSource.addValue("srNo" , glTxnDtlPymtEnt.getSrNo());
		paramSource.addValue("mainglCd" , glTxnDtlPymtEnt.getMainglCd());
		paramSource.addValue("subglCd" , glTxnDtlPymtEnt.getSubglCd());
		paramSource.addValue("personalLedgerCd" , glTxnDtlPymtEnt.getPersonalLedgerCd());
		paramSource.addValue("drCrFlg" , glTxnDtlPymtEnt.getDrCrFlg());
		paramSource.addValue("txnAmt" , glTxnDtlPymtEnt.getTxnAmt());
		paramSource.addValue("txnRmk" , glTxnDtlPymtEnt.getTxnRmk());
		paramSource.addValue("userId" , glTxnDtlPymtEnt.getUserId());
		paramSource.addValue("createdBy" , glTxnDtlPymtEnt.getCreatedBy());
		paramSource.addValue("lastUpdatedBy" , glTxnDtlPymtEnt.getLastUpdatedBy());
		paramSource.addValue("createdDt" , glTxnDtlPymtEnt.getCreatedDt());
		paramSource.addValue("lastUpdatedDt" , glTxnDtlPymtEnt.getLastUpdatedDt());
		paramSource.addValue("metaStatus" , glTxnDtlPymtEnt.getMetaStatus());
		paramSource.addValue("metaRemarks" , glTxnDtlPymtEnt.getMetaRemarks());
		paramSource.addValue("entityCd" , glTxnDtlPymtEnt.getEntityCd());
		return paramSource;
	}


	@Override
	public Integer addGlTxnDtlPymtEntData(GlTxnDtlPymtEnt  glTxnDtlPymtEnt){

		log.info("inside GlTxnDtlPymtEntDaoImpl#addGlTxnDtlPymtEntData()");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		paramSource.addValue("paymentNo" , glTxnDtlPymtEnt.getPaymentNo());
		paramSource.addValue("adviceNo" , glTxnDtlPymtEnt.getAdviceNo());
		paramSource.addValue("sectionCd" , glTxnDtlPymtEnt.getSectionCd());
		paramSource.addValue("logicallocCd" , glTxnDtlPymtEnt.getLogicallocCd());
		paramSource.addValue("glTxnType" , glTxnDtlPymtEnt.getGlTxnType());
		paramSource.addValue("srNo" , glTxnDtlPymtEnt.getSrNo());
		paramSource.addValue("mainglCd" , glTxnDtlPymtEnt.getMainglCd());
		paramSource.addValue("subglCd" , glTxnDtlPymtEnt.getSubglCd());
		paramSource.addValue("personalLedgerCd" , glTxnDtlPymtEnt.getPersonalLedgerCd());
		paramSource.addValue("personalLedgerLevel", glTxnDtlPymtEnt.getPlLevel());
		paramSource.addValue("subBifurcationLevel", glTxnDtlPymtEnt.getSubBifurcationLevel());
		paramSource.addValue("subBifurcationCode", glTxnDtlPymtEnt.getSubBifurcationCode());
		paramSource.addValue("drCrFlg" , glTxnDtlPymtEnt.getDrCrFlg());
		paramSource.addValue("txnAmt" , glTxnDtlPymtEnt.getTxnAmt());
		paramSource.addValue("txnRmk" , glTxnDtlPymtEnt.getTxnRmk());
		paramSource.addValue("userId" , glTxnDtlPymtEnt.getUserId());
		paramSource.addValue("createdBy" , userInfoService.getUser());
		paramSource.addValue("createdDt" , glTxnDtlPymtEnt.getCreatedDt());
		paramSource.addValue("metaStatus" , "V");
		paramSource.addValue("metaRemarks" , glTxnDtlPymtEnt.getMetaRemarks());
		paramSource.addValue("entityCd" , "ECGC");


		try {
			return namedParameterJdbcTemplate.update(GlTxnDtlPymtEntQueries.ADD_GLTXNDTLPYMTENT_DATA, paramSource);
		} catch (Exception e) {
			log.error("Failed to create Payment GL Txn Entry for Payment No : and Payment Advice No : {}, {}", glTxnDtlPymtEnt.getPaymentNo(), glTxnDtlPymtEnt.getAdviceNo());
			throw new FailToInsertDataException("Failed to create Payment GL Txn Entry");
		}
	}

	public List<GlTxnDtlPymtEnt> getGlTxnDtlPymtEntByPaymentDtl(Integer paymentNo, String logicalLoc, String sectionCd){
		log.info("inside GlTxnDtlPymtEntDaoImpl#getGlTxnDtlPymtEntByPaymentDtl()");

		Map<String, Object>paramsMap= new HashMap<>();


		List<GlTxnDtlPymtEnt>  glTxnDtlPymtEntList = new ArrayList<>();
			paramsMap.put("paymentNo",paymentNo);
			paramsMap.put("logicallocCd", logicalLoc);
			paramsMap.put("sectionCd", sectionCd);

			glTxnDtlPymtEntList=namedParameterJdbcTemplate.query(GlTxnDtlPymtEntQueries.GET_GLTXNDTLPYMTENT_BY_PAYMENT_DTL, paramsMap,
					  new RowMapper<GlTxnDtlPymtEnt>(){

				@Override
				public GlTxnDtlPymtEnt mapRow(ResultSet rs, int rowNum)  throws SQLException{
					GlTxnDtlPymtEnt glTxnDtlPymtEnt = new GlTxnDtlPymtEnt();

						glTxnDtlPymtEnt.setPaymentNo(rs.getInt("payment_no"));
						glTxnDtlPymtEnt.setAdviceNo(rs.getInt("advice_no"));
						glTxnDtlPymtEnt.setSectionCd(rs.getString("section_cd"));
						glTxnDtlPymtEnt.setLogicallocCd(rs.getString("logicalloc_cd"));
						glTxnDtlPymtEnt.setGlTxnType(rs.getString("gl_txn_type"));
						glTxnDtlPymtEnt.setSrNo(rs.getInt("sr_no"));
						glTxnDtlPymtEnt.setMainglCd(rs.getString("maingl_cd"));
						glTxnDtlPymtEnt.setSubglCd(rs.getString("subgl_cd"));
						glTxnDtlPymtEnt.setPersonalLedgerCd(rs.getString("personal_ledger_cd"));
						glTxnDtlPymtEnt.setDrCrFlg(rs.getString("dr_cr_flg"));
						glTxnDtlPymtEnt.setTxnAmt(rs.getDouble("txn_amt"));
						glTxnDtlPymtEnt.setTxnRmk(rs.getString("txn_rmk"));
						glTxnDtlPymtEnt.setUserId(rs.getString("user_id"));
						glTxnDtlPymtEnt.setCreatedBy(rs.getString("created_by"));
						glTxnDtlPymtEnt.setLastUpdatedBy(rs.getString("last_updated_by"));
						glTxnDtlPymtEnt.setCreatedDt(rs.getDate("created_dt").toLocalDate());
						glTxnDtlPymtEnt.setMetaStatus(rs.getString("meta_status"));
						glTxnDtlPymtEnt.setMetaRemarks(rs.getString("meta_remarks"));
						glTxnDtlPymtEnt.setEntityCd(rs.getString("entity_cd"));
						glTxnDtlPymtEnt.setPlLevel(rs.getString("personal_ledger_level"));
						glTxnDtlPymtEnt.setSubBifurcationLevel(rs.getString("sub_bifurcation_level"));
						glTxnDtlPymtEnt.setSubBifurcationCode(rs.getString("sub_bifurcation_code"));
						glTxnDtlPymtEnt.setGlName(rs.getString("gl_name"));
					return glTxnDtlPymtEnt;
				}
			} );
			if (glTxnDtlPymtEntList.isEmpty()) {
				log.error("No record found for payment no : {}, logicalLoc : {} and sectioncd : {}", paymentNo, logicalLoc, sectionCd);
				return null;
			}

		return glTxnDtlPymtEntList;

	}

	/*
	@Override
	public List<GlTxnDtlPymtEnt> getGlTxnDtlPymtEntList(){
		log.info("inside GlTxnDtlPymtEntDaoImpl  -  getGlTxnDtlPymtEntList()");
		List<GlTxnDtlPymtEnt> list=new ArrayList();
		try {
			 String sql = GlTxnDtlPymtEntQueries.GET_GlTxnDtlPymtEnt_DATA;
			  list=(List<GlTxnDtlPymtEnt>) namedParameterJdbcTemplate.query(sql, new GlTxnDtlPymtEntMapper() );
			 System.out.println(list);
			 return list;
		} catch (Exception e) {
			log.error("ERROR: DAO Module {}", e.getMessage());
			throw e;
		}
	}






	@Override
	public Integer updateGlTxnDtlPymtEntData(GlTxnDtlPymtEnt  glTxnDtlPymtEnt){
	log.info("inside GlTxnDtlPymtEntDaoImpl  -  updateGlTxnDtlPymtEntData()");
	try {
		 String sql = GlTxnDtlPymtEntQueries.UPDATE_GlTxnDtlPymtEnt_DATA;

	if(namedParameterJdbcTemplate.update(sql,getGlTxnDtlPymtEntParamsMap(glTxnDtlPymtEnt))>0){
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
}


class GlTxnDtlPymtEntMapper implements RowMapper<GlTxnDtlPymtEnt> {
	@Override
public GlTxnDtlPymtEnt mapRow(ResultSet rs, int rowNum) throws SQLException {
	GlTxnDtlPymtEnt glTxnDtlPymtEnt = new GlTxnDtlPymtEnt();
	glTxnDtlPymtEnt.setPaymentNo(rs.getDouble("payment_no"));
	glTxnDtlPymtEnt.setAdviceNo(rs.getDouble("advice_no"));
	glTxnDtlPymtEnt.setSectionCd(rs.getString("section_cd"));
		glTxnDtlPymtEnt.setLogicallocCd(rs.getString("logicalloc_cd"));
		glTxnDtlPymtEnt.setGlTxnType(rs.getString("gl_txn_type"));
		glTxnDtlPymtEnt.setSrNo(rs.getDouble("sr_no"));
	glTxnDtlPymtEnt.setMainglCd(rs.getDouble("maingl_cd"));
	glTxnDtlPymtEnt.setSubglCd(rs.getDouble("subgl_cd"));
	glTxnDtlPymtEnt.setPersonalLedgerCd(rs.getString("personal_ledger_cd"));
		glTxnDtlPymtEnt.setDrCrFlg(rs.getString("dr_cr_flg"));
		glTxnDtlPymtEnt.setTxnAmt(rs.getDouble("txn_amt"));
	glTxnDtlPymtEnt.setTxnRmk(rs.getString("txn_rmk"));
		glTxnDtlPymtEnt.setUserId(rs.getString("user_id"));
		glTxnDtlPymtEnt.setCreatedBy(rs.getString("created_by"));
		glTxnDtlPymtEnt.setLastUpdatedBy(rs.getString("last_updated_by"));
		glTxnDtlPymtEnt.setCreatedDt(rs.getDate("created_dt"));
	glTxnDtlPymtEnt.setLastUpdatedDt(rs.getDate("last_updated_dt"));
	glTxnDtlPymtEnt.setMetaStatus(rs.getString("meta_status"));
		glTxnDtlPymtEnt.setMetaRemarks(rs.getString("meta_remarks"));
		glTxnDtlPymtEnt.setEntityCd(rs.getString("entity_cd"));
		return glTxnDtlPymtEnt;
	}
	}



class GlTxnDtlPymtEntResultSetExtractor implements ResultSetExtractor<GlTxnDtlPymtEnt> {
	@Override
public GlTxnDtlPymtEnt extractData(ResultSet rs) throws SQLException,DataAccessException {
	GlTxnDtlPymtEnt glTxnDtlPymtEnt = null;
	if (rs.next()) {
	glTxnDtlPymtEnt = new GlTxnDtlPymtEnt();
	glTxnDtlPymtEnt.setPaymentNo(rs.getDouble("payment_no"));
		glTxnDtlPymtEnt.setAdviceNo(rs.getDouble("advice_no"));
		glTxnDtlPymtEnt.setSectionCd(rs.getString("section_cd"));
		glTxnDtlPymtEnt.setLogicallocCd(rs.getString("logicalloc_cd"));
		glTxnDtlPymtEnt.setGlTxnType(rs.getString("gl_txn_type"));
		glTxnDtlPymtEnt.setSrNo(rs.getDouble("sr_no"));
		glTxnDtlPymtEnt.setMainglCd(rs.getDouble("maingl_cd"));
		glTxnDtlPymtEnt.setSubglCd(rs.getDouble("subgl_cd"));
		glTxnDtlPymtEnt.setPersonalLedgerCd(rs.getString("personal_ledger_cd"));
		glTxnDtlPymtEnt.setDrCrFlg(rs.getString("dr_cr_flg"));
		glTxnDtlPymtEnt.setTxnAmt(rs.getDouble("txn_amt"));
		glTxnDtlPymtEnt.setTxnRmk(rs.getString("txn_rmk"));
		glTxnDtlPymtEnt.setUserId(rs.getString("user_id"));
		glTxnDtlPymtEnt.setCreatedBy(rs.getString("created_by"));
		glTxnDtlPymtEnt.setLastUpdatedBy(rs.getString("last_updated_by"));
		glTxnDtlPymtEnt.setCreatedDt(rs.getDate("created_dt"));
		glTxnDtlPymtEnt.setLastUpdatedDt(rs.getDate("last_updated_dt"));
		glTxnDtlPymtEnt.setMetaStatus(rs.getString("meta_status"));
		glTxnDtlPymtEnt.setMetaRemarks(rs.getString("meta_remarks"));
		glTxnDtlPymtEnt.setEntityCd(rs.getString("entity_cd"));

	}return glTxnDtlPymtEnt;
	}
	*/
}


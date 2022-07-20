package in.ecgc.smile.erp.accounts.repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger
;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import in.ecgc.smile.erp.accounts.model.SectionOfTds;

import in.ecgc.smile.erp.accounts.util.TdsQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;

import javax.sql.DataSource;
@Repository
	public class SectionOfTdsDaoImpl implements SectionOfTdsDao
{
	private static final Logger logger = LoggerFactory.getLogger(SectionOfTdsDaoImpl.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public  SectionOfTdsDaoImpl(DataSource dataSource)
	 {
	namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Autowired
	UserInfoService userInfoService;
	
	public MapSqlParameterSource getTdsParamsMap(SectionOfTds tds){
	MapSqlParameterSource paramSource = new MapSqlParameterSource();
	paramSource.addValue("srNo" , tds.getSrNo());
	
	paramSource.addValue("tdsSection" , tds.getTdsSection());
	
	paramSource.addValue("particulars" , tds.getParticulars());
	
	paramSource.addValue("subParticular" , tds.getSubParticular());
	
	paramSource.addValue("thresholdLimit" , tds.getThresholdLimit());
	
	paramSource.addValue("tdsRate" , tds.getTdsRate());
	
	
	
	if(tds.getCreatedBy().equals("null")) {
		paramSource.addValue("createdBy" , userInfoService.getUser());
		paramSource.addValue("lastUpdatedDt" , tds.getLastUpdatedDt());
		paramSource.addValue("lastUpdatedBy" , tds.getLastUpdatedBy());
	}else {
		paramSource.addValue("lastUpdatedBy" , userInfoService.getUser());
		paramSource.addValue("createdBy" , tds.getCreatedBy());
		paramSource.addValue("createdDt" , tds.getCreatedDt());
	}
		
	paramSource.addValue("metaStatus" , tds.getMetaStatus());
	
	
	return paramSource;
	}
	@Override
	public List<SectionOfTds> getTdsList(){
	logger.info("inside TdsDaoImpl  -  getTdsList()");
	List<SectionOfTds> list=new ArrayList();
	try {
		 String sql = TdsQueries.GET_TDS_DATA;
		  list=(List<SectionOfTds>) namedParameterJdbcTemplate.query(sql, new TdsMapper() );
		 System.out.println(list);
		 return list;
	} catch (Exception e) {
		logger.error("ERROR: DAO Module {}", e.getMessage());
		throw e;
	}
	}
	public SectionOfTds getTdsDataById(int srNo){
	logger.info("inside TdsDaoImpl  -  getTdsDatById()");
	Map<String, Object>paramsMap= new HashMap<>();
	paramsMap.put("srNo", srNo);
	SectionOfTds  tds;
	try {
		 String sql = TdsQueries.GET_TDS_DATA_BY_ID;
		  tds=namedParameterJdbcTemplate.query(sql, paramsMap,new TdsResultSetExtractor() );
		 System.out.println(tds);
		 return tds;
	} catch (Exception e) {
		logger.error("ERROR: DAO Module {}", e.getMessage());
		throw e;
	}
	}
	@Override
	public boolean addTdsData(SectionOfTds  tds){
	logger.info("inside TdsDaoImpl  -  addTdsData()");
	try {
		String sql =TdsQueries.ADD_TDS_DATA;
	
	if(namedParameterJdbcTemplate.update(sql,getTdsParamsMap(tds))>0){
		return true;
	}
	else{
	return false;
	}
	} catch (Exception e) {
		logger.error("ERROR: DAO Module {}", e.getMessage());
		throw e;
	}
	}
	@Override
	public boolean updateTdsData(SectionOfTds  tds){
	logger.info("inside TdsDaoImpl  -  updateTdsData()");
	try {
		 String sql = TdsQueries.UPDATE_TDS_DATA;
	
	if(namedParameterJdbcTemplate.update(sql,getTdsParamsMap(tds))>0){
		return true;
	}
	else{
	return false;
	}
	} catch (Exception e) {
		logger.error("ERROR: DAO Module {}", e.getMessage());
		throw e;
	}
	}
}class TdsMapper implements RowMapper<SectionOfTds> {
	@Override
public SectionOfTds mapRow(ResultSet rs, int rowNum) throws SQLException {
		SectionOfTds tds = new SectionOfTds();
	tds.setSrNo(rs.getInt("sr_no"));
	tds.setTdsSection(rs.getString("tds_section"));
		tds.setParticulars(rs.getString("particulars"));
		tds.setSubParticular(rs.getString("sub_particular"));
		tds.setThresholdLimit(rs.getDouble("threshold_limit"));
	tds.setTdsRate(rs.getDouble("tds_rate"));
	tds.setCreatedDt(rs.getDate("created_dt"));
	tds.setCreatedBy(rs.getString("created_by"));
		tds.setLastUpdatedDt(rs.getDate("last_updated_dt"));
	tds.setLastUpdatedBy(rs.getString("last_updated_by"));
		tds.setMetaStatus(rs.getBoolean("meta_status"));
	return tds;
	}
	}
class TdsResultSetExtractor implements ResultSetExtractor<SectionOfTds> {
	@Override
public SectionOfTds extractData(ResultSet rs) throws SQLException,DataAccessException {
		SectionOfTds tds = null;
	if (rs.next()) {
	tds = new SectionOfTds();
	tds.setSrNo(rs.getInt("sr_no"));
		tds.setTdsSection(rs.getString("tds_section"));
		tds.setParticulars(rs.getString("particulars"));
		tds.setSubParticular(rs.getString("sub_particular"));
		tds.setThresholdLimit(rs.getDouble("threshold_limit"));
		tds.setTdsRate(rs.getDouble("tds_rate"));
		tds.setCreatedDt(rs.getDate("created_dt"));
		tds.setCreatedBy(rs.getString("created_by"));
		tds.setLastUpdatedDt(rs.getDate("last_updated_dt"));
		tds.setLastUpdatedBy(rs.getString("last_updated_by"));
		tds.setMetaStatus(rs.getBoolean("meta_status"));
		
	}return tds;
	}
}


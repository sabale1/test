package in.ecgc.smile.erp.accounts.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import in.ecgc.smile.erp.accounts.model.ScheduleCdGlMapping;
import in.ecgc.smile.erp.accounts.util.ScheduleCdGlMappingQueries;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;

@Repository
public class ScheduleCdGlMappingDaoImpl implements ScheduleCdGlMappingDao {
	private static final Logger logger = LoggerFactory.getLogger(ScheduleCdGlMappingDaoImpl.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	

	public ScheduleCdGlMappingDaoImpl(DataSource dataSource) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Autowired
	UserInfoService userInfoService;

	public MapSqlParameterSource getScheduleCdGlMappingParamsMap(ScheduleCdGlMapping scheduleCdGlMapping) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		//paramSource.addValue("seqNo", scheduleCdGlMapping.getSeqNo());

		paramSource.addValue("mainglCd", scheduleCdGlMapping.getMainglCd());

		paramSource.addValue("subglCd", scheduleCdGlMapping.getSubglCd());

		paramSource.addValue("scheduleCd", scheduleCdGlMapping.getScheduleCd());

		paramSource.addValue("scheduleItemCd", scheduleCdGlMapping.getScheduleItemCd());

		paramSource.addValue("createdBy", userInfoService.getUser());

		paramSource.addValue("lastUpdatedBy", scheduleCdGlMapping.getLastUpdatedBy());

		paramSource.addValue("createdDt", scheduleCdGlMapping.getCreatedDt());

		paramSource.addValue("lastUpdatedDt", scheduleCdGlMapping.getLastUpdatedDt());

		paramSource.addValue("remarks", scheduleCdGlMapping.getRemarks());

		paramSource.addValue("delFlag", scheduleCdGlMapping.getDelFlag());

		return paramSource;
	}

	public MapSqlParameterSource getScheduleCdGlMappingParamsMapUpdate(ScheduleCdGlMapping scheduleCdGlMapping) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("seqNo", scheduleCdGlMapping.getSeqNo());

		paramSource.addValue("mainglCd", scheduleCdGlMapping.getMainglCd());

		paramSource.addValue("subglCd", scheduleCdGlMapping.getSubglCd());

		paramSource.addValue("scheduleCd", scheduleCdGlMapping.getScheduleCd());

		paramSource.addValue("scheduleItemCd", scheduleCdGlMapping.getScheduleItemCd());

		paramSource.addValue("createdBy", scheduleCdGlMapping.getCreatedBy());

		paramSource.addValue("lastUpdatedBy", userInfoService.getUser());

		paramSource.addValue("createdDt", scheduleCdGlMapping.getCreatedDt());

		paramSource.addValue("lastUpdatedDt", scheduleCdGlMapping.getLastUpdatedDt());

		paramSource.addValue("remarks", scheduleCdGlMapping.getRemarks());

		paramSource.addValue("delFlag", scheduleCdGlMapping.getDelFlag());

		return paramSource;
	}

	
	@Override
	public List<ScheduleCdGlMapping> getScheduleCdGlMappingList() {
		logger.info("inside ScheduleCdGlMappingDaoImpl  -  getScheduleCdGlMappingList()");
		List<ScheduleCdGlMapping> list = new ArrayList();
		try {
			String sql = ScheduleCdGlMappingQueries.GET_SCHEDULECDGLMAPPING_DATA;
			list = (List<ScheduleCdGlMapping>) namedParameterJdbcTemplate.query(sql, new ScheduleCdGlMappingMapper());
			System.out.println(list);
			return list;
		} catch (Exception e) {
			logger.error("ERROR: DAO Module {}", e.getMessage());
			throw e;
		}
	}

	public ScheduleCdGlMapping getScheduleCdGlMappingDataById(int seqNo) {
		logger.info("inside ScheduleCdGlMappingDaoImpl  -  getScheduleCdGlMappingDatById()");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("seqNo", seqNo);
		ScheduleCdGlMapping scheduleCdGlMapping;
		try {
			String sql = ScheduleCdGlMappingQueries.GET_SCHEDULECDGLMAPPING_DATA_BY_ID;
			scheduleCdGlMapping = namedParameterJdbcTemplate.query(sql, paramsMap,
					new ScheduleCdGlMappingResultSetExtractor());
			System.out.println(scheduleCdGlMapping);
			return scheduleCdGlMapping;
		} catch (Exception e) {
			logger.error("ERROR: DAO Module {}", e.getMessage());
			throw e;
		}
	}

	@Override
	public boolean addScheduleCdGlMappingData(ScheduleCdGlMapping scheduleCdGlMapping) {
		logger.info("inside ScheduleCdGlMappingDaoImpl  -  addScheduleCdGlMappingData()");
		try {
			String sql = ScheduleCdGlMappingQueries.ADD_SCHEDULECDGLMAPPING_DATA;

			if (namedParameterJdbcTemplate.update(sql, getScheduleCdGlMappingParamsMap(scheduleCdGlMapping)) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("ERROR: DAO Module {}", e.getMessage());
			throw e;
		}
	}

	@Override
	public boolean updateScheduleCdGlMappingData(ScheduleCdGlMapping scheduleCdGlMapping) {
		logger.info("inside ScheduleCdGlMappingDaoImpl  -  updateScheduleCdGlMappingData()");
		logger.info("ScheduleCdGlMapping oblect is : {}",scheduleCdGlMapping);
		try {
			String sql = ScheduleCdGlMappingQueries.UPDATE_SCHEDULECDGLMAPPING_DATA;

			if (namedParameterJdbcTemplate.update(sql, getScheduleCdGlMappingParamsMapUpdate(scheduleCdGlMapping)) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("ERROR: DAO Module {}", e.getMessage());
			throw e;
		}
	}

	
}

class ScheduleCdGlMappingMapper implements RowMapper<ScheduleCdGlMapping> {
	@Override
	public ScheduleCdGlMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
		ScheduleCdGlMapping scheduleCdGlMapping = new ScheduleCdGlMapping();
		scheduleCdGlMapping.setSeqNo(rs.getInt("seq_no"));
		scheduleCdGlMapping.setMainglCd(rs.getString("maingl_cd"));
		scheduleCdGlMapping.setSubglCd(rs.getString("subgl_cd"));
		scheduleCdGlMapping.setScheduleCd(rs.getString("schedule_cd"));
		scheduleCdGlMapping.setScheduleItemCd(rs.getString("schedule_item_cd"));
		scheduleCdGlMapping.setCreatedBy(rs.getString("created_by"));
		scheduleCdGlMapping.setLastUpdatedBy(rs.getString("last_updated_by"));
		scheduleCdGlMapping.setCreatedDt(rs.getDate("created_dt"));
		scheduleCdGlMapping.setLastUpdatedDt(rs.getDate("last_updated_dt"));
		scheduleCdGlMapping.setRemarks(rs.getString("remarks"));
		scheduleCdGlMapping.setDelFlag(rs.getString("del_flag"));
		return scheduleCdGlMapping;
	}
}

class ScheduleCdGlMappingResultSetExtractor implements ResultSetExtractor<ScheduleCdGlMapping> {
	@Override
	public ScheduleCdGlMapping extractData(ResultSet rs) throws SQLException, DataAccessException {
		ScheduleCdGlMapping scheduleCdGlMapping = null;
		if (rs.next()) {
			scheduleCdGlMapping = new ScheduleCdGlMapping();
			scheduleCdGlMapping.setSeqNo(rs.getInt("seq_no"));
			scheduleCdGlMapping.setMainglCd(rs.getString("maingl_cd"));
			scheduleCdGlMapping.setSubglCd(rs.getString("subgl_cd"));
			scheduleCdGlMapping.setScheduleCd(rs.getString("schedule_cd"));
			scheduleCdGlMapping.setScheduleItemCd(rs.getString("schedule_item_cd"));
			scheduleCdGlMapping.setCreatedBy(rs.getString("created_by"));
			scheduleCdGlMapping.setLastUpdatedBy(rs.getString("last_updated_by"));
			scheduleCdGlMapping.setCreatedDt(rs.getDate("created_dt"));
			scheduleCdGlMapping.setLastUpdatedDt(rs.getDate("last_updated_dt"));
			scheduleCdGlMapping.setRemarks(rs.getString("remarks"));
			scheduleCdGlMapping.setDelFlag(rs.getString("del_flag"));

		}
		return scheduleCdGlMapping;
	}
}

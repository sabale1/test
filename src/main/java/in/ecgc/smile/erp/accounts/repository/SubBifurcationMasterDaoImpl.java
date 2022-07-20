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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.exception.DatabaseOperationFailException;
import in.ecgc.smile.erp.accounts.exception.RecordAlreadyExistException;
import in.ecgc.smile.erp.accounts.exception.SubBifurcationLevelCodeNotFound;
import in.ecgc.smile.erp.accounts.model.SubBifurcations;
import in.ecgc.smile.erp.accounts.util.SubBifurcationsMasterQueries;
import in.ecgc.smile.erp.accounts.util.UserInfoServiceUtil;
import lombok.extern.slf4j.Slf4j;

@Repository
@Transactional
@Slf4j
public class SubBifurcationMasterDaoImpl implements SubBifurcationMasterDao {

	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private UserInfoServiceUtil userInforServiceUtil;

	@Autowired
	public SubBifurcationMasterDaoImpl(DataSource dataSource) {
		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<SubBifurcations> getSubBifurcations()  {
		log.info("Inside SubBifurcationMasterDaoImpl#getSubBifurcations");
			List<SubBifurcations> list = new ArrayList<>();
			RowMapper<SubBifurcations> rowMapper= SubBifurcationsMasterQueries::mapRow;
			list = namedParameterJdbcTemplate.query(SubBifurcationsMasterQueries.GET_ALL_BIFURCATION,rowMapper);
			return list;
	}

	@Override
	public Integer addSubBifurcation(SubBifurcations subBifurcations) {
		log.info("Inside SubBifurcationMasterDaoImpl#addSubBifurcation");
		subBifurcations.setCreatedBy(userInforServiceUtil.getCurrentUser());
		subBifurcations.setLastUpdatedBy(userInforServiceUtil.getCurrentUser());
		Integer rowNum = 0;
		try {
		Map<String, Object> paramMapForHdr = SubBifurcationsMasterQueries.getParamMap(subBifurcations);
		rowNum = namedParameterJdbcTemplate.update(SubBifurcationsMasterQueries.ADD_BIFURCATION, paramMapForHdr);
			return rowNum;		
		}
		catch(DuplicateKeyException dke) {
			log.error("Duplicate key exception while adding new SubBifurcation {} {}",subBifurcations.getSubBifurcationLevel(),dke);
			throw new RecordAlreadyExistException("Sub bifurcation with Level Code already exists",
					new Object[] {subBifurcations.getSubBifurcationLevel()});
		}
		catch (DataAccessException e) {
			log.error("Database Exception while adding new SubBifurcation {}",e);
		}
		return rowNum;
	}

	@Override
	public SubBifurcations getSubBifurcationsByLevel(String subBifurcationLevel) {
		log.info("Inside SubBifurcationMasterDaoImpl#getSubBifurcationsByLevel");
		SubBifurcations  list = new SubBifurcations();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("level",subBifurcationLevel);
		try {
			list = namedParameterJdbcOperations.queryForObject(SubBifurcationsMasterQueries.GET_BIFURCATION_BY_LEVEL,
					paramMap,new RowMapper<SubBifurcations>() {

				@Override
				public SubBifurcations mapRow(ResultSet rs, int rowNum) throws SQLException {
						SubBifurcations subBifurcation = new SubBifurcations();				
					subBifurcation.setSubBifurcationLevel(rs.getString("bifurcation_level_code"));
					subBifurcation.setDescription(rs.getString("description"));
					subBifurcation.setCreatedBy(rs.getString("created_by"));
					subBifurcation.setCreatedDt(rs.getObject("created_dt",LocalDate.class));
					subBifurcation.setLastUpdatedBy(rs.getString("last_updated_by"));
					subBifurcation.setLastUpdatedDt(rs.getObject("last_updated_dt",LocalDate.class));
					subBifurcation.setMetaRemarks(rs.getString("meta_remarks"));
				
						return subBifurcation;
					}
				
			});
		} catch (DataAccessException e) {
			throw new SubBifurcationLevelCodeNotFound("No Sub Bifurcation details found with the given levelCode",
					new String[] { subBifurcationLevel});
		}
		return list;	
	}

	@Override
	public List<String> getSubBifurcationLevels(){
		log.info("Inside SubBifurcationMasterDaoImpl#getSubBifurcationLevels");
			List<String> list;
			list = namedParameterJdbcTemplate.query(SubBifurcationsMasterQueries.GET_BIFURCATION__LEVELS,new RowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("bifurcation_level_code");
				}
			});
			return list;
	}

	@Override
	public SubBifurcations updateSubBifurcationLevel(SubBifurcations subBifurcations) {
		log.info("Inside SubBifurcationMasterDaoImpl#updateSubBifurcationLevel");
		int rowCount;
		try {
			Map<String, Object> namedParameters = new HashMap<String, Object>();
			
			log.info("updated entity {}",subBifurcations);
			namedParameters.put("subBifurcationLevel", subBifurcations.getSubBifurcationLevel());
			namedParameters.put("description", subBifurcations.getDescription());
			namedParameters.put("lastUpdatedBy", userInforServiceUtil.getCurrentUser());
			rowCount = namedParameterJdbcTemplate.update(SubBifurcationsMasterQueries.UPDATE_BIFURCATION, namedParameters);
		}
		catch(DuplicateKeyException dke) {
			log.error("Duplicate key exception while adding new SubBifurcation {} {}",subBifurcations.getSubBifurcationLevel(),dke);
			throw new RecordAlreadyExistException("Failed to Update Sub bifurcation Level code. Sub bifurcation with Level Code already exists",
					new String[] {subBifurcations.getSubBifurcationLevel()});
		}
		catch (DataAccessException e) {
			log.info("Exception occured while updating new GL code...{}",e);
			throw new DatabaseOperationFailException("Failed to Update Sub bifurcation Level code" + e.getMessage());
	
		}
		if (rowCount == 1)
			return subBifurcations;
		else
			return null;
	}
}

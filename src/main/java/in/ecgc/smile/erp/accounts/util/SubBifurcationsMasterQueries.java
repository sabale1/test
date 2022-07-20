package in.ecgc.smile.erp.accounts.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import in.ecgc.smile.erp.accounts.model.SubBifurcations;
import lombok.extern.slf4j.Slf4j;

public interface SubBifurcationsMasterQueries {

	public static String ADD_BIFURCATION ="INSERT INTO accounts.ecgc_acct_sub_bifurcations\r\n"
			+ "(bifurcation_level_code, description, created_by, created_dt, meta_status, meta_remarks) "
			+ "VALUES(:subBifurcationLevel,:description,:createdBy,now(),'V',:remarks);";
	
	public static String UPDATE_BIFURCATION ="UPDATE accounts.ecgc_acct_sub_bifurcations "
			+ " SET  description=:description, last_updated_by=:lastUpdatedBy,"
			+ " last_updated_dt=now(), meta_status='V' where bifurcation_level_code=:subBifurcationLevel";
	
	public static String GET_ALL_BIFURCATION ="SELECT * FROM accounts.ecgc_acct_sub_bifurcations;";
	public static String GET_BIFURCATION_BY_LEVEL ="SELECT * FROM accounts.ecgc_acct_sub_bifurcations where bifurcation_level_code = :level;";
	public static String GET_BIFURCATION__LEVELS ="select distinct bifurcation_level_code from accounts.ecgc_acct_sub_bifurcations easb order by bifurcation_level_code;";
	
	public static SubBifurcations mapRow(ResultSet rs, int rowNum) throws SQLException  {
		//System.err.println("inside Row mapper");
		SubBifurcations subBifurcations = new SubBifurcations();
			subBifurcations.setSubBifurcationLevel(rs.getString("bifurcation_level_code"));
			subBifurcations.setDescription(rs.getString("description"));
			subBifurcations.setCreatedBy(rs.getString("created_by"));
			subBifurcations.setCreatedDt(rs.getObject("created_dt",LocalDate.class));
			subBifurcations.setLastUpdatedBy(rs.getString("last_updated_by"));
			subBifurcations.setLastUpdatedDt(rs.getObject("last_updated_dt",LocalDate.class));
			subBifurcations.setMetaRemarks(rs.getString("meta_remarks"));
			return subBifurcations;
		
	}
	
	public static Map<String, Object> getParamMap(SubBifurcations subBifurcations){
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", 'V');
		
		paramMap.put("subBifurcationLevel",subBifurcations.getSubBifurcationLevel());
	//	paramMap.put("value",subBifurcations.getValue());
		paramMap.put("description",subBifurcations.getDescription());
		paramMap.put("createdBy",subBifurcations.getCreatedBy());
		paramMap.put("lastUpdatedBy",subBifurcations.getLastUpdatedBy());
		paramMap.put("remarks",subBifurcations.getMetaRemarks());
		//paramMap.put("srNo",subBifurcations.getSrNo());
		
		return paramMap;
	}
	
}

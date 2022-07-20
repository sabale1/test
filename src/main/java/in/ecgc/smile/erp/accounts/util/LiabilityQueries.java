package in.ecgc.smile.erp.accounts.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import in.ecgc.smile.erp.accounts.model.LiabilityGLMapping;


public interface LiabilityQueries {

	public static final String GET_MAPPING = "SELECT * FROM accounts.ecgc_acct_modulewise_liability_gl_mapping;";

	public static final String GET_MAPPING_FOR_MODULE = "select "
			+ "a.* , b.gl_name , b.pl_level , b.sub_bifurcation_level "
			+ "from  "
			+ "accounts.ecgc_acct_modulewise_liability_gl_mapping a "
			+ "JOIN "
			+ "accounts.ecgc_acct_entity_gl_mst b "
			+ "on "
			+ "a.maingl_cd = b.maingl_cd and a.subgl_cd = b.subgl_cd  "
			+ "where  "
			+ "a.module_cd=:moduleCd and a.mapping_cd=:mappingCd "
			+ "order by "
			+ "a.sr_no";

	public static final String GET_ALL_MAPPING_FOR_MODULE = "select * from accounts.ecgc_acct_modulewise_liability_gl_mapping where module_cd=:moduleCd;";

	public static final String ADD_MAPPING_FOR_MODULE = "INSERT INTO accounts.ecgc_acct_modulewise_liability_gl_mapping "
			+ "(module_cd, mapping_cd, sr_no, mapping_name, maingl_cd, subgl_cd, dr_cr_flag, amt_calc, remarks, txn_type, sub_biurcation, "
			+ "created_dt, created_by, last_updated_by, last_updated_dt, meta_remarks, meta_status, \r\n"
			+ " tds_applicable, rc_applicable, gst_tds_applicable)\r\n"
			+ "VALUES(:moduleCd, :mappingCd,:srNo,:mappingName,:mainGl,:subGl,:drcdFlag,:amtCalc,:remark,:txnType,:subBifurcation,"
			+ " 'now()',:createdBy,:updatedBy, 'now()',:metaRemarks,:status,"
			+ ":tdsApplicable,:rcApplicable,:gstTdsApplicable)";

	public static final String GET_MAPPING_CD_FOR_MODULE_CD= "select  distinct mapping_cd, mapping_name from accounts.ecgc_acct_modulewise_liability_gl_mapping where module_cd =:moduleCd";

	public static final String GET_DISTINCT_MODULE_CD = "SELECT distinct module_cd FROM accounts.ecgc_acct_modulewise_liability_gl_mapping;";

	public static final String GET_APPLICABLE_TCODES = "select * from accounts.ecgc_acct_entity_gl_mst eaegm where (maingl_cd ,subgl_cd ) "
			+ " in (select maingl_cd, subgl_cd from accounts.ecgc_acct_modulewise_liability_gl_mapping eamlgm "
			+ " where module_cd = :moduleCd and mapping_cd = :mappingCd)";


	public static LiabilityGLMapping mapRow(ResultSet rs, int rowNum) throws SQLException  {
		LiabilityGLMapping glMapping = new LiabilityGLMapping();
			glMapping.setModuleCd(rs.getString("module_cd"));
			glMapping.setMappingCd(rs.getString("mapping_cd"));
			glMapping.setMappingName(rs.getString("mapping_name"));
			glMapping.setSrNo(rs.getInt("sr_no"));
			glMapping.setAmtCalc(rs.getFloat("amt_calc"));
			glMapping.setMainGL(rs.getString("maingl_cd"));
			glMapping.setSubGL(rs.getString("subgl_cd"));
			glMapping.setGlName(rs.getString("gl_name"));
			glMapping.setTxnType(rs.getString("txn_type"));
			glMapping.setDrCrFlag(rs.getString("dr_cr_flag"));
			glMapping.setRemarks(rs.getString("remarks"));
			glMapping.setSubBifurcation(rs.getString("sub_biurcation"));
			glMapping.setCreatedBy(rs.getString("created_by"));
			glMapping.setEntityCd(rs.getString("entity_cd"));
			glMapping.setTdsApplicable(rs.getString("tds_applicable").charAt(0));
			glMapping.setRcApplicable(rs.getString("rc_applicable").charAt(0));
			glMapping.setGstTdsApplicable(rs.getString("gst_tds_applicable").charAt(0));
			glMapping.setPlLevel(rs.getString("pl_level"));
			glMapping.setSubBifurcationLevel(rs.getString("sub_bifurcation_level"));
			return glMapping;
	}


	public static LiabilityGLMapping mapRowGetAll(ResultSet rs, int rowNum) throws SQLException  {
		LiabilityGLMapping glMapping = new LiabilityGLMapping();
			glMapping.setModuleCd(rs.getString("module_cd"));
			glMapping.setMappingCd(rs.getString("mapping_cd"));
			glMapping.setMappingName(rs.getString("mapping_name"));
			glMapping.setSrNo(rs.getInt("sr_no"));
			glMapping.setAmtCalc(rs.getFloat("amt_calc"));
			glMapping.setMainGL(rs.getString("maingl_cd"));
			glMapping.setSubGL(rs.getString("subgl_cd"));
			glMapping.setTxnType(rs.getString("txn_type"));
			glMapping.setDrCrFlag(rs.getString("dr_cr_flag"));
			glMapping.setRemarks(rs.getString("remarks"));
			glMapping.setSubBifurcation(rs.getString("sub_biurcation"));
			glMapping.setCreatedBy(rs.getString("created_by"));
			glMapping.setEntityCd(rs.getString("entity_cd"));
			glMapping.setTdsApplicable(rs.getString("tds_applicable").charAt(0));
			glMapping.setRcApplicable(rs.getString("rc_applicable").charAt(0));
			glMapping.setGstTdsApplicable(rs.getString("gst_tds_applicable").charAt(0));
				return glMapping;
	}

		public static Map<String, Object> getParamMapForMapping(LiabilityGLMapping liabilityMapping){
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("status", 'V');
			paramMap.put("moduleCd",liabilityMapping.getModuleCd());
			paramMap.put("mappingCd",liabilityMapping.getMappingCd());
			paramMap.put("mappingName",liabilityMapping.getMappingName());
			paramMap.put("mainGl",liabilityMapping.getMainGL());
			paramMap.put("subGl",liabilityMapping.getSubGL());
			paramMap.put("srNo",liabilityMapping.getSrNo());
			paramMap.put("amtCalc",liabilityMapping.getAmtCalc());
			paramMap.put("drcdFlag",liabilityMapping.getDrCrFlag());
			paramMap.put("txnType",liabilityMapping.getTxnType());
			paramMap.put("subBifurcation",liabilityMapping.getSubBifurcation());
			paramMap.put("remark",liabilityMapping.getRemarks());
			paramMap.put("createdBy",liabilityMapping.getCreatedBy());
			paramMap.put("updatedBy",liabilityMapping.getLastUpdatedBy());
			paramMap.put("metaRemarks",liabilityMapping.getMetaRemarks());
			paramMap.put("entityCd",liabilityMapping.getEntityCd());
			paramMap.put("tdsApplicable",liabilityMapping.getTdsApplicable());
			paramMap.put("rcApplicable",liabilityMapping.getRcApplicable());
			paramMap.put("gstTdsApplicable",liabilityMapping.getGstTdsApplicable());

			return paramMap;
	}

		public static final String GET_MAPPING_CD_NAME_FOR_MODULE_CD= "select  distinct mapping_cd, mapping_name from accounts.ecgc_acct_modulewise_liability_gl_mapping where module_cd =:moduleCd";

		public static final String UPDATE_Module_Mapping_Codes =/* "UPDATE accounts.ecgc_acct_modulewise_liability_gl_mapping"
				+"SET mapping_name=:mappingName, maingl_cd=:mainGl, subgl_cd=:subGl, dr_cr_flag=:drcdFlag, amt_calc=:amtCalc, remarks=:remark, "
				+ "txn_type=:txnType, sub_biurcation=:subBifurcation,"
				+" entity_cd=:gstTdsApplicable, tds_applicable=:tdsApplicable, rc_applicable=:rcApplicable, gst_tds_applicable=:gstTdsApplicable WHERE module_cd=:moduleCd AND mapping_cd=:mappingCd";
				*/
				
				"UPDATE accounts.ecgc_acct_modulewise_liability_gl_mapping SET mapping_name=:mappingName, maingl_cd=:mainGl, "
				+ "subgl_cd=:subGl, dr_cr_flag=:drcdFlag, amt_calc=:amtCalc, remarks=:remark , txn_type=:txnType,"
				+ "sub_biurcation=:subBifurcation,tds_applicable=:tdsApplicable, gst_tds_applicable=:gstTdsApplicable,rc_applicable=:rcApplicable"
				+ " where module_cd=:moduleCd and mapping_cd=:mappingCd and sr_no= (:srNo)::varchar " ;
						
}



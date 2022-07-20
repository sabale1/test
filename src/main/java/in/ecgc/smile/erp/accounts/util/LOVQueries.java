package in.ecgc.smile.erp.accounts.util;

public interface LOVQueries {

	public static final String GET_T1_CODE_LIST = "select lov_sub_cd , lov_value , lov_desc from accounts.ecgc_acct_lov_mst ealm where lov_sub_cd IN ('T1') and del_flag = 'false' ";

	public static final String GET_T2_CODE_LIST = "select lov_sub_cd , lov_value , lov_desc from accounts.ecgc_acct_lov_mst ealm where lov_sub_cd IN ('T2') and del_flag = 'false' ";

	public static final String GET_ALL_LOV_MST_ENTRIES="SELECT lov_cd,lov_sub_cd,lov_value,lov_desc,created_dt,del_flag"
			+ " FROM accounts.ecgc_acct_lov_mst ";

	public static final String INSERT_LOV_MST_ENTRY_DETAILS = "INSERT INTO ecgc_acct_lov_mst"
			+ "(lov_cd,lov_sub_cd,lov_value,lov_desc, created_by,created_dt,del_flag )"
			+ "VALUES(:lovCd,:lovSubCd,:lovValue,:lovDesc,:createdBy, now(),:delFlag)";

	public static final String GET_LOV_MST_ENTRY = "SELECT * FROM ecgc_acct_lov_mst WHERE  lov_cd = :lovCd "
			+ "AND lov_sub_cd = :lovSubCd AND lov_value = :lovValue";

	public static final String UPDATE_LOV_MST_ENTRY = "UPDATE ecgc_acct_lov_mst SET lov_desc = :lovDesc , del_flag = :delFlag , last_updated_by = :lastUpdatedBy, last_updated_dt = now()"
			+ " WHERE lov_cd = :lovCd AND lov_sub_cd = :lovSubCd AND lov_value = :lovValue ";

	public static final String DISABLE_LOV_ENTRY = "UPDATE ecgc_acct_lov_mst SET del_flag = true , last_updated_by = :lastUpdatedBy, last_updated_dt = now()"
			+ " WHERE lov_cd = :lovCd AND lov_sub_cd = :lovSubCd AND lov_value = :lovValue";

	public static final String GET_PAY_TO_LIST = "select lov_sub_cd , lov_value , lov_desc from accounts.ecgc_acct_lov_mst ealm where lov_sub_cd IN ('PAY_TO_TYPE') and del_flag = 'false' ";

	public static final String GET_CURRENCY_VAL_LIST = "select lov_sub_cd , lov_value , lov_desc from accounts.ecgc_acct_lov_mst ealm where lov_sub_cd IN ('CURRENCY') and del_flag = 'false' ORDER BY lov_value ";

	public static final String GET_SECTION_CODE_LIST = "select lov_sub_cd , lov_value , lov_desc from accounts.ecgc_acct_lov_mst ealm where lov_sub_cd IN ('SECTION_CD') and del_flag = 'false' ";

	public static final String GET_REQUEST_TYPE_LIST = "select lov_sub_cd , lov_value , lov_desc from accounts.ecgc_acct_lov_mst ealm where lov_sub_cd IN ('REQUEST_TYPE') and del_flag = 'false' ";

	public static final String GET_FTR_FOR_LIST = "select lov_sub_cd , lov_value , lov_desc from accounts.ecgc_acct_lov_mst ealm where lov_sub_cd IN ('FTR_FOR') and del_flag = 'false' ";

	public static final String GET_Dishonor_Reasons_LIST = "select lov_sub_cd , lov_value , lov_desc from accounts.ecgc_acct_lov_mst ealm where lov_sub_cd IN ('DISHNR_REASON') and del_flag = 'false' ";

	public static final String GET_INSTRUMENT_TYPE_LIST = "select lov_sub_cd , lov_value , lov_desc from accounts.ecgc_acct_lov_mst ealm where lov_sub_cd = 'INSTRUMENT_TYPE' and del_flag = 'false' " ;

	public static final String GET_SCHEDULE_TITLE_DETAIL = "select lov_sub_cd,lov_value ,lov_desc from accounts.ecgc_acct_lov_mst ealm where lov_sub_cd = 'SCHEDULE_TITLE_DETAIL' and del_flag = 'false' ";

	public static final String GET_PREFIX_LIST = "select lov_sub_cd,lov_value ,lov_desc from accounts.ecgc_acct_lov_mst ealm where lov_sub_cd = 'PREFIX' and del_flag = 'false' ";
}


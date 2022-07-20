package in.ecgc.smile.erp.accounts.util;

public interface ScheduleCdGlMappingQueries{
static String ADD_SCHEDULECDGLMAPPING_DATA="INSERT INTO accounts.ecgc_acct_schedule_cd_gl_mapping(maingl_cd,subgl_cd,schedule_cd,schedule_item_cd,created_by,last_updated_by,created_dt,last_updated_dt,remarks,del_flag) VALUES (:mainglCd,:subglCd,:scheduleCd,:scheduleItemCd,:createdBy,:lastUpdatedBy,now(),:lastUpdatedDt,:remarks,:delFlag)";

static String GET_SCHEDULECDGLMAPPING_DATA="SELECT seq_no,maingl_cd,subgl_cd,schedule_cd,schedule_item_cd,created_by,last_updated_by,created_dt,last_updated_dt,remarks,del_flag FROM accounts.ecgc_acct_schedule_cd_gl_mapping";

static String UPDATE_SCHEDULECDGLMAPPING_DATA="UPDATE accounts.ecgc_acct_schedule_cd_gl_mapping SET maingl_cd=:mainglCd, subgl_cd=:subglCd, schedule_cd=:scheduleCd, schedule_item_cd=:scheduleItemCd, created_by=:createdBy, last_updated_by=:lastUpdatedBy, created_dt=:createdDt, last_updated_dt=now(), remarks=:remarks, del_flag=:delFlag WHERE seq_no=:seqNo";

static String GET_SCHEDULECDGLMAPPING_DATA_BY_ID="SELECT seq_no,maingl_cd,subgl_cd,schedule_cd,schedule_item_cd,created_by,last_updated_by,created_dt,last_updated_dt,remarks,del_flag FROM accounts.ecgc_acct_schedule_cd_gl_mapping WHERE seq_no=:seqNo";

}

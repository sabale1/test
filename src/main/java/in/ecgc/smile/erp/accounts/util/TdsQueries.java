package in.ecgc.smile.erp.accounts.util;
public interface TdsQueries{

static String ADD_TDS_DATA="INSERT INTO accounts.ecgc_acct_section_of_tds(tds_section,particulars,sub_particular,threshold_limit,tds_rate,created_dt,created_by,last_updated_dt,last_updated_by,meta_status) VALUES (:tdsSection,:particulars,:subParticular,:thresholdLimit,:tdsRate,now(),:createdBy,:lastUpdatedDt,:lastUpdatedBy,:metaStatus)";
static String GET_TDS_DATA="SELECT sr_no,tds_section,particulars,sub_particular,threshold_limit,tds_rate,created_dt,created_by,last_updated_dt,last_updated_by,meta_status FROM accounts.ecgc_acct_section_of_tds";
static String UPDATE_TDS_DATA="UPDATE accounts.ecgc_acct_section_of_tds SET sr_no=:srNo, tds_section=:tdsSection, particulars=:particulars, sub_particular=:subParticular, threshold_limit=:thresholdLimit, tds_rate=:tdsRate, last_updated_dt= now(), last_updated_by=:lastUpdatedBy, meta_status=:metaStatus WHERE sr_no=:srNo";
static String GET_TDS_DATA_BY_ID="SELECT sr_no,tds_section,particulars,sub_particular,threshold_limit,tds_rate,created_dt,created_by,last_updated_dt,last_updated_by,meta_status FROM accounts.ecgc_acct_section_of_tds WHERE sr_no=:srNo";
}

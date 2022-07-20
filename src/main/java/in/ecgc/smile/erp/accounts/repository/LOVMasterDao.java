package in.ecgc.smile.erp.accounts.repository;

import java.util.List;
import java.util.Map;

import in.ecgc.smile.erp.accounts.model.LOVMaster;

public interface LOVMasterDao {
	
	Integer addLovMstEntry(LOVMaster lovmst);
	
	Map<String, String> getT1codes() ;
	
	Map<String, String> getT2codes() ;


	List<LOVMaster> viewallLOVMstEntries();

	LOVMaster viewLovEntry(String lovCd, String lovSubCd, String lovValue);

	int modifyLovEntry(LOVMaster lov);

	Integer disableLovEntry(String lovCd, String lovSubCd, String lovValue);

	Map<String, String> getpayTo();

	Map<String, String> getcurrencyVal();

	Map<String, String> getsectionCodes();

	Map<String, String> getrequestTypes();

	Map<String, String> getfrtFor();

	Map<String, String> getdishonorReasons();
	

	public Map<String, String> getInstrumentTypes();
	
	public Map<String, String> getScheduleTitleDetails();
	
	public Map<String, String> getPrefixTypes();

	
}

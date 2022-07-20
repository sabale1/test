package in.ecgc.smile.erp.accounts.service;

import java.util.List;
import java.util.Map;

import in.ecgc.smile.erp.accounts.model.LOVMaster;

public interface LOVMasterService {
	
	Map<String, String> t1Codes();
	
	Map<String, String> t2Codes();


	int addLovMstEntry(LOVMaster lovmst);
	
	List<LOVMaster> viewallLOVMstEntries();
	
	LOVMaster viewLovEntry(String lovCd, String lovSubCd, String lovValue);	
	
	int modifyLovEntry(LOVMaster lov);
	
	Integer disableLovEntry(String lovCd, String lovSubCd, String lovValue);
	
	Map<String, String> payTo();

	Map<String, String> currencyVal();

	Map<String, String> sectionCodes();
	
	public Map<String, String> getInstrumentTypes();
	
	public Map<String, String> getScheduleTitleDetails();


	public Map<String, String> getPrefixTypes();

	Map<String, String> requestTypes();

	Map<String, String> frtFor();

	Map<String, String> dishonorReasons();


}

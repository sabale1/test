package in.ecgc.smile.erp.accounts.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.exception.LOVMstEntryNotFound;
import in.ecgc.smile.erp.accounts.model.LOVMaster;
import in.ecgc.smile.erp.accounts.repository.LOVMasterDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LOVMasterServiceImpl implements LOVMasterService {

	@Autowired(required = true)
	LOVMasterDao lovmstdao;

	@Override
	public int addLovMstEntry(LOVMaster lovmst) {
		log.info("Inside LOVMasterServiceImpl#addLovMstEntry");
		return lovmstdao.addLovMstEntry(lovmst);

	}

	@Override
	public List<LOVMaster> viewallLOVMstEntries() {
		log.info("Inside LOVMasterServiceImpl#viewallLOVMstEntries");
		List<LOVMaster> result = lovmstdao.viewallLOVMstEntries();
		return result;
	}

	@Override
	public LOVMaster viewLovEntry(String lovCd, String lovSubCd, String lovValue) {
		log.info("Inside LOVMasterServiceImpl#viewLovEntry");
		return lovmstdao.viewLovEntry(lovCd, lovSubCd, lovValue);
	}

	@Override
	public int modifyLovEntry(LOVMaster lov) {
		log.info("Inside LOVMasterServiceImpl#modifyLovEntry");
		return lovmstdao.modifyLovEntry(lov);
	}

	@Override
	public Integer disableLovEntry(String lovCd, String lovSubCd, String lovValue) {
		log.info("Inside LOVMasterServiceImpl#disableLovEntry");
		LOVMaster lov = viewLovEntry(lovCd, lovSubCd, lovValue);
		return lovmstdao.disableLovEntry(lovCd, lovSubCd, lovValue);
	}

	@Override
	public Map<String, String> t1Codes() {
		log.info("Inside LOVMasterServiceImpl#t1Codes");
		return lovmstdao.getT1codes();
	}

	@Override
	public Map<String, String> t2Codes() {
		log.info("Inside LOVMasterServiceImpl#t2Codes");
		return lovmstdao.getT2codes();
	}
	
	@Override
	public Map<String, String> payTo() {
		log.info("Inside LOVMasterServiceImpl#payTo");
		return lovmstdao.getpayTo();
	}

	@Override
	public Map<String, String> currencyVal() {
		log.info("Inside LOVMasterServiceImpl#currencyVal");
		return lovmstdao.getcurrencyVal();
	}

	@Override
	public Map<String, String> sectionCodes() {
		log.info("Inside LOVMasterServiceImpl#sectionCodes");
		return lovmstdao.getsectionCodes();
	}

	
	@Override
	public Map<String, String> requestTypes() {
		log.info("Inside LOVMasterServiceImpl#requestTypes");
		return lovmstdao.getrequestTypes();
	}
	
	@Override
	public Map<String, String> frtFor() {
		log.info("Inside LOVMasterServiceImpl#frtFor");
		return lovmstdao.getfrtFor();
	}
	
	@Override
	public Map<String, String> dishonorReasons() {
		log.info("Inside LOVMasterServiceImpl#dishonorReasons");
		return lovmstdao.getdishonorReasons();
	}

	@Override
	public Map<String, String> getInstrumentTypes() {
		log.info("Inside LOVMasterServiceImpl#getInstrumentTypes");
		return lovmstdao.getInstrumentTypes();
	}

	@Override
	public Map<String, String> getScheduleTitleDetails() {
		log.info("Inside LOVMasterServiceImpl#getScheduleTitleDetails");
		return lovmstdao.getScheduleTitleDetails();
	}

	@Override
	public Map<String, String> getPrefixTypes() {
		log.info("Inside LOVMasterServiceImpl#getPrefixTypes");
		return lovmstdao.getPrefixTypes();
	}
	
	
}

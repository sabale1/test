package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.model.AutoGLTxnRequestModel;
import in.ecgc.smile.erp.accounts.repository.AutoGLTxnDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AutoGLTxnServiceImpl implements AutoGLTxnService{

	@Autowired
	AutoGLTxnDao autoGLTxnDao;
	
	@Override
	public Integer createTxnRequest(AutoGLTxnRequestModel autoGLTxnRequestModel) throws Exception {
		log.info("Inside AutoGLTxnServiceImpl#createTxnRequest");
		if (autoGLTxnDao.createTxnRequest(autoGLTxnRequestModel) >= 1) 
			return 1;
			else
				throw new Exception("Something Went Worng");
		
	}

	@Override
	public List<AutoGLTxnRequestModel> getAllAutoTxnRequest() {
		log.info("Inside AutoGLTxnServiceImpl#getAllAutoTxnRequest");
		return autoGLTxnDao.getAllAutoTxnRequest();
	}

	@Override
	public AutoGLTxnRequestModel getAllAutoTxnRequestById(String requestId) {
		log.info("Inside AutoGLTxnServiceImpl#getAllAutoTxnRequestById");
		return autoGLTxnDao.getAllAutoTxnRequestById(requestId);
	}

	@Override
	public Integer updateAutoTxnRequest(AutoGLTxnRequestModel autoGLTxnRequestModel) {
		log.info("Inside AutoGLTxnServiceImpl#updateAutoTxnRequest");
		return null;
	}

	@Override
	public Integer updateRequestStatus(String reqId, String status) {
		log.info("Inside AutoGLTxnServiceImpl#updateRequestStatus");
		return autoGLTxnDao.updateRequestStatus(reqId, status);
	}

	
}

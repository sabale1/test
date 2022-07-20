package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.exception.PettyCashDetailsNotFound;
import in.ecgc.smile.erp.accounts.model.PettyCashMaster;
import in.ecgc.smile.erp.accounts.repository.PettyCashMasterDao;
import in.ecgc.smile.erp.accounts.service.sts.StateTransitionService;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PettyCashMasterServiceImpl implements PettyCashMasterService {

	@Autowired
	PettyCashMasterDao pettyCashMasterDao;

	@Autowired
	UserInfoService userInfoService;

	@Autowired
	StateTransitionService stateTransitionService;

	@Override
	public Integer addPettyCashDetails(PettyCashMaster pettyCashMaster) {
		log.info("Inside PettyCashMasterServiceImpl#addPettyCashDetails");
		pettyCashMaster.setRequisitionNo(pettyCashMasterDao.getRequisitionNo(pettyCashMaster.getLogicalLocCode(),
				pettyCashMaster.getFiscalYr()));

		if (userInfoService.getUser() == null)
			pettyCashMaster.setCreatedBy("ACCOUNTS");
		else
			pettyCashMaster.setCreatedBy(userInfoService.getUser());

		String startState = "request_generation";
		String userId = userInfoService.getUser();
		String status = "request_submited";

		Long processInstanceId = null;
		try {
			processInstanceId = stateTransitionService.generateProcessInstance(userId, "petty_cash", "accounts",
					startState);
		} catch (Exception e) {
			log.info("Exception in generateProcessInstance : {}", e);
		}
		int result = pettyCashMasterDao.addPettyCashDetails(pettyCashMaster);
		pettyCashMasterDao.updateProcessInstanceId(pettyCashMaster.getRequisitionNo(),
				pettyCashMaster.getLogicalLocCode(), processInstanceId);

		// update process
		try {
			String res = stateTransitionService.updateProcessInstance(processInstanceId, status, userId);
		} catch (Exception e) {
			log.info("Exception in updateProcessInstance : {}", e);
		}

		if (result == 1)
			return pettyCashMaster.getRequisitionNo();
		else
			return 0;
	}

	@Override
	public List<PettyCashMaster> listAllPettyCashMaster() {
		log.info("Inside PettyCashMasterServiceImpl#listAllPettyCashMaster");
		try {
			return pettyCashMasterDao.listAllPettyCash();
		} catch (Exception e) {
			log.info("Exception in listAllPettyCash : {}", e);
			return null;
		}
	}

	@Override
	public PettyCashMaster findByRequisitionNo(Integer requisitionNo, String logicalLocCode) {
		log.info("Inside PettyCashMasterServiceImpl#findByRequisitionNo");
		PettyCashMaster pettyCashMaster = pettyCashMasterDao.findByRequisitionNo(requisitionNo, logicalLocCode);
		if (pettyCashMaster == null) {
			throw new PettyCashDetailsNotFound(
					"No petty cash details found with the given requisitionNo, logicalLocCode",
					new Integer[] { requisitionNo });
		}
		return pettyCashMaster;
	}

	@Override
	public Integer approvedStatus(PettyCashMaster pettyCashMaster) {
		log.info("Inside PettyCashMasterServiceImpl#approvedStatus");
		if (userInfoService.getUser() == null)
			pettyCashMaster.setLastUpdatedBy("ACCOUNTS");
		else
			pettyCashMaster.setCreatedBy(userInfoService.getUser());

		String userId = userInfoService.getUser();
		String status = "request_approved_or_rejected";

		Long processInstanceId = null;
		try {
			processInstanceId = pettyCashMasterDao.getProcessInstanceId(pettyCashMaster.getRequisitionNo(),
					pettyCashMaster.getLogicalLocCode());
			log.info("processInstanceId  {} " , processInstanceId);
		} catch (Exception e) {
			log.info("Exception in getProcessInstanceId : {}", e);
		}
		Integer result = pettyCashMasterDao.approvedStatus(pettyCashMaster);

		pettyCashMasterDao.updateProcessInstanceId(pettyCashMaster.getRequisitionNo(),
				pettyCashMaster.getLogicalLocCode(), processInstanceId);
		// update process
		try {
			String res = stateTransitionService.updateProcessInstance(processInstanceId, status, userId);
		} catch (Exception e) {
			log.info("Exception in updateProcessInstance : {}", e);
		}

		return result;
	}

	@Override
	public Integer updatePettyCash(Integer requisitionNo, String logicalLocCode, PettyCashMaster pettyCashUpdate) {
		log.info("Inside PettyCashMasterServiceImpl#updatePettyCash");
		PettyCashMaster pettyCashMaster = pettyCashMasterDao.findByRequisitionNo(requisitionNo, logicalLocCode);
		if (pettyCashMaster == null) {
			throw new PettyCashDetailsNotFound(
					"No petty cash details found with the given requisitionNo, logicalLocCode",
					new Integer[] { requisitionNo });
		}
		return pettyCashMasterDao.updatePettyCash(requisitionNo, logicalLocCode, pettyCashUpdate);
	}

	@Override
	public Long getProcessInstanceId(Integer requisitionNo, String logicalLocCode) {
		log.info("Inside PettyCashMasterServiceImpl#getProcessInstanceId");
		return pettyCashMasterDao.getProcessInstanceId(requisitionNo, logicalLocCode);
	}

	@Override
	public Integer updateProcessInstanceId(Integer requisitionNo, String logicalLocCode, Long processInstanceId) {
		log.info("Inside PettyCashMasterServiceImpl#updateProcessInstanceId");
		return pettyCashMasterDao.updateProcessInstanceId(requisitionNo, logicalLocCode, processInstanceId);
	}

	@Override
	public Integer approveMultipleRequest(List<PettyCashMaster> pettyCashMasters) {
		log.info("Inside PettyCashMasterServiceImpl#approveMultipleRequest");
		try {
			int result = 0;
			for (PettyCashMaster obj : pettyCashMasters) {
				result += pettyCashMasterDao.approvedStatus(obj);

			}
			return result;

		} catch (Exception e) {
			log.info("Exception Occured {}", e);
			return 0;
		}
	}

}

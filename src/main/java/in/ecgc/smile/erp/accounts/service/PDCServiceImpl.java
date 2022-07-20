package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.exception.PDCEntryAlreadyExists;
import in.ecgc.smile.erp.accounts.model.PostDatedCheque;
import in.ecgc.smile.erp.accounts.repository.PDCDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PDCServiceImpl implements PDCService{

	@Autowired
	PDCDao pdcDao;
	@Override
	public Boolean createPDCEntry(PostDatedCheque pdc) {
		log.info("Inside PDCServiceImpl#createPDCEntry");
		if (pdcDao.viewByChequeNo(pdc.getSrNo())==null) {
			int result = pdcDao.createPDCEntry(pdc);
			
			if (result == 1) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new PDCEntryAlreadyExists(
					new String[] {pdc.getInstrumentNo()},
					"Cheque Entry Already Exists");
		}
	}

	@Override
	public List<PostDatedCheque> listAllPDC() {
		log.info("Inside PDCServiceImpl#listAllPDC");
		return pdcDao.listAllPDC();
	}

	@Override
	public PostDatedCheque viewByChequeNo(Integer cheqId) {
		log.info("Inside PDCServiceImpl#viewByChequeNo");
		return pdcDao.viewByChequeNo(cheqId);
	}

	@Override
	public List<PostDatedCheque> viewByStatus(Character status) {
		log.info("Inside PDCServiceImpl#viewByStatus");
		return pdcDao.viewByStatus(status);
	}

	@Override
	public Integer changeStatus(String chqNo, PostDatedCheque pdc) {
		log.info("Inside PDCServiceImpl#changeStatus");
		return pdcDao.changeStatus(chqNo,pdc);
	}

	@Override
	public PostDatedCheque checkUnique(String chequeNo, String chequeDate) {
		log.info("Inside PDCServiceImpl#checkUnique");
		return pdcDao.checkUnique(chequeNo, chequeDate);
	}

}

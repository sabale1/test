package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.exception.RecordNotFoundException;
import in.ecgc.smile.erp.accounts.model.StampParameterModel;
import in.ecgc.smile.erp.accounts.repository.StampDao;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Stamp Parameter
 * 
 * @author Amitesh Banerjee
 * @version 1.0 27-April-2020
 *
 */
@Slf4j
@Service
public class StampServiceImpl implements StampService {

	@Autowired(required = true)
	StampDao stampDao;

	@Override
	public Integer addStampParameter(StampParameterModel stampParameter) {
		log.info("Inside StampServiceImpl#addStampParameter");
		return stampDao.addStampParameter(stampParameter);
	}

	@Override
	public List<StampParameterModel> allStampParameter() {
		log.info("Inside StampServiceImpl#allStampParameter");
		return stampDao.allStampParameter();
	}

	@Override
	public StampParameterModel updateStampParameter(Integer stampCode, StampParameterModel stampParameterUpdate) {
		log.info("Inside StampServiceImpl#updateStampParameter");
		StampParameterModel model = stampDao.viewByStampCode(stampCode);
//		if(model==null)
//		{
//			log.error("Exception in StampDaoImpl#UpdateStampParameter");
//			throw new DatabaseOperationFailException("Stamp Parameter is invalid");	
//		}
//		return stampDao.updateStampParameter(stampCode,stampParameterUpdate);
		if (model != null) {
			return stampDao.updateStampParameter(stampCode, stampParameterUpdate);
		} else {
			log.error("Exception in StampDaoImpl#UpdateStampParameter");
			throw new RecordNotFoundException("Stamp Parameter Not found while updating");
		}
	}

	@Override
	public StampParameterModel viewByStampCode(Integer stampCode) {
		log.info("Inside StampServiceImpl#viewByStampCode");
		return stampDao.viewByStampCode(stampCode);
	}

	@Override
	public Double getStampAmtByFromAndToAmt(Double receiptAmount) {
		log.info("Inside StampServiceImpl#getStampAmtByFromAndToAmt");
		return stampDao.getStampAmtByFromAndToAmt(receiptAmount);
	}

	@Override
	public Boolean checkFromAmtTOToAmt(Double fromAmount, Double toAmount) {
		log.info("Inside StampServiceImpl#checkFromAmtTOToAmt");
		return stampDao.checkFromAmtTOToAmt(fromAmount, toAmount);
	}

}

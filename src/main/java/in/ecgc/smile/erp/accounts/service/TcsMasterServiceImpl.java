package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.model.TcsMaster;
import in.ecgc.smile.erp.accounts.repository.TcsMasterDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TcsMasterServiceImpl implements TcsMasterService {

	@Autowired
	TcsMasterDao tcsMasterDao;

	@Override
	public List<TcsMaster> listAllTcs() {
		log.info("Inside TcsMasterServiceImpl#listAllTcs");
		return tcsMasterDao.listAllTcs();
	}

	@Override
	public Boolean addTcsMaster(TcsMaster tcsMaster) {
		log.info("Inside TcsMasterServiceImpl#addTcsMaster");
		if (tcsMasterDao.checkExistingData(tcsMaster.getFinYear(), tcsMaster.getFromAmount(), tcsMaster.getToAmount(),
				tcsMaster.getSex(), tcsMaster.getFromDate(), tcsMaster.getToDate()) != null) {
			log.info("Slab already exist!!!");
			return false;
		} else if (tcsMaster.getFromDate().after(tcsMaster.getToDate())) {
			log.info("from Date should be less than To Date");
			return false;
		} else if (tcsMaster.getFromAmount() >= tcsMaster.getToAmount()) {
			log.info("from Amount should be less than To Amount");
			return false;
		} else {
			int result = tcsMasterDao.addTcsMaster(tcsMaster);
			if (result == 1) {
				return true;
			} else {
				return false;
			}

		}
	}

	@Override
	public Boolean checkFromAmtTOToAmt(Double fromAmount, Double toAmount) {
		log.info("Inside TCSServiceImpl#checkFromAmtTOToAmt");
		return tcsMasterDao.checkFromAmtTOToAmt(fromAmount, toAmount);
	}

}

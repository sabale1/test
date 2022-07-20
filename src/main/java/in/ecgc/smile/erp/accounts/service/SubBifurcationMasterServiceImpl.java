package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.model.SubBifurcations;
import in.ecgc.smile.erp.accounts.repository.SubBifurcationMasterDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubBifurcationMasterServiceImpl implements SubBiFurcationMasterService {

	@Autowired
	SubBifurcationMasterDao subBifurcationDao;

	@Override
	public List<SubBifurcations> getSubBifurcations() {
		log.info("Inside SubBifurcationMasterServiceImpl#getSubBifurcations");
		return subBifurcationDao.getSubBifurcations();
	}

	@Override
	public Integer addSubBifurcation(SubBifurcations subBifurcations) {
		log.info("Inside SubBifurcationMasterServiceImpl#addSubBifurcation");
		Integer result = subBifurcationDao.addSubBifurcation(subBifurcations);
		return result;
	}

	@Override
	public SubBifurcations updatedSubBifurcations(SubBifurcations currentSubBifurcations,
			SubBifurcations updatedSubBifurcations) {
		log.info("Inside SubBifurcationMasterServiceImpl#updatedSubBifurcations");
		currentSubBifurcations.setSubBifurcationLevel(updatedSubBifurcations.getSubBifurcationLevel());
		currentSubBifurcations.setDescription(updatedSubBifurcations.getDescription());
		currentSubBifurcations.setLastUpdatedBy(updatedSubBifurcations.getLastUpdatedBy());
		log.info("after setting updated values to currentSubBifurcations : {} " , currentSubBifurcations);
		return subBifurcationDao.updateSubBifurcationLevel(currentSubBifurcations);
	}

	@Override
	public SubBifurcations getSubBifurcationsByLevel(String subBifurcationLevel) {
		log.info("Inside SubBifurcationMasterServiceImpl#getSubBifurcationsByLevel");
		return subBifurcationDao.getSubBifurcationsByLevel(subBifurcationLevel);
	}

	@Override
	public List<String> getSubBifurcationsLevel() {
		log.info("Inside SubBifurcationMasterServiceImpl#getSubBifurcationsLevel");
		return subBifurcationDao.getSubBifurcationLevels();
	}

}

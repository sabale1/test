package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.exception.SubBifurcationValueNotFoundException;
import in.ecgc.smile.erp.accounts.model.SubBifurcationValue;
import in.ecgc.smile.erp.accounts.repository.SubBifurcationValueDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubBifurcationValueServiceImpl implements SubBifurcationValueService {

	@Autowired
	SubBifurcationValueDao subBifurcationValueDao;

	@Override
	public List<SubBifurcationValue> getSubBifurcationsDtlList() {
		log.info("Inside SubBifurcationValueServiceImpl#getSubBifurcationsDtlList");
		try {
			return subBifurcationValueDao.getSubBifurcationsDtlList();
		} catch (Exception e) {
			log.error("ERROR: Service getSubBifurcationsDtlList() {}", e.getMessage());
			throw new SubBifurcationValueNotFoundException();

		}
	}

	@Override
	public SubBifurcationValue getSubBifurcationsDtlDataById(String bifurcationLevelCode, String bifurcationValueCode) {
		log.info("Inside SubBifurcationValueServiceImpl#getSubBifurcationsDtlDataById");
		try {
			return subBifurcationValueDao.getSubBifurcationsDtlDataById(bifurcationLevelCode, bifurcationValueCode);

		} catch (Exception e) {
			throw new SubBifurcationValueNotFoundException();
		}
		// return
		// subBifurcationValueDao.getSubBifurcationsDtlDataById(bifurcationLevelCode,bifurcationValueCode);
	}

	@Override
	public boolean addSubBifurcationsDtlData(SubBifurcationValue subBifurcationValue) {
		log.info("Inside SubBifurcationValueServiceImpl#addSubBifurcationsDtlData");
		return subBifurcationValueDao.addSubBifurcationsDtlData(subBifurcationValue);
	}

	@Override
	public boolean updateSubBifurcationsDtlData(String bifurcationLlevelCode, String bifurcationValueCode,
			SubBifurcationValue subBifurcationValue) {
//		int result =  subBifurcationValueDao.updateSubBifurcationsDtlData(bifurcationLlevelCode, bifurcationValueCode, subBifurcationValue);
//		if(result ==1) {
//			return true;
//		}else
//			return false;
		log.info("Inside SubBifurcationValueServiceImpl#updateSubBifurcationsDtlData");
		return subBifurcationValueDao.updateSubBifurcationsDtlData(bifurcationLlevelCode, bifurcationValueCode,
				subBifurcationValue);

	}

	@Override
	public boolean disableSubBifurcationValue(String bifurcationLevelCode, String bifurcationValueCode) {
		log.info("Inside SubBifurcationValueServiceImpl#disableSubBifurcationValue");
		return subBifurcationValueDao.disableSubBifurcationValue(bifurcationLevelCode, bifurcationValueCode);
	}

	@Override
	public String getBifurcationCode(String levelCode) {
		log.info("Inside SubBifurcationValueServiceImpl#getBifurcationCode");
		return subBifurcationValueDao.getBifurcationCode(levelCode);
	}

	@Override
	public List<SubBifurcationValue> getAllSubBifurcationValueCodeByLevelCode(String levelCode) {
		log.info("Inside SubBifurcationValueServiceImpl#getAllSubBifurcationValueCodeByLevelCode");
		return subBifurcationValueDao.getAllSubBifurcationValueCodeByLevelCode(levelCode);
	}

	@Override
	public List<SubBifurcationValue> findSubBifurcationValueList(String mainGLCode, String subGLCode) {
		log.info("Inside SubBifurcationValueServiceImpl#findSubBifurcationValueList");
	//	System.err.println("on service Impl ");
		return subBifurcationValueDao.findSubBifurcationValueList(mainGLCode, subGLCode);
	}
}

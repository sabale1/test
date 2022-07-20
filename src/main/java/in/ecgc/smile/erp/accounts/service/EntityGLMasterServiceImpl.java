package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.repository.EntityGLMasterDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EntityGLMasterServiceImpl implements EntityGLMasterService {
	// private static final Logger log =
	// LoggerFactory.getLogger(EntityGLMasterServiceImpl.class);

	@Autowired
	EntityGLMasterDao entityGLMasterDao;
	@Autowired
	ReceiptService receiptService;

	/**
	 * Add new GL code
	 * 
	 * service implementation to add new GL code into database throws
	 * GLCodeImcompleteDataException{@link in.ecgc.erp.accounts.exception.GLCodeImcompleteDataException}
	 * if input data is invalid.
	 */
	@Override
	public Integer addGLCode(EntityGL entityGL) {
		log.info("Inside EntityGLMasterServiceImpl#addGLCode");
		return entityGLMasterDao.addGLCode(entityGL);
	}

	/**
	 * View all GL codes
	 * 
	 * service implementation to list all GL codes
	 * 
	 */
	@Override
	public List<EntityGL> listAllGLCodes() {
		log.info("Inside EntityGLMasterServiceImpl#listAllGLCodes");
		List<EntityGL> list = entityGLMasterDao.listAllGLCodes();
		if (list == null) {
			log.info("Service: Reading all GL Codes , Returning empty GL Code List");
			return null;
		} else
			return list;
	}

	/**
	 * Find GL code details by entityGLCode
	 * 
	 * service implementation to find details of GL code
	 * 
	 */
	@Override
	public EntityGL findGLByGLCode(String mainGLCode, String subGLCode) {
		log.info("Inside EntityGLMasterServiceImpl#findGLByGLCode");
		return entityGLMasterDao.findGLByGLCode(mainGLCode, subGLCode);
	}

	/**
	 * Update GL code details
	 * 
	 * service implementation to update details of GL code
	 * 
	 */
	@Override
	public EntityGL updateGLCode(EntityGL currentEntityGL, EntityGL updatedEntityGL) {
		log.info("Inside EntityGLMasterServiceImpl#updateGLCode");

		currentEntityGL.setGlName(updatedEntityGL.getGlName());
		//currentEntityGL.setGlType(updatedEntityGL.getGlType());
		currentEntityGL.setGlSubtype(updatedEntityGL.getGlSubtype());
		currentEntityGL.setBalInd(updatedEntityGL.getBalInd());
		currentEntityGL.setZeroBalFlg(updatedEntityGL.getZeroBalFlg());
		currentEntityGL.setCashaccount(updatedEntityGL.getCashaccount());
		currentEntityGL.setMetaRemarks(updatedEntityGL.getMetaRemarks());
		currentEntityGL.setLastUpdatedBy(updatedEntityGL.getLastUpdatedBy());
		currentEntityGL.setActive(updatedEntityGL.getActive());
		currentEntityGL.setMetaRemarks(updatedEntityGL.getMetaRemarks());
		currentEntityGL.setOldCd(updatedEntityGL.getOldCd());
		currentEntityGL.setCashFlow(updatedEntityGL.getCashFlow());
		currentEntityGL.setPlLevel(updatedEntityGL.getPlLevel());
		currentEntityGL.setIrdaBpaCd(updatedEntityGL.getIrdaBpaCd());
		currentEntityGL.setIrdaCd(updatedEntityGL.getIrdaCd());
		currentEntityGL.setSchedule6(updatedEntityGL.getSchedule6());
		currentEntityGL.setFinancialFormName(updatedEntityGL.getFinancialFormName());
		currentEntityGL.setSubBifurcationLevel(updatedEntityGL.getSubBifurcationLevel());
		currentEntityGL.setBalInd(updatedEntityGL.getBalInd());
		currentEntityGL.setT1(updatedEntityGL.getT1());
		currentEntityGL.setT2(updatedEntityGL.getT2());
		currentEntityGL.setT3(updatedEntityGL.getT3());
		currentEntityGL.setT4(updatedEntityGL.getT4());
		currentEntityGL.setT5(updatedEntityGL.getT5());
		currentEntityGL.setT6(updatedEntityGL.getT6());
		currentEntityGL.setT7(updatedEntityGL.getT7());
		currentEntityGL.setT8(updatedEntityGL.getT8());
		currentEntityGL.setT9(updatedEntityGL.getT9());
		currentEntityGL.setT10(updatedEntityGL.getT10());
		currentEntityGL.setT11(updatedEntityGL.getT11());
		currentEntityGL.setT12(updatedEntityGL.getT12());
		currentEntityGL.setT13(updatedEntityGL.getT13());
		currentEntityGL.setT14(updatedEntityGL.getT14());
		currentEntityGL.setT15(updatedEntityGL.getT15());
		currentEntityGL.setT16(updatedEntityGL.getT16());
		currentEntityGL.setT17(updatedEntityGL.getT17());
		currentEntityGL.setT18(updatedEntityGL.getT18());
		currentEntityGL.setT19(updatedEntityGL.getT19());
		currentEntityGL.setT20(updatedEntityGL.getT20());
		currentEntityGL.setT21(updatedEntityGL.getT21());
		currentEntityGL.setT22(updatedEntityGL.getT22());
		currentEntityGL.setT23(updatedEntityGL.getT23());
		currentEntityGL.setT24(updatedEntityGL.getT24());
		currentEntityGL.setT25(updatedEntityGL.getT25());
		currentEntityGL.setT26(updatedEntityGL.getT26());
		currentEntityGL.setT27(updatedEntityGL.getT27());
		currentEntityGL.setT28(updatedEntityGL.getT28());
		currentEntityGL.setT29(updatedEntityGL.getT29());
		currentEntityGL.setT30(updatedEntityGL.getT30());
		currentEntityGL.setT31(updatedEntityGL.getT31());
		currentEntityGL.setT32(updatedEntityGL.getT32());
		currentEntityGL.setT33(updatedEntityGL.getT33());
		currentEntityGL.setScheduleItemCd(updatedEntityGL.getScheduleItemCd());
		currentEntityGL.setScheduleCd(updatedEntityGL.getScheduleCd());
		log.info("after setting updated values to currentEntityGL : {} " , currentEntityGL);
		return entityGLMasterDao.updateGLCode(currentEntityGL);
	}

	/**
	 * Disable GL code details
	 * 
	 * service implementation to disable GL code
	 * 
	 */
	@Override
	public Integer disableGLCode(EntityGL entityGL) {
		log.info("Inside EntityGLMasterServiceImpl#disableGLCode");
		if (entityGL.getGlIsGroup().equals('N') || entityGL.getGlIsGroup().equals('n'))
			return entityGLMasterDao.disableGLCode(entityGL);
		else
			return -1;

	}

//	@Override
//	public String findSubBifurcations(String mainGLCode, String subGLCode)  {
//		log.info("Inside getSubBifurcations Method");
//		
//		String list = entityGLMasterDao.findSubBifurcations(mainGLCode, subGLCode);
//		//log.error("+++++++++++Bifurcation level Code is +++++++" + list);
//		if(list==null)
//			return null;
//		else
//			return list;
//	}

	@Override
	public Integer getRegularGLTypesByOpeningDay() {
		log.info("Inside EntityGLMasterServiceImpl#getRegularGLTypesByOpeningDay");
		return entityGLMasterDao.getRegularGLTypesByOpeningDay();
	}

	@Override
	public Integer getConfiguredGLTypesByOpeningDay() {
		log.info("Inside EntityGLMasterServiceImpl#getConfiguredGLTypesByOpeningDay");
		return entityGLMasterDao.getConfiguredGLTypesByOpeningDay();
	}

	@Override
	public List<EntityGL> getsubGLCodebyMainGLCode(String mainGLCode) {
		log.info("Inside EntityGLMasterServiceImpl#getsubGLCodebyMainGLCode");
		return entityGLMasterDao.getsubGLCodebyMainGLCode(mainGLCode);
	}

	@Override
	public List<EntityGL> getAllGlByIsGlGroup() {
		log.info("Inside EntityGLMasterServiceImpl#getAllGlByIsGlGroup");
		return entityGLMasterDao.getAllGlByIsGlGroup();
	}

	@Override
	public Integer activateGLCode(EntityGL entityGL) {
		log.info("Inside EntityGLMasterServiceImpl#activateGLCode");
		return entityGLMasterDao.activateGLCode(entityGL);
	
	}

	@Override
	public 	List<EntityGL> getGstGlCodes(String branchGstin , String customerGstin)
 {
		String gstCombination;
		String param1, param2;
		int branchStateCode= Integer.parseInt(branchGstin.substring(0,2));
		int ExporterStateCode=Integer.parseInt(customerGstin.substring(0,2));
		if(branchStateCode == ExporterStateCode)
		{		
			if(receiptService.getAllstatesBystateCode(branchStateCode).getStateUtType().equals("Union Territory")
					||(receiptService.getAllstatesBystateCode(ExporterStateCode).getStateUtType()).equals("Union Territory") )
			{
				gstCombination="CGST-UTGST";
			}
			else {
				gstCombination = "CGST-SGST" ; 
			}
		}
		else  {			
			if(receiptService.getAllstatesBystateCode(branchStateCode).getStateUtType().equals("Union Territory")
					|| (receiptService.getAllstatesBystateCode(ExporterStateCode).getStateUtType()).equals("Union Territory") )
			{
				gstCombination = "CGST-UTGST" ; 
			}
			else {
				gstCombination = "CGST-IGST" ; 
				}		
		}	
		 param1 = gstCombination.substring(0,4);
		 param2 = gstCombination.substring(5);		
		return entityGLMasterDao.getGstGlCodes(param1, param2);
	}

	@Override
	public List<GlTxnDtl> getAllGlTxn(String mainGLCode, String subGLCode) {
		log.info("Inside EntityGLMasterDaoImpl#getAllGlTxn");
		return entityGLMasterDao.getAllGlTxn(mainGLCode, subGLCode);
	}

}

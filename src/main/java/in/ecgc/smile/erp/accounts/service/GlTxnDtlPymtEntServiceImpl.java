package in.ecgc.smile.erp.accounts.service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.exception.FailToInsertDataException;
import in.ecgc.smile.erp.accounts.model.GlTxnDtlPymtEnt;
import in.ecgc.smile.erp.accounts.model.LiabilityGLMapping;
import in.ecgc.smile.erp.accounts.model.Payments;
import in.ecgc.smile.erp.accounts.repository.GlTxnDtlPymtEntDao;
import in.ecgc.smile.erp.accounts.repository.LiabilityDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class GlTxnDtlPymtEntServiceImpl implements GlTxnDtlPymtEntService{

	@Autowired
	GlTxnDtlPymtEntDao glTxnDtlPymtEntDao;

	@Autowired
	LiabilityDao liabilityDao;

	@Autowired
	GlTxnService glTxnService;



	@Override
	@Transactional
	public Boolean addGlTxnDtlPymtEntData(Payments payments){

		log.info("inside GlTxnDtlPymtEntBeServiceImpl#addGlTxnDtlPymtEntData()");

		List<LiabilityGLMapping> mappingList = liabilityDao.getMAppingListforModule(payments.getModuleCd(),
				payments.getMappingCd());

		Double amt = 0.0;
		Integer count = 0;

		if (payments.getPymtInForex().equals('Y')) {
			amt = payments.getAmtPaid() * payments.getExchrateAtPymt();
			amt = BigDecimal.valueOf(amt).setScale(2, RoundingMode.HALF_UP).doubleValue();
		} else {
			amt = payments.getAmtPaid();
		}
		int i = 0;

		for(int j=0; j<payments.getGlTxnDtlPymtEntList().size(); j++) {
			for(int k=0; k<mappingList.size(); k++) {
				if(payments.getGlTxnDtlPymtEntList().get(j).getMainglCd().equals(mappingList.get(k).getMainGL()) && payments.getGlTxnDtlPymtEntList().get(j).getSubglCd().equals(mappingList.get(k).getSubGL()) ) {
					mappingList.get(k).setPlLevel(payments.getGlTxnDtlPymtEntList().get(j).getPlLevel());
					mappingList.get(k).setPlCode(payments.getGlTxnDtlPymtEntList().get(j).getPersonalLedgerCd());
					mappingList.get(k).setSubBifurcationLevel(payments.getGlTxnDtlPymtEntList().get(j).getSubBifurcationLevel());
					mappingList.get(k).setSubBifurcation(payments.getGlTxnDtlPymtEntList().get(j).getSubBifurcationCode());
					mappingList.get(k).setRemarks(payments.getGlTxnDtlPymtEntList().get(j).getTxnRmk());
				}

			}
		}



		for (LiabilityGLMapping glMapping : mappingList) {
			GlTxnDtlPymtEnt glTxnDtlPymtEnt = new GlTxnDtlPymtEnt();

			glTxnDtlPymtEnt.setAdviceNo(payments.getAdviceNo());
			glTxnDtlPymtEnt.setDrCrFlg(glMapping.getDrCrFlag());
			glTxnDtlPymtEnt.setGlTxnType(glMapping.getTxnType());
			glTxnDtlPymtEnt.setLogicallocCd(payments.getLogicallocCd());
			glTxnDtlPymtEnt.setMainglCd(glMapping.getMainGL());
			glTxnDtlPymtEnt.setPaymentNo(payments.getPymtNo());
			glTxnDtlPymtEnt.setPersonalLedgerCd(glMapping.getPlCode());
			glTxnDtlPymtEnt.setPlLevel(glMapping.getPlLevel());
			glTxnDtlPymtEnt.setSubBifurcationCode(glMapping.getSubBifurcation());
			glTxnDtlPymtEnt.setSubBifurcationLevel(glMapping.getSubBifurcationLevel());
			glTxnDtlPymtEnt.setSectionCd(payments.getSectionCd());
			glTxnDtlPymtEnt.setSrNo(++i);
			glTxnDtlPymtEnt.setSubglCd(glMapping.getSubGL());
			Double amt1 = amt * glMapping.getAmtCalc();
			Double truncatedAmt = BigDecimal.valueOf(amt1).setScale(2, RoundingMode.HALF_UP).doubleValue();
			glTxnDtlPymtEnt.setTxnAmt(truncatedAmt);
			glTxnDtlPymtEnt.setTxnRmk(glMapping.getRemarks());

			count += glTxnDtlPymtEntDao.addGlTxnDtlPymtEntData(glTxnDtlPymtEnt);
		}
		if (count >= 3) {
			return true;
		}
		else {
			throw new FailToInsertDataException("Failed to create transaction entry");
		}
	}



	@Override
	public List<GlTxnDtlPymtEnt> getGlTxnDtlPymtEntByPaymentDtl(Integer paymentNo, String logicalLoc, String sectionCd) {
		log.info("inside GlTxnDtlPymtEntBeServiceImpl#getGlTxnDtlPymtEntByPaymentDtl()");
		return glTxnDtlPymtEntDao.getGlTxnDtlPymtEntByPaymentDtl(paymentNo, logicalLoc, sectionCd);
	}



	/*

	@Override
	public List<GlTxnDtlPymtEnt> getGlTxnDtlPymtEntList(){
	log.info("inside GlTxnDtlPymtEntBeServiceImpl  -  getGlTxnDtlPymtEntList()");
	List<GlTxnDtlPymtEnt> glTxnDtlPymtEntlist=new ArrayList<>();
	try {
	glTxnDtlPymtEntlist=glTxnDtlPymtEntDao.getGlTxnDtlPymtEntList();
	return glTxnDtlPymtEntlist;}
	 catch (Exception e) {
	log.error("ERROR: Service getGlTxnDtlPymtEntList() {}", e.getMessage());
	}
	return glTxnDtlPymtEntlist;
	}


	@Override
	public boolean updateGlTxnDtlPymtEntData(GlTxnDtlPymtEnt  glTxnDtlPymtEnt){
	log.info("inside GlTxnDtlPymtEntBeServiceImpl  -  updateGlTxnDtlPymtEntData()");
	Boolean result=false;
	try {
	result=glTxnDtlPymtEntDao.updateGlTxnDtlPymtEntData(glTxnDtlPymtEnt);
	return result;
	}
	 catch (Exception e) {
	log.error("ERROR: Service updateGlTxnDtlPymtEntData() {}", e.getMessage());

	}
	return result;
	}

	*/
}

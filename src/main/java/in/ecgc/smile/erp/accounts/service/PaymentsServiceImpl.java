package in.ecgc.smile.erp.accounts.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnDtlPymtEnt;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.LiabilityGLMapping;
import in.ecgc.smile.erp.accounts.model.Payments;
import in.ecgc.smile.erp.accounts.model.PaymentsTcodes;
import in.ecgc.smile.erp.accounts.repository.LiabilityDao;
import in.ecgc.smile.erp.accounts.repository.PaymentsDao;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PaymentsServiceImpl implements PaymentsService {

	@Autowired
	PaymentsDao paymentsDao;

	@Autowired
	LiabilityDao liabilityDao;

	@Autowired
	GlTxnService glTxnService;

	@Autowired
	PaymentAdviceService paymentAdviceService;

	@Autowired
	UserInfoService userInfoService;

	@Autowired
	GlTxnDtlPymtEntService glTxnDtlPymtEntService;

	@Override
	public Integer createGLTxn(Payments payments) {
		log.info("Inside PaymentsServiceImpl#createGLTxn");
		GlTxnHdr glTxnHdr = new GlTxnHdr();
		glTxnHdr.setEntityCd("ECGC");
		List<LiabilityGLMapping> mappingList = liabilityDao.getMAppingListforModule(payments.getModuleCd(),
				payments.getMappingCd());

		glTxnHdr.setLogicalLocCd(payments.getLogicallocCd());
		glTxnHdr.setTxnDt(LocalDate.now());
		glTxnHdr.setCreatedBy(userInfoService.getUser());
		
		Double amt = 0.0;

		if (payments.getPymtInForex().equals("Y")) {
			amt = payments.getAmtPaid() * payments.getExchrateAtPymt();
			amt = BigDecimal.valueOf(amt).setScale(2, RoundingMode.HALF_UP).doubleValue();
		} else {
			amt = payments.getAmtPaid();
		}

		String txnType = null;
		List<GlTxnDtl> glTxnDtls = new ArrayList<>();
		for (LiabilityGLMapping glMapping : mappingList) {
			GlTxnDtl dtl = new GlTxnDtl();
			dtl.setGlTxnType(glMapping.getTxnType());
			dtl.setDrCrFlag(glMapping.getDrCrFlag());
			dtl.setLogicalLocCd(payments.getLogicallocCd());
			dtl.setMainGlCd(glMapping.getMainGL());
			dtl.setSubGlCd(glMapping.getSubGL());
			dtl.setSubBiFurcationCd(glMapping.getSubBifurcation());
			dtl.setCreatedBy(userInfoService.getUser());
			Double amt1 = amt * glMapping.getAmtCalc();
			Double truncatedAmt = BigDecimal.valueOf(amt1).setScale(2, RoundingMode.HALF_UP).doubleValue();
			dtl.setTxnAmt(truncatedAmt);
			dtl.setT1(payments.getPaymentsTcodes().getT1());
			dtl.setT2(payments.getPaymentsTcodes().getT2());
			dtl.setT3(payments.getPaymentsTcodes().getT3());
			dtl.setT4(payments.getPaymentsTcodes().getT4());
			dtl.setT5(payments.getPaymentsTcodes().getT5());
			dtl.setT6(payments.getPaymentsTcodes().getT6());
			dtl.setT7(payments.getPaymentsTcodes().getT7());
			dtl.setT8(payments.getPaymentsTcodes().getT8());
			dtl.setT9(payments.getPaymentsTcodes().getT9());
			dtl.setT10(payments.getPaymentsTcodes().getT10());
			dtl.setT11(payments.getPaymentsTcodes().getT11());
			dtl.setT11(payments.getPaymentsTcodes().getT12());
			dtl.setAd1(payments.getPaymentsTcodes().getAd1());
			dtl.setAd2(payments.getPaymentsTcodes().getAd2());
			dtl.setAd3(payments.getPaymentsTcodes().getAd3());
			dtl.setAd4(payments.getPaymentsTcodes().getAd4());
			glTxnDtls.add(dtl);

			txnType = glMapping.getTxnType();
		}
		glTxnHdr.setGlTxnType(txnType);
		glTxnHdr.setFiscalYr(payments.getFiscalYr());
		glTxnHdr.setGlTxnDtls(glTxnDtls);
		log.info("Gl headr : {}", glTxnHdr);

		return glTxnService.addGlTxn(glTxnHdr);

	}

	@Transactional
	@Override
	public Integer createPaymentEntry(Payments payments) {
		log.info("Inside PaymentsServiceImpl#createPaymentEntry");

		payments.setGlTxnNo(null);
		Integer paymentNo = paymentsDao.createPaymentEntry(payments);
			payments.setPymtNo(paymentNo);

		glTxnDtlPymtEntService.addGlTxnDtlPymtEntData(payments);
		paymentAdviceService.updatePaymentAdviceStatus(payments.getLogicallocCd(), payments.getSectionCd(), payments.getAdviceNo(), "UNPAID");

		return paymentNo;
	}

	@Override
	public Payments getPaymentsByPaymentDtl(Integer pymtNo, String logicalLocCd, String sectionCd) {

		log.info("Inside PaymentsServiceImpl#getPaymentsByPaymentDtl");

		PaymentsTcodes paymentsTcodes = paymentsDao.getPaymentsTcodesByPaymentsDtl(pymtNo,logicalLocCd,sectionCd);

		List<GlTxnDtlPymtEnt> glTxnDtlPymtEntList = glTxnDtlPymtEntService.getGlTxnDtlPymtEntByPaymentDtl(pymtNo, logicalLocCd, sectionCd);

		Payments payments = new Payments(paymentsTcodes, glTxnDtlPymtEntList);

		payments = paymentsDao.getPaymentsByPaymentDtl(pymtNo, logicalLocCd, sectionCd);
		payments.setPaymentsTcodes(paymentsTcodes);
		payments.setGlTxnDtlPymtEntList(glTxnDtlPymtEntList);

		return payments;
	}

	@Override
	public List<Payments> getPaymentsList() {
		log.info("Inside PaymentsServiceImpl  -  getPaymentsList()");
		List<Payments> paymentslist = new ArrayList<>();
		try {
			paymentslist = paymentsDao.getPaymentsList();
			return paymentslist;
		} catch (Exception e) {
			log.error("ERROR: Service getPaymentsList() {}", e.getMessage());
		}
		return null;
	}

	@Override
	public boolean updatePaymentsData(Payments payments) {
		log.info("Inside PaymentsServiceImpl#updatePaymentsData");
		Boolean result = false;
		try {

			result = paymentsDao.updatePaymentsData(payments);
			return result;
		} catch (Exception e) {
			log.error("ERROR: Service updatePaymentsData() {}", e.getMessage());
		}
		return result;
	}

	@Override
	public boolean updatePaymentTcodes(Payments payments) {
		log.info("Inside PaymentsServiceImpl#updatePaymentTcodes");
		Boolean result = false;
		try {
			result = paymentsDao.updatePaymentTcodes(payments);
			return result;
		} catch (Exception e) {
			log.error("ERROR: Service updatePaymentTcodes() {}", e.getMessage());
		}
		return result;
	}

	@Override
	public List<Payments> getpaymentsbylocsecdt(String logicalLocCd, String sectionCd, String fromDt, String toDt) {
		log.info("Inside PaymentsServiceImpl#getpaymentsbylocsecdt");
		List<Payments> payments = null;
		try {
			payments = paymentsDao.getpaymentsbylocsecdt(logicalLocCd, sectionCd, fromDt, toDt);
		} catch (Exception e) {
			log.error("ERROR: Service getpaymentsbylocsecdt() {}", e.getMessage());
		}
		return payments;
	}

	@Override
	public List<Payments> getpaymentsbydt(String fromDt, String toDt) {
		log.info("Inside PaymentsServiceImpl#getpaymentsbydt");
		List<Payments> payments = null;
		try {
			payments = paymentsDao.getpaymentsbydt(fromDt, toDt);
		} catch (Exception e) {
			log.error("ERROR: Service getpaymentsbydt() {}", e.getMessage());
		}
		return payments;
	}

	@Override
	public List<Payments> getpaymentsbyLocSecYr(String logicalLocCd, String sectionCd, String fiscalYr) {
		log.info("Inside PaymentsServiceImpl#getpaymentsbyLocSecYr");
		List<Payments> payments = null;
		try {
			payments = paymentsDao.getpaymentsbyLocSecYr(logicalLocCd, sectionCd, fiscalYr);
		} catch (Exception e) {
			log.error("ERROR: Service getpaymentsbyLocSecYr() {}", e.getMessage());
		}
		return payments;
	}


	@Override
	public Payments getPaymentsByPymtNo(Integer paymentNo, String logicalLocCd, String sectionCd) {
		log.info("Inside PaymentsServiceImpl#getPaymentsByPymtNo");
		return paymentsDao.getPaymentsByPymtNo(paymentNo, logicalLocCd, sectionCd);
	}
	
	@Transactional
	@Override
	public Integer takeDecision(String logicalLocCd, String sectionCd, Integer paymentNo, String decision) {
		log.info("Inside PaymentsServiceImpl#takeDecision");

		Payments payments = getPaymentsByPaymentDtl(paymentNo, logicalLocCd, sectionCd);

		if (decision.equals("APRV")) {

		GlTxnHdr glTxnHdr = new GlTxnHdr();

		glTxnHdr.setLogicalLocCd(logicalLocCd);
		glTxnHdr.setFiscalYr(payments.getFiscalYr());
		glTxnHdr.setGlTxnType(payments.getGlTxnType());
		glTxnHdr.setTxnDt(LocalDate.now());
		glTxnHdr.setCreatedBy(userInfoService.getUser());

		List<GlTxnDtl> glTxnDtlsList = new ArrayList<GlTxnDtl>();

		for(GlTxnDtlPymtEnt dtlPymtEnt : payments.getGlTxnDtlPymtEntList()) {
			GlTxnDtl glTxnDtl = new GlTxnDtl();

			glTxnDtl.setLogicalLocCd(logicalLocCd);
			glTxnDtl.setDrCrFlag(dtlPymtEnt.getDrCrFlg());
			glTxnDtl.setTxnAmt(dtlPymtEnt.getTxnAmt());
			glTxnDtl.setMainGlCd(dtlPymtEnt.getMainglCd());
			glTxnDtl.setSubGlCd(dtlPymtEnt.getSubglCd());
			glTxnDtl.setGlTxnType(dtlPymtEnt.getGlTxnType());
			glTxnDtl.setPlCd(dtlPymtEnt.getPersonalLedgerCd());
			glTxnDtl.setSubBiFurcationCd(dtlPymtEnt.getSubBifurcationCode());
			glTxnDtl.setT1(payments.getPaymentsTcodes().getT1());
			glTxnDtl.setT2(payments.getPaymentsTcodes().getT2());
			glTxnDtl.setT3(payments.getPaymentsTcodes().getT3());
			glTxnDtl.setT4(payments.getPaymentsTcodes().getT4());
			glTxnDtl.setT5(payments.getPaymentsTcodes().getT5());
			glTxnDtl.setT6(payments.getPaymentsTcodes().getT6());
			glTxnDtl.setT7(payments.getPaymentsTcodes().getT7());
			glTxnDtl.setT8(payments.getPaymentsTcodes().getT8());
			glTxnDtl.setT9(payments.getPaymentsTcodes().getT9());
			glTxnDtl.setT10(payments.getPaymentsTcodes().getT10());
			glTxnDtl.setT11(payments.getPaymentsTcodes().getT11());
			glTxnDtl.setT12(payments.getPaymentsTcodes().getT12());
			glTxnDtl.setAd1(payments.getPaymentsTcodes().getAd1());
			glTxnDtl.setAd2(payments.getPaymentsTcodes().getAd2());
			glTxnDtl.setAd3(payments.getPaymentsTcodes().getAd3());
			glTxnDtl.setAd4(payments.getPaymentsTcodes().getAd4());
			glTxnDtl.setCreatedBy(userInfoService.getUser());
			glTxnDtl.setRemarks(dtlPymtEnt.getTxnRmk());

			glTxnDtlsList.add(glTxnDtl);
		}

		glTxnHdr.setGlTxnDtls(glTxnDtlsList);

		Integer glTxnNo = glTxnService.addGlTxn(glTxnHdr);

		paymentAdviceService.updatePaymentAdviceStatus(logicalLocCd, sectionCd, payments.getAdviceNo(), "PAID");

		return glTxnNo;

		}else {
			paymentAdviceService.updatePaymentAdviceStatus(logicalLocCd, sectionCd, payments.getAdviceNo(), "NAPRV");

			return -1;
		}

	}

}

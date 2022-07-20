package in.ecgc.smile.erp.accounts.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.exception.ImproperFtrDataProvidedException;
import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.model.FTR;
import in.ecgc.smile.erp.accounts.model.FtrDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.repository.EntityGLMasterDao;
import in.ecgc.smile.erp.accounts.repository.FTRDao;
import in.ecgc.smile.erp.accounts.util.FtrReportedToBankExcel;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FTRServiceImpl implements FTRService {

	@Autowired
	FTRDao ftrDao;

	@Autowired
	EntityGLMasterDao entityGLMasterDao;

	@Autowired
	GlTxnService txnService;

	@Autowired
	UserInfoService userInfoService;

	@Autowired
	FiscalYearService fiscalYearService;

	@Override
	public Integer addRequest(FTR ftr) throws ImproperFtrDataProvidedException {
		log.info("Inside FTRServiceImpl#addRequest");
		ftr.setFtrReqBy(101);
		try {
			int reqNo = ftrDao.getSeq();
			ftr.setFtrReqNo(reqNo);
			if (ftr.getFtrTrfAmt() >= 50000 && ftr.getFtrTrfAmt() % 10000 == 0) {
				if (ftrDao.addRequest(ftr) == 1) {
					int srno = 1;
					for (FtrDtl temp : ftr.getFtrDtl()) {
						temp.setFTRRequestNo(ftr.getFtrReqNo());
						temp.setFTRRequestSrNo(srno);
						temp.setLogicalLocCode(ftr.getLogicalLocCode());
						ftrDao.addFtrDetail(temp);
						srno += 1;
					}
					return reqNo;
				} else {
					log.info("not inserted");
					return 0;
				}
			} else {
				throw new ImproperFtrDataProvidedException("Data not provided properly");
			}
		} catch (ImproperFtrDataProvidedException exception) {
			throw new ImproperFtrDataProvidedException("Data not provided properly");

		} catch (Exception e) {
			log.info("Exception Occured {}", e);
			return null;
		}

	}

	@Override
	public List<FTR> getAllFTRRequest() {
		log.info("Inside FTRServiceImpl#getAllFTRRequest");
		try {
			return ftrDao.getAllFTRRequest();
		} catch (Exception e) {
			log.info("Exception Occured {}", e);
			return null;
		}
	}

	@Override
	public List<FTR> getAllPendingFTRRequest() {
		log.info("Inside FTRServiceImpl#getAllPendingFTRRequest");
		return ftrDao.getAllPendingFTRRequest();
	}

	@Override
	public List<FTR> getAllFTRRequest(String logicalLoc) {
		log.info("Inside FTRServiceImpl#getAllFTRRequest");
		return ftrDao.getAllFTRRequest(logicalLoc);
	}

	@Override
	public FTR getFTRRequestDTL(String FTRId) {
		log.info("Inside FTRServiceImpl#getFTRRequestDTL");
		return ftrDao.findFtrDtl(FTRId);
	}

	@Override
	public Integer decisionOnFTRRequest(FTR ftr) {
		log.info("Inside FTRServiceImpl#decisionOnFTRRequest");
		try {
			int result = ftrDao.decisionOnFTRRequest(ftr);
			if (result != 0) {
			}
			return result;

		} catch (Exception e) {
			log.info("Exception Occured {}", e);
			return 0;
		}
	}
	
	@Override
	public Integer updateFTRRequest(FTR ftr) {
		log.info("Inside FTRServiceImpl#updateFTRRequest");
		log.info("Ftr : {}", ftr.toString());
		try {
			
			Integer oldSrNo = ftrDao.getMaxSrNo(ftr.getFtrReqNo());
			Integer srno = 0;
			for (FtrDtl temp : ftr.getFtrDtl()) {
				srno += 1;
				temp.setFTRRequestNo(ftr.getFtrReqNo());
				temp.setBranchCode(ftr.getFtrReqBranchCd());
				temp.setLogicalLocCode(ftr.getLogicalLocCode());
				temp.setFTRRequestSrNo(srno);
				ftrDao.updateFtrDtl(temp);
				}
			while (oldSrNo > srno) {
				srno += 1;
				ftrDao.deteleFtrReqDtl(ftr.getFtrReqNo(), srno);
			}
			if (ftrDao.updateFtrhdr(ftr) == 1)
				return 1;
		} catch (Exception e) {

			log.info("Exception Occured {}", e);
		}

		return 0;
	}
	
	
	@Override
	public Integer deleteFTRRequest(Integer ftrNo) {
		log.info("Inside FTRServiceImpl#deleteFTRRequest");
		FTR ftr = ftrDao.findFtrDtl(ftrNo.toString());
		if (ftr.getFtrReqStatus().charAt(0) != 'P') {
			return -2;
		}
		return ftrDao.deteleFtrReq(ftrNo);
	}

	@Override
	public Integer decisionOnMultipleFTRRequest(List<FTR> ftrs) {
		log.info("Inside FTRServiceImpl#decisionOnMultipleFTRRequest");
		try {
			int result = 0;
			for (FTR ftr : ftrs) {

				if (ftr.getFtrReqStatus().equals("R")) {
					ftr.setPvStatus("P");
				}
				if (ftr.getFtrReqStatus().equals("D")) {
					ftr.setPvStatus("D");
				}
				result += ftrDao.decisionOnFTRRequest(ftr);

			}
			return result;

		}
		/*
		 * catch(IOException e) { System.err.println("--Sheet can not be generated--");
		 * return 0; }
		 */
		catch (Exception e) {
			log.info("Exception Occured {}", e);
			return 0;
		}
	}

	@Override
	public List<FTR> getAllApprovedFTRRequest() {
		log.info("Inside FTRServiceImpl#getAllApprovedFTRRequest");
		try {
			return ftrDao.getAllApprovedFTRRequest();
		} catch (Exception e) {
			log.info("Exception Occured {}", e);
			return null;
		}
	}

	@Override
	public Integer generateExcelForFTRRequest(String[] ftrCds, String bank) {
		log.info("Inside FTRServiceImpl#generateExcelForFTRRequest");
		try {
			List<FTR> ftrs = new ArrayList<FTR>();
			for (String ftrCd : ftrCds) {
				FTR ftr = ftrDao.findFtrDtl(ftrCd);
				ftr.setFtrReqStatus("E");
				ftrs.add(ftr);
			}
			if (this.decisionOnMultipleFTRRequest(ftrs) >= 1)
				return FtrReportedToBankExcel.generateSheet(ftrs, bank);
			else
				return 0;
		} catch (IOException e) {
			log.info("Exception Occured {}", e);

			return 0;
		} catch (Exception e) {
			log.info("Exception Occured {}", e);

			return 0;
		}

	}

	@Override
	public Integer changeStatusToTransfer(String FTRId, String paymentdt, String remark, List<Integer> gltxnadd) {
		log.info("Inside FTRServiceImpl#changeStatusToTransfer");
		return ftrDao.changeStatusToTransfer(FTRId, paymentdt, remark, gltxnadd);
	}

	@Override
	public List<Integer> getGlHdrDtl(String FTRId) {
		log.info("Inside FTRServiceImpl#getFtrHdrDtl");
		FTR ftr = ftrDao.findFtrById(FTRId);
		List<Integer> gltxnno = new ArrayList<Integer>();

		GlTxnHdr glTxnHdrho = new GlTxnHdr();
		glTxnHdrho.setEntityCd("ECGC");
		glTxnHdrho.setGlTxnNo(0);
		// glTxnHdrho.setLogicalLocCd(ftr.getReqTo());
		glTxnHdrho.setLogicalLocCd("HOLOG01");
		glTxnHdrho.setGlTxnType("IB");
		glTxnHdrho.setTxnDt(LocalDate.now());
		glTxnHdrho.setReference("");
		glTxnHdrho.setRevarsalGlTxnType(null);
		glTxnHdrho.setRevarsalGlTxnNo(0);
		glTxnHdrho.setReversalReason(null);
		glTxnHdrho.setFiscalYr(fiscalYearService.findCurrentFiscalYear().getCurrFiscalYear());
		glTxnHdrho.setMetaStatus(null);
//		glTxnHdrho.setMetaRemarks(ftr.getFtrReqNo().toString());
		glTxnHdrho.setCreatedBy(userInfoService.getUserId());
		glTxnHdrho.setCreatedDt(new Date());
		glTxnHdrho.setLastUpdatedBy(null);
		glTxnHdrho.setLastUpdatedDt(null);
		log.info("Entity GL Code {}", ftr);
		// EntityGL entityGL = entityGLMasterDao.getGlCodeByLocation(ftr.getReqTo());
		// EntityGL entityGL = entityGLMasterDao.getGlCodeByLocation("HOLOG01");

		GlTxnDtl txnDtlho1 = new GlTxnDtl();
		txnDtlho1.setGlTxnNo(0);
		txnDtlho1.setSrNo(1);
		txnDtlho1.setDrCrFlag("Cr");
		txnDtlho1.setTxnAmt(ftr.getFtrTrfAmt());
		txnDtlho1.setOldCode(null);
		txnDtlho1.setCodeType(null);
		txnDtlho1.setEntityGlCd("ECGC");

		// txnDtlho1.setMainGlCd(entityGL.getMainglCd());
		txnDtlho1.setMainGlCd("1700");
		// txnDtlho1.setSubGlCd(entityGL.getSubglCd());
		txnDtlho1.setSubGlCd("011");
		// txnDtlho1.setPlCd(entityGL.getPlLevel());
		txnDtlho1.setPlCd("");
		// txnDtlho1.setSubBiFurcationCd(entityGL.getPlLevel());
		txnDtlho1.setLogicalLocCd("HOLOG01");

		txnDtlho1.setGlTxnType("IB");
		txnDtlho1.setRemarks(FTRId);
		txnDtlho1.setT1("TEST");
		txnDtlho1.setT2("TEST");
		txnDtlho1.setT3("TEST");
		txnDtlho1.setT4("TEST");
		txnDtlho1.setT5("TEST");
		txnDtlho1.setT6("TEST");
		txnDtlho1.setT7("TEST");
		txnDtlho1.setT8("TEST");
		txnDtlho1.setT9("TEST");
		txnDtlho1.setT10("TEST");
		txnDtlho1.setT11("TEST");
		txnDtlho1.setT12("TEST");
		txnDtlho1.setAd1(null);
		txnDtlho1.setAd2(null);
		txnDtlho1.setAd3(null);
		txnDtlho1.setAd4(null);
		txnDtlho1.setMetaStatus(null);
//		txnDtlho1.setMetaRemarks(ftr.getFtrReqNo().toString());
		txnDtlho1.setCreatedBy(userInfoService.getUserId());
		txnDtlho1.setCreatedDt(new Date());
		txnDtlho1.setLastUpdatedBy(null);
		txnDtlho1.setLastUpdatedDt(null);
		log.info("Entity GL Code {}", ftr.getLogicalLocCode());
		EntityGL entityGLBr = entityGLMasterDao.getGlCodeByLocation(ftr.getLogicalLocCode());

		GlTxnDtl txnDtlho2 = new GlTxnDtl();
		txnDtlho2.setGlTxnNo(0);
		txnDtlho2.setSrNo(2);
		txnDtlho2.setDrCrFlag("Dr");
		txnDtlho2.setTxnAmt(ftr.getFtrTrfAmt());
		txnDtlho2.setOldCode(null);
		txnDtlho2.setCodeType(null);
		txnDtlho2.setEntityGlCd("ECGC");

		txnDtlho2.setMainGlCd(entityGLBr.getMainglCd());
		txnDtlho2.setSubGlCd(entityGLBr.getSubglCd());
		txnDtlho2.setPlCd(entityGLBr.getPlLevel());
		txnDtlho2.setSubBiFurcationCd(entityGLBr.getSubBifurcationLevel());
		txnDtlho2.setLogicalLocCd("HOLOG01");

		txnDtlho2.setGlTxnType("IB");
		txnDtlho2.setRemarks(FTRId);
		txnDtlho2.setT1("TEST");
		txnDtlho2.setT2("TEST");
		txnDtlho2.setT3("TEST");
		txnDtlho2.setT4("TEST");
		txnDtlho2.setT5("TEST");
		txnDtlho2.setT6("TEST");
		txnDtlho2.setT7("TEST");
		txnDtlho2.setT8("TEST");
		txnDtlho2.setT9("TEST");
		txnDtlho2.setT10("TEST");
		txnDtlho2.setT11("TEST");
		txnDtlho2.setT12("TEST");
		txnDtlho2.setAd1(null);
		txnDtlho2.setAd2(null);
		txnDtlho2.setAd3(null);
		txnDtlho2.setAd4(null);
		txnDtlho2.setMetaStatus(null);
//		txnDtlho2.setMetaRemarks(ftr.getFtrReqNo().toString());
		txnDtlho2.setCreatedBy(userInfoService.getUserId());
		txnDtlho2.setCreatedDt(new Date());
		txnDtlho2.setLastUpdatedBy(null);
		txnDtlho2.setLastUpdatedDt(null);

		List<GlTxnDtl> glTxnDtls = new ArrayList<>();
		glTxnDtls.add(txnDtlho1);
		glTxnDtls.add(txnDtlho2);

		glTxnHdrho.setGlTxnDtls(glTxnDtls);
		log.info("Gl Transaction Header HO Entry {}", glTxnHdrho);
		Integer gltxnadd1 = txnService.addGlTxn(glTxnHdrho);
		log.info("Gl gltxnadd1 {}", gltxnadd1);
		gltxnno.add(gltxnadd1);

		GlTxnHdr glTxnHdrbr = new GlTxnHdr();
		glTxnHdrbr.setEntityCd("ECGC");
		glTxnHdrbr.setGlTxnNo(0);
		glTxnHdrbr.setLogicalLocCd(ftr.getLogicalLocCode());
		glTxnHdrbr.setGlTxnType("IB");
		glTxnHdrbr.setTxnDt(LocalDate.now());
		glTxnHdrbr.setReference("");
		glTxnHdrbr.setRevarsalGlTxnType(null);
		glTxnHdrbr.setRevarsalGlTxnNo(0);
		glTxnHdrbr.setReversalReason(null);
		glTxnHdrbr.setFiscalYr(fiscalYearService.findCurrentFiscalYear().getCurrFiscalYear());
		glTxnHdrbr.setMetaStatus(null);
//		glTxnHdrbr.setMetaRemarks(ftr.getFtrReqNo().toString());
		glTxnHdrbr.setCreatedBy(userInfoService.getUserId());
		glTxnHdrbr.setCreatedDt(new Date());
		glTxnHdrbr.setLastUpdatedBy(null);
		glTxnHdrbr.setLastUpdatedDt(null);
//		log.info("Entity GL Code {}", ftr);
		// EntityGL entityGL = entityGLMasterDao.getGlCodeByLocation(ftr.getReqTo());
		// EntityGL entityGL = entityGLMasterDao.getGlCodeByLocation("HOLOG01");

		GlTxnDtl txnDtlBr1 = new GlTxnDtl();
		txnDtlBr1.setGlTxnNo(1);
		txnDtlBr1.setSrNo(1);
		txnDtlBr1.setDrCrFlag("Dr");
		txnDtlBr1.setTxnAmt(ftr.getFtrTrfAmt());
		txnDtlBr1.setOldCode(null);
		txnDtlBr1.setCodeType(null);
		txnDtlBr1.setEntityGlCd("ECGC");

		txnDtlBr1.setMainGlCd("1700");
		txnDtlBr1.setSubGlCd("005");
		txnDtlBr1.setPlCd("");
		// txnDtlBr1.setSubBiFurcationCd(entityGL.getPlLevel());
		txnDtlBr1.setLogicalLocCd(ftr.getLogicalLocCode());

		txnDtlBr1.setGlTxnType("IB");
		txnDtlBr1.setRemarks(FTRId);
		txnDtlBr1.setT1("TEST");
		txnDtlBr1.setT2("TEST");
		txnDtlBr1.setT3("TEST");
		txnDtlBr1.setT4("TEST");
		txnDtlBr1.setT5("TEST");
		txnDtlBr1.setT6("TEST");
		txnDtlBr1.setT7("TEST");
		txnDtlBr1.setT8("TEST");
		txnDtlBr1.setT9("TEST");
		txnDtlBr1.setT10("TEST");
		txnDtlBr1.setT11("TEST");
		txnDtlBr1.setT12("TEST");
		txnDtlBr1.setAd1(null);
		txnDtlBr1.setAd2(null);
		txnDtlBr1.setAd3(null);
		txnDtlBr1.setAd4(null);
		txnDtlBr1.setMetaStatus(null);
//		txnDtlBr1.setMetaRemarks(ftr.getFtrReqNo().toString());
		txnDtlBr1.setCreatedBy(userInfoService.getUserId());
		txnDtlBr1.setCreatedDt(new Date());
		txnDtlBr1.setLastUpdatedBy(null);
		txnDtlBr1.setLastUpdatedDt(null);
		log.info("Entity GL Code {}", ftr.getLogicalLocCode());
//		EntityGL entityGLBr = entityGLMasterDao.getGlCodeByLocation(ftr.getLogicalLocCode());

		GlTxnDtl txnDtlbr2 = new GlTxnDtl();
		txnDtlbr2.setGlTxnNo(0);
		txnDtlbr2.setSrNo(2);
		txnDtlbr2.setDrCrFlag("Cr");
		txnDtlbr2.setTxnAmt(ftr.getFtrTrfAmt());
		txnDtlbr2.setOldCode(null);
		txnDtlbr2.setCodeType(null);
		txnDtlbr2.setEntityGlCd("ECGC");

		txnDtlbr2.setMainGlCd("2200");
		txnDtlbr2.setSubGlCd("001");
		txnDtlbr2.setPlCd("");
		// txnDtlbr2.setSubBiFurcationCd(entityGLBr.getSubBifurcationLevel());
		txnDtlbr2.setLogicalLocCd(ftr.getLogicalLocCode());

		txnDtlbr2.setGlTxnType("IB");
		txnDtlbr2.setRemarks(FTRId);
		txnDtlbr2.setT1("TEST");
		txnDtlbr2.setT2("TEST");
		txnDtlbr2.setT3("TEST");
		txnDtlbr2.setT4("TEST");
		txnDtlbr2.setT5("TEST");
		txnDtlbr2.setT6("TEST");
		txnDtlbr2.setT7("TEST");
		txnDtlbr2.setT8("TEST");
		txnDtlbr2.setT9("TEST");
		txnDtlbr2.setT10("TEST");
		txnDtlbr2.setT11("TEST");
		txnDtlbr2.setT12("TEST");
		txnDtlbr2.setAd1(null);
		txnDtlbr2.setAd2(null);
		txnDtlbr2.setAd3(null);
		txnDtlbr2.setAd4(null);
		txnDtlbr2.setMetaStatus(null);
//		txnDtlbr2.setMetaRemarks(ftr.getFtrReqNo().toString());
		txnDtlbr2.setCreatedBy(userInfoService.getUserId());
		txnDtlbr2.setCreatedDt(new Date());
		txnDtlbr2.setLastUpdatedBy(null);
		txnDtlbr2.setLastUpdatedDt(null);

		List<GlTxnDtl> glTxnDtlbrs = new ArrayList<>();
		glTxnDtlbrs.add(txnDtlBr1);
		glTxnDtlbrs.add(txnDtlbr2);

		glTxnHdrbr.setGlTxnDtls(glTxnDtlbrs);
		log.info("Gl Transaction Header RO Entry {}", glTxnHdrbr);
		Integer gltxnadd2 = txnService.addGlTxn(glTxnHdrbr);
		log.info("Gl gltxnadd2 {}", gltxnadd2);
		gltxnno.add(gltxnadd2);
		return gltxnno;
	}

	@Override
	public List<FTR> generateExelFTR(String[] ftrs) {
		log.info("Inside FTRServiceImpl#generateExelFTR");
		try {
			List<FTR> ftrlist = new ArrayList<FTR>();
			for (String ftrCd : ftrs) {
				FTR ftr = ftrDao.generateExelFTR(ftrCd);
				ftrlist.add(ftr);
			}
			return ftrlist;
		} catch (Exception e) {
			log.info("Exception Occured {}", e);
			return null;
		}
	}
}

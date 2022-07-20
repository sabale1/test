package in.ecgc.smile.erp.accounts.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.dto.GLReport;
import in.ecgc.smile.erp.accounts.dto.GLReportIn;
import in.ecgc.smile.erp.accounts.dto.GLReportOut;
import in.ecgc.smile.erp.accounts.exception.CalendarRecordNotFoundException;
import in.ecgc.smile.erp.accounts.exception.GLTxnDBFailedException;
import in.ecgc.smile.erp.accounts.exception.InvalidGLTxnDataException;
import in.ecgc.smile.erp.accounts.exception.PlCdNotPresentException;
import in.ecgc.smile.erp.accounts.exception.SubBifNotPresentException;
import in.ecgc.smile.erp.accounts.exception.TCodeNotPresentException;
import in.ecgc.smile.erp.accounts.model.Calendar;
import in.ecgc.smile.erp.accounts.model.DateOperation;
import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.model.FiscalYearModel;
import in.ecgc.smile.erp.accounts.model.GlBrBalanceIn;
import in.ecgc.smile.erp.accounts.model.GlBrBalanceOut;
import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.repository.GlTxnDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GlTxnServiceImpl implements GlTxnService {

	@Autowired
	GlTxnDao txnDao;

	@Autowired
	EntityGLMasterService entityGlMstService;

	@Autowired
	CalendarService calendarService;

	@Autowired
	FiscalYearService fiscalYrService;

	@Override
	public List<GlTxnHdr> getAllGlTxnHdrs(String LogicalLoc) {
		log.info("Inside GlTxnServiceImpl#getAllGlTxnHdrs");
		return txnDao.getAllGlTxn(LogicalLoc);
	}

	@Override
	public List<GlTxnHdr> getAllGlTxnHdrs(String LogicalLoc, String glTxnType) {
		log.info("Inside GlTxnServiceImpl#getAllGlTxnHdrs");
		return txnDao.getAllGlTxn(LogicalLoc, glTxnType);
	}

	@Override
	public GlTxnHdr getGlTxn(Integer glTxnNo, String LogicalLoc, String glTxnType) {
		log.info("Inside GlTxnServiceImpl#getGlTxn");
		return txnDao.getGlTxn(glTxnNo, LogicalLoc, glTxnType);
	}

	@Override
	@Transactional
	public Integer addGlTxn(GlTxnHdr glTxnHdr) {
		log.info("Inside GlTxnServiceImpl#addGlTxn");
		log.info("GL Hdr : {}", glTxnHdr);
		DateOperation dt = new DateOperation(glTxnHdr.getTxnDt().getMonthValue());

		Integer srno = 0;

		FiscalYearModel fiscalYearModel = fiscalYrService.findCurrentFiscalYear();

		log.info("fiscalYearModel : {}", fiscalYearModel);
		log.info("glTxnHdr.getFiscalYr() : {}", glTxnHdr.getFiscalYr());
		log.info("dt.currentMonth : {}", dt.currentMonth);
		log.info("glTxnHdr.getLogicalLocCd() : {}", glTxnHdr.getLogicalLocCd());
		log.info("glTxnHdr.getGlTxnType() : {}", glTxnHdr.getGlTxnType());

		// Get GLTXN seq no.
		glTxnHdr.setGlTxnNo(
				txnDao.getGlTxnNo(glTxnHdr.getLogicalLocCd(), glTxnHdr.getGlTxnType(), glTxnHdr.getFiscalYr()));

		for (GlTxnDtl dtl : glTxnHdr.getGlTxnDtls()) {
			srno += 1;
			dtl.setSrNo(srno);
		}

		if (srno < 2) {
			throw new RuntimeException(
					"Incomplete GL transaction details filled, atleast 1 Debit and 1 Credit transaction is required.");
		}

		String fiscalYr = glTxnHdr.getFiscalYr();
		String currMon = dt.currentMonth;
		String logicalLoc = glTxnHdr.getLogicalLocCd();
		String glTxnType = glTxnHdr.getGlTxnType();
		log.info("curr Month is :{} logicalLoc is :{} Gl Txn Type is : {} ", currMon, logicalLoc, glTxnType);
		Calendar calendar = calendarService.getByGlTypeLogicalLoc(fiscalYr, currMon, logicalLoc, glTxnType);

		log.info("Calendar : {}", calendar);

		if (calendar != null && (calendar.getClosedStatus().equals('N') || calendar.getClosedStatus().equals('n'))) {

			for (GlTxnDtl dtl : glTxnHdr.getGlTxnDtls()) {
				srno += 1;
				dtl.setSrNo(srno);
			}
			Boolean isvalid;
			isvalid = this.isValid(glTxnHdr);

			if (isvalid) {
				if (txnDao.addGlTxn(glTxnHdr) >= 3) {

					Integer res = txnDao.updateGlTxnNo(glTxnHdr.getLogicalLocCd(), glTxnHdr.getGlTxnType(),
							glTxnHdr.getFiscalYr(), glTxnHdr.getGlTxnNo());

					txnDao.updateDrCrBrbal(glTxnHdr.getGlTxnDtls(), fiscalYr, glTxnHdr.getTxnDt());
					txnDao.updateDrCrBrbalYr(glTxnHdr.getGlTxnDtls(), fiscalYr, glTxnHdr.getTxnDt());

					if (res == 1) {
						log.info("GL Transaction successsfully done");
						return glTxnHdr.getGlTxnNo();
					} else {
						txnDao.deleteGlTxnDtl(glTxnHdr.getGlTxnNo());
						txnDao.deleteGlTxnHdr(glTxnHdr.getGlTxnNo());
						throw new GLTxnDBFailedException("Failed to add GL transaction details for LogicalLocation: "
								+ glTxnHdr.getLogicalLocCd() + ", TxnType: " + glTxnHdr.getGlTxnType()
								+ ", Fiscal Year:" + glTxnHdr.getFiscalYr());
					}
				}
			} else {
				throw new GLTxnDBFailedException("Validation failed for GL transaction header");
			}
		} else {
			throw new CalendarRecordNotFoundException(
					"Calendar is not opened for the Fiscal year : " + fiscalYr + ", Month : " + currMon
							+ ", Logical location : " + logicalLoc + " and Transaction type : " + glTxnType + "");
		}
		return 0;
	}

	public Boolean isValid(GlTxnHdr glTxnHdr) {
		log.info("Inside GlTxnServiceImpl#isValid");

		Double txnamt = 0.00;
		Integer srno = 0;
		if (glTxnHdr.getFiscalYr() == null || glTxnHdr.getGlTxnType() == null || glTxnHdr.getLogicalLocCd() == null
				|| glTxnHdr.getTxnDt() == null)
			throw new InvalidGLTxnDataException("Incomplete Gl Transaction data");
		for (GlTxnDtl dtl : glTxnHdr.getGlTxnDtls()) {
			srno += 1;
			dtl.setSrNo(srno);
			EntityGL gl = entityGlMstService.findGLByGLCode(dtl.getMainGlCd(), dtl.getSubGlCd());
			this.checkplcdsubbif(dtl, gl);
			log.info("Entitygl object is :{}", gl);
			if (gl != null) {
				if (gl.getT1() != null && gl.getT1().equals('Y') && (dtl.getT1() == null || dtl.getT1() == "")) {
					throw new TCodeNotPresentException("T1 is not present");
				}
				if (gl.getT2() != null && gl.getT2().equals('Y') && (dtl.getT2() == null || dtl.getT2() == "")) {
					throw new TCodeNotPresentException("T2 is not present");
				}
				if (gl.getT3() != null && gl.getT3().equals('Y') && (dtl.getT3() == null || dtl.getT3() == "")) {
					throw new TCodeNotPresentException("T3 is not present");
				}
				if (gl.getT4() != null && gl.getT4().equals('Y') && (dtl.getT4() == null || dtl.getT4() == "")) {
					throw new TCodeNotPresentException("T4 is not present");
				}
				if (gl.getT5() != null && gl.getT5().equals('Y') && (dtl.getT5() == null || dtl.getT5() == "")) {
					throw new TCodeNotPresentException("T5 is not present");
				}
				if (gl.getT6() != null && gl.getT6().equals('Y') && (dtl.getT6() == null || dtl.getT6() == "")) {
					throw new TCodeNotPresentException("T6 is not present");
				}
				if (gl.getT7() != null && gl.getT7().equals('Y') && (dtl.getT7() == null || dtl.getT7() == "")) {
					throw new TCodeNotPresentException("T7 is not present");
				}
				if (gl.getT8() != null && gl.getT8().equals('Y') && (dtl.getT8() == null || dtl.getT8() == "")) {
					throw new TCodeNotPresentException("T8 is not present");
				}
				if (gl.getT9() != null && gl.getT9().equals('Y') && (dtl.getT9() == null || dtl.getT9() == "")) {
					throw new TCodeNotPresentException("T9 is not present");
				}
				if (gl.getT10() != null && gl.getT10().equals('Y') && (dtl.getT10() == null || dtl.getT10() == "")) {
					throw new TCodeNotPresentException("T10 is not present");
				}
				if (gl.getT11() != null && gl.getT11().equals('Y') && (dtl.getT11() == null || dtl.getT11() == "")) {
					throw new TCodeNotPresentException("T11 is not present");
				}
				if (gl.getT12() != null && gl.getT12().equals('Y') && (dtl.getT12() == null || dtl.getT12() == "")) {
					throw new TCodeNotPresentException("T12 is not present");
				}
				if (gl.getT13() != null && gl.getT13().equals('Y') && (dtl.getT13() == null || dtl.getT13() == "")) {
					throw new TCodeNotPresentException("T13 is not present");
				}
				if (gl.getT14() != null && gl.getT14().equals('Y') && (dtl.getT14() == null || dtl.getT14() == "")) {
					throw new TCodeNotPresentException("T14 is not present");
				}
				if (gl.getT15() != null && gl.getT15().equals('Y') && (dtl.getT15() == null || dtl.getT15() == "")) {
					throw new TCodeNotPresentException("T15 is not present");
				}
				if (gl.getT16() != null && gl.getT16().equals('Y') && (dtl.getT16() == null || dtl.getT16() == "")) {
					throw new TCodeNotPresentException("T16 is not present");
				}
				if (gl.getT17() != null && gl.getT17().equals('Y') && (dtl.getT17() == null || dtl.getT17() == "")) {
					throw new TCodeNotPresentException("T17 is not present");
				}
				if (gl.getT18() != null && gl.getT18().equals('Y') && (dtl.getT18() == null || dtl.getT18() == "")) {
					throw new TCodeNotPresentException("T18 is not present");
				}
				if (gl.getT19() != null && gl.getT19().equals('Y') && (dtl.getT19() == null || dtl.getT19() == "")) {
					throw new TCodeNotPresentException("T19 is not present");
				}
				if (gl.getT20() != null && gl.getT20().equals('Y') && (dtl.getT20() == null || dtl.getT20() == "")) {
					throw new TCodeNotPresentException("T20 is not present");
				}
				if (gl.getT21() != null && gl.getT21().equals('Y') && (dtl.getT21() == null || dtl.getT21() == "")) {
					throw new TCodeNotPresentException("T21 is not present");
				}
				if (gl.getT22() != null && gl.getT22().equals('Y') && (dtl.getT22() == null || dtl.getT22() == "")) {
					throw new TCodeNotPresentException("T22 is not present");
				}
				if (gl.getT23() != null && gl.getT23().equals('Y') && (dtl.getT23() == null || dtl.getT23() == "")) {
					throw new TCodeNotPresentException("T23 is not present");
				}
				if (gl.getT24() != null && gl.getT24().equals('Y') && (dtl.getT24() == null || dtl.getT24() == "")) {
					throw new TCodeNotPresentException("T24 is not present");
				}
				if (gl.getT25() != null && gl.getT25().equals('Y') && (dtl.getT25() == null || dtl.getT25() == "")) {
					throw new TCodeNotPresentException("T25 is not present");
				}
				if (gl.getT26() != null && gl.getT26().equals('Y') && (dtl.getT26() == null || dtl.getT26() == "")) {
					throw new TCodeNotPresentException("T26 is not present");
				}
				if (gl.getT27() != null && gl.getT27().equals('Y') && (dtl.getT27() == null || dtl.getT27() == "")) {
					throw new TCodeNotPresentException("T27 is not present");
				}
				if (gl.getT28() != null && gl.getT28().equals('Y') && (dtl.getT28() == null || dtl.getT28() == "")) {
					throw new TCodeNotPresentException("T28 is not present");
				}
				if (gl.getT29() != null && gl.getT29().equals('Y') && (dtl.getT29() == null || dtl.getT29() == "")) {
					throw new TCodeNotPresentException("T29 is not present");
				}
				if (gl.getT30() != null && gl.getT30().equals('Y') && (dtl.getT30() == null || dtl.getT30() == "")) {
					throw new TCodeNotPresentException("T30 is not present");
				}
				if (gl.getT31() != null && gl.getT31().equals('Y') && (dtl.getT31() == null || dtl.getT31() == "")) {
					throw new TCodeNotPresentException("T31 is not present");
				}
				if (gl.getT32() != null && gl.getT32().equals('Y') && (dtl.getT32() == null || dtl.getT32() == "")) {
					throw new TCodeNotPresentException("T32 is not present");
				}
				if (gl.getT33() != null && gl.getT33().equals('Y') && (dtl.getT33() == null || dtl.getT33() == "")) {
					throw new TCodeNotPresentException("T33 is not present");
				}
				if (dtl.getDrCrFlag().equalsIgnoreCase("dr")) {
					txnamt -= dtl.getTxnAmt();
				} else if (dtl.getDrCrFlag().equalsIgnoreCase("cr")) {
					txnamt += dtl.getTxnAmt();
				} else {
					throw new InvalidGLTxnDataException("DR/CR Flag missing ");
				}
			} else {
				return false;
			}
		}
		if (txnamt >= 1)
			throw new InvalidGLTxnDataException("Debit Credit not matched ");
		return true;
	}

	private Boolean checkplcdsubbif(GlTxnDtl dtl, EntityGL gl) {
		String plget = gl.getPlLevel();
		log.info("PL GET  {}",plget);
		if(gl.getPlLevel().equals(""))
		{gl.setPlLevel(null);
		}
		if(gl.getSubBifurcationLevel().equals(""))
		{gl.setSubBifurcationLevel(null);
		}
		if ((gl.getPlLevel() != null) && ((dtl.getPlCd() == null) || (dtl.getPlCd().equals("")))) {
			log.error("Personal Ledger Code Not Found");
			throw new PlCdNotPresentException("Please enter "+gl.getPlLevel()+" number in Personal Ledger Code where MainGl = "+gl.getMainglCd()+" and SubGL = " +gl.getSubglCd());
		}
		if ((gl.getSubBifurcationLevel() != null)
				&& ((dtl.getSubBiFurcationCd() == null) || (dtl.getSubBiFurcationCd().equals("")))) {
			log.error("Sub Bifercation Level Code Not Found");
			throw new SubBifNotPresentException("Please enter "+gl.getSubBifurcationLevel()+" Sub Bifurcation Code where MainGl = "+gl.getMainglCd()+" and SubGL = " +gl.getSubglCd());
		}
		return true;
	}

	@Override
	public Integer updateGlTxnDtl(GlTxnDtl glTxnDtl) {
		log.info("Inside GlTxnServiceImpl#updateGlTxnDtl");
		return txnDao.updateGlTxnDtl(glTxnDtl);
	}

	@Override
	public List<GlTxnHdr> getAllGlTxnByFromDtToDt(LocalDate fromDt, LocalDate toDt) {
		log.info("Inside GlTxnServiceImpl#getAllGlTxnByFromDtToDt");
		return txnDao.getAllGlTxnByFromDtToDt(fromDt, toDt);
	}

	@Override
	public Integer reverseTransaction(GlTxnHdr glTxnHdr) {
		log.info("Inside GlTxnServiceImpl#reverseTransaction");
		Integer reversalTxnNo = 0;
		FiscalYearModel fisyr = fiscalYrService.findCurrentFiscalYear();
		Integer result = 0;
		try {
			GlTxnHdr reversalTxnHdr = new GlTxnHdr();
			reversalTxnHdr.setGlTxnType("RV");
			reversalTxnHdr.setLogicalLocCd(glTxnHdr.getLogicalLocCd());
			reversalTxnHdr.setReference(glTxnHdr.getReference());
			reversalTxnHdr.setTxnDt(LocalDate.now());
			reversalTxnHdr.setLastUpdatedBy("101");
			reversalTxnHdr.setFiscalYr(fisyr.getCurrFiscalYear());
			List<GlTxnDtl> list = new ArrayList<>();
			for (GlTxnDtl dtl : glTxnHdr.getGlTxnDtls()) {
				dtl.setGlTxnType("RV");
				if (dtl.getDrCrFlag().equalsIgnoreCase("Dr")) {
					dtl.setDrCrFlag("Cr");
				} else {
					dtl.setDrCrFlag("Dr");
				}
				list.add(dtl);
			}
			reversalTxnHdr.setGlTxnDtls(list);
			reversalTxnNo = this.addGlTxn(reversalTxnHdr);
			log.info("reversal txn no. {}", reversalTxnNo);
			if (reversalTxnNo > 0) {
				glTxnHdr.setRevarsalGlTxnNo(reversalTxnNo);
				glTxnHdr.setRevarsalGlTxnType("RV");

				result = txnDao.updateHdrOnRevarsal(glTxnHdr);
				log.info("Result after updating HDr table on service {}", result);
			}
			if (result > 0) {
				log.info("Transaction number geneated is :: {}", reversalTxnNo);
				return reversalTxnNo;
			} else {
				log.info("Exception in reversal Txn {} ", result);
				throw new InvalidGLTxnDataException("Transaction not updated");
			}
		}

		catch (Exception e) {
			throw new InvalidGLTxnDataException("Exception in reverse transaction : " + e.getMessage());
		}
	}

	@Override
	public BigDecimal getTotalAmt(String mainGlCd, String subGlCd) {
		log.info("Inside GlTxnServiceImpl#getTotalAmt");
		return txnDao.getTotalAmt(mainGlCd, subGlCd);
	}

	@Override
	public BigDecimal getTotalCreditAmt(String mainGlCd, String subGlCd) {
		log.info("Inside GlTxnServiceImpl#getTotalCreditAmt");
		return txnDao.getTotalCreditAmt(mainGlCd, subGlCd);
	}

	@Override
	public List<GlTxnHdr> getAllGltxnByFromDtLoc(LocalDate fromDt, LocalDate toDt, String logicallocation) {
		log.info("Inside GlTxnServiceImpl#getAllGltxnByFromDtLoc");
		return txnDao.getAllGltxnByFromDtLoc(fromDt, toDt, logicallocation);
	}

	@Override
	public List<GlTxnHdr> getAllGlTxnByTxnNoTxnTypeLoc(Integer glTxnNo, String glTxnType, String logicalLoc) {
		log.info("Inside GlTxnServiceImpl#getAllGlTxnByTxnNoTxnTypeLoc");
		return txnDao.getAllGlTxnByTxnNoTxnTypeLoc(glTxnNo, glTxnType, logicalLoc);
	}

	@Override
	public GlBrBalanceOut getBrBalance(GlBrBalanceIn balanceIn) {
		log.info("Inside GlTxnServiceImpl#getBrBalance");
		return txnDao.getBrBalance(balanceIn);
	}

	@Override
	@Transactional
	public GLReportOut getPolicyReport(GLReportIn glReportIn) {
		log.info("Inside GlTxnServiceImpl#getPolicyReport");
		List<GLReport> glReports = new ArrayList<GLReport>();
		GLReportOut glReportOut = new GLReportOut();

		glReports = txnDao.getTransactionList(glReportIn);

		glReportOut.setGlReport(glReports);

		return glReportOut;
	}

	@Override
	public List<GlTxnHdr> getAllGlTxnDtl() {
		return txnDao.getAllGlTxnDtl();
	}
}

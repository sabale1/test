package in.ecgc.smile.erp.accounts.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.integrate.model.OfficeMaster;
import in.ecgc.smile.erp.accounts.integrate.service.OrgStructService;
import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.model.TrialBalance;
import in.ecgc.smile.erp.accounts.model.TrialBalanceAllLocation;
import in.ecgc.smile.erp.accounts.model.TrialBalanceSingleLocation;
import in.ecgc.smile.erp.accounts.repository.EntityGLMasterDao;
import in.ecgc.smile.erp.accounts.repository.TrialBalanceDao;
import lombok.extern.slf4j.Slf4j;

/**
 * Fiscal year service implementation
 * 
 * @version 1.0 15-September-2020
 * @author Sanjali Kesarkar
 *
 */
@Slf4j
@Service
public class TrialBalanceServiceImpl implements TrialBalanceService {

	@Autowired
	TrialBalanceDao trialBalanceDao;

	@Autowired
	EntityGLMasterDao entityGLMasterDao;

	@Override
	public TrialBalanceSingleLocation getByLogicalLoc(@NotBlank String logicalloc, @NotBlank String balancedate) {
		log.info("Inside TrialBalanceServiceImpl#getByLogicalLoc");
		TrialBalanceSingleLocation location = new TrialBalanceSingleLocation();
		BigDecimal totalAsstCr = new BigDecimal(0.00);
		BigDecimal totalImcmCr = new BigDecimal(0.00);
		BigDecimal totalExpCr = new BigDecimal(0.00);
		BigDecimal totalLiaCr = new BigDecimal(0.00);
		BigDecimal totalAsstDr = new BigDecimal(0.00);
		BigDecimal totalImcmDr = new BigDecimal(0.00);
		BigDecimal totalExpDr = new BigDecimal(0.00);
		BigDecimal totalLiaDr = new BigDecimal(0.00);
		List<TrialBalance> reportList = new ArrayList<>();
		List<EntityGL> entityGLList = new ArrayList<>();
		entityGLList = entityGLMasterDao.listAllGLCodes();
		log.info("entityGLList : -  {} ", entityGLList);

		for (EntityGL entityGL : entityGLList) {
			TrialBalance tribal = trialBalanceDao.getByLogicalLoc(logicalloc, balancedate, entityGL.getMainglCd(),
					entityGL.getSubglCd(), entityGL.getGlName(), entityGL.getGlType());
			reportList.add(tribal);
			if (entityGL.getGlType().equalsIgnoreCase("ASST")) {
				totalAsstCr = totalAsstCr.add(tribal.getCr_amt());
				totalAsstDr = totalAsstDr.add(tribal.getDr_amt());
			}
			if (entityGL.getGlType().equalsIgnoreCase("LIAB")) {
				totalLiaCr = totalLiaCr.add(tribal.getCr_amt());
				totalLiaDr = totalLiaDr.add(tribal.getDr_amt());
			}
			if (entityGL.getGlType().equalsIgnoreCase("INCM")) {
				totalImcmCr = totalImcmCr.add(tribal.getCr_amt());
				totalImcmDr = totalImcmDr.add(tribal.getDr_amt());
			}
			if (entityGL.getGlType().equalsIgnoreCase("EXPD")) {
				totalExpCr = totalExpCr.add(tribal.getCr_amt());
				totalExpDr = totalExpDr.add(tribal.getDr_amt());
			}

		}
		location.setLogicalLoc(logicalloc);
		location.setTotalAsstCr(totalAsstCr);
		location.setTotalAsstDr(totalAsstDr);
		location.setTotalExpdCr(totalExpDr);
		location.setTotalExpdDr(totalExpDr);
		location.setTotalIncmCr(totalImcmCr);
		location.setTotalIncmDr(totalImcmDr);
		location.setTotalLiabCr(totalLiaCr);
		location.setTotalLiabDr(totalLiaDr);
		location.setTotalDr(totalAsstDr.add(totalLiaDr).add(totalExpDr).add(totalImcmDr));
		location.setTotalCr(totalAsstCr.add(totalLiaCr).add(totalExpCr).add(totalImcmCr));
		location.setTotalDiff(location.getTotalDr().subtract(location.getTotalCr()));
		location.setTrialBal(reportList);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate lcdt = LocalDate.parse(balancedate, formatter);
		location.setBalanceDt(lcdt);
		return location;
	}

	@Override
	public TrialBalanceAllLocation getForAllLocation(@NotBlank String balancedate) {
		log.info("Inside TrialBalanceServiceImpl#getForAllLocation");
		TrialBalanceAllLocation trialbalallloc = new TrialBalanceAllLocation();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate lcdt = LocalDate.parse(balancedate, formatter);
		trialbalallloc.setBalanceDt(lcdt);
		
		BigDecimal totalAsstCr = new BigDecimal(0.00);
		BigDecimal totalImcmCr = new BigDecimal(0.00);
		BigDecimal totalExpCr = new BigDecimal(0.00);
		BigDecimal totalLiaCr = new BigDecimal(0.00);
		BigDecimal totalAsstDr = new BigDecimal(0.00);
		BigDecimal totalImcmDr = new BigDecimal(0.00);
		BigDecimal totalExpDr = new BigDecimal(0.00);
		BigDecimal totalLiaDr = new BigDecimal(0.00);
		
		List<TrialBalance> reportList = new ArrayList<>();
		List<EntityGL> entityGLList = new ArrayList<>();
		entityGLList = entityGLMasterDao.listAllGLCodes();
		log.info("entityGLList : -  {} ", entityGLList);

		for (EntityGL entityGL : entityGLList) {
			TrialBalance tribal = trialBalanceDao.getForAllLocation(balancedate, entityGL.getMainglCd(),
					entityGL.getSubglCd(), entityGL.getGlName(), entityGL.getGlType());
			reportList.add(tribal);
			if (entityGL.getGlType().equalsIgnoreCase("ASST")) {
				totalAsstCr = totalAsstCr.add(tribal.getCr_amt());
				totalAsstDr = totalAsstDr.add(tribal.getDr_amt());
			}
			if (entityGL.getGlType().equalsIgnoreCase("LIAB")) {
				totalLiaCr = totalLiaCr.add(tribal.getCr_amt());
				totalLiaDr = totalLiaDr.add(tribal.getDr_amt());
			}
			if (entityGL.getGlType().equalsIgnoreCase("INCM")) {
				totalImcmCr = totalImcmCr.add(tribal.getCr_amt());
				totalImcmDr = totalImcmDr.add(tribal.getDr_amt());
			}
			if (entityGL.getGlType().equalsIgnoreCase("EXPD")) {
				totalExpCr = totalExpCr.add(tribal.getCr_amt());
				totalExpDr = totalExpDr.add(tribal.getDr_amt());
			}

		}
		trialbalallloc.setTotalAsstCr(totalAsstCr);
		trialbalallloc.setTotalAsstDr(totalAsstDr);
		trialbalallloc.setTotalExpdCr(totalExpDr);
		trialbalallloc.setTotalExpdDr(totalExpDr);
		trialbalallloc.setTotalIncmCr(totalImcmCr);
		trialbalallloc.setTotalIncmDr(totalImcmDr);
		trialbalallloc.setTotalLiabCr(totalLiaCr);
		trialbalallloc.setTotalLiabDr(totalLiaDr);
		trialbalallloc.setTotalDr(totalAsstDr.add(totalLiaDr).add(totalExpDr).add(totalImcmDr));
		trialbalallloc.setTotalCr(totalAsstCr.add(totalLiaCr).add(totalExpCr).add(totalImcmCr));
		trialbalallloc.setTotalDiff(trialbalallloc.getTotalDr().subtract(trialbalallloc.getTotalCr()));
		trialbalallloc.setTrialBal(reportList);
		
		
		return trialbalallloc;
		

		
	}

}

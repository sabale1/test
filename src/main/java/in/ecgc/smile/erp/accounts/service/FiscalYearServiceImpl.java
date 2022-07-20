package in.ecgc.smile.erp.accounts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.model.FiscalYearModel;
import in.ecgc.smile.erp.accounts.repository.FiscalYearDao;
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
public class FiscalYearServiceImpl implements FiscalYearService {
	@Autowired
	FiscalYearDao fiscalYearDao;
	/**
	 * Find current fiscal year details
	 * 
	 * service implementation to find details of current fiscal year
	 * 
	 */
	@Override
	public FiscalYearModel findCurrentFiscalYear() {
		log.info("Inside FiscalYearServiceImpl#findCurrentFiscalYear");
		return fiscalYearDao.findCurrentFiscalYear();
	}
	/**
	 * Get list of fiscal years
	 * 
	 * service implementation to get list of fiscal years
	 * 
	 */
	@Override
	public List<String> getFiscalYearList() {
		log.info("Inside FiscalYearServiceImpl#getFiscalYearList");
		List<String> fiscalYearList = fiscalYearDao.getFiscalYearList();
		log.info("Fiscal year list is : :{}",fiscalYearList);
		return fiscalYearList;
	}
	@Override
	public List<FiscalYearModel> getFiscalYrModelList() {
		log.info("Inside FiscalYearServiceImpl#getFiscalYrModelList");
		List<FiscalYearModel> fiscalYearList = new ArrayList<>();

		FiscalYearModel fiscalYearModel =  fiscalYearDao.findCurrentFiscalYear();
		
		
		if (fiscalYearModel!=null) {
			
			int shortYr = Integer.parseInt(fiscalYearModel.getCurrFiscalYear().substring(5, 9));
			log.info("Short year : {}", shortYr);
			
			for(int i=0; i<4; i++) {
				
				int prevShortYr = shortYr-1; //2023 
				
				FiscalYearModel prevFiscalYear = new FiscalYearModel();
				
				LocalDate currFiscalStDt = LocalDate.of(prevShortYr,4,1); 
								
				String currFiscalYr = --prevShortYr+"-"+ ++prevShortYr ; // 2022-2023
				
				LocalDate prevFiscalClDt = LocalDate.of(prevShortYr,3,31);
				
				String prevFiscalYr = shortYr-3+"-"+--prevShortYr ; // 202
				
				shortYr--;
				
				prevFiscalYear.setCurrFiscalYear(currFiscalYr);
				prevFiscalYear.setPrevFiscalYear(prevFiscalYr);
				prevFiscalYear.setPrevFiscalYearClosedDt(prevFiscalClDt);
				prevFiscalYear.setCurrFiscalYearStartDt(currFiscalStDt);
				
				prevFiscalYear.setMetaStatus('I');
				fiscalYearList.add(prevFiscalYear);
			}
			fiscalYearList.add(fiscalYearModel);
		}
		
		log.info("Fiscal year list is : :{}",fiscalYearList);
		return fiscalYearList;
	}
	@Override
	public FiscalYearModel getFiscalYrDataById(String currFiscYr) {
		log.info("Inside FiscalYearServiceImpl#getFiscalYrDataById");
		FiscalYearModel fiscalYear = fiscalYearDao.getFiscalYrDataById(currFiscYr);
		log.info("Fiscal year is : :{}",fiscalYear);
		return fiscalYear;
	}
	@Override
	public String fiscalYearSchedularAuto() {
		log.info("Inside FiscalYearServiceImpl#fiscalYearSchedularAuto");
		FiscalYearModel fiscalYearModel = fiscalYearDao.findCurrentFiscalYear();
		if (fiscalYearModel != null) {
			String prefiscalyr = fiscalYearModel.getCurrFiscalYear();
			String[] result = prefiscalyr.split("-");
			Integer first = Integer.parseInt(result[0]); 
			Integer sec = Integer.parseInt(result[1]); 
			first = first + 1;
			sec = sec + 1;
			fiscalYearDao.updateCurrentFiscalYear(first,sec,prefiscalyr );
			return first+"-"+sec;
			
		}
		return null;
	}
	@Override
	public Integer createCurrentFiscalYearEntry(FiscalYearModel fiscalYr) {
		return fiscalYearDao.createCurrentFiscalYearEntry(fiscalYr);
	}
	@Override
	public Integer closePreviousFiscal(String prevFiscalYr) {
		Integer res = null;
		try {
			res = fiscalYearDao.closePreviousFiscal(prevFiscalYr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
}

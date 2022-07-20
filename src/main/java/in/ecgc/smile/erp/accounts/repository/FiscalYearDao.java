package in.ecgc.smile.erp.accounts.repository;

import java.util.List;

import in.ecgc.smile.erp.accounts.model.FiscalYearModel;

public interface FiscalYearDao {
	
	FiscalYearModel findCurrentFiscalYear();
	List<String> getFiscalYearList();
	List<FiscalYearModel> getFiscalYrModelList();
	FiscalYearModel getFiscalYrDataById(String currFiscYr);
	Integer updateCurrentFiscalYear(Integer first, Integer sec, String prefiscalyr);
	Integer createCurrentFiscalYearEntry(FiscalYearModel fiscalYr);
	Integer closePreviousFiscal(String prevFiscalYr);
}

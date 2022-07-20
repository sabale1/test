package in.ecgc.smile.erp.accounts.repository; 

import java.util.List;

import in.ecgc.smile.erp.accounts.model.SectionOfTds;

public interface SectionOfTdsDao{
	public List<SectionOfTds> getTdsList()throws Exception;
	public SectionOfTds getTdsDataById(int srNo);
	public boolean addTdsData(SectionOfTds  tds);
	public boolean updateTdsData(SectionOfTds  tds);
}

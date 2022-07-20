package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import in.ecgc.smile.erp.accounts.model.SectionOfTds;

public interface SectionOfTdsService{
	public List<SectionOfTds> getTdsList();
	public boolean addTdsData(SectionOfTds  tds);
	public boolean updateTdsData(SectionOfTds  tds);
	public SectionOfTds getTdsDataById(int srNo);
}

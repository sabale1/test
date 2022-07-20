package in.ecgc.smile.erp.accounts.repository;

import java.util.List;

import in.ecgc.smile.erp.accounts.model.ScheduleCdGlMapping;

public interface ScheduleCdGlMappingDao {
	public List<ScheduleCdGlMapping> getScheduleCdGlMappingList() throws Exception;

	public ScheduleCdGlMapping getScheduleCdGlMappingDataById(int seqNo);

	public boolean addScheduleCdGlMappingData(ScheduleCdGlMapping scheduleCdGlMapping);

	public boolean updateScheduleCdGlMappingData(ScheduleCdGlMapping scheduleCdGlMapping);
	
}

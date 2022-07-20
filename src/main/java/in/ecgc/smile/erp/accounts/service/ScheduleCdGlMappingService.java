package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import in.ecgc.smile.erp.accounts.model.ScheduleCdGlMapping;

public interface ScheduleCdGlMappingService {
	public List<ScheduleCdGlMapping> getScheduleCdGlMappingList();

	public boolean addScheduleCdGlMappingData(ScheduleCdGlMapping scheduleCdGlMapping);

	public boolean updateScheduleCdGlMappingData(ScheduleCdGlMapping scheduleCdGlMapping);

	public ScheduleCdGlMapping getScheduleCdGlMappingDataById(int seqNo);
}

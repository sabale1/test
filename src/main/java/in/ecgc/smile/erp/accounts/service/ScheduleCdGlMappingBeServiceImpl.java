package in.ecgc.smile.erp.accounts.service;

import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.exception.RecordNotFoundException;
import in.ecgc.smile.erp.accounts.model.ScheduleCdGlMapping;
import in.ecgc.smile.erp.accounts.repository.ScheduleCdGlMappingDao;

@Service
@Transactional
public class ScheduleCdGlMappingBeServiceImpl implements ScheduleCdGlMappingService {
	private static final Logger logger = LoggerFactory.getLogger(ScheduleCdGlMappingBeServiceImpl.class);
	@Autowired
	ScheduleCdGlMappingDao scheduleCdGlMappingDao;

	@Override
	public List<ScheduleCdGlMapping> getScheduleCdGlMappingList() {
		logger.info("inside ScheduleCdGlMappingBeServiceImpl  -  getScheduleCdGlMappingList()");
		List<ScheduleCdGlMapping> scheduleCdGlMappinglist = new ArrayList<>();
		try {
			scheduleCdGlMappinglist = scheduleCdGlMappingDao.getScheduleCdGlMappingList();
			return scheduleCdGlMappinglist;
		} catch (Exception e) {
			logger.error("ERROR: Service getScheduleCdGlMappingList() {}", e.getMessage());
		}
		return scheduleCdGlMappinglist;
	}

	@Override
	public ScheduleCdGlMapping getScheduleCdGlMappingDataById(int seqNo) {
		logger.info("inside ScheduleCdGlMappingBeServiceImpl  -  getScheduleCdGlMappingDataById(seqNo)");
		ScheduleCdGlMapping scheduleCdGlMapping = scheduleCdGlMappingDao.getScheduleCdGlMappingDataById(seqNo);
		if (scheduleCdGlMapping != null)
			return scheduleCdGlMapping;
		throw new RecordNotFoundException("required details are not found", new String[] { "seqNo" });
	}

	@Override
	public boolean addScheduleCdGlMappingData(ScheduleCdGlMapping scheduleCdGlMapping) {
		logger.info("inside ScheduleCdGlMappingBeServiceImpl  -  addScheduleCdGlMappingData()");
		Boolean result = false;
		try {
			result = scheduleCdGlMappingDao.addScheduleCdGlMappingData(scheduleCdGlMapping);
			return result;
		} catch (Exception e) {
			logger.error("ERROR: Service addScheduleCdGlMappingData() {}", e.getMessage());

		}
		return result;
	}

	@Override
	public boolean updateScheduleCdGlMappingData(ScheduleCdGlMapping scheduleCdGlMapping) {
		logger.info("inside ScheduleCdGlMappingBeServiceImpl  -  updateScheduleCdGlMappingData()");
		Boolean result = false;
		try {
			result = scheduleCdGlMappingDao.updateScheduleCdGlMappingData(scheduleCdGlMapping);
			return result;
		} catch (Exception e) {
			logger.error("ERROR: Service updateScheduleCdGlMappingData() {}", e.getMessage());

		}
		return result;
	}
}

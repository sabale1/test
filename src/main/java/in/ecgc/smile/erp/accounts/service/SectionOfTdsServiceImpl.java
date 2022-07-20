package in.ecgc.smile.erp.accounts.service;

//Import Model Class & Dao Interface 
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.ecgc.smile.erp.accounts.model.SectionOfTds;
import in.ecgc.smile.erp.accounts.repository.SectionOfTdsDao;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;


@Service
@Transactional
public class SectionOfTdsServiceImpl implements SectionOfTdsService {
	private static final Logger logger = LoggerFactory.getLogger(SectionOfTdsServiceImpl.class);
	@Autowired
	SectionOfTdsDao tdsDao;
	
	@Autowired
	UserInfoService userInfoService;

	@Override
	public List<SectionOfTds> getTdsList() {
		logger.info("inside TdsBeServiceImpl  -  getTdsList()");
		List<SectionOfTds> tdslist = new ArrayList<>();
		try {
			tdslist = tdsDao.getTdsList();
			return tdslist;
		} catch (Exception e) {
			logger.error("ERROR: Service getTdsList() {}", e.getMessage());
		}
		return tdslist;
	}

	@Override
	public SectionOfTds getTdsDataById(int srNo) {
		logger.info("inside TdsBeServiceImpl  -  getTdsDataById(srNo)");
		SectionOfTds tds = tdsDao.getTdsDataById(srNo);
		if (tds != null)
			return tds;
		// throw new Exception("required details are not found", new String[] { "srNo"
		// });
		return tds;
	}

	@Override
	public boolean addTdsData(SectionOfTds tds) {
		logger.info("inside TdsBeServiceImpl  -  addTdsData()");
		Boolean result = false;
		try {
			tds.setCreatedBy(userInfoService.getUser());
			tds.setCreatedDt(new Date());
			result = tdsDao.addTdsData(tds);
			return result;
		} catch (Exception e) {
			logger.error("ERROR: Service addTdsData() {}", e.getMessage());

		}
		return result;
	}

	@Override
	public boolean updateTdsData(SectionOfTds tds) {
		logger.info("inside TdsBeServiceImpl  -  updateTdsData()");
		Boolean result = false;
		try {
			tds.setLastUpdatedBy(userInfoService.getUser());
			tds.setLastUpdatedDt(new Date());
			result = tdsDao.updateTdsData(tds);
			return result;
		} catch (Exception e) {
			logger.error("ERROR: Service updateTdsData() {}", e.getMessage());

		}
		return result;
	}
}

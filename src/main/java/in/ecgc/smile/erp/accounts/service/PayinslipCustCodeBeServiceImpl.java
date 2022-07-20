package in.ecgc.smile.erp.accounts.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import in.ecgc.smile.erp.accounts.model.PayinslipCustCode;
import in.ecgc.smile.erp.accounts.repository.PayinslipCustCodeDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PayinslipCustCodeBeServiceImpl implements PayinslipCustCodeService {

	@Autowired
	PayinslipCustCodeDao payinslipCustCodeDao;

	@Override
	public List<PayinslipCustCode> getPayinslipCustCodeList() {
		log.info("inside PayinslipCustCodeBeServiceImpl  -  getPayinslipCustCodeList()");
		List<PayinslipCustCode> payinslipCustCodelist = new ArrayList<>();
		try {
			payinslipCustCodelist = payinslipCustCodeDao.getPayinslipCustCodeList();
			return payinslipCustCodelist;
		} catch (Exception e) {
			log.error("ERROR: Service getPayinslipCustCodeList() {}", e.getMessage());
		}
		return payinslipCustCodelist;
	}

	@Override
	public PayinslipCustCode getPayinslipCustCodeDataById(String customerCd) {
		log.info("inside PayinslipCustCodeBeServiceImpl  -  getPayinslipCustCodeDataById(customerCd)");
		PayinslipCustCode payinslipCustCode = payinslipCustCodeDao.getPayinslipCustCodeDataById(customerCd);
		if (payinslipCustCode != null)
			return payinslipCustCode;
//	throw new NotFoundException("required details are not found", new String[] { "customerCd" });
		return payinslipCustCode;
	}

	@Override
	public boolean addPayinslipCustCodeData(PayinslipCustCode payinslipCustCode) {
		log.info("inside PayinslipCustCodeBeServiceImpl  -  addPayinslipCustCodeData()");
		Boolean result = false;
		try {
			result = payinslipCustCodeDao.addPayinslipCustCodeData(payinslipCustCode);
			return result;
		} catch (Exception e) {
			log.error("ERROR: Service addPayinslipCustCodeData() {}", e.getMessage());
		}
		return result;
	}

	@Override
	public boolean updatePayinslipCustCodeData(PayinslipCustCode payinslipCustCode) {
		log.info("inside PayinslipCustCodeBeServiceImpl  -  updatePayinslipCustCodeData()");
		Boolean result = false;
		try {
			result = payinslipCustCodeDao.updatePayinslipCustCodeData(payinslipCustCode);
			return result;
		} catch (ResponseStatusException e) {
			log.error("ERROR: Service updatePayinslipCustCodeData() {}", e.getMessage());
		}
		return result;
	}

	@Override
	public List<PayinslipCustCode> getPayinSlipCustCodeByLogicalLoc(@NotBlank String logicalLoc) {
		log.info("Inside PayinSlipServiceImpl#getPayinSlipCustCodeByLogicalLoc");
		return payinslipCustCodeDao.getPayinSlipCustCodeByLogicalLoc(logicalLoc);

	}

}

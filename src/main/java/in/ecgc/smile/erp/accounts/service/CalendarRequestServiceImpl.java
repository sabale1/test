package in.ecgc.smile.erp.accounts.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ecgc.smile.erp.accounts.exception.CalendarExceptionHandling;
import in.ecgc.smile.erp.accounts.model.Calendar;
import in.ecgc.smile.erp.accounts.model.CalendarRequestModel;
import in.ecgc.smile.erp.accounts.model.DateOperation;
import in.ecgc.smile.erp.accounts.repository.CalendarDao;
import in.ecgc.smile.erp.accounts.repository.CalendarRequestDao;
import in.ecgc.smile.erp.accounts.repository.CalendarRequestDaoImpl;
import in.ecgc.smile.erp.sys.auth.be.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CalendarRequestServiceImpl implements CalendarRequestService {

	@Autowired
	CalendarRequestDao requestDao;

	@Autowired
	CalendarDao calendarDao;

	@Autowired
	CalendarRequestDaoImpl calendarRequestDaoImpl;

	@Autowired
	UserInfoService userInfoService;

	@Override
	public Integer generateRequest(CalendarRequestModel requestModel) {
		log.info("Inside CalendarRequestServiceImpl#generateRequest");
		Calendar calendar = calendarDao.getCalendar(requestModel.getCalendarId());
		if (calendar != null) {
			requestModel.setGlTxnType(calendar.getGlTxnType());
			requestModel.setFiscalYr(calendar.getFiscalYear());
			requestModel.setLogicalLocCode(calendar.getLogicalLocCode());
			requestModel.setMonth(calendar.getMonth());
			return requestDao.generateRequest(requestModel);
		} else {
			return null;
		}
	}

	@Override
	public Integer generateCalRequest(CalendarRequestModel requestModel) {
		log.info("Inside CalendarRequestServiceImpl#generateCalRequest");
		Integer reqId = calendarRequestDaoImpl.getSeq();
		requestModel.setRequestId(reqId);
		Calendar calendar = calendarDao.getCalendar(requestModel.getCalendarId());
		if (calendar != null) {
			requestModel.setGlTxnType(calendar.getGlTxnType());
			requestModel.setFiscalYr(calendar.getFiscalYear());
			requestModel.setLogicalLocCode(calendar.getLogicalLocCode());
			requestModel.setMonth(calendar.getMonth());
			requestModel.setRequestedBy(Integer.parseInt(userInfoService.getUser()));
			Date nextdt = new Date();
			nextdt.setDate(nextdt.getDate()+1);
			requestModel.setValidityDt(nextdt);
			requestDao.generateRequest(requestModel);
			return requestModel.getRequestId();
		} else {
			return null;
		}
	}

	@Override
	public CalendarRequestModel updateCalendarRequest(Integer reqId, CalendarRequestModel updateModel) {
		log.info("Inside CalendarRequestServiceImpl#updateCalendarRequest");
		return requestDao.updateCalendarRequest(reqId, updateModel);
	}

	@Override
	public List<CalendarRequestModel> viewAllRequest() {
		log.info("Inside CalendarRequestServiceImpl#viewAllRequest");
		return requestDao.viewAllRequest();
	}

	@Override
	public CalendarRequestModel viewRequestById(Integer reqId) throws CalendarExceptionHandling {
		log.info("Inside CalendarRequestServiceImpl#viewRequestById");
		CalendarRequestModel calendarModel = requestDao.viewRequestById(reqId);
		if (calendarModel == null) {
			throw new CalendarExceptionHandling("No request found with the given Request ID");
		}
		return calendarModel;
	}

	public Integer decisionOnRequest(CalendarRequestModel requestModel) {
		log.info("Inside CalendarRequestServiceImpl#decisionOnRequest");
		if (requestModel.getRequestStatus().equalsIgnoreCase("A")) {

			requestDao.decisionOnRequest(requestModel);
			calendarDao.updateStatus1(requestModel.getCalendarId(), "N");
			Calendar calendar = calendarDao.getCalendar(requestModel.getCalendarId());
			DateOperation dateOperation = new DateOperation(calendar.getMonth().substring(0, 3));
			calendar.setMonth(dateOperation.nextMonth);
			if (requestModel.getMonth().equalsIgnoreCase("mar")) {
				String fisyr = calendar.getFiscalYear();
				String[] parts = fisyr.split("-");
				Integer part1 = Integer.parseInt(parts[0]);
				Integer part2 = Integer.parseInt(parts[1]);
				part1 = part1 + 1;
				part2 = part2 + 1;

				calendarDao.openPrev(calendar.getLogicalLocCode(), calendar.getGlTxnType(), "Y", calendar.getMonth(),
						part1 + "-" + part2);
			} else {
				calendarDao.openPrev(calendar.getLogicalLocCode(), calendar.getGlTxnType(), "Y", calendar.getMonth(),
						calendar.getFiscalYear());
			}
			return 1;
		} else {
			return requestDao.decisionOnRequest(requestModel);
		}
	}

	@Override
	public Integer decisionMultipleRequest(List<CalendarRequestModel> requestModel) {
		log.info("Inside CalendarRequestServiceImpl#decisionMultipleRequest");
		try {
			int result = 0;
			for (CalendarRequestModel obj : requestModel) {
				result += this.decisionOnRequest(obj);

			}
			return result;

		} catch (Exception e) {
			log.info("Exception Occured {}", e);
			return 0;
		}
	}

}

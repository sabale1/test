package in.ecgc.smile.erp.accounts.model;

import java.util.Date;

import lombok.Data;

@Data
public class CalendarMstAnnual
{
	private String calendarAnnualId;
	private String logicalLocCd;
	private String fiscalYr;
	private String month;
	private String userId;
	private Date closedDt;
	private Date lastTransDt;
	
}

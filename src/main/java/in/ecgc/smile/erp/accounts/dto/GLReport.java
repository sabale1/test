package in.ecgc.smile.erp.accounts.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class GLReport {

	private LocalDate txnDate;
	private String remark;
	private String txnType;
	private String txnNo;
	private BigDecimal txnAmt;
	private String drCrFlag;
}

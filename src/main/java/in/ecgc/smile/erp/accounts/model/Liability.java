/**
 *
 */
package in.ecgc.smile.erp.accounts.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author CMJ-SENG-LAPTOP9
 *
 */
@Data
public class Liability {

	@NotBlank(message = "{moduleCd.required}")
	private String moduleCd;

	@NotBlank(message = "{mappingCd.required}")
	private String mappingCd;

	@NotNull(message = "{baseAmt.required}")
	private Double baseAmt;

	@NotBlank(message = "{logicalLogCd.required}")
	private String logicalLogCd;

	private LocalDate txnDt;

	@NotBlank(message = "{fiscalyear.required}")
	private String fiscalYr;

	private Boolean isAmtInInr;

	private String currCode;
	private String currName;
	private Float exchangeRate;
	private String t1;
	private String t2;
	private String t3;
	private String t4;
	private String t5;
	private String t6;
	private String t7;
	private String t8;
	private String t9;
	private String t10;
	private String t11;
	private String t12;
	private String t13;
	private String t14;
	private String t15;
	private String t16;
	private String t17;
	private String t18;
	private String t19;
	private String t20;
	private String t21;
	private String t22;
	private String t23;
	private String t24;
	private String t25;
	private String t26;
	private String t27;
	private String t28;
	private String t29;
	private String t30;
	private String t31;
	private String t32;
	private String t33;
	private LocalDate ad1;
	private LocalDate ad2;
	private LocalDate ad3;
	private LocalDate ad4;
	private String remarks;
	private Integer userId;
}

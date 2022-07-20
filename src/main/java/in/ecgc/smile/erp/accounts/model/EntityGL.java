package in.ecgc.smile.erp.accounts.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EntityGL {
	
	
	private String entityGlCd;
	
	@NotBlank(message = "MainGL Code is Required")
	@Pattern(regexp = "^[0-9]{4}$", message ="Main GL Code should be between 0000 to 9999")
	private String mainglCd;
	
	@Pattern(regexp = "^[0-9]{3}$", message ="Sub GL Code should be between 000 to 999")
	@NotBlank(message = "Sub GL Code is Required")
	private String subglCd;
	
    @NotNull(message = "GL Name is required")
    @Size(min = 1, max=100, message = "GL Name should be between 1 to 100 characters")
	private String glName;
	
	private Character glIsGroup;
	
	@NotNull(message = "GL Type is required")
	@Size(min = 2, max=4, message = "GL Type should be 4 characters")
	private String glType;
	
//	@NotBlank(message = "GL Sub Type is Required")
	private String glSubtype;
	private String balInd;
	
	private Character zeroBalFlg;
	private Character active;
	private Integer cashFlow;
	
	private String logicalLocCd;
	
	private String plLevel;
	private Character t1;
	private Character t2;
	private Character t3;
	private Character t4;
	private Character t5;
	private Character t6;
	private Character t7;
	private Character t8;
	private Character t9;
	private Character t10;
	private Character t11;
	private Character t12;
	private Character t13;
	private Character t14;
	private Character t15;
	private Character t16;
	private Character t17;
	private Character t18;
	private Character t19;
	private Character t20;
	private Character t21;
	private Character t22;
	private Character t23;
	private Character t24;
	private Character t25;
	private Character t26;
	private Character t27;
	private Character t28;
	private Character t29;
	private Character t30;
	private Character t31;
	private Character t32;
	private Character t33;
	
	
	
//	@NotBlank(message = "IRDABPA Code is Required")
	private String irdaBpaCd;
	
//	@NotBlank(message = "IRDA Code is Required")
	private String irdaCd;
	
//	@NotBlank(message = "Schedule code is Required")
	private String schedule6;
	
//	@NotBlank(message = "Financial Form Name is Required")
	private String financialFormName;
	
//	@NotBlank(message = "Sub Bifurcation Level is Required")
	private String subBifurcationLevel;
	private Integer cashaccount;
	private String createdBy;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate createdDt;
	private String lastUpdatedBy;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate lastUpdatedDt;
	private String metaRemarks;
	private String oldCd;
	private String scheduleItemCd;
	private String scheduleCd;
		
}

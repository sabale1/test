package in.ecgc.smile.erp.accounts.model;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChqDishonorEntry
{
	private String logicalLocCode;
	private Integer receiptNumber ;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate receiptDate;
	//@NotBlank(message = "This is Mandatory")
	private String iwdNumber;
	private String remarks;	
	//@NotBlank(message = "This is Mandatory")
	private String receivedFromCode;
//	@NotBlank(message = "This is Mandatory")
	private String receivedFromName;
	//@NotBlank(message = "This is Mandatory")
	private String receivedFromAddress;
	//@NotNull(message = "This is Mandatory")
	private Double	stampAmount;
	//@NotBlank(message = "This is Mandatory")
	private String instrumentType; 
	//@NotBlank(message = "This is Mandatory")
	//@Pattern(regexp = "[0-9]", message = "{please enter a valid Instrument Number}")
	private String instrumentNumber;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate instrumentDate;
	private String drawnOn; 
	private String glTxnType;
	private Integer glTxnNumber;
	private Integer revarsalGlTxnNo;
	//@NotBlank(message = "This is Mandatory")
	private String receivedfromType;
	//@NotNull(message = "This is Mandatory")
	private Double receiptAmount; 
	private String glFlag;
	private String payInSlip; // new column
	private Integer oldReceiptNumber;
	private Integer glTxnNumberOld;	
	private String metaStatus; // changed from ecgcStatus to metaStatus
	private String metaRemarks;	
	//private Integer receiptId;

	
	

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dishonorDt;
	
	
	private String dishonorReason;
	
	
	//@NotBlank(message="{fiscalYr.required}")
	private String fiscalYr;
	private String createdBy;
	private LocalDate createdDt;
	private String lastUpdatedBy;
	private LocalDate lastUpdatedDt;

	
	
}

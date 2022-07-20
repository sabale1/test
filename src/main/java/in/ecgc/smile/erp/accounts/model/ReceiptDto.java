package in.ecgc.smile.erp.accounts.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ReceiptDto {


	@NotBlank(message = "Logical Location is Required")
	private String logicalLocCode;
	private Integer receiptNumber ;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate receiptDate;
//	@NotBlank(message = "Inward Number is Required")
	private String iwdNumber;
	private String remarks;	
	@NotBlank(message = "Received From Code is Required")
	private String receivedFromCode;
	private String receivedFromName;
	private String receivedFromAddress;
	@NotNull(message = "Stamp Amount is Required")
	private Double	stampAmount;
	@NotBlank(message = "Instrument Type is Required")
	private String instrumentType; 
	//@Pattern(regexp = "[0-9]", message = "Please Enter a Valid Instrument Number")
	@NotBlank(message = "Instrument Number is Requrired ")
	private String instrumentNumber;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Instrument Date is Required")
	private LocalDate instrumentDate;
	@NotBlank(message = "Drawn no is Required")
	private String drawnOn; 
	private Integer glTxnNumber; // only numeric
	@NotBlank(message = "Received From Type is Required")
	private String receivedfromType;
	@NotNull(message = "Receipt Amount is Required")
	private Double receiptAmount; 
	private String glFlag;
	//@NotBlank(message = "Payin Slip is Required")
	private String payInSlip; // new column
	private Integer oldReceiptNumber;
	private Integer glTxnNumberOld;	
	private String productDescription;
	private double hsn;
	private double uom;
	private double qty;
	private double rate;
	private double amount;
	private double discount;
	private double taxableDiscount;
	//@NotBlank(message = "Customer's GST number is Required")
	private String customerGstNo;
	private String ecgcGstNo;
	private String invoiceNo;
	private double cgstAmount;
	private double sgstAmount;
	private double igstAmount;
	private double utgstAmount;
	private double cgstTaxPer;
	private double sgstTaxPer;
	private double igstTaxPer;
	private double utgstTaxPer;
	//@NotBlank(message = "Customer's State is required")
	private String expoState;
//	@NotBlank(message = "Branch's State is Required")
	private String branchState;
	private String taxType;
	private String oldInvoiceNo;
	@NotBlank(message = "Please Select an Option")
	private String sez;
	
	private String entityCd;
	
	@NotBlank(message = "GL Txn Type is Required For Transaction")
	private String glTxnType;
	//@NotBlank(message = "Txn Date is Required For Transaction")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate txnDt;
	private String reference;
	private String revarsalGlTxnType;
	private Integer revarsalGlTxnNo;
	private String reversalReason;
	//@NotBlank(message = " Required For Transaction")
	private String fiscalYr;
	private String t1;
	private String t2;
	private String t3;
	private String t4;
	private String t5;
	private String t6;
	private String t7;
	private String t8;
	private String t9;
	private String 	t10;
	private String t11;
	private String t12;
	private String t13;
	private String t14;

	
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	LocalDate ad1; 
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	LocalDate ad2; 
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	LocalDate ad3; 
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	LocalDate ad4;
	Character reversal;
	private List<GlTxnDtl> glTxnDtls;
}
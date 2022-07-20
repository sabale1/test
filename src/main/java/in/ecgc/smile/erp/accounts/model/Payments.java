package in.ecgc.smile.erp.accounts.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Payments
{


	private PaymentsTcodes paymentsTcodes;


	private List<GlTxnDtlPymtEnt> glTxnDtlPymtEntList;


	public Payments() {
		super();
	}

	public Payments(PaymentsTcodes paymentsTcodes) {
		super();
		this.paymentsTcodes = paymentsTcodes;
	}


	public Payments(PaymentsTcodes paymentsTcodes, List<GlTxnDtlPymtEnt> glTxnDtlPymtEntList) {
		super();
		this.paymentsTcodes = paymentsTcodes;
		this.glTxnDtlPymtEntList = glTxnDtlPymtEntList;
	}


	@NotBlank(message="{entityCd.required}")
	@Size(min=0,max=4,message="{entityCd.size}")
	private String entityCd;

	@NotBlank(message="{logicallocCd.required}")
	@Size(min=0,max=10,message="{logicallocCd.size}")
	private String logicallocCd;

	@NotBlank(message="{pymtNo.required}")
	@Size(min=0,max=10,message="{pymtNo.size}")
	private Integer pymtNo;

	@Size(min=0,max=13,message="{pymtDt.size}")
	private LocalDate pymtDt;

	@NotBlank(message="{sectionCd.required}")
	@Size(min=0,max=10,message="{sectionCd.size}")
	private String sectionCd;

	@NotBlank(message="{adviceNo.required}")
	@Size(min=0,max=10,message="{adviceNo.size}")
	private Integer adviceNo;

	@NotBlank(message="{payToType.required}")
	@Size(min=0,max=4,message="{payToType.size}")
	private String payToType;

	@NotBlank(message="{pymtPartyCd.required}")
	@Size(min=0,max=50,message="{pymtPartyCd.size}")
	private String pymtPartyCd;

	@Size(min=0,max=100,message="{pymtPartyName.size}")
	private String pymtPartyName;

	@Size(min=0,max=14,message="{amtPaid.size}")
	private double amtPaid;

	@Size(min=0,max=300,message="{remarks.size}")
	private String remarks;

	@NotBlank(message="{instrumentType.required}")
	@Size(min=0,max=4,message="{instrumentType.size}")
	private String instrumentType;

	@Size(min=0,max=20,message="{instrumentNo.size}")
	private String instrumentNo;

	@Size(min=0,max=100,message="{drawnOn.size}")
	private String drawnOn;

	@Size(min=0,max=100,message="{favouring.size}")
	private String favouring;

	@Size(min=0,max=1,message="{pymtToEmployee.size}")
	private String pymtToEmployee;

	@Size(min=0,max=1,message="{pymtInForex.size}")
	private Character pymtInForex;

	@Size(min=0,max=14,message="{exchrateAtBillIwd.size}")
	private double exchrateAtBillIwd;

	@Size(min=0,max=14,message="{exchrateAtPymt.size}")
	private double exchrateAtPymt;

	@NotBlank(message="{glFlg.required}")
	@Size(min=0,max=1,message="{glFlg.size}")
	private String glFlg;

	@Size(min=0,max=2,message="{glTxnType.size}")
	private String glTxnType;

	@Size(min=0,max=10,message="{glTxnNo.size}")
	private Integer glTxnNo;

	private String moduleCd;

	private String mappingCd;

	@Size(min=0,max=9,message="{fiscalYr.size}")
	private String fiscalYr;

	@Size(min=0,max=31,message="{oldCd.size}")
	private String oldCd;

	@NotBlank(message="{createdBy.required}")
	@Size(min=0,max=50,message="{createdBy.size}")
	private String createdBy;

	@Size(min=0,max=50,message="{lastUpdatedBy.size}")
	private String lastUpdatedBy;

	@Size(min=0,max=29,message="{createdDt.size}")
	private LocalDate createdDt;

	@NotBlank(message="{lastUpdatedDt.required}")
	@Size(min=0,max=29,message="{lastUpdatedDt.size}")
	private LocalDate lastUpdatedDt;

	@Size(min=0,max=2147483647,message="{metaStatus.size}")
	private String metaStatus;

	@Size(min=0,max=2147483647,message="{metaRemarks.size}")
	private String metaRemarks;

}

package in.ecgc.smile.erp.accounts.model;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class GlTxnDtlPymtEnt
{
	@Size(min=10,max=10,message="{paymentNo.size}")
	private Integer paymentNo;

	@Size(min=10,max=10,message="{adviceNo.size}")
	private Integer adviceNo;

	@Size(min=2,max=10,message="{sectionCd.size}")
	private String sectionCd;

	@Size(min=2,max=10,message="{logicallocCd.size}")
	private String logicallocCd;

	private String glTxnType;

	private Integer srNo;

	@Size(min=4,max=4,message="{mainglCd.size}")
	private String mainglCd;

	@Size(min=4,max=3,message="{subglCd.size}")
	private String subglCd;

	private String glName;

	private String plLevel;

	private String personalLedgerCd;

	private String subBifurcationLevel;

	private String subBifurcationCode;

	private String drCrFlg;

	private double txnAmt;

	private String txnRmk;

	private String userId;

	@Size(min=0,max=50,message="{createdBy.size}")
	private String createdBy;

	@Size(min=0,max=50,message="{lastUpdatedBy.size}")
	private String lastUpdatedBy;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate createdDt;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate lastUpdatedDt;

	@Size(min=0,max=2147483647,message="{metaStatus.size}")
	private String metaStatus;

	@Size(min=0,max=2147483647,message="{metaRemarks.size}")
	private String metaRemarks;

	@Size(min=0,max=4,message="{entityCd.size}")
	private String entityCd;

	}

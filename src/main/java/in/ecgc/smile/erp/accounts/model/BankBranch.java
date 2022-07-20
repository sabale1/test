package in.ecgc.smile.erp.accounts.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BankBranch {

	@NotBlank(message = "{logicalLocCode.required}")
	private String logicalLocCode;
	private String accountingLogicalLocCode;
	@NotBlank(message = "{expensebankName.required}")
	private String expensebankName;
	@NotBlank(message = "{expensebankAddr.required}")
	private String expensebankAddr;
	@Pattern(regexp = "^\\d{9,18}$", message = "Please Enter a Valid Account Number")
	@NotBlank(message = "{expenseAccountNumber.required}")
	private String expenseAccountNumber;
	@Pattern(regexp = "^[A-Z|a-z]{4}[0][a-zA-Z0-9]{6}$", message = "Please Enter a Valid IFSC code")
	@NotBlank(message = "{expenseAcctIfscCode.required}")
	private String expenseAccountIfscCode;
	@Pattern(regexp = "^[A-Z|a-z]{4}[0][a-zA-Z0-9]{6}$", message = "Please Enter a Valid NEFT code")
	@NotBlank(message = "{expenseAcctNeftCode.required}")
	private String expenseAcctNeftCode;

	@NotBlank(message = "Required For Transaction")
	private String dribmainGlCd;
	@NotBlank(message = "Required For Transaction")
	private String dribsubGlCd;

	@NotBlank(message = "Required For Transaction")
	private String cribmainGlCd;
	@NotBlank(message = "Required For Transaction")
	private String cribsubGlCd;

	@NotBlank(message = "Required For Transaction")
	private String expensebankmainGlCd;
	@NotBlank(message = "Required For Transaction")
	private String expensebanksubGlCd;

	@NotBlank(message = "Required For Transaction")
	private String crsubbifurcationCd;

	@NotBlank(message = "Required For Transaction")
	private String expencebanksubbifurcationCd;

	private String beneficiaryacctName;
	private String beneficiarycodeIbank;

	@NotBlank(message = "{clientId.required}")
	private String clientId;

	@NotNull(message = "{active.required}")
	private Boolean active;

	private String gstin;

	public BankBranch() {
		super();
	}

}

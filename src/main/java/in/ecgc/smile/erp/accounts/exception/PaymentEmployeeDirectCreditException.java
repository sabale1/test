package in.ecgc.smile.erp.accounts.exception;

public class PaymentEmployeeDirectCreditException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Object[] args; 
	
	public PaymentEmployeeDirectCreditException() {
		super();
	}
	
	public PaymentEmployeeDirectCreditException(String message) {
		super(message);
	}
	
	public PaymentEmployeeDirectCreditException(String message, Object[] args) {
		super(message);
		this.args = args;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	
}

package in.ecgc.smile.erp.accounts.exception;

public class RecordAlreadyExistException extends RuntimeException
{

	private static final long serialVersionUID = -3226630364916511527L;
	private Object[] args; 
	
	public RecordAlreadyExistException() {

		super();
	}
	
	public RecordAlreadyExistException(String message) {
		super(message);
	}
	
	public RecordAlreadyExistException(String message, Object[] args) {
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

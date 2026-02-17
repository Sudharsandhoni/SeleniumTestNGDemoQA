package core.exceptions;

public class CustomTimeoutException extends RuntimeException {

	public CustomTimeoutException(String message) {
		super(message);
	}

	public CustomTimeoutException(Exception exception) {
		super(exception);
	}

	public CustomTimeoutException(String message, Exception exception) {
		super(message, exception);
	}
}

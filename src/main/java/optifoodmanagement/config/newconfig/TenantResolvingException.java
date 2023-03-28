package optifoodmanagement.config.newconfig;

public class TenantResolvingException extends Exception {
	
	public TenantResolvingException(Throwable throwable, String message) {
		super(message, throwable);
	}
}
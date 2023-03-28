package optifoodmanagement.ui.model.request;

public class ResetPasswordRequestModel {

	private String email;
	private String resetCode;
	private String newPassword;
	private String oldPassword;
    private String licenceKey;
	
	public String getLicenceKey() {
		return licenceKey;
	}
	public void setLicenceKey(String licenceKey) {
		this.licenceKey = licenceKey;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getResetCode() {
		return resetCode;
	}
	public void setResetCode(String resetCode) {
		this.resetCode = resetCode;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}

package optifoodmanagement.ui.model.request;

public class PrivilegeRequestModel {
	private String privilegeName;
	private String privilegeDescription;
	private String privilegeUrl;
	private String method;
	
	private String scope;
	private String createdBy;
	private String updatedBy;

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getPrivilegeDescription() {
		return privilegeDescription;
	}

	public void setPrivilegeDescription(String privilegeDescription) {
		this.privilegeDescription = privilegeDescription;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getPrivilegeUrl() {
		return privilegeUrl;
	}

	public void setPrivilegeUrl(String privilegeUrl) {
		this.privilegeUrl = privilegeUrl;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
}

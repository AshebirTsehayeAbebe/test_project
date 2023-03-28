package optifoodmanagement.ui.model.response;

import java.util.List;

public class UserRoleResponseModel {

	private Long userId;
	private String userFullName;
	
	private List<RoleResponseModel> roles;
	private String createdBy;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	
	public List<RoleResponseModel> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleResponseModel> roles) {
		this.roles = roles;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}

package optifoodmanagement.ui.model.request;

public class RolePrivilegeRequestModel {

	private Long roleId;
	private Integer[] privilegeIds;
	private String isPrivileged;
	private String createdBy;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(Integer[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	public String getIsPrivileged() {
		return isPrivileged;
	}

	public void setIsPrivileged(String isPrivileged) {
		this.isPrivileged = isPrivileged;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}

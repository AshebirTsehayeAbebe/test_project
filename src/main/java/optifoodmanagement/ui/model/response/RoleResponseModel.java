package optifoodmanagement.ui.model.response;

public class RoleResponseModel {

	private Long roleId;
	private String roleName;
	private String roleFullName;
	private String createdBy;
	private Integer totalPage;
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleFullName() {
		return roleFullName;
	}

	public void setRoleFullName(String roleFullName) {
		this.roleFullName = roleFullName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

}

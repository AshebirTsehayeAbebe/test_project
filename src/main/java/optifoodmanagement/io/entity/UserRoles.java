package optifoodmanagement.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import optifoodmanagement.model.audit.Audit;

@Entity(name = "users_role")
public class UserRoles extends Audit implements Serializable {

	private static final long serialVersionUID = 9155893809458722562L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userRoleId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private Long roleId;

	@Column
	private boolean isDeleted = false;

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}

package optifoodmanagement.io.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.*;

import optifoodmanagement.model.audit.Audit;

@Entity
@Table(name = "roles")
public class Role extends Audit {

	private static final long serialVersionUID = 8324707424655964845L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;

	@Column(nullable = false, unique = true)
	private String roleName;

	@Column(nullable = false, unique = true)
	private String roleFullName;

	@Column
	private boolean isDeleted = false;

	public String getRoleFullName() {
		return roleFullName;
	}

	public void setRoleFullName(String roleFullName) {
		this.roleFullName = roleFullName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


//	public List<RoleEntity> getRoles(String username) {
//		List<Map<String, Object>> results = jdbcTemplate
//				.queryForList("select user_role from user_role where user_name = ?", new Object[] { username });
//		List<RoleEntity> roles = results.stream().map(m -> {
//			Role role = new Role();
//			role.setRole(String.valueOf(m.get("user_role")));
//			return role;
//		}).collect(Collectors.toList());
//		return roles;
//	}
	
}

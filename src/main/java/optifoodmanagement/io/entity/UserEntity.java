package optifoodmanagement.io.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name = "users")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1500349168344396612L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, unique = true)
	private String userId;
	
	@Column(nullable = false, length = 50)
	private String firstName;
	
	@Column(length = 50)
	private long tenantId;
	
	@Column(nullable = false, length = 50)
	private String lastName;
	
	@Column(nullable = false, length = 15)
	private String mobilePhone;
	
	@Column(nullable = false, length = 120, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String encryptedPassword;
	
	private String emailVerificationToken;
	
	@Column(nullable = true)
	private String passwordResetCode;
	
	@Column(nullable = false)
	private String userType;
	
	@Column(nullable = false)
	private String userStatus;
	
	@Column(nullable = true)
	private String profilePicture;
	
	@Column
	private Integer restaurantId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "auth_provider")
	private AuthenticationProvider authProvider;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roleEntities = new HashSet<>();

	public Set<Role> getRoles() {
		return roleEntities;
	}
	
	public void setRoles(Set<Role> roleEntities) {
		this.roleEntities = roleEntities;
	}
	
	public Integer getRestaurantId() {
		return restaurantId;
	}

	
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getUserStatus() {
		return userStatus;
	}
	
	public void setUserStatus(String status) {
		this.userStatus = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public String getPasswordResetCode() {
		return passwordResetCode;
	}

	public void setPasswordResetCode(String passwordResetCode) {
		this.passwordResetCode = passwordResetCode;
	}

	public Set<Role> getRoleEntities() {
		return roleEntities;
	}

	public void setRoleEntities(Set<Role> roleEntities) {
		this.roleEntities = roleEntities;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public long getTenantId() {
		return tenantId;
	}

	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}

	public AuthenticationProvider getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(AuthenticationProvider authProvider) {
		this.authProvider = authProvider;
	}
}

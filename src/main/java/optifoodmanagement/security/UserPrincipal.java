package optifoodmanagement.security;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import optifoodmanagement.io.entity.UserEntity;

public class UserPrincipal implements UserDetails {
    private Long id;

	private String name;
	
    private String username;
	
	private String userType;
	
    private String userStatus;
    private long tenantId;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(Long id, String name, String username, String email, String userType, String userStatus, Long tenantId, 
	    String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
		this.name = name;
        this.username = username;
		this.userType = userType;
        this.userStatus = userStatus;
        this.email = email;
        this.password = password;
		this.authorities = authorities;
		this.tenantId = tenantId;
    }

    public static UserPrincipal create(UserEntity user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
		        .map(role -> new SimpleGrantedAuthority(role.getRoleFullName())).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
		        user.getFirstName(),
                user.getUserId(),
                user.getEmail(),
		        user.getUserType(),
                user.getUserStatus(),
                user.getTenantId(),
		        user.getEncryptedPassword(), authorities
        );
    }

    
    public Long getId() {
        return id;
    }

	public String getName() {
		return name;
	}
	
    public String getEmail() {
        return email;
    }
    
    public String getUserStatus() {
		return userStatus;
	}
	
	public String getUserType() {
		return userType;
	}
	
    public long getTenantId() {
		return tenantId;
	}

	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}

	@Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

	
}

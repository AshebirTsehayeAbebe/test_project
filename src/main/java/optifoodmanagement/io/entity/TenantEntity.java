package optifoodmanagement.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import optifoodmanagement.model.audit.Audit;

@Entity(name = "tenant")
public class TenantEntity extends Audit implements Serializable {

	private static final long serialVersionUID = 1500349168344396612L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long tenantId;
	
	@Column
	private String name;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private String url;
	
	@Column(nullable = false, unique = true)
	private String licenseKey;

	
	public long getTenantId() {
		return tenantId;
	}

	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	

}

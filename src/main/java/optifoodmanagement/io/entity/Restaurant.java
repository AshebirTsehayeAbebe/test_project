package optifoodmanagement.io.entity;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import optifoodmanagement.model.audit.Audit;

@Entity(name = "restaurant")
public class Restaurant extends Audit implements Serializable {
	
	private static final long serialVersionUID = 3798856537559182204L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer restaurantId;
	
	@Column(nullable = false, length = 50)
	private String restaurantName;
	
	@Column
	private String companyId;
	
	@Column(nullable = false)
	private String licenseKey;
	
	@Column
	private Time openingTime;
	
	@Column
	private Time closingTime;
	
	@Column
	private String address;
	
	@Column
	private String cityName;
	
	@Column
	private String postalCode;
	
	@Column
	private String websiteLink;
	
	@Column
	private Integer contactId;
	
	@Column
	private Integer chainId;
	
	@Column
	private String logo;
	
	@Column
	private String database;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private String url;

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getRestaurantId() {
		return restaurantId;
	}
	
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public String getRestaurantName() {
		return restaurantName;
	}
	
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getLicenseKey() {
		return licenseKey;
	}

	
	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}
	
	public Time getOpeningTime() {
		return openingTime;
	}
	
	public void setOpeningTime(Time openingTime) {
		this.openingTime = openingTime;
	}
	
	public Time getClosingTime() {
		return closingTime;
	}
	
	public void setClosingTime(Time closingTime) {
		this.closingTime = closingTime;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getWebsiteLink() {
		return websiteLink;
	}
	
	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}
	
	public Integer getContactId() {
		return contactId;
	}
	
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	
	public Integer getChainId() {
		return chainId;
	}
	
	public void setChainId(Integer chainId) {
		this.chainId = chainId;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}

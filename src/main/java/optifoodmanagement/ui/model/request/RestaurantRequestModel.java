package optifoodmanagement.ui.model.request;

import java.sql.Time;

public class RestaurantRequestModel {
	
	private String restaurantName;
	private String companyId;
	private Time openingTime;
	private Time closingTime;
	private String address;
	private String cityName;
	private String postalCode;
	private String websiteLink;
	private Integer contactId;
	private Integer chainId;
	private String database;
	private String url;
	private String username;
	private String password;
	
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	
}

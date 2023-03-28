package optifoodmanagement.ui.model.response;

import java.sql.Time;

public class RestaurantResponseModel {
	
	private Integer restaurantId;
	private String restaurantName;
	private String companyId;
	private String licenseKey;
	private Time openingTime;
	private Time closingTime;
	private String address;
	private String cityName;
	private String postalCode;
	private String websiteLink;
	private Integer contactId;
	private String contactName;
	private Integer chainId;
	private String chainName;
	private String logo;
	private String database;
	private String url;
	private Integer totalPage;
	
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
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public Integer getChainId() {
		return chainId;
	}
	
	public void setChainId(Integer chainId) {
		this.chainId = chainId;
	}
	
	public String getChainName() {
		return chainName;
	}
	
	public void setChainName(String chainName) {
		this.chainName = chainName;
	}
	
	public Integer getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}

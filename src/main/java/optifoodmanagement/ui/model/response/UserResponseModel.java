package optifoodmanagement.ui.model.response;


public class UserResponseModel {
	private long id;
	private String userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String mobilePhone;
	private String userType;
	private String userStatus;
	private Integer restaurantId;
	private String restaurantName;
	private int totalPages;
	private String profilePicture;
	private long tenantId;
	private RestaurantResponseModel restaurantResponseModel;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public RestaurantResponseModel getRestaurantResponseModel() {
		return restaurantResponseModel;
	}

	public void setRestaurantResponseModel(RestaurantResponseModel restaurantResponseModel) {
		this.restaurantResponseModel = restaurantResponseModel;
	}

	public long getTenantId() {
		return tenantId;
	}

	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
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
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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
	
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
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
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	public String getProfilePicture() {
		return profilePicture;
	}
	
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	
}

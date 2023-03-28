package optifoodmanagement.ui.model.response;

import java.util.List;

public class JwtAuthenticationResponse {

	private String accessToken;
	private String userId;
	private String userType;
	private String userStatus;
	
	private List<RoleResponseModel> roleResponseModels;
	private List<RoleResponseForLogin> grantedPrivileges;
	private RestaurantResponseModel restaurantResponseModel;

	public RestaurantResponseModel getRestaurantResponseModel() {
		return restaurantResponseModel;
	}

	public void setRestaurantResponseModel(RestaurantResponseModel restaurantResponseModel) {
		this.restaurantResponseModel = restaurantResponseModel;
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

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<RoleResponseForLogin> getGrantedPrivileges() {
		return grantedPrivileges;
	}

	public void setGrantedPrivileges(List<RoleResponseForLogin> grantedPrivileges) {
		this.grantedPrivileges = grantedPrivileges;
	}

	public List<RoleResponseModel> getRoleResponseModels() {
		return roleResponseModels;
	}
	
	public void setRoleResponseModels(List<RoleResponseModel> roleResponseModels) {
		this.roleResponseModels = roleResponseModels;
	}
	
}

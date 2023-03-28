package optifoodmanagement.ui.model.response;


public class MacAddressResponseModel {
	
	private Integer macAddressId;
	
	private String macAddress;
	
	private Integer restaurantId;
	
	private String status;
	
	private Integer totalPage;
	
	public Integer getMacAddressId() {
		return macAddressId;
	}
	
	public void setMacAddressId(Integer macAddressId) {
		this.macAddressId = macAddressId;
	}
	
	public String getMacAddress() {
		return macAddress;
	}
	
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	public Integer getRestaurantId() {
		return restaurantId;
	}
	
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
}

package optifoodmanagement.ui.model.request;

import org.springframework.web.multipart.MultipartFile;

public class LogoRequestModel {
	
	private MultipartFile logo;
	
	private Integer restaurantId;
	
	public MultipartFile getLogo() {
		return logo;
	}

	
	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}
	
	public Integer getRestaurantId() {
		return restaurantId;
	}
	
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	

}

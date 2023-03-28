package optifoodmanagement.service;

import java.io.IOException;
import java.util.List;

import optifoodmanagement.ui.model.request.LogoRequestModel;
import optifoodmanagement.ui.model.request.RestaurantRequestModel;
import optifoodmanagement.ui.model.response.RestaurantResponseModel;

public interface RestaurantService {
	
	RestaurantResponseModel saveRestaurant(RestaurantRequestModel requestDetail);
	
	RestaurantResponseModel updateRestaurant(Integer restaurantId, RestaurantRequestModel requestDetail);
	
	String deleteRestaurant(Integer restaurantId);
	
	List<RestaurantResponseModel> getRestaurants(String searchKey, int contactId, int chainId, int page, int limit);
	
	RestaurantResponseModel getRestaurant(Integer restaurantId);
	
	String resetLicenseKey(Integer restaurantId);
	
	String uploadLogo(LogoRequestModel requestDetail) throws IOException;
	
}

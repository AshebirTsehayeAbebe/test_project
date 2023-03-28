package optifoodmanagement.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import optifoodmanagement.service.RestaurantService;
import optifoodmanagement.ui.model.request.LogoRequestModel;
import optifoodmanagement.ui.model.request.RestaurantRequestModel;
import optifoodmanagement.ui.model.response.RestaurantResponseModel;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
	
	@Autowired
	RestaurantService restaurantService;
	
	@PostMapping
	@ApiOperation(value = "Saves restaurant detail to database", notes = "Provide restaurant detail to save", response = RestaurantResponseModel.class)
	public RestaurantResponseModel saveRestaurant(@RequestBody RestaurantRequestModel requestDetail) {
		
		RestaurantResponseModel returnValue = restaurantService.saveRestaurant(requestDetail);
		
		return returnValue;
		
	}
	
	@PutMapping(path = "/{restaurantId}")
	@ApiOperation(value = "Updates restaurant detail based on the given id", notes = "Provide restaurant detail with id to update the record", response = RestaurantResponseModel.class)
	public RestaurantResponseModel updateRestaurant(@RequestBody RestaurantRequestModel requestDetail,
	        @PathVariable Integer restaurantId) {
		RestaurantResponseModel returnValue = restaurantService.updateRestaurant(restaurantId, requestDetail);
		return returnValue;
	}
	
	@GetMapping(path = "/{restaurantId}")
	@ApiOperation(value = "Finds restaurant by id", notes = "Provide an id to look up specific restaurant from the restaurants", response = RestaurantResponseModel.class)
	public RestaurantResponseModel getRestaurant(@PathVariable Integer restaurantId) {
		RestaurantResponseModel returnValue = restaurantService.getRestaurant(restaurantId);
		return returnValue;
	}
	
	@GetMapping
	@ApiOperation(value = "Fetches restaurants for given limit per page", notes = "Provide limit and page to get required count of restaurants per page", response = RestaurantResponseModel.class)
	public List<RestaurantResponseModel> getRestaurants(
	        @RequestParam(value = "searchKey", defaultValue = "") String searchKey,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "limit", defaultValue = "1000") int limit,
	        @RequestParam(value = "contactId", defaultValue = "0") int contactId,
	        @RequestParam(value = "chainId", defaultValue = "0") int chainId) {
		List<RestaurantResponseModel> returnValue = restaurantService.getRestaurants(searchKey, contactId, chainId, page,
		    limit);
		return returnValue;
	}
	
	@DeleteMapping(path = "/{restaurantId}")
	@ApiOperation(value = "Deletes restaurant for given id", notes = "Provide an id to delete specific restaurant from database", response = String.class)
	public String deleteRestaurant(@PathVariable Integer restaurantId) {
		String returnValue = restaurantService.deleteRestaurant(restaurantId);
		return returnValue;
	}
	
	@PutMapping(path = "/reset-license-key/{restaurantId}")
	@ApiOperation(value = "Resets license key for a given restaurant id", notes = "Provide restaurant id to Resets license key", response = String.class)
	public String resetLicenseKey(@PathVariable Integer restaurantId) {
		String returnValue = restaurantService.resetLicenseKey(restaurantId);
		return returnValue;
	}
	
	@PostMapping(path = "/upload-logo")
	public String uploadLogo(@ModelAttribute LogoRequestModel requestDetail) throws IOException {
		
		String returnValue = restaurantService.uploadLogo(requestDetail);
		
		return returnValue;
	}
	
}

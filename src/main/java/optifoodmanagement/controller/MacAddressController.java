package optifoodmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import optifoodmanagement.service.MacAddressService;
import optifoodmanagement.ui.model.request.MacAddressRequestModel;
import optifoodmanagement.ui.model.request.StatusRequestModel;
import optifoodmanagement.ui.model.response.MacAddressResponseModel;

@RestController
@RequestMapping("/api/mac-address")
public class MacAddressController {
	
	@Autowired
	MacAddressService macAddressService;
	
	@PostMapping
	@ApiOperation(value = "Saves mac address detail to database", notes = "Provide Mac Address detail to save", response = MacAddressResponseModel.class)
	public MacAddressResponseModel saveMacAddress(@RequestBody MacAddressRequestModel requestDetail) {
		
		MacAddressResponseModel returnValue = macAddressService.saveMacAddress(requestDetail);
		
		return returnValue;
		
	}
	
	@GetMapping(path = "/{macAddressId}")
	@ApiOperation(value = "Finds mac address by id", notes = "Provide an id to look up specific mac address from the mac addresses", response = MacAddressResponseModel.class)
	public MacAddressResponseModel getMacAddress(@PathVariable Integer macAddressId) {
		MacAddressResponseModel returnValue = macAddressService.getMacAddress(macAddressId);
		return returnValue;
	}
	
	@GetMapping
	@ApiOperation(value = "Fetches mac address for given limit per page", notes = "Provide limit and page to get required count of mac address per page", response = MacAddressResponseModel.class)
	public List<MacAddressResponseModel> getMacAddresses(
	        @RequestParam(value = "searchKey", defaultValue = "") String searchKey,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "limit", defaultValue = "1000") int limit,
	        @RequestParam(value = "restaurantId", defaultValue = "0") int restaurantId) {
		List<MacAddressResponseModel> returnValue = macAddressService.getMacAddresses(searchKey, restaurantId, page, limit);
		return returnValue;
	}
	
	@PutMapping(path = "/{macAddressId}")
	@ApiOperation(value = "Updates mac address detail based on the given id", notes = "Provide mac address detail with id to update the record", response = MacAddressResponseModel.class)
	public MacAddressResponseModel updateMacAddress(@RequestBody MacAddressRequestModel requestDetail,
	        @PathVariable Integer macAddressId) {
		MacAddressResponseModel returnValue = macAddressService.updateMacAddress(macAddressId, requestDetail);
		return returnValue;
	}
	
	@DeleteMapping(path = "/{macAddressId}")
	@ApiOperation(value = "Deletes mac address for given id", notes = "Provide an id to delete specific mac address from database", response = String.class)
	public String deleteMacAddress(@PathVariable Integer macAddressId) {
		String returnValue = macAddressService.deleteMacAddress(macAddressId);
		return returnValue;
	}
	
	@PutMapping(path = "/change-status/{macAddressId}")
	@ApiOperation(value = "Change mac address status based on the given id", notes = "Provide staus  with id to change mac address status", response = MacAddressResponseModel.class)
	public String changeMacAddressS(@RequestBody StatusRequestModel requestDetail, @PathVariable Integer macAddressId) {
		String returnValue = macAddressService.changeMacAddress(macAddressId, requestDetail);
		return returnValue;
	}
	
}

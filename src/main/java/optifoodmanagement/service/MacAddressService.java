package optifoodmanagement.service;

import java.util.List;

import optifoodmanagement.ui.model.request.MacAddressRequestModel;
import optifoodmanagement.ui.model.request.StatusRequestModel;
import optifoodmanagement.ui.model.response.MacAddressResponseModel;

public interface MacAddressService {
	
	MacAddressResponseModel saveMacAddress(MacAddressRequestModel requestDetail);
	
	MacAddressResponseModel getMacAddress(Integer macAddressId);
	
	List<MacAddressResponseModel> getMacAddresses(String searchKey, Integer restaurantId, int page, int limit);
	
	MacAddressResponseModel updateMacAddress(Integer macAddressId, MacAddressRequestModel requestDetail);
	
	String deleteMacAddress(Integer macAddressId);
	
	String changeMacAddress(Integer macAddressId, StatusRequestModel requestDetail);
	
}

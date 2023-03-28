package optifoodmanagement.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import optifoodmanagement.exception.AppException;
import optifoodmanagement.io.entity.MacAddress;
import optifoodmanagement.io.repositories.MacAddressRepository;
import optifoodmanagement.service.MacAddressService;
import optifoodmanagement.ui.model.request.MacAddressRequestModel;
import optifoodmanagement.ui.model.request.StatusRequestModel;
import optifoodmanagement.ui.model.response.MacAddressResponseModel;

@Service
public class MacAddressServiceImpl implements MacAddressService {
	
	@Autowired
	MacAddressRepository macAddressRepository;
	
	@Override
	public MacAddressResponseModel saveMacAddress(MacAddressRequestModel requestDetail) {
		MacAddressResponseModel returnValue = new MacAddressResponseModel();
		MacAddress macAddress = new MacAddress();
		
		BeanUtils.copyProperties(requestDetail, macAddress);
		macAddress.setStatus("Disabled");
		MacAddress savedDetail = macAddressRepository.save(macAddress);
		
		if (savedDetail == null)
			throw new AppException("Record not saved!");
		BeanUtils.copyProperties(savedDetail, returnValue);
		return returnValue;
	}
	
	@Override
	public MacAddressResponseModel getMacAddress(Integer macAddressId) {
		MacAddressResponseModel returnValue = new MacAddressResponseModel();
		MacAddress macAddress = macAddressRepository.findByMacAddressIdAndIsDeleted(macAddressId, false);
		
		if (macAddress == null)
			throw new AppException("No record with this id!");
		
		BeanUtils.copyProperties(macAddress, returnValue);
		return returnValue;
	}
	
	@Override
	public List<MacAddressResponseModel> getMacAddresses(String searchKey, Integer restaurantId, int page, int limit) {
		List<MacAddressResponseModel> returnValue = new ArrayList<>();
		
		if (page > 0)
			page = page - 1;
		
		Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("macAddressId").descending());
		Page<MacAddress> macAddressPage = null;
		
		if ("".equals(searchKey) && restaurantId == 0)
			macAddressPage = macAddressRepository.findByIsDeleted(false, pageableRequest);
		
		else if ("".equals(searchKey) && restaurantId != 0)
			macAddressPage = macAddressRepository.findByRestaurantIdAndIsDeleted(false, restaurantId, pageableRequest);
		
		else if (!"".equals(searchKey) && restaurantId == 0)
			macAddressPage = macAddressRepository.findByIsDeletedAndMacAddressContaining(false, searchKey, pageableRequest);
		
		else
			macAddressPage = macAddressRepository.findByRestaurantIdAndMacAddressAndIsDeleted(restaurantId, searchKey, false,
			    pageableRequest);
		
		List<MacAddress> macAddresses = macAddressPage.getContent();
		
		int totalPages = macAddressPage.getTotalPages();
		for (MacAddress macAddress : macAddresses) {
			
			MacAddressResponseModel responseModel = new MacAddressResponseModel();
			BeanUtils.copyProperties(macAddress, responseModel);
			
			if (returnValue.size() == 0) {
				responseModel.setTotalPage(totalPages);
			}
			
			returnValue.add(responseModel);
		}
		return returnValue;
	}
	
	@Override
	public MacAddressResponseModel updateMacAddress(Integer macAddressId, MacAddressRequestModel requestDetail) {
		MacAddressResponseModel returnValue = new MacAddressResponseModel();
		MacAddress macAddress = macAddressRepository.findByMacAddressIdAndIsDeleted(macAddressId, false);
		
		if (macAddress == null)
			throw new AppException("No record with this id!");
		BeanUtils.copyProperties(requestDetail, macAddress);
		MacAddress savedDetail = macAddressRepository.save(macAddress);
		
		if (savedDetail == null)
			throw new AppException("Record not updated!");
		BeanUtils.copyProperties(savedDetail, returnValue);
		return returnValue;
	}
	
	@Override
	public String deleteMacAddress(Integer macAddressId) {
		
		MacAddress macAddress = macAddressRepository.findByMacAddressIdAndIsDeleted(macAddressId, false);
		if (macAddress == null)
			throw new AppException("No record with this id!");
		macAddress.setDeleted(true);
		MacAddress savedDetail = macAddressRepository.save(macAddress);
		
		if (savedDetail == null)
			throw new AppException("Record not deleted!");
		return "Deleted successfully!";
	}
	
	@Override
	public String changeMacAddress(Integer macAddressId, StatusRequestModel requestDetail) {
		
		MacAddress macAddress = macAddressRepository.findByMacAddressIdAndIsDeleted(macAddressId, false);
		
		if (macAddress == null)
			throw new AppException("No record with this id!");
		macAddress.setStatus(requestDetail.getStatus());
		MacAddress savedDetail = macAddressRepository.save(macAddress);
		
		if (savedDetail == null)
			throw new AppException("Record not updated!");
		return "Status changed successfully!";
	}
	
}

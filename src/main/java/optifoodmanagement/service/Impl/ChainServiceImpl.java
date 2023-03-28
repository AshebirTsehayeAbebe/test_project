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
import optifoodmanagement.io.entity.Chain;
import optifoodmanagement.io.entity.Restaurant;
import optifoodmanagement.io.repositories.ChainRepository;
import optifoodmanagement.io.repositories.RestaurantRepository;
import optifoodmanagement.service.ChainService;
import optifoodmanagement.ui.model.request.ChainRequestModel;
import optifoodmanagement.ui.model.response.ChainResponseModel;

@Service
public class ChainServiceImpl implements ChainService {
	
	@Autowired
	ChainRepository chainRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Override
	public ChainResponseModel saveChain(ChainRequestModel requestDetail) {
		ChainResponseModel returnValue = new ChainResponseModel();
		Chain chain = new Chain();
		
		BeanUtils.copyProperties(requestDetail, chain);
		Chain savedDetail = chainRepository.save(chain);
		
		if (savedDetail == null)
			throw new AppException("Record not saved!");
		BeanUtils.copyProperties(savedDetail, returnValue);
		return returnValue;
	}
	
	@Override
	public ChainResponseModel getChain(Integer chainId) {
		ChainResponseModel returnValue = new ChainResponseModel();
		Chain chain = chainRepository.findByChainIdAndIsDeleted(chainId, false);
		
		if (chain == null)
			throw new AppException("No record with this id!");
		
		BeanUtils.copyProperties(chain, returnValue);
		return returnValue;
	}
	
	@Override
	public List<ChainResponseModel> getChains(String searchKey, int page, int limit) {
		List<ChainResponseModel> returnValue = new ArrayList<>();
		
		if (page > 0)
			page = page - 1;
		
		Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("chainId").descending());
		Page<Chain> chainPage = null;
		
		if ("".equals(searchKey))
			chainPage = chainRepository.findByIsDeleted(false, pageableRequest);
		else
			chainPage = chainRepository.findByIsDeletedAndChainNameContaining(false, searchKey, pageableRequest);
		
		List<Chain> chains = chainPage.getContent();
		
		int totalPages = chainPage.getTotalPages();
		for (Chain chain : chains) {
			
			ChainResponseModel responseModel = new ChainResponseModel();
			BeanUtils.copyProperties(chain, responseModel);
			
			if (returnValue.size() == 0) {
				responseModel.setTotalPage(totalPages);
			}
			
			returnValue.add(responseModel);
		}
		return returnValue;
	}
	
	@Override
	public ChainResponseModel updateChain(Integer chainId, ChainRequestModel requestDetail) {
		ChainResponseModel returnValue = new ChainResponseModel();
		Chain chain = chainRepository.findByChainIdAndIsDeleted(chainId, false);
		
		if (chain == null)
			throw new AppException("No record with this id!");
		BeanUtils.copyProperties(requestDetail, chain);
		Chain savedDetail = chainRepository.save(chain);
		
		if (savedDetail == null)
			throw new AppException("Record not updated!");
		BeanUtils.copyProperties(savedDetail, returnValue);
		return returnValue;
	}

	@Override
	public String deleteChain(Integer chainId) {
		List<Restaurant> restaurants = restaurantRepository.findByChainIdAndIsDeleted(chainId, false);
		if (restaurants.size() > 0)
			throw new AppException("Could not delete a cahin that has restaurant!");
		
		Chain chain = chainRepository.findByChainIdAndIsDeleted(chainId, false);
		if (chain == null)
			throw new AppException("No record with this id!");
		chain.setDeleted(true);
		Chain savedDetail = chainRepository.save(chain);
		
		if (savedDetail == null)
			throw new AppException("Record not deleted!");
		return "Deleted successfully!";
	}
	
}

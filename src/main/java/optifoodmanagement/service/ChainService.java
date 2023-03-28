package optifoodmanagement.service;

import java.util.List;

import optifoodmanagement.ui.model.request.ChainRequestModel;
import optifoodmanagement.ui.model.response.ChainResponseModel;

public interface ChainService {
	
	ChainResponseModel saveChain(ChainRequestModel requestDetail);
	
	ChainResponseModel getChain(Integer chainId);
	
	List<ChainResponseModel> getChains(String searchKey, int page, int limit);
	
	ChainResponseModel updateChain(Integer chainId, ChainRequestModel requestDetail);
	
	String deleteChain(Integer chainId);
	

}

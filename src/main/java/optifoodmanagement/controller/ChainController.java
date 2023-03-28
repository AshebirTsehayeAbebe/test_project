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
import optifoodmanagement.service.ChainService;
import optifoodmanagement.service.RestaurantService;
import optifoodmanagement.ui.model.request.ChainRequestModel;
import optifoodmanagement.ui.model.response.ChainResponseModel;

@RestController
@RequestMapping("/api/chain")
public class ChainController {
	
	@Autowired
	ChainService chainService;
	
	@Autowired
	RestaurantService restaurantService;
	
	@PostMapping
	@ApiOperation(value = "Saves chain detail to database", notes = "Provide chain detail to save", response = ChainResponseModel.class)
	public ChainResponseModel saveChain(@RequestBody ChainRequestModel requestDetail) {
		
		ChainResponseModel returnValue = chainService.saveChain(requestDetail);
		
		return returnValue;
		
	}
	
	@GetMapping(path = "/{chainId}")
	@ApiOperation(value = "Finds chain by id", notes = "Provide an id to look up specific chain from the chains", response = ChainResponseModel.class)
	public ChainResponseModel getRestaurant(@PathVariable Integer chainId) {
		ChainResponseModel returnValue = chainService.getChain(chainId);
		return returnValue;
	}
	
	@GetMapping
	@ApiOperation(value = "Fetches chains for given limit per page", notes = "Provide limit and page to get required count of chains per page", response = ChainResponseModel.class)
	public List<ChainResponseModel> getChains(@RequestParam(value = "searchKey", defaultValue = "") String searchKey,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "limit", defaultValue = "1000") int limit) {
		List<ChainResponseModel> returnValue = chainService.getChains(searchKey, page, limit);
		return returnValue;
	}
	
	@PutMapping(path = "/{chainId}")
	@ApiOperation(value = "Updates chain detail based on the given id", notes = "Provide chain detail with id to update the record", response = ChainResponseModel.class)
	public ChainResponseModel updateChain(@RequestBody ChainRequestModel requestDetail, @PathVariable Integer chainId) {
		ChainResponseModel returnValue = chainService.updateChain(chainId, requestDetail);
		return returnValue;
	}
	
	@DeleteMapping(path = "/{chainId}")
	@ApiOperation(value = "Deletes chain for given id", notes = "Provide an id to delete specific chain from database", response = String.class)
	public String deleteChain(@PathVariable Integer chainId) {
		String returnValue = chainService.deleteChain(chainId);
		return returnValue;
	}
	
}

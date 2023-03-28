package optifoodmanagement.io.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import optifoodmanagement.io.entity.Restaurant;

@Repository
public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Integer> {
	
	Restaurant findByRestaurantIdAndIsDeleted(Integer restaurantId, boolean b);
	
	Page<Restaurant> findByIsDeleted(boolean b, Pageable pageableRequest);
	
	Page<Restaurant> findByIsDeletedAndRestaurantNameContaining(boolean b, String searchKey, Pageable pageableRequest);
	
	List<Restaurant> findByChainIdAndIsDeleted(Integer chainId, boolean b);
	
	List<Restaurant> findByContactIdAndIsDeleted(Integer contactId, boolean b);
	
	Restaurant findByLicenseKeyAndIsDeleted(String licenseKey, boolean b);
	
	Page<Restaurant> findByContactIdAndChainIdAndIsDeleted(int contactId, int chainId, boolean b, Pageable pageableRequest);
	
	Page<Restaurant> findByContactIdAndIsDeleted(int contactId, boolean b, Pageable pageableRequest);
	
	Page<Restaurant> findByChainIdAndIsDeleted(int chainId, boolean b, Pageable pageableRequest);
	
	Page<Restaurant> findByRestaurantNameAndChainIdAndIsDeleted(String searchKey, int chainId, boolean b,
	        Pageable pageableRequest);
	
	Page<Restaurant> findByRestaurantNameAndContactIdAndIsDeleted(String searchKey, int contactId, boolean b,
	        Pageable pageableRequest);
	
	Page<Restaurant> findByRestaurantNameAndContactIdAndChainIdAndIsDeleted(String searchKey, int contactId, int chainId,
	        Boolean b,
	        Pageable pageableRequest);

	Restaurant findTopByLicenseKeyAndIsDeleted(String liceseKey, boolean b);
	
}

package optifoodmanagement.io.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import optifoodmanagement.io.entity.MacAddress;

@Repository
public interface MacAddressRepository extends PagingAndSortingRepository<MacAddress, Integer> {
	
	MacAddress findByMacAddressIdAndIsDeleted(Integer macAddressId, boolean b);
	
	Page<MacAddress> findByIsDeleted(boolean b, Pageable pageableRequest);
	
	Page<MacAddress> findByIsDeletedAndMacAddressContaining(boolean b, String searchKey, Pageable pageableRequest);
	
	Page<MacAddress> findByRestaurantIdAndIsDeleted(boolean b, Integer restaurantId, Pageable pageableRequest);
	
	Page<MacAddress> findByRestaurantIdAndMacAddressAndIsDeleted(Integer restaurantId, String searchKey, boolean b,
	        Pageable pageableRequest);
	
}

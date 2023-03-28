package optifoodmanagement.io.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import optifoodmanagement.io.entity.Chain;

@Repository
public interface ChainRepository extends PagingAndSortingRepository<Chain, Integer> {
	
	Chain findByChainIdAndIsDeleted(Integer chainId, boolean b);
	
	Page<Chain> findByIsDeleted(boolean b, Pageable pageableRequest);
	
	Page<Chain> findByIsDeletedAndChainNameContaining(boolean b, String searchKey, Pageable pageableRequest);
	
}

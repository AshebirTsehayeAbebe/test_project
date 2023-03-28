package optifoodmanagement.io.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import optifoodmanagement.io.entity.TenantEntity;

@Repository
public interface TenantRepository extends PagingAndSortingRepository<TenantEntity, Long> {

	TenantEntity findByTenantIdAndIsDeleted(long l, boolean b);

	Page<TenantEntity> findByIsDeleted(boolean b, Pageable pageableRequest);

	Page<TenantEntity> findByIsDeletedAndNameContaining(boolean b, String searchKey, Pageable pageableRequest);

	TenantEntity findTopByLicenseKeyAndIsDeleted(String liceseKey, boolean b);
	
	List<TenantEntity> findByIsDeleted(boolean b);
	
	TenantEntity findByLicenseKeyAndIsDeleted(String licenseKey, boolean b);
	
}
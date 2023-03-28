package optifoodmanagement.io.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import optifoodmanagement.io.entity.Privilege;

public interface PrivilegeRepository extends PagingAndSortingRepository<Privilege, Integer> {


	Privilege findByPrivilegeIdAndIsDeleted(Integer privilegeId, boolean b);

	Page<Privilege> findByIsDeleted(boolean b, Pageable pageableRequest);

	List<Privilege> findByIsDeleted(boolean b);



	List<Privilege> findByPrivilegeUrlAndMethodAndIsDeleted(String privilegeUrl, String method, boolean b);



	

	List<Privilege> findByPrivilegeName(String privilegeName);

	List<Privilege> findByPrivilegeNameContainingOrPrivilegeUrlContainingOrPrivilegeDescriptionContainingAndIsDeleted(
			String searchKey, String searchKey2, String searchKey3, boolean b);



}

package optifoodmanagement.io.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import optifoodmanagement.io.entity.UserRoles;

public interface UserRolesRepository extends PagingAndSortingRepository<UserRoles, Long> {

	UserRoles findByUserRoleId(Long userRoleId);


	UserRoles findByUserRoleIdAndIsDeleted(Long userRoleId, boolean b);

	List<UserRoles> findByUserId(Long id);

	List<UserRoles> findByRoleId(Long id);


	List<UserRoles> findByUserIdAndIsDeleted(long id, boolean b);


	UserRoles findByUserIdAndRoleId(Long userId, Long roleId);
	
	

}

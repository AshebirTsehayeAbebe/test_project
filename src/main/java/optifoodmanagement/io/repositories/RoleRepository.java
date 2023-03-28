package optifoodmanagement.io.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import optifoodmanagement.io.entity.Role;
import optifoodmanagement.shared.RoleName;

import java.util.Optional;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
//    Optional<RoleEntity> findByName(RoleName roleUser);

	Role findByRoleName(String userRole);
	Role findByRoleNameAndIsDeleted(String userType, boolean b);
	Role findByRoleIdAndIsDeleted(Long roleId, boolean b);
	Page<Role> findByIsDeleted(boolean b, Pageable pageableRequest);
	Page<Role> findByIsDeletedAndRoleNameContaining(boolean b, String searchKey, Pageable pageableRequest);

}

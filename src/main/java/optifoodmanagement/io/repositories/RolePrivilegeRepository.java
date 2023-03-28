package optifoodmanagement.io.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import optifoodmanagement.io.entity.RolePrivilege;

public interface RolePrivilegeRepository extends PagingAndSortingRepository<RolePrivilege, Long>{


	RolePrivilege findByRolePrivilegeIdAndIsDeleted(Long rolePrivilegeId, boolean b);

	List<RolePrivilege> findByIsDeleted(boolean b);

	RolePrivilege findByRoleIdAndPrivilegeIdAndIsDeleted(Long roleId, Integer privilegeId, boolean b);

	RolePrivilege findByRolePrivilegeId(Long rolePrivilegeId);

	List<RolePrivilege> findByRoleIdAndIsDeleted(Long id, boolean b);

	List<RolePrivilege> findByRoleIdAndIsDeleted(Integer roleId, boolean b);

	List<RolePrivilege> findByPrivilegeIdAndIsDeleted(Integer privilegeId, boolean b);

	List<RolePrivilege> findByRoleIdAndIsPrivilagedAndIsDeleted(Long roleId, boolean b, boolean c);
	List<RolePrivilege> findByPrivilegeIdAndRoleIdAndIsDeleted(Integer privilegeId, Long roleId, boolean b);

	RolePrivilege findByRoleIdAndPrivilegeId(Long roleId, Integer privilegeId);
	
	
}

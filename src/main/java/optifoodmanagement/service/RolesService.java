package optifoodmanagement.service;

import java.util.List;

import optifoodmanagement.ui.model.request.RoleRequestModel;
import optifoodmanagement.ui.model.request.UserRoleRequestModel;
import optifoodmanagement.ui.model.response.RoleResponseModel;

public interface RolesService {
	
	RoleResponseModel saveRoles(RoleRequestModel roleDetails);
	
	RoleResponseModel updateRole(long roleId, RoleRequestModel roleDetails);
	
	String deleteRole(Long roleId);
	
	String saveUserRole(UserRoleRequestModel userRoleDetail);
	
	String updateUserRole(UserRoleRequestModel userRoleDetail, Long userRoleId);
	
	String deleteUserRole(Long userRoleId);
	
	List<RoleResponseModel> getRoles(String searchKey, int page, int limit);
	
	RoleResponseModel getRole(Long roleId);
	
	
}

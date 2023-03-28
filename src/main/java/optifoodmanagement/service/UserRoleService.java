package optifoodmanagement.service;

import optifoodmanagement.ui.model.request.UserRoleRequestModel;
import optifoodmanagement.ui.model.response.UserRoleResponseModel;

public interface UserRoleService {
	
	String saveUserRole(UserRoleRequestModel userRoleDetail);
	
	String updateUserRole(UserRoleRequestModel userRoleDetail, Long userRoleId);
	
	String deleteUserRole(Long userRoleId);
	
	UserRoleResponseModel getAssignedRoles(Long userId);
	
}

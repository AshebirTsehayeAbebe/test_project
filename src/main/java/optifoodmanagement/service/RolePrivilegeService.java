package optifoodmanagement.service;

import java.util.List;

import optifoodmanagement.ui.model.request.RolePrivilegeRequestModel;
import optifoodmanagement.ui.model.response.RolePrivilegesResponseModel;

public interface RolePrivilegeService {

	String saveRolePrivilegeData(RolePrivilegeRequestModel rolePrivilegeDetail);
	
	String updateRolePrivilegeData(RolePrivilegeRequestModel rolePrivilegeDetail, Long rolePrivilegeId);
	
	String deleteRolePrivilageData(Long rolePrivilegeId);
	
	List<RolePrivilegesResponseModel> getRolePrivileges(Long roleId);
	
}

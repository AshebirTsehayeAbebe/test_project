package optifoodmanagement.service;

import java.util.List;

import optifoodmanagement.ui.model.request.PrivilegeRequestModel;
import optifoodmanagement.ui.model.response.PrivilegeResponseModel;
public interface PrivilegeService {
	
	PrivilegeResponseModel savePrivilege(PrivilegeRequestModel privilegeDetail);
	
	PrivilegeResponseModel getPrivilege(Integer privilegeId);
	
	PrivilegeResponseModel updatePrivilege(PrivilegeRequestModel privilegeDetail, Integer privilegeId);
	
	List<PrivilegeResponseModel> getAllPrivileges(int page, int limit, String searchKey, Long roleId);
	
	String deletePrivilege(Integer privilegeId);
	
}

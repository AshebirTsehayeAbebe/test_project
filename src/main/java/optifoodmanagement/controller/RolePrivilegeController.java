package optifoodmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import optifoodmanagement.service.RolePrivilegeService;
import optifoodmanagement.ui.model.request.RolePrivilegeRequestModel;
import optifoodmanagement.ui.model.response.RolePrivilegesResponseModel;

@RestController
@RequestMapping("/api/role-privilege")
public class RolePrivilegeController {
	
	@Autowired
	RolePrivilegeService rolePrivilegeService;
	
	@PostMapping
	@ApiOperation(value = "Assign privilege to role for given role id", notes = "Provide role and privilege detail to assing privilege to roles", response = String.class)
	public String saveRolePrivilegeData(@RequestBody RolePrivilegeRequestModel rolePrivilegeDetail) {
		String returnValue = rolePrivilegeService.saveRolePrivilegeData(rolePrivilegeDetail);
		return returnValue;
	}
	
	@PutMapping(path = "/{rolePrivilegeId}")
	@ApiOperation(value = "Update role privileges for given role id", notes = "Provide role and privilege detail to update role privilege for a given role id", response = String.class)
	public String updateRolePrivilegeData(@RequestBody RolePrivilegeRequestModel rolePrivilegeDetail,
	        @PathVariable Long rolePrivilegeId) {
		String returnValue = rolePrivilegeService.updateRolePrivilegeData(rolePrivilegeDetail, rolePrivilegeId);
		return returnValue;
	}
	
	@DeleteMapping(path = "/{rolePrivilegeId}")
	@ApiOperation(value = "Delete role privileges for a given role id", notes = "Provide role id a given privilege id", response = String.class)
	public String deleteRolePrivilegeData(@PathVariable Long rolePrivilegeId) {
		String returnValue = rolePrivilegeService.deleteRolePrivilageData(rolePrivilegeId);
		
		return returnValue;
	}
	
	@GetMapping(path = "/{roleId}")
	@ApiOperation(value = "Get role privileges for a given role id", notes = "Provide role id to a get role privileges", response = String.class)
	public List<RolePrivilegesResponseModel> getRolePrivileges(@PathVariable Long roleId) {
		
		List<RolePrivilegesResponseModel> returnValue = rolePrivilegeService.getRolePrivileges(roleId);
		return returnValue;
	}
}

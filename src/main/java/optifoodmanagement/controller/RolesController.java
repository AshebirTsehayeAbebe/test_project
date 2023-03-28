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
import optifoodmanagement.service.RolesService;
import optifoodmanagement.ui.model.request.RoleRequestModel;
import optifoodmanagement.ui.model.request.UserRoleRequestModel;
import optifoodmanagement.ui.model.response.RoleResponseModel;

@RestController
@RequestMapping("/api/roles")
public class RolesController {
	
	@Autowired
	RolesService rolesService;
	
	@PostMapping
	@ApiOperation(value = "Saves role detail to database", notes = "Provide role detail to save", response = RoleResponseModel.class)
	public RoleResponseModel saveRoles(@RequestBody RoleRequestModel rolesDetail) {
		
		RoleResponseModel returnValue = rolesService.saveRoles(rolesDetail);
		
		return returnValue;
		
	}
	
	@PutMapping(path = "/{roleId}")
	@ApiOperation(value = "Updates role detail based on the given id", notes = "Provide role detail with id to update the record", response = RoleResponseModel.class)
	public RoleResponseModel updateRoles(@RequestBody RoleRequestModel roleDetails, @PathVariable Long roleId) {
		RoleResponseModel returnValue = rolesService.updateRole(roleId, roleDetails);
		return returnValue;
	}
	
	@GetMapping(path = "/{roleId}")
	@ApiOperation(value = "Finds role by id", notes = "Provide an id to look up specific role from the roles", response = RoleResponseModel.class)
	public RoleResponseModel getRole(@PathVariable Long roleId) {
		RoleResponseModel returnValue = rolesService.getRole(roleId);
		return returnValue;
	}
	
	@GetMapping
	@ApiOperation(value = "Fetches roles for given limit per page", notes = "Provide limit and page to get required count of roles per page", response = RoleResponseModel.class)
	public List<RoleResponseModel> getRoles(@RequestParam(value = "searchKey", defaultValue = "") String searchKey,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "limit", defaultValue = "100") int limit) {
		List<RoleResponseModel> returnValue = rolesService.getRoles(searchKey, page, limit);
		return returnValue;
	}
	
	@DeleteMapping(path = "/{roleId}")
	@ApiOperation(value = "Deletes role for given id", notes = "Provide an id to delete specific role from database", response = String.class)
	public String deleteRole(@PathVariable Long roleId) {
		String returnValue = rolesService.deleteRole(roleId);
		return returnValue;
	}
	
//	@PostMapping(path = "/user-role")
//	@ApiOperation(value = "Assign role to user for given user id", notes = "Provide user and role detail to assing role to users", response = String.class)
//	public String saveUserRole(@RequestBody UserRoleRequestModel userRoleDetail) {
//		String returnValue = rolesService.saveUserRole(userRoleDetail);
//		return returnValue;
//		
//	}
	
//	@PutMapping(path = "/user-role/{userRoleId}")
//	@ApiOperation(value = "Update user roles for given user id", notes = "Provide user and role detail to update user role for a given user id", response = String.class)
//	public String updateUserRole(@RequestBody UserRoleRequestModel userRoleDetail, @PathVariable Long userRoleId) {
//		String returnValue = rolesService.updateUserRole(userRoleDetail, userRoleId);
//		return returnValue;
//	}
//	
//
//	@DeleteMapping(path = "/user-role/{userRoleId}")
//	@ApiOperation(value = "Delete user roles for a given user id", notes = "Provide user id a given user id", response = String.class)
//	public String deleteUserRole(@PathVariable Long userRoleId) {
//		String returnValue = rolesService.deleteUserRole(userRoleId);
//		
//		return returnValue;
//	}
	
}

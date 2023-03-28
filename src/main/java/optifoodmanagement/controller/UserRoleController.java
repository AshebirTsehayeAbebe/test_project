package optifoodmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import optifoodmanagement.service.UserRoleService;
import optifoodmanagement.ui.model.request.UserRoleRequestModel;
import optifoodmanagement.ui.model.response.UserRoleResponseModel;

@RestController
@RequestMapping("/api/user-role")
public class UserRoleController {
	
	@Autowired
	UserRoleService userRoleService;
	
	@PostMapping
	@ApiOperation(value = "Assigns roles to user", notes = "Provide role ids and user id to save", response = String.class)
	public String saveUserRole(@RequestBody UserRoleRequestModel userRoleDetail) {
		String returnValue = userRoleService.saveUserRole(userRoleDetail);
		return returnValue;
		
	}
	
	@PutMapping(path = "/{userRoleId}")
	@ApiOperation(value = "Updates roles of user", notes = "Provide user role id and user role detail to update roles of a user", response = String.class)
	public String updateUserRole(@RequestBody UserRoleRequestModel userRoleDetail, @PathVariable Long userRoleId) {
		String returnValue = userRoleService.updateUserRole(userRoleDetail, userRoleId);
		return returnValue;
	}
	
	@DeleteMapping(path = "/{userRoleId}")
	@ApiOperation(value = "Deletes role of user", notes = "Provide user role id to delete role of a user", response = String.class)
	public String deleteUserRole(@PathVariable Long userRoleId) {
		String returnValue = userRoleService.deleteUserRole(userRoleId);
		
		return returnValue;
	}
	
	@GetMapping(path = "/assigned-roles/{userId}")
	@ApiOperation(value = "Fetches roles of a user", notes = "Provide user id to fetch roles of a user", response = String.class)
	public UserRoleResponseModel getAssignedRoles(@PathVariable Long userId) {
		UserRoleResponseModel returnValue = userRoleService.getAssignedRoles(userId);
		
		return returnValue;
	}
}

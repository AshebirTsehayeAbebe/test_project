package optifoodmanagement.controller;
//package com.api.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.api.service.ManageDepartmentUsersService;
//import com.api.ui.model.request.DepartmentUsersRequestModel;
//import com.api.ui.model.response.DepartmentUsersResponseModel;
//
//public class AssignRoleConroller {
//
//	@Autowired
//	ManageDepartmentUsersService manageDepartmentUsersService;
//	
//	@PostMapping
//	public DepartmentUsersResponseModel assignDepartment(@RequestBody DepartmentUsersRequestModel requestDetail) {
//		
//		DepartmentUsersResponseModel returnValue = manageDepartmentUsersService.assignDepartment(requestDetail);		
//		return returnValue;
//		
//	}
//	
//	@PutMapping(path = "/{departmentUserId}")
//	public String updateDepartmentUser(@RequestBody DepartmentUsersRequestModel requestDetail, @PathVariable Long departmentUserId) {
//		
//		String returnValue = manageDepartmentUsersService.updateDepartmentUser(departmentUserId, requestDetail);		
//		return returnValue;
//	}
//	
//	@GetMapping(path = "/{departmentUserId}")
//	public DepartmentUsersResponseModel getDepartmentUser(@PathVariable Long departmentUserId) {
//		
//		DepartmentUsersResponseModel returnValue = manageDepartmentUsersService.getDepartmentUser(departmentUserId);
//		return returnValue;
//	}
//	
//	@GetMapping
//	public List<DepartmentUsersResponseModel> getDepartmentUsers(@RequestParam(value="userId", defaultValue = "") String userId, @RequestParam(value="departmentId", defaultValue = "0") Integer departmentId, @RequestParam(value="page", defaultValue = "1") int page,
//			   @RequestParam(value="limit", defaultValue = "1000") int limit) {
//		
//		List<DepartmentUsersResponseModel> returnValue = manageDepartmentUsersService.getDepartmentUsers(userId, departmentId, page, limit);
//		
//		return returnValue;
//	}
//	
//	@DeleteMapping(path = "/{departmentUserId}")
//	public String deleteDepartmentUser(@PathVariable Long departmentUserId) {
//		
//		String returnValue = manageDepartmentUsersService.deleteDepartmentUser(departmentUserId);
//	    return returnValue;
//	}
//}

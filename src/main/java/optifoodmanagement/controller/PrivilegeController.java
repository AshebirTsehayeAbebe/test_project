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
import optifoodmanagement.service.PrivilegeService;
import optifoodmanagement.ui.model.request.PrivilegeRequestModel;
import optifoodmanagement.ui.model.request.RolePrivilegeRequestModel;
import optifoodmanagement.ui.model.response.PrivilegeResponseModel;
import optifoodmanagement.ui.model.response.RolePrivilegesResponseModel;

@RestController
@RequestMapping("/api/privileges")
public class PrivilegeController {
	
	@Autowired
	PrivilegeService privilegeService;
	
	@PostMapping
	@ApiOperation(value = "Saves privilege detail to database", notes = "Provide privilege detail to save", response = PrivilegeResponseModel.class)
	public PrivilegeResponseModel savePrivilege(@RequestBody PrivilegeRequestModel privilegeDetail) {
		
		PrivilegeResponseModel returnValue = privilegeService.savePrivilege(privilegeDetail);
		
		return returnValue;
	}
	
	@GetMapping(path = "/{privilegeId}")
	@ApiOperation(value = "Updates privilege detail based on the given id", notes = "Provide privilege detail with id to update the record", response = PrivilegeResponseModel.class)
	public PrivilegeResponseModel getPrivilege(@PathVariable Integer privilegeId) {
		PrivilegeResponseModel returnValue = privilegeService.getPrivilege(privilegeId);
		return returnValue;
	}
	
	@PutMapping(path = "/{privilegeId}")
	@ApiOperation(value = "Finds privilege by id", notes = "Provide an id to look up specific privilege from the privileges", response = PrivilegeResponseModel.class)
	public PrivilegeResponseModel updatePrivilege(@RequestBody PrivilegeRequestModel privilegeDetail,
	        @PathVariable Integer privilegeId) {
		
		PrivilegeResponseModel returnValue = privilegeService.updatePrivilege(privilegeDetail, privilegeId);
		return returnValue;
	}
	
	@GetMapping
	@ApiOperation(value = "Fetches privileges for given limit per page", notes = "Provide limit and page to get required count of privilege per page", response = PrivilegeResponseModel.class)
	public List<PrivilegeResponseModel> getAllPrivileges(@RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "limit", defaultValue = "25") int limit,
	        @RequestParam(value = "roleId", defaultValue = "0") Long roleId,
	        @RequestParam(value = "searchKey", defaultValue = "") String searchKey) {
		List<PrivilegeResponseModel> returnValue = privilegeService.getAllPrivileges(page, limit, searchKey, roleId);
		return returnValue;
	}
	
	@DeleteMapping(path = "/{privilegeId}")
	@ApiOperation(value = "Deletes privilege for given id", notes = "Provide an id to delete specific privilege from database", response = String.class)
	public String deletePrivilege(@PathVariable Integer privilegeId) {
		String returnValue = privilegeService.deletePrivilege(privilegeId);
		
		return returnValue;
	}
	
}

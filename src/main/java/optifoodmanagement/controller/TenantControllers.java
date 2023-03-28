package optifoodmanagement.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import optifoodmanagement.service.TenantService;
import optifoodmanagement.ui.model.request.TenantRequestModel;
import optifoodmanagement.ui.model.response.TenantResponseModel;


@RestController
@RequestMapping("/api/tenant")
public class TenantControllers {
	
	@Autowired
	TenantService tenantService;
	
	@PostMapping
	@ApiOperation(value = "Saves tenant detail to database", notes = "Provide tenant detail to save")
	public TenantResponseModel saveTenant(@RequestHeader(value = "header") @RequestBody TenantRequestModel tenantDetails)
	        throws AddressException, MessagingException, IOException {
		
		TenantResponseModel returnValue = tenantService.saveTenant(tenantDetails);
		
		return returnValue;
	}
	
	@GetMapping(path="/{tenantId}")
	@ApiOperation(value = "Finds tenant by tenant id", notes = "Provide an tenant id to look up specific tenant from the tenants", response = TenantResponseModel.class)
	public TenantResponseModel getTenant(@PathVariable Integer tenantId) {
		TenantResponseModel returnValue = tenantService.getTenant(tenantId);
		return returnValue;
	}

	@GetMapping
	@ApiOperation(value = "Fetches tenants for given limit per page", notes = "Provide limit and page to get required count of tenants per page", response = TenantResponseModel.class)
	public List<TenantResponseModel> getTenants(@RequestParam(value = "searchKey", defaultValue = "") String searchKey,
	        @RequestParam(value = "page", defaultValue = "1") int page,
								   @RequestParam(value="limit", defaultValue = "25") int limit){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.print("========================="+auth.getAuthorities()+",,,,,====================");

		List<TenantResponseModel> returnValue = tenantService.getTenants(page, limit, searchKey);
		return returnValue;
	}
	
	@PutMapping(path="/{id}")
	@ApiOperation(value = "Updates tenant detail based on the given tenant id", notes = "Provide tenant detail with tenant id to update the record", response = TenantResponseModel.class)
	public TenantResponseModel updateTenant(@PathVariable Integer id, @RequestBody TenantRequestModel requestDetail) {
		
		TenantResponseModel returnValue = tenantService.updateTenant(id, requestDetail);
		
		return returnValue;
	}
	
	@DeleteMapping(path = "/{tenantId}")
	@ApiOperation(value = "Deletes tenant by tenant id", notes = "Provide tenant id to delete tenant from database", response = String.class)
	public String deleteTenant(@PathVariable Integer tenantId) {
		String returnValue = tenantService.deleteTenant(tenantId);
		return returnValue;
	}

	
}

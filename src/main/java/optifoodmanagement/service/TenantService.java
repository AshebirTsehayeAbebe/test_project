package optifoodmanagement.service;
import java.util.List;

import optifoodmanagement.ui.model.request.TenantRequestModel;
import optifoodmanagement.ui.model.response.TenantResponseModel;

public interface TenantService{
	
	TenantResponseModel saveTenant(TenantRequestModel tenant);
	
	TenantResponseModel getTenant(Integer tenantId);
	
	TenantResponseModel updateTenant(Integer tenantId, TenantRequestModel tenantDetails);
	
	List<TenantResponseModel> getTenants(int page, int limit, String searchKey);
	
	String deleteTenant(Integer tenantId);
	
}

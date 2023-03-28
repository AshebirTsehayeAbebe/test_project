package optifoodmanagement.service.Impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import optifoodmanagement.exception.AppException;
import optifoodmanagement.io.entity.TenantEntity;
import optifoodmanagement.io.repositories.TenantRepository;
import optifoodmanagement.service.TenantService;
import optifoodmanagement.shared.GenerateRandomStrings;
import optifoodmanagement.ui.model.request.TenantRequestModel;
import optifoodmanagement.ui.model.response.TenantResponseModel;

@Service
public class TenantServiceImpl implements TenantService {
	
	@Autowired
	TenantRepository tenantRepository;
	
	@Autowired
	GenerateRandomStrings generateRandomString;
	
	@Override
	public TenantResponseModel saveTenant(TenantRequestModel tenant) {
		TenantResponseModel returnValue = new TenantResponseModel();
		TenantEntity tenantEntity = new TenantEntity();
		BeanUtils.copyProperties(tenant, tenantEntity);
		String licenseKey = "";
		for (int i = 0; i < 100; i++) {
			licenseKey = generateRandomString.generateUserId(24);
			TenantEntity checkTenant = tenantRepository.findByLicenseKeyAndIsDeleted(licenseKey, false);
			if (checkTenant == null)
				break;
			
		}
		tenantEntity.setLicenseKey(licenseKey);
		TenantEntity savedDetail = tenantRepository.save(tenantEntity);
		if (savedDetail != null) {
			try {
				createDB(savedDetail.getName());
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		BeanUtils.copyProperties(savedDetail, returnValue);
		
		return returnValue;
	}
	
	@Override
	public TenantResponseModel getTenant(Integer tenantId) {
		TenantResponseModel returnValue = new TenantResponseModel();
		TenantEntity tenantEntity = tenantRepository.findByTenantIdAndIsDeleted(tenantId, false);
		if (tenantEntity == null)
			throw new AppException("No tenant with this id.");
		BeanUtils.copyProperties(tenantEntity, returnValue);
		return returnValue;
	}
	
	@Override
	public List<TenantResponseModel> getTenants(int page, int limit, String searchKey) {
		
		List<TenantResponseModel> returnValue = new ArrayList<>();
		
		if (page > 0)
			page = page - 1;
		Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("tenantId").descending());
		Page<TenantEntity> tenantPage = null;
		if ("".equals(searchKey)) 
			tenantPage = tenantRepository.findByIsDeleted(false, pageableRequest);
		else {
			tenantPage = tenantRepository.findByIsDeletedAndNameContaining(false, searchKey, pageableRequest);
			
		}
		
		List<TenantEntity> tenants = tenantPage.getContent();
		int totalPages = tenantPage.getTotalPages();
		for (TenantEntity tenant : tenants) {
			TenantResponseModel tenantResponseModel = new TenantResponseModel();
			BeanUtils.copyProperties(tenant, tenantResponseModel);
			if (returnValue.size() == 0) {
				tenantResponseModel.setTotalPage(totalPages);
			}
			
			returnValue.add(tenantResponseModel);
		}
		return returnValue;
	}
	
	@Override
	public TenantResponseModel updateTenant(Integer tenantId, TenantRequestModel tenantDetails) {
		
		TenantResponseModel returnValue = new TenantResponseModel();
		TenantEntity tenantEntity = tenantRepository.findByTenantIdAndIsDeleted(tenantId, false);
		if (tenantEntity == null)
			throw new AppException("No tenant with this id!");
		BeanUtils.copyProperties(tenantDetails, tenantEntity);
		TenantEntity savedDetail = tenantRepository.save(tenantEntity);
		BeanUtils.copyProperties(savedDetail, returnValue);
		return returnValue;
	}
	
	@Override
	public String deleteTenant(Integer tenantId) {
		
		TenantEntity tenantEntity = tenantRepository.findByTenantIdAndIsDeleted(tenantId, false);
		if (tenantEntity == null)
			throw new AppException("No tenant with this id!");
		tenantEntity.setDeleted(true);
		TenantEntity savedDetail = tenantRepository.save(tenantEntity);
		if (savedDetail == null)
			throw new AppException("Something goes wrong!");
		return "Deleted successfully!";
	}
	
	@Autowired
	EntityManager entityManager;
	
	public String createDB(String db) throws SQLException {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tenant1", "openpg", "openpgpwd");
			Statement smt = con.createStatement();
			smt.executeUpdate("CREATE DATABASE " + db);
			//			Flyway flyway = Flyway.configure().dataSource(dataSource).target(MigrationVersion.LATEST).load();
			//			flyway.migrate();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "DB Created!";
	}
	
}

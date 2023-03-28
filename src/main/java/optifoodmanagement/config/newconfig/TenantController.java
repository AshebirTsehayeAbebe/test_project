package optifoodmanagement.config.newconfig;
//package ownerdashboard.config.newconfig;
//
//import java.sql.SQLException;
//import java.util.Map;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RestController
//@RequestMapping("/tenants")
//public class TenantController {
//	
//	private final MultiTenantManager tenantManager;
//	
//	public TenantController(MultiTenantManager tenantManager) {
//		this.tenantManager = tenantManager;
//	}
//	
//	/**
//	 * Get list of all tenants in the local storage
//	 */
//	@GetMapping
//	public ResponseEntity<?> getAll() {
//		return ResponseEntity.ok(tenantManager.getTenantList());
//	}
//	
//	/**
//	 * Add the new tenant on the fly
//	 *
//	 * @param dbProperty Map with tenantId and related datasource properties
//	 */
//	@PostMapping
//	public ResponseEntity<?> add(@RequestBody Map<String, String> dbProperty) {
//		
//		String tenantId = dbProperty.get("tenantId");
//		String url = dbProperty.get("url");
//		String username = dbProperty.get("username");
//		String password = dbProperty.get("password");
//		
//		if (tenantId == null || url == null || username == null || password == null) {
//			throw new InvalidDbPropertiesException();
//		}
//		
//		try {
//			tenantManager.addTenant(tenantId, url, username, password);
//			return ResponseEntity.ok(dbProperty);
//		}
//		catch (SQLException e) {
//			throw new LoadDataSourceException(e);
//		}
//	}
//	
//	@PostMapping("/add-tenant")
//	public ResponseEntity<?> addTenant() {
//		
//		String tenantId = "tenant2";
//		String url = "jdbc:postgresql://localhost:5432/tenant1";
//		String username = "openpg";
//		String password = "openpgpwd";
//		
//		if (tenantId == null || url == null || username == null || password == null) {
//			throw new InvalidDbPropertiesException();
//		}
//		
//		try {
//			tenantManager.addTenant(tenantId, url, username, password);
//			return ResponseEntity.ok("Working");
//		}
//		catch (SQLException e) {
//			throw new LoadDataSourceException(e);
//		}
//	}
//}

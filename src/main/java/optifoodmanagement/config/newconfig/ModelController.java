package optifoodmanagement.config.newconfig;
//package ownerdashboard.config.newconfig;
//
//import java.sql.SQLException;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RestController
//@RequestMapping("/models")
//public class ModelController {
//	
//	private static final String MSG_INVALID_TENANT_ID = "[!] DataSource not found for given tenant Id '{}'!";
//	
//	private static final String MSG_INVALID_DB_PROPERTIES_ID = "[!] DataSource properties related to the given tenant ('{}') is invalid!";
//	
//	private static final String MSG_RESOLVING_TENANT_ID = "[!] Could not resolve tenant ID '{}'!";
//	
//	private final ModelService modelService;
//	
//	private final MultiTenantManager tenantManager;
//	
//	public ModelController(ModelService modelService, MultiTenantManager tenantManager) {
//		this.modelService = modelService;
//		this.tenantManager = tenantManager;
//	}
//	
//	@GetMapping
//	public ResponseEntity<?> getAll(@RequestHeader(value = "X-TenantID") String tenantId) {
//		setTenant(tenantId);
//		return ResponseEntity.ok(modelService.findAll());
//	}
//	
//	@PostMapping
//	public ResponseEntity<?> create(@RequestHeader("X-TenantId") String tenantId) {
//		setTenant(tenantId);
//		return ResponseEntity.ok(modelService.save(new Model(tenantId)));
//	}
//	
//	private void setTenant(String tenantId) {
//		try {
//			tenantManager.setCurrentTenant(tenantId);
//		}
//		catch (SQLException e) {
//			throw new InvalidDbPropertiesException();
//		}
//		catch (TenantNotFoundException e) {
//			throw new InvalidTenantIdExeption();
//		}
//		catch (TenantResolvingException e) {
//			throw new InvalidTenantIdExeption();
//		}
//	}
//}

package optifoodmanagement.config;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import optifoodmanagement.io.entity.Privilege;
import optifoodmanagement.io.entity.RolePrivilege;
import optifoodmanagement.io.entity.UserRoles;
import optifoodmanagement.io.repositories.PrivilegeRepository;
import optifoodmanagement.io.repositories.RolePrivilegeRepository;
import optifoodmanagement.io.repositories.RoleRepository;
import optifoodmanagement.io.repositories.UserRepository;
import optifoodmanagement.io.repositories.UserRolesRepository;
import optifoodmanagement.security.JwtTokenProvider;
import optifoodmanagement.ui.model.response.AuthorizeApiResponseModel;

@RestController
@RequestMapping("/microservice-authorization")
public class MicroserviceAuthorizationController {
	
	@Value("${app.jwtSecret}")
	private String SECRET_KEY;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	PrivilegeRepository privilegeRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	RolePrivilegeRepository rolePrivilegeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRolesRepository userRolesRepository;
	
	@GetMapping
	public ResponseEntity<AuthorizeApiResponseModel> authorizeApiRequest(
	        @RequestParam(value = "token", defaultValue = "") String token,
	        @RequestParam(value = "method", defaultValue = "") String method,
	        @RequestParam(value = "uri", defaultValue = "") String uri)
	        throws AddressException, MessagingException, IOException {
		
		boolean hasAnError = false;
		AuthorizeApiResponseModel returnValue = new AuthorizeApiResponseModel();
		List<Privilege> privilegeEntities = new ArrayList<>();
		List<Privilege> privilegeEntities1 = privilegeRepository.findByPrivilegeUrlAndMethodAndIsDeleted(uri, method,
		    false);
		
		if (true) {
			/////////////////For Open APIs
			if (true) {
				returnValue.setMessage("Granted!!");
				returnValue.setPath(uri);
				returnValue.setStatus(HttpStatus.ACCEPTED);
				returnValue.setStatusCode(HttpStatus.ACCEPTED.value());
				returnValue.setTimestamp(Instant.now());
			}
			
			///////////////////For Authorized APIs
			else {
				if (token.equals("") || token == null) {
					hasAnError = true;
					returnValue.setMessage("Unauthorized!!");
					returnValue.setPath(uri);
					returnValue.setStatus(HttpStatus.UNAUTHORIZED);
					returnValue.setStatusCode(HttpStatus.UNAUTHORIZED.value());
					returnValue.setTimestamp(Instant.now());
				}
				
				if (!hasAnError) {
					
					try {
						isTokenExpired(token.split(" ")[1]);
					}
					catch (Exception ex) {
						if (ex.toString().contains("io.jsonwebtoken.ExpiredJwtException")) {
							hasAnError = true;
							returnValue.setMessage("Token has been expired!!");
							returnValue.setPath(uri);
							returnValue.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
							returnValue.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
							returnValue.setTimestamp(Instant.now());
						} else {
							System.out.print(ex);
						}
					}
				}
				
				if (token != null && !"".equals(token) && !hasAnError) {
					token = token.split(" ")[1];
					
					Claims claims = Jwts.parser().setSigningKey("JWTSuperSecretKey").parseClaimsJws(token).getBody();
					
					Long userId = Long.parseLong(claims.getSubject());
					userRepository.findById((long) 23);
					if (userId != null) {
						boolean granted = false;
						
						List<UserRoles> userRolesEntities = userRolesRepository.findByUserIdAndIsDeleted(userId, false);
						for (UserRoles userRolesEntity : userRolesEntities) {
							List<RolePrivilege> rolePrivilegeEntities = rolePrivilegeRepository
							        .findByRoleIdAndIsDeleted(userRolesEntity.getRoleId(), false);
							for (RolePrivilege rolePrivilegeEntity : rolePrivilegeEntities) {
								Privilege privilegeEntity = privilegeRepository
								        .findByPrivilegeIdAndIsDeleted(rolePrivilegeEntity.getPrivilegeId(), false);
								
								if (privilegeEntity != null) {
									privilegeEntities.add(privilegeEntity);
									if (privilegeEntities1.get(0).getPrivilegeId() == privilegeEntity.getPrivilegeId()) {
										granted = true;
									}
								}
							}
						}
						
						if (granted) {
							hasAnError = true;
							returnValue.setMessage("You are not granted to this privilege");
							returnValue.setPath(uri);
							returnValue.setStatus(HttpStatus.FORBIDDEN);
							returnValue.setStatusCode(HttpStatus.FORBIDDEN.value());
							returnValue.setTimestamp(Instant.now());
						}
						
						else {
							returnValue.setMessage("Granted");
							returnValue.setPath(uri);
							returnValue.setStatus(HttpStatus.ACCEPTED);
							returnValue.setStatusCode(HttpStatus.ACCEPTED.value());
							returnValue.setTimestamp(Instant.now());
						}
					}
					
					//					Stream filteredList = privilegeEntities.stream()
					//					        .filter(item -> item.getPrivilegeId() == privilegeEntities1.get(0).getPrivilegeId());
					//					
					//					if (filteredList.toArray().length == 0) {
					//						hasAnError = true;
					//						returnValue.setMessage("You are not granted to this privilege");
					//						returnValue.setPath(uri);
					//						returnValue.setStatus(HttpStatus.FORBIDDEN);
					//						returnValue.setStatusCode(HttpStatus.FORBIDDEN.value());
					//						returnValue.setTimestamp(Instant.now());
					//					}
					//					
					//					else {
					//						returnValue.setMessage("Granted");
					//						returnValue.setPath(uri);
					//						returnValue.setStatus(HttpStatus.ACCEPTED);
					//						returnValue.setStatusCode(HttpStatus.ACCEPTED.value());
					//						returnValue.setTimestamp(Instant.now());
					//					}
				}
				
			}
		} else {
			hasAnError = true;
			returnValue.setMessage("API is not found!!!");
			returnValue.setPath(uri);
			returnValue.setStatus(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
			returnValue.setStatusCode(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value());
			returnValue.setTimestamp(Instant.now());
			System.out.print("Nowwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"
			        + HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value() + "Nowwwwwwwwwwwww");
		}
		return new ResponseEntity<AuthorizeApiResponseModel>(returnValue, HttpStatus.ACCEPTED);
	}
	
	// Get Expiration and compare it with new Date()
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claim = extractAllClaims(token);
		return claimResolver.apply(claim);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey("JWTSuperSecretKey").parseClaimsJws(token).getBody();
	}
	
}

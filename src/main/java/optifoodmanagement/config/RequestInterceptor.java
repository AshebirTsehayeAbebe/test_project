package optifoodmanagement.config;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
	
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
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	        throws ServletException, IOException {
		if (true)
			return true;
			   boolean returnValue = false;
			   boolean hasAnError = false;
			   String uri = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
			   String method = request.getMethod();
			   String token = "";
		
			   if(request.getHeader("Authorization") != null)
				   token = request.getHeader("Authorization");
			   
		if (request.getRequestURI().contentEquals("/microservice-authorization")
		        || request.getRequestURI().contentEquals("/handle-exception")
		        || request.getRequestURI().contentEquals("/auth/login")
		        || request.getRequestURI().contains("swagger")
		        || request.getRequestURI().equalsIgnoreCase("/**")
		        || request.getRequestURI().equalsIgnoreCase("/swagger-ui.html/**")
		        || request.getRequestURI().equalsIgnoreCase("/swagger-ui.html#/**")) {
			return true;
			
		}
		
			    AuthorizeApiResponseModel authorizeApiResponseModel = new AuthorizeApiResponseModel();
				List<Privilege> privilegeEntities = new ArrayList<>();
				List<Privilege> privilegeEntities1 = privilegeRepository.findByPrivilegeUrlAndMethodAndIsDeleted(uri,
						method, false);
				
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
				if (privilegeEntities1.size() > 0) {
					
		/////////////////For Open APIs
					if (privilegeEntities1.get(0).getScope().equals("Open")) {
						returnValue = true;
					}
		
			else {
				/////////////////////For Authorized APIs
				if (token.equals("") || token == null) {
					hasAnError = true;
					authorizeApiResponseModel.setMessage("Unauthorized");
					authorizeApiResponseModel.setPath(uri);
					authorizeApiResponseModel.setStatus(HttpStatus.UNAUTHORIZED);
					authorizeApiResponseModel.setStatusCode(HttpStatus.FORBIDDEN.value());
					authorizeApiResponseModel.setTimestamp(Instant.now());
				}
				
				if (!hasAnError) {
					try {
						isTokenExpired(token.split(" ")[1]);
					}
					catch (Exception ex) {
						if (ex.toString().contains("io.jsonwebtoken.ExpiredJwtException")) {
							hasAnError = true;
							authorizeApiResponseModel.setMessage("Token Expired");
							authorizeApiResponseModel.setPath(uri);
							authorizeApiResponseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
							authorizeApiResponseModel.setStatusCode(HttpStatus.FORBIDDEN.value());
							authorizeApiResponseModel.setTimestamp(Instant.now());
						}
					}
					
					if (token != null && !"".equals(token) && !hasAnError) {
						// token=token.split(" ")[1];
						
						// Claims claims = Jwts.parser()
						// .setSigningKey("JWTSuperSecretKey")
						// .parseClaimsJws(token)
						// .getBody();
						//
						// Long userId = Long.parseLong(claims.getSubject());
						Long userId = (long) 23;
						// Optional<UserEntity> user=userRepository.findById((long)23);
						// UserEntity userEntity = null;
						if (userId != null) {
							// userEntity=user.get();
							
							List<UserRoles> userRolesEntities = userRolesRepository.findByUserIdAndIsDeleted(userId,
							    false);
							for (UserRoles userRolesEntity : userRolesEntities) {
								List<RolePrivilege> rolePrivilegeEntities = rolePrivilegeRepository
								        .findByRoleIdAndIsDeleted(userRolesEntity.getRoleId(), false);
								for (RolePrivilege rolePrivilegeEntity : rolePrivilegeEntities) {
									Privilege privilegeEntity = privilegeRepository
									        .findByPrivilegeIdAndIsDeleted(rolePrivilegeEntity.getPrivilegeId(), false);
									
									if (privilegeEntity != null)
										privilegeEntities.add(privilegeEntity);
								}
							}
						}
						
						Stream filteredList = privilegeEntities.stream()
						        .filter(item -> item.getPrivilegeId() == privilegeEntities1.get(0).getPrivilegeId());
						
						if (filteredList.toArray().length == 0) {
							hasAnError = true;
							authorizeApiResponseModel.setMessage("You are not granted to this privilege");
							authorizeApiResponseModel.setPath(uri);
							authorizeApiResponseModel.setStatus(HttpStatus.FORBIDDEN);
							authorizeApiResponseModel.setStatusCode(HttpStatus.FORBIDDEN.value());
							authorizeApiResponseModel.setTimestamp(Instant.now());
						}
						
						else
							returnValue = true;
					}
				}
				
			}
			
		}
		
		else {
			hasAnError = true;
			authorizeApiResponseModel.setMessage("API is not found");
			authorizeApiResponseModel.setPath(uri);
			authorizeApiResponseModel.setStatus(HttpStatus.NOT_FOUND);
			authorizeApiResponseModel.setStatusCode(HttpStatus.NOT_FOUND.value());
			authorizeApiResponseModel.setTimestamp(Instant.now());
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		if (hasAnError) {
			request.setAttribute("message", authorizeApiResponseModel.getMessage());
			request.setAttribute("statusCode", authorizeApiResponseModel.getStatusCode());
			request.setAttribute("status", authorizeApiResponseModel.getStatus());
			request.setAttribute("path", authorizeApiResponseModel.getPath());
			request.setAttribute("timestamp", authorizeApiResponseModel.getTimestamp());
			request.getRequestDispatcher("/handle-exception").forward(request, response);
			return true;
		}
		
		else if (returnValue) {
			return true;
		}
		
		else {
			request.setAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
			request.setAttribute("message", "Something goes wrong!!");
			request.setAttribute("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			request.setAttribute("path", request.getRequestURI());
			request.setAttribute("timestamp", Instant.now());
			request.getRequestDispatcher("/handle-exception").forward(request, response);
			return true;
		}
		
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
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	        ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle");
		//	      try {
		//	          int error = 1/0;
		//	      } catch (Exception e) {
		//	          log.info("Exception will be handled inside catch block");
		//	      }
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
	        Exception exception) throws Exception {
		System.out.println("afterCompletion");
		
	}
}
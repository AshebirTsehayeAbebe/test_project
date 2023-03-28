package optifoodmanagement.config;
//package com.api;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//import java.util.stream.Stream;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.servlet.HandlerMapping;
//
//import com.api.exception.AppException;
//import com.api.io.entity.PrivilegeEntity;
//import com.api.io.entity.RolePrivilegeEntity;
//import com.api.io.entity.UserEntity;
//import com.api.io.entity.UserRolesEntity;
//import com.api.io.repositories.PrivilegeRepository;
//import com.api.io.repositories.RolePrivilegeRepository;
//import com.api.io.repositories.RoleRepository;
//import com.api.io.repositories.UserRepository;
//import com.api.io.repositories.UserRolesRepository;
//import com.api.security.JwtTokenProvider;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//
//@Component
//public class AuthorizeApiRequest extends OncePerRequestFilter{
//
//	@Value("${app.jwtSecret}")
//    private String SECRET_KEY;
//	
//	@Autowired
//	JwtTokenProvider jwtTokenProvider;
//	
//	@Autowired
//	PrivilegeRepository privilegeRepository;
//	
//	@Autowired
//	RoleRepository roleRepository;
//	
//	@Autowired
//	RolePrivilegeRepository rolePrivilegeRepository;
//	
//	@Autowired
//	UserRepository userRepository;
//	
//	@Autowired
//	UserRolesRepository userRolesRepository;
//	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		
//		HttpServletResponse response1= null;
//		HttpServletRequest request1 = null;
//		String url = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
//		
//		
//		try {
//			filterChain.doFilter(request1, response1);
//		} catch (Exception ex) {
//			
//		}
//		System.out.print("Chacking URL:"+ url);
//		boolean isTokenExpired = false;
//		List<PrivilegeEntity> privilegeEntities1 = privilegeRepository.findByPrivilegeUrlAndMethodAndIsDeleted(url, request.getMethod(), false);
//		
//		if(privilegeEntities1.size()==0)
//			throw new AppException("Incorrect API!!, Check your uri or method");
//		
/////////////////////For Authorized APIs
//		if(privilegeEntities1.size()>0 && !privilegeEntities1.get(0).getScope().equals("Open")){
//
//				if(request.getHeader("Authorization")==null)
//					throw new AppException("Unauthorized!!");
//					
//				try {
//					 isTokenExpired = isTokenExpired(request.getHeader("Authorization").split(" ")[1]);
//				} catch (Exception ex) {
//					if (ex.toString().contains("io.jsonwebtoken.ExpiredJwtException")) {
//						isTokenExpired = true;
//					}else {
//						System.out.print(ex);
//					}
//				}		
//				
//				if(isTokenExpired)
//					throw new AppException("Token has been expired!!");
//		
//				String token=request.getHeader("Authorization");    
//				List<PrivilegeEntity> privilegeEntities = new ArrayList<>();
//				
//				if(token!=null && !"".equals(token)) {
//				token=token.split(" ")[1];
//				Long userId=jwtTokenProvider.getUserIdFromJWT(token);
//				Optional<UserEntity> user=userRepository.findById(userId);
//				UserEntity userEntity = null;
//				if(user!=null && user.get()!=null) {
//				   userEntity=user.get();
//				 
//				List<UserRolesEntity> userRolesEntities = userRolesRepository.findByUserIdAndIsDeleted(userId, false);
//				  for(UserRolesEntity userRolesEntity: userRolesEntities) {
//					  List<RolePrivilegeEntity> rolePrivilegeEntities = rolePrivilegeRepository.findByRoleIdAndIsDeleted(userRolesEntity.getRoleId(), false);
//					  for(RolePrivilegeEntity rolePrivilegeEntity: rolePrivilegeEntities) {
//						  PrivilegeEntity privilegeEntity = privilegeRepository.findByPrivilegeIdAndIsDeleted(rolePrivilegeEntity.getPrivilegeId(), false);
//						  privilegeEntities.add(privilegeEntity);
//					  }
//				  }
//				}
//				}
//				
//				Stream filteredList = privilegeEntities.stream().filter(item -> item.getPrivilegeUrl().equals(request.getRequestURI())&&item.getMethod().equals(request.getMethod()));
//				
//				if(filteredList.toArray().length>0)
//				  throw new AppException("Not Granted");				
//		}
//	
//	}
//	
//// Get Expiration and compare it with new Date()
//
//	public boolean isTokenExpired(String token) {
//	    return extractExpiration(token).before(new Date());
//	}
//
//
//	public Date extractExpiration(String token) {
//	    return extractClaim(token, Claims::getExpiration);
//	}
//
//
//	public <T> T extractClaim(String token , Function<Claims, T> claimResolver) {
//	    final Claims claim= extractAllClaims(token);
//	    return claimResolver.apply(claim);
//	}
//
//
//	private Claims extractAllClaims(String token) {
//	    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//	}
//
//}

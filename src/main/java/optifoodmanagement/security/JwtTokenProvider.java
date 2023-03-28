package optifoodmanagement.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import optifoodmanagement.io.entity.Privilege;
import optifoodmanagement.io.entity.Restaurant;
import optifoodmanagement.io.entity.Role;
import optifoodmanagement.io.entity.RolePrivilege;
import optifoodmanagement.io.entity.UserEntity;
import optifoodmanagement.io.entity.UserRoles;
import optifoodmanagement.io.repositories.PrivilegeRepository;
import optifoodmanagement.io.repositories.RestaurantRepository;
import optifoodmanagement.io.repositories.RolePrivilegeRepository;
import optifoodmanagement.io.repositories.RoleRepository;
import optifoodmanagement.io.repositories.UserRepository;
import optifoodmanagement.io.repositories.UserRolesRepository;
import optifoodmanagement.ui.model.response.JwtAuthenticationResponse;
import optifoodmanagement.ui.model.response.PrivilegeResponseModel;
import optifoodmanagement.ui.model.response.RestaurantResponseModel;
import optifoodmanagement.ui.model.response.RoleResponseForLogin;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;
    
    @Value("${app.jwtPrefix}")
    private String jwtPrefix;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    RolePrivilegeRepository rolePrivilegeRepository;
    
    @Autowired
    PrivilegeRepository privilegeRepository;
    
    
    @Autowired
    UserRolesRepository userRolesRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
    public JwtAuthenticationResponse generateToken(Authentication authentication, String liceseKey) {
    	JwtAuthenticationResponse returnValue= new JwtAuthenticationResponse();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        String jwt =jwtPrefix + Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        returnValue.setAccessToken(jwt);
        returnValue.setUserId(userPrincipal.getUsername());
		returnValue.setUserType(userPrincipal.getUserType());
        returnValue.setUserStatus(userPrincipal.getUserStatus());
        List<RoleResponseForLogin> grantedPrivileges = new ArrayList<>();
		new ArrayList<>();
        
        List<UserRoles> userRolesEntities = userRolesRepository.findByUserId(userPrincipal.getId());
		for(UserRoles userRolesEntity: userRolesEntities) {
			RoleResponseForLogin roleResponse = new RoleResponseForLogin();
			Role roleEntity = roleRepository.findByRoleIdAndIsDeleted(userRolesEntity.getRoleId(),false);

			if(roleEntity!=null) {
				roleResponse.setId(roleEntity.getRoleId());
				roleResponse.setRoleName(roleEntity.getRoleName());
				roleResponse.setRoleFullName(roleEntity.getRoleFullName());
				returnValue.setUserType(roleEntity.getRoleName());
				
				List<PrivilegeResponseModel> privilegeResponseModels = new ArrayList<>();

				List<RolePrivilege> rolePrivilegeEntities = rolePrivilegeRepository.findByRoleIdAndIsPrivilagedAndIsDeleted(roleEntity.getRoleId(),true,false);
				for(RolePrivilege rolePrivilegeEntity: rolePrivilegeEntities) {
					
					PrivilegeResponseModel privilegeResponseModel = new PrivilegeResponseModel();
					Privilege privilegeEntity = privilegeRepository.findByPrivilegeIdAndIsDeleted(rolePrivilegeEntity.getPrivilegeId(),false);
					
					if(privilegeEntity != null) {
						BeanUtils.copyProperties(privilegeEntity, privilegeResponseModel);
						privilegeResponseModel.setRoleId(rolePrivilegeEntity.getRoleId());
						privilegeResponseModels.add(privilegeResponseModel);
						roleResponse.setPrivileges(privilegeResponseModels);
					}
				}
				grantedPrivileges.add(roleResponse);
			}

		returnValue.setGrantedPrivileges(grantedPrivileges);
		}
		
		Restaurant restaurant = restaurantRepository.findTopByLicenseKeyAndIsDeleted(liceseKey, false);
		if (restaurant!=null) {
			RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel();
			BeanUtils.copyProperties(restaurant, restaurantResponseModel);
			returnValue.setRestaurantResponseModel(restaurantResponseModel);
		}

      
        return returnValue;
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
    public String getUserIdStrFromJWT(String token) {
    	if(token.split(" ").length>1)
    		token=token.split(" ")[1];
    	String userIdStr=null;
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        
        Long userId=Long.parseLong(claims.getSubject());
        Optional<UserEntity> user=userRepository.findById(userId);
        
        if(user!=null && user.get()!=null) {
      	  UserEntity userEntity=user.get();
      	  if(userEntity!=null)
      		  userIdStr=userEntity.getUserId();
        }
        
        return userIdStr;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}

package optifoodmanagement.service.Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import optifoodmanagement.exception.AppException;
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
import optifoodmanagement.security.JwtTokenProvider;
import optifoodmanagement.service.UserService;
import optifoodmanagement.shared.GenerateRandomStrings;
import optifoodmanagement.shared.SendEmail;
import optifoodmanagement.ui.model.request.ResetPasswordRequestModel;
import optifoodmanagement.ui.model.request.SearchRequestModel;
import optifoodmanagement.ui.model.request.SendEmailRequestModel;
import optifoodmanagement.ui.model.request.UploadProfileRequestModel;
import optifoodmanagement.ui.model.request.UserDetailRequestModel;
import optifoodmanagement.ui.model.response.JwtAuthenticationResponse;
import optifoodmanagement.ui.model.response.PrivilegeResponseModel;
import optifoodmanagement.ui.model.response.RestaurantResponseModel;
import optifoodmanagement.ui.model.response.RoleResponseForLogin;
import optifoodmanagement.ui.model.response.UserResponseModel;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GenerateRandomStrings generateRandomString;
	
	@Autowired
	PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    RoleRepository roleRepository;
	
	@Autowired
	PrivilegeRepository privilegeRepository;
	
	@Autowired
    RolePrivilegeRepository rolePrivilegeRepository;
	
	@Autowired
    UserRolesRepository userRolesRepository;
	
	@Autowired
	SendEmail sendMailComponent;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired 
	JwtTokenProvider jwtTokenProvider;
	
	@Value("${file.upload-dir}")
    private String uploadDirectory;
	
	@Value("${app.HostDomain}")
    private String applicationHostDomain;
	
	@Override
	public UserResponseModel saveUser(UserDetailRequestModel user)
	        throws AddressException, MessagingException, IOException {
		
		UserResponseModel returnValue = new UserResponseModel();
		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new AppException("Record already exists with this Email.");
			
    		UserEntity userEntity = new UserEntity();
    		BeanUtils.copyProperties(user, userEntity);
    		String defaultUserStatus = "NotVerified";
    		String emailVerificationToken = generateRandomString.generateUserId(45);
    		
    		userEntity.setEmailVerificationToken(emailVerificationToken);
    		userEntity.setUserStatus(defaultUserStatus);
    		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    		userEntity.setUserId(generateRandomString.generateUserId(30));
		
		Role userRole = roleRepository.findByRoleName(user.getUserType());
		
		if (userRole != null)
			userEntity.setRoles(Collections.singleton(userRole));

    		UserEntity storedUserDetailsEntity = userRepository.save(userEntity);
    		String mailSubject = "";
    		String mailBody = "<b>Verify your the user </b><br><br> Follow this link --> <a href='" + applicationHostDomain + "/verifyaccount?verificationToken=" + emailVerificationToken + "'><b><i>Click me to Verify</i></b></a><br><br> <span style='color:red; font-size:12px;'> Don't reply to this email !</span>";
    		SendEmailRequestModel sendMail = new SendEmailRequestModel();
    		sendMail.setToAddress(storedUserDetailsEntity.getEmail());
    		sendMail.setSubject(mailSubject);
    		sendMail.setBody(mailBody);
    		sendMailComponent.sendMail(sendMail);
    		
    		BeanUtils.copyProperties(storedUserDetailsEntity, returnValue);
		
		return returnValue;
		
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserResponseModel getuser(String email) {
		
		UserResponseModel returnValue = new UserResponseModel();
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new AppException("User not found");
		Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeleted(userEntity.getRestaurantId(), false);
		if(restaurant!=null) {
			RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel();
			BeanUtils.copyProperties(restaurant, restaurantResponseModel);
			returnValue.setRestaurantResponseModel(restaurantResponseModel);
		}
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserResponseModel getUserByUserId(String UserId) {
			
		UserResponseModel returnValue = new UserResponseModel();
		UserEntity userEntity = userRepository.findByUserId(UserId);
		
		if (userEntity == null)
			throw new AppException("User not found");
		Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeleted(userEntity.getRestaurantId(), false);
		if(restaurant!=null) {
			RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel();
			BeanUtils.copyProperties(restaurant, restaurantResponseModel);
			returnValue.setRestaurantResponseModel(restaurantResponseModel);
		}
		BeanUtils.copyProperties(userEntity, returnValue); 
		return returnValue;
	}

	@Override
	public UserResponseModel updateUser(String userId, UserDetailRequestModel userDetail) {
		
		UserResponseModel returnValue = new UserResponseModel();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) 
			throw new RuntimeException("User not found.");
		userEntity.setEmail(userDetail.getEmail());
		userEntity.setFirstName(userDetail.getFirstName());
		userEntity.setLastName(userDetail.getLastName());
		userEntity.setMobilePhone(userDetail.getMobilePhone());
		userEntity.setEmail(userDetail.getEmail());
		userEntity.setUserType(userDetail.getUserType());
		userEntity.setRestaurantId(userDetail.getRestaurantId());
		
		UserEntity updatesUserDetailsEntity = userRepository.save(userEntity);
		BeanUtils.copyProperties(updatesUserDetailsEntity, returnValue);
		return returnValue;
	}
	
	@Override
	public UserResponseModel updateUserStatus(String userId, UserDetailRequestModel userDetail) {
		UserResponseModel returnValue = new UserResponseModel();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) 
			throw new RuntimeException("User not found.");
		
		userEntity.setUserStatus(userDetail.getUserStatus());		
		UserEntity updatesUserDetailsEntity = userRepository.save(userEntity);
		BeanUtils.copyProperties(updatesUserDetailsEntity, returnValue); 
		return returnValue;
	}
	
	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) 
			throw new AppException("User not found");
		userRepository.delete(userEntity);
	}

	@Override
	public List<UserResponseModel> getUsers(int page, int limit, String searchKey, Integer restaurantId) {
		 
		List<UserResponseModel> returnValue = new ArrayList<>();
	    if(page > 0) page = page - 1; 
	    Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("id").descending());
	    StringUtils.countOccurrencesOf(searchKey, " ");
	    searchKey.split(" ");
	    
	    if("".equals(searchKey)) {
	    	Page<UserEntity> usersPage = null;
			if (restaurantId != 0)
				usersPage = userRepository.findByRestaurantId(restaurantId, pageableRequest);
	    	else
	    		usersPage = userRepository.findAll(pageableRequest);
			int totalPages = usersPage.getTotalPages();
	 	    List<UserEntity> users = usersPage.getContent();
	 	    for(UserEntity userEntity : users) {
				UserResponseModel userResponseModel = new UserResponseModel();
				if (userEntity == null)
					throw new AppException("User not found");
				BeanUtils.copyProperties(userEntity, userResponseModel);
				userResponseModel.setEmail(userEntity.getEmail());
				if (returnValue.size() == 0) {
					userResponseModel.setTotalPages(totalPages);
				}
				Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeleted(userEntity.getRestaurantId(), false);
				if(restaurant!=null) {
					RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel();
					BeanUtils.copyProperties(restaurant, restaurantResponseModel);
					userResponseModel.setRestaurantResponseModel(restaurantResponseModel);
				}
				returnValue.add(userResponseModel);
				//	 	    	BeanUtils.copyProperties(userEntity, userRest);
	 	    }
	    }
		return returnValue;
	}

	@Override
	public String checkEmail(String email) {
		if(userRepository.findByEmail(email) == null) {
			return "Email doesn't exist";
		}else {
			return "Email exists";
		}
	}

	@Override
	public List<UserResponseModel> searchUsers(SearchRequestModel searchkeyDetail, int page, int limit) {
		
		String searchKey = searchkeyDetail.getSearchKey();
		List<UserResponseModel> returnValue = new ArrayList<>();
		
		if (page > 0)
			page = page - 1;
		Pageable pageableRequest = PageRequest.of(page, limit);
		int countSpaces = StringUtils.countOccurrencesOf(searchKey, " ");
		Page<UserEntity> usersPage = null;
		
		String[] searchKeyArray = searchKey.split(" ");
		if (countSpaces == 0) {
			usersPage = userRepository
			        .findByFirstNameContainingOrLastNameContainingOrMobilePhoneContainingOrEmailContainingOrUserStatusContaining(
			            searchKey, searchKey, searchKey, searchKey, searchKey, pageableRequest);
		} else if (countSpaces == 1) {
			String firstName = searchKeyArray[0];
			String lastName = searchKeyArray[1];
			usersPage = userRepository.findByFirstNameContainingAndLastNameContaining(firstName, lastName, pageableRequest);
		}
		int totalPages = usersPage.getTotalPages();
		List<UserEntity> users = usersPage.getContent();
		for (UserEntity userEntity : users) {
			UserResponseModel userRest = new UserResponseModel();
			BeanUtils.copyProperties(userEntity, userRest);
			if (returnValue.size() == 0) {
				userRest.setTotalPages(totalPages);
			}
			if(userEntity!=null) {
				Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeleted(userEntity.getRestaurantId(), false);
				if(restaurant!=null) {
					RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel();
					BeanUtils.copyProperties(restaurant, restaurantResponseModel);
					userRest.setRestaurantResponseModel(restaurantResponseModel);
				}
			}

			returnValue.add(userRest);
		}
		return returnValue;
	}

	@Override
	public String uploadProfilePicture(UploadProfileRequestModel requestDetail) throws IOException {
		
		String rootDirectory = "C://wamp64/www/";
		String uploadDir = rootDirectory + "/user_profiles/";
		
		File directory = new File(uploadDir);
	    if (!directory.exists()){
	        directory.mkdirs();
	    }
		
		String returnValue = "Image not saved";
		byte[] bytes = requestDetail.getProfilePicture().getBytes();
		
		String userId = requestDetail.getUserId();
		String fileName = requestDetail.getProfilePicture().getOriginalFilename();
		String extention = (fileName.substring(fileName.lastIndexOf(".") + 1)).toLowerCase();
//		String newFileName = userId + "_" +  generateRandomString.generateUserId(5) + "." +  extention;
		String newFileName = userId + "." +  extention;
		Path path = Paths.get(uploadDir + newFileName);
	    Files.write(path, bytes);
	    
	    UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) 
			throw new AppException("User not found.");
		
		userEntity.setProfilePicture(newFileName);	
		UserEntity updatesUserDetailsEntity = userRepository.save(userEntity);
		if(updatesUserDetailsEntity.getProfilePicture() != null) {
			returnValue = "Profile picture Saved";
		}
		
		return returnValue;
		
	}

	@Override
	public String resetPassword(ResetPasswordRequestModel resetPasswordDetail) {
		
		String returnValue = "Password not changed";
		UserEntity userEntity = userRepository.findByEmailAndPasswordResetCode(resetPasswordDetail.getEmail(),resetPasswordDetail.getResetCode());
		if(userEntity == null) throw new AppException("Password Can't be changed");
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(resetPasswordDetail.getNewPassword()));
		UserEntity passworUpdated = userRepository.save(userEntity);
		if(passworUpdated != null) {
			returnValue = "Password changed successfully";
		}
		return returnValue;
	}

	@Override
	public String changeAccountPassword(ResetPasswordRequestModel changePassRequest) {
		
		UserEntity userEntity = userRepository.findByEmail(changePassRequest.getEmail());
		
		if(userEntity == null) 
			throw new RuntimeException("User not found.");
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(changePassRequest.getNewPassword()));
		userRepository.save(userEntity);
		
		return "Password changed successfully";
	}



	@Override
	public JwtAuthenticationResponse getCurrentUserDetail(String token) {
		if(token==null || "".equals(token))
			return null;
		
		JwtAuthenticationResponse returnValue= new JwtAuthenticationResponse();
		Long id = jwtTokenProvider.getUserIdFromJWT(token);
		Optional<UserEntity> userEntity= userRepository.findById(id);
		
		if(userEntity !=null) {
			returnValue.setAccessToken(token);
			returnValue.setUserId(userEntity.get().getUserId());
			returnValue.setUserStatus(userEntity.get().getUserStatus());
			Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeleted(userEntity.get().getRestaurantId(), false);
			if(restaurant!=null) {
				RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel();
				BeanUtils.copyProperties(restaurant, restaurantResponseModel);
				returnValue.setRestaurantResponseModel(restaurantResponseModel);
			}
			List<RoleResponseForLogin> grantedPrivileges = new ArrayList<>();
			List<UserRoles> userRolesEntities = userRolesRepository.findByUserId(id);
			for(UserRoles userRolesEntity: userRolesEntities) {
				RoleResponseForLogin roleResponse = new RoleResponseForLogin();
				Role roleEntity = roleRepository.findByRoleIdAndIsDeleted(userRolesEntity.getRoleId(),false);

				if(roleEntity!=null) {
					roleResponse.setId(roleEntity.getRoleId());
					roleResponse.setRoleName(roleEntity.getRoleName());
					roleResponse.setRoleFullName(roleEntity.getRoleFullName());
					
					
					List<PrivilegeResponseModel> privilegeResponseModels = new ArrayList<>();

					List<RolePrivilege> rolePrivilegeEntities = rolePrivilegeRepository
					        .findByRoleIdAndIsPrivilagedAndIsDeleted(roleEntity.getRoleId(), true, false);

					for (RolePrivilege rolePrivilegeEntity : rolePrivilegeEntities) {
						
						PrivilegeResponseModel privilegeResponseModel = new PrivilegeResponseModel();
						Privilege privilegeEntity = privilegeRepository
						        .findByPrivilegeIdAndIsDeleted(rolePrivilegeEntity.getPrivilegeId(), false);
						
						if(privilegeEntity != null) {
							BeanUtils.copyProperties(privilegeEntity, privilegeResponseModel);
							privilegeResponseModel.setRoleId(rolePrivilegeEntity.getRoleId());
							privilegeResponseModels.add(privilegeResponseModel);
						}
					}
					roleResponse.setPrivileges(privilegeResponseModels);
					grantedPrivileges.add(roleResponse);
				}
							
			
			returnValue.setGrantedPrivileges(grantedPrivileges);
		}
		}	
		return returnValue;
	}

	@Override
	public UserResponseModel getUserById(Long id) {

		UserResponseModel returnValue = new UserResponseModel();
		Optional<UserEntity> userEntity = userRepository.findById(id);

		if (userEntity == null)
			throw new AppException("User not found");
		Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeleted(userEntity.get().getRestaurantId(), false);
		if(restaurant!=null) {
			RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel();
			BeanUtils.copyProperties(restaurant, restaurantResponseModel);
			returnValue.setRestaurantResponseModel(restaurantResponseModel);
		}
      returnValue.setFirstName(userEntity.get().getFirstName());
      returnValue.setLastName(userEntity.get().getLastName());
		BeanUtils.copyProperties(userEntity, returnValue);
		
		return returnValue;
	}


}

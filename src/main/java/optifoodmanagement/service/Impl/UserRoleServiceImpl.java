package optifoodmanagement.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import optifoodmanagement.exception.AppException;
import optifoodmanagement.io.entity.Role;
import optifoodmanagement.io.entity.UserEntity;
import optifoodmanagement.io.entity.UserRoles;
import optifoodmanagement.io.repositories.RoleRepository;
import optifoodmanagement.io.repositories.UserRepository;
import optifoodmanagement.io.repositories.UserRolesRepository;
import optifoodmanagement.service.UserRoleService;
import optifoodmanagement.ui.model.request.UserRoleRequestModel;
import optifoodmanagement.ui.model.response.RoleResponseModel;
import optifoodmanagement.ui.model.response.UserRoleResponseModel;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	
	@Autowired
	UserRolesRepository userRoleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public String saveUserRole(UserRoleRequestModel userRoleDetail) {
		
		List<UserRoles> userRolesEntities = userRoleRepository.findByUserIdAndIsDeleted(userRoleDetail.getUserId(), false);
		for (UserRoles userRolesEntity : userRolesEntities) {
			if (Arrays.asList(userRoleDetail.getRoleIds().equals(userRolesEntity.getRoleId())).size() > 0) {
				userRolesEntity.setDeleted(true);
				userRoleRepository.save(userRolesEntity);
			}
		}
		for (Long roleId : userRoleDetail.getRoleIds()) {
			UserRoles userRolesEntity = new UserRoles();
			
			UserRoles checkRole = userRoleRepository.findByUserIdAndRoleId(userRoleDetail.getUserId(), roleId);
			if (checkRole != null) {
				checkRole.setDeleted(false);
				userRoleRepository.save(checkRole);
				continue;
			}
			userRolesEntity.setRoleId(roleId);
			userRolesEntity.setUserId(userRoleDetail.getUserId());
			userRoleRepository.save(userRolesEntity);
		}
		
		return "Saved successfuly!";
	}
	
	@Override
	public String updateUserRole(UserRoleRequestModel userRoleDetail, Long userRoleId) {
		UserRoles userRolesEntity = userRoleRepository.findByUserRoleIdAndIsDeleted(userRoleId, false);
		if (userRolesEntity == null)
			throw new AppException("Data not found");
		
		BeanUtils.copyProperties(userRoleDetail, userRolesEntity);
		userRoleRepository.save(userRolesEntity);
		return "Data updated!";
	}
	
	@Override
	public String deleteUserRole(Long userRoleId) {
		UserRoles userRolesEntity = userRoleRepository.findByUserRoleIdAndIsDeleted(userRoleId, false);
		
		if (userRolesEntity == null)
			throw new AppException("Role not found");
		userRolesEntity.setDeleted(true);
		userRoleRepository.save(userRolesEntity);
		return "Data deleted!";
		
	}
	
	@Override
	public UserRoleResponseModel getAssignedRoles(Long userId) {
		UserRoleResponseModel returnValue = new UserRoleResponseModel();
		
		List<UserRoles> userRolesEntities = userRoleRepository.findByUserIdAndIsDeleted(userId, false);
		userRolesEntities.size();
		userRolesEntities.size();
		List<RoleResponseModel> roleResponseModels = new ArrayList<>();
		for (UserRoles userRolesEntity : userRolesEntities) {
			RoleResponseModel responseModel = new RoleResponseModel();
			Role roleEntity = roleRepository.findByRoleIdAndIsDeleted(userRolesEntity.getRoleId(), false);
			if (roleEntity != null) {
				BeanUtils.copyProperties(roleEntity, responseModel);
				roleResponseModels.add(responseModel);
			}
			
		}
		
		Optional<UserEntity> userEntity = userRepository.findById(userId);
		if (userEntity.isPresent()) {
			returnValue.setUserFullName(userEntity.get().getFirstName() + " " + userEntity.get().getLastName());
			
		}
		returnValue.setRoles(roleResponseModels);
		returnValue.setUserId(userId);
		
		return returnValue;
	}
	

}
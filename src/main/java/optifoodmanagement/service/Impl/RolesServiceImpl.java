package optifoodmanagement.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import optifoodmanagement.exception.AppException;
import optifoodmanagement.io.entity.Role;
import optifoodmanagement.io.entity.UserEntity;
import optifoodmanagement.io.entity.UserRoles;
import optifoodmanagement.io.repositories.RoleRepository;
import optifoodmanagement.io.repositories.UserRepository;
import optifoodmanagement.io.repositories.UserRolesRepository;
import optifoodmanagement.service.RolesService;
import optifoodmanagement.ui.model.request.RoleRequestModel;
import optifoodmanagement.ui.model.request.UserRoleRequestModel;
import optifoodmanagement.ui.model.response.RoleResponseModel;

@Service
@Transactional
public class RolesServiceImpl implements RolesService {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRolesRepository userRoleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public RoleResponseModel saveRoles(RoleRequestModel roleDetails) {
		
		RoleResponseModel returnValue = new RoleResponseModel();
		Role roleEntity = new Role();
		BeanUtils.copyProperties(roleDetails, roleEntity);
		Role savedDetail = roleRepository.save(roleEntity);
		BeanUtils.copyProperties(savedDetail, returnValue);
		return returnValue;
	}
	
	@Override
	public RoleResponseModel updateRole(long roleId, RoleRequestModel roleDetails) {
		
		RoleResponseModel returnValue = new RoleResponseModel();
		
		Role roleEntity = roleRepository.findByRoleIdAndIsDeleted(roleId, false);
		if (roleEntity == null)
			throw new AppException("No role with this id.");
		
		BeanUtils.copyProperties(roleDetails, roleEntity);
		Role savedRole = roleRepository.save(roleEntity);
		BeanUtils.copyProperties(savedRole, returnValue);
		
		return returnValue;
	}
	
	@Override
	public String deleteRole(Long roleId) {
		// TODO Auto-generated method stub
		Role roleEntity = roleRepository.findByRoleIdAndIsDeleted(roleId, false);
		
		if (roleEntity == null)
			throw new AppException("Role not found");
		
		roleEntity.setDeleted(true);
		roleRepository.save(roleEntity);
		return "Data deleted";
		
	}
	
	@Override
	public String saveUserRole(UserRoleRequestModel userRoleDetail) {
		
		List<UserRoles> userRolesEntities = userRoleRepository.findByUserIdAndIsDeleted(userRoleDetail.getUserId(),
		    false);
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
		// TODO Auto-generated method stub
		UserRoles userRolesEntity = userRoleRepository.findByUserRoleIdAndIsDeleted(userRoleId, false);
		if (userRolesEntity == null)
			throw new AppException("Data not found");
		
		BeanUtils.copyProperties(userRoleDetail, userRolesEntity);
		userRoleRepository.save(userRolesEntity);
		return "Data updated!";
	}
	
	@Override
	public String deleteUserRole(Long userRoleId) {
		// TODO Auto-generated method stub
		UserRoles userRolesEntity = userRoleRepository.findByUserRoleIdAndIsDeleted(userRoleId, false);
		
		if (userRolesEntity == null)
			throw new AppException("Role not found");
		userRolesEntity.setDeleted(true);
		userRoleRepository.save(userRolesEntity);
		return "Data deleted!";
		
	}
	
	@Override
	public List<RoleResponseModel> getRoles(String searchKey, int page, int limit) {
		List<RoleResponseModel> returnValue = new ArrayList<>();
		
		if (page > 0)
			page = page - 1;
		
		Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("roleName").ascending());
		Page<Role> rolePage = null;
		
		if ("".equals(searchKey))
			rolePage = roleRepository.findByIsDeleted(false, pageableRequest);// .findAll(pageableRequest);
		else
			rolePage = roleRepository.findByIsDeletedAndRoleNameContaining(false, searchKey, pageableRequest);// .findAll(pageableRequest);
			
		List<Role> roleEntities = rolePage.getContent();
		
		int totalPages = rolePage.getTotalPages();
		for (Role roleEntity : roleEntities) {
			
			RoleResponseModel roleResponseModel = new RoleResponseModel();
			BeanUtils.copyProperties(roleEntity, roleResponseModel);
			roleResponseModel.setRoleId(roleEntity.getRoleId());
			
			UserEntity userEntity = userRepository.findByUserId(roleEntity.getCreatedBy());
			if (userEntity != null) {
				roleResponseModel.setCreatedBy(
				    userEntity.getFirstName() + " " + userEntity.getLastName());
//			}
			} else
				roleResponseModel.setCreatedBy("");
			
			if (returnValue.size() == 0) {
				roleResponseModel.setTotalPage(totalPages);
			}
			
			returnValue.add(roleResponseModel);
		}
		return returnValue;
	}
	
	@Override
	public RoleResponseModel getRole(Long roleId) {
		RoleResponseModel returnValue = new RoleResponseModel();
		Role roleEntity = roleRepository.findByRoleIdAndIsDeleted(roleId, false);
		if (roleEntity == null)
			throw new AppException("No role with this id");
		BeanUtils.copyProperties(roleEntity, returnValue);
		returnValue.setRoleId(roleEntity.getRoleId());
		return returnValue;
	}
	
	

}

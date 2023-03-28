package optifoodmanagement.service.Impl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import optifoodmanagement.exception.AppException;
import optifoodmanagement.io.entity.Privilege;
import optifoodmanagement.io.entity.RolePrivilege;
import optifoodmanagement.io.entity.UserEntity;
import optifoodmanagement.io.repositories.PrivilegeRepository;
import optifoodmanagement.io.repositories.RolePrivilegeRepository;
import optifoodmanagement.io.repositories.UserRepository;
import optifoodmanagement.service.RolePrivilegeService;
import optifoodmanagement.ui.model.request.RolePrivilegeRequestModel;
import optifoodmanagement.ui.model.response.RolePrivilegesResponseModel;

@Service
@Transactional
public class RolePrivilegeServiceImpl implements RolePrivilegeService {
	
	@Autowired
	PrivilegeRepository privilegeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RolePrivilegeRepository rolePrivilegeRepository;
	
	@Override
	public String saveRolePrivilegeData(RolePrivilegeRequestModel rolePrivilegeDetail) {
		
		List<RolePrivilege> rolePrivilegeEntities = rolePrivilegeRepository
		        .findByRoleIdAndIsDeleted(rolePrivilegeDetail.getRoleId(), false);
		for (RolePrivilege rolePrivilegeEntity : rolePrivilegeEntities) {
			if (Arrays.asList(rolePrivilegeDetail.getPrivilegeIds().equals(rolePrivilegeEntity.getPrivilegeId()))
			        .size() > 0) {
				rolePrivilegeEntity.setDeleted(true);
				rolePrivilegeRepository.save(rolePrivilegeEntity);
			}
		}
		for (Integer privilegeId : rolePrivilegeDetail.getPrivilegeIds()) {
			RolePrivilege rolePrivilegeEntity = new RolePrivilege();
			
			RolePrivilege checkPrivilege = rolePrivilegeRepository
			        .findByRoleIdAndPrivilegeId(rolePrivilegeDetail.getRoleId(), privilegeId);
			if (checkPrivilege != null) {
				checkPrivilege.setDeleted(false);
				rolePrivilegeRepository.save(checkPrivilege);
				continue;
			}
			rolePrivilegeEntity.setRoleId(rolePrivilegeDetail.getRoleId());
			rolePrivilegeEntity.setPrivilegeId(privilegeId);
			rolePrivilegeRepository.save(rolePrivilegeEntity);
		}
		
		return "Saved successfuly!";
		
	}
	
	@Override
	public String updateRolePrivilegeData(RolePrivilegeRequestModel rolePrivilegeDetail, Long rolePrivilegeId) {
		RolePrivilege rolePrivilegeEntity = rolePrivilegeRepository.findByRolePrivilegeIdAndIsDeleted(rolePrivilegeId,
		    false);
		BeanUtils.copyProperties(rolePrivilegeDetail, rolePrivilegeEntity);
		rolePrivilegeRepository.save(rolePrivilegeEntity);
		return "Data Updated!";
	}
	
	@Override
	public String deleteRolePrivilageData(Long rolePrivilegeId) {
		
		RolePrivilege rolePrivilegeEntity = rolePrivilegeRepository.findByRolePrivilegeIdAndIsDeleted(rolePrivilegeId,
		    false);
		if (rolePrivilegeEntity == null)
			throw new AppException("role privilage relation with this id not exists");
		
		rolePrivilegeEntity.setDeleted(true);
		rolePrivilegeRepository.save(rolePrivilegeEntity);
		return "Data deleted";
		
	}
	
	@Override
	public List<RolePrivilegesResponseModel> getRolePrivileges(Long roleId) {
		
		List<RolePrivilegesResponseModel> returnValue = new ArrayList<>();
		
		List<RolePrivilege> rolePrivilegePage = null;
		
		rolePrivilegePage = rolePrivilegeRepository.findByRoleIdAndIsDeleted(roleId, false);// .findAll(pageableRequest);
		
		for (RolePrivilege rolePrivilegeEntity : rolePrivilegePage) {
			
			RolePrivilegesResponseModel rolePrivilegesResponseModel = new RolePrivilegesResponseModel();
			if (rolePrivilegeEntity.isPrivilaged() == true)
				rolePrivilegesResponseModel.setAllowed(true);
			else
				rolePrivilegesResponseModel.setAllowed(false);
			Privilege privilegeEntity = privilegeRepository
			        .findByPrivilegeIdAndIsDeleted(rolePrivilegeEntity.getPrivilegeId(), false);
			if (privilegeEntity != null) {
				rolePrivilegesResponseModel.setPrivilegeName(privilegeEntity.getPrivilegeName());
				rolePrivilegesResponseModel.setPrivilegeDescription(privilegeEntity.getPrivilegeDescription());
				rolePrivilegesResponseModel.setMethod(privilegeEntity.getMethod());
				rolePrivilegesResponseModel.setPrivilegeUrl(privilegeEntity.getPrivilegeUrl());
				rolePrivilegesResponseModel.setPrivilegeId(privilegeEntity.getPrivilegeId());
			}
			
			BeanUtils.copyProperties(rolePrivilegeEntity, rolePrivilegesResponseModel);
			
			UserEntity userEntity = userRepository.findByUserId(rolePrivilegeEntity.getCreatedBy());
			if (userEntity != null) {
				rolePrivilegesResponseModel.setCreatedBy(
				    userEntity.getFirstName() + " " + userEntity.getLastName());
				
			} else
				rolePrivilegesResponseModel.setCreatedBy("");
			
			returnValue.add(rolePrivilegesResponseModel);
		}
		
		return returnValue;
		
	}
	
}

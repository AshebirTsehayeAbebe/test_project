package optifoodmanagement.service.Impl;

import java.util.ArrayList;
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
import optifoodmanagement.service.PrivilegeService;
import optifoodmanagement.ui.model.request.PrivilegeRequestModel;
import optifoodmanagement.ui.model.response.PrivilegeResponseModel;

@Service
@Transactional
public class PrivilegeServiceImpl implements PrivilegeService {
	
	@Autowired
	PrivilegeRepository privilegeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RolePrivilegeRepository rolePrivilegeRepository;
	
	@Override
	public PrivilegeResponseModel savePrivilege(PrivilegeRequestModel privilegeDetail) {
		List<Privilege> privilegeEnti = null;
		Privilege savedPrivilegeEntity = null;
		PrivilegeResponseModel returnValue = new PrivilegeResponseModel();
		privilegeEnti = privilegeRepository.findByPrivilegeUrlAndMethodAndIsDeleted(privilegeDetail.getPrivilegeUrl(),
		    privilegeDetail.getMethod(), false);
		
		if (privilegeEnti.size() > 0)
			throw new AppException("Privilege already exists with this Method.");
		privilegeEnti = privilegeRepository.findByPrivilegeName(privilegeDetail.getPrivilegeName());
		if (privilegeEnti.size() > 0) {
			Privilege privilegeEntity1 = privilegeEnti.get(privilegeEnti.size() - 1);
			if (privilegeEntity1.isDeleted() == true) {
				
				privilegeEntity1.setDeleted(false);
				savedPrivilegeEntity = privilegeRepository.save(privilegeEntity1);
			} else {
				throw new AppException("Privilege Already Exists With This Name.");
			}
		} else {
			Privilege privilegeEntity = new Privilege();
			BeanUtils.copyProperties(privilegeDetail, privilegeEntity);
			savedPrivilegeEntity = privilegeRepository.save(privilegeEntity);
		}
		BeanUtils.copyProperties(savedPrivilegeEntity, returnValue);
		return returnValue;
	}
	
	@Override
	public PrivilegeResponseModel getPrivilege(Integer privilegeId) {
		PrivilegeResponseModel returnValue = new PrivilegeResponseModel();
		Privilege privilegeEntity = privilegeRepository.findByPrivilegeIdAndIsDeleted(privilegeId, false);
		if (privilegeEntity == null)
			throw new AppException("No Privilege with this Id");
		BeanUtils.copyProperties(privilegeEntity, returnValue);
		return returnValue;
	}
	
	@Override
	public PrivilegeResponseModel updatePrivilege(PrivilegeRequestModel privilegeDetail, Integer privilegeId) {
		PrivilegeResponseModel returnValue = new PrivilegeResponseModel();
		Privilege privilegeEntity = privilegeRepository.findByPrivilegeIdAndIsDeleted(privilegeId, false);
		if (privilegeEntity == null)
			throw new AppException("No Privilege with this Id");
		BeanUtils.copyProperties(privilegeDetail, privilegeEntity);
		Privilege savedPrivilegeEntity = privilegeRepository.save(privilegeEntity);
		BeanUtils.copyProperties(savedPrivilegeEntity, returnValue);
		return returnValue;
	}
	
	@Override
	public List<PrivilegeResponseModel> getAllPrivileges(int page, int limit, String searchKey, Long roleId) {
		List<PrivilegeResponseModel> returnValue = new ArrayList<>();
		
		if (page > 0)
			page = page - 1;
		
		List<Privilege> agentPage = null;
		
		if ("".equals(searchKey))
			agentPage = privilegeRepository.findByIsDeleted(false);// .findAll(pageableRequest);
		else
			agentPage = privilegeRepository
			        .findByPrivilegeNameContainingOrPrivilegeUrlContainingOrPrivilegeDescriptionContainingAndIsDeleted(
			            searchKey, searchKey, searchKey, false);
		
		for (Privilege privilegeEntity : agentPage) {
			
			PrivilegeResponseModel privilegeResponseModel = new PrivilegeResponseModel();
			BeanUtils.copyProperties(privilegeEntity, privilegeResponseModel);
			List<RolePrivilege> rolePrivilegeEntity = rolePrivilegeRepository
			        .findByPrivilegeIdAndRoleIdAndIsDeleted(privilegeEntity.getPrivilegeId(), roleId, false);
			if (rolePrivilegeEntity.size() > 0) {
				for (RolePrivilege rolePrivilegeEntity2 : rolePrivilegeEntity) {
					if (rolePrivilegeEntity2 != null) {
						if (rolePrivilegeEntity2.isPrivilaged() == true)
							privilegeResponseModel.setPrivileged(true);
						else if (rolePrivilegeEntity2.isPrivilaged() == false)
							privilegeResponseModel.setPrivileged(false);
						
					}
				}
			}
			

			UserEntity userEntity = userRepository.findByUserId(privilegeEntity.getCreatedBy());
			if (userEntity != null) {
				
				privilegeResponseModel.setCreatedBy(
				    userEntity.getFirstName() + " " + userEntity.getLastName());
				
			} else
				privilegeResponseModel.setCreatedBy("");
			
			returnValue.add(privilegeResponseModel);
		}
		return returnValue;
	}
	
	@Override
	public String deletePrivilege(Integer privilegeId) {
		Privilege privilegeEntity = privilegeRepository.findByPrivilegeIdAndIsDeleted(privilegeId, false);
		if (privilegeEntity == null)
			throw new AppException("No Privilege with this Id");
		privilegeEntity.setDeleted(true);
		privilegeRepository.save(privilegeEntity);
		return "Privilege Deleted!";
	}

	
}

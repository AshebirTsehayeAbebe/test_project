package optifoodmanagement.io.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import optifoodmanagement.io.entity.UserEntity;


@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	UserEntity findByEmail(String email); 
	
	UserEntity findByUserId(String userId);
	
	UserEntity findByEmailAndEncryptedPassword(String email, String encryptedPassword);
	
	UserEntity findByEmailVerificationToken(String emailVerificationToken);
	
	UserEntity findByEmailAndPasswordResetCode(String email, String resetCode);

	List<UserEntity> findByRestaurantId(Integer restaurantId);
	
	Page<UserEntity> findByFirstNameContainingOrLastNameContainingOrMobilePhoneContainingOrEmailContainingOrUserStatusContaining(
	        String searchKey, String searchKey2, String searchKey3, String searchKey4, String searchKey5,
	        Pageable pageableRequest);

	Page<UserEntity> findByTenantId(Long tenantId, Pageable pageableRequest);
	
	Page<UserEntity> findByRestaurantId(Integer restaurantId, Pageable pageableRequest);
	
	Page<UserEntity> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName,
	        Pageable pageableRequest);
	
	//	UserEntity findByPersonId(Long personId);
	
	
//	Page<UserEntity> findByUserTypeNot(String string, Pageable pageableRequest);
//	
//	Page<UserEntity> findByUserType(String string, Pageable pageableRequest);

	
}
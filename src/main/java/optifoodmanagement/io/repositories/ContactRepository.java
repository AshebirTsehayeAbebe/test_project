package optifoodmanagement.io.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import optifoodmanagement.io.entity.Contact;

@Repository
public interface ContactRepository extends PagingAndSortingRepository<Contact, Integer> {
	
	Contact findByContactIdAndIsDeleted(Integer contactId, boolean b);
	
	Page<Contact> findByIsDeleted(boolean b, Pageable pageableRequest);
	
	Page<Contact> findByIsDeletedAndNameContaining(boolean b, String searchKey, Pageable pageableRequest);
	
}

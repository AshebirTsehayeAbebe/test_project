package optifoodmanagement.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import optifoodmanagement.config.CryptoManager;
import optifoodmanagement.exception.AppException;
import optifoodmanagement.io.entity.Contact;
import optifoodmanagement.io.entity.Restaurant;
import optifoodmanagement.io.repositories.ContactRepository;
import optifoodmanagement.io.repositories.RestaurantRepository;
import optifoodmanagement.service.ContactService;
import optifoodmanagement.ui.model.request.ContactRequestModel;
import optifoodmanagement.ui.model.response.ContactResponseModel;

@Service
public class ContactSerivceImpl implements ContactService {
	
	static CryptoManager obj = new CryptoManager();
	
	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Override
	public ContactResponseModel saveContact(ContactRequestModel requestDetail) {
		ContactResponseModel returnValue = new ContactResponseModel();
		Contact contact = new Contact();
		
		BeanUtils.copyProperties(requestDetail, contact);
		String encryptedPhoneNumber = obj.encrypt(requestDetail.getPhoneNumber());
		String encryptedEmail = obj.encrypt(requestDetail.getEmail());
		contact.setPhoneNumber(encryptedPhoneNumber);
		contact.setEmail(encryptedEmail);
		Contact savedDetail = contactRepository.save(contact);
		
		if (savedDetail == null)
			throw new AppException("Record not saved!");
		BeanUtils.copyProperties(savedDetail, returnValue);
		
		String phoneNumber = obj.decrypt(savedDetail.getPhoneNumber());
		String email = obj.decrypt(savedDetail.getEmail());
		returnValue.setPhoneNumber(phoneNumber);
		returnValue.setEmail(email);
		
		return returnValue;
	}
	
	@Override
	public ContactResponseModel getContact(Integer contactId) {
		ContactResponseModel returnValue = new ContactResponseModel();
		Contact contact = contactRepository.findByContactIdAndIsDeleted(contactId, false);
		
		if (contact == null)
			throw new AppException("No record with this id!");
		
		BeanUtils.copyProperties(contact, returnValue);
		String phoneNumber = obj.decrypt(contact.getPhoneNumber());
		String email = obj.decrypt(contact.getEmail());
		returnValue.setPhoneNumber(phoneNumber);
		returnValue.setEmail(email);
		return returnValue;
	}
	
	@Override
	public List<ContactResponseModel> getContacts(String searchKey, int page, int limit) {
		
		List<ContactResponseModel> returnValue = new ArrayList<>();
		
		if (page > 0)
			page = page - 1;
		
		Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("contactId").descending());
		Page<Contact> contactPage = null;
		
		if ("".equals(searchKey))
			contactPage = contactRepository.findByIsDeleted(false, pageableRequest);
		else
			contactPage = contactRepository.findByIsDeletedAndNameContaining(false, searchKey, pageableRequest);
		
		List<Contact> contacts = contactPage.getContent();
		
		int totalPages = contactPage.getTotalPages();
		for (Contact contact : contacts) {
			
			ContactResponseModel contactResponseModel = new ContactResponseModel();
			BeanUtils.copyProperties(contact, contactResponseModel);
			String phoneNumber = obj.decrypt(contact.getPhoneNumber());
			String email = obj.decrypt(contact.getEmail());
			contactResponseModel.setPhoneNumber(phoneNumber);
			contactResponseModel.setEmail(email);
			
			if (returnValue.size() == 0) {
				contactResponseModel.setTotalPage(totalPages);
			}
			
			returnValue.add(contactResponseModel);
		}
		return returnValue;
	}
	
	@Override
	public ContactResponseModel updateContact(Integer contactId, ContactRequestModel requestDetail) {
		ContactResponseModel returnValue = new ContactResponseModel();
		Contact contact = contactRepository.findByContactIdAndIsDeleted(contactId, false);
		
		if (contact == null)
			throw new AppException("No record with this id!");
		BeanUtils.copyProperties(requestDetail, contact);
		String encryptedPhoneNumber = obj.encrypt(requestDetail.getPhoneNumber());
		String encryptedEmail = obj.encrypt(requestDetail.getEmail());
		contact.setPhoneNumber(encryptedPhoneNumber);
		contact.setEmail(encryptedEmail);
		Contact savedDetail = contactRepository.save(contact);
		
		if (savedDetail == null)
			throw new AppException("Record not updated!");
		BeanUtils.copyProperties(savedDetail, returnValue);
		String phoneNumber = obj.decrypt(savedDetail.getPhoneNumber());
		String email = obj.decrypt(savedDetail.getEmail());
		returnValue.setPhoneNumber(phoneNumber);
		returnValue.setEmail(email);
		return returnValue;
	}
	
	@Override
	public String deleteContact(Integer contactId) {
		List<Restaurant> restaurants = restaurantRepository.findByContactIdAndIsDeleted(contactId, false);
		if (restaurants.size() > 0)
			throw new AppException("Could not delete a contact that has restaurant!");
		
		Contact contact = contactRepository.findByContactIdAndIsDeleted(contactId, false);
		if (contact == null)
			throw new AppException("No record with this id!");
		contact.setDeleted(true);
		Contact savedDetail = contactRepository.save(contact);
		
		if (savedDetail == null)
			throw new AppException("Record not deleted!");
		return "Deleted successfully!";
	}
	
}

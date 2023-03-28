package optifoodmanagement.service;

import java.util.List;

import optifoodmanagement.ui.model.request.ContactRequestModel;
import optifoodmanagement.ui.model.response.ContactResponseModel;

public interface ContactService {
	
	ContactResponseModel saveContact(ContactRequestModel requestDetail);
	
	ContactResponseModel getContact(Integer contactId);
	
	List<ContactResponseModel> getContacts(String searchKey, int page, int limit);
	
	ContactResponseModel updateContact(Integer contactId, ContactRequestModel requestDetail);
	
	String deleteContact(Integer contactId);
	
}

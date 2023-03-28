package optifoodmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import optifoodmanagement.service.ContactService;
import optifoodmanagement.ui.model.request.ContactRequestModel;
import optifoodmanagement.ui.model.response.ContactResponseModel;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
	
	@Autowired
	ContactService contactService;
	
	@PostMapping
	@ApiOperation(value = "Saves contact detail to database", notes = "Provide contact detail to save", response = ContactResponseModel.class)
	public ContactResponseModel saveContact(@RequestBody ContactRequestModel requestDetail) {
		
		ContactResponseModel returnValue = contactService.saveContact(requestDetail);
		
		return returnValue;
		
	}
	
	@GetMapping(path = "/{contactId}")
	@ApiOperation(value = "Finds contact by id", notes = "Provide an id to look up specific contact from the contacts", response = ContactResponseModel.class)
	public ContactResponseModel getContact(@PathVariable Integer contactId) {
		ContactResponseModel returnValue = contactService.getContact(contactId);
		return returnValue;
	}
	
	@GetMapping
	@ApiOperation(value = "Fetches contacts for given limit per page", notes = "Provide limit and page to get required count of contacts per page", response = ContactResponseModel.class)
	public List<ContactResponseModel> getContacts(@RequestParam(value = "searchKey", defaultValue = "") String searchKey,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "limit", defaultValue = "1000") int limit) {
		List<ContactResponseModel> returnValue = contactService.getContacts(searchKey, page, limit);
		return returnValue;
	}
	
	@PutMapping(path = "/{contactId}")
	@ApiOperation(value = "Updates contact detail based on the given id", notes = "Provide contact detail with id to update the record", response = ContactResponseModel.class)
	public ContactResponseModel updateContact(@RequestBody ContactRequestModel requestDetail,
	        @PathVariable Integer contactId) {
		ContactResponseModel returnValue = contactService.updateContact(contactId, requestDetail);
		return returnValue;
	}
	
	@DeleteMapping(path = "/{contactId}")
	@ApiOperation(value = "Deletes contact for given id", notes = "Provide an id to delete specific contact from database", response = String.class)
	public String deleteContact(@PathVariable Integer contactId) {
		String returnValue = contactService.deleteContact(contactId);
		return returnValue;
	}
}

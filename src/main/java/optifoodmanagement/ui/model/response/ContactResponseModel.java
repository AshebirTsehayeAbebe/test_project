package optifoodmanagement.ui.model.response;

public class ContactResponseModel {
	
	private Integer contactId;
	
	private String firstName;
	
	private String name;
	
	private String phoneNumber;
	
	private String email;
	
	private Integer totalPage;
	
	public Integer getContactId() {
		return contactId;
	}
	
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	
	public String getFirstName() {
		return firstName;
	}

	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
}

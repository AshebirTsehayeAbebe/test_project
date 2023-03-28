package optifoodmanagement.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import optifoodmanagement.model.audit.Audit;

@Entity(name = "contact")
public class Contact extends Audit implements Serializable {
	
	private static final long serialVersionUID = 217777461560355700L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer contactId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column
	private String phoneNumber;
	
	@Column(nullable = false)
	private String email;
	
	public Integer getContactId() {
		return contactId;
	}
	
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFirstName() {
		return firstName;
	}

	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	
}

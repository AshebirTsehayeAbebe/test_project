package optifoodmanagement.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import optifoodmanagement.model.audit.Audit;

@Entity(name = "mac_address")
public class MacAddress extends Audit implements Serializable {
	
	private static final long serialVersionUID = 2032764385106648063L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer macAddressId;
	
	@Column
	private String macAddress;
	
	@Column
	private Integer restaurantId;
	
	@Column
	private String status;
	
	public Integer getMacAddressId() {
		return macAddressId;
	}
	
	public void setMacAddressId(Integer macAddressId) {
		this.macAddressId = macAddressId;
	}
	
	public String getMacAddress() {
		return macAddress;
	}
	
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	public Integer getRestaurantId() {
		return restaurantId;
	}
	
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	

}

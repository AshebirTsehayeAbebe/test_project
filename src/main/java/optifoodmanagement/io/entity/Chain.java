package optifoodmanagement.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import optifoodmanagement.model.audit.Audit;

@Entity(name="chain")
public class Chain extends Audit implements Serializable {
	
	private static final long serialVersionUID = 9033402897635452712L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer chainId;
	
	@Column(nullable = false, length = 50)
	private String chainName;
	
	public Integer getChainId() {
		return chainId;
	}
	
	public void setChainId(Integer chainId) {
		this.chainId = chainId;
	}
	
	public String getChainName() {
		return chainName;
	}
	
	public void setChainName(String chainName) {
		this.chainName = chainName;
	}
	
	
}

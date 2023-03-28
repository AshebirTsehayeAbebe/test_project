package optifoodmanagement.config.newconfig;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

//
//@Data
//@NoArgsConstructor
@Entity
@Table(name = "models")
public class Model {
	
	@Id
	@Column(columnDefinition = "text")
	private String id;
	
	@Column(nullable = false)
	@JsonFormat(shape = STRING)
	private Instant createdAt;
	
	@Column(nullable = false, columnDefinition = "text")
	private String tenant;
	
	public Model(String tenant) {
		this.tenant = tenant;
	}
	
	@PrePersist
	protected void prePersist() {
		id = Cuid.createCuid();
		createdAt = Instant.now();
	}
}

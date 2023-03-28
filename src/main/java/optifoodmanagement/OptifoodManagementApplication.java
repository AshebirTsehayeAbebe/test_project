package optifoodmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationListener;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		OptifoodManagementApplication.class,
		Jsr310JpaConverters.class
})
//@EnableFeignClients("com.api.microservices_api")
@EnableDiscoveryClient
public class OptifoodManagementApplication implements ApplicationListener<ApplicationReadyEvent>{

	private AutowireCapableBeanFactory beanFactory;
	
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		try {
			
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "openpgp", "openpgpwd");
			statement = connection.createStatement();
			statement.executeQuery("SELECT count(*) FROM pg_database WHERE datname = 'optifood_management'");
			ResultSet resultSet = statement.getResultSet();
			resultSet.next();
			int count = resultSet.getInt(1);
			if (count <= 0) {
				statement.executeUpdate("CREATE DATABASE optifood_management");
			} else {}
		}
		catch (SQLException e) {}
		finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			}
			catch (SQLException e) {}
		}
		SpringApplication.run(OptifoodManagementApplication.class, args);
		
	}
	

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
	}

	

	@Autowired
	public void MyFactoryBean(AutowireCapableBeanFactory beanFactory) {
	    this.beanFactory = beanFactory;
	}
	

}

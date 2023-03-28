package optifoodmanagement.controller;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenant-db")
public class CreateDBController {
	
	@GetMapping
	public void createTenantDB(@RequestParam(value = "db", defaultValue = "") String db) throws SQLException {
//		try {
//			Class.forName("org.postgresql.Driver");
//			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "openpg", "openpgpwd");
//			Statement smt = con.createStatement();
//			smt.executeUpdate("CREATE DATABASE [tenant5] WITH OWNER DEFAULT");
//		}
//		catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

	}
}

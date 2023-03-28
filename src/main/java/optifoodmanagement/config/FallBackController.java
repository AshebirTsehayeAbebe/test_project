package optifoodmanagement.config;

import java.io.IOException;
import java.time.Instant;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import optifoodmanagement.ui.model.response.AuthorizeApiResponseModel;

@RestController
@RequestMapping("/handle-exception")
public class FallBackController {
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
	@ResponseBody
	public ResponseEntity<AuthorizeApiResponseModel> handleException(HttpServletRequest request,
	        HttpServletResponse response) throws AddressException, MessagingException, IOException {
		
		HttpStatus status = (HttpStatus) request.getAttribute("status");
		
		AuthorizeApiResponseModel authorizeApiResponseModel = new AuthorizeApiResponseModel();
		authorizeApiResponseModel.setMessage((String) request.getAttribute("message"));
		authorizeApiResponseModel.setPath((String) request.getAttribute("path"));
		authorizeApiResponseModel.setStatus((HttpStatus) request.getAttribute("status"));
		authorizeApiResponseModel.setStatusCode(((HttpStatus) request.getAttribute("statusCode")).value());
		authorizeApiResponseModel.setTimestamp((Instant) request.getAttribute("timestamp"));
		
		return new ResponseEntity<AuthorizeApiResponseModel>(authorizeApiResponseModel, status);
		
	}
	
}

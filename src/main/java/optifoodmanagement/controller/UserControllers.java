package optifoodmanagement.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import optifoodmanagement.security.JwtTokenProvider;
import optifoodmanagement.service.UserService;
import optifoodmanagement.ui.model.request.LoginRequestModel;
import optifoodmanagement.ui.model.request.SearchRequestModel;
import optifoodmanagement.ui.model.request.UploadProfileRequestModel;
import optifoodmanagement.ui.model.request.UserDetailRequestModel;
import optifoodmanagement.ui.model.response.JwtAuthenticationResponse;
import optifoodmanagement.ui.model.response.OperationStatusModel;
import optifoodmanagement.ui.model.response.RequestOperationName;
import optifoodmanagement.ui.model.response.RequestOperationStatus;
import optifoodmanagement.ui.model.response.UserResponseModel;


@RestController
@RequestMapping("/api/user")
public class UserControllers {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@PostMapping
	@ApiOperation(value = "Saves user detail to database", notes = "Provide user detail to save")
	public UserResponseModel saveUser(@RequestHeader(value = "header") @RequestBody UserDetailRequestModel userDetails)
	        throws AddressException, MessagingException, IOException {
		
		UserResponseModel returnValue = userService.saveUser(userDetails);
		
		return returnValue;
	}
	
	@GetMapping(path="/{userId}")
	@ApiOperation(value = "Finds user by user id", notes = "Provide an user id to look up specific user from the users", response = UserResponseModel.class)
	public UserResponseModel getUserByUserId(@PathVariable String userId) {
		UserResponseModel returnValue = userService.getUserByUserId(userId);
		return returnValue;
	}
	
	@GetMapping(path="/get-user-by-id/{id}")
	@ApiOperation(value = "Finds user by id", notes = "Provide an id to look up specific user from the users", response = UserResponseModel.class)
	public UserResponseModel getUserById(@PathVariable Long id) {
		UserResponseModel returnValue = userService.getUserById(id);
		return returnValue;
	}
	
	
	@GetMapping
	@ApiOperation(value = "Fetches users for given limit per page", notes = "Provide limit and page to get required count of users per page", response = UserResponseModel.class)
	public List<UserResponseModel> getUsers(@RequestParam(value = "searchKey", defaultValue = "") String searchKey,
	        @RequestParam(value = "restaurantId", defaultValue = "0") Integer restaurantId,
	        @RequestParam(value = "page", defaultValue = "1") int page,
								   @RequestParam(value="limit", defaultValue = "25") int limit){
		
		List<UserResponseModel> returnValue = userService.getUsers(page, limit, searchKey, restaurantId);
		return returnValue;
	}
	
	@PutMapping(path="/{id}")
	@ApiOperation(value = "Updates user detail based on the given user id", notes = "Provide user detail with user id to update the record", response = UserResponseModel.class)
	public UserResponseModel updateUser(@PathVariable String id, @RequestBody UserDetailRequestModel userDetails) {
		
		UserResponseModel returnValue = userService.updateUser(id, userDetails);
		
		return returnValue;
	}
	
	@DeleteMapping(path="/{id}")
	@ApiOperation(value = "Deletes user for given id", notes = "Provide an id to delete specific user from database", response = String.class)
	public OperationStatusModel deleteUser(@PathVariable String id) {
		
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOpreationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(id);
		
		returnValue.setOpreationResult(RequestOperationStatus.SUCCESS.name());
		
		return returnValue;
	}
	
	@PostMapping("/login")
	@ApiOperation(value = "Sign in user to the system by authenticating user email and password", notes = "Provide correct email and password to sign in to the sytem", response = JwtAuthenticationResponse.class)
	public JwtAuthenticationResponse authenticateUser(@Valid @RequestBody LoginRequestModel loginRequest) {
		
		Authentication authentication = authenticationManager
		        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		JwtAuthenticationResponse returnValue = tokenProvider.generateToken(authentication, loginRequest.getLicenseKey());
		return returnValue;
	}
	
	@PostMapping(path = "/search")
	public List<UserResponseModel> searchUsers(@RequestBody SearchRequestModel searchkeyDetail,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "limit", defaultValue = "25") int limit) {
		
		List<UserResponseModel> returnValue = userService.searchUsers(searchkeyDetail, page, limit);
		
		return returnValue;
	}
	
	@PostMapping(path = "/uploadprofile")
	public String uploadProfilePicture(@ModelAttribute UploadProfileRequestModel requestDetail) throws IOException {
		
		String returnValue = userService.uploadProfilePicture(requestDetail);
		
		return returnValue;
	}
	//	
	//	@GetMapping(path = "/checkemail/{email}")
	//	public String checkEmail(@PathVariable String email) {
	//		String returnValue = userService.checkEmail(email);
	//		return returnValue;
	//		
	//	}
	//	
	@PutMapping(path = "changestatus/{id}")
	public UserResponseModel updateUserStatus(@PathVariable String id, @RequestBody UserDetailRequestModel userDetails) {
		
		UserResponseModel returnValue = userService.updateUserStatus(id, userDetails);
		
		return returnValue;
	}
	//	
	//	@PutMapping(path = "/resetpassword")
	//	public String resetPassword(@RequestBody ResetPasswordRequestModel resetPasswordDetail) {
	//		
	//		String returnValue = userService.resetPassword(resetPasswordDetail);
	//		return returnValue;
	//	}
	//	
	//	
	//
	//	@GetMapping("/current-user/{token}")
	//	public JwtAuthenticationResponse getCurrentUserDetail(@PathVariable("token") String token) {
	//		
	//		JwtAuthenticationResponse returnValue = userService.getCurrentUserDetail(token);			
	//		return returnValue;
	//	}	

	
}

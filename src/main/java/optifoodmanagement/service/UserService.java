package optifoodmanagement.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.security.core.userdetails.UserDetailsService;

import optifoodmanagement.ui.model.request.ResetPasswordRequestModel;
import optifoodmanagement.ui.model.request.SearchRequestModel;
import optifoodmanagement.ui.model.request.UploadProfileRequestModel;
import optifoodmanagement.ui.model.request.UserDetailRequestModel;
import optifoodmanagement.ui.model.response.JwtAuthenticationResponse;
import optifoodmanagement.ui.model.response.UserResponseModel;

public interface UserService extends UserDetailsService{
	
	UserResponseModel saveUser(UserDetailRequestModel user) throws AddressException, MessagingException, IOException;
	
	UserResponseModel getuser(String email);
	
	UserResponseModel getUserByUserId(String userId);
	
	UserResponseModel updateUser(String userId, UserDetailRequestModel userDetails);
	void deleteUser(String userId);
	
	List<UserResponseModel> getUsers(int page, int limit, String searchKey, Integer restaurantId);
	
	UserResponseModel updateUserStatus(String id, UserDetailRequestModel userDetails);
	String checkEmail(String email);
	
	List<UserResponseModel> searchUsers(SearchRequestModel searchkeyDetail, int page, int limit);
	String uploadProfilePicture(UploadProfileRequestModel requestDetail) throws IOException;
	String resetPassword(ResetPasswordRequestModel resetPasswordDetail);
	String changeAccountPassword(ResetPasswordRequestModel changePassRequest);
	JwtAuthenticationResponse getCurrentUserDetail(String token);
	
	UserResponseModel getUserById(Long id);
	
	
}

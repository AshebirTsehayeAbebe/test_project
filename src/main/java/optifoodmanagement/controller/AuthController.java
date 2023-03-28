package optifoodmanagement.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import optifoodmanagement.security.JwtTokenProvider;
import optifoodmanagement.service.UserService;
import optifoodmanagement.ui.model.request.LoginRequestModel;
import optifoodmanagement.ui.model.request.ResetPasswordRequestModel;
import optifoodmanagement.ui.model.request.UserDetailRequestModel;
import optifoodmanagement.ui.model.response.JwtAuthenticationResponse;
import optifoodmanagement.ui.model.response.UserResponseModel;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Autowired
	UserService userService;

    @PostMapping("/login")	
    public JwtAuthenticationResponse authenticateUser(@Valid @RequestBody LoginRequestModel loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtAuthenticationResponse returnValue = tokenProvider.generateToken(authentication, loginRequest.getLicenseKey());
        return returnValue;
    }

    @PostMapping(path="/signup")
	public UserResponseModel createUser(@RequestBody UserDetailRequestModel userDetails)
	        throws AddressException, MessagingException, IOException {
		
		UserResponseModel returnValue = userService.saveUser(userDetails);
		
		return returnValue;
	}
    
    @PutMapping(path="/changepassword")
	public String changeAccountPassword(@RequestBody ResetPasswordRequestModel changePassRequest) {
    	
    	String returnValue = "Password not Changed";
    	Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		changePassRequest.getEmail(),
                		changePassRequest.getOldPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtAuthenticationResponse authenticated = tokenProvider.generateToken(authentication, changePassRequest.getLicenceKey());
        if(authenticated != null) {
        	 returnValue = userService.changeAccountPassword(changePassRequest);
        }
		return returnValue;
	}
}

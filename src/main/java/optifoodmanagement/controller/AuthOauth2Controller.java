package optifoodmanagement.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import optifoodmanagement.security.JwtTokenProvider;
import optifoodmanagement.service.UserService;

@RestController
@RequestMapping("/login")
public class AuthOauth2Controller {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Autowired
	UserService userService;
    
//    @GetMapping	
//    public String authenticateOauth2User() {
//    	
//        return "mmmmmmm";
//    }
    @GetMapping
    public String user(Authentication authentication) {
		DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
		String userName = userDetails.getAttribute("email");
        return userName;
    }



}

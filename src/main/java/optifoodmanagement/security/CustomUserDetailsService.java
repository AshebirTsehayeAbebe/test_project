package optifoodmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import optifoodmanagement.exception.AppException;
import optifoodmanagement.exception.ResourceNotFoundException;
import optifoodmanagement.io.entity.UserEntity;
import optifoodmanagement.io.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        UserEntity user = userRepository.findByEmail(usernameOrEmail);
        if(user == null) throw new AppException("Invalid login");

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
    	UserEntity user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}
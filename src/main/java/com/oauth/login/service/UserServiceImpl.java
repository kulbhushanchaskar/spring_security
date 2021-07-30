package com.oauth.login.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oauth.login.model.User;
import com.oauth.login.security.UserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	//Inject repository object to perform db operations
    //private final UserRepository userRepository;

    public UserDetails loadUserById(String id) {
    	//get user from db and generate
    	
    	User user = new User();
    	
    	user.setId("1");
    	user.setName("myuser");
        return UserPrincipal.create(user);
    }
}

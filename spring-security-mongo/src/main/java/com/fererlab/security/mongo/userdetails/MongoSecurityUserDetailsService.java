package com.fererlab.security.mongo.userdetails;

import com.fererlab.security.mongo.entity.MongoSecurityUserRoles;
import com.fererlab.security.mongo.repository.MongoSecurityUserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MongoSecurityUserDetailsService implements UserDetailsService {
 
    @Autowired
    private MongoSecurityUserDetailsRepository userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String username) {
        MongoSecurityUserRoles userRoles = userRepository.findByUsername(username);
        if (userRoles == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MongoSecurityUserDetails(userRoles);
    }

}
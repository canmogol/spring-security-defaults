package com.fererlab;

import com.fererlab.security.mongo.entity.JpaSecurityRole;
import com.fererlab.security.mongo.entity.JpaSecurityUser;
import com.fererlab.security.mongo.repository.JpaSecurityRoleRepository;
import com.fererlab.security.mongo.repository.JpaSecurityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class RestJpaExampleApplication extends SpringBootServletInitializer implements ApplicationListener<ApplicationReadyEvent> {

    public static void main(String[] args) {
        SpringApplication.run(RestJpaExampleApplication.class, args);
    }

    @Autowired
    private JpaSecurityUserRepository userDetailsRepository;

    @Autowired
    private JpaSecurityRoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // lets add an admin user
        JpaSecurityUser admin = new JpaSecurityUser();
        admin.setUsername("admin");
        admin.setPassword("admin");
        JpaSecurityRole role_name_query = new JpaSecurityRole("ROLE_NAME_QUERY");
        JpaSecurityRole role_name_command = new JpaSecurityRole("ROLE_NAME_COMMAND");
        JpaSecurityRole roleQuery = roleRepository.save(role_name_query);
        JpaSecurityRole roleCommand = roleRepository.save(role_name_command);
        admin.getRoles().add(roleQuery);
        admin.getRoles().add(roleCommand);
        userDetailsRepository.save(admin);
    }
}
